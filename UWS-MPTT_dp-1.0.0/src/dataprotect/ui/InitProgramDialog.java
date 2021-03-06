/*
 * InitProgramDialog.java
 *
 * Created on January 14, 2005, 5:05 PM
 */

package dataprotect.ui;

import javax.swing.*;

/**
 *
 * @author  Administrator
 */
public class InitProgramDialog extends javax.swing.JDialog {
    
    /** Creates new form InitProgramDialog */
    public InitProgramDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public InitProgramDialog( SanBootView view,String title,String label ){
        this( view,true );
        myInit( title,label );
    }

//    public InitProgramDialog( JDialog diag,String title,String label ){
//        super( diag,true );
//        myInit( title,label );
//    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" 生成的代码 ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Initializing the program, please wait a moment ...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel1, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));

        jPanel2.setPreferredSize(new java.awt.Dimension(10, 40));
        jProgressBar1.setPreferredSize(new java.awt.Dimension(250, 20));
        jPanel2.add(jProgressBar1);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new InitProgramDialog(new javax.swing.JFrame(), true).setVisible( true );
    }
    
    // 变量声明 - 不进行修改//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    // 变量声明结束//GEN-END:variables
    
    private void myInit( String title,String label ){
        setupLanguage( title,label );
        setDefaultCloseOperation( JDialog.DO_NOTHING_ON_CLOSE );
        jProgressBar1.setStringPainted( true );
        jProgressBar1.setValue( 0 );
        jProgressBar1.setString( "" );
    }
    
    private void setupLanguage( String title,String label ){    
        setTitle( title );
        jLabel1.setText( label );
    }
    
    public void setProcess( int val,String status ){
        jProgressBar1.setValue( val );
        jProgressBar1.setString( status +" [" + val + "%]" );
    }
}
