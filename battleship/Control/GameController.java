
package battleship.Control;

import battleship.Model.*;
import battleship.Model.Point;
import battleship.SaveLoad;
import battleship.Attack.*;
import battleship.UI.*;

import java.util.*;
import java.awt.*;

/**
 * File: GameController.java
 * <p>Mr. Anadarajan
 * <p>ICS4U1
 * <p>06 January, 2021
 * 
 * <p>Final Evaluation: Battleship Tournament
 * <p>Description: Handles input, calls AI when needed and acts as the "hub" of the program.
 * Facilitates the flow of gameplay.
 * @author Amber Liu
 * @author Elaine Yang
 */

public class GameController {

	private GameWindow gameWindow;
	private Board ourBoard;
	private Board enemyBoard;
	private AI ai;
	private int stage = 0;
	private ArrayList<Coordinate> userHits,userShots, aiHits;
	private Stats ourStats, enemyStats;
	private StatsPanel ourStatsPanel, enemyStatsPanel;
	private StartUpParams sup;


	private boolean isLoadGame = false;
	private String username;
	private boolean currentGameOver = false;

	void initialize() { //called at the start of every game

		//set all the values to zero, create new boards, ai, etc.
		ourBoard = new Board(Constants.boardSize);
		enemyBoard = new Board(Constants.boardSize);
		ourStats = new Stats(0, 0, 0);
		enemyStats = new Stats(0, 0, 0);
		stage = 0;

		ai = new AI();

		ai.placeShips(ourBoard,isLoadGame);

		gameWindow = new GameWindow(this, ourBoard, enemyBoard);

		ourStatsPanel = new StatsPanel();
		enemyStatsPanel = new StatsPanel();

		userHits = new ArrayList<Coordinate>();
		userShots = new ArrayList<Coordinate>();
		aiHits = new ArrayList<Coordinate>();

		//create the window
		//not too sure what this is, eclipse made this on its own...
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

	/**
	 * Generates an attack from AI, communicates it to the user, and changes the game window to
	 * reflect the results of the attack.
	 */
	void attack() {
		Coordinate ourAttack = ai.getNextMove(sup.israndomAIPicked());
		String ourAttackString = ourAttack.coordFormat(ourAttack);
		String attackResult = gameWindow.getAttackResult(ourAttackString); // GUI shows a popup prompting answer from user

		System.out.println("Our AI attacks " + ourAttackString); // print to console

		Point attackPoint = enemyBoard.getPoint(ourAttack.getRow(), ourAttack.getColumn());
		attackPoint.setIsHit(true); // either way the attacked point is hit

		System.out.println(attackResult + " " + ourAttack.getRow() + " " + ourAttack.getColumn());
		if (attackResult.equals("Hit!")) { //if we hit
			attackPoint.setIsTaken(true); 
			ai.setTargetMode(true); //switch to target mode
			ai.setEndOfCurrentDirection(false);
			aiHits.add(ourAttack);

			if (ai.getHits().size() == 0) { //if we haven't hit anything before
				ai.setFirstHit(ourAttack); //set our new first hit
				ai.getHits().add(ourAttack);
			} else {
				ai.setDirectionSet(true); //since we've had at least one successful hit since first
				ai.getHits().add(ourAttack);
			}
			String hitShip = gameWindow.getShipHit(); // get id of ship
			gameWindow.playGameSound("Hit");

			attackPoint.setShipId(hitShip); 
			enemyStats.incrementTotalHit();

		} else if (attackResult.equals("Sank!")) {
			boolean noShipsLeft = true;
			Coordinate nextFirstHit = new Coordinate(0,0);

			attackPoint.setIsSunk(true);
			attackPoint.setIsTaken(true);
			String hitShip = gameWindow.getShipHit();
			attackPoint.setShipId(hitShip);
			gameWindow.playGameSound("Hit");
			enemyStats.incrementTotalHit();
			enemyStats.incrementTotalSunk();

			for(int i = 0; i<Constants.boardSize; i++){ //check if we accidentally hit any other ships
				for(int j = 0; j<Constants.boardSize; j++){
					if(enemyBoard.getPoint(i, j).getIsTaken()&&!enemyBoard.getPoint(i, j).getIsSunk()){
						//if there's a hit but not sunk ship
						noShipsLeft = false;
						nextFirstHit.setRow(i);
						nextFirstHit.setColumn(j);
						break;
					}
				} //try to break out as early as possible
				if(!noShipsLeft){
					break;
				}
			}
			ai.resetVals();
			if(!noShipsLeft){ //if there is another ship we hit
				ai.setFirstHit(nextFirstHit); //target that ship starting from our first hit
				ai.getHits().add(nextFirstHit);
				ai.setTargetMode(true);
			}

		} else { // Missed
			enemyStats.incrementTotalMiss();
			gameWindow.playGameSound("Missed");
			if (ai.isTargetMode()) { //if we were trying to hit a ship and missed
				ai.setEndOfCurrentDirection(true); //let AI know we need to change directions
			}
		}
		gameWindow.refreshEnemyStats(enemyStats);
		gameWindow.refreshEnemyBoard(enemyBoard);


		if (enemyStats.getTotalHit() == Constants.hitsToWin) {
			gameWindow.getWatch().stop();
			gameWindow.playGameSound("We won");
			gameWindow.popupDialog("You lost!", "Better luck next time! Press OK to restart."); // SHEEESH WE WIN
			//destroy the old game window
			gameWindow.destroy();
			currentGameOver = true;
		}

		stage = 2;

	}

	/**
	 * Asks the user for their move, checks the result, and communicates it to the user.
	 * Updates the board to reflect the attack's success.
	 */
	void recordAttack() {
		String enemyAttack = gameWindow.getEnemyAttackCoord().toUpperCase();
		System.out.println("enemy attack:" +enemyAttack);
		//gameWindow.playGameSound("Shoot");
		// convert the entered string into a Coord
		int col = Integer.parseInt(enemyAttack.substring(1)) - 1;
		int row = ((int) enemyAttack.charAt(0)) - 65;
		Coordinate enemyAtkCoord = new Coordinate(row, col);
		System.out.println("enemy attackCoord: "+enemyAtkCoord.toString());
		Point attackedPoint = ourBoard.getPoint(enemyAtkCoord.getRow(), enemyAtkCoord.getColumn());
		attackedPoint.setIsHit(true);
		AttackResults enemyAttackResult = ai.getEnemyAttackResult(ourBoard, enemyAtkCoord);

		if (enemyAttackResult.getResult() == "Hit") {

			if(userShots.contains(enemyAtkCoord)){ //in case the player accidentally repeats themselves
				repeatResponse();
			}else{

				ourStats.incrementTotalHit();
				userHits.add(enemyAtkCoord);
				gameWindow.playGameSound("Hit");
				gameWindow.popupDialog("Ouch...", "You hit our " + enemyAttackResult.getShipName() + "!");
			}
		} else if (enemyAttackResult.getResult() == "Sink") {

			if(userShots.contains(enemyAtkCoord)){ 
				repeatResponse();
			}
			else{
				ourStats.incrementTotalHit();
				ourStats.incrementTotalSunk();
				gameWindow.playGameSound("Hit");
				gameWindow.popupDialog("Oh no!", "You sank our " + enemyAttackResult.getShipName() + "!");
			}
		} else {
			if(userShots.contains(enemyAtkCoord)){ 
				repeatResponse();
			}else{

				ourStats.incrementTotalMiss();
				gameWindow.playGameSound("Missed");
				gameWindow.popupDialog("Phew!", "Missed!");
			}
		}
		userShots.add(enemyAtkCoord);


		gameWindow.refreshOurBoard(ourBoard);
		gameWindow.refreshOurStats(ourStats);

		if (ourStats.getTotalHit() == Constants.hitsToWin) {
			gameWindow.getWatch().stop();
			gameWindow.playGameSound("They won");
			gameWindow.popupDialog("Congrats!", "Looks like you've outsmarted our strategists! Press OK to restart.");
			gameWindow.destroy();
			currentGameOver = true;
		}
		

		stage = 0;

	}

	void repeatResponse(){
		ourStats.incrementTotalMiss();
		gameWindow.playGameSound("Missed");
		gameWindow.popupDialog("Phew!", "Did you mean to repeat yourself? Miss!");
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

	/**
	 * Starts the game and loops through the two possible states (us shooting vs. enemy shooting).
	 * Since it's a while(true) loop, it will run constantly. After the current game is over, the loop goes
	 * back to the beginning, and everything is reset.
	 */
	public void startGame() {

		while (true) { // loop that brings game back to initial state after game over
			initialize(); // reset vals to 0
			gameWindow.getLoadButton().setEnabled(true);
			username = gameWindow.getUsername();
			gameWindow.popupDialog("A friendly reminder...", "If you saved a game earlier, please load it now!");
			sup = gameWindow.getStartParams(isLoadGame); // get start up params (who goes first, etc) from gui
			gameWindow.getLoadButton().setEnabled(false);
			gameWindow.getWatch().start(); //start gameWindow watch
			

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

	/**
	 * Calls SaveLoad class and gets all the needed params 
	 * to save the current game state, board, and status of player and AI
	 */
	public void saveGame(){

		save.save(username, this.gameWindow.getWatch().getElapsedTime(), stage, ourStats, enemyStats,
        ai.getPastShots(),aiHits,userShots,userHits,
        ai.getShipsPlaced(), gameWindow.getSup(),
        ai.getHits(), ai.isTargetMode(), ai.getDirectionSet(), 
        ai.getDirection(), ai.getFirstHit(), ai.getEndOfCurrentDirection());

	}

	/**
	 * loadGame will prompt for the username of the saved game
	 * Using getters from SaveLoad class, loadGame() puts the saved params
	 * back into their original needed places
	 */
	public void loadGame(){
		isLoadGame = true;
		boolean fileFound=false;

		while (fileFound == false){ //while file is not found contine to ask user for username
				username = gameWindow.getUsername();
				save.setSaveName(username);
				fileFound=save.load(fileFound);

		}

		this.gameWindow.getWatch().setElapsedTime(save.getElapsedTime()); 
		stage = save.getStage(); 

		ourStats = save.getOurStats();
		enemyStats = save.getEnmeyStats();

		ai.setPastShots(save.getPastShots());
		aiHits = save.getAiHits();
		userShots = save.getUserShots();
		userHits = save.getUserHits();

		ourBoard = new Board(Constants.boardSize); 
		enemyBoard = new Board(Constants.boardSize);

		ai.setShipsPlaced(save.getShipsPlaced()); 
		ai.placeShips(ourBoard, isLoadGame);

		ourBoard.loadBoard(ourBoard, userShots, userHits);
		enemyBoard.loadBoard(enemyBoard, ai.getPastShots(), aiHits);

		gameWindow.refreshOurBoard(ourBoard);
		gameWindow.refreshOurStats(ourStats);
		gameWindow.refreshEnemyBoard(enemyBoard);
		gameWindow.refreshEnemyStats(enemyStats);

		sup = save.getSup();
		gameWindow.setSup(sup);

		ai.setHits(save.getHits());
		ai.setTargetMode(save.getTargetMode());
		ai.setDirectionSet(save.getDirectionSet());
		ai.setDirection(save.getDirection());
		ai.setFirstHit(save.getFirstHit());
		ai.setEndOfCurrentDirection(save.getEndOfCurrentDirection());

	}


	//getter for gameWindow 
    public SaveLoad getSave() {
        return save;
    }

	public void setIsLoadGame(boolean isLoadGame){
		this.isLoadGame = isLoadGame;
	}


	

}
