package battleship;

import java.io.*;
import java.util.ArrayList;

import battleship.Attack.*;
import battleship.Model.*;




public class SaveLoad {

    AI ai = new AI();
    
    Board ourBoard = new Board(Constants.boardSize);
	Board enemyBoard = new Board(Constants.boardSize);
    Board board = new Board(Constants.boardSize);

    public void Save(int enemyMisses, int enemyHits, int ourHits, int ourMisses, ArrayList<Coordinate> pastShots){
        try{
            FileOutputStream saveFile = new FileOutputStream("SaveFile.sav");

            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            save.writeObject(enemyMisses);
            pastShots = ai.getPastShots();
            save.writeObject(pastShots);
            //save our ship placements
            //save our pasthits
            //save time
            //save player pastShots 

            save.close();
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }

    public int Load(){
        int enemyMisses=0;
        ArrayList<Coordinate> pastShots = new ArrayList<Coordinate>();

        try{
            FileInputStream saveFile = new FileInputStream("SaveFile.sav");
            
            ObjectInputStream save = new ObjectInputStream(saveFile);
            
            enemyMisses = (Integer) save.readObject();
            pastShots = (ArrayList) save.readObject();
            //load our past hits
            //load other stats
            //load timer
            //load player pastShots
            //load our ship placements

            //for loop to load the point status to draw the board
            //if point = pastShots[i]
                //if pastShots [i] = past hit
                    //set point to is taken, and is hit
                //else
                    //set point to is taken

                    // for(int rows = 0; rows < Constants.boardSize; rows++){
                    //     for (int j = 0; j < 10; j++) {
                    //         if (board.getPoint(rows, j)==pastShots.get(j).getRow()) {
                    //             if(board.getPoint(rows, j).getIsTaken()) {
                    //             System.out.print("X ");
                    //             }else{
                    //                 System.out.print("M ");
                    //             }
                    //         }
                    //         else {
                    //             System.out.print("o ");
                    //         }
                    //     }
                    //     System.out.println();

            save.close();

        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        return enemyMisses;

    }
}
