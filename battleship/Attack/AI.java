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

                System.out.println("The orientation is "+ orientation);

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
            System.out.println("End row: " + endRow);
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
            System.out.println("End col: " + endCol);

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
}
