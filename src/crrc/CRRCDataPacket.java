/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crrc;

import jmnav.math.Vec3d;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

/**
 *
 * @author Jesper
 */
public class CRRCDataPacket {

    private Vec3d acc;
    private Vec3d ang;
    private Vec3d mag;
    private double alt;
    private double spd;
    private double gps_longitude;
    private double gps_latitude;
    private double gps_alt;
    private double gps_speed;
    private final double gps_compass;
    private final double gps_speed_N;
    private final double gps_speed_E;
    private final double gps_speed_V;

    public CRRCDataPacket(byte[] buf) {
        byte[] state = Arrays.copyOfRange(buf, 3, 31);
        ShortBuffer sbuf = ByteBuffer.wrap(state).order(ByteOrder.BIG_ENDIAN).asShortBuffer();
        short[] sdata = new short[sbuf.capacity()];
        sbuf.get(sdata);
        double[] fdata = new double[sdata.length];
        for (int i = 0; i < fdata.length; i++) {
            fdata[i] = sdata[i] / 32768.0;
        }
        acc = new Vec3d(fdata[0], fdata[1], fdata[2]); // G/s
//        acc.mult(2.0 * 9.80); // Convert G to m/s^2
        ang = new Vec3d(fdata[3], fdata[4], fdata[5]);
        ang.mult(200.0 * Math.PI / 180); // rads/s
        mag = new Vec3d(fdata[6], fdata[7], fdata[8]).newLength(1.0);
        alt = 10000.0 * fdata[12]; // m/s
        spd = 80.0 * fdata[13];  // m/s

        byte[] gps = Arrays.copyOfRange(buf, 34, 66);
        IntBuffer lbuf = ByteBuffer.wrap(gps).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
        gps_longitude = lbuf.get(3) * 10e-7;
        gps_latitude = lbuf.get(4) * 10e-7;
        gps_alt = lbuf.get(5) / 1000.0; // [mm] -> [m]
        gps_speed_N = lbuf.get(0) / 100.0; // [cm/s] -> [m/s]
        gps_speed_E = lbuf.get(1) / 100.0; // [cm/s] -> [m/s]
        gps_speed_V = lbuf.get(2) / 100.0; // [cm/s] -> [m/s]
        gps_speed = Math.sqrt(gps_speed_V * gps_speed_V
                + gps_speed_N * gps_speed_N
                + gps_speed_E * gps_speed_E); // [m/s]
        gps_compass = Math.atan2(lbuf.get(1), lbuf.get(0)); // [rad]
    }

    public double getSpeedV() {
        return gps_speed_V;
    }

    public double getSpeedN() {
        return gps_speed_N;
    }

    public double getSpeedE() {
        return gps_speed_E;
    }

    public double getCompassGps() {
        return gps_compass;
    }

    public Vec3d getAcc() {
        return acc;
    }

    public double getAlt() {
        return alt;
    }

    public double getAltitudeGps() {
        return gps_alt;
    }

    public double getSpeedGps() {
        return gps_speed;
    }

    public Vec3d getAng() {
        return ang;
    }

    public double getLatitude() {
        return gps_latitude;
    }

    public double getLongitude() {
        return gps_longitude;
    }

    public Vec3d getMag() {
        return mag;
    }

    public double getSpd() {
        return spd;
    }

    @Override
    public String toString() {
        return String.format(
                "Acc:   " + acc + "\n"
                + "Ang:   " + ang + "\n"
                + "Mag:   " + mag + "\n"
                + "Alt:   %2.2f (gps: %2.2f )\n"
                + "Speed: %2.2f\n"
                + "Long:  %1.6f\n"
                + "Lat:   %1.6f\n", alt, gps_alt, spd, gps_longitude, gps_latitude);
    }
}
