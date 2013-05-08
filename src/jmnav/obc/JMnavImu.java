/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.obc;

import java.io.IOException;

/**
 *
 * @author Jesper
 */
class JMnavImu {

    private JMnavImuDevice dev;

    public JMnavImu(JMnavImuDevice dev) {
        this.dev = dev;
    }

    public ImuData readData() {
        ImuData data = null;
        try {
            data = dev.read();
        } catch (IOException ex) {
            System.exit(1);
        }
        return data;
    }
}
