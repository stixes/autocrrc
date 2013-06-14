/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.math;

import jmnav.math.Vec2d;
import jmnav.math.Mat2d;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jesper
 */
public class Mat2dTest {

    public Mat2dTest() {
    }

    /**
     * Test of eye method, of class Mat2d.
     */
    @Test
    public void testEye() {
        System.out.println("eye");
        Mat2d expResult = new Mat2d(1, 0, 0, 1);
        Mat2d result = Mat2d.eye();
        assertEquals(expResult, result);
    }

    /**
     * Test of mult method, of class Mat2d.
     */
    @Test
    public void testMult_Mat2d() {
        System.out.println("mult");
        Mat2d n = new Mat2d(5, 6, 7, 8);
        Mat2d instance = new Mat2d(1, 2, 3, 4);
        Mat2d expResult = new Mat2d(19, 22, 43, 50);
        Mat2d result = instance.mult(n);
        assertEquals(expResult, result);
    }

    /**
     * Test of mult method, of class Mat2d.
     */
    @Test
    public void testMult_Vec2d() {
        System.out.println("mult");
        Vec2d v = Vec2d.unitX();
        Mat2d instance = Mat2d.eye();
        Vec2d expResult = Vec2d.unitX();
        Vec2d result = instance.mult(v);
        assertEquals(expResult, result);
    }

    /**
     * Test of mult method, of class Mat2d.
     */
    @Test
    public void testMult_double() {
        System.out.println("mult");
        double b = 2.0;
        Mat2d instance = new Mat2d(1, 2, 3, 4);
        Mat2d expResult = new Mat2d(2, 4, 6, 8);
        Mat2d result = instance.mult(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of mult method, of class Mat2d.
     */
    @Test
    public void testSum() {
        System.out.println("sum");
        Mat2d b = new Mat2d(2, 3, 4, 5);
        Mat2d instance = new Mat2d(1, 2, 3, 4);
        Mat2d expResult = new Mat2d(3, 5, 7, 9);
        Mat2d result = instance.sum(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of transpose method, of class Mat2d.
     */
    @Test
    public void testTranspose() {
        System.out.println("transpose");
        Mat2d instance = new Mat2d(1, 2, 3, 4);
        Mat2d expResult = new Mat2d(1, 3, 2, 4);
        Mat2d result = instance.transpose();
        assertEquals(expResult, result);
    }

    /**
     * Test of inverted method, of class Mat2d.
     */
    @Test
    public void testInverted() {
        System.out.println("inverted");
        Mat2d instance = new Mat2d(1, 2, 3, 4);
        Mat2d expResult = new Mat2d(-2, 1, 1.5, -0.5);
        Mat2d result = instance.inverted();
        assertEquals(expResult, result);
    }

    /**
     * Test of det method, of class Mat2d.
     */
    @Test
    public void testDet() {
        System.out.println("det");
        Mat2d instance = new Mat2d(1, 2, 3, 4);
        double expResult = -2.0;
        double result = instance.det();
        assertEquals(expResult, result, 0.0);
    }
}