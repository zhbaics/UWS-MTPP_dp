/*
 * Tool.java
 *
 * Created on 2008/9/12,��PM�1:08
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package dataprotect.tool;

import dataprotect.ui.SanBootView;
import mylib.tool.GUITool;

/**
 *
 * @author zjj
 */
public class Tool {
    /** Creates a new instance of Tool */
    public Tool() {
    }
    
    public static void prtExceptionMsg( Exception ex ){
        prtExceptionMsg( ex,"" );
    }
    
    public static void prtExceptionMsg( Exception ex,String prefix ){
        String msg = GUITool.getExceptionMsg(ex);
        if( prefix.length() == 0 )
            SanBootView.log.error( "guisanboot.Tool class", msg );
        else
            SanBootView.log.error( prefix,msg );
    }
}
