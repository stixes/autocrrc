/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.control;

/**
 *
 * @author Jesper
 */
public class Kalman1d {

    double dt;
    double x = 0.0, x_o = 0.0;
    double p;
    final double q, r;

    public Kalman1d() {
        this(1, 1, 0.01);
    }

    public Kalman1d(double q, double r) {
        this.q = q;
        this.r = r;
        setDt(0.01);
    }

    public Kalman1d(double q, double r, double dt) {
        this.q = q;
        this.r = r;
        setDt(dt);
    }

    public void setDt(double dt) {
        this.dt = dt;
        reset();
    }

    public void reset() {
        p = 1;
    }

    public double update(double z) {
//        double alpha = 0.2;
//        x = alpha*z + (1-alpha)*x;
//        return x;
        double p_p = p +q;
        double k = p_p / (p_p + r);
        x = x + k * (z - x);
        p = (1 - k) * p_p;
        return x;
    }

    public static void main(String[] args) {
        System.out.println("Kalman1d test");
        double[] d = {0.39,0.5,0.48,0.29,0.25,0.32,0.34,0.48,0.41,0.45};
        Kalman1d filt = new Kalman1d(0, 0.1);
        for (int i = 0; i < d.length; i++) {
            double z = d[i];
            System.out.println(String.format("k: %d z: %s.3f x: %2.3f",i,z,filt.update(z)));
        }

    }
}
