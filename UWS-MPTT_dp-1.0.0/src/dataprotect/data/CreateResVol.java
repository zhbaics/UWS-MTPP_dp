package dataprotect.data;

import java.io.*;
import java.net.*;
import java.util.regex.*;
import dataprotect.ui.*;

public class CreateResVol extends NetworkRunning {
    private String volName = null;
    private String devName = null;
    private String mp = null;
    
    public void parser(String line)  {
        // ResVol4 ResVol4 /mnt/ResVol4
        if( line == null || line.length() == 0 ) return;
SanBootView.log.debug(this.getClass().getName(), "CreateResVol output ====> "+ line ); 
            String[] list = Pattern.compile("\\s+").split( line,-1 );
            volName = list[0];
            devName = list[1];
            mp = list[2];
    }

    public CreateResVol( String cmd,Socket socket ) throws IOException {
        super( cmd,socket );
    }
    
    public CreateResVol( String cmd ){
        super( cmd );
    }

    public boolean realDo(){   
SanBootView.log.info(this.getClass().getName(), "CreateResVol cmd"+ this.getCmdLine() );         
        try{
            volName = null; devName = null; mp = null;            
            run();
        }catch(Exception ex){
            setExceptionErrMsg( ex );
            setExceptionRetCode( ex );
        }
SanBootView.log.info(this.getClass().getName(), "CreateResVol cmd retcode"+ this.getRetCode() ); 
        boolean isOk = ( getRetCode() == AbstractNetworkRunning.OK );
        if( !isOk ){
SanBootView.log.error(this.getClass().getName(), "CreateResVol cmd errmsg"+ this.getErrMsg() );             
        }
        return isOk;
    }
    
    public String getShname(){
        return volName;
    }
    
    public String getDevName(){
       return "/dev/evms/"+devName;
    }
    
    public String getMp(){
        return mp;
    }
}
