/*
 * GetInitProgressIntoTableModel.java
 *
 * Created on June 23, 2010, 09:26 AM
 */

package dataprotect.cmdp.service;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;

import mylib.UI.*;
import dataprotect.ui.*;
import dataprotect.cmdp.entity.InitProgressRecord;
import dataprotect.data.BootHost;
import dataprotect.data.VolumeMap;
import dataprotect.res.ResourceCenter;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author  Administrator
 */
public class GetInitProgressIntoTableModel {
    private InitProgressRecord curRec  = null;
    private SanBootView view;
    private BootHost host;
    private DefaultTableModel model;
    private GetInitProgress geter;
    
    /** Creates a new instance of GetInitProgressIntoTableModel */
    public GetInitProgressIntoTableModel( SanBootView _view,BootHost host )throws IOException{
        view = _view;
        this.host = host;
        
        geter = new GetInitProgress("");
        geter.setSocket( view.getSocket() );
    }
    
    public void updateInitProgress() throws IOException {
        Vector<VolumeMap> volList = view.initor.mdb.getVolMapOnClntID( host.getID() );
        int size = volList.size();
        if( size > 0 ){
            for( int i=0; i<size; i++ ){
                VolumeMap vol = volList.elementAt(i);
                if( vol.isMtppProtect() ) continue;
                
                view.initor.setResetNetwork( false );
                view.initor.mdb.setNewTimeOut( ResourceCenter.LIMITE_OF_RESPOND );
                boolean isOk = geter.updateInitProgress( host.getIP(),host.getPort(),vol.getVolName(),vol.getVolDiskLabel() );
                if( isOk ){
                    curRec = geter.getInitRecord();
                    curRec.setIsNotConnectError( geter.isNotConnectToHost() );

                    if( curRec.isNotConnectToClient() ){
                        curRec.setPercent( SanBootView.res.getString("common.conError") );
                    }else{
                        if( !curRec.isInitState() ){
                            curRec.setPercent( "100%" );
                            curRec.setRemainTime("0");
                        }
                    }
                }else{
                    if( geter.getRetCode() == ResourceCenter.ERRCODE_TIMEOUT ){
                        curRec = new InitProgressRecord( vol.getVolDiskLabel() );
                        curRec.setPercent( SanBootView.res.getString("common.timeout") );
                    }else{
                        curRec = new InitProgressRecord( vol.getVolDiskLabel() );
                    }
                }

                if( view.initor.isResetNetwork() ){
                    geter.setSocket( view.getSocket() ); // 有可能出现超时,所以要重新设置socket
                }
                try {
                    SwingUtilities.invokeAndWait(insertModel);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GetInitProgressIntoTableModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GetInitProgressIntoTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            view.initor.mdb.setNewTimeOut( ResourceCenter.DEFAULT_TIMEOUT );
        }
    }

    Runnable insertModel = new Runnable(){
        public void run(){
            Object[] one = new Object[6];
            
            one[0] = curRec;
            
            one[1] = new GeneralBrowserTableCell(
                -1,curRec.getPercent(),JLabel.LEFT
            );

            one[2] = new GeneralBrowserTableCell(
                -1,curRec.getSpeed(),JLabel.LEFT
            );

            one[3] = new GeneralBrowserTableCell(
                //-1,curRec.getRemainTime(),JLabel.LEFT
                -1,curRec.getFormattedRemainTime(),JLabel.LEFT
            );
            
            one[4] = new GeneralBrowserTableCell(
                //-1,curRec.getElapsedTime(),JLabel.LEFT
                -1,curRec.getFormattedElapsedTime(),JLabel.LEFT
            );

            one[5] = new GeneralBrowserTableCell(
                -1,curRec.getFinished(),JLabel.LEFT
            );
            
            model.addRow( one );
        }
    };
    
    public void setTaskTable( BrowserTable table){
        model = (DefaultTableModel)table.getModel();
    }
}
