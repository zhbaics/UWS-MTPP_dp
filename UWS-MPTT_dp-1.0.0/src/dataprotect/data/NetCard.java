/*
 * NetCard.java
 *
 * Created on 2006/12/29,�PM 2:06
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package dataprotect.data;

import java.util.Vector;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class NetCard {
    public boolean isSel = false;
    public String info =""; // adapter info
    public String mac =""; 
    public Vector<BindIPAndMask> ipList = new Vector<BindIPAndMask>(); //主机原来的ip/mask组
    public boolean isGiga = false; // is a giga netcard?
    
    /** Creates a new instance of NetCard */
    public NetCard() {
    }
    
    public NetCard( String mac ){
        this.mac = mac;
    }
    
    @Override public String toString(){
        return info;
    }
    
    public void addIpBinder( BindIPAndMask binder ){
        ipList.add( binder );
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
    
    public String prtMe(){
        BindIPAndMask binder;
        StringBuffer ret;
        
        ret = new StringBuffer(info);                   //ret = info;
        ret.append(";").append(mac).append(" ");        //ret += ";"+ mac+" ";
        int size = ipList.size();
        for( int i=0; i<size; i++ ){
            binder = ipList.elementAt(i);
            ret.append(binder.ip).append(";").append(binder.mask).append(";");    //ret+=binder.ip+";"+binder.mask+";";
        }
        
        return ret.toString();
    }
    
    public static String getSimpleMac( String mac ){
        StringBuffer ret = new StringBuffer("");
        String[] items = Pattern.compile("-").split( mac,-1 );
        for( int i=0; i<items.length; i++ ){
            if( items[i].length() == 0 )continue;
            ret.append(items[i]);               //ret +=items[i];
        }
        return ret.toString();
    }
    
    public static String getSimpleMac1( String mac ){
        StringBuffer ret = new StringBuffer("");
        String[] items = Pattern.compile(":").split( mac,-1 );
        for( int i=0; i<items.length; i++ ){
            if( items[i].length() == 0 )continue;
            ret.append(items[i]);               //ret +=items[i];
        }
        return ret.toString();
    }
    
    public static String getUnixMac( String mac ){
        StringBuffer ret = new StringBuffer("");
        boolean isFirst = true;
        
        String[] items = Pattern.compile("-").split( mac,-1 );
        for( int i=0; i<items.length; i++ ){
            if( items[i].length() == 0 )continue;
            if( isFirst ){
                ret = new StringBuffer(items[i]);
                isFirst = false;
            }else{
                ret.append(":").append(items[i]);
            }
        }
        return ret.toString();
    }
}
