/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.obc;

import jmnav.math.Vec3d;

/**
 *
 * @author Jesper
 */
public class ImuData {

    public double dt; // Time since last measurement
    public Vec3d acc; // [m/s]
    public Vec3d ang; // [rad/s]
    public Vec3d mag; // [Gauss]
    public double abs_pressure; // [m] - Altutude
    public double pitot; // [m/s] - Speed
    public GpsData gpsData = null; // Is set if available

    public ImuData(double dt, Vec3d acc, Vec3d ang, Vec3d mag, double abs_pressure, double pitot) {
        this.dt = dt;
        this.acc = acc;
        this.ang = ang;
        this.mag = mag;
        this.abs_pressure = abs_pressure;
        this.pitot = pitot;
    }

    @Override
    public String toString() {
        return String.format("ImuData:\n"
                + "dt = %2.2f \n"
                + "acc= " + acc + "\n"
                + "ang= " + ang + "\n"
                + "mag= " + mag + "\n"
                + "abs_pressure= %2.2f\n"
                + "pitot= %2.2f", dt, abs_pressure, pitot);
    }
}
