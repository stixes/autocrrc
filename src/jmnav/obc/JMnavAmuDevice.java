/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.obc;

import java.io.IOException;

/**
 *
 * @author Jesper
 */
public interface JMnavAmuDevice {

    /**
     * Send a command signal to the Amu.
     * Common mapping (-1..1):
     * 0 = Throttle
     * 1 = Aileron
     * 2 = Elevators
     * 3 = Rudder (If used)
     *
     * @param inputs
     */
    public void sendCommand(CmdData inputs) throws IOException;

}
