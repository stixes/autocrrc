/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav;

/**
 *
 * @author Jesper
 */
public interface Pilot {
    
    public void update(PlaneState ps);
    public double[] getControls();
    
}
