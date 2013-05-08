/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.obc;

/**
 *
 * @author Jesper
 */
public class JMnavAHRS {

    private ImuData last;
    private JMnavImu imu;
    private double alt;
    private double speed;
    private double compass;
    private double roll;
    private double pitch;

    public JMnavAHRS(JMnavImu imu) {
        this.imu = imu;
    }

    public Odometry processData(ImuData input) {
        // In case GPS data is missing, retain last available information
        if (null == input.gpsData) {
            input.gpsData = last.gpsData;
        }
        // Read speed from preasure
        speed = input.pitot;
        // Read Altitude from preasure
        alt = input.abs_pressure;

        // Compas direction by x0y clamping yaw.
        // X = S 1..-1 N
        // Y = W 1..-1 E
        // Z = Up 1..-1 Down
        compass = Math.atan2(input.mag.y, -input.mag.x) + Math.PI;

        roll = Math.asin(input.ang.z * speed / 9.81);
        if (last != null) {
            pitch = Math.asin(last.gpsData.speed_V / speed);
        }
        last = input;
        //        System.out.println(input);
        Odometry odo = new Odometry();
        odo.altitude = alt;
        odo.compass = compass;
        odo.roll = roll;
        odo.pitch = pitch;
        odo.speed = speed;
        odo.gpsAltitude = input.gpsData.altitude;
        odo.gpsLatitude = input.gpsData.latitude;
        odo.gpsLongitude = input.gpsData.longitude;
        odo.gpsSpeed = input.gpsData.speed;
        odo.gpsSpeedE = input.gpsData.speed_E;
        odo.gpsSpeedN = input.gpsData.speed_N;
        odo.gpsSpeedV = input.gpsData.speed_V;
        return odo;
    }

    Odometry getOdometry() {
        return processData(imu.readData());

    }
}
