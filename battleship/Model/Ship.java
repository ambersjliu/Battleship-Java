


package battleship.Model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * File: Ship.java
 * <p>Mr. Anadarajan
 * <p>ICS4U1
 * <p>09 December 2021
 * 
 * <p>Final Evaluation: Battleship Tournament
 * <p>Description: Defines the ship objects. The second, extended constructor will also place the ships on the board passed as a parameter.
 * <p>Part of the Model package, describing the data and objects used in the game.
 * @author Amber Liu
 */


public class Ship implements Serializable{ //so Ship objects can be saved and loaded
    private int shipLength;
    private String shipName;
    private Board board;
    private int startRow;
    private int startCol;
    private String orientation;
    private ArrayList<Coordinate> shipPoints = new ArrayList<Coordinate>(); //an array of what points the ship takes up

    /**
     * The basic constructor to create a ship. Used to initialize ship names/lengths before placing.
     * @param shipName name of ship
     * @param shipLength how long the ship is
     */
    public Ship(String shipName, int shipLength){
        this.shipLength = shipLength;
        this.shipName = shipName;
    }

    /**
     * The extended Ship constructor. Also places the ship on the specified board.
     * @param board which board to place on
     * @param shipName name of ship
     * @param shipLength how long the ship is
     * @param startRow //the row its starting point is on
     * @param startCol //the column its starting point is on
     * @param orientation //which direction the ship will go: right or down
     */
	public Ship(Board board, String shipName, int shipLength, int startRow, int startCol, String orientation) {
        this.shipLength = shipLength;
		this.board = board;
		this.shipName = shipName;
        this.startRow = startRow;
        this.startCol = startCol;
        this.orientation = orientation;

        //Only two directions, since using up/left is functionally the same
        if(orientation.equals("DOWN")){
			for (int i = 0; i < shipLength; i++) {
				int row = startRow + i; //since going down, only increase the row
				Coordinate coord = new Coordinate(row, startCol);

                shipPoints.add(coord);                 
                
				board.getPoint(row, startCol).setShipId(shipName); 
				board.getPoint(row, startCol).setIsTaken(true);
            }
        }
        else{
			for (int i = 0; i < shipLength; i++) {
				int col = startCol + i;
				Coordinate coord = new Coordinate(startRow, col);
                shipPoints.add(coord); 

				board.getPoint(startRow, col).setShipId(shipName);
				board.getPoint(startRow, col).setIsTaken(true);
            }
        }
    }




    public ArrayList<Coordinate> getShipPoints(){
        return shipPoints;
    }

/*     public void printShipPoints(){
        for(int i = 0; i<shipLength; i++){
            System.out.println(Arrays.toString(shipPoints.get(i)));
        }
    } */

    public int getShipLength(){
        return shipLength;
    }

    public void setShipLength(int length){
        shipLength =  length;
    }

    public String getShipName(){
        return shipName;
    }

    public void setShipName(String name){
        shipName  = name;
    }

	public int getShipSurvivingPoints() {
		int sum = 0;
		for (Coordinate c : shipPoints) {
			if (!this.board.getPoint(c.getRow(), c.getColumn()).getIsHit())
				sum++;
		}
		return sum;
	}

    public boolean checkIfSunk(String shipId, HashMap <String, Ship> ships){
        if (ships.get(shipId).getShipSurvivingPoints()==0){
            return true;
        }else{
            return false;
        }

    }
    public int getStartRow(){
        return startRow;
    }
    public int getStartCol(){
        return startCol;
    }
    public String getStartOrient(){
        return orientation;
    }
}
