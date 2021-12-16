package battleship.Attack;

import battleship.Model.*;
import java.util.*;

public class SmartAI extends AI {
    int direction = 0;
    boolean mode = false; //false = HUNT, true = TARGET
    boolean directionSet = false;
    Coordinate firstHit;

    Random rand = new Random();


    public Coordinate target(){

        if(!directionSet){
            //fire shot in our current direction
            //direction should be incremented somewhere else...
        }else{
            //if last shot hit
                //return another hit in that direction]
            
        }
        
    }

    public Coordinate nextMove(){
        if(!mode){
            return hunt();
        }else{
            return target();
        }
    }



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

