/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crrc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jesper
 */
public class Vec3dTest {
    private Vec3d instance;
    
    public Vec3dTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Vec3d(1,0,0);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getX method, of class Vec3d.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        double expResult = 1.0;
        double result = instance.getX();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setX method, of class Vec3d.
     */
    @Test
    public void testSetX() {
        System.out.println("setX");
        double x = 1234.0;
        instance.setX(x);
        assertEquals(x, instance.getX(), 0.0);        
    }

    /**
     * Test of getY method, of class Vec3d.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        double expResult = 0.0;
        double result = instance.getY();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setY method, of class Vec3d.
     */
    @Test
    public void testSetY() {
        System.out.println("setY");
        double y = 1234.0;
        instance.setY(y);
        assertEquals(y, instance.getY(), 0.0);        
    }

    /**
     * Test of getZ method, of class Vec3d.
     */
    @Test
    public void testGetZ() {
        System.out.println("getZ");
        double expResult = 0.0;
        double result = instance.getZ();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setZ method, of class Vec3d.
     */
    @Test
    public void testSetZ() {
        System.out.println("setZ");
        double z = 1234.0;
        instance.setZ(z);
        assertEquals(z, instance.getZ(), 0.0);        
    }

    /**
     * Test of length method, of class Vec3d.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        double expResult = 1.0;
        double result = instance.length();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setLength method, of class Vec3d.
     */
    @Test
    public void testNewLength() {
        System.out.println("setLength");
        double l = 5.0;
        double result = instance.newLength(l).length();
        assertEquals(l, result, 0.0);
    }

    /**
     * Test of mult method, of class Vec3d.
     */
    @Test
    public void testMult() {
        System.out.println("mult");
        double l = 2.0;
        double expResult = 2.0;
        double result = instance.mult(l).length();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of sub method, of class Vec3d.
     */
    @Test
    public void testSub() {
        System.out.println("sub");
        Vec3d v = new Vec3d(3,2,1);
        Vec3d expResult = new Vec3d(-2,-2,-1);
        Vec3d result = instance.sub(v);
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class Vec3d.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        System.out.println("sub");
        Vec3d v = new Vec3d(3,2,1);
        Vec3d expResult = new Vec3d(4,2,1);
        Vec3d result = instance.add(v);
        assertEquals(expResult, result);
    }

}