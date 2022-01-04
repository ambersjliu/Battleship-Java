package battleship.Model;

import java.io.Serializable;

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

    public void drawBoard(Board board){
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
}
