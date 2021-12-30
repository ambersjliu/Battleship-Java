package battleship.UI;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import battleship.Control.GameController;
import battleship.Model.*;
import battleship.UI.*;

public class GameWindow {

	private JFrame frmBattleship;
	private StatsPanel ourStatsPanel, enemyStatsPanel;
	private GameController gameController;
	private Board ourBoard, enemyBoard;
	private BoardPanel enemyBoardPanel, ourBoardPanel;
	private JButton recordHitButton, attackedBtn, exitBtn, newGameBtn;
	private JTabbedPane boardsPane;

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
		getFrmBattleship().setBounds(100, 100, 640, 480);
		getFrmBattleship().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrmBattleship().setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
		getFrmBattleship().getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel topPanel = initializeTopPanel();
		getFrmBattleship().getContentPane().add(topPanel, BorderLayout.NORTH);

		boardsPane = initializeBoardsPane();
		getFrmBattleship().getContentPane().add(boardsPane, BorderLayout.CENTER);

	}

	private JPanel initializeTopPanel() {
		JPanel topPanel = new JPanel();

		JLabel titleLabel = new JLabel("May the Force be with you! ");
		titleLabel.setFont(new Font("Microsoft PhagsPa", Font.BOLD | Font.ITALIC, 14));
		topPanel.add(titleLabel);
		return topPanel;
	}

	JTabbedPane initializeBoardsPane() {
		JTabbedPane boardsPane = new JTabbedPane(JTabbedPane.TOP);

		JPanel ourPanel = new JPanel();
		boardsPane.addTab("Light side", null, ourPanel, null);
		ourPanel.setLayout(new BorderLayout(0, 0));

		ourBoardPanel = new BoardPanel(this.ourBoard, this.gameController, "Light");
		ourPanel.add(ourBoardPanel, BorderLayout.CENTER);

		ourStatsPanel = new StatsPanel();
		ourPanel.add(ourStatsPanel, BorderLayout.EAST);

		JPanel enemyPanel = new JPanel();
		boardsPane.addTab("Dark side", null, enemyPanel, null);
		enemyPanel.setLayout(new BorderLayout(0, 0));

		enemyStatsPanel = new StatsPanel();
		enemyPanel.add(enemyStatsPanel, BorderLayout.EAST);

		enemyBoardPanel = new BoardPanel(this.enemyBoard, this.gameController, "Dark");
		enemyPanel.add(enemyBoardPanel, BorderLayout.CENTER);

		boardsPane.setSelectedComponent(enemyPanel);
		return boardsPane;
	}

	public BoardPanel getDarkBoardPanel() {
		return enemyBoardPanel;
	}

	public BoardPanel getLightBoardPanel() {
		return ourBoardPanel;
	}

	public StartUpParams getStartParams() {

		String[] whoMovesFirst = { "Light", "Dark" };
		String[] AIlevel = { "Random", "Advanced" };
		JComboBox jcFirstMover = new JComboBox(whoMovesFirst);
		JComboBox jcAILevel = new JComboBox(AIlevel);

		Object[] objects = new Object[] { "Who moves first", jcFirstMover, "AI level", jcAILevel };
		JOptionPane.showConfirmDialog(frmBattleship, objects, "Start up parameters", JOptionPane.DEFAULT_OPTION);

		return new StartUpParams(jcFirstMover.getSelectedIndex() == 0, jcAILevel.getSelectedIndex() == 0);

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
		String[] attackResults = { "Missed :-(", "Hit :-)", "Sink! :-))" };

		JComboBox jcAttackResults = new JComboBox(attackResults);
		Object objects[] = new Object[] { "X-Wings, attack: ", ourAttackCoordStr, "Did we hit?", jcAttackResults };
		JOptionPane.showConfirmDialog(frmBattleship, objects, "Attack result", JOptionPane.DEFAULT_OPTION);

		return jcAttackResults.getSelectedItem().toString();
	}

	public void refreshDarkBoard(Board enemyBoard) {
		enemyBoardPanel.updateBoard(enemyBoard);
	}

	public void refreshLightBoard(Board ourBoard) {
		ourBoardPanel.updateBoard(ourBoard);
	}

	public void destroy() {
		getFrmBattleship().setVisible(false);
		getFrmBattleship().dispose(); // Destroy the JFrame object;
	}

	public void refreshLightStats(Stats ourStats) {
		ourStatsPanel.setHitStats(ourStats.getTotalHit());
		ourStatsPanel.setMissedStats(ourStats.getTotalMiss());
		ourStatsPanel.setSunkStats(ourStats.getTotalSunk());
	}

	public void refreshDarkStats(Stats enemyStats) {
		enemyStatsPanel.setHitStats(enemyStats.getTotalHit());
		enemyStatsPanel.setMissedStats(enemyStats.getTotalMiss());
		enemyStatsPanel.setSunkStats(enemyStats.getTotalSunk());
	}

	public String getDarkAttackCoord() {
		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Incoming attack coordinate");
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

}
