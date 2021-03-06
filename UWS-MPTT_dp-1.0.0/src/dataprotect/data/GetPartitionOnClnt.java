package dataprotect.data;

import dataprotect.res.ResourceCenter;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
import dataprotect.ui.*;

public class GetPartitionOnClnt extends NetworkRunning {
    private SystemPartitionForWin curPart = null;
    private Vector<SystemPartitionForWin> cache = new Vector<SystemPartitionForWin>();

    public void parser( String line ){
        if( this.isMTPPCmd() ){
            this.parserMTPP( line );
        }else{
           this.parserCMDP( line );
        }
    }

    public void parserCMDP( String line ){
        if( !this.isContinueToParserRetValueForCMDP( line ) ) return;

        if( this.isEqZero() ){
            this.parserMTPP( line );
        }else{
            this.errMsg += line +"\n";
        }
    }

    public void parserMTPP(String line)  {
        if( line == null || line.length() == 0 ) return;
        line = line.trim();
SanBootView.log.debug(getClass().getName(),"========> "+ line );          
        try{
            String[] list = Pattern.compile(";").split( line,-1 );
            
            if( list[0].length() == 0 ){ // ������
                return;
            }
            
            if( list[3].length() == 0 ){ // ������
                return;
            }
            
            if( list[7].length() == 0 ){ // ������
                return;
            }
            
            curPart = new SystemPartitionForWin();
            curPart.disklabel = list[0];
            curPart.isActive = list[1];
            curPart.label = list[2];
            curPart.volInfo = list[3];
            curPart.fsType = list[4];
            curPart.size = list[5];
            curPart.available = list[6];
            curPart.vender = list[7];
            curPart.iscsiVar = list[8];
                        
            cache.addElement( curPart );
        }catch(Exception ex){
            curPart = null;
        }
    }

    public GetPartitionOnClnt( String cmd,Socket socket ) throws IOException {
        super( cmd,socket );
    }
    
    public GetPartitionOnClnt( String cmd ){
        super( cmd,ResourceCenter.DEFAULT_CHARACTER_SET );
    }

    public boolean realDo(){ 
SanBootView.log.info( getClass().getName(), " get partition (confile) cmd: "+ getCmdLine() );         
        try{
            curPart = null;
            cache.removeAllElements();
            
            run();
        }catch(Exception ex){
            setExceptionErrMsg( ex );
            setExceptionRetCode( ex );
        }
SanBootView.log.info( getClass().getName(), " get partition (confile) retcode: "+ getRetCode() );     
        boolean isOk = this.isOKForExcuteThisCmd();
        if( !isOk ){
SanBootView.log.error( getClass().getName(), " get partition (confile) errmsg: "+ getErrMsg() );              
        }
        return isOk;
    }
    
    public Vector<SystemPartitionForWin> getAllPartition(){
        int size = cache.size();
        Vector<SystemPartitionForWin> ret = new Vector<SystemPartitionForWin>( size );
        for( int i=0; i<size; i++ ){
            ret.addElement( cache.elementAt(i) );
        }
        return ret;
    }

    public SystemPartitionForWin getSysPartStatistic( String driver ){
        SystemPartitionForWin part;
        
        int size = cache.size();
        for( int i=0; i<size; i++ ){
            part = cache.elementAt(i);
            if( part.disklabel.toUpperCase().equals( driver.toUpperCase() ) ){
                return part;
            }
        }
        
        return null;
    }

    public String prtMe(){
        if( this.isCMDPCmd() ){
            return prtMeForMTPP();
        }else{
            return prtMeForCMDP();
        }
    }

    public String prtMeForCMDP(){
        boolean isFirst = true;
        String tmp;
        SystemPartitionForWin part;
        StringBuffer buf = new StringBuffer();

        int size = cache.size();
        for( int i=0; i<size; i++ ){
            part = cache.elementAt( i );
            tmp = part.disklabel+";"+part.uuid+";"+part.volInfo+";"+part.fsType+
                  ";"+part.size+";"+part.layoutcount+";"+part.layouts;
            if( isFirst ){
                buf.append( tmp );
                isFirst = false;
            }else{
                buf.append( "\n" + tmp );
            }
        }

        return buf.toString();
    }

    public String prtMeForMTPP(){
        boolean isFirst = true;
        String tmp;
        SystemPartitionForWin part;
        StringBuffer buf = new StringBuffer();
        
        int size = cache.size();
        for( int i=0; i<size; i++ ){
            part = cache.elementAt( i );
            tmp = part.disklabel+";"+part.isActive+";"+part.label+";"+part.getVolInfo()+";"+part.fsType+
                  ";"+part.size+";"+part.available+";"+part.vender+";"+part.iscsiVar;
            if( isFirst ){
                buf.append( tmp );
                isFirst = false;
            }else{
                buf.append( "\n" + tmp );
            }
        }
        
        return buf.toString();
    }
}
