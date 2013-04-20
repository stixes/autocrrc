/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import jmnavpilot.PlaneState;

/**
 *
 * @author Jesper
 */
public class AltMeter extends Instrument {

    private final double maxAlt;
    private double alt = 0;

    public AltMeter() {
        super();
        maxAlt = 100; // meters
        alt = 10;
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

        // draw circle (color already set to foreground)
        g.setColor(Color.black);
        g.drawLine(0, y, size.width, y);
    }

    @Override
    public void update(PlaneState state) {
        this.alt = state.getAlt();
    }
}
