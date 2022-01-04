package battleship.Model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Ship implements Serializable{
    private int shipLength;
    private String shipName;
    private Board board;
    private int startRow;
    private int startCol;
    private String orientation;
    private ArrayList<Coordinate> shipPoints = new ArrayList<Coordinate>();

    public Ship(String shipName, int shipLength){
        this.shipLength = shipLength;
        this.shipName = shipName;
    }

	public Ship(Board board, String shipName, int shipLength, int startRow, int startCol, String orientation) {
        this.shipLength = shipLength;
		this.board = board;
		this.shipName = shipName;
        this.startRow = startRow;
        this.startCol = startCol;
        this.orientation = orientation;

        if(orientation.equals("DOWN")){
			for (int i = 0; i < shipLength; i++) {
				int row = startRow + i;
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
