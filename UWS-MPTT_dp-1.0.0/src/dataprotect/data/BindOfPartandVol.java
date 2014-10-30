/*
 * BindOfPartandVol.java
 *
 * Created on 2007/1/4,��PM 7:46
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package dataprotect.data;

import dataprotect.res.ResourceCenter;

/**
 *
 * @author Administrator
 */
public class BindOfPartandVol {
    public SystemPartitionForWin part = null;
    public boolean isProtected = true ;
    public boolean isFormatted = false;
    public int action = 0; // 0: crt 1: sel
    public boolean isRealVol = false;
    public String volName="";
    public Volume vol = null;
    public String volSize="";
    public int blkSize=17;
    public int poolid=-999;
    public int targetID=-1;  // targetID不为 -1表示创建成功
    public int rootID=-1; 
    //public String maxSnap = "32"; 
    public String maxSnap = ResourceCenter.MAX_SNAP_NUM+"";
    public String desc=""; 
    public int ptype = BootHost.PROTECT_TYPE_CMDP; 

    /** Creates a new instance of BindOfPartandVol */
    public BindOfPartandVol() {
        if( this.isProtectedByCMDP() ){
            maxSnap = ResourceCenter.MAX_SNAP_CMDP_NUM+"";
        }else{
            maxSnap = ResourceCenter.MAX_SNAP_NUM+"";
        }
    }
    
    public String getMaxSnap(){
        if( isRealVol ){
            return maxSnap;
        }else{
            /*
            if( ResourceCenter.MAX_SNAP_NUM <= 32 ){
                return ResourceCenter.MAX_SNAP_NUM+"";
            }else{ // > 32
                return "32";
            }
             */
             int maxsnap_temp = this.isProtectedByCMDP()? ResourceCenter.MAX_SNAP_CMDP_NUM:ResourceCenter.MAX_SNAP_NUM;
             if( maxsnap_temp > 250 ){
                 return "250";
             }else{
                 return maxsnap_temp+"";
             }
        }
    }

    public boolean isProtectedByCMDP(){
        return ( this.ptype == BootHost.PROTECT_TYPE_CMDP );
    }
    
    public boolean isProtectedByMTPP(){
        return ( this.ptype == BootHost.PROTECT_TYPE_MTPP );
    }
}
