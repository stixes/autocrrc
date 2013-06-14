/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.math;

/**
 *
 * @author Jesper
 */
public class Vec2d {

    public double x, y;

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2d() {
        this(0, 0);
    }

    public static Vec2d unitX() {
        return new Vec2d(1, 0);
    }

    public static Vec2d unitY() {
        return new Vec2d(0, 1);
    }

    public double angle() {
        return Math.atan2(y, x);
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
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

    public Vec2d sum(Vec2d v) {
        return new Vec2d(x + v.x, y + v.y);
    }

    public double dot(Vec2d v) {
        return x * v.x + v.y * y;
    }

    public Vec2d scale(double a) {
        return new Vec2d(x * a, y * a);
    }

    public Vec2d norm() {
        return scale(1 / length());
    }

    @Override
    public String toString() {
        return String.format("Vec2d{%2.2f ; %2.2f}",x,y);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
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
        final Vec2d other = (Vec2d) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }


}
