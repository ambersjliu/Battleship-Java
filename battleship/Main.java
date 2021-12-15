package battleship;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import battleship.Attack.AI;
import battleship.Attack.SmartAI;
import battleship.Model.Board;
import battleship.Model.Constants;
import battleship.Model.Coordinate;
import battleship.Model.Ship;

public class Main {
	AI ai;

	@Override
	public boolean equals(Object obj) {
		return (this == obj);
	}

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		SmartAI ai = new SmartAI();

		Board testBoard = new Board(Constants.boardSize);

		Ship carrier = new Ship("Carrier", 5);
		Ship destroyer = new Ship("Destroyer", 2);

		Ship[] shipArr = new Ship[] { carrier, destroyer };

		HashMap<String, Ship> ships = ai.placeShips(shipArr, testBoard);

		while (ships.get("Carrier").getShipSurvivingPoints() > 0) {

			testBoard.drawBoard(testBoard);

			System.out.println("Enter a coordinate:");

			// convert inputted coord into an array of 2 ints

			String coord = input.nextLine().toUpperCase();

			int col = Integer.parseInt(coord.substring(1)) - 1;
			int row = ((int) coord.charAt(0)) - 65;

			Coordinate numcoord = new Coordinate(row, col);

			System.out.println(ships.get("Carrier").getShipPoints().size());
			for (Coordinate p : ships.get("Carrier").getShipPoints()) {
				System.out.println(p);
			}

			System.out.println(numcoord);

			if (ships.get("Carrier").getShipPoints().contains(numcoord)) { // need to override equals for this to work,
																			// working on that...
				carrier.setShipLength(carrier.getShipLength() - 1); // temp, will create a checkShipHit method +
																	// shipSunk
				System.out.println("Hit!");
			} else {

				System.out.println("Miss!");
			}
			testBoard.getPoint(numcoord.getRow(), numcoord.getColumn()).setIsHit(true);

		}
		System.out.println("Carrier is down.");
		// before game setup (menu)

		// prompts user to load save or start a new game

		System.out.println("complex (1) or simple (2) ai?");
		int gameMode = input.nextInt();
		// set the ai

		System.out.println("Who goes first? you (1) or ai (2)?");
		int firstMove = input.nextInt();

		// turn gameplay loop
		int enemyHits = 0;
		int enemyMisses = 0;
		int ourHits = 0;
		int ourMisses = 0;

		Board ourBoard = new Board(Constants.boardSize);
		// place 5 ships on ourboard
		Board enemyBoard = new Board(Constants.boardSize);

		// while enemyhits or ourhits are less than 17
		while (enemyHits < 17 && ourHits < 17) {

			ourBoard.drawBoard(ourBoard);

			// player turn
			if (firstMove == 1) {
				System.out.println("Enter a coordinate (letter-number format):");
				String coord = input.nextLine().toUpperCase();

				int col = Integer.parseInt(coord.substring(1)) - 1;
				int row = ((int) coord.charAt(0)) - 65;

				int[] numcoord = { row, col };

				System.out.println("You attacked" + Arrays.toString(numcoord));

				// if player hit
				// if numcoord coord on the ourboard is not deflaut
				if (!ourBoard.getPoint(numcoord[0], numcoord[1]).getShipId().equals("default")) {
					// call checkIfHit
					// check which ship is hit and which ship is
					// tell user, which ship is hit
					System.out.println("YOU HIT MY" + ourBoard.getPoint(numcoord[0], numcoord[1]).getShipId());
					enemyHits++;

					// if sunk
					// call check if sunk
					// if shipIsSunk is true{
					// System.out.println("YOU"VE SUNK MY"+shipId);
					// }

					// if player miss
				} else {
					System.out.println("Missed ;)");
					enemyMisses++;
				}
			}
			// prompt user to save, or save and exit

			// ai turn
			else {
				// int[] numcoord = call attack method and return an attack coordinate
				// System.out.println("AI's attack"+Arrays.toString(numcoord));

				System.out.println("hit(1) or miss(2)?");
				int userinput = input.nextInt();

				// if ai hit
				if (userinput == 1) {

					System.out.println("Which ship?");
					String[] theShips = { "Carrier", "BattleShip", "Cruiser", "Submarine", "Destroyer" };

					// prints shipIDs alongside numbers
					for (int i = 0; i < theShips.length; i++) {
						System.out.println((i + 1) + " " + theShips[i]);
					}
					userinput = input.nextInt();
					// updating the enemyboard
					// enemyBoard.getPoint(numcoord[0],numcoord[1]).setShipId(theShips[userinput-1]);
					// enemyBoard.getPoint(numcoord[0],numcoord[1]).setIsHit(true));
					// add point to shipPoints

					ourHits++;

					// ask if ship that was hit, has suck
					System.out.println("Not sunk(1) or has sunk(2)?");
					userinput = input.nextInt();

					// if sunk
					if (userinput == 2) {
						// set all points of said enemyShip to sunk
					}

					// if ai miss
				} else {
					// enemyBoard.getPoint(numcoord[0],numcoord[1]).setIsTaken(true));
					ourMisses++;

				}

			}
		} // end of while gameplay loop

	}

}
