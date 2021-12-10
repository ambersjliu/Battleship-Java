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
        



        //"ships"
        for(int i = 2; i < 7; i++){
            testBoard.getPoint(i, 1).setIsTaken(true);
            testBoard.getPoint(1, i).setIsTaken(true);
        }

        testBoard.drawBoard(testBoard);
 




        

    }
}
