package battleship;

import java.util.HashMap;
import java.util.Scanner;

import battleship.Attack.*;
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



        
		// before game setup (menu)

		// prompts user to load save or start a new game

		

		Scanner input = new Scanner(System.in);


		System.out.println("complex (1) or simple (2) ai?");
		int gameMode = input.nextInt();
		DumbAI ai = new DumbAI();

		// set the ai

		System.out.println("Who goes first? you (1) or ai (2)?");
		int firstMove = input.nextInt();

		// turn gameplay loop
		int enemyHits = 0;
		int enemyMisses = 0;
		int ourHits = 0;
		int ourMisses = 0;

		Board ourBoard = new Board(Constants.boardSize);
		Board enemyBoard = new Board(Constants.boardSize);

		// place 5 ships on ourboard

		Ship carrier = new Ship("Carrier", 5);
		Ship battleship = new Ship("Battleship", 4);
		Ship cruiser = new Ship("Cruiser", 3);
		Ship submarine = new Ship("Submarine", 3);
		Ship destroyer = new Ship("Destroyer", 2);

		
		boolean first = true;

		Ship[] shipArr = new Ship[] { carrier, battleship, cruiser, submarine, destroyer};
		HashMap<String, Ship> ships = ai.placeShips(shipArr, ourBoard);


		// while enemyhits or ourhits are less than 17
    
		while (enemyHits < 17 && ourHits < 17) {


			// player turn
			if (firstMove == 1 || first==false) {
				first = false;

				System.out.println("Our board:");
				ourBoard.drawBoard(ourBoard);

				System.out.println("Your board: ");
				enemyBoard.drawBoard(enemyBoard);

				System.out.println("Your turn!");
				System.out.println("Enter a coordinate (letter-number format):");
				String enter = input.nextLine();
				String coord = input.nextLine().toUpperCase();

				int col = Integer.parseInt(coord.substring(1)) - 1;
				int row = ((int) coord.charAt(0)) - 65;

				Coordinate numcoord = new Coordinate(row,col);

				//System.out.println("You attacked" + numcoord);


				// if player hit
				// if numcoord coord on the ourboard is not default
				String hitShipId = ourBoard.getPoint(numcoord.getRow(), numcoord.getColumn()).getShipId();


				if (!hitShipId.equals("default")) {					// tell user, which ship is hit
					System.out.println("You hit our " + hitShipId + "!");// check which ship is hit and which ship is
					for (Coordinate p : ships.get(hitShipId).getShipPoints()) {
					 	if(p.equals(numcoord)){
							 ourBoard.getPoint(p.getRow(), p.getColumn()).setIsHit(true);
						 }
					}
					enemyHits++;

					// if sunk
					if (ships.get(hitShipId).getShipSurvivingPoints() < 0){
						 System.out.println("You sunk our " + hitShipId); 						 //u sunk my ship id

					}
		
				} else {
					System.out.println("Missed ;)");
					enemyMisses++;
				}
				ourBoard.getPoint(numcoord.getRow(), numcoord.getColumn()).setIsHit(true);

			}
			// prompt user to save, or save and exit

			// ai turn
			if(firstMove == 2 || first == false){

				System.out.println("Our board:");
				ourBoard.drawBoard(ourBoard);

				System.out.println("Your board: ");
				enemyBoard.drawBoard(enemyBoard);

				first = false;
				Coordinate numcoord = new Coordinate(ai.attack().getRow(), ai.attack().getColumn());

				System.out.println("Ai's turn!");

				System.out.println("Ai attacks "+ numcoord.coordFormat(numcoord));
				

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
					enemyBoard.getPoint(numcoord.getRow(), numcoord.getColumn()).setIsTaken(true);

					String enemyShipId = enemyBoard.getPoint(numcoord.getRow(), numcoord.getColumn()).getShipId();
					enemyBoard.getPoint(numcoord.getRow(), numcoord.getColumn()).setShipId(theShips[userinput-1]);
					// add point to shipPoints
					

					ourHits++;

					System.out.println("Not sunk(1) or has sunk(2)?");
					userinput = input.nextInt();

					// if sunk
					if (userinput == 2) {
						// set all points of said enemyShip to sunk
						for(int i = 0; i<Constants.boardSize; i++){
							for(int j = 0; j<Constants.boardSize; j++){
								if(enemyBoard.getPoint(i,j).equals(enemyShipId)){
									enemyBoard.getPoint(i, j).setIsSunk(true);
								}
							}
						}
					} 

					// if ai miss
				} else {
					// enemyBoard.getPoint(numcoord[0],numcoord[1]).setIsTaken(true));
					ourMisses++;

				}
				enemyBoard.getPoint(numcoord.getRow(), numcoord.getColumn()).setIsHit(true);



			}
		} // end of while gameplay loop

		System.out.println("\nYour total hits: " + enemyHits + "\nYour total misses: " + enemyMisses);
		System.out.println("\nOur total hits: " + ourHits + "\nOur total misses: " + ourMisses);

		
		if(enemyHits == 17){
			System.out.println("Congrats on winning!");
		} 
		if(ourHits == 17){
			System.out.println("\nOur AI won! Better luck next time!");
		}
		input.close();


	}

}
