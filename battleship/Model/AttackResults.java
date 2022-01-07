

package battleship.Model;

/**
 * File: AttackResults.java
 * <p>Mr. Anadarajan
 * <p>ICS4U1
 * <p>06 January, 2021
 * 
 * <p>Final Evaluation: Battleship Tournament
 * <p>Description: Bundles together the result of an attack and which ship was hit, if any. Used in GameController.
 * <p>Part of the Model package, describing the data and objects used in the game.
 * @author Amber Liu
 */


public class AttackResults {

	private String shipName;
	private String result; //"hit" or "miss" or "sink"

	public AttackResults() {
	}

	//constructor, takes in ship name and miss/hit/sink
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

