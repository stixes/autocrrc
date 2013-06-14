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
public class AoAMeter extends Instrument {

    private double aoa = 0;

    public AoAMeter() {
        super();
    }

    public AoAMeter(double maxHeight) {
        super();
    }

    @Override
    public void paint(Graphics g) {
        // Dynamically calculate size information
        Dimension size = getSize();

        int y = (int) (size.height/2 - (size.height * aoa / 90)) - 1;

        g.setColor(Color.black);
        g.fillRect(0, 0, size.width, size.height);
        g.setColor(Color.white);
        String msg = "Angle";
        if (5 + g.getFontMetrics().getStringBounds(msg, g).getWidth() > size.width) {
            msg = "AoA";
        }
        g.drawString(msg, 5, g.getFontMetrics().getHeight());
        g.drawString(String.format("%3.1f", aoa), 5, size.height / 2);
        g.drawLine(0, y, size.width, y);
    }

    @Override
    public void update(Odometry state) {
        this.aoa = state.pitch * 180 / Math.PI;
        this.repaint();
    }
}
