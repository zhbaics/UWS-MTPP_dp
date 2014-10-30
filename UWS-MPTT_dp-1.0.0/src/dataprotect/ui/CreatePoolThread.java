/*
 * CreatePoolThread.java
 *
 * Created on 2008/3/27, ��AM 11:35
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package dataprotect.ui;

import dataprotect.data.*;
import dataprotect.res.*;

/**
 *
 * @author Administrator
 */
public class CreatePoolThread extends BasicGetSomethingThread{
    private String name;
    private long size;
    private String password;
    
    /** Creates a new instance of CreatePoolThread */
    public CreatePoolThread(
        SanBootView view,
        String name,
        long size,
        String password
    ) {
        super( view );
        
        this.name = name;
        this.size = size;
        this.password = password;
    }
    
    public boolean realRun(){ 
        view.initor.mdb.setNewTimeOut( ResourceCenter.MAX_TIMEOUT );    
        boolean aIsOk = view.initor.mdb.crtResVol( name,size );
        view.initor.mdb.restoreOldTimeOut();
        if( aIsOk ){
            String devName = view.initor.mdb.getResDevName();
            String mp = view.initor.mdb.getResMp();
            
            Pool pool = new Pool( -1,name, mp, devName, password );
            aIsOk = view.initor.mdb.addPool( pool );
            if(  aIsOk ){
                pool.setPool_id( view.initor.mdb.getNewId() );
                view.initor.mdb.addPoolIntoCache( pool );
            }else{
                errMsg = SanBootView.res.getString("MenuAndBtnCenter.error.addPool")+
                        view.initor.mdb.getErrorMessage();
            }
        }else{
            errMsg =  SanBootView.res.getString("MenuAndBtnCenter.error.crtResVol")+
                    view.initor.mdb.getErrorMessage();
        }
        
        return aIsOk;
    }
    
    public boolean isOK(){
        return isOk();
    }
}