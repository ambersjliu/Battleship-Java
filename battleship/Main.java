package battleship;


import battleship.Attack.AI;
import battleship.Attack.DumbAI;
import battleship.Attack.SmartAI;
import battleship.Model.*;


public class Main {
    AI ai;

    public static void main(String[] args) {
/*         if (low ai){
            ai=new DumbAI()
        }else{
            ai=new SmartAI()
        } */
        Board testBoard = new Board(Constants.boardSize);

        testBoard.getPoint(1, 1).getIsHit();

        //System.out.println(testBoard.get)

        //Point[][] testBoard = new Point[10][10];
        
        //Board board, String shipName, int shipLength, Point start, String orientation
        //"ships"
        Ship carrier = new Ship(testBoard, "Carrier", 5, testBoard.getPoint(6, 2), "DOWN");

        testBoard.drawBoard(testBoard);
        
 




        

    }

    
}
