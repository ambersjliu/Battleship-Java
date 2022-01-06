/*
PROGRAM NAME - Battle-Ship/AttackResults

PROGRAMMERS - 

USAGE - Tying an attack to its result

DATE - Started 12/08/2021
	   Completed 01/06/2022	

BUGS - 

DESCRIPTION - 

*/

package battleship.Model;

public class AttackResults {

	private String shipName;
	private String result; //"hit" or "miss" or "sink"

	public AttackResults() {
	}

	public AttackResults(String shipName, String result) {
		this.shipName = shipName;
		this.result = result;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}

