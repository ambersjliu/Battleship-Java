package battleship.Model;

public class Board {
    Point[][] points;
    private int boardSize;

    public Board(int size){
        boardSize = size;
        points = new Point[boardSize][boardSize];
        
    }
}
