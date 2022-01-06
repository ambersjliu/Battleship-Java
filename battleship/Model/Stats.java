
/*
PROGRAM NAME - 

PROGRAMMERS - 

USAGE - Has useful methods for keeping track of both User and AI hits/misses/and sunks,
		tying hits/misses/sunks together as a Stats object

DATE - Started 12/08/2021
	   Completed 01/06/2022	

BUGS - 

DESCRIPTION - 

*/
package battleship.Model;

import java.io.Serializable;

public class Stats implements Serializable{
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
