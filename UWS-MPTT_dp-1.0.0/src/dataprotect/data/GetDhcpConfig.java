/*
 * GetDhcpConfig.java
 *
 * Created on March 28, 2005, 4:56 PM
 */

package dataprotect.data;
import java.io.*;
import java.net.*;

import dataprotect.ui.SanBootView;

/**
 *
 * @author  Administrator
 */
public class GetDhcpConfig extends NetworkRunning{
    private DHCPConf dhcp_conf = new DHCPConf();
    private StringBuffer buf;
    
    /** Creates a new instance of GetDhcpConfig */
    public GetDhcpConfig( String cmd,Socket socket ) throws IOException {
        super( cmd,socket );
    }
    
    public GetDhcpConfig( String cmd ){
        super( cmd );
    }
    
    public GetDhcpConfig( Socket socket ) throws IOException {
        super( socket );
    }
    
    public void parser(String line){
        if( line == null || line.length() == 0 || line.startsWith("#") ){
            return;
        }
        buf.append( line );
        buf.append( "\n" );
    }
    
    public boolean getContent(){
SanBootView.log.info( getClass().getName(), " get dhcp config cmd: " + this.getCmdLine() );
        try{
            buf = new StringBuffer();
            this.dhcp_conf.clearAllDHCP();

            run();
        }catch(Exception ex){
            setExceptionErrMsg( ex );
            setExceptionRetCode( ex );
        }
SanBootView.log.info( getClass().getName(), " get dhcp config retcode: " + this.getRetCode()  );     
        boolean isOk = ( getRetCode() == AbstractNetworkRunning.OK );
        if( !isOk ){
SanBootView.log.error( getClass().getName(), " get dhcp config errmsg: " + this.getErrMsg()  );               
        }
        this.dhcp_conf.parserConf( buf );
        return isOk;
    }
    
    public DHCPOpt getCurrentDhcp( String title ){
        return this.dhcp_conf.getOneDHCP( title );
    }

    public void addOneDhcp( DHCPOpt one ){
        this.dhcp_conf.addOneDhcp( one );
    }

    public void removeOneDhcp( DHCPOpt one ){
        this.dhcp_conf.removeOneDhcp( one );
    }

    public String prtMe(){
        return this.dhcp_conf.prtMe1();
    }
}
