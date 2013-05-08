/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crrc;

import java.io.IOException;
import jmnav.obc.JMnavAmuDevice;

/**
 *
 * @author Jesper
 */
public class CRRCAmu implements JMnavAmuDevice {

    public CRRCAmu() throws IOException {
        CRRCSocket.getSocket();
    }

    /**
     * Expects: 0 = Throttle -1..1, 1 = Aileron -1..1, 2 = Elevator -1..1.
     */
    @Override
    public void sendCommand(double[] i) throws IOException {
        double[] data = {0.5 + (i[2] + i[1]) / 2, 0.5 - (i[2] - i[1]) / 2, 0.5 * i[0] + 0.5, 0, 0, 0, 0, 0, 0};
            CRRCSocket.getSocket().send(new CRRCCmdPacket(CRRCCmdPacket.SET_SERVO, data));
    }
}
