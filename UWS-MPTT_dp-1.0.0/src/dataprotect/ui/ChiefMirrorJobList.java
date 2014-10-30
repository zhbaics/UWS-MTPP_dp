/*
 * ChiefMirrorJobList.java
 *
 * Created on June 10, 2008, 5:53 PM
 */

package dataprotect.ui;

import javax.swing.*;
import java.util.ArrayList;
import mylib.UI.*;
import dataprotect.res.*;
import dataprotect.MenuAndBtnCenterForMainUi;

/**
 *
 * @author  Administrator
 */
public class ChiefMirrorJobList extends ChiefNode implements TreeRevealable{      
    public ChiefMirrorJobList( int type ){
        super( type );
    }

    /** Creates a new instance of ChiefMirrorJobList */
    public ChiefMirrorJobList() {
        super( ResourceCenter.TYPE_CHIEF_MIRROR_JOB );
    }
    
    public void addFunctionsForTree(){
        if( fsForTree == null ){
            fsForTree = new ArrayList<Integer>( 2 );
            fsForTree.add( new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_MJ ) );
            fsForTree.add( new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_MJ1) );
        }
    }
    
    public void addFunctionsForTable(){
        if( fsForTable == null ){
            fsForTable = new ArrayList<Integer>( 0 );
        }
    }
    
    /////////////////////////////////////////////////////////////////
    //
    //           下 面 的 方 法 跟 UI 有 关�
    //
    /////////////////////////////////////////////////////////////////
    //** TreeRevealable的实现*/
    public Icon getExpandIcon(){
        return ResourceCenter.BTN_ICON_TASKLIST;
    }
    public Icon getCollapseIcon(){
        return ResourceCenter.BTN_ICON_TASKLIST;
    }
    public boolean enableTreeEditable(){
        return false;
    }
    public String toTreeString(){
        return SanBootView.res.getString("common.mirrorlist");
    }
    public String toTipString(){
        return toTreeString();
    }
}
