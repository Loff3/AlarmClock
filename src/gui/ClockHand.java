package gui;

import java.awt.Color;
import java.awt.Graphics;

public class ClockHand {
    private double lengthRatio;
    private Color color;
    private double angle;

    public ClockHand(double lengthRatio, Color color) {
        this.lengthRatio = lengthRatio;
        this.color = color;
        this.angle = 0;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void draw(Graphics g, int xCenter, int yCenter, int radius) {
        int length = (int) (radius * lengthRatio);
        int xEnd = xCenter + (int) (length * Math.sin(angle));
        int yEnd = yCenter - (int) (length * Math.cos(angle));

        g.setColor(color);
        g.drawLine(xCenter, yCenter, xEnd, yEnd);
    }
}
