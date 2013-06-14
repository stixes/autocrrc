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
class JMnavAmu {

    private JMnavAmuDevice dev;

    public JMnavAmu(JMnavAmuDevice dev) {
        this.dev = dev;
    }

    public void sendCommand(CmdData inputs) throws IOException {
        dev.sendCommand(inputs);
    }

}
