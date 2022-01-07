
package battleship.Model;

import java.io.Serializable;
import java.util.Objects;

/**
 * File: Coordinate.java
 * <p>Mr. Anadarajan
 * <p>ICS4U1
 * <p>06 January, 2021
 * 
 * <p>Final Evaluation: Battleship Tournament
 * <p>Description: Used to refer to x,y coordinates without being bound to a specific Point or Board.
 * Was also preferable to constantly having to create int arrays of size 2.
 * <p>Part of the Model package, describing the data and objects used in the game.
 * @author Amber Liu
 */

public class Coordinate implements Serializable{
private int row; 
private int column;



    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }


    public int getRow() {
        return this.row;
    }


    public void setRow(int row) {
        this.row = row;
    }


    public int getColumn() {
        return this.column;
    }

    public void setColumn(int column) {
        this.column = column;
    }


    @Override
    public String toString() {
        return "{" +
            " row='" + getRow() + "'" +
            ", column='" + getColumn() + "'" +
            "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate coordinate = (Coordinate) o;
        return row == coordinate.row && column == coordinate.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
    
    //converts a coord to letter-number format.
    public String coordFormat(Coordinate c){
        char row = (char)(c.getRow()+65);
        String col = c.getColumn()+1+"";
        return row+col;
    }

    public Point coordPoint(Coordinate c, Board b){
        return(b.getPoint(c.getRow(), c.getColumn()));
    }
    
}
