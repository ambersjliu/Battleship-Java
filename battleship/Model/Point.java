package battleship.Model;

public class Point {
    private boolean isTaken;
    private boolean isHit;

    boolean getIsHit(){
        return this.isHit;
    }

    boolean getIsTaken(){
        return this.isTaken;
    }

    void setIsHit(boolean state){
        this.isHit = state;
    }

    

}
