/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.obc;

import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.JXInputDevice;
import de.hardcode.jxinput.JXInputManager;

/**
 *
 * @author Jesper
 */
class JMnavPilot {

    private JMnavAmu amu;
    private final Axis ax_x, ax_y, ax_t;

    public JMnavPilot(JMnavAmu amu) {
        this.amu = amu;
        JXInputDevice dev = JXInputManager.getJXInputDevice(0);
        ax_x = dev.getAxis(0);
        ax_y = dev.getAxis(1);
        ax_t = dev.getAxis(2);
    }

    void process(Odometry odometry) {
        JXInputManager.updateFeatures();
        double e = -ax_y.getValue();
        if (Math.abs(e) < 0.1) {
            e = 0.0;
        }
        double a = ax_x.getValue();
        if (Math.abs(a) < 0.1) {
            a = 0;
        }
        double t = ax_t.getValue();


        double[] cmd = new double[3];
        cmd[0] = t; // Throttle
        cmd[1] = a; // Elevator
        cmd[2] = e; // Aileron

        amu.sendCommand(cmd);
    }
}
