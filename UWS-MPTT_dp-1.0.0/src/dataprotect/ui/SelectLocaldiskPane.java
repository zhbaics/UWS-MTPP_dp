/*
 * SelectSnapshotPane.java
 *
 * Created on 2006/12/29,�AM�9:52
 */

package dataprotect.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.util.regex.*;
import dataprotect.data.*;
import dataprotect.res.ResourceCenter;
import dataprotect.ui.multiRenderTable.*;

/**
 *
 * @author  Administrator
 */
public class SelectLocaldiskPane extends javax.swing.JPanel {
    
    /** Creates new form SelectBootHostPane */
    public SelectLocaldiskPane() {
        initComponents();
    }

    public SelectLocaldiskPane( SanBootView view,DestAgent host ) {
        this( view,host,ResourceCenter.CMD_TYPE_MTPP );
    }

    public SelectLocaldiskPane( SanBootView view,DestAgent host,int mode ) {
        this();
        myInit( view,host,mode );
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(20, 10));
        add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setPreferredSize(new java.awt.Dimension(20, 10));
        add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(10, 70));
        jScrollPane1.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        jTextArea1.setLineWrap(true);
        jTextArea1.setDisabledTextColor(java.awt.Color.black);
        jTextArea1.setEnabled(false);
        jTextArea1.setOpaque(false);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setPreferredSize(new java.awt.Dimension(10, 25));
        jPanel3.add(jPanel6, java.awt.BorderLayout.SOUTH);

        add(jPanel3, java.awt.BorderLayout.CENTER);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    ////GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    ////GEN-END:variables
    
    JTableY table;
    SanBootView view;
    DestAgent host;
    int mode;
    Object[] header;
    RowEditorModel rm0 = new RowEditorModel();
    VolumeMap osVol = null;
    
    private void myInit( SanBootView _view,DestAgent _host,int mode ){
        view = _view;
        host = _host;
        this.mode = mode;
        
        jTextArea1.setText(
            SanBootView.res.getString("FailbackWizardDialog.tip2")
        );
    }
     
    public void setupTableNotFromMappingTable( Vector list  ){
        VolumeMap volMap;
        VolumeMapWrapper wrapper;
        JComboBox cb; 
        JCheckBox cbox;
        DefaultCellEditor ed;
        CheckBoxEditor ced;
        int i;
        Object[][] data;
        
        int num = list.size();
        data = new Object[num][3]; 
        header = new Object[3]; //  restore?, driverLetter, dest
        
        header[0] = SanBootView.res.getString("SelectRestoreDiskPane.table.restore");
        header[1] = SanBootView.res.getString("SelectRestoreDiskPane.table.volMap");
        header[2] = SanBootView.res.getString("SelectRestoreDiskPane.table.dest");
        
        for( i=0; i<num; i++ ){
            volMap = (VolumeMap)list.elementAt(i);
            wrapper = new VolumeMapWrapper( );
            wrapper.volMap = volMap;
            
            data[i][0] = Boolean.TRUE;
            data[i][1] = wrapper;
            data[i][2] = "";
        }
        
        MyDefaultTableModelForTabY model = new MyDefaultTableModelForTabY( data,header,null );
        
        table = new JTableY( model );
        table.setRowHeight( 20 );
        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        
        ced = new CheckBoxEditor();
        rm0.addEditorForRow( 0, ced );
        table.setCol0EditorModel( rm0 );
        
        TableColumnModel tableColumnModel = table.getColumnModel();
        int colNum = tableColumnModel.getColumnCount();
        tableColumnModel.getColumn(0).setWidth( 150 );
        tableColumnModel.getColumn(1).setWidth( 150 );
        tableColumnModel.getColumn(2).setWidth( 150 );
        for( i=0;i<colNum;i++ )
            table.sizeColumnsToFit(i);

        table.getTableHeader().setBorder( BorderFactory.createRaisedBevelBorder() );
        table.getTableHeader().setReorderingAllowed(false);
        
        jScrollPane2.getViewport().add( table,null );
        jScrollPane2.getViewport().setBackground( Color.white );
    }
    
    public void setupTableFromMappingTable( Vector list  ){
        RestoreMapper mapper;
        
        VolumeMap volMap;
        VolumeMapWrapper wrapper;
        JComboBox cb; 
        JCheckBox cbox;
        DefaultCellEditor ed;
        CheckBoxEditor ced;
        int i;
        Object[][] data;
        
        int num = list.size();
        data = new Object[num][3]; 
        header = new Object[3]; //  restore?, driverLetter, dest
        
        header[0] = SanBootView.res.getString("SelectRestoreDiskPane.table.restore");
        header[1] = SanBootView.res.getString("SelectRestoreDiskPane.table.volMap");
        header[2] = SanBootView.res.getString("SelectRestoreDiskPane.table.dest");
        
        for( i=0; i<num; i++ ){
            mapper = (RestoreMapper)list.elementAt(i);
            
            data[i][0] = Boolean.TRUE;
            data[i][1] = mapper.getSrc();
            data[i][2] = mapper.getDest();
        }
        
        MyDefaultTableModelForTabY model = new MyDefaultTableModelForTabY( data,header,null );
        
        table = new JTableY( model );
        table.setRowHeight( 20 );
        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        
        ced = new CheckBoxEditor();
        rm0.addEditorForRow( 0, ced );
        table.setCol0EditorModel( rm0 );
        
        TableColumnModel tableColumnModel = table.getColumnModel();
        int colNum = tableColumnModel.getColumnCount();
        tableColumnModel.getColumn(0).setWidth( 150 );
        tableColumnModel.getColumn(1).setWidth( 150 );
        tableColumnModel.getColumn(2).setWidth( 150 );
        for( i=0;i<colNum;i++ )
            table.sizeColumnsToFit(i);

        table.getTableHeader().setBorder( BorderFactory.createRaisedBevelBorder() );
        table.getTableHeader().setReorderingAllowed(false);
        
        jScrollPane2.getViewport().add( table,null );
        jScrollPane2.getViewport().setBackground( Color.white );
    }
    
    public boolean toRecoverOSDisk(){
        VolumeMapWrapper wraper;
        Object val;
        String src;
        
        MyDefaultTableModelForTabY model = (MyDefaultTableModelForTabY)table.getModel();
        int lineNum = model.getRowCount();
        for( int row=0; row<lineNum; row++ ){
        
            val = model.getValueAt(row,1);
            if( val instanceof VolumeMapWrapper ){
                wraper = (VolumeMapWrapper)val;
                if( wraper.volMap.getVolDiskLabel().toUpperCase().equals("C:\\") ){
                    return ((Boolean)model.getValueAt(row, 0 )).booleanValue();
                }
            }else{
                src = (String)val;
                if( src.toUpperCase().equals("C") ){
                    return ((Boolean)model.getValueAt(row, 0 )).booleanValue();
                }
            }
        }
        
        return false;
    }
    
    public Vector getSelectedLocaldisk(){
        RestoreMapper mapper;
        VolumeMapWrapper wraper;
        DestDevice dest;
        Object val;
        boolean isSel;
        
        MyDefaultTableModelForTabY model = (MyDefaultTableModelForTabY)table.getModel();
        int lineNum = model.getRowCount();
        Vector ret = new Vector( lineNum );
        
        for( int row=0; row<lineNum; row++ ){
            isSel = ((Boolean)model.getValueAt( row, 0 )).booleanValue();
            if( !isSel ) continue;

            mapper = new RestoreMapper();
            val = model.getValueAt( row,1 );
            if( val instanceof VolumeMapWrapper ){
                wraper = (VolumeMapWrapper)val;
                mapper.setSrc( wraper.volMap.getVolDiskLabel().toUpperCase().substring( 0,1 ) );
            }else{
                mapper.setSrc( (String)val );
            }

            val = model.getValueAt( row,2 );
            if( val instanceof DestDevice ){
                mapper.setDest( (DestDevice)model.getValueAt( row,2 ) );
            }else{
                dest = new DestDevice( (String)val, "" ); // 用户输入的
                mapper.setDest( dest );
            }

            ret.addElement( mapper );
        }
        
        return ret;
    }
    
    // 下面的检查确保恢复目的都是本地盘
    public boolean checkInfoValidity(){
        boolean isSel,isOk;
        String src,dest,ip;
        Object val;
        int port;
        Matcher matcher;
        VolumeMapWrapper wraper;
        
        port = host.getPort();
        ip = host.getIP();
        
        Pattern pattern = Pattern.compile("^[a-zA-Z]$");
         
        MyDefaultTableModelForTabY model = (MyDefaultTableModelForTabY)table.getModel();
        int lineNum = model.getRowCount();
        for( int row=0; row<lineNum; row++ ){
            isSel = ((Boolean)model.getValueAt( row, 0 )).booleanValue();
            if( isSel ){
                val = model.getValueAt( row,2 );
                if( val instanceof DestDevice ){
                    dest = ((DestDevice)val).getMp();
                }else{
                    dest = (String)val;
                }
                
                matcher = pattern.matcher( dest );
                if( !matcher.find() ){
                    JOptionPane.showMessageDialog(this,
                        SanBootView.res.getString("SelectRestoreDiskPane.error.invalidVal") + " ["+row+",2]"
                    );
                    return false;
                }
                
                if( !badDestDriver( dest ) ){
                    JOptionPane.showMessageDialog(this,
                        SanBootView.res.getString("SelectRestoreDiskPane.error.rstOntoSelf") + " ["+row+",2]"
                    );
                    return false;
                }
                
                val = model.getValueAt( row, 1 );
                if( val instanceof VolumeMapWrapper ){
                    wraper = (VolumeMapWrapper)val;
                    src = wraper.volMap.getVolDiskLabel().toUpperCase().substring( 0,1 );
                }else{
                    src = (String)val;
                }
                
                // 判断dest是否为本地盘；如果为iSCSI则报错
                if( src.toUpperCase().equals("C") ){
                    // 不检查OS对应的恢复目的盘，因为该盘符已经不存在了
                    continue;
                }
                
                isOk = view.initor.mdb.isStartupfromSAN( ip,port,dest,mode );
                if( isOk ){
                    if( view.initor.mdb.isStartupFromSAN() ){
                        JOptionPane.showMessageDialog(this,
                            SanBootView.res.getString("SelectRestoreDiskPane.error.notLocalDisk") + " [ " + dest + " ]"
                        );
                        return false;
                    }
                }else{
                    JOptionPane.showMessageDialog(this,
                        SanBootView.res.getString("SelectRestoreDiskPane.error.getStartupInfo") + " [ " + dest +" ]"
                    );
                    return false;   
                }
            }
        }
        
        return true;
    }
    
    private boolean badDestDriver( String dest ){
        VolumeMapWrapper wraper;
        String src;
        Object val;
        
        MyDefaultTableModelForTabY model = (MyDefaultTableModelForTabY)table.getModel();
        int lineNum = model.getRowCount();
        for( int row=0; row<lineNum; row++ ){
            val = model.getValueAt( row, 1 );
            if( val instanceof VolumeMapWrapper ){
                wraper = (VolumeMapWrapper)val;
                if( wraper.volMap.getVolDiskLabel().toUpperCase().substring(0,1).equals( dest.toUpperCase() ) ){
                    return false;
                }
            }else{
                src = (String)val;
                if( src.toUpperCase().equals( dest.toUpperCase() ) ){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    @SuppressWarnings("empty-statement")
    public void fireEditingStopMsg(){
        TableCellEditor dce;
        
        AbstractTableModel model = (AbstractTableModel)table.getModel();
        int lineNum = model.getRowCount();
        for( int i=0; i<lineNum; i++  ){
            for( int j=0;j<3; j++ ){ // 专门停止0,2列
                if( j == 1 ) continue;
                
                dce = table.getCellEditor( i,j );
                if( dce!=null ){
                    while(!dce.stopCellEditing()){};
                }
            }
        }
    }
}
