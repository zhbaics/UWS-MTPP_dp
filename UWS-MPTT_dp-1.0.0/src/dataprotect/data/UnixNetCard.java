/*
 * NetCard.java
 *
 * Created on 2006/12/29,�PM�2:06
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package dataprotect.data;

import java.util.Vector;
import java.util.regex.*;

/**
 *
 * @author Administrator
 */
public class UnixNetCard {
    public boolean isSel = false;
    public String netInterface ="";
    public String mac ="";
    public Vector<BindIPAndMask> ipList = new Vector<BindIPAndMask>(); //主机原来的ip/mask组
    
    public String ip=""; // 用户所选的ip
    public String subnet=""; //用户所选的subnet
    public String dns="";
    public String gateway="";
    
    /** Creates a new instance of NetCard */
    public UnixNetCard() {
    }
    
    @Override public String toString(){
        return netInterface;
    }
    
    public Vector<BindIPAndMask> getIPList(){
        int size = ipList.size();
        if( size > 0 ){
            return ipList;
        }else{
            BindIPAndMask binder = new BindIPAndMask();
            Vector<BindIPAndMask> ret = new Vector<BindIPAndMask>(1);
            ret.addElement( binder );
            return ret;
        }
    }
    
    public String getDefaultIP(){
        int size = ipList.size();
        if( size > 0 ){
            BindIPAndMask binder = ipList.elementAt(0);
            return binder.ip;
        }else{
            return "";
        }
    }
    
    public String prtMe( boolean isboot ){
        BindIPAndMask binder;
        StringBuffer ret;
        
        if( isboot){
            ret = new StringBuffer(netInterface + "(" + "*" + ")" + ";" + mac + ";");
        }else{
            ret = new StringBuffer(netInterface + ";" + mac + ";");
        }
        int size = ipList.size();
        for( int i=0; i<size; i++ ){
            binder = ipList.elementAt(i);
            ret.append(binder.ip).append(";").append(binder.mask).append(";");      //ret+=binder.ip+";"+binder.mask+";";
        }
        
        return ret.toString();
    }
    
    public static String getSimpleMac( String mac ){
        StringBuffer ret = new StringBuffer("");
        String[] items = Pattern.compile(":").split( mac,-1 );
        for( int i=0; i<items.length; i++ ){
            ret.append(items[i]);           //ret +=items[i];
        }
        return ret.toString();
    }
    
    public String getMac1(){
        StringBuffer ret= new StringBuffer("01");
        String[] items = Pattern.compile(":").split( mac,-1 );
        for( int i=0; i<items.length; i++ ){
            ret.append("-").append(items[i]);           //ret +="-"+items[i];
        }
        return ret.toString();
    }
    
    public static String getPXEMac( String mac ){
        StringBuffer ret = new StringBuffer("01");
        String[] items = Pattern.compile(":").split( mac,-1 );
        for( int i=0; i<items.length; i++ ){
            ret.append("-").append(items[i]);               //ret +="-"+items[i];
        }
        return ret.toString();
    }
}