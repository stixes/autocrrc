/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.obc;

/**
 *
 * @author Jesper
 */
public class GpsData {

    public double dt;
    public double longitude;
    public double latitude;
    public double altitude;
    public double speed;
    public double speed_N;
    public double speed_E;
    public double speed_V;

    public GpsData(double dt, double gps_longitude, double gps_latitude, double gps_alt, double gps_speed, double gps_speed_N, double gps_speed_E, double gps_speed_V) {
        this.dt = dt;
        this.longitude = gps_longitude;
        this.latitude = gps_latitude;
        this.altitude = gps_alt;
        this.speed = gps_speed;
        this.speed_N = gps_speed_N;
        this.speed_E = gps_speed_E;
        this.speed_V = gps_speed_V;
    }
}
