/*
 * InitApp
 *
 * Created on 2006/11/16, PM 4:55
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package dataprotect.ui;

import dataprotect.cmdp.entity.ChiefPPProfile;
import dataprotect.cmdp.service.InitProgressInfoReceiver;
import dataprotect.cmdp.ui.ChiefSnapshotForCMDP;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.*;
import mylib.UI.*;
import dataprotect.data.*;
import dataprotect.datadup.ui.viewobj.ChiefProfile;
import dataprotect.datadup.ui.viewobj.ChiefScheduler;
import dataprotect.res.*;
import dataprotect.datadup.ui.MonitorInfoReceiver;
import dataprotect.remotemirror.ChiefMJScheduler;
import dataprotect.remotemirror.ChiefRollbackHost;
import dataprotect.unlimitedIncMj.entity.ChiefCloneDiskList;
import dataprotect.unlimitedIncMj.entity.ChiefCopyJobList;
import dataprotect.unlimitedIncMj.entity.ChiefLocalUnLimitedIncMirrorVolList;
import dataprotect.unlimitedIncMj.entity.ChiefLocalUnLimitedIncSnapList;

/**
 *
 * @author Administrator
 */
public class InitApp implements SlowerLaunch {

    public final static String CONF_PATH = "ibconf";
    public ManageDB mdb;
    public DHCPdb dhcpdb;
    private SanBootView view;
    private String status = "";
    public String errormsg = "";
    private boolean isConnected = false;
    private boolean isInited = false;
    private boolean reset_network = false;
    public GUIAdminOpt adminOpt = new GUIAdminOpt();
    // 上一次登陆的uws
    public GUIAdminOptUWS lastUWS = null;
    // 当前正在登陆的UWS
    public GUIAdminOptUWS uws = null;
    public String serverIp = "";
    public String txIp = "";
    public int port = ResourceCenter.C_S_PORT;
    public String user = "";
    public String passwd = "";
    public String dhcpIp = "";
    public int dhcp_port = ResourceCenter.C_S_PORT;
    public String dhcp_acct = ResourceCenter.DEFAULT_ACCT;
    public String dhcp_passwd = "";
    public String post_process_ip = "";
    public String lang = "zh";  // 英文:en    中文:zh
    public int loglevel = ResourceCenter.LOG_LEVEL_INFO;
    private int cnt = 0;
    public boolean isCrtedVg = false;
    public static boolean recLog = false;
    public Socket socket = null;
    public Socket dhcpSocket = null;
    private int errNum = 0;

    public InitApp(SanBootView _view){
        view = _view;

        MonitorInfoReceiver monitor = new MonitorInfoReceiver(view);
        monitor.start();
        // 首先使得 monitorInfoReceiver阻塞睡觉, 当到进入
        // monitordialog 时, 将之唤醒。
        monitor.requestSuspend();

        InitProgressInfoReceiver initProgressMonitor = new InitProgressInfoReceiver(view);
        initProgressMonitor.start();
        // 首先使得 InitProgressInfoReceiver 阻塞睡觉, 当到进入
        // QueryInitProgressDialog 时, 将之唤醒。
        initProgressMonitor.requestSuspend();

        mdb = new ManageDB(view);
        mdb.setMonitor(monitor);
        mdb.setInitProgressMonitor(initProgressMonitor);
        dhcpdb = new DHCPdb();
        getConf();
    }

    public synchronized boolean isLogined() {
        return this.isConnected;
    }

    public synchronized void setLoginedFlag(boolean val) {
        this.isConnected = val;
    }

    public synchronized boolean isInited() {
        return this.isInited;
    }

    public synchronized void setInitedFlag(boolean val) {
        this.isInited = val;
    }

    public synchronized boolean isResetNetwork() {
        return this.reset_network;
    }

    public synchronized void setResetNetwork(boolean val) {
        this.reset_network = val;
    }

    public boolean init() {
        boolean licenseOk = true;
        boolean ret = true;
        StringBuffer errBuf = new StringBuffer();

        errNum = 0;

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.conf");
        // 分析服务器端的配置文件
        if (!mdb.getConfig()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_CONF) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }

        // 根据最新的path来设置一些cmd的路径
        mdb.setCmdPath();

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.dhcpConf");
        // 分析服务器端的dhcp配置文件
        if (!mdb.getDhcpConfig()) {
            // don't care what happen.
        } else {
            String srvip;
            if (uws != null) {
                srvip = uws.getServerIp();
            } else {
                srvip = this.serverIp;
            }
            DHCPOpt dhcp = mdb.getCurrentDhcp(srvip);
            if (dhcp != null) {
                dhcpIp = dhcp.getServerIp();
                dhcp_port = dhcp.getIntPort();
                dhcp_acct = dhcp.getUser();
                dhcp_passwd = dhcp.getPassword();
            } else {
                dhcpIp = "";
                dhcp_port = ResourceCenter.C_S_PORT;
                dhcp_acct = "";
                dhcp_passwd = "";
            }
        }

        // 先与dhcp server通信,并获取dhcp info
        boolean ok = view.initor.realLogin(dhcpIp, dhcp_port, dhcp_acct, dhcp_passwd, 1);
        if (ok) {
            SanBootView.log.info(getClass().getName(), "getting dhcp info.....");
            totalStep = 25;

            cnt++;
            status = SanBootView.res.getString("common.loadstatus.isDHCPStart");
            boolean dhcpEnable = false;
            boolean viewOk = dhcpdb.viewFileContents("/etc/sysconfig/customdrv/dhcpenable");
            if (viewOk) {
                dhcpEnable = true;
            } else {
                // 通信错误
                if (dhcpdb.getErrorCode() <= -1000) {
                    errNum += 1;
                    errBuf.append(
                            "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_DHCP_START) + " : "
                            + dhcpdb.getErrorMessage());
                    ret = false;
                }
            }
            SanBootView.log.info(getClass().getName(), " dhcpEnable: " + dhcpEnable);

            dhcpdb.setDHCPStartFlag(dhcpEnable);

            cnt++;
            status = SanBootView.res.getString("common.loadstatus.global");
            if (!dhcpdb.getGlobalOptFromDhcp()) {
                errNum += 1;
                errBuf.append(
                        "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_DHCP_OPT) + " : "
                        + dhcpdb.getErrorMessage());
                ret = false;
            }

            cnt++;
            status = SanBootView.res.getString("common.loadstatus.subnet");
            if (!dhcpdb.getSubnetFromDhcp()) {
                errNum += 1;
                errBuf.append(
                        "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_SUBNET) + " : "
                        + dhcpdb.getErrorMessage());
                ret = false;
            }

            cnt++;
            status = SanBootView.res.getString("common.loadstatus.ibootsrv");
            if (!dhcpdb.getIBootSrvFromDhcp()) {
                errNum += 1;
                errBuf.append(
                        "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_DHCP_IBOOT) + " : "
                        + dhcpdb.getErrorMessage());
                ret = false;
            }

            cnt++;
            status = SanBootView.res.getString("common.loadstatus.clnt");
            if (!dhcpdb.getClientFromDhcp()) {
                errNum += 1;
                errBuf.append(
                        "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_CLT_DHCP) + " : "
                        + dhcpdb.getErrorMessage());
                ret = false;
            }
        } else {
            dhcpdb.setLoginOKFlag(false);
            dhcpdb.clearDb();
            if (dhcpIp.length() == 0 || dhcp_acct.length() == 0 || dhcp_passwd == null || dhcp_passwd.length() == 0) {
                errNum += 1;
                errBuf.append(
                        "\n" + errNum + ". " + SanBootView.res.getString("common.error.noDHCP"));
            } else {
                errNum += 1;
                errBuf.append(
                        "\n" + errNum + ". " + this.errormsg);
            }
            ret = false;
        }

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.host");
        if (!mdb.updateBootHost()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_BOOT_HOST) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.clnt");
        if (!mdb.getBakClientList()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_CLIENT) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.vol");
        if (!mdb.updateVolumeMap()) {
            //if( !mdb.pseduoUpdateVolMap() ){
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_VOLMAP) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }

        // 调整volumeMap的保护类型,为了向前兼容。 2011.4.21 - jason
        mdb.updatePTypeForVolumeMap();

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.iboot");
        if (!mdb.listIboot()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_LIST_IBOOT) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        } else {
            if (!mdb.isStartupIboot()) {
                errNum += 1;
                errBuf.append(
                        "\n" + errNum + ". " + SanBootView.res.getString("common.error.ibootNotStartup"));
                ret = false;
            }
        }

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.service");
        if (!mdb.updateServMap()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_SERVMAP) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.user");
        if (!mdb.updateAllBakUser()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_USER) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }
        view.audit.setLogonUser(view.initor.user);
        view.audit.setUserPassword(view.initor.passwd);

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.getPool");
        if (!mdb.updatePool()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_POOL) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        } else {
            if (mdb.getPoolNum() <= 0) {
                errNum += 1;
                errBuf.append(
                        "\n" + errNum + ". " + SanBootView.res.getString("common.errmsg.noPool"));
                ret = false;
            }
        }

        /*
         * cnt++; status =
         * SanBootView.res.getString("common.loadstatus.getswu"); if(
         * !mdb.updateUWSrv() ){ errNum+=1; errBuf.append( "\n"+ errNum + ".
         * "+ResourceCenter.getCmdString( ResourceCenter.CMD_GET_UWS_SRV )+" :
         * "+ mdb.getErrorMessage() ); ret = false; }
         *
         * cnt++; status =
         * SanBootView.res.getString("common.loadstatus.getsrcagnt"); if(
         * !mdb.updateSrcAgnt() ){ errNum+=1; errBuf.append( "\n"+ errNum + ".
         * "+ResourceCenter.getCmdString( ResourceCenter.CMD_GET_SRCAGNT )+" :
         * "+ mdb.getErrorMessage() ); ret = false; }
         */
        cnt++;
        status = SanBootView.res.getString("common.loadstatus.getmg");
        if (!mdb.updateMg()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_MG) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }
        /*
         * cnt++; status = SanBootView.res.getString("common.loadstatus.getmj");
         * if( !mdb.updateMj() ){ errNum+=1; errBuf.append( "\n"+ errNum + ".
         * "+ResourceCenter.getCmdString( ResourceCenter.CMD_GET_MJ )+" : "+
         * mdb.getErrorMessage() ); ret = false; }
         *
         * cnt++; status =
         * SanBootView.res.getString("common.loadstatus.getmjsch"); if(
         * !mdb.updateMjSch() ){ errNum+=1; errBuf.append( "\n"+ errNum + ".
         * "+ResourceCenter.getCmdString( ResourceCenter.CMD_GET_MJ_SCH )+" : "+
         * mdb.getErrorMessage() ); ret = false; }
         */

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.getDA");
        if (!mdb.updateNBH()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_NETBOOTED_HOST) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.getSU");
        if (!mdb.updateMSU()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_SNAPUSAGE) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }
        /*
         * cnt++; status =
         * SanBootView.res.getString("common.loadstatus.getDiskInfo"); if(
         * !mdb.updateMDI() ){ errNum+=1; errBuf.append( "\n"+ errNum + ".
         * "+ResourceCenter.getCmdString( ResourceCenter.CMD_GET_MIRROR_DISK )+"
         * : "+ mdb.getErrorMessage() ); ret = false; }
         */

        // 调整mirrorDiskInfo的保护类型,为了向前兼容。 2011.4.21 - jason
        mdb.updatePTypeForMirrorDiskInfo();

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.pprofile");
        if (!mdb.updateDg()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + "." + ResourceCenter.getCmdString(ResourceCenter.CMD_CMDP_GET_DG)
                    + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.pparse");
        if (!mdb.updatePPProfileList()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + "." + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_PROFILE) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        } else {
            mdb.prtAllPPProfile();
        }

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.profile");
        if (!mdb.listProfile()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + "." + ResourceCenter.getCmdString(ResourceCenter.CMD_LIST_DIR)
                    + "(" + ResourceCenter.PROFILE_DIR + ")"
                    + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.parse");
        if (!mdb.updateProfile()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + "." + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_PROFILE) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.objlist");
        if (!mdb.updateBakObjList()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + "." + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_BAKOBJECT) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.schlist");
        if (!mdb.updateCronScheduler()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + "." + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_DB_SCHEDULER) + " : "
                    + mdb.getErrorMessage());
            ret = false;
        }

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.isCrtVg");
        long maxTotal = mdb.parsePool();
        if (maxTotal <= 0) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + ". " + SanBootView.res.getString("common.errmsg.parsePool"));
            ret = false;
        } else {
            // 大于10g就认为创建了卷组
            isCrtedVg = maxTotal > 10 * 1024 * 1024 * 1024;
        }

        cnt++;
        status = SanBootView.res.getString("common.loadstatus.chkLic");
        if (!mdb.getLicense()) {
            errNum += 1;
            errBuf.append(
                    "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_LICENCE) + " : "
                    + mdb.getErrorMessage());
            licenseOk = false;
            ret = false;
            ResourceCenter.MAX_SNAP_NUM = 0;
            ResourceCenter.MAX_SNAP_CMDP_NUM = 0;
        } else {
            if (mdb.getSnapInLicOfMTPP() <= 0 || mdb.getSnapInLicOfCMDP() <= 0) {
                errNum += 1;
                errBuf.append(
                        "\n" + errNum + ". " + ResourceCenter.getCmdString(ResourceCenter.CMD_GET_LICENCE) + " : "
                        + mdb.getErrorMessage());
                licenseOk = false;
                ret = false;

                if (mdb.getSnapInLicOfMTPP() <= 0) {
                    ResourceCenter.MAX_SNAP_NUM = 0;
                } else {
                    ResourceCenter.MAX_SNAP_NUM = mdb.getSnapInLicOfMTPP();
                }

                if (mdb.getSnapInLicOfCMDP() <= 0) {
                    ResourceCenter.MAX_SNAP_CMDP_NUM = 0;
                } else {
                    ResourceCenter.MAX_SNAP_CMDP_NUM = mdb.getSnapInLicOfCMDP();
                }
            } else {
                view.isSupportCMDP = mdb.isSupportCMDP();
                ResourceCenter.MAX_SNAP_NUM = mdb.getSnapInLicOfMTPP();
                ResourceCenter.MAX_SNAP_CMDP_NUM = mdb.getSnapInLicOfCMDP();
            }
        }
        SanBootView.log.info(getClass().getName(), "mtpp point: " + view.initor.mdb.getPointOfMTPP());
        SanBootView.log.info(getClass().getName(), "cmdp point: " + view.initor.mdb.getPointOfCMDP());
        SanBootView.log.info(getClass().getName(), "mtpp maxsnap num: " + ResourceCenter.MAX_SNAP_NUM);
        SanBootView.log.info(getClass().getName(), "cmdp maxsnap num: " + ResourceCenter.MAX_SNAP_CMDP_NUM);

        errormsg = SanBootView.res.getString("common.error.initfail") + errBuf.toString();

        if (licenseOk) {
            initTree();
        }

        this.setInitedFlag(true);
        return ret;
    }

    public void initTree() {
        //BrowserTreeNode root = view.getTreeRoot();
        //root.removeAllChildren();
        //view.setRootVisiable( false );

        ChiefLocalUWSManage localUWS = new ChiefLocalUWSManage();
        BrowserTreeNode localUWSNode = new BrowserTreeNode(localUWS, false);
        localUWS.setTreeNode(localUWSNode);
        //localUWS.setFatherNode( root );
        //root.add( localUWSNode );

        BrowserTreeNode root = localUWSNode;
        view.setTreeRoot(localUWSNode);

        ChiefRemoteUWSManage remoteUWS = new ChiefRemoteUWSManage();
        BrowserTreeNode remoteUWSNode = new BrowserTreeNode(remoteUWS, false);
        remoteUWS.setTreeNode(remoteUWSNode);
        remoteUWS.setFatherNode(root);
        //root.add( remoteUWSNode );

        ChiefHost chiefHost = new ChiefHost();
        BrowserTreeNode chiefHostNode = new BrowserTreeNode(chiefHost, false);
        chiefHost.setTreeNode(chiefHostNode);
        chiefHost.setFatherNode(localUWSNode);
        localUWSNode.add(chiefHostNode);
        initHost(chiefHostNode);

        ChiefScheduler chiefSch = new ChiefScheduler();
        BrowserTreeNode chiefSchNode = new BrowserTreeNode(chiefSch, true);
        chiefSch.setTreeNode(chiefSchNode);
        chiefSch.setFatherNode(localUWSNode);
        //localUWSNode.add( chiefSchNode );

        ChiefOrphanVolume chiefOrphVol = new ChiefOrphanVolume();
        BrowserTreeNode chiefOrphVolNode = new BrowserTreeNode(chiefOrphVol, false);
        chiefOrphVol.setTreeNode(chiefOrphVolNode);
        chiefOrphVol.setFatherNode(localUWSNode);
        localUWSNode.add(chiefOrphVolNode);

        ChiefMJManage chiefMJMg = new ChiefMJManage();
        BrowserTreeNode chiefMjMgNode = new BrowserTreeNode(chiefMJMg, true);
        chiefMJMg.setTreeNode(chiefMjMgNode);
        chiefMJMg.setFatherNode(localUWSNode);
        //localUWSNode.add( chiefMjMgNode );

        ChiefMJScheduler chiefMjSch = new ChiefMJScheduler();
        BrowserTreeNode chiefMjSchNode = new BrowserTreeNode(chiefMjSch, true);
        chiefMjSch.setTreeNode(chiefMjSchNode);
        chiefMjSch.setFatherNode(localUWSNode);
        //localUWSNode.add( chiefMjSchNode );

        ChiefDestUWS chiefDestUWS = new ChiefDestUWS();
        BrowserTreeNode chiefDestUWSNode = new BrowserTreeNode(chiefDestUWS, false);
        chiefDestUWS.setTreeNode(chiefDestUWSNode);
        chiefDestUWS.setFatherNode(localUWSNode);
        //localUWSNode.add( chiefDestUWSNode );

        ChiefRollbackHost chiefRbhost = new ChiefRollbackHost();
        BrowserTreeNode chiefRollbackHostNode = new BrowserTreeNode(chiefRbhost, false);
        chiefRbhost.setTreeNode(chiefRollbackHostNode);
        chiefRbhost.setFatherNode(localUWSNode);
        //localUWSNode.add( chiefRollbackHostNode );
        //initRollbackHost( chiefRollbackHostNode );

        ChiefPool chiefPool = new ChiefPool();
        BrowserTreeNode chiefPoolNode = new BrowserTreeNode(chiefPool, true);
        chiefPool.setTreeNode(chiefPoolNode);
        localUWSNode.add(chiefPoolNode);

        view.reloadTreeNode(root);
        //view.reloadTreeNode( localUWSNode );

        // 显示点击 root 后的右边表中的内容
        view.setTreeSelectionRow(0);
        view.getTree().requestFocus();
    }
/*
    private void initRollbackHost(BrowserTreeNode chiefRollbackHostNode) {
        int i, j, size, size1;
        SourceAgent one;
        MirrorDiskInfo mdi;
        ArrayList list;

        ArrayList hostList = view.initor.mdb.getAllRollbackSrcAgnt();
        size = hostList.size();
        for (i = 0; i < size; i++) {
            one = (SourceAgent) hostList.get(i);

            BrowserTreeNode hostNode = new BrowserTreeNode(one, false);
            one.setTreeNode(hostNode);
            one.setFatherNode(chiefRollbackHostNode);
            chiefRollbackHostNode.add(hostNode);

            ChiefHostVolume chiefHostVol = new ChiefHostVolume();
            BrowserTreeNode chiefHVolNode = new BrowserTreeNode(chiefHostVol, false);
            chiefHostVol.setTreeNode(chiefHVolNode);
            chiefHostVol.setFatherNode(hostNode);

            ChiefNetBootHost chiefNBootHost = new ChiefNetBootHost();
            BrowserTreeNode chiefNBHNode = new BrowserTreeNode(chiefNBootHost, false);
            chiefNBootHost.setTreeNode(chiefNBHNode);
            chiefNBootHost.setFatherNode(hostNode);

            hostNode.add(chiefHVolNode);
            hostNode.add(chiefNBHNode);

            list = view.initor.mdb.getMDIFromCacheOnSrcAgntID(one.getSrc_agnt_id());
            size1 = list.size();
            for (j = 0; j < size1; j++) {
                mdi = (MirrorDiskInfo) list.get(j);
                BrowserTreeNode volNode = new BrowserTreeNode(mdi, false);
                mdi.setTreeNode(volNode);
                mdi.setFatherNode(chiefHVolNode);

                if (!mdi.isUIM_Vol()) { // 普通增量镜像卷
                    // 准备lunmap list node
                    ChiefLunMap chiefLm = new ChiefLunMap();
                    BrowserTreeNode chiefLmNode = new BrowserTreeNode(chiefLm, true);
                    chiefLm.setTreeNode(chiefLmNode);
                    chiefLm.setFatherNode(volNode);
                    volNode.add(chiefLmNode);

                    // 准备snapshot list node
                    ChiefSnapshot chiefSnap = new ChiefSnapshot();
                    BrowserTreeNode chiefSnapNode = new BrowserTreeNode(chiefSnap, false);
                    chiefSnap.setTreeNode(chiefSnapNode);
                    chiefSnap.setFatherNode(volNode);
                    volNode.add(chiefSnapNode);

                    // 准备mj list node
                    ChiefMirrorJobList chiefMjList = new ChiefMirrorJobList();
                    BrowserTreeNode chiefMjNode = new BrowserTreeNode(chiefMjList, false);
                    chiefMjList.setTreeNode(chiefMjNode);
                    chiefMjList.setFatherNode(volNode);
                    volNode.add(chiefMjNode);
                } else { // 无限增量镜像卷
                    // unlimited incremental mirror snapshot list
                    ChiefLocalUnLimitedIncSnapList chiefLocalUIMSnapList = new ChiefLocalUnLimitedIncSnapList();
                    BrowserTreeNode chiefLocalUIMSnapNode = new BrowserTreeNode(chiefLocalUIMSnapList, false);
                    chiefLocalUIMSnapList.setTreeNode(chiefLocalUIMSnapNode);
                    chiefLocalUIMSnapList.setFatherNode(volNode);
                    volNode.add(chiefLocalUIMSnapNode);

                    // clone disk list
                    ChiefCloneDiskList chiefCloneDiskList = new ChiefCloneDiskList();
                    BrowserTreeNode chiefCloneDiskNode = new BrowserTreeNode(chiefCloneDiskList, false);
                    chiefCloneDiskList.setTreeNode(chiefCloneDiskNode);
                    chiefCloneDiskList.setFatherNode(volNode);
                    volNode.add(chiefCloneDiskNode);

                    // chief copy job list
                    ChiefCopyJobList chiefCJList = new ChiefCopyJobList();
                    BrowserTreeNode chiefCJNode = new BrowserTreeNode(chiefCJList, false);
                    chiefCJList.setTreeNode(chiefCJNode);
                    chiefCJList.setFatherNode(volNode);
                    volNode.add(chiefCJNode);
                }

                chiefHVolNode.add(volNode);
            }
        }
    }
*/
    public int getIpThirdSegment(String ip){
        int index = ip.lastIndexOf(".");
        String segment = ip.substring(0, index);
        index = segment.lastIndexOf(".");
        String segment1 = segment.substring(index+1, segment.length());
        try{
            return Integer.valueOf(segment1);
        } catch(Exception ex){
            return -1;
        }
    }
    
    //专有方法，只为两个网段提供
    public Vector sortByIpSegment(Vector list , int size){
        Vector ret = new Vector();
        ArrayList tlist = new ArrayList();
        BootHost temp , temp1;
        if( size > 1){
            temp = (BootHost)list.elementAt(0);
            for( int i = 1 ; i < size ; i++ ){
                temp1 = (BootHost)list.elementAt(i);
                if( getIpThirdSegment(temp1.getIP()) > getIpThirdSegment(temp.getIP()) ){
                    tlist.add(temp1);
                } else{
                    ret.add(temp1);
                }
            }
            ret.add(temp);
            if(tlist.size() > 0){
                for( int i = 0 ; i < tlist.size() ; i ++){
                    ret.add(tlist.get(i));
                }
            }
        } else if( size ==1){
            return list;
        }
        return ret;
    }
    
    private void initHost(BrowserTreeNode chiefHostNode) {
        int i, j, size, size1, targetID, maxSnap;
        BootHost one;
        VolumeMap vol, tgt;
        LogicalVol lv;
        Vector list;
        
        Vector hostList1 = view.initor.mdb.getAllBootHost();
        //sort by ip
        Vector hostList = sortByIpSegment(hostList1 ,hostList1.size());
        size = hostList.size();
        for (i = 0; i < size; i++) {
            one = (BootHost) hostList.elementAt(i);
            
                BrowserTreeNode hostNode = new BrowserTreeNode(one, false);
                one.setTreeNode(hostNode);
                one.setFatherNode(chiefHostNode);
                chiefHostNode.add(hostNode);

                ChiefHostVolume chiefHostVol = new ChiefHostVolume();
                BrowserTreeNode chiefHVolNode = new BrowserTreeNode(chiefHostVol, false);
                chiefHostVol.setTreeNode(chiefHVolNode);
                chiefHostVol.setFatherNode(hostNode);

                BrowserTreeNode chiefLProfNode, chiefPProfNode = null;
                if (one.isWinHost() && one.isCMDPProtect()) {
                    ChiefPPProfile chiefPPProf = new ChiefPPProfile();
                    chiefPProfNode = new BrowserTreeNode(chiefPPProf, true);
                    chiefPPProf.setTreeNode(chiefPProfNode);
                    chiefPPProf.setFatherNode(hostNode);

                    ChiefProfile chiefProf = new ChiefProfile();
                    chiefLProfNode = new BrowserTreeNode(chiefProf, false);
                    chiefProf.setTreeNode(chiefLProfNode);
                    chiefProf.setFatherNode(hostNode);
                } else {
                    ChiefProfile chiefProf = new ChiefProfile();
                    chiefLProfNode = new BrowserTreeNode(chiefProf, false);
                    chiefProf.setTreeNode(chiefLProfNode);
                    chiefProf.setFatherNode(hostNode);
                }

                ChiefNetBootHost chiefNBootHost = new ChiefNetBootHost();
                BrowserTreeNode chiefNBHNode = new BrowserTreeNode(chiefNBootHost, false);
                chiefNBootHost.setTreeNode(chiefNBHNode);
                chiefNBootHost.setFatherNode(hostNode);

                hostNode.add(chiefHVolNode);
                if (one.isWinHost() && one.isCMDPProtect()) {
                    hostNode.add(chiefPProfNode);
                }
                //hostNode.add( chiefLProfNode );
                hostNode.add(chiefNBHNode);

                if (one.isWinHost()) {
                    list = view.initor.mdb.getVolMapOnClntID(one.getID());
                    size1 = list.size();
                    for (j = 0; j < size1; j++) {
                        vol = (VolumeMap) list.elementAt(j);

                        BrowserTreeNode volNode = new BrowserTreeNode(vol, false);
                        vol.setTreeNode(volNode);
                        vol.setFatherNode(chiefHVolNode);

                        // 准备lunmap list node
                        ChiefLunMap chiefLm = new ChiefLunMap();
                        BrowserTreeNode chiefLmNode = new BrowserTreeNode(chiefLm, true);
                        chiefLm.setTreeNode(chiefLmNode);
                        chiefLm.setFatherNode(volNode);
                        volNode.add(chiefLmNode);

                        // 准备snapshot list node
                        if (vol.isCMDPProtect()) {
                            ChiefSnapshotForCMDP chiefSnapForCMDP = new ChiefSnapshotForCMDP(vol.isOsVolMap());
                            BrowserTreeNode chiefSnapNode = new BrowserTreeNode(chiefSnapForCMDP, false);
                            chiefSnapForCMDP.setTreeNode(chiefSnapNode);
                            chiefSnapForCMDP.setFatherNode(volNode);
                            volNode.add(chiefSnapNode);
                        } else {
                            ChiefSnapshot chiefSnap = new ChiefSnapshot();
                            BrowserTreeNode chiefSnapNode = new BrowserTreeNode(chiefSnap, false);
                            chiefSnap.setTreeNode(chiefSnapNode);
                            chiefSnap.setFatherNode(volNode);
                            volNode.add(chiefSnapNode);
                        }

                        // 准备mj list node
                        ChiefMirrorJobList chiefMjList = new ChiefMirrorJobList();
                        BrowserTreeNode chiefMjNode = new BrowserTreeNode(chiefMjList, false);
                        chiefMjList.setTreeNode(chiefMjNode);
                        chiefMjList.setFatherNode(volNode);
                        //volNode.add( chiefMjNode );

                        // unlimited incremental mirror vol list
                        ChiefLocalUnLimitedIncMirrorVolList chiefUIMVolList = new ChiefLocalUnLimitedIncMirrorVolList();
                        BrowserTreeNode chiefUIMVolNode = new BrowserTreeNode(chiefUIMVolList, false);
                        chiefUIMVolList.setTreeNode(chiefUIMVolNode);
                        chiefUIMVolList.setFatherNode(volNode);
                        //volNode.add( chiefUIMVolNode );

                        // clone disk list
                        ChiefCloneDiskList chiefCloneDiskList = new ChiefCloneDiskList();
                        BrowserTreeNode chiefCloneDiskNode = new BrowserTreeNode(chiefCloneDiskList, false);
                        chiefCloneDiskList.setTreeNode(chiefCloneDiskNode);
                        chiefCloneDiskList.setFatherNode(volNode);
                        //volNode.add( chiefCloneDiskNode );

                        chiefHVolNode.add(volNode);
                    }
                } else { // non-windows 主机的lv list
                    list = view.initor.mdb.getRealLVListOnClntID(one.getID());
                    size1 = list.size();
                    for (j = 0; j < size1; j++) {
                        vol = (VolumeMap) list.elementAt(j);
                        tgt = view.initor.mdb.getTgtOnVGname(vol.getVolDesc(), one.getID());
                        if (tgt != null) {
                            targetID = tgt.getVolTargetID();
                            maxSnap = tgt.getMaxSnapNum();
                        } else {
                            targetID = vol.getVolTargetID();
                            maxSnap = vol.getMaxSnapNum();
                        }
                        lv = new LogicalVol(
                                vol.getVolName(),
                                vol.getVolClntID(),
                                vol.getVolDiskLabel(),
                                targetID,
                                maxSnap,
                                vol.getVolDesc(),
                                vol.getVol_rootid());
                        lv.setType(ResourceCenter.TYPE_LV_INDEX);

                        BrowserTreeNode lvNode = new BrowserTreeNode(lv, false);
                        lv.setTreeNode(lvNode);
                        lv.setFatherNode(chiefHVolNode);

                        // 准备lunmap list node
                        ChiefLunMap chiefLm = new ChiefLunMap();
                        BrowserTreeNode chiefLmNode = new BrowserTreeNode(chiefLm, true);
                        chiefLm.setTreeNode(chiefLmNode);
                        chiefLm.setFatherNode(lvNode);
                        lvNode.add(chiefLmNode);

                        // 准备snapshot list node
                        ChiefSnapshot chiefSnap = new ChiefSnapshot();
                        BrowserTreeNode chiefSnapNode = new BrowserTreeNode(chiefSnap, false);
                        chiefSnap.setTreeNode(chiefSnapNode);
                        chiefSnap.setFatherNode(lvNode);
                        lvNode.add(chiefSnapNode);

                        // 准备mj list node
                        ChiefMirrorJobList chiefMjList = new ChiefMirrorJobList();
                        BrowserTreeNode chiefMjNode = new BrowserTreeNode(chiefMjList, false);
                        chiefMjList.setTreeNode(chiefMjNode);
                        chiefMjList.setFatherNode(lvNode);
                        //lvNode.add( chiefMjNode );

                        // unlimited incremental mirror vol list
                        ChiefLocalUnLimitedIncMirrorVolList chiefUIMVolList = new ChiefLocalUnLimitedIncMirrorVolList();
                        BrowserTreeNode chiefUIMVolNode = new BrowserTreeNode(chiefUIMVolList, false);
                        chiefUIMVolList.setTreeNode(chiefUIMVolNode);
                        chiefUIMVolList.setFatherNode(lvNode);
                        //lvNode.add( chiefUIMVolNode );

                        // clone disk list
                        ChiefCloneDiskList chiefCloneDiskList = new ChiefCloneDiskList();
                        BrowserTreeNode chiefCloneDiskNode = new BrowserTreeNode(chiefCloneDiskList, false);
                        chiefCloneDiskList.setTreeNode(chiefCloneDiskNode);
                        chiefCloneDiskList.setFatherNode(lvNode);
                        //lvNode.add( chiefCloneDiskNode );

                        chiefHVolNode.add(lvNode);
                    }
                }
            
        }
    }
/*
    private void initPool(BrowserTreeNode chiefPoolNode) {
        int i, size;
        Pool one;

        ArrayList poolList = view.initor.mdb.getPoolList();
        size = poolList.size();
        for (i = 0; i < size; i++) {
            one = (Pool) poolList.get(i);
            BrowserTreeNode poolNode = new BrowserTreeNode(one, false);
            one.setTreeNode(poolNode);
            one.setFatherNode(chiefPoolNode);
            chiefPoolNode.add(poolNode);
        }
    }
*/
    public void initAppWithGraphy() {
        // 图形化的显示初始化过程
        status = "";
        cnt = 0;
        totalStep = 20;
        InitProgramDialog initDiag = new InitProgramDialog(
                view,
                SanBootView.res.getString("InitProgramDialog.title"),
                SanBootView.res.getString("InitProgramDialog.label.tip"));
        Thread initThread = new Thread(new LaunchSomething(initDiag, this));
        initThread.start();
        int width = 300 + ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 120 + ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        initDiag.setSize(width, height);
        initDiag.setLocation(view.getCenterPoint(width, height));
        initDiag.setVisible(true);
    }

    public boolean realLogin() {
        this.setResetNetwork(true);
        return realLogin(view.initor.serverIp, view.initor.port, view.initor.user, view.initor.passwd, 0);
    }

    public boolean realLogin(String ip, int port, String user, String passwd, int mode) {
        boolean ret;

        Socket _socket = generateSocket(ip, port, mode);
        if (_socket == null) {
            return false;
        }

        if (mode == 0) {
            socket = _socket;
            // set socket for all cmd
            mdb.setCurSocket();
            ret = mdb.login(user, passwd);
            if (!ret) {
                errormsg = SanBootView.res.getString("common.error.loginfail") + " : "
                        + mdb.getErrorMessage();
            }
        } else {
            dhcpSocket = _socket;
            dhcpdb.setSocket(dhcpSocket);
            ret = dhcpdb.login(user, passwd);
            if (!ret) {
                errormsg = SanBootView.res.getString("common.error.loginfail1") + " : "
                        + dhcpdb.getErrorMessage();
            } else {
                //  view.initor.dhcpIp = ip;
                //  view.initor.dhcp_port = port;
                //  view.initor.dhcp_acct = user;
                //  view.initor.dhcp_passwd = passwd;
            }
        }

        return ret;
    }

    public void dealLoginFailure() {
        JOptionPane.showMessageDialog(
                view,
                SanBootView.res.getString("common.error.loginfail"));

        setLoginedFlag(false);
        setInitedFlag(false);
    }

    private Socket generateSocket(String serverIp, int port, int mode) {
        try {
            Socket aSocket = new Socket(serverIp, port);
            return aSocket;
        } catch (UnknownHostException ex) {
            if (mode == 0) {
                this.errormsg = SanBootView.res.getString("common.error.loginfail") + " : "
                        + SanBootView.res.getString("common.error.unknownHost");
            } else {
                this.errormsg = SanBootView.res.getString("common.error.loginfail1") + " : "
                        + SanBootView.res.getString("common.error.unknownHost");
            }
            return null;
        } catch (IOException ex) {
            if (mode == 0) {
                this.errormsg = SanBootView.res.getString("common.error.loginfail") + " : "
                        + SanBootView.res.getString("common.error.io");
            } else {
                this.errormsg = SanBootView.res.getString("common.error.loginfail1") + " : "
                        + SanBootView.res.getString("common.error.io");
            }
            return null;
        }
    }

    public void destroySocket() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        socket = null;
    }

    public String getLoadingStatus() {
        return status;
    }
    int totalStep = 20; // 缺省

    public int getLoadingProcessVal() {
        return (cnt * 100) / totalStep;
    }

    public String getInitErrMsg() {
        return errormsg;
    }

    public static String getUserHomeDir() {
        Properties properties = System.getProperties();
        return properties.getProperty("user.home");
    }

    public static String getUserWorkDir() {
        Properties properties = System.getProperties();
        return properties.getProperty("user.dir");
    }

    public void getConf(){
            FileReader fin1 = null;
            try{
            fin1 = new FileReader(getUserWorkDir() + File.separatorChar + InitApp.CONF_PATH);
            BufferedReader in = new BufferedReader(fin1);
            StringBuffer buf = realGetConf(in);
            adminOpt.parserConf(buf);
            in.close();
            fin1.close();
        } catch (IOException ex) {
            Logger.getLogger(InitApp.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(InitApp.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(InitApp.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                if( fin1 !=null ){
                    fin1.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(InitApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void clearCurUWS() {
        this.lastUWS = uws;
        uws = null;
        serverIp = "";
        port = ResourceCenter.C_S_PORT;
        ResourceCenter.MAX_SNAP_NUM = 0;
        ResourceCenter.MAX_SNAP_CMDP_NUM = 0;
        user = "";
        passwd = "";
        dhcpIp = "";
        dhcp_port = ResourceCenter.C_S_PORT;
        dhcp_passwd = "";
        view.audit.clearUserObj();
    }

    public void addUWSConf() {
        if (this.uws == null) {
            // 这种情况目前不会发生
            GUIAdminOptUWS newUWS = new GUIAdminOptUWS();
            newUWS.setServerIp(this.serverIp);
            newUWS.setTxIP(this.serverIp);
            newUWS.setPort(this.port + "");
            newUWS.setUser(this.user);
            newUWS.setDesc(this.serverIp);
            this.uws = newUWS;
            this.adminOpt.addUWS(newUWS);
        } else {
            uws.setServerIp(this.serverIp);
            uws.setPort(this.port + "");
            uws.setUser(this.user);
            uws.setDesc(this.serverIp);
            GUIAdminOptUWS oldUWS = this.adminOpt.getUWS(uws.getServerIp());
            if (oldUWS != null) {
                this.adminOpt.removeUWS(oldUWS);
            }
            this.adminOpt.addUWS(uws);
        }
    }

    public void saveConf() {
        try {
            String ipaddrPath = getUserWorkDir() + File.separator + InitApp.CONF_PATH;
            adminOpt.outputConf(ipaddrPath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public StringBuffer realGetConf(BufferedReader in) {
        String s1;
        StringBuffer buf = new StringBuffer();
        try {
            while ((s1 = in.readLine()) != null) {
                if (s1.length() == 0) {
                    continue;
                }
                s1 = s1.trim();
                buf.append(s1 + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return buf;
    }

    public boolean isCrtVG() {
        return isCrtedVg;
    }

    public String getSrvIP() {
        return serverIp;
    }

    public String getAdminIP() {
        return this.serverIp;
    }

    public String getTxIP() {
        return txIp;
    }
}
