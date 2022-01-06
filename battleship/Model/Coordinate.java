/*
PROGRAM NAME - Battle-Ship/Coordinate

PROGRAMMERS - 

USAGE - 

DATE - Started 12/08/2021
	   Completed 01/06/2022	

BUGS - 

DESCRIPTION - 

*/

package battleship.Model;

import java.io.Serializable;
import java.util.Objects;

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
