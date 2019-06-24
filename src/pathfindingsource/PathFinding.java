/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingsource;

import java.util.ArrayList;
/**
 *
 * @author Carla
 */
public class PathFinding {
    
    public static ArrayList<Node> findPath(Grid grid, Node startNode, Node targetNode, char heuristicType){
        long startTime = System.nanoTime();
        ArrayList<Node> openSet = new ArrayList();
        ArrayList<Node> closedSet = new ArrayList();
        openSet.add(startNode);

        while (openSet.size() > 0){
            Node currentNode = openSet.get(0);
            for (int i = 1; i < openSet.size(); i++){
                if (openSet.get(i).getFCost() < currentNode.getFCost() || openSet.get(i).getFCost() == currentNode.getFCost() && openSet.get(i).hCost < currentNode.hCost)
                    currentNode = openSet.get(i);
            }
            
            openSet.remove(currentNode);
            closedSet.add(currentNode);

            if(currentNode == targetNode){
               long endTime = System.nanoTime();
               long duration = (endTime - startTime) / 1000000;
               System.out.println("Path found: " + duration + " ms");
               return retracePath(startNode, targetNode);
            }
            
            for (Node neighbour : grid.getNeighbours(currentNode, heuristicType)){
                if(!neighbour.walkable || closedSet.contains(neighbour))
                   continue;
                
                int newMovementCostToNeighbour = currentNode.gCost + neighbour.weight;
                if (newMovementCostToNeighbour < neighbour.gCost || !openSet.contains(neighbour)){
                    neighbour.gCost = newMovementCostToNeighbour;
                    neighbour.hCost = getDistance(neighbour, targetNode, heuristicType);
                    neighbour.parent = currentNode;

                    if (!openSet.contains(neighbour))
                        openSet.add(neighbour);
                }
            }
        }
        return null;
   }
   
    private  static ArrayList<Node> retracePath (Node startNode, Node endNode){
        ArrayList<Node> path = new ArrayList();
        Node currentNode = endNode;

        while (currentNode != startNode){
           path.add(currentNode);
           currentNode = currentNode.parent;
        }
        return path;
    }

    private static int getDistance (Node nodeA, Node nodeB, char heuristicType){
        int distanceX = Math.abs(nodeA.gridXPosition - nodeB.gridXPosition);
        int distanceY = Math.abs(nodeA.gridYPosition - nodeB.gridYPosition);

        switch(heuristicType){
            case 'e': //Euclidean distance
                return (int) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            case 'm': //Manhattan distance
                return distanceX + distanceY;       
            case 'c': //Chebyshev distance
                return Math.max(distanceX, distanceY);
        }
        return -1;
    }
}