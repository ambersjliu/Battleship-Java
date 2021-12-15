package battleship.Attack;
import battleship.Model.*;
import battleship.UI.Renderer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class AI {
    protected Board enemyBoard;
    protected Renderer renderer;
    protected ArrayList<int[]> pastHits = new ArrayList<int[]>();

    //todo: checkIfHit
    //todo: checkIfSunk
    //figure out why contains is not cooperating...
    //tmrw: get random ai moves going?
    
    //checkIfHit
    //takes in int coord array, board
    //check if point has id 
    //if has id, check if sunk, print sunk (shipId)
    //print hit (shipId) otherwise

    //if id is default then print miss
    //either way set the point isHit = true

    public AI(){
        enemyBoard = new Board(Constants.boardSize);
        renderer=new Renderer();
    }



    public HashMap<String, Ship> placeShips(Ship[] ships, Board board){
        HashMap<String, Ship> shipDict = new HashMap<>();
        
        for(int i = 0; i<ships.length; i++){
            Random rand = new Random();
            boolean okShip = false;
            
            int startX = 0;
            int startY = 0;
            int orientation = 0; //either 1 or 0: 0 is down, 1 is right

            

            while (!okShip) { // while the ship isnt ok
                startX = rand.nextInt(10); // generate new start coords
                startY = rand.nextInt(10);
                orientation = rand.nextInt(2);

                System.out.println("The orientation is "+ orientation);

                // check if it's valid
                okShip = validShip(board, ships[i].getShipLength(), board.getPoint(startY, startX),
                        Constants.orientation[orientation]);
            }

            Ship newShip = new Ship(board, ships[i].getShipName(), ships[i].getShipLength(),
                    board.getPoint(startY, startX), Constants.orientation[orientation]);
            shipDict.put(ships[i].getShipName(), newShip);
            }
        return shipDict;
     }


    public boolean validShip(Board board, int shipLength, Point start, String orientation){
        int row=start.getY();
        int col=start.getX();  
        

        if(orientation == "DOWN"){
            int endRow = row + shipLength;
            System.out.println("End row: " + endRow);
            if(endRow>Constants.boardSize){
                return false;
            }

            for(int i = row; i<endRow; i++){
                if(!board.getPoint(i, col).getShipId().equals("default")){
                    return false;
                }
            }

        }

        if(orientation == "RIGHT"){
            int endCol= col + shipLength;
            System.out.println("End col: " + endCol);

            if(endCol>Constants.boardSize){
                return false;
            }

            for(int i = col; i<endCol; i++){
                if(!board.getPoint(row, i).getShipId().equals("default")){
                    return false;
                }
            }
        }

        return true;
        
    }



    // public boolean checkIfSunk(String shipID, HashMap <String, Ship> shipDict){
    //     ArrayList<int[]> shipPoints = new ArrayList<int[]>();
    //     shipPoints = shipDict.get(shipID).getShipPoints();
    //     boolean check = false;
    //     int hits = 0;
    //     for (int i = 0; i <shipDict.get(shipID).getShipLength();i++){
    //         //this is really bad
    //         // if shipID's Points at index i is hit
    //         if (shipPoints.get(i).getIsHit() == true){
    //            hits++;
    //         }
    //     }

    //     if (hits = shipDict.get(shipID).getShipLength()){
    //        return true;
    //     }

        
        

    // }




}
