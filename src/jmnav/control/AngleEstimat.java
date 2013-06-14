/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.control;

import jmnav.math.Mat3d;
import jmnav.math.Vec3d;

/**
 * Generic Angle estimator of roll or pitch.
 *
 * @author Jesper
 */
public class AngleEstimat {

    private double dt;
    private Mat3d a, h, b, p;
    private Mat3d q = new Mat3d(1, 0, 0, 0, 1, 0, 0, 0, 0.001).mult(1);
    private Mat3d r = new Mat3d(10, 0, 0, 0, 1, 0, 0, 0, 1).mult(1);
    private Vec3d x = new Vec3d(0, 0, 0);

    public void setDt(double dt) {
        this.dt = dt;
        a = new Mat3d(
                1, dt, 0,
                0, 0, 0,
                0, 0, 1);
        h = new Mat3d(
                1, 0, 0,
                0, 0, 0,
                0, 0, 0);
        b = new Mat3d(
                0, 0, 0,
                0, dt, 0,
                0, 0, 0);
    }

    public void reset() {
        p = Mat3d.eye().mult(10);
        x = new Vec3d();
    }

    public AngleEstimat(double dt) {
        setDt(dt);
        reset();
    }

    public AngleEstimat() {
        this(0.02);
    }

    public Vec3d getX() {
        return x;
    }

    public Vec3d update(double[] z_i) {
        return update(z_i[0], z_i[1]);
    }

    public Vec3d update(double dAngle) {
        // Input from Gyroscope
        Vec3d u = new Vec3d(0, dAngle, 0);
        // Propagate State: - X = A * X
        return a.mult(x).add(b.mult(u));
    }

    public Vec3d update(double dAngle, double angle) {
        // Measurement of the Angle
        Vec3d z = new Vec3d(angle, 0, 0);
        // Input from Gyroscope
        Vec3d u = new Vec3d(0, dAngle, 0);
        // Predict: State - Xp = A * X
        Vec3d x_p = a.mult(x).add(b.mult(u));
        // Predict: Measurement covariance - P = A * P * A' + Q;
        p = a.mult(p).mult(a.transpose()).add(q);
        // Innovation
        Vec3d y = z.sub(h.mult(x_p));
        // Innovation Covariance
        Mat3d s = h.mult(p.mult(h.transpose())).add(r);
        // Kalman Gain
        Mat3d k = p.mult(h.transpose().mult(s.inverse()));
        // Esitmate update
        x = x_p.add(k.mult(y));
        // Posterior measurement covariance
        p = Mat3d.eye().sub(k.mult(h)).mult(p);
        return x;
    }

    private String aryDump(Mat3d a) {
        double[][] m = a.m;
        String s = "";
        for (int i = 0; i < m.length; i++) {
            s = s + "{";
            for (int j = 0; j < m[i].length; j++) {
                s = s + m[i][j] + ",";
            }
            s = s + "}";
        }
        return s;
    }

    public double getDt() {
        return dt;
    }

    @Override
    public String toString() {
        return "AngleEstimat{" + aryDump(a) + '}';
    }
}
