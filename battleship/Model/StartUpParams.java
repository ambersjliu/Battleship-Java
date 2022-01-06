package battleship.Model;

import java.io.Serializable;

public class StartUpParams implements Serializable{
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

	public void setisrandomAIPicked(boolean randomAiPicked) {
		this.randomAIPicked = randomAiPicked;
	}


	@Override
	public String toString() {
		return "StartUpParams [weGoFirst=" + weGoFirst + ", randomAIPicked=" + randomAIPicked + "]";
	}    
}
