/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataprotect.unlimitedIncMj.service;

import dataprotect.data.MirrorDiskInfo;
import dataprotect.res.ResourceCenter;
import dataprotect.ui.ChiefHostVolume;
import dataprotect.ui.SanBootView;
import dataprotect.unlimitedIncMj.entity.ChiefUnLimitedIncSnapList;
import mylib.UI.BrowserTreeNode;

/**
 * ProcessEventOnChiefUIMSnap.java
 *
 * Created on 2010-1-12, 17:11:32
 */
public class ProcessEventOnChiefUIMSnap extends ProcessEventOnUnlimitedIncMirrorVol{
    public ProcessEventOnChiefUIMSnap(){
        this( null );
    }
    
    public ProcessEventOnChiefUIMSnap( SanBootView view ){
        super( ResourceCenter.TYPE_CHIEF_UIM_SNAP,view );
    }

    @Override public void showSnapList( BrowserTreeNode chiefUIMSnap,int eventType ){
        curMirrorVolNode = chiefUIMSnap;

        ChiefUnLimitedIncSnapList chiefUIMSnapObj = (ChiefUnLimitedIncSnapList)chiefUIMSnap.getUserObject();
        BrowserTreeNode mirrorVolNode = chiefUIMSnapObj.getFatherNode();
        MirrorDiskInfo mdi = (MirrorDiskInfo)mirrorVolNode.getUserObject();
//        BrowserTreeNode chiefHostVol = mdi.getFatherNode();
//        ChiefHostVolume chiefHostVolume = (ChiefHostVolume)chiefHostVol.getUserObject();
//        BrowserTreeNode srcAgntNode = chiefHostVolume.getFatherNode();
//        SourceAgent srcagnt = (SourceAgent)srcAgntNode.getUserObject();
        boolean isCmdp = mdi.isCMDPProtect() && mdi.getSrc_agent_mp().substring(0,1).toUpperCase().equals( "C" );
        
        if( mdi.isRemoteCjVol() ){
            realShowSnapList( mdi.getSnap_rootid(),curMirrorVolNode,eventType,ResourceCenter.TYPE_UNLIMITED_INC_MIRROR_SNAP1,isCmdp );
        }else{
            realShowSnapList( mdi.getSnap_rootid(),curMirrorVolNode,eventType,ResourceCenter.TYPE_UNLIMITED_INC_MIRROR_SNAP,isCmdp );
        }
    }
}
