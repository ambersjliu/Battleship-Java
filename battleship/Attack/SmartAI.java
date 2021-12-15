package battleship.Attack;

import battleship.Model.*;
import java.util.*;

public class SmartAI extends AI {
    public enum Mode{
        HUNT, TARGET
    }

    
     //aim determines which mode to go into based on success of prev shot?
    public Coordinate aim(Point last, Board board, Ship[] ships){
        if(last.getIsTaken()){
            return target(Mode.TARGET, board, pastHits);            
        }
        else if(last.getIsSunk()){
            for(int row = 0; row< Constants.boardSize; row++){
                for(int col = 0; col < Constants.boardSize; col++){
                    
                }
            }
        }

    } 

/*     //called with aim
    public int[] target(Mode mode, Board board, ArrayList<Coordinate> arr){
        Random rand = new Random();
        int[] coord = new int[]{1, 2};

        if(mode == mode.HUNT){
            while((coord[0]+coord[1])%2==1 && !arr.contains(coord)){
                coord[0] = rand.nextInt(Constants.boardSize);
                coord[1] = rand.nextInt(Constants.boardSize);
            }
            
        }else{
            //gonna write aim first lol
        }
    } */
}
