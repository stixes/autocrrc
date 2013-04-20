/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

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

    public GamePadPilot() {
        JXInputDevice dev = JXInputManager.getJXInputDevice(0);
        ax_x = dev.getAxis(0);
        System.out.println("Assigned " + ax_x.getName() + " to Aileron.");
        ax_y = dev.getAxis(1);
        System.out.println("Assigned " + ax_y.getName() + " to Elevron.");
        ax_t = dev.getAxis(2);
        System.out.println("Assigned " + ax_t.getName() + " to Throttle.");
    }

    @Override
    public void update(PlaneState ps) {
    }

    @Override
    public double[] getControls() {
        JXInputManager.updateFeatures();

        double e = -ax_y.getValue();
        if (Math.abs(e) < 0.1) {
            e = 0;
        }
        double a = ax_x.getValue();
        if (Math.abs(a) < 0.1) {
            a = 0;
        }
        double t = ax_t.getValue();

        double[] result = {
            e + a,
            e - a,
            0.5 + t / 2
        };

        return result;
    }
}
