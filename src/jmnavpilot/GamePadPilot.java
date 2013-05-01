/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

import jmnavpilot.control.PIDController;
import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.JXInputDevice;
import de.hardcode.jxinput.JXInputManager;

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
    private final PIDController pid_e;
    private double roll;

    public GamePadPilot() {
        JXInputDevice dev = JXInputManager.getJXInputDevice(0);
        ax_x = dev.getAxis(0);
        ax_y = dev.getAxis(1);
        ax_t = dev.getAxis(2);
        pid_t = new PIDController(5, 0.5, 1, 15, 0, 5);
        pid_e = new PIDController(0.5, 0.01, 2);
    }

    @Override
    public void update(PlaneState ps) {
        if (ps.isReset()) {
            pid_t.reset();
            pid_e.reset();
        }
        speed = ps.getSpeed();
        roll = ps.getSensors().getAcc().getY();
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
//        a = - 0.5 * pid_e.update(roll);

//        t= -1; // Debug

//        System.out.println(String.format("roll: %3.2f  A: %2.2f", roll, a));
//        System.out.println(String.format("speed: %3.2f  T: %2.2f", speed, t));

        double[] result = {
            e + a,
            e - a,
            t
        };

        return result;
    }
}
