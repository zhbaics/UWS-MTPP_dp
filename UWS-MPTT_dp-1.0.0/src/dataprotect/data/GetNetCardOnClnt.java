package dataprotect.data;

import java.io.*;
import java.net.*;
import java.util.*;
import dataprotect.ui.*;

public class GetNetCardOnClnt extends NetworkRunning {
    private NetCard curCard = null;
    private Vector<NetCard> cache = new Vector<NetCard>();
    
    public void parser(String line)  {
        if( line == null || line.length() == 0 ) return;
SanBootView.log.debug(getClass().getName(),"========> "+ line );         

        int idx1 = line.indexOf(":");
        int idx2 = line.indexOf("Mac:", idx1+1);
        
        try{
            curCard = new NetCard();
            curCard.info = line.substring(idx1+1, idx2);
            curCard.info =curCard.info.trim();
            if( curCard.info.indexOf("1000") >=0 ){
                curCard.isGiga = true;
            }
            curCard.mac = line.substring(idx2+4);
            curCard.mac = curCard.mac.trim();
            cache.addElement( curCard );
        }catch(Exception ex){
            curCard = null;
        }
    }

    public GetNetCardOnClnt( String cmd,Socket socket ) throws IOException {
        super( cmd,socket );
    }
    
    public GetNetCardOnClnt( String cmd ){
        super( cmd );
    }

    public boolean realDo(){ 
SanBootView.log.info( getClass().getName(), " get netcard of client cmd: "+ getCmdLine() );         
        try{
            curCard = null;
            cache.removeAllElements();
            
            run();
        }catch(Exception ex){
            setExceptionErrMsg( ex );
            setExceptionRetCode( ex );
        }
SanBootView.log.info( getClass().getName(), " get netcard of client retcode: "+ getRetCode() ); 
        boolean isOk  =( getRetCode() == AbstractNetworkRunning.OK );
        if( !isOk ){
SanBootView.log.error( getClass().getName(), " get netcard of client errmsg: "+ getErrMsg() ); 
        }
        return isOk; 
    }
    
    public Vector<NetCard> getAllNetCard(){
        Vector<NetCard> ret = new Vector<NetCard>();
        int size = cache.size();
        for( int i=0; i<size; i++ ){
            ret.addElement( cache.elementAt(i) );
        }
        return ret;
    }
}
