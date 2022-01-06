/*
PROGRAM NAME - Battle-Ship/StartUpParams

PROGRAMMERS - 

USAGE - initialize who attacks first and which AI is picked, 
		tying them togehter as needed information before game starts

DATE - Started 12/08/2021
	   Completed 01/06/2022	

BUGS - 

DESCRIPTION - 

*/

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
