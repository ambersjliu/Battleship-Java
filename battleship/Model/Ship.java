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

    public Ship(Board board, String shipName, int shipLength, Point start, Point end, String orientation){
        
        if(orientation.equals("UP")){
            for (int i = 0; i < shipLength; i++) {              
                shipPoints.add(board.getPoint(start.getY()-i, start.getX()));
                board.getPoint(start.getY()-i, start.getX()).setShipId(shipName);
            }
        }

        if(orientation.equals("DOWN")){
            for (int i = 0; i < shipLength; i++) {             
                shipPoints.add(board.getPoint(start.getY()+i, start.getX()));
                board.getPoint(start.getY()+i, start.getX()).setShipId(shipName);
            }
        }

        if(orientation.equals("LEFT")){
            for (int i = 0; i < shipLength; i++) {           
                shipPoints.add(board.getPoint(start.getY(), start.getX()-i));
                board.getPoint(start.getY()+i, start.getX()-i).setShipId(shipName);
            }
        }

        if(orientation.equals("RIGHT")){
            for (int i = 0; i < shipLength; i++) {          
                shipPoints.add(board.getPoint(start.getY(), start.getX()+i));
                board.getPoint(start.getY()+i, start.getX()+i).setShipId(shipName);
            }
        }
    }

}
