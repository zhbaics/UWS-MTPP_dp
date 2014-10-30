package dataprotect;

import dataprotect.audit.data.Audit;
import dataprotect.data.GUIAdminOptUWS;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dataprotect.res.ResourceCenter;
import dataprotect.tool.UWSSockConnectManager;
import dataprotect.ui.SanBootView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import mylib.UI.ImagePanel;
import mylib.tool.Check;

public class WelcomFrame {
    private JFrame jframe;
    private JLabel jLabel,jLabel1,jLabel2,jLabel3,tmp_label,err_label;
    private GridBagLayout gridbag;
    private JTextField jtfield2,jtfield3;
    private JComboBox jComboBox_server;
    private JPasswordField jpfield1;
    private JButton jbutton1,jbutton2;
    private Font font = new Font("宋体",0,18);
    private Font font1 = new Font("宋体",0,16);
    private ImagePanel jpanel = new ImagePanel( null, ImagePanel.SCALED );

    public WelcomFrame(){
        jframe = new JFrame();
        tmp_label = new JLabel();
        err_label = new JLabel();
        jLabel = new JLabel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jComboBox_server = new JComboBox();
        jComboBox_server.setEditable( true );
        this.jComboBox_server.setPreferredSize(new java.awt.Dimension(150, 22));
        jComboBox_server.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        
        jtfield2 = new JTextField();
        jtfield3 = new JTextField();
        jpfield1 = new JPasswordField();
        gridbag  = new GridBagLayout();
        jbutton1 = new JButton();
        jbutton2 = new JButton();
        
        // Get all jdk system properties,including file.encoding etc.
        // If you want to specify VM option such as file.encoding,you can
        // add -Dfile.encoding=GBK to project's property
        Properties p = System.getProperties();
        for ( Iterator it = p.keySet().iterator(); it.hasNext(); )
        {
             String key = (String ) it.next();
             String value = (String )  p.get(key);
             System.out.println(key +":" +value);
        }

        try{
            String test="我是中国人";

            // getBytes()按照系统缺省的字符集编码
            //String s2 = new String( test.getBytes(),"GB18030" );
            String s2 = new String( test.getBytes(),"UTF-8");
            //String s2 = new String( test.getBytes(),"GBK");
            
System.out.println(" test string: " + s2 );
            boolean utf8 = Charset.isSupported("UTF-8");
System.out.println("utf8: " + utf8);
            boolean gbk = Charset.isSupported("GBK");
System.out.println("gbk: " + gbk);
            boolean gbk18030 = Charset.isSupported("GB18030");
System.out.println("gbk18030: " + gbk18030);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        init();
        regKeyboardAction();
        setupServerComboBox();
        setLanguage();

        this.jpfield1.addKeyListener(new java.awt.event.KeyAdapter(){
            @Override public void keyPressed( KeyEvent e ){
                if( e.getKeyCode() == KeyEvent.VK_ENTER ){
                   login();
                }
            }
        });
    }

    private void jComboBox1ActionPerformed( java.awt.event.ActionEvent evt ){
         comboBox_process();
    }

    private void comboBox_process(){
        GUIAdminOptUWS uws;
        int size = this.jComboBox_server.getModel().getSize();
        if( size <= 0 ) return;

        Object obj = jComboBox_server.getSelectedItem();
        if( obj instanceof GUIAdminOptUWS ){
            uws = (GUIAdminOptUWS)obj;
            this.jtfield2.setText( uws.getPort()+"" );
            this.jtfield3.setText( uws.getUser() );
            this.jpfield1.setText( "" );
        }else{
            uws = new GUIAdminOptUWS();
            uws.setServerIp( (String)obj );
            uws.setPort( this.jtfield2.getText() );
            uws.setTxIP( (String)obj );
            uws.setUser( jtfield2.getText() );
            
            this.jComboBox_server.addItem( uws );
            
            //this.jtfield2.setText( uws.getPort()+"" );
            //this.jtfield3.setText( uws.getUser() );
            //this.jpfield1.setText( "" );
        }
    }

    private void displayLoginInfo(){
        jpanel.add( this.tmp_label );
        jpanel.add( this.err_label );
        jpanel.add(jLabel);
        jpanel.add(jLabel1);
        jpanel.add(jLabel2);
        jpanel.add(jLabel3);
        jpanel.add(this.jComboBox_server);
        jpanel.add(jtfield2,true);
        jpanel.add(jtfield3,true);
        jpanel.add(jpfield1,true);
        jpanel.add(jbutton1);
        jpanel.add(jbutton2);

        jpanel.validate();
        jpanel.repaint();
    }

    private void setLanguage(){
        jLabel.setText(  SanBootView.res.getString("ConnectDialog.label.text3") );
System.out.println( jLabel.getText() +" : "+ jLabel.getSize().toString() );

        jLabel1.setText( SanBootView.res.getString("ConnectDialog.label.text4") );
System.out.println( jLabel1.getText() +" : "+ jLabel1.getSize().toString() );

        jLabel2.setText( SanBootView.res.getString("ConnectDialog.label.text2"));
System.out.println( jLabel2.getText() +" : "+ jLabel2.getSize().toString() );

        jLabel3.setText( SanBootView.res.getString("ConnectDialog.label.text1"));
System.out.println( jLabel3.getText() +" : "+ jLabel3.getSize().toString() );


System.out.println( "password " +" : "+ this.jpfield1.getSize().toString() );
System.out.println( this.jtfield2.getText()+" : "+ this.jtfield2.getSize().toString() );
System.out.println( this.jtfield3.getText()+" : "+ this.jtfield3.getSize().toString() );
System.out.println( "combobox"+" : "+ this.jComboBox_server.getSize().toString() );

        jbutton1.setText( SanBootView.res.getString("ConnectDialog.button.connect"));
        jbutton1.setBorder(BorderFactory.createRaisedBevelBorder());
        jbutton1.setOpaque(false);
        jbutton1.setBackground(Color.ORANGE);
        jbutton1.setContentAreaFilled(true);
        jbutton1.setFont(new Font("宋体", Font.PLAIN, 18));
        jbutton1.setForeground(Color.BLACK);

        jbutton2.setText( SanBootView.res.getString("common.button.cancel"));
        jbutton2.setBorder(BorderFactory.createRaisedBevelBorder());
        jbutton2.setOpaque(false);
        jbutton2.setBackground(Color.ORANGE);
        jbutton2.setContentAreaFilled(true);
        jbutton2.setFont(new Font("宋体", Font.PLAIN, 18));
        jbutton2.setForeground(Color.BLACK);
    }
    
    /**
     * init()初始化并显示界面
     */
    int x_point;
    int y_point;
    SanBootView view;

    private void setupServerComboBox(){
        GUIAdminOptUWS uws;
        String ip = "";

        if( view.initor.lastUWS != null ){
            uws = view.initor.lastUWS;
            ip = uws.getServerIp();
System.out.println(" last swu server: "+ ip );
        }else{
            uws = view.initor.adminOpt.getFirstUWS();
            if( uws != null ){
System.out.println(" new swu server: "+ ip );
                ip = uws.getServerIp();
            }else{
System.out.println(" none swu server" );
            }
        }

        int index=0 ;

        jComboBox_server.removeAllItems();

        ArrayList list = view.initor.adminOpt.getAllUWS();
        int size = list.size();
        for( int i=0; i<size; i++ ){
            GUIAdminOptUWS one = (GUIAdminOptUWS)list.get(i);
// System.out.println(" uws: "+ uws.getServerIp() );
            if( one.getServerIp().equals( ip ) ){
                index = i;
            }
            jComboBox_server.addItem( list.get(i) );
        }
        
        if( size > 0 ){
            jComboBox_server.setSelectedIndex( index );
        }else{
            this.jtfield2.setText( "5010" );
            this.jtfield3.setText( "" );
            this.jpfield1.setText( "" );
        }
    }

    private void init(){
        view = new SanBootView( ResourceCenter.MODE_RELEASE );
        jpanel.setImageIcon( ((ImageIcon)ResourceCenter.Welcom_ICON).getImage() );
        
        jpanel.addMouseListener( new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        
        //设置JFrame为全屏
        jframe.setUndecorated(true);
        jframe.getGraphicsConfiguration().getDevice().setFullScreenWindow(jframe);
        
        //设置JPanel为透明，且使用GridBagLayout布局管理器
        jpanel.setOpaque(true);
        jpanel.setLayout(gridbag);
        
        java.awt.GridBagConstraints gridBagConstraints;
        
        tmp_label.setText("");
        tmp_label.setPreferredSize( new Dimension( 100,100) );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 120, 0);
        gridbag.setConstraints( tmp_label, gridBagConstraints );
        
        jLabel.setText("Server :");
        jLabel.setFont( font );
        jLabel.setForeground( Color.WHITE );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints( jLabel, gridBagConstraints );
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        gridbag.setConstraints( jComboBox_server, gridBagConstraints );

        jLabel1.setText("Port :");
        jLabel1.setFont( font );
        jLabel1.setForeground( Color.WHITE );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        gridbag.setConstraints( jLabel1, gridBagConstraints );

        jtfield2.setText( "5010" );
        this.jtfield2.setPreferredSize(new java.awt.Dimension(50, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        gridbag.setConstraints( jtfield2, gridBagConstraints );

        jLabel2.setText("User :");
        jLabel2.setFont( font );
        jLabel2.setForeground( Color.WHITE );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        gridbag.setConstraints( jLabel2, gridBagConstraints );

        jtfield3.setText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        gridbag.setConstraints( jtfield3, gridBagConstraints );

        jLabel3.setText("Password :");
        jLabel3.setFont( font );
        jLabel3.setForeground( Color.WHITE );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        gridbag.setConstraints( jLabel3, gridBagConstraints );

        this.jpfield1.setText("");
        jpfield1.setPreferredSize(new java.awt.Dimension(100, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        gridbag.setConstraints( jpfield1, gridBagConstraints );

        this.jbutton1.setText("Logon");
        jbutton1.setFont( font1 );
        jbutton1.setPreferredSize(new Dimension(100,25));
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.fill = java.awt.GridBagConstraints.NONE;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        gridbag.setConstraints( jbutton1, gridBagConstraints );

        jbutton2.setText("Cancel");
        jbutton2.setFont( font1 );
        jbutton2.setPreferredSize(new Dimension(100,25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.NONE;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        gridbag.setConstraints( jbutton2, gridBagConstraints );

        this.err_label.setText("");
        this.err_label.setFont( font );
        err_label.setForeground( Color.RED );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        gridbag.setConstraints( err_label, gridBagConstraints );

        jbutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login();
            }
        });

        jbutton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               System.exit(0);
            }
        });
        
        jframe.getContentPane().setLayout( new java.awt.BorderLayout() );
        jframe.add( jpanel,java.awt.BorderLayout.CENTER );
    }

    Runnable refreshFrame = new Runnable(){
        public void run(){
            view.validate();
            view.repaint();
        }
    };

    private void login(){
        Object obj = this.jComboBox_server.getSelectedItem();
        if( obj == null ) return;

        GUIAdminOptUWS selUWS;
        GUIAdminOptUWS uws = null;
        String aIp,txIP;
        if( obj instanceof GUIAdminOptUWS ){
            uws = (GUIAdminOptUWS)obj;
            aIp = uws.getServerIp();
            txIP = uws.getTxIP();
            
            if( aIp.equals("") ){
                this.err_label.setText( SanBootView.res.getString("OptionDialog.error.noneAdminIP") );
                return;
            }
            
            if( !Check.ipCheck( aIp ) ){
                this.err_label.setText( SanBootView.res.getString("OptionDialog.error.badAdminIP") );
                return;
            }
            
            if( txIP.equals("") ){
                this.err_label.setText( SanBootView.res.getString("OptionDialog.error.noneTxIP") );
                return;
            }

            if( !Check.ipCheck( txIP ) ){
                this.err_label.setText( SanBootView.res.getString("OptionDialog.error.badTxIP") );
                return;
            }

        }else{
            aIp = ((String)obj).trim();
            if( aIp.equals("") ){
                this.err_label.setText( SanBootView.res.getString("ConnectDialog.error.noneIP") );
                return;
            }

            if( !Check.ipCheck( aIp ) ){
                this.err_label.setText( SanBootView.res.getString("ConnectDialog.error.badIP") );
                return;
            }
            uws = view.initor.adminOpt.getUWS( aIp );
        }

        String _port = this.jtfield2.getText().trim();
        if( _port.equals("") ){
            this.err_label.setText( SanBootView.res.getString("ConnectDialog.errMsg.nullPort") );
            return;
        }

        if( !Check.digitCheck( _port ) ){
            this.err_label.setText( SanBootView.res.getString("ConnectDialog.errMsg.badPort") );
            return;
        }

        int port = ResourceCenter.C_S_PORT;
        try{
            port = Integer.parseInt( _port );
        }catch(Exception ex){}

        if( port <1 || port >65535 ){
            this.err_label.setText( SanBootView.res.getString("ConnectDialog.errMsg.invalidPort") );
            return ;
        }

        String user = this.jtfield3.getText().trim();
        if( user.equals("") ){
            this.err_label.setText( SanBootView.res.getString("ConnectDialog.errMsg.noneUser") );
            return;
        }

        if( Check.checkInput( user ) ){
            this.err_label.setText( SanBootView.res.getString("ConnectDialog.errMsg.badUser") );
            return;
        }

        if( user.getBytes().length >= 32 ){
            this.err_label.setText( SanBootView.res.getString("ConnectDialog.errMsg.invalidUser") );
            return;
        }
        
        String pwd = new String( this.jpfield1.getPassword() );
        if( !pwd.equals("") ){
            if( pwd.getBytes().length >= 32  ){
                this.err_label.setText( SanBootView.res.getString("ConnectDialog.errMsg.invalidPwd") );
                return;
            }
        }
        
        this.jframe.dispose();

        // 展开view
        boolean packFrame = true;
        try{
            if (packFrame)
                view.pack();  //Pack frames that have useful preferred size info, e.g. from their layout
            else
                view.validate();  //Validate frames that have preset sizes

            // Get all jdk system properties,including file.encoding etc.
            // If you want to specify VM option such as file.encoding,you can
            // add -Dfile.encoding=GBK to project's property
            Properties p = System.getProperties();
            for ( Iterator it = p.keySet().iterator(); it.hasNext(); )
            {
                 String key = (String ) it.next();
                 String value = (String )  p.get(key);
                 System.out.println(key +":" +value);
            }
            
            // Center the window
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = view.getSize();
            if (frameSize.height > screenSize.height) {
                frameSize.height = screenSize.height;
            }
            if (frameSize.width > screenSize.width) {
                frameSize.width = screenSize.width;
            }
            view.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

            // maximized frame
            view.setExtendedState( JFrame.MAXIMIZED_BOTH );
            view.setVisible( true );

            // 刷新frame,否则有时候第一次启动时（冷启动）会出现界面紊乱的情况,
            // 但下面这段代码不知有无作用
            //*
            try{
                SwingUtilities.invokeLater(
                    refreshFrame
                );
            }catch(Exception ex){
                ex.printStackTrace();
            }
             //*/

            int fail_cnt = 0;
            boolean ok = false;
            while( fail_cnt<3 && !ok ){
                // 记录当前登录的UWS信息
                view.initor.serverIp = aIp;
                view.initor.user     = user;
                view.initor.passwd   = pwd;
                view.initor.port     = port;
                
                if( uws != null ){
                    selUWS               = uws;
                    view.initor.txIp     = selUWS.getTxIP();
                    view.initor.uws      = selUWS;
                } else {
                    view.initor.txIp     =aIp;
                }

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

            err_label.setText(""); // clear err label text,2012.3.27
            
            view.mbCenter.setEnabledOnLogin( false );

            view.setTitle( SanBootView.res.getString( "View.frameTitle" )+"[ "+view.initor.serverIp+" ]" );

            // 增加登陆的新UWS到conf中
            view.initor.addUWSConf();
            view.initor.saveConf();

            // 开始真正地初始化 iboot server
            view.initor.initAppWithGraphy();
            
            view.mbCenter.setupConnectButtonStatus( view.initor.isLogined() );

            view.mbCenter.setEnabledOnMainMenu( MenuAndBtnCenterForMainUi.INDEX_LOGICAL_PROTECT, view.initor.mdb.isSupportMTPP() );
            view.mbCenter.setEnabledOnMainMenu( MenuAndBtnCenterForMainUi.INDEX_PHYSICAL_PROTECT,view.initor.mdb.isSupportCMDP() );

            UWSSockConnectManager conThread = new UWSSockConnectManager( view );
            conThread.start();

            Audit audit = view.audit.registerAuditRecord( 0, MenuAndBtnCenterForMainUi.FUNC_LOGIN );
            audit.setEventDesc( "Logon to system successfully.");
            view.audit.addAuditRecord( audit );

        }catch( Exception e ){
            e.printStackTrace();
            JOptionPane.showMessageDialog( null,
                "该应用程序初始化失败"  // "application initialization failed."
            );
            System.exit( 0 );
        }
    }

    public Point getCenterPoint(int width,int height){
        int x = ( this.jframe.getSize().width - width ) / 2 + this.jframe.getX();
        int y = ( this.jframe.getSize().height - height ) / 2 + this.jframe.getY();
        return new Point(x,y);
    }

    private void regKeyboardAction(){
        this.jComboBox_server.registerKeyboardAction(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    jtfield2.requestFocusInWindow();
                }
            },
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );

        jtfield2.registerKeyboardAction(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    jtfield3.requestFocus();
                }
            },
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true),
            //JComponent.WHEN_IN_FOCUSED_WINDOW
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );

        jtfield3.registerKeyboardAction(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    jpfield1.requestFocus();
                }
            },
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true),
            //JComponent.WHEN_IN_FOCUSED_WINDOW
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    boolean isDisplay = false;
    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {
/*
        // TODO add your handling code here:
System.out.println( evt.getX() +" : " + evt.getY() +" ++++ " + x_point+":"+y_point);
        int x = evt.getX();
        int y = evt.getY();
        int abs_x = java.lang.Math.abs(x-x_point);
        int abs_y = java.lang.Math.abs(y-y_point);
        if( abs_x < 720 && abs_x >= 460 && abs_y >=360 && abs_y < 455 ){
            if( !this.isDisplay ){
System.out.println( " suiable click on: "+ evt.getX() +" : " + evt.getY() );
                this.displayLoginInfo();
                this.isDisplay = true;
            }
        }
*/
        if( !this.isDisplay ){
System.out.println( " mouse click on: "+ evt.getX() +" : " + evt.getY() );
            this.displayLoginInfo();
            this.isDisplay = true;
        }
    }
    
    public void showMe(){
        jframe.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        jframe.setVisible(true);
    }
    
    public static void main(String[] args) {
        // 使用户界面具有所在系统的风格
        try{
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
            //Class cls = Class.forName("org.fife.plaf.OfficeXP.OfficeXPLookAndFeel");
            //UIManager.setLookAndFeel( (LookAndFeel)cls.newInstance() );
        }catch(Exception ex){
            ex.printStackTrace();
        }

        // 设置字体,否则用 default font 效果不好
        Check.initGlobalFontSetting( new Font("Dialog",0,12) );

        new WelcomFrame().showMe();
    }
}
