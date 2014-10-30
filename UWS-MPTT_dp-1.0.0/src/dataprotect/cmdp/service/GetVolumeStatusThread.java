/*
 * GetVolumeStatusThread.java
 *
 * Created on 2010/7/16, PM 5:55
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package dataprotect.cmdp.service;

import dataprotect.cmdp.entity.InitProgressRecord;
import dataprotect.cmdp.entity.WorkModeForMirroring;
import dataprotect.ui.*;
import java.util.ArrayList;
import dataprotect.data.*;
import dataprotect.res.*;
import dataprotect.tool.Tool;

/**
 *
 * @author Administrator
 */
public class GetVolumeStatusThread extends BasicGetSomethingThread{
    private GetOrphanVol action;
    private Object[] volObjList;  // 类型为: VolumeMap
    private String ip;
    private int port;
    private ArrayList<InitProgressRecord> initRecList = new ArrayList<InitProgressRecord>();
    private ArrayList<WorkModeForMirroring> workModeList = new ArrayList<WorkModeForMirroring>();
      
    /** Creates a new instance of GetVolumeStatusThread */
    public GetVolumeStatusThread(
        SanBootView view,
        Object[] volObjList,
        String ip,
        int port
    ) {
        super( view,false );
        this.volObjList = volObjList;
        this.ip = ip;
        this.port = port;
    }
    
    public boolean realRun(){
        boolean ok,isMgStart;
        
        initRecList.clear();

        try{
            action = new GetOrphanVol(
                ResourceCenter.getCmd( ResourceCenter.CMD_GET_VOL ), 
                view.getSocket(),
                view
            );
            action.setAddCacheFlag( true );
            action.setFilterFlag( false );
            
            ok = action.realDo();
            errMsg = action.getErrMsg();

            if( ip.length() != 0 ){
                if( volObjList.length > 0 ){
                    // 继续获取"同步状态"( for cmdp )
                    view.initor.setResetNetwork( false );
                    view.initor.mdb.setNewTimeOut( ResourceCenter.LIMITE_OF_RESPOND );
                    for( int i=0; i<volObjList.length; i++ ){
                        VolumeMap volMap = (VolumeMap)volObjList[i];
                        if( volMap.isMtppProtect() ) continue;

                        if( view.initor.mdb.getCurrentSyncState( ip,port,volMap.getVolName(),volMap.getVolDiskLabel() ) ){
                            if( view.initor.mdb.currentStateIsConnectError() ){
                                addRec( i,InitProgressRecord.STATE_notConnect );
                                break;
                            }else{
                                initRecList.add( view.initor.mdb.getCurRec() );
                            }
                        }else{
                            if( view.initor.mdb.currentStateIsConnectError() ){
                                addRec( i,InitProgressRecord.STATE_notConnect );
                            }else if( view.initor.mdb.getErrorCode() == ResourceCenter.ERRCODE_TIMEOUT ){
                                addRec( i,InitProgressRecord.STATE_timeout );
                            }else{
                                addRec( i,InitProgressRecord.STATE_network );
                            }
                            break;
                        }
                    }

                    if( !view.initor.isResetNetwork() ){
                        view.initor.mdb.restoreOldTimeOut();
                    }

                    // 再获取“mg进程的状态”(用于判断是否可以自动创建快照)
                    for( int i=0; i<volObjList.length; i++ ){
                        VolumeMap volMap = (VolumeMap)volObjList[i];
                        if( volMap.isMtppProtect() ) continue;

                        if( volMap.getVol_mgid() > 0 ){
                            isMgStart = view.initor.mdb.checkMg( volMap.getVol_mgid() );
                        }else{
                            // mg id 无效
                            isMgStart = false;
                        }

                        WorkModeForMirroring wkmode = new WorkModeForMirroring( volMap.getVolDiskLabel(),isMgStart );
                        this.workModeList.add( wkmode );
                    }
                }
            }
            
        }catch(Exception ex){
            ok = false;
            Tool.prtExceptionMsg( ex );
        }
        
        return ok;
    }

    private void addRec( int begin,String state ){
        for( int i=begin; i<volObjList.length; i++  ){
            VolumeMap volMap = (VolumeMap)this.volObjList[i];
            initRecList.add( new InitProgressRecord( state,volMap.getVolDiskLabel() ) );
        }
    }

    public ArrayList getRet(){
        return action.getAllVolFromCache();
    }
    public ArrayList getSyncState(){
        return this.initRecList;
    }
    public ArrayList getWorkModeofMirroring(){
        return this.workModeList;
    }
    public boolean getRetVal(){
        return isOk();
    }
    public String getErrorMsg(){
        return getErrMsg();
    }

    public GetOrphanVol getAction() {
        return action;
    }
    
}