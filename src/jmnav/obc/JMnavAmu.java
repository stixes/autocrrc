/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.obc;

/**
 *
 * @author Jesper
 */
class JMnavAmu {

    private JMnavAmuDevice dev;

    public JMnavAmu(JMnavAmuDevice dev) {
        this.dev = dev;
    }

    public void sendCommand(double[] inputs) {
        dev.sendCommand(inputs);
    }

}
