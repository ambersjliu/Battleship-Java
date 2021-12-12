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

        //the carrier length is 0 for some reason which is weird cuz its used
        // to dispay the ship, so i and to set carrier ship length again to make
        // it go into the while loop
        System.out.println(carrier.getShipLength());
        carrier.setShipLength(5);        
        System.out.println(carrier.getShipLength());

        
        while (carrier.getShipLength() > 0) {

            testBoard.drawBoard(testBoard);

            System.out.println("Enter a coordinate:");

            // convert inputted coord into an array of 2 ints

            String coord = input.next();
            char[] coordArray = coord.toCharArray();
            int ascii = Character.toUpperCase(coordArray[0]);
            int[] numcoord = { ascii - 65, (coordArray[1] - '0')-1 };
            System.out.println(Arrays.toString(numcoord));

            // check if carrier.shipPoints has that array
        
            //idk what that is ;-;
            // for (int i = 0; i>carrier.getShipLength();i++){
            //     if (carrier.getShipPoint(i))
            // }

            // instead i just use if point is taken
            if (testBoard.getPoint(numcoord[0],numcoord[1]).getIsTaken() == true){
                testBoard.getPoint(numcoord[0],numcoord[1]).setIsHit(true);
                carrier.setShipLength(carrier.getShipLength()-1);
                System.out.println(carrier.getShipLength());
            }
            else{
                testBoard.getPoint(numcoord[0],numcoord[1]).setIsMiss(true);
                System.out.println(testBoard.getPoint(numcoord[0],numcoord[1]).getIsMiss());
            }

            
        }

    }

}
