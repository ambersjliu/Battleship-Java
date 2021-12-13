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
        //create array of size 2 to save each point's coordinates in
        //add it to the ship's "shipPoints" ArrayList

        /* if(orientation.equals("V")){
            for (int i = 0; i < shipLength; i++) {    
                coords[0] = start.getY()-i; //going up
                coords[1] = start.getX();
                shipPoints.add(coords); 

                board.getPoint(start.getY()-i, start.getX()).setShipId(shipName);
                board.getPoint(start.getY()-i, start.getX()).setIsTaken(true);
            }
        } */

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

        /* if(orientation.equals("LEFT")){
            for (int i = 0; i < shipLength; i++) { 
                coords[0] = start.getY(); 
                coords[1] = start.getX()-i;//going left
                shipPoints.add(coords); 

                board.getPoint(start.getY(), start.getX()-i).setShipId(shipName);
                board.getPoint(start.getY(), start.getX()-i).setIsTaken(true);
            }
        } */

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
