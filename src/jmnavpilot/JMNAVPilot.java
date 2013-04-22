/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

import crrc.CRRCCmdPacket;
import jmnavpilot.ui.jMNAVStatusFrame;
import crrc.CRRCDataPacket;
import crrc.CRRCSocket;
import java.io.IOException;

/**
 *
 * @author Jesper
 */
public class JMNAVPilot {

    private final CRRCSocket mnav;
//    private final Gamepad g = new Gamepad();

    public JMNAVPilot() throws Exception {
        mnav = CRRCSocket.getSocket();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        System.out.println("Hello, World!");
        JMNAVPilot pilot = new JMNAVPilot();
        pilot.run();
    }

    /**
     *
     * @param ch1 -1..1
     * @param ch2 -1..1
     * @param throttle 0..1
     */
    public void steer(double ch1, double ch2, double throttle) throws IOException {
        double[] data = {0.5 + ch1 / 2, 0.5 - ch2 / 2, throttle, 0, 0, 0, 0, 0, 0};
        CRRCCmdPacket ctrl_pack = new CRRCCmdPacket(CRRCCmdPacket.SET_SERVO, data);
        mnav.send(ctrl_pack);
    }

    public void run() throws IOException {
        jMNAVStatusFrame frame = new jMNAVStatusFrame();
        frame.setVisible(true);
        PlaneState ps = new PlaneState(1.0 / 100);
        Pilot pilot = new GamePadPilot();
        double[] ctrls;
        while (true) {
            ps.update(mnav.receive());
            pilot.update(ps);
            ctrls = pilot.getControls();
            steer(ctrls[0], ctrls[1], ctrls[2]);
            frame.updateState(ps);
            frame.updateText(ps.getSensors().toString());
        }
    }
}
