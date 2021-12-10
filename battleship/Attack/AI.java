package battleship.Attack;
import battleship.Model.*;
import battleship.UI.Renderer;

public abstract class AI {
    protected Board enemyBoard;
    protected Renderer renderer;

    public AI(){
        enemyBoard = new Board(Constants.boardSize);
        renderer=new Renderer();
    }

    public abstract String attack();



}
