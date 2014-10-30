/*
 * LaunchSomething.java
 *
 * Created on April 12, 2005, 11:42 AM
 */

package dataprotect.ui;

import dataprotect.tool.ui.BasicProcessor;
import javax.swing.*;

/**
 *
 * @author  Administrator
 */
public class LaunchSomething extends BasicProcessor{
    /** Creates a new instance of LaunchSomething */
    public LaunchSomething( InitProgramDialog diag, SlowerLaunch launcher ) {
        super( diag,launcher );
    }

    public void doSomething(){
       if( !launcher.init() ){
            if( launcher.getInitErrMsg().length() != 0 ){
                JOptionPane.showMessageDialog(diag,
                    launcher.getInitErrMsg()
                );
            }
        }

        if( !launcher.isCrtVG() ){
            JOptionPane.showMessageDialog(diag,
                SanBootView.res.getString("common.error.noVG1") + launcher.getSrvIP()
            );
        }
    }
}