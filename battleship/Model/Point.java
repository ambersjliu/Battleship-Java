

package battleship.Model;

import java.io.Serializable;

/**
 * File: Point.java
 * <p>Mr. Anadarajan
 * <p>ICS4U1
 * <p>06 January, 2021
 * 
 * <p>Final Evaluation: Battleship Tournament
 * <p>Description: Defines the Point objects. Each point can be hit, taken, or sunk. Usage: Boards are 2D arrays of Points.
 * <p>Part of the Model package, describing the data and objects used in the game.
 * @author Amber Liu
 * @author Elaine Yang
 */

public class Point implements Serializable{
    private boolean isTaken;
    private boolean isHit;
    private String shipId;
    private boolean isSunk;
    private int x;
    private int y;

    public Point(){
        this.isTaken = false;
        this.isHit = false;
        this.isSunk = false;
        this.shipId = "default";
        
    }
    
    public Point(int x, int y){
        this.isTaken = false;
        this.isHit = false;
        this.isSunk = false;
        this.shipId = "default";
        this.x = x;
        this.y = y;
    }

    public Point(boolean taken, boolean hit, boolean isSunk, String shipId, int x, int y){
        this.isTaken = taken;
        this.isHit = hit;
        this.shipId = shipId;
        this.isSunk = isSunk;
        this.x = x;
        this.y = y;
    }
    


    public boolean getIsHit(){
        return this.isHit;
    }

    public void setIsHit(boolean state){
        this.isHit = state;
    }

    public boolean getIsTaken(){
        return this.isTaken;
    }

    public void setIsTaken(boolean taken){
        this.isTaken = taken;
    }

    public String getShipId(){
        return this.shipId;
    }

    public void setShipId(String shipId){
        this.shipId = shipId;
    }

    public boolean getIsSunk(){
        return this.isSunk;
    }

    public void setIsSunk(boolean state){
        this.isSunk = state;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }



    

}
