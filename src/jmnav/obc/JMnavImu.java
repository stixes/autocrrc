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
public class JMnavImu implements Runnable {

    private JMnavImuDevice dev;
    private JMnavAHRS ahrs;
    private boolean running = true;

    public JMnavImu(JMnavImuDevice dev, JMnavAHRS ahrs) {
        this.dev = dev;
        this.ahrs = ahrs;
    }

    public void start() {
        new Thread(this).start();
    }

    public void run() {
        ImuData data;
        while (running) {
            try {
                data = dev.read();
                ahrs.processData(data);
            } catch (IOException ex) {
                System.exit(0);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        running = false;
    }
}
