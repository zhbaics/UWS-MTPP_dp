/*
 * GetProfileThread.java
 *
 * Created on Aug 6, 2008, 12:10 AM
 */

package dataprotect.datadup.ui;
import dataprotect.cmdp.entity.PPProfile;
import dataprotect.datadup.data.UniProfile;
import dataprotect.res.ResourceCenter;
import dataprotect.ui.GeneralGetSomethingThread;
import dataprotect.ui.GeneralProcessEventForSanBoot;
import dataprotect.ui.SanBootView;
import java.util.ArrayList;
import javax.swing.*;
import mylib.UI.*;

/**
 *
 * @author  Administrator
 */
public class GetProfileThread extends GeneralGetSomethingThread{
    private long cid;
    private int mode;

    /** Creates a new instance of GetProfileThread */
    public GetProfileThread( 
        SanBootView view, 
        BrowserTreeNode fNode,
        long _cid,
        int mode,
        GeneralProcessEventForSanBoot processEvent,
        int eventType
    ){
        super( view,fNode, processEvent, eventType );
        cid = _cid;
        this.mode = mode;
    }
    
    public boolean realRun(){        
        if( eventType == Browser.TREE_EXPAND_EVENT ){
            try{
                SwingUtilities.invokeAndWait( clearTree ); 
            }catch( Exception ex ){
                ex.printStackTrace();
            }
        }else if( eventType == Browser.TREE_SELECTED_EVENT ){
            try{
                SwingUtilities.invokeAndWait( clearTable ); 
            }catch( Exception ex ){
                ex.printStackTrace();
            }
        }
        
        if( cid != -1 ){
            if( mode == ResourceCenter.CMD_TYPE_MTPP ){
                ArrayList profList = view.initor.mdb.getAllProfileOnClntID( cid );
                int size = profList.size();
//System.out.println(" size: "+ size );
                for( int i=0; i<size; i++ ){
                    UniProfile prof =(UniProfile)profList.get(i);
//System.out.println(" prof name: "+prof.getProfileName() );
//System.out.println(" prof content:\n"+prof.prtMe() );
                    if( eventType == Browser.TREE_EXPAND_EVENT  ){
                        BrowserTreeNode cNode = new BrowserTreeNode( prof,true );
                        prof.setTreeNode( cNode );
                        prof.setFatherNode( fNode );
                        view.addNode( fNode,cNode );
                    }else{
                        processEvent.insertSomethingToTable( prof );
                    }
                }
            }else{
                ArrayList profList = view.initor.mdb.getPPProfile( cid ); 
                int size = profList.size();
                for( int i=0; i<size; i++ ){
                    PPProfile prof =(PPProfile)profList.get(i);
                    if( eventType == Browser.TREE_EXPAND_EVENT  ){
                        BrowserTreeNode cNode = new BrowserTreeNode( prof,true );
                        prof.setTreeNode( cNode );
                        prof.setFatherNode( fNode );
                        view.addNode( fNode,cNode );
                    }else{
                        processEvent.insertSomethingToTable( prof );
                    }
                }
            }
        }
        
        if( eventType == Browser.TREE_EXPAND_EVENT ){
            view.reloadTreeNode( fNode );
        }
        
        return true;
    }
}
