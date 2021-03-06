package dataprotect.cmdp.service;

/**
 * GetVolInfoOnClnt 2010.07.12 AM 11:51
 */
import dataprotect.data.*;
import dataprotect.res.ResourceCenter;
import java.io.*;
import java.net.*;
import java.util.*;
import dataprotect.ui.*;

public class GetVolInfoOnClnt extends NetworkRunning {
    private SystemPartitionForWin curPart = null;
    private Vector<SystemPartitionForWin> cache = new Vector<SystemPartitionForWin>();

    public void parser( String line ){
        this.parserCMDP( line );
    }

    public void parserCMDP( String line ){
        if( !this.isContinueToParserRetValueForCMDP1(line) )return;

        if( !this.isEqZero() ){
            this.errMsg += line +"\n";
        }else{
            String s1 = line.trim();
SanBootView.log.debug(getClass().getName(),"#####: " + s1 );

            int index = s1.indexOf("=");
            if( index>0){
                String value = s1.substring( index+1 ).trim();
SanBootView.log.debug(getClass().getName(),"@@@@@: "+value );

                if( s1.startsWith( SystemPartitionForWin.Partition_CMDP_NAME ) ){
                }else if( s1.startsWith( SystemPartitionForWin.Partition_CMDP_UUID )){
                    curPart.uuid = value;
                    curPart.type = ResourceCenter.CMD_TYPE_CMDP;
                    cache.add( curPart );
                }else if( s1.startsWith( SystemPartitionForWin.Partition_CMDP_VOLNAME ) ){
                    curPart.volname = value;
                    curPart.volInfo = curPart.volname;
                }else if( s1.startsWith( SystemPartitionForWin.Partition_CMDP_ISCSI ) ){
                    curPart.iscsi = value;
                }else if( s1.startsWith( SystemPartitionForWin.Partition_CMDP_SIZE ) ){
                    curPart.size = value;
                }else if(s1.startsWith( SystemPartitionForWin.Partition_CMDP_LAYOUTCNT)){
                    curPart.layoutcount=value;
                }else if(s1.startsWith( SystemPartitionForWin.Partition_CMDP_LETTER )){
                    curPart.disklabel = value+":\\";
                }else if(s1.startsWith( SystemPartitionForWin.Partition_CMDP_HASFS )){
                    curPart.hasfs = value;
                }else if( s1.startsWith( SystemPartitionForWin.Partition_CMDP_LAYOUTS ) ){
                    curPart.layouts = value;
                }else if( s1.startsWith( SystemPartitionForWin.Partition_CMDP_FS ) ){
                    curPart.fsType = value;
                }else if( s1.startsWith( SystemPartitionForWin.Partition_CMDP_IBOOT )){
                    curPart.isboot = value;
                }
            }else{
                if( s1.startsWith( BootHost.CMDPRECFLAG )){
                    this.curPart = new SystemPartitionForWin();
                }
            }
        }
    }

    public GetVolInfoOnClnt( String cmd,Socket socket ) throws IOException {
        super( cmd,socket );
    }
    
    public GetVolInfoOnClnt( String cmd ){
        super( cmd );
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

    public Object[] getAllLocalDiskPartition(){ // 经过排序的
        int size = cache.size();
        Vector<SystemPartitionForWin> ret = new Vector<SystemPartitionForWin>( size );
        for( int i=0; i<size; i++ ){
            SystemPartitionForWin part = cache.elementAt(i);
            if( part.fsType.length() != 0 && !part.fsType.toUpperCase().equals("CDFS") && part.iscsi.equals("0") && !part.disklabel.equals(":\\") ){
                ret.addElement( cache.elementAt(i) );
            }
        }

        Object[] partList = ret.toArray();
        Arrays.sort( partList );
        return partList;
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

    public String prtMeForCMDP(){
        boolean isFirst = true;
        String tmp;
        SystemPartitionForWin part;
        StringBuffer buf = new StringBuffer();

        int size = cache.size();
        for( int i=0; i<size; i++ ){
            part = cache.elementAt( i );
            if( !part.iscsi.equals("0") || part.fsType.length() == 0 || part.fsType.toUpperCase().equals("CDFS") || part.disklabel.equals(":\\") ) continue;
            //if( !part.iscsi.equals("0") || part.fsType.equals("") || part.disklabel.equals(":\\") ) continue;

            tmp = part.disklabel+";"+part.uuid+";"+part.getVolInfo()+";"+part.fsType+
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
}
