/*
 * NetbootedHostService.java
 *
 * Created on 2008/6/30,��PM�2:34
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package dataprotect.ui;

import dataprotect.res.ResourceCenter;
import javax.swing.Icon;

/**
 *
 * @author Administrator
 */
public class NetbootedHostService extends ServiceItem{
    
    /** Creates a new instance of NetbootedHostService */
    public NetbootedHostService() {
    }
    
    @Override public String toTableString(){
        return SanBootView.res.getString("common.netboothostlist");
    }
    
    @Override public Icon getTableIcon(){
        return ResourceCenter.ICON_FIX;
    }
}
