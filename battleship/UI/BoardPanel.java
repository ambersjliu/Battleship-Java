package battleship.UI;
import java.awt.*;
import javax.swing.*;

import battleship.Control.*;
import battleship.Model.*;
import battleship.Model.Point;

public class BoardPanel extends JPanel{
    Board board;
	GameController controller;
	String name;
	JButton[][] shipButtons = new JButton[Constants.boardSize][Constants.boardSize];

	public BoardPanel(Board board, GameController controller, String name) {
		this.board = board;
		this.controller = controller;
		this.name = name;
		setLayout(new GridLayout(0, Constants.boardSize + 1));
		showInitialBoard(board);
	}

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
				markBtn.setBackground(p.getIsTaken() ? Color.GRAY : Color.LIGHT_GRAY);
/* 				if (p.getIsHit())
				else if (p.getIsTaken())
					mark = "o";
				else
					mark = " "; */
				markBtn.setMargin(new Insets(0, 0, 0, 0));
				markBtn.setFocusPainted(false);
				markBtn.setEnabled(false);
				add(markBtn);
				shipButtons[row][col] = markBtn;
			}
		}
	}

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
				}
				else //nothing was hit
					currentBtn.setBackground(p.getIsTaken() ? Color.GRAY : Color.LIGHT_GRAY); //only for viewing ships on our end...
					mark = " ";
				currentBtn.setText(mark);
			}
		}
	}
}
