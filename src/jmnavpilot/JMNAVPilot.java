/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jesper
 */
public class JMNAVPilot {

    private final MNAV mnav;

    public JMNAVPilot() throws Exception {
        mnav = new MNAV();
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
        ss = mnav.concat(ss,
                mnav.concat(mnav.bytesFromPPM((1 + ch0) / 2), // Unknown
                mnav.concat(mnav.bytesFromPPM((1 + ch1) / 2), // Unknown
                mnav.concat(mnav.bytesFromPPM(thrust), // Thrust
                blancs))));
        mnav.sendData(ss);

    }

    public void run() {
        System.out.println(mnav);
        int count = 0;
        setServos(0.5f, 0.55f, 1);
        System.out.println(mnav.update());
        setServos(0.5f, 0.5f, 0);
        System.out.println(mnav.update());
//        while (true) {
//            MNAVState state = mnav.update();
//            if (10 == count++) {
//                System.out.println(state);
//                count = 0;
//            }
//        }
    }
}
