/*
 * MirrorDiskInfoWrapper.java
 *
 * Created on 2008/7/3,�PM�6:51
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package dataprotect.data;

import dataprotect.ui.SanBootView;

/**
 *
 * @author zjj
 */
public class MirrorDiskInfoWrapper {
    private int type;
    public MirrorDiskInfo mdi;
    
    @Override public String toString(){
        if( type == 0 ){
            return mdi.getSrc_agent_mp();
        }else{
            return SanBootView.res.getString("common.vol") + " : " + mdi.toString();
        }
    }
    
    /** Creates a new instance of MirrorDiskInfoWrapper */
    public MirrorDiskInfoWrapper() {
        this(0);
    }
    
    public MirrorDiskInfoWrapper( int type) {
        this.type = type;
    }
}
