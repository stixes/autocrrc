/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.math;

import jmnav.math.Vec2d;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jesper
 */
public class Vec2dTest {

    public Vec2dTest() {
    }

    /**
     * Test of unitX method, of class Vec2d.
     */
    @Test
    public void testUnitX() {
        System.out.println("unitX");
        Vec2d expResult = new Vec2d(1, 0);
        Vec2d result = Vec2d.unitX();
        assertEquals(expResult, result);
    }

    /**
     * Test of unitY method, of class Vec2d.
     */
    @Test
    public void testUnitY() {
        System.out.println("unitY");
        Vec2d expResult = new Vec2d(0, 1);
        Vec2d result = Vec2d.unitY();
        assertEquals(expResult, result);
    }

    /**
     * Test of angle method, of class Vec2d.
     */
    @Test
    public void testAngle() {
        System.out.println("angle");
        Vec2d instance = new Vec2d(1, 1);
        double expResult = Math.PI / 4;
        double result = instance.angle();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of length method, of class Vec2d.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        Vec2d instance = new Vec2d(3, 4);
        double expResult = 5.0;
        double result = instance.length();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getX method, of class Vec2d.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Vec2d instance = Vec2d.unitX();
        double expResult = 1.0;
        double result = instance.getX();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setX method, of class Vec2d.
     */
    @Test
    public void testSetX() {
        System.out.println("setX");
        double x = 123.4;
        Vec2d instance = new Vec2d();
        instance.setX(x);
        assertEquals(x, instance.getX(), 0.0);
    }

    /**
     * Test of getY method, of class Vec2d.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Vec2d instance = Vec2d.unitY();
        double expResult = 1.0;
        double result = instance.getY();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setY method, of class Vec2d.
     */
    @Test
    public void testSetY() {
        System.out.println("setY");
        double y = 123.4;
        Vec2d instance = new Vec2d();
        instance.setY(y);
        assertEquals(y, instance.getY(), 0.0);
    }

    /**
     * Test of sum method, of class Vec2d.
     */
    @Test
    public void testSum() {
        System.out.println("sum");
        Vec2d v = Vec2d.unitX();
        Vec2d instance = Vec2d.unitY();
        Vec2d expResult = new Vec2d(1, 1);
        Vec2d result = instance.sum(v);
        assertEquals(expResult, result);
    }

    /**
     * Test of dot method, of class Vec2d.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        Vec2d v = new Vec2d(1, 2);
        Vec2d instance = new Vec2d(3, 4);
        double expResult = 11;
        double result = instance.dot(v);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of scale method, of class Vec2d.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        double a = 123.4;
        Vec2d instance = new Vec2d(1, 1);
        Vec2d expResult = new Vec2d(a, a);
        Vec2d result = instance.scale(a);
        assertEquals(expResult, result);
    }

    /**
     * Test of norm method, of class Vec2d.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
        Vec2d instance = new Vec2d(0,4);
        Vec2d expResult = Vec2d.unitY();
        Vec2d result = instance.norm();
        assertEquals(expResult, result);
    }

}