/*
 * ProcessEventOnUnlimitedIncMirroredSnap.java
 *
 * Created on December 15, 2009, 11:43 AM
 */

package dataprotect.unlimitedIncMj.service;

import dataprotect.ui.*;
import javax.swing.tree.*;
import dataprotect.res.*;

/**
 *
 * @author  Administrator
 */
public class ProcessEventOnUnlimitedIncMirroredSnap extends GeneralProcessEventForSanBoot { 
    /** Creates a new instance of ProcessEventOnUnlimitedIncMirroredSnap */
    public ProcessEventOnUnlimitedIncMirroredSnap(){
        this( null );
    }
    public ProcessEventOnUnlimitedIncMirroredSnap(SanBootView view) {
        super( ResourceCenter.TYPE_UNLIMITED_INC_MIRROR_SNAP,view );
    }
    
    
    @Override public void processTreeSelection(TreePath path){
    }
    
    @Override public void processTreeExpand(TreePath path){
    }
    
    public void realDoTableDoubleClick(Object cell){
        // do nothing
    }

    public void insertSomethingToTable( Object obj ){
    }

    public void setupTableList(){
    }   
}
