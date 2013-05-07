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
public class Horizon extends Instrument {

    private double roll = 0;

    public Horizon() {
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        // Dynamically calculate size information
        Dimension size = getSize();
        // diameter
        int d = Math.min(size.width, size.height);
        int x = (size.width - d) / 2;
        int y = (size.height - d) / 2;

        // draw circle (color already set to foreground)
        g.setColor(Color.blue);
        g.fillOval(x, y, d, d);
        g.setColor(Color.green);
        g.fillArc(x, y, d, d, (int) (180 + roll * 180 / Math.PI), 180);
        g.setColor(Color.white);
        g.drawString(String.format("%3.1f", roll * 180 / Math.PI), size.width / 2, size.height / 2);

    }

    @Override
    public void update(PlaneState data) {
        roll = data.getRoll();
        this.repaint();
    }
}
