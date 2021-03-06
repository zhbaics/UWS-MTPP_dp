/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * QueryUnlimitedIncSnapDialog.java
 *
 * Created on 2010-1-5, 11:23:42
 */

package dataprotect.unlimitedIncMj.ui;

import dataprotect.MenuAndBtnCenterForMainUi;
import dataprotect.data.MirrorDiskInfo;
import dataprotect.remotemirror.BasicCrtMjAction;
import dataprotect.res.ResourceCenter;
import dataprotect.ui.DelSnapAction;
import dataprotect.ui.SanBootView;
import dataprotect.unlimitedIncMj.action.CloneDiskAction;
import dataprotect.unlimitedIncMj.entity.UnlimitedIncMirroredSnap;
import dataprotect.unlimitedIncMj.model.table.CloneDisk;
import dataprotect.unlimitedIncMj.service.GetUISnapGeter;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import mylib.UI.BrowserTable;

/**
 *
 * @author zjj
 */
public class QueryUnlimitedIncSnapDialog extends javax.swing.JDialog {
    public final static int PAGE_SIZE = 33;

    /** Creates new form QueryUnlimitedIncSnapDialog */
    public QueryUnlimitedIncSnapDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public QueryUnlimitedIncSnapDialog( 
        SanBootView view,
        MirrorDiskInfo disk,
        int sum,
        int begin,
        int end,
        CloneDisk newCloneDisk,
        int type,
        boolean isCmdp
    ){
        this( view,true );
        myInit( view,disk,sum,begin,end,newCloneDisk,type,isCmdp );
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(175, 385));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Browser"));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jButton1.setText("First");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel3.add(jButton1, gridBagConstraints);

        jButton2.setText("Prev");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel3.add(jButton2, gridBagConstraints);

        jButton3.setText("Next");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel3.add(jButton3, gridBagConstraints);

        jButton4.setText("Last");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel3.add(jButton4, gridBagConstraints);

