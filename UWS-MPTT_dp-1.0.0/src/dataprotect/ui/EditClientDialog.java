/*
 * EditSubnetDialog.java
 *
 * Created on 2007/12/14, AM 11:36
 */

package dataprotect.ui;

import dataprotect.data.*;
import dataprotect.res.ResourceCenter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;


/**
 *
 * @author  Administrator
 */
public class EditClientDialog extends javax.swing.JDialog {
    
    /** Creates new form EditSubnetDialog */
    public EditClientDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public EditClientDialog( SanBootView view,DhcpDialog diag,DhcpClientInfo clnt,int selRow ){
        this( view,true );
        myInit( view,diag,clnt,selRow );
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
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(674, 120));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("*MAC :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jLabel1, gridBagConstraints);

        jTextField1.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jTextField1, gridBagConstraints);

        jLabel2.setText("*Host IP :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jTextField2.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel1.add(jTextField2, gridBagConstraints);

        jLabel3.setText("*IBoot Server :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setText("*Subnet :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel5.setText("DNS :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jLabel5, gridBagConstraints);

        jTextField5.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jTextField5, gridBagConstraints);

        jLabel6.setText("Default GW :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

        jTextField6.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel1.add(jTextField6, gridBagConstraints);

        jComboBox1.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jComboBox1, gridBagConstraints);

        jComboBox2.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel1.add(jComboBox2, gridBagConstraints);

        jLabel7.setText("OS Type :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jLabel7, gridBagConstraints);

        jComboBox3.setPreferredSize(new java.awt.Dimension(29, 20));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jComboBox3, gridBagConstraints);

        jLabel8.setText("OS Target ID :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel1.add(jLabel8, gridBagConstraints);

        jTextField3.setPreferredSize(new java.awt.Dimension(72, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel1.add(jTextField3, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jSeparator1, java.awt.BorderLayout.NORTH);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        jButton1.setText("OK");
        jButton1.setPreferredSize(new java.awt.Dimension(75, 24));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton2.setText("Cancel");
        jButton2.setPreferredSize(new java.awt.Dimension(75, 24));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        okButton();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        values = null;
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
// TODO add your handling code here:
    do_OStypeCombobox();
}//GEN-LAST:event_jComboBox3ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditSubnetDialog(new javax.swing.JFrame(), true).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
 
    DhcpClientInfo clnt;
    SanBootView view;
    DhcpDialog diag;
    int selRow;
    Object[] values;
    
    private void myInit( SanBootView _view,DhcpDialog _diag,DhcpClientInfo _clnt,int _selRow ){
        view   = _view;
        diag   = _diag;
        clnt   = _clnt;
        selRow = _selRow;
        
        setupIbootSrvCombox();
        setupSubnetCombox();
        setupOsTypeCombox();
        
        if( clnt != null ){
            jTextField1.setText( clnt.mac );
            jTextField2.setText( clnt.ip );
            jTextField5.setText( clnt.getDNS() );
            jTextField6.setText( clnt.getDefGw() );
            String ostype = clnt.getOSType();
SanBootView.log.debug(getClass().getName(), " ostype: "+ostype );            
            if( ostype.toUpperCase().startsWith("WIN") ){
                jComboBox3.setSelectedItem( ResourceCenter.OS_WIN );
            }else if( ostype.toUpperCase().startsWith("LIN")){
                jComboBox3.setSelectedItem( ResourceCenter.OS_LINUX );
            }else{
                jComboBox3.setSelectedItem( ResourceCenter.OS_WIN );
            }
            jComboBox1.setSelectedItem( clnt.nextServer.trim() );
            jComboBox2.setSelectedItem( clnt.subnet.trim() );
            if( clnt.os_tid.length() != 0 ){
                // iboot rapid booting
                jTextField3.setText( clnt.os_tid+"" );
            }
            this.do_OStypeCombobox();
        }
        
        regKeyboardAction();
        setupLanguage();
    }
    
    void setupLanguage(){
        this.setTitle( SanBootView.res.getString("EditSubnetDialog.title.edit2"));
        this.jLabel1.setText( SanBootView.res.getString("EditSubnetDialog.label.mac"));
        this.jLabel2.setText( SanBootView.res.getString("EditSubnetDialog.label.ip"));
        this.jLabel3.setText( SanBootView.res.getString("EditSubnetDialog.label.ibootsrv"));
        this.jLabel4.setText( SanBootView.res.getString("EditSubnetDialog.label.subnet"));
        this.jLabel5.setText( SanBootView.res.getString("EditSubnetDialog.label.dns"));
        this.jLabel6.setText( SanBootView.res.getString("EditSubnetDialog.label.defgw"));
        this.jLabel7.setText( SanBootView.res.getString("EditSubnetDialog.label.ostype"));
        this.jLabel8.setText( SanBootView.res.getString("EditSubnetDialog.label.ostid"));
        jButton1.setText( SanBootView.res.getString("common.button.ok"));
        jButton2.setText( SanBootView.res.getString("common.button.cancel"));
    }
    
    private void do_OStypeCombobox(){
        String type = (String)this.jComboBox3.getSelectedItem();
        if( type.equals( ResourceCenter.OS_WIN ) ){
            this.jTextField3.setEnabled( true );
            this.jLabel8.setEnabled( true );
        }else{
            this.jTextField3.setEnabled( false );
            this.jLabel8.setEnabled( false );
        }
    }
    
    private void regKeyboardAction(){            
        jTextField1.registerKeyboardAction(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    jTextField2.requestFocusInWindow();
                }
            },
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
        
        jTextField2.registerKeyboardAction(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    jComboBox1.requestFocus();
                }
            },
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
          
        jComboBox1.registerKeyboardAction(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    jComboBox2.requestFocus();
                }
            },
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
        
        jComboBox2.registerKeyboardAction(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    jTextField5.requestFocus();
                }
            },
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
            
        jTextField5.registerKeyboardAction(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    jTextField6.requestFocus();
                }
            },
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
        
