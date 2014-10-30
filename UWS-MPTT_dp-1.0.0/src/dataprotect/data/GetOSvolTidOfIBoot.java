/*
 * GetOSvolTidOfIBoot.java
 *
 * Created on 3 18, 2008, 10:43 AM
 */

package dataprotect.data;

import java.net.*;
import java.io.*;
import dataprotect.res.*;
import dataprotect.ui.SanBootView;

public class GetOSvolTidOfIBoot extends NetworkRunning{
    protected int tid = -1;
    
    public void parser( String line ){
        String s1 = line.trim();
SanBootView.log.debug(getClass().getName(),"========> "+ s1 );            
        tid = Integer.parseInt( s1 );
    }
     
    /** Creates a new instance of AddCmdNetworkRunning */
    public GetOSvolTidOfIBoot() {
    }
    
    public GetOSvolTidOfIBoot(Socket socket )throws IOException{
        super( socket );
    }
    
    public GetOSvolTidOfIBoot(String cmdLine,Socket socket) throws IOException{
        super( cmdLine,socket );
    }
    
    public boolean realDo( String ip ){
        setCmdLine( ResourceCenter.getCmd( ResourceCenter.CMD_GET_OSVOLTID ) + ip );
SanBootView.log.info( getClass().getName(), " get os vol tid of iboot cmd : "+ this.getCmdLine()  );     
        try{
            tid = -1;         
            run();
        }catch(Exception ex){
            setExceptionErrMsg( ex );
            setExceptionRetCode( ex );
        }
SanBootView.log.info( getClass().getName(), " get os vol tid of iboot ret : "+ this.getRetCode() ); 
        boolean isOk = ( getRetCode() == AbstractNetworkRunning.OK );
        if( !isOk ){
SanBootView.log.error( getClass().getName(), " get os vol tid of iboot errmsg : "+ this.getErrMsg() );             
        }
        return isOk;
    }
    
    public int getTargetID(){
        return tid;
    }
}
