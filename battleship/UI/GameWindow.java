


package battleship.UI;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Font;

import javax.sound.sampled.*;
import javax.swing.*;

import java.io.*;
import java.net.*;

import battleship.SaveLoad;
import battleship.Control.*;
import battleship.Model.*;

import java.awt.event.*;
import java.awt.*;

/**
 * File: GameWindow.java
 * <p>Mr. Anadarajan
 * <p>ICS4U1
 * <p>27 December, 2021
 * 
 * <p>Final Evaluation: Battleship Tournament
 * <p>Description: GUI methods called by GameController to display the game, ask for user input, and play sound.
 * 
 * @author Amber Liu
 * @author Elaine Yang
 */

public class GameWindow implements ActionListener {

	private JFrame frmBattleship;
	private StatsPanel ourStatsPanel, enemyStatsPanel;
	private GameController gameController;
	private Board ourBoard, enemyBoard;
	private BoardPanel enemyBoardPanel, ourBoardPanel;
	private JButton saveButton, loadButton;
	private JTabbedPane boardsPane;
	private StartUpParams sup;
	private ImageIcon loadIcon, saveIcon, disabledLoad;
	private Dimension buttonSize;

	String username;
 	Watch watch;

	/**
	 * Create the application, and loads in the images used for the save, load buttons.
	 * @throws MalformedURLException
	 */
	public GameWindow(GameController gameController, Board ourBoard, Board enemyBoard) {
		URL url;
		URL url2;
		URL url3;
		try {
			url = new File("resources/save button.png").toURI().toURL();
			saveIcon = new ImageIcon(url);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try {
			url2 = new File("resources/load button.png").toURI().toURL();
			loadIcon = new ImageIcon(url2);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		
		try {
			url3 = new File("resources/load disabled.png").toURI().toURL();
			disabledLoad = new ImageIcon(url3);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		this.gameController = gameController;
		this.ourBoard = ourBoard;
		this.enemyBoard = enemyBoard;
		initialize();
	}

	/**
	 * Creates the game window by calling the initializing methods for the other 
	 * components and arranging them inside frmBattleship.
	 */
	private void initialize() {
		setFrmBattleship(new JFrame());
		getFrmBattleship().setTitle("BattleShip");
		getFrmBattleship().setResizable(false);
		getFrmBattleship().setBounds(200, 200, 640, 480);
		getFrmBattleship().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrmBattleship().setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
		getFrmBattleship().getContentPane().setLayout(new BorderLayout(0, 0));

		
		getFrmBattleship().getContentPane().setBackground(Constants.bgColor);

		JPanel topPanel = initializeTopPanel();
		getFrmBattleship().getContentPane().add(topPanel, BorderLayout.NORTH);

		boardsPane = initializeBoardsPane();
		getFrmBattleship().getContentPane().add(boardsPane, BorderLayout.CENTER);

	}

	/**
	 * Draws the top panel with the save/load buttons and timer.
	 * @return the top panel
	 */
	private JPanel initializeTopPanel() {
		buttonSize = new Dimension(140, 40);
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Constants.bgColor);
		GridBagConstraints c = new GridBagConstraints();

		//save, load buttons
		saveButton = new JButton();
		loadButton = new JButton();

		saveButton.setBounds(400,20,140,40);
		saveButton.setPreferredSize(buttonSize);
		saveButton.setIcon(saveIcon);
		saveButton.setFocusable(false);
		saveButton.addActionListener(this);

		loadButton.setBounds(400,20,140,40);
		loadButton.setPreferredSize(buttonSize);

		loadButton.setIcon(loadIcon);
		loadButton.setDisabledIcon(disabledLoad); //because the default disabled look is too greyed out
		loadButton.setFocusable(false);
		loadButton.addActionListener(this);

		//timer 
 		watch = new Watch();
		topPanel.add(watch.getTimeLabel());
		topPanel.add(saveButton);
		topPanel.add(loadButton); 


		return topPanel;
	}

	/**
	 * Creates a tabbed pane so that the player can switch their between the two board views.
	 * Also draws a statsPanel on the side displaying the number of hits/misses/sunk ships each board has accrued.
	 * @return the tabbed pane with the boards and their respective stats panels
	 */
	JTabbedPane initializeBoardsPane() {
		JTabbedPane boardsPane = new JTabbedPane(JTabbedPane.TOP);

		JPanel ourPanel = new JPanel();
		ourPanel.setForeground(Constants.bgColor);
        boardsPane.addTab("Our board", null, ourPanel, null);
        ourPanel.setLayout(new BorderLayout(0, 0));

		ourBoardPanel = new BoardPanel(this.ourBoard, this.gameController, "Ours");
		ourPanel.add(ourBoardPanel, BorderLayout.CENTER);

		ourStatsPanel = new StatsPanel();
		ourPanel.add(ourStatsPanel, BorderLayout.EAST);

		JPanel enemyPanel = new JPanel();
		boardsPane.addTab("Your board", null, enemyPanel, null);
		enemyPanel.setLayout(new BorderLayout(0, 0));

		enemyStatsPanel = new StatsPanel();
		enemyPanel.add(enemyStatsPanel, BorderLayout.EAST);

		enemyBoardPanel = new BoardPanel(this.enemyBoard, this.gameController, "Theirs");
		enemyPanel.add(enemyBoardPanel, BorderLayout.CENTER);

		boardsPane.setSelectedComponent(enemyPanel);
		return boardsPane;
	}

	public BoardPanel getEnemyBoardPanel() {
		return enemyBoardPanel;
	}

	public BoardPanel getOurBoardPanel() {
		return ourBoardPanel;
	}


	/**
	 * @param loadGame is load game button selected
	 * @return if load game is false, returns user selected StartUpParams of current game
	 * if load game is true, returns StartUpParams that is saved
	 */

	public StartUpParams getStartParams(boolean loadGame) {

		String[] whoMovesFirst = { "Our AI", "Your AI" };
		String[] AIlevel = { "Random", "Advanced" };
		JComboBox jcFirstMover = new JComboBox(whoMovesFirst);
		JComboBox jcAILevel = new JComboBox(AIlevel);

		Object[] objects = new Object[] { "Who moves first?", jcFirstMover, "Choose your AI level:", jcAILevel };
		JOptionPane.showConfirmDialog(frmBattleship, objects, "Start up parameters", JOptionPane.DEFAULT_OPTION);
		
		if (loadGame == false){
			sup = new StartUpParams(jcFirstMover.getSelectedIndex() == 0, jcAILevel.getSelectedIndex() == 0);
			return new StartUpParams(jcFirstMover.getSelectedIndex() == 0, jcAILevel.getSelectedIndex() == 0);
		}

		else{
			return sup;
		}

	}

	//for save and load
	public StartUpParams getSup(){ //get sup with out making a new one
		return sup;
	}

	public void setSup(StartUpParams sup){ 
		this.sup = sup;
	}

	/**
	 * A method for quickly creating a popup with an OK button
	 * @param title The title in the dialog frame
	 * @param message The message inside the popup
	 */
	public void popupDialog(String title, String message) {
		JOptionPane.showConfirmDialog(frmBattleship, message, title, JOptionPane.DEFAULT_OPTION);
	}

	public JFrame getFrmBattleship() {
		return frmBattleship;
	}

	public void setFrmBattleship(JFrame frmBattleship) {
		this.frmBattleship = frmBattleship;
	}

	/**
	 * Prompts the user to choose the result of our AI's attack from a dropdown menu.
	 * @param ourAttackCoordStr the coordinate we're attacking
	 * @return the result chosen by the user
	 */
	public String getAttackResult(String ourAttackCoordStr) {
		String[] attackResults = { "Missed!", "Hit!", "Sank!" };

        //Change so that if "hit" or "sank", a new popup will appear with a ComboBox asking which one we hit.

		JComboBox jcAttackResults = new JComboBox(attackResults);
		Object objects[] = new Object[] { "Our AI attacks: ", ourAttackCoordStr, "Did we hit?", jcAttackResults };
		JOptionPane.showConfirmDialog(frmBattleship, objects, "Attack result", JOptionPane.DEFAULT_OPTION);

		return jcAttackResults.getSelectedItem().toString();
	}

	/**
	 * Asks user to select which ship was hit from a dropdown menu.
	 * @return result chosen by the user
	 */
	public String getShipHit(){
		String[] ships = {"Carrier","Battleship", "Cruiser", "Submarine", "Destroyer"};
		JComboBox jcShipOptions = new JComboBox(ships);
		Object objects[] = new Object[]{"Which ship was struck?", jcShipOptions};
		JOptionPane.showConfirmDialog(frmBattleship, objects, "Attack result", JOptionPane.DEFAULT_OPTION);

		return jcShipOptions.getSelectedItem().toString();

	}

	/**
	 * Asks user for their username. Used when identifying which file should be loaded.
	 * @return Entered username
	 */
	public String getUsername(){
		String user = JOptionPane.showInputDialog(frmBattleship, "Enter a username: ", "Username", JOptionPane.DEFAULT_OPTION);
		return user;

	}

	public void refreshEnemyBoard(Board enemyBoard) {
		enemyBoardPanel.updateBoard(enemyBoard);
	}

	public void refreshOurBoard(Board ourBoard) {
		ourBoardPanel.updateBoard(ourBoard);
	}

	/**
	 * Destroys the current game window. Used to clear everything at the end of the game.
	 */
	public void destroy() {
		getFrmBattleship().setVisible(false);
		getFrmBattleship().dispose(); // Destroy the JFrame object;
	}

	/**
	 * Updates the stats panel for our board to reflect its current state.
	 * @param ourStats our stats
	 */
	public void refreshOurStats(Stats ourStats) {
		ourStatsPanel.setHitStats(ourStats.getTotalHit());
		ourStatsPanel.setMissedStats(ourStats.getTotalMiss());
		ourStatsPanel.setSunkStats(ourStats.getTotalSunk());
	}

	public void refreshEnemyStats(Stats enemyStats) {
		enemyStatsPanel.setHitStats(enemyStats.getTotalHit());
		enemyStatsPanel.setMissedStats(enemyStats.getTotalMiss());
		enemyStatsPanel.setSunkStats(enemyStats.getTotalSunk());
	}

	/**
	 * Prompts user to enter their attack as a String.
	 */
	public String getEnemyAttackCoord() { 
		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter your attack coordinate: ");
		JTextField enemyAttackCoord = new JTextField(3);
		panel.add(lbl);
		panel.add(enemyAttackCoord);
		String enemyAttackCoordStr = "";

		while (true) { //continuously create the window if the attack isn't valid/if the user closes thw indow
			int selectedOption = JOptionPane.showOptionDialog(frmBattleship, panel, "Incoming attack!", JOptionPane.NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			try { //make sure the attack is a valid coordinate
				if (selectedOption == 0) {
					enemyAttackCoordStr = enemyAttackCoord.getText();
					char row = enemyAttackCoordStr.toLowerCase().charAt(0);
					int col = Integer.parseInt(enemyAttackCoordStr.substring(1));
					if ((row >= 'a' && row <= 'j') && (col >= 1 && col <= 10))
						break;
				}
			} catch (Exception e) {
			}
		}

		return enemyAttackCoordStr;
		
	}

	public JButton getLoadButton(){
		return loadButton;
	}
	

	/**
	 * Plays a different .wav clip depending on the mode given.
	 * @param mode the context(shoot, hit, miss, game over)
	 */
	public void playGameSound(String mode){
		String SHOOTAUDIOFILE = "resources/shoot.wav";
		String HITAUDIOFILE = "resources/explode.wav";
		String MISSEDAUDIOFILE = "resources/splash.wav";
		String YOUWINFILE = "resources/youwin.wav";
		String YOULOSTFILE = "resources/youlost.wav";
		String audioFile;
		Clip clip;
		AudioInputStream audioInputStream;

		if(mode.equals("Shoot")){
			audioFile = SHOOTAUDIOFILE;
		}else if(mode.equals("Hit")){
			audioFile = HITAUDIOFILE;
		}else if(mode.equals("They won")){
			audioFile = YOUWINFILE;
		}else if(mode.equals("We won")){
			audioFile = YOULOSTFILE;	
		}else{
			audioFile = MISSEDAUDIOFILE;
		}

		try {
			// create AudioInputStream object
			audioInputStream = AudioSystem.getAudioInputStream(new File(audioFile).getAbsoluteFile());
			// create clip reference
			clip = AudioSystem.getClip();
			// open audioInputStream to the clip
			clip.open(audioInputStream);
			clip.start();
			System.out.println("Playing audio");
			if(mode.equals("They won")||mode.equals("We won")){ //since end sounds are much longer
				Thread.sleep(5000); //longer time where thread is put to sleep
			}else{
				Thread.sleep(2100); //unfortunately playing sounds needs you to put the thread to sleep which means everything else stops 
				//this could be solved by playing the sound on another thread but I have no idea how to do that
			}
			clip.close();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) { 
			e.printStackTrace();
		}
		
	}


	@Override
    public void actionPerformed(ActionEvent e) {
		if(e.getSource()==saveButton) {
			this.gameController.saveGame();
	
		}
		if(e.getSource()==loadButton) {

			//get the saved time into the gui timer
			watch.setElapsedTime(this.gameController.getSave().getElapsedTime());

			this.gameController.loadGame();


		}

	}
	//for game controller to access the watch
	public Watch getWatch() {
		return watch;
	}




}
