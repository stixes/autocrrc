/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.obc;

import jmnav.control.AngleEstimat;
import jmnav.control.IEIntegrator;
import jmnav.control.Kalman1d;
import jmnav.control.PitchEstimator;
import jmnav.ui.JMnavStatusFrame;

/**
 *
 * @author Jesper
 */
class JMnavAHRS {

    private ImuData last;
    private JMnavImu imu;
    private double alt;
    private double speed;
    private double compass;
    private double roll;
    private double pitch;
    private AngleEstimat est_roll;
    private AngleEstimat est_pitch;
    private Kalman1d vSpeed;
    private Kalman1d est_altitude;

    public JMnavAHRS(JMnavImu imu) {
        this.imu = imu;
        est_roll = new AngleEstimat(0);
        est_pitch = new PitchEstimator();
        vSpeed = new Kalman1d(0.01, 0.3);
        est_altitude = new Kalman1d(0.01, 0.3);
    }

    public Odometry processData(ImuData input) {
        est_roll.setDt(input.dt);
        est_pitch.setDt(input.dt);
//        System.out.println(String.format("dt: %1.3f", input.dt));

        double[] z = new double[2];
        // In case GPS data is missing, retain last available information
        if (null == input.gpsData && last != null) {
//            input.gpsData = last.gpsData;
        }
        // Read speed from preasure
        speed = input.pitot;
        // Read Altitude from preasure
        alt = est_altitude.update(input.abs_pressure);

        // Compas direction by x0y clamping yaw.
        // X = S 1..-1 N
        // Y = W 1..-1 E
        // Z = Up 1..-1 Down
        compass = Math.atan2(input.mag.y, -input.mag.x) + Math.PI;

        roll = Math.asin(input.ang.z * speed / 9.81);
//        roll = Math.atan2(input.acc.y, Math.sqrt(input.acc.x * input.acc.x + input.acc.z * input.acc.z));
        roll = est_roll.update(input.ang.x, roll).getX();

//        roll = Math.asin((Math.sin(roll) * input.ang.y + Math.cos(roll) * input.ang.z) * speed / 9.81);

        if (last != null) {
//            double v_speed = -vSpeed.update((input.abs_pressure - last.abs_pressure) / input.dt);
//            double v_speed = -(((input.abs_pressure - last.abs_pressure) / input.dt) / input.pitot);
            double v_speed = -(alt - last.abs_pressure) / (input.pitot * input.dt);
            if (input.gpsData != null) {
                double v_speed_g = input.gpsData.speed_V;
//                System.out.println(String.format("alt: %2.2f/%2.2f vs: %2.3f Gvs: %2.3f err: %2.3f", alt, last.abs_pressure, v_speed, v_speed_g, v_speed - v_speed_g));
                v_speed = v_speed_g;
//                pitch = Math.asin(v_speed);
//                int_pitch.setVal(pitch);
            } else {
//                System.out.println("vs: " + v_speed);
//                pitch -= 2 * input.dt * (Math.cos(roll) * input.ang.y + Math.sin(roll) * input.ang.z);
            }
//            double acc = (speed - last.pitot) * Math.cos(pitch);
//            double acc = (speed - last.pitot);
//            pitch = est_pitch.update(input.dt * (Math.cos(roll) * input.ang.y + Math.sin(roll) * input.ang.z), -Math.atan2(input.acc.x - acc, Math.sqrt(input.acc.y * input.acc.y + input.acc.z * input.acc.z))).x;
//            pitch = est_pitch.update(input.ang.y, Math.asin(v_speed / speed)).x;
            pitch = Math.asin(v_speed / speed);
//            pitch = -Math.atan2(input.acc.x - acc, Math.sqrt(input.acc.y * input.acc.y + input.acc.z * input.acc.z));
        }

//        pitch = int_pitch.update(Math.cos(roll) * input.ang.y + Math.sin(roll) * input.ang.z);


        last = input;

        Odometry odo = new Odometry();
        odo.altitude = alt;
        odo.compass = compass;
        odo.roll = roll;
        odo.pitch = pitch;
        odo.speed = speed;
        if (input.gpsData != null) {
            odo.hasGPS = true;
            odo.gpsAltitude = input.gpsData.altitude;
            odo.gpsLatitude = input.gpsData.latitude;
            odo.gpsLongitude = input.gpsData.longitude;
            odo.gpsSpeed = input.gpsData.speed;
            odo.gpsSpeedE = input.gpsData.speed_E;
            odo.gpsSpeedN = input.gpsData.speed_N;
            odo.gpsSpeedV = input.gpsData.speed_V;
        }
        JMnavStatusFrame.GetFrame().updateState(odo);
        return odo;
    }

    Odometry getOdometry() {
        return processData(imu.readData());

    }
}
