/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import jmnav.PlaneState;

/**
 *
 * @author Jesper
 */
public class AltMeter extends Instrument {

    private double maxAlt;
    private double alt = 0;

    public AltMeter() {
        super();
        maxAlt = 100; // meters
        alt = 0;
    }

    public AltMeter(double maxHeight) {
        super();
        this.maxAlt = maxHeight;
    }

    @Override
    public void paint(Graphics g) {
        // Dynamically calculate size information
        Dimension size = getSize();

        int y = (int) (size.height - (size.height * alt / maxAlt)) - 1;

        g.setColor(Color.black);
        g.fillRect(0, 0, size.width, size.height);
        g.setColor(Color.white);
        String msg = "Altitude [m]";
        if (5 + g.getFontMetrics().getStringBounds(msg, g).getWidth() > size.width) {
            msg = "Alt. [m]";
        }
        g.drawString(msg, 5, g.getFontMetrics().getHeight());
        g.drawString(String.format("%3.1f", alt), 5, size.height / 2);
        g.drawLine(0, y, size.width, y);
    }

    @Override
    public void update(PlaneState state) {
        this.alt = state.getAlt();
        if (alt > maxAlt) {
            maxAlt = maxAlt * 10;
        }
        if (alt < maxAlt / 10 && maxAlt > 10) {
            maxAlt = maxAlt / 10;
        }
        this.repaint();
    }
}
