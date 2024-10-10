package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import time.TimeType;

public class AnalogClockPanel extends JPanel {
    private ClockHand hourPointer;
    private ClockHand minutePointer;
    private ClockHand secondPointer;

    public AnalogClockPanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.black);

        hourPointer = new ClockHand(0.5, Color.white);
        minutePointer = new ClockHand(0.75, Color.white);
        secondPointer = new ClockHand(0.9, Color.red);
    }

    public void updateTime(TimeType currentTime) {
        int hours = currentTime.getHour();
        int minutes = currentTime.getMinute();
        int seconds = currentTime.getSecond();

        double secondAngle = Math.toRadians((seconds / 60.0) * 360);
        double minuteAngle = Math.toRadians(((minutes + seconds / 60.0) / 60.0) * 360);
        double hourAngle = Math.toRadians(((hours % 12 + minutes / 60.0) / 12.0) * 360);

        secondPointer.setAngle(secondAngle);
        minutePointer.setAngle(minuteAngle);
        hourPointer.setAngle(hourAngle);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int xCenter = width / 2;
        int yCenter = height / 2;
        int radius = Math.min(width, height) / 2 - 20; // Subtract padding

        g.setColor(Color.GRAY);
        g.drawOval(xCenter - radius, yCenter - radius, 2 * radius, 2 * radius);

        drawMinuteMarkers(g, xCenter, yCenter, radius);

        drawHourMarkers(g, xCenter, yCenter, radius);

        hourPointer.draw(g, xCenter, yCenter, radius);
        minutePointer.draw(g, xCenter, yCenter, radius);
        secondPointer.draw(g, xCenter, yCenter, radius);
    }

    private void drawHourMarkers(Graphics g, int xCenter, int yCenter, int radius) {
        g.setColor(Color.white);
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians(i * 30 - 90);
            int xStart = xCenter + (int) ((radius - 15) * Math.cos(angle));
            int yStart = yCenter + (int) ((radius - 15) * Math.sin(angle));
            int xEnd = xCenter + (int) (radius * Math.cos(angle));
            int yEnd = yCenter + (int) (radius * Math.sin(angle));
            g.drawLine(xStart, yStart, xEnd, yEnd);
        }
    }

    private void drawMinuteMarkers(Graphics g, int xCenter, int yCenter, int radius) {
        g.setColor(Color.GRAY);
        for (int i = 0; i < 60; i++) {
            if (i % 5 != 0) {
                double angle = Math.toRadians(i * 6 - 90);
                int xStart = xCenter + (int) ((radius - 10) * Math.cos(angle));
                int yStart = yCenter + (int) ((radius - 10) * Math.sin(angle));
                int xEnd = xCenter + (int) (radius * Math.cos(angle));
                int yEnd = yCenter + (int) (radius * Math.sin(angle));
                g.drawLine(xStart, yStart, xEnd, yEnd);
            }
        }
    }
}
