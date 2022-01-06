/*
PROGRAM NAME - 

PROGRAMMERS - 

USAGE - Initailzing enemyBoards and ourBoards

DATE - Started 12/08/2021
	   Completed 01/06/2022	

BUGS - 

DESCRIPTION - Input: Board size, Points
              Output: Updated board with point statuses for console 

*/

package battleship.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable{
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

	public void setPoint(Point point, int row, int column) {
		points[row][column] = point;
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

    public void loadBoard(Board Board, ArrayList<Coordinate> pastShots,
    ArrayList<Coordinate> pastHits){

        for (int rows = 0; rows < Constants.boardSize; rows++) {
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
