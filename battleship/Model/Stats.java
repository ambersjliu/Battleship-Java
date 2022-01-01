package battleship.Model;


public class Stats {
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
