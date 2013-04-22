/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

/**
 *
 * @author Jesper
 */
public class PIDController {

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

    public boolean isSteady() {
        if (input != input) {
            return false;
        }
        return Math.abs(lastErr) < 0.05;
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
        double vel = err - lastErr;
        lastErr = err;
        this.output = Kp * err + Ki * totalErr + Kd * vel;
        this.output = Math.min(Math.max(this.output, minOut), maxOut);
        return output;
    }
    
    public void enable() {
        enabled = true;
    }
    
    public void disable() {
        enabled = false;
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        PIDController pid = new PIDController(10.1, 0, 4);
        double val = 10.0;
        int i;
        for (i = 0; i < 50 && !pid.isSteady(); i++) {
            val += 0.1 * pid.update(val) + 2;
            System.out.print(pid.isSteady() ? "1" : "0");
            for (int t = 0; t < (int) (val) + 25; t++) {
                System.out.print(" ");
            }
            System.out.println("x");
        }
        if (i < 50) {
            System.out.println("i: " + i);
        } else {
            while (i++ < 1000 && !pid.isSteady()) {
                val += 0.1 * pid.update(val) + 2;
            }
            System.out.println("i: " + i + " err: " + pid.getError());
        }
    }
}
