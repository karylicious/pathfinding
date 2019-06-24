/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingsource;

/**
 *
 * @author Carla
 */
public class Node {
    public boolean walkable;
    public int hCost; //heuristic value
    public int gCost; // Movement Cost
    public int gridXPosition;
    public int gridYPosition;
    public boolean alreadyVisited;
    public Node parent; // A node to reach this node
    public String id;
    public int weight;
    
    public Node(boolean walkable,  int gridX, int gridY , String id, int weight){
        this.id = id;
        gridXPosition = gridX;
        gridYPosition = gridY;
        this.walkable = walkable;
        this.weight = weight;
    }
    
    public int getFCost(){
        return gCost + hCost;
    }
}