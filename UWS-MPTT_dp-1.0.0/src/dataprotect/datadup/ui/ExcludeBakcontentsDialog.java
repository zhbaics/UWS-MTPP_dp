/*
 * ExcludeBakcontentsDialog.java
 *
 * Created on July 29, 2008, 12:01 PM
 */

package dataprotect.datadup.ui;

import dataprotect.datadup.data.BackupClient;
import dataprotect.res.ResourceCenter;
import dataprotect.ui.SanBootView;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;


/**
 *
 * @author  jjzhang
 */
public class ExcludeBakcontentsDialog extends javax.swing.JDialog implements BrowseBakedFile {
    
    /** Creates new form ExcludeBakcontentsDialog */
    public ExcludeBakcontentsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public ExcludeBakcontentsDialog(Dialog parent,boolean modal){
        super( parent,modal );
        initComponents();
    }
    
    public ExcludeBakcontentsDialog(
        Dialog parent,
        BackupClient cli,
        SanBootView view,
        Vector roots,
        Vector selFileList,
        ArrayList excludeList
    ){
        this( parent,true );
        myInit( cli,view,roots,selFileList,excludeList );
    }
     
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" ��ɵĴ��� ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(new javax.swing.border.TitledBorder("Selected to backup"));
        jScrollPane1.setBackground(java.awt.Color.white);
        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(250, 10));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(new javax.swing.border.TitledBorder("Excluded list"));
        jScrollPane2.setBackground(java.awt.Color.white);
        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(100, 0));
        jButton1.setText("Add");
        jButton1.setPreferredSize(new java.awt.Dimension(55, 24));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(jButton1, gridBagConstraints);

        jButton2.setText("Remove");
        jButton2.setMargin(new java.awt.Insets(2, 1, 2, 1));
        jButton2.setPreferredSize(new java.awt.Dimension(81, 24));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel4.add(jButton2, gridBagConstraints);

        jButton4.setText("Cancel");
        jButton4.setPreferredSize(new java.awt.Dimension(69, 24));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel4.add(jButton4, gridBagConstraints);

        jButton3.setText("Done");
        jButton3.setMargin(new java.awt.Insets(2, 1, 2, 1));
        jButton3.setPreferredSize(new java.awt.Dimension(81, 24));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel4.add(jButton3, gridBagConstraints);

        jButton5.setText("Stop");
        jButton5.setPreferredSize(new java.awt.Dimension(59, 24));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel4.add(jButton5, gridBagConstraints);

        jPanel2.add(jPanel4, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jTextField1.setEditable(false);
        jPanel5.add(jTextField1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel5, java.awt.BorderLayout.NORTH);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if( fetcher!=null ){
            // 根据fs list从复制内容里筛选出要复制的fs(只需通信一次)
            fetcher.setDestroyFlag( true );
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    // finish button process
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ArrayList<String> ret = getExcludeContent();
        StringBuffer filelist_sb = new StringBuffer("");
        int size = ret.size();
        if( size > 0 ){
            for( int i=0;i<size;i++ ){
                filelist_sb.append(ret.get( i )).append(";");       //filelist += (String)ret.get( i ) + ";";
            }
            String filelist = filelist_sb.toString();
            if( filelist.getBytes().length >= ResourceCenter.CONTENT_SIZE ){
                JOptionPane.showMessageDialog(this,
                    SanBootView.res.getString("ExcludeBakcontentsDialog.errMsg.tooLongBakContent")
                );
                return;
            }
        }
        
        value = ret;
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed
         
    public Object getValues(){
        return value;
    }
    
    // cancel button process
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        value = null;
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed
    
    // remove button process
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int[] indexList = excludeList.getSelectedIndices();
        if( indexList == null || indexList.length<=0 ) {
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("ExcludeBakcontentsDialog.errmsg.notselected")
            );
            return;
        }
        
        for( int i =indexList.length-1; i>=0 ;i-- ){
            model2.removeElementAt( indexList[i] );
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    
    // add button process
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String selfile;
        
        int[] indexs = selFileList.getSelectedIndices();
        if( indexs == null || indexs.length<=0 ) {
            JOptionPane.showMessageDialog(this,
                SanBootView.res.getString("ExcludeBakcontentsDialog.erorr.notSel")
            );
            return;
        }
        
        for( int i=0; i<indexs.length; i++ ){
            if( indexs[i]>=0 ){
                String element = (String)model1.getElementAt( indexs[i] );
                if( element.startsWith("ERROR") ){
                    JOptionPane.showMessageDialog(this,
                        SanBootView.res.getString("ExcludeBakcontentsDialog.error.badFile")
                    );
                    return;
                }
                
                if( element.equals("../") ){
                    JOptionPane.showMessageDialog(this,
                        SanBootView.res.getString("ExcludeBakcontentsDialog.error.badsel")
                    );
                    return;
                }
                
                if( !element.startsWith("/") ){
                    selfile = curPath+element;
                }else{
                    selfile = element;
                }
SanBootView.log.debug(getClass().getName(), "Selected file(add file): "+selfile);
                
                if( !isExist( selfile ) ){
                    if( isSelected1( selfile ) ){
                        JOptionPane.showMessageDialog(this,
                            SanBootView.res.getString("ExcludeBakcontentsDialog.error.subdirexist")
                        );
                        return;
                    }
                    
                    // 判断是否超过了长度限制
                    // 每个文件后面跟一个";",所以要多加1
                    totalChar +=( selfile.getBytes().length+1 );
            
                    if( totalChar >=ResourceCenter.CONTENT_SIZE ){
                        JOptionPane.showMessageDialog(this,
                            SanBootView.res.getString("ExcludeBakcontentsDialog.errMsg.tooLongBakContent")
                        );
                        totalChar -= ( selfile.getBytes().length+1 );
                        return;
                    }
                     
                    model2.addElement( selfile );
                }else{
                    JOptionPane.showMessageDialog(this,
                        SanBootView.res.getString("ExcludeBakcontentsDialog.error.sameFile")+
                        " "+selfile
                    );
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new ExcludeBakcontentsDialog(new javax.swing.JFrame(), true).setVisible( true );
    }
    
    // �������� - �������޸�//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    // ������������//GEN-END:variables
    
    String curPath  = "/";
    String rootPath = "/";
    SanBootView view;
    BackupClient cli;
    Vector selFilesList;
    ArrayList excludesList;
    DefaultListModel model1 = new DefaultListModel();
    JList selFileList = new JList( model1 );
    DefaultListModel model2 = new DefaultListModel();
    JList excludeList = new JList( model2 );
    private Vector roots;  // 存放所有的文件系统(从外面传进来的)
    FileListFetcher fetcher;
    int totalChar = 0; // 所选文件的总字节数
    Object value = null;

    private void myInit( BackupClient _cli,SanBootView _view, Vector _roots, Vector _selFilesList,ArrayList _excludesList ){
        cli = _cli;
        view = _view;
        roots = _roots;
        selFilesList = _selFilesList;
        excludesList = _excludesList;
        
        setupLanguage();
        
        this.jScrollPane1.getViewport().add( selFileList,null );
        selFileList.setCellRenderer( new FileDirRenderer() );
        this.jScrollPane2.getViewport().add( excludeList,null);
        excludeList.setCellRenderer( new FileDirRenderer() );
        
        selFileList.addMouseListener( new MouseAdapter(){
            public void mouseClicked( MouseEvent e){
                doubleClickPrcess(e);
            }
        });
        
        setupSelFileList();
        setupExcludeList();   
    }
    
    private void setupLanguage(){
        setTitle(SanBootView.res.getString("ExcludeBakcontentsDialog.diagTitle"));
        
        jButton1.setText(SanBootView.res.getString("common.button.add1"));
        jButton1.setIcon( ResourceCenter.BTN_ICON_ADD );
        jButton2.setText(SanBootView.res.getString("common.button.del"));
        jButton2.setIcon( ResourceCenter.BTN_ICON_REMOVE );
        jButton3.setText(SanBootView.res.getString("common.button.finish"));
        jButton4.setText( SanBootView.res.getString("common.button.cancel"));
        jButton5.setText( SanBootView.res.getString("common.button.stop"));
        
        TitledBorder bor = (TitledBorder)jPanel1.getBorder();
        bor.setTitle(
            SanBootView.res.getString("ExcludeBakcontentsDialog.border.selFiles") 
        );
        
        bor = (TitledBorder)jPanel3.getBorder();
        bor.setTitle(
            SanBootView.res.getString("ExcludeBakcontentsDialog.border.exclude")
        );
    }
    
    // BrowseBakedFile interface
    public void setEnabledTF1(){
    }
     
    Cursor oldCursor;
    public void setWaitCursor( ){
        oldCursor = this.getCursor();
        this.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR )  );
    }
    
    public void resotreCursor(){
        this.setCursor( oldCursor );
    }
    
    private void setupSelFileList(){
        int size = selFilesList.size();
        for( int i=0;i<size;i++ ){
            model1.addElement( selFilesList.elementAt(i));
        }
    }
    
    private void setupExcludeList(){
        int size = excludesList.size();
        for( int i= 0;i<size; i++){
            model2.addElement( excludesList.get( i ) );
        }
    }
    
    private void doubleClickPrcess(  MouseEvent  e ){
        String tmpStr;
        
        if( e.getClickCount() >= 2 ){
            if( fetcher!=null && !fetcher.isOver() ){
SanBootView.log.warning( getClass().getName()," already has a fetcher to get files, please wait a moment....");
                return;
            }
            
            int index = selFileList.locationToIndex( e.getPoint() );
            String file = (String)model1.getElementAt( index );
System.out.println(" curPath: "+ curPath + " selfile: "+file );
            
            if( file.equals("../") ){
                if( isTop( curPath ) ){
                    displayRoot();
                    jTextField1.setText( "" );
                    return;
                }else{
                    curPath = this.getFatherPath();
System.out.println(" curPath(getFatherPath): " + curPath );
                }
            }else if( isDir( file ) ){
                // 检查 curPath是否已经被选择备份了
                if( file.startsWith("/") ){
                    tmpStr = file;
                }else{
                    tmpStr = curPath + file;
                }
                
                if( isSelected( tmpStr ) ){
                    JOptionPane.showMessageDialog(this,
                        SanBootView.res.getString("ExcludeBakcontentsDialog.error.alreadySeled")
                    );
                    return;
                }
                
                curPath = tmpStr;
System.out.println(" curPath(isDir): "+curPath );
            }else{
                return;
            }
            
            jTextField1.setText( curPath );
            model1.removeAllElements();
            
            fetcher = new FileListFetcher( 
                cli, 
                this.curPath, 
                model1, 
                null,
                this,
                view.getSocket(),
                false,
                0,
                false
            );
            fetcher.start();
        }
    }
    
    private boolean isSelected( String path ){
        int size = model2.getSize();
        for( int i=0; i<size; i++ ){
            String file = (String)model2.elementAt(i);
            if( file.equals( path ) ){
                return true;
            }
        }
        
        return false;
    }
    
    private boolean isTop( String dir ){
        int size = selFilesList.size();
        for( int i=0; i<size; i++ ){
            String one = (String)selFilesList.elementAt(i);
            if( one.equals( dir ) ){
                return true;
            }
        }
        return false;
    }
    
    private void displayRoot(){
        model1.removeAllElements();
        
        int size = selFilesList.size();
SanBootView.log.debug( getClass().getName(), " root item size: " + size );        
        for( int i=0; i<size; i++ ){
            String one = (String)selFilesList.elementAt(i);
SanBootView.log.debug( getClass().getName(), " root item ====>: " + one );   
            model1.addElement( one );
        }
    }

//    private boolean isRoot( String dir ){
//        int size = roots.size();
//        for( int i=0; i<size; i++ ){
//            String one = (String)roots.elementAt(i);
//            if( one.equals( dir ) ){
//                return true;
//            }
//        }
//        return false;
//    }
    
    private boolean isDir( String file ){
        int length = file.length();
        char tmp = file.charAt( length-1 );
        if( (tmp == '/')|| (tmp == '\\') )
            return true;
        else
            return false;
    }
    
    private String getFatherPath(){
        String ret = "/";
        
        int index = this.curPath.lastIndexOf("/");
        if( index >=0 ){
            try{
                ret = this.curPath.substring( 0,index );
                index = ret.lastIndexOf("/");
                if( index >=0 ){
                    ret = ret.substring(0,index)+"/";
                }
            }catch(Exception ex){
                ret = "/";
            }
        }
        
        return ret;
    }
    
    boolean isSort = false;
    Object[] sortedFsList;
    private String getRootFsFromPath( String path ){
        if( !isSort ){
//System.out.println(" sort rootfs size: "+ roots.size() ); 
            sortedFsList = roots.toArray();
            Arrays.sort( sortedFsList );
            isSort = true;
        }
        
        int len = sortedFsList.length;
        for( int i=len-1; i>=0; i-- ){
            String rootfs = (String)sortedFsList[i];          
            if( path.startsWith( rootfs ) ){
                return rootfs;
            }
        }
        
        return "/";
    }
    
    public void saveRoots(){
    }
    
    public boolean isExist( String file ){
        int size = model2.getSize();
        for( int i=0;i<size;i++ ){
            String item = (String) model2.getElementAt(i);
            if( item.equals( file ) ){
                return true;
            }
        }
        
        return false;
    }
    
    private boolean isSelected1( String path ){
//System.out.println(" isSelected1 is called [path]: " + path);
        boolean isWin = cli.isWin();
        
        int size = model2.getSize();
        for( int i=0; i<size; i++ ){
            String selFile = (String)model2.elementAt(i);
//System.out.println( " compared file(isSelected1): " + selFile );
            
            if( isWin ){
                if( selFile.toUpperCase().startsWith( path.toUpperCase() ) ){
                    int len = path.length();
                    if( path.charAt( len-1 ) =='/' ){
                        return true;
                    }else{
                        if( selFile.charAt( len ) == '/' ){
                            return true; 
                        }
                    }
                }else{
                    if( path.toUpperCase().startsWith( selFile.toUpperCase() ) ){
                        int len = selFile.length();
                        if( selFile.charAt( len-1 ) =='/' ){
                            return true;
                        }else{
                            if( path.charAt( len ) == '/' ){
                                return true; 
                            }
                        }
                    }
                }
            }else{  // linux platform
                if( selFile.equals("/") ){
                    String rfs = getRootFsFromPath( path );
                    if( rfs.equals("/") ){
                        return true;
                    }
                    continue;
                }
                
                if( path.equals("/") ){
                    // 检查file属于哪个fs
                    String rfs = getRootFsFromPath( selFile );
//System.out.println(" / is selected, " + " compared file: "+ selFile ); 
                    if( rfs.equals("/") ){
                        return true;
                    }
                    continue;
                }
                
                if( selFile.startsWith( path ) ){
                    int len = path.length();
                    if( path.charAt( len-1 ) =='/' ){
                        return true;
                    }else{
                        if( selFile.charAt( len ) == '/' ){
                            return true; 
                        }
                    }
                }else{
                    if( path.startsWith( selFile ) ){
                        int len = selFile.length();
                        if( selFile.charAt( len-1 ) =='/' ){
                            return true;
                        }else{
                            if( path.charAt( len ) == '/' ){
                                return true; 
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    private ArrayList<String> getExcludeContent(){
        int size = model2.getSize();
        ArrayList<String> ret = new ArrayList<String>(size);
        for( int i=0;i<size;i++ ){
            ret.add( (String)model2.elementAt( i) );
        }
        return ret;
    }
}