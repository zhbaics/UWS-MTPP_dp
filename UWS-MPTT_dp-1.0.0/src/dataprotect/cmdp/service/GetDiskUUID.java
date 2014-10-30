/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataprotect.cmdp.service;

import dataprotect.data.NetworkRunning;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class GetDiskUUID extends NetworkRunning{
    private ArrayList uuidlist = new ArrayList();
    
    @Override
    public void parser(String line) {
        
        if( line == null || line.length() == 0 ) return;
        if( !line.startsWith("Result") && !line.startsWith("*") ){
            uuidlist.add(line);
        }
    }
    
    public  void clearCache(){
        uuidlist.clear();
    }
    
    public String getUUIDByDiskNo(int diskno){
        String ret = "";
        if(uuidlist.size() > 0){
            for(int i = 0 ; i < uuidlist.size() ; i++ ){
                String tmp = uuidlist.get(i).toString();
                String temp[] = tmp.split(" ");
                if( temp[0].endsWith(diskno+"") ){
                    ret = temp[1];
                    break;
                }
            }
        } else {
            ret = "";
        }
        return ret;
    }
}
