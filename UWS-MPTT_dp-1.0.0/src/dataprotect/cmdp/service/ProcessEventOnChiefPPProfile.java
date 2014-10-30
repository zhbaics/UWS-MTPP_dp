/*
 * ProcessEventOnChiefPPProfile.java
 *
 * Created on June 10, 2010, 10:27 AM
 */

package dataprotect.cmdp.service;

import dataprotect.cmdp.entity.PPProfile;
import dataprotect.cmdp.entity.PPProfileItem;
import javax.swing.*;
import javax.swing.tree.*;
import mylib.UI.*;
import dataprotect.datadup.ui.viewobj.ChiefProfile;
import dataprotect.res.*;
import dataprotect.ui.GeneralProcessEventForSanBoot;
import dataprotect.datadup.ui.GetProfileThread;
import dataprotect.ui.SanBootView;
import dataprotect.data.BootHost;
import dataprotect.data.VolumeMap;

/**
 *
 * @author  Administrator
 */
public class ProcessEventOnChiefPPProfile extends GeneralProcessEventForSanBoot {
    BrowserTreeNode curChiefProfNode = null;
    
    /** Creates a new instance of ProcessEventOnChiefPPProfile */
    public ProcessEventOnChiefPPProfile(){
        this( null );
    }

    public ProcessEventOnChiefPPProfile(SanBootView view) {
        super( ResourceCenter.TYPE_CHIEF_PPPROF,view );
    }
    
    private void showProfList( BrowserTreeNode chiefProfNode,int eventType ){
        curChiefProfNode = chiefProfNode;
        
        ChiefProfile chiefProf = (ChiefProfile)chiefProfNode.getUserObject();
        BrowserTreeNode hostNode = chiefProf.getFatherNode();
        int clntId = -1;
        if( hostNode != null ){
            BootHost bootHost = (BootHost)hostNode.getUserObject();
            clntId = bootHost.getID();
        }
        
        GetProfileThread thread = new GetProfileThread(
            view, 
            chiefProfNode,
            clntId,
            ResourceCenter.CMD_TYPE_CMDP,
            this,
            eventType
        );
        view.startupProcessDiag(
            SanBootView.res.getString("View.pdiagTitle.getProf"),
            SanBootView.res.getString("View.pdiagTip.getProf"),
            thread
        );
    }
    
    @Override public void processTreeSelection(TreePath path){
        if ( !view.initor.isLogined() ) return;
        
        view.removeRightPane();
        view.addTable();
        
        BrowserTreeNode chiefProfNode = (BrowserTreeNode)path.getLastPathComponent();
        if( chiefProfNode != null ){
            showProfList( chiefProfNode,Browser.TREE_SELECTED_EVENT );   
        } 
    }
   
    @Override public void processTreeExpand(TreePath path){
        /// 布置table中的内容�
        BrowserTreeNode chiefProfNode = (BrowserTreeNode)path.getLastPathComponent();
        if( chiefProfNode != null ){
            showProfList( chiefProfNode,Browser.TREE_EXPAND_EVENT );
        }  
    }
    
    public void realDoTableDoubleClick(Object cell){
        // do nothing
    }
    
    public void setupTableList(){
        Object[] title = new Object[]{
            SanBootView.res.getString("View.table.profile.content1"),
            SanBootView.res.getString("View.table.profile.autocrtversion"),
            SanBootView.res.getString("View.table.profile.minSnapSize"),
            SanBootView.res.getString("View.table.profile.dbtype"),
            SanBootView.res.getString("View.table.profile.dbinstances"),
            SanBootView.res.getString("View.table.profile.services")
        };
        table.setupTitle( title );
        
        int[][] widthList = new int[][]{
            {0,200},{1,420},{2,150},{3,150},{4,200},{5,350}
        };
        table.setupTableColumnWidth( widthList );
        table.getTableHeader().setBorder( BorderFactory.createRaisedBevelBorder() );
        table.getTableHeader().setReorderingAllowed( false );
    }
    
    public void insertSomethingToTable( Object obj ){
        PPProfile prof =(PPProfile)obj;
        prof.setFatherNode( curChiefProfNode );
        
        Object[] one = new Object[6];
        one[0] = prof;
        one[1] =  new GeneralBrowserTableCell(
            -1,prof.getIntervalString(),JLabel.LEFT
        );
        PPProfileItem master_disk = prof.getMainDiskItem();
        if( master_disk !=null){
            one[2] =  new GeneralBrowserTableCell(
                -1,master_disk.getMg().getMg_min_snap_size()+"",JLabel.LEFT
            );
    SanBootView.log.debug(getClass().getName(), "dbtype: "+ master_disk.getVolMap().getDBType() );
            one[3] = new GeneralBrowserTableCell(
                -1,VolumeMap.getDbString( master_disk.getVolMap().getDBType() ),JLabel.LEFT
            );
            one[4] = new GeneralBrowserTableCell(
                -1,master_disk.getVolMap().getDatabase_instances(),JLabel.LEFT
            );
            one[5] = new GeneralBrowserTableCell(
                -1,master_disk.getVolMap().getChangeVerService(),JLabel.LEFT
            );
        } else {
            one[2] =  new GeneralBrowserTableCell(
                -1,"",JLabel.LEFT
            );
    SanBootView.log.debug(getClass().getName(), "dbtype: " );
            one[3] = new GeneralBrowserTableCell(
                -1,VolumeMap.getDbString( 0 ),JLabel.LEFT
            );
            one[4] = new GeneralBrowserTableCell(
                -1,"",JLabel.LEFT
            );
            one[5] = new GeneralBrowserTableCell(
                -1,"",JLabel.LEFT
            );
        }
        
        table.insertRow(one);
    }
}
