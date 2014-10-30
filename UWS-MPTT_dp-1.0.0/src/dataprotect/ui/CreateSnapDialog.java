/*
 * CreateSnapDialog.java
 *
 * Created on 2006/12/28, ��AM��4:17
 */

package dataprotect.ui;

import javax.swing.*;
import mylib.tool.Check;

/**
 *
 * @author  Administrator
 */
public class CreateSnapDialog extends javax.swing.JDialog {
    
    /** Creates new form CreateSnapDialog */
    public CreateSnapDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public CreateSnapDialog( SanBootView view,int mode ){
        this( view,true );
        myInit( view,mode );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Snapshot Name :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jTextField1.setPreferredSize(new java.awt.Dimension(275, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        jPanel1.add(jTextField1, gridBagConstraints);

        jTextField2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 1, 3, 1));
        jTextField2.setDisabledTextColor(java.awt.Color.black);
        jTextField2.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jTextField2, gridBagConstraints);

        jLabel2.setText("Snapshot Desc :");
        jLabel2.setPreferredSize(new java.awt.Dimension(90, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jTextField3.setPreferredSize(new java.awt.Dimension(270, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        jPanel1.add(jTextField3, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 1, 2, 1));
        jPanel3.setPreferredSize(new java.awt.Dimension(173, 40));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 35, 5));

        jButton1.setText("OK");
        jButton1.setPreferredSize(new java.awt.Dimension(75, 27));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton2.setText("Cancel");
        jButton2.setPreferredSize(new java.awt.Dimension(75, 27));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);
        jPanel2.add(jSeparator1, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        values = null;
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String _name = jTextField1.getText().trim();
        if( _name.length() == 0 ){
            JOptionPane.showMessageDialog(this,
                    SanBootView.res.getString("CreateSnapDialog.error.noneName")
            );
            return;
        }
        
        if( Check.checkInput( _name ) ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("CreateSnapDialog.error.badName")
            );
            return;
        }
        
        if( _name.getBytes().length >=255 ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("CreateSnapDialog.error.nametoolong")
            );
            return;
        }
        
        String _desc = jTextField3.getText().trim();
        if( Check.checkInput( _desc ) ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("CreateSnapDialog.error.badDesc")
            );
            return;
        }
        
        if( _desc.getBytes().length >=255 ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("CreateSnapDialog.error.Desctoolong")
            );
            return;
        }
        
        values = new Object[2];
        values[0] = _name;
        values[1] = _desc;
        
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateSnapDialog(new javax.swing.JFrame(), true).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
    
    SanBootView view;
    int mode;
    private Object[] values=null;
    
    private void myInit( SanBootView view,int mode ){
        this.view = view;
        this.mode = mode;

        if( mode == 0 ){
            this.jTextField2.setText( SanBootView.res.getString("CreateSnapDialog.text") );
        }else{
            jPanel1.remove( this.jTextField2 );
        }
        setupLanguage();
    }
    
    private void setupLanguage(){
        setTitle( SanBootView.res.getString("CreateSnapDialog.title"));
        jLabel1.setText( SanBootView.res.getString("CreateSnapDialog.label.name"));
        jLabel2.setText( SanBootView.res.getString("CreateSnapDialog.label.desc"));
        jButton1.setText( SanBootView.res.getString("common.button.ok"));
        jButton2.setText( SanBootView.res.getString("common.button.cancel"));
    }
    
    public Object[] getValues(){
        return values;
    }
}
