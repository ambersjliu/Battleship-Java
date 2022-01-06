package battleship.UI;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import battleship.Model.Constants;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.*;


public class StatsPanel extends JPanel {
	JButton missedStatsButton;
	JButton hitStatsButton;
	JButton sunkStatsButton;

	ImageIcon hitsIcon;
	ImageIcon missesIcon;
	ImageIcon sunkIcon;

	public StatsPanel() {
		setLayout(new GridLayout(0, 2));

		URL url;
		try {
			url = new File("resources/hits button.png").toURI().toURL();
			hitsIcon = new ImageIcon(url);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		URL url2;
		try {
			url2 = new File("resources/misses button.png").toURI().toURL();
			missesIcon = new ImageIcon(url2);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		URL url3;
		try {
			url3 = new File("resources/sunk button.png").toURI().toURL();
			sunkIcon = new ImageIcon(url3);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		JLabel hitText = new JLabel(hitsIcon);
		add(hitText);

		hitStatsButton = new JButton("0");
		hitStatsButton.setBackground(Constants.bgColor);
		hitStatsButton.setForeground(Constants.fgColor);
		hitStatsButton.setEnabled(false);
		add(hitStatsButton);

		JLabel missesText = new JLabel(missesIcon);
		add(missesText);

		missedStatsButton = new JButton("0");
		missedStatsButton.setBackground(Constants.bgColor);
		missedStatsButton.setForeground(Constants.fgColor);
		missedStatsButton.setEnabled(false);
		add(missedStatsButton);

		JLabel sunkText = new JLabel(sunkIcon);
		add(sunkText);

		sunkStatsButton = new JButton("0");
		sunkStatsButton.setEnabled(false);
		sunkStatsButton.setBackground(Constants.bgColor);
		sunkStatsButton.setForeground(Constants.fgColor);
		add(sunkStatsButton);
	}

	public void setMissedStats(int missed) {
		missedStatsButton.setText(String.valueOf(missed));
	}

	public void setHitStats(int hit) {
		hitStatsButton.setText(String.valueOf(hit));
	}

	public void setSunkStats(int sunken) {
		sunkStatsButton.setText(String.valueOf(sunken));
	}

}
