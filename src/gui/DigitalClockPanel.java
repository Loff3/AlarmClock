package gui;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import time.TimeType;

public class DigitalClockPanel extends JPanel {

    private JLabel timeLabel;

    public DigitalClockPanel() {

        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.black);

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        timeLabel.setForeground(Color.GRAY);
        add(timeLabel, BorderLayout.CENTER);

    }

    public void updateTime(TimeType currentTime) {
        SwingUtilities.invokeLater(() -> timeLabel.setText(currentTime.toString()));
    }

}