package battleship.Control;

import battleship.Model.*;
import battleship.Model.Point;
import battleship.Attack.*;
import battleship.UI.*;

import java.util.*;
// import java.util.Timer;
import javax.swing.*;
import javax.swing.Timer;

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
	private String username;
 	private Watch watch;
 	private Timer timer;
	private Intro intro;
	private boolean currentGameOver = false;

	void initialize() { //called at the start of every game

		ourBoard = new Board(Constants.boardSize);
		enemyBoard = new Board(Constants.boardSize);
		ourStats = new Stats(0, 0, 0);
		enemyStats = new Stats(0, 0, 0);
		stage = 0;

		ai = new AI();

		ai.placeShips(ourBoard);

		gameWindow = new GameWindow(this, ourBoard, enemyBoard);

		//create the window
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

	void attack() {
		Coordinate ourAttack = ai.getNextMove(sup.israndomAIPicked());
		String ourAttackString = ourAttack.coordFormat(ourAttack);
		String attackResult = gameWindow.getAttackResult(ourAttackString); // GUI shows a popup prompting answer from
																			// user

		System.out.println("Our AI attacks " + ourAttackString); // print to console

		Point attackPoint = enemyBoard.getPoint(ourAttack.getRow(), ourAttack.getColumn());
		attackPoint.setIsHit(true); // either way the attacked point is hit

		System.out.println(attackResult + " " + ourAttack.getRow() + " " + ourAttack.getColumn());
		if (attackResult.equals("Hit!")) {
			attackPoint.setIsTaken(true);
			ai.setTargetMode(true);
			ai.setEndOfCurrentDirection(false);

			if (ai.getHits().size() == 0) {
				ai.setFirstHit(ourAttack);
				ai.getHits().add(ourAttack);
			} else {
				ai.setDirectionSet(true);
				ai.getHits().add(ourAttack);
			}
			String hitShip = gameWindow.getShipHit(); // get id of ship
			attackPoint.setShipId(hitShip); 
			enemyStats.incrementTotalHit();
		} else if (attackResult.equals("Sank!")) {
			attackPoint.setIsSunk(true);
			attackPoint.setIsTaken(true);
			String hitShip = gameWindow.getShipHit();
			attackPoint.setShipId(hitShip);
			enemyStats.incrementTotalHit();
			enemyStats.incrementTotalSunk();
			ai.resetVals();
		} else { // Missed
			enemyStats.incrementTotalMiss();
			if (ai.isTargetMode()) {
				ai.setEndOfCurrentDirection(true);
			}
		}
		gameWindow.refreshEnemyStats(enemyStats);
		gameWindow.refreshEnemyBoard(enemyBoard);

		if (enemyStats.getTotalHit() == Constants.hitsToWin) {
			gameWindow.popupDialog("We won!", "Good game! Press OK to restart."); // SHEEESH WE WIN
		}

		stage = 2;

	}

	void recordAttack() {
		String enemyAttack = gameWindow.getEnemyAttackCoord().toUpperCase();
		System.out.println(enemyAttack);
		// convert the entered string into a Coord
		int col = Integer.parseInt(enemyAttack.substring(1)) - 1;
		int row = ((int) enemyAttack.charAt(0)) - 65;
		Coordinate enemyAtkCoord = new Coordinate(row, col);
		System.out.println(enemyAtkCoord.toString());
		Point attackedPoint = ourBoard.getPoint(enemyAtkCoord.getRow(), enemyAtkCoord.getColumn());
		attackedPoint.setIsHit(true);
		AttackResults enemyAttackResult = ai.getEnemyAttackResult(ourBoard, enemyAtkCoord);
		if (enemyAttackResult.getResult() == "Hit") {
			ourStats.incrementTotalHit();
			gameWindow.popupDialog("Ouch...", "You hit our " + enemyAttackResult.getShipName() + "!");
		} else if (enemyAttackResult.getResult() == "Sink") {
			ourStats.incrementTotalHit();
			ourStats.incrementTotalSunk();
			gameWindow.popupDialog("Oh no!", "You sank our" + enemyAttackResult.getShipName() + "!");
		} else {
			ourStats.incrementTotalMiss();
			gameWindow.popupDialog("Phew!", "Missed!");
		}

		gameWindow.refreshOurBoard(ourBoard);
		gameWindow.refreshOurStats(ourStats);

		if (ourStats.getTotalHit() == Constants.hitsToWin) {
			gameWindow.popupDialog("Oh dear...", "Looks like we've lost! Press OK to restart.");
			// gamewindow destroy?
			currentGameOver = true;
		}

		stage = 0;

	}

	void updateState() {
		switch (stage) {
			case 0:
				attack(); // call attack method
				break;

			case 2:
				recordAttack(); // call enemy attack method
				break;
			default:
				break;
		}
	}

	public void startGame() {

		while (true) { // loop that brings game back to initial state after game over
			initialize(); // reset vals to 0
			username = gameWindow.getUsername();
			sup = gameWindow.getStartParams(); // get start up params (who goes first, etc) from gui
			System.out.println("Start up params" + sup); // test
			
			watch = new Watch();
			timer = watch.getTimer();
			watch.start();
			
			if (sup.doWeGoFirst())
				stage = 0;
			else
				stage = 2;
			currentGameOver = false;
			while (!currentGameOver) { // while no one has won
				updateState(); // update state will switch between two stages
			}
		}
	}

	

}
