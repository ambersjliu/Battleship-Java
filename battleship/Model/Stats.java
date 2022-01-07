

package battleship.Model;

/**
 * File: Stats.java
 * <p>Mr. Anadarajan
 * <p>ICS4U1
 * <p>06 January, 2021
 * 
 * <p>Final Evaluation: Battleship Tournament
 * <p>Description: Defines the Stats object used in methods, ties together hits, misses and sunks
 * <p>Part of the Model package, describing the data and objects used in the game.
 * @author Amber Liu
 */

import java.io.Serializable;

public class Stats implements Serializable{ //so stat objects can be saved and loaded
	private int totalHit;
	private int totalMiss;
	private int totalSunk;

	public Stats(int totalHit, int totalMiss, int totalSunk) {
		this.totalHit = totalHit;
		this.totalMiss = totalMiss;
		this.totalSunk = totalSunk;
	}

	public void incrementTotalHit() {
		totalHit++;
	}

	public void incrementTotalMiss() {
		totalMiss++;
	}

	public void incrementTotalSunk() {
		totalSunk++;
	}

	public int getTotalHit() {
		return totalHit;
	}

	public int getTotalMiss() {
		return totalMiss;
	}

	public int getTotalSunk() {
		return totalSunk;
	}
}
