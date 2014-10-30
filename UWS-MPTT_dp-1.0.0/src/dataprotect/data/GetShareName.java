package dataprotect.data;

import java.io.*;
import java.net.*;
import java.util.*;
import dataprotect.ui.*;

public class GetShareName extends NetworkRunning {
    ArrayList<String> list = new ArrayList<String>();
    
    public void parser(String line)  {
        if( line == null || line.length() == 0 ) return;
SanBootView.log.debug(getClass().getName(),"========> "+ line );
        
        int indx1 = line.indexOf("[");
        int indx2 = line.indexOf("]");
        list.add( line.substring( indx1+1, indx2 ) );
//        try{
//            list.add( line.substring( indx1+1, indx2 ) );
//        }catch(Exception ex){}
    }
    
    public GetShareName( String cmd,Socket socket ) throws IOException {
        super( cmd,socket );
    }
    
    public GetShareName( String cmd ){
        super( cmd );
    }
    
    public boolean realDo(){
SanBootView.log.info( getClass().getName(), " get sharename cmd: "+ getCmdLine() );         
        try{
            list.clear();
            run();
        }catch(Exception ex){
            setExceptionErrMsg( ex );
            setExceptionRetCode( ex );
        }
SanBootView.log.info( getClass().getName(), " get sharename retcode: "+ getRetCode() ); 
        boolean isOk = ( getRetCode() == AbstractNetworkRunning.OK );
        if( !isOk ){
SanBootView.log.error( getClass().getName(), " get sharename errmsg: "+ getErrMsg() );             
        }
        return isOk;
    }
        
    public ArrayList getShNameList(){
        return list;
    }
}
