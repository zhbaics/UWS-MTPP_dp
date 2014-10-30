/*
 * GetSystemTime.java
 *
 * Created on April 22, 2005, 1:12 PM
 */

package dataprotect.data;

import dataprotect.ui.SanBootView;
import java.util.regex.*;


/**
 *
 * @author  Administrator
 */
public class MountLV extends NetworkRunning{  
    private String mp = "";
    
    /** Creates a new instance of GetSystemTime */
    public MountLV( String cmd ){
        super( cmd );
    }
    
    public MountLV(){
    }
    
    public void parser( String line ){
        if( line == null || line.length() == 0 ) return;
SanBootView.log.debug(getClass().getName()," mount lv output: "+line ); 
        
        String[] lines = Pattern.compile("\\s+").split( line,-1 );
        try{
            String dev = lines[0];
SanBootView.log.debug(getClass().getName()," mount lv output dev: "+dev );
            if( !lines[1].equals("mounted") ) return;
            if( !lines[2].equals("on") ) return;
            if( !lines[4].toUpperCase().equals("SUCCESSFULLY")) return;
            mp = lines[3];
SanBootView.log.debug(getClass().getName()," get ok,mp="+mp );
            
        }catch(Exception ex){
            //ex.printStackTrace();
            mp = "";
        }
    }
    
    public String getMountPoint(){
        return mp;
    }
}