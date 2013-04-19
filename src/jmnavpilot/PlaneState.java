/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

import crrc.CRRCDataPacket;

/**
 *
 * @author Jesper
 */
public class PlaneState {

    private CRRCDataPacket sensors = null;
    private double roll = 0;
    private double pitch = 0;
    private double yaw = 0;
    private double alt = 0;
    private final double dt;

    public PlaneState(double dt) {
        this.dt = dt;
        reset();
    }

    public void update(CRRCDataPacket s) {
        roll += s.getAng().getX() * dt;
        pitch += s.getAng().getY() * dt;
        yaw += s.getAng().getZ() * dt;
        alt = s.getAlt();
        sensors = s;
        // Resetting state on chrash or reset
        if (s.getAlt() + s.getSpd() == 0) {
            reset();
        }
    }

    public double getPitch() {
        return pitch;
    }

    public CRRCDataPacket getSensors() {
        return sensors;
    }

    public double getRoll() {
        return roll;
    }

    public double getYaw() {
        return yaw;
    }

    public double getAlt() {
        return alt;
    }

    private void reset() {
        roll = pitch = yaw = alt = 0.0;
    }

    @Override
    public String toString() {
        return "PlaneState{" + "alt=" + alt + "roll=" + roll + ", pitch=" + pitch + ", yaw=" + yaw + '}';
    }
}
