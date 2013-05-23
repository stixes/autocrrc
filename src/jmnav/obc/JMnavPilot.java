/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.obc;

import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.JXInputDevice;
import de.hardcode.jxinput.JXInputManager;
import java.io.IOException;
import jmnav.control.PIDController;
import jmnav.math.Vec2d;
import jmnav.math.Vec3d;

/**
 *
 * @author Jesper
 */
class JMnavPilot implements Runnable {

    // Plane config
    private final double max_roll = 8;
    private final double max_pitch = 8;
    private final double set_speed = 10;
    private final double pitch_ff = 2.2; // r = m*g/2k
    private final JMnavAmu amu;
    private final Axis ax_x, ax_y, ax_t;
    private final JMnavAHRS ahrs;
    private boolean running = true;
    private Vec3d destination;
    private PIDController pid_t; // Throttle controller
    private PIDController pid_a; // Aileron controller
    private PIDController pid_e; // Elevator controller
    private PIDController pid_h; // Altitude controller
    private PIDController pid_c; // Heading controller
    private double c_heading = 0;
    private final JMnavComm comm;

    public JMnavPilot(JMnavAHRS ahrs, JMnavAmu amu) {
        this.amu = amu;
        this.ahrs = ahrs;
        this.comm = new JMnavComm(this);
        JXInputDevice dev = JXInputManager.getJXInputDevice(0);
        ax_x = dev.getAxis(0);
        ax_y = dev.getAxis(1);
        ax_t = dev.getAxis(2);
        pid_t = new PIDController(0.2, 0.01, 0.0);
        pid_a = new PIDController(0.04, 0, 0.1);
        pid_e = new PIDController(0.04, 0, 0.1);
        pid_h = new PIDController(0.3, 0.01, 0.2);
        pid_c = new PIDController(0.05, 0, 0.1);
        comm.destinationReached();
    }

    private CmdData process(Odometry odometry) {
        comm.send(odometry);
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
            pid_t.setRef(set_speed * (1 + t));
            t = pid_t.update(odometry.speed);
        } else {
            t = 1;
        }

        if (odometry.hasGPS) {
            Vec2d location = new Vec2d(odometry.gpsLatitude - destination.x, odometry.gpsLongitude - destination.y);
            if (location.length() < 0.001) {
                comm.destinationReached();
            }
            c_heading = location.angle();
        }
        double c_err = 0;
        if (odometry.altitude > 5) {
            // Heading need to adjust for roll around, so we work on heading error:
            c_err = (odometry.compass - c_heading) * 180 / Math.PI;
            if (c_err > 180) {
                c_err -= 360;
            } else if (c_err < -180) {
                c_err += 360;
            }
            double c = pid_c.update(c_err);
            pid_a.setRef(c * max_roll);
            a = pid_a.update(odometry.roll * 180 / Math.PI);

            // Altitude adjustment
            pid_h.setRef(destination.z);
            double h = pid_h.update(odometry.altitude);
//            e = 0.2 * h;
            pid_e.setRef(-max_pitch * h);
            e = pid_e.update(odometry.pitch * 180 / Math.PI);
            // Compensatre for roll
            e -= pitch_ff * odometry.roll * odometry.roll;
        } else {
            // Takeoff
            pid_e.setRef(-max_pitch);
            e = pid_e.update(odometry.pitch * 180 / Math.PI);
        }

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
        System.out.println("JMnav pilot Assuming controls..");
        while (running) {
            try {
                amu.sendCommand(process(ahrs.getOdometry()));
            } catch (IOException ex) {
                System.exit(1);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        running = false;
    }

    void setDestination(Vec3d waypoint) {
        destination = waypoint;
        System.out.println("New WP: " + destination);
    }
}
