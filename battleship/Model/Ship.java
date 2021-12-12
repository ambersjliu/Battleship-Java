package battleship.Model;
import java.util.ArrayList;

public class Ship {
    private int shipLength;
    private String shipName;
    private Board board;
    private ArrayList<Point> shipPoints = new ArrayList<Point>();

    public Ship(Board board, String shipName, int shipLength){
        this.shipLength = shipLength;
        this.shipName = shipName;
    }

    public Ship(Board board, String shipName, int shipLength, Point start, String orientation){
        
        if(orientation.equals("UP")){
            for (int i = 0; i < shipLength; i++) {              
                shipPoints.add(board.getPoint(start.getY()-i, start.getX()));
                board.getPoint(start.getY()-i, start.getX()).setShipId(shipName);
                board.getPoint(start.getY()-i, start.getX()).setIsTaken(true);
            }
        }

        if(orientation.equals("DOWN")){
            for (int i = 0; i < shipLength; i++) {             
                shipPoints.add(board.getPoint(start.getY()+i, start.getX()));
                board.getPoint(start.getY()+i, start.getX()).setShipId(shipName);
                board.getPoint(start.getY()+i, start.getX()).setIsTaken(true);
            }
        }

        if(orientation.equals("LEFT")){
            for (int i = 0; i < shipLength; i++) {           
                shipPoints.add(board.getPoint(start.getY(), start.getX()-i));
                board.getPoint(start.getY(), start.getX()-i).setShipId(shipName);
                board.getPoint(start.getY(), start.getX()-i).setIsTaken(true);
            }
        }

        if(orientation.equals("RIGHT")){
            for (int i = 0; i < shipLength; i++) {          
                shipPoints.add(board.getPoint(start.getY(), start.getX()+i));
                board.getPoint(start.getY(), start.getX()+i).setShipId(shipName);
                board.getPoint(start.getY(), start.getX()+i).setIsTaken(true);

            }
        }
    }



    public Point getShipPoint(int index){
        return shipPoints.get(index);
    }

    public int getShipLength(){
        return shipLength;
    }

    public void setShipLength(int newLength){
        shipLength =newLength;
        
    }

    public String getShipName(){
        return shipName;
    }

    public void setShipName(String name){
        shipName  = name;
    }


}
