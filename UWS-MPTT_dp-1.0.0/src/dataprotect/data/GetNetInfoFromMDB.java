package dataprotect.data;

import java.io.*;
import java.net.*;
import java.util.regex.*;
import dataprotect.ui.*;

public class GetNetInfoFromMDB extends NetworkRunning {
    private String mac="";
    private boolean founed = false;
    
    public void parser(String line)  {
        if( line == null || line.length() == 0 ) return;
SanBootView.log.debug(getClass().getName(),"========> "+ line );      
        String tmp = line.trim();
        if( tmp.startsWith("Adapter") ){
            int idx = tmp.indexOf("(*)");
            if( idx >=0 ){
                founed = true;
            }
        }else if( tmp.startsWith("Mac") ){
            if( founed ){
                String[] list = Pattern.compile(":").split(tmp,-1);
                mac = list[1].trim();
                founed = false;
            }
        }
    }

    public GetNetInfoFromMDB( String cmd,Socket socket ) throws IOException {
        super( cmd,socket );
    }
    
    public GetNetInfoFromMDB( String cmd ){
        super( cmd );
    }

    public boolean realDo(){        
SanBootView.log.info( getClass().getName(), " get netinfo confile cmd: "+ getCmdLine() );         
        try{            
            founed = false;
            mac ="";
            
            run();
        }catch(Exception ex){
            setExceptionErrMsg( ex );
            setExceptionRetCode( ex );
        }
SanBootView.log.info( getClass().getName(), " get netinfo confile retcode: "+ getRetCode() );    
        boolean isOk = ( getRetCode() == AbstractNetworkRunning.OK );
        if( !isOk ){
SanBootView.log.error( getClass().getName(), " get netinfo confile errmsg: "+ getErrMsg() );              
        }
        return isOk;
    }
    
    public String getBootMac(){
        return mac;
    }
    
}
