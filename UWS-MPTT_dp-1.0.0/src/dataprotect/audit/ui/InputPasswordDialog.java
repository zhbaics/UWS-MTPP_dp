/*
 * InputPasswordDialog.java
 *
 * Created on Aug 13, 2009, 10:20 AM
 */

package dataprotect.audit.ui;

import dataprotect.ui.*;
import dataprotect.audit.data.BackupUser;

import java.awt.event.*;
import javax.swing.*;
import mylib.tool.Check;

/**
 *
 * @author  Administrator
 */
public class InputPasswordDialog extends javax.swing.JDialog {
    
    /** Creates new form InputPasswordDialog */
    public InputPasswordDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public InputPasswordDialog(java.awt.Dialog parent,boolean modal ){
        super( parent,modal );
        initComponents();
    }
    
    public InputPasswordDialog( java.awt.Frame parent,BackupUser user ){
        this(parent,true);
        myInit( user );
    }
    
    public InputPasswordDialog( UserMgrDialog parent,BackupUser user ){
        this( parent,true );
        myInit( user );
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
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jPasswordField3 = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Old Password :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jPasswordField1.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(jPasswordField1, gridBagConstraints);

        jLabel2.setText("New Password :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jPasswordField2.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(jPasswordField2, gridBagConstraints);

        jLabel3.setText("Password  Again :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jPasswordField3.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(jPasswordField3, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jSeparator1, java.awt.BorderLayout.NORTH);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 5));

        jButton1.setText("OK");
        jButton1.setPreferredSize(new java.awt.Dimension(75, 24));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        jButton2.setText("Cancel");
        jButton2.setMargin(new java.awt.Insets(2, 1, 2, 1));
        jButton2.setPreferredSize(new java.awt.Dimension(75, 24));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);

        jPanel3.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // close button process
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.newPasswd = null;
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // ok button process
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String old = new String( jPasswordField1.getPassword() );
        if( !old.equals( user.getPasswd() ) ){
            JOptionPane.showMessageDialog(this, 
                SanBootView.res.getString("InputPasswordDialog.errMsg.wrongOldPwd")
            );
            return;
        }
        
        String newpasswd1 = new String( jPasswordField2.getPassword() );
        if( newpasswd1.length() == 0 ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("InputPasswordDialog.errMsg.nonNewPasswd")
            );
            return;
        }
        
        if( Check.checkInput( newpasswd1 ) ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("InputPasswordDialog.errMsg.badPasswd")
            );
            return;
        }
        
        if( newpasswd1.getBytes().length >=32 ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("InputPasswordDialog.errMsg.tooBigPasswd")
            );
            return;
        }
        
        String newpasswd2 = new String( jPasswordField3.getPassword() );
        if( !newpasswd1.equals( newpasswd2 ) ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("InputPasswordDialog.errMsg.disMatched")
            );
            return;
        }
        
        this.newPasswd = newpasswd1;
        
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new InputPasswordDialog(new javax.swing.JFrame(), true).setVisible( true );
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
    
    private BackupUser user;
    private String newPasswd;
    
    public String getNewPasswd(){
        return newPasswd;
    }
    
    private void myInit( BackupUser user ){
        this.user = user;
            
        regKeyboardAction();
        setupLanguage();
    }
    
    private void regKeyboardAction(){   
        jPasswordField1.registerKeyboardAction(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    jPasswordField2.requestFocus();
                }
            },
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        ); 
        
        jPasswordField2.registerKeyboardAction(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    jPasswordField3.requestFocus();
                }
            },
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        ); 
        
        jPasswordField3.registerKeyboardAction(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    jButton1.requestFocus();
                }
            },
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        ); 
    }
    
    private void setupLanguage(){
        this.setTitle(SanBootView.res.getString("InputPasswdDialog.dialogTitle.modify"));
        this.jLabel1.setText(
            SanBootView.res.getString("InputPasswdDialog.label.oldPwd")
        );
        this.jLabel2.setText(
            SanBootView.res.getString("InputPasswdDialog.label.newPwd")
        );
        this.jLabel3.setText(
            SanBootView.res.getString("InputPasswdDialog.label.inputAgain")
        );
        this.jButton1.setText(
            SanBootView.res.getString("common.button.ok")
        );
        this.jButton2.setText(
            SanBootView.res.getString("common.button.cancel")
        );
    }
}
