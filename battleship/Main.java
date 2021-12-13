package battleship;

import battleship.Attack.AI;
import battleship.Attack.DumbAI;
import battleship.Attack.SmartAI;
import battleship.Model.*;
import java.util.*;
import java.util.zip.Adler32;

public class Main {
    AI ai;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        AI ai = new AI();

        Board testBoard = new Board(Constants.boardSize);

        testBoard.getPoint(1, 1).getIsHit();

        Ship carrier = new Ship(testBoard, "Carrier", 5, testBoard.getPoint(6, 2), "DOWN");

        carrier.printShipPoints();
        
        while (carrier.getShipLength() > 0) {

            testBoard.drawBoard(testBoard);

            System.out.println("Enter a coordinate:");

            // convert inputted coord into an array of 2 ints
            
            String coord = input.nextLine().toUpperCase(); //get user input
            /* char[] coordArray = coord.toCharArray(); //convert
            int ascii = Character.toUpperCase(coordArray[0]);
            int[] numcoord = { ascii - 65, (coordArray[1] - '0')-1 };
            System.out.println(Arrays.toString(numcoord)); */



            int col = Integer.parseInt(coord.substring(1)) - 1;
            int row= ((int) coord.charAt(0)) - 65;

            int[] numcoord = {row,col}; 

            System.out.println(Arrays.toString(numcoord));

            if(carrier.getShipPoints().contains(numcoord)){
                carrier.setShipLength(carrier.getShipLength()-1);
               
                System.out.println("Hit!");
            }else{
               
                System.out.println("Miss!");
            }
            testBoard.getPoint(numcoord[0], numcoord[1]).setIsHit(true);
            
 /*            if (testBoard.getPoint(numcoord[0],numcoord[1]).getIsTaken() == true){
                testBoard.getPoint(numcoord[0],numcoord[1]).setIsHit(true);
                carrier.setShipLength(carrier.getShipLength()-1);
                System.out.println(carrier.getShipLength());
            }
            else{
                testBoard.getPoint(numcoord[0],numcoord[1]).setIsHit(true);
                System.out.println(testBoard.getPoint(numcoord[0],numcoord[1]).getIsTaken());
            } */

            
        }

    }

}
