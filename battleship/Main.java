package battleship;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import battleship.Attack.*;
import battleship.Model.Board;
import battleship.Model.Constants;
import battleship.Model.Coordinate;
import battleship.Model.Ship;

public class Main  {
	AI ai;

	@Override
	public boolean equals(Object obj) {
		return (this == obj);
	}

	public static void main(String[] args) {

		AI ai = new AI();
		int gameMode = 0;
		int firstMove = 0;
		// set the ai

		// turn gameplay loop
		int enemyHits = 0;
		int enemyMisses = 0;
		int ourHits = 0;
		int ourMisses = 0;

		// shots that hit a ship
		ArrayList<Coordinate> userHits = new ArrayList<Coordinate>();
		ArrayList<Coordinate> aiHits = new ArrayList<Coordinate>();

		Board ourBoard = new Board(Constants.boardSize);
		Board enemyBoard = new Board(Constants.boardSize);

		// initialize 5 ships

		Ship carrier = new Ship("Carrier", 5);
		Ship battleship = new Ship("Battleship", 4);
		Ship cruiser = new Ship("Cruiser", 3);
		Ship submarine = new Ship("Submarine", 3);
		Ship destroyer = new Ship("Destroyer", 2);

		boolean first = true;

		// Ship[] shipArr = new Ship[] {carrier, battleship, cruiser, submarine,
		// destroyer };
		// HashMap<String, Ship> ships = ai.placeShips(shipArr, ourBoard, userLoad);

		ai.resetVals();

		// before game setup (menu)

		// prompts user to load save or start a new game

		Scanner input = new Scanner(System.in);

		System.out.println("New game (1) or load? (2)");
		int userLoad = input.nextInt();

		SaveLoad s = new SaveLoad();
		String saveName;

		if (userLoad == 1) { // new game

			System.out.println("complex (1) or simple (2) ai?");
			gameMode = input.nextInt(); // i havent figured out how to switch game modes yet...
			// ok, screw it. im putting everything inside ai!

			System.out.println("Who goes first? you (1) or ai (2)?");
			firstMove = input.nextInt();

		} else { // load save

			// boolean fileFound = false;

			while (true) {

				System.out.println("Enter name of file:");
				saveName = input.next();
				s.setSaveName(saveName);

				try {
					System.out.println(s.load());
					break;

				} catch (Exception e) {

					System.out.println(e.getClass());

				}
			}

			firstMove = 1;

			enemyHits = s.getEnemyHits();
			enemyMisses = s.getEnemyMisses();
			ourHits = s.getOurHits();
			ourMisses = s.getOurMisses();

			ai.setPastShots(s.getPastShots());
			userHits = s.getUserHits();
			aiHits = s.getAiHits();

			ai.setShipsPlaced(s.getShipsPlaced());

			// for the ai
			gameMode = s.getGameMode();

			// put Hits in ai
			// make a aiHits ArrayList, (points that were hit and has a ship)

			ai.setHits(s.getHits());
			ai.setMode(s.getMode());
			ai.setDirectionSet(s.getDirectionSet());
			ai.setDirection(s.getDirection());
			ai.setFirstHit(s.getFirstHit());

			// loading the boards, wish i didnt have to put it in main but,
			// enemyBoard and ourBoard are both here

			// enemyBoard
			for (int rows = 0; rows < Constants.boardSize; rows++) {
				for (int j = 0; j < 10; j++) {

					Coordinate coord = new Coordinate(rows, j);

					if (ai.getPastShots().contains(coord)) {
						enemyBoard.getPoint(rows, j).setIsHit(true);

						if (aiHits.contains(coord)) {
							enemyBoard.getPoint(rows, j).setIsTaken(true);
						}
					}

				}
			}

			// ourBoard
			for (int rows = 0; rows < Constants.boardSize; rows++) {
				for (int j = 0; j < 10; j++) {

					Coordinate coord = new Coordinate(rows, j);

					if (userHits.contains(coord)) {
						ourBoard.getPoint(rows, j).setIsHit(true);

						String hitShipId = ourBoard.getPoint(coord.getRow(), coord.getColumn()).getShipId();

						if (!hitShipId.equals("default")) {
							ourBoard.getPoint(rows, j).setIsTaken(true);

						}
					}

				}
			}

		}

		// place ships

		Ship[] shipArr = new Ship[] { carrier, battleship, cruiser, submarine, destroyer };
		HashMap<String, Ship> ships = ai.placeShips(shipArr, ourBoard, userLoad, ai.getShipsPlaced());

		// For testing, set ships on ourBoard as X to see ships
		// for (int rows = 0; rows < Constants.boardSize; rows++) {
		// for (int j = 0; j < 10; j++) {

		// Coordinate coord = new Coordinate(rows, j);
		// String test = ourBoard.getPoint(coord.getRow(),
		// coord.getColumn()).getShipId();

		// if (!test.equals("default")){
		// ourBoard.getPoint(rows, j).setIsHit(true);

		// }

		// }
		// }

		// while enemyhits or ourhits are less than 17

		while (enemyHits < 17 && ourHits < 17) {

			// player turn
			if (firstMove == 1 || first == false) {
				first = false;

				System.out.println("Our board:");
				ourBoard.drawBoard(ourBoard);

				System.out.println("Your board: ");
				enemyBoard.drawBoard(enemyBoard);

				System.out.println("Your turn!");
				System.out.println("Enter a coordinate (letter-number format):");

				String enter = input.nextLine(); // catch enter, had issues with input earlier
				String coord = input.nextLine().toUpperCase();

				int col = Integer.parseInt(coord.substring(1)) - 1;
				int row = ((int) coord.charAt(0)) - 65;

				Coordinate numcoord = new Coordinate(row, col);
				userHits.add(numcoord);
				// System.out.println("You attacked" + numcoord);

				// if player hit
				// if numcoord coord on the ourboard is not default
				String hitShipId = ourBoard.getPoint(numcoord.getRow(), numcoord.getColumn()).getShipId();

				if (!hitShipId.equals("default")) { // tell user, which ship is hit
					System.out.println("You hit our " + hitShipId + "!");// check which ship is hit and which ship is
					for (Coordinate p : ships.get(hitShipId).getShipPoints()) {
						if (p.equals(numcoord)) {
							ourBoard.getPoint(p.getRow(), p.getColumn()).setIsHit(true);
						}
					}
					System.out.println(
							hitShipId + " has " + ships.get(hitShipId).getShipSurvivingPoints() + " points left.");
					enemyHits++;

					// if sunk
					if (ships.get(hitShipId).getShipSurvivingPoints() < 0) {
						System.out.println("You sunk our " + hitShipId); // u sunk my ship id

					}

				}

				else {
					System.out.println("Missed ;)");
					enemyMisses++;
				}
				ourBoard.getPoint(numcoord.getRow(), numcoord.getColumn()).setIsHit(true);

			}

			// ai turn
			if (firstMove == 2 || first == false) {

				System.out.println("Our board:");
				ourBoard.drawBoard(ourBoard);

				System.out.println("Your board: ");
				enemyBoard.drawBoard(enemyBoard);

				first = false;
				Coordinate numcoord = ai.getNextMove(gameMode);

				System.out.println("Ai's turn!");

				System.out.println("Ai attacks " + numcoord.coordFormat(numcoord));

				System.out.println("hit(1) or miss(2)?");
				int userinput = input.nextInt();

				// if ai hit
				if (userinput == 1) {
					if (ai.getFirstHit().getRow() == -1) {
						ai.setFirstHit(numcoord);
						ai.getHits().add(numcoord);

					} else {
						ai.getHits().add(numcoord);
					}

					System.out.println("Current first hit: " + ai.getFirstHit().coordFormat(ai.getFirstHit()));
					System.out.println("Current last hit " + ai.getHits().get(ai.getHits().size() - 1));

					System.out.println("Which ship?");
					String[] theShips = { "Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer" };

					// prints shipIDs alongside numbers
					for (int i = 0; i < theShips.length; i++) {
						System.out.println((i + 1) + " " + theShips[i]);
					}

					userinput = input.nextInt();
					// updating the enemyboard
					enemyBoard.getPoint(numcoord.getRow(), numcoord.getColumn()).setIsTaken(true);

					// String enemyShipId = enemyBoard.getPoint(numcoord.getRow(),
					// numcoord.getColumn()).getShipId();
					enemyBoard.getPoint(numcoord.getRow(), numcoord.getColumn()).setShipId(theShips[userinput - 1]);
					// add point to shipPoints
					ai.setMode(true); // enter target mode
					// if this this also hit

					if (ai.getHits().size() >= 2) {
						ai.setDirectionSet(true);
					}

					ourHits++;
					aiHits.add(numcoord);

					System.out.println("Not sunk(1) or has sunk(2)?");
					userinput = input.nextInt();

					// if sunk
					if (userinput == 2) {
						/*
						 * // set all points of said enemyShip to sunk
						 * for(int i = 0; i<Constants.boardSize; i++){
						 * for(int j = 0; j<Constants.boardSize; j++){
						 * if(enemyBoard.getPoint(i,j).getShipId().equals(enemyShipId)){
						 * enemyBoard.getPoint(i, j).setIsSunk(true);
						 * }
						 * }
						 * }
						 */
						ai.resetVals();
						System.out.println("Current direction is " + ai.getDirection());
						ai.setMode(false);
					}

					// if ai miss
				} else {
					// enemyBoard.getPoint(numcoord[0],numcoord[1]).setIsTaken(true));
					ourMisses++;

					if (ai.getMode()) { // ONLY if in hunt mode!
						if (ai.getDirectionSet()) {
							if (ai.getDirection() > 1) {
								ai.getHits().add(ai.getFirstHit());
								ai.setDirection(ai.getDirection() - 2);
							} else {
								ai.setDirection(ai.getDirection() + 2);
							}
						} else {
							ai.setDirection(ai.getDirection() + 1);
						}
					}

				}
				// System.out.println(enemyBoard.getPoint(numcoord.getRow(),
				// numcoord.getColumn()).getIsTaken());

				enemyBoard.getPoint(numcoord.getRow(), numcoord.getColumn()).setIsHit(true);

			}

			// prompt user to save, or save and exit
			System.out.println("Enter (1) to continue, (2) to save");
			userLoad = input.nextInt();

			if (userLoad == 2) {

				System.out.println("Enter name of save file:");
				saveName = input.next();

				s.save(saveName,
						enemyMisses, enemyHits, ourHits, ourMisses, // save stats
						ai.getPastShots(), userHits, aiHits, // save hits
						ai.getShipsPlaced(), // save ships
						gameMode, ai.getHits(), ai.getMode(), ai.getDirectionSet(), // save start ai stuff
						ai.getDirection(), ai.getFirstHit());

				// for (Coordinate pastShot : userHits) {
				// System.out.println(pastShot);
				// }
				break;

			}

		} // end of while gameplay loop

		System.out.println("\nYour total hits: " + enemyHits + "\nYour total misses: " + enemyMisses);
		System.out.println("\nOur total hits: " + ourHits + "\nOur total misses: " + ourMisses);

		if (enemyHits == 17) {
			System.out.println("Congrats on winning! Play again soon!");
		}
		if (ourHits == 17) {
			System.out.println("\nOur AI won! Better luck next time!");
		}
		input.close();

	}

}
