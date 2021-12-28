package battleship.Attack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import battleship.Model.*;
import battleship.UI.Renderer;

public class AI {
    protected Board enemyBoard;
    protected Renderer renderer;
    protected ArrayList<Coordinate> pastShots = new ArrayList<Coordinate>();

    //only used for smartAI
    protected int direction = 0;
    protected boolean mode = false; //false = HUNT, true = TARGET
    protected boolean directionSet = false;
    protected Coordinate firstHit = new Coordinate(-1, -1);
    protected ArrayList<Coordinate> hits = new ArrayList<Coordinate>();

    Random rand = new Random();


    public Coordinate getNextMove(int gameMode){
        if(gameMode == 1){
            System.out.println("You picked complex.");
            return attack();
        } else{
            System.out.println("You picked simple.");
            return randAttack();
        }
    } 

    //random attack
    public Coordinate randAttack() {
        System.out.println("Rand attack is running.");
        Coordinate coord = new Coordinate(1, 2);
        while((coord.getRow()+coord.getColumn())%2==1 && !pastShots.contains(coord)){
            coord.setRow(rand.nextInt(Constants.boardSize));
            coord.setColumn(rand.nextInt(Constants.boardSize));
        }
        pastShots.add(coord);
        return coord;
    }

    //hunt and target attack

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



    //ship placement methods
    public HashMap<String, Ship> placeShips(Ship[] ships, Board board){
        HashMap<String, Ship> shipDict = new HashMap<>();

        Random rand = new Random();
        for(int i = 0; i<ships.length; i++){
            boolean okShip = false;
            
            int startCol = 0;
            int startRow = 0;
            int orientation = 0; //either 1 or 0: 0 is down, 1 is right
            
            while (!okShip) { // while the ship isnt ok
                startCol = rand.nextInt(10); // generate new start coords
                startRow = rand.nextInt(10);
                orientation = rand.nextInt(2);


                // check if it's valid
				okShip = validShip(board, ships[i].getShipLength(), startRow, startCol,
                        Constants.orientation[orientation]);
            }

            Ship newShip = new Ship(board, ships[i].getShipName(), ships[i].getShipLength(),
					startRow, startCol, Constants.orientation[orientation]);
            shipDict.put(ships[i].getShipName(), newShip);
            }
        return shipDict;
     }


	public boolean validShip(Board board, int shipLength, int startRow, int startCol, String orientation) {

        if(orientation == "DOWN"){
				int endRow = startRow + shipLength;
            if(endRow>Constants.boardSize){
                return false;
            }

			for (int i = startRow; i < endRow; i++) {
				if (!board.getPoint(i, startCol).getShipId().equals("default")) {
                    return false;
                }
            }
        }

        if(orientation == "RIGHT"){
			int endCol = startCol + shipLength;

            if(endCol>Constants.boardSize){
                return false;
            }

			for (int i = startCol; i < endCol; i++) {
				if (!board.getPoint(startRow, i).getShipId().equals("default")) {
                    return false;
                }
            }
        }
        return true;
    }

    //setters, getters for hunt and target ai...

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

    public ArrayList<Coordinate> getPastShots(){

        return pastShots;
    }
   



   

    
}





