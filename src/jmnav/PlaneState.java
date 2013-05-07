/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav;

import crrc.CRRCDataPacket;
import jmnav.math.Vec3d;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import jmnav.control.AngleEstimat;
import jmnav.control.IEIntegrator;
import jmnav.control.KalmanGyro;

/**
 *
 * @author Jesper
 */
public class PlaneState {

    private CRRCDataPacket sensors = null, sensors_1 = null;
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
    private final AngleEstimat est_roll;
    private final AngleEstimat est_pitch;
    private final IEIntegrator int_roll;
    private KalmanGyro roll_est = new KalmanGyro();
    private final IEIntegrator int_pitch;

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
        outfile.write("\"GPSSpeed\",");
        outfile.write("\"Alt\",");
        outfile.write("\"GPSAlt\",");
        outfile.newLine();
        outfile.flush();
        est_roll = new AngleEstimat();
        est_pitch = new AngleEstimat();
        int_roll = new IEIntegrator(0.01);
        int_pitch = new IEIntegrator(0.01);
        reset();
    }

    public void update(CRRCDataPacket s) throws IOException {

//        // Create filtered inputs:
        double alpha = 1; // 0..1 - Lower = harder filter
        Vec3d f_acc_1 = new Vec3d();
        f_acc_1.setX(alpha * s.getAcc().getX() - (1 - alpha) * f_acc.getX());
        f_acc_1.setY(alpha * s.getAcc().getY() - (1 - alpha) * f_acc.getY());
        f_acc_1.setZ(alpha * s.getAcc().getZ() - (1 - alpha) * f_acc.getZ());
        f_acc = f_acc_1;

//        // Simple compensated acceleration vector
//        Vec3d acc, ang, g = new Vec3d();
//        double accel = 0;
//        if (sensors != null) {
//            accel = 2.5 * (s.getSpd() - sensors.getSpd());
//            acc = s.getAcc().add(sensors.getAcc()).mult(0.5);
//            ang = s.getAng().add(sensors.getAng()).mult(0.5);
//            g.x = acc.x - accel;
//            g.y = acc.y - 0.075 * ang.z * s.getSpd();
//            g.z = acc.z + 0.075 * ang.y * s.getSpd();
//            double[] z = new double[2];
//            z[0] = Math.atan(g.y / g.z);
//            z[1] = est_pitch.update(z).getX();
//            roll = est_pitch.update(z).x;
//            pitch = Math.atan(g.x / g.z);
//            //System.out.println(String.format("gx: %3.3f acc: %3.3f", acc.x/accel, accel));
//        } else {
//            roll = 0;
//        }

//        z[0] = s.getAng().getX();
//        z[1] = -Math.atan2(s.getAcc().getZ() , s.getAcc().getY()) - Math.PI/2;
//        z[0] = roll;
//        z[1] = s.getAng().getX();
//        //z[0] = Math.atan2(-s.getAcc().getY(), -s.getAcc().getZ());
//
////        roll = z[0];
////        roll = int_roll.update(z[1]);
////        roll = a * roll + (1-a)*(-Math.atan2(s.getAcc().getZ() , s.getAcc().getY()) - Math.PI/2);
//        z[0] = s.getAng().getY();
//        z[1] = -Math.atan2(s.getAcc().getZ(), s.getAcc().getX()) - Math.PI / 2;
//        pitch = est_pitch.update(z).getX();
//        pitch = -Math.atan2(s.getAcc().getZ() , s.getAcc().getX()) - Math.PI/2;

//        roll = roll_est.update(s.getAng().x);
//        if (Math.abs(s.getAcc().y)<0.01 && Math.abs(s.getMag().z)<0.1) {
//            int_roll.reset();
//        }
//        roll = int_roll.update(s.getAng().x);

        // Read speed from preasure
        speed = s.getSpd();
        // Read Altitude from preasure
        alt = s.getAlt();
        // While on the ground, remove simulator noise
        if (alt == 0) {
//            roll = 0.0;
//            pitch = 0.0;
        }

        // Compas direction by x0y clamping yaw.
        // X = S 1..-1 N
        // Y = W 1..-1 E
        // Z = Up 1..-1 Down
        compass = Math.atan2(s.getMag().getY(), -s.getMag().getX()) + Math.PI;

        roll = Math.asin(s.getAng().z * speed / 9.81);
        if (sensors != null) {
            pitch = Math.asin(s.getSpeedV() / speed);
        }
//        pitch = Math.asin(s.getAng().x * speed / 9.81);
//        pitch = int_pitch.update(s.getAng().y);







//        // Implement acceleration compensation as per.
//        // https://docs.google.com/viewer?a=v&q=cache:8UnwCrM2iiQJ:cortex-ap.googlecode.com/svn/trunk/Doc/Real-Time%2520Attitude%2520Position%2520Estimation.pdf+&hl=da&gl=dk&pid=bl&srcid=ADGEEShFuNt7mXYrVHDKONgaAo6onu2WTi8YfWq4M3DdFBelyrFySL2NwM-16lINxVGuS_ManelZrZpF-XI22husHkC46uHylrwgNicDN3DDon7bj0g-SRgyn3iUUup5f3-iRdSXNNjV&sig=AHIEtbT1jxyhlmJX2-cNgtlAAd2DA5zYJg
//
//        // Needs previous state
//        double sigma_r, sigma_p, dt = 0.01;
//        Vec3d a_gps = new Vec3d(), r = new Vec3d(), a = new Vec3d();
//        if (sensors != null && sensors_1 != null) {
//            // Estimat GPS acceleration
//            a_gps.y = ((s.getSpeedN() - sensors.getSpeedN())
//                    + (sensors.getSpeedN() - sensors_1.getSpeedN())) / 2;
//            a_gps.x = ((s.getSpeedE() - sensors.getSpeedE())
//                    + (sensors.getSpeedE() - sensors_1.getSpeedE())) / 2;
//            a_gps.z = (s.getAltitudeGps() - sensors.getAltitudeGps()
//                    - (sensors.getAltitudeGps() - sensors_1.getAltitudeGps()));
//            // Average body accelrations
//            a.x = 2 * 9.81 * (s.getAcc().x + sensors.getAcc().x + sensors_1.getAcc().x) / 3;
//            a.y = 2 * 9.81 * (s.getAcc().y + sensors.getAcc().y + sensors_1.getAcc().y) / 3;
//            a.z = 2 * 9.81 * (s.getAcc().z + sensors.getAcc().z + sensors_1.getAcc().z) / 3;
//            // Align to bodyframe
//            r.x = -(Math.cos(compass) * a_gps.x + Math.sin(compass) * a_gps.y);
//            r.y = -(-Math.sin(compass) * a_gps.x + Math.cos(compass) * a_gps.y);
//            r.z = 9.81 - a_gps.z;
//            // Calculate Sigma for roll
//            sigma_r = (r.x * a.x + r.z * Math.sqrt(r.x * r.x + r.z * r.z - a.x * a.x))
//                    / (r.x * r.x + r.z * r.z);
//            roll = Math.atan((sigma_r * r.x - a.x) / (sigma_r * r.z));
//            System.out.println(String.format("r: %3.2f", roll * 180 / Math.PI));
//        }

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
        outfile.write(s.getSpeedGps() + ",");
        outfile.write(s.getAlt() + "");
        outfile.write(s.getAltitudeGps() + "");
        outfile.newLine();
        outfile.flush();
        // Store last sensor data.
        sensors_1 = sensors;
        sensors = s;
        // Resetting state on chrash or reset
        if (s.getAlt() + s.getSpd() == 0) {
            reset();
        }
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
        est_roll.reset();
        est_pitch.reset();
    }

    public boolean isReset() {
        return reset;
    }

    @Override
    public String toString() {
        return "PlaneState{" + "alt=" + alt + "roll=" + roll + ", pitch=" + pitch + ", yaw=" + yaw + '}';
    }
}
