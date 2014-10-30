package dataprotect.cmdp.service;

/**
 * InitProgressInfoReceiver.java
 *
 * Created on June 23, 2010, 9:06 AM
 */
import dataprotect.cmdp.entity.InitProgressRecord;
import dataprotect.data.BootHost;
import dataprotect.data.VolumeMap;
import dataprotect.res.ResourceCenter;
import java.beans.*;
import java.util.Vector;
import dataprotect.ui.SanBootView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitProgressInfoReceiver extends Thread{
    private boolean Continue = true;
    private int     sleepTime  = 4; //4 秒��
    private Vector<PropertyChangeListener>  pListenerList = new Vector<PropertyChangeListener>();
    private PropertyChangeSupport  agent = new PropertyChangeSupport(this);
    private boolean suspendRequested = false; 
    SanBootView view;
    private BootHost host;
    private GetInitProgress geter;
    private ArrayList<InitProgressRecord> recList = new ArrayList<InitProgressRecord>();

    public InitProgressInfoReceiver( SanBootView _view ){
        view = _view;

        geter = new GetInitProgress("");
    }

    public void setHost( BootHost host ){
        this.host = host;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener I){
        agent.addPropertyChangeListener(I);
        pListenerList.addElement( I );
    }
    
    public void removePropertyChangeListener(PropertyChangeListener I){
        agent.removePropertyChangeListener(I);
        pListenerList.removeElement( I );
    }
    
    public synchronized void requestSuspend(){
        suspendRequested = true;
    }
    
    private synchronized void checkSuspended() throws InterruptedException{
        while( suspendRequested ){
            wait();
        }
    }
    
    public synchronized void requestResume() {
        suspendRequested = false;
        notify();
    }
    
    public synchronized boolean isContinue(){
        return Continue;
    }

    public synchronized void setContinue(boolean val){
        Continue = val;
    }

    @Override public void  run(){
        while( isContinue() ){
            try{
                checkSuspended();               // 检查 "是否要求暂停获取 task info"
                sleep(sleepTime * 1000);
            }catch(InterruptedException ex){
                Logger.getLogger(InitProgressInfoReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
            try{
                getInitProgressInfo();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<InitProgressRecord> getInitProgress(){
        return this.recList;
    }
    
    private void getInitProgressInfo() throws IOException{
        InitProgressRecord curRec;

        this.geter.setSocket( view.getSocket() );
        this.recList.clear();

        Vector<VolumeMap> volList = view.initor.mdb.getVolMapOnClntID( host.getID() );
        int size = volList.size();
        if( size > 0 ){
            for( int i=0; i<size; i++ ){
                VolumeMap vol = volList.elementAt( i );
                if( vol.isMtppProtect() ) continue;
                
                view.initor.setResetNetwork( false );
                view.initor.mdb.setNewTimeOut( ResourceCenter.LIMITE_OF_RESPOND );
                boolean isOk = geter.updateInitProgress( host.getIP(),host.getPort(),vol.getVolName(),vol.getVolDiskLabel() );
                if( isOk ){
                    curRec = geter.getInitRecord();
                    curRec.setIsNotConnectError( geter.isNotConnectToHost() );
                }else{
                    if( geter.getRetCode() == ResourceCenter.ERRCODE_TIMEOUT ){
                        curRec = new InitProgressRecord( vol.getVolDiskLabel() );
                        curRec.setPercent( SanBootView.res.getString("common.timeout") );
                    }else{
                        curRec = new InitProgressRecord( vol.getVolDiskLabel() );
                    }
                }
                recList.add( curRec );

                if( view.initor.isResetNetwork() ){
                    geter.setSocket( view.getSocket() ); // 有可能出现超时,所以要重新设置socket
                }
            }
            view.initor.mdb.setNewTimeOut( ResourceCenter.DEFAULT_TIMEOUT );
        }

        if( pListenerList.size() > 0 ){ // 通知Listener们去获取新的Init-progress info.
            agent.firePropertyChange(
                "INITPROGRESSINFO"+host.getIP(),
                "old",
                "new"
            );
        }
    }
}
