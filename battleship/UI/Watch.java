
/*
PROGRAM NAME - Battle-Ship/Watch

PROGRAMMERS - Elaine Yang

USAGE - Keep track of and controls the time the game has played 
		(after StartUpParams to When ai/user wins)

DATE - Started 12/08/2021
	   Completed 01/06/2022	

BUGS - 

DESCRIPTION - Input: n/a
			  Ouput: The time eplapsed in hours, minutes, secounds

*/

package battleship.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


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


	//Tics the timer, adds one secound to elapsedTime
	public Timer timer = new Timer(1000, new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {

			elapsedTime=elapsedTime+1000; 
			hours = (elapsedTime/3600000); //converts number into hour/minutes/secounds form
			minutes = (elapsedTime/60000) % 60;
			seconds = (elapsedTime/1000) % 60;

			seconds_string = String.format("%02d", seconds);
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
	
	public void reset() {
		timer.stop();
		elapsedTime=0;
		seconds =0;
		minutes=0;
		hours=0;
		seconds_string = String.format("%02d", seconds);
		minutes_string = String.format("%02d", minutes);
		hours_string = String.format("%02d", hours);
		timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
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

    

