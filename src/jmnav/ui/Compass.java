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
public class Compass extends Instrument {

    private double compass = 0;

    public Compass() {
    }

    @Override
    public void paint(Graphics g) {
        // Dynamically calculate size information
        Dimension size = getSize();
        // diameter
        int d = Math.min(size.width, size.height);
        int x = (size.width - d) / 2;
        int y = (size.height - d) / 2;

        // draw circle (color already set to foreground)
        g.setColor(Color.black);
        g.fillOval(x, y, d, d);
        g.drawOval(x, y, d, d);

        x = size.width / 2;
        y = size.height / 2;
        g.setColor(Color.white);
        g.drawLine(x, y, x + (int) (Math.sin(compass) * d), y + (int) (Math.cos(compass) * d));

    }

    @Override
    public void update(PlaneState data) {
        compass = data.getCompass();
        this.repaint();
    }
}