        jTextField6.registerKeyboardAction(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    jTextField3.requestFocus();
                }
            },
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }
    
    void setupIbootSrvCombox(){
        Vector list = view.initor.dhcpdb.getIbootSrvListFromDhcp();
        int size = list.size();
        for( int i=0; i<size; i++ ){
            this.jComboBox1.addItem( ((DhcpIBootSrv)list.elementAt( i)).ip );
        }
    }
    
    void setupSubnetCombox(){
        Vector list = view.initor.dhcpdb.getSubnetListFromDhcp();
        int size = list.size();
        for( int i=0; i<size; i++ ){
            this.jComboBox2.addItem( ((SubNetInDHCPConf)list.elementAt( i)).subnet );
        }
    }
    
    void setupOsTypeCombox(){
        jComboBox3.addItem( ResourceCenter.OS_WIN );
        jComboBox3.addItem( ResourceCenter.OS_LINUX );
    }
    
    void okButton(){
//        InetAddress ip=null,dns=null,defgw=null;
        
        String macStr = jTextField1.getText().trim();
        String ipStr = jTextField2.getText().trim();
        String dnsStr = jTextField5.getText().trim();
        String defgwStr = jTextField6.getText().trim();
        
        String ibootSrv_ip = (String)jComboBox1.getSelectedItem();
        String subnet = (String)jComboBox2.getSelectedItem();
        String osType = (String)jComboBox3.getSelectedItem();
        if( osType.equals( ResourceCenter.OS_WIN ) ){
            osType = "windows";
        }else if( osType.equals( ResourceCenter.OS_LINUX ) ){
            osType = "linux";
        }else{
            osType = "windows";
        }
        
        // 以下格式均有效
        // 00:02:B3:DB:0A:B0
        // 00-02-B3-DB-0A-B0 
        // 0002B3DBOABO
        if( !DhcpClientInfo.isValidMAC( macStr ) ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("EditSubnetDialog.errMsg.invalidMAC")
            );
            return;
        }
        
        String macStr1 = DhcpClientInfo.getMacStr( macStr );
System.out.println(" changed mac: "+ macStr1 );         
        if( diag.isSameClnt( macStr1 ) ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("EditSubnetDialog.errMsg.sameMac") +" "+ macStr1
            );
            return;
        }
         
        if ( !checkOneClnt( ipStr,dnsStr,defgwStr ) ){
            return;
        }
        
        String strOsTid = jTextField3.getText().trim();
        if( osType.equals("windows") && strOsTid.length() != 0 ){
            int osTid;
            try{
                osTid = Integer.parseInt( strOsTid );
            }catch( Exception ex ){
                JOptionPane.showMessageDialog(this,
                    SanBootView.res.getString("EditSubnetDialog.errMsg.badOStid") +" : "+ strOsTid
                );
                return;
            }
            if( osTid <=0 ){
                JOptionPane.showMessageDialog(this,
                    SanBootView.res.getString("EditSubnetDialog.errMsg.badOStid") +" : "+ strOsTid
                );
                return;
            }
        }else{
            if( !osType.equals("windowns") ){
                strOsTid = "";
            }
        }
        
        values = new Object[8];
        values[0] = ipStr;
        values[1] = macStr1;
        values[2] = subnet;
        values[3] = ibootSrv_ip;
        values[4] = dnsStr;
        values[5] = defgwStr;
        values[6] = osType;
        values[7] = strOsTid;
        
        this.dispose();
    }
    
    public Object[] getValues(){
        return values;
    }
    
    private boolean checkOneClnt( String ipStr,String dnsStr,String defgwStr ){
//        InetAddress ip,dns,defgw;
        
        if( ipStr.length() == 0 ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("EditSubnetDialog.errMsg.noneHostIP")
            );
            return false;
        }
        
        try{
            InetAddress ip = InetAddress.getByName( ipStr );
SanBootView.log.debug(getClass().getName(), " InetAddressIP: "+ ip );
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("EditSubnetDialog.errMsg.invalidHostIP") +" : "+ipStr
            );
            return false;
        }
        
        if( dnsStr.length() != 0 ){
            try{
                InetAddress ip = InetAddress.getByName( dnsStr );
SanBootView.log.debug(getClass().getName(), " InetAddressIP: "+ ip );
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,
                    SanBootView.res.getString("EditSubnetDialog.errMsg.invalidDNS") + " : "+dnsStr
                );
                return false;
            }
        }
        
        if( defgwStr.length() != 0 ){
            try{
                InetAddress ip = InetAddress.getByName( defgwStr );
SanBootView.log.debug(getClass().getName(), " InetAddressIP: "+ ip );
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,
                    SanBootView.res.getString("EditSubnetDialog.errMsg.invalidDefGw") + " : "+defgwStr
                );
                return false;
            }
        }
        
        return true;
    }
}
