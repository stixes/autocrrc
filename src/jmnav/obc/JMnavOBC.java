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
        JMnavOBC obc = new JMnavOBC();
        obc.run();
    }

    private void run() throws IOException {
        JMnavImu imu = new JMnavImu(new CRRCImu(),
                new JMnavAHRS(
                new JMnavPilot(
                new JMnavAmu(
                new CRRCAmu()
                ))));
        imu.run();
    }
}
