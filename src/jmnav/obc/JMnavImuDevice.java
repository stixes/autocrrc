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
public interface JMnavImuDevice {


    /**
     * This must be a blocking read that return the IMU measurements.
     * Can throw IOException in case of fatal read errors.
     *
     * @return ImuData
     * @throws IOException
     */
    public ImuData read() throws IOException;

}
