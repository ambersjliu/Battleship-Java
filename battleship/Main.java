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
        
        while(carrier.getShipLength()>0){
            System.out.println("Enter a coordinate:");
            String coord = input.nextLine();
            //convert inputted coord into an array of 2 ints
            //check if carrier.shipPoints has that array
            //if yes then set ishit to true on the board and subtract shiplength by one
            //if no do nothing (loop repeats)
        }
        

    }
    
}
