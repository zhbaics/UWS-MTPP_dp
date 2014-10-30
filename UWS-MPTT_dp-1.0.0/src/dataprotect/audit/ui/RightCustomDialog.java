/*
 * RightCustomDialog.java
 */

package dataprotect.audit.ui;

import dataprotect.ui.SanBootView;
import dataprotect.audit.data.BackupUser;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

/**
 *
 * @author  Administrator
 */
public class RightCustomDialog extends javax.swing.JDialog {

    /** Creates new form RightCustomDialog */
    public RightCustomDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public RightCustomDialog(java.awt.Dialog parent, boolean modal) {
        super( parent, modal );
        initComponents();
    }
    
    public RightCustomDialog( UserMgrDialog umdialog,int right,BackupUser user ){
        this( umdialog,true );
        myInit( umdialog,right,user );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jCheckBox25 = new javax.swing.JCheckBox();
        jCheckBox26 = new javax.swing.JCheckBox();
        jCheckBox27 = new javax.swing.JCheckBox();
        jCheckBox28 = new javax.swing.JCheckBox();
        jCheckBox29 = new javax.swing.JCheckBox();
        jCheckBox30 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jCheckBox17 = new javax.swing.JCheckBox();
        jCheckBox18 = new javax.swing.JCheckBox();
        jCheckBox19 = new javax.swing.JCheckBox();
        jCheckBox20 = new javax.swing.JCheckBox();
        jCheckBox21 = new javax.swing.JCheckBox();
        jCheckBox22 = new javax.swing.JCheckBox();
        jCheckBox23 = new javax.swing.JCheckBox();
        jCheckBox24 = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jCheckBox13 = new javax.swing.JCheckBox();
        jCheckBox14 = new javax.swing.JCheckBox();
        jCheckBox15 = new javax.swing.JCheckBox();
        jCheckBox16 = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox31 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Host"));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jCheckBox25.setText("Initiate Host");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel7.add(jCheckBox25, gridBagConstraints);

        jCheckBox26.setText("NetBoot Host");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel7.add(jCheckBox26, gridBagConstraints);

        jCheckBox27.setText("Restore Original Disk");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel7.add(jCheckBox27, gridBagConstraints);

        jCheckBox28.setText("Local Disk Boot");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel7.add(jCheckBox28, gridBagConstraints);

        jCheckBox29.setText("Modify Host");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel7.add(jCheckBox29, gridBagConstraints);

        jCheckBox30.setText("Delete Host");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel7.add(jCheckBox30, gridBagConstraints);

        jCheckBox4.setText("Delete NetBoot Host");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel7.add(jCheckBox4, gridBagConstraints);

        jPanel5.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Snapshot"));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jCheckBox17.setText("Create Volume");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel8.add(jCheckBox17, gridBagConstraints);

        jCheckBox18.setText("Delete Volume");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel8.add(jCheckBox18, gridBagConstraints);

        jCheckBox19.setText("Create LunMap");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel8.add(jCheckBox19, gridBagConstraints);

        jCheckBox20.setText("Delete LunMap");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel8.add(jCheckBox20, gridBagConstraints);

        jCheckBox21.setText("Create Snapshot");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel8.add(jCheckBox21, gridBagConstraints);

        jCheckBox22.setText("Delete Snapshot");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel8.add(jCheckBox22, gridBagConstraints);

        jCheckBox23.setText("Create View");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel8.add(jCheckBox23, gridBagConstraints);

        jCheckBox24.setText("Delete View");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel8.add(jCheckBox24, gridBagConstraints);

        jPanel5.add(jPanel8, java.awt.BorderLayout.SOUTH);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Data duplication"));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        jCheckBox7.setText("Create Profile");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel6.add(jCheckBox7, gridBagConstraints);

        jCheckBox8.setText("Modify Profile");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel6.add(jCheckBox8, gridBagConstraints);

        jCheckBox9.setText("Delete Profile");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel6.add(jCheckBox9, gridBagConstraints);

        jCheckBox10.setText("Run Profile");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel6.add(jCheckBox10, gridBagConstraints);

        jCheckBox11.setText("Verify Profile");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel6.add(jCheckBox11, gridBagConstraints);

        jCheckBox12.setText("Rename Profile");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel6.add(jCheckBox12, gridBagConstraints);

        jCheckBox13.setText("Create Scheduler");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel6.add(jCheckBox13, gridBagConstraints);

        jCheckBox14.setText("Modify Scheduler");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel6.add(jCheckBox14, gridBagConstraints);

        jCheckBox15.setText("Delete  Scheduler");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel6.add(jCheckBox15, gridBagConstraints);

        jCheckBox16.setText("Delete duplication log");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel6.add(jCheckBox16, gridBagConstraints);

        jPanel3.add(jPanel6, java.awt.BorderLayout.SOUTH);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Other"));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jCheckBox1.setText("DHCP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(jCheckBox1, gridBagConstraints);

        jCheckBox5.setText("Create Pool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel4.add(jCheckBox5, gridBagConstraints);

        jCheckBox6.setText("Delete Pool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(jCheckBox6, gridBagConstraints);

        jCheckBox2.setText("Modify User Password");
        jPanel4.add(jCheckBox2, new java.awt.GridBagConstraints());

        jCheckBox3.setText("Cancel Task");
        jPanel4.add(jCheckBox3, new java.awt.GridBagConstraints());

        jCheckBox31.setText("Shutdown");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(jCheckBox31, gridBagConstraints);

        jPanel1.add(jPanel4, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 1, 10, 1));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 5));

        jButton1.setText("OK");
        jButton1.setPreferredSize(new java.awt.Dimension(76, 24));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        jButton2.setText("Cancel");
        jButton2.setPreferredSize(new java.awt.Dimension(76, 24));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    closeBtn_process();
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    cancelBtn_process();
}//GEN-LAST:event_jButton2ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RightCustomDialog dialog = new RightCustomDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox16;
    private javax.swing.JCheckBox jCheckBox17;
    private javax.swing.JCheckBox jCheckBox18;
    private javax.swing.JCheckBox jCheckBox19;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox20;
    private javax.swing.JCheckBox jCheckBox21;
    private javax.swing.JCheckBox jCheckBox22;
    private javax.swing.JCheckBox jCheckBox23;
    private javax.swing.JCheckBox jCheckBox24;
    private javax.swing.JCheckBox jCheckBox25;
    private javax.swing.JCheckBox jCheckBox26;
    private javax.swing.JCheckBox jCheckBox27;
    private javax.swing.JCheckBox jCheckBox28;
    private javax.swing.JCheckBox jCheckBox29;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox30;
    private javax.swing.JCheckBox jCheckBox31;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    // End of variables declaration//GEN-END:variables
    
    int oldRight;
    int newRight;
    UserMgrDialog umDialog;
    BackupUser user;
    
    private void myInit( UserMgrDialog _umDialog,int oldRight,BackupUser user ){
        umDialog = _umDialog;
        this.oldRight = oldRight;
        this.user = user;
        
        setupRight();
        setupLanguage();
    }
    
    private void setupLanguage(){
        this.setTitle( SanBootView.res.getString("RightCustomDialog.title"));
        this.jButton1.setText( SanBootView.res.getString("common.button.ok") ); 
        this.jButton2.setText( SanBootView.res.getString("common.button.cancel") );
        this.jCheckBox1.setText( SanBootView.res.getString("View.MenuItem.dhcp") );
        this.jCheckBox2.setText( SanBootView.res.getString("UserMgrDialog.btn.modPasswd") );
        this.jCheckBox3.setText( SanBootView.res.getString("RightCustomDialog.checkbox.canceljob") );
        this.jCheckBox4.setText( SanBootView.res.getString("RightCustomDialog.checkbox.delnetboothost") );        
        this.jCheckBox5.setText( SanBootView.res.getString("RightCustomDialog.checkbox.crtpool") );
        this.jCheckBox6.setText( SanBootView.res.getString("RightCustomDialog.checkbox.delpool") );
        this.jCheckBox7.setText( SanBootView.res.getString("RightCustomDialog.checkbox.crtprof") );
        this.jCheckBox8.setText( SanBootView.res.getString("RightCustomDialog.checkbox.modprof") );
        this.jCheckBox9.setText( SanBootView.res.getString("RightCustomDialog.checkbox.delprof") );
        this.jCheckBox10.setText( SanBootView.res.getString("RightCustomDialog.checkbox.runprof") );
        this.jCheckBox11.setText( SanBootView.res.getString("RightCustomDialog.checkbox.verprof") );
        this.jCheckBox12.setText( SanBootView.res.getString("RightCustomDialog.checkbox.renprof") );
        this.jCheckBox13.setText( SanBootView.res.getString("RightCustomDialog.checkbox.crtsch") );
        this.jCheckBox14.setText( SanBootView.res.getString("RightCustomDialog.checkbox.modsch") );
        this.jCheckBox15.setText( SanBootView.res.getString("RightCustomDialog.checkbox.delsch") );
        this.jCheckBox16.setText( SanBootView.res.getString("RightCustomDialog.checkbox.delduplog") );
        this.jCheckBox17.setText( SanBootView.res.getString("common.inittask.crtVol") );
        this.jCheckBox18.setText( SanBootView.res.getString("common.cmd.delVol") );
        this.jCheckBox19.setText( SanBootView.res.getString("RightCustomDialog.checkbox.crtlm") );
        this.jCheckBox20.setText( SanBootView.res.getString("RightCustomDialog.checkbox.dellm") );
        this.jCheckBox21.setText( SanBootView.res.getString("View.MenuItem.crtSnap") );
        this.jCheckBox22.setText( SanBootView.res.getString("View.MenuItem.delSnap") );
        this.jCheckBox23.setText( SanBootView.res.getString("View.MenuItem.crtView") );
        this.jCheckBox24.setText( SanBootView.res.getString("View.MenuItem.delView") );
        this.jCheckBox25.setText( SanBootView.res.getString("RightCustomDialog.checkbox.init") );
        this.jCheckBox26.setText( SanBootView.res.getString("RightCustomDialog.checkbox.netboot") );
        this.jCheckBox27.setText( SanBootView.res.getString("RightCustomDialog.checkbox.rstOriDisk") );
        this.jCheckBox28.setText( SanBootView.res.getString("RightCustomDialog.checkbox.localdiskboot") );
        this.jCheckBox29.setText( SanBootView.res.getString("View.MenuItem.modHost") );
        this.jCheckBox30.setText( SanBootView.res.getString("View.MenuItem.delHost") );
        this.jCheckBox31.setText( SanBootView.res.getString("RightCustomDialog.checkbox.shutdown") );
                
        TitledBorder bor = (TitledBorder)jPanel6.getBorder();
        bor.setTitle(
            SanBootView.res.getString("RightCustomDialog.border.dup")
        );
        bor = (TitledBorder)jPanel4.getBorder();
        bor.setTitle(
            SanBootView.res.getString("RightCustomDialog.border.other")
        );
        bor = (TitledBorder)jPanel7.getBorder();
        bor.setTitle(
            SanBootView.res.getString("RightCustomDialog.border.host")
        );
        bor = (TitledBorder)jPanel8.getBorder();
        bor.setTitle(
            SanBootView.res.getString("RightCustomDialog.border.snap")
        );
    }
    
    private void setupRight(){
        this.jCheckBox1.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_DHCP ) );
        this.jCheckBox2.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_MOD_PWD ) );
        this.jCheckBox3.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_CANCEL_JOB) );
        this.jCheckBox4.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_DEL_NETBOOT_HOST ) );
        this.jCheckBox5.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_CRT_POOL ) );
        this.jCheckBox6.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_DEL_POOL) );
        this.jCheckBox7.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_CRT_PROF ) );
        this.jCheckBox8.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_MOD_PROF ) );
        this.jCheckBox9.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_DEL_PROF ) );
        this.jCheckBox10.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_RUN_PROF ) );
        this.jCheckBox11.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_VER_PROF ) );
        this.jCheckBox12.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_REN_PROF ) );
        this.jCheckBox13.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_CRT_SCH ) );
        this.jCheckBox14.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_MOD_SCH ) );
        this.jCheckBox15.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_DEL_SCH ) );
        this.jCheckBox16.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_DEL_DUPLOG ) );
        this.jCheckBox17.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_CRT_VOL ) );
        this.jCheckBox18.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_DEL_VOL ) );
        this.jCheckBox19.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_CRT_LM ) );
        this.jCheckBox20.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_DEL_LM ) );
        this.jCheckBox21.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_CRT_SNAP ) );
        this.jCheckBox22.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_DEL_SNAP ) );
        this.jCheckBox23.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_CRT_VIEW ) );
        this.jCheckBox24.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_DEL_VIEW ) );
        this.jCheckBox25.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_INIT_HOST ) );
        this.jCheckBox26.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_NETBOOT) );
        this.jCheckBox27.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_RST_ORG_DISK) );
        this.jCheckBox28.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_LOCAL_DISK_BOOT) );
        this.jCheckBox29.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_MOD_HOST ) );
        this.jCheckBox30.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_DEL_HOST ) );
        this.jCheckBox31.setSelected( BackupUser.hasThisRight( oldRight, BackupUser.RIGHT_SHUTDOWN ) );
    }
    
    private int getRight(){
        int aRight = 0;      
        
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_DHCP,jCheckBox1.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_MOD_PWD, jCheckBox2.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_CANCEL_JOB, jCheckBox3.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_DEL_NETBOOT_HOST, jCheckBox4.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_CRT_POOL, jCheckBox5.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_DEL_POOL, jCheckBox6.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_CRT_PROF, jCheckBox7.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_MOD_PROF, jCheckBox8.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_DEL_PROF, jCheckBox9.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_RUN_PROF, jCheckBox10.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_VER_PROF, jCheckBox11.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_REN_PROF, jCheckBox12.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_CRT_SCH, jCheckBox13.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_MOD_SCH, jCheckBox14.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_DEL_SCH, jCheckBox15.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_DEL_DUPLOG, jCheckBox16.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_CRT_VOL, jCheckBox17.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_DEL_VOL, jCheckBox18.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_CRT_LM, jCheckBox19.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_DEL_LM, jCheckBox20.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_CRT_SNAP, jCheckBox21.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_DEL_SNAP, jCheckBox22.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_CRT_VIEW, jCheckBox23.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_DEL_VIEW, jCheckBox24.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_INIT_HOST, jCheckBox25.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_NETBOOT, jCheckBox26.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_RST_ORG_DISK, jCheckBox27.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_LOCAL_DISK_BOOT, jCheckBox28.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_MOD_HOST, jCheckBox29.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_DEL_HOST, jCheckBox30.isSelected() );
        aRight = BackupUser.setThisRight( aRight,BackupUser.RIGHT_SHUTDOWN, jCheckBox31.isSelected() );
        
        return aRight;
    }
    
    public int getNewRight(){
        return newRight;
    }
    
    private void closeBtn_process(){
        newRight = getRight();
        if( user != null ){
            boolean isOk = umDialog.view.initor.mdb.modBackupUser( user.getID(),newRight );
            if( isOk ){
                user.setRight( newRight );
                JOptionPane.showMessageDialog(this,SanBootView.res.getString("RightCustomDialog.error.modRightOk"));
                this.dispose();
                umDialog.setupUserTable();
            }else{
                JOptionPane.showMessageDialog(this,SanBootView.res.getString("RightCustomDialog.error.modRightfailed"));
            }
        }else{
            this.dispose();
        }
    }
    
    private void cancelBtn_process(){
        newRight = -1;
        this.dispose();
    }
}
