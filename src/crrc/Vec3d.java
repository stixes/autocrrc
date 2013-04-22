/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crrc;

/**
 *
 * @author Jesper
 */
public class Vec3d {

    double x, y, z;

    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3d() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getLen() {
        return Math.sqrt(this.x * this.x
                + this.y * this.y
                + this.z * this.z);
    }

    public void setLen(double l) {
        mult(l / getLen());
    }

    public void mult(double l) {
        this.x *= l;
        this.y *= l;
        this.z *= l;
    }

    public void sub(Vec3d v) {
        this.x -= v.getX();
        this.y -= v.getY();
        this.z -= v.getZ();
    }

    public void add(Vec3d v) {
        this.x += v.getX();
        this.y += v.getY();
        this.z += v.getZ();
    }

    @Override
    public String toString() {
        return String.format("Vec3d{ x=%2.2f, y=%2.2f, z=%2.2f }", x, y, z);
    }
}
