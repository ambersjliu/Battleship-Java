package battleship.Attack;

import battleship.Model.*;
import java.util.*;

public class SmartAI extends AI {
    int direction = 0;
    boolean mode = false; //false = HUNT, true = TARGET
    boolean directionSet = false;
    Coordinate firstHit = new Coordinate(-1, -1);
    ArrayList<Coordinate> hits = new ArrayList<Coordinate>();

    

    Random rand = new Random();


    public Coordinate target(){
        Coordinate returnCoord = new Coordinate(0,0);
        if(!directionSet){ //havent found the ship's next point, go around
            System.out.println("Direction not set, current direction: " + direction);
            switch (direction) {
                case 0: //up
                    if(firstHit.getRow()==0){ //if we're on first row, go right instead
                        direction = 1;
                        returnCoord.setRow(firstHit.getRow());
                        returnCoord.setColumn(firstHit.getColumn()+1);
                    }else{
                        returnCoord.setRow(firstHit.getRow()-1);
                        returnCoord.setColumn(firstHit.getColumn());
                    }
                    break;
                case 1:
                    if(firstHit.getColumn()==Constants.boardSize-1){ //if we're on last row, go right instead
                        direction = 2;
                        returnCoord.setRow(firstHit.getRow()+1);
                        returnCoord.setColumn(firstHit.getColumn());
                    }else{
                        returnCoord.setRow(firstHit.getRow());
                        returnCoord.setColumn(firstHit.getColumn()+1);
                    }               
                    break;
                case 2://go down
                    if(firstHit.getRow()==Constants.boardSize-1){ //if we're on first row, go right instead
                        direction = 3; //go left
                        returnCoord.setRow(firstHit.getRow());
                        returnCoord.setColumn(firstHit.getColumn()-1);
                    }else{
                        returnCoord.setRow(firstHit.getRow()+1);
                        returnCoord.setColumn(firstHit.getColumn());
                    }                  
                    break;
                case 3:
                    returnCoord.setRow(firstHit.getRow());
                    returnCoord.setColumn(firstHit.getColumn()-1);
                    break;
                default:
                    break;
            }
        }else{
            System.out.println("Direction set, current direction:" + direction);
            Coordinate lastHit = hits.get(hits.size()-1);
            switch (direction) {
                case 0: //up
                    if(lastHit.getRow()==0){ //if we reach top, switch direction to down from the first shot
                        direction = 2;
                        returnCoord.setRow(firstHit.getRow()+1);
                        returnCoord.setColumn(firstHit.getColumn());
                    }else{
                        returnCoord.setRow(lastHit.getRow()-1);
                        returnCoord.setColumn(lastHit.getColumn());
                    }
                    break;
                case 1:
                    if(lastHit.getColumn()==Constants.boardSize-1){ //if we're on last row, go left from firstshot instead
                        direction = 3;
                        returnCoord.setRow(firstHit.getRow());
                        returnCoord.setColumn(firstHit.getColumn()-1);
                    }else{
                        returnCoord.setRow(lastHit.getRow());
                        returnCoord.setColumn(lastHit.getColumn()+1);
                    }               
                    break;
                case 2://go down
                    if(lastHit.getRow()==Constants.boardSize-1){ //go up if too low
                        direction = 0; //go up
                        returnCoord.setRow(firstHit.getRow()-1);
                        returnCoord.setColumn(firstHit.getColumn());
                    }else{
                        returnCoord.setRow(lastHit.getRow()+1);
                        returnCoord.setColumn(lastHit.getColumn());
                    }                  
                    break;
                case 3: 
                    if(lastHit.getColumn()==0){//go right if out of bounds
                        returnCoord.setRow(firstHit.getRow());
                        returnCoord.setColumn(firstHit.getColumn()+1);
                    }else{
                        returnCoord.setRow(lastHit.getRow());
                        returnCoord.setColumn(lastHit.getColumn()-1);
                    }
                    break;
                default:
                    break;
            }
        }
        return returnCoord;
         
    }

    public Coordinate attack(){
        if(!mode){
            return hunt();
        }else{
            return target();
        }
    }



    public Coordinate hunt(){
        Coordinate coord = new Coordinate(0,0);
        while((coord.getRow()+coord.getColumn())%2==0 && !pastShots.contains(coord)){
            coord.setRow(rand.nextInt(Constants.boardSize));
            coord.setColumn(rand.nextInt(Constants.boardSize));
        }
        pastShots.add(coord);
        return coord;
    }

    public void resetVals(){
        mode = false;
        directionSet = false;
        direction = 0;
        firstHit.setColumn(-1);
        firstHit.setRow(-1);
        hits.clear();
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

    public ArrayList<Coordinate> getHits(){
        return hits;
    }
}

