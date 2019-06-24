/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingsource;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Carla
 */
public class Grid extends JPanel {
    private String map = "++++++++++@+@+++++++\n"+
                "##+#+++@@@@@@+++++++\n" +
                "######++@xxx@+++++++\n" +
                "######++@xxxx@++++++\n" +
                "++#+++++@@xxx@++@@++\n" +
                "+##+@@+++@@@@@+@@@++\n" +
                "#@++@@+++++++++@@+++\n" +
                "+@+@++++++++++++++##\n" +
                "++@xx@@++++++++++###\n" +
                "+@xxxx@@++++#######+\n" +
                "+@x@@@x@#+++####@+++\n" +
                "+@@+++####++###+++++\n" +
                "++########++++++++++\n" +
                "#########++o+++++++o\n" +
                "++####+++@@oo++++++o\n" +
                "++++++++++@+ooo++ooo\n" +
                "+@@@@@+++@+++ooooooo\n" +
                "@@+++++++++++ooooooo\n" +
                "+++++++++++ooooooooo\n" +
                "+++++++++++ooooooooo";    
    
   private JLabel[][] labelsCells;
   private JLabel cell = new JLabel();
   private JLabel cellStartingPoint = new JLabel();
   
   private String [][] cellsOriginalColor;
   private boolean isStartingPointSelected = false;
   private boolean isDestinationPointSelected = false;
   
   private Node[][] nodeList;
   private int gridSizeY;
   private int gridSizeX;
   private int cellWidth;
   Node startNode, targetNode;   
   
   public Grid(int gridSizeX, int gridSizeY, int cellWidth) {
        nodeList = new Node [gridSizeX][gridSizeY];
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        this.cellWidth = cellWidth;
        labelsCells = new JLabel[gridSizeX][gridSizeY];
        cellsOriginalColor = new String[gridSizeX][gridSizeY];
        Dimension celSize = new Dimension(cellWidth, cellWidth);
        MyMouseListener myListener = new MyMouseListener(this);

        setLayout(new GridLayout(gridSizeX, gridSizeY));
        char character;
        int index = 0;
        int num = 1;
        for (int x = 0; x < labelsCells.length; x++) {
            for (int y = 0; y < labelsCells[x].length; y++) {
                character = map.charAt(index);

                cell = new JLabel(); //Integer.toString(num)
                cell.setOpaque(true);            
                cell.setBackground(Color.decode(getColorMathed(character)));
                cell.setPreferredSize(celSize);
                cell.setBorder(BorderFactory.createLineBorder(Color.decode("#C0C0FF")));

                //The black cells are not walkable
                if(character != 'o'){
                    cell.addMouseListener(myListener);
                    nodeList[x][y] = new Node(true, x, y, Integer.toString(num), getWeightMathed(character));
                }
                else 
                    nodeList[x][y] = new Node(false, x, y, Integer.toString(num), -1);

                add(cell);
                labelsCells[x][y] = cell;
                cellsOriginalColor[x][y] = getColorMathed(character);

                index++;
                num++;
            }
            index++;   //  This is to skip the empty character which means the break line on the map string
        }
    } 
    
    public ArrayList<Node> getNeighbours(Node node, char heuristicType){
        ArrayList<Node> neighbours = new ArrayList();

        for(int x = -1; x <= 1; x++){
             for(int y = -1; y <= 1; y++){
                if(x == 0 && y == 0)
                    continue;                

                int checkX = node.gridXPosition + x;
                int checkY = node.gridYPosition + y;
                
                if(checkX >= 0 && checkX < gridSizeX && checkY >= 0 && checkY < gridSizeY && nodeList[checkX][checkY].walkable){
                    if(startNode == nodeList[checkX][checkY])
                        continue;

                    //Without this verification, by using manhattan, the same nodes already visited are revisited again for some reason
                    if(nodeList[checkX][checkY].alreadyVisited)
                        continue;

                    boolean isDiagonal = false;
                    if((heuristicType == 'm' && checkX < node.gridXPosition && checkY < node.gridYPosition) ||
                    (heuristicType == 'm' && checkX < node.gridXPosition && checkY > node.gridYPosition) ||
                    (heuristicType == 'm' && checkX > node.gridXPosition && checkY < node.gridYPosition) ||
                    (heuristicType == 'm' && checkX > node.gridXPosition && checkY > node.gridYPosition))
                        isDiagonal = true;                
                
                    if(isDiagonal)
                        nodeList[checkX][checkY].weight += nodeList[checkX][checkY].weight;
                    nodeList[checkX][checkY].alreadyVisited = true;
                    neighbours.add(nodeList[checkX][checkY]);
                    //JLabel label = labelsCells[checkX][checkY];
                    //label.setBackground(Color.PINK);
                }
            }
        }
        return neighbours;
    }   
   
    public String getColorMathed(char character){
        switch(character){
            case '+':   //  white
                return "#FFFFFF";
            case 'x':   // middle grey
                return "#E0E0E0";
            case '@':   // lightest grey
                return "#F0F0F0";
            case 'o':   // black
                return "#000000";

            case '#':   // darkest grey
                return "#D0D0D0";          
        }
        return null;
    }
    
    public int getWeightMathed(char character){
        switch(character){
            case '+':   //  white
                return 1;
            case 'x':   // middle grey
                return 3;
            case '@':   // lightest grey
                return 2;
            case '#':   // darkest grey
                return 4;          
        }
        return -1;
    }    

   public void labelPressed(JLabel cellLabel) {
      for (int x = 0; x < labelsCells.length; x++) {
         for (int y = 0; y < labelsCells[x].length; y++) {
            if (cellLabel == labelsCells[x][y]) {
                if(isStartingPointSelected && !isDestinationPointSelected){
                    if(cellLabel != cellStartingPoint){
                        isDestinationPointSelected = true;          
                        targetNode = nodeList[x][y];
                        findAndTraceTheShortestPath(startNode, targetNode);
                    }
                }
                else if(!isStartingPointSelected){                     
                    isStartingPointSelected = true;
                    cellLabel.setBackground(Color.GREEN);
                    cellStartingPoint = cellLabel;
                    startNode = nodeList[x][y];
                }
            }
         }
      }
   }
   
    private void findAndTraceTheShortestPath(Node startNode, Node targetNode){
        char heuristicType = ' ';
        if(Main.chebyshevRadioButton.isSelected())
             heuristicType = 'c';
        else if(Main.euclideanRadioButton.isSelected())
            heuristicType = 'e';
        else if(Main.manhattanRadioButton.isSelected())
            heuristicType = 'm';
               
        ArrayList<Node> nodeFoundList = PathFinding.findPath(this, startNode, targetNode, heuristicType);

        int totalCostOfShortestPath = 0;
        for (Node nodeFound : nodeFoundList){
                JLabel label = labelsCells[nodeFound.gridXPosition][nodeFound.gridYPosition];
                label.setBackground(Color.red);
                totalCostOfShortestPath += nodeFound.weight;
        }
        cellStartingPoint.setBackground(Color.red);        
        Main.costValueLabel.setText(Integer.toString(totalCostOfShortestPath));
    }
}