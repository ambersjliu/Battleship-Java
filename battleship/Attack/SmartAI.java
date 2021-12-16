package battleship.Attack;

import battleship.Model.*;
import java.util.*;

public class SmartAI extends AI {
    int direction = 0;
    boolean mode = false; //false = HUNT, true = TARGET
    boolean directionSet = false;
    Coordinate firstHit;

    Random rand = new Random();
/* 

    public Coordinate target(Point lastShot){
        //if last shot was a miss, then continue switching directions...
        //else, if the ship isn't sunk, then keep going
        //if we miss again and the ship isnt sunk, go back to the first shot in the opposite direction
    } */

    // public Coordinate nextMove(){
    //     if(!mode){
    //         return hunt();
    //     }else{
    //         return target();
    //     }
    // }

    public Coordinate hunt(){
        Coordinate coord = new Coordinate(0,0);
        while((coord.getRow()+coord.getColumn())%2==1 && !pastShots.contains(coord)){
            coord.setRow(rand.nextInt(Constants.boardSize));
            coord.setColumn(rand.nextInt(Constants.boardSize));
        }
        pastShots.add(coord);
        return coord;
    }



    


     //aim determines which mode to go into based on success of prev shot?
/*     public Coordinate aim(Point last, Board board, Ship[] ships){
        if(last.getIsTaken()){
            return target(Mode.TARGET, board, pastHits);            
        }
        else if(last.getIsSunk()){
            for(int row = 0; row< Constants.boardSize; row++){
                for(int col = 0; col < Constants.boardSize; col++){
                    
                }
            }
        } */



/*     //called with aim
    public int[] target(Mode mode, Board board, ArrayList<Coordinate> arr){
        Random rand = new Random();
        int[] coord = new int[]{1, 2};

        if(mode == mode.HUNT){
            while((coord[0]+coord[1])%2==1 && !arr.contains(coord)){
                coord[0] = rand.nextInt(Constants.boardSize);
                coord[1] = rand.nextInt(Constants.boardSize);
            }
            
        }else{
            //gonna write aim first lol
        }
    } */


    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isMode() {
        return this.mode;
    }

    public boolean getMode() {
        return this.mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public boolean isDirectionSet() {
        return this.directionSet;
    }

    public boolean getDirectionSet() {
        return this.directionSet;
    }

    public void setDirectionSet(boolean directionSet) {
        this.directionSet = directionSet;
    }

    public Coordinate getFirstHit() {
        return this.firstHit;
    }

    public void setFirstHit(Coordinate firstHit) {
        this.firstHit = firstHit;
    }
}

