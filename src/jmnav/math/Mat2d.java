/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.math;

import java.util.Arrays;

/**
 *
 * @author Jesper
 */
public class Mat2d {

    public double[][] m;

    public Mat2d(double a, double b, double c, double d) {
        m = new double[2][2];
        m[0][0] = a;
        m[0][1] = b;
        m[1][0] = c;
        m[1][1] = d;
    }

    public Mat2d(double[][] m) {
        this.m = m;
    }

    public Mat2d() {
        this(new double[2][2]);
    }

    public static Mat2d eye() {
        double[][] m = {{1, 0}, {0, 1}};
        return new Mat2d(m);
    }

    public Mat2d mult(Mat2d n) {
        double[][] a = new double[2][2];
        for (int j = 0; j < a.length; j++) {
            for (int i = 0; i < a[j].length; i++) {
                for (int k = 0; k < 2; k++) {
                    a[j][i] += n.m[k][i] * m[j][k];
                }
            }
        }
        return new Mat2d(a);
    }

    public Vec2d mult(Vec2d v) {
        return new Vec2d(
                v.x * m[0][0] + v.y * m[0][1],
                v.x * m[1][0] + v.y * m[1][1]);
    }

    public Mat2d mult(double b) {
        double[][] a = new double[2][2];
        for (int j = 0; j < a.length; j++) {
            for (int i = 0; i < a[j].length; i++) {
                a[j][i] = m[j][i] * b;
            }
        }
        return new Mat2d(a);
    }

    public Mat2d sum(Mat2d b) {
        double[][] a = new double[2][2];
        for (int j = 0; j < a.length; j++) {
            for (int i = 0; i < a[j].length; i++) {
                a[j][i] = m[j][i] + b.m[j][i];
            }
        }
        return new Mat2d(a);
    }

    public Mat2d transpose() {
        double[][] a = new double[2][2];
        for (int j = 0; j < a.length; j++) {
            for (int i = 0; i < a[j].length; i++) {
                a[j][i] = m[i][j];
            }
        }
        return new Mat2d(a);
    }

    public Mat2d inverted() {
        return new Mat2d(m[1][1], -m[0][1], -m[1][0], m[0][0]).mult(1 / det());
    }

    public double det() {
        return m[0][0] * m[1][1] - m[1][0] * m[0][1];
    }

    @Override
    public String toString() {
        return String.format("Mat2d{%2.2f %2.2f ; %2.2f %2.2f }", m[0][0], m[0][1], m[1][0], m[1][1]);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Arrays.deepHashCode(this.m);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mat2d other = (Mat2d) obj;
        if (!Arrays.deepEquals(this.m, other.m)) {
            return false;
        }
        return true;
    }
}
