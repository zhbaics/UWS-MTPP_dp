/*
 * GetSystemTime.java
 *
 * Created on April 22, 2005, 1:12 PM
 */

package dataprotect.data;
import dataprotect.ui.SanBootView;

/**
 *
 * @author  Administrator
 */
public class GetTargetServerName extends NetworkRunning{
    private String name="";
    
    /** Creates a new instance of GetSystemTime */
    public GetTargetServerName( String cmd ){
        super( cmd );
    }
    
    public void parser( String line ){
        if( line == null || line.length() == 0 ) return;
SanBootView.log.debug(getClass().getName(),"========> "+ line );         
        name = line.trim();   
    }
    
    public String getTargetSrvName(){
SanBootView.log.info( getClass().getName(), " get target server name cmd: " + getCmdLine() );
        try{
            run();
        }catch(Exception ex){
            setExceptionErrMsg( ex );
            setExceptionRetCode(ex );
        }
SanBootView.log.info( getClass().getName(), " get target server name cmd retcode: " + getRetCode());
        if( getRetCode() != AbstractNetworkRunning.OK ){
SanBootView.log.error( getClass().getName(), " get target server name cmd errmsg: " + getErrMsg());    
            return "";
        }else{
            return name;
        }
    }
}
