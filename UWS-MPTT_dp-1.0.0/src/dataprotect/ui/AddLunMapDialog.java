/*
 * AddLunMapDialog.java
 *
 * Created on 2006/12/27, afternoon�6:48
 */

package dataprotect.ui;

import javax.swing.*;
import mylib.tool.*;


/**
 *
 * @author  Administrator
 */
public class AddLunMapDialog extends javax.swing.JDialog {
    
    /** Creates new form AddLunMapDialog */
    public AddLunMapDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public AddLunMapDialog( SanBootView view ){
        this( view,true );
        myInit( view );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField3 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jTextField4 = new javax.swing.JTextField();
        jPasswordField3 = new javax.swing.JPasswordField();
        jPasswordField4 = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("IP :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Mask :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setText("Access Right :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setText("Server User :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel5.setText("Password :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setText("Password Again :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

        jLabel7.setText("Client User :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        jPanel1.add(jLabel7, gridBagConstraints);

        jLabel8.setText("Password :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        jPanel1.add(jLabel8, gridBagConstraints);

        jLabel9.setText("Password Again :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        jPanel1.add(jLabel9, gridBagConstraints);

        jTextField1.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        jPanel1.add(jTextField1, gridBagConstraints);

        jTextField2.setPreferredSize(new java.awt.Dimension(65, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 3, 0, 0);
        jPanel1.add(jTextField2, gridBagConstraints);

        jComboBox1.setPreferredSize(new java.awt.Dimension(90, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 3, 0, 0);
        jPanel1.add(jComboBox1, gridBagConstraints);

        jTextField3.setPreferredSize(new java.awt.Dimension(65, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 3, 0, 0);
        jPanel1.add(jTextField3, gridBagConstraints);

        jPasswordField1.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 3, 0, 0);
        jPanel1.add(jPasswordField1, gridBagConstraints);

        jPasswordField2.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 3, 0, 0);
        jPanel1.add(jPasswordField2, gridBagConstraints);

        jTextField4.setPreferredSize(new java.awt.Dimension(65, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 3, 0, 0);
        jPanel1.add(jTextField4, gridBagConstraints);

        jPasswordField3.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 3, 0, 0);
        jPanel1.add(jPasswordField3, gridBagConstraints);

        jPasswordField4.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 3, 0, 0);
        jPanel1.add(jPasswordField4, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(10, 50));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 35, 5));

        jPanel3.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(3, 1, 3, 1)));
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
    }
    // </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String _ip = jTextField1.getText().trim();
        if( _ip.length() == 0 ){
            JOptionPane.showMessageDialog( this,
                SanBootView.res.getString("AddLunMapDialog.error.noneIP")
            );
            return;
        }
        
        if( !Check.ipCheck( _ip ) ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("AddLunMapDialog.errMsg.invalidIP")
            );
            return;
        }
        
        String _mask = jTextField2.getText().trim();
        if( _mask.length() == 0 ){
            JOptionPane.showMessageDialog( this,
                SanBootView.res.getString("AddLunMapDialog.error.noneMask")
            );
            return;
        }
        
        /*
        if( !Check.maskCheck( _mask ) ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("AddLunMapDialog.errMsg.invalidMask")
            );
            return;
        }*/
        
        String _srvuser = jTextField3.getText().trim();
        String _clntuser = jTextField4.getText().trim();
        
        String srv_passwd = new String ( jPasswordField1.getPassword() );
        String srv_passwd1 = new String ( jPasswordField2.getPassword() );
        if( !srv_passwd.equals( srv_passwd1 ) ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("AddLunMapDialog.errMsg.misMatchedSrvPass")
            );
            return;
        }
        
        String clnt_passwd = new String ( jPasswordField3.getPassword() );
        String clnt_passwd1 = new String ( jPasswordField4.getPassword() );
        if( !clnt_passwd.equals( clnt_passwd1 ) ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("AddLunMapDialog.errMsg.misMatchedClntPass")
            );
            return;
        }
        
        values = new String[7];
        values[0] = _ip;
        values[1] = _mask;
        values[2] = (String)jComboBox1.getSelectedItem();
        values[3] = _srvuser;
        values[4] = srv_passwd;
        values[5] = _clntuser;
        values[6] = clnt_passwd;
        
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        values = null;
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddLunMapDialog(new javax.swing.JFrame(), true).setVisible(true);
            }
        });
    }
    
    ////GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JPasswordField jPasswordField4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    ////GEN-END:variables
    
    SanBootView view;
    Object[] values;
    
    private void  myInit( SanBootView _view) {
        view = _view;
        jTextField2.setText("255.255.255.255");
        setupAccessRight();
        setupLanguage();
    }
    
    private void setupAccessRight(){
        jComboBox1.addItem("rw");
        jComboBox1.addItem("--");
    }
    
    private void setupLanguage(){
        this.setTitle( SanBootView.res.getString("AddLunMapDialog.title.add"));
        jLabel1.setText( SanBootView.res.getString("AddLunMapDialog.label.ip") );
        jLabel2.setText( SanBootView.res.getString("AddLunMapDialog.label.mask") );
        jLabel3.setText( SanBootView.res.getString("AddLunMapDialog.label.access") );
        jLabel4.setText( SanBootView.res.getString("AddLunMapDialog.label.srvusr") );
        jLabel5.setText( SanBootView.res.getString("AddLunMapDialog.label.passwd") );
        jLabel6.setText( SanBootView.res.getString("AddLunMapDialog.label.passwd1") );
        jLabel7.setText( SanBootView.res.getString("AddLunMapDialog.label.clntusr") );
        jLabel8.setText( SanBootView.res.getString("AddLunMapDialog.label.passwd") );
        jLabel9.setText( SanBootView.res.getString("AddLunMapDialog.label.passwd1") );
        jButton1.setText(  SanBootView.res.getString("common.button.ok") );
        jButton2.setText(  SanBootView.res.getString("common.button.cancel") );
    }
    
    public Object[] getValues(){
        return values;
    }
}
