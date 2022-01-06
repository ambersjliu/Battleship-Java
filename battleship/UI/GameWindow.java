
/*
PROGRAM NAME - 

PROGRAMMERS - 

USAGE - The main GUI, diplays all GUI elements in it other than intro

DATE - Started 12/08/2021
	   Completed 01/06/2022	

BUGS - 

DESCRIPTION - 

*/

package battleship.UI;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Font;

import javax.sound.sampled.*;
import javax.swing.*;

import java.io.*;

import battleship.SaveLoad;
import battleship.Attack.*;
import battleship.Control.*;
import battleship.Model.*;

import java.awt.event.*;
import java.awt.*;


public class GameWindow implements ActionListener {

	private JFrame frmBattleship;
	private StatsPanel ourStatsPanel, enemyStatsPanel;
	private GameController gameController;
	private Board ourBoard, enemyBoard;
	private BoardPanel enemyBoardPanel, ourBoardPanel;
	private JButton recordHitButton, attackedBtn, exitBtn, newGameBtn, saveButton, loadButton;
	private JTabbedPane boardsPane;
	private StartUpParams sup;

	String username;
 	Watch watch;

	/**
	 * Create the application.
	 */
	public GameWindow(GameController gameController, Board ourBoard, Board enemyBoard) {
		this.gameController = gameController;
		this.ourBoard = ourBoard;
		this.enemyBoard = enemyBoard;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
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

	private JPanel initializeTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Constants.bgColor);
		GridBagConstraints c = new GridBagConstraints();

		saveButton = new JButton("Save");
		loadButton = new JButton("Load");

		saveButton.setBounds(400,20,140,40);
		saveButton.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 14));
		saveButton.setFocusable(false);
		saveButton.addActionListener(this);

		loadButton.setBounds(400,20,140,40);
		loadButton.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 14));
		loadButton.setFocusable(false);
		loadButton.addActionListener(this);

		//timer 
 		watch = new Watch();
		topPanel.add(watch.getTimeLabel());
		topPanel.add(saveButton);
		topPanel.add(loadButton); 


		return topPanel;
	}

	JTabbedPane initializeBoardsPane() {
		JTabbedPane boardsPane = new JTabbedPane(JTabbedPane.TOP);

		JPanel ourPanel = new JPanel();
		ourPanel.setForeground(Constants.bgColor);
        boardsPane.addTab("Our board", null, ourPanel, null);
        ourPanel.setLayout(new BorderLayout(0, 0));

		ourBoardPanel = new BoardPanel(this.ourBoard, this.gameController, "Light");
		ourPanel.add(ourBoardPanel, BorderLayout.CENTER);

		ourStatsPanel = new StatsPanel();
		ourPanel.add(ourStatsPanel, BorderLayout.EAST);

		JPanel enemyPanel = new JPanel();
		boardsPane.addTab("Your board", null, enemyPanel, null);
		enemyPanel.setLayout(new BorderLayout(0, 0));

		enemyStatsPanel = new StatsPanel();
		enemyPanel.add(enemyStatsPanel, BorderLayout.EAST);

		enemyBoardPanel = new BoardPanel(this.enemyBoard, this.gameController, "Dark");
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

	public StartUpParams getStartParams(boolean loadGame) {

		String[] whoMovesFirst = { "Our AI", "Your AI" };
		String[] AIlevel = { "Random", "Advanced" };
		JComboBox jcFirstMover = new JComboBox(whoMovesFirst);
		JComboBox jcAILevel = new JComboBox(AIlevel);

		Object[] objects = new Object[] { "Who moves first?", jcFirstMover, "Choose your AI level", jcAILevel };
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


	public void popupDialog(String title, String message) {
		JOptionPane.showConfirmDialog(frmBattleship, message, title, JOptionPane.DEFAULT_OPTION);
	}

	public JFrame getFrmBattleship() {
		return frmBattleship;
	}

	public void setFrmBattleship(JFrame frmBattleship) {
		this.frmBattleship = frmBattleship;
	}

	public String getAttackResult(String ourAttackCoordStr) {
		String[] attackResults = { "Missed!", "Hit!", "Sank!" };

        //Change so that if "hit" or "sank", a new popup will appear with a ComboBox asking which one we hit.

		JComboBox jcAttackResults = new JComboBox(attackResults);
		Object objects[] = new Object[] { "Our AI attacks: ", ourAttackCoordStr, "Did we hit?", jcAttackResults };
		JOptionPane.showConfirmDialog(frmBattleship, objects, "Attack result", JOptionPane.DEFAULT_OPTION);

		return jcAttackResults.getSelectedItem().toString();
	}

	public String getShipHit(){
		String[] ships = {"Carrier","Battleship", "Cruiser", "Submarine", "Destroyer"};
		JComboBox jcShipOptions = new JComboBox(ships);
		Object objects[] = new Object[]{"Which ship was struck?", jcShipOptions};
		JOptionPane.showConfirmDialog(frmBattleship, objects, "Attack result", JOptionPane.DEFAULT_OPTION);

		return jcShipOptions.getSelectedItem().toString();

	}

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

	public void destroy() {
		getFrmBattleship().setVisible(false);
		getFrmBattleship().dispose(); // Destroy the JFrame object;
	}

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

	public String getEnemyAttackCoord() { 
		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter your attack coordinate: ");
		JTextField enemyAttackCoord = new JTextField(3);
		panel.add(lbl);
		panel.add(enemyAttackCoord);
		String enemyAttackCoordStr = "";

		while (true) {
			int selectedOption = JOptionPane.showOptionDialog(frmBattleship, panel, "Incoming attack!", JOptionPane.NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			try {
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
				Thread.sleep(5000); //longer time before thread put to sleep
			}else{
				Thread.sleep(2100); //unfortunately playing sounds halt the program until the thread is put to sleep
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
