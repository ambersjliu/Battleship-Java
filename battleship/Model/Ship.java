package battleship.Model;
import java.util.ArrayList;
import java.util.Arrays;

public class Ship {
    private int shipLength;
    private String shipName;
    private Board board;
    private ArrayList<int[]> shipPoints = new ArrayList<int[]>();

    public Ship(Board board, String shipName, int shipLength){
        this.shipLength = shipLength;
        this.shipName = shipName;
    }

    public Ship(Board board, String shipName, int shipLength, Point start, String orientation){
        int[] coords; 
        this.shipLength = shipLength;

        int row=start.getY();
        int col=start.getX();
        if(orientation.equals("DOWN")){
            for (int i = 0; i < shipLength; i++,row++) { 
                coords=new int[2];
                coords[0] = row; //going down
                coords[1] = col;
                shipPoints.add(coords);                 
                
                board.getPoint(row, col).setShipId(shipName);
                board.getPoint(row, col).setIsTaken(true);
            }
        }


        if(orientation.equals("RIGHT")){
            for (int i = 0; i < shipLength; i++, col++) {  
                coords=new int[2];        
                coords[0] = row; 
                coords[1] = col;//going right
                shipPoints.add(coords); 

                board.getPoint(row,col).setShipId(shipName);
                board.getPoint(row, col).setIsTaken(true);

            }
        }
    }




    public ArrayList<int[]> getShipPoints(){
        return shipPoints;
    }

    public void printShipPoints(){
        for(int i = 0; i<shipLength; i++){
            System.out.println(Arrays.toString(shipPoints.get(i)));
        }
    }

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


}
