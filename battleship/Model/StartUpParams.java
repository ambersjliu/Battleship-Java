package battleship.Model;

public class StartUpParams {
	private boolean weGoFirst;
	private boolean randomAIPicked;

	public StartUpParams(boolean weGoFirst, boolean randomAIPicked) {
		super();
		this.weGoFirst = weGoFirst;
		this.randomAIPicked = randomAIPicked;
	}

	public boolean doWeGoFirst() {
		return weGoFirst;
	}

	public boolean israndomAIPicked() {
		return randomAIPicked;
	}

	@Override
	public String toString() {
		return "StartUpParams [weGoFirst=" + weGoFirst + ", randomAIPicked=" + randomAIPicked + "]";
	}    
}
