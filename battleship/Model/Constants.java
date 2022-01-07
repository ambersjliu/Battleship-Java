/**
 * File: Board.java
 * <p>Mr. Anadarajan
 * <p>ICS4U1
 * <p>06 January, 2021
 * 
 * <p>Final Evaluation: Battleship Tournament
 * <p>Description: Defines the Constants used throughout the project.
 * <p>Part of the Model package, describing the data and objects used in the game.
 * @author Amber Liu
 * @author Elaine Yang
 */

package battleship.Model;
import java.awt.Color;

public class Constants {
    //how large the board should be
    public static int boardSize = 10;

    //possible ship orientations
    public static String[] orientation = {"DOWN", "RIGHT"};

    //names of the ships
    public static String[] theShips= {"Carrier","BattleShip","Cruiser","Submarine","Destroyer"};

    //how many hits are needed to win
    public static int hitsToWin = 17;

    //the background colour of the GUI 
    public static Color bgColor = new Color(34, 45, 48);

    //the text/foreground colour of the GUI
    public static Color fgColor = new Color(0, 219, 255);



}
