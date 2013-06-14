/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.control;

import jmnav.math.Mat3d;

/**
 *
 * @author Jesper
 */
public class PitchEstimator extends AngleEstimat {

    private Mat3d q = new Mat3d(0.1, 0, 0, 0, 0.01, 0, 0, 0, 0.001).mult(1);
    private Mat3d r = new Mat3d(100, 0, 0, 0, 100, 0, 0, 0, 1).mult(1);

}
