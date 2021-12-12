package battleship.Model;

public class Board {
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
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if (board.getPoint(i,j).getIsHit() == true){
                    System.out.print("O ");
                }
                else if(board.getPoint(i, j).getIsTaken() == true){
                    System.out.print("* ");
                }
                else if(board.getPoint(i,j).getIsMiss() == true){
                    System.out.print("x ");
                }
                else{
                    System.out.print("o ");
                }
            }
            System.out.println();
        }
    }
}
