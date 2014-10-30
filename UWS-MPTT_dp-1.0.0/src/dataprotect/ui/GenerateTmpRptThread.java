/*
 * GenerateTmpRptThread.java
 *
 * Created on 2007/5/18, PM 4:35
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package dataprotect.ui;

import java.io.*;
import dataprotect.data.*;
import dataprotect.res.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class GenerateTmpRptThread extends BasicGetSomethingThread{
    private GenerateUWSReport action = null;
    private boolean retval;
    private String beginDate;
    private String endDate;
    
    /** Creates a new instance of GenerateTmpRptThread */
    public GenerateTmpRptThread(
        SanBootView view,
        String _beginDate,
        String _endDate
    ) {
        super( view );
        
        beginDate = _beginDate;
        endDate = _endDate;
    }
    
    public boolean realRun(){
        InputStream inputStream = null;
        InputStreamReader reader1;
        InputStream inputStream2 = null;
        InputStreamReader reader2;
        try{
            action = new GenerateUWSReport(
                ResourceCenter.getCmd( ResourceCenter.CMD_GENERATE_UWS_RPT )+" "+beginDate +" "+endDate + " 0",
                view.getSocket()
            );            
            retval = action.generateRpt();
            if( retval ){
                // 获得当前的运行环境
                Runtime rt = Runtime.getRuntime();
                // 真正运行命令�����
                Process pr = rt.exec( "cmd /c start http://" + view.initor.serverIp+"/tmpreport/" + action.getTmpRptFile() );
                
                inputStream = pr.getInputStream();
                reader1 = new InputStreamReader( inputStream );
                
                String line1;
                while( ( line1 = readLine(reader1) ) != null ){
                    //parser(line1);
                }
                reader1.close();
                inputStream.close();
                
                int retCode = pr.waitFor();
                if( retCode != 0 ){
                    inputStream2 = pr.getErrorStream();
                    reader2 = new InputStreamReader( inputStream2 );
                    
                    String line2;
                    StringBuffer errMsg_sb =  new StringBuffer(errMsg);
                    while((line2=readLine(reader2))!=null){
                        errMsg_sb.append(line2).append("\n");       //errMsg += line2+"\n";
                    }
                    errMsg = errMsg_sb.toString();
                    reader2.close();
                    inputStream2.close();
                    retval = false;
                }else{
                    retval = true;
                }
            }else{
                errMsg = action.getErrMsg();
            }
        }catch(Exception ex){
            pdiag.dispose();
            ex.printStackTrace();
        }finally{
            try {
                if( inputStream != null ){ inputStream.close();}
            } catch (IOException ex) {
                Logger.getLogger(GenerateTmpRptThread.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if( inputStream2 != null ){
                        inputStream2.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GenerateTmpRptThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return true; // 这样就不会直接报告错误了
    }
    
    public static String readLine(InputStreamReader in) throws IOException{
        StringBuffer strBuf = new StringBuffer();
        int c;
        c = in.read();
        while( c>=0 && c!='\n'){
            strBuf.append((char)c);
            c = in.read();
        }
        if( c<0 && strBuf.length() == 0 )
            return null;
        return strBuf.toString();
    }   
    
    public boolean getRetVal(){
        return retval;
    }
    public String getErrorMsg(){
        return errMsg;
    }
}