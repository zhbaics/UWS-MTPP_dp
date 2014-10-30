/*
 * ActModePaneForRestUnixOrgDisk.java
 *
 * Created on 2006/12/29, afternoon 9:52
 */

package dataprotect.ui;

import javax.swing.*;

/**
 *
 * @author  Administrator
 */
public class ActModePaneForRestUnixOrgDisk extends javax.swing.JPanel {
    
    /** Creates new form SelectBootHostPane */
    public ActModePaneForRestUnixOrgDisk() {
        initComponents();
    }
    
    public ActModePaneForRestUnixOrgDisk( SanBootView view ){
        this();
        myInit( view );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jTextArea3 = new javax.swing.JTextArea();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel8 = new javax.swing.JPanel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel10 = new javax.swing.JPanel();
        jTextArea2 = new javax.swing.JTextArea();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(20, 10));
        add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setPreferredSize(new java.awt.Dimension(20, 10));
        add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(10, 40));
        jScrollPane1.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        jTextArea1.setLineWrap(true);
        jTextArea1.setDisabledTextColor(java.awt.Color.black);
        jTextArea1.setEnabled(false);
        jTextArea1.setOpaque(false);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel5.setPreferredSize(new java.awt.Dimension(10, 230));
        jPanel7.setLayout(null);

        jPanel7.setPreferredSize(new java.awt.Dimension(10, 100));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel9.setBorder(new javax.swing.border.TitledBorder("                                                                                             "));
        jPanel9.setOpaque(false);
        jPanel9.setPreferredSize(new java.awt.Dimension(764, 45));
        jTextArea3.setEditable(false);
        jTextArea3.setLineWrap(true);
        jTextArea3.setDisabledTextColor(java.awt.Color.black);
        jTextArea3.setEnabled(false);
        jTextArea3.setOpaque(false);
        jPanel9.add(jTextArea3, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel9);
        jPanel9.setBounds(0, 12, 460, 90);

        jRadioButton1.setText("Restoring original disk partitions automaticlly");
        jRadioButton1.setBorder(null);
        jRadioButton1.setPreferredSize(new java.awt.Dimension(100, 19));
        jPanel7.add(jRadioButton1);
        jRadioButton1.setBounds(20, 10, 265, 19);

        jPanel5.add(jPanel7, java.awt.BorderLayout.NORTH);

        jPanel8.setLayout(null);

        jPanel8.setPreferredSize(new java.awt.Dimension(10, 90));
        jRadioButton2.setText("Creating partitions manually");
        jRadioButton2.setBorder(null);
        jRadioButton2.setPreferredSize(new java.awt.Dimension(200, 19));
        jPanel8.add(jRadioButton2);
        jRadioButton2.setBounds(20, 10, 175, 19);

        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel10.setBorder(new javax.swing.border.TitledBorder("                               "));
        jTextArea2.setEditable(false);
        jTextArea2.setLineWrap(true);
        jTextArea2.setDisabledTextColor(java.awt.Color.black);
        jTextArea2.setEnabled(false);
        jTextArea2.setOpaque(false);
        jPanel10.add(jTextArea2, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel10);
        jPanel10.setBounds(0, 12, 460, 70);

        jPanel5.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        add(jPanel3, java.awt.BorderLayout.CENTER);

    }
    // </editor-fold>//GEN-END:initComponents
        
    ////GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    ////GEN-END:variables
    
    SanBootView view;
    ButtonGroup grp = new ButtonGroup();
    
    private void myInit( SanBootView _view ){
        view = _view;
        setupLanguage();
        
        grp.add( jRadioButton1 );
        grp.add( jRadioButton2 );
        
        jTextArea1.setText(
            SanBootView.res.getString("RestoreOriginalDiskWizardDialog.tip5")
        );
        jTextArea2.setText(
            "      "+SanBootView.res.getString("RestoreOriginalDiskWizardDialog.manualText1")
        );
        jTextArea3.setText(
            "      "+SanBootView.res.getString("RestoreOriginalDiskWizardDialog.autoText1")
        );
    }
    
    private void setupLanguage(){
        jRadioButton1.setText(SanBootView.res.getString("ActModePaneForRestWinOrgDisk.radioBtn.auto"));
        jRadioButton2.setText(SanBootView.res.getString("ActModePaneForRestWinOrgDisk.radioBtn.manual"));
    }
    
    public boolean isAutoRst(){
        return jRadioButton1.isSelected();
    }
    public void setAutoRst( boolean val ){
        jRadioButton1.setSelected( val );
    }
    public boolean isManualRst(){
        return jRadioButton2.isSelected();
    }
    public void setManualRst( boolean val ){
        jRadioButton2.setSelected( val );
    } 
}