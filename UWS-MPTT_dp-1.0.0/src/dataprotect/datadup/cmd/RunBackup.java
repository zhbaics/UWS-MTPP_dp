package dataprotect.datadup.cmd;

import dataprotect.data.AbstractNetworkRunning;
import dataprotect.data.NetworkRunning;
import java.net.*;
import java.io.*;

public class RunBackup extends NetworkRunning{
    public void parser(String line){};

    public RunBackup( String cmd,Socket socket ) throws IOException {
        super( cmd,socket );
    }
  
    public boolean isOk(){
        return getRetCode() == AbstractNetworkRunning.OK;
    }
}
