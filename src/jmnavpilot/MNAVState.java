/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

import java.text.DecimalFormat;

/**
 *
 * @author Jesper
 */
public class MNAVState {

    private float xAccel = 0; // [-2,2] G
    private float yAccel = 0;
    private float zAccel = 0;
    private float xRate = 0; // [-200,200] deg/sec
    private float yRate = 0;
    private float zRate = 0;
    private float xMag = 0; // [-1,1] Gauss
    private float yMag = 0;
    private float zMag = 0;
    private float xTemp = 0; // [-200,200] Celcius
    private float yTemp = 0;
    private float zTemp = 0;
    private float absPress = 0; // [-100,10000] m
    private float pitotPress = 0; // [0,80] m/s
    private float[] ppm = new float[8];

    public MNAVState() {
    }

    public float getAbsPress() {
        return absPress;
    }

    public void setAbsPress(float absPress) {
        this.absPress = absPress;
    }

    public float getPitotPress() {
        return pitotPress;
    }

    public void setPitotPress(float pitotPress) {
        this.pitotPress = pitotPress;
    }

    public float getxAccel() {
        return xAccel;
    }

    public void setxAccel(float xAccel) {
        this.xAccel = xAccel;
    }

    public float getxMag() {
        return xMag;
    }

    public void setxMag(float xMag) {
        this.xMag = xMag;
    }

    public float getxRate() {
        return xRate;
    }

    public void setxRate(float xRate) {
        this.xRate = xRate;
    }

    public float getxTemp() {
        return xTemp;
    }

    public void setxTemp(float xTemp) {
        this.xTemp = xTemp;
    }

    public float getyAccel() {
        return yAccel;
    }

    public void setyAccel(float yAccel) {
        this.yAccel = yAccel;
    }

    public float getyMag() {
        return yMag;
    }

    public void setyMag(float yMag) {
        this.yMag = yMag;
    }

    public float getyRate() {
        return yRate;
    }

    public void setyRate(float yRate) {
        this.yRate = yRate;
    }

    public float getyTemp() {
        return yTemp;
    }

    public void setyTemp(float yTemp) {
        this.yTemp = yTemp;
    }

    public float getzAccel() {
        return zAccel;
    }

    public void setzAccel(float zAccel) {
        this.zAccel = zAccel;
    }

    public float getzMag() {
        return zMag;
    }

    public void setzMag(float zMag) {
        this.zMag = zMag;
    }

    public float getzRate() {
        return zRate;
    }

    public void setzRate(float zRate) {
        this.zRate = zRate;
    }

    public float getzTemp() {
        return zTemp;
    }

    public void setzTemp(float zTemp) {
        this.zTemp = zTemp;
    }

    public float[] getPpm() {
        return ppm;
    }

    public void setPpm(float[] ppm) {
        this.ppm = ppm;
    }

    public float getPpm(int i) {
        return ppm[i];
    }

    public void setPpm(int i, float val) {
        this.ppm[i] = val;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat(" 0.00;-#");
        return "+- MNAVState ------------------------------+\n"
                + " Accel:       ( " + df.format(xAccel) + " , " + df.format(yAccel) + " , " + df.format(zAccel) + ")\n"
                + " Rate:        ( " + df.format(xRate) + " , " + df.format(yRate) + " , " + df.format(zRate) + ")\n"
                + " Mag:         ( " + df.format(xMag) + " , " + df.format(yMag) + " , " + df.format(zMag) + ")\n"
                + " Alt:        " + df.format(absPress) + "[m] - Speed: " + df.format(pitotPress) + "[m/s]\n"
                + "+------------------------------------------+";
    }
}
