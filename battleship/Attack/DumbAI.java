package battleship.Attack;
import battleship.Model.*;
import java.util.Random;



public class DumbAI extends AI{
    //pain, wanted to test github
    public int [] attack() {
        Random rand = new Random();

<<<<<<< HEAD

=======
        int x = rand.nextInt(10);
        int y = rand.nextInt(10);
        int attCoor [] = {x,y};

        //should setting the point to miss/hit be in here as well or in a controller


        return attCoor;
    }
>>>>>>> aa9498522b87f64cb5e99eacab7a35351266ddf8

}
