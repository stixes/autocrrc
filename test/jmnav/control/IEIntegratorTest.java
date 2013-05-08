/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.control;

import jmnav.control.IEIntegrator;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jesper
 */
public class IEIntegratorTest {

    public IEIntegratorTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getVal method, of class IEIntegrator.
     */
    @Test
    public void testGetVal() {
        System.out.println("getVal");
        IEIntegrator instance = new IEIntegrator();
        double expResult = 0.0;
        double result = instance.getVal();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of update method, of class IEIntegrator.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        double step = 0.01;
        int steps = 100;
        IEIntegrator instance = new IEIntegrator(step);
        double[] result = new double[steps];
        double[] expResult = new double[steps];
        for (int i = 0; i < steps; i++) {
            result[i] = instance.update(i*step);
            expResult[i] = i*i*step*step;
        }
        System.out.println(Arrays.toString(result));
        System.out.println(Arrays.toString(expResult));
        assertArrayEquals(expResult, result,0.01); // Estimate quadratic within 1% error
    }

    /**
     * Test of reset method, of class IEIntegrator.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        IEIntegrator instance = new IEIntegrator();
        instance.reset();
        assertEquals(0.0, instance.getVal(), 0.0);
    }
}