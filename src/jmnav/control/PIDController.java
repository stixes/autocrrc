/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.control;

/**
 *
 * @author Jesper
 */
public class PIDController {

    private double dt = 0.01;
    private double Kp, Ki, Kd;
    private double lastErr = 0.0;
    private double totalErr = 0.0;
    private double ref = 0.0;
    private double input = Double.NaN, output = 0.0;
    private double maxOut = 1.0, minOut = -1.0;
    private boolean enabled = true;

    public PIDController(double p, double i, double d) {
        this(p, i, d, 0.0);
    }

    public PIDController(double p, double i, double d, double ref) {
        this.Kp = p;
        this.Ki = i;
        this.Kd = d;
        this.ref = ref;
    }

    public PIDController(double p, double i, double d, double ref, double minOut, double maxOut) {
        this.Kp = p;
        this.Ki = i;
        this.Kd = d;
        this.ref = ref;
        this.maxOut = maxOut;
        this.minOut = minOut;
    }

    public static PIDController ZNtuned(double ku, double pu) {
        double kp = 0.60 * ku;
        double ki = 2*kp/pu;
        double kd = kp * pu / 8;
        return new PIDController(kp, ki, kd);
    }

    public double getP() {
        return Kp;
    }

    public void setP(double p) {
        this.Kp = p;
    }

    public double getI() {
        return Ki;
    }

    public void setI(double i) {
        this.Ki = i;
    }

    public double getD() {
        return Kd;
    }

    public void setD(double d) {
        this.Kd = d;
    }

    public double getRef() {
        return ref;
    }

    public void setRef(double ref) {
        this.ref = ref;
    }

    public void setInput(double input) {
        this.input = input;
    }

    public double getOutput() {
        return output;
    }

    public double getError() {
        return lastErr;
    }

    public void setOutputRange(double min, double max) {
        minOut = min;
        maxOut = max;
    }

    public boolean isSteady() {
        if (input != input) {
            return false;
        }
        return Math.abs(lastErr) < 0.01;
    }

    public double update(double input) {
        if (!enabled) {
            return 0.0;
        }
        this.input = input;
        double err = ref - input;

        if ((totalErr + err) * Ki < maxOut && (totalErr + err) * Ki > minOut) {
            totalErr += err;
        }
        double vel = (err - lastErr) * dt;
        lastErr = err;
        this.output = Kp * err + Ki * totalErr + Kd * vel;
        this.output = Math.min(Math.max(this.output, minOut), maxOut);
        return output;
    }

    public void reset() {
        lastErr = totalErr = 0.0;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        PIDController pid = new PIDController(0.1, 0.05, 0);
        double val = 1.0;
        int i;
        for (i = 0; i < 50 && !pid.isSteady(); i++) {
            val += pid.update(val)+0.1;
            System.out.print(String.format("%s v: %2.3f ",(pid.isSteady() ? "1" : "0"),val));
            for (int t = 0; t < (int) (val*15) + 25; t++) {
                System.out.print(" ");
            }
            System.out.println("x");
        }
        if (i < 50) {
            System.out.println("i: " + i);
        } else {
            while (i++ < 1000 && !pid.isSteady()) {
                val += 0.1 * pid.update(val) ;
            }
            System.out.println("i: " + i + " err: " + pid.getError());
        }
        System.out.println("pid: "+pid);
    }

    @Override
    public String toString() {
        return "PIDController{" + "lastErr=" + lastErr + ", totalErr=" + totalErr + ", ref=" + ref + ", output=" + output + ", maxOut=" + maxOut + ", minOut=" + minOut + '}';
    }


}
