/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.control;

/**
 *
 * @author Jesper
 */
public class IEIntegrator {

    private double step;
    private long count = 0; // Integrator age
    private double z1 = 0.0; // old v value
    private double val = 0.0;

    public IEIntegrator() {
        this(0.01); // Default to 10ms
    }

    public IEIntegrator(double step) {
        this.step = step;
    }

    public double getVal() {
        return val;
    }

    public long getCount() {
        return count;
    }

    public double getAge() {
        return count * step;
    }

    /**
     * Implements Heun's method (Improved Euler) for 2. order intergral
     *
     * @param z
     * @return
     */
    public double update(double z) {
//        val = val + step * z;
        if (count < 1) {
            val = val + step * z;
            z1 = z;
        } else {
            val = val + step * (z + z1);
            z1 = z;
        }
        count++;
        return val;
    }

    public void reset() {
        val = z1 = 0.0;
        count = 0;
    }
}
