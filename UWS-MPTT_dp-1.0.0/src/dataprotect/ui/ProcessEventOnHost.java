/*
 * ProcessEventOnHost.java
 *
 * Created on December 10, 2004, 3:25 PM
 */

package dataprotect.ui;

import dataprotect.data.BootHost;
import dataprotect.datadup.ui.viewobj.HostProfileService;
import javax.swing.*;
import javax.swing.tree.*;
import mylib.UI.*;
import dataprotect.res.*;

/**
 *
 * @author  Administrator
 */
public class ProcessEventOnHost extends GeneralProcessEventForSanBoot {   
    /** Creates a new instance of ProcessEventOnHost */
    public ProcessEventOnHost(){
        this( null );
    }
    public ProcessEventOnHost(SanBootView view) {
        super( ResourceCenter.TYPE_HOST_INDEX,view );
    }
    
    @Override public void processTreeSelection(TreePath path){
        if ( !view.initor.isLogined() ) return;
        
        view.removeRightPane();
        view.addTable();
        
        // 布置table中的内容
        setupTableList();
        BrowserTreeNode hostNode = (BrowserTreeNode)path.getLastPathComponent();
        BootHost host = (BootHost)hostNode.getUserObject();
        insertSomethingToTable( host );
    }
    
    public void realDoTableDoubleClick(Object cell){
        // do nothing
    }
      
    public void insertSomethingToTable( Object obj ){
        BootHost host = (BootHost)obj;

        Object[] one = new Object[2];
        HostVolumeService service = new HostVolumeService();
        one[0] = service;
        one[1] = new GeneralBrowserTableCell(
            -1, SanBootView.res.getString("common.desc.hostVolList"),JLabel.LEFT
        );
        table.insertRow( one  );

        if( host.isCMDPProtect() ){
            HostProfileService service1 = new HostProfileService( 1 );
            one[0] = service1;
            one[1] = new GeneralBrowserTableCell(
                -1, SanBootView.res.getString("common.desc.pprofList"),JLabel.LEFT
            );
            table.insertRow( one  );
        }
        
        NetbootedHostService service2 = new NetbootedHostService();
        one[0] = service2;
        one[1] = new GeneralBrowserTableCell(
            -1,SanBootView.res.getString("common.desc.netbootedHostList"),JLabel.LEFT
        );
        table.insertRow( one ); 
    }
    
    public void setupTableList(){    
        Object[] title = new Object[]{
            SanBootView.res.getString("common.table.item"),
            SanBootView.res.getString("common.table.desc")
        };
        table.setupTitle( title );
        
        int[][] widthList = new int[][]{
            {0,150},  {1,375}
        };
        table.setupTableColumnWidth(widthList);
        table.getTableHeader().setBorder( BorderFactory.createRaisedBevelBorder() );
        table.getTableHeader().setReorderingAllowed( false );
    } 
}
