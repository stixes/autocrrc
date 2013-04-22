/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

import crrc.CRRCDataPacket;
import crrc.Vec3d;

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
    private double compass;
    private double speed;
    private double lastAltD = 0.0;

    public PlaneState(double dt) {
        this.dt = dt;
        reset();
    }

    public void update(CRRCDataPacket s) {

        pitch += s.getAng().getY() * dt;
        yaw += s.getAng().getZ() * dt;

        // Estimate speed from GPS
        if (sensors != null) {
            double acc = s.getSpd() - sensors.getSpd();
            double d_alt = s.getAlt() - sensors.getAlt();
            double acc_alt = d_alt - lastAltD;
            lastAltD = d_alt;
            Vec3d a_est = new Vec3d(-acc, 0, 9.80 - acc_alt);
            a_est.add(s.getAcc());
            System.out.println("acc: "+a_est);
            // Estimate roll, compensating for acceleration (in XY plane only)
            roll = Math.atan2(-s.getAcc().getY(), s.getAcc().getZ());
        } else {
            // Estimate roll by assuming Acc points to earth.
            roll = Math.atan2(-s.getAcc().getY(), s.getAcc().getZ());
        }

        // Read speed from preasure
        speed = s.getSpd();
        // Read Altitude from preasure
        alt = s.getAlt();
        // Store last sensor data.
        sensors = s;
        // Resetting state on chrash or reset
        if (s.getAlt() + s.getSpd() == 0) {
            reset();
        }
        // Compas direction by x0y clamping yaw.
        // X = S 1..-1 N
        // Y = W 1..-1 E
        // Z = Up 1..-1 Down
        compass = Math.atan2(-s.getMag().getY(), -s.getMag().getX()) + Math.PI;
    }

    public double getSpeed() {
        return speed;
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

    public double getCompass() {
        return compass;
    }

    private void reset() {
        roll = pitch = yaw = alt = 0.0;
    }

    @Override
    public String toString() {
        return "PlaneState{" + "alt=" + alt + "roll=" + roll + ", pitch=" + pitch + ", yaw=" + yaw + '}';
    }
}
