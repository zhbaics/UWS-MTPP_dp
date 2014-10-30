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
public class GetKernelVer extends NetworkRunning{
    private String kernelVer = "";
    
    /** Creates a new instance of GetSystemTime */
    public GetKernelVer( String cmd ){
        super( cmd );
    }
    
    public void parser( String line ){
        if( line == null || line.length() == 0 ) return;
SanBootView.log.debug(getClass().getName(),"========> "+ line );         
        kernelVer = line;
    }
    
    public String getKernelVer(){
SanBootView.log.info( getClass().getName()," get kernel ver: "+ getCmdLine()  ); 
        try{
            kernelVer = "";
            run();
        }catch(Exception ex){
            setExceptionErrMsg( ex );
            setExceptionRetCode(ex );
        }
SanBootView.log.info( getClass().getName(), " get kernel ver retcode: "+ getRetCode() ); 

        if( getRetCode() != AbstractNetworkRunning.OK ){
SanBootView.log.error( getClass().getName(), " get kernel ver errMsg: "+ getErrMsg() );     
            return "";
        }else{
            return kernelVer;
        }
    }
}
