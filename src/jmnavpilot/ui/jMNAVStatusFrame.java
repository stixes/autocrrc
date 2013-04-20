/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot.ui;

import jmnavpilot.PlaneState;

/**
 *
 * @author Jesper
 */
public class jMNAVStatusFrame extends javax.swing.JFrame {

    /**
     * Creates new form jMNAVStatusFrame
     */
    public jMNAVStatusFrame() {
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jMNAVStatusFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jMNAVStatusFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jMNAVStatusFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jMNAVStatusFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        jTextPane1 = new javax.swing.JTextPane();
        altMeter2 = new jmnavpilot.ui.AltMeter();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextPane1.setText("Hello, World!");
        getContentPane().add(jTextPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 498, 198));
        getContentPane().add(altMeter2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 60, 100));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void updateText(String str) {
        jTextPane1.setText(str);
    }
    
    public void updateState(PlaneState state) {
        altMeter2.update(state);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private jmnavpilot.ui.AltMeter altMeter2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}