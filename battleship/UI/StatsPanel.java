package battleship.UI;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StatsPanel extends JPanel {
	JButton missedStatsButton;
	JButton hitStatsButton;
	JButton sunkStatsButton;

	public StatsPanel() {
		setLayout(new GridLayout(0, 2));
		JButton hitText = new JButton("Total hits");
		hitText.setEnabled(false);
		add(hitText);

		hitStatsButton = new JButton("0");
		hitStatsButton.setEnabled(false);
		add(hitStatsButton);

		JButton missedText = new JButton("Total missed");
		missedText.setEnabled(false);
		add(missedText);

		missedStatsButton = new JButton("0");
		missedStatsButton.setEnabled(false);
		add(missedStatsButton);

		JButton sunkStatsText = new JButton("Total sunken");
		sunkStatsText.setEnabled(false);
		add(sunkStatsText);

		sunkStatsButton = new JButton("0");
		sunkStatsButton.setEnabled(false);
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
