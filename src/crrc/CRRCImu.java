/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crrc;

import java.io.IOException;
import jmnav.obc.GpsData;
import jmnav.obc.ImuData;
import jmnav.obc.JMnavImuDevice;

/**
 *
 * @author Jesper
 */
public class CRRCImu implements JMnavImuDevice {

    private int c_gps;
    private final int ds = 1; // Return only every ds packet
    private final int gps_ds = 1; // Include GPS data in every gps_ds packet
    private long ts = System.currentTimeMillis();

    public CRRCImu() throws IOException {
        CRRCSocket.getSocket();
    }

    @Override
    public ImuData read() throws IOException {
        int c = ds;
//        double dt = (System.currentTimeMillis() - ts) / 1000.0;
        double dt = 0.02;
        ImuData output = null;
        CRRCDataPacket data = null;
        while (c-- > 0) {
            data = CRRCSocket.getSocket().receive();
//            System.out.println(String.format("dt: %3.4f",ts/1000.0));
            output = new ImuData(dt * ds, data.getAcc(), data.getAng(), data.getMag(), data.getAlt(), data.getSpd());
        }
        if (++c_gps == gps_ds) {
            output.gpsData = new GpsData(dt * ds * gps_ds, data.getLongitude(), data.getLatitude(), data.getAltitudeGps(), data.getSpeedGps(), data.getSpeedN(), data.getSpeedE(), data.getSpeedV());
            c_gps = 0;
        }
        ts = System.currentTimeMillis();
        return output;
    }
}
