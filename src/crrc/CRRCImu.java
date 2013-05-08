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

    public CRRCImu() throws IOException {
        CRRCSocket.getSocket();
    }

    @Override
    public ImuData read() throws IOException {
        CRRCDataPacket data = CRRCSocket.getSocket().receive();
        ImuData output = new ImuData(0.01, data.getAcc(), data.getAng(), data.getMag(), data.getAlt(), data.getSpd());
        output.gpsData = new GpsData(0.01, data.getLongitude(), data.getLatitude(), data.getAltitudeGps(), data.getSpeedGps(), data.getSpeedN(), data.getSpeedE(), data.getSpeedV());
        return output;
    }

}
