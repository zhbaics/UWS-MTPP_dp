/*
 * MyDefaultTableModelForTableForSetupMjSch.java
 *
 * Created on 2010/09/09, �PM�1:01
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package dataprotect.remotemirror.ui.multiRenderTable;

import javax.swing.table.*;

/**
 *
 * @author Administrator
 */
public class MyDefaultTableModelForTableForSetupMjSch extends DefaultTableModel{
    public MyDefaultTableModelForTableForSetupMjSch( Object[] header,int colNum ){
        super( header,colNum );
    }
    
    public MyDefaultTableModelForTableForSetupMjSch( Object[][] data,Object[] header ){
        super( data,header );
    }

    @Override public Object getValueAt(int row, int col){
        return super.getValueAt(row,col);
    }
    
    @Override public boolean isCellEditable(int row, int col){
        if( col == 1 ) return false;
        return true;
    }
}
