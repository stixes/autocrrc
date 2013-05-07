/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.control;

import jmnav.math.Mat2d;
import jmnav.math.Vec2d;

/**
 *
 * @author Jesper
 */
public class KalmanGyro {

    double dt;
    Vec2d x = new Vec2d();
    Mat2d p;
    Mat2d a, b, q, r, h;

    public KalmanGyro() {
        this(0.01);
    }

    public KalmanGyro(double dt) {
        setDt(dt);
    }

    public void setDt(double dt) {
        this.dt = dt;
        reset();
    }

    public void reset() {
        a = new Mat2d(1, -dt, 0, 1);
        b = new Mat2d(dt, 0, 0, 0);
        h = new Mat2d(1, 0, 0, 0);
        p = Mat2d.eye();
        q = new Mat2d(100, 0, 0, 0.01);
        r = new Mat2d(1000, 0, 0, 1);
    }

    public double update(double u) {
        Vec2d z = new Vec2d(u, 0);
        Vec2d x_p = a.mult(x).sum(b.mult(z));
        p = a.mult(p).mult(a.transpose()).sum(q);
        Vec2d y = z.sum(h.mult(x_p).scale(-1));
        Mat2d s = h.mult(p.mult(h.transpose())).sum(r);
        Mat2d k = p.mult(h.transpose().mult(s.inverted()));
        x = x_p.sum(k.mult(y));
        p = Mat2d.eye().sum(k.mult(h).mult(-1)).mult(p);
        return x.x;
    }
}
