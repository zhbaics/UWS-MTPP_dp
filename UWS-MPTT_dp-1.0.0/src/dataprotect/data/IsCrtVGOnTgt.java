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
public class IsCrtVGOnTgt extends NetworkRunning{
    private String ret="";
    
    /** Creates a new instance of GetSystemTime */
    public IsCrtVGOnTgt( String cmd ){
        super( cmd );
    }
    
    public IsCrtVGOnTgt(){
    }
    
    public void parser( String line ){
        if( line == null || line.length() == 0 ) return;
SanBootView.log.debug(getClass().getName(),"(IsCrtVGOnTgt)========> "+ line );         
        ret = line;
    }
    
    public boolean isCrtVgOnTgt(){ 
SanBootView.log.info( getClass().getName(), " isCrtVgOnTgt cmd: "+ this.getCmdLine() );
        try{
            run();
        }catch(Exception ex){
            setExceptionErrMsg( ex );
            setExceptionRetCode(ex );
        }
SanBootView.log.info( getClass().getName(), " isCrtVgOnTgt retcode: "+ this.getRetCode() );
        
        if( getRetCode() != AbstractNetworkRunning.OK ){
SanBootView.log.error( getClass().getName(), " isCrtVgOnTgt errmsg: "+ this.getErrMsg() );    
            return false;
        }else{
            return ret.equals("0");
        }
    }
}
