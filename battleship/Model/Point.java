package battleship.Model;

public class Point {
    private boolean isTaken;
    private boolean isHit;

    public Point(boolean taken, boolean hit){
        this.isTaken = taken;
        this.isHit = hit;
    }


    public boolean getIsHit(){
        return this.isHit;
    }

    public boolean getIsTaken(){
        return this.isTaken;
    }

    public void setIsHit(boolean state){
        this.isHit = state;
    }

    public void setIsTaken(boolean taken){
        this.isTaken = taken;
    }

    

}
