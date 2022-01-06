/*
PROGRAM NAME - Battle-Ship/Constants

PROGRAMMERS - Amber Liu, Elaine Yang

USAGE - Keeping things final varibles consistent across the whole program

DATE - Started 12/08/2021
	   Completed 01/06/2022	

BUGS - 

DESCRIPTION - 

*/

package battleship.Model;
import java.awt.Color;

public class Constants {
    public static int boardSize = 10;

    public static String[] orientation = {"DOWN", "RIGHT"};

    public static String[] theShips= {"Carrier","BattleShip","Cruiser","Submarine","Destroyer"};

    public static int hitsToWin = 17;

    public static Color bgColor = new Color(34, 45, 48);

    public static Color fgColor = new Color(0, 219, 255);



}
