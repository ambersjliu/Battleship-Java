

package battleship.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * File: Board.java
 * <p>Mr. Anadarajan
 * <p>ICS4U1
 * <p>06 January, 2021
 * 
 * <p>Final Evaluation: Battleship Tournament
 * <p>Description: Defines the Board objects used in the game and their methods.
 * <p>Part of the Model package, describing the data and objects used in the game.
 * @author Amber Liu
 * @author Elaine Yang
 */


public class Board implements Serializable{ //so board board can be saved and loaded
    Point[][] points;


    public Board(int boardSize){
        points = new Point[boardSize][boardSize];

        //initialize board with hit and taken all as false
        for(int i = 0; i<boardSize; i++){
            for(int j = 0; j < 10; j++){
                //points[i][j] = new Point(i, j);
                points[i][j] = new Point(i,j);
            }
        }
    }

    public Point[][] getPoints() {
		return points;
	}

	public void setPoints(Point[][] points) {
		this.points = points;
	}

	public Point getPoint(int row, int column) {
		return points[row][column];
	}


    public void drawBoard(Board board){ //used only for console
        System.out.print(" ");
        for(int col=1; col<=Constants.boardSize; col++){
            System.out.print(" " +col);
        }

        System.out.println();
        char letter = 'A';

        for(int rows = 0; rows < Constants.boardSize; rows++, letter++){
            System.out.print(letter + " ");
            for (int j = 0; j < 10; j++) {
                if (board.getPoint(rows, j).getIsHit()) {
                    if(board.getPoint(rows, j).getIsTaken()) {
                    System.out.print("X ");
                    }else{
                        System.out.print("M ");
                    }
                }
                else {
                    System.out.print("o ");
                }
            }
            System.out.println();
        }
    }

    /**
     * 
     * @param Board Takes in enemyBoard/ourBoard to load changes to 
     * @param pastShots Takes in the pastShots of aiPastShots/userPastShots 
     * to set point statuses to be isHit
     * @param pastHits Takes in the pastHits of aiPastHits/userPastHits 
     * to set point statuses to be isTaken
     * @return n/a
     */

    public void loadBoard(Board Board, ArrayList<Coordinate> pastShots,
    ArrayList<Coordinate> pastHits){

        for (int rows = 0; rows < Constants.boardSize; rows++) { //loops through all points of the board
            for (int j = 0; j < 10; j++) {

                Coordinate coord = new Coordinate(rows, j);

                if (pastShots.contains(coord)) { 
                    Board.getPoint(rows, j).setIsHit(true);

                    if (pastHits.contains(coord)) {
                        Board.getPoint(rows, j).setIsTaken(true);
                    }
                }

            }
        }

        


    }
}
