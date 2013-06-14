/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.math;

/**
 *
 * @author Jesper
 */
public class Vec3d {

    public double x, y, z;

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

    public double length() {
        return Math.sqrt(this.x * this.x
                + this.y * this.y
                + this.z * this.z);
    }

    public Vec3d newLength(double l) {
        return mult(l / length());
    }

    public Vec3d mult(double l) {
        return new Vec3d(this.x*l,this.y*l,this.z*l);
    }

    public Vec3d sub(Vec3d v) {
        return new Vec3d(this.x-v.getX(),this.y-v.getY(),this.z-v.getZ());
    }

    public Vec3d add(Vec3d v) {
        return new Vec3d(this.x+v.getX(),this.y+v.getY(),this.z+v.getZ());
    }

    @Override
    public String toString() {
        return String.format("Vec3d{ x=%2.4f, y=%2.4f, z=%2.4f }", x, y, z);
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Vec3d other = (Vec3d) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
            return false;
        }
        return true;
    }

}
