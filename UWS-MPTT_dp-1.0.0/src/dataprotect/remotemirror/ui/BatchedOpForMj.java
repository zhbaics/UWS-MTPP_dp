/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataprotect.remotemirror.ui;

import dataprotect.tool.ui.BasicProcessor;
import dataprotect.ui.InitProgramDialog;
import dataprotect.ui.SlowerLaunch;
import javax.swing.JOptionPane;

/**
 * BatchedOpForMj.java
 *
 * Created on 2010-8-21, 14:23:12
 */
public class BatchedOpForMj extends BasicProcessor{
    /** Creates a new instance of BatchedOpForMj */
    public BatchedOpForMj( InitProgramDialog diag, SlowerLaunch launcher ) {
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
    }
}
