package battleship.Attack;
import battleship.Model.*;
import battleship.UI.Renderer;
import java.util.ArrayList;

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

    public boolean validShip(Board board, int shipLength, Point start, String orientation){
        int row=start.getY();
        int col=start.getX();  
        

        if(orientation == "DOWN"){
            int endRow = row + shipLength;
            if(endRow>Constants.boardSize){
                return false;
            }

            for(int i = row; i<=endRow; i++){
                if(!board.getPoint(i, col).getShipId().equals("default")){
                    return false;
                }
            }

        }

        if(orientation == "RIGHT"){
            int endCol= col + shipLength;
            if(endCol>Constants.boardSize){
                return false;
            }

            for(int i = col; i<=endCol; i++){
                if(!board.getPoint(row, i).getShipId().equals("default")){
                    return false;
                }
            }
        }

        return true;
        
    }








}
