/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.obc;

import crrc.CRRCAmu;
import crrc.CRRCImu;
import java.io.IOException;

/**
 *
 * @author Jesper
 */
public class JMnavOBC {

    public static void main(String[] Args) throws IOException {
        System.out.println("JMnav pilot 0.01b starting up..");
        JMnavOBC obc = new JMnavOBC();
        obc.run();
    }

    /**
     * Application main loop.
     */
    private void run() {
        // Set up control train. Pilot drives.
        try {
        JMnavPilot pilot = new JMnavPilot(
                new JMnavAHRS(new JMnavImu(new CRRCImu())),
                new JMnavAmu(new CRRCAmu()));
        pilot.run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
}
