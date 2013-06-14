/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import jmnav.obc.Odometry;

/**
 *
 * @author Jesper
 */
public class SpdMeter extends Instrument {

    private double maxSpd;
    private double spd = 0;

    public SpdMeter() {
        super();
        maxSpd = 50; // meters
        spd = 0;
    }

    public SpdMeter(double maxHeight) {
        super();
        this.maxSpd = maxHeight;
    }

    @Override
    public void paint(Graphics g) {
        // Dynamically calculate size information
        Dimension size = getSize();

        int y = (int) (size.height - (size.height * spd / maxSpd)) - 1;

        g.setColor(Color.black);
        g.fillRect(0, 0, size.width, size.height);
        g.setColor(Color.white);
        String msg = "Speed [m/s]";
        if (5 + g.getFontMetrics().getStringBounds(msg, g).getWidth() > size.width) {
            msg = "Spd. [m/s]";
        }
        g.drawString(msg, 5, g.getFontMetrics().getHeight());
        g.drawString(String.format("%3.1f", spd), 5, size.height / 2);
        g.drawLine(0, y, size.width, y);
    }

    @Override
    public void update(Odometry state) {
        this.spd = state.speed;
        this.repaint();
    }
}
