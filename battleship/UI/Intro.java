package battleship.UI;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.*;


public class Intro extends JFrame{
    JFrame introFrame;
    Image bgImg;
    ImageIcon bg;
    JButton startButton;

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
        drawIntro();

    }

    public void drawIntro() {
        //need to create imageIcon showing our intro img
        //display it etc
        //also take in a username to use in our save file

        //add a listener to the button: when clicked, close the frame.

 

        introFrame = new JFrame();
        introFrame.setTitle("Battleship");
        introFrame.setResizable(false);
        introFrame.setBounds(100, 100, 640, 480);
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


        introFrame.getContentPane().add(startButton);

        introFrame.setVisible(true);
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




    }

}
