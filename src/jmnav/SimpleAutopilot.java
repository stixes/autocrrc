/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav;

/**
 *
 * @author Jesper
 */
public class SimpleAutopilot implements Pilot {

    private PlaneState cs;

    @Override
    public void update(PlaneState ps) {
        cs = ps;
    }

    @Override
    public double[] getControls() {

        // P control Roll stabilizer. K = 0.5
//        double roll = -0.3f * cs.getRoll() - 0.75f * cs.getSensors().getzRate();
//        double roll = -0.25f * cs.getRoll() - 0.125f * cs.getYaw();
        double roll = -0.2f * cs.getRoll();
//        double roll = 0.2f * cs.getSensors().getyAccel(); // Roll ??
        roll = Math.min(1, Math.max(-1, roll));

        // Hard takeoff, level flight alt/thrust
        double pitch = -0.0f;
        double thrust = -0.07f * (cs.getAlt() - 25);
        thrust = Math.min(0.77f, Math.max(0, 0.5f + thrust / 2));
//        if (cs.getAlt() < 1) {
//            pitch = -1f;
//            thrust = 1f;
//        }
        double[] result = {roll, pitch, thrust};
        return result;
    }
}
