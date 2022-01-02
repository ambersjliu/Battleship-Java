package battleship.Model;

public class AttackResults {

	private String shipName;
	private String result;

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

