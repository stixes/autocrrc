/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnav.ui;

import jmnav.obc.Odometry;

/**
 *
 * @author Jesper
 */
public class JMnavStatusFrame extends javax.swing.JFrame {

    private static JMnavStatusFrame obj = null;

    /**
     * Creates new form JMnavStatusFrame
     */
    public JMnavStatusFrame() {
        initComponents();
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JMnavStatusFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        altMeter = new jmnav.ui.AltMeter();
        horizon1 = new jmnav.ui.Horizon();
        compass1 = new jmnav.ui.Compass();
        spdMeter1 = new jmnav.ui.SpdMeter();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(altMeter, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 300));
        getContentPane().add(horizon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 300, 300));
        getContentPane().add(compass1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 100, 110));
        getContentPane().add(spdMeter1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 50, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static JMnavStatusFrame GetFrame() {
        if (obj == null) {
            obj = new JMnavStatusFrame();
            obj.setVisible(true);
        }
        return obj;
    }


    public void updateState(Odometry state) {
        altMeter.update(state);
        spdMeter1.update(state);
        horizon1.update(state);
        compass1.update(state);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private jmnav.ui.AltMeter altMeter;
    private jmnav.ui.Compass compass1;
    private jmnav.ui.Horizon horizon1;
    private jmnav.ui.SpdMeter spdMeter1;
    // End of variables declaration//GEN-END:variables
}
