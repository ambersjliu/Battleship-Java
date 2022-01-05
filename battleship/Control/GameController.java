package battleship.Control;

import battleship.Model.*;
import battleship.Model.Point;
import battleship.SaveLoad;
import battleship.Attack.*;
import battleship.UI.*;

import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;

public class GameController {

	private GameWindow gameWindow;
	private Board ourBoard;
	private Board enemyBoard;
	private AI ai;
	private int stage = 0;
	private Coordinate ourAttackCoord, ourHitCoord, enemyAttackCoord;
	private ArrayList<Coordinate> userHits,userShots, aiHits;
	private Stats ourStats, enemyStats;
	private StatsPanel ourStatsPanel, enemyStatsPanel;
	private StartUpParams sup;


	private boolean loadGame = false;
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

		ai.placeShips(ourBoard,loadGame);

		gameWindow = new GameWindow(this, ourBoard, enemyBoard);

		ourStatsPanel = new StatsPanel();
		enemyStatsPanel = new StatsPanel();

		userHits = new ArrayList<Coordinate>();
		userShots = new ArrayList<Coordinate>();
		aiHits = new ArrayList<Coordinate>();

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
			aiHits.add(ourAttack);

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
			enemyStatsPanel.setMissedStats(enemyStats.getTotalHit());

		} else if (attackResult.equals("Sank!")) {
			attackPoint.setIsSunk(true);
			attackPoint.setIsTaken(true);
			String hitShip = gameWindow.getShipHit();
			attackPoint.setShipId(hitShip);
			enemyStats.incrementTotalHit();
			enemyStats.incrementTotalSunk();
			enemyStatsPanel.setMissedStats(enemyStats.getTotalHit());
			enemyStatsPanel.setMissedStats(enemyStats.getTotalSunk());
			ai.resetVals();

		} else { // Missed
			enemyStats.incrementTotalMiss();
			enemyStatsPanel.setMissedStats(enemyStats.getTotalMiss());

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
		userShots.add(enemyAtkCoord);
		
		if (enemyAttackResult.getResult() == "Hit") {
			ourStats.incrementTotalHit();
			ourStatsPanel.setMissedStats(ourStats.getTotalHit());
			userHits.add(enemyAtkCoord);
			gameWindow.popupDialog("Ouch...", "You hit our " + enemyAttackResult.getShipName() + "!");
		} else if (enemyAttackResult.getResult() == "Sink") {
			ourStats.incrementTotalHit();
			ourStats.incrementTotalSunk();
			ourStatsPanel.setMissedStats(ourStats.getTotalHit());
			ourStatsPanel.setMissedStats(ourStats.getTotalSunk());
			gameWindow.popupDialog("Oh no!", "You sank our" + enemyAttackResult.getShipName() + "!");
		} else {
			ourStats.incrementTotalMiss();
			ourStatsPanel.setMissedStats(ourStats.getTotalMiss());
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

	SaveLoad save = new SaveLoad();
	String saveName = "save1";

	public void saveGame(){

		//open save window
		//save or save and exit?
		// watch.stop();

		save.save(saveName, this.gameWindow.getWatch().getElapsedTime(), stage, ourStats, enemyStats,
        ai.getPastShots(),aiHits,userShots,userHits,
        ai.getShipsPlaced(),
        sup.israndomAIPicked(), ai.getHits(), ai.isTargetMode(), ai.getDirectionSet(), 
        ai.getDirection(), ai.getFirstHit());

		System.out.println(enemyStats.getTotalMiss());
	}

	public void loadGame(){
		loadGame = true;
	
		// this.gameWindow.getStartParams().destroy();
		// gameWindow.close();
		//close start up parms

		save.load();

		this.gameWindow.getWatch().setElapsedTime(save.getElapsedTime());
		stage = save.getStage();

		ourStats = save.getOurStats();
		enemyStats = save.getEnmeyStats();

		ai.setPastShots(save.getPastShots());
		aiHits = save.getAiHits();
		userShots = save.getUserShots();
		userHits = save.getUserHits();

		ourBoard = new Board(Constants.boardSize); //wipes out current boards
		enemyBoard = new Board(Constants.boardSize);

		ai.setShipsPlaced(save.getShipsPlaced()); //load ships
		ai.placeShips(ourBoard, loadGame);

		ourBoard.loadBoard(ourBoard, userShots, userHits);
		enemyBoard.loadBoard(enemyBoard, ai.getPastShots(), aiHits);

		gameWindow.refreshOurBoard(ourBoard);
		gameWindow.refreshOurStats(ourStats);
		gameWindow.refreshEnemyBoard(enemyBoard);
		gameWindow.refreshEnemyStats(enemyStats);

		sup.setisrandomAIPicked(save.israndomAIPicked());
		ai.setHits(save.getHits());
		ai.setTargetMode(save.getTargetMode());
		ai.setDirectionSet(save.getDirectionSet());
		ai.setDirection(save.getDirection());
		ai.setFirstHit(save.getFirstHit());

		System.out.println(ai.isTargetMode());


	}


    public SaveLoad getSave() {
        return save;
    }


	

}
