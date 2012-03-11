/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

/**
 *
 * @author Jesper
 */
public class PlaneState {

    private MNAVState sensors = null;
    private float roll = 0;
    private float pitch = 0;
    private float yaw = 0;
    private float alt = 0;
    private final float dt;

    public PlaneState(float dt) {
        this.dt = dt;
        reset();
    }

    public void update(MNAVState s) {
        roll += s.getxRate() * dt;
        pitch += s.getyRate() * dt;
        yaw += s.getzRate() * dt;
        alt = s.getAbsPress();
        sensors = s;
        // Resetting state on chrash or reset
        if (s.getAbsPress() + s.getPitotPress() == 0) {
            reset();
        }
    }

    public float getPitch() {
        return pitch;
    }

    public MNAVState getSensors() {
        return sensors;
    }

    public float getRoll() {
        return roll;
    }

    public float getYaw() {
        return yaw;
    }

    public float getAlt() {
        return alt;
    }

    private void reset() {
        roll = pitch = yaw = alt = 0;
    }

    @Override
    public String toString() {
        return "PlaneState{" + "alt=" + alt + "roll=" + roll + ", pitch=" + pitch + ", yaw=" + yaw + '}';
    }
}
