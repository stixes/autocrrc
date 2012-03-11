/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

import net.java.games.input.*;

/**
 *
 * @author Jesper
 */
public class Gamepad {

    private Controller c;
    private Component[] comps;

    public Gamepad() {
        ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment();
        Controller[] cs = ce.getControllers();
        for (int i = 0; i < cs.length; i++) {
            System.out.println(i + ". " + cs[i].getName() + ", " + cs[i].getType());
        }
        c = cs[3];
        c.poll();
        
        comps = c.getComponents();
        for (int i = 0; i < comps.length; i++) {
            Component component = comps[i];
            System.out.println("Component (" + i + "): " + component.getName());
        }

    }

    public void poll() {
        c.poll();
    }
    
    public float getAxis(int idx) {
//        System.out.println("compnent: "+idx+" val: "+comps[idx].getPollData()+" DZ: "+comps[idx].getDeadZone());
        return comps[idx].getPollData();
    }

    private boolean isButton(Component c) {
        if (!c.isAnalog() && !c.isRelative()) {  // digital & absolute 
            String className = c.getIdentifier().getClass().getName();
            if (className.endsWith("Button")) {
                return true;
            }
        }
        return false;
    }  // end of isButton()
}
