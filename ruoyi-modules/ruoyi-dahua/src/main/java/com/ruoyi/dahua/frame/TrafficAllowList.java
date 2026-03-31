package com.ruoyi.dahua.frame;

import com.ruoyi.dahua.common.FunctionList;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.UnsupportedEncodingException;

import static com.ruoyi.dahua.lib.NetSDKLib.NET_MAX_NAME_LEN;
import static com.ruoyi.dahua.lib.NetSDKLib.NET_MAX_PLATE_NUMBER_LEN;
import static java.util.Locale.ENGLISH;

class JNATrafficListFrame extends Frame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static NetSDKLib NetSdk        = NetSDKLib.NETSDK_INSTANCE;
	static NetSDKLib ConfigSdk     = NetSDKLib.CONFIG_INSTANCE;
	
	//登陆参数
	private String m_strIp         = "172.13.138.22";
	private Integer m_nPort        = new Integer("37777");
	private String m_strUser       = "admin";
	private String m_strPassword   = "hzci202239";
	private int nNo = 0;
	private String[] name = {Res.string().getSerialNumber(), Res.string().getLicensePlateNumber(), Res.string().getCarOwner(),
			Res.string().getStartTime(), Res.string().getEndTime(),Res.string().getOpenModel()};

	//设备信息
	private NetSDKLib.NET_DEVICEINFO_Ex m_stDeviceInfo = new NetSDKLib.NET_DEVICEINFO_Ex(); // 对应CLIENT_LoginEx2
	private LLong m_hLoginHandle = new LLong(0);   //登陆句柄
	private NetSDKLib.NET_TRAFFIC_LIST_RECORD pstRecordAdd = new NetSDKLib.NET_TRAFFIC_LIST_RECORD(); // 开闸权限
	
	//////////////////SDK相关信息///////////////////////////
	//NetSDK 库初始化
	public class SDKEnvironment {
		
		private boolean bInit    = false;
		private boolean bLogopen = false;   
		
		private DisConnect disConnect       = new DisConnect();    //设备断线通知回调
		private HaveReConnect haveReConnect = new HaveReConnect(); //网络连接恢复
			
		//设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
		public class DisConnect implements NetSDKLib.fDisConnect {
			public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
				System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
			}
		}		
		//网络连接恢复，设备重连成功回调
		// 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
		public class HaveReConnect implements NetSDKLib.fHaveReConnect {
			public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
				System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
			}
		}
		
		//初始化
		public boolean init() {	
			bInit = NetSdk.CLIENT_Init(disConnect, null);
			if(!bInit) {
				System.out.println("Initialize SDK failed");
				return false;
			}
			
			//打开日志，可选
			NetSDKLib.LOG_SET_PRINT_INFO setLog = new NetSDKLib.LOG_SET_PRINT_INFO();
			File path = new File(".");		
			String logPath = path.getAbsoluteFile().getParent() + "\\sdk_log\\TrafficList" + System.currentTimeMillis() + ".log";			
			
			setLog.bSetFilePath = 1; 
			System.arraycopy(logPath.getBytes(), 0, setLog.szLogFilePath, 0, logPath.getBytes().length);
			
			setLog.bSetPrintStrategy = 1;
			setLog.nPrintStrategy    = 0;
			bLogopen = NetSdk.CLIENT_LogOpen(setLog);
			if(!bLogopen ) {
				System.err.println("Failed to open NetSDK log");
			}
			
			// 设置断线重连回调接口，设置过断线重连成功回调函数后，当设备出现断线情况，SDK内部会自动进行重连操作
			// 此操作为可选操作，但建议用户进行设置
			NetSdk.CLIENT_SetAutoReconnect(haveReConnect, null);
		    
			//设置登录超时时间和尝试次数，可选
			int waitTime = 5000; //登录请求响应超时时间设置为5S
			int tryTimes = 3;    //登录时尝试建立链接3次
			NetSdk.CLIENT_SetConnectTime(waitTime, tryTimes);
			
			// 设置更多网络参数，NET_PARAM的nWaittime，nConnectTryNum成员与CLIENT_SetConnectTime 
			// 接口设置的登录设备超时时间和尝试次数意义相同,可选
			NetSDKLib.NET_PARAM netParam = new NetSDKLib.NET_PARAM();
			netParam.nConnectTime = 10000; //登录时尝试建立链接的超时时间
			NetSdk.CLIENT_SetNetworkParam(netParam);	
			return true;
		}
		
		//清除环境
		public void cleanup() {
			if(bLogopen) {
				NetSdk.CLIENT_LogClose();
			}
			
			if(bInit) {
				NetSdk.CLIENT_Cleanup();
			}
		}
	}
		
	private SDKEnvironment sdkEnv;

	public JNATrafficListFrame() {	
		sdkEnv = new SDKEnvironment();
		sdkEnv.init();
	    setTitle(Res.string().getTrafficAllowList());
	    setSize(900, 650);
	    setLayout(new BorderLayout());
	    setLocationRelativeTo(null);
	    setVisible(true);
        	    
	    loginPanel = new LoginPanel();
	    TrafficPanel trafficPanel = new TrafficPanel();
	    QueryViewPanel queryViewPanel = new QueryViewPanel();
	     
	    add(loginPanel, BorderLayout.NORTH);
	    add(trafficPanel, BorderLayout.WEST);
	    add(queryViewPanel, BorderLayout.CENTER);
	    
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		System.out.println("Window Closing");
	    		//登出
	    		logoutButtonPerformed(null);    		
	    		dispose();

				// 返回主菜单
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						FunctionList demo = new FunctionList();
						demo.setVisible(true);
					}
				});
	    	}
	    });

	}
	
	/////////////////面板///////////////////
	//////////////////////////////////////
	//设置边框
	private void setBorderEx(JComponent object, String title, int width) {
	    Border innerBorder = BorderFactory.createTitledBorder(title);
	    Border outerBorder = BorderFactory.createEmptyBorder(width, width, width, width);
	    object.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));	 
	}
	
	//登录面板
	public class LoginPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public LoginPanel() {
			loginBtn = new JButton(Res.string().getLogin());
			logoutBtn = new JButton(Res.string().getLogout());
			nameLabel = new JLabel(Res.string().getUserName());
			passwordLabel = new JLabel(Res.string().getPassword());
			nameTextArea = new JTextField(m_strUser, 8);
			passwordTextArea = new JPasswordField(m_strPassword, 8);
			ipLabel = new JLabel(Res.string().getIp());
			portLabel = new JLabel(Res.string().getPort());
			ipTextArea = new JTextField(m_strIp, 16);
			portTextArea = new JTextField(m_nPort.toString(), 8);

			setLayout(new FlowLayout());
		    setBorderEx(this, Res.string().getLogin(), 2);
		    
		    add(ipLabel);
		    add(ipTextArea);
		    add(portLabel);
		    add(portTextArea);
		    add(nameLabel);
		    add(nameTextArea);
		    add(passwordLabel);
		    add(passwordTextArea);
		    add(loginBtn);
		    add(logoutBtn);
		    
		    logoutBtn.setEnabled(false);
		    
		    //登录按钮，监听事件
		    loginBtn.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		new SDKEnvironment().init();
		    		loginButtonPerformed(e);
		    	}
		    });
			
		    //登出按钮，监听事件
		    logoutBtn.addActionListener(new ActionListener(){
		    	public void actionPerformed(ActionEvent e) {
		    		logoutButtonPerformed(e);
		    	}
		    });
		}
	}
		
	//允许名单操作面板
	public class TrafficPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TrafficPanel() {
			setBorderEx(this,Res.string().getAllowlistOperation(), 4);
			Dimension dim = this.getPreferredSize();
			dim.width = 300;
			this.setPreferredSize(dim);	
			
			SinglePanel singlePanel = new SinglePanel();
			BatchPanel batchPanel = new BatchPanel();
			setLayout(new BorderLayout());
            add(singlePanel, BorderLayout.NORTH);
            add(batchPanel, BorderLayout.SOUTH);
		}
	}
	
	// 单个上传面板
	public class SinglePanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SinglePanel(){
			setBorderEx(this, Res.string().getSingleUpload(), 4);
			Dimension dim = this.getPreferredSize();
			dim.height = 200;
			this.setPreferredSize(dim);
			setLayout(new GridLayout(4, 2, 30, 20));

			numLabel = new JLabel( Res.string().getLicensePlateRun());
			numTextArea = new JTextField("");
		//	queryBtn = new JButton("查询");
			queryExBtn = new JButton(Res.string().getFuzzyQuery());
			addBtn = new JButton(Res.string().getAdd());
			deleteBtn = new JButton(Res.string().getDelete());
			modifyBtn = new JButton(Res.string().getModify());
			alldeleteBtn = new JButton(Res.string().getDeleteAll());
			
		//	queryBtn.setEnabled(false);
			queryExBtn.setEnabled(false);
			addBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
			modifyBtn.setEnabled(false);
			alldeleteBtn.setEnabled(false);
			
			add(numLabel);
			add(numTextArea);
		//	add(queryBtn);
			add(queryExBtn);
			add(addBtn);
			add(deleteBtn);
			add(modifyBtn);
			add(alldeleteBtn);
			
		/*	queryBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {			
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					model.setRowCount(0);  // 在模糊查询前，清空表格
					data = new Object[200][6];  // 再重设表格，
					query();
				}
			});*/
			queryExBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					model.setRowCount(0);  // 在模糊查询前，清空表格
					data = new Object[200][6];  // 再重设表格，
					queryEx();
				}
			});
			addBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new AddFrame();
				}					
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int rowCount = table.getSelectedRowCount();
					if(rowCount > 0) {
						deleteOperate();
						int row = table.getSelectedRow();
						DefaultTableModel model = (DefaultTableModel)table.getModel();
						model.removeRow(row);   // 删除选中的行
						data = new Object[200][6];  // 再重设表格，
					}	else {

						JOptionPane.showMessageDialog(null, Res.string().getSelectData());
					}
				}
			});
			modifyBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int rowCount = table.getSelectedRowCount();
					if(rowCount > 0) {
						new ModifyFrame();
						int row = table.getSelectedRow(); //获得所选的单行
						nullTextArea31.setText(String.valueOf(model.getValueAt(row, 1)));
						nullTextArea41.setText(String.valueOf(model.getValueAt(row, 2)));
						startTextArea1.setText(String.valueOf(model.getValueAt(row, 3)));
						endTextArea1.setText(String.valueOf(model.getValueAt(row, 4)));
						if((model.getValueAt(row, 5)).equals(Res.string().getAuthorization())) {
							jr1.setSelected(true);
						} else {
							jr1.setSelected(false);
						}
					} else {
						JOptionPane.showMessageDialog(null, Res.string().getSelectData());
					}
				}
			});
			alldeleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {		
					alldeleteOperate();
				}
			});
		}
	}
	
	// 批量上传面板
	public class BatchPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BatchPanel() {
			setBorderEx(this, Res.string().getBatchUpload(), 4);
			Dimension dim = this.getPreferredSize();
			dim.height = 150;
			this.setPreferredSize(dim);
			setLayout(new GridLayout(3, 2, 30, 20));
			
			browseTextArea = new JTextField();
			browseBtn = new JButton(Res.string().getBrowse());
			nullLabel1 = new JLabel("");
			upLoadBtn = new JButton(Res.string().getUpload());
			nullLabel2 = new JLabel("");

			browseTextArea.setEditable(false);
			browseBtn.setEnabled(false);
			upLoadBtn.setEnabled(false);

			add(browseTextArea);
			add(browseBtn);
			add(nullLabel1);
			add(upLoadBtn);
			add(nullLabel2);

			final File[] file = new File[1];
			browseBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jfc = new JFileChooser();
					jfc.setMultiSelectionEnabled(true); //可以拖选多个文件
					jfc.setAcceptAllFileFilterUsed(false); //关掉显示所有
					//添加过滤器
					jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
						public boolean accept(File f) {
							if(f.getName().endsWith(".CSV")||f.isDirectory()) {
								file[0] =f;
								return true;
							}
							return false;
						}
						public String getDescription() {
							return ".CSV";
						}
					});
			        if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			        	System.out.println(jfc.getSelectedFile().getAbsolutePath());
			        	browseTextArea.setText(jfc.getSelectedFile().getAbsolutePath());
			        } 
				}
			});

			upLoadBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if(browseTextArea.getText().isEmpty()){
						
					}else {


						String uploading = Res.string().getUploading();
						upLoadBtn.setLabel(uploading);
						JOptionPane jOptionPane=new JOptionPane();
								if(uploading.equals("uploading")){
									jOptionPane.setDefaultLocale(ENGLISH);
								}

					if(	jOptionPane.showConfirmDialog(null, Res.string().getSureUpload())==JOptionPane.OK_OPTION){
						upLoad();
					}

						upLoadBtn.setLabel(Res.string().getUpload());
					}
				}
			});
		}
	}
	
	// 查询显示 面板
	public class QueryViewPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public QueryViewPanel() {
			setBorderEx(this, Res.string().getQueryInformation() , 4);
			setLayout(new BorderLayout());
			
			// 在JTable列表里添加一个模版，信息存在模版里
			data = new Object[200][6];
			model = new DefaultTableModel(data, name);
			table = new JTable(model);
			
			// 设置某列的宽度
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
			table.getColumnModel().getColumn(3).setPreferredWidth(120);
			table.getColumnModel().getColumn(4).setPreferredWidth(120);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 只能选中一行		

			// 建立滑动面板，并插入列表
			JScrollPane scrollPane = new JScrollPane(table);
			add(scrollPane, BorderLayout.CENTER);
		}
	}

	//添加按钮窗口	
	public class AddFrame extends Frame{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public AddFrame(){
			setTitle(Res.string().getDialog());
			setSize(450, 450);
			setLocationRelativeTo(null);
			setVisible(true);
			setLayout(new BorderLayout());
            DialogPanel dialogPanel = new DialogPanel();
            add(dialogPanel, BorderLayout.CENTER);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e){
					dispose();
				}
			});
		}
		public class DialogPanel extends JPanel{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public DialogPanel() {
				setBorderEx(this, Res.string().getAdd(), 4);
				Dimension dim = this.getPreferredSize();
				dim.height = 400;
				dim.width = 400;
				this.setPreferredSize(dim);
				setLayout(new GridLayout(3, 1));
				JPanel jp11 = new JPanel();
				JPanel jp1 = new JPanel();
				JPanel jp2 = new JPanel();
				JPanel jp3 = new JPanel();
				
				numberLabel = new JLabel(Res.string().getLicensePlateNumber());
				String[] str;
				if(Res.string().getLicensePlateNumber().equals("license plate number")){
					str= new String[]{"jing", "jin", "ji"};
				}else {
					//下拉菜单设置选项
					str = new String[]{"京", "津", "冀", "晋", "内蒙古", "辽", "吉", "黑", "沪", "鲁", "苏", "浙", "皖", "闽", "赣", "豫", "鄂", "湘",
							"粤", "桂", "琼", "渝", "川", "贵", "云", "藏", "陕", "甘", "青", "宁", "新", "港", "澳", "台"};
				}

	            /*ComboBoxModel jComboBoxModel = new DefaultComboBoxModel(str);
				jComboBox.setModel(jComboBoxModel);*/
				jComboBox = new JComboBox(str);	
				jComboBox.setPreferredSize(new Dimension(100, 25));  // 设置宽度
				
				nullTextArea3 = new JTextField(8);
				userLabel = new JLabel(Res.string().getCarOwner());
				nullTextArea4 = new JTextField(8);
				startTime = new JLabel(Res.string().getStartTime());
				startTextArea = new JTextField("2021/11/1 6:07:07");
				stopTime = new JLabel(Res.string().getEndTime());
				endTextArea = new JTextField("2021/11/1 8:08:07");
				
				jr = new JRadioButton(Res.string().getAuthorization());
				jr.setSelected(true);
				okBtn = new JButton(Res.string().getConfirm());
				cancleBtn = new JButton(Res.string().getCancel());
				
				jp11.setLayout(new FlowLayout(FlowLayout.CENTER));
				jp11.add(jComboBox);
				jp11.add(nullTextArea3);
				
				jp1.setLayout(new GridLayout(4, 2, 1, 8));
				jp1.add(numberLabel);
				jp1.add(jp11);
				jp1.add(userLabel);
				jp1.add(nullTextArea4);
				jp1.add(startTime);
				jp1.add(startTextArea);
				
				jp1.add(stopTime);
				jp1.add(endTextArea);
				
				jp2.setLayout(new FlowLayout(FlowLayout.CENTER));
				jp2.add(jr);
				
				jp3.setLayout(new FlowLayout(FlowLayout.CENTER));
				jp3.add(okBtn);
				jp3.add(cancleBtn);
								
				add(jp1);
				add(jp2);
				add(jp3);
				
				okBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(jr.isSelected()) {
							pstRecordAdd.stAuthrityTypes[0].emAuthorityType = NetSDKLib.EM_NET_AUTHORITY_TYPE.NET_AUTHORITY_OPEN_GATE;
							pstRecordAdd.stAuthrityTypes[0].bAuthorityEnable = 1;	
						} else {
							pstRecordAdd.stAuthrityTypes[0].emAuthorityType = NetSDKLib.EM_NET_AUTHORITY_TYPE.NET_AUTHORITY_OPEN_GATE;
							pstRecordAdd.stAuthrityTypes[0].bAuthorityEnable = 0;	
						}
						addOperate();
						dispose();
					}
				});
				
				cancleBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});						
			}		 
		}	 	
	}
	// 修改按钮窗口	
	public class ModifyFrame extends Frame{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public ModifyFrame(){
			setTitle(Res.string().getModifyPanel());
			setSize(450, 350);
			setLocationRelativeTo(null);
			setVisible(true);
			setLayout(new BorderLayout());
            ModifyPanel modifyPanel = new ModifyPanel();
            add(modifyPanel, BorderLayout.CENTER);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e){
					dispose();
				}
			});
		}
		public class ModifyPanel extends JPanel{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public ModifyPanel() {
				setBorderEx(this, Res.string().getModify(), 3);
				Dimension dim = this.getPreferredSize();
				dim.height = 450;
				dim.width = 350;

				this.setPreferredSize(dim);
				setLayout(new GridLayout(3, 1,2,2));
				JPanel jp111 = new JPanel();
				JPanel jp11 = new JPanel();
				JPanel jp21 = new JPanel();
				JPanel jp31 = new JPanel();
				
				numberLabel1 = new JLabel(Res.string().getLicensePlateNumber());
				nullTextArea31 = new JTextField(33);
				nullTextArea31.setEditable(true);
				userLabel1 = new JLabel(Res.string().getCarOwner());
				nullTextArea41 = new JTextField(8);
				startTime1 = new JLabel(Res.string().getStartTime());
				startTextArea1 = new JTextField("2021/11/1 6:07:07");
				stopTime1 = new JLabel(Res.string().getEndTime());
				endTextArea1 = new JTextField("2021/11/1 8:08:07");
				
				jr1 = new JRadioButton(Res.string().getAuthorization());
				okBtn1 = new JButton(Res.string().getConfirm());
				cancleBtn1 = new JButton(Res.string().getCancel());
				
				jp111.setLayout(new FlowLayout(FlowLayout.CENTER));
				jp111.add(nullTextArea31);
				
				jp11.setLayout(new GridLayout(4, 2, 1, 1));

				jp11.add(numberLabel1);
				jp11.add(jp111);
				jp11.add(userLabel1);
				jp11.add(nullTextArea41);
				jp11.add(startTime1);
				jp11.add(startTextArea1);
				
				jp11.add(stopTime1);
				jp11.add(endTextArea1);
				
				jp21.setLayout(new FlowLayout(FlowLayout.CENTER));
				jp21.add(jr1);


				jp31.setLayout(new FlowLayout(FlowLayout.CENTER));
				jp31.add(okBtn1);
				jp31.add(cancleBtn1);
								
				add(jp11);
				add(jp21);
				add(jp31);
				
				okBtn1.addActionListener(new ActionListener() {


					public void actionPerformed(ActionEvent e) {



						if(jr1.isSelected()) {
							pstRecordAdd.stAuthrityTypes[0].emAuthorityType = NetSDKLib.EM_NET_AUTHORITY_TYPE.NET_AUTHORITY_OPEN_GATE;
							pstRecordAdd.stAuthrityTypes[0].bAuthorityEnable = 1;	
						} else {
							pstRecordAdd.stAuthrityTypes[0].emAuthorityType = NetSDKLib.EM_NET_AUTHORITY_TYPE.NET_AUTHORITY_OPEN_GATE;
							pstRecordAdd.stAuthrityTypes[0].bAuthorityEnable = 0;	
						}
						modifyOperate();
						dispose();
					}
				});
				
				cancleBtn1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});						
			}		 
		}	 	
	}
	
	////////////////////事件执行//////////////////////
	///////////////////////////////////////////////
	//登录按钮事件
	private void loginButtonPerformed(ActionEvent e) {
		m_strIp = ipTextArea.getText();
		m_nPort = Integer.parseInt(portTextArea.getText());	
		m_strUser = nameTextArea.getText();
		m_strPassword = new String(passwordTextArea.getPassword());
		
		System.out.println("设备地址：" + m_strIp + "\n端口号：" + m_nPort 
				+ "\n用户名：" + m_strUser + "\n密码：" + m_strPassword);
		
		int nSpecCap = NetSDKLib.EM_LOGIN_SPAC_CAP_TYPE.EM_LOGIN_SPEC_CAP_TCP; //=0
		IntByReference nError = new IntByReference(0);
		m_hLoginHandle = NetSdk.CLIENT_LoginEx2(m_strIp, m_nPort.intValue(), m_strUser, m_strPassword, nSpecCap, null, m_stDeviceInfo, nError);
		if(m_hLoginHandle.longValue() == 0) {
			int error = 0;
			error = NetSdk.CLIENT_GetLastError();
			System.err.printf("Login Device[%s] Port[%d]Failed. Last Error[0x%x]\n", m_strIp, m_nPort, error);
			JOptionPane.showMessageDialog(this, Res.string().getLoginFailed()+":" + String.format("[0x%x]", error));
		} else {
			System.out.println("Login Success [ " + m_strIp + " ]");
			JOptionPane.showMessageDialog(this, Res.string().getLoginSuccess());
			logoutBtn.setEnabled(true);
			loginBtn.setEnabled(false);	
		//	queryBtn.setEnabled(true);
			queryExBtn.setEnabled(true);
			addBtn.setEnabled(true);
			deleteBtn.setEnabled(true);
			modifyBtn.setEnabled(true);
			browseBtn.setEnabled(true);
			upLoadBtn.setEnabled(true);
			alldeleteBtn.setEnabled(true);
		}	
	}
	
	//登出按钮事件
	private void logoutButtonPerformed(ActionEvent e) {
		if(m_hLoginHandle.longValue() != 0) {
			System.out.println("Logout Button Action");
		
			if(NetSdk.CLIENT_Logout(m_hLoginHandle)) {
				System.out.println("Logout Success [ " + m_strIp + " ]");
				m_hLoginHandle.setValue(0);
				logoutBtn.setEnabled(false);
				loginBtn.setEnabled(true);	
		//		queryBtn.setEnabled(false);
				queryExBtn.setEnabled(false);
				addBtn.setEnabled(false);
				deleteBtn.setEnabled(false);
				modifyBtn.setEnabled(false);
				browseBtn.setEnabled(false);
				upLoadBtn.setEnabled(false);
				
			}
		}
	}
    
	// 查询按钮事件
	private void query() {
		// 开始查询记录
		NetSDKLib.NET_IN_FIND_RECORD_PARAM stuFindInParam = new NetSDKLib.NET_IN_FIND_RECORD_PARAM();
		stuFindInParam.emType = NetSDKLib.EM_NET_RECORD_TYPE.NET_RECORD_TRAFFICREDLIST;
		
		NetSDKLib.FIND_RECORD_TRAFFICREDLIST_CONDITION stuRedListCondition = new NetSDKLib.FIND_RECORD_TRAFFICREDLIST_CONDITION();
		stuFindInParam.pQueryCondition = stuRedListCondition.getPointer();
		byte[] numText;
		try {

			numText	= numTextArea.getText().trim().getBytes("GBK");
			System.arraycopy(numText, 0, stuRedListCondition.szPlateNumber, 0, numText.length);
		} catch (UnsupportedEncodingException e) {
			System.err.println("字符串转码异常");
		}

		
		NetSDKLib.NET_OUT_FIND_RECORD_PARAM stuFindOutParam = new NetSDKLib.NET_OUT_FIND_RECORD_PARAM();

		if((numTextArea.getText()).equals("")) {
			JOptionPane.showMessageDialog(this, Res.string().getEnterQueryData());
		}else {
			stuRedListCondition.write();
			boolean bRet = NetSdk.CLIENT_FindRecord(m_hLoginHandle, stuFindInParam, stuFindOutParam, 5000);
			stuRedListCondition.read();
			System.out.println("FindRecord Succeed" + "\n" + "FindHandle :" + stuFindOutParam.lFindeHandle);
			if(bRet) {
				int nRecordCount = 10;
				NetSDKLib.NET_IN_FIND_NEXT_RECORD_PARAM stuFindNextInParam = new NetSDKLib.NET_IN_FIND_NEXT_RECORD_PARAM();
				stuFindNextInParam.lFindeHandle = stuFindOutParam.lFindeHandle;
				stuFindNextInParam.nFileCount = nRecordCount;  //想查询的记录条数
				
				NetSDKLib.NET_OUT_FIND_NEXT_RECORD_PARAM stuFindNextOutParam = new NetSDKLib.NET_OUT_FIND_NEXT_RECORD_PARAM();
				stuFindNextOutParam.nMaxRecordNum = nRecordCount;
				NetSDKLib.NET_TRAFFIC_LIST_RECORD pstRecord = new NetSDKLib.NET_TRAFFIC_LIST_RECORD();
				stuFindNextOutParam.pRecordList = pstRecord.getPointer();

				pstRecord.write();
				boolean zRet = NetSdk.CLIENT_FindNextRecord(stuFindNextInParam, stuFindNextOutParam, 5000);
				pstRecord.read();
				
				if(zRet) {		
					System.out.println("record are found!");
					
					for(int i=0; i < stuFindNextOutParam.nRetRecordNum; i++) {
						data[i][0] = String.valueOf(i);
						try {
							data[i][1] = new String(pstRecord.szPlateNumber,"GBK").trim();
							data[i][2] = new String(pstRecord.szMasterOfCar,"GBK").trim();
							data[i][3] = pstRecord.stBeginTime.toStringTime();
							data[i][4] = pstRecord.stCancelTime.toStringTime();
						} catch (UnsupportedEncodingException e) {
							System.err.println("字符串转码异常");
						}

						if(pstRecord.stAuthrityTypes[0].bAuthorityEnable == 1) {
							data[i][5] = "授权";
						} else {
							data[i][5] = "不授权";
						}
						model.setDataVector(data, name);
					}				
				} 
				
				NetSdk.CLIENT_FindRecordClose(stuFindOutParam.lFindeHandle);      
			}else {
				System.err.println("Can Not Find This Record" + String.format("0x%x", NetSdk.CLIENT_GetLastError()));					
			}
		}    		
	}
	public static void ByteArrZero(byte[] dst) {
		// 清零
		for (int i = 0; i < dst.length; ++i) {
			dst[i] = 0;
		}
	}
	// 模糊查询按钮事件
		private   void queryEx() {

		NetSDKLib.NET_IN_FIND_RECORD_PARAM stuFindInParam = new NetSDKLib.NET_IN_FIND_RECORD_PARAM();
		stuFindInParam.emType = NetSDKLib.EM_NET_RECORD_TYPE.NET_RECORD_TRAFFICREDLIST;
		
		NetSDKLib.FIND_RECORD_TRAFFICREDLIST_CONDITION stuRedListConditionEx = new NetSDKLib.FIND_RECORD_TRAFFICREDLIST_CONDITION();
		stuFindInParam.pQueryCondition = stuRedListConditionEx.getPointer();
		JNATrafficListFrame.ByteArrZero(stuRedListConditionEx.szPlateNumberVague);

		try {
			byte[] numText = numTextArea.getText().trim().getBytes("GBK");

			System.arraycopy(numText, 0, stuRedListConditionEx.szPlateNumberVague, 0, numText.length);
		} catch (UnsupportedEncodingException e) {
			System.err.println("字符串转码异常");
		}

		
		NetSDKLib.NET_OUT_FIND_RECORD_PARAM stuFindOutParam = new NetSDKLib.NET_OUT_FIND_RECORD_PARAM();
		
		stuRedListConditionEx.write(); 
		boolean bRet = NetSdk.CLIENT_FindRecord(m_hLoginHandle, stuFindInParam, stuFindOutParam, 10000);
		stuRedListConditionEx.read();
			int total=0;
		if(bRet) {
			int doNextCount = 0;
			while(true) {
				int nRecordCount = 10;
				NetSDKLib.NET_IN_FIND_NEXT_RECORD_PARAM stuFindNextInParam = new NetSDKLib.NET_IN_FIND_NEXT_RECORD_PARAM();
				stuFindNextInParam.lFindeHandle = stuFindOutParam.lFindeHandle;
				stuFindNextInParam.nFileCount = nRecordCount;
				
				NetSDKLib.NET_OUT_FIND_NEXT_RECORD_PARAM stuFindNextOutParam = new NetSDKLib.NET_OUT_FIND_NEXT_RECORD_PARAM();
				stuFindNextOutParam.nMaxRecordNum = nRecordCount;
				NetSDKLib.NET_TRAFFIC_LIST_RECORD pstRecordEx = new NetSDKLib.NET_TRAFFIC_LIST_RECORD();
				stuFindNextOutParam.pRecordList = new Memory(pstRecordEx.dwSize * nRecordCount);   //分配(stRecordEx.dwSize * nRecordCount)个内存


				// 把内存里的dwSize赋值
				for (int i=0; i<stuFindNextOutParam.nMaxRecordNum; ++i)
				{
					ToolKits.SetStructDataToPointer(pstRecordEx, stuFindNextOutParam.pRecordList, i*pstRecordEx.dwSize);
				}
				
				pstRecordEx.write();
				boolean zRet = NetSdk.CLIENT_FindNextRecord(stuFindNextInParam, stuFindNextOutParam, 10000);
				pstRecordEx.read();

				int jnumber=stuFindNextOutParam.nRetRecordNum;

				if(zRet) {

					for(int i=0; i < jnumber; i++) {
						int item = i + doNextCount * nRecordCount;
						total=item+1;
						if(item>data.length-1){

							continue;
						}
						data[item][0] = String.valueOf(item);
						ToolKits.GetPointerDataToStruct(stuFindNextOutParam.pRecordList, i*pstRecordEx.dwSize, pstRecordEx);
						try {
							data[item][1] = new String(pstRecordEx.szPlateNumber,"GBK").trim();
							data[item][2] = new String(pstRecordEx.szMasterOfCar,"GBK").trim();
							data[item][3] = pstRecordEx.stBeginTime.toStringTime();
							data[item][4] = pstRecordEx.stCancelTime.toStringTime();
						} catch (UnsupportedEncodingException e) {
							System.err.println("字符串转码异常");
						}

						if(pstRecordEx.stAuthrityTypes[0].bAuthorityEnable == 1) {
							data[item][5] = Res.string().getAuthorization();
						} else {
							data[item][5] =Res.string().getUnauthorization();
						}

						model.setDataVector(data, name);

					}
					
					if (stuFindNextOutParam.nRetRecordNum < nRecordCount)
					{
						break;
					} else {
						doNextCount ++;
					}
				} else {
					break;
				}
			}
			NetSdk.CLIENT_FindRecordClose(stuFindOutParam.lFindeHandle);

			JOptionPane.showMessageDialog(this, total+" "+Res.string().getDataNumber());

		}else {
			System.err.println("Can Not Find This Record" + String.format("0x%x", NetSdk.CLIENT_GetLastError()));
		}
	}
    
	// 添加按钮事件
	private void addOperate() {
		NetSDKLib.NET_INSERT_RECORD_INFO stInsertInfo = new NetSDKLib.NET_INSERT_RECORD_INFO();  // 添加
		
		NetSDKLib.NET_TRAFFIC_LIST_RECORD.ByReference stRec = new NetSDKLib.NET_TRAFFIC_LIST_RECORD.ByReference();


		try {

			String plate = nullTextArea3.getText();

			boolean matches = plate.matches("^[\\da-zA-Z]*$");

			if(!matches){
				JOptionPane.showMessageDialog(this, Res.string().getFillingRules());
				return;
			}

			byte[] PlateNumber
					= (jComboBox.getSelectedItem().toString() + plate).getBytes("GBK");

			if(PlateNumber.length>NET_MAX_PLATE_NUMBER_LEN){
				JOptionPane.showMessageDialog(this, Res.string().getLicensePlateLengthTooLong());

				return;
			}

			byte[] MasterOfCar = nullTextArea4.getText().getBytes("GBK");

			if(MasterOfCar.length>NET_MAX_NAME_LEN){
				JOptionPane.showMessageDialog(this, Res.string().getNameTooLong());
				return;
			}

			System.arraycopy(PlateNumber, 0, stRec.szPlateNumber, 0, PlateNumber.length);
			System.arraycopy(MasterOfCar, 0, stRec.szMasterOfCar, 0, MasterOfCar.length);
		} catch (UnsupportedEncodingException e) {
			System.err.println("字符串转码异常");
		}

		String[] start = startTextArea.getText().split(" ");
		String st1 = start[0];
		String st2 = start[1];
		String[] start1 = st1.split("/"); //年月日
		String[] start2 = st2.split(":"); // 时分
		String[] end = endTextArea.getText().split(" ");
		String ed1 = end[0];
		String ed2 = end[1];
		String[] end1 = ed1.split("/"); //年月日
		String[] end2 = ed2.split(":"); // 时分
		stRec.stBeginTime.dwYear = Integer.parseInt(start1[0]);
		stRec.stBeginTime.dwMonth = Integer.parseInt(start1[1]);
		stRec.stBeginTime.dwDay = Integer.parseInt(start1[2]);
		stRec.stBeginTime.dwHour = Integer.parseInt(start2[0]);
		stRec.stBeginTime.dwMinute = Integer.parseInt(start2[1]);
		stRec.stBeginTime.dwSecond = Integer.parseInt(start2[2]);
		stRec.stCancelTime.dwYear = Integer.parseInt(end1[0]);
		stRec.stCancelTime.dwMonth = Integer.parseInt(end1[1]);
		stRec.stCancelTime.dwDay = Integer.parseInt(end1[2]);
		stRec.stCancelTime.dwHour = Integer.parseInt(end2[0]);
		stRec.stCancelTime.dwMinute = Integer.parseInt(end2[1]);
		stRec.stCancelTime.dwSecond = Integer.parseInt(end2[2]);
		stRec.nAuthrityNum = 1;
		stRec.stAuthrityTypes[0].emAuthorityType = pstRecordAdd.stAuthrityTypes[0].emAuthorityType;
		stRec.stAuthrityTypes[0].bAuthorityEnable = pstRecordAdd.stAuthrityTypes[0].bAuthorityEnable;
		
		stInsertInfo.pRecordInfo = stRec;

		NetSDKLib.NET_IN_OPERATE_TRAFFIC_LIST_RECORD stInParam = new NetSDKLib.NET_IN_OPERATE_TRAFFIC_LIST_RECORD();
		stInParam.emOperateType = NetSDKLib.EM_RECORD_OPERATE_TYPE.NET_TRAFFIC_LIST_INSERT;
		stInParam.emRecordType = NetSDKLib.EM_NET_RECORD_TYPE.NET_RECORD_TRAFFICREDLIST;
		stInParam.pstOpreateInfo = stInsertInfo.getPointer();
	
		NetSDKLib.NET_OUT_OPERATE_TRAFFIC_LIST_RECORD stOutParam = new NetSDKLib.NET_OUT_OPERATE_TRAFFIC_LIST_RECORD();
		stRec.write();
		stInsertInfo.write();
		stInParam.write();

		boolean zRet = NetSdk.CLIENT_OperateTrafficList(m_hLoginHandle, stInParam, stOutParam, 5000);
		if(zRet) {
			stInParam.read();
			System.out.println("succeed!");
			JOptionPane.showMessageDialog(this, Res.string().getAddSuccess());
		} else {
			System.err.println("failed!" + String.format("0x%x", NetSdk.CLIENT_GetLastError()));
			JOptionPane.showMessageDialog(this, Res.string().getAddFail());
		}
	}

	// 查询之前的记录号
	private void findRecordCount() {
		// 开始查询记录
		NetSDKLib.NET_IN_FIND_RECORD_PARAM stuFindInParam = new NetSDKLib.NET_IN_FIND_RECORD_PARAM();
		stuFindInParam.emType = NetSDKLib.EM_NET_RECORD_TYPE.NET_RECORD_TRAFFICREDLIST;
		NetSDKLib.FIND_RECORD_TRAFFICREDLIST_CONDITION stuRedListCondition = new NetSDKLib.FIND_RECORD_TRAFFICREDLIST_CONDITION();
		stuFindInParam.pQueryCondition = stuRedListCondition.getPointer();
		// 获取选中行的车牌号，并赋值
		int row = table.getSelectedRow();
		try {
			System.arraycopy(String.valueOf(model.getValueAt(row, 1)).getBytes("GBK"), 0, stuRedListCondition.szPlateNumber,
					0, String.valueOf(model.getValueAt(row, 1)).getBytes("GBK").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		NetSDKLib.NET_OUT_FIND_RECORD_PARAM stuFindOutParam = new NetSDKLib.NET_OUT_FIND_RECORD_PARAM();

		stuFindInParam.write();
		stuRedListCondition.write();
		boolean bRet = NetSdk.CLIENT_FindRecord(m_hLoginHandle, stuFindInParam, stuFindOutParam, 5000);
		stuRedListCondition.read();
		stuFindInParam.read();

		if(bRet){
			int nRecordCount = 1;

			NetSDKLib.NET_IN_FIND_NEXT_RECORD_PARAM stuFindNextInParam = new NetSDKLib.NET_IN_FIND_NEXT_RECORD_PARAM();
			stuFindNextInParam.lFindeHandle = stuFindOutParam.lFindeHandle;
			stuFindNextInParam.nFileCount = nRecordCount;  //想查询的记录条数

			NetSDKLib.NET_OUT_FIND_NEXT_RECORD_PARAM stuFindNextOutParam = new NetSDKLib.NET_OUT_FIND_NEXT_RECORD_PARAM();
			stuFindNextOutParam.nMaxRecordNum = nRecordCount;
			NetSDKLib.NET_TRAFFIC_LIST_RECORD pstRecord = new NetSDKLib.NET_TRAFFIC_LIST_RECORD();
			stuFindNextOutParam.pRecordList = pstRecord.getPointer();

			stuFindNextInParam.write();
			stuFindNextOutParam.write();
			pstRecord.write();
			boolean zRet = NetSdk.CLIENT_FindNextRecord(stuFindNextInParam, stuFindNextOutParam, 5000);
			pstRecord.read();
			stuFindNextInParam.read();
			stuFindNextOutParam.read();

			if(zRet) {
				// 获取当前记录号
				nNo = pstRecord.nRecordNo;
			}
			// 停止查询
			NetSdk.CLIENT_FindRecordClose(stuFindOutParam.lFindeHandle);
		} else {
			System.err.println("error occured!" + String.format("0x%x", NetSdk.CLIENT_GetLastError()));
		}
	}
	
	// 删除按钮事件
	private void deleteOperate() {

		findRecordCount();
			
		// 获得之前查询到的记录号后，开始删除数据
		NetSDKLib.NET_REMOVE_RECORD_INFO stRemoveInfo = new NetSDKLib.NET_REMOVE_RECORD_INFO();

		stRemoveInfo.nRecordNo = nNo;
		System.out.println("nNo:"+nNo);
		NetSDKLib.NET_IN_OPERATE_TRAFFIC_LIST_RECORD stInParam = new NetSDKLib.NET_IN_OPERATE_TRAFFIC_LIST_RECORD();
		stInParam.emOperateType = NetSDKLib.EM_RECORD_OPERATE_TYPE.NET_TRAFFIC_LIST_REMOVE;
		stInParam.emRecordType = NetSDKLib.EM_NET_RECORD_TYPE.NET_RECORD_TRAFFICREDLIST;;
		stInParam.pstOpreateInfo = stRemoveInfo.getPointer();
		NetSDKLib.NET_OUT_OPERATE_TRAFFIC_LIST_RECORD stOutParam = new NetSDKLib.NET_OUT_OPERATE_TRAFFIC_LIST_RECORD();
		
		stInParam.write();
		stRemoveInfo.write();
		boolean zRet = NetSdk.CLIENT_OperateTrafficList(m_hLoginHandle, stInParam, stOutParam, 5000);

		if(zRet) {
			System.out.println("delete  succeed!");
			JOptionPane.showMessageDialog(this, Res.string().getDeleteSuccess());
		} else {
			JOptionPane.showMessageDialog(this, Res.string().getDeleteFail());
			System.err.println("failed!" + String.format("0x%x", NetSdk.CLIENT_GetLastError()));
		}	
	}

	// 修改按钮事件
	private void modifyOperate() {


		findRecordCount();

		NetSDKLib.NET_TRAFFIC_LIST_RECORD.ByReference stRec = new NetSDKLib.NET_TRAFFIC_LIST_RECORD.ByReference();
		try {

            System.arraycopy(nullTextArea31.getText().getBytes("GBK"), 0, stRec.szPlateNumber, 0, nullTextArea31.getText().getBytes("GBK").length);
			System.arraycopy(nullTextArea41.getText().getBytes("GBK"), 0, stRec.szMasterOfCar, 0, nullTextArea41.getText().getBytes("GBK").length);

			if(	stRec.szPlateNumber.length>NET_MAX_PLATE_NUMBER_LEN-1){
				JOptionPane.showMessageDialog(this, Res.string().getLicensePlateLengthTooLong());
				return;
			}


			if(stRec.szMasterOfCar.length>NET_MAX_NAME_LEN){
				JOptionPane.showMessageDialog(this, Res.string().getNameTooLong());
				return;
			}
		} catch (UnsupportedEncodingException e) {
			System.err.println("字符串转码异常");
		}



		String[] start = startTextArea1.getText().split(" ");
		String st1 = start[0];
		String st2 = start[1];
		String[] start1 = st1.split("/"); //年月日
		String[] start2 = st2.split(":"); // 时分
		String[] end = endTextArea1.getText().split(" ");
		String ed1 = end[0];
		String ed2 = end[1];
		String[] end1 = ed1.split("/"); //年月日
		String[] end2 = ed2.split(":"); // 时分
		stRec.stBeginTime.dwYear = Integer.parseInt(start1[0]);
		stRec.stBeginTime.dwMonth = Integer.parseInt(start1[1]);
		stRec.stBeginTime.dwDay = Integer.parseInt(start1[2]);
		stRec.stBeginTime.dwHour = Integer.parseInt(start2[0]);
		stRec.stBeginTime.dwMinute = Integer.parseInt(start2[1]);
		stRec.stBeginTime.dwSecond = Integer.parseInt(start2[2]);
		stRec.stCancelTime.dwYear = Integer.parseInt(end1[0]);
		stRec.stCancelTime.dwMonth = Integer.parseInt(end1[1]);
		stRec.stCancelTime.dwDay = Integer.parseInt(end1[2]);
		stRec.stCancelTime.dwHour = Integer.parseInt(end2[0]);
		stRec.stCancelTime.dwMinute = Integer.parseInt(end2[1]);	
		stRec.stCancelTime.dwSecond = Integer.parseInt(end2[2]);
		stRec.nAuthrityNum = 1;
		stRec.stAuthrityTypes[0].emAuthorityType = pstRecordAdd.stAuthrityTypes[0].emAuthorityType;
		stRec.stAuthrityTypes[0].bAuthorityEnable = pstRecordAdd.stAuthrityTypes[0].bAuthorityEnable;
				
		stRec.nRecordNo = nNo;

		NetSDKLib.NET_UPDATE_RECORD_INFO stUpdateInfo = new NetSDKLib.NET_UPDATE_RECORD_INFO();
		stUpdateInfo.pRecordInfo = stRec;
		
		NetSDKLib.NET_IN_OPERATE_TRAFFIC_LIST_RECORD stInParam = new NetSDKLib.NET_IN_OPERATE_TRAFFIC_LIST_RECORD();
		stInParam.emOperateType = NetSDKLib.EM_RECORD_OPERATE_TYPE.NET_TRAFFIC_LIST_UPDATE;
		stInParam.emRecordType = NetSDKLib.EM_NET_RECORD_TYPE.NET_RECORD_TRAFFICREDLIST;
		stInParam.pstOpreateInfo = stUpdateInfo.getPointer();
		NetSDKLib.NET_OUT_OPERATE_TRAFFIC_LIST_RECORD stOutParam = new NetSDKLib.NET_OUT_OPERATE_TRAFFIC_LIST_RECORD();

		stRec.write();
		stUpdateInfo.write();
		stInParam.write();
		boolean zRet = NetSdk.CLIENT_OperateTrafficList(m_hLoginHandle, stInParam, stOutParam, 5000);
		if(zRet) {
			System.out.println("succeed!");
			System.out.println("stOutParam:"+stOutParam.nRecordNo);
			JOptionPane.showMessageDialog(this, Res.string().getModifySuccess());
		} else {
			JOptionPane.showMessageDialog(this, Res.string().getModifyFail());

			System.err.println("failed!" + String.format("0x%x", NetSdk.CLIENT_GetLastError()));
		}
	}
	
	// 全部删除
	private void alldeleteOperate() {
		int type = NetSDKLib.CtrlType.CTRLTYPE_CTRL_RECORDSET_CLEAR;
		NetSDKLib.NET_CTRL_RECORDSET_PARAM param = new NetSDKLib.NET_CTRL_RECORDSET_PARAM();
		param.emType = NetSDKLib.EM_NET_RECORD_TYPE.NET_RECORD_TRAFFICREDLIST;
		param.write();
		boolean zRet = NetSdk.CLIENT_ControlDevice(m_hLoginHandle, type, param.getPointer(), 5000);
		if(zRet) {
			System.out.println("全部删除成功");
			JOptionPane.showMessageDialog(this, Res.string().getDeleteSuccess());
		} else {
			System.err.println("全部删除失败");
			JOptionPane.showMessageDialog(this, Res.string().getDeleteFail());
		}
	}
	
	// 上传按钮事件      注：上传*.CSV的文件，文件的数据会覆盖原数据库的数据，所以可以从数据库导出文件，并在文件里添加数据后，再上传
	private void upLoad() {


		JOptionPane jOptionPane=new JOptionPane();

		NetSDKLib.NETDEV_BLACKWHITE_LIST_INFO stIn = new NetSDKLib.NETDEV_BLACKWHITE_LIST_INFO();
		Pointer szInBuf = stIn.getPointer();
		int nInBufLen = stIn.size();
		try {
			System.arraycopy(jfc.getSelectedFile().getAbsolutePath().getBytes("GBK"), 0, stIn.szFile, 0
					, jfc.getSelectedFile().getAbsolutePath().getBytes("GBK").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		stIn.nFileSize = 1024*1024*3;  // 升级文件大小
		stIn.byFileType = 1; //当前文件类型,0-禁止名单,1-允许名单
		stIn.byAction = 0; //动作,0-覆盖,1-追加
		stIn.write();
		LLong zRet = NetSdk.CLIENT_FileTransmit(m_hLoginHandle, NetSDKLib.NET_DEV_BLACKWHITETRANS_START, szInBuf, nInBufLen, TransFileCall.getInstance(), null, 5000);


		stIn.read();
		if(zRet.longValue() == 0) {
			System.err.println("Start failed!" + String.format("0x%x", NetSdk.CLIENT_GetLastError()));

			String	type=Res.string().getUploadFail();
			jOptionPane.showMessageDialog( this, type);

			return;
		}

		stIn.write();
		LongByReference handleReference = new LongByReference(zRet.longValue());   //LLong转为Pointer*
		LLong zRet1 = NetSdk.CLIENT_FileTransmit(m_hLoginHandle, NetSDKLib.NET_DEV_BLACKWHITETRANS_SEND, handleReference.getPointer(), nInBufLen, TransFileCall.getInstance(), null, 20000);
		stIn.read();
		if(zRet1.longValue() == 0) {
	    	System.err.println("Send failed!" + String.format("0x%x", NetSdk.CLIENT_GetLastError()));

			String	type=Res.string().getUploadFail();
			jOptionPane.showMessageDialog( this, type);
	    }else {

	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	stIn.write();
	    	LLong zRet2 = NetSdk.CLIENT_FileTransmit(m_hLoginHandle, NetSDKLib.NET_DEV_BLACKWHITETRANS_STOP,  handleReference.getPointer(), nInBufLen, TransFileCall.getInstance(), null, 5000);
			stIn.read();
	    	if(zRet2.longValue() == 0) {

	    		System.err.println("Stop failed!" + String.format("0x%x", NetSdk.CLIENT_GetLastError()));
				String	type=Res.string().getUploadFail();
				jOptionPane.showMessageDialog( this, type);
	    	} else {

				String	type=Res.string().getUploadSuccess();
				jOptionPane.showMessageDialog( this, type);
	    	}

	    }


	}

	private static class TransFileCall implements NetSDKLib.fTransFileCallBack{
		private static TransFileCall instance;
		public static TransFileCall getInstance() {
			if (instance == null) {
						instance = new TransFileCall();
			}
			return instance;
		}


		@Override
		public void invoke(LLong lHandle, int nTransType, int nState, int nSendSize, int nTotalSize, Pointer dwUser) {
			/*	System.out.println("nTransType:"+nTransType);
				System.out.println("nState:"+nState);*/
		}
	}

	/////////////////////组件//////////////////////////
	//////////////////////////////////////////////////

	//登录组件
	private LoginPanel loginPanel;
	private JButton loginBtn;
	private JButton logoutBtn;
	
	private JLabel numLabel;
	private JTextField numTextArea;
	//private JButton queryBtn;
	private JButton queryExBtn;
	private JButton addBtn;
	private JButton deleteBtn;
	private JButton modifyBtn;

	private JTextField browseTextArea;
	private JButton browseBtn;
	private JLabel nullLabel1;
	private JButton upLoadBtn;
	private JLabel nullLabel2;

	private JComboBox jComboBox;
	private JLabel numberLabel;
	private JTextField nullTextArea3;
	private JLabel userLabel;
	private JTextField nullTextArea4;
	private JLabel startTime;
	private JTextField startTextArea;
	private JLabel stopTime;
	private JTextField endTextArea;
	private JRadioButton jr;
	private JButton okBtn;
	private JButton cancleBtn;
	private JButton alldeleteBtn;
	
	private JLabel numberLabel1;
	private JTextField nullTextArea31;
	private JLabel userLabel1;
	private JTextField nullTextArea41;
	private JLabel startTime1;
	private JTextField startTextArea1;
	private JLabel stopTime1;
	private JTextField endTextArea1;
	private JRadioButton jr1;
	private JButton okBtn1;
	private JButton cancleBtn1;

	private JFileChooser jfc;
    private JLabel ipLabel;
    private JTextField ipTextArea;
	private JLabel nameLabel;
	private JLabel passwordLabel;
	private JLabel portLabel;
	
	private JTextField portTextArea;
	private JTextField nameTextArea;
	private JPasswordField passwordTextArea;	
    
	private DefaultTableModel model;
	private JTable table;
	private Object[][] data;
}


public class TrafficAllowList {  
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JNATrafficListFrame demo = new JNATrafficListFrame();
				demo.setVisible(true);
			}
		});		
	}
}

