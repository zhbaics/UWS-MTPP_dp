/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataprotect.cmdp.action;

import dataprotect.MenuAndBtnCenterForMainUi;
import dataprotect.cmdp.ui.QuerySyncStateDialog;
import dataprotect.data.BootHost;
import dataprotect.data.VolumeMap;
import dataprotect.res.ResourceCenter;
import dataprotect.ui.GeneralActionForMainUi;
import dataprotect.ui.SanBootView;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import mylib.UI.BrowserTreeNode;

/**
 * QuerySyncStateAction.java
 *
 * Created on 2010-6-22, 15:34:44
 */
public class QuerySyncStateAction extends GeneralActionForMainUi{
    public QuerySyncStateAction(){
        super(
            ResourceCenter.ICON_MOD_PROFILE,
            ResourceCenter.MENU_ICON_BLANK,
            "View.MenuItem.querySyncState",
            MenuAndBtnCenterForMainUi.FUNC_PHY_QUERY_SYNC_STATE
        );
    }

    @Override public void doAction( ActionEvent evt ){
SanBootView.log.info(getClass().getName(),"########### Entering query sync state action. " );
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;

        boolean isVolMap = ( selObj instanceof VolumeMap );
        if( !isVolMap ) return;

        VolumeMap volMap = (VolumeMap)selObj;
        if( !view.initor.mdb.updateOneVolumeMap( volMap.getVolName() ) ){
SanBootView.log.error( getClass().getName(), "can not get latest vol info for "+ volMap.getVolName() );
            JOptionPane.showMessageDialog( view,
                ResourceCenter.getCmdString( ResourceCenter.CMD_GET_VOLMAP ) +" : " +
                view.initor.mdb.getErrorMessage()
            );
            return;
        }else{
            VolumeMap newVolMap = view.initor.mdb.getOneVolMap( volMap.getVolName() );
            if( newVolMap != null ){
                view.initor.mdb.replaceVolMap( volMap.getVolName(), newVolMap.getVol_info() );
SanBootView.log.debug(getClass().getName(), "volMap's vol_info when replaceVolMap: " + volMap.getVol_info() );
            }else{
 SanBootView.log.error( getClass().getName(), "the latest got volMap is null. "+ volMap.getVolName() );
                JOptionPane.showMessageDialog( view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_VOLMAP ) +" : " +
                    view.initor.mdb.getErrorMessage()
                );
                return;
            }
        }

        BootHost host = view.initor.mdb.getBootHostFromVector( volMap.getVolClntID() );
        if( host == null ){
SanBootView.log.error( getClass().getName(), "can not find host according volume's clntid: "+ volMap.getVolClntID() );
            JOptionPane.showMessageDialog( view, SanBootView.res.getString("MonitorDialog.errMsg.notFoundClient"));
            return;
        }
        
        QuerySyncStateDialog dialog = new QuerySyncStateDialog( view,volMap,host ,null);
        int width  = 395+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 430+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
        
SanBootView.log.info(getClass().getName(),"########### End of query sync state action. " );
    }
}
