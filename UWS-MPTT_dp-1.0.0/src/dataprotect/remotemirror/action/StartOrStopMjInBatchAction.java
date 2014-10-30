/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataprotect.remotemirror.action;

import dataprotect.MenuAndBtnCenterForMainUi;
import dataprotect.remotemirror.ui.MirrorJobGeter;
import dataprotect.remotemirror.ui.StartOrStopMjInBatchDialog;
import dataprotect.res.ResourceCenter;
import dataprotect.ui.ChiefMJManage;
import dataprotect.ui.GeneralActionForMainUi;
import dataprotect.ui.ProcessEventOnChiefMjMg;
import dataprotect.ui.SanBootView;
import java.awt.event.ActionEvent;
import javax.swing.tree.TreePath;
import mylib.UI.Browser;
import mylib.UI.BrowserTreeNode;

/**
 * StartOrStopMjInBatchAction.java
 *
 * Created on 2010-8-18, 11:34
 */
public class StartOrStopMjInBatchAction extends GeneralActionForMainUi{
    public StartOrStopMjInBatchAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK,
            "View.MenuItem.startStopMjInBatch",
            MenuAndBtnCenterForMainUi.FUNC_BATCH_START_STOP_MJ
        );
    }

    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering start-stop mirror job in batch action. " );
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        if( !(selObj instanceof ChiefMJManage) ) return;
        
        StartOrStopMjInBatchDialog dialog = new StartOrStopMjInBatchDialog( view );
        MirrorJobGeter geter = new MirrorJobGeter(
            dialog,
            view
        );
        geter.start();
        
        int width  = 1000+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;  // 800
        int height = 540+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;    // 450
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
        
        Object retVal = dialog.getRetVal();
        if( retVal == null ) {
SanBootView.log.info(getClass().getName(),"########### End1 of start-stop mirror job in batch action. " );
            return;
        }
        
        if( ((Integer)retVal).intValue() == 1 ){
            ChiefMJManage chiefMjManage =(ChiefMJManage)selObj;
            BrowserTreeNode chiefMjManageNode = chiefMjManage.getTreeNode();
            view.setCurNode( chiefMjManageNode );
            view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
            TreePath path = new TreePath( chiefMjManageNode.getPath() );
            ProcessEventOnChiefMjMg peOnChiefMjMg = new ProcessEventOnChiefMjMg( view );
            peOnChiefMjMg.processTreeSelection( path );
            peOnChiefMjMg.controlMenuAndBtnForTreeEvent();
        }
SanBootView.log.info(getClass().getName(),"########### End of start-stop mirror job in batch action. " );
    }
}
