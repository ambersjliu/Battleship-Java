package battleship.Control;
import battleship.Model.*;
import battleship.Model.Point;
import battleship.Attack.*;
import battleship.UI.*;

import java.util.*;
import java.awt.*;

public class GameController {

    private GameWindow gameWindow;
	private Board ourBoard;
	private Board enemyBoard;
	private AI ai;
	private int stage = 0;
	private Coordinate ourAttackCoord, enemyAttackCoord;
	private Stats ourStats, enemyStats;
	private StartUpParams sup;

    private boolean currentGameOver = false;



    void initialize() {
		ourBoard = new Board(Constants.boardSize);
		enemyBoard = new Board(Constants.boardSize);
		ourStats = new Stats(0, 0, 0);
		enemyStats = new Stats(0, 0, 0);
		stage = 0;

		ai = new AI();

		Ship carrier = new Ship("Carrier", 5);
		Ship battleship = new Ship("Battleship", 4);
		Ship cruiser = new Ship("Cruiser", 3);
		Ship submarine = new Ship("Submarine", 3);
		Ship destroyer = new Ship("Destroyer", 2);
	
		Ship[] shipArr = new Ship[] { carrier, battleship, cruiser, submarine, destroyer}; 

		HashMap<String, Ship> ships = ai.placeShips(shipArr, ourBoard);		
		gameWindow = new GameWindow(this, ourBoard, enemyBoard);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gameWindow.getFrmBattleship().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	void attack(){
		Coordinate ourAttack = ai.getNextMove(sup.israndomAIPicked());
		String ourAttackString = ourAttack.coordFormat(ourAttack);
		String attackResult = gameWindow.getAttackResult(ourAttackString);

		System.out.println("Our AI attacks " + ourAttackString);

		Point attackPoint = enemyBoard.getPoint(ourAttack.getRow(), ourAttack.getColumn());
		attackPoint.setIsHit(true);

		System.out.println(attackResult + " " + ourAttack.getRow() + " " + ourAttack.getColumn());
		if (attackResult.equals("Hit!")) {
			attackPoint.setIsTaken(true);
			String hitShip = gameWindow.getShipHit(); //get id of ship
			attackPoint.setShipId(hitShip); //in BoardPanel i will add a condition where the button "mark" changes to be the ship initial :)
			enemyStats.incrementTotalHit();
		}else if(attackResult.equals("Sank!")){
			attackPoint.setIsSunk(true);
			attackPoint.setIsTaken(true);
			String hitShip = gameWindow.getShipHit();
			attackPoint.setShipId(hitShip);
			enemyStats.incrementTotalHit();
			enemyStats.incrementTotalSunk();
		}else{
			enemyStats.incrementTotalMiss();
		}
		if(enemyStats.getTotalHit()==Constants.hitsToWin){
			gameWindow.popupDialog("We won!", "Good game! Press OK to restart.");
		}

		stage = 2;
		

	}

    void updateState() {
		switch (stage) {
		case 0:
			attack(); //call attack method
			break;

		case 2:
			//recordAttack(); //call enemy attack method
			break;
		default:
			break;
		}
	}

    public void startGame() {

		while (true) { //loop that brings game back to initial state after game over
			initialize(); //reset vals to 0
			sup = gameWindow.getStartParams(); //get start up params (who goes first, etc) from gui
			System.out.println("Start up params" + sup); //test
			if (sup.doWeGoFirst())
				stage = 0;
			else
				stage = 2;
			currentGameOver = false; 
			while (!currentGameOver) { //while no one has won
				updateState(); //update state will switch between two stages
			}
		}
	}
}
