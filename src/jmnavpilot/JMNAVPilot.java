/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

import crrc.CRRCDataPacket;
import crrc.CRRCSocket;
import java.io.IOException;
//import net.java.games.input.*;

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

    public void setServos(float ch0, float ch1, float thrust) {
        byte[] ss = {'S', 'S'};
        byte[] blancs = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        ss = mnav.concat(ss,
//                mnav.concat(mnav.bytesFromPPM((1 + ch0) / 2), // Unknown
//                mnav.concat(mnav.bytesFromPPM((1 + ch1) / 2), // Unknown
//                mnav.concat(mnav.bytesFromPPM(thrust), // Thrust
//                blancs))));
//        mnav.sendData(ss);

    }

    public void run() throws IOException {
        System.out.println(mnav);
        jMNAVStatusFrame frame = new jMNAVStatusFrame();
        frame.setVisible(true);
        int count = 0;
        float ch0, ch1;
        PlaneState ps = new PlaneState(1f / 100);
        CRRCDataPacket state;
        Pilot pilot = new SimpleAutopilot();
        float[] controls;
        while (true) {
            state = mnav.receive();
            if (count++ == 25) {
                long time = System.currentTimeMillis();
                System.out.println("time: " + time);
                frame.updateText(state.toString());

                count = 0;
            }

        }
//        int count = 0;
//        setServos(0.5f, 0.55f, 1);
//        System.out.println(mnav.update());
//        setServos(0.5f, 0.5f, 0);
//        System.out.println(mnav.update());
//        while (true) {
//            MNAVState state = mnav.update();
//            if (10 == count++) {
//                System.out.println(state);
//                count = 0;
//            }
//        }
    }
}
