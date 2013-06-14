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
public class JMnavComm {

    private JMnavPilot pilot;
    private Vec3d[] waypoints;
    private int currentWP = 0;

    public JMnavComm(JMnavPilot pilot) {
        this.pilot = pilot;
//        waypoints[0] = new Vec3d(0.03, 0.03, 15.0);
//        waypoints[1] = new Vec3d(-0.03, 0.03, 40.0);
//        waypoints[2] = new Vec3d(0.0, 0.0, 20.0);
        waypoints = new Vec3d[5];
        waypoints[0] = new Vec3d(0.0, 0.02, 35.1);
        waypoints[1] = new Vec3d(-0.02, 0.02, 35.1);
        waypoints[2] = new Vec3d(-0.02, -0.02, 35.1);
        waypoints[3] = new Vec3d(0.02, -0.02, 35.1);
        waypoints[4] = new Vec3d(0.02, 0.02, 35.1);
    }

    void send(Odometry odometry) {
//        System.out.println(String.format(
//                "Autopilot:\n"
//                + "R: %2.3f\n"
//                + "P: %2.3f\n"
//                + "H: %2.3f\n"
//                + "C: %3.3f",
//                odometry.roll * 180 / Math.PI,
//                odometry.pitch * 180 / Math.PI,
//                odometry.altitude,
//                0.0));
//        System.out.println(String.format(
//                "Loc: %1.8f %1.8f Alt: %2.1f Distance: %1.8f",
//                odometry.gpsLatitude,
//                odometry.gpsLongitude,
//                odometry.altitude,
//                0.0));
    }

    void destinationReached() {
        pilot.setDestination(waypoints[currentWP++]);
        if (currentWP == waypoints.length) {
            currentWP = 0;
        }
    }
}
