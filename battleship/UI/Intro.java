package battleship.UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.*;
import battleship.Control.*;


public class Intro{
    private JFrame introFrame;
    private Image bgImg;
    private ImageIcon bg;
    private JButton startButton;
    private boolean closeWindow;


    public void close(){
        introFrame.setVisible(false);
    }

    public Intro(){

        URL url;
		try {
			url = new File("resources/introimg.png").toURI().toURL();
			bg = new ImageIcon(url);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    public void drawIntro() {
        //need to create imageIcon showing our intro img
        //display it etc
        //also take in a username to use in our save file

        //add a listener to the button: when clicked, close the frame.

        closeWindow = false;

        introFrame = new JFrame();
        introFrame.setTitle("Battleship");
        introFrame.setResizable(false);
        introFrame.setBounds(200, 200, 640, 480);
		introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel bgLabel = new JLabel();
		bgLabel.setIcon(bg);
		introFrame.setContentPane(bgLabel);
        introFrame.getContentPane().setLayout(null);

        startButton = new JButton("Start!");
        startButton.setBackground(new Color(165, 42, 42));
		startButton.setForeground(Color.WHITE);
		startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBounds(270, 300, 100, 30);

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

    public boolean getIntroClosed(){
        return closeWindow;
    }

}
