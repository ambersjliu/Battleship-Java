package battleship;


import battleship.Attack.AI;
import battleship.Attack.DumbAI;
import battleship.Attack.SmartAI;
import battleship.Model.Point;
import battleship.Model.Board;

public class Main {
    AI ai;

    public static void main(String[] args) {
/*         if (low ai){
            ai=new DumbAI()
        }else{
            ai=new SmartAI()
        } */
        Board testBoard = new Board(10);

        //System.out.println(testBoard.get)

        //Point[][] testBoard = new Point[10][10];
        



        //"ships"
        for(int i = 2; i < 7; i++){
            testBoard.getPoint(i, 1).setIsTaken(true);
            testBoard.getPoint(1, i).setIsTaken(true);
        }

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(testBoard.getPoint(i, j).getIsTaken() == false){
                    System.out.print("o ");
                }else{
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
 




        

    }
}
