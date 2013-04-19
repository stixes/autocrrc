/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

/**
 *
 * @author Jesper
 */
public interface Pilot {
    
    public void update(PlaneState ps);
    public double[] getControls();
    
}
