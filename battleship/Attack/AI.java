package battleship.Attack;
import battleship.Model.*;
import battleship.UI.Renderer;

public class AI {
    protected Board enemyBoard;
    protected Renderer renderer;

    public AI(){
        enemyBoard = new Board(Constants.boardSize);
        renderer=new Renderer();
    }



/*     public boolean allSunk(Ship ship){
        for (int i = 0; i<ship.getShipLength();i++){
            if (!ship.getShipPoint(i).getIsHit()){
                return false;
            }
        }
        return true;
    }
 */
   /*  public boolean isValidShip(Ship ship){
        
       
    } */



}
