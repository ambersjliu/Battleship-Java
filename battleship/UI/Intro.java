


package battleship.UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.*;
import battleship.Control.*;

/**
 * File: Intro.java
 * <p>Mr. Anadarajan
 * <p>ICS4U1
 * <p>06 January, 2021
 * 
 * <p>Final Evaluation: Battleship Tournament
 * <p>Description: Intro window displayed before game start. 
 * 
 * @author Amber Liu
 */

public class Intro{
    private JFrame introFrame;
    private ImageIcon bg;
    private JButton startButton;

    /**
     * Closes the window.
     */
    public void close(){
        introFrame.setVisible(false);
    }

    /**
     * Intro constructor. Loads the intro image from resources, and draws the window.
     * @throws MalformedURLException
     */
    public Intro(){

        URL url;
		try {
			url = new File("resources/introimg.png").toURI().toURL();
			bg = new ImageIcon(url);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        drawIntro();

    }

    /**
     * Method that creates the intro window. Calls game controller to begin game once START is clicked.
     */
    public void drawIntro() {


        introFrame = new JFrame();
        introFrame.setTitle("Battleship");
        introFrame.setResizable(false);
        introFrame.setBounds(200, 200, 640, 480);
		introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel bgLabel = new JLabel();
		bgLabel.setIcon(bg);
		introFrame.setContentPane(bgLabel);
        introFrame.getContentPane().setLayout(null);

        startButton = new JButton("START!");
        startButton.setBackground(new Color(165, 42, 42));
		startButton.setForeground(Color.WHITE);
        startButton.setFocusable(false);
		startButton.setFont(new Font("Bahnschrift", Font.BOLD, 23));
        startButton.setBounds(260, 300, 120, 32);

        startButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                close();
                
		        GameController gameController = new GameController();
		        gameController.startGame();
            } 
          } );

        introFrame.getContentPane().add(startButton);

        introFrame.setVisible(true);
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




    }


}
