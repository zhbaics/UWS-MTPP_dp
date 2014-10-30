/*
 * FreeUIMVolumeService.java
 *
 * Created on 2010/1/27, PM 3:39
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package dataprotect.unlimitedIncMj.ui;

import dataprotect.ui.*;

/**
 *
 * @author Administrator
 */
public class FreeUIMVolumeService extends ServiceItem{
    
    /** Creates a new instance of FreeUIMVolumeService */
    public FreeUIMVolumeService() {
    }
    
    @Override public String toTableString(){
        return SanBootView.res.getString("common.freeuimvollist");
    }
}
