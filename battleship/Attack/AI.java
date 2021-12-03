package battleship.Attack;
import battleship.Model.Board;
import battleship.UI.Renderer;

public abstract class AI {
    protected Board enemyBoard;
    protected Renderer renderer;

    public AI(){
        enemyBoard=new Board(10);
        renderer=new Renderer();
    }

    public abstract String attack();



}
