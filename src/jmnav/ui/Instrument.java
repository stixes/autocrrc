/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import jmnav.PlaneState;

/**
 *
 * @author Jesper
 */
public class Instrument extends Component {

    @Override
    public void paint(Graphics g) {
        // Dynamically calculate size information
        Dimension size = getSize();
        // diameter
        int d = Math.min(size.width, size.height);
        int x = (size.width - d) / 2;
        int y = (size.height - d) / 2;

        // draw circle (color already set to foreground)
        g.fillOval(x, y, d, d);
        g.setColor(Color.black);
        g.drawOval(x, y, d, d);
    }

    public void update(PlaneState data) {
    }
;
}
