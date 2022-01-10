

package battleship.Model;

import java.io.Serializable;

/**
 * File: StartUpParams.java
 * <p>Mr. Anadarajan
 * <p>ICS4U1
 * <p>27 December 2021
 * 
 * <p>Final Evaluation: Battleship Tournament
 * <p>Description: Bundles together the two player-selected options at the beginning of the game: AI level, and who goes first.
 * <p>Part of the Model package, describing the data and objects used in the game.
 * @author Amber Liu
 */

public class StartUpParams implements Serializable{ //so StartUpParmas objects can be saved and loaded
	private boolean weGoFirst;
	private boolean randomAIPicked;

	public StartUpParams(boolean weGoFirst, boolean randomAIPicked) {
		super();
		this.weGoFirst = weGoFirst;
		this.randomAIPicked = randomAIPicked;
	}

	//setters and getters

	public boolean doWeGoFirst() {
		return weGoFirst;
	}

	public boolean israndomAIPicked() {
		return randomAIPicked;
	}

	public void setisrandomAIPicked(boolean randomAiPicked) {
		this.randomAIPicked = randomAiPicked;
	}
	
	@Override
	public String toString() {
		return "StartUpParams [weGoFirst=" + weGoFirst + ", randomAIPicked=" + randomAIPicked + "]";
	}    
}
