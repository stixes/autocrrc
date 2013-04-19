/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crrc;

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

    public CRRCDataPacket(byte[] buf) {
        byte[] state = Arrays.copyOfRange(buf, 3, 31);
        ShortBuffer sbuf = ByteBuffer.wrap(state).order(ByteOrder.BIG_ENDIAN).asShortBuffer();
        short[] sdata = new short[sbuf.capacity()];
        sbuf.get(sdata);
        double[] fdata = new double[sdata.length];
        for (int i = 0; i < fdata.length; i++) {
            fdata[i] = sdata[i] / 32768.0;
        }
        acc = new Vec3d(fdata[0], fdata[1], fdata[2]);
//        acc.add(new Vec3d(0,0,0.5)); // Compensate for gravity
        acc.mult(2.0 * 9.80); // Convert G to m/s^2
        ang = new Vec3d(fdata[3], fdata[4], fdata[5]);
//        ang.mult(200.0);
        mag = new Vec3d(fdata[6], fdata[7], fdata[8]);
        mag.setLen(1.0);
        alt = 10000.0 * fdata[12];
        spd = 80.0 * fdata[13];

        byte[] gps = Arrays.copyOfRange(buf, 34, 66);
        IntBuffer lbuf = ByteBuffer.wrap(gps).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
        gps_longitude = lbuf.get(3) * 10e-7;
        gps_latitude = lbuf.get(4) * 10e-7;
        gps_alt = lbuf.get(5) / 1000.0;
    }

    public Vec3d getAcc() {
        return acc;
    }

    public double getAlt() {
        return alt;
    }

    public double getAltitude() {
        return gps_alt;
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
        return "test:\n"
                + "Acc:   " + acc + "\n"
                + "Ang:   " + ang + "\n"
                + "Mag:   " + mag + "\n"
                + "Alt:   " + alt + " (gps: " + gps_alt + " )\n"
                + "Speed: " + spd + "\n"
                + "Long:  " + gps_longitude + "\n"
                + "Lat:   " + gps_latitude + "\n";
    }
}
