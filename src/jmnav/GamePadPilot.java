/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav;

import jmnav.control.PIDController;
import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.JXInputDevice;
import de.hardcode.jxinput.JXInputManager;
import jmnav.control.AngleEstimat;

/**
 *
 * @author Jesper
 */
public class GamePadPilot implements Pilot {

    private final Axis ax_x;
    private final Axis ax_y;
    private final Axis ax_t;
    private final PIDController pid_t;
    private double speed;
    private final PIDController pid_a;
    private double roll;
    private double altitude;
    private final PIDController pid_e;
    private double pitch;
    private final PIDController pid_h;

    public GamePadPilot() {
        JXInputDevice dev = JXInputManager.getJXInputDevice(0);
        ax_x = dev.getAxis(0);
        ax_y = dev.getAxis(1);
        ax_t = dev.getAxis(2);
        pid_t = new PIDController(0.15, 0.3, 0);
        pid_a = new PIDController(0.04, 0, 0.1);
        pid_e = new PIDController(0.04, 0, 0.1);
        pid_h = new PIDController(0.3, 0.01, 0.2);
    }

    @Override
    public void update(PlaneState ps) {
        if (ps.isReset()) {
            pid_t.reset();
            pid_a.reset();
            pid_e.reset();
        }
        speed = ps.getSpeed();
        roll = ps.getRoll();
        pitch = ps.getPitch();
        altitude = ps.getAlt();
    }

    @Override
    public double[] getControls() {
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

        if (Math.abs(t) < 0.2) {
            t = 0.2 * pid_t.update(speed);
        } else {
            t = 0.5 + t * 0.5;
        }
        if (t > 0.1 || altitude > 5) {
            pid_t.setRef(10 * (1 + t));
            t = pid_t.update(speed);
            t = (t + 1) / 2;
        } else {
            t = 0;
        }

        pid_a.setRef(a * 8);
        if (altitude > 5) {
            a = pid_a.update(roll * 180 / Math.PI);
        } else {
//            pid_a.update(roll * 180 / Math.PI);
        }

        if (altitude > 5) {
            pid_h.setRef(25 + e * 10);
            double h = pid_h.update(altitude);
            pid_e.setRef(-15 * h);
            e = pid_e.update(pitch * 180 / Math.PI);
            // Compensatre for roll
            double r = 4; // r = m*g/2k
            e -= r * roll * roll;
        }


//        t= -1; // Debug

//        System.out.println(String.format("roll: %3.2f  A: %2.2f", roll, a));
//        System.out.println(String.format("speed: %3.2f  T: %2.2f", speed, t));

        double[] result = {
            e + a,
            e - a,
            t
        };

//        System.out.println(String.format("t: %1.3f",t));
//        System.out.println(String.format("e: %1.3f", e));
//        System.out.println(String.format("h: %1.3f", h));

        return result;
    }
}
