/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.math;

import jmnav.math.Mat3d;
import jmnav.math.Vec3d;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jesper
 */
public class Mat3dTest {
    private Mat3d instance;
    
    public Mat3dTest() {
    }
    
    @Before
    public void setUp() {
        instance = Mat3d.eye();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of get method, of class Mat3d.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int x = 1;
        int y = 1;
        double expResult = 1.0;
        double result = instance.get(x, y);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of set method, of class Mat3d.
     */
    @Test
    public void testSet() {
        System.out.println("set");
        int x = 1;
        int y = 1;
        double val = 1234.0;
        instance.set(x, y, val);
        assertEquals(new Mat3d(1,0,0,0,1234,0,0,0,1), instance);        
    }

    /**
     * Test of transpose method, of class Mat3d.
     */
    @Test
    public void testTranspose() {
        System.out.println("transpose");
        Mat3d result = instance.transpose();
        assertEquals(instance, result);
        instance = new Mat3d(0,1,0,0,1,0,0,1,0);
        result = instance.transpose();
        assertEquals(new Mat3d(0,0,0,1,1,1,0,0,0), result);
    }

    /**
     * Test of add method, of class Mat3d.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Mat3d expResult = new Mat3d(2,1,1,1,2,1,1,1,2);
        Mat3d result = instance.add(new Mat3d(1,1,1,1,1,1,1,1,1));
        assertEquals(expResult, result);
    }

    /**
     * Test of mult method, of class Mat3d.
     */
    @Test
    public void testMult_double() {
        System.out.println("mult");
        double a = 5.0;
        Mat3d expResult = new Mat3d(5,0,0,0,5,0,0,0,5);
        Mat3d result = instance.mult(a);
        assertEquals(expResult, result);
    }

    /**
     * Test of mult method, of class Mat3d.
     */
    @Test
    public void testMult_Vec3d() {
        System.out.println("mult");
        Vec3d a = new Vec3d(1,1,1);
        Vec3d expResult = new Vec3d(1,1,1);
        Vec3d result = instance.mult(a);
        assertEquals(expResult, result);
        instance = new Mat3d(1,2,3,4,5,6,7,8,9);
        a = new Vec3d(1,2,3);
        expResult = new Vec3d(14,32,50);
        result = instance.mult(a);
        assertEquals(expResult, result);
    }

    /**
     * Test of mult method, of class Mat3d.
     */
    @Test
    public void testMult_Mat3d() {
        System.out.println("mult");
        Mat3d result = instance.mult(instance);
        assertEquals(instance, result); // Eye * Eye = Eye
        instance = new Mat3d(1,2,3,4,5,6,7,8,9);
        result = instance.mult(instance);
        Mat3d expResult = new Mat3d(30,36,42,66,81,96,102,126,150);
        assertEquals(expResult, result);
        instance = new Mat3d(-1,2,3,4,-5,6,7,8,-9);
        result = instance.mult(instance);
        expResult = new Mat3d(30,12,-18,18,81,-72,-38,-98,150);
        assertEquals(expResult, result);
        instance = new Mat3d(1,2,3,0,4,5,6,7,0);
        result = instance.mult(new Mat3d(1,2,3,4,5,6,7,8,9));
        expResult = new Mat3d(30,36,42,51,60,69,34,47,60);
        assertEquals(expResult, result);
    }

    /**
     * Test of inverse method, of class Mat3d.
     */
    @Test
    public void testInverse() {
        System.out.println("inverse");
        Mat3d expResult = new Mat3d(1,-0d,0,-0d,1,-0d,0,-0d,1);
        Mat3d result = instance.inverse();
        assertEquals(expResult, result);
        instance = new Mat3d(1,2,3,0,1,4,5,6,0);
        expResult = new Mat3d(-24,18,5,20,-15,-4,-5,4,1);
        result = instance.inverse();
        assertEquals(expResult, result);
    }

}