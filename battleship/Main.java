package battleship;


import battleship.Attack.AI;
import battleship.Attack.DumbAI;
import battleship.Attack.SmartAI;
import battleship.Model.*;
import java.util.*;

public class Main {
    AI ai;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        AI ai = new AI();

        Board testBoard = new Board(Constants.boardSize);

        testBoard.getPoint(1, 1).getIsHit();

        Ship carrier = new Ship(testBoard, "Carrier", 5, testBoard.getPoint(6, 2), "DOWN");

        testBoard.drawBoard(testBoard);
        
        

        
    
        while(!ai.allSunk(carrier)){
            System.out.println("Enter a coordinate:");
            String coordinate = input.nextLine();
            
        }
        

    }
    String coord;

    public int[] (String coord){
        String [] coor = coord.split(" ");
        
        

    }
}
