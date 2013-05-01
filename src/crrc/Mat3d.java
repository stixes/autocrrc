/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crrc;

import java.util.Arrays;

/**
 *
 * @author Jesper
 */
public class Mat3d {

    public double m[][] = new double[3][3];

    public Mat3d() {
    }

    public Mat3d(double a11, double a21, double a31,
            double a12, double a22, double a32,
            double a13, double a23, double a33) {
        set(0, 0, a11);
        set(1, 0, a21);
        set(2, 0, a31);
        set(0, 1, a12);
        set(1, 1, a22);
        set(2, 1, a32);
        set(0, 2, a13);
        set(1, 2, a23);
        set(2, 2, a33);
    }

    private Mat3d(double[][] n) {
        m = n.clone();
    }
    
    public static Mat3d eye() {
        return new Mat3d(1,0,0,0,1,0,0,0,1);
    }
    public static Mat3d ones() {
        return new Mat3d(1,1,1,1,1,1,1,1,1);
    }
    public static Mat3d zeros() {
        return new Mat3d(0,0,0,0,0,0,0,0,0);
    }

    public double get(int x, int y) {
        return m[y][x];
    }

    public void set(int x, int y, double val) {
        m[y][x] = val;
    }

    public Mat3d transpose() {
        double[][] n = new double[3][3];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                n[i][j] = m[j][i];
            }
        }
        return new Mat3d(n);
    }

    public Mat3d add(Mat3d n) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                m[i][j] += n.m[i][j];
            }
        }
        return new Mat3d(m);
    }

    public Mat3d sub(Mat3d n) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                m[i][j] -= n.m[i][j];
            }
        }
        return new Mat3d(m);
    }

    public Mat3d mult(double a) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                m[i][j] *= a;
            }
        }
        return new Mat3d(m);
    }

    public Vec3d mult(Vec3d a) {
        return new Vec3d(
                a.getX() * get(0, 0) + a.getY() * get(1, 0) + a.getZ() * get(2, 0),
                a.getX() * get(0, 1) + a.getY() * get(1, 1) + a.getZ() * get(2, 1),
                a.getX() * get(0, 2) + a.getY() * get(1, 2) + a.getZ() * get(2, 2));
    }

    public Mat3d mult(Mat3d a) {
        double[][] n = new double[3][3];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                for (int x = 0; x < 3; x++) {
                    n[i][j] += m[i][x] * a.m[x][j];
                }
            }
        }
        return new Mat3d(n);
    }

    /**
     * Returns the inverse of the Matrix.
     * http://en.wikipedia.org/wiki/Invertible_matrix
     * 
     * @return Mat3d
     */
    public Mat3d inverse() {
        Mat3d n;
        double a = get(0, 0), b = get(1, 0), c = get(2, 0);
        double d = get(0, 1), e = get(1, 1), f = get(2, 1);
        double g = get(0, 2), h = get(1, 2), k = get(2, 2);
        double det = a*(e*k-f*h)-b*(k*d-f*g)+c*(d*h-e*g);
        n =new Mat3d( (e*k-f*h),-(b*k-c*h), (b*f-c*e),
                     -(d*k-f*g), (a*k-c*g),-(a*f-c*d),
                      (d*h-e*g),-(a*h-b*g), (a*e-b*d));
        n.mult(1/det);
        return n;
    }

    @Override
    public String toString() {
        String s = "{%2.2f ,%2.2f ,%2.2f}";
        return String.format(s, m[0][0], m[0][1], m[0][2]) + "\n"
                + String.format(s, m[1][0], m[1][1], m[1][2]) + "\n"
                + String.format(s, m[2][0], m[2][1], m[2][2]);
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Mat3d other = (Mat3d) obj;
        if (!Arrays.deepEquals(this.m, other.m)) {
            return false;
        }
        return true;
    }
    
}
