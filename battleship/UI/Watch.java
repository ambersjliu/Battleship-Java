
package battleship.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * File: Watch.java
 * <p>Mr. Anadarajan
 * <p>ICS4U1
 * <p>06 January, 2021
 * 
 * <p>Final Evaluation: Battleship Tournament
 * <p>Description: Keep track of and creates the GUI timer 
 * @author Elaine Yang
 */


public class Watch implements ActionListener{
    
	//initialze timer varaibles 
	JLabel timeLabel = new JLabel();
    int elapsedTime = 0;
	int seconds =0;
	int minutes =0;
	int hours =0;
	boolean started = false;
	String seconds_string = String.format("%02d", seconds);
	String minutes_string = String.format("%02d", minutes);
	String hours_string = String.format("%02d", hours);


	//Tics the timer, adds one secound to elapsedTime, updates timer
	public Timer timer = new Timer(1000, new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {

			elapsedTime=elapsedTime+1000; 
			hours = (elapsedTime/3600000); //converts number into hour/minutes/secounds form
			minutes = (elapsedTime/60000) % 60;
			seconds = (elapsedTime/1000) % 60;

			seconds_string = String.format("%02d", seconds); //formats number as two digits (8 -> 08)
			minutes_string = String.format("%02d", minutes);
			hours_string = String.format("%02d", hours);
			timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
			
		}
		
	});

	// sets the initial timerLable for gui
	public Watch(){

		timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
		timeLabel.setBounds(200,40,120,60);
		timeLabel.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 18));
		timeLabel.setBorder(BorderFactory.createBevelBorder(1));
		timeLabel.setOpaque(true);
		timeLabel.setHorizontalAlignment(JTextField.CENTER);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	//methods for timer functions 
    public void start() {
		timer.start();
	}
	
	public void stop() {
		timer.stop();
	}


	
	//methods for GUI to access the timer
    
	public JLabel getTimeLabel(){
		return timeLabel;
	}

	public Timer getTimer(){
		return timer;
	}

	public void setElapsedTime(int elapsedTime){
		this.elapsedTime = elapsedTime;

	}

	public int getElapsedTime(){
		return elapsedTime;
	}
} 

    

