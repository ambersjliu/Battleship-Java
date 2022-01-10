
package battleship.UI;
import java.awt.*;
import javax.swing.*;

import battleship.Control.*;
import battleship.Model.*;
import battleship.Model.Point;

/**
 * File: BoardPanel.java
 * <p>Mr. Anadarajan
 * <p>ICS4U1
 * <p>28 December, 2021
 * 
 * <p>Final Evaluation: Battleship Tournament
 * <p>Description: Describes and creates the Board panel displayed in the GameWindow before and during the game.
 * 
 * @author Amber Liu
 * @author Elaine Yang
 */


public class BoardPanel extends JPanel{
    Board board; 
	GameController controller;
	String name;
	JButton[][] shipButtons = new JButton[Constants.boardSize][Constants.boardSize];

	public BoardPanel(Board board, GameController controller, String name) {
		this.board = board; //the board to display
		this.controller = controller; 
		this.name = name; //name of displayed board
		setLayout(new GridLayout(0, Constants.boardSize + 1)); //grid layout with 1	more column than grid size to fit letters
		showInitialBoard(board);
	}

	/**
	 * Displays the board at the beginning of the game.
	 * @param board the board to draw
	 */
	public void showInitialBoard(Board board) {
		Point[][] points = board.getPoints();
		// show first line
		add(new JLabel(" "));

		for (int col = 1; col <= Constants.boardSize; col++) {
			JLabel letterLbl = new JLabel(String.valueOf(col));
			letterLbl.setHorizontalAlignment(SwingConstants.CENTER);
			add(letterLbl);
		}
		char letter = 'A';
		for (int row = 0; row < Constants.boardSize; row++, letter++) {
			JLabel letterLbl = new JLabel(String.valueOf(letter));
			letterLbl.setHorizontalAlignment(SwingConstants.CENTER);
			add(letterLbl);
			for (int col = 0; col < Constants.boardSize; col++) {
				Point p = points[row][col];
				JButton markBtn = new JButton();
				markBtn.setBackground(Color.LIGHT_GRAY);
				markBtn.setMargin(new Insets(0, 0, 0, 0));
				markBtn.setFocusPainted(false);
				markBtn.setEnabled(false);
				add(markBtn);
				shipButtons[row][col] = markBtn;
			}
		}
	}

	/** 
	* Updates the board to reflect hits, misses, ship locations, etc.
	* @param board board to draw 
	*/
	public void updateBoard(Board board) {
		Point[][] points = board.getPoints();

		String mark = " ";
		for (int row = 0; row < Constants.boardSize; row++) {
			for (int col = 0; col < Constants.boardSize; col++) {
				Point p = points[row][col];
				JButton currentBtn = shipButtons[row][col];
                //ternary operator (?) works as such
                //if p is taken (?), set to one colour. else (:) set to another colour

				if (p.getIsHit()){ //if point is hit
					currentBtn.setBackground(p.getIsTaken() ? Color.RED : Color.WHITE); //if ship hit, make it red. otherwise, make it white
					mark = "X";

					if (!p.getShipId().equals("default")){ //if point hit contains a ship
						mark = addShipInitials(p);
					}

            //Adjust later so that if isTaken, "mark" should be a letter based on which ship is there (for clarity's sake...)
				}else{ //nothing was hit
					currentBtn.setBackground(Color.LIGHT_GRAY); //only for viewing ships on our end...
					mark = " ";
                }
				currentBtn.setText(mark);
			}
		}
	}
	
	/**
	 * 
	 * @param p Takes in a point on board
	 * @return String with first 2 letters of Ship name
	 */
	public String addShipInitials (Point p){
		String mark = null;
	
			if (p.getShipId().equals("Carrier")){ //checks which ship is at point  
				mark = "CA";				//and assigns the corresponding letters 
			}
			else if (p.getShipId().equals("Battleship")){
				mark = "BA";
			}	
			else if (p.getShipId().equals("Cruiser")){
				mark = "CR";
			}
			else if (p.getShipId().equals("Submarine")){
				mark = "SU";
			}
			else {
				mark = "DE";
			}
		
		return mark;

	}
}
