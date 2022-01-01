package battleship.Attack;
import battleship.Model.*;
import java.util.Random;



public class DumbAI extends AI{
    
    Random rand = new Random();

    public Coordinate attack() {
        Coordinate coord = new Coordinate(1, 2);
        while((coord.getRow()+coord.getColumn())%2==1 && !pastShots.contains(coord)){
            coord.setRow(rand.nextInt(Constants.boardSize));
            coord.setColumn(rand.nextInt(Constants.boardSize));
        }
        pastShots.add(coord);
        return coord;
    }

}
