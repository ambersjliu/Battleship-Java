/*
PROGRAM NAME - Battle-Ship/Game

PROGRAMMERS - Amber Liu, Elaine Yang

USAGE - Starts the game

DATE - Started 12/08/2021
	   Completed 01/06/2022	

BUGS - 

DESCRIPTION - A simple version of Battleship made with Java Swing, made to compete 
			  in the 2021-2022 ICS4U class Battleship tournament. It will operate 
			  on a “player vs. AI” system, with the player marking their ship 
			  locations elsewhere, and not on a “player vs. player” one.

*/

package battleship;
import battleship.Control.*;
import battleship.UI.Intro;

public class Game {
    
	public static void main(String[] args) {
		Intro intro = new Intro();
		intro.drawIntro();

	}

	
}
