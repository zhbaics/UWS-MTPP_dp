/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataprotect.cmdp.ui.multiRenderTable;

import dataprotect.cmdp.entity.PPProfile;
import dataprotect.cmdp.entity.PPProfileItem;
import dataprotect.data.BootHost;
import dataprotect.data.MirrorDiskInfo;
import dataprotect.data.MirrorDiskInfoWrapper;
import dataprotect.data.VolumeMap;
import dataprotect.data.VolumeMapWrapper;
import dataprotect.ui.SanBootView;
import dataprotect.ui.SelectSnapshotPane;
import dataprotect.ui.multiRenderTable.JTableY;
import dataprotect.ui.multiRenderTable.MyDefaultTableModelForTabY;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 * MyDefaultCellEditor.java
 *
 * Created on 2010-6-30, 14:25:53
 */
public class MyDefaultCellEditor extends DefaultCellEditor{
    private Object selObj = null;
    private JTableY tab;
    private SanBootView view;
    private SelectSnapshotPane pane;
    private BootHost host;

    private void actPerformed(){
        VolumeMap volMap;
        String diskLabel;
        MirrorDiskInfo mdi;

        int selRow = tab.getSelectedRow();
        if( selRow < 0 ) return;
        
        MyDefaultTableModelForTabY model = (MyDefaultTableModelForTabY)tab.getModel();
        Object volObj = model.getValueAt( selRow, 1 );
        if( volObj instanceof VolumeMapWrapper ){
            volMap = ((VolumeMapWrapper)volObj).volMap;
            if( volMap.isMtppProtect() ) return;
            diskLabel = volMap.getVolDiskLabel();
        }else{
            mdi = ((MirrorDiskInfoWrapper)volObj).mdi;
            diskLabel = mdi.getSrc_agent_mp();
        }

        PPProfile prof = view.initor.mdb.getBelongedDg( host.getID(),diskLabel );
        if( prof == null) return ;
        if( !prof.isValidDriveGrp() ) return;

        JComboBox comboBox = (JComboBox)this.getComponent();
        selObj = comboBox.getSelectedItem();

        ArrayList<PPProfileItem> elements = prof.getElements();
        int size = elements.size();
        for( int i=0; i<size; i++ ){
            PPProfileItem item = elements.get( i );
            if( item.getVolMap().getVolDiskLabel().equals( diskLabel ) ){ continue; }

            int row = pane.getValueOnDiskLabel( item.getVolMap().getVolDiskLabel() );
            if( row == -1 ) continue;

            pane.setVersionObjOfSameTime( row,selObj );
        }
    }

    public MyDefaultCellEditor( final JComboBox comboBox,BootHost host,SelectSnapshotPane pane,JTableY tab,SanBootView view ) {
        super( comboBox );
        
        this.tab = tab;
        this.view = view;
        this.pane = pane;
        this.host = host;

        comboBox.addItemListener( new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                actPerformed();
            }
        });
    }
}
