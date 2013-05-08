/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.obc;

import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.JXInputDevice;
import de.hardcode.jxinput.JXInputManager;
import jmnav.control.PIDController;

/**
 *
 * @author Jesper
 */
class JMnavPilot implements Runnable {

    private final JMnavAmu amu;
    private final Axis ax_x, ax_y, ax_t;
    private final JMnavAHRS ahrs;
    private boolean running = true;
    private PIDController pid_t;
    private PIDController pid_a;
    private PIDController pid_e;
    private PIDController pid_h;

    public JMnavPilot(JMnavAHRS ahrs, JMnavAmu amu) {
        this.amu = amu;
        this.ahrs = ahrs;
        JXInputDevice dev = JXInputManager.getJXInputDevice(0);
        ax_x = dev.getAxis(0);
        ax_y = dev.getAxis(1);
        ax_t = dev.getAxis(2);
        pid_t = new PIDController(0.15, 0.3, 0);
        pid_a = new PIDController(0.04, 0, 0.1);
        pid_e = new PIDController(0.04, 0, 0.1);
        pid_h = new PIDController(0.3, 0.01, 0.2);
    }

    private CmdData process(Odometry odometry) {
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

        // Throttle PI
        if (t > 0.1 || odometry.altitude > 5) {
            pid_t.setRef(15 * (1 + t));
            t = pid_t.update(odometry.speed);
        } else {
            t = -1;
        }

        pid_a.setRef(a * 8);
        if (odometry.altitude > 5) {
            a = pid_a.update(odometry.roll * 180 / Math.PI);
        }

        if (odometry.altitude > 5) {
            pid_h.setRef(25 + e * 10);
            double h = pid_h.update(odometry.altitude);
            pid_e.setRef(-15 * h);
            e = pid_e.update(odometry.pitch * 180 / Math.PI);
            // Compensatre for roll
            double r = 5; // r = m*g/2k
            e -= r * odometry.roll * odometry.roll;
        }

//        System.out.println(String.format(
//                "Autopilot:\n"
//                + "R: %2.3f\n"
//                + "P: %2.3f\n"
//                + "H: %2.3f", odometry.roll * 180 / Math.PI, odometry.pitch * 180 / Math.PI, odometry.altitude));
        CmdData cmd = new CmdData(
                a,// Aileron
                e,// Elevator
                0, // Unused
                t // Throttle
                );

        return cmd;
    }

    /**
     * Using Runnable allows for better threaded operation.
     */
    @Override
    public void run() {
        while (running) {
            amu.sendCommand(process(ahrs.getOdometry()));
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        running = false;
    }
}
