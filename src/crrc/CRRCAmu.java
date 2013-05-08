/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crrc;

import java.io.IOException;
import jmnav.obc.CmdData;
import jmnav.obc.JMnavAmuDevice;

/**
 *
 * @author Jesper
 */
public class CRRCAmu implements JMnavAmuDevice {

    public CRRCAmu() throws IOException {
        CRRCSocket.getSocket();
    }

    @Override
    public void sendCommand(CmdData i) throws IOException {
        double[] data = {0.5 + (i.elevator + i.aileron) / 2,
            0.5 - (i.elevator - i.aileron) / 2,
            0.5 * i.throttle + 0.5, 0, 0, 0, 0, 0, 0};
        CRRCSocket.getSocket().send(new CRRCCmdPacket(CRRCCmdPacket.SET_SERVO, data));
    }
}
