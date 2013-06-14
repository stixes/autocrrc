/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import jmnav.obc.Odometry;

/**
 *
 * @author Jesper
 */
public class Horizon extends Instrument {

    private double roll = 0;
    private double pitch;

    public Horizon() {
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        // Dynamically calculate size information
        Dimension size = getSize();
        // diameter
        int d = size.height / 2;
        int rOffset = (int) (Math.tan(roll) * d);
        int pOffset = (int) (Math.tan(pitch) * d);
        int y1 = (int) (d - pOffset + rOffset);
        int y2 = (int) (d - pOffset - rOffset);

        // draw circle (color already set to foreground)
        g.setColor(Color.cyan);
        Polygon upper = new Polygon();
        upper.addPoint(0, 0);
        upper.addPoint(size.width - 1, 0);
        upper.addPoint(size.width - 1, y2);
        upper.addPoint(0, y1);
        g.fillPolygon(upper);
        g.setColor(Color.green);
        Polygon lower = new Polygon();
        lower.addPoint(0, size.height - 1);
        lower.addPoint(size.width - 1, size.height - 1);
        lower.addPoint(size.width - 1, y2);
        lower.addPoint(0, y1);
        g.fillPolygon(lower);
        g.setColor(Color.darkGray);
        g.drawLine(0, y1, size.width - 1, y2);

        // Helper lines
        g.setColor(Color.lightGray);
        int x = size.width / 10;
        for (int i = -30; i < 40; i += 10) {
            int offset = (int) (Math.tan(i * Math.PI / 180) * d);
            g.drawLine(size.width / 2 - x, size.height / 2 + offset, size.width / 2 + x, size.height / 2 + offset);

        }
        for (int i = -35; i < 40; i += 5) {
            int offset = (int) (Math.tan(i * Math.PI / 180) * d);
            g.drawLine(size.width / 2 - x / 4, size.height / 2 + offset, size.width / 2 + x / 4, size.height / 2 + offset);

        }
        g.setColor(Color.black);
        g.drawLine(0, size.height / 2, size.width - 1, size.height / 2);

//        g.drawLine(0, y1-1, size.width-1, y2-1);
        g.drawRect(0, 0, size.width - 1, size.height - 1);



//        g.fillOval(x, y, d, d);
//        g.setColor(Color.green);
//        g.fillArc(x, y, d, d, (int) (180 + roll), 180);
        g.setColor(Color.white);
        String msg = String.format("R: %3.1f°", roll * 180 / Math.PI);
        int txtOffset = (int) (g.getFontMetrics().getStringBounds(msg, g).getWidth() / 2);
        g.drawString(msg, size.width / 2 - txtOffset, 15);
        msg = String.format("P: %3.1f°", pitch * 180 / Math.PI);
        txtOffset = (int) (g.getFontMetrics().getStringBounds(msg, g).getWidth() / 2);
        g.drawString(msg, size.width / 2 - txtOffset, size.height - 5);

    }

    @Override
    public void update(Odometry data) {
        roll = data.roll;
        pitch = data.pitch;
        this.repaint();
    }
}
