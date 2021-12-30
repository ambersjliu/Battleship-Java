package battleship.Control;
import battleship.Model.*;
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

    private boolean currentGameOver = false;



    void initialize() {
		ourBoard = new Board(Constants.boardSize);
		enemyBoard = new Board(Constants.boardSize);
		ourStats = new Stats(0, 0, 0);
		enemyStats = new Stats(0, 0, 0);
		stage = 0;

		ai = new AI();

		HashMap<String, Ship> ships = ai.placeShips(Constants.shipArr, ourBoard);		
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

    void updateState() {
		switch (stage) {
		case 0:
			//attack(); //call attack method
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
			StartUpParams sup = gameWindow.getStartParams(); //get start up params (who goes first, etc) from gui
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
