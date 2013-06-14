/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.control;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jesper
 */
public class Kalman1dTest {

    public Kalman1dTest() {
    }

    /**
     * Test of setDt method, of class Kalman1d.
     */
    @Test
    public void testSetDt() {
        System.out.println("setDt");
        double dt = 0.0;
        Kalman1d instance = new Kalman1d();
        instance.setDt(dt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class Kalman1d.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        Kalman1d instance = new Kalman1d();
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class Kalman1d.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        double z = 0.0;
        Kalman1d instance = new Kalman1d();
        double expResult = 0.0;
        double result = instance.update(z);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}