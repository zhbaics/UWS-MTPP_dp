/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataprotect.cmdp.action;

import dataprotect.MenuAndBtnCenterForMainUi;
import dataprotect.cmdp.entity.ServicesOnVolume;
import dataprotect.cmdp.ui.PhyRestoreOriginalDiskWizardDialog;
import dataprotect.data.BootHost;
import dataprotect.data.DestAgent;
import dataprotect.data.DhcpClientInfo;
import dataprotect.data.NetCard;
import dataprotect.data.SourceAgent;
import dataprotect.res.ResourceCenter;
import dataprotect.ui.ChiefNetBootHost;
import dataprotect.ui.GeneralActionForMainUi;
import dataprotect.ui.SanBootView;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import mylib.UI.BrowserTreeNode;

/**
 * RestoreOriginalDataByPhyProtect.java
 *
 * Created on 2010-6-7, 13:49:26
 */
public class RestoreOriginalDataByPhyProtect extends GeneralActionForMainUi{
    private boolean isSwitchDisk = false;

    public RestoreOriginalDataByPhyProtect(){
        this( false );
    }

     public RestoreOriginalDataByPhyProtect( boolean isSwitch ){
        super(
            ResourceCenter.BTN_ICON_RD_16,
            ResourceCenter.BTN_ICON_RD_50,
            "View.MenuItem.rstOriData",
            MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_RESTORE_DISK
        );
        this.isSwitchDisk = isSwitch;
    }
    
    @Override public void doAction(ActionEvent evt){
        boolean isOk,needModUUID = false;
        Vector sysPartList;

        if( this.isSwitchDisk ){
SanBootView.log.info(getClass().getName(),"########### Entering phy-switch-restore original disk action. " );
        }else{
SanBootView.log.info(getClass().getName(),"########### Entering phy-restore original disk action. " );
        }

        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;

        if( selObj instanceof BootHost ){
            BootHost host = (BootHost)selObj;

            if( !host.isWinHost() ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.notWinHost")
                );
                return;
            }

            // 判断是否成功初始化了
            if( !host.isInited() ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.notInited")
                );
                return;
            }

            if( !this.isSwitchDisk ){
                // 判断是否从 network 启动
                isOk = view.initor.mdb.isStartupfromSAN( host.getIP(),host.getPort(),"C",ResourceCenter.CMD_TYPE_CMDP );
                if( isOk ){
                    if( !view.initor.mdb.isStartupFromSAN() ){
                        JOptionPane.showMessageDialog( view,
                            SanBootView.res.getString("MenuAndBtnCenter.error.notStartFromNet")
                        );
                        return;
                    }
                }else{
                    JOptionPane.showMessageDialog( view,
                        ResourceCenter.getCmdString( ResourceCenter.CMD_IS_STARTUP_FROM_NET )+" : "+
                            view.initor.mdb.getErrorMessage()
                    );
                    return;
                }
            }
            
            String targetSrvName = view.initor.mdb.getHostName();
            if( targetSrvName.length() == 0 ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("EditProfileDialog.error.getHostNameFailed")
                );
                return;
            }

            String windir = view.initor.mdb.getWinDir( host.getIP(),host.getPort(),ResourceCenter.CMD_TYPE_CMDP );
SanBootView.log.info(getClass().getName(), "windir: " + windir );
            if( windir.length() == 0 ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_WINDIR )+
                    ": "+
                    view.initor.mdb.getErrorMessage()
                );
                return;
            }

            isOk = view.initor.mdb.getVolInfoForCMDP( host.getIP(),host.getPort() );
            if( !isOk ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_PARTITION )+"  "+
                    SanBootView.res.getString("common.failed")
                );
                return;
            }else{
                sysPartList = view.initor.mdb.getVolInfoForCMDP();
            }

            DestAgent da = new DestAgent(
                -1,
                host.getIP(),
                host.getPort(),
                host.getOS(),
                "",  // mac
                "",  // desc
                BootHost.PROTECT_TYPE_CMDP
            );
            da.setHostType( DestAgent.TYPE_ORI_HOST );
            da.setSrc_Agnt_id( host.getID() );
            da.setPort1( host.getMtppPort() );
            da.setHostUUID( host.getUUID() );
            da.setBootMode( host.getBootMode() );
            da.setMachine( host.getMachine() );

            PhyRestoreOriginalDiskWizardDialog dialog = new PhyRestoreOriginalDiskWizardDialog(
                    windir,view,da,targetSrvName,sysPartList,needModUUID,isSwitchDisk,null );
            int width  = 525+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 370+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
        }else{
            DestAgent da = (DestAgent)selObj;
            BrowserTreeNode chiefNBHNode = da.getFatherNode();
            ChiefNetBootHost chiefNBH = (ChiefNetBootHost)chiefNBHNode.getUserObject();
            BrowserTreeNode hostNode = chiefNBH.getFatherNode();
            Object hostObj = hostNode.getUserObject();

            if( !da.isWinHost() ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.notWinHost")
                );
                return;
            }

            // 判断是否从 network 启动
            isOk = view.initor.mdb.isStartupfromSAN( da.getDst_agent_ip(),da.getDst_agent_port(), "C",ResourceCenter.CMD_TYPE_CMDP );
            if( isOk ){
                if( !view.initor.mdb.isStartupFromSAN() ){
                    JOptionPane.showMessageDialog( view,
                        SanBootView.res.getString("MenuAndBtnCenter.error.notStartFromNet")
                    );
                    return;
                }
            }else{
                JOptionPane.showMessageDialog( view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_IS_STARTUP_FROM_NET )+" : "+
                        view.initor.mdb.getErrorMessage()
                );
                return;
            }
            
            String targetSrvName = view.initor.mdb.getHostName();
            if( targetSrvName.length() == 0 ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("EditProfileDialog.error.getHostNameFailed")
                );
                return;
            }

            String windir = view.initor.mdb.getWinDir( da.getDst_agent_ip(),da.getDst_agent_port(),ResourceCenter.CMD_TYPE_CMDP );
SanBootView.log.info(getClass().getName(), "windir: " + windir );
            if( windir.length() == 0 ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_WINDIR )+
                    ": "+
                    view.initor.mdb.getErrorMessage()
                );
                return;
            }

            isOk = view.initor.mdb.getVolInfoForCMDP( da.getDst_agent_ip(),da.getDst_agent_port() );
            if( !isOk ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_PARTITION )+"  "+
                    SanBootView.res.getString("common.failed")
                );
                return;
            }else{
                sysPartList = view.initor.mdb.getVolInfoForCMDP();
            }

            ArrayList<ServicesOnVolume> service_list = null;
            if( hostObj instanceof BootHost ){
                BootHost bh = (BootHost)hostObj;
                da.setSrc_Agnt_id( bh.getID() );
                da.setPort1( bh.getMtppPort() );
                da.setHostType( DestAgent.TYPE_DST_AGNT  );
                da.setHostUUID( bh.getUUID() );
                da.setBootMode( bh.getBootMode() );
                da.setMachine( bh.getMachine() );
                
                // 获取bh的网卡信息
                isOk = view.initor.mdb.getIPInfoFromSrv(  ResourceCenter.CLT_IP_CONF + "/" + bh.getID() + ResourceCenter.CONF_IP );
                if( !isOk ){
                    JOptionPane.showMessageDialog(view,
                        SanBootView.res.getString("InitBootHostWizardDialog.log.getIpInfo")+" "+
                        SanBootView.res.getString("common.failed")
                    );
                    return;
                }else{
                    ArrayList netCardList = view.initor.mdb.getAllNetCardinfoFromSrv();
                    if( !this.isContainedInSet( netCardList, da.getDst_agent_mac() ) ){
                        needModUUID = true;
                    }
                }
            }else{
                SourceAgent sa = (SourceAgent)hostObj;
                da.setSrc_Agnt_id( sa.getSrc_agnt_id() );
                da.setPort1( sa.getSrc_agnt_port1() ); // 表示 mtpp port
                da.setHostType( DestAgent.TYPE_SRC_AGNT );
                da.setRealBootHostFlag( false );
                da.setBootMode( sa.getSrc_agnt_boot_mode() );
                da.setMachine( sa.getMachine() );
                needModUUID = true;

                // 获取跟volume关联的service
                isOk = view.initor.mdb.getServicesOnVolume( da.getServiceOnVolPath() );
                if( !isOk ){
                    JOptionPane.showMessageDialog(view,
                        SanBootView.res.getString("RestoreOriginalDiskWizardDialog.log.getServiceOnVolume")+" "+
                        SanBootView.res.getString("common.failed")
                    );
                    return;
                }else{
                    service_list = view.initor.mdb.getAllServiceListOnVol();
                }
            }
            
            PhyRestoreOriginalDiskWizardDialog dialog = new PhyRestoreOriginalDiskWizardDialog( 
                    windir,view,da,targetSrvName,sysPartList,needModUUID,isSwitchDisk,service_list );
            int width  = 525+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 370+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
        }
        if( !this.isSwitchDisk ){
SanBootView.log.info(getClass().getName(),"########### End of phy-restore original disk action." );
        }else{
SanBootView.log.info(getClass().getName(),"########### End of phy-switch-restore original disk action." );
        }
    }

    private boolean isContainedInSet( ArrayList<NetCard> list,String mac ){
        int size = list.size();
        for( int i=0; i<size; i++ ){
            NetCard nc = list.get(i);
            String smac1 = DhcpClientInfo.getSimpleMac( nc.mac );
            String smac2 = DhcpClientInfo.getSimpleMac( mac );
            if( smac1.equals( smac2) ){
                return true;
            }
        }
        return false;
    }
}
