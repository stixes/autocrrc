/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.obc;

/**
 *
 * @author Jesper
 */
public class CmdData {

    public double aileron;
    public double elevator;
    public double rudder;
    public double throttle;

    public CmdData(double aileron, double elevator, double rudder, double throttle) {
        this.aileron = aileron;
        this.elevator = elevator;
        this.rudder = rudder;
        this.throttle = throttle;
    }
}
