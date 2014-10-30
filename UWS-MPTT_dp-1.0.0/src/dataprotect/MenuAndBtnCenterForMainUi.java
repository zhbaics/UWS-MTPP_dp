package dataprotect;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.util.*;
import java.util.regex.*;
import java.io.File;

import mylib.UI.*;
import dataprotect.ui.*;
import dataprotect.data.*;
import dataprotect.datadup.cmd.RunBackup;
import dataprotect.datadup.data.*;
import dataprotect.datadup.ui.*;
import dataprotect.datadup.ui.viewobj.*;
import dataprotect.datadup.ui.ProcessEvent.*;
import dataprotect.remotemirror.BasicCrtMjAction;
import dataprotect.remotemirror.ChiefRollbackHost;
import dataprotect.remotemirror.IbootForLinuxSourceAgentWizardDialog;
import dataprotect.remotemirror.RollbackThread;
import dataprotect.remotemirror.RollbackVolThread;
import dataprotect.res.*;
import dataprotect.tool.UWSSockConnectManager;
import dataprotect.snapusage.*;
import dataprotect.unlimitedIncMj.action.*;
import dataprotect.unlimitedIncMj.entity.ChiefCopyJobList;
import dataprotect.unlimitedIncMj.model.table.CloneDisk;
import dataprotect.unlimitedIncMj.service.ProcessEventOnChiefCj;
import dataprotect.unlimitedIncMj.service.StartorStopCjThread;
import dataprotect.unlimitedIncMj.service.StartorStopUnlimitedIncMjThread;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import dataprotect.ui.snaptree.SnapTreeDialog;
import dataprotect.cmdp.action.*;
import dataprotect.cmdp.entity.PPProfile;
import dataprotect.remotemirror.action.CreateMjInBatchAction;
import dataprotect.remotemirror.action.CrtMjSchAction;
import dataprotect.remotemirror.action.DelMjSchAction;
import dataprotect.remotemirror.action.ModMjSchAction;
import dataprotect.remotemirror.action.ModifyMjInBatchAction;
import dataprotect.remotemirror.action.SetupMjSchAction;
import dataprotect.remotemirror.action.StartOrStopMjInBatchAction;
import dataprotect.unlimitedIncMj.entity.ChiefCloneDiskList;
import dataprotect.audit.data.*;
import dataprotect.audit.ui.AuditLogDialog;
import dataprotect.audit.ui.AuditLogGeter;
import dataprotect.audit.ui.UserMgrDialog;
import dataprotect.cmdp.entity.PPProfileItem;
import dataprotect.tool.crypto.StringUtils;

public class MenuAndBtnCenterForMainUi {
    // 控制 menu 和 button的行为时用到的常数
    public static final int  LEVEL_ROOT         = 0;
    public static final int  LEVEL_HOSTLIST     = 1;
    public static final int  LEVEL_BOOT_HOST    = 2;
    public static final int  LEVEL_VOL          = 3;
    public static final int  LEVEL_LV           = 4;
    public static final int  LEVEL_ACTLINK      = 5;
    public static final int  LEVEL_LMLIST       = 6;
    public static final int  LEVEL_LM           = 7;
    public static final int  LEVEL_SNAPLIST     = 8;
    public static final int  LEVEL_SNAP         = 9;
    public static final int  LEVEL_VIEW         = 10;
    public static final int  LEVEL_ORPH_VOLLIST = 11;
    public static final int  LEVEL_ORPH_VOL     = 12;
    public static final int  LEVEL_USER_LIST    = 13;
    public static final int  LEVEL_USER         = 14;
    public static final int  LEVEL_POOLLIST     = 15;
    public static final int  LEVEL_POOL         = 16;
    public static final int  LEVEL_UWSLIST      = 17;
    public static final int  LEVEL_DEST_UWS_SRV = 18;
    public static final int  LEVEL_MJLIST       = 19;
    public static final int  LEVEL_MJ           = 20;
    public static final int  LEVEL_MIRROR_SNAP  = 21;
    public static final int  LEVEL_SRC_UWS_SRV  = 22;
    public static final int  LEVEL_MIRROR_SNAP_VIEW = 23;
    public static final int  LEVEL_NETBOOTED_HOST = 24;  // 网络启动主机
    public static final int  LEVEL_SRC_AGENT    = 25;    // 远程主机（目的端用来表示源端主机的对象）
    public static final int  LEVEL_DEL_SNAP     = 26;
    public static final int  LEVEL_PROFLIST     = 27;
    public static final int  LEVEL_PROF         = 28;
    public static final int  LEVEL_SCHLIST      = 29;
    public static final int  LEVEL_SCH          = 30;
    public static final int  LEVEL_CLONE_DISK   = 31;   // 克隆盘
    public static final int  LEVEL_MIRROR_VOL   = 32;   // 普通镜像卷
    public static final int  LEVEL_UIM_VOL      = 33;   // 无限增量镜像卷
    public static final int  LEVEL_UIM_SNAP_LIST = 34;
    public static final int  LEVEL_UIM_SNAP     = 35;   // 无限增量快照
    public static final int  LEVEL_PPPROF       = 36;   // physical protect profile
    public static final int  LEVEL_UNKNOWN      = 99;
    
    public final static  int FUNC_SEPARATOR    = 0;
    
    // file
    public final static  int FUNC_EXIT        = 1;
    public final static  int FUNC_LOGIN       = 2;
    public final static  int FUNC_LOGOUT      = 3;
    public final static  int FUNC_PROPERTY    = 4;
    
    // win op(logical protect)
    public final static  int FUNC_INIT          = 10;
    public final static  int FUNC_FAILOVER      = 11;
    public final static  int FUNC_FAILBACK      = 12;
    public final static  int FUNC_RESTORE_DISK  = 13;
    public final static  int FUNC_ABOUT         = 16;
    public final static  int FUNC_ROLLBACK      = 17;
    
    // volume
    public final static  int FUNC_CRT_VOL   = 20;
    public final static  int FUNC_DEL_VOL   = 21;
    public final static  int FUNC_ONLINE    = 22;
    public final static  int FUNC_OFFLINE   = 23;
    public final static  int FUNC_ROLLBACK_VOL = 24;
    
    // snapshot 
    public final static  int FUNC_CRT_SNAP   = 30;
    public final static  int FUNC_DEL_SNAP   = 31;
    public final static  int FUNC_MNT_SNAP   = 32;
    public final static  int FUNC_CRT_SYNC_SNAP  = 33;   // for cmdp
    public final static  int FUNC_CRT_ASYNC_SNAP = 34;   // for cmdp
    
    // host lummap
    public final static  int FUNC_LUNMAP    = 40;
    public final static  int FUNC_CANCEL_LM = 41;
    
    // host
    public final static  int FUNC_CRT_HOST  = 50;
    public final static  int FUNC_DEL_HOST  = 51;
    public final static  int FUNC_MOD_HOST  = 52;

    // setup
    public final static  int FUNC_DHCP = 53;
    public final static  int FUNC_MSG_REPORT = 54;
    public final static  int FUNC_ADMINOPT = 55;
    public final static  int FUNC_MOD_PASS = 56;
    
    // dest UWS server
    public final static  int FUNC_CRT_UWS_SRV = 57;
    public final static  int FUNC_DEL_UWS_SRV = 58;
    public final static  int FUNC_MOD_UWS_SRV = 59;
    
    // user
    public final static  int FUNC_CRT_USER  = 60;
    public final static  int FUNC_DEL_USER  = 61;
    public final static  int FUNC_MOD_USER  = 62;
    public final static  int FUNC_MOD_PASSWD  = 63;
    
    // del netboot host
    public final static  int FUNC_DEL_DSTAGNT = 64;
    
    // del srcagent <==> del rollbacked host
    public final static  int FUNC_DEL_SRCAGNT = 65;
    
    // non-win op(logical protect)
    public final static  int FUNC_INIT_NWIN  = 70;
    public final static  int FUNC_REST_DISK_NWIN  = 71;
    public final static  int FUNC_SELECT_BOOT_VER = 72;
    public final static  int FUNC_IBOOT_WIZARD = 73;
    public final static  int FUNC_ROLLBACK_NWIN = 74;
    
    // view
    public final static int FUNC_CRT_VIEW = 80;
    public final static int FUNC_DEL_VIEW = 81;
    public final static int FUNC_MNT_VIEW = 82;
    public final static int FUNC_UMNT_VIEW = 83;
    
    // pool
    public final static int FUNC_CRT_POOL = 90;
    public final static int FUNC_DEL_POOL = 91;   
    
    // mj
    public final static int FUNC_CRT_MJ = 100;
    public final static int FUNC_DEL_MJ = 101;
    public final static int FUNC_MOD_MJ = 102;
    public final static int FUNC_START_MJ = 103;
    public final static int FUNC_STOP_MJ = 104;
    public final static int FUNC_MONITOR_MJ = 105;
    public final static int FUNC_CRT_MJ1 = 106;
    public final static int FUNC_QUICK_START_MJ = 107;
    public final static int FUNC_BATCH_START_STOP_MJ = 108;
    public final static int FUNC_BATCH_CREATE_MJ = 109;
    public final static int FUNC_CRT_MJ_SCH = 142;
    public final static int FUNC_MOD_MJ_SCH = 143;   // 修改镜像时间计划
    public final static int FUNC_DEL_MJ_SCH = 144;   // 删除镜像时间计划
    public final static int FUNC_SETUP_MJ_SCH = 145;
    public final static int FUNC_BATCH_MOD_MJ = 1090;

    // src uws node
    public final static  int FUNC_MOD_SRC_UWS_SRV = 110;

    // copy job (cj)
    public final static int FUNC_CRT_CJ = 111;
    public final static int FUNC_DEL_CJ = 112;
    public final static int FUNC_MOD_CJ = 113;
    public final static int FUNC_START_CJ = 114;
    public final static int FUNC_STOP_CJ = 115;
    public final static int FUNC_MONITOR_CJ = 116;
    public final static int FUNC_QUICK_START_CJ = 117;

    // data dup
    public final static int FUNC_ADD_PROF = 120;
    public final static int FUNC_MOD_PROF = 121;
    public final static int FUNC_DEL_PROF = 122;
    public final static int FUNC_RENAME_PROF = 123;
    public final static int FUNC_RUN_PROF    = 124;
    public final static int FUNC_VERIFY_PROF = 125;
    
    public final static int FUNC_ADD_SCH = 126;
    public final static int FUNC_MOD_SCH = 127;
    public final static int FUNC_DEL_SCH = 128;
    public final static int FUNC_DUP_LOG = 129;
    public final static int FUNC_MONITOR_DD = 130;

    // unlimited incremental snapshot
    public final static int FUNC_CLONE_DISK = 131;
    public final static int FUNC_DEL_UI_MIRROR_VOL = 132;
    public final static int FUNC_DEL_CLONE_DISK = 133;
    public final static int FUNC_QUEYR_UISNAP = 134;
    // public final static int FUNC_DEL_CJ_VOL = 135;  // 删除copy job产生的镜像卷及其克隆盘

    // win op(physical protect)
    public final static  int FUNC_WIN_PHY_INIT          = 135;
    public final static  int FUNC_WIN_PHY_FAILOVER      = 136;
    public final static  int FUNC_WIN_PHY_FAILBACK      = 137;
    public final static  int FUNC_WIN_PHY_RESTORE_DISK  = 138;

    // modify profile( physical protect )
    public final static  int FUNC_PHY_MOD_PROFILE  = 139;

    public final static  int FUNC_PHY_QUERY_INIT_PROGRESS = 140;
    public final static  int FUNC_PHY_QUERY_SYNC_STATE = 141;
    public final static  int FUNC_PHY_QUERY_SYNC_STATE_TABLE = 1410;
    public final static  int FUNC_WIN_PHY_SWITCH_TO_NETDISK = 146;
    public final static  int FUNC_WIN_PHY_DUP_NETDISK = 147;
    public final static  int FUNC_WIN_PHY_SWITCH_TO_LOCALDISK = 148;

    public final static  int FUNC_WIN_SWITCH_TO_NETDISK = 149;
    public final static  int FUNC_WIN_DUP_NETDISK = 150;
    public final static  int FUNC_WIN_SWITCH_TO_LOCALDISK = 151;
    public final static  int FUNC_PHY_START_AUTO_CRT_SNAP = 152;

    // misc.
    public final static int FUNC_CANCEL_JOB = 298;
    public final static int FUNC_SHUTDOWN = 299;
    public final static int FUNC_SNAP_TREE = 300;
    public final static int FUNC_USER_MGR  = 297;
    public final static int FUNC_USER_AUDIT = 296;

    // menu 
    public final static int INDEX_FILE   = 0;
    public final static int INDEX_LOGICAL_PROTECT = 1;
    public final static int INDEX_PHYSICAL_PROTECT = 2;
    public final static int INDEX_DEV    = 3;
    public final static int INDEX_REMOTE_DR = 4;
    public final static int INDEX_SETUP  = 5;
    public final static int INDEX_HELP   = 6;
    
    JMenu[] mainMenu = new JMenu[]{
        new JMenu(ResourceCenter.MENU_FILE),  // file
        new JMenu(ResourceCenter.MENU_LOGICAL_PROTECT),  // logical protect
        new JMenu(ResourceCenter.MENU_PHYSICAL_PROTECT), // physical protect
        new JMenu(ResourceCenter.MENU_DEV),   // device
        new JMenu(ResourceCenter.MENU_RDR),   // remote DR
        new JMenu(ResourceCenter.MENU_SETUP), // setup
        new JMenu(ResourceCenter.MENU_HELP)   // help
    };
    
    // file menu
    CustomMenuItem jMenuExit;
    CustomMenuItem jMenuLogin;
    CustomMenuItem jMenuProperty;
    BrowserToolMenuItem jMenuLogout;
    
    // win op menu(logical protect)
    JMenu jMenuWinPro = new JMenu();
    CustomMenuItem jMenuInit;
    JMenu jMenuDR = new JMenu();
    CustomMenuItem  jMenuFailover;    // data disater from local disk to iscsi
    CustomMenuItem  jMenuSwitchToNetDisk; //switch disk from local disk to iscsi
    CustomMenuItem  jMenuFailback;    // data disater from iscsi to local disk
    CustomMenuItem  jMenuSwitchToLocalDisk; //switch disk from iscsi to local disk
    CustomMenuItem  jMenuRestoreData; // migrate data to orginal disk
    CustomMenuItem  jMenuDuplicateDataFromIscsiToLocaldisk; // duplicate data from iscsi to local disk
    
    // non-win op menu(logical protect)
    JMenu jMenuNonWinPro = new JMenu();
    CustomMenuItem  jMenuInitNWinHost;
    CustomMenuItem  jMenuSelBootVer;
    CustomMenuItem  jMenuRestDataForNWinHost;
    CustomMenuItem  jMenuIBootForLinux;

    // win op menu(physical protect)
    CustomMenuItem jMenuWinPhyInit;
    JMenu jMenuWinPhyDR = new JMenu();
    CustomMenuItem  jMenuWinPhyFailover;    // data disater from local disk to iscsi
    CustomMenuItem  jMenuWinPhySwitchToNetDisk;  // switch disk from local disk to iscsi
    CustomMenuItem  jMenuWinPhyFailback;    // data disater from iscsi to local disk
    CustomMenuItem  jMenuWinPhySwitchToLocalDisk; // switch disk from iscsi to local disk
    CustomMenuItem  jMenuWinPhyRestoreData; // migrate data to orginal disk
    CustomMenuItem  jMenuWinPhyDuplicateDataFromIscsiToLocaldisk; // duplicate data from iscsi to local disk
    CustomMenuItem  jMenuWinPhyQuerySyncState; // query volume's sync state
    CustomMenuItem  jMenuWinPhyQuerySyncStateForTable; // query volume's sync state
    CustomMenuItem  jMenuWinPhyQueryInitState; // query initializing volume's progress
    CustomMenuItem  jMenuWinPhyStartAutoCrtSnap; // start auto-crt-snap process( start_mirror )

    // device menu
    JMenu jMenuVol = new JMenu();  // volume submenu
    CustomMenuItem  jMenuCrtVol;
    CustomMenuItem  jMenuDelVol;
    CustomMenuItem  jMenuOnlineVol;
    CustomMenuItem  jMenuOfflineVol;
    
    JMenu jMenuView = new JMenu(); // view submenu
    CustomMenuItem jMenuCrtView;
    CustomMenuItem jMenuDelView;
    CustomMenuItem jMenuMntView;
    CustomMenuItem jMenuUMntView;
    
    JMenu jMenuSnap = new JMenu(); // snapshot submenu
    CustomMenuItem  jMenuCrtSnap;
    CustomMenuItem  jMenuCrtSyncSnap;
    CustomMenuItem  jMenuCrtAsyncSnap;
    CustomMenuItem  jMenuDelSnap;
    CustomMenuItem  jMenuMntSnap;
    
    JMenu jMenuLunMap = new JMenu(); // lunmap submenu
    CustomMenuItem jMenuCrtLm;
    CustomMenuItem jMenuDelLm;
    
    JMenu jMenuPool = new JMenu(); // pool submenu
    CustomMenuItem jMenuCrtPool;
    CustomMenuItem jMenuDelPool;
    
    // setup Menu
    JMenu jMenuHost = new JMenu(); // host submenu
    CustomMenuItem jMenuCrtHost;
    CustomMenuItem jMenuDelHost;
    CustomMenuItem jMenuModHost;

    CustomMenuItem jMenuModSrcUWSrv; // 修改源端UWS server
    BrowserToolMenuItem jMenuDhcpSet;
    BrowserToolMenuItem jMenuMsgReport;
    BrowserToolMenuItem jMenuModPass;
    BrowserToolMenuItem jMenuAdminOpt;
    CustomMenuItem jMenuDelDstAgnt;
    
    JMenu jMenuUser = new JMenu(); // user submenu
    BrowserToolMenuItem jMenuCrtUser;
    BrowserToolMenuItem jMenuDelUser;
    BrowserToolMenuItem jMenuModUser;
    BrowserToolMenuItem jMenuModPasswd;

    BrowserToolMenuItem jMenuUserMgr;
    BrowserToolMenuItem jMenuAudit;
    BrowserToolMenuItem shutdown; // shutdown uws server
    
    // data dup
    JMenu jMenuProf = new JMenu(); // profile
    CustomMenuItem addProfile;
    CustomMenuItem modProfile;
    CustomMenuItem modPhyProfile; // modify profile for physical protect
    CustomMenuItem delProfile;
    CustomMenuItem runProfile;
    CustomMenuItem verifyProfile;
    CustomMenuItem renameProfile;
    
    JMenu jMenuSch = new JMenu();  // schedule
    CustomMenuItem addSch;
    CustomMenuItem modSch;
    CustomMenuItem delSch;
    BrowserToolMenuItem dupLog;   // browse log 
    BrowserToolMenuItem monitorDup; // monitor task

    CustomMenuItem jMenuSnapTree; // snapshot tree operation

    // about menu
    BrowserToolMenuItem jMenuAboutBackup;

    ////////////////////////////////////////////
    //
    //     Remote Data Disaster Management
    //
    ////////////////////////////////////////////
    // remote mirror server
    CustomMenuItem jMenuCrtUWS;
    CustomMenuItem jMenuDelUWS;
    CustomMenuItem jMenuModUWS;

    // mirror job
    CustomMenuItem jMenuCrtMj;
    CustomMenuItem jMenuCrtMj1;
    CustomMenuItem jMenuDelMj;
    CustomMenuItem jMenuModMj;
    CustomMenuItem jMenuStartMj;
    CustomMenuItem jMenuStopMj;
    CustomMenuItem jMenuMonMj;
    CustomMenuItem jMenuQStartMj;
    CustomMenuItem jMenuBatchedStartStopMj;
    CustomMenuItem jMenuBatchedCrtMj;
    CustomMenuItem jMenuBatchedModMj;
    CustomMenuItem jMenuSetupMjSch;

    // copy job
    CustomMenuItem jMenuCrtCj;
    CustomMenuItem jMenuDelCj;
    CustomMenuItem jMenuModCj;
    CustomMenuItem jMenuStartCj;
    CustomMenuItem jMenuQStartCj;
    CustomMenuItem jMenuStopCj;
    CustomMenuItem jMenuMonCj;
    
    // roll back
    CustomMenuItem  jMenuRollbackVol;
    CustomMenuItem  jMenuRollbackForNWin;   // rollback from linux SourceAgent to BootHost
    CustomMenuItem  jMenuRollback;    // rollback from windows SourceAgent to BootHost
    CustomMenuItem  jMenuDelSrcAgnt;

    // unlimited incremental snapshot
    CustomMenuItem jMenuCloneDisk;
    CustomMenuItem jMenuDelUiMirrorVol;
    CustomMenuItem jMenuDelCloneDisk;
    CustomMenuItem jMenuQueryUISnap; // QueryUISnapAction
    
    // mirror job scheduler
    CustomMenuItem jMenuCrtMjSch;
    CustomMenuItem jMenuModMjSch;
    CustomMenuItem jMenuDelMjSch;
    
    BrowserToolButton btnLogin;
    BrowserToolButton btnLogout;
    BrowserToolButton btnInit;
    BrowserToolButton btnFailover;
    BrowserToolButton btnRstOriData;
    BrowserToolButton btnUWSRpt;
    BrowserToolButton btnMonitor;
    BrowserToolButton btnExit;
    BrowserToolButton btnDupLog;
    BrowserToolButton btnMon;
    
    SanBootView view;
    BrowserTree tree;  
    Map<Integer,Object> funcMap = new HashMap<Integer,Object>(); // 右键功能的HashMap
    
    public MenuAndBtnCenterForMainUi( SanBootView _view ) {
        view = _view;
        tree = view.getTree();
    }
    
    boolean isFirst = true;
    public void languageSet(){
        view.setTitle( SanBootView.res.getString( "View.frameTitle" ) );

        mainMenu[INDEX_FILE].setText(SanBootView.res.getString("View.Menu.file"));
        jMenuExit.setText(SanBootView.res.getString("View.MenuItem.exit"));
        jMenuLogin.setText(SanBootView.res.getString("View.MenuItem.connect"));
        jMenuLogout.setText(SanBootView.res.getString("View.MenuItem.disConnect"));
        jMenuProperty.setText(SanBootView.res.getString("View.MenuItem.property"));
        mainMenu[INDEX_LOGICAL_PROTECT].setText(SanBootView.res.getString("View.Menu.logicalPro"));
        mainMenu[INDEX_PHYSICAL_PROTECT].setText(SanBootView.res.getString("View.Menu.physicalPro"));
        jMenuWinPro.setText(SanBootView.res.getString("View.Menu.winop"));
        jMenuNonWinPro.setText(SanBootView.res.getString("View.Menu.nwinop"));
        jMenuInit.setText( SanBootView.res.getString("View.MenuItem.init") );
        jMenuWinPhyInit.setText( SanBootView.res.getString("View.MenuItem.init") );
        jMenuInit.setPopMenuText( SanBootView.res.getString("View.MenuItem.init1") );
        jMenuWinPhyInit.setPopMenuText( SanBootView.res.getString("View.MenuItem.init3") );
        jMenuDR.setText( SanBootView.res.getString("View.MenuItem.dataDisater") );
        jMenuWinPhyDR.setText( SanBootView.res.getString("View.MenuItem.dataDisater") );
        jMenuFailover.setText( SanBootView.res.getString("View.MenuItem.failover") );
        jMenuSwitchToNetDisk.setText( SanBootView.res.getString("View.MenuItem.switchDisk"));
        jMenuWinPhyFailover.setText( SanBootView.res.getString("View.MenuItem.failover") );
        jMenuWinPhySwitchToNetDisk.setText( SanBootView.res.getString("View.MenuItem.switchDisk"));
        jMenuWinPhySwitchToLocalDisk.setText( SanBootView.res.getString("View.MenuItem.switchDisk1"));
        jMenuSwitchToLocalDisk.setText( SanBootView.res.getString("View.MenuItem.switchDisk1"));
        jMenuWinPhyDuplicateDataFromIscsiToLocaldisk.setText(SanBootView.res.getString("View.MenuItem.cpnetdisk"));
        jMenuDuplicateDataFromIscsiToLocaldisk.setText( SanBootView.res.getString("View.MenuItem.cpnetdisk"));
        jMenuFailback.setText( SanBootView.res.getString("View.MenuItem.failback") );
        jMenuWinPhyFailback.setText( SanBootView.res.getString("View.MenuItem.failback") );
        jMenuRestoreData.setText( SanBootView.res.getString("View.MenuItem.rstOriData"));
        jMenuWinPhyRestoreData.setText( SanBootView.res.getString("View.MenuItem.rstOriData"));
        jMenuWinPhyQuerySyncState.setText( SanBootView.res.getString("View.MenuItem.querySyncState") );
        jMenuWinPhyQuerySyncStateForTable.setText( SanBootView.res.getString("View.MenuItem.querySyncState") );
        jMenuWinPhyStartAutoCrtSnap.setText( SanBootView.res.getString("View.MenuItem.startAutoCrtSnap") );
        jMenuWinPhyQueryInitState.setText( SanBootView.res.getString("View.MenuItem.queryInitState") );
        jMenuRollback.setText( SanBootView.res.getString("View.MenuItem.rollback") );
        jMenuInitNWinHost.setText( SanBootView.res.getString("View.MenuItem.init") );
        jMenuInitNWinHost.setPopMenuText( SanBootView.res.getString("View.MenuItem.init2") );
        jMenuSelBootVer.setText( SanBootView.res.getString("View.MenuItem.selBootVer") );
        jMenuIBootForLinux.setText( SanBootView.res.getString("View.MenuItem.ibootForLinux") );
        jMenuRestDataForNWinHost.setText( SanBootView.res.getString("View.MenuItem.rstOriData") );
        jMenuRollbackForNWin.setText( SanBootView.res.getString("View.MenuItem.rollbacknwin") );
        jMenuProf.setText(SanBootView.res.getString("View.Menu.prof"));
        jMenuProf.setIcon( ResourceCenter.ICON_PROFILE );
        addProfile.setText(SanBootView.res.getString("View.MenuItem.addProf"));
        modProfile.setText(SanBootView.res.getString("View.MenuItem.modProf"));
        modPhyProfile.setText(SanBootView.res.getString("View.MenuItem.modProf"));
        delProfile.setText(SanBootView.res.getString("View.MenuItem.delProf"));
        runProfile.setText( SanBootView.res.getString("View.MenuItem.runProf"));
        verifyProfile.setText( SanBootView.res.getString("View.MenuItem.verifyProf"));
        renameProfile.setText( SanBootView.res.getString("View.MenuItem.renameProf"));
        jMenuSch.setText(SanBootView.res.getString("View.Menu.sch"));
        jMenuSch.setIcon( ResourceCenter.SMALL_SCH );
        addSch.setText(SanBootView.res.getString("View.MenuItem.addSch"));
        modSch.setText(SanBootView.res.getString("View.MenuItem.modSch"));
        delSch.setText(SanBootView.res.getString("View.MenuItem.delSch"));
        dupLog.setText(SanBootView.res.getString("View.MenuItem.duplog"));
        monitorDup.setText(SanBootView.res.getString("View.MenuItem.mon"));
        jMenuSnapTree.setText(SanBootView.res.getString("View.MenuItem.snaptree"));
        mainMenu[INDEX_DEV].setText( SanBootView.res.getString("View.Menu.device") );
        jMenuVol.setText( SanBootView.res.getString("View.Menu.vol"));
        jMenuCrtVol.setText( SanBootView.res.getString("View.MenuItem.crtVol") );
        jMenuDelVol.setText( SanBootView.res.getString("View.MenuItem.delVol") );
        jMenuOnlineVol.setText( SanBootView.res.getString("View.MenuItem.onlineDev"));
        jMenuOfflineVol.setText( SanBootView.res.getString("View.MenuItem.offlineDev"));
        jMenuRollbackVol.setText( SanBootView.res.getString("View.MenuItem.rollbackvol") );
        jMenuView.setText( SanBootView.res.getString("View.Menu.view"));
        jMenuCrtView.setText( SanBootView.res.getString("View.MenuItem.crtView") );
        jMenuDelView.setText( SanBootView.res.getString("View.MenuItem.delView") );
        jMenuMntView.setText( SanBootView.res.getString("View.MenuItem.mntView") );
        jMenuUMntView.setText( SanBootView.res.getString("View.MenuItem.umntView") );  
        jMenuSnap.setText( SanBootView.res.getString("View.Menu.snap") );
        jMenuCrtSnap.setText( SanBootView.res.getString("View.MenuItem.crtSnap") );
        jMenuCrtSyncSnap.setText( SanBootView.res.getString("View.MenuItem.crtSnap1"));
        jMenuCrtAsyncSnap.setText( SanBootView.res.getString("View.MenuItem.crtSnap2"));
        jMenuDelSnap.setText( SanBootView.res.getString("View.MenuItem.delSnap") );
        jMenuMntSnap.setText( SanBootView.res.getString("View.MenuItem.mntSnap"));
        jMenuLunMap.setText( SanBootView.res.getString("View.Menu.lm") );
        jMenuCrtLm.setText( SanBootView.res.getString("View.MenuItem.crtLm") );
        jMenuDelLm.setText( SanBootView.res.getString("View.MenuItem.delLm") );
        jMenuCrtMj.setText( SanBootView.res.getString("View.MenuItem.crtMj"));
        jMenuCrtMj1.setText( SanBootView.res.getString("View.MenuItem.crtIncMj"));
        jMenuDelMj.setText( SanBootView.res.getString("View.MenuItem.delMj"));
        jMenuModMj.setText( SanBootView.res.getString("View.MenuItem.modMj"));
        jMenuStartMj.setText( SanBootView.res.getString("View.MenuItem.startMj"));
        jMenuQStartMj.setText( SanBootView.res.getString("View.MenuItem.qstartMj"));
        jMenuBatchedStartStopMj.setText( SanBootView.res.getString("View.MenuItem.startStopMjInBatch") );
        jMenuBatchedCrtMj.setText( SanBootView.res.getString("View.MenuItem.crtMjInBatch") );
        jMenuBatchedModMj.setText( SanBootView.res.getString("View.MenuItem.modMjInBatch") );
        jMenuSetupMjSch.setText( SanBootView.res.getString("View.MenuItem.setupMjSch") );
        jMenuStopMj.setText( SanBootView.res.getString("View.MenuItem.stopMj"));
        jMenuMonMj.setText( SanBootView.res.getString("View.MenuItem.monMj"));
        jMenuCrtCj.setText( SanBootView.res.getString("View.MenuItem.crtCj"));
        jMenuDelCj.setText( SanBootView.res.getString("View.MenuItem.delCj"));
        jMenuModCj.setText( SanBootView.res.getString("View.MenuItem.modCj"));
        jMenuStartCj.setText( SanBootView.res.getString("View.MenuItem.startCj"));
        jMenuQStartCj.setText( SanBootView.res.getString("View.MenuItem.qstartCj"));
        jMenuStopCj.setText( SanBootView.res.getString("View.MenuItem.stopCj"));
        jMenuMonCj.setText( SanBootView.res.getString("View.MenuItem.monCj"));
        jMenuPool.setText( SanBootView.res.getString("View.MenuItem.pool"));
        jMenuCrtPool.setText( SanBootView.res.getString("View.MenuItem.crtPool"));
        jMenuDelPool.setText( SanBootView.res.getString("View.MenuItem.delPool"));
        jMenuCrtMjSch.setText( SanBootView.res.getString("View.MenuItem.crtMjSch"));
        jMenuModMjSch.setText( SanBootView.res.getString("View.MenuItem.modMjSch"));
        jMenuDelMjSch.setText( SanBootView.res.getString("View.MenuItem.delMjSch"));
        mainMenu[INDEX_REMOTE_DR].setText( SanBootView.res.getString("View.Menu.rdr") );
        mainMenu[INDEX_SETUP].setText(SanBootView.res.getString("View.Menu.setup"));
        jMenuHost.setText( SanBootView.res.getString("View.Menu.host") );
        jMenuHost.setIcon( ResourceCenter.SMALL_HOST );
        jMenuCrtHost.setText( SanBootView.res.getString("View.MenuItem.crtHost") );
        jMenuDelHost.setText( SanBootView.res.getString("View.MenuItem.delHost") );
        jMenuModHost.setText( SanBootView.res.getString("View.MenuItem.modHost"));
        jMenuCrtUWS.setText( SanBootView.res.getString("View.MenuItem.crtSWU") );
        jMenuDelUWS.setText( SanBootView.res.getString("View.MenuItem.delSWU") );
        jMenuModUWS.setText( SanBootView.res.getString("View.MenuItem.modSWU") );
        jMenuModSrcUWSrv.setText( SanBootView.res.getString("View.MenuItem.modSrcSWU"));
        jMenuDhcpSet.setText( SanBootView.res.getString("View.MenuItem.dhcp") );
        jMenuMsgReport.setText( SanBootView.res.getString("View.MenuItem.msgReport"));
        jMenuModPass.setText(SanBootView.res.getString("View.MenuItem.modPass"));
        jMenuAdminOpt.setText( SanBootView.res.getString("View.MenuItem.adminOpt"));
        jMenuDelDstAgnt.setText( SanBootView.res.getString("View.MenuItem.delDstAgnt"));
        jMenuDelDstAgnt.setPopMenuText( SanBootView.res.getString("View.MenuItem.delDstAgnt") );
        jMenuDelSrcAgnt.setText( SanBootView.res.getString("View.MenuItem.delRHost") );
        jMenuUser.setText( SanBootView.res.getString("View.Menu.user"));
        jMenuCrtUser.setText( SanBootView.res.getString("View.MenuItem.crtUser"));
        jMenuDelUser.setText( SanBootView.res.getString("View.MenuItem.delUser"));
        jMenuModUser.setText( SanBootView.res.getString("View.MenuItem.modUser"));
        jMenuModPasswd.setText( SanBootView.res.getString("View.MenuItem.modPasswd"));
	
        jMenuUserMgr.setText( SanBootView.res.getString("View.MenuItem.userMgr"));
        jMenuAudit.setText( SanBootView.res.getString("View.MenuItem.audit"));
        shutdown.setText( SanBootView.res.getString("RightCustomDialog.checkbox.shutdown") );

        mainMenu[INDEX_HELP].setText(SanBootView.res.getString("View.Menu.help"));
        jMenuAboutBackup.setText(SanBootView.res.getString("View.MenuItem.about"));

        jMenuCloneDisk.setText( SanBootView.res.getString("View.MenuItem.clonedisk"));
        jMenuDelUiMirrorVol.setText(SanBootView.res.getString("View.MenuItem.deluimirvol"));
        jMenuDelCloneDisk.setText(SanBootView.res.getString("View.MenuItem.delCloneDisk"));
        jMenuQueryUISnap.setText( SanBootView.res.getString("View.MenuItem.queryUISnap"));

        btnLogin.setToolTipText(SanBootView.res.getString("View.tooltip.connect"));
        btnLogout.setToolTipText(SanBootView.res.getString("View.tooltip.disconnect"));
        btnExit.setToolTipText(SanBootView.res.getString("View.tooltip.exit"));
        btnInit.setToolTipText( SanBootView.res.getString("View.tooltip.initHost") );
        btnFailover.setToolTipText( SanBootView.res.getString("View.tooltip.failover"));
        btnRstOriData.setToolTipText( SanBootView.res.getString("View.tooltip.rstOriData") );
        btnUWSRpt.setToolTipText( SanBootView.res.getString("View.tooltip.productReport") );
        btnDupLog.setToolTipText( SanBootView.res.getString("View.tooltip.duplog"));
        btnMon.setToolTipText( SanBootView.res.getString("View.tooltip.mon"));
        
        if( isFirst ){
            view.setTreeRootNode( tree ); // initialize root node for tree
            isFirst = false;
        }
    }

    public void init(){
        generateMenuAndBtn();
        addMenuToView();
        addButtonToView();
    }
    
    private void generateMenuAndBtn(){
        LoginAction loginAction = new LoginAction();
        loginAction.setView( view );
        jMenuLogin = new CustomMenuItem(
            KeyStroke.getKeyStroke(
                KeyEvent.VK_C,Event.ALT_MASK
            ),
            loginAction
        );
        btnLogin = new BrowserToolButton(loginAction);
        
        LogoutAction logoutAction = new LogoutAction();
        logoutAction.setView( view );
        jMenuLogout = new BrowserToolMenuItem( logoutAction );
        btnLogout = new BrowserToolButton( logoutAction );
        
        PropertyAction propertyAction = new PropertyAction();
        propertyAction.setView( view );
        jMenuProperty = new CustomMenuItem( propertyAction );
        
        ExitAction exitAction = new ExitAction();
        exitAction.setView( view );
        jMenuExit = new CustomMenuItem(
            KeyStroke.getKeyStroke(
                KeyEvent.VK_X,Event.ALT_MASK
            ),
            exitAction
        );
        btnExit = new BrowserToolButton(exitAction);
                
        InitHostAction initAct = new InitHostAction();
        initAct.setView( view );
        jMenuInit = new CustomMenuItem( initAct );
        btnInit =  new BrowserToolButton( initAct );

        InitHostByPhyProtectAction phyInitAct = new InitHostByPhyProtectAction();
        phyInitAct.setView( view );
        this.jMenuWinPhyInit = new CustomMenuItem( phyInitAct );

        InitNWinHostAction initNWinAct = new InitNWinHostAction();
        initNWinAct.setView( view );
        jMenuInitNWinHost = new CustomMenuItem( initNWinAct );
        
        SelectBootVerAction selAct = new SelectBootVerAction();
        selAct.setView( view );
        jMenuSelBootVer = new CustomMenuItem( selAct );
        
        IBootForLinuxAction ibootForLnx = new IBootForLinuxAction();
        ibootForLnx.setView(  view );
        jMenuIBootForLinux = new CustomMenuItem( ibootForLnx );
       
        // data disater from local disk to iscsi
        FailoverAction failoverAction = new FailoverAction();
        failoverAction.setView( view );
        jMenuFailover =  new CustomMenuItem( failoverAction );
        btnFailover = new BrowserToolButton( failoverAction );

        FailoverAction switchDiskAction = new FailoverAction( true );
        switchDiskAction.setView( view );
        this.jMenuSwitchToNetDisk =  new CustomMenuItem( switchDiskAction );

        FailoverByPhyProtectAction phyFailoverAction = new FailoverByPhyProtectAction();
        phyFailoverAction.setView( view );
        this.jMenuWinPhyFailover = new CustomMenuItem( phyFailoverAction );

        FailoverByPhyProtectAction phySwitchDiskAction = new FailoverByPhyProtectAction( true );
        phySwitchDiskAction.setView( view );
        this.jMenuWinPhySwitchToNetDisk = new CustomMenuItem( phySwitchDiskAction );
        
        // data disater from iscsi to local disk
        FailbackAction failbackAction = new FailbackAction();
        failbackAction.setView( view );
        jMenuFailback =  new CustomMenuItem( failbackAction );

        FailbackAction switchToLocalDiskAction = new FailbackAction( true );
        switchToLocalDiskAction.setView( view );
        this.jMenuSwitchToLocalDisk =  new CustomMenuItem( switchToLocalDiskAction );

        FailbackByPhyProtectAction phyFailbackAction = new FailbackByPhyProtectAction();
        phyFailbackAction.setView( view );
        this.jMenuWinPhyFailback =  new CustomMenuItem( phyFailbackAction );

        FailbackByPhyProtectAction phySwitchToLocalDiskAction = new FailbackByPhyProtectAction( true );
        phySwitchToLocalDiskAction.setView( view );
        this.jMenuWinPhySwitchToLocalDisk =  new CustomMenuItem( phySwitchToLocalDiskAction );

        RestoreOriginalData restOriData = new RestoreOriginalData();
        restOriData.setView( view );
        jMenuRestoreData =  new CustomMenuItem( restOriData );
        btnRstOriData = new BrowserToolButton( restOriData );

        RestoreOriginalData dupDataToLocalDisk = new RestoreOriginalData( true );
        dupDataToLocalDisk.setView( view );
        jMenuDuplicateDataFromIscsiToLocaldisk =  new CustomMenuItem( dupDataToLocalDisk );

        RestoreOriginalDataByPhyProtect phyRestOriData = new RestoreOriginalDataByPhyProtect();
        phyRestOriData.setView( view );
        jMenuWinPhyRestoreData =  new CustomMenuItem( phyRestOriData );
        
        RestoreOriginalDataByPhyProtect phyDupDataToLocalDisk = new RestoreOriginalDataByPhyProtect( true );
        phyDupDataToLocalDisk.setView( view );
        jMenuWinPhyDuplicateDataFromIscsiToLocaldisk =  new CustomMenuItem( phyDupDataToLocalDisk );

        QuerySyncStateAction querySyncState = new QuerySyncStateAction();
        querySyncState.setView( view );
        jMenuWinPhyQuerySyncState = new CustomMenuItem( querySyncState );
        
        QuerySyncStateActionForTable querySyncStateTable = new QuerySyncStateActionForTable();
        querySyncStateTable.setView(view);
        jMenuWinPhyQuerySyncStateForTable = new CustomMenuItem( querySyncStateTable );

        StartAutoCrtSnapshotAction winPhyStartAutoCrtSnap = new StartAutoCrtSnapshotAction();
        winPhyStartAutoCrtSnap.setView( view );
        jMenuWinPhyStartAutoCrtSnap = new CustomMenuItem( winPhyStartAutoCrtSnap );

        QueryInitProgressAction queryInitProgress = new QueryInitProgressAction();
        queryInitProgress.setView( view );
        jMenuWinPhyQueryInitState = new CustomMenuItem( queryInitProgress );

        RollbackAction rollback = new RollbackAction();
        rollback.setView( view );
        jMenuRollback =  new CustomMenuItem( rollback );
        
        RestoreOriginalDataForNWin restOriDataForNWin = new RestoreOriginalDataForNWin();
        restOriDataForNWin.setView( view );
        jMenuRestDataForNWinHost =  new CustomMenuItem( restOriDataForNWin );
        
        RollbackForNWinAction rollback1 = new RollbackForNWinAction();
        rollback1.setView( view );
        jMenuRollbackForNWin =  new CustomMenuItem( rollback1 );
        
        CrtVolAction crtVolAction = new CrtVolAction();
        crtVolAction.setView( view );
        jMenuCrtVol =  new CustomMenuItem(
            crtVolAction
        );
        
        DelVolAction delVolAction = new DelVolAction();
        delVolAction.setView( view );
        jMenuDelVol =  new CustomMenuItem(
            delVolAction
        );
        
        OnlineDevAction onlineVol = new OnlineDevAction();
        onlineVol.setView( view );
        jMenuOnlineVol = new CustomMenuItem(
            onlineVol
        );
        
        OfflineDevAction offlineVol = new OfflineDevAction();
        offlineVol.setView( view );
        jMenuOfflineVol = new CustomMenuItem(
            offlineVol
        );
        
        RollbackVolAction rollbackvol = new RollbackVolAction();
        rollbackvol.setView( view);
        jMenuRollbackVol = new CustomMenuItem( rollbackvol );
        
        CrtViewAction crtViewAction = new CrtViewAction();
        crtViewAction.setView( view );
        jMenuCrtView =  new CustomMenuItem(
            crtViewAction
        );
        
        DelViewAction delViewAction = new DelViewAction();
        delViewAction.setView( view );
        jMenuDelView =  new CustomMenuItem(
            delViewAction
        );
        
        MntViewAction mntViewAction = new MntViewAction();
        mntViewAction.setView( view );
        jMenuMntView =  new CustomMenuItem(
            mntViewAction
        );
        
        UMntViewAction umntViewAction = new UMntViewAction();
        umntViewAction.setView( view );
        jMenuUMntView =  new CustomMenuItem(
            umntViewAction
        );
        
        CrtSnapAction crtSnapAction = new CrtSnapAction( 1 );
        crtSnapAction.setView( view );
        jMenuCrtSnap =  new CustomMenuItem(
            crtSnapAction
        );
        jMenuCrtSyncSnap =  new CustomMenuItem(
            crtSnapAction
        );

        CrtSnapAction crtSnapAction2 = new CrtSnapAction( 0 );
        crtSnapAction2.setView( view );
        jMenuCrtAsyncSnap =  new CustomMenuItem(
            crtSnapAction2
        );

        DelSnapAction delSnapAction = new DelSnapAction();
        delSnapAction.setView( view );
        jMenuDelSnap =  new CustomMenuItem(
            delSnapAction
        );
        
        MntSnapAction mntSnapAction = new MntSnapAction();
        mntSnapAction.setView( view );
        jMenuMntSnap =  new CustomMenuItem(
            mntSnapAction
        );
        
        CrtlmAction crtlmAction = new CrtlmAction();
        crtlmAction.setView( view );
        jMenuCrtLm =  new CustomMenuItem(
            crtlmAction
        );
        
        DellmAction dellmAction = new DellmAction();
        dellmAction.setView( view );
        jMenuDelLm =  new CustomMenuItem(
            dellmAction
        );
        
        CrtMjAction crtMjAction = new CrtMjAction();
        crtMjAction.setView( view );
        jMenuCrtMj =  new CustomMenuItem(
            crtMjAction
        );
        
        CrtIncrementMjAction crtIncMjAction = new CrtIncrementMjAction();
        crtIncMjAction.setView( view );
        jMenuCrtMj1 =  new CustomMenuItem(
            crtIncMjAction
        );

        DelMjAction delMjAction = new DelMjAction();
        delMjAction.setView( view );
        jMenuDelMj =  new CustomMenuItem(
            delMjAction
        );
        
        ModMjAction modMjAction = new ModMjAction();
        modMjAction.setView( view );
        jMenuModMj =  new CustomMenuItem(
            modMjAction
        );

        CrtMjSchAction crtMjSchAction = new CrtMjSchAction();
        crtMjSchAction.setView( view );
        jMenuCrtMjSch = new CustomMenuItem(
            crtMjSchAction
        );

        ModMjSchAction modMjSchAction = new ModMjSchAction();
        modMjSchAction.setView( view );
        jMenuModMjSch = new CustomMenuItem(
            modMjSchAction
        );

        DelMjSchAction delMjSchAction = new DelMjSchAction();
        delMjSchAction.setView( view );
        jMenuDelMjSch = new CustomMenuItem(
            delMjSchAction
        );

        StartMjAction startMjAction = new StartMjAction( false );
        startMjAction.setView( view );
        jMenuStartMj =  new CustomMenuItem(
            startMjAction
        );

        QuickStartMjAction qStartMjAction = new QuickStartMjAction();
        qStartMjAction.setView( view );
        jMenuQStartMj = new CustomMenuItem(
            qStartMjAction
        );

        CreateMjInBatchAction batchedCrtMjAction = new CreateMjInBatchAction();
        batchedCrtMjAction.setView( view );
        this.jMenuBatchedCrtMj = new CustomMenuItem(
            batchedCrtMjAction
        );
        
        ModifyMjInBatchAction batchedModMjAction = new ModifyMjInBatchAction();
        batchedModMjAction.setView( view );
        this.jMenuBatchedModMj = new CustomMenuItem(
            batchedModMjAction
        );

        SetupMjSchAction setupMjSchAction = new SetupMjSchAction();
        setupMjSchAction.setView( view );
        this.jMenuSetupMjSch = new CustomMenuItem(
            setupMjSchAction
        );

        StartOrStopMjInBatchAction batchedStartStopMjAction = new StartOrStopMjInBatchAction();
        batchedStartStopMjAction.setView( view );
        this.jMenuBatchedStartStopMj = new CustomMenuItem(
            batchedStartStopMjAction
        );

        StopMjAction stopMjAction = new StopMjAction();
        stopMjAction.setView( view );
        jMenuStopMj =  new CustomMenuItem(
            stopMjAction
        );
        
        MonitorMjAction monMjAction = new MonitorMjAction();
        monMjAction.setView( view );
        jMenuMonMj = new CustomMenuItem(
            monMjAction
        );

        CrtCjAction crtCjAction = new CrtCjAction();
        crtCjAction.setView( view );
        jMenuCrtCj =  new CustomMenuItem(
            crtCjAction
        );
        
        jMenuDelCj =  new CustomMenuItem(
            delMjAction
        );
        
        jMenuModCj =  new CustomMenuItem(
            modMjAction
        );
        
        jMenuStartCj =  new CustomMenuItem(
            startMjAction
        );
        jMenuQStartCj = new CustomMenuItem(
            qStartMjAction
        );

        jMenuStopCj =  new CustomMenuItem(
            stopMjAction
        );
        jMenuMonCj = new CustomMenuItem(
            monMjAction
        );

        CrtPoolAction crtPoolAction = new CrtPoolAction();
        crtPoolAction.setView( view );
        jMenuCrtPool =  new CustomMenuItem(
            crtPoolAction
        );
        
        DelPoolAction delPoolAction = new DelPoolAction();
        delPoolAction.setView( view );
        jMenuDelPool =  new CustomMenuItem(
            delPoolAction
        );
        
        CrtHostAction crtHostAction = new CrtHostAction();
        crtHostAction.setView( view );
        jMenuCrtHost = new CustomMenuItem( crtHostAction );
        
        DelHostAction delHostAction = new DelHostAction();
        delHostAction.setView( view );
        jMenuDelHost = new CustomMenuItem( delHostAction );
        
        ModHostAction modHostAction = new ModHostAction();
        modHostAction.setView( view );
        jMenuModHost = new CustomMenuItem( modHostAction );
	
        UserMgrAction userMgrAction = new UserMgrAction();
        userMgrAction.setView( view );
        jMenuUserMgr = new BrowserToolMenuItem( userMgrAction );

        AuditAction auditAction = new AuditAction();
        auditAction.setView( view );
        jMenuAudit = new BrowserToolMenuItem( auditAction );

        ShutdownAction powerdown = new ShutdownAction();
        powerdown.setView( view );
        shutdown = new BrowserToolMenuItem( powerdown );

        CrtUWSAction crtUWSAction = new CrtUWSAction();
        crtUWSAction.setView( view );
        jMenuCrtUWS = new CustomMenuItem( crtUWSAction );
        
        DelUWSAction delUWSAction = new DelUWSAction();
        delUWSAction.setView( view );
        jMenuDelUWS = new CustomMenuItem( delUWSAction );
        
        ModUWSAction modUWSAction = new ModUWSAction();
        modUWSAction.setView( view );
        jMenuModUWS = new CustomMenuItem( modUWSAction );
        
        ModSrcUWSAction modSrcUWSAction = new ModSrcUWSAction();
        modSrcUWSAction.setView( view );
        jMenuModSrcUWSrv = new CustomMenuItem( modSrcUWSAction );
        
        MsgReportAction msgReportAction = new MsgReportAction();
        msgReportAction.setView( view );
        jMenuMsgReport = new BrowserToolMenuItem( msgReportAction );
        btnUWSRpt = new BrowserToolButton( msgReportAction );
        
        LoginPass loginPass = new LoginPass();
        loginPass.setView( view );
        jMenuModPass = new BrowserToolMenuItem( loginPass );
        
        LoginSetup loginSetup = new LoginSetup();
        loginSetup.setView( view );
        jMenuAdminOpt = new BrowserToolMenuItem( loginSetup );
        
        DelDstAgent delNBH = new DelDstAgent();
        delNBH.setView( view );
        jMenuDelDstAgnt = new CustomMenuItem( delNBH );
        
        DelSrcAgntAction delSrcAgnt = new DelSrcAgntAction();
        delSrcAgnt.setView( view );
        jMenuDelSrcAgnt = new CustomMenuItem( delSrcAgnt ); 
        
        DhcpSet dhcpAct = new DhcpSet( );
        dhcpAct.setView( view );
        jMenuDhcpSet = new BrowserToolMenuItem( dhcpAct );
        
        CrtUserAction crtUserAction = new CrtUserAction();
        crtUserAction.setView( view );
        jMenuCrtUser = new BrowserToolMenuItem( crtUserAction );
        
        DelUserAction delUserAction = new DelUserAction();
        delUserAction.setView( view );
        jMenuDelUser = new BrowserToolMenuItem( delUserAction );

        ModUserAction modUserAction = new ModUserAction();
        modUserAction.setView( view );
        jMenuModUser = new BrowserToolMenuItem( modUserAction );
        
        ModPasswdAction modPasswdAction = new ModPasswdAction();
        modPasswdAction.setView( view );
        jMenuModPasswd = new BrowserToolMenuItem( modPasswdAction );
           
        AddProfAction addProfAct = new AddProfAction();
        addProfAct.setView( view );
        addProfile = new CustomMenuItem( addProfAct );
        
        ModProfAction modProfAct = new ModProfAction();
        modProfAct.setView( view );
        modProfile = new CustomMenuItem( modProfAct );

        ModPhyProfAction modPhyProfAct = new ModPhyProfAction();
        modPhyProfAct.setView( view );
        modPhyProfile = new CustomMenuItem( modPhyProfAct );

        DelProfAction delProfAct = new DelProfAction();
        delProfAct.setView( view );
        delProfile = new CustomMenuItem( delProfAct );
        
        RunProfAction runProfAct = new RunProfAction();
        runProfAct.setView( view );
        runProfile = new CustomMenuItem( runProfAct );
        
        VerifyProfAction verifyProfAct = new VerifyProfAction();
        verifyProfAct.setView( view );
        verifyProfile = new CustomMenuItem( verifyProfAct );
        
        RenameProfAction renameProf = new RenameProfAction();
        renameProf.setView( view );
        renameProfile = new CustomMenuItem( renameProf );
        
        AddSchAction addSchAct = new AddSchAction();
        addSchAct.setView( view );
        addSch = new CustomMenuItem( addSchAct );
   
        ModSchAction modSchAct = new ModSchAction();
        modSchAct.setView( view );
        modSch = new CustomMenuItem( modSchAct );
        
        DelSchAction delSchAct = new DelSchAction();
        delSchAct.setView( view );
        delSch = new CustomMenuItem( delSchAct );
            
        DupLogAction dupLogAct = new DupLogAction();
        dupLogAct.setView( view );
        dupLog = new BrowserToolMenuItem( dupLogAct );
        btnDupLog = new BrowserToolButton( dupLogAct );
        
        MonitorAction monitorAct = new MonitorAction();
        monitorAct.setView( view );
        monitorDup = new BrowserToolMenuItem( monitorAct );
        btnMon = new BrowserToolButton( monitorAct );

        SnapTreeAction snapTreeAct = new SnapTreeAction();
        snapTreeAct.setView( view );
        jMenuSnapTree = new CustomMenuItem( snapTreeAct );

        AboutAction aboutAction = new AboutAction();
        aboutAction.setView( view );
        jMenuAboutBackup = new BrowserToolMenuItem( aboutAction );

        CloneDiskAction cloneDiskAction = new CloneDiskAction();
        cloneDiskAction.setView( view );
        jMenuCloneDisk = new CustomMenuItem( cloneDiskAction );

        DeleteUiMirrorVolAction delUiMirVol = new DeleteUiMirrorVolAction();
        delUiMirVol.setView( view );
        jMenuDelUiMirrorVol = new CustomMenuItem( delUiMirVol );

        DeleteCloneDiskAction delCloneDisk = new DeleteCloneDiskAction();
        delCloneDisk.setView( view );
        jMenuDelCloneDisk = new CustomMenuItem( delCloneDisk );

        QueryUISnapAction queryUiSnap = new QueryUISnapAction();
        queryUiSnap.setView( view );
        jMenuQueryUISnap = new CustomMenuItem( queryUiSnap );
        
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_SEPARATOR ), new Object()
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_PROPERTY ),this.jMenuProperty
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_INIT ),this.jMenuInit 
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_INIT ),this.jMenuWinPhyInit
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_FAILOVER ),this.jMenuFailover 
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_FAILOVER ),this.jMenuWinPhyFailover
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_SWITCH_TO_NETDISK ),this.jMenuWinPhySwitchToNetDisk
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_WIN_SWITCH_TO_NETDISK ),this.jMenuSwitchToNetDisk
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_FAILBACK ),this.jMenuFailback 
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_FAILBACK ),this.jMenuWinPhyFailback
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_SWITCH_TO_LOCALDISK ),this.jMenuWinPhySwitchToLocalDisk
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_WIN_SWITCH_TO_LOCALDISK ),this.jMenuSwitchToLocalDisk
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_RESTORE_DISK ),this.jMenuRestoreData 
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_RESTORE_DISK ),this.jMenuWinPhyRestoreData
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_DUP_NETDISK ),this.jMenuWinPhyDuplicateDataFromIscsiToLocaldisk
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_WIN_DUP_NETDISK ),this.jMenuDuplicateDataFromIscsiToLocaldisk
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_PHY_QUERY_SYNC_STATE ),this.jMenuWinPhyQuerySyncState
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_PHY_QUERY_SYNC_STATE_TABLE), this.jMenuWinPhyQuerySyncStateForTable
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_PHY_START_AUTO_CRT_SNAP ),this.jMenuWinPhyStartAutoCrtSnap
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_PHY_QUERY_INIT_PROGRESS ),this.jMenuWinPhyQueryInitState
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_ROLLBACK ),this.jMenuRollback 
        );
        
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_VOL ),this.jMenuCrtVol 
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_VOL ),this.jMenuDelVol 
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_ONLINE ),this.jMenuOnlineVol 
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_OFFLINE ),this.jMenuOfflineVol 
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_ROLLBACK_VOL ),this.jMenuRollbackVol 
        );
        
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_SNAP ),this.jMenuCrtSnap 
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_SYNC_SNAP ),this.jMenuCrtSyncSnap 
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_ASYNC_SNAP ),this.jMenuCrtAsyncSnap
        );

        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_SNAP ),this.jMenuDelSnap 
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_MNT_SNAP ),this.jMenuMntSnap 
        );
        
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_LUNMAP ),this.jMenuCrtLm 
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CANCEL_LM ),this.jMenuDelLm 
        );
        
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_HOST ),this.jMenuCrtHost 
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_HOST ),this.jMenuDelHost
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_MOD_HOST ),this.jMenuModHost
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_DSTAGNT ),this.jMenuDelDstAgnt
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_SRCAGNT ),this.jMenuDelSrcAgnt         
        );
        
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_UWS_SRV ),this.jMenuCrtUWS
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_UWS_SRV ),this.jMenuDelUWS
        );
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_MOD_UWS_SRV ),this.jMenuModUWS
        );
        
        funcMap.put( 
            new Integer( MenuAndBtnCenterForMainUi.FUNC_INIT_NWIN ),this.jMenuInitNWinHost 
        ); 
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_REST_DISK_NWIN ),this.jMenuRestDataForNWinHost
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_SELECT_BOOT_VER ),this.jMenuSelBootVer
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_IBOOT_WIZARD ),this.jMenuIBootForLinux
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_ROLLBACK_NWIN ),this.jMenuRollbackForNWin
        );
        
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_VIEW ),this.jMenuCrtView
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_VIEW ),this.jMenuDelView
        );
        
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_MNT_VIEW ),this.jMenuMntView
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_UMNT_VIEW ),this.jMenuUMntView
        );
        
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_POOL ),this.jMenuCrtPool
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_POOL ),this.jMenuDelPool
        );
        
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_MJ ),this.jMenuCrtMj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_MJ1 ),this.jMenuCrtMj1
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_MJ ),this.jMenuDelMj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_MOD_MJ ),this.jMenuModMj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_MJ_SCH ), this.jMenuCrtMjSch
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_MOD_MJ_SCH ), this.jMenuModMjSch
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_MJ_SCH ), this.jMenuDelMjSch
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_START_MJ ),this.jMenuStartMj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_QUICK_START_MJ ),this.jMenuQStartMj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_BATCH_START_STOP_MJ ),this.jMenuBatchedStartStopMj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_BATCH_CREATE_MJ ),this.jMenuBatchedCrtMj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_BATCH_MOD_MJ ),this.jMenuBatchedModMj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_SETUP_MJ_SCH ),this.jMenuSetupMjSch
        );
        
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_STOP_MJ ),this.jMenuStopMj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_MONITOR_MJ ),this.jMenuMonMj
        );

        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CRT_CJ ),this.jMenuCrtCj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_CJ ),this.jMenuDelCj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_MOD_CJ ),this.jMenuModCj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_START_CJ ),this.jMenuStartCj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_STOP_CJ ),this.jMenuStopCj
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_MONITOR_CJ ),this.jMenuMonCj
        );

        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_CLONE_DISK ),this.jMenuCloneDisk
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_UI_MIRROR_VOL ),this.jMenuDelUiMirrorVol
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_CLONE_DISK ),this.jMenuDelCloneDisk
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_QUEYR_UISNAP ),this.jMenuQueryUISnap
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_SNAP_TREE ),this.jMenuSnapTree
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_MOD_SRC_UWS_SRV ),this.jMenuModSrcUWSrv
        );
        
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_ADD_PROF ),this.addProfile
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_MOD_PROF ),this.modProfile
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_PHY_MOD_PROFILE ),this.modPhyProfile
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_PROF ),this.delProfile
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_RENAME_PROF ),this.renameProfile
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_RUN_PROF ),this.runProfile
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_VERIFY_PROF ),this.verifyProfile
        );
        
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_ADD_SCH ),this.addSch
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_MOD_SCH ),this.modSch
        );
        funcMap.put(
            new Integer( MenuAndBtnCenterForMainUi.FUNC_DEL_SCH ),this.delSch
        ); 
    }

    public void setEnabledOnMainMenu( int index,boolean val ){
        mainMenu[index].setEnabled( val );
    }
    
    private void addMenuToView(){
        mainMenu[INDEX_FILE].setMnemonic('F');
        mainMenu[INDEX_LOGICAL_PROTECT].setMnemonic('L');
        mainMenu[INDEX_PHYSICAL_PROTECT].setMnemonic('P');
        mainMenu[INDEX_DEV].setMnemonic('D');
        mainMenu[INDEX_REMOTE_DR].setMnemonic('E');
        mainMenu[INDEX_SETUP].setMnemonic('S');
        mainMenu[INDEX_HELP].setMnemonic('H');
        
        view.addMenu( mainMenu[INDEX_FILE] );
        //view.addMenu( mainMenu[INDEX_LOGICAL_PROTECT] );
        view.addMenu( mainMenu[INDEX_PHYSICAL_PROTECT] );
        mainMenu[INDEX_LOGICAL_PROTECT].add( this.jMenuWinPro );
        if( ResourceCenter.isRelease( view.mode ) ){
            mainMenu[INDEX_LOGICAL_PROTECT].add( this.jMenuNonWinPro );
        }
        mainMenu[INDEX_LOGICAL_PROTECT].addSeparator();
        
        if( view.hasDP ){
            mainMenu[INDEX_LOGICAL_PROTECT].add( this.jMenuProf );
            jMenuProf.add( runProfile );
            jMenuProf.add( verifyProfile );
            jMenuProf.addSeparator();
            jMenuProf.add( addProfile );
            jMenuProf.add( modProfile );
            jMenuProf.add( delProfile );
            jMenuProf.add( renameProfile );
            mainMenu[INDEX_LOGICAL_PROTECT].addSeparator();
            mainMenu[INDEX_LOGICAL_PROTECT].add( this.jMenuSch );
            jMenuSch.add( addSch );
            jMenuSch.add( modSch );
            jMenuSch.add( delSch );
            mainMenu[INDEX_LOGICAL_PROTECT].addSeparator();
            mainMenu[INDEX_LOGICAL_PROTECT].add( this.dupLog );
            mainMenu[INDEX_LOGICAL_PROTECT].addSeparator();
            mainMenu[INDEX_LOGICAL_PROTECT].add( this.monitorDup );
        }
        //view.addMenu( mainMenu[INDEX_DEV] );
        //view.addMenu( mainMenu[INDEX_REMOTE_DR] );
        view.addMenu( mainMenu[INDEX_SETUP] );
        //view.addMenu( mainMenu[INDEX_HELP] );
        
        mainMenu[INDEX_FILE].add(jMenuLogin);
        mainMenu[INDEX_FILE].add(jMenuLogout);
        mainMenu[INDEX_FILE].add(jMenuProperty );
        mainMenu[INDEX_FILE].addSeparator();
        mainMenu[INDEX_FILE].add(jMenuExit);

        jMenuWinPro.add( jMenuInit );
        jMenuWinPro.add( jMenuDR );
        jMenuDR.setIcon( ResourceCenter.MENU_ICON_BLANK );
        jMenuDR.add( jMenuFailover );
        jMenuDR.add( jMenuFailback );
        jMenuWinPro.add( jMenuRestoreData );

        this.jMenuNonWinPro.add(jMenuInitNWinHost );
        this.jMenuNonWinPro.add(jMenuSelBootVer);
        this.jMenuNonWinPro.add(jMenuIBootForLinux);
        this.jMenuNonWinPro.add(jMenuRestDataForNWinHost );

        mainMenu[INDEX_PHYSICAL_PROTECT].add( this.jMenuWinPhyInit );
        mainMenu[INDEX_PHYSICAL_PROTECT].add( this.jMenuWinPhyDR );
        this.jMenuWinPhyDR.add( this.jMenuWinPhyFailover );
        this.jMenuWinPhyDR.add(this.jMenuWinPhyFailback );
        mainMenu[INDEX_PHYSICAL_PROTECT].add( this.jMenuWinPhyRestoreData );

        mainMenu[INDEX_PHYSICAL_PROTECT].addSeparator();
        mainMenu[INDEX_PHYSICAL_PROTECT].add( modPhyProfile );

        mainMenu[INDEX_DEV].add( jMenuVol );
        jMenuVol.add( jMenuCrtVol );
        jMenuVol.add( jMenuDelVol );
        mainMenu[INDEX_DEV].add( jMenuSnap );
        jMenuSnap.add( jMenuCrtSnap );
        jMenuSnap.add(jMenuDelSnap );
        mainMenu[INDEX_DEV].add( jMenuView );
        jMenuView.add( jMenuCrtView );
        jMenuView.add( jMenuDelView );
        mainMenu[INDEX_DEV].addSeparator();
        mainMenu[INDEX_DEV].add(jMenuLunMap);
        jMenuLunMap.add(jMenuCrtLm);
        jMenuLunMap.add(jMenuDelLm );
        mainMenu[INDEX_DEV].addSeparator();
        mainMenu[INDEX_DEV].add( jMenuOnlineVol );
//        mainMenu[INDEX_DEV].add( jMenuOfflineVol );
        mainMenu[INDEX_DEV].addSeparator();
        mainMenu[INDEX_DEV].add(jMenuPool);
        jMenuPool.add( jMenuCrtPool );
        jMenuPool.add( jMenuDelPool );
        
        mainMenu[INDEX_REMOTE_DR].add( this.jMenuCrtUWS );
        mainMenu[INDEX_REMOTE_DR].add( this.jMenuModUWS );
        mainMenu[INDEX_REMOTE_DR].add( this.jMenuDelUWS );
        mainMenu[INDEX_REMOTE_DR].addSeparator();
        mainMenu[INDEX_REMOTE_DR].add( this.jMenuCrtMj );
        mainMenu[INDEX_REMOTE_DR].add( this.jMenuCrtMj1 );
        mainMenu[INDEX_REMOTE_DR].add( jMenuDelMj );
        mainMenu[INDEX_REMOTE_DR].add( jMenuModMj );
        mainMenu[INDEX_REMOTE_DR].add( this.jMenuStartMj );
        mainMenu[INDEX_REMOTE_DR].add( jMenuStopMj );
        mainMenu[INDEX_REMOTE_DR].add( jMenuMonMj );
        mainMenu[INDEX_REMOTE_DR].addSeparator();
        mainMenu[INDEX_REMOTE_DR].add( jMenuRollback );
        mainMenu[INDEX_REMOTE_DR].add( jMenuRollbackForNWin );
        mainMenu[INDEX_REMOTE_DR].add( jMenuRollbackVol );
        mainMenu[INDEX_REMOTE_DR].addSeparator();
        mainMenu[INDEX_REMOTE_DR].add( this.jMenuDelSrcAgnt );
        mainMenu[INDEX_REMOTE_DR].addSeparator();
        mainMenu[INDEX_REMOTE_DR].add( jMenuQueryUISnap );
        mainMenu[INDEX_REMOTE_DR].add( jMenuCloneDisk );
        mainMenu[INDEX_REMOTE_DR].add( jMenuDelUiMirrorVol );
        mainMenu[INDEX_REMOTE_DR].add( jMenuDelCloneDisk );
        
        //mainMenu[INDEX_SETUP].add( jMenuHost );
        jMenuHost.setIcon( ResourceCenter.MENU_ICON_BLANK );
        jMenuHost.add( jMenuCrtHost );
        jMenuHost.add( jMenuModHost );
        jMenuHost.add( jMenuDelHost );
        mainMenu[INDEX_SETUP].add( jMenuDhcpSet );
        mainMenu[INDEX_SETUP].add( jMenuModPass );
        mainMenu[INDEX_SETUP].add( jMenuAdminOpt );
        //mainMenu[INDEX_SETUP].add( jMenuUserMgr );
        mainMenu[INDEX_SETUP].add( jMenuAudit );

        // 暂时没有用户管理
        /*
        mainMenu[INDEX_SETUP].add( jMenuUser  );
        jMenuUser.add( jMenuCrtUser );
        jMenuUser.add( jMenuDelUser );
        jMenuUser.add( jMenuModUser );
        jMenuUser.add( jMenuModPasswd );
         */
              
        mainMenu[INDEX_HELP].add(jMenuAboutBackup);
    }
    
    private void addButtonToView(){
        CustomSeparator jSeparator0 = new CustomSeparator( JSeparator.VERTICAL );
        view.addSeparator( jSeparator0 );
        
        view.addToolButton( btnLogin );
        view.addToolButton( btnLogout );
        //CustomSeparator jSeparator1 = new CustomSeparator( JSeparator.VERTICAL );
        //view.addSeparator( jSeparator1 );
        
        //view.addToolButton( btnInit );
        //view.addToolButton( btnFailover );
        //view.addToolButton( btnRstOriData );

        // 废掉这个功能（2010.3.18）
        //view.addToolButton( btnUWSRpt );
        CustomSeparator jSeparator2 = new CustomSeparator( JSeparator.VERTICAL );
        view.addSeparator( jSeparator2 );
        
//        if( view.hasDP ){
//            view.addToolButton( btnDupLog );
//            view.addToolButton( btnMon );
//            CustomSeparator jSeparator3 = new CustomSeparator( JSeparator.VERTICAL );
//            view.addSeparator( jSeparator3 );
//        }
        
        view.addToolButton(btnExit);
    }
    
    public void setupConnectButtonStatus(boolean isConnected){
        if( isConnected ){
            jMenuLogin.setEnabled(false);
            btnLogin.setEnabled(false);
            jMenuLogout.setEnabled(true);
            btnLogout.setEnabled(true);
            jMenuProperty.setEnabled( false );
            
            jMenuInit.setEnabled( true );
            btnInit.setEnabled( true );
            jMenuInitNWinHost.setEnabled( true );
            jMenuSelBootVer.setEnabled( false );
            jMenuIBootForLinux.setEnabled( false );
            jMenuFailover.setEnabled( false );
            jMenuFailback.setEnabled( false );
            btnFailover.setEnabled( false );
            jMenuRestoreData.setEnabled( false );
            jMenuRollback.setEnabled( false );
            btnRstOriData.setEnabled( false );
            jMenuRestDataForNWinHost.setEnabled( false );
            jMenuRollbackForNWin.setEnabled( false );
            btnUWSRpt.setEnabled( true );
 
            jMenuCrtVol.setEnabled( false );
            jMenuDelVol.setEnabled( false );
            
            jMenuOnlineVol.setEnabled( false );
            
            jMenuCrtView.setEnabled( false );
            jMenuDelView.setEnabled( false );
            
            jMenuPool.setEnabled( true );
            jMenuCrtPool.setEnabled( true );
            jMenuDelPool.setEnabled( false );
            
            jMenuCrtSnap.setEnabled( false );
            jMenuDelSnap.setEnabled( false );
    
            jMenuCrtLm.setEnabled( false );
            jMenuDelLm.setEnabled( false );
            
            jMenuCrtHost.setEnabled( true );
            jMenuDelHost.setEnabled( false );
            jMenuModHost.setEnabled( false );
            jMenuDhcpSet.setEnabled( true );
            jMenuMsgReport.setEnabled( true );
            jMenuModPass.setEnabled( true );
            
        }else{
            setDisableForMenuAndBtn();
            
            jMenuLogin.setEnabled( true );
            btnLogin.setEnabled( true );
            jMenuLogout.setEnabled(false);
            btnLogout.setEnabled(false);
        }
    }

    public static String getAuditOPType( int type ){
        String str;

        switch( type ){
            case FUNC_INIT:
                str = SanBootView.res.getString("View.MenuItem.init");
                break;
            case FUNC_FAILOVER:
                str = SanBootView.res.getString("View.MenuItem.failover");
                break;
            case FUNC_RESTORE_DISK:
                str = SanBootView.res.getString("View.MenuItem.rstOriData");
                break;
            case FUNC_FAILBACK:
                str = SanBootView.res.getString("View.MenuItem.failback");
                break;
            case FUNC_MOD_HOST:
                str = ResourceCenter.getCmdString(  ResourceCenter.CMD_MOD_CLIENT );
                break;
            case FUNC_DEL_HOST:
                str = ResourceCenter.getCmdString(  ResourceCenter.CMD_DEL_CLIENT );
                break;
            case FUNC_CRT_VOL:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_ADD_VOL );
                break;
            case FUNC_DEL_VOL:
                str = ResourceCenter.getCmdString(  ResourceCenter.CMD_DEL_VOL );
                break;
            case FUNC_LUNMAP:
                str = ResourceCenter.getCmdString(  ResourceCenter.CMD_ADD_LUNMAP);
                break;
            case FUNC_CANCEL_LM:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_DEL_LUNMAP );
                break;
            case FUNC_CRT_SNAP:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_ADD_SNAP );
                break;
            case FUNC_DEL_SNAP:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_DEL_SNAP );
                break;
            case FUNC_CRT_VIEW:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_ADD_VIEW );
                break;
            case  FUNC_DEL_VIEW:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_DEL_VIEW );
                break;
            case FUNC_ADD_PROF:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_ADD_PROFILE );
                break;
            case FUNC_DEL_PROF:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_DEL_PROFILE );
                break;
            case FUNC_MOD_PROF:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_MOD_PROFILE );
                break;
            case FUNC_RUN_PROF:
                str = SanBootView.res.getString("View.MenuItem.runProf");
                break;
            case FUNC_VERIFY_PROF:
                str = SanBootView.res.getString("View.MenuItem.verifyProf");;
                break;
            case FUNC_RENAME_PROF:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_RENAME_PROFILE );
                break;
            case FUNC_ADD_SCH:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_ADD_DB_SCHEDULER );
                break;
            case FUNC_MOD_SCH:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_MOD_DB_SCHEDULER );
                break;
            case FUNC_DEL_SCH:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_DEL_DB_SCHEDULER );
                break;
            case FUNC_DHCP:
                str = SanBootView.res.getString("View.MenuItem.dhcp");
                break;
            case FUNC_ADMINOPT:
                str = SanBootView.res.getString("View.MenuItem.adminOpt");
                break;
            case FUNC_USER_AUDIT:
                str = SanBootView.res.getString("View.MenuItem.audit");
                break;
            case FUNC_USER_MGR:
                str = SanBootView.res.getString("View.MenuItem.userMgr");
                break;
            case FUNC_CRT_POOL:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_DHCP_CHG_OPT );
                break;
            case FUNC_DEL_POOL:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_DHCP_CHG_OPT );
                break;
            case FUNC_CANCEL_JOB:
                str = ResourceCenter.getCmdString( ResourceCenter.CMD_KILL_TASK );
                break;
            case FUNC_MOD_PASS:
                str = SanBootView.res.getString("View.MenuItem.modPasswd");
                break;
            case FUNC_DUP_LOG:
                str = SanBootView.res.getString("common.cmd.delTskLog");
                break;
            case FUNC_LOGIN:
                str = SanBootView.res.getString("common.cmd.login");
                break;
            case FUNC_LOGOUT:
                str = SanBootView.res.getString("common.cmd.logout");
                break;
            case FUNC_SHUTDOWN:
                str = SanBootView.res.getString("RightCustomDialog.checkbox.shutdown");
                break;
            case FUNC_WIN_PHY_INIT:
                str = SanBootView.res.getString("RightCustomDialog.checkbox.phy-init");
                break;
            case FUNC_WIN_PHY_FAILOVER:
                str = SanBootView.res.getString("RightCustomDialog.checkbox.phy-netboot");
                break;
            case FUNC_WIN_PHY_FAILBACK:
                str = SanBootView.res.getString("RightCustomDialog.checkbox.phy-localdiskboot");
                break;
            case FUNC_WIN_PHY_RESTORE_DISK:
                str = SanBootView.res.getString("RightCustomDialog.checkbox.phy-rstOriDisk");
                break;
            default:
                str = SanBootView.res.getString( "common.cmd.unknown" );
                break;
        }

        return str;
    }

    public static boolean isRelatedToUser( int eid ){
        switch( eid ){
            // 如下操作与client有关系
            case FUNC_INIT:
            case FUNC_FAILOVER:
            case FUNC_MOD_HOST:
            case FUNC_DEL_HOST:
            case FUNC_ADD_PROF:
            case FUNC_DEL_PROF:
            case FUNC_MOD_PROF:
            case FUNC_RUN_PROF:
            case FUNC_VERIFY_PROF:
            case FUNC_RENAME_PROF:
            case FUNC_ADD_SCH:
            case FUNC_MOD_SCH:
            case FUNC_DEL_SCH:
            case FUNC_CANCEL_JOB:

            case FUNC_WIN_PHY_INIT:
            case FUNC_WIN_PHY_FAILOVER:
                return true;
            // 如下操作与client没有关系
            case FUNC_CRT_VOL:
            case FUNC_DEL_VOL:
            case FUNC_LUNMAP:
            case FUNC_CANCEL_LM:
            case FUNC_CRT_SNAP:
            case FUNC_DEL_SNAP:
            case FUNC_CRT_VIEW:
            case FUNC_DEL_VIEW:
            case FUNC_DHCP:
            case FUNC_ADMINOPT:
            case FUNC_USER_AUDIT:
            case FUNC_USER_MGR:
            case FUNC_CRT_POOL:
            case FUNC_DEL_POOL:
            case FUNC_MOD_PASS:
            case FUNC_DUP_LOG:
            case FUNC_LOGIN:
            case FUNC_LOGOUT:
            case FUNC_RESTORE_DISK:
            case FUNC_FAILBACK:
            case FUNC_SHUTDOWN:
                
            case FUNC_WIN_PHY_FAILBACK:
            case FUNC_WIN_PHY_RESTORE_DISK:
                return false;
            default:
                return false;
        }
    }

    public int getLevelFromType( int type ){
        int level;
SanBootView.log.debug( getClass().getName(), "type ======> : " + type  ); 
        
        switch( type ){
            case ResourceCenter.TYPE_ROOT_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_ROOT;
                break;
            case ResourceCenter.TYPE_CHIEF_HOST_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_HOSTLIST;
                break;
            case ResourceCenter.TYPE_HOST_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_BOOT_HOST;
                break;
            case ResourceCenter.TYPE_CHIEF_DEST_UWS:
                level = MenuAndBtnCenterForMainUi.LEVEL_UWSLIST;
                break;
            case ResourceCenter.TYPE_DEST_UWS:
                level = MenuAndBtnCenterForMainUi.LEVEL_DEST_UWS_SRV; // 目的端UWS server
                break;
            case ResourceCenter.TYPE_SRC_UWSSRV:
                level = MenuAndBtnCenterForMainUi.LEVEL_SRC_UWS_SRV;  // 源端UWS server
                break;
            case ResourceCenter.TYPE_VOLUME_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_VOL;
                break;
            case ResourceCenter.TYPE_LV_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_LV;
                break;
            case ResourceCenter.TYPE_ACTLINK_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_ACTLINK;
                break;
            case ResourceCenter.TYPE_CHIEF_LUNMAP_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_LMLIST;
                break;
            case ResourceCenter.TYPE_LUNMAP_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_LM;
                break;
            case ResourceCenter.TYPE_CHIEF_SNAP_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_SNAPLIST;
                break;
            case ResourceCenter.TYPE_SNAP_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_SNAP;
                break;
            case ResourceCenter.TYPE_MIRROR_SNAP:
                level = MenuAndBtnCenterForMainUi.LEVEL_MIRROR_SNAP;
                break;
            case ResourceCenter.TYPE_MIRROR_SNAP_VIEW:
                level = MenuAndBtnCenterForMainUi.LEVEL_MIRROR_SNAP_VIEW;
                break;
            case ResourceCenter.TYPE_VIEW_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_VIEW;
                break;
            case ResourceCenter.TYPE_CHIEF_ORPHAN_VOL:
                level = MenuAndBtnCenterForMainUi.LEVEL_ORPH_VOLLIST;
                break;
            case ResourceCenter.TYPE_ORPHAN_VOL:
                level = MenuAndBtnCenterForMainUi.LEVEL_ORPH_VOL;
                break;
            case ResourceCenter.TYPE_CHIEF_USER_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_USER_LIST;
                break;
            case ResourceCenter.TYPE_USER_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_USER;
                break;
            case ResourceCenter.TYPE_CHIEF_POOL:
                level = MenuAndBtnCenterForMainUi.LEVEL_POOLLIST;
                break;
            case ResourceCenter.TYPE_POOL:
                level = MenuAndBtnCenterForMainUi.LEVEL_POOL;
                break;
            case ResourceCenter.TYPE_CHIEF_MIRROR_JOB:
                level = MenuAndBtnCenterForMainUi.LEVEL_MJLIST;
                break;
            case ResourceCenter.TYPE_MIRROR_JOB:
                level = MenuAndBtnCenterForMainUi.LEVEL_MJ;
                break;
            case ResourceCenter.TYPE_REMOTE_HOST:
                level = MenuAndBtnCenterForMainUi.LEVEL_SRC_AGENT;
                break;
            case ResourceCenter.TYPE_DEL_SNAP_INDEX:
                level = MenuAndBtnCenterForMainUi.LEVEL_DEL_SNAP;
                break;
            case ResourceCenter.TYPE_DEST_AGENT:
                level = MenuAndBtnCenterForMainUi.LEVEL_NETBOOTED_HOST;
                break;  
            case ResourceCenter.TYPE_CHIEF_PROF:
            case ResourceCenter.TYPE_CHIEF_PPPROF:
                level = MenuAndBtnCenterForMainUi.LEVEL_PROFLIST;
                break;
            case ResourceCenter.TYPE_PROF:
                level = MenuAndBtnCenterForMainUi.LEVEL_PROF;
                break;
            case ResourceCenter.TYPE_PPPROF:
                level = MenuAndBtnCenterForMainUi.LEVEL_PPPROF;
                break;
            case ResourceCenter.TYPE_CHIEF_SCH:
                level = MenuAndBtnCenterForMainUi.LEVEL_SCHLIST;
                break;
            case ResourceCenter.TYPE_SCH:
                level = MenuAndBtnCenterForMainUi.LEVEL_SCH;
                break;
            case ResourceCenter.TYPE_CLONE_DISK:
                level = MenuAndBtnCenterForMainUi.LEVEL_CLONE_DISK;
                break;
            case ResourceCenter.TYPE_MIRROR_VOL:
                level = MenuAndBtnCenterForMainUi.LEVEL_MIRROR_VOL;
                break;
            case ResourceCenter.TYPE_UNLIMITED_INC_MIRROR_SNAP:
                level = MenuAndBtnCenterForMainUi.LEVEL_UIM_SNAP;
                break;
            case ResourceCenter.TYPE_UNLIMITED_INC_MIRROR_VOL:
                level = MenuAndBtnCenterForMainUi.LEVEL_UIM_VOL;
                break;
            case ResourceCenter.TYPE_CHIEF_UIM_SNAP:
                level = MenuAndBtnCenterForMainUi.LEVEL_UIM_SNAP_LIST;
                break;
            default:
                level = MenuAndBtnCenterForMainUi.LEVEL_UNKNOWN;
                break;
        }
SanBootView.log.debug( getClass().getName(), "level ======> : " + level  );
        return level;
    }
    
    // 根据树上点中节点的种类,来设置不同 button 的状态
    public void setupSelectionButtonStatus(int level){
        BackupUser user = null;
SanBootView.log.debug( getClass().getName(), "select level: "+level );
        
        // 先进行全局性的控制
        setDisableForMenuAndBtn();
        setEnabledForGolbalMenuAndBtn();
                
        // 再进行局部上的控制
        switch( level ){
            case MenuAndBtnCenterForMainUi.LEVEL_HOSTLIST:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!= null ){
                    if( user.isAdminRight() ){
                        jMenuHost.setEnabled( true );
                        jMenuCrtHost.setEnabled( true );
                        jMenuDelHost.setEnabled( false );
                        jMenuModHost.setEnabled( false );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_BOOT_HOST:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        jMenuProperty.setEnabled( true );
                        jMenuHost.setEnabled( true );
                        jMenuCrtHost.setEnabled( false );
                        jMenuDelHost.setEnabled( true );
                        jMenuModHost.setEnabled( true );

                        this.jMenuWinPro.setEnabled(true);
                        this.jMenuNonWinPro.setEnabled(true);
                        jMenuDR.setEnabled( true );
                        jMenuSelBootVer.setEnabled( true );
                        jMenuIBootForLinux.setEnabled( true );
                        jMenuFailover.setEnabled( true );
                        btnFailover.setEnabled( true );
                        jMenuFailback.setEnabled( true );
                        jMenuRestoreData.setEnabled( true );
                        btnRstOriData.setEnabled( true );
                        jMenuRestDataForNWinHost.setEnabled( true );

                        this.jMenuWinPhyInit.setEnabled( true );
                        this.jMenuWinPhyDR.setEnabled( true );
                        this.jMenuWinPhyFailover.setEnabled( true );
                        this.jMenuWinPhyFailback.setEnabled( true );
                        this.jMenuWinPhyRestoreData.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_UWSLIST:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!= null ){
                    if( user.isAdminRight() ){
                        jMenuCrtUWS.setEnabled( true );
                        jMenuDelUWS.setEnabled( false );
                        jMenuModUWS.setEnabled( false );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_DEST_UWS_SRV:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        jMenuCrtUWS.setEnabled( false );
                        jMenuDelUWS.setEnabled( true );
                        jMenuModUWS.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_DEL_SNAP:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        jMenuSnap.setEnabled( true );
                        jMenuDelSnap.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_SRC_UWS_SRV:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        jMenuModSrcUWSrv.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_ORPH_VOLLIST:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        jMenuVol.setEnabled( true );
                        jMenuCrtVol.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_ORPH_VOL:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        jMenuVol.setEnabled( true );
                        jMenuDelVol.setEnabled( true );
                        jMenuOnlineVol.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_SNAPLIST:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user != null ){
                    if( user.isAdminRight() ){
                        jMenuSnap.setEnabled( true );
                        jMenuCrtSnap.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_SNAP:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user != null ){
                    if( user.isAdminRight() ){
                        jMenuView.setEnabled( true );
                        jMenuCrtView.setEnabled( true );
                        jMenuSnap.setEnabled( true );
                        jMenuDelSnap.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_MIRROR_SNAP:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user != null ){
                    if( user.isAdminRight() ){
                        jMenuView.setEnabled( true );
                        jMenuCrtView.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_VIEW:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user != null ){
                    if( user.isAdminRight() ){
                        jMenuView.setEnabled( true );
                        jMenuDelView.setEnabled( true );
                        jMenuOnlineVol.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_LMLIST:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user != null ){
                    if( user.isAdminRight() ){
                        jMenuLunMap.setEnabled( true );
                        jMenuCrtLm.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_LM:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user != null ){
                    if( user.isAdminRight() ){
                        jMenuLunMap.setEnabled( true );
                        jMenuDelLm.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_POOL:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user != null ){
                    if( user.isAdminRight() ){
                        jMenuDelPool.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_MJLIST:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user != null ){
                    if( user.isAdminRight() ){
                        jMenuCrtMj.setEnabled( true );
                        jMenuCrtMj1.setEnabled( true );
                        jMenuDelMj.setEnabled( false );
                        jMenuModMj.setEnabled( false );
                        jMenuStartMj.setEnabled( false );
                        jMenuStopMj.setEnabled( false );
                        jMenuMonMj.setEnabled( false );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_MJ:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user != null ){
                    if( user.isAdminRight() ){
                        Object selObj = view.getSelectedObjFromSanBoot();
                        MirrorJob mj = (MirrorJob)selObj;
                        if( mj != null ){
                            jMenuCrtMj.setEnabled( false );
                            jMenuCrtMj1.setEnabled( false );
                            jMenuDelMj.setEnabled( !mj.isCjType() );
                            jMenuModMj.setEnabled( !mj.isCjType() );
                            jMenuStartMj.setEnabled( !mj.isCjType() );
                            jMenuStopMj.setEnabled( !mj.isCjType() );
                            jMenuMonMj.setEnabled( !mj.isCjType() );
                        }
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_MIRROR_VOL:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user != null ){
                    if( user.isAdminRight() ){
                        this.jMenuRollbackVol.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_SRC_AGENT:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user != null ){
                    if( user.isAdminRight() ){
                        Object selObj = view.getSelectedObjFromSanBoot();
                        if( ( selObj == null ) || !( selObj instanceof SourceAgent ) ) return;
                        SourceAgent srcAgnt = (SourceAgent)selObj;
                        if( srcAgnt.isNormalHost() ){
                            if( srcAgnt.isWinHost() ){
                                jMenuDR.setEnabled( true );
                                jMenuFailover.setEnabled( true );
                                this.jMenuRollback.setEnabled( true );
                                 btnFailover.setEnabled( true );
                            }else{
                                jMenuSelBootVer.setEnabled( true );
                                this.jMenuIBootForLinux.setEnabled( true );
                                this.jMenuRollbackForNWin.setEnabled( true );
                            }
                        }else{
                            if( srcAgnt.isWinHost() ){
                                jMenuDR.setEnabled( true );
                                jMenuFailover.setEnabled( true );
                                 btnFailover.setEnabled( true );
                            }else{
                                jMenuSelBootVer.setEnabled( true );
                                this.jMenuIBootForLinux.setEnabled( true );
                            }
                            this.jMenuDelSrcAgnt.setEnabled( true );
                        }

                        /*
                        this.jMenuRollback.setEnabled( true );
                        this.jMenuRollbackForNWin.setEnabled( true );
                        this.jMenuDelSrcAgnt.setEnabled( true );

                        jMenuDR.setEnabled( true );
                        jMenuSelBootVer.setEnabled( true );
                        jMenuIBootForLinux.setEnabled( true );
                        jMenuFailover.setEnabled( true );
                        btnFailover.setEnabled( true );
                         */
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_NETBOOTED_HOST:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user != null ){
                    if( user.isAdminRight() ){
                        jMenuDR.setEnabled( true );
                        jMenuFailback.setEnabled( true );
                        jMenuRestoreData.setEnabled( true );
                        btnRstOriData.setEnabled( true );
                        jMenuRestDataForNWinHost.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_VOL:
            case MenuAndBtnCenterForMainUi.LEVEL_LV:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        jMenuOnlineVol.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_PROFLIST:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( ( user != null ) && user.isAdminRight() ){
                    Object selObj = view.getSelectedObjFromSanBoot();
                    if( ( selObj == null ) || !( selObj instanceof ChiefProfile ) ) return;

                    ChiefProfile chiefProf = (ChiefProfile)selObj;
                    if( chiefProf != null ){
                        BrowserTreeNode hostNode = chiefProf.getFatherNode();
                        if( hostNode != null ){
                            BootHost host = (BootHost)hostNode.getUserObject();
                            if( ( host != null ) && host.isMTPPProtect() ){
                                jMenuProf.setEnabled( true );
                                addProfile.setEnabled( true );
                            }
                        }
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_PROF:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        jMenuProf.setEnabled( true );
                        modProfile.setEnabled( true );
                        delProfile.setEnabled( true );
                        runProfile.setEnabled( true );
                        verifyProfile.setEnabled( true );
                        renameProfile.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_PPPROF:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        this.modPhyProfile.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_SCH:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        jMenuSch.setEnabled( true );
                        modSch.setEnabled( true );
                        delSch.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_UIM_VOL:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        this.jMenuQueryUISnap.setEnabled( true );
                        this.jMenuDelUiMirrorVol.setEnabled( true );
                        this.jMenuRollbackVol.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_UIM_SNAP_LIST:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        this.jMenuQueryUISnap.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_UIM_SNAP:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        this.jMenuCloneDisk.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_CLONE_DISK:
                user = view.initor.mdb.getBakUserOnName(
                    view.initor.user
                );
                if( user!=null ){
                    if( user.isAdminRight() ){
                        this.jMenuDelCloneDisk.setEnabled( true );
                    }
                }
                break;
            case MenuAndBtnCenterForMainUi.LEVEL_ROOT:
            case MenuAndBtnCenterForMainUi.LEVEL_ACTLINK:
            case MenuAndBtnCenterForMainUi.LEVEL_USER_LIST:
            case MenuAndBtnCenterForMainUi.LEVEL_POOLLIST:
            case MenuAndBtnCenterForMainUi.LEVEL_USER:
            case MenuAndBtnCenterForMainUi.LEVEL_MIRROR_SNAP_VIEW:
            case MenuAndBtnCenterForMainUi.LEVEL_SCHLIST:
            case MenuAndBtnCenterForMainUi.LEVEL_UNKNOWN:
            default:
                break;
        }
    }

    public void ctrlMenuAndBtnOnRight( int right ){
        if( BackupUser.hasThisRight( right, BackupUser.RIGHT_ADMIN ) ){
            return;
        }

        this.jMenuInit.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_INIT_HOST ) );
        this.btnInit.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_INIT_HOST ) );
        this.jMenuFailover.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_NETBOOT ) );
        this.btnFailover.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_NETBOOT ) );
        this.jMenuRestoreData.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_RST_ORG_DISK ) );
        this.btnRstOriData.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_RST_ORG_DISK ) );
        this.jMenuFailback.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_LOCAL_DISK_BOOT) );
        this.addProfile.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_CRT_PROF) );
        this.modProfile.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_MOD_PROF) );
        this.delProfile.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_DEL_PROF) );
        this.runProfile.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_RUN_PROF) );
        this.verifyProfile.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_VER_PROF) );
        this.renameProfile.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_REN_PROF) );

        if(  BackupUser.hasThisRight( right, BackupUser.RIGHT_CRT_SCH) ||
             BackupUser.hasThisRight( right, BackupUser.RIGHT_MOD_SCH) ||
             BackupUser.hasThisRight( right, BackupUser.RIGHT_DEL_SCH)
        ){
             this.jMenuSch.setEnabled( true );
        }
        this.addSch.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_CRT_SCH)  );
        this.modSch.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_MOD_SCH) );
        this.delSch.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_DEL_SCH)  );

        this.jMenuUserMgr.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_MOD_PWD) );
        this.jMenuDhcpSet.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_DHCP) );
        this.shutdown.setEnabled( BackupUser.hasThisRight( right, BackupUser.RIGHT_SHUTDOWN) );
        this.jMenuAudit.setEnabled( false );
    }

    private void setEnabledForGolbalMenuAndBtn(){
        //BackupUser user = view.initor.mdb.getBakUserOnName(
        //    view.initor.user
        //);
                
        //if( user!=null ){
          //  if( user.isAdminRight() ){
                if( view.audit.isLoginUsrIsAdmin() ){
                    this.jMenuWinPro.setEnabled( true );
                    this.jMenuNonWinPro.setEnabled( true );
                    jMenuInit.setEnabled(true);
                    btnInit.setEnabled(true);
                    jMenuInitNWinHost.setEnabled( true );
                    this.jMenuWinPhyInit.setEnabled( true );

                    jMenuHost.setEnabled( true );
                    jMenuCrtHost.setEnabled( true );
                    jMenuDelHost.setEnabled( false );
                    jMenuModHost.setEnabled( false );

                    jMenuCrtUWS.setEnabled( true );
                    jMenuDelUWS.setEnabled( false );
                    jMenuModUWS.setEnabled( false );

                    jMenuPool.setEnabled( true );
                    jMenuCrtPool.setEnabled( true );

                    jMenuDhcpSet.setEnabled( true );
                    jMenuMsgReport.setEnabled( true );
                    jMenuModPass.setEnabled( true );
                    btnUWSRpt.setEnabled( true );

                    jMenuSch.setEnabled( true );
                    addSch.setEnabled( true );
                    dupLog.setEnabled( true );
                    btnDupLog.setEnabled( true );
                    monitorDup.setEnabled( true );
                    btnMon.setEnabled( true );

                    jMenuUserMgr.setEnabled( true );
                    jMenuAudit.setEnabled( true );
                    shutdown.setEnabled( true );
                }
            //}
        //}
    }
    
    private void setDisableForMenuAndBtn( ){
        jMenuProperty.setEnabled( false );
        jMenuWinPro.setEnabled( false );
        jMenuNonWinPro.setEnabled( false );
        jMenuInit.setEnabled( false);
        jMenuWinPhyInit.setEnabled( false );
        btnInit.setEnabled( false );
        jMenuInitNWinHost.setEnabled( false );
        jMenuSelBootVer.setEnabled(false);
        jMenuIBootForLinux.setEnabled(false );
        jMenuDR.setEnabled( false );
        jMenuWinPhyDR.setEnabled( false );
        jMenuFailover.setEnabled( false );
        jMenuWinPhyFailover.setEnabled(false );
        jMenuFailback.setEnabled( false );
        jMenuWinPhyFailback.setEnabled(false);
        btnFailover.setEnabled( false );
        jMenuRestoreData.setEnabled( false );
        jMenuWinPhyRestoreData.setEnabled(false);
        jMenuRollback.setEnabled( false );
        btnRstOriData.setEnabled( false );
        jMenuRestDataForNWinHost.setEnabled( false );
        jMenuRollbackForNWin.setEnabled( false );
        
        jMenuVol.setEnabled( false );
        jMenuCrtVol.setEnabled( false );
        jMenuDelVol.setEnabled( false );
        jMenuOnlineVol.setEnabled( false );
            
        jMenuView.setEnabled( false );
        jMenuCrtView.setEnabled( false );
        jMenuDelView.setEnabled( false );
        
        jMenuPool.setEnabled( false );
        jMenuCrtPool.setEnabled( false );
        jMenuDelPool.setEnabled( false );
        
        jMenuSnap.setEnabled( false );
        jMenuCrtSnap.setEnabled( false );
        jMenuDelSnap.setEnabled( false );
    
        jMenuLunMap.setEnabled( false );
        jMenuCrtLm.setEnabled( false );
        jMenuDelLm.setEnabled( false );
     
        jMenuHost.setEnabled( false );
        jMenuCrtHost.setEnabled( false );
        jMenuDelHost.setEnabled( false );
        jMenuModHost.setEnabled( false );
        
        jMenuCrtUWS.setEnabled( false );
        jMenuDelUWS.setEnabled( false );
        jMenuModUWS.setEnabled( false );
        
        jMenuCrtMj.setEnabled( false );
        jMenuDelMj.setEnabled( false );
        jMenuModMj.setEnabled( false );
        jMenuStartMj.setEnabled( false );
        jMenuStopMj.setEnabled( false );
        jMenuMonMj.setEnabled( false );
        
        jMenuProf.setEnabled( false );
        addProfile.setEnabled( false );
        modProfile.setEnabled( false );
        modPhyProfile.setEnabled( false );
        delProfile.setEnabled( false );
        runProfile.setEnabled( false );
        verifyProfile.setEnabled( false );
        renameProfile.setEnabled( false );
        jMenuSch.setEnabled( false );
        addSch.setEnabled( false );
        delSch.setEnabled( false );
        modSch.setEnabled( false );
        dupLog.setEnabled( false );
        btnDupLog.setEnabled( false );
        monitorDup.setEnabled( false );
        btnMon.setEnabled( false );
        jMenuModSrcUWSrv.setEnabled( false );
        jMenuDhcpSet.setEnabled( false );
        jMenuMsgReport.setEnabled( false );
        jMenuModPass.setEnabled( false );
        btnUWSRpt.setEnabled( false );

        jMenuCrtUWS.setEnabled( false );
        jMenuDelUWS.setEnabled( false );
        jMenuModUWS.setEnabled( false );

        jMenuCrtMj.setEnabled( false );
        jMenuCrtMj1.setEnabled( false );
	jMenuDelMj.setEnabled( false );
        jMenuModMj.setEnabled( false );
        jMenuStartMj.setEnabled( false );
        jMenuStopMj.setEnabled( false );
        jMenuMonMj.setEnabled( false );

        jMenuRollbackVol.setEnabled( false );
        jMenuRollbackForNWin.setEnabled( false );
        jMenuRollback.setEnabled( false );
        jMenuDelSrcAgnt.setEnabled( false );

        jMenuCloneDisk.setEnabled( false );
        jMenuDelUiMirrorVol.setEnabled( false );
        jMenuDelCloneDisk.setEnabled( false );
        jMenuQueryUISnap.setEnabled( false );

        jMenuUserMgr.setEnabled( false );
        jMenuAudit.setEnabled( false );
        shutdown.setEnabled( false );
    }
    
    public void setEnabledOnLogin( boolean val ){
        jMenuLogin.setEnabled( val );
    }

    // 判断是否为logical protection function
    private boolean isLPFunction( int funcID ){
        int[] funList = new int[]{
            MenuAndBtnCenterForMainUi.FUNC_INIT,
            MenuAndBtnCenterForMainUi.FUNC_FAILOVER,
            MenuAndBtnCenterForMainUi.FUNC_WIN_SWITCH_TO_NETDISK,
            MenuAndBtnCenterForMainUi.FUNC_RESTORE_DISK,
            MenuAndBtnCenterForMainUi.FUNC_WIN_DUP_NETDISK,
            MenuAndBtnCenterForMainUi.FUNC_FAILBACK,
            MenuAndBtnCenterForMainUi.FUNC_WIN_SWITCH_TO_LOCALDISK,
            MenuAndBtnCenterForMainUi.FUNC_INIT_NWIN,
            MenuAndBtnCenterForMainUi.FUNC_REST_DISK_NWIN,
            MenuAndBtnCenterForMainUi.FUNC_SELECT_BOOT_VER,
            MenuAndBtnCenterForMainUi.FUNC_IBOOT_WIZARD,
            MenuAndBtnCenterForMainUi.FUNC_ADD_PROF,
            MenuAndBtnCenterForMainUi.FUNC_MOD_PROF,
            MenuAndBtnCenterForMainUi.FUNC_DEL_PROF,
            MenuAndBtnCenterForMainUi.FUNC_RENAME_PROF,
            MenuAndBtnCenterForMainUi.FUNC_RUN_PROF,
            MenuAndBtnCenterForMainUi.FUNC_VERIFY_PROF,
            MenuAndBtnCenterForMainUi.FUNC_ADD_SCH,
            MenuAndBtnCenterForMainUi.FUNC_MOD_SCH,
            MenuAndBtnCenterForMainUi.FUNC_DEL_SCH,
            MenuAndBtnCenterForMainUi.FUNC_DUP_LOG,
            MenuAndBtnCenterForMainUi.FUNC_MONITOR_DD
        };
        for( int i=0; i<funList.length; i++ ){
            if( funList[i] == funcID ){
                return true;
            }
        }
        return false;
    }

    // 判断是否为physical protection function
    private boolean isPPFunction( int funcID ){
        int[] funList = new int[]{
            MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_INIT,
            MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_FAILOVER,
            MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_SWITCH_TO_NETDISK,
            MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_DUP_NETDISK,
            MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_SWITCH_TO_LOCALDISK,
            MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_FAILBACK,
            MenuAndBtnCenterForMainUi.FUNC_WIN_PHY_RESTORE_DISK,
            MenuAndBtnCenterForMainUi.FUNC_PHY_MOD_PROFILE,
            MenuAndBtnCenterForMainUi.FUNC_PHY_QUERY_INIT_PROGRESS,
            MenuAndBtnCenterForMainUi.FUNC_PHY_QUERY_SYNC_STATE,
            MenuAndBtnCenterForMainUi.FUNC_PHY_QUERY_SYNC_STATE_TABLE,
            MenuAndBtnCenterForMainUi.FUNC_PHY_START_AUTO_CRT_SNAP
        };
        for( int i=0; i<funList.length; i++ ){
            if( funList[i] == funcID ){
                return true;
            }
        }
        return false;
    }

    //////////////////////////////////////////////////////////////
    //
    //            ��下 面 是 对 右 键 的 支 持
    //
    //     下面的 selectedObj 是PopMenu上选中的菜单项,
    //     右键的行为应该针对它展开
    //
    //////////////////////////////////////////////////////////////
    private JPopupMenu popmenu = new JPopupMenu();
    PopupMenuListener popupMenuListener;
    
    private Object selectedObj = null;
    public Object getSelectedObjForPopupMenu(){
        return selectedObj;
    }
    
    public int addPopMenuItem( Object selectedObj,int type ){
        int i,fsize,okcnt,funcId;
//        boolean addSeparator = true,doCmdpAct;
        
        popmenu.removeAll();
        okcnt = 0;
        
        ArrayList funcSet = ((GeneralInfo)selectedObj).getFunctionList( type );
        if( funcSet == null ) return okcnt;
        
        fsize = funcSet.size();
        for( i=0; i<fsize; i++ ){            
            Object func = funcMap.get( (Integer)funcSet.get(i) );
            if( func == null ) {            
                continue;
            }

            /*
            if( selectedObj instanceof VolumeMap ){
                VolumeMap volMap = (VolumeMap)selectedObj;
                BootHost host = view.initor.mdb.getBootHostFromVector( volMap.getVolClntID() );
                if( host != null ){
                    doCmdpAct = host.isCMDPProtect();
                }else{
                    doCmdpAct = false;
                }
            }else{
                doCmdpAct = false;
            }
             */

            funcId = ( (Integer)funcSet.get(i) ).intValue();
            /*
            if( funcId == MenuAndBtnCenterForMainUi.FUNC_PHY_QUERY_SYNC_STATE ||
                funcId == MenuAndBtnCenterForMainUi.FUNC_PHY_START_AUTO_CRT_SNAP
            ){
                if( !doCmdpAct ) {
                    addSeparator = false;
                    continue;
                }else{
                    addSeparator = true;
                }
            }
            */

            if( func instanceof CustomMenuItem ){
                CustomMenuItem oldItem = (CustomMenuItem)func;
                CustomMenuItem item = new CustomMenuItem(
                    oldItem.getPopMenuText(), 
                    oldItem.getIcon(),
                    oldItem.getAccelerator(), 
                    oldItem.getActionObj()
                );
                if( this.isLPFunction( funcId ) ){
                    item.setEnabled( view.initor.mdb.isSupportMTPP() );
                }else if( this.isPPFunction( funcId ) ){
                    item.setEnabled( view.initor.mdb.isSupportCMDP() );
                }
                okcnt++;
                popmenu.add( item );
            }else{
//                if( addSeparator ){
                    popmenu.addSeparator();
//                }
            }
        }

        return okcnt;
    }
    
    /*
    private boolean checkPerm( int func ){
        if( !view.initor.isConnected ) return false;
        VssUser user = view.initor.mdb.getVssUserOnUserName(
            view.initor.user
        );
        if( user.isAdminRight() ){
            return true;
        }else{
            if( func == MenuAndBtnCenter.FUNC_MODPASSWD ){

                return true;
            }else{
                return false;
            }
        }
    }
    */
    
    public void showPopupMenu( Object _selectedObj,Component fatherComp,int type,int x,int y ){ 
        selectedObj = _selectedObj;
        if( addPopMenuItem( selectedObj,type ) > 0 ){
            popmenu.removePopupMenuListener( popupMenuListener );
            if( type == Browser.POPMENU_TREE_TYPE ){
                popupMenuListener = new PopupMenuListenerForTree();
            }else if( type == Browser.POPMENU_TABLE_ITEM_TYPE ){
                popupMenuListener = new PopupMenuListenerForTable();
            }
            popmenu.addPopupMenuListener( popupMenuListener );
            
            if( view.initor.isLogined() ) {
                popmenu.show( fatherComp,x,y );
            }
        }
    }
    
    class PopupMenuListenerForTree implements PopupMenuListener{
        public void popupMenuCanceled(PopupMenuEvent e){
        }

        public void popupMenuWillBecomeVisible(PopupMenuEvent e){
            view.setIsRightClicked(true);
            view.setTempPath();
        }
        
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e){
            view.setIsRightClicked(false);
        }
    }
    
    class PopupMenuListenerForTable implements PopupMenuListener{
        public void popupMenuCanceled(PopupMenuEvent e){
        }
        
        public void popupMenuWillBecomeVisible(PopupMenuEvent e){
            view.setTempPathOnTable();
        }
        
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e){
        }
    }
}

class LoginAction extends GeneralActionForMainUi{
    public LoginAction(){
        super(
            ResourceCenter.BTN_ICON_QCONNECT_16,
            ResourceCenter.BTN_ICON_QCONNECT_50,
            "View.MenuItem.connect",
            MenuAndBtnCenterForMainUi.FUNC_LOGIN
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        ConnectDialog dialog;
        int width,height;
        int fail_cnt = 0;
        boolean ok = false;
        GUIAdminOptUWS selUWS,uws;
       
SanBootView.log.info(getClass().getName(),"########### Entering login action. " ); 

        if( view.initor.lastUWS != null ){
            uws = view.initor.lastUWS;
System.out.println(" last swu server: "+ uws.getServerIp() );
        }else{
            uws = view.initor.adminOpt.getFirstUWS();
            if( uws != null ){
System.out.println(" new swu server: "+ uws.getServerIp() );
            }else{
System.out.println(" none swu server" );
            }
        }
        dialog = new ConnectDialog( 
            view,
            uws
        );
        width  = 400+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        height = 210+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint(width,height) );
        
        while( fail_cnt<3 && !ok ){     
            dialog.setVisible( true );
            
            Object[] ret = dialog.getValues();
            if( ret == null ) return;
            
            // 记录当前登录的UWS信息
            view.initor.serverIp = (String)ret[0];
            view.initor.user     = (String)ret[1];
            view.initor.passwd   = (String)ret[2];
            view.initor.port     = ((Integer)ret[3]).intValue();
            
            selUWS               = (GUIAdminOptUWS)ret[4];
            view.initor.txIp     = selUWS.getTxIP();
            view.initor.uws      = selUWS;
            
            //  与 iboot server 相 连
            ok = view.initor.realLogin( view.initor.serverIp,view.initor.port,view.initor.user,view.initor.passwd,0 );
            if( !ok ){
                fail_cnt++;
                JOptionPane.showMessageDialog( view, 
                    view.initor.getInitErrMsg()  
                );
            }
    	}// end of while
        
    	if( !ok ){
            view.initor.dealLoginFailure();
            return;
   	}else{
            view.initor.setLoginedFlag( true );
    	}
        
        view.mbCenter.setEnabledOnLogin( false );

        view.setTitle( SanBootView.res.getString( "View.frameTitle" )+"[ "+view.initor.serverIp+" ]" );

        // 开始真正地初始化 iboot server
        view.initor.initAppWithGraphy();
        
        // 增加登陆的新UWS到conf中
        view.initor.addUWSConf();
        view.initor.saveConf();
        view.mbCenter.setupConnectButtonStatus( view.initor.isLogined() );
        
        view.mbCenter.setEnabledOnMainMenu( MenuAndBtnCenterForMainUi.INDEX_LOGICAL_PROTECT, view.initor.mdb.isSupportMTPP() );
        view.mbCenter.setEnabledOnMainMenu( MenuAndBtnCenterForMainUi.INDEX_PHYSICAL_PROTECT,view.initor.mdb.isSupportCMDP() );
        
        UWSSockConnectManager conThread = new UWSSockConnectManager( view );
        conThread.start();

        Audit audit = view.audit.registerAuditRecord( 0, MenuAndBtnCenterForMainUi.FUNC_LOGIN );
        audit.setEventDesc( "Logon to system successfully.");
        view.audit.addAuditRecord( audit );

SanBootView.log.info(getClass().getName(),"########### End of login action. " ); 
    }
}

class LogoutAction extends GeneralActionForMainUi{
    public LogoutAction(Icon menuIcon,Icon btnIcon,String text,int fID){
        super( menuIcon,btnIcon,text,fID);
    }
    
    public LogoutAction(){
        super(
            ResourceCenter.BTN_ICON_DISCON_16, 
            ResourceCenter.BTN_ICON_DISCON_50,
            "View.MenuItem.disConnect",
            MenuAndBtnCenterForMainUi.FUNC_LOGOUT
        );
    }
    
    Runnable cleanTip = new Runnable(){
        public void run(){
            view.setConnectionTip( ResourceCenter.MENU_ICON_BLANK,"");      
        }
    };
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering logout action. " );

        Audit audit = view.audit.registerAuditRecord( 0, MenuAndBtnCenterForMainUi.FUNC_LOGOUT );
        audit.setEventDesc( "Logout from system successfully.");
        view.audit.addAuditRecord( audit );
        
        view.initor.mdb.logout();
        view.initor.mdb.closeStreamOnCurSocket();
   
        view.getTable().setModel( new DefaultTableModel() );
        view.setTreeRootNode( view.getTree() );
        view.removeRightPane();

        view.setCurNode( null );
        view.initor.setLoginedFlag( false );
        view.initor.setInitedFlag(  false );
        view.initor.mdb.targetSrvName = null;
        view.isSupportCMDP = false;
        view.initor.clearCurUWS();
        view.mbCenter.setupConnectButtonStatus( view.initor.isLogined() ); 
        view.setTitle( SanBootView.res.getString( "View.frameTitle" ) );
        
//        try{
        SwingUtilities.invokeLater( cleanTip );
//        }catch(Exception ex){}
SanBootView.log.info(getClass().getName(),"########### End of logout action. " ); 
    }
}

class PropertyAction extends GeneralActionForMainUi{
    public PropertyAction(Icon menuIcon,Icon btnIcon,String text,int fID){
        super( menuIcon,btnIcon,text,fID);
    }
    
    public PropertyAction(){
        super(
            ResourceCenter.SMALL_PROPERTY,
            ResourceCenter.MENU_ICON_BLANK,
            "SanBootView.MenuItem.property",
            MenuAndBtnCenterForMainUi.FUNC_PROPERTY
        );
    }

    private void addService( HashMap map,Vector serviceList ){
        int size = serviceList.size();
        for( int i=0; i<size; i++ ){
            Service service = (Service)serviceList.get(i);
            if( map.get( service.getServName() ) == null ){
                map.put( service.getServName(), service );
            }
        }
    }

    @Override public void doAction(ActionEvent evt){
        int width,height;
        
SanBootView.log.info(getClass().getName(),"########### Entering property action. " );         
        
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) {
SanBootView.log.info(getClass().getName(),"########### selObj is null.\nEnd of property action. " );
            return;
        }
        
        boolean isHost = ( selObj instanceof BootHost );
        if( !isHost ) {
SanBootView.log.info(getClass().getName(),"########### selObj is not host. \nEnd of property action. " );
            return;
        } else{
        
//        if( isHost ){
            BootHost host =(BootHost)selObj;
            if( host.isWinHost() ){
                HashMap map = new HashMap();
                if( host.isCMDPProtect() ){
                    // 获取服务器上保存的
                    boolean isOk = view.initor.mdb.getOSService( ResourceCenter.CLT_IP_CONF+"/"+host.getID() + ResourceCenter.CONF_SERVICE,ResourceCenter.CMD_TYPE_CMDP );
                    if( isOk ){
                        this.addService( map, view.initor.mdb.getOSService() );
                    }
                }

                HostPropertyDialog dialog = new HostPropertyDialog( view,host,map );
                width  = 525+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
                height = 400+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
                dialog.setSize( width,height );
                dialog.setLocation( view.getCenterPoint( width,height ) );
                dialog.setVisible( true );
            }else{
                UnixHostPropertyDialog dialog = new UnixHostPropertyDialog( view,host );
                width  = 530+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
                height = 400+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
                dialog.setSize( width,height );
                dialog.setLocation( view.getCenterPoint( width,height ) );
                dialog.setVisible( true );
            }
//        }else{
//SanBootView.log.warning( getClass().getName(),"unknown host type. \n########### End of property action. " );
//        }
        }
SanBootView.log.info(getClass().getName(),"########### End of property action. " );         
    }
}

class ExitAction extends GeneralActionForMainUi {
    public ExitAction(Icon menuIcon,Icon btnIcon,String text,int fID) {
        super(menuIcon,btnIcon,text,fID);
    }

    public ExitAction(){
        super(
          ResourceCenter.BTN_ICON_EXIT_16,
          ResourceCenter.BTN_ICON_EXIT_50, 
          "View.MenuItem.exit",
          MenuAndBtnCenterForMainUi.FUNC_EXIT
        );
    }

    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering exit action." );
        Audit audit = view.audit.registerAuditRecord( 0, MenuAndBtnCenterForMainUi.FUNC_LOGOUT );
        audit.setEventDesc( "Logout from system successfully.");
        view.audit.addAuditRecord( audit );

        view.dispose();
        System.exit(0);
SanBootView.log.info(getClass().getName(),"########### End of exit action." );  
    }
}

class InitHostAction extends GeneralActionForMainUi {      
    public InitHostAction(){
        super(
          ResourceCenter.BTN_ICON_INIT_16, 
          ResourceCenter.BTN_ICON_INIT_50,
          "View.MenuItem.init",
          MenuAndBtnCenterForMainUi.FUNC_INIT
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        boolean hasEnoughSpace = false;

SanBootView.log.info(getClass().getName(),"########### Entering window host's init process" );
        Object selObj = view.getSelectedObjFromSanBoot();
        BootHost host = null;
        if( (selObj != null) && (selObj instanceof BootHost)  ){
            host = (BootHost)selObj;
        }
        
        GetAllPoolThread thread = new GetAllPoolThread(
            view
        );
        view.startupProcessDiag( 
            SanBootView.res.getString("View.pdiagTitle.getPool1"),
            SanBootView.res.getString("View.pdiagTip.getPool1"),
            thread
        );
        
        ArrayList list = thread.getRet();
        if( list == null ){
            JOptionPane.showMessageDialog( view,
                SanBootView.res.getString("MenuAndBtnCenter.error.getPool") +
                   view.initor.mdb.getErrorMessage()
            );
            return;
        }else{
            int size = list.size();
            if( size <= 0 ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.noPool")
                );
                return;
            }else{
                for( int i=0; i<size; i++ ){
                    Pool pool = (Pool)list.get(i);
                    if( pool.getRealFreeSize() > 0 ){
                        hasEnoughSpace = true;
                        break;
                    }
                }
                
                // 存储池空间已被预分配完了，只能使用之前分配的target或者空闲卷了
                if( !hasEnoughSpace ){
                    int ret = JOptionPane.showConfirmDialog(
                        view,
                        SanBootView.res.getString("MenuAndBtnCenter.confirm26"),
                        SanBootView.res.getString("common.confirm"),  //"Confirm",
                        JOptionPane.OK_CANCEL_OPTION
                    );
                    if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
                        return;
                    }
                }
            }
        }
        
        InitBootHostWizardDialog dialog = new InitBootHostWizardDialog( view,host,hasEnoughSpace );
        int width  = 560+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 385+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of window host's init process action. " );         
    }
}

class InitNWinHostAction extends GeneralActionForMainUi {      
    public InitNWinHostAction(){
        super(
          ResourceCenter.BTN_ICON_INIT_16, 
          ResourceCenter.MENU_ICON_BLANK,
          "View.MenuItem.init",
          MenuAndBtnCenterForMainUi.FUNC_INIT_NWIN
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        boolean hasEnoughSpace = false;

SanBootView.log.info(getClass().getName(),"########### Entering Linux host's init action. " );         
        GetAllPoolThread thread = new GetAllPoolThread(
            view
        );
        view.startupProcessDiag( 
            SanBootView.res.getString("View.pdiagTitle.getPool1"),
            SanBootView.res.getString("View.pdiagTip.getPool1"),
            thread
        );
        
        ArrayList list = thread.getRet();
        if( list == null ){
            JOptionPane.showMessageDialog( view,
                SanBootView.res.getString("MenuAndBtnCenter.error.getPool") +
                   view.initor.mdb.getErrorMessage()
            );
            return;
        }else{
            int size = list.size();
            if( size <=0 ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.noPool")
                );
                return;
            }else{
                for( int i=0; i<size; i++ ){
                    Pool pool = (Pool)list.get(i);
                    if( pool.getRealFreeSize() >0 ){
                        hasEnoughSpace = true;
                        break;
                    }
                }
                
                // 存储池空间已被预分配完了，只能使用之前分配的target或者空闲卷了
                if( !hasEnoughSpace ){
                    int ret = JOptionPane.showConfirmDialog(
                        view,
                        SanBootView.res.getString("MenuAndBtnCenter.confirm26"),
                        SanBootView.res.getString("common.confirm"),  //"Confirm",
                        JOptionPane.OK_CANCEL_OPTION
                    );
                    if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
                        return;
                    }
                }

            }
        }
        
        Object selObj = view.getSelectedObjFromSanBoot();
        BootHost host = null;
        if( (selObj != null) && (selObj instanceof BootHost)  ){
            host = (BootHost)selObj;
        }
        
        InitNWinHostWizardDialog dialog = new InitNWinHostWizardDialog( view,host,hasEnoughSpace );
        int width  = 560+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 385+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of linux's host init action. " );         
    }
}        

class IBootForLinuxAction extends GeneralActionForMainUi {
    public IBootForLinuxAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.BTN_ICON_DR_50, 
            "View.MenuItem.ibootForLinux",
            MenuAndBtnCenterForMainUi.FUNC_IBOOT_WIZARD
        );
    }   
    
    @Override public void doAction(ActionEvent evt){
        boolean isOk;
        String targetSrvName,original_boot_MAC;
        
SanBootView.log.info( getClass().getName(),"########### Entering linux iboot wizard ......" );
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) {
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
            return;
        }
        
        boolean isBootHost = ( selObj instanceof BootHost );
        boolean isSrvAgent = ( selObj instanceof SourceAgent );        
        if( isBootHost ){
            BootHost host = (BootHost)selObj;
            
            if( host.isWinHost() ){
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.isWinHost")
                );
                return;
            }
            
            if( !host.isInited() ){
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.notInited")
                );
                return;
            }
            
            String status = isStartFromNet( host.getIP(),host.getPort() ); 
            if(  !status.toUpperCase().equals("UNKNOW") ){
                if( status.toUpperCase().equals("YES") ){
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                    JOptionPane.showMessageDialog( view,
                        SanBootView.res.getString("MenuAndBtnCenter.error.notChgVerWhenNetboot")
                    );
                    return;
                }
            }else{
                int ret = JOptionPane.showConfirmDialog(
                    view,
                    SanBootView.res.getString("MenuAndBtnCenter.confirm16"),
                    SanBootView.res.getString("common.confirm"),  //"Confirm",
                    JOptionPane.OK_CANCEL_OPTION
                );
                if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                    return;
                }
            }
            
            targetSrvName = view.initor.mdb.getHostName();
            if( targetSrvName.length() == 0 ){
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("InitBootHostWizardDialog.log.getHostNameFailed")
                );
                return;
            }
            
            isOk = view.initor.mdb.getUnixNetCardInfo( ResourceCenter.CLT_IP_CONF+"/"+host.getID()+".conf" );
            if( isOk ){
                original_boot_MAC = view.initor.mdb.getUnixBootMac();
                if( original_boot_MAC.length() == 0 ){
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                    JOptionPane.showMessageDialog(view,
                        SanBootView.res.getString("FailoverWizardDialog.error.notFoundBootMac")
                    );
                    return;
                 }
            }else{
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("FailoverWizardDialog.error.notFoundBootMac")
                );
                return;
            }
            
            isOk = view.initor.mdb.getUnixPart1( ResourceCenter.CLT_IP_CONF + "/" + host.getID() + ResourceCenter.CONF_MP );
            if( !isOk ){
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_UNIX_PART )+" : "+
                        view.initor.mdb.getErrorMessage()
                );
                return;
            }
            Vector partList = view.initor.mdb.getUnixSysPart();
            
            IbootForLinuxWizardDialog dialog = new IbootForLinuxWizardDialog( view,host,targetSrvName,partList,original_boot_MAC );  
            int width  = 560+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 400+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE; // 380;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
        }else if( isSrvAgent ){
            boolean mustCheckDiskExist = false;
            SourceAgent host = (SourceAgent)selObj;
            BrowserTreeNode fNode = host.getFatherNode();
            Object fObj = fNode.getUserObject();
            if( fObj instanceof ChiefRollbackHost ){
                mustCheckDiskExist = true;
            }

            if( host.isWinHost() ){
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.isWinHost")
                );
                return;
            }
            
            int ret = JOptionPane.showConfirmDialog(
                view,
                SanBootView.res.getString("MenuAndBtnCenter.confirm6"),
                SanBootView.res.getString("common.confirm"),  //"Confirm",
                JOptionPane.OK_CANCEL_OPTION
            );
            if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                return;
            }
            
            targetSrvName = view.initor.mdb.getHostName();
            if( targetSrvName.length() == 0 ){
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("InitBootHostWizardDialog.log.getHostNameFailed")
                );
                return;
            }
            
            isOk = view.initor.mdb.getUnixNetCardInfo( ResourceCenter.CLT_IP_CONF + "/" + ResourceCenter.PREFIX_SRC_AGNT + host.getSrc_agnt_id() + ".conf" );
            if( isOk ){
                original_boot_MAC = view.initor.mdb.getUnixBootMac();
                if( original_boot_MAC.length() == 0 ){
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                    JOptionPane.showMessageDialog(view,
                        SanBootView.res.getString("FailoverWizardDialog.error.notFoundBootMac")
                    );
                    return;
                 }
            }else{
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("FailoverWizardDialog.error.notFoundBootMac")
                );
                return;
            }
            
            isOk = view.initor.mdb.getUnixPart1( ResourceCenter.CLT_IP_CONF + "/" + ResourceCenter.PREFIX_SRC_AGNT + host.getSrc_agnt_id() + ResourceCenter.CONF_MP );
            if( !isOk ){
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_UNIX_PART )+" : "+
                        view.initor.mdb.getErrorMessage()
                );
                return;
            }
            Vector partList = view.initor.mdb.getUnixSysPart();
            
            IbootForLinuxSourceAgentWizardDialog dialog = new IbootForLinuxSourceAgentWizardDialog( view,host,targetSrvName,partList,original_boot_MAC,mustCheckDiskExist );
            int width  = 560+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 400+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE; // 380;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
        }else{
SanBootView.log.info(getClass().getName(),"########### End of linux iboot wizard. " );
            return;
        }
    }
    
    private String isStartFromNet( String ip,int port ){
        /// 判断是否从 network 启动
        boolean isOk = view.initor.mdb.isStartupfromNetBoot( ip,port );
        if( isOk ){
            String ret = view.initor.mdb.isStartupFromNetBoot()?"yes":"no";
            return ret;
        }else{
            return "unknow";
        }
    }
}
       
class SelectBootVerAction extends GeneralActionForMainUi {
    public SelectBootVerAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.BTN_ICON_DR_50, 
            "View.MenuItem.selBootVer",
            MenuAndBtnCenterForMainUi.FUNC_SELECT_BOOT_VER
        );
    }   
    
    @Override public void doAction(ActionEvent evt){
        boolean isOk;
        String bootMac,targetSrvName;
        
SanBootView.log.info( getClass().getName(),"########### Entering select boot version action." );         
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( selObj instanceof BootHost ){
            BootHost host = (BootHost)selObj;
            
            if( host.isWinHost() ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.isWinHost")
                );
                return;
            }
            
            if( !host.isInited() ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.notInited")
                );
                return;
            }
            
            String status = isStartFromNet( host.getIP(),host.getPort() ); 
            if(  !status.toUpperCase().equals("UNKNOW") ){
                if( status.toUpperCase().equals("YES") ){
                    JOptionPane.showMessageDialog( view,
                        SanBootView.res.getString("MenuAndBtnCenter.error.notChgVerWhenNetboot")
                    );
                    return;
                }
            }else{
                int ret = JOptionPane.showConfirmDialog(
                    view,
                    SanBootView.res.getString("MenuAndBtnCenter.confirm16"),
                    SanBootView.res.getString("common.confirm"),  //"Confirm",
                    JOptionPane.OK_CANCEL_OPTION
                );
                if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
                    return;
                }
            }
            
            isOk = view.initor.mdb.getUnixNetCardInfo( ResourceCenter.CLT_IP_CONF+"/"+host.getID()+ ResourceCenter.CONF_IP );
            if( isOk ){
                bootMac = view.initor.mdb.getUnixBootMac();
                 if( bootMac.length() == 0 ){
                    JOptionPane.showMessageDialog(view,
                        SanBootView.res.getString("FailoverWizardDialog.error.notFoundBootMac")
                    );
                    return;
                 }
            }else{
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("FailoverWizardDialog.error.notFoundBootMac")
                );
                return;
            }
            
            targetSrvName = view.initor.mdb.getHostName();
            if( targetSrvName.length() == 0 ){
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("InitBootHostWizardDialog.log.getHostNameFailed")
                );
                return;
            }
            
            isOk = view.initor.mdb.getUnixPart1( ResourceCenter.CLT_IP_CONF + "/" + host.getID() + ResourceCenter.CONF_MP );
            if( !isOk ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_UNIX_PART )+" : "+
                        view.initor.mdb.getErrorMessage()
                );
                return;
            }
            Vector partList = view.initor.mdb.getUnixSysPart();
            
            ArrayList<BindOfDiskLabelAndTid> oldBootVerList = view.getUnixBootVer( host.getID() );
            
            // 正在准备快照版本
            GetUnixRstVer getRstVer = new GetUnixRstVer( view,host.getID() );
            view.startupProcessDiag( 
                SanBootView.res.getString("View.pdiagTitle.getSnapVer"),
                SanBootView.res.getString("View.pdiagTip.getSnapVer"),
                getRstVer
            );    
            Vector bindList = getRstVer.getBindList();
            
            SelectBootVerDialog dialog = new SelectBootVerDialog( view,bindList,oldBootVerList,bootMac,targetSrvName,host,partList );  
            int width  = 525+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 370+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
        }
//        else if( selObj instanceof SourceAgent ){
            
            //////////////////////////////////////////////////////////////////////////
            //
            // ��对于SourceAgent来说，直接在目的端UWS上选择网启版本没有意义。因为该操作是对���Ƕ�
            // Դ�源端uws上的主机设计的,而目的端UWS上不存在源端UWS的环境.
            //                    
            //                          2009-09-23,zjj
            //
            //////////////////////////////////////////////////////////////////////////
                        
/*            
            SourceAgent sa = (SourceAgent)selObj;
            
            if( sa.isWinHost() ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.isWinHost")
                );
                return;
            }
            
            isOk = view.initor.mdb.getUnixNetCardInfo( ResourceCenter.CLT_IP_CONF+"/" + ResourceCenter.PREFIX_SRC_AGNT + sa.getSrc_agnt_id() + ResourceCenter.CONF_IP );
            if( isOk ){
                bootMac = view.initor.mdb.getUnixBootMac();
                 if( bootMac.equals("") ){
                    JOptionPane.showMessageDialog(view,
                        SanBootView.res.getString("FailoverWizardDialog.error.notFoundBootMac")
                    );
                    return;
                 }
            }else{
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("FailoverWizardDialog.error.notFoundBootMac")
                );
                return;
            }
            
            targetSrvName = view.initor.mdb.getHostName();
            if( targetSrvName.equals("") ){
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("InitBootHostWizardDialog.log.getHostNameFailed")
                );
                return;
            }
            
            isOk = view.initor.mdb.getUnixPart1( ResourceCenter.CLT_IP_CONF + "/" + ResourceCenter.PREFIX_SRC_AGNT + sa.getSrc_agnt_id() + ResourceCenter.CONF_MP );
            if( !isOk ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_UNIX_PART )+" : "+
                        view.initor.mdb.getErrorMessage()
                );
                return;
            }
            Vector partList = view.initor.mdb.getUnixSysPart();
                
            // 正在准备快照版本
            GetUnixRstVerForSrcAgent getRstVer = new GetUnixRstVerForSrcAgent( view,sa.getSrc_agnt_id() );
            view.startupProcessDiag( 
                view,
                SanBootView.res.getString("View.pdiagTitle.getSnapVer"),
                SanBootView.res.getString("View.pdiagTip.getSnapVer"),
                getRstVer
            );
            if( getRstVer.isNoVersion() ){
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.noVersion")
                );
                return;
            }            
            Vector bindList = getRstVer.getBindList();
            
            SelectBootVerForSrcAgntDialog dialog = new SelectBootVerForSrcAgntDialog( view,bindList,null,bootMac,targetSrvName,sa,partList );  
            int width  = 525+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 370+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
 */            
//        }
SanBootView.log.info(getClass().getName(),"########### End of select boot version action. " );  
    }
    
    private String isStartFromNet( String ip,int port ){
        // 判断是否从 network 启动
        boolean isOk = view.initor.mdb.isStartupfromNetBoot( ip,port );
        if( isOk ){
            String ret = view.initor.mdb.isStartupFromNetBoot()?"yes":"no";
            return ret;
        }else{
            return "unknow";
        }
    }
}

class FailoverAction extends GeneralActionForMainUi {
    boolean isSwitchDisk = false;

    public FailoverAction(){
       this( false );
    }

    public FailoverAction( boolean isSwitchDisk ){
        super(
            ResourceCenter.BTN_ICON_DR_16,
            ResourceCenter.BTN_ICON_DR_50,
            "View.MenuItem.failover",
            MenuAndBtnCenterForMainUi.FUNC_FAILOVER
        );
        this.isSwitchDisk = isSwitchDisk;
    }

    @Override public void doAction(ActionEvent evt){
        if( this.isSwitchDisk ){
SanBootView.log.info(getClass().getName(),"########### Entering switch-to-netdisk action. " );
        }else{
SanBootView.log.info(getClass().getName(),"########### Entering failover action. " );
        }
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        boolean isBootHost = ( selObj instanceof BootHost );
        boolean isSrvAgent = ( selObj instanceof SourceAgent );
        if( isBootHost ){
SanBootView.log.info(getClass().getName(),"selected host is boothost. " );
            BootHost host = (BootHost)selObj;
            if( !host.isWinHost() ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.notWinHost")
                );
                return;
            }
            
            if( !host.isInited() ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.notInited")
                );
                return;
            }

            int ret;

            if( this.isSwitchDisk ){
                // 检查是否有正在进行的数据复制任务，若有则报告错误。用户取消掉该任务后，方可进行该操作。
                if( !this.checkTask( host ) ){
                    JOptionPane.showMessageDialog( view,
                        SanBootView.res.getString("MenuAndBtnCenter.error.TaskIsRunning")
                    );
                    return;
                }

                ret = JOptionPane.showConfirmDialog(
                        view,
                        SanBootView.res.getString("MenuAndBtnCenter.confirm27"),
                        SanBootView.res.getString("common.confirm"),  //"Confirm",
                        JOptionPane.OK_CANCEL_OPTION
                    );
                    if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
SanBootView.log.info(getClass().getName(),"cancel this operation! \n########### End of switch-to-netdisk action. " );
                        return;
                    }
            }else{
                ret = JOptionPane.showConfirmDialog(
                    view,
                    SanBootView.res.getString("MenuAndBtnCenter.confirm6"),
                    SanBootView.res.getString("common.confirm"),  //"Confirm",
                    JOptionPane.OK_CANCEL_OPTION
                );
                if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
SanBootView.log.info(getClass().getName(),"cancel this operation! \n########### End of failover action. " );
                    return;
                }
            }
            
            FailoverWizardDialog dialog = new FailoverWizardDialog( view,host,isSwitchDisk );
            int width  = 560+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 400+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE; // 380;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
        }else if( isSrvAgent ){
SanBootView.log.info(getClass().getName(),"selected host is srcagnt. " );
            boolean mustCheckDiskExist =false;
            SourceAgent host = (SourceAgent)selObj;
            BrowserTreeNode fNode = host.getFatherNode();
            Object fObj = fNode.getUserObject();
            if( fObj instanceof ChiefRollbackHost ){
                mustCheckDiskExist = true;
            }

            host.getTreeNode();
            if( !host.isWinHost() ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.notWinHost")
                );
                return;
            }
            
            int ret = JOptionPane.showConfirmDialog(
                view,
                SanBootView.res.getString("MenuAndBtnCenter.confirm6"),
                SanBootView.res.getString("common.confirm"),  //"Confirm",
                JOptionPane.OK_CANCEL_OPTION
            );
            if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
SanBootView.log.info(getClass().getName(),"cancel this operation! \n########### End of failover action. " );
                return;
            }
            
            FailoverForSrcAgntWizardDialog dialog = new FailoverForSrcAgntWizardDialog( view,host,mustCheckDiskExist );
            int width  = 560+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 400+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE; // 380;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );  
        }else{
            return;
        }
SanBootView.log.info(getClass().getName(),"########### End of failover action. " );         
    }

    private boolean checkTask( BootHost host ){
        BackupClient bkClnt = view.initor.mdb.getBkClntOnUUID( host.getUUID()   );
        if( bkClnt == null ) return true;

        ArrayList profList = view.initor.mdb.getAllProfileOnClntID( bkClnt.getID() );
        if( profList.size() <= 0 ){
            return true;
        }
        
        boolean isOk = view.initor.mdb.updateTaskList();
        if( isOk ){
            ArrayList taskList = view.initor.mdb.getAllTaskList( 0 );
            for( int i=0; i<taskList.size(); i++ ){
                BakTask task = (BakTask)taskList.get(i);
                if( this.isContained( task.getProfileName(), profList ) ){
SanBootView.log.error( getClass().getName(), "This task still is running: " + task.getProfileName() +".  So can't begin to switch-net-disk.");
                    return false;
                }
            }
            return true;
        }else{
            return true;
        }
    }

    private boolean isContained( String profName,ArrayList profList ){
        int size = profList.size();
        for( int i=0; i<size; i++ ){
            UniProfile prof =(UniProfile)profList.get(i);
            if( prof.toString().equals( profName ) ){
                return true;
            }
        }
        return false;
    }
}

class FailbackAction extends GeneralActionForMainUi {
    private boolean isSwitchDisk = false;
    
    public FailbackAction(){
        this( false );
    }

    public FailbackAction( boolean isSwitchDisk ){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK,
            "View.MenuItem.failback",
            MenuAndBtnCenterForMainUi.FUNC_FAILBACK
        );
        this.isSwitchDisk = isSwitchDisk;
    }
    
    @Override public void doAction(ActionEvent evt){
        BootVerList bootVer;
        
        if( this.isSwitchDisk ){
SanBootView.log.info(getClass().getName(),"########### Entering switch-to-localdisk action." );
        }else{
SanBootView.log.info(getClass().getName(),"########### Entering failback action. " );
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
            
            if( !host.isInited() ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.notInited")
                );
                return;
            }
            
            if( !isStartFromLocal( host.getIP(),host.getPort() ) ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.notStartFromLocal")
                );
                return;
            }
            
            int ret;
            if( this.isSwitchDisk ){
                ret = JOptionPane.showConfirmDialog(
                    view,
                    SanBootView.res.getString("MenuAndBtnCenter.confirm28"),
                    SanBootView.res.getString("common.confirm"),  //"Confirm",
                    JOptionPane.OK_CANCEL_OPTION
                );
                if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
                    return;
                }
            }else{
                ret = JOptionPane.showConfirmDialog(
                    view,
                    SanBootView.res.getString("MenuAndBtnCenter.confirm5"),
                    SanBootView.res.getString("common.confirm"),  //"Confirm",
                    JOptionPane.OK_CANCEL_OPTION
                );
                if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
                    return;
                }
            }
            
            bootVer= view.getBootVer( host.getID() );
            
            DestAgent da = new DestAgent(
                -1,
                host.getIP(),
                host.getPort(),
                host.getOS(), 
                "",  // mac
                "",  // desc
                BootHost.PROTECT_TYPE_MTPP
            );
            da.setSrc_Agnt_id( host.getID() );
            da.setHostType( DestAgent.TYPE_ORI_HOST );
            da.setStopAllServFlag( host.isStopAllBaseServ() );
            
            FailbackWizardDialog dialog = new FailbackWizardDialog( view,da,bootVer,isSwitchDisk );
            int width  = 525+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 370+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
            
            if( this.isSwitchDisk ){
SanBootView.log.info(getClass().getName(),"########### End of switch-to-localdisk action." );
            }else{
SanBootView.log.info(getClass().getName(),"########### End of phy-failback action." );
            }
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
            
            if( !isStartFromLocal( da.getIP(),da.getPort() ) ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.notStartFromLocal")
                );
                return;
            }
            
            int ret = JOptionPane.showConfirmDialog(
                view,
                SanBootView.res.getString("MenuAndBtnCenter.confirm5"),
                SanBootView.res.getString("common.confirm"),  //"Confirm",
                JOptionPane.OK_CANCEL_OPTION
            );
            if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
                return;
            }
            
            bootVer= view.getBootVerForDestAgent( da.getID() );
            
            if( hostObj instanceof BootHost ){
                BootHost bh = (BootHost)hostObj;
                da.setSrc_Agnt_id( bh.getID() );
                da.setHostType( DestAgent.TYPE_DST_AGNT );
                da.setStopAllServFlag( bh.isStopAllBaseServ() );
            }else{
                SourceAgent sa = (SourceAgent)hostObj;
                da.setSrc_Agnt_id( sa.getSrc_agnt_id() );
                da.setHostType( DestAgent.TYPE_SRC_AGNT );
                da.setRealBootHostFlag( false );
            }
            
            FailbackWizardDialog dialog = new FailbackWizardDialog( view,da,bootVer,isSwitchDisk );
            int width  = 525+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 370+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of failback action. " );             
        }
    }
    
    private boolean isStartFromLocal( String ip,int port ){
        // 判断是否从 network 启动��
        boolean isOk = view.initor.mdb.isStartupfromSAN( ip,port,"C" );
        if( isOk ){
            return !view.initor.mdb.isStartupFromSAN();
        }else{
            return false;
        }
    }
}

class RollbackVolAction extends GeneralActionForMainUi {
    public RollbackVolAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK,
            "View.MenuItem.rollback",
            MenuAndBtnCenterForMainUi.FUNC_ROLLBACK_VOL
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering rollback mirrored vol action. " );         
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( !(selObj instanceof MirrorDiskInfo )  ) return;
        MirrorDiskInfo mdi =(MirrorDiskInfo )selObj;

        int ret = JOptionPane.showConfirmDialog(
            view,
            SanBootView.res.getString("MenuAndBtnCenter.confirm19"),
            SanBootView.res.getString("common.confirm"),  //"Confirm",
            JOptionPane.OK_CANCEL_OPTION
        );        
        if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
            return;
        }
              
        RollbackVolThread thread = new RollbackVolThread( view,mdi );
        view.startupProcessDiag(
            SanBootView.res.getString("View.pdiagTitle.rollback"),
            SanBootView.res.getString("View.pdiagTip.rollback"),
            thread
        );
SanBootView.log.info(getClass().getName(),"########### End of rollback mirrored vol action. " );
    }
}

class RollbackAction extends GeneralActionForMainUi {
    public RollbackAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK,
            "View.MenuItem.rollback",
            MenuAndBtnCenterForMainUi.FUNC_ROLLBACK
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering rollback win host action. " );         
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( !(selObj instanceof SourceAgent)  ) return;
        SourceAgent sa =(SourceAgent )selObj;
        
        int ret = JOptionPane.showConfirmDialog(
            view,
            SanBootView.res.getString("MenuAndBtnCenter.confirm19"),
            SanBootView.res.getString("common.confirm"),  //"Confirm",
            JOptionPane.OK_CANCEL_OPTION
        );        
        if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
            return;
        }
              
        RollbackThread thread = new RollbackThread( view,sa );
        view.startupProcessDiag(
            SanBootView.res.getString("View.pdiagTitle.rollback"),
            SanBootView.res.getString("View.pdiagTip.rollback"),
            thread
        );
SanBootView.log.info(getClass().getName(),"########### End of rollback win host action. " );
    }
}

class RollbackForNWinAction extends GeneralActionForMainUi {
    public RollbackForNWinAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK,
            "View.MenuItem.rollback",
            MenuAndBtnCenterForMainUi.FUNC_ROLLBACK_NWIN
        );
    }
    
    @Override public void doAction(ActionEvent evt){    
SanBootView.log.info(getClass().getName(),"########### Entering rollback linux host action. " );         
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( !(selObj instanceof SourceAgent)  ) return;
        SourceAgent sa =(SourceAgent )selObj;
        
        int ret = JOptionPane.showConfirmDialog(
            view,
            SanBootView.res.getString("MenuAndBtnCenter.confirm19"),
            SanBootView.res.getString("common.confirm"),  //"Confirm",
            JOptionPane.OK_CANCEL_OPTION
        );        
        if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
            return;
        }
                
        RollbackThread thread = new RollbackThread( view,sa );
        view.startupProcessDiag(
            SanBootView.res.getString("View.pdiagTitle.rollback"),
            SanBootView.res.getString("View.pdiagTip.rollback"),
            thread
        );
SanBootView.log.info(getClass().getName(),"########### End of rollback linux host action. " );          
    }
}

class RestoreOriginalData extends GeneralActionForMainUi {
    private boolean isSwitchDisk = false;

    public RestoreOriginalData(){
        this( false );
    }
    
    public RestoreOriginalData( boolean isSwitchDisk ){
        super(
            ResourceCenter.BTN_ICON_RD_16,
            ResourceCenter.BTN_ICON_RD_50,
            "View.MenuItem.rstOriData",
            MenuAndBtnCenterForMainUi.FUNC_RESTORE_DISK
        );
        this.isSwitchDisk = isSwitchDisk;
    }
    
    @Override public void doAction(ActionEvent evt){
        boolean isOk,needModUUID = false;

        if( this.isSwitchDisk ){
SanBootView.log.info(getClass().getName(),"########### Entering switch-restore original disk action. " );
        }else{
SanBootView.log.info(getClass().getName(),"########### Entering restore original disk action. " );
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
                isOk = view.initor.mdb.isStartupfromSAN( host.getIP(),host.getPort(),"C" );
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
            
            String windir = view.initor.mdb.getWinDir( host.getIP(),host.getPort() );
SanBootView.log.info(getClass().getName(), "windir: " + windir );
            if( windir.length() == 0 ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_WINDIR )+
                    ": "+
                    view.initor.mdb.getErrorMessage()
                );
                return;
            }
           
            DestAgent da = new DestAgent(
                -1,
                host.getIP(),
                host.getPort(),
                host.getOS(), 
                "", // mac
                "",  // desc
                BootHost.PROTECT_TYPE_MTPP
            );
            da.setHostType( DestAgent.TYPE_ORI_HOST );
            da.setSrc_Agnt_id( host.getID() );
            da.setHostUUID( host.getUUID() );
            da.setBootMode( host.getBootMode() );
            da.setMachine( host.getMachine() );
            
            RestoreOriginalDiskWizardDialog dialog = new RestoreOriginalDiskWizardDialog( 
                    windir,view,da,targetSrvName,needModUUID,isSwitchDisk );
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
            isOk = view.initor.mdb.isStartupfromSAN( da.getDst_agent_ip(),da.getDst_agent_port(), "C" );
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
            
            String windir = view.initor.mdb.getWinDir( da.getDst_agent_ip(),da.getDst_agent_port() );
SanBootView.log.info(getClass().getName(), "windir: " + windir );
            if( windir.length() == 0 ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_WINDIR )+
                    ": "+
                    view.initor.mdb.getErrorMessage()
                );
                return;
            }
            
            if( hostObj instanceof BootHost ){
                BootHost bh = (BootHost)hostObj;
                da.setSrc_Agnt_id( bh.getID() );
                da.setHostType( DestAgent.TYPE_DST_AGNT );
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
                da.setHostType( DestAgent.TYPE_SRC_AGNT );
                da.setRealBootHostFlag( false );
                da.setBootMode( sa.getSrc_agnt_boot_mode() );
                da.setMachine( sa.getMachine() );
                needModUUID = true;
            }
            
            RestoreOriginalDiskWizardDialog dialog = new RestoreOriginalDiskWizardDialog(
                    windir,view,da,targetSrvName,needModUUID,isSwitchDisk );
            int width  = 525+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 370+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
        }
        
        if( !this.isSwitchDisk ){
SanBootView.log.info(getClass().getName(),"########### End of restore original disk action." );
        }else{
SanBootView.log.info(getClass().getName(),"########### End of switch-restore original disk action." );
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

class RestoreOriginalDataForNWin extends GeneralActionForMainUi {
    public RestoreOriginalDataForNWin(){
        super(
            ResourceCenter.BTN_ICON_RD_16,
            ResourceCenter.BTN_ICON_RD_50,
            "View.MenuItem.rstOriData",
            MenuAndBtnCenterForMainUi.FUNC_REST_DISK_NWIN
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        boolean isOk,needModUUID = false;
        ArrayList lvmInfo;
SanBootView.log.info(getClass().getName(),"########### Entering linux host's restore original disk action. " );

        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
         
        if( selObj instanceof BootHost ){
            BootHost host = (BootHost)selObj;
            
            if( host.isWinHost() ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.isWinHost")
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
            
            // 判断是否从 network 启动
            isOk = view.initor.mdb.isStartupfromNetBoot( host.getIP(),host.getPort() );
            if( isOk ){
                if( !view.initor.mdb.isStartupFromNetBoot() ){
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
            
            isOk = view.initor.mdb.getLVMInfoFromPXELinux( ResourceCenter.CLT_IP_CONF+"/"+host.getID() + ResourceCenter.CONF_LVMINFO );
            if( isOk ){
                lvmInfo = view.initor.mdb.getLVMInfo();
                if( lvmInfo == null || lvmInfo.size() <= 0 ){
SanBootView.log.error( getClass().getName()," #### lvm info from xxx-lvm.conf is empty, exit");                    
                    JOptionPane.showMessageDialog( view,
                        SanBootView.res.getString("MenuAndBtnCenter.error.getLVMInfo")
                    );
                    return;
                }
            }else{
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.getLVMInfo")
                );
                return;
            }
System.out.println(" lvm info: \n" + view.initor.mdb.getLVMInfoString() );

            DestAgent da = new DestAgent(
                -1,
                host.getIP(),
                host.getPort(),
                host.getOS(), 
                "", // mac
                "",  // desc
                0
            );
            da.setHostType( DestAgent.TYPE_ORI_HOST );
            da.setSrc_Agnt_id( host.getID() );
            da.setHostUUID( host.getUUID() );
            
            RestoreOrigiDiskForUnixWizardDialog dialog = new RestoreOrigiDiskForUnixWizardDialog( view,da,targetSrvName,lvmInfo,needModUUID );
            int width  = 525+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 370+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
        }else { // DestAgent
            DestAgent da = (DestAgent)selObj;
            BrowserTreeNode chiefNBHNode = da.getFatherNode();
            ChiefNetBootHost chiefNBH = (ChiefNetBootHost)chiefNBHNode.getUserObject();
            BrowserTreeNode hostNode = chiefNBH.getFatherNode();
            Object hostObj = hostNode.getUserObject();
            
System.out.println(" da os: "+ da.getComment() );            
            if( da.isWinHost() ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.isWinHost")
                );
                return;
            }
            
            // 判断是否从 network 启动����
            isOk = view.initor.mdb.isStartupfromNetBoot( da.getDst_agent_ip(),da.getDst_agent_port() );
            if( isOk ){
                if( !view.initor.mdb.isStartupFromNetBoot() ){
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
            
            String tftpRootPath = view.initor.mdb.getTftpRootPath();
            if( tftpRootPath.length() == 0 ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("EditProfileDialog.error.tftp")
                );
                return;
            }
                        
            isOk = view.initor.mdb.listDir( tftpRootPath+"/"+da.getDst_agent_mac().toLowerCase()+"/" );
            if( !isOk ){
                JOptionPane.showMessageDialog(view, 
                    SanBootView.res.getString("MenuAndBtnCenter.error.getLVMInfo") 
                );
                return;
            }else{
                ArrayList fList = view.initor.mdb.getFileList();
                if( fList.size() == 0 ){
 SanBootView.log.error( getClass().getName(),"no files found in specified dir." );                   
                    JOptionPane.showMessageDialog(view, 
                        SanBootView.res.getString("MenuAndBtnCenter.error.getLVMInfo")
                    );
                    return;
                }else{
                    // get iBootInfoxxx file
                    fList = getFileList( fList );
                    if( fList.size() == 0 ){
SanBootView.log.error( getClass().getName(),"not to find iBootInfo-xxx file" );                             
                        JOptionPane.showMessageDialog(view, 
                            SanBootView.res.getString("MenuAndBtnCenter.error.getLVMInfo")
                        );
                        return;
                    }else{
                        String conf = ((UnixFileObj)fList.get(0)).getAbsPath();                        
                        isOk = view.initor.mdb.getLVMInfoFromPXELinux( conf );                        
                        if( isOk ){
                            lvmInfo = view.initor.mdb.getLVMInfo();
                            if( lvmInfo == null || lvmInfo.size() <= 0 ){
SanBootView.log.error( getClass().getName()," #### lvm info from xxx-lvm.conf is empty, exit");                    
                                JOptionPane.showMessageDialog( view,
                                    SanBootView.res.getString("MenuAndBtnCenter.error.getLVMInfo")
                                );
                                return;
                            }
                        }else{
                            JOptionPane.showMessageDialog( view,
                                SanBootView.res.getString("MenuAndBtnCenter.error.getLVMInfo")
                            );
                            return;
                        }
System.out.println(" lvm info: \n" + view.initor.mdb.getLVMInfoString() );
                     }
                }
            }
            
            if( hostObj instanceof BootHost ){
                BootHost bh = (BootHost)hostObj;
                da.setSrc_Agnt_id( bh.getID() );
                da.setHostType( DestAgent.TYPE_DST_AGNT );
                da.setHostUUID( bh.getUUID() );

                // 获取bh的网卡信息
                isOk = view.initor.mdb.getUnixNetCardFromSrv(  ResourceCenter.CLT_IP_CONF + "/" + bh.getID() + ResourceCenter.CONF_IP );
                if( !isOk ){
                    JOptionPane.showMessageDialog(view,
                        SanBootView.res.getString("InitBootHostWizardDialog.log.getIpInfo")+" "+
                        SanBootView.res.getString("common.failed")
                    );
                    return;
                }else{
                    ArrayList netCardList = view.initor.mdb.getUnixNetCardFromSrv();
                    if( !this.isContainedInSet( netCardList, da.getDst_agent_mac() ) ){
                        needModUUID = true;
                    }
                }
            }else{
                SourceAgent sa = (SourceAgent)hostObj;
                da.setSrc_Agnt_id( sa.getSrc_agnt_id() );
                da.setHostType( DestAgent.TYPE_SRC_AGNT );
                da.setRealBootHostFlag( false );
                needModUUID = true;
            }
            
            RestoreOrigiDiskForUnixWizardDialog dialog = new RestoreOrigiDiskForUnixWizardDialog( view,da,targetSrvName,lvmInfo,needModUUID );
            int width  = 525+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 370+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
        }
SanBootView.log.info(getClass().getName(),"########### End of linux host's restore original disk action. " );         
    }
    
    public ArrayList getFileList( ArrayList fileList ){
        String name;
        String[] line;
        
        ArrayList ret = new ArrayList();
        
        int size = fileList.size();
        for( int i=0;i<size;i++ ){
            UnixFileObj one = ( UnixFileObj )fileList.get(i);            
            name = one.getName(); 
            if( name == null || name.length() == 0 )
                continue;
            
            if( !one.isDir() ){
System.out.println(" file name: " + name ); 
                line = Pattern.compile("\\.").split( name );
                if( line.length > 2 ){
                    String end = line[line.length-1];
System.out.println(" end: "+ end );
                    if( line[0].equals("iBootInfo") ){
                        if( end.equals("NONE") || end.equals("LVM1") || end.equals("LVM2") ){
                            ret.add( one );
                        }
                    }
                }
            }
        }
        
        return ret;
    }

    private boolean isContainedInSet( ArrayList<UnixNetCard> list,String mac ){
        int size = list.size();
        for( int i=0; i<size; i++ ){
            UnixNetCard nc = list.get(i);
            String smac1 = DhcpClientInfo.getSimpleMac( nc.mac );
            String smac2 = DhcpClientInfo.getSimpleMac( mac );
            if( smac1.equals( smac2) ){
                return true;
            }
        }
        return false;
    }
}
        
class CrtVolAction extends GeneralActionForMainUi{
    public CrtVolAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.crtVol",
            MenuAndBtnCenterForMainUi.FUNC_CRT_VOL
        );
    }
    
    @Override public void doAction(ActionEvent evt){   
SanBootView.log.info(getClass().getName(),"########### Entering create vol action. " );         
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        boolean isChiefOrphVol = ( selObj instanceof ChiefOrphanVolume );
        boolean isBootHost = ( selObj instanceof BootHost );
        
        if( !isChiefOrphVol && !isBootHost ){
            return;
        }
        
        ArrayList poolList = view.initor.mdb.getPoolList();
        
        CreateOrphanVolDialog  dialog = new CreateOrphanVolDialog( view,poolList ); 
        int width = 330+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 180+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
        
        Object[] ret = dialog.getValues();
        if( ret == null || ret.length <= 0 ) return;

        Audit audit = view.audit.registerAuditRecord( 0, MenuAndBtnCenterForMainUi.FUNC_CRT_VOL );

        String name = (String)ret[0];
        int bksize = ((Integer)ret[1]).intValue();
        int bknum = ((Integer)ret[2]).intValue();
        int pid=((Integer)ret[3]).intValue();
        
        boolean isOk = view.initor.mdb.addOrphVol( name, bksize,bknum,pid );
        if( isOk ){
            audit.setEventDesc( "Create volume: " + name + " successfully." );
            view.audit.addAuditRecord( audit );

            if( isChiefOrphVol ){
                ChiefOrphanVolume chiefOrphVol = (ChiefOrphanVolume)selObj;
                BrowserTreeNode chiefOrphVolNode = chiefOrphVol.getTreeNode();
               
                Volume newVolume = view.initor.mdb.getCrtVolume();
                if( newVolume != null ){
                    BrowserTreeNode newVolNode = new BrowserTreeNode( newVolume,false );
                    newVolume.setTreeNode( newVolNode );
                    newVolume.setFatherNode( chiefOrphVolNode );
                    view.insertNodeToTree( chiefOrphVolNode,newVolNode );
                }
                
                // 显示点击 chiefOrphVolNode 后的右边tabpane中的内容
                view.setCurNode( chiefOrphVolNode );
                view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                ProcessEventOnChiefOrphanVol peOnChiefOrphanVol = new ProcessEventOnChiefOrphanVol( view );
                TreePath path = new TreePath( chiefOrphVolNode.getPath() );
                peOnChiefOrphanVol.processTreeSelection( path );
                peOnChiefOrphanVol.controlMenuAndBtnForTreeEvent();
                view.getTree().setSelectionPath( path );
                view.getTree().requestFocus();
            }else{
            }
        }else{
            audit.setEventDesc( "Failed to create volume: " + name );
            view.audit.addAuditRecord( audit );

            view.showError1(
                ResourceCenter.CMD_ADD_VOL, 
                view.initor.mdb.getErrorCode(), 
                //ResourceCenter.getCrVolErrStr( view.initor.mdb.getErrorCode() )
                view.initor.mdb.getErrorMessage()
            );
            return;
        }
SanBootView.log.info(getClass().getName(),"########### End of create vol action. " );         
    }
}

class DelVolAction extends GeneralActionForMainUi{
    public DelVolAction(){
        super(
            ResourceCenter.ICON_DELETE,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.delVol",
            MenuAndBtnCenterForMainUi.FUNC_DEL_VOL
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering delete vol action. " );         
        Object[] selObj = view.getMulSelectedObjFromSanBoot();
        if( selObj == null || selObj.length <= 0 ) return;
        
        int ret =  JOptionPane.showConfirmDialog(
            view,
            SanBootView.res.getString("MenuAndBtnCenter.confirm1"),
            SanBootView.res.getString("common.confirm"),  //"Confirm",
            JOptionPane.OK_CANCEL_OPTION
        );
        if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION ) ){
            return;
        }

        if(  !view.initor.mdb.updateMDI() ){
            view.showError1(
                ResourceCenter.CMD_GET_MIRROR_DISK,
                view.initor.mdb.getErrorCode(),
                view.initor.mdb.getErrorMessage()
            );
SanBootView.log.info(getClass().getName(),"failed to get MDI.\n########### End of delete vol action." );
            return;
        }

        ProgressDialog pdiag = new ProgressDialog( 
            view,
            SanBootView.res.getString("View.pdiagTitle.delvol"),
            SanBootView.res.getString("View.pdiagTip.delvol")
        );
        DelVol delvol = new DelVol( pdiag,selObj,view );
        delvol.start();
        pdiag.mySetSize();
        pdiag.setLocation( view.getCenterPoint( pdiag.getDefWidth(),pdiag.getDefHeight() ) );
        pdiag.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of delete vol action." );         
    }
    
    class DelVol extends Thread{
        ProgressDialog pdiag;
        Object[] vols;
        SanBootView view;
        
        public DelVol( ProgressDialog pdiag,Object[] vols,SanBootView view ){
            this.pdiag = pdiag;
            this.vols = vols;
            this.view = view;
        }
        
        Runnable close = new Runnable(){
            public void run(){
                pdiag.dispose();
            }
        };
        
        @Override public void run() throws HeadlessException {
            Volume vol;
            MirrorGrp mg;
            MirrorJob mj;
            boolean isOk,hasActiveMj,isGoing;
            BrowserTreeNode fNode,chiefOrphVolNode;
            ChiefOrphanVolume chiefOrphVol;
            Vector<LunMap> lmList;
            ArrayList<Volume> activeMj = new ArrayList<Volume>();
            ArrayList<MirrorJob> mjList;
            int size1,size2,j,tid,k,m,size3,i,size,size4,mg_id;
            LunMap lm;
            Audit audit;

            vol = (Volume)vols[0];            
            fNode = vol.getFatherNode();
            chiefOrphVol = (ChiefOrphanVolume)fNode.getUserObject();
            chiefOrphVolNode = chiefOrphVol.getTreeNode();
            
            size = vols.length;
            for( i=0; i<size; i++ ){
                if( !( vols[i] instanceof Volume) ) continue;

                audit = view.audit.registerAuditRecord( 0, MenuAndBtnCenterForMainUi.FUNC_DEL_VOL );

                hasActiveMj = false;
                vol = (Volume)vols[i];
                //fNode = vol.getFatherNode();
                tid = vol.getTargetID();

                mg = view.initor.mdb.getMGFromVectorOnRootID( vol.getSnap_root_id() );
                if( mg != null ){
                    mjList = view.initor.mdb.getIncMjListFromVecOnSrcRootIdOrMgID( vol.getSnap_root_id(),mg.getMg_id() );
                }else{
                    mjList = view.initor.mdb.getIncMjListFromVecOnSrcRootId( vol.getSnap_root_id() );
                }

                size3 = mjList.size();
                for( m=0; m<size3; m++ ){
                    mj = (MirrorJob)mjList.get( m );
                    if( mj.isMJStart() ){
                        activeMj.add( vol );
                        hasActiveMj = true;
                        break;
                    }
                }

                if( hasActiveMj ){
                    continue;
                }

                // 首先删除老的、没用的lunmap,不管是否成功删除
                isOk = view.initor.mdb.getLunMapForTID( tid );
                if( isOk ){
                    lmList = view.initor.mdb.getAllLunMapForTid();
                    size1 = lmList.size();
                    for( j=0;j<size1;j++ ){
                        lm = (LunMap)lmList.elementAt( j );
                        view.initor.mdb.delLunMap( tid, lm.getIP(),lm.getMask(), lm.getAccessMode() );
                    }
                }

                // 再删除disk上所有快照的view的lunmap
                isOk = view.initor.mdb.getAllView( vol.getSnap_root_id() );
                if( isOk ){
                    ArrayList viewList = view.initor.mdb.getViewList();
                    size2 = viewList.size();
                    for( k=0; k<size2; k++ ){
                        View viewObj = (View)viewList.get( k );
                        isOk = view.initor.mdb.getLunMapForTID( viewObj.getSnap_target_id() );
                        if( isOk ){
                            lmList = view.initor.mdb.getAllLunMapForTid();
                            size1 = lmList.size();
                            for( j=0;j<size1;j++ ){
                                lm = (LunMap)lmList.elementAt(j);
                                view.initor.mdb.delLunMap( viewObj.getSnap_target_id(), lm.getIP(),lm.getMask(), lm.getAccessMode() );
                                /*
                                if( !isOk ){
                                    JOptionPane.showMessageDialog(view,
                                        ResourceCenter.getCmd( ResourceCenter.CMD_DEL_LUNMAP) + " "+ viewObj.getSnap_target_id() +" "+lm.getIP() +" "+lm.getMask() +" "+lm.getAccessMode() +" "+
                                            view.initor.mdb.getErrorMessage()
                                    );
                                    return;
                                }
                                 */
                            }
                        }else{
                            /*
                            JOptionPane.showMessageDialog(view,
                                ResourceCenter.getCmd( ResourceCenter.CMD_GET_LUNMAP) + " [ " +viewObj.getSnap_target_id() + " ] "+
                                    view.initor.mdb.getErrorMessage()
                            );
                            return;
                             */
                        }
                    }
                }else{
                    /*
                    JOptionPane.showMessageDialog(view,
                        ResourceCenter.getCmd( ResourceCenter.CMD_GET_VIEW) + " "+
                            view.initor.mdb.getErrorMessage()
                    );
                    return;
                     */
                }

                isGoing = true;
                
                // delete mj(mg) on vol
                if( mg != null ){
                    // 如果mg上有active的mj，那么就不会到达这里（前面检查了是否存在active mj),
                    // 只能先把mj停止才行。但是，对于cmdp来说，即使mg上面没有mj，start_mirror也
                    // 可能开启（用于自动创建快照）。由于这里不知道是否为CMDP还是mtpp，所以统一
                    // 检查mg的状态。
                    if( !view.initor.mdb.checkMg( mg.getMg_id() ) ){
                        // mg 已经停止了
                        isOk = true;
                    }else{
                        isOk = view.initor.mdb.stopMg( mg.getMg_id() );
                    }

                    if( isOk ){
                        if( !view.initor.mdb.delMg( mg.getMg_id() ) ){
                            view.showError1(
                                ResourceCenter.CMD_DEL_MG,
                                view.initor.mdb.getErrorCode(),
                                view.initor.mdb.getErrorMessage()
                            );
                            isGoing = false;
                        }else{
                            view.initor.mdb.removeMGFromVector( mg );
                            // 成功删除mg后系统会自动将mdb中相关的mj(普通镜像任务)删除
                        }
                    }else{
                        isGoing = false;
                    }
                }
                
                if( isGoing ){
                    // 删除该mj对应的无限增量镜像卷
                    if( !delUIMVol( vol.getSnap_root_id() ) ){
                        isGoing = false;
                    }
                }

                if( isGoing ){
                    // 删除mj对应的克隆盘
                    if( !delCloneDisk( vol.getSnap_root_id() ) ){
                        isGoing = false;
                    }
                }

                if( isGoing ){
                    size3 = mjList.size();
                    for( m=0; m<size3; m++ ){
                        mj = (MirrorJob)mjList.get( m );
                        if( mj.getMj_mg_id() == -1 ){ // 无限增量镜像卷
                            if( !view.initor.mdb.delMj( mj.getMj_id() ) ){
                                view.showError1(
                                    ResourceCenter.CMD_DEL_MJ,
                                    view.initor.mdb.getErrorCode(),
                                    view.initor.mdb.getErrorMessage()
                                );
                                isGoing = false;
                                break;
                            }else{
                                view.initor.mdb.removeMJFromVector( mj );
                            }
                        }else{ // 普通镜像卷
                            view.initor.mdb.removeMJFromVector( mj );
                        }
                    }
                }

                if( isGoing ){
                    view.initor.mdb.setNewTimeOut( ResourceCenter.MAX_TIMEOUT );
                    isOk = view.initor.mdb.delVolume( vol );
                    view.initor.mdb.restoreOldTimeOut();
                    if( isOk ){
                        audit.setEventDesc( "Delete volume: " + vol.getSnap_name() + " successfully.");
                        view.audit.addAuditRecord( audit );

                        BrowserTreeNode selVolNode = view.getOrphVolNodeOnChiefOrphVol( chiefOrphVolNode,vol.getTargetID() );
                        if( selVolNode !=null ){
                            view.removeNodeFromTree( chiefOrphVolNode, selVolNode );
                        }
                    }else{
                        audit.setEventDesc( "Failed to delete volume: " + vol.getSnap_name() );
                        view.audit.addAuditRecord( audit );

                        view.showError1(
                            ResourceCenter.CMD_DEL_VOL,
                            view.initor.mdb.getErrorCode(),
                            view.initor.mdb.getErrorMessage()
                        );
                    }
                }
            }
            try {
                SwingUtilities.invokeAndWait(close);
            } catch (InterruptedException ex) {
                Logger.getLogger(DelVolAction.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(DelVolAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            size4 = activeMj.size();
            if( size4 >0 ){
                String errStr = SanBootView.res.getString("MenuAndBtnCenter.error.hasActiveMj")+":\n";
                StringBuffer sb = new StringBuffer(errStr);
                boolean isFirst = true;
                for( i=0; i<size4; i++ ){
                    vol = (Volume)activeMj.get(i);
                    if( isFirst ){
                        sb.append(vol.getSnap_name()).append("[").append(vol.getSnap_target_id()).append("]");
//                      errStr += vol.getSnap_name()+"["+vol.getSnap_target_id()+"]";
                        isFirst = false;
                    }else{
                        sb.append(", ").append(vol.getSnap_name()).append("[").append(vol.getSnap_target_id()).append("]");
//                        errStr += ", " + vol.getSnap_name()+"["+vol.getSnap_target_id()+"]";
                    }
                }
                errStr = sb.toString();
                JOptionPane.showMessageDialog( view, errStr );
            }
            
            if( size4 != size ){
                // 显示点击 chiefOrphVolNode 后的右边tabpane中的内容
                view.setCurNode( chiefOrphVolNode );
                view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                ProcessEventOnChiefOrphanVol peOnChiefOrphanVol = new ProcessEventOnChiefOrphanVol( view );
                TreePath path = new TreePath( chiefOrphVolNode.getPath() );
                peOnChiefOrphanVol.processTreeSelection( path );
                peOnChiefOrphanVol.controlMenuAndBtnForTreeEvent();
                view.getTree().setSelectionPath( path );
                view.getTree().requestFocus();
            }
        }

        private boolean delCloneDisk( int rootid ){
            boolean isOk, destroyOk;
            CloneDisk cd;

            isOk = view.initor.mdb.getCloneDiskList( -1,CloneDisk.IS_FREEVOL,rootid );
            if( isOk ){
                ArrayList list = view.initor.mdb.getCloneDiskList();
                int size = list.size();
                for( int i=0; i<size; i++ ){
                    cd = (CloneDisk)list.get(i);

                    delLunMapOnDisk( cd.getTarget_id() );
                    delLunMapOnView( cd.getRoot_id() );

                    view.initor.mdb.setNewTimeOut( ResourceCenter.MAX_TIMEOUT );
                    destroyOk = view.initor.mdb.destroyDisk( cd.getRoot_id() );
                    view.initor.mdb.restoreOldTimeOut();
                    if( !destroyOk && ( view.initor.mdb.getErrorCode() == 100 )  ){
                        // cd.getRoot_id()代表的disk本身就不存在
                        destroyOk = true;
                    }
                    
                    if( !destroyOk ){
                        view.showError1(
                            ResourceCenter.CMD_DESTORY_DISK,
                            view.initor.mdb.getErrorCode(),
                            view.initor.mdb.getErrorMessage()
                        );
                        isOk = false;
                        break;
                    }else{
                        if( !view.initor.mdb.delCloneDisk( "", 5010, 1, "", cd.getId() ) ){
                            view.showError1(
                                ResourceCenter.CMD_DEL_CLONE_DISK,
                                view.initor.mdb.getErrorCode(),
                                view.initor.mdb.getErrorMessage()
                            );
                            isOk = false;
                            break;
                        }
                    }
                }
            }else{
                view.showError1(
                    ResourceCenter.CMD_GET_CLONE_DISK,
                    view.initor.mdb.getErrorCode(),
                    view.initor.mdb.getErrorMessage()
                );
            }

            return isOk;
        }

        private boolean delUIMVol( int rootid ){
            MirrorDiskInfo mdi;
            boolean isOk = true,destroyOk;

            ArrayList list = view.initor.mdb.getMDIFromCacheOnHostIDandRootID( -1,rootid );
            int size = list.size();
            for( int i=0; i<size; i++ ){
                mdi = (MirrorDiskInfo)list.get( i );
                if( !mdi.isLocalMirrorVol() ) continue;

                view.initor.mdb.setNewTimeOut( ResourceCenter.MAX_TIMEOUT );
                destroyOk = view.initor.mdb.destroyDisk( mdi.getSnap_rootid() );
                view.initor.mdb.restoreOldTimeOut();
                if( !destroyOk && ( view.initor.mdb.getErrorCode() == 100 )  ){
                    // mdi.getSnap_rootid()代表的disk本身就不存在
                    destroyOk = true;
                }

                if(  destroyOk ){
                    if( view.initor.mdb.delMDI( mdi.getSnap_rootid() ) ){
                        view.initor.mdb.removeMDIFromCache( mdi );
                    }else{
                        view.showError1(
                            ResourceCenter.CMD_DEL_MIRROR_DISK,
                            view.initor.mdb.getErrorCode(),
                            view.initor.mdb.getErrorMessage()
                        );
                        isOk = false;
                        break;
                    }
                }else{
                    view.showError1(
                        ResourceCenter.CMD_DESTORY_DISK,
                        view.initor.mdb.getErrorCode(),
                        view.initor.mdb.getErrorMessage()
                    );
                    isOk = false;
                    break;
                }
            }

            return isOk;
        }

        private void delLunMapOnDisk( int tid ){
            // 首先删除老的、没用的lunmap,不管是否成功删除
            boolean isOk = view.initor.mdb.getLunMapForTID( tid );
            if( isOk ){
                Vector<LunMap> lmList = view.initor.mdb.getAllLunMapForTid();
                int size = lmList.size();
                for( int j=0;j<size;j++ ){
                    LunMap lm = (LunMap)lmList.elementAt( j );
                    view.initor.mdb.delLunMap( tid, lm.getIP(),lm.getMask(), lm.getAccessMode() );
                }
            }
        }

        private void delLunMapOnView( int root_id ){
            // 再删除disk上所有快照的view的lunmap
            boolean isOk = view.initor.mdb.getAllView( root_id );
            if( isOk ){
                ArrayList<View> viewList = view.initor.mdb.getViewList();
                int size2 = viewList.size();
                for( int k=0; k<size2; k++ ){
                    View viewObj = (View)viewList.get( k );
                    isOk = view.initor.mdb.getLunMapForTID( viewObj.getSnap_target_id() );
                    if( isOk ){
                        Vector<LunMap> lmList = view.initor.mdb.getAllLunMapForTid();
                        int size1 = lmList.size();
                        for( int j=0;j<size1;j++ ){
                            LunMap lm = (LunMap)lmList.elementAt(j);
                            view.initor.mdb.delLunMap( viewObj.getSnap_target_id(), lm.getIP(),lm.getMask(), lm.getAccessMode() );
                        }
                    }
                }
            }
        }
    }
}

class OnlineDevAction extends GeneralActionForMainUi{
    public OnlineDevAction(){
        super(
            ResourceCenter.BTN_ICON_ONLINE,
            ResourceCenter.BTN_ICON_ONLINE, 
            "View.MenuItem.onlineDev",
            MenuAndBtnCenterForMainUi.FUNC_ONLINE
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        int rootid,local_snap_id=-1;
        BrowserTreeNode fNode;
SanBootView.log.info(getClass().getName(),"########### Entering online device action. " );         
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( selObj instanceof Volume ){
SanBootView.log.info( getClass().getName()," (OnlineDevAction)doing online for volume" );             
            Volume vol = (Volume)selObj;
            fNode = vol.getFatherNode();
            rootid = vol.getSnap_root_id();
            local_snap_id = vol.getSnap_local_snapid(); 
        }else if( selObj instanceof LogicalVol ){
SanBootView.log.info( getClass().getName()," (OnlineDevAction)doing online for lv");                 
            LogicalVol lv = (LogicalVol)selObj;
            fNode = lv.getFatherNode();
            VolumeMap tgt = view.initor.mdb.getTargetOnLV( lv );
            if( tgt == null ){
SanBootView.log.error( this.getClass().getName(),"Can't find volmap for lv: "+ lv.getLVName() );                
                return;
            }
            rootid = tgt.getVol_rootid();
        }else if( selObj instanceof View  ){
SanBootView.log.info( getClass().getName()," (OnlineDevAction)doing online for view"); 
            View v =(View)selObj;
            fNode = v.getFatherNode();
            rootid = v.getSnap_root_id();
            local_snap_id = v.getSnap_local_snapid();
        }else if( selObj instanceof VolumeMap ){
SanBootView.log.info( getClass().getName()," (OnlineDevAction)doing online for volmapMap");      
            VolumeMap volMap = (VolumeMap)selObj;
            fNode = volMap.getFatherNode();
            rootid = volMap.getVol_rootid();
        }else{
SanBootView.log.info( getClass().getName()," (OnlineDevAction)doing online for other obj,exit.");                 
            return;
        }   
        
        OnlineOrOfflineDeviceThread online = new OnlineOrOfflineDeviceThread( view, rootid,local_snap_id, OnlineOrOfflineDeviceThread.ACT_ONLINE );
        view.startupProcessDiag( 
            SanBootView.res.getString("View.pdiagTitle.onlineDev"),
            SanBootView.res.getString("View.pdiagTip.onlineDev"), 
            online
        );
        
        if( online.isOk() ){
            if( fNode != null ){
                view.setCurNode( fNode );
                view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                TreePath path = new TreePath( fNode.getPath() );
                
                Object fObj = fNode.getUserObject();
                if( fObj instanceof ChiefHostVolume ){
                    ProcessEventOnChiefHostVol peOnChiefHVol = new ProcessEventOnChiefHostVol( view );
                    peOnChiefHVol.processTreeSelection( path );
                    peOnChiefHVol.controlMenuAndBtnForTreeEvent();
                }else if( fObj instanceof ChiefOrphanVolume ){
                    ProcessEventOnChiefOrphanVol peOnChiefFreeVol = new ProcessEventOnChiefOrphanVol( view );
                    peOnChiefFreeVol.processTreeSelection( path );
                    peOnChiefFreeVol.controlMenuAndBtnForTreeEvent();
                }else if( fObj instanceof Snapshot ) {
                    ProcessEventOnSnapshot peOnSnap = new ProcessEventOnSnapshot( view );
                    peOnSnap.processTreeSelection( path );
                    peOnSnap.controlMenuAndBtnForTreeEvent();
                }
                
                view.getTree().setSelectionPath( path );
                view.getTree().requestFocus();
            }
        }else{
            if( !online.isOk() ){
                view.showError1(
                    online.getErrCmd(),
                    online.getErrCode(),
                    online.getErrMsg()
                );
            }
            return;
        }
SanBootView.log.info(getClass().getName(),"########### End of online device action. " );         
    }    
}

class OfflineDevAction extends GeneralActionForMainUi{
    public OfflineDevAction(){
        super(
            ResourceCenter.BTN_ICON_OFFLINE,
            ResourceCenter.BTN_ICON_OFFLINE, 
            "View.MenuItem.offlineDev",
            MenuAndBtnCenterForMainUi.FUNC_OFFLINE
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        
    }
}

class CrtViewAction extends GeneralActionForMainUi{
    public CrtViewAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.crtView",
            MenuAndBtnCenterForMainUi.FUNC_CRT_VIEW
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        Object volObj;
        BrowserTreeNode snapFNode,snapNode;
        int snap_pool_id;
        int snap_rootid;
        int snap_local_id;
        boolean needZeroUUID = false;
        
SanBootView.log.info(getClass().getName(),"########### Entering create view action. " );         
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        boolean isSnapshot = ( selObj instanceof Snapshot );
        boolean isMirroredSnap = ( selObj instanceof MirroredSnap );
        if( !isSnapshot && !isMirroredSnap ){
            return;
        }
        
        int maxNum = ResourceCenter.MAX_SNAP_NUM;
        if( maxNum <= 0 ){
SanBootView.log.error( getClass().getName(),"(CrtViewAction) bad system allowed max snap num: "+ maxNum );            
            JOptionPane.showMessageDialog(view,
                SanBootView.res.getString("MenuAndBtnCenter.error.badSnapNum")
            );
            return;
        }
        
        if( selObj instanceof Snapshot ){
            Snapshot snap = (Snapshot)selObj;
            snapNode = snap.getTreeNode();
            snap_pool_id = snap.getSnap_pool_id();
            snap_rootid = snap.getSnap_root_id();
            snap_local_id = snap.getSnap_local_snapid();
            snapFNode = snap.getFatherNode();
        }else{
            MirroredSnap ms = (MirroredSnap)selObj;
            snapNode = ms.getTreeNode();
            snap_pool_id = ms.snap.getSnap_pool_id();
            snap_rootid = ms.snap.getSnap_root_id();
            snap_local_id = ms.snap.getSnap_local_snapid();
            snapFNode = ms.getFatherNode();
        }
        
        Object snapFObj = snapFNode.getUserObject();
        if( snapFObj instanceof ChiefSnapshot ){
            ChiefSnapshot chiefSnap = (ChiefSnapshot)snapFObj;
            BrowserTreeNode fNode = chiefSnap.getFatherNode();
            volObj = fNode.getUserObject();
        }else if( snapFObj instanceof MirrorDiskInfo ){
            volObj = snapFObj;
        }else{
            return;
        }
        
        int rootid = -1; 
  //      int snapid = -1;
  //      int poolid = -1;
        if( volObj instanceof Volume ){
SanBootView.log.info( getClass().getName()," (CrtViewAction)crt view for volume" );              
            Volume vol = (Volume)volObj;
            rootid = vol.getSnap_root_id();
        }else if( volObj instanceof LogicalVol ){
SanBootView.log.info( getClass().getName()," (CrtViewAction)crt view for lv");              
            LogicalVol lv =(LogicalVol)volObj;
            VolumeMap tgt = view.initor.mdb.getTargetOnLV( lv );
            if(tgt != null){
                maxNum = tgt.getMaxSnapNum();
                rootid = tgt.getVol_rootid();
            }
        }else if( volObj instanceof View ){
SanBootView.log.info( getClass().getName()," (CrtViewAction)crt view for view");     
            View v = (View)volObj;
            // 暂不支持对view的快照再作view
            rootid = v.getSnap_root_id();
        }else if( volObj instanceof VolumeMap ){
SanBootView.log.info( getClass().getName()," (CrtViewAction)crt view for volmap");            
            VolumeMap volMap = (VolumeMap)volObj;
            BootHost host = view.initor.mdb.getBootHostFromVector( volMap.getVolClntID() );
            if( host != null ){
                needZeroUUID = host.isCMDPProtect();
            }else{
                needZeroUUID  = true; // 保险起见，需要zero uuid on sector for view
            }
            
            if( volMap.isMtppProtect() ){
                maxNum = volMap.getMaxSnapNum();
            }else{
                MirrorGrp mg = view.initor.mdb.getMGFromVectorOnID( volMap.getVol_mgid() );
                if( mg != null ){
                    maxNum = mg.getMg_max_snapshot();
                }
            }
            rootid = volMap.getVol_rootid();
        }else if( volObj instanceof CloneDisk ){
            CloneDisk cloneDisk =(CloneDisk)volObj;
            rootid = cloneDisk.getRoot_id();
        }else{
            MirrorDiskInfo mdi =(MirrorDiskInfo)volObj;
            SourceAgent srcAgnt = view.initor.mdb.getSrcAgntFromVectorOnID( mdi.getSrc_agnt_id() );
            if( srcAgnt != null ){
                needZeroUUID = srcAgnt.isCMDPProtect();
            }else{
                needZeroUUID = true; // 保险起见，需要zero uuid on sector for view
            }
            rootid = mdi.getSnap_rootid();
        }
        
        if( rootid == -1 ) {
SanBootView.log.error( getClass().getName()," Can't get root id.");            
            return;
        }
        
        Pool pool = view.initor.mdb.getPool( snap_pool_id );
        if( pool == null ){
            JOptionPane.showMessageDialog( view,
                SanBootView.res.getString("MenuAndBtnCenter.error.notFoundPool")
            );
            return;
        }else{
            boolean isOk = view.initor.mdb.getPoolInfo( pool.getPool_id() );
            if( !isOk ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_POOLINFO )+" : "+
                        view.initor.mdb.getErrorMessage()
                );
                return;
            }
            
            long total = view.initor.mdb.getPoolTotalCap();
            long vused = view.initor.mdb.getPoolVUsed();
            long avail = total-vused;
            if( avail <= 0 ){
                JOptionPane.showMessageDialog(view,
                        SanBootView.res.getString("CreateOrphanVol.error.noSpaceOnPool2")+ " " +pool.getPool_name()+"\n"+
                        SanBootView.res.getString("MenuAndBtnCenter.error.crtView")
                );
                return;
            }
        }
        
        CreateViewDialog  dialog = new CreateViewDialog( view,snap_rootid,snap_local_id );
        int width = 275+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 165+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );

        Object[] ret = dialog.getValues();
        if( ret == null || ret.length <= 0 ) return;
        String name = (String)ret[0];
        
        ProgressDialog pdiag = new ProgressDialog( 
            view,
            SanBootView.res.getString("View.pdiagTitle.crtView"),
            SanBootView.res.getString("View.pdiagTip.crtView")
        );
        
        CrtView crtView = new CrtView( pdiag,snap_rootid, snap_local_id,pool.getPool_id(),name,maxNum,needZeroUUID );
        crtView.start();
        pdiag.mySetSize();
        pdiag.setLocation( view.getCenterPoint( pdiag.getDefWidth(),pdiag.getDefHeight() ) );
        pdiag.setVisible( true );

        Audit audit = view.audit.registerAuditRecord( 0,MenuAndBtnCenterForMainUi.FUNC_CRT_VIEW );

        if( crtView.isOK() && !crtView.isCancelOp() ){
            audit.setEventDesc( "Create view: " + name + " successfully." );
            view.audit.addAuditRecord( audit );

            if( isSnapshot ){
                // 显示点击 snapNode 后的右边tabpane中的内容
                // 精确地获取snapNode ！！！！！！！！！！！！！！
                if( snapNode == null ) {
SanBootView.log.info(getClass().getName(),"########### End of create view action. " ); 
                    return;
                }

                view.setCurNode( snapNode ); 
                view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                ProcessEventOnSnapshot peOnSnap = new ProcessEventOnSnapshot( view );
                TreePath path = new TreePath( snapNode.getPath() );
                peOnSnap.processTreeSelection( path );
                peOnSnap.controlMenuAndBtnForTreeEvent();
                view.getTree().setSelectionPath( path );
                view.getTree().requestFocus();
            }else{
                // 显示点击 snapNode 后的右边tabpane中的内容
                // 精确地获取snapNode ！！！！！！！！！！！！！！！
                if( snapNode == null ) {
SanBootView.log.info(getClass().getName(),"########### End of create view action. " );
                    return;
                }

                view.setCurNode( snapNode );
                view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                ProcessEventOnMirroredSnap peOnMSnap = new ProcessEventOnMirroredSnap( view );
                TreePath path = new TreePath( snapNode.getPath() );
                peOnMSnap.processTreeSelection( path );
                peOnMSnap.controlMenuAndBtnForTreeEvent();
                view.getTree().setSelectionPath( path );
                view.getTree().requestFocus();
            }
            
        }else{
            if( !crtView.isOK() ){
                audit.setEventDesc( "Failed to create view: " + name );
                view.audit.addAuditRecord( audit );

                view.showError1(
                    crtView.getErrCmd(), 
                    view.initor.mdb.getErrorCode(), 
                    view.initor.mdb.getErrorMessage()
                );
            }
            return;
        }
SanBootView.log.info(getClass().getName(),"########### End of create view action. " );         
    }
    
    class CrtView extends Thread{
        ProgressDialog pdiag;
        int rootid,snapid,poolid;
        String name;
        int maxNum;
        boolean needZeroUUID;
        boolean isOk;
        boolean cancelOp;
        int errcode;
        String errmsg;
        int cmd;
        ArrayList<View> viewList = new ArrayList<View>();
        
        public CrtView( 
            ProgressDialog pdiag,
            int rootid,
            int snapid,
            int poolid,
            String name,
            int maxNum,
            boolean needZeroUUID
        ){
            this.pdiag = pdiag;
            this.rootid = rootid;
            this.snapid = snapid;
            this.poolid = poolid;
            this.name = name;
            this.maxNum = maxNum;
            this.needZeroUUID = needZeroUUID;
        }
        
        Runnable close = new Runnable(){
            public void run(){
                pdiag.dispose();
            }
        };
        
        @Override public void run() throws HeadlessException {
            int i,j,size,local_snap_id,size1,cnt;
            Snapshot snap,newSnap;
            View viewObj;
            BasicVDisk directChild;
            cancelOp = false;

            isOk = view.initor.mdb.queryVSnapDB(
                "select * from " + ResourceCenter.VSnap_DB +" where "+
                    BasicVDisk.BVDisk_Snap_Root_ID + "=" + rootid +";"
            );
            if( isOk ){
                int num = view.initor.mdb.getSnapNumFromQuerySql( rootid );
SanBootView.log.info( getClass().getName()," Snap num on disk: "+ num +" , max snap set on this vol: "  + maxNum );
                if( num >= maxNum ){
                    // 检查其他快照(除要做view的快照)是否都有view
                    cnt = 0;
                    ArrayList snapList = view.initor.mdb.getSnapListFromQuerySql( rootid );
                    size = snapList.size();
                    for( i=0; i<size; i++ ){
                        snap = (Snapshot)snapList.get(i);
                        if( snap.getSnap_local_snapid() != snapid ){
                            // 获取树上从该快照开始所有的 view 节点
                            viewList.clear();
                            getViewChild( snap );
                            size1 = viewList.size();
SanBootView.log.debug( getClass().getName()," Child of view from this snap node on tree: ["+snap.getSnap_root_id()+
                        "."+snap.getSnap_local_snapid() +"]" );
SanBootView.log.debug( getClass().getName(),"===========================");
                            for( j=0; j<size1; j++ ){
                                viewObj = (View)viewList.get(j);
SanBootView.log.debug( getClass().getName()," view local_snap_id: "+viewObj.getSnap_local_snapid() +
                                    " view target_id: "+ viewObj.getSnap_target_id()
                                );
                            }
SanBootView.log.debug( getClass().getName(),"===========================\r\n");            

                            // 根据该快照的子节点来判断直属于它的view
                            ArrayList directChildList = snap.getChildList();
                            size1 = directChildList.size();
                            for( j=0; j<size1; j++ ){
                                local_snap_id = ((Integer)directChildList.get(j)).intValue();
                                directChild = view.initor.mdb.getVDisk( local_snap_id );
                                if( directChild != null ){
                                    viewObj = null;
                                    if( directChild.isSnap() ){
                                        viewObj = searchViewList( directChild.getSnap_target_id() );
                                    }else{ // direct child is view or disk
                                        if( directChild.isView() ){
                                            if( directChild.getSnap_target_id() != snap.getSnap_target_id() ){
                                                viewObj = new View( directChild );
                                            }
                                        }
                                    }

                                    if( viewObj != null ){
                                        cnt+=1;
SanBootView.log.debug( getClass().getName()," Found direct view: ["+ viewObj.getSnap_root_id()+
                            "." + viewObj.getSnap_local_snapid() +"]");
                                        break;
                                    }
                                }else{
SanBootView.log.error( getClass().getName()," Not found disk from vsnap db: ["+ snap.getSnap_root_id() +"."+
                        local_snap_id +"]");
                                }
                            }
                        }
                    }

                    if( cnt >= ( size-1 ) ){
                        // 说明除了要创建view的快照外所有的快照都有view了，直接报告该错误。
                        JOptionPane.showMessageDialog(view,
                            SanBootView.res.getString("MenuAndBtnCenter.error.beyondMaxSnapNum")
                        );
                        cancelOp = true;
                    }
                }
            }else{
                this.cmd = ResourceCenter.CMD_GET_SNAP;
                this.errcode = view.initor.mdb.getErrorCode();
                this.errmsg = view.initor.mdb.getErrorMessage();
            }
            
            if( isOk && !cancelOp ){
                view.initor.mdb.setNewTimeOut( ResourceCenter.MAX_TIMEOUT ); 
                isOk = view.initor.mdb.addView( name, rootid, snapid,poolid );
                view.initor.mdb.restoreOldTimeOut();
                if( !isOk ){
                    this.cmd = ResourceCenter.CMD_ADD_VIEW;
                    this.errcode = view.initor.mdb.getErrorCode();
                    this.errmsg = view.initor.mdb.getErrorMessage();
                }else{
                    if( needZeroUUID ){
                        View newView = view.initor.mdb.getCrtView();
                        isOk = view.initor.mdb.zeroUUIDOnSector( newView.getSnap_root_id(),newView.getSnap_local_snapid() );
                        if( !isOk ){
SanBootView.log.error(getClass().getName(), "failed to zero uuid of sector for view(pdmc): "+ newView.getSnap_root_id()+"/"+newView.getSnap_local_snapid() );
                            this.cmd = ResourceCenter.CMD_ADD_VIEW;
                            this.errcode = view.initor.mdb.getErrorCode();
                            this.errmsg = view.initor.mdb.getErrorMessage();
                            view.initor.mdb.delView( newView ); // 将它删除，不管结果
                        }
                    }
                }
            }
            try {
                SwingUtilities.invokeAndWait(close);
            } catch (InterruptedException ex) {
                Logger.getLogger(CrtViewAction.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(CrtViewAction.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        public boolean isOK(){
            return isOk;
        }
        
        public boolean isCancelOp(){
            return cancelOp;
        }
        
        public int getErrCmd(){
            return cmd;
        }
        
        public int getErrCode(){
            return errcode;
        }
        
        public String getErrMsg(){
            return errmsg;
        }
        
        private void getViewChild( BasicVDisk beginDisk ){            
            ArrayList childIdList = beginDisk.getChildList();
            ArrayList childDiskList = view.initor.mdb.getDiskFromQuerySql( childIdList );
            int size = childDiskList.size();
            for( int i=0; i<size; i++ ){
                BasicVDisk disk = (BasicVDisk)childDiskList.get(i);
                if( !disk.isView() && !disk.isOriDisk() ){
                    getViewChild( disk );
                }else{
                    if( disk.isView() ){
                        viewList.add( new View( disk ) );
                    }
                }
            }
        }
        
        private View searchViewList( int target_id ){
            View viewObj;
            int size = viewList.size();
            for( int j=0; j<size; j++ ){
                viewObj = (View)viewList.get(j);
                if( viewObj.getSnap_target_id() == target_id ){
                    return viewObj;
                }
            }
            return null;
        }
    }
}

class DelViewAction extends GeneralActionForMainUi{
    public DelViewAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.delView",
            MenuAndBtnCenterForMainUi.FUNC_DEL_VIEW
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering delete view action. " );         
        Object[] selObj = view.getMulSelectedObjFromSanBoot();
        if( selObj == null || selObj.length<=0 ) return;
        
        int ret =  JOptionPane.showConfirmDialog(
            view,
            SanBootView.res.getString("MenuAndBtnCenter.confirm7"),
            SanBootView.res.getString("common.confirm"),  //"Confirm",
            JOptionPane.OK_CANCEL_OPTION
        );
        if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION ) ){
            return;
        }
        
        ProgressDialog initDiag = new ProgressDialog( 
            view,
            SanBootView.res.getString("View.pdiagTitle.delview"),
            SanBootView.res.getString("View.pdiagTip.delview")
        );
        DelView delvol = new DelView( initDiag,selObj,view );
        delvol.start(); 
        initDiag.mySetSize();
        initDiag.setLocation( view.getCenterPoint( initDiag.getDefWidth(),initDiag.getDefHeight() ) );
        initDiag.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of delete view action. " );         
    }
    
    class DelView extends Thread{
        ProgressDialog pdiag;
        Object[] views;
        SanBootView view;
        
        public DelView( ProgressDialog pdiag,Object[] views,SanBootView view ){
            this.pdiag = pdiag;
            this.views = views;
            this.view = view;
        }
        
        Runnable close = new Runnable(){
            public void run(){
                pdiag.dispose();
            }
        };
        
        @Override public void run(){
            View v;
            boolean isOk;
            BrowserTreeNode fNode;
            Vector lmList;
            int size1,j,tid;
            LunMap lm;
            Audit audit;

            v = (View)views[0];            
            
            int size = views.length;
            for( int i=0; i<size; i++ ){
                if( views[i] instanceof View ){
                    v = (View)views[i];            
                    fNode = v.getFatherNode();
                    tid = v.getTargetID();
                    
                    // 首先删除老的、没用的lunmap,不管是否成功删除
                    isOk = view.initor.mdb.getLunMapForTID( tid );
                    if( isOk ){
                        lmList = view.initor.mdb.getAllLunMapForTid();
                        size1 = lmList.size();
                        for( j=0;j<size1;j++ ){
                            lm = (LunMap)lmList.elementAt( j );
                            view.initor.mdb.delLunMap( tid, lm.getIP(),lm.getMask(), lm.getAccessMode() );
                        }
                    }
                    audit = view.audit.registerAuditRecord( 0, MenuAndBtnCenterForMainUi.FUNC_DEL_VIEW );
                    isOk = view.initor.mdb.delView( v );
                    if( isOk ){
                        audit.setEventDesc( "Delete view: " + v.getSnap_name() + " successfully." );
                        view.audit.addAuditRecord( audit );

                        BrowserTreeNode selViewNode = view.getViewNodeOnSnapshot( fNode,v.getTargetID() );
                        if( selViewNode !=null ){
                            view.removeNodeFromTree( fNode,selViewNode );
                        }
                    }else{
                        audit.setEventDesc( "Failed to delete view: " + v.getSnap_name() );
                        view.audit.addAuditRecord( audit );

                        view.showError1(
                            ResourceCenter.CMD_DEL_VIEW, 
                            view.initor.mdb.getErrorCode(), 
                            //ResourceCenter.getDelVolErrStr( view.initor.mdb.getErrorCode() )
                            view.initor.mdb.getErrorMessage()
                        );
                    }
                }
            }
            
            try{
                SwingUtilities.invokeAndWait( close );
            }catch( Exception ex ){
                ex.printStackTrace();
            }
            
            // 显示点击 snapNode 后的右边tabpane中的内容
            fNode = v.getFatherNode();
            Object snapObj = fNode.getUserObject();
            if( snapObj instanceof Snapshot ){
                view.setCurNode( fNode );
                view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                ProcessEventOnSnapshot peOnSnap = new ProcessEventOnSnapshot( view );
                TreePath path = new TreePath( fNode.getPath() );
                peOnSnap.processTreeSelection( path );
                peOnSnap.controlMenuAndBtnForTreeEvent();
                view.getTree().setSelectionPath( path );
                view.getTree().requestFocus();
            }else{
                view.setCurNode( fNode );
                view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                ProcessEventOnMirroredSnap peOnMSnap = new ProcessEventOnMirroredSnap( view );
                TreePath path = new TreePath( fNode.getPath() );
                peOnMSnap.processTreeSelection( path );
                peOnMSnap.controlMenuAndBtnForTreeEvent();
                view.getTree().setSelectionPath( path );
                view.getTree().requestFocus();
            }
        }
    }
}

class MntViewAction extends GeneralActionForMainUi{
    public MntViewAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.mntView",
            MenuAndBtnCenterForMainUi.FUNC_MNT_VIEW
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        BrowserTreeNode chiefHostVolNode;
        
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( !( selObj instanceof View ) ){
            return;
        }
        
        View viewObj = (View)selObj;
        BrowserTreeNode fNode_Snap = viewObj.getFatherNode();
        Snapshot snap = (Snapshot)fNode_Snap.getUserObject();                
        BrowserTreeNode fNode = snap.getFatherNode();
        ChiefSnapshot chiefSnap = (ChiefSnapshot)fNode.getUserObject();
        BrowserTreeNode volNode = chiefSnap.getFatherNode();
        Object volObj = volNode.getUserObject();
        if( volObj instanceof Volume ){
            Volume vol = (Volume)volObj;
            chiefHostVolNode = vol.getFatherNode();
        }else if( volObj instanceof LogicalVol ){
            LogicalVol lv = (LogicalVol)volObj;
            chiefHostVolNode = lv.getFatherNode();            
        }else if( volObj instanceof View ){
            View v = (View)volObj;
            chiefHostVolNode = v.getFatherNode();
        }else{
            VolumeMap volMap = (VolumeMap)volObj;
            chiefHostVolNode = volMap.getFatherNode();
        }
        ChiefHostVolume chiefHostVol = (ChiefHostVolume)chiefHostVolNode.getUserObject();
        BrowserTreeNode hostNode = chiefHostVol.getFatherNode();
        BootHost host = (BootHost)hostNode.getUserObject();
        
        MountSnapOrViewDialog dialog = new MountSnapOrViewDialog( view,host,true );
        int width  = 355+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 255+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
    }   
}

class UMntViewAction extends GeneralActionForMainUi{
    public UMntViewAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.umntView",
            MenuAndBtnCenterForMainUi.FUNC_UMNT_VIEW
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        BrowserTreeNode chiefHostVolNode;
        
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( !( selObj instanceof View ) ){
            return;
        }
        
        View viewObj = (View)selObj;
        BrowserTreeNode fNode_Snap = viewObj.getFatherNode();
        Snapshot snap = (Snapshot)fNode_Snap.getUserObject();                
        BrowserTreeNode fNode = snap.getFatherNode();
        ChiefSnapshot chiefSnap = (ChiefSnapshot)fNode.getUserObject();
        BrowserTreeNode volNode = chiefSnap.getFatherNode();
        Object volObj = volNode.getUserObject();
        if( volObj instanceof Volume ){
            Volume vol = (Volume)volObj;
            chiefHostVolNode = vol.getFatherNode();
        }else if( volObj instanceof LogicalVol ){
            LogicalVol lv = (LogicalVol)volObj;
            chiefHostVolNode = lv.getFatherNode();            
        }else if( volObj instanceof View ){
            View v = (View)volObj;
            chiefHostVolNode = v.getFatherNode();
        }else{
            VolumeMap volMap = (VolumeMap)volObj;
            chiefHostVolNode = volMap.getFatherNode();
        }
        ChiefHostVolume chiefHostVol = (ChiefHostVolume)chiefHostVolNode.getUserObject();
        BrowserTreeNode hostNode = chiefHostVol.getFatherNode();
        BootHost host = (BootHost)hostNode.getUserObject();
        
        UmountViewDialog dialog = new UmountViewDialog( view,host );
        int width = 355+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 255+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
    }   
}

class MntSnapAction extends GeneralActionForMainUi{
    public MntSnapAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.mntSnap",
            MenuAndBtnCenterForMainUi.FUNC_MNT_SNAP
        );
    }
    
    @Override public void doAction(ActionEvent evt){   
        BrowserTreeNode chiefHostVolNode;
        
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( !( selObj instanceof Snapshot ) ){
            return;
        }        
        
        Snapshot snap = (Snapshot)selObj;
        BrowserTreeNode fNode = snap.getFatherNode();
        ChiefSnapshot chiefSnap = (ChiefSnapshot)fNode.getUserObject();
        BrowserTreeNode volNode = chiefSnap.getFatherNode();
        Object volObj = volNode.getUserObject();
        if( volObj instanceof Volume ){
            Volume vol = (Volume)volObj;
            chiefHostVolNode = vol.getFatherNode();           
        }else if( volObj instanceof LogicalVol ){
            LogicalVol lv = (LogicalVol)volObj;
            chiefHostVolNode = lv.getFatherNode();            
        }else if( volObj instanceof View ){
            View v = (View)volObj;
            chiefHostVolNode = v.getFatherNode();
        }else{
            VolumeMap volMap = (VolumeMap)volObj;
            chiefHostVolNode = volMap.getFatherNode();
        }
        ChiefHostVolume chiefHostVol = (ChiefHostVolume)chiefHostVolNode.getUserObject();
        BrowserTreeNode hostNode = chiefHostVol.getFatherNode();
        BootHost host = (BootHost)hostNode.getUserObject();
        
        MountSnapOrViewDialog dialog = new MountSnapOrViewDialog( view,host,false );
        int width  = 355+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 255+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
    }   
}

class CrtSnapAction extends GeneralActionForMainUi{
    private int mode; // 1: create sync snap  0: create anti-sync snap
    
    public CrtSnapAction( int mode ){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.crtSnap",
            MenuAndBtnCenterForMainUi.FUNC_CRT_SNAP
        );
        this.mode = mode;
    }

    private boolean isInvalidMaxSnapLicense(){
        int maxNum_mtpp = ResourceCenter.MAX_SNAP_NUM;
        int maxNum_cmdp = ResourceCenter.MAX_SNAP_CMDP_NUM;
        if( maxNum_mtpp <=0 && maxNum_cmdp <=0 ){
            JOptionPane.showMessageDialog(view,
                SanBootView.res.getString("MenuAndBtnCenter.error.badSnapNum")
            );
            return true;
        }
        return false;
    }

    private int getMaxSnapNum(){
        int maxNum_mtpp = ResourceCenter.MAX_SNAP_NUM;
        int maxNum_cmdp = ResourceCenter.MAX_SNAP_CMDP_NUM;
        if( maxNum_mtpp > maxNum_cmdp ){
            return maxNum_mtpp;
        }else{
            return maxNum_cmdp;
        }
    }

    private boolean isInvalidMaxSnapLicenseForMTPP(){
        int maxNum = ResourceCenter.MAX_SNAP_NUM;
        if( maxNum <=0 ){
SanBootView.log.error( getClass().getName(),"(CrtSnapAction) bad system allowed max snap num: "+ maxNum );
            JOptionPane.showMessageDialog(view,
                SanBootView.res.getString("MenuAndBtnCenter.error.badSnapNum")
            );
            return true;
        }
        return false;
    }

    private boolean isInvalidMaxSnapLicenseForCMDP(){
        int maxNum = ResourceCenter.MAX_SNAP_CMDP_NUM;
        if( maxNum <= 0 ){
SanBootView.log.error( getClass().getName(),"(CrtSnapAction) bad system allowed max snap num: "+ maxNum );
            JOptionPane.showMessageDialog(view,
                SanBootView.res.getString("MenuAndBtnCenter.error.badSnapNum")
            );
            return true;
        }
        return false;
    }

    @Override public void doAction(ActionEvent evt){
        int maxNum = 0;
        boolean isOk;
        boolean needSendNetbootInfo = false;
        BootHost host = null;
        PPProfile prof = null;

SanBootView.log.info(getClass().getName(),"########### Entering create snap action. " );         
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;

        if( !( selObj instanceof ChiefSnapshot ) ){
            return;
        }
        
        ChiefSnapshot chiefSnap = (ChiefSnapshot)selObj;
        BrowserTreeNode chiefSnapNode = chiefSnap.getTreeNode();
        BrowserTreeNode fNode = chiefSnap.getFatherNode();
        Object volObj = fNode.getUserObject();
        
        int rootid = -1; 
        int snapid;
        int poolid;
        String volMapName;
        if( volObj instanceof Volume ){
SanBootView.log.info( getClass().getName()," (CrtSnapAction)crt snap for volume" );
            if( isInvalidMaxSnapLicense() )return;
            Volume vol = (Volume)volObj;
            rootid = vol.getSnap_root_id();
            maxNum = getMaxSnapNum();
        }else if( volObj instanceof LogicalVol ){
SanBootView.log.info( getClass().getName()," (CrtSnapAction)crt snap for lv");              
            if( isInvalidMaxSnapLicenseForMTPP() )return;
            LogicalVol lv =(LogicalVol)volObj;
            VolumeMap tgt = view.initor.mdb.getTargetOnLV( lv );
            if( tgt != null){
                maxNum = tgt.getMaxSnapNum();
                rootid = tgt.getVol_rootid();
            }
        }else if( volObj instanceof View ){
SanBootView.log.info( getClass().getName()," (CrtSnapAction)crt snap for view"); 
            if( isInvalidMaxSnapLicense() )return;
            // 暂不支持view的快照
            View v = (View)volObj;
            maxNum = getMaxSnapNum();
            rootid = v.getSnap_root_id();
        }else if( volObj instanceof MirrorDiskInfo ){
            if( isInvalidMaxSnapLicense() )return;
            MirrorDiskInfo mdi = (MirrorDiskInfo)volObj;

            BrowserTreeNode chiefHVolNode = mdi.getFatherNode();
            Object chiefHVolObj = chiefHVolNode.getUserObject();
            ChiefHostVolume chiefHVol = (ChiefHostVolume)chiefHVolObj;
            BrowserTreeNode hostNode = chiefHVol.getFatherNode();
            SourceAgent sa = (SourceAgent)hostNode.getUserObject();
            maxNum = sa.isCMDPProtect()?ResourceCenter.MAX_SNAP_CMDP_NUM:ResourceCenter.MAX_SNAP_NUM;
            rootid = mdi.getSnap_rootid();
        }else if( volObj instanceof CloneDisk ){
            if( isInvalidMaxSnapLicense() )return;
            CloneDisk cloneDisk = (CloneDisk)volObj;
            rootid = cloneDisk.getRoot_id();

            BrowserTreeNode chiefCloneDiskNode = cloneDisk.getFatherNode();
            Object chiefCloneDiskObj = chiefCloneDiskNode.getUserObject();
            ChiefCloneDiskList chiefCloneDiskList = (ChiefCloneDiskList)chiefCloneDiskObj;
            
            BrowserTreeNode generalVolNode = chiefCloneDiskList.getFatherNode();
            Object generalVolObj = generalVolNode.getUserObject();
            if( generalVolObj instanceof MirrorDiskInfo ){
                MirrorDiskInfo mdi = (MirrorDiskInfo)generalVolObj;
                BrowserTreeNode chiefHVolNode = mdi.getFatherNode();
                Object chiefHVolObj = chiefHVolNode.getUserObject();
                ChiefHostVolume chiefHVol = (ChiefHostVolume)chiefHVolObj;
                BrowserTreeNode hostNode = chiefHVol.getFatherNode();
                SourceAgent sa = (SourceAgent)hostNode.getUserObject();
                maxNum = sa.isCMDPProtect()?ResourceCenter.MAX_SNAP_CMDP_NUM:ResourceCenter.MAX_SNAP_NUM;
            }else {
                VolumeMap volMap = (VolumeMap)generalVolObj;
                BrowserTreeNode chiefHVolNode = volMap.getFatherNode();
                Object chiefHVolObj = chiefHVolNode.getUserObject();
                ChiefHostVolume chiefHVol = (ChiefHostVolume)chiefHVolObj;
                BrowserTreeNode hostNode = chiefHVol.getFatherNode();
                BootHost bh = (BootHost)hostNode.getUserObject();
                maxNum = bh.isCMDPProtect()?ResourceCenter.MAX_SNAP_CMDP_NUM:ResourceCenter.MAX_SNAP_NUM;
            }
        }else{ // VolumeMap
SanBootView.log.info( getClass().getName()," (CrtSnapAction)crt snap for volmap");
            VolumeMap volMap = (VolumeMap)volObj;
            rootid = volMap.getVol_rootid();

            BrowserTreeNode chiefHVolNode = volMap.getFatherNode();
            Object chiefHVolObj = chiefHVolNode.getUserObject();
            ChiefHostVolume chiefHVol = (ChiefHostVolume)chiefHVolObj;
            BrowserTreeNode hostNode = chiefHVol.getFatherNode();
            host = (BootHost)hostNode.getUserObject();

            prof = view.initor.mdb.getBelongedDg( host.getID(),volMap.getVolDiskLabel() );

            if( mode == 0 ){
                if( prof != null ){
                    if( prof.isValidDriveGrp() ){
                        if( prof.getItem("C") != null ){
                            if( volMap.getVolDiskLabel().substring(0,1).toUpperCase().equals("C") ){
SanBootView.log.warning( getClass().getName(),"(CrtSnapAction) can't create latest snapshot for dg which include C disk." );
                                JOptionPane.showMessageDialog(view,
                                    SanBootView.res.getString("MenuAndBtnCenter.error.crtLatestSnapForOSDisk")
                                );
                            }else{
                                JOptionPane.showMessageDialog(view,
                                    SanBootView.res.getString("MenuAndBtnCenter.error.crtLatestSnapForOSDisk1")
                                );
                            }
                            return;
                        }
                    }else{
                        if( volMap.getVolDiskLabel().substring(0,1).toUpperCase().equals("C") ){
SanBootView.log.warning( getClass().getName(),"(CrtSnapAction) can't create latest snapshot for C." );
                            // 因为给C盘创建快照前，需要修改注册表，但是“最新快照”只是对target做快照（在服务器上），
                            // 所以无法修改注册表，所以不能给C盘创建"最新快照"
                            JOptionPane.showMessageDialog(view,
                                SanBootView.res.getString("MenuAndBtnCenter.error.crtLatestSnapForOSDisk")
                            );
                            return;
                        }
                    }
                }
            }

            if( host.isMTPPProtect() ){
                if( isInvalidMaxSnapLicenseForMTPP() )return;
                maxNum = volMap.getMaxSnapNum();
            }else{
                if( isInvalidMaxSnapLicenseForCMDP() ) return;
                if( volMap.isCMDPProtect() ){
                    MirrorGrp mg = view.initor.mdb.getMGFromVectorOnRootID( rootid );
                    if( mg != null ){
                        maxNum = mg.getMg_max_snapshot();
                    }
                }else{
                    if( isInvalidMaxSnapLicenseForMTPP() )return;
                    maxNum = volMap.getMaxSnapNum();
                }
            }
            
            if( volMap.getVolDiskLabel().toUpperCase().equals("C:\\") ){
                // 只有对C盘做快照时才需要发送 netbootinfo 到所有相关的dest uws server上
                needSendNetbootInfo = true;
            }                  
        }
        
        if( maxNum <= 0 ){
SanBootView.log.error( getClass().getName(),"(CrtSnapAction) bad user defined max snap num: "+ maxNum );
            JOptionPane.showMessageDialog(view,
                SanBootView.res.getString("MenuAndBtnCenter.error.badSnapNum1")
            );
            return;            
        }
        
        if( rootid == -1 ) {
SanBootView.log.error( getClass().getName()," Can't get root id.");            
            return;
        }

        isOk = view.initor.mdb.updateOrphanVol();
        if( !isOk ){
            view.showError1(
                ResourceCenter.CMD_GET_VOL, 
                view.initor.mdb.getErrorCode(), 
                view.initor.mdb.getErrorMessage()
            );
            return;
        }else{
            Volume vol = view.initor.mdb.getVolume( rootid );
            if( vol == null ){
SanBootView.log.error(getClass().getName(), "can't find volume, rootid: " + rootid );
                return;
            }else{
                snapid = vol.getSnap_local_snapid();
                poolid = vol.getSnap_pool_id();
                volMapName = vol.getSnap_name();
            }
        }
        
        Pool pool = view.initor.mdb.getPool( poolid );
        if( pool == null ){
SanBootView.log.error( getClass().getName()," not found pool: poolid: " + poolid );            
            JOptionPane.showMessageDialog( view,
                SanBootView.res.getString("MenuAndBtnCenter.error.notFoundPool")
            );
            return;
        }else{
            isOk = view.initor.mdb.getPoolInfo( pool.getPool_id() );
            if( !isOk ){
SanBootView.log.error( getClass().getName()," can't get pool info: poolid: " + pool.getPool_id() );                
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_POOLINFO )+" : "+
                        view.initor.mdb.getErrorMessage()
                );
                return;
            }
            
            long total = view.initor.mdb.getPoolTotalCap();
            long vused = view.initor.mdb.getPoolVUsed();
            long avail = total - vused;
            if( avail <=0 ){
SanBootView.log.error( getClass().getName()," There is no available capacity in pool: "+ pool.getPool_id() );                
                JOptionPane.showMessageDialog(view,
                        SanBootView.res.getString("CreateOrphanVol.error.noSpaceOnPool1")+ " " +pool.getPool_name()+"\n"+
                        SanBootView.res.getString("MenuAndBtnCenter.error.crtSnap")
                );
                return;
            }
        }
        
        if( this.mode == 1){
            if(host != null) {
                isOk = view.initor.mdb.getCurrentSyncState(host.getIP(), host.getPort(), volMapName);
                if( isOk ){
                    if( !view.initor.mdb.currentStateIsSync() ){
                        JOptionPane.showMessageDialog(view,
                            SanBootView.res.getString("CreateSnapshot.error.notSyncState")
                        );
                        return;
                    }
                }
            }
        }
        CreateSnapDialog dialog = new CreateSnapDialog( view,mode );
        int width = 355+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 265+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
        
        Object[] ret = dialog.getValues();
        if( ret == null || ret.length <=0 ) return;
        
        String name = (String)ret[0];
        String desc = (String)ret[1];
        if( mode == 0 ){ // create async snapshot for cmdp
            if( name.length() >= 241 ){
                name = name.substring( 0,241 ) + "[flawed_snap]";
            }else{
                name = name + "[flawed_snap]";
            }
        }
        
        ProgressDialog crtSnapDiag = new ProgressDialog( 
            view,
            SanBootView.res.getString("View.pdiagTitle.crtSnap"),
            SanBootView.res.getString("View.pdiagTip.crtSnap")
        );
        CrtSnapshot crtSnap = new CrtSnapshot( 
            crtSnapDiag,rootid,snapid,pool.getPool_id(),
            name,desc,maxNum,needSendNetbootInfo,
            host,prof,mode
        );
        crtSnap.start();
        crtSnapDiag.mySetSize();
        crtSnapDiag.setLocation( view.getCenterPoint( crtSnapDiag.getDefWidth(),crtSnapDiag.getDefHeight() ) );
        crtSnapDiag.setVisible( true );

        Audit audit = view.audit.registerAuditRecord( 0, MenuAndBtnCenterForMainUi.FUNC_CRT_SNAP );

        if( crtSnap.isOK() && !crtSnap.isCancelOp()){
            audit.setEventDesc( "Create snapshot: " + name + " successfully." );
            view.audit.addAuditRecord( audit );

            // 显示点击 chiefSnapNode 后的右边tabpane中的内容
            view.setCurNode( chiefSnapNode );
            view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
            ProcessEventOnChiefSnap peOnChiefSnap = new ProcessEventOnChiefSnap( view );
            TreePath path = new TreePath( chiefSnapNode.getPath() );
            peOnChiefSnap.processTreeSelection( path );
            peOnChiefSnap.controlMenuAndBtnForTreeEvent();
            view.getTree().setSelectionPath( path );
            view.getTree().requestFocus();
        }else{
            if( !crtSnap.isOK() ){
                audit.setEventDesc( "Failed to create snapshot: " + name );
                view.audit.addAuditRecord( audit );
                
                view.showError1(
                    crtSnap.getErrCmd(),
                    crtSnap.getErrCode(),
                    crtSnap.getErrMsg()
                );
            }
            return;
        }
SanBootView.log.info(getClass().getName(),"########### End of create snap action. " );         
    }
    
    class CrtSnapshot extends Thread{
        ProgressDialog pdiag;
        int rootid,snapid,poolid;
        String name;
        int maxNum;
        boolean needSendNetbootInfo;
        BootHost host;
        boolean isOk;
        boolean cancelOp;
        int errcode;
        String errmsg;
        int cmd;
        MirrorGrp mg;
        PPProfile prof;
        String desc ="";
        int mode;   // 1: create sync snap  0: create anti-sync snap
        
        public CrtSnapshot( 
            ProgressDialog pdiag,
            int rootid,
            int snapid,
            int poolid,
            String name,
            String desc,
            int maxNum,
            boolean needSendNetbootInfo,
            BootHost host,
            PPProfile prof,
            int mode
        ){
            this.pdiag = pdiag;
            this.rootid = rootid;
            this.snapid = snapid;
            this.poolid = poolid;
            this.name = name;
            this.desc = desc;
            this.maxNum = maxNum;
            this.needSendNetbootInfo = needSendNetbootInfo;
            this.host = host;
            this.prof = prof;
            this.mode = mode;
        }
        
        Runnable close = new Runnable(){
            public void run(){
                pdiag.dispose();
            }
        };
        
        @Override public void run() throws HeadlessException {
            cancelOp = false;
            isOk = view.initor.mdb.getSnapshot( rootid );
            if( isOk ){
                int num = view.initor.mdb.getSnapshotNum();
SanBootView.log.info( getClass().getName()," Snap num on disk: "+ num  );               
                if( num >= maxNum ){
                    Snapshot snap = view.initor.mdb.getLastSnapshot();
SanBootView.log.info( getClass().getName(), " last snap crt time: "+ snap.getSnap_create_time() );
                    
                    int ret = JOptionPane.showConfirmDialog(
                        view,
                        SanBootView.res.getString("MenuAndBtnCenter.confirm11"),
                        SanBootView.res.getString("common.confirm"),  //"Confirm",
                        JOptionPane.OK_CANCEL_OPTION
                    );
                    if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
                        cancelOp = true;
                    }
                    
                    if( !cancelOp ){
                        view.initor.mdb.setNewTimeOut( ResourceCenter.MAX_TIMEOUT ); 
                        isOk = view.initor.mdb.delSnapshot( snap.getSnap_root_id(),snap.getSnap_local_snapid() );  
                        view.initor.mdb.restoreOldTimeOut();
                        if( !isOk ){
                            this.cmd = ResourceCenter.CMD_DEL_SNAP;
                            this.errcode = view.initor.mdb.getErrorCode();
                            this.errmsg = view.initor.mdb.getErrorMessage();
                        }
                    }
                }
            }else{
                this.cmd = ResourceCenter.CMD_GET_SNAP;
                this.errcode = view.initor.mdb.getErrorCode();
                this.errmsg = view.initor.mdb.getErrorMessage();
            }
            
            if( isOk && !cancelOp ){
                view.initor.mdb.setNewTimeOut( ResourceCenter.MAX_TIMEOUT );
                if( prof == null ){
                    isOk = view.initor.mdb.addSnapshot(
                        rootid, snapid, poolid, name
                    );
                }else{
                    String dg_name = "";
                    int isGrp = 0;
                    if( prof.isValidDriveGrp() ){
                        dg_name = prof.getDriveGrpName();
                        isGrp = 1;
                    }else{
                        PPProfileItem profileItem =  prof.getMainDiskItem();
                        if( profileItem != null ){
                            VolumeMap volMap = profileItem.getVolMap();
                            if( volMap !=null ){
                                dg_name = volMap.getVolName();
                                if(dg_name == null){
                                    dg_name = "";
                                }
                            }
                        }                        
                    }
                    isOk = view.initor.mdb.addSnapshotForCMDP(
                        host.getIP(),host.getPort(),dg_name ,isGrp,name+":"+desc,mode
                    );
                }
                view.initor.mdb.restoreOldTimeOut();
                if( !isOk ){
                    this.cmd = ResourceCenter.CMD_ADD_SNAP;
                    this.errcode = view.initor.mdb.getErrorCode();
                    this.errmsg = view.initor.mdb.getErrorMessage();
                }else{
                    // 给mg发信号, 通知它：卷上创建了一个新快照
                    view.initor.mdb.sendSigToMgOnRootID( rootid,MirrorGrp.SIG_SIGUSR2 );
                }    
            }
            
                /*
                // 发送 netbootinfo 到所有的dest uws server中
                if( needSendNetbootInfo ){
                // 不管成功与否
                view.initor.mdb.sendNetbootInfoToDestUWS( rootid, host );
                }
                 */

            try {
                SwingUtilities.invokeAndWait(close);
            } catch (InterruptedException ex) {
                Logger.getLogger(CrtSnapAction.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(CrtSnapAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public boolean isOK(){
            return isOk;
        }
        
        public boolean isCancelOp(){
            return cancelOp;
        }
        
        public int getErrCmd(){
            return cmd;
        }
        
        public int getErrCode(){
            return errcode;
        }
        
        public String getErrMsg(){
            return errmsg;
        }
    }
}

class CrtlmAction extends GeneralActionForMainUi{
    public CrtlmAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.crtlm",
            MenuAndBtnCenterForMainUi.FUNC_LUNMAP
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        int tid;
        ChiefLunMap chiefLm;
        BrowserTreeNode chiefLMNode=null,diskNode=null;
        Object volObj;

SanBootView.log.info(getClass().getName(),"########### Entering create lunmap action." );         
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;

        if( !( selObj instanceof ChiefLunMap ) &&
            !( selObj instanceof View ) &&
            !( selObj instanceof VolumeMap ) &&
            !( selObj instanceof Volume ) &&
            !( selObj instanceof LogicalVol ) &&
            !( selObj instanceof MirrorDiskInfo ) &&
            !( selObj instanceof CloneDisk  )
        ){
            return;
        }

        if( selObj instanceof ChiefLunMap ){
            chiefLm = (ChiefLunMap)selObj;
            chiefLMNode = chiefLm.getTreeNode();
            BrowserTreeNode volNode = chiefLm.getFatherNode();
            volObj = volNode.getUserObject();
        }else if( selObj instanceof View ){
            View viewObj = (View)selObj;
            diskNode = viewObj.getTreeNode();
            volObj = selObj;
        }else if( selObj instanceof VolumeMap ){
            VolumeMap volMapObj = (VolumeMap)selObj;
            diskNode = volMapObj.getTreeNode();
            volObj = selObj;
        }else if( selObj instanceof Volume ){
            Volume vObj = (Volume)selObj;
            diskNode = vObj.getTreeNode();
            volObj = selObj;
        }else if( selObj instanceof LogicalVol ){
            LogicalVol lvObj = (LogicalVol)selObj;
            diskNode = lvObj.getTreeNode();
            volObj = selObj;
        }else if( selObj instanceof MirrorDiskInfo ){
            MirrorDiskInfo mdi =(MirrorDiskInfo)selObj;
            diskNode = mdi.getTreeNode();
            volObj = selObj;
        }else{ // CloneDisk
            CloneDisk cd  = (CloneDisk)selObj;
            diskNode = cd.getTreeNode();
            volObj = selObj;
        }

        if( diskNode != null ){
            chiefLMNode = view.getChiefLunMapNodeOnViewNode( diskNode );
        }

        AddLunMapDialog dialog = new AddLunMapDialog( view );
        int width = 400+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 320+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
        
        Object[] ret = dialog.getValues();
        if( ret == null || ret.length <= 0 ) return;
        
        if( volObj instanceof Volume ){
            Volume vol = (Volume)volObj;
            tid = vol.getTargetID();
        }else if( volObj instanceof LogicalVol ){
            LogicalVol lv = (LogicalVol)volObj;
            tid = view.initor.mdb.getTargetIDOnLV( lv );
        }else if( volObj instanceof View){
            View viewObj = (View)volObj;
            tid = viewObj.getTargetID();
        }else if( volObj instanceof MirrorDiskInfo ){
            MirrorDiskInfo mdi = (MirrorDiskInfo)volObj;
            tid = mdi.getTargetID();
        }else if( volObj instanceof CloneDisk ){
            CloneDisk cloneDisk =(CloneDisk)volObj;
            tid = cloneDisk.getTarget_id();
        }else{
            VolumeMap volMap = (VolumeMap)volObj;
            tid = volMap.getVolTargetID();
        }

        Audit audit = view.audit.registerAuditRecord( 0,MenuAndBtnCenterForMainUi.FUNC_LUNMAP );

        boolean isOk = view.initor.mdb.addLunMap(
            tid,
            (String)ret[0], // ip
            (String)ret[1], // mask
            (String)ret[2], // rwset
            (String)ret[3], // sev user
            (String)ret[4], // ser passwd
            (String)ret[5], //client user
            (String)ret[6] // client passwd
        );
        
        if( isOk ){
            audit.setEventDesc( "Add lunmap for target:" + tid +" on " + ret[0] + " successfully." );
            view.audit.addAuditRecord( audit );

            // 显示点击 chiefLMNode 后的右边tabpane中的内容
            if( chiefLMNode != null ){
                view.setCurNode( chiefLMNode );
                view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                ProcessEventOnChiefLunMap peOnChiefLM= new ProcessEventOnChiefLunMap( view );
                TreePath path = new TreePath( chiefLMNode.getPath() );
                peOnChiefLM.processTreeSelection( path );
                peOnChiefLM.controlMenuAndBtnForTreeEvent();
                view.getTree().setSelectionPath( path );
                view.getTree().requestFocus();
            }
        }else{
            audit.setEventDesc( "Failed to add lunmap for target: " + tid +" on " + ret[0] );
            view.audit.addAuditRecord( audit );

            view.showError1(
                ResourceCenter.CMD_ADD_LUNMAP, 
                view.initor.mdb.getErrorCode(), 
                //ResourceCenter.getGeneralErr( view.initor.mdb.getErrorCode() )
                view.initor.mdb.getErrorMessage()
            );
            return;
        }
SanBootView.log.info(getClass().getName(),"########### End of create lunmap action. " );         
    }
}

class DellmAction extends GeneralActionForMainUi{
    public DellmAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.dellm",
            MenuAndBtnCenterForMainUi.FUNC_CANCEL_LM
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        boolean isOk;
SanBootView.log.info(getClass().getName(),"########### Entering delete lunmap action. " );         
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;

        if( !( selObj instanceof LunMap ) ){
            return;
        }
        
        int ret = JOptionPane.showConfirmDialog(
            view,
            SanBootView.res.getString("MenuAndBtnCenter.confirm4"),
            SanBootView.res.getString("common.confirm"),  //"Confirm",
            JOptionPane.OK_CANCEL_OPTION
        ) ;
        if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
            return;
        }

        Audit audit = view.audit.registerAuditRecord( 0, MenuAndBtnCenterForMainUi.FUNC_CANCEL_LM );

        LunMap lm = (LunMap)selObj;
        BrowserTreeNode chiefLMNode = lm.getFatherNode();
        ChiefLunMap chiefLM = (ChiefLunMap)chiefLMNode.getUserObject();
        BrowserTreeNode volNode = chiefLM.getFatherNode();
        Object volObj = volNode.getUserObject();
        int tid;
        if( volObj instanceof Volume ){
            Volume vol = (Volume)volObj;
            tid = vol.getTargetID();
            isOk = view.initor.mdb.delLunMap( tid, lm.getIP(), lm.getMask(),lm.getAccessMode() );
        }else if( volObj instanceof LogicalVol ){
            LogicalVol lv = (LogicalVol)volObj;
            tid = view.initor.mdb.getTargetIDOnLV( lv );
            isOk = view.initor.mdb.delLunMap( tid, lm.getIP(),lm.getMask(),lm.getAccessMode() );
        }else if( volObj instanceof VolumeMap ) {
            VolumeMap volMap = (VolumeMap)volObj;
            tid = volMap.getVolTargetID();
            isOk = view.initor.mdb.delLunMap( tid ,lm.getIP(),lm.getMask(),lm.getAccessMode() );
        }else if( volObj instanceof MirrorDiskInfo ){
            MirrorDiskInfo mdi = (MirrorDiskInfo)volObj;
            tid = mdi.getTargetID();
            isOk = view.initor.mdb.delLunMap( tid ,lm.getIP(),lm.getMask(),lm.getAccessMode() );
        }else if( volObj instanceof CloneDisk ){
            CloneDisk cloneDisk = (CloneDisk)volObj;
            tid = cloneDisk.getTarget_id();
            isOk = view.initor.mdb.delLunMap( tid, lm.getIP(), lm.getMask(),lm.getAccessMode() );
        }else{
            View v = (View)volObj;
            tid = v.getTargetID();
            isOk = view.initor.mdb.delLunMap( tid,lm.getIP(),lm.getMask(),lm.getAccessMode() );
        }
        
        if( isOk ){
            audit.setEventDesc( "Delete lunmap for target:" + tid + " on " + lm.getIP() + " successfully." );
            view.audit.addAuditRecord( audit);

            // 显示点击 chiefLMNode 后的右边tabpane中的内容
            view.setCurNode( chiefLMNode );
            view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
            ProcessEventOnChiefLunMap peOnChiefLM= new ProcessEventOnChiefLunMap( view );
            TreePath path = new TreePath( chiefLMNode.getPath() );
            peOnChiefLM.processTreeSelection( path );
            peOnChiefLM.controlMenuAndBtnForTreeEvent();
            view.getTree().setSelectionPath( path );
            view.getTree().requestFocus();
        }else{
            audit.setEventDesc( "Failed to delete lunmap for target:" + tid + " on " + lm.getIP() );
            view.audit.addAuditRecord( audit);

            view.showError1( 
                ResourceCenter.CMD_DEL_LUNMAP, 
                view.initor.mdb.getErrorCode(), 
                //ResourceCenter.getGeneralErr( view.initor.mdb.getErrorCode() )
                view.initor.mdb.getErrorMessage()
            );
            return;
        }
SanBootView.log.info(getClass().getName(),"########### End of delete lunmap action. " );         
    }
}

class CrtMjAction extends BasicCrtMjAction{
    public CrtMjAction(){
    	super( MenuAndBtnCenterForMainUi.FUNC_CRT_MJ );
    }
}

class CrtCjAction extends BasicCrtMjAction{
    public CrtCjAction(){
    	super( MenuAndBtnCenterForMainUi.FUNC_CRT_CJ );
    }
}

class CrtIncrementMjAction extends BasicCrtMjAction{
    public CrtIncrementMjAction(){
    	super( MenuAndBtnCenterForMainUi.FUNC_CRT_MJ1 );
    }
}

class DelMjAction extends GeneralActionForMainUi{
    public DelMjAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.delMj",
            MenuAndBtnCenterForMainUi.FUNC_DEL_MJ
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        boolean isOk;
        
SanBootView.log.info(getClass().getName(),"########### Entering delete mirror job action. " );         
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ){
SanBootView.log.info(getClass().getName(),"selobj is null! \n########### End of delete mirror job action. " );
            return;
        }
        if( !( selObj instanceof MirrorJob ) ){
SanBootView.log.info(getClass().getName(),"bad type !\n ########### End of delete mirror job action. " );
            return;
        }
        
        MirrorJob mj = (MirrorJob)selObj;
        if( mj.isMJStart() ){
            JOptionPane.showMessageDialog(view,
                SanBootView.res.getString("MenuAndBtnCenter.error.mjisrunning")
            );
            return;
        }
        
        int retVal =  JOptionPane.showConfirmDialog(
            view,
            mj.isCjType()?SanBootView.res.getString("MenuAndBtnCenter.confirm23"):SanBootView.res.getString("MenuAndBtnCenter.confirm13"),
            SanBootView.res.getString("common.confirm")+"[" + mj.getMj_job_name() +"]",  //"Confirm",
            JOptionPane.OK_CANCEL_OPTION
        );
        if( ( retVal == JOptionPane.CANCEL_OPTION ) || ( retVal == JOptionPane.CLOSED_OPTION ) ){
            return;
        }
        
        Object[] ret = view.getSomtthingOnTreeFromMjObj( mj );
        if( ret == null ) return;
        
        Object hostObj = ret[0];
        int rootid =((Integer)ret[1]).intValue();
        String vol_name = (String)ret[2];
        String vol_mp = (String)ret[3];
        int ptype = ((Integer)ret[4]).intValue();
        
        if( hostObj != null ){
            boolean delHostInfo = false;

            // delete the following codes because user is difficulty to understand "删除该镜像任务对应的主机信息吗?"
            // 的含义 （2010.5.27 ）
            /*
            if( mj.isRemoteMjType() ){
                delHostInfo = true;
                retVal =  JOptionPane.showConfirmDialog(
                    view,
                    SanBootView.res.getString("MenuAndBtnCenter.confirm20"),
                    SanBootView.res.getString("common.confirm"),  //"Confirm",
                    JOptionPane.OK_CANCEL_OPTION
                );
                if( ( retVal == JOptionPane.CANCEL_OPTION ) || ( retVal == JOptionPane.CLOSED_OPTION ) ){
                    delHostInfo = false;
                }
            }
            */

            if( mj.isCjType() ){
                isOk = view.initor.mdb.delMj( mj.getMj_id() );
            }else{
SanBootView.log.info(getClass().getName(), "test whether destination swu server can be connected.");
                view.initor.mdb.targetSrvName = "";
                view.initor.mdb.getHostName(
                    mj.getMj_dest_ip(),
                    mj.getMj_dest_port(),
                    mj.getMj_dest_pool(),
                    mj.getMj_dest_pool_passwd()
                );
                if( view.initor.mdb.getErrorCode() != AbstractNetworkRunning.OK ){
                    retVal =  JOptionPane.showConfirmDialog(
                        view,
                        SanBootView.res.getString("MenuAndBtnCenter.confirm24"),
                        SanBootView.res.getString("common.confirm"),  //"Confirm",
                        JOptionPane.OK_CANCEL_OPTION
                    );
                    if( ( retVal == JOptionPane.CANCEL_OPTION ) || ( retVal == JOptionPane.CLOSED_OPTION ) ){
                        return;
                    }else{
                        isOk = view.initor.mdb.delMj( mj.getMj_id() );
                    }
                }else{
                    // 清除targetSrvName,免得影响其他代码（宁肯重新获取）
                    view.initor.mdb.targetSrvName = "";
                    DeleteMjThread thread = new DeleteMjThread( view, mj, 0, hostObj, rootid, vol_name,vol_mp,delHostInfo,ptype );
                    view.startupProcessDiag(
                        SanBootView.res.getString("View.pdiagTitle.delMj"),
                        SanBootView.res.getString("View.pdiagTip.delMj"),
                        thread
                    );
                    isOk = thread.isOK();
                }
            }
        }else{
            isOk = view.initor.mdb.delMj( mj.getMj_id() );
        }

        // 将对应的mg也删除掉，否则有可能服务器端还有对应的进程( start_mirror )
        MirrorGrp mg = view.initor.mdb.getMGFromVectorOnRootID( rootid );
        if( mg != null && ( mg.getMg_type() != MirrorGrp.MG_TYPE_RADISK ) ){
SanBootView.log.info(getClass().getName(), "Try to delete corresponding mirror group object.if failed, do not care.");
            
            // find mj list related to this mg. one mg can be shared by many mj
            ArrayList mjList = view.initor.mdb.getMjListFromVecOnMgID( mg.getMg_id() );
            if( mjList.size() ==1 ){
                if( view.initor.mdb.delMg( mg.getMg_id() ) ){
                    view.initor.mdb.removeMGFromVector( mg );
                }
            }
        }

        if( isOk ){
            view.initor.mdb.removeMJFromVector( mj );
            
            BrowserTreeNode mjFNode = mj.getFatherNode();
            Object fMjObj = mjFNode.getUserObject();
            view.setCurNode( mjFNode );
            view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
            TreePath path = new TreePath( mjFNode.getPath() ); 
            if( fMjObj instanceof ChiefMirrorJobList ){  
                BrowserTreeNode selMjNode = view.getMjNodeOnMjName( mjFNode,mj.toTreeString() );
                if( selMjNode!= null ){
                    view.removeNodeFromTree( mjFNode, selMjNode );
                }
                
                ProcessEventOnChiefMj peOnChiefMj = new ProcessEventOnChiefMj( view );   
                peOnChiefMj.processTreeSelection( path );
                peOnChiefMj.controlMenuAndBtnForTreeEvent();
            }else if( fMjObj instanceof ChiefCopyJobList ){
                BrowserTreeNode selMjNode = view.getMjNodeOnMjName( mjFNode,mj.toTreeString() );
                if( selMjNode!= null ){
                    view.removeNodeFromTree( mjFNode, selMjNode );
                }
                ProcessEventOnChiefCj peOnChiefCj = new ProcessEventOnChiefCj( view );
                peOnChiefCj.processTreeSelection( path );
                peOnChiefCj.controlMenuAndBtnForTreeEvent();
            }else{
                ProcessEventOnChiefMjMg peOnChiefMjMg = new ProcessEventOnChiefMjMg( view );
                peOnChiefMjMg.processTreeSelection( path );
                peOnChiefMjMg.controlMenuAndBtnForTreeEvent(); 
            }
            view.getTree().setSelectionPath( path );
            view.getTree().requestFocus();
        }else{
            if( hostObj == null ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_DEL_MJ ) +" : "+view.initor.mdb.getErrorMessage()
                );
                return;
            }
        }
SanBootView.log.info(getClass().getName(),"########### End of delete mirror job action. " );         
    }
}

class ModMjAction extends GeneralActionForMainUi{
    public ModMjAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.modMj",
            MenuAndBtnCenterForMainUi.FUNC_MOD_MJ
        );
    }
    
    @Override public void doAction(ActionEvent evt){        
SanBootView.log.info(getClass().getName(),"########### Entering modify mirror job action. " );         
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( !( selObj instanceof MirrorJob ) ){
            return;
        }
        
        MirrorJob mj = (MirrorJob)selObj;
        
        if( mj.isMJStart() ){
            JOptionPane.showMessageDialog(view,
                SanBootView.res.getString("MenuAndBtnCenter.error.mjisrunning1")
            );
            return;
        }

        int func;
        if( mj.isNormalMjType() ){
            func = MenuAndBtnCenterForMainUi.FUNC_CRT_MJ;
        }else if( mj.isIncMjType() ){
            func = MenuAndBtnCenterForMainUi.FUNC_CRT_MJ1;
        }else{
            func = MenuAndBtnCenterForMainUi.FUNC_CRT_CJ;
        }

        EditMirrorJobDialog dialog = new EditMirrorJobDialog( view,mj, "",func,false );
        int width = 400+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 380+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
        
        Object[] ret = dialog.getValues();
        if( ret == null ) return;
        
        String job_name = (String)ret[0];
        String job_desc = (String)ret[1];
        UWSrvNode uws = (UWSrvNode)ret[2];
        Pool pool = (Pool)ret[3];
        int opt = ((Integer)ret[4]).intValue();
        
        MirrorJob newMJ = new MirrorJob(
            mj.getMj_mg_id(),
            job_name, 
            uws.getUws_ip(),
            uws.getUws_port(),
            opt,
            pool.getPool_id(),
            pool.getPool_passwd(),
            job_desc
        );
        newMJ.setMj_id( mj.getMj_id() );
        
        boolean isOk = view.initor.mdb.modMj1( newMJ ); 
        if( isOk ){
            mj.setMj_job_name( job_name );
            mj.setMj_dest_ip( uws.getUws_ip() );
            mj.setMj_dest_port( uws.getUws_port() );
            mj.setMj_transfer_option( opt );
            mj.setMj_dest_pool( pool.getPool_id() );
            mj.setMj_dest_pool_passwd( pool.getPool_passwd() );
            mj.setMj_desc( job_desc );
            
            BrowserTreeNode mjFNode = mj.getFatherNode();
            Object fMjObj = mjFNode.getUserObject();
            view.setCurNode( mjFNode );
            view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
            TreePath path = new TreePath( mjFNode.getPath() ); 
            if( fMjObj instanceof ChiefMirrorJobList ){  
                ProcessEventOnChiefMj peOnChiefMj = new ProcessEventOnChiefMj( view );   
                peOnChiefMj.processTreeSelection( path );
                peOnChiefMj.controlMenuAndBtnForTreeEvent();
            }else if( fMjObj instanceof ChiefCopyJobList ){
                ProcessEventOnChiefCj peOnChiefCj = new ProcessEventOnChiefCj( view );
                peOnChiefCj.processTreeSelection( path );
                peOnChiefCj.controlMenuAndBtnForTreeEvent();
            }else{
                ProcessEventOnChiefMjMg peOnChiefMjMg = new ProcessEventOnChiefMjMg( view );
                peOnChiefMjMg.processTreeSelection( path );
                peOnChiefMjMg.controlMenuAndBtnForTreeEvent(); 
            }
            view.getTree().setSelectionPath( path );
            view.getTree().requestFocus();
        }else{
            JOptionPane.showMessageDialog(view,
                ResourceCenter.getCmdString( ResourceCenter.CMD_MOD_MJ ) +" : "+view.initor.mdb.getErrorMessage()
            );
            return;
        }
SanBootView.log.info(getClass().getName(),"########### End of modify mirror job action. " );         
    }
}

class QuickStartMjAction extends StartMjAction{
    public QuickStartMjAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK,
            "View.MenuItem.qstartMj",
            MenuAndBtnCenterForMainUi.FUNC_QUICK_START_MJ,
            true
        );
    }
}

class StartMjAction extends GeneralActionForMainUi{
    boolean quickStart;

    public StartMjAction( Icon icon1,Icon icon2,String title,int funcID,boolean quickStart ){
        super(
            icon1,
            icon2,
            title,
            funcID
        );
        this.quickStart = quickStart;
    }

    public StartMjAction( boolean quickStart ){
        super(
            ResourceCenter.startIcon,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.startMj",
            MenuAndBtnCenterForMainUi.FUNC_START_MJ
        );
        this.quickStart = quickStart;
    }
    
    @Override public void doAction(ActionEvent evt){
        Object hostObj;
        int rootid,ptype;
        String vol_name;
        String vol_mp;
        
SanBootView.log.info(getClass().getName(),"########### Entering start mirror job action. " );         
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        if( !( selObj instanceof MirrorJob ) ) return;
        
        MirrorJob mj = (MirrorJob)selObj;
        Object[] ret = view.getSomtthingOnTreeFromMjObj( mj );
        if( ret == null ) return;
        
        hostObj = ret[0];
        rootid =((Integer)ret[1]).intValue();
        vol_name = (String)ret[2];
        vol_mp = (String)ret[3];
        ptype = ((Integer)ret[4]).intValue();
        
        String bootMac = "";
        if( hostObj != null ){
            if( hostObj instanceof BootHost ){
                BootHost bh = (BootHost)hostObj;
                if( !bh.isWinHost() ){
                    boolean ok = view.initor.mdb.getUnixNetCardInfo( ResourceCenter.CLT_IP_CONF + "/" + bh.getID() + ResourceCenter.CONF_IP );
                    if( ok ){
                        bootMac = view.initor.mdb.getUnixBootMac();
                        if( bootMac.length() == 0 ){
                            JOptionPane.showMessageDialog( view,
                                SanBootView.res.getString("EditProfileDialog.error.getBootMac")
                            );
                            return;
                        }
                    }
                }
            }else if( hostObj instanceof SourceAgent ){
                SourceAgent sa = (SourceAgent)hostObj;
                if( !sa.isWinHost() ){
                    boolean ok = view.initor.mdb.getUnixNetCardInfo( ResourceCenter.CLT_IP_CONF + "/" + ResourceCenter.PREFIX_SRC_AGNT + sa.getSrc_agnt_id() + ResourceCenter.CONF_IP );
                    if( ok ){
                        bootMac = view.initor.mdb.getUnixBootMac();
                        if( bootMac.length() == 0 ){
                            JOptionPane.showMessageDialog( view,
                                SanBootView.res.getString("EditProfileDialog.error.getBootMac")
                            );
                            return;
                        }
                    }
                }
            }
        }
        
        boolean isOk;
        if( mj.isNormalMjType() ){
SanBootView.log.info(getClass().getName(), "this mirror job is a normal job.");
            StartorStopMjThread thread1 = new StartorStopMjThread( view, mj, 0,hostObj, rootid, vol_name,vol_mp,bootMac,quickStart,ptype );
            view.startupProcessDiag( 
                SanBootView.res.getString("View.pdiagTitle.startMj")+" [ "+mj.getMj_job_name() +" ] ",
                SanBootView.res.getString("View.pdiagTip.startMj"), 
                thread1
            );
            isOk = thread1.isOk();
        }else if( mj.isIncMjType() ){
SanBootView.log.info(getClass().getName(), "this mirror job is an incremental job.");
            StartorStopUnlimitedIncMjThread thread2 = new StartorStopUnlimitedIncMjThread( view,mj,0,hostObj,rootid,vol_name,vol_mp,bootMac,quickStart,ptype );
            view.startupProcessDiag(
                SanBootView.res.getString("View.pdiagTitle.startMj")+" [ "+mj.getMj_job_name() +" ] ",
                SanBootView.res.getString("View.pdiagTip.startMj"),
                thread2
            );
            isOk = thread2.isOk();
        }else{
SanBootView.log.info(getClass().getName(), "this mirror job is a copy job.");
            StartorStopCjThread thread3 = new StartorStopCjThread( view,mj,0,hostObj,rootid,vol_name,vol_mp,bootMac,quickStart,ptype );
            view.startupProcessDiag(
                SanBootView.res.getString("View.pdiagTitle.startCj")+" [ "+mj.getMj_job_name() +" ] ",
                SanBootView.res.getString("View.pdiagTip.startCj"),
                thread3
            );
            isOk = thread3.isOk();
        }

        if( isOk ){
            mj.setMj_job_status( MirrorJob.MJ_STATUS_START );
            
            BrowserTreeNode mjFNode = mj.getFatherNode();
            Object fObj = mjFNode.getUserObject();
            view.setCurNode( mjFNode );
            view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
            TreePath path = new TreePath( mjFNode.getPath() ); 
            if( fObj instanceof ChiefCopyJobList ){
                ProcessEventOnChiefCj peOnChiefCj = new ProcessEventOnChiefCj( view );
                peOnChiefCj.processTreeSelection( path );
                peOnChiefCj.controlMenuAndBtnForTreeEvent();
            }else if( fObj instanceof ChiefMirrorJobList  ){
                ProcessEventOnChiefMj peOnChiefMj = new ProcessEventOnChiefMj( view );   
                peOnChiefMj.processTreeSelection( path );
                peOnChiefMj.controlMenuAndBtnForTreeEvent();
            }else{
                ProcessEventOnChiefMjMg peOnChiefMjMg = new ProcessEventOnChiefMjMg( view );
                peOnChiefMjMg.processTreeSelection( path );
                peOnChiefMjMg.controlMenuAndBtnForTreeEvent(); 
            }
            view.getTree().setSelectionPath( path );
            view.getTree().requestFocus();
        }
SanBootView.log.info(getClass().getName(),"########### End of start mirror job action. " );         
    }
}

class StopMjAction extends GeneralActionForMainUi{
    public StopMjAction(){
        super(
            ResourceCenter.stopIcon,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.stopMj",
            MenuAndBtnCenterForMainUi.FUNC_STOP_MJ
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering stop mirror job action. " );                 
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) {
SanBootView.log.info(getClass().getName(),"selobj is null!\n ########### Entering stop mirror job action. " );
            return;
        }
        
        if( !( selObj instanceof MirrorJob ) ){
SanBootView.log.info(getClass().getName(),"bad type ! \n ########### Entering stop mirror job action. " );
            return;
        }
        
        MirrorJob mj = (MirrorJob)selObj;
        boolean isOk;
        if( mj.isNormalMjType() ){
            StartorStopMjThread thread = new StartorStopMjThread( view, mj, 1,null, 0, "" ,"","",false,BootHost.PROTECT_TYPE_MTPP );
            view.startupProcessDiag(
                SanBootView.res.getString("View.pdiagTitle.stopMj")+" [ "+mj.getMj_job_name() +" ] ",
                SanBootView.res.getString("View.pdiagTip.stopMj"),
                thread
            );
            isOk = thread.isOk();
        }else if( mj.isIncMjType() ){
            StartorStopUnlimitedIncMjThread thread1 = new StartorStopUnlimitedIncMjThread( view, mj, 1,null, 0, "" ,"","",false,BootHost.PROTECT_TYPE_MTPP );
            view.startupProcessDiag(
                SanBootView.res.getString("View.pdiagTitle.stopMj")+" [ "+mj.getMj_job_name() +" ] ",
                SanBootView.res.getString("View.pdiagTip.stopMj"),
                thread1
            );
            isOk = thread1.isOk();
        }else{
            StartorStopCjThread thread2 = new StartorStopCjThread( view,mj,1,null,0,"","","",false,BootHost.PROTECT_TYPE_MTPP );
            view.startupProcessDiag(
                SanBootView.res.getString("View.pdiagTitle.stopCj")+" [ "+mj.getMj_job_name() +" ] ",
                SanBootView.res.getString("View.pdiagTip.stopCj"),
                thread2
            );
            isOk = thread2.isOk();
        }
        
        if( isOk ){
            mj.setMj_job_status( MirrorJob.MJ_STATUS_STOP );
            
            BrowserTreeNode mjFNode = mj.getFatherNode();
            Object fMjObj = mjFNode.getUserObject();
            view.setCurNode( mjFNode );
            view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
            TreePath path = new TreePath( mjFNode.getPath() ); 
            if( fMjObj instanceof ChiefMirrorJobList ){  
                ProcessEventOnChiefMj peOnChiefMj = new ProcessEventOnChiefMj( view );   
                peOnChiefMj.processTreeSelection( path );
                peOnChiefMj.controlMenuAndBtnForTreeEvent();
            }else if( fMjObj instanceof ChiefCopyJobList ){
                ProcessEventOnChiefCj peOnChiefCj = new ProcessEventOnChiefCj( view );
                peOnChiefCj.processTreeSelection( path );
                peOnChiefCj.controlMenuAndBtnForTreeEvent();
            }else{
                ProcessEventOnChiefMjMg peOnChiefMjMg = new ProcessEventOnChiefMjMg( view );
                peOnChiefMjMg.processTreeSelection( path );
                peOnChiefMjMg.controlMenuAndBtnForTreeEvent(); 
            }
            view.getTree().setSelectionPath( path );
            view.getTree().requestFocus();
        }
SanBootView.log.info(getClass().getName(),"########### End of stop mirror job action. " );                 
    }
}

class MonitorMjAction extends GeneralActionForMainUi{
    public MonitorMjAction(){
        super(
            ResourceCenter.monitorIcon,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.monMj",
            MenuAndBtnCenterForMainUi.FUNC_MONITOR_MJ
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering monitor mirror job action. " );                 
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) {
SanBootView.log.info(getClass().getName(),"selobj is null!\n########### End of monitor mirror job action. " );
            return;
        }
        
        if( !( selObj instanceof MirrorJob ) ){
SanBootView.log.info(getClass().getName(),"bad type !\n ########### End of monitor mirror job action. " );
            return;
        }
        MirrorJob mj = (MirrorJob)selObj;
        
        MonitorMJDialog diag = new MonitorMJDialog( view,mj );
        MjStatusGeter geter = new MjStatusGeter(
            diag,
            mj,
            view
        );
        geter.start();
        
        int width  = 400+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 275+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        diag.setSize( width, height );
        diag.setLocation( view.getCenterPoint( width,height ) );
        diag.setVisible( true );     
SanBootView.log.info(getClass().getName(),"########### End of monitor mirror job action. " );                 
    }
}

class CrtPoolAction extends GeneralActionForMainUi{
     public CrtPoolAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.crtPool",
            MenuAndBtnCenterForMainUi.FUNC_CRT_POOL
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        // get vg size
SanBootView.log.info(getClass().getName(),"########### Entering create pool action. " );                 
        boolean isOk = view.initor.mdb.getVolGrpSize();
        if( isOk ){
            float vgSize = view.initor.mdb.getRealVGSize();
            if( vgSize<=0 ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.noVgSpace")
                );
                return;
            }
            
            float tmp = (float)(vgSize/1024.0);
SanBootView.log.info(getClass().getName(), " free size of VG : " + tmp );
            
            ArrayList nameList;
            isOk = view.initor.mdb.getShareName();
            if( isOk ){
                nameList = view.initor.mdb.getSharenameList();
            }else{
                nameList = view.initor.mdb.getPoolList();
            }
            
            CreatePoolDialog dialog = new CreatePoolDialog( view, tmp,nameList ); 
            int width = 275+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 285+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
            
            Object[] ret = dialog.getValues();
            if( ret == null || ret.length <= 0 ) return;
            
            String name = (String)ret[0];
            float size = ((Float)ret[1]).floatValue();
            String password = (String)ret[2];
            float fsize = size*1024;
            long lsize = (long)fsize;
            
            CreatePoolThread thread = new CreatePoolThread(
                view,
                name,
                lsize,
                password
            );
            view.startupProcessDiag( 
                SanBootView.res.getString("View.pdiagTitle.crtPool"),
                SanBootView.res.getString("View.pdiagTip.crtPool"), 
                thread
            );
            
            Audit audit = view.audit.registerAuditRecord( 0, MenuAndBtnCenterForMainUi.FUNC_CRT_POOL );

            isOk = thread.isOK();
            if( isOk ){
                audit.setEventDesc( "Create pool: " + name + " successfully." );
                view.audit.addAuditRecord( audit );

                // 显示点击 chiefPool 后的右边tabpane中的内容
                BrowserTreeNode chiefPool = view.getChiefNodeOnRoot( ResourceCenter.TYPE_CHIEF_POOL );
                if( chiefPool!= null ){
                    view.setCurNode( chiefPool );
                    view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                    ProcessEventOnChiefPool peOnChiefPool= new ProcessEventOnChiefPool( view );
                    TreePath path = new TreePath( chiefPool.getPath() );
                    peOnChiefPool.processTreeSelection( path );
                    peOnChiefPool.controlMenuAndBtnForTreeEvent();
                    view.getTree().setSelectionPath( path );
                    view.getTree().requestFocus();
                }
            }else{
                audit.setEventDesc( "Failed to create pool: " + name );
                view.audit.addAuditRecord( audit );

                JOptionPane.showMessageDialog( view,
                   thread.getErrMsg()
                );
                return;
            }
        }else{
            JOptionPane.showMessageDialog( view,
                SanBootView.res.getString("MenuAndBtnCenter.error.getVgSize")
            );
            return;
        }
SanBootView.log.info(getClass().getName(),"########### End of create pool action. " );          
    }
}

class DelPoolAction extends GeneralActionForMainUi{
     public DelPoolAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.delPool",
            MenuAndBtnCenterForMainUi.FUNC_DEL_POOL
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering delete pool action. " );          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( selObj instanceof Pool ){
            Pool pool = (Pool)selObj;
            boolean isOk = view.initor.mdb.getPoolInfo( pool.getPool_id() );
            if( !isOk ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_POOLINFO )+" : "+
                        view.initor.mdb.getErrorMessage()
                );
                
                return;
            }else{
                long vused = view.initor.mdb.getPoolVUsed();
                if( vused <= 0 ){
                    int ret =  JOptionPane.showConfirmDialog(
                        view,
                        SanBootView.res.getString("MenuAndBtnCenter.confirm8"),
                        SanBootView.res.getString("common.confirm"),  //"Confirm",
                        JOptionPane.OK_CANCEL_OPTION
                    );
                    if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION ) ){
                        return;
                    }
                }else{
                    int ret =  JOptionPane.showConfirmDialog(
                        view,
                        SanBootView.res.getString("MenuAndBtnCenter.confirm9"),
                        SanBootView.res.getString("common.confirm"),  //"Confirm",
                        JOptionPane.OK_CANCEL_OPTION
                    );
                    if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION ) ){
                        return;
                    }
                    
                    //删除使用该pool的volume/snapshot/view，然后再删除该pool
                    //但是这样要求遍历mdb找出所有要删除的obj
                    
                }
            }

            Audit audit = view.audit.registerAuditRecord( 0, MenuAndBtnCenterForMainUi.FUNC_DEL_POOL );

            isOk = view.initor.mdb.delResVol( pool.getPool_name() );
            if( isOk ){
                if( view.initor.mdb.delPool( pool.getPool_id() ) ){
                    audit.setEventDesc( "Delete pool: "+ pool.getPool_name() + " successfully." );
                    view.audit.addAuditRecord( audit );

                    // 显示点击 chiefPool 后的右边tabpane中的内容
                    BrowserTreeNode chiefPool = view.getChiefNodeOnRoot( ResourceCenter.TYPE_CHIEF_POOL );
                    if( chiefPool!= null ){
                        view.setCurNode( chiefPool );
                        view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                        ProcessEventOnChiefPool peOnChiefPool= new ProcessEventOnChiefPool( view );
                        TreePath path = new TreePath( chiefPool.getPath() );
                        peOnChiefPool.processTreeSelection( path );
                        peOnChiefPool.controlMenuAndBtnForTreeEvent();
                        view.getTree().setSelectionPath( path );
                        view.getTree().requestFocus();
                    }
                }else{
                    audit.setEventDesc( "Failed to delete pool: " + pool.getPool_name() );
                    view.audit.addAuditRecord( audit );

                    JOptionPane.showMessageDialog( view,
                        SanBootView.res.getString("MenuAndBtnCenter.error.delPool") +
                            view.initor.mdb.getErrorMessage()
                    );
                    return;
                }
            }else{
                audit.setEventDesc( "Failed to delete pool: " + pool.getPool_name() );
                view.audit.addAuditRecord( audit );

                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.delResVol")+
                        view.initor.mdb.getErrorMessage()
                );
                return;               
            }
        }
SanBootView.log.info(getClass().getName(),"########### End of delete pool action. " );          
    }
}
    
class CrtHostAction extends GeneralActionForMainUi{
    private ChiefHost chiefHost = null;
    
    public CrtHostAction(){
        super(
            ResourceCenter.SMALL_ADD_HOST,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.crtHost",
            MenuAndBtnCenterForMainUi.FUNC_CRT_HOST
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        AddHostDialog dialog;
        boolean isOK;
SanBootView.log.info(getClass().getName(),"########### Entering create host action. " );          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( selObj instanceof ChiefHost ){
            chiefHost = (ChiefHost)selObj;
            // 发生在root下的chief host node上
            dialog = new AddHostDialog( null,view );
        }else{ // 发生在其他节点上
            dialog = new AddHostDialog( null,view );
        }
        
        int width  = 275+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 145+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );

        Object[] ret = dialog.getValue();
        if( ret == null || ret.length <= 0 ) return;
        
        String ip =(String)ret[0];
        int port = ((Integer)ret[1]).intValue();
        
        GetHostInfoThread thread = new GetHostInfoThread(
            view,
            ip, 
            port
        );
        view.startupProcessDiag( 
            SanBootView.res.getString("MenuAndBtnCenter.pdiagTitle.gettingAgentInfo"),
            SanBootView.res.getString("MenuAndBtnCenter.pdiagTip.gettingAgentInfo"), 
            thread
        );
        
        isOK = thread.isOk();
        if( isOK  ){
            String uuid = thread.getUUID();
            if( uuid.length() == 0 ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_GET_UUID_WIN )+ " : "+ thread.getErrMsgFromGetAgentInfo()
                );
                return;
            }
            
            // 判断是否存在相同的uuid（当网络启动在另外一台机器上后，源盘恢复后该机器的uuid就和源机器一样了）
            if( view.initor.mdb.getHostFromCacheOnUUID( uuid ) != null ){
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.sameUUID")
                );
                return;
            }
            if( thread.getGetAgentInfo() == null){
                JOptionPane.showMessageDialog(view,SanBootView.res.getString("MenuAndBtnCenter.error.gettingAgentInfo"));
                return;
            }
            BootHost newHost = new BootHost(
                -1,
                thread.getHostName(),
                ip,
                thread.getMachine(),
                port,
                port,
                thread.getOSName(),
                "Online",
                uuid,    // host uuid
                0,  // inited ?
                0,
                0,
                1,
                "",
                1,
                1
            );
            
            isOK = view.initor.mdb.addOneBootHost( newHost );
            if( isOK ){
                newHost.setID( view.initor.mdb.getNewId() );
                view.initor.mdb.addBootHostToVector( newHost );

                processGUIWhenAddHost( newHost );
            }else{
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_ADD_BOOT_HOST ) + " : " +
                    view.initor.mdb.getErrorMessage()
                );
            }
        }else{
            JOptionPane.showMessageDialog(view,
                thread.getErrMsgFromGetAgentInfo()
            );
            return;
        }
SanBootView.log.info(getClass().getName(),"########### End of create host action. " );          
    }
    
    private void processGUIWhenAddHost( BootHost host ){
        BrowserTreeNode chiefHostNode;
          
        // 在树上初始化新建的host( 点击发生在root下的chiefhost上或其他节点上 )
        if( chiefHost != null ){ //点击发生在root下的chiefhost上
            setupHostNodeOnRoot( chiefHost.getTreeNode(),host );
            chiefHostNode = (BrowserTreeNode)chiefHost.getTreeNode();
        }else{ // 发生在其他节点上
            chiefHostNode = view.getChiefNodeOnRoot(
                ResourceCenter.TYPE_CHIEF_HOST_INDEX
            );
            setupHostNodeOnRoot( chiefHostNode,host );
        }
        
        if( chiefHostNode!=null ){
            view.setCurNode( chiefHostNode );
            view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
            ProcessEventOnChiefHost peOnChiefHost = new ProcessEventOnChiefHost( view );
            TreePath path = new TreePath( chiefHostNode.getPath() );
            peOnChiefHost.processTreeSelection( path );
            peOnChiefHost.controlMenuAndBtnForTreeEvent();
            view.getTree().setSelectionPath( path );
            view.getTree().requestFocus();
        }
    }
    
    private void setupHostNodeOnRoot( BrowserTreeNode chiefHostNode,BootHost host ){
        BrowserTreeNode hostNode = new BrowserTreeNode( host,false );
        host.setTreeNode( hostNode );
        host.setFatherNode( chiefHostNode );
        
        ChiefHostVolume chiefHostVol = new ChiefHostVolume();
        BrowserTreeNode chiefHVolNode = new BrowserTreeNode( chiefHostVol,false );
        chiefHostVol.setTreeNode( chiefHVolNode );
        chiefHostVol.setFatherNode( hostNode );
        
        ChiefProfile chiefProfile = new ChiefProfile();
        BrowserTreeNode chiefProfNode = new BrowserTreeNode( chiefProfile,false );
        chiefProfile.setTreeNode( chiefProfNode );
        chiefProfile.setFatherNode( hostNode );
        
        ChiefNetBootHost chiefNBootHost = new ChiefNetBootHost();
        BrowserTreeNode chiefNBHNode = new BrowserTreeNode( chiefNBootHost,false );
        chiefNBootHost.setTreeNode( chiefNBHNode );
        chiefNBootHost.setFatherNode( hostNode );
        
        hostNode.add( chiefHVolNode );
        hostNode.add( chiefProfNode );
        hostNode.add( chiefNBHNode );
        
        view.insertNodeToTree( chiefHostNode,hostNode );
    }
}

class DelSrcAgntAction extends GeneralActionForMainUi{    
    public DelSrcAgntAction(){
        super(
            ResourceCenter.SMALL_DEL_HOST,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.delHost",
            MenuAndBtnCenterForMainUi.FUNC_DEL_SRCAGNT
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering delete sourceagent action. " );          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( !(selObj instanceof SourceAgent) ) return;
        SourceAgent selHost = (SourceAgent)selObj;
        
        int ret = JOptionPane.showConfirmDialog(
            view,
            SanBootView.res.getString("MenuAndBtnCenter.confirm3"),
            SanBootView.res.getString("common.confirm"),  //"Confirm",
            JOptionPane.OK_CANCEL_OPTION
        );
        if(  ( ret == JOptionPane.CANCEL_OPTION ) || (  ret == JOptionPane.CLOSED_OPTION) ){
            return;
        }
        
        dataprotect.remotemirror.DelSrcAgntThread thread = new dataprotect.remotemirror.DelSrcAgntThread( view,selHost,false );
        view.startupProcessDiag(
            SanBootView.res.getString("View.pdiagTitle.delHost"),
            SanBootView.res.getString("View.pdiagTip.delHost"),
            thread
        );
SanBootView.log.info(getClass().getName(),"########### End of delete SourceAgent action. " );          
    }
}

class DelHostAction extends GeneralActionForMainUi{    
    public DelHostAction(){
        super(
            ResourceCenter.SMALL_DEL_HOST,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.delHost",
            MenuAndBtnCenterForMainUi.FUNC_DEL_HOST
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        int ret;
        boolean isConnect = true;
        
SanBootView.log.info(getClass().getName(),"########### Entering delete host(BootHost) action." );          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( ( selObj == null ) || !(selObj instanceof BootHost) ) return;
        BootHost selHost = (BootHost)selObj;
        
        if( selHost.isWinHost() ){
            if( selHost.isCMDPProtect() ){
                try{
                    // 获取主机信息,
                    GetAgentInfo getAgentInfo = new GetAgentInfo(
                        ResourceCenter.getCmdpS2A_CmdPath1( selHost.getIP(), selHost.getPort() ) + "getsysinfo 2>/dev/null",
                        view.getSocket()
                    );
                    getAgentInfo.setCmdType( ResourceCenter.CMD_TYPE_CMDP );
                    boolean isOk = getAgentInfo.getAgentInfo();
                    if( !isOk ){
                        ret = JOptionPane.showConfirmDialog(
                            view,
                            SanBootView.res.getString("MenuAndBtnCenter.confirm10"),
                            SanBootView.res.getString("common.confirm"),  //"Confirm",
                            JOptionPane.OK_CANCEL_OPTION
                        );
                        if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
                            return;
                        }

                        isConnect = false;
                    }else{
                        ret = JOptionPane.showConfirmDialog(
                            view,
                            SanBootView.res.getString("MenuAndBtnCenter.confirm3"),
                            SanBootView.res.getString("common.confirm"),  //"Confirm",
                            JOptionPane.OK_CANCEL_OPTION
                        );
                        if(  ( ret == JOptionPane.CANCEL_OPTION ) || (  ret == JOptionPane.CLOSED_OPTION) ){
                            return;
                        }
                    }
                }catch(Exception ex){
                    ret = JOptionPane.showConfirmDialog(
                        view,
                        SanBootView.res.getString("MenuAndBtnCenter.confirm10"),
                        SanBootView.res.getString("common.confirm"),  //"Confirm",
                        JOptionPane.OK_CANCEL_OPTION
                    );
                    if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
                        return;
                    }

                    isConnect = false;
                }
            }else{
                ret = JOptionPane.showConfirmDialog(
                    view,
                    SanBootView.res.getString("MenuAndBtnCenter.confirm3"),
                    SanBootView.res.getString("common.confirm"),  //"Confirm",
                    JOptionPane.OK_CANCEL_OPTION
                );
                if(  ( ret == JOptionPane.CANCEL_OPTION ) || (  ret == JOptionPane.CLOSED_OPTION) ){
                    return;
                }
            }
        }else{
            if( !view.initor.mdb.addIscsiInitorDriver( selHost.getIP(),selHost.getPort() ) ){
                ret = JOptionPane.showConfirmDialog(
                    view,
                    SanBootView.res.getString("MenuAndBtnCenter.confirm10"),
                    SanBootView.res.getString("common.confirm"),  //"Confirm",
                    JOptionPane.OK_CANCEL_OPTION
                );
                if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
                    return;
                }

                isConnect = false;
            }else{
                ret = JOptionPane.showConfirmDialog(
                    view,
                    SanBootView.res.getString("MenuAndBtnCenter.confirm3"),
                    SanBootView.res.getString("common.confirm"),  //"Confirm",
                    JOptionPane.OK_CANCEL_OPTION
                );
                if(  ( ret == JOptionPane.CANCEL_OPTION ) || (  ret == JOptionPane.CLOSED_OPTION) ){
                    return;
                }
            }
        }
        
        DelHost thread = new DelHost( view,selHost,isConnect );
        view.startupProcessDiag(
            SanBootView.res.getString("View.pdiagTitle.delHost"),
            SanBootView.res.getString("View.pdiagTip.delHost"),
            thread
        );
SanBootView.log.info(getClass().getName(),"########### End of delete host(BootHost) action. " );          
    }
}

class ModHostAction extends GeneralActionForMainUi{
    public ModHostAction(){
        super(
            ResourceCenter.SMALL_MOD_HOST,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.modHost",
            MenuAndBtnCenterForMainUi.FUNC_MOD_HOST
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        BindofVolAndSnap bind;
        Object volObj;
        ArrayList snapList;
        int i,j,size,size1,tid;
        boolean isOK;
        
SanBootView.log.info(getClass().getName(),"########### Entering modify host action. " );          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( !(selObj instanceof BootHost) ) return;
        
        try{
            BootHost selHost = (BootHost)selObj;
            BrowserTreeNode selHostNode = selHost.getTreeNode();
            BrowserTreeNode chiefHostNode = (BrowserTreeNode)selHostNode.getParent();   
            BackupClient selClnt = view.initor.mdb.getBkClntOnUUID( selHost.getUUID() );
            
            // 检查可否修改该主机
            
            AddHostDialog dialog = new AddHostDialog( selHost,view );
            int width  = 340+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 145+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
            
            Object[] ret  = dialog.getValue();
            if( ret == null || ret.length <= 0) return;
            
            String ip =(String)ret[0];
            int port = ((Integer)ret[1]).intValue();  // cmdp port
            int port1 = ((Integer)ret[2]).intValue(); // mtpp port
            
            GetHostInfoThread thread = new GetHostInfoThread(
                view,
                ip, 
                port,
                selHost.isWinHost(),
                selHost.getProtectType()
            );
            view.startupProcessDiag( 
                SanBootView.res.getString("MenuAndBtnCenter.pdiagTitle.gettingAgentInfo"),
                SanBootView.res.getString("MenuAndBtnCenter.pdiagTip.gettingAgentInfo"), 
                thread
            );
            
            isOK = thread.isOk();
            if( isOK ){
                String uuid = thread.getUUID();
                if( uuid.length() == 0 ){
SanBootView.log.error( getClass().getName(),"UUID is null for host: "+ ip + "/"+ port );                        
                    JOptionPane.showMessageDialog(view,
                        ResourceCenter.getCmdString( ResourceCenter.CMD_MOD_BOOT_HOST )+":"+
                            view.initor.mdb.getErrorMessage()
                    );
                    return;
                }
                if(thread.getGetAgentInfo() == null){
                    JOptionPane.showMessageDialog(view, SanBootView.res.getString("MenuAndBtnCenter.error.gettingAgentInfo"));
                    return;
                }
                // 检查平台是否冲突
                if( ( selHost.isWinHost() && !thread.isWinHost() )||
                    ( !selHost.isWinHost() && thread.isWinHost() ) 
                ){
                    JOptionPane.showMessageDialog(view,
                        SanBootView.res.getString("MenuAndBtnCenter.error.mismatchedOS")
                    );
                    return;
                }else{
                    if( selHost.isWinHost() && thread.isWinHost() ){
                        if( !selHost.getWinPlatForm().equals( thread.getWinPlatForm() ) ){
                            JOptionPane.showMessageDialog(view,
                                SanBootView.res.getString("MenuAndBtnCenter.error.mismatchedOS")
                            );
                            return;
                        }
                    }
                }
                
                // check uuid
                if( !uuid.equals( selHost.getUUID() ) ){
                    int retval = JOptionPane.showConfirmDialog(
                        view,
                        SanBootView.res.getString("MenuAndBtnCenter.confirm17"),
                        SanBootView.res.getString("common.confirm"),  //"Confirm",
                        JOptionPane.OK_CANCEL_OPTION
                    );
                    if( ( retval == JOptionPane.CANCEL_OPTION ) || ( retval == JOptionPane.CLOSED_OPTION) ){
                        return;
                    }
                }
                if( thread.getGetAgentInfo() == null){
                    JOptionPane.showMessageDialog(view,SanBootView.res.getString("MenuAndBtnCenter.error.gettingAgentInfo"));
                    return;
                }
                BootHost newHost = new BootHost(
                    selHost.getID(),
                    thread.getHostName(),
                    ip,
                    thread.getMachine(),
                    port,
                    port1,
                    thread.getOSName(),
                    "Online",
                    uuid, // uuid
                    selHost.getInitFlag(),  // inited ?
                    selHost.getAutoDRFlag(), 
                    selHost.getAutoRebootFlag(), 
                    selHost.getStopAllBaseServFlag(),
                    selHost.getBootMac(),
                    selHost.getBootMode(),
                    selHost.getProtectType()
                );
                
                Audit audit = view.audit.registerAuditRecord( selHost.getID(), MenuAndBtnCenterForMainUi.FUNC_MOD_HOST );

                isOK = view.initor.mdb.modOneBootHost( newHost );
                if( isOK ){
                    audit.setEventDesc("Modify host ( id: " + selHost.getID() + " ) successfully." );
                    view.audit.addAuditRecord( audit );

                    // 修改d2d client,不管结果
                    if( !changeD2DClient( selClnt,uuid, thread, ip, port1 ) ){
SanBootView.log.error( getClass().getName()," Modify d2d client failed: "+ip +"/" + port );                            
                        JOptionPane.showMessageDialog(view,
                            ResourceCenter.getCmdString( ResourceCenter.CMD_MOD_BOOT_HOST )+" : "+
                                view.initor.mdb.getErrorMessage()
                        );
                        return;
                    }
                    
                    // dhcp和profile的修改先不管��Ȳ���
                    
                    // 收集该机器上所有相关的vol,snap,view等,以便修改lunmap
                    ProgressDialog pdiag = new ProgressDialog( 
                        view,
                        SanBootView.res.getString("View.pdiagTitle.modifyHost"),
                        SanBootView.res.getString("View.pdiagTip.modifyHost")
                    );

                    // 通过第六个参数，强行指定返回值中包含volmap本身，这样就可以在下面修正volmap的lunmap了
                    GetRstVersion getRstVer = new GetRstVersion( pdiag,view,selHost.getID(),selHost.isCMDPProtect(),true,true );
                    getRstVer.start();
                    pdiag.mySetSize();
                    pdiag.setLocation( view.getCenterPoint( pdiag.getDefWidth(),pdiag.getDefHeight() ) );
                    pdiag.setVisible( true );

                    Vector bindList = getRstVer.getBindList();
                    size = bindList.size();
                    for( i=0; i<size; i++ ){
                        bind = (BindofVolAndSnap)bindList.elementAt(i);
                        snapList = bind.getSnapList();
                        volObj = bind.getVolObj();
                        if( volObj instanceof VolumeMap ){   
                            size1 = snapList.size();
                            for( j=0; j<size1; j++ ){
                                Object obj = snapList.get(j);
                                if( obj instanceof ViewWrapper ){
                                    ViewWrapper vw = (ViewWrapper)obj;
                                    tid = vw.view.getSnap_target_id();
                                }else if( obj instanceof VolumeMapWrapper ){
                                    VolumeMapWrapper vm =(VolumeMapWrapper)obj;
                                    tid = vm.volMap.getVolTargetID();
                                }else if( obj instanceof MirrorDiskInfoWrapper ){
                                    tid = -1;
                                }else{ // SnapWrapper   
                                    tid = -1;
                                }

                                if( tid != -1 ){
                                    isOK = view.initor.mdb.addLunMap( tid, ip, "255.255.255.255", "rw", "", "", "", "" );
                                    if( !isOK ){
                                        JOptionPane.showMessageDialog(view,
                                           SanBootView.res.getString("InitBootHostWizardDialog.log.lunmap") + " [ " + tid + " " + ip + " 255.255.255.255 rw ]" + " " +
                                                  SanBootView.res.getString("common.failed")
                                        );
                                        return;                            
                                    }
                                }
                            }
                        }
                    }

                    // 用新值替换旧值.这样GUI上所有地方都该改过来了
                    selHost.setName( newHost.getName() );
                    selHost.setIP( newHost.getIP() );
                    selHost.setMachine( newHost.getMachine() );
                    selHost.setPort( newHost.getPort() );
                    selHost.setOS( newHost.getOS() );
                    selHost.setStatus( newHost.getStatus() );
                    selHost.setUUID( newHost.getUUID() );

                    view.getTreeModel().reload( chiefHostNode );

                    // 显示点击 chiefHostNode 后的右边tabpane中的内容��
                    view.setCurNode( chiefHostNode );
                    view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                    ProcessEventOnChiefHost peOnChiefHost = new ProcessEventOnChiefHost( view );
                    TreePath path = new TreePath( chiefHostNode.getPath() );
                    peOnChiefHost.processTreeSelection( path );
                    peOnChiefHost.controlMenuAndBtnForTreeEvent();
                    view.getTree().setSelectionPath( path );
                    view.getTree().requestFocus();
                }else{
                    audit.setEventDesc("Failed to modify host ( id: " + selHost.getID() + " )" );
                    view.audit.addAuditRecord( audit );

                    JOptionPane.showMessageDialog(view,
                        ResourceCenter.getCmdString( ResourceCenter.CMD_MOD_BOOT_HOST )+" : "+
                            view.initor.mdb.getErrorMessage()
                    );
                }
            }else{
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_MOD_BOOT_HOST )+":"+
                        view.initor.mdb.getErrorMessage()
                );
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
SanBootView.log.info(getClass().getName(),"########### End of modify host action. " );          
    }
    
    private boolean changeD2DClient( BackupClient selClnt,String uuid,GetHostInfoThread thread,String ip,int port ){
        if( selClnt != null ){
            BackupClient newClnt = new BackupClient(
                selClnt.getID(),
                thread.getHostName(),
                ip,
                thread.getMachine(),
                port,
                thread.getOSName(),
                "Online",
                uuid, // uuid
                selClnt.getAcctID()
            );
            
            boolean isOK = view.initor.mdb.modOneClient( newClnt );
            if( isOK ){
                // 用新值替换旧值.这样GUI上所有地方都该改过来了
                selClnt.setHostName( newClnt.getHostName() );
                selClnt.setIP( newClnt.getIP() );
                selClnt.setMachineType( newClnt.getMachineType() );
                selClnt.setPort( newClnt.getPort() );
                selClnt.setOsType( newClnt.getOsType() );
                selClnt.setStatus( newClnt.getStatus() );
                selClnt.setUUID( newClnt.getUUID() );
            }
            return isOK;
        }
        
        return true;		
    }
}

class UserMgrAction extends GeneralActionForMainUi{
    public UserMgrAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK,
            "View.MenuItem.userMgr",
            MenuAndBtnCenterForMainUi.FUNC_USER_MGR
        );
    }

    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering user manager action. " );
        UserMgrDialog dialog = new UserMgrDialog( view );
        int width  = 545;
        int height = 560;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of user manager action. " );
    }
}

class AuditAction extends GeneralActionForMainUi{
    public AuditAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK,
            "View.MenuItem.audit",
            MenuAndBtnCenterForMainUi.FUNC_USER_AUDIT
        );
    }

    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering audit action. " );
        AuditLogDialog dialog = new AuditLogDialog( view );

        AuditLogGeter geter = new AuditLogGeter(
            view,
            dialog,
            1,
            50
        );
        geter.start();

        int width  = 605;
        int height = 575;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of audit action. " );
    }
}

class ShutdownAction extends GeneralActionForMainUi {
    public ShutdownAction() {
        super(
          ResourceCenter.MENU_ICON_BLANK,
          ResourceCenter.MENU_ICON_BLANK,
          "RightCustomDialog.checkbox.shutdown",
          MenuAndBtnCenterForMainUi.FUNC_SHUTDOWN
        );
    }

    @Override public void doAction( ActionEvent evt ){
        int ret = JOptionPane.showConfirmDialog(
            view,
            SanBootView.res.getString("MenuAndBtnCenter.confirm19"),
            SanBootView.res.getString("common.confirm"),  //"Confirm",
            JOptionPane.OK_CANCEL_OPTION
        );
        if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
            return;
        }

        Audit audit = view.audit.registerAuditRecord( 0, MenuAndBtnCenterForMainUi.FUNC_SHUTDOWN );
        audit.setEventDesc( "Shutdown RapidDR server successfully.");
        view.audit.addAuditRecord( audit );

        view.initor.mdb.powerdownUws();
        JOptionPane.showMessageDialog(view,
            SanBootView.res.getString("MenuAndBtnCenter.error.shutdown")
        );
    }
}

class CrtUWSAction extends GeneralActionForMainUi{
    private ChiefDestUWS chiefDestUWS = null;
    
    public CrtUWSAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.crtSWU",
            MenuAndBtnCenterForMainUi.FUNC_CRT_UWS_SRV
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        AddUWSSrvDialog dialog;
        
SanBootView.log.info(getClass().getName(),"########### Entering create swu server action. " );          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( selObj instanceof ChiefDestUWS ){
            chiefDestUWS = (ChiefDestUWS)selObj;
            // 发生在root下的chief dest uws node上
            dialog = new AddUWSSrvDialog( null,view );
        }else{ // 发生在其他节点上
            dialog = new AddUWSSrvDialog( null,view );
        }
        
        int width  = 275+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 155+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );

        Object[] ret = dialog.getValue();
        if( ret == null || ret.length <= 0 ) return;
        
        String ip =(String)ret[0];
        int port = ((Integer)ret[1]).intValue();
        
        UWSrvNode newUWS = new UWSrvNode(
            -1,  
            ip,
            port,
            ""
        );
        
        CreateUWS thread = new CreateUWS( view,newUWS );
        view.startupProcessDiag(
            SanBootView.res.getString("View.pdiagTitle.crtSWUSrvNode"),
            SanBootView.res.getString("View.pdiagTip.crtSWUSrvNode"),
            thread
        );
SanBootView.log.info(getClass().getName(),"########### End of create swu server action. " );          
    }
    
    class CreateUWS extends BasicGetSomethingThread{
        UWSrvNode newUWS;
        
        public CreateUWS( SanBootView view, UWSrvNode uws ){
            super( view );            
            this.newUWS  = uws; 
        }
        
        public boolean realRun(){
            int dest_uws_port;
            String dest_uws_ip;
            boolean aIsOk;
            
            dest_uws_ip = newUWS.getUws_ip();
            dest_uws_port = newUWS.getUws_port();
            aIsOk = view.initor.mdb.updateRemotePool( dest_uws_ip,dest_uws_port );
            if( !aIsOk ){
                errMsg = SanBootView.res.getString("MenuAndBtnCenter.error.getPool")+" : "+view.initor.mdb.getErrorMessage();
                return false;
            }
            
            ArrayList list = view.initor.mdb.getRemotePoolList();
            if( list.size() <=0 ){
                errMsg = SanBootView.res.getString("MenuAndBtnCenter.error.noPool1");
                return false;
            }
            
            Pool pool = (Pool)list.get(0);
            
            view.initor.mdb.targetSrvName = null;
            String psn = view.initor.mdb.getHostName( dest_uws_ip, dest_uws_port, pool.getPool_id(),pool.getPool_passwd() );
            if( psn.length() == 0 ){
                errMsg = SanBootView.res.getString("MenuAndBtnCenter.error.getHostName");
                view.initor.mdb.targetSrvName = null;
                return false;
            }
            view.initor.mdb.targetSrvName = null;
            newUWS.setUws_psn( psn.toUpperCase() );

            // 本地增加一台dest-end uws server
            aIsOk = view.initor.mdb.addUWSrv( "",-1, -1,"",newUWS );
            if( aIsOk ){
                newUWS.setUws_id( view.initor.mdb.getNewId() );
                view.initor.mdb.addUWSrvToVector( newUWS );
                processGUIWhenAddUWSrv( newUWS );
            }else{                
                errMsg =  ResourceCenter.getCmdString( ResourceCenter.CMD_ADD_UWS_SRV ) + " : " +
                    view.initor.mdb.getErrorMessage();         
                return false;
            }
            
            return true;
        }
        
        private void processGUIWhenAddUWSrv( UWSrvNode srv ){
            BrowserTreeNode chiefDestUWSNode;

            // 在树上初始化新建的uwsrv node( 点击发生在root下的chiefDestUWS上或其他节点上 )
            if( chiefDestUWS != null ){ //点击发生在root下的chiefRemoteUWS上
                chiefDestUWSNode = (BrowserTreeNode)chiefDestUWS.getTreeNode();
            }else{ // 发生在其他节点上
                chiefDestUWSNode = view.getChiefNodeOnRoot(
                    ResourceCenter.TYPE_CHIEF_DEST_UWS
                );
            }

            if( chiefDestUWSNode != null ){
                view.setCurNode( chiefDestUWSNode );
                view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                ProcessEventOnChiefDestUWS peOnChiefDestUWS = new ProcessEventOnChiefDestUWS( view );
                TreePath path = new TreePath( chiefDestUWSNode.getPath() );
                peOnChiefDestUWS.processTreeSelection( path );
                peOnChiefDestUWS.controlMenuAndBtnForTreeEvent();
                view.getTree().setSelectionPath( path );
                view.getTree().requestFocus();
            }
        }
    }
}

class DelUWSAction extends GeneralActionForMainUi{
    SourceAgent saForUWS = null;
    
    public DelUWSAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.delSWU",
            MenuAndBtnCenterForMainUi.FUNC_DEL_UWS_SRV
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        boolean isOk;
        
SanBootView.log.info(getClass().getName(),"########### Entering delete swu server action. " );          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( !( selObj instanceof UWSrvNode ) ) {
SanBootView.log.info(getClass().getName(),"########### (Unknown type )End of delete swu server action. " );             
            return;
        }
        
        try{           
            UWSrvNode selUWS = (UWSrvNode)selObj;
            
            if( isUsed( selUWS.getUws_id() ) ){
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.delSrcSWU")
                );
                return;
            }
                 
            int ret = JOptionPane.showConfirmDialog(
                view,
                SanBootView.res.getString("MenuAndBtnCenter.confirm12"),
                SanBootView.res.getString("common.confirm"),  //"Confirm",
                JOptionPane.OK_CANCEL_OPTION
            );
            if(  ( ret == JOptionPane.CANCEL_OPTION ) || (  ret == JOptionPane.CLOSED_OPTION) ){
                return;
            }
            
            if( saForUWS != null ){ //delete srcagnt represented src-end UWS
                isOk = view.initor.mdb.delSrcAgnt( "", 0, 0, "", saForUWS.getSrc_agnt_id() );
                if( isOk ){
                    view.initor.mdb.removeSrcAgntFromCache( saForUWS );
                }else{
                    JOptionPane.showMessageDialog( view,
                        ResourceCenter.getCmd( ResourceCenter.CMD_DEL_SRCAGNT )+":"+
                            view.initor.mdb.getErrorMessage()
                    );
                    return;
                }
            }
            
            isOk = view.initor.mdb.delUWSrv( selUWS.getUws_id() ); 
            if( isOk ){
                view.initor.mdb.removeUWSrvFromVector( selUWS );
                
                // 将相关的mj删除
                ArrayList mjs = view.initor.mdb.getAllMJOnDestIP( selUWS.getUws_ip() );
                int size = mjs.size();
                for( int i=0; i<size; i++ ){
                    MirrorJob mj = (MirrorJob)mjs.get(i);
                    if( view.initor.mdb.delMj( mj.getMj_id() ) ){
                        view.initor.mdb.removeMJFromVector( mj );
                    }   
                }

                // 显示点击 chiefDestUWSrvNode 后的右边tabpane中的内容���
                BrowserTreeNode chiefDestUWSrvNode = view.getChiefNodeOnRoot( ResourceCenter.TYPE_CHIEF_DEST_UWS );
                if(chiefDestUWSrvNode != null){       
                    view.setCurNode( chiefDestUWSrvNode );
                    view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                    ProcessEventOnChiefDestUWS peOnChiefDestUWSrv = new ProcessEventOnChiefDestUWS( view );
                    TreePath path = new TreePath( chiefDestUWSrvNode.getPath() );
                    peOnChiefDestUWSrv.processTreeSelection( path );
                    peOnChiefDestUWSrv.controlMenuAndBtnForTreeEvent();
                    view.getTree().setSelectionPath( path );
                    view.getTree().requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmd( ResourceCenter.CMD_DEL_UWS_SRV )+":"+
                        view.initor.mdb.getErrorMessage()
                );
            }           
        }catch(Exception ex){
            ex.printStackTrace();
        }
SanBootView.log.info(getClass().getName(),"########### End of delete swu server action. " );          
    }
    
    private boolean isUsed( int uws_id ){
        SourceAgent sa = null;
        int realSaCnt=0;
        ArrayList saList = view.initor.mdb.getSrcAgntFromVecOnUWSID( uws_id );
        
        if( saList.size() <=0 ) return false;
        int size = saList.size();
        for( int i=0; i<size; i++ ){
            sa = (SourceAgent)saList.get(i);
            if( !sa.isRepresentAUWS() ){              
                realSaCnt++;
            }
        }
        if( realSaCnt >0 ) return true;
        this.saForUWS = sa;
        if(sa!=null){
            ArrayList mdiList = view.initor.mdb.getMDIFromCacheOnSrcAgntID( sa.getSrc_agnt_id() );
            return ( mdiList.size() > 0 );
        }else
            return false;     
    }
}

class ModSrcUWSAction extends GeneralActionForMainUi{
    public ModSrcUWSAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.modSrcSWU",
            MenuAndBtnCenterForMainUi.FUNC_MOD_SRC_UWS_SRV
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        boolean isOK;
        
SanBootView.log.info(getClass().getName(),"########### Entering modify source-end swu server action. " );          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        try{
            if( selObj instanceof SrcUWSrvNodeWrapper ){
                SrcUWSrvNodeWrapper selSrv = (SrcUWSrvNodeWrapper)selObj;
                
                // 检查可否修改该主机
                
                AddUWSSrvDialog dialog = new AddUWSSrvDialog( selSrv.getMetaData(),view );
                int width  = 275+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
                int height = 175+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
                dialog.setSize( width,height );
                dialog.setLocation( view.getCenterPoint( width,height ) );
                dialog.setVisible( true );
                
                Object[] ret  = dialog.getValue();
                if( ret == null || ret.length <= 0) return;
                
                String ip =(String)ret[0];
                int port = ((Integer)ret[1]).intValue();
                
                UWSrvNode newSrv = new UWSrvNode(
                    selSrv.getMetaData().getUws_id(),
                    ip,
                    port,
                    selSrv.getMetaData().getUws_psn()
                );
                
                isOK = view.initor.mdb.modUWSrv( newSrv );
                if( isOK ){
                    // 用新值替换旧值.这样GUI上所有地方都该改过来了���
                    selSrv.getMetaData().setUws_ip( newSrv.getUws_ip() );
                    selSrv.getMetaData().setUws_port( newSrv.getUws_port() );
                    
                    // 显示点击 remoteUWSMg 后的右边tabpane中的内容��
                    BrowserTreeNode remoteUWSmg = view.getChiefNodeOnSuperRoot( ResourceCenter.TYPE_REMOTE_UWS_MANAGE );
                    if(remoteUWSmg != null){
                        view.setCurNode( remoteUWSmg );
                        view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                        ProcessEventOnRemoteUWSManage peOnRemoteUWSmg = new ProcessEventOnRemoteUWSManage( view );
                        TreePath path = new TreePath( remoteUWSmg.getPath() );
                        peOnRemoteUWSmg.processTreeSelection( path );
                        peOnRemoteUWSmg.controlMenuAndBtnForTreeEvent();
                        view.getTree().setSelectionPath( path );
                        view.getTree().requestFocus();
                    }
                }else{
                    JOptionPane.showMessageDialog(view,
                        ResourceCenter.getCmdString( ResourceCenter.CMD_MOD_UWS_SRV )+" : "+
                            view.initor.mdb.getErrorMessage()
                    );
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
SanBootView.log.info(getClass().getName(),"########### End of modify source-end swu server action. " );       
    }
}

class ModUWSAction extends GeneralActionForMainUi{
    public ModUWSAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.modSWU",
            MenuAndBtnCenterForMainUi.FUNC_MOD_UWS_SRV
        );
    }
    
    @Override public void doAction(ActionEvent evt){        
SanBootView.log.info(getClass().getName(),"########### Entering modify swu server action. " );                
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null || !(selObj instanceof UWSrvNode) ){
SanBootView.log.info(getClass().getName(),"########### End of modify swu server action. " );            
            return;
        }
        
        try{
            UWSrvNode selSrv = (UWSrvNode)selObj;

            // 检查可否修改该UWS服务器

            AddUWSSrvDialog dialog = new AddUWSSrvDialog( selSrv,view );
            int width  = 275+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 175+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );

            Object[] ret  = dialog.getValue();
            if( ret == null || ret.length <= 0) return;

            String ip =(String)ret[0];
            int port = ((Integer)ret[1]).intValue();
System.out.println(" new ip: "+ ip +" new port: "+port );            
            ModUWSSrvNode  thread = new ModUWSSrvNode( view,selSrv, ip, port );
            view.startupProcessDiag(
                SanBootView.res.getString("View.pdiagTitle.modSWUSrvNode"),
                SanBootView.res.getString("View.pdiagTip.modSWUSrvNode"),
                thread
            );                                    
        }catch(Exception ex){
            ex.printStackTrace();
        }
SanBootView.log.info(getClass().getName(),"########### End of modify swu server action. " );                 
    }
    
    class ModUWSSrvNode extends BasicGetSomethingThread{
        UWSrvNode selSrv;
        String new_ip;
        int new_port;
        StringBuffer mod_mj_err_buf = new StringBuffer();
        StringBuffer start_mj_err_buf = new StringBuffer();
        
        public ModUWSSrvNode( SanBootView view,UWSrvNode selSrv,String new_ip,int new_port ){
            super( view );
            
            this.selSrv   = selSrv;
            this.new_ip   = new_ip;
            this.new_port = new_port;
        }
        
        public boolean realRun(){
            boolean ok;
            boolean mod_mj_isFirst = true;
            boolean start_mj_isFirst = true;
            String strMj_Name;
            
            ok = view.initor.mdb.updateRemotePool( new_ip,new_port );
            if( !ok ){
                errMsg = SanBootView.res.getString("MenuAndBtnCenter.error.getPool")+" : "+view.initor.mdb.getErrorMessage();
                return false;
            }
            
            ArrayList list = view.initor.mdb.getRemotePoolList();
            if( list.size() <=0 ){
                errMsg = SanBootView.res.getString("MenuAndBtnCenter.error.noPool1");
                return false;
            }            
            Pool pool = (Pool)list.get(0);
            
            view.initor.mdb.targetSrvName = null;
            String psn = view.initor.mdb.getHostName( new_ip, new_port, pool.getPool_id(),pool.getPool_passwd() );
            if( psn.length() == 0 ){
                errMsg = SanBootView.res.getString("MenuAndBtnCenter.error.getHostName");
                view.initor.mdb.targetSrvName = null;
                return false;
            }
            view.initor.mdb.targetSrvName = null;
            
            if( !selSrv.getUws_psn().toUpperCase().equals( psn.toUpperCase() ) ){
                errMsg = SanBootView.res.getString("MenuAndBtnCenter.error.notSameSWU");
                return false;
            }
                     
            String old_dest_ip = selSrv.getUws_ip();
            if( !realModUWSrvNode( selSrv,new_ip,new_port,psn.toUpperCase() ) ) return false;
             
            ArrayList mjList = view.initor.mdb.getAllMJOnDestIP( old_dest_ip );
            int size =mjList.size();
System.err.println("mj size: "+size ); 
            
            for( int i=0; i<size; i++ ){
                MirrorJob mj = (MirrorJob)mjList.get( i );
                strMj_Name = mj.getMj_job_name();
                
                if( !view.initor.mdb.modMj2( mj.getMj_id(), new_ip, new_port ) ){
                    if( mod_mj_isFirst ){
                        mod_mj_err_buf.append( strMj_Name );
                        mod_mj_isFirst = false;
                    }else{
                        mod_mj_err_buf.append( "," + strMj_Name );
                    }
                }else{
                    mj.setMj_dest_ip( new_ip );
                    mj.setMj_dest_port( new_port );
                    
                    if( mj.isMJStart() ){
                        ok = view.initor.mdb.checkMg( mj.getMj_mg_id() );
                        if( ok ){
                            ok = view.initor.mdb.startMj( mj.getMj_id() );
                            if( !ok ){
                                if( start_mj_isFirst ){
                                    start_mj_err_buf.append( strMj_Name );
                                    start_mj_isFirst = false;                               
                                }else{
                                    start_mj_err_buf.append( "," + strMj_Name );
                                }
                            }
                        }else{
                            ok = view.initor.mdb.startMg( mj.getMj_mg_id() );
                            if( ok ){
                                ok = view.initor.mdb.startMj( mj.getMj_id() );
                                if( !ok ){
                                    if( start_mj_isFirst ){
                                        start_mj_err_buf.append( strMj_Name );
                                        start_mj_isFirst = false;            
                                    }else{
                                        start_mj_err_buf.append( "," + strMj_Name );                      
                                    }
                                }
                            }else{
                                start_mj_err_buf.append( "," + strMj_Name );                      
                            }
                        }
                    }
                }
            }
            
            boolean isRealOk = mod_mj_err_buf.toString().length() == 0 && start_mj_err_buf.toString().length() == 0;
            if( !isRealOk ){
                if( mod_mj_err_buf.toString().length() != 0 ){
                    errMsg = SanBootView.res.getString("MenuAndBtnCenter.error.modmj") + mod_mj_err_buf.toString();
                }
                if( start_mj_err_buf.toString().length() != 0 ){
                    errMsg += "\n" + SanBootView.res.getString("MenuAndBtnCenter.error.startmj") + start_mj_err_buf.toString();
                }
            }            
            return isRealOk;           
        }
        
        private boolean realModUWSrvNode( UWSrvNode selSrv,String new_ip,int new_port, String new_psn ){
            UWSrvNode newSrv = new UWSrvNode(
                selSrv.getUws_id(),
                new_ip,
                new_port,
                new_psn
            );
            boolean isOK = view.initor.mdb.modUWSrv( newSrv );             
            if( isOK ){
                // 用新值替换旧值.这样GUI上所有地方都该改过来了
                selSrv.setUws_ip( newSrv.getUws_ip() );
                selSrv.setUws_port( newSrv.getUws_port() );
                selSrv.setUws_psn( new_psn );
                
                // 显示点击 chiefDestUWSrvNode 后的右边tabpane中的内容����
                BrowserTreeNode chiefDestUWSrvNode = view.getChiefNodeOnRoot( ResourceCenter.TYPE_CHIEF_DEST_UWS );
                if( chiefDestUWSrvNode !=null ){
                    view.setCurNode( chiefDestUWSrvNode );
                    view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                    ProcessEventOnChiefDestUWS peOnChiefDestUWS = new ProcessEventOnChiefDestUWS( view );
                    TreePath path = new TreePath( chiefDestUWSrvNode.getPath() );
                    peOnChiefDestUWS.processTreeSelection( path );
                    peOnChiefDestUWS.controlMenuAndBtnForTreeEvent();
                    view.getTree().setSelectionPath( path );
                    view.getTree().requestFocus();
                }
            }else{
                errMsg = ResourceCenter.getCmd( ResourceCenter.CMD_MOD_UWS_SRV )+":"+
                        view.initor.mdb.getErrorMessage();
            }
            
            return isOK;
        }
    }
}

class DhcpSet extends GeneralActionForMainUi{    
    public DhcpSet(){
        super(
            ResourceCenter.SMALL_DHCP, 
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.dhcp",
            MenuAndBtnCenterForMainUi.FUNC_DHCP
        );
    }
    
    @Override public void doAction(ActionEvent evt){  
SanBootView.log.info(getClass().getName(),"########### Entering dhcp operation action. " );                 
        DhcpDialog dialog = new DhcpDialog( view );
        
        // 图形化的显示初始化过程
        InitProgramDialog initDiag = new InitProgramDialog( 
            view,
            SanBootView.res.getString("DhcpDialog.initTitle"),
            SanBootView.res.getString("DhcpDialog.initLabel")
        );
        Thread initThread = new Thread( new LaunchSomething( initDiag,dialog ) );
        initThread.start();
        int width  = 300+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 120+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        initDiag.setSize( width, height );
        initDiag.setLocation( view.getCenterPoint(width,height) ); 
        initDiag.setVisible( true );
        
        width  = 480+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        height = 465+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of dhcp operation acion. " );                 
    }
}

class MsgReportAction extends GeneralActionForMainUi{    
    public MsgReportAction(){
        super(
            ResourceCenter.BTN_ICON_RPT_16, 
            ResourceCenter.BTN_ICON_RPT_50, 
            "View.MenuItem.msgReport",
            MenuAndBtnCenterForMainUi.FUNC_MSG_REPORT
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        UWSRptConf conf;
SanBootView.log.info(getClass().getName(),"########### Entering message report action. " );                 
        boolean isOk = view.initor.mdb.getUWSRptConf();
        if( !isOk ){
            conf = new UWSRptConf();
        }else{
            conf = view.initor.mdb.getUWSRptConfContent();
        }
        
        ReportConfDialog dialog = new ReportConfDialog( view,conf );
        int width  = 540+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 455+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of message report action. " );                 
    }
}

class DelDstAgent extends GeneralActionForMainUi {    
    public DelDstAgent(){
        super(
            ResourceCenter.MENU_ICON_BLANK, 
            ResourceCenter.SMALL_EDIT_PASSWORD,  
            "View.MenuItem.delDstAgnt",
            MenuAndBtnCenterForMainUi.FUNC_DEL_DSTAGNT
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering delete dst-agent(netboot host) action ");                 
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        if( !(selObj instanceof DestAgent) )  return;
        
        int ret = JOptionPane.showConfirmDialog(
            view,
            SanBootView.res.getString("MenuAndBtnCenter.confirm18"),
            SanBootView.res.getString("common.confirm"),  //"Confirm",
            JOptionPane.OK_CANCEL_OPTION
        );
        if(  ( ret == JOptionPane.CANCEL_OPTION ) || (  ret == JOptionPane.CLOSED_OPTION) ){
            return;
        }

        DestAgent da = (DestAgent)selObj;
        DelNetBootHost thread = new DelNetBootHost( view,da );
        view.startupProcessDiag(
            SanBootView.res.getString("View.pdiagTitle.delNBHost"),
            SanBootView.res.getString("View.pdiagTip.delNBHost"),
            thread
        );
SanBootView.log.info(getClass().getName(),"########### End of delete dst-agent(netboot host) action. " ); 
    }
    
    class DelNetBootHost extends BasicGetSomethingThread{
        DestAgent da;
        BrowserTreeNode selHostNode;
        BrowserTreeNode fNode;
        
        public DelNetBootHost( SanBootView view,DestAgent da ){
            super( view );
            this.da = da;
            this.selHostNode   = da.getTreeNode();
            this.fNode = da.getFatherNode();
        }
        
        public boolean realRun(){
             if( !delNetBootHost() ){ return false; }
             return this.realDelHost( fNode,selHostNode );                          
        }
        
        private boolean delNetBootHost(){
            int i,size;
            SnapUsage su;
            boolean aIsOk;
            
            int da_id = da.getDst_agent_id();
            aIsOk = view.delConfigFileUsingByNetBootHost( da_id );
            if( !aIsOk ){
                errMsg = view.getErrMsg();
                return false;
            }
            
            ArrayList list = view.initor.mdb.getMSUFromCacheOnDstAgntID( da_id );
            size = list.size();
            for( i=0; i<size; i++ ){
                su = (SnapUsage)list.get(i);
                aIsOk = view.initor.mdb.delMSU( su.getUsage_id() );
                if( !aIsOk ){
                    errMsg = view.initor.mdb.getErrorMessage();
                    return false;
                }else{
                    view.initor.mdb.removeMSUFromCache( su );
                }
            }

            aIsOk = view.initor.mdb.delNBH( da_id );
            if( !aIsOk ){
                errMsg = view.initor.mdb.getErrorMessage();
                return false;
            }
            
            if( da.isWinHost() ){
                view.initor.mdb.delIboot( da.getDst_agent_mac() );
            }
            
            return true;
        }
        
        private boolean realDelHost( BrowserTreeNode chiefHostNode,BrowserTreeNode selHostNode ){
            view.initor.mdb.removeNBHFromCache( da );
            if( selHostNode != null ){
                view.removeNodeFromTree( chiefHostNode, selHostNode );
            }
            
            // 显示点击 chiefHostNode 后的右边tabpane中的内容�
            view.setCurNode( chiefHostNode );
            view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
            ProcessEventOnChiefNetBootHost peOnChiefHost = new ProcessEventOnChiefNetBootHost( view );
            TreePath path = new TreePath( chiefHostNode.getPath() );
            peOnChiefHost.processTreeSelection( path );
            peOnChiefHost.controlMenuAndBtnForTreeEvent();
            view.getTree().setSelectionPath( path );
            view.getTree().requestFocus();
            
            return isOk;
        }
    }
}

class LoginPass extends GeneralActionForMainUi {
    public LoginPass() {
        super(
            ResourceCenter.SMALL_EDIT_PASSWORD,  
            ResourceCenter.SMALL_EDIT_PASSWORD,
            "View.MenuItem.modPass",
            MenuAndBtnCenterForMainUi.FUNC_MOD_PASS
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering login password config action. " );                          
        BackupUser user = view.initor.mdb.getBakUserOnName( view.initor.user );
        if( user == null ) {
SanBootView.log.error( getClass().getName(),"Can't find user which name is : "+ view.initor.user );
            JOptionPane.showMessageDialog(view,
                SanBootView.res.getString("MenuAndBtnCenter.error.findUser")
            );
            return;
        }
        
        dataprotect.ui.InputPasswordDialog dialog = new dataprotect.ui.InputPasswordDialog( view,user );
        int width  = 285+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 230+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
        
        Object _name = dialog.getNewName();
        if( _name == null ) return;
        
        String  name = (String )_name;
        String  newPass = dialog.getNewPasswd();
        
        modUser thread = new modUser( view,user,name,newPass,view.initor.port,view.initor.txIp );
        view.startupProcessDiag(
            SanBootView.res.getString("View.pdiagTitle.modUser"),
            SanBootView.res.getString("View.pdiagTip.modUser"),
            thread
        );
SanBootView.log.info(getClass().getName(),"########### End of login password config action. " );                          
    }
    
    class modUser extends BasicGetSomethingThread {
        private BackupUser user;
        private String name;
        private String pass;
        private int port;
        private String txIp;
        
        public modUser( SanBootView view,BackupUser user,String name,String pass,int port,String txIp){
            super( view );
            this.user = user;
            this.name = name;
            this.pass = pass;
            this.port = port;
            this.txIp = txIp;
        }
        
        public boolean realRun( ){
            boolean isOK = view.initor.mdb.modBackupUser( user.getID(), name,pass );
            if( isOK ){
                user.setUserName( name );
                user.setPasswd( pass );
                view.initor.user = name;
                view.initor.passwd = pass;
                
                // modify dhcp user and dhcp password
                String tgt_srv = view.initor.mdb.getHostName();
                String dhcp_srv = view.initor.dhcpdb.getHostName();
SanBootView.log.debug( getClass().getName()," tgt_srv name: "+ tgt_srv );
SanBootView.log.debug( getClass().getName()," dhcp_srv name: "+ dhcp_srv );                
                if( tgt_srv.length() != 0 && dhcp_srv.length() != 0 && tgt_srv.length() != 0 ){
                    // 被管理的uws server和指定的dhcp是同一台机器�
                    view.initor.dhcp_acct = name;
                    view.initor.dhcp_passwd = pass;
                    if( !saveDhcpConf( view.initor.serverIp,view.initor.port,name,pass ) ){
                        return false;
                    }
                }
                
                ModifyProfileThread modProf = new ModifyProfileThread( view, name, pass, txIp, port );
                if( !modProf.realRun() ){
                    errMsg = modProf.getErrMsg();
                    return false;
                }else{
                    return true;
                }
            }else{
                errMsg = SanBootView.res.getString("InputPasswordDialog.msg.modfail")+
                        view.initor.mdb.getErrorMessage();
                return false;
            }
        }
        
        boolean saveDhcpConf( String ip,int port,String user,String pass ){
            File tmpFile = view.initor.mdb.createTmpFile( ResourceCenter.PREFIX,ResourceCenter.SUFFIX_PROF ); 
            if( tmpFile == null ){
                errMsg = SanBootView.res.getString("common.errmsg.crtTmpFileLocallyFailed");
                return false;
            }
            
            if( pass.length() != 0 ){
                pass = StringUtils.encrypt( pass );
            }
            StringBuffer buf = new StringBuffer("");
            buf.append( ResourceCenter.DHCP_IP +"="+ip );
            buf.append( "\n"+ResourceCenter.DHCP_PORT +"="+port);
            buf.append( "\n"+ResourceCenter.DHCP_ACCT +"="+user);
            buf.append( "\n"+ResourceCenter.DHCP_PASS +"="+pass);
            
            if( !view.initor.mdb.sendFileToServer( tmpFile.getName(),buf.toString() ) ){
                tmpFile.delete();
                errMsg =  SanBootView.res.getString("common.errmsg.sendFileFailed")+" : "+
                    view.initor.mdb.getErrorMessage();
                return false;
            }

            // 将tmpFile move to profile dir
            if( !view.initor.mdb.moveFile( ResourceCenter.TMP_DIR + tmpFile.getName(), ResourceCenter.CLT_IP_CONF+ResourceCenter.DHCP_CONF_FILE ) ) {
                tmpFile.delete();
                errMsg = ResourceCenter.getCmdString( ResourceCenter.CMD_MOD_DHCP)+
                    ": "+
                    SanBootView.res.getString("common.failed");
                 return false;
            }
            tmpFile.delete();
            return true;
        }
    }
}

class LoginSetup extends GeneralActionForMainUi {
    public LoginSetup() {
        super(
          ResourceCenter.SMALL_ADMIN_OPTION,  
          ResourceCenter.SMALL_ADMIN_OPTION,  
          "View.MenuItem.adminOpt",
          MenuAndBtnCenterForMainUi.FUNC_ADMINOPT
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering login setup action. " );                          
        OptionDialog dialog = new OptionDialog( view );
        int width  = 430+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 265+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of login setup action. " );                          
    }
}

class CrtUserAction extends GeneralActionForMainUi{
    public CrtUserAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK, 
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.crtUser",
            MenuAndBtnCenterForMainUi.FUNC_CRT_USER
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering create user action. " );                          
        AddUserDialog dialog = new AddUserDialog( view,null,-1 );
        int width  = 270+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 265+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of create user action. " );                          
    }
}

class DelUserAction extends GeneralActionForMainUi{
    public DelUserAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.delUser",
            MenuAndBtnCenterForMainUi.FUNC_DEL_USER
        );
    }
    
    @Override public void doAction(ActionEvent evt){
    }
}

class ModUserAction extends GeneralActionForMainUi{
    public ModUserAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.modUser",
            MenuAndBtnCenterForMainUi.FUNC_MOD_USER
        );
    }
    
    @Override public void doAction(ActionEvent evt){
    }
}

class ModPasswdAction extends GeneralActionForMainUi{
    public ModPasswdAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.modPasswd",
            MenuAndBtnCenterForMainUi.FUNC_MOD_PASSWD
        );
    }
    
    @Override public void doAction(ActionEvent evt){
    }
}

class AddProfAction extends GeneralActionForMainUi{
    public AddProfAction(){
        super(
            ResourceCenter.ICON_ADD_PROFILE,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.addProf",
            MenuAndBtnCenterForMainUi.FUNC_ADD_PROF
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        int width,height;
        boolean ok;
        
SanBootView.log.info(getClass().getName(),"########### Entering add lp-profile action. " );
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        boolean isChiefProf = ( selObj instanceof ChiefProfile );
        if( !isChiefProf ) return;

        ChiefProfile chiefProf = (ChiefProfile)selObj;
        BrowserTreeNode hostNode = chiefProf.getFatherNode();
        BootHost host = (BootHost)hostNode.getUserObject();
        
        if( !host.isInited() ){
            JOptionPane.showMessageDialog( view,
                SanBootView.res.getString("MenuAndBtnCenter.error.notInited")
            );
            return;
        }

        ArrayList hidenFs = null;
        if( host.isIA() ){
            ok = view.initor.mdb.getOldDiskPartitionTableForUnix( ResourceCenter.CLT_IP_CONF + "/" + host.getID() + ResourceCenter.CONF_OLDDISK );
            if( ok ){
                hidenFs = view.initor.mdb.getIAHidenFsFromOldDiskPartForUnix();
            }else{
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.getHidenFs")
                );
                return;
            }
        }

        BackupClient bkClnt = view.initor.mdb.getBkClntOnUUID( host.getUUID() );
SanBootView.log.info( getClass().getName()," host uuid: "+ host.getUUID() );

        if( bkClnt == null ){
SanBootView.log.warning( getClass().getName()," no d2d client for boot host "+ host.getName() );    
            bkClnt = new BackupClient( host.getName(), host.getIP(), host.getMachine(), host.getMtppPort(), host.getOS(), host.getUUID() );
            ok = view.initor.mdb.addOneClient( bkClnt );
            if( !ok ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_ADD_CLIENT )+
                    ": "+
                    view.initor.mdb.getErrorMessage()
                );
                return;
            }else{
                bkClnt.setID( view.initor.mdb.getNewId() );
                view.initor.mdb.addBakClntIntoCache( bkClnt );
            }
        }else{
            // 换成当前正在使用的ip和port比较保险
            bkClnt.setIP( host.getIP() );
            bkClnt.setPort( host.getMtppPort() );
        }

        String windir="";
        if( host.isWinHost()  ){
            // if failed, re-get windir in EditProfileDialog
            windir = view.initor.mdb.getWinDir( host.getIP(),host.getMtppPort() );
SanBootView.log.info(getClass().getName(), "windir: " + windir );
        }

        EditProfileDialog dialog = new EditProfileDialog( windir,view,host,bkClnt,new ArrayList(0),null,null,hidenFs );
        width  = 560+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        height = 480+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of add profile action. " ); 
    }
}

class ModProfAction extends GeneralActionForMainUi{
    public ModProfAction(){
        super(
            ResourceCenter.ICON_MOD_PROFILE,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.modProf",
            MenuAndBtnCenterForMainUi.FUNC_MOD_PROF
        );
    }
    
    @Override public void doAction( ActionEvent evt ){
        int width,height;
        
SanBootView.log.info(getClass().getName(),"########### Entering modify profile action. " );                          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        boolean isProf = ( selObj instanceof UniProfile );
        if( !isProf ) return;

        UniProfile _prof = (UniProfile)selObj;
        BrowserTreeNode chiefProfNode = _prof.getFatherNode();
        ChiefProfile chiefProf = (ChiefProfile)chiefProfNode.getUserObject();
        BrowserTreeNode hostNode = chiefProf.getFatherNode();
        BootHost host =(BootHost)hostNode.getUserObject();

        ArrayList hidenFs = null;
        if( host.isIA() ){
            boolean ok = view.initor.mdb.getOldDiskPartitionTableForUnix( ResourceCenter.CLT_IP_CONF + "/" + host.getID() + ResourceCenter.CONF_OLDDISK );
            if( ok ){
                hidenFs = view.initor.mdb.getIAHidenFsFromOldDiskPartForUnix();
            }else{
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.getHidenFs")
                );
                return;
            }
        }
        
        // get real profile from cache. contents of profile on GUI maybe older.
        UniProfile prof = view.initor.mdb.getOneProfile( _prof.getProfileName() );
SanBootView.log.info( getClass().getName(),"profile to modify: "+_prof.getProfileName() );            
        if( prof == null ) return;

        BackupClient bkClnt = view.initor.mdb.getBkClntOnUUID( host.getUUID() );
SanBootView.log.info( getClass().getName()," host uuid: "+ host.getUUID() );

        if( bkClnt == null ){
SanBootView.log.error( getClass().getName()," no d2d client for boot host "+ host.getName() );
            JOptionPane.showMessageDialog(view,
                SanBootView.res.getString("common.errcode.inconsistentProf")
            );
            return;
        }else{
            // 换成当前正在使用的ip和port比较保险
            bkClnt.setIP( host.getIP() );
            bkClnt.setPort( host.getPort() );
        }

        String bkobjId = prof.getUniProIdentity().getBkObj_ID();
        // 进去之前先重新获取一下backup object,因为备份的时候bkobj会变化，所以只能update bk obj
        view.initor.mdb.updateBakObjList( bkobjId );
        BakObject bkObj = view.initor.mdb.getOneBakObject();
        if( bkObj == null ){
SanBootView.log.error( getClass().getName()," no d2d bkobj, bkobj id: "+ bkobjId );
            JOptionPane.showMessageDialog(view,
                SanBootView.res.getString("common.errcode.inconsistentProf")
            );
            return;
        }else{
            // update bkboj in cache
            view.initor.mdb.removeBakObjFromVector( bkObj.getBakObjID() );
            view.initor.mdb.addBakObjIntoVector( bkObj );
        }

        String windir ="";
        if( host.isWinHost()  ){
            // if failed, re-get windir in EditProfileDialog
            windir = view.initor.mdb.getWinDir( host.getIP(),host.getMtppPort() );
SanBootView.log.info( getClass().getName(), "windir: " + windir );
        }

        ArrayList schList = view.initor.mdb.getSchOnProfName( prof.getProfileName() );

        EditProfileDialog dialog = new EditProfileDialog( windir,view, host, bkClnt, schList, bkObj,prof,hidenFs );
        width  = 560+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        height = 480+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of modify profile action. " );
    }
}
        
class DelProfAction extends GeneralActionForMainUi{
    public DelProfAction(){
        super(
            ResourceCenter.ICON_DEL_PROFILE,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.delProf",
            MenuAndBtnCenterForMainUi.FUNC_DEL_PROF
        );
    }
    
    @Override public void doAction(ActionEvent evt){        
SanBootView.log.info(getClass().getName(),"########### Entering delete profile action. " );                          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        boolean isProf = ( selObj instanceof UniProfile );
        if( isProf ){
            UniProfile _prof = (UniProfile)selObj;
            BrowserTreeNode chiefProfNode = _prof.getFatherNode();
            ChiefProfile chiefProf = (ChiefProfile)chiefProfNode.getUserObject(); 
            BrowserTreeNode hostNode = chiefProf.getFatherNode();
            BootHost host =(BootHost)hostNode.getUserObject();
            
            UniProfile prof = view.initor.mdb.getOneProfile( _prof.getProfileName() );
SanBootView.log.info( getClass().getName(),"profile to delete: " + _prof.getProfileName() );    
            if( prof == null ) return;

            int ret = JOptionPane.showConfirmDialog(
                view,
                SanBootView.res.getString("MenuAndBtnCenter.confirm15"),
                SanBootView.res.getString("common.confirm"),  //"Confirm",
                JOptionPane.OK_CANCEL_OPTION
            );
            if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
                return;
            }
            
            String bkobjId = prof.getUniProIdentity().getBkObj_ID();
SanBootView.log.info( getClass().getName(),"profile to delete: " + prof.getProfileName() );            
            BakObject bkObj = view.initor.mdb.getBakObjFromVector( bkobjId );
            if( bkObj != null ){
                if( !view.initor.mdb.deleteBakObj( bkObj.getBakObjID() ) ){
                    JOptionPane.showMessageDialog(view,
                        ResourceCenter.getCmdString( ResourceCenter.CMD_DEL_BAKOBJECT )+
                        ": "+
                        view.initor.mdb.getErrorMessage()
                    );
                    return;
                }else{
                    view.initor.mdb.removeBakObjFromVector( bkObj );
                }
            }
            
            // 不要删除d2d client,因为要与FIVStorEX共享这个client.
            
            ArrayList schList = view.initor.mdb.getSchOnProfName( prof.getProfileName() );
            int size = schList.size();
            for( int i=0; i<size; i++ ){
                DBSchedule sch = (DBSchedule)schList.get(i);
                if( !view.initor.mdb.deleteOneScheduler( sch ) ){
                    JOptionPane.showMessageDialog(view,
                        ResourceCenter.getCmdString( ResourceCenter.CMD_DEL_DB_SCHEDULER ) +
                        ": "+
                        view.initor.mdb.getErrorMessage()
                    );
                    return;
                }else{
                    view.initor.mdb.removeSch( sch );
                }
            }

            Audit audit = view.audit.registerAuditRecord( host.getID(), MenuAndBtnCenterForMainUi.FUNC_DEL_PROF );

            if( !view.initor.mdb.delFile( prof.getProfileName()  ) ){
                audit.setEventDesc( "Failed to delete profile: "+ prof.getProfileNameWithoutExtName() );
                view.audit.addAuditRecord( audit );

                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_DEL_FILE ) +
                    ": "+
                    view.initor.mdb.getErrorMessage()
                );
            }else{
                audit.setEventDesc( "Delete profile: "+ prof.getProfileNameWithoutExtName() + " successfully." );
                view.audit.addAuditRecord( audit );

                view.initor.mdb.removeProfFromCache( prof );
                
                    // refresh chiefProfile
                    BrowserTreeNode selProfNode = view.getProfNodeOnChiefProf( chiefProfNode,prof.toString() );
                    if( selProfNode!= null ){
                        view.removeNodeFromTree( chiefProfNode, selProfNode );
                    }
                    view.setCurNode( chiefProfNode );
                    view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                    ProcessEventOnChiefProfile peOnChiefProf = new ProcessEventOnChiefProfile( view ); 
                    TreePath path = new TreePath( chiefProfNode.getPath() );
                    peOnChiefProf.processTreeSelection( path );
                    peOnChiefProf.controlMenuAndBtnForTreeEvent();
                    view.getTree().setSelectionPath( path );
                    view.getTree().requestFocus();
            }
        }
SanBootView.log.info(getClass().getName(),"########### End of delete profile action. " );                          
    }
}    

class RenameProfAction extends GeneralActionForMainUi{
    UniProfile prof;
    String oldName;
    
    public RenameProfAction(){
        super(
            ResourceCenter.MENU_ICON_BLANK,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.renameProf",
            MenuAndBtnCenterForMainUi.FUNC_RENAME_PROF
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering rename profile action. " );                          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        boolean isProf = ( selObj instanceof UniProfile );
        if( isProf ){
            UniProfile aprof = (UniProfile)selObj;
            BrowserTreeNode chiefProfNode = aprof.getFatherNode();
            ChiefProfile chiefProf = (ChiefProfile)chiefProfNode.getUserObject();
            BrowserTreeNode hostNode = chiefProf.getFatherNode();
            BootHost host =(BootHost)hostNode.getUserObject();
            
            prof = view.initor.mdb.getOneProfile( aprof.getProfileName() );
SanBootView.log.info( getClass().getName(),"profile to rename: " + aprof.getProfileName() );    
            if( prof == null ) return;
            
            oldName = prof.getProfileName();
            ArrayList schList = view.initor.mdb.getSchOnProfName( oldName );
            
            InputNameDialog dialog = new InputNameDialog( view,prof.toString() ); 
            int width = 275+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 165+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
            
            Object[] ret = dialog.getValues();
            if( ret == null || ret.length <=0 ) return;
            
            String newName = ResourceCenter.PROFILE_DIR + (String)ret[0];
            
            File tmpFile = view.initor.mdb.createTmpFile( ResourceCenter.PREFIX,ResourceCenter.SUFFIX_PROF );
            if( tmpFile == null ){
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("common.errmsg.crtTmpFileLocallyFailed")
                );
                return;
            }
            
            prof.setProfileName( newName );
            prof.setHeaderProfileName();
            prof.setIdentityProfileName();
            
            // 发送profile的内容
            if( !view.initor.mdb.sendFileToServer( tmpFile.getName(),prof.prtMe() ) ){
                tmpFile.delete();
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("common.errmsg.sendFileFailed")+" : "+
                    view.initor.mdb.getErrorMessage()
                );
                // 失败, 回退
                undo_rename_profile();
                return;
            }    

            Audit audit = view.audit.registerAuditRecord( host.getID(), MenuAndBtnCenterForMainUi.FUNC_RENAME_PROF );

            boolean ok = view.initor.mdb.moveFile(
                ResourceCenter.TMP_DIR + tmpFile.getName(), 
                newName
            );
            if( ok ){
                audit.setEventDesc( "Rename profile from " + oldName + " to " + newName + " successfully." );
                view.audit.addAuditRecord( audit );

                // del older profile
                view.initor.mdb.delFile( oldName );
                
                // modify scheduler related with older profile
                int size = schList.size();
                for( int i=0; i<size; i++ ){
                    DBSchedule sch = (DBSchedule)schList.get(i);
                    sch.setProfName( newName );
                    // 不管结果
                    view.initor.mdb.addOneScheduler( sch ); 
                }
                
                // refresh chiefProfile
//                if( chiefProfNode != null ){
                    view.setCurNode( chiefProfNode );
                    view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                    ProcessEventOnChiefProfile peOnChiefProf = new ProcessEventOnChiefProfile( view ); 
                    TreePath path = new TreePath( chiefProfNode.getPath() );
                    peOnChiefProf.processTreeSelection( path );
                    peOnChiefProf.controlMenuAndBtnForTreeEvent();
                    view.getTree().setSelectionPath( path );
                    view.getTree().requestFocus();
//                }
            }else{
                audit.setEventDesc( "Failed to rename profile from " + oldName + " to " + newName );
                view.audit.addAuditRecord( audit );

                // 失败, 回退
                undo_rename_profile();
                tmpFile.delete();
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_RENAME_PROFILE )+ 
                    ": "+
                    view.initor.mdb.getErrorMessage()
                );
                return;
            }
            tmpFile.delete();
        }
SanBootView.log.info(getClass().getName(),"########### Entering rename profile action. " );                          
    }
    
    private void undo_rename_profile(){
        prof.setProfileName( oldName );
        prof.setHeaderProfileName();
        prof.setIdentityProfileName();
    }
}

class RunProfAction extends GeneralActionForMainUi{
    public RunProfAction(){
        super(
            ResourceCenter.ICON_RUN_PROFILE,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.runProf",
            MenuAndBtnCenterForMainUi.FUNC_RUN_PROF
        );
    }
    
    @Override public void doAction(ActionEvent evt){  
SanBootView.log.info(getClass().getName(),"########### Entering run profile action. " );                          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        boolean isProf = ( selObj instanceof UniProfile );
        if( isProf ){
            UniProfile _prof = (UniProfile)selObj;
            BrowserTreeNode fNode = _prof.getFatherNode();
            ChiefProfile chiefProf = (ChiefProfile)fNode.getUserObject();
            fNode = chiefProf.getFatherNode();
            BootHost host = (BootHost)fNode.getUserObject();
            
            // get real profile from cache. contents of profile on GUI maybe older.
            UniProfile prof = view.initor.mdb.getOneProfile( _prof.getProfileName() );
SanBootView.log.info( getClass().getName(),"profile to run: " + _prof.getProfileName() );             
            if( prof == null ) {
SanBootView.log.error( getClass().getName(), "not found profile from memory cache.");
                return;
            }
            
            // 必须是初始化好的，否则profile中的target可能不对��ܲ���
            if( !host.isInited() ){
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("MenuAndBtnCenter.error.notInited")
                );
                return;   
            }

            String bkClntId = prof.getUniProIdentity().getClntID();
            BackupClient bkClnt = view.initor.mdb.getClientFromVectorOnID( bkClntId );
            if (bkClnt == null) return;
            if( bkClnt.getPort() != host.getMtppPort() ){
                // 容错性代码。2011.5.24
                bkClnt.setPort( host.getMtppPort() );
            }

            if( !view.initor.mdb.checkProfile( prof ) ){
                JOptionPane.showMessageDialog( view,
                    view.initor.mdb.getProfErrMsg()
                );
                return;
            }
            
            UniProIdentity identity = prof.getUniProIdentity();
            String bkobjId = prof.getUniProIdentity().getBkObj_ID();
            // 进去之前，先重新获取一下backup object list,
            // 因为备份的时候，bkobj会变化，所以只能
            // update all bk obj 
            view.initor.mdb.updateBakObjList( bkobjId );
            BakObject bkObj = view.initor.mdb.getOneBakObject();
            if( bkObj == null ){
SanBootView.log.error( getClass().getName()," missing bkobj in profile: "+prof.getProfileName() );   
                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("EditProfileDialog.error.lostBakObj")
                );
                return;
            }
            identity.setBkObj_SN( bkObj.getBakObjSN() +"" );
            
            File tmpFile = view.initor.mdb.createTmpFile( ResourceCenter.PREFIX,ResourceCenter.SUFFIX_PROF );
            if( tmpFile == null ){
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("common.errmsg.crtTmpFileLocallyFailed")
                );
                return;
            }
            
            // 发送profile的内容
            if( !view.initor.mdb.sendFileToServer( tmpFile.getName(),prof.prtMe() ) ){
                tmpFile.delete();
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("common.errmsg.sendFileFailed")+" : "+
                    view.initor.mdb.getErrorMessage()
                );    
                return;
            }    
            
            // 将tmpFile move to profile dir
            boolean ok = view.initor.mdb.moveFile(
                ResourceCenter.TMP_DIR + tmpFile.getName(), 
                ResourceCenter.PROFILE_DIR + prof.toString()
            );
            tmpFile.delete();
            if( !ok ){
                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_MOD_PROFILE )+
                    ": "+
                    SanBootView.res.getString("common.failed")
                );
                return;
            }
            
            // 开始备份
            RunBackup dup = null;
            try{
SanBootView.log.info( getClass().getName(),"data-dup profile: \n" + prof.prtMe() ); 
                dup = new RunBackup( 
                    ResourceCenter.getCmd( ResourceCenter.CMD_DATA_DUP )+ 
                    prof.getProfileName() +
                    " -clnt=" +
                    bkClnt.getID(),
                    view.getSocket()
                );
                
SanBootView.log.info( getClass().getName(), " data-duplication cmd: " + dup.getCmdLine() );

                dup.run();
            }catch( Exception ex ){
                if(dup != null){
                    dup.setExceptionErrMsg( ex);
                    dup.setExceptionRetCode( ex );
                } else {
                    return;
                }
            }
            
    SanBootView.log.info( getClass().getName(), " data-duplication cmd retcode: " + dup.getRetCode() );

            Audit audit = view.audit.registerAuditRecord( host.getID(), MenuAndBtnCenterForMainUi.FUNC_RUN_PROF );
            
            if( dup.isOk() ){
                audit.setEventDesc( "Run profile: " + prof.getProfileNameWithoutExtName() + " successfully." );
                view.audit.addAuditRecord( audit );

                MonitorDialog dialog = new MonitorDialog( view );
                TaskListGeter geter = new TaskListGeter(
                    dialog,
                    view
                );
                geter.start();
                
                int width  = 585+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
                int height = 335+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
                dialog.setSize( width,height );
                dialog.setLocation( view.getCenterPoint( width,height ) );
                
                dialog.setVisible( true );       
            }else{
                audit.setEventDesc( "Failed to run profile: " + prof.getProfileNameWithoutExtName() );
                view.audit.addAuditRecord( audit );

SanBootView.log.error( getClass().getName(), " data-duplication cmd errmsg: " + dup.getErrMsg() );    
                JOptionPane.showMessageDialog(view,
                    SanBootView.res.getString("EditProfileDialog.error.bakCmdFail")
                );
            }
        }
SanBootView.log.info(getClass().getName(),"########### End of run profile action. " );                          
    }
}

class VerifyProfAction extends GeneralActionForMainUi{
    public VerifyProfAction(){
        super(
            ResourceCenter.ICON_VERIFY_PROFILE,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.verifyProf",
            MenuAndBtnCenterForMainUi.FUNC_VERIFY_PROF
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering verify profile action. " );                          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        
        boolean isProf = ( selObj instanceof UniProfile );
        if( isProf ){
            UniProfile _prof = (UniProfile)selObj;
            BrowserTreeNode fNode = _prof.getFatherNode();
            ChiefProfile chiefProf = (ChiefProfile)fNode.getUserObject();
            fNode = chiefProf.getFatherNode();
            BootHost host = (BootHost)fNode.getUserObject();
            
            // get real profile from cache. contents of profile on GUI maybe older.
            UniProfile prof = view.initor.mdb.getOneProfile( _prof.getProfileName() );
SanBootView.log.info( getClass().getName(),"profile to verify: " + _prof.getProfileName() );                
            if( prof == null ) return;

            Audit audit = view.audit.registerAuditRecord( host.getID(), MenuAndBtnCenterForMainUi.FUNC_VERIFY_PROF );
            
            if( !view.initor.mdb.checkProfile( prof ) ){
                audit.setEventDesc( "Failed to verify profile: " + prof.getProfileNameWithoutExtName() + " successfully." );
                view.audit.addAuditRecord( audit );

                JOptionPane.showMessageDialog( view,
                    view.initor.mdb.getProfErrMsg()
                );
            }else{
                audit.setEventDesc( "Verify profile: " + prof.getProfileNameWithoutExtName() + " successfully." );
                view.audit.addAuditRecord( audit );

                JOptionPane.showMessageDialog( view,
                    SanBootView.res.getString("common.errcode.consistentProf")
                );
            }
        }
SanBootView.log.info(getClass().getName(),"########### End of verify profile action. " );                          
    }
}

class AddSchAction extends GeneralActionForMainUi{
    public AddSchAction(){
        super(
            ResourceCenter.SMALL_ADD_SCH,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.addSch",
            MenuAndBtnCenterForMainUi.FUNC_ADD_SCH
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        SchedDialog dialog;
        UniProfile _prof=null,prof;
        
SanBootView.log.info(getClass().getName(),"########### Entering add scheduler action. " );                          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ){
            dialog = new SchedDialog( view,null, null ); 
        }else{
            if( selObj instanceof UniProfile ){
                _prof = (UniProfile)selObj;
                
                 // get real profile from cache. Contents of profile on GUI maybe older.
                prof = view.initor.mdb.getOneProfile( _prof.getProfileName() );
SanBootView.log.info( getClass().getName(),"profile to add scheduler: " + _prof.getProfileName() );                 
                if( prof == null ) return;
                
//System.out.println(" prof : \n "+ prof.prtMe() ); 
                if( !view.initor.mdb.checkProfile( prof ) ){
                    JOptionPane.showMessageDialog( view,
                        view.initor.mdb.getProfErrMsg()
                    );
                    return;
                }            
                dialog = new SchedDialog( view,null,prof );
            }else{
                dialog = new SchedDialog( view,null,null );
            }
        }
        
        int width  = 585+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 400+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
        
        Object[] ret = dialog.getValues();
        if( ret == null ) return;
        
        DBSchedule dbSche = (DBSchedule)ret[0];

        String profName = dbSche.getProfName();
        UniProfile prof1 = view.initor.mdb.getOneProfile( profName );
        if(prof1 == null) return;
        UniProIdentity identity = prof1.getUniProIdentity();
        String cltid = identity.getClntID();
        BackupClient bk_clnt = view.initor.mdb.getClientFromVectorOnID( cltid );
        int clnt_id = 0;
        if( bk_clnt != null ){
            BootHost host = view.initor.mdb.getHostFromCacheOnUUID( bk_clnt.getUUID() );
            if( host != null ){
                clnt_id = host.getID();
            }
        }

        Audit audit = view.audit.registerAuditRecord( clnt_id, MenuAndBtnCenterForMainUi.FUNC_ADD_SCH );

        boolean ok = view.initor.mdb.addOneScheduler( dbSche ); 
        if( ok ){
            dbSche.setID( view.initor.mdb.getNewId() );           
            view.initor.mdb.addSchIntoCache( dbSche );

            // must be after setting newid to dbsche
            audit.setEventDesc( "Add scheduler: " + dbSche.getName() + " successfully.");
            view.audit.addAuditRecord( audit );
        }else{
            audit.setEventDesc( "Failed to add scheduler: " + dbSche.getName() );
            view.audit.addAuditRecord( audit );

            JOptionPane.showMessageDialog(view,
                ResourceCenter.getCmdString( ResourceCenter.CMD_ADD_DB_SCHEDULER )+
                ": "+
                view.initor.mdb.getErrorMessage()
            );
            return;
        }
        
//        if( ok ){
            if( _prof != null ){
                BrowserTreeNode chiefProfNode = _prof.getFatherNode();
                if( chiefProfNode != null ){
                    view.setCurNode( chiefProfNode );
                    view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                    ProcessEventOnChiefProfile peOnChiefProf = new ProcessEventOnChiefProfile( view ); 
                    TreePath path = new TreePath( chiefProfNode.getPath() );
                    peOnChiefProf.processTreeSelection( path );
                    peOnChiefProf.controlMenuAndBtnForTreeEvent();
                    view.getTree().setSelectionPath( path );
                    view.getTree().requestFocus();
                }
            }else{
                BrowserTreeNode chiefSchNode = view.getChiefNodeOnRoot( ResourceCenter.TYPE_CHIEF_SCH );
                if( chiefSchNode != null ){
                    view.setCurNode( chiefSchNode );
                    view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                    ProcessEventOnChiefSch peOnChiefSch = new ProcessEventOnChiefSch( view ); 
                    TreePath path = new TreePath( chiefSchNode.getPath() );
                    peOnChiefSch.processTreeSelection( path );
                    peOnChiefSch.controlMenuAndBtnForTreeEvent();
                    view.getTree().setSelectionPath( path );
                    view.getTree().requestFocus();
                }
            }
//        }
SanBootView.log.info(getClass().getName(),"########### End of add scheduler action. " );                          
    }
}
     
class ModSchAction extends GeneralActionForMainUi{
    public ModSchAction(){
        super(
            ResourceCenter.SMALL_MOD_SCH,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.modSch",
            MenuAndBtnCenterForMainUi.FUNC_MOD_SCH
        );
    }
    
    @Override public void doAction(ActionEvent evt){
        SchedDialog dialog;
        BrowserTreeNode fNode;
SanBootView.log.info(getClass().getName(),"########### Entering modify scheduler action. " );                          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        boolean isSch = ( selObj instanceof DBSchedule );
        if( isSch ){
            DBSchedule sch = (DBSchedule)selObj;
            
            fNode = sch.getFatherNode();
            if( fNode == null ){
                dialog = new SchedDialog( view,sch,null ); 
            }else{
                Object usrObj = fNode.getUserObject();
                if( usrObj instanceof UniProfile ){
                    UniProfile prof = (UniProfile)usrObj;
                    dialog = new SchedDialog( view,sch, prof );
                }else{
                    dialog = new SchedDialog( view,sch,null );
                }
            }
            
            int width  = 585+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
            int height = 400+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
            dialog.setSize( width,height );
            dialog.setLocation( view.getCenterPoint( width,height ) );
            dialog.setVisible( true );
            
            Object[] ret = dialog.getValues();
            if( ret == null ) return;
            
            DBSchedule dbSche = (DBSchedule)ret[0]; 
            dbSche.setID( sch.getID() );

            String profName = dbSche.getProfName();
            UniProfile prof1 = view.initor.mdb.getOneProfile( profName );
            if(prof1 == null) return;
            UniProIdentity identity = prof1.getUniProIdentity();
            String cltid = identity.getClntID();
            BackupClient bk_clnt = view.initor.mdb.getClientFromVectorOnID( cltid );
            int clnt_id = 0;
            if( bk_clnt != null ){
                BootHost host = view.initor.mdb.getHostFromCacheOnUUID( bk_clnt.getUUID() );
                if( host != null ){
                    clnt_id = host.getID();
                }
            }
            Audit audit = view.audit.registerAuditRecord( clnt_id, MenuAndBtnCenterForMainUi.FUNC_MOD_SCH );

            boolean ok = view.initor.mdb.addOneScheduler( dbSche ); 
            if( ok ){
                audit.setEventDesc( "Modify scheduler: " + dbSche.getName() + " successfully.");
                view.audit.addAuditRecord( audit );

                view.initor.mdb.removeSchFromCache( sch );
                view.initor.mdb.addSchIntoCache( dbSche );
            }else{
                audit.setEventDesc( "Failed to modify scheduler: " + dbSche.getName() );
                view.audit.addAuditRecord( audit );

                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_MOD_DB_SCHEDULER )+
                    ": "+
                    view.initor.mdb.getErrorMessage()
                );
                return;
            }
            
            if( ok ){
                fNode = sch.getFatherNode();
                ProcessEvent processEvent = null;
                if( fNode != null ){ 
                    view.setCurNode( fNode );
                    view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                    Object userObj = fNode.getUserObject();
                    if( userObj instanceof UniProfile ){ // is a profile tree node 
                        processEvent = new ProcessEventOnProfile( view ); 
                    }else if( userObj instanceof ChiefScheduler ){
                        processEvent = new ProcessEventOnChiefSch( view ); 
                    }
                }else{
                    fNode = view.getChiefNodeOnRoot( ResourceCenter.TYPE_CHIEF_SCH );
                    if(fNode != null){
                        view.setCurNode( fNode );
                        view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                        processEvent = new ProcessEventOnChiefSch( view );
                    }
                }
                if( processEvent != null && fNode !=null ){
                    TreePath path = new TreePath( fNode.getPath() );
                    processEvent.processTreeSelection( path );
                    processEvent.controlMenuAndBtnForTreeEvent();
                    view.getTree().setSelectionPath( path );
                    view.getTree().requestFocus();
                }
            }
        }
SanBootView.log.info(getClass().getName(),"########### End of modify scheduler action. " );                          
    }
}
     
class DelSchAction extends GeneralActionForMainUi{
    public DelSchAction(){
        super(
            ResourceCenter.SMALL_DEL_SCH,
            ResourceCenter.MENU_ICON_BLANK, 
            "View.MenuItem.delSch",
            MenuAndBtnCenterForMainUi.FUNC_DEL_SCH
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering delete scheduler action. " );                          
        Object selObj = view.getSelectedObjFromSanBoot();
        if( selObj == null ) return;
        boolean isSch = ( selObj instanceof DBSchedule );
        if( isSch ){
            DBSchedule sch = (DBSchedule)selObj;
            
            int ret = JOptionPane.showConfirmDialog(
                view,
                SanBootView.res.getString("MenuAndBtnCenter.confirm14"),
                SanBootView.res.getString("common.confirm"),  //"Confirm",
                JOptionPane.OK_CANCEL_OPTION
            );
            if( ( ret == JOptionPane.CANCEL_OPTION ) || ( ret == JOptionPane.CLOSED_OPTION) ){
                return;
            }

            String profName = sch.getProfName();
            UniProfile prof1 = view.initor.mdb.getOneProfile( profName );
            if(prof1 == null) return;
            UniProIdentity identity = prof1.getUniProIdentity();
            String cltid = identity.getClntID();
            BackupClient bk_clnt = view.initor.mdb.getClientFromVectorOnID( cltid );
            int clnt_id = 0;
            if( bk_clnt != null ){
                BootHost host = view.initor.mdb.getHostFromCacheOnUUID( bk_clnt.getUUID() );
                if( host != null ){
                    clnt_id = host.getID();
                }
            }
            Audit audit = view.audit.registerAuditRecord( clnt_id, MenuAndBtnCenterForMainUi.FUNC_DEL_SCH );

            boolean ok = view.initor.mdb.deleteOneScheduler( sch ); 
            if( ok ){
                audit.setEventDesc( "Delete scheduler: " + sch.getName() + " successfully." );
                view.audit.addAuditRecord( audit );

                view.initor.mdb.removeSchFromCache( sch );  
            }else{
                audit.setEventDesc( "Failed to delete scheduler: " + sch.getName() );
                view.audit.addAuditRecord( audit );

                JOptionPane.showMessageDialog(view,
                    ResourceCenter.getCmdString( ResourceCenter.CMD_DEL_DB_SCHEDULER )+
                    ": "+
                    view.initor.mdb.getErrorMessage()
                );
                return;
            }
            
//            if( ok ){
                BrowserTreeNode fNode = sch.getFatherNode();
                ProcessEvent processEvent = null;
                if( fNode != null ){ 
                    view.setCurNode( fNode );
                    view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                    Object userObj = fNode.getUserObject();
                    if( userObj instanceof UniProfile ){ // is a profile tree node 
                        processEvent = new ProcessEventOnProfile( view ); 
                    }else if( userObj instanceof ChiefScheduler ){
                        processEvent = new ProcessEventOnChiefSch( view ); 
                    }
                }else{
                    fNode = view.getChiefNodeOnRoot( ResourceCenter.TYPE_CHIEF_SCH );
                    if(fNode != null) {
                        view.setCurNode( fNode );
                        view.setCurBrowserEventType( Browser.TREE_SELECTED_EVENT );
                        processEvent = new ProcessEventOnChiefSch( view ); }
                }
                if(processEvent != null && fNode !=null ){
                    TreePath path = new TreePath( fNode.getPath() );
                    processEvent.processTreeSelection( path );
                    processEvent.controlMenuAndBtnForTreeEvent();
                    view.getTree().setSelectionPath( path );
                    view.getTree().requestFocus();
                }
//            }
        }
SanBootView.log.info(getClass().getName(),"########### End of delete scheduler action. " );                          
    }
}
      
class DupLogAction extends GeneralActionForMainUi{
    public DupLogAction(){
        super(
            ResourceCenter.ICON_DUP_LOG_16, 
            ResourceCenter.ICON_DUP_LOG_50, 
            "View.MenuItem.duplog",
            MenuAndBtnCenterForMainUi.FUNC_DUP_LOG
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering dup-log browser action");        
        BackupHistoryDialog dialog = new BackupHistoryDialog( view );
        
        TaskLogGeter geter = new TaskLogGeter(
            view, 
            dialog,
            1,
            50
        );
        geter.start();
        
        int width  = 725+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 550+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of dup-log browser action. " );                          
    }
}

class MonitorAction extends GeneralActionForMainUi{
    public MonitorAction(){
        super(
            ResourceCenter.BTN_ICON_MON_16,
            ResourceCenter.BTN_ICON_MON_50, 
            "View.MenuItem.mon",
            MenuAndBtnCenterForMainUi.FUNC_MONITOR_DD
        );
    }
    
    @Override public void doAction(ActionEvent evt){
SanBootView.log.info(getClass().getName(),"########### Entering task monitor action. " );                          
        MonitorDialog dialog = new MonitorDialog( view );
        TaskListGeter geter = new TaskListGeter(
            dialog,
            view
        );
        geter.start();

        int width  = 765+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 385+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of task monitor action. " );                          
    }
}

class SnapTreeAction extends GeneralActionForMainUi{
    public SnapTreeAction(){
        super(
            ResourceCenter.BTN_ICON_MON_16,
            ResourceCenter.BTN_ICON_MON_50,
            "View.MenuItem.mon",
            MenuAndBtnCenterForMainUi.FUNC_SNAP_TREE
        );
    }

    @Override public void doAction(ActionEvent evt){
        SnapTreeDialog dialog = new SnapTreeDialog( view );
        int width  = 600+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 480+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setTitle( "Snapshot Tree");
        dialog.setVisible( true );
    }
}

class AboutAction extends GeneralActionForMainUi {
    public AboutAction() {
        super(
          ResourceCenter.BTN_ICON_INFO_16,
          ResourceCenter.BTN_ICON_ABOUT_20,
          "View.MenuItem.about",
          MenuAndBtnCenterForMainUi.FUNC_ABOUT
        );
    }
    
    @Override public void doAction( ActionEvent evt ){     
SanBootView.log.info(getClass().getName(),"########### Entering about-help action. " );                          
        JDialog dialog = new AboutDialog(
            view, 
            SanBootView.res.getString("common.product") +" Manager GUI",
            ResourceCenter.BTN_ICON_LOGO
        );
        int width  = 380+ResourceCenter.GLOBAL_DELTA_WIDTH_SIZE;
        int height = 190+ResourceCenter.GLOBAL_DELTA_HIGH_SIZE;
        dialog.setSize( width,height );
        dialog.setLocation( view.getCenterPoint( width,height ) );
        dialog.setTitle( SanBootView.res.getString("AboutDialog.dialog_title"));
        dialog.setVisible( true );
SanBootView.log.info(getClass().getName(),"########### End of about-help action. " );                          
    }
}
