package dataprotect.data;

import java.io.*;
import java.net.*;
import java.util.regex.*;
import dataprotect.ui.*;

public class GetUnixNetCardInfo extends NetworkRunning {
    private String mac="";
    
    public void parser(String line)  {
        // 格式: eth0(*);00:D0:B7:B8:F0:8C;20.20.1.155;255.0.0.0;
        String  _mac;
        
        if( line == null || line.length() == 0 ) return;
SanBootView.log.debug(getClass().getName(),"========> "+ line );     

        String[] list = Pattern.compile(";").split( line,-1 );
        _mac = list[1];
        if( list[0].indexOf("*") >= 0 ){
            mac = _mac;
        }
    }

    public GetUnixNetCardInfo( String cmd,Socket socket ) throws IOException {
        super( cmd,socket );
    }
    
    public GetUnixNetCardInfo( String cmd ){
        super( cmd );
    }
    
    public GetUnixNetCardInfo(){
    }

    public boolean realDo(){
SanBootView.log.info( getClass().getName(), " get unix netcard (confile) cmd: "+ getCmdLine() );         
        try{
            mac ="";
            run();
        }catch(Exception ex){
            ex.printStackTrace();
            setExceptionErrMsg( ex );
            setExceptionRetCode( ex );
        }
SanBootView.log.info( getClass().getName(), " get unix netcard (confile) retcode: "+ getRetCode() );   
        boolean isOk = ( getRetCode() == AbstractNetworkRunning.OK );
        if( !isOk ){
SanBootView.log.error( getClass().getName(), " get unix netcard (confile) errmsg: "+ getErrMsg() );               
        }
        return isOk;
    }
    
    public String getBootMac(){
        return mac;
    }
}