        jLabel1.setText("Goto :");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 0);
        jPanel3.add(jLabel1, gridBagConstraints);

        jTextField1.setPreferredSize(new java.awt.Dimension(55, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 0);
        jPanel3.add(jTextField1, gridBagConstraints);

        jButton8.setText("...");
        jButton8.setPreferredSize(new java.awt.Dimension(25, 23));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 0);
        jPanel3.add(jButton8, gridBagConstraints);

        jLabel4.setText("Page Size :");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 0, 0);
        jPanel3.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("宋体", 1, 18)); // NOI18N
        jLabel5.setText("qq");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 0, 0);
        jPanel3.add(jLabel5, gridBagConstraints);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Query Condititon"));
        jPanel4.setPreferredSize(new java.awt.Dimension(175, 197));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Start :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(jLabel2, gridBagConstraints);

        jTextField2.setText("2009");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(jTextField2, gridBagConstraints);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel4.add(jComboBox1, gridBagConstraints);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel4.add(jComboBox2, gridBagConstraints);

        jLabel3.setText("End :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel4.add(jLabel3, gridBagConstraints);

        jTextField3.setText("2009");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(jTextField3, gridBagConstraints);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel4.add(jComboBox3, gridBagConstraints);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel4.add(jComboBox4, gridBagConstraints);

        jButton5.setText("Start");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel4.add(jButton5, gridBagConstraints);

        jPanel1.add(jPanel4, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());
        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 5));

        jButton6.setText("Delete");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton6);

        jButton7.setText("Clone");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton7);

        jButton10.setText("Create CJ");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton10);

        jButton9.setText("Close");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton9);

        jPanel2.add(jPanel6, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        firstBtn_process();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        prevBtn_process();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        nextBtn_process();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        lastBtn_process();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        startQueryBtn_process();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        delBtn_process();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        cloneBtn_process();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        gotoBtn_process();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        crtCJBtn_process();
    }//GEN-LAST:event_jButton10ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QueryUnlimitedIncSnapDialog dialog = new QueryUnlimitedIncSnapDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    SanBootView view;
    MirrorDiskInfo disk;
    int sum;
    int begin;
    int end;
    int page;
    int page_sum;
    int delta ;
    BrowserTable table = new BrowserTable();
    boolean isFirst = true;
    boolean beforeGet = true;
    boolean hasDel = false;
    private final Object lock = new Object();
    private CloneDisk newCloneDisk;
    private int type;
    private boolean isCmdp;

    // 表选择listener,但是要注意add和remove，否则gui上有问题
    ListSelectionListener listener;

    private void myInit(
        SanBootView view,
        MirrorDiskInfo disk,
        int sum,
        int begin,
        int end,
        CloneDisk newCloneDisk,
        int type,
        boolean isCmdp
    ){
        this.view = view;
        this.disk = disk;
        this.sum  = sum;
        this.begin = begin;
        this.end = end;
        this.newCloneDisk = newCloneDisk;
        this.type = type;
        this.isCmdp = isCmdp;

        page = sum/QueryUnlimitedIncSnapDialog.PAGE_SIZE;
        page_sum = page*QueryUnlimitedIncSnapDialog.PAGE_SIZE;
        delta = sum - page_sum;
        if( delta > 0 ){
            page += 1;
        }
        jLabel5.setText( ""+ page );

        jScrollPane1.getViewport().add( table,null );
        jScrollPane1.getViewport().setBackground( Color.white );
        table.addMouseListener( new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                table_mouseClicked(e);
            }

        });

        table.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );

        listener = new ListSelectionListener(){
            public void valueChanged( ListSelectionEvent e ){
                table_mouseClicked( null );
            }
        };

        jPanel1.remove( this.jPanel4 );
        if( type == ResourceCenter.TYPE_UNLIMITED_INC_MIRROR_SNAP1 ){
            jPanel6.remove( this.jButton10 );
        }
        enableButton( false );
        setupTaskTable();
        setupLanguage();
    }

    private void setupLanguage(){
        this.setTitle( SanBootView.res.getString("QueryUnlimitedIncSnapDialog.title") );
        TitledBorder bor = (TitledBorder)jPanel3.getBorder();
        bor.setTitle(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.border.browser"));
        bor = (TitledBorder)jPanel4.getBorder();
        bor.setTitle(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.border.condition"));
        jButton1.setText(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.btn.first")); 
        jButton2.setText(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.btn.prev"));
        jButton3.setText(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.btn.next"));
        jButton4.setText(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.btn.last"));
        jButton5.setText(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.btn.start"));   
        jButton6.setText(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.btn.delete"));
        jButton7.setText(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.btn.clone"));
        jButton9.setText(SanBootView.res.getString("common.button.close"));
        jButton10.setText(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.btn.crtCJ"));
        jLabel1.setText(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.label.goto")+" : ");
        jLabel2.setText(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.label.start")+" : ");
        jLabel3.setText(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.label.end") +" : ");
        jLabel4.setText(SanBootView.res.getString("QueryUnlimitedIncSnapDialog.label.pagesize") +" : ");
    }

    private void table_mouseClicked( MouseEvent e ){
        int row = table.getSelectedRow();
        if( row < 0 ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("BackupHistoryDialog.error.nosel")
            );
            return;
        }
    }

    public void addListSelectionListener(){
        if( listener != null ){
            table.getSelectionModel().addListSelectionListener(
                listener
            );
        }
    }

    private void removeListSelectionListener(){
        if( listener != null ){
            table.getSelectionModel().removeListSelectionListener(
                listener
            );
        }
    }

    public DefaultTableModel getTableModel(){
        return (DefaultTableModel)table.getModel();
    }

    private void setupTaskTable(){
        Object[] title = new Object[]{
            SanBootView.res.getString("View.table.snap.snapid"),
            SanBootView.res.getString("View.table.snap.name"),
            SanBootView.res.getString("View.table.snap.crtTime")
        };
        table.setupTitle( title );

        int[][] widthList = new int[][]{
            {0,100},  {1,165}, {2,155}
        };
        table.setupTableColumnWidth(widthList);
        table.getTableHeader().setBorder( BorderFactory.createRaisedBevelBorder() );
        table.getTableHeader().setReorderingAllowed( false );
    }

    public void enableButton( boolean val ){
        if( isFirst ){
            jButton2.setEnabled( false );
            isFirst = false;
        }else{
            if( getBeforeGet() ){
                this.jButton2.setEnabled( val );
            }else{
                if( begin <=1 ){
                    jButton2.setEnabled( false );
                }else if( begin>=QueryUnlimitedIncSnapDialog.PAGE_SIZE ){
                    jButton2.setEnabled( true );
                }
            }
        }

        if( getBeforeGet() ){
            jButton3.setEnabled( val );
        }else{
            if( table.getRowCount()< QueryUnlimitedIncSnapDialog.PAGE_SIZE ){
                jButton3.setEnabled( false);
            }else{
                if( ( end + 1 ) >= this.sum ){
                    jButton3.setEnabled( false );
                }else{
                    jButton3.setEnabled( true );
                }
            }
        }

        jButton1.setEnabled( val );
        jButton4.setEnabled( val );
        jButton5.setEnabled( val );
        jButton6.setEnabled( val );
        jButton7.setEnabled( val );
        jButton8.setEnabled( val );
        jButton9.setEnabled( val );
        jButton10.setEnabled( val );

        if( !val)
            setDefaultCloseOperation( JDialog.DO_NOTHING_ON_CLOSE );
        else
            setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );

        /*
        if( val ){ // 判断 next btn的下一步状态
            if( begin == 0 && end == ( QueryUnlimitedIncSnapDialog.PAGE_SIZE - 1 ) && table.getRowCount()<=0 ){
                hasDel = false;
                begin = 0;
                end = 0;
                this.jButton3.setEnabled(true);
            }
        }
         */
    }

    private void removeAllRow(){
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        int size = model.getRowCount();
        while( size >0 ){
             model.removeRow( 0);
             size = size -1;
        }
    }

    public void setBeforeGet( boolean val){
        synchronized ( lock ){
            beforeGet = val;
        }
    }

    public boolean getBeforeGet(){
        synchronized ( lock ){
            return beforeGet;
        }
    }

    private void firstBtn_process(){
        removeListSelectionListener();  //暂时去掉，否则会有GUI上的问题
        removeAllRow();

        if( hasDel ){
            hasDel = false;
        }
        begin = 0;
        end = QueryUnlimitedIncSnapDialog.PAGE_SIZE-1;

        setBeforeGet( true );
        enableButton( false );

        GetUISnapGeter geter = new GetUISnapGeter(
            view,
            this,
            disk.getSnap_rootid(),
            begin,
            end,
            type,
            isCmdp
        );
        geter.start();
    }

    private void prevBtn_process(){
        removeListSelectionListener();  //暂时去掉，否则会有GUI上的问题
        removeAllRow();

        if( hasDel ){
            hasDel = false;
        }
        end = begin-1;
        begin = begin-QueryUnlimitedIncSnapDialog.PAGE_SIZE;

        setBeforeGet( true );
        enableButton( false );

        GetUISnapGeter geter = new GetUISnapGeter(
            view,
            this,
            disk.getSnap_rootid(),
            begin,
            end,
            type,
            isCmdp
        );
        geter.start();
    }

    private void startQueryBtn_process(){
        //java.util.GregorianCalendar begin_cal = new java.util.GregorianCalendar();
        //java.util.GregorianCalendar end_cal = new java.util.GregorianCalendar();

        try{
            String _begin_year = this.jTextField2.getText().trim();
            String _end_year = this.jTextField3.getText().trim();

            int begin_year = Integer.parseInt( _begin_year );
            int end_year = Integer.parseInt( _end_year );
            if( begin_year <1975 ){
                JOptionPane.showMessageDialog(view, SanBootView.res.getString("QueryUnlimitedIncSnapDialog.error.invalidYear") );
                return;
            }

            if( end_year <1975 ){
                 JOptionPane.showMessageDialog(view, SanBootView.res.getString("QueryUnlimitedIncSnapDialog.error.invalidYear") );
                 return;
            }

            String _begin_month = (String)this.jComboBox1.getSelectedItem();
            if( _begin_month.length() == 1 ){
                _begin_month="0"+_begin_month;
 SanBootView.log.info(getClass().getName(),"_begin_month: " + _begin_month);
            }
//            int begin_month = Integer.parseInt( _begin_month );
            String _end_month = (String)this.jComboBox3.getSelectedItem();
            if( _end_month.length() == 1 ){
                _end_month="0"+_end_month;
 SanBootView.log.info(getClass().getName(),"_end_month: " + _end_month);
            }
//            int end_month = Integer.parseInt( _end_month );

            String _begin_day = (String)this.jComboBox2.getSelectedItem();
            if( _begin_day.length() == 1 ){
                _begin_day = "0"+_begin_day;
 SanBootView.log.info(getClass().getName(),"_begin_day: " + _begin_day);
            }
//            int begin_day = Integer.parseInt( _begin_day );
            String _end_day = (String)this.jComboBox4.getSelectedItem();
            if( _end_day.length() == 1 ){
                _end_day = "0"+_end_day;
 SanBootView.log.info(getClass().getName(),"_end_day: " + _end_day);
            }
//            int end_day = Integer.parseInt( _end_day );

//            String begin_date = begin_year+_begin_month+_begin_day+"000000";
//            String end_date = end_year+_end_month+_end_day+"235959";


            removeListSelectionListener();  //暂时去掉，否则会有GUI上的问题
            removeAllRow();

            if( hasDel ){
                hasDel = false;
            }else{
                begin = end+1;
                end = end+QueryUnlimitedIncSnapDialog.PAGE_SIZE;
            }

            setBeforeGet( true );
            enableButton( false );

            GetUISnapGeter geter = new GetUISnapGeter(
                view,
                this,
                disk.getSnap_rootid(),
                begin,
                end,
                type,
                isCmdp
            );
            geter.start();
        }catch( Exception ex ){
            JOptionPane.showMessageDialog(view, SanBootView.res.getString("QueryUnlimitedIncSnapDialog.error.invalidYear") );
        }
    }

    private void nextBtn_process(){
        removeListSelectionListener();  //暂时去掉，否则会有GUI上的问题
        removeAllRow();

        if( hasDel ){
            hasDel = false;
        }else{
            begin = end+1;
            end = end+QueryUnlimitedIncSnapDialog.PAGE_SIZE;
        }

        setBeforeGet( true );
        enableButton( false );

        GetUISnapGeter geter = new GetUISnapGeter(
            view,
            this,
            disk.getSnap_rootid(),
            begin,
            end,
            type,
            isCmdp
        );
        geter.start();
    }

    private void lastBtn_process(){
        removeListSelectionListener();  //暂时去掉，否则会有GUI上的问题
        removeAllRow();

        if( hasDel ){
            hasDel = false;
        }
        if( delta >0 ){
            begin = page_sum;
            end = sum;
        }else{
            begin = (page-1)*QueryUnlimitedIncSnapDialog.PAGE_SIZE;
            end = sum;
        }
        
        setBeforeGet( true );
        enableButton( false );

        GetUISnapGeter geter = new GetUISnapGeter(
            view,
            this,
            disk.getSnap_rootid(),
            begin,
            end,
            type,
            isCmdp
        );
        geter.start();
    }

    private void delBtn_process(){
        int[] rows = table.getSelectedRows();
        if( rows == null || rows.length <= 0 ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("BackupHistoryDialog.error.nosel")
            );
            return;
        }

        int snapIdCol = table.getColumn(
            SanBootView.res.getString("View.table.snap.snapid")
        ).getModelIndex();

        removeListSelectionListener();

        Object selObj = table.getValueAt( rows[0], snapIdCol );
        DelSnapAction delSnapAction = new DelSnapAction();
        delSnapAction.setView( view );
        delSnapAction.setTableModel( (DefaultTableModel)table.getModel() );
        delSnapAction.setRow( rows[0] );
        delSnapAction.doAction1( selObj );

        if( delSnapAction.isOK() ){
            hasDel = true;
        }

        // 重新计算下列的值
        sum -=1;
        page = sum/QueryUnlimitedIncSnapDialog.PAGE_SIZE;
        page_sum = page*QueryUnlimitedIncSnapDialog.PAGE_SIZE;
        delta = sum - page_sum;
        if( delta > 0 ){
            page += 1;
        }
        
        addListSelectionListener();
    }

    private void cloneBtn_process(){
        int[] rows = table.getSelectedRows();
        if( rows == null || rows.length <= 0 ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("BackupHistoryDialog.error.nosel")
            );
            return;
        }

        int snapIdCol = table.getColumn(
            SanBootView.res.getString("View.table.snap.snapid")
        ).getModelIndex();

        removeListSelectionListener();

        UnlimitedIncMirroredSnap ui_snap = (UnlimitedIncMirroredSnap)table.getValueAt( rows[0], snapIdCol );
        newCloneDisk.setSrc_inc_mirvol_snap_local_id( ui_snap.snap.getSnap_local_snapid() );
        newCloneDisk.setCrt_time( ui_snap.snap.getSnap_create_time() );
        newCloneDisk.setDefaultDesc();
        
        CloneDiskAction cloneDiskAction = new CloneDiskAction();
        cloneDiskAction.setView( view );
        cloneDiskAction.doAction1( ui_snap,newCloneDisk );
        if( cloneDiskAction.isOK() ){
            if( !cloneDiskAction.isCanceled() ){
                JOptionPane.showMessageDialog(this,
                    SanBootView.res.getString("QueryUnlimitedIncSnapDialog.error.cloneOK")
                );
            }
        }else{
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("QueryUnlimitedIncSnapDialog.error.cloneFail")
            );
        }
       
        addListSelectionListener();
    }

    private void gotoBtn_process(){
        String _page = this.jTextField1.getText().trim();
        int intPage;
        try{
            intPage = Integer.parseInt( _page );
        }catch(Exception ex){
            JOptionPane.showMessageDialog(view, SanBootView.res.getString("QueryUnlimitedIncSnapDialog.error.invalidPage") );
            return;
        }

        if( intPage <=0 ){
            intPage = 1;
        }

        if( intPage > page ){
            intPage = page;
        }

        this.jTextField1.setText( intPage+"" );

        removeListSelectionListener();  //暂时去掉，否则会有GUI上的问题
        removeAllRow();

        if( hasDel ){
            hasDel = false;
        }

        begin = (intPage-1)*QueryUnlimitedIncSnapDialog.PAGE_SIZE;
        end = begin + QueryUnlimitedIncSnapDialog.PAGE_SIZE -1 ;

        setBeforeGet( true );
        enableButton( false );

        GetUISnapGeter geter = new GetUISnapGeter(
            view,
            this,
            disk.getSnap_rootid(),
            begin,
            end,
            type,
            isCmdp
        );
        geter.start();
    }

    private void crtCJBtn_process(){
        int[] rows = table.getSelectedRows();
        if( rows == null || rows.length <= 0 ){
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("BackupHistoryDialog.error.nosel")
            );
            return;
        }

        int snapIdCol = table.getColumn(
            SanBootView.res.getString("View.table.snap.snapid")
        ).getModelIndex();

        removeListSelectionListener();

        UnlimitedIncMirroredSnap ui_snap = (UnlimitedIncMirroredSnap)table.getValueAt( rows[0], snapIdCol );
        BasicCrtMjAction crtCj = new BasicCrtMjAction( MenuAndBtnCenterForMainUi.FUNC_CRT_CJ );
        crtCj.setView( view );
        if( crtCj.realDo( ui_snap ) ){
            if( !crtCj.isCanceled() ){
                JOptionPane.showMessageDialog(this,
                    SanBootView.res.getString("QueryUnlimitedIncSnapDialog.error.crtCjOk")
                );
            }
        }else{
             JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("QueryUnlimitedIncSnapDialog.error.crtCjFailed")
            );
        }
        
        addListSelectionListener();
    }
}
