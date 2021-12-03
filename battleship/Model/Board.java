package battleship.Model;

public class Board {
    Point[][] points;
    private int boardSize;

    public Board(int size){
        boardSize = size;
        points = new Point[boardSize][boardSize];

        //initialize board with hit and taken all as false
        for(int i = 0; i<boardSize; i++){
            for(int j = 0; j < 10; j++){
                points[i][j] = new Point(false, false);
            }
        }
    }

    //getter method for individiual coordinates
    public Point getPoint(int pointX, int pointY){
        return points[pointX][pointY];
    }
}
