package dataprotect.datadup.ui;

import java.beans.*;
import java.util.Vector;
import dataprotect.ui.SanBootView;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonitorInfoReceiver extends Thread{
    private boolean Continue = true;
    private int     sleepTime  = 4; //4 秒��
    private Vector<PropertyChangeListener>  pListenerList = new Vector<PropertyChangeListener>();
    private PropertyChangeSupport  agent = new PropertyChangeSupport(this);
    private boolean suspendRequested = false; 
    SanBootView view;

    public MonitorInfoReceiver( SanBootView _view ) {
        view = _view;
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

    @Override
    public void  run(){
        while( isContinue() ){
            try {
                // 检查 "是否要求暂停获取 task info"
                checkSuspended();
            } catch (InterruptedException ex) {
                Logger.getLogger(MonitorInfoReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                sleep(sleepTime * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MonitorInfoReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }      
            getTaskInfo();
        }
    }

    private void getTaskInfo(){
        boolean isOk = view.initor.mdb.updateTaskList();
        
        if( pListenerList.size()>0 ){ // 通知Listener们去获取新的task info.
            if( isOk ){
                agent.firePropertyChange(
                    "TASKINFO",
                    "old",
                    "new"
                );
            }else{
                // 通知listener们获取task info 失败
                agent.firePropertyChange(
                    view.initor.mdb.getErrorMessage(),
                    "old",
                    "new"
                );
            }
        }
    }
}
