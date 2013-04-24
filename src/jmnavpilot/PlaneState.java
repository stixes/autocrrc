/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

import crrc.CRRCDataPacket;
import crrc.Vec3d;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
    private double compass;
    private double speed;
    private double lastAltD = 0.0;
    private boolean reset = false;
    private Vec3d f_acc = new Vec3d();
    private BufferedWriter outfile;

    public PlaneState() throws IOException {
        outfile = new BufferedWriter(new FileWriter("test.csv"));
        outfile.write("\"Acc_X\",");
        outfile.write("\"Acc_Y\",");
        outfile.write("\"Acc_Z\",");
        outfile.write("\"Ang_X\",");
        outfile.write("\"Ang_Y\",");
        outfile.write("\"Ang_Z\",");
        outfile.write("\"Mag_X\",");
        outfile.write("\"Mag_Y\",");
        outfile.write("\"Mag_Z\",");
        outfile.write("\"Speed\",");
        outfile.write("\"Alt\",");
        outfile.newLine();
        outfile.flush();
        reset();
    }

    public void update(CRRCDataPacket s) throws IOException {

        // Create filtered inputs:
        double a = 0.8; // 0..1 - Lower = harder filter
        Vec3d f_acc_1 = new Vec3d();
        f_acc_1.setX(a * s.getAcc().getX() - (1 - a) * f_acc.getX());
        f_acc_1.setY(a * s.getAcc().getY() - (1 - a) * f_acc.getY());
        f_acc_1.setZ(a * s.getAcc().getZ() - (1 - a) * f_acc.getZ());
        f_acc = f_acc_1;

        // Estimate speed from GPS
        if (sensors != null) {
            double acc = s.getSpd() - sensors.getSpd();
            double d_alt = s.getAlt() - sensors.getAlt();
            double acc_alt = d_alt - lastAltD;
            lastAltD = d_alt;
            double r_x = acc, r_y = 0, r_z = acc_alt;

            // Estimate roll, compensating for acceleration (in XY plane only)
            roll = Math.atan2(-s.getAcc().getY(), s.getAcc().getZ() - acc_alt);
        } else {
            // Estimate roll by assuming Acc points to earth.
            roll = Math.atan2(-s.getAcc().getY(), s.getAcc().getZ());
        }
        roll = Math.atan(-f_acc.getY() / f_acc.getZ());

        // Read speed from preasure
        speed = s.getSpd();
        // Read Altitude from preasure
        alt = s.getAlt();
        // While on the ground, remove simulator noise
        if (alt == 0) {
            roll = 0.0;
            pitch = 0.0;
        }
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
        // Log output
        outfile.write(s.getAcc().getX() + ",");
        outfile.write(s.getAcc().getY() + ",");
        outfile.write(s.getAcc().getZ() + ",");
        outfile.write(s.getAng().getX() + ",");
        outfile.write(s.getAng().getY() + ",");
        outfile.write(s.getAng().getZ() + ",");
        outfile.write(s.getMag().getX() + ",");
        outfile.write(s.getMag().getY() + ",");
        outfile.write(s.getMag().getZ() + ",");
        outfile.write(s.getSpd() + ",");
        outfile.write(s.getAlt() + "");
        outfile.newLine();
        outfile.flush();
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

    @Override
    protected void finalize() throws Throwable {
        outfile.close();
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }

    public double getAlt() {
        return alt;
    }

    public double getCompass() {
        return compass;
    }

    private void reset() {
        reset = true;
        roll = pitch = yaw = alt = 0.0;
    }

    public boolean isReset() {
        return reset;
    }

    @Override
    public String toString() {
        return "PlaneState{" + "alt=" + alt + "roll=" + roll + ", pitch=" + pitch + ", yaw=" + yaw + '}';
    }
}
