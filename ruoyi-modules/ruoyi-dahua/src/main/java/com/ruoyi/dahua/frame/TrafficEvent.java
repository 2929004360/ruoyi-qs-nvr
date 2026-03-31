package com.ruoyi.dahua.frame;

import com.ruoyi.dahua.common.*;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.LoginModule;
import com.ruoyi.dahua.module.RealPlayModule;
import com.ruoyi.dahua.module.TrafficEventModule;
import com.sun.jna.CallbackThreadInitializer;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

/*
 * 智能交通Demo
 */
class TrafficEventFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private Vector<String> chnlist = new Vector<String>();

	private DefaultTableModel model;
	private AnalyzerDataCB m_AnalyzerDataCB = new AnalyzerDataCB();
	// 设备断线通知回调
	private static DisConnect disConnect       = new DisConnect();

	// 网络连接恢复
	private static HaveReConnect haveReConnect = new HaveReConnect();

	// 预览句柄
	public static LLong m_hPlayHandle = new LLong(0);

	boolean b_openStrobe = false;
	boolean b_realplay = false;
	boolean b_attach = false;

	private int i = 1;  // 列表序号

	private class TRAFFIC_INFO {
		private String m_EventName;         	  // 事件名称
		private String m_PlateNumber;       	  // 车牌号
		private String m_PlateType;               // 车牌类型
		private String m_PlateColor;      	  	  // 车牌颜色
		private String m_VehicleColor;    	  	  // 车身颜色
		private String m_VehicleType;       	  // 车身类型
		private String m_VehicleSize;     	  	  // 车辆大小
		private String m_FileCount;				  // 文件总数
		private String m_FileIndex;				  // 文件编号
		private String m_GroupID;				  // 组ID
		private String m_IllegalPlace;			  // 违法地点
		private String m_LaneNumber;              // 通道号
		private NetSDKLib.NET_TIME_EX m_Utc;      // 事件时间
		private int m_bPicEnble;       	  		  // 车牌对应信息，BOOL类型
		private int m_OffSet;          	  		  // 车牌偏移量
		private int m_FileLength;                 // 文件大小
		private NetSDKLib.DH_RECT m_BoundingBox;  // 包围盒
	}

	private  TRAFFIC_INFO trafficInfo = new TRAFFIC_INFO();
	private BufferedImage snapImage = null;
	private BufferedImage plateImage = null;

	private Component  target     = this;

	// 获取界面窗口
	private static JFrame frame = new JFrame();

	public TrafficEventFrame() {
		setTitle(Res.string().getITSEvent());
		setLayout(new BorderLayout());
		pack();
		setSize(800, 565);
		setResizable(false);
		setLocationRelativeTo(null);
		LoginModule.init(disConnect, haveReConnect);   // 打开工程，初始化

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		loginPanel = new LoginPanel();
		itsPanel = new ItsPanel();

		add(loginPanel, BorderLayout.NORTH);
		add(itsPanel, BorderLayout.CENTER);

		loginPanel.addLoginBtnActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(loginPanel.checkLoginText()) {
					if(login()) {
						frame = ToolKits.getFrame(e);
						frame.setTitle(Res.string().getITSEvent() + " : " + Res.string().getOnline());
					}
				}
			}
		});

		loginPanel.addLogoutBtnActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setTitle(Res.string().getITSEvent());
				logout();
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				RealPlayModule.stopRealPlay(m_hPlayHandle);
				TrafficEventModule.detachIVSEvent();
				LoginModule.logout();
				LoginModule.cleanup();  // 关闭工程，释放资源
				dispose();

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
	// 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
	private static class DisConnect implements NetSDKLib.fDisConnect {
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
			// 断线提示
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setTitle(Res.string().getITSEvent() + " : " + Res.string().getDisConnectReconnecting());
				}
			});
		}
	}

	// 网络连接恢复，设备重连成功回调
	// 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
	private static class HaveReConnect implements NetSDKLib.fHaveReConnect {
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);

			// 重连提示
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setTitle(Res.string().getITSEvent() + " : " + Res.string().getOnline());
				}
			});
		}
	}
	// 登录
	public boolean login() {
		Native.setCallbackThreadInitializer(m_AnalyzerDataCB,
				new CallbackThreadInitializer(false, false, "traffic callback thread"));
		if(LoginModule.login(loginPanel.ipTextArea.getText(),
				Integer.parseInt(loginPanel.portTextArea.getText()),
				loginPanel.nameTextArea.getText(),
				new String(loginPanel.passwordTextArea.getPassword()))) {

			loginPanel.setButtonEnable(true);
			setButtonEnable(true);

			for(int i = 1; i < LoginModule.m_stDeviceInfo.byChanNum + 1; i++) {
				chnlist.add(Res.string().getChannel() + " " + String.valueOf(i));
			}

			// 登陆成功，将通道添加到控件
			chnComboBox.setModel(new DefaultComboBoxModel(chnlist));
		} else {
			JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	// 登出
	public void logout() {
		RealPlayModule.stopRealPlay(m_hPlayHandle);
		TrafficEventModule.detachIVSEvent();
		LoginModule.logout();

		loginPanel.setButtonEnable(false);
		setButtonEnable(false);
		realPlayWindow.repaint();
		eventnameTextField.setText("");
		licensePlateTextField.setText("");
		eventTimeTextField.setText("");

		b_realplay = false;
		realplayBtn.setText(Res.string().getStartRealPlay());
		b_attach = false;
		attachBtn.setText(Res.string().getAttach());
		b_openStrobe = false;
		openStrobeButton.setText(Res.string().getOpenStrobe());

		i = 1; // 列表序号置1

		for(int i = 0; i < LoginModule.m_stDeviceInfo.byChanNum; i++) {
			chnlist.clear();
		}

		chnComboBox.setModel(new DefaultComboBoxModel());

		// 列表清空
		defaultModel.setRowCount(0);
		defaultModel.setRowCount(8);
		table.updateUI();

		SnapImagePanel.setOpaque(true);
		SnapImagePanel.repaint();

		plateImagePanel.setOpaque(true);
		plateImagePanel.repaint();
	}

	private class ItsPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public ItsPanel() {
			BorderEx.set(this, null, 2);
			setLayout(new BorderLayout());

			operatePanel = new OperatePanel();
			realPlayPanel = new RealPlayPanel();
			eventInfoPanel = new EventInfoPanel();
			messagePanel = new MessagePanel();

			add(operatePanel, BorderLayout.NORTH);
			add(realPlayPanel, BorderLayout.WEST);
			add(eventInfoPanel, BorderLayout.CENTER);
			add(messagePanel, BorderLayout.SOUTH);
		}
	}

	/**
	 * 操作面板
	 */
	private class OperatePanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public OperatePanel() {
			BorderEx.set(this, Res.string().getOperate(), 2);
			setLayout(new FlowLayout());

			chnlabel = new JLabel(Res.string().getChannel());
			chnComboBox = new JComboBox();

			JLabel nullLabel2 = new JLabel("   ");
			JLabel nullLabel3 = new JLabel("   ");
			JLabel nullLabel4 = new JLabel("   ");
			JLabel nullLabel5 = new JLabel("   ");

			realplayBtn = new JButton(Res.string().getStartRealPlay());
			attachBtn = new JButton(Res.string().getAttach());
			manualSnapBtn = new JButton(Res.string().getManualCapture());
			openStrobeButton = new JButton(Res.string().getOpenStrobe());

			chnComboBox.setPreferredSize(new Dimension(100, 20));
			realplayBtn.setPreferredSize(new Dimension(125, 20));
			attachBtn.setPreferredSize(new Dimension(120, 20));
			manualSnapBtn.setPreferredSize(new Dimension(125, 20));
			openStrobeButton.setPreferredSize(new Dimension(120, 20));

			add(chnlabel);
			add(chnComboBox);
			add(nullLabel2);
			add(realplayBtn);
			add(nullLabel3);
			add(attachBtn);
			add(nullLabel4);
			add(manualSnapBtn);
			add(nullLabel5);
			add(openStrobeButton);

			chnComboBox.setEnabled(false);
			realplayBtn.setEnabled(false);
			attachBtn.setEnabled(false);
			manualSnapBtn.setEnabled(false);
			openStrobeButton.setEnabled(false);

			realplayBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					realplay();
				}
			});

			attachBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					attach();
				}
			});

			manualSnapBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!TrafficEventModule.manualSnapPicture(chnComboBox.getSelectedIndex())) {
						JOptionPane.showMessageDialog(null, Res.string().getManualCaptureFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			openStrobeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openstrobe();
				}
			});
		}
	}

	// 预览
	public void realplay() {
		if(!b_realplay) {
			m_hPlayHandle = RealPlayModule.startRealPlay(chnComboBox.getSelectedIndex(), 0,
					realPlayWindow);
			if(m_hPlayHandle.longValue() != 0) {
				realPlayWindow.repaint();
				b_realplay = true;
				chnComboBox.setEnabled(false);
				realplayBtn.setText(Res.string().getStopRealPlay());
			}
		} else {
			RealPlayModule.stopRealPlay(m_hPlayHandle);
			realPlayWindow.repaint();
			b_realplay = false;
			chnComboBox.setEnabled(true);
			realplayBtn.setText(Res.string().getStartRealPlay());
		}
	}

	// 订阅
	public void attach() {
		if(!b_attach) {
			if(TrafficEventModule.attachIVSEvent(chnComboBox.getSelectedIndex(),
					m_AnalyzerDataCB)) {
				b_attach = true;
				attachBtn.setText(Res.string().getDetach());
			}
		} else {
			TrafficEventModule.detachIVSEvent();
			b_attach = false;
			attachBtn.setText(Res.string().getAttach());
		}
	}

	// 出入口开闸
	public void openstrobe() {
		if(!b_openStrobe) {
			if(TrafficEventModule.New_OpenStrobe()) {
				b_openStrobe = true;
				openStrobeButton.setText(Res.string().getCloseStrobe());
			} else {
				JOptionPane.showMessageDialog(null, Res.string().getOpenStrobeFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			}
		} else {
			TrafficEventModule.New_CloseStrobe();
			b_openStrobe = false;
			openStrobeButton.setText(Res.string().getOpenStrobe());
		}
	}
	/**
	 * 预览面板
	 */
	private class RealPlayPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public RealPlayPanel() {
			BorderEx.set(this, Res.string().getRealplay(), 2);
			setLayout(new BorderLayout());
			Dimension dim = getPreferredSize();
			dim.height = 280;
			dim.width = 320;
			setPreferredSize(dim);

			JPanel reaJPanel = new JPanel();
			reaJPanel.setLayout(new BorderLayout());
			reaJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			add(reaJPanel, BorderLayout.CENTER);

			realPlayWindow = new Panel();
			realPlayWindow.setBackground(Color.GRAY);

			reaJPanel.add(realPlayWindow, BorderLayout.CENTER);
		}
	}

	/**
	 * 事件及图片面板
	 */
	private class EventInfoPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public EventInfoPanel() {
			Dimension dimension = new Dimension(-1, -1);

			BorderEx.set(this, Res.string().getEventPicture(), 2);
			setLayout(new BorderLayout());

			//////// 车牌及抓图时间面板
			JPanel paramPanel = new JPanel();
			JPanel textPanel = new JPanel();
			JPanel platePanel = new JPanel();

			///////////车牌小图
			plateImagePanel = new PaintPanel(); // 车牌小图
			plateImageLabel = new JLabel(Res.string().getPlatePicture());

			dimension.width = 145;
			dimension.height = 49;
			plateImagePanel.setPreferredSize(dimension);
			platePanel.setLayout(new BorderLayout());
			platePanel.add(plateImagePanel, BorderLayout.SOUTH);
			platePanel.add(plateImageLabel, BorderLayout.CENTER);

			///////////
			eventnameLabel = new JLabel(Res.string().getEventName());
			eventnameTextField = new JTextField("");
			eventTimeLabel = new JLabel(Res.string().getEventTime());
			eventTimeTextField = new JTextField("");
			licensePlateLabel = new JLabel(Res.string().getLicensePlate());
			licensePlateTextField = new JTextField("");

			dimension.width = 165;
			dimension.height = 45;
			paramPanel.setPreferredSize(dimension);
			paramPanel.setLayout(new BorderLayout());
			paramPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

			textPanel.setLayout(new GridLayout(6, 2));
			textPanel.add(eventnameLabel);
			textPanel.add(eventnameTextField);
			textPanel.add(eventTimeLabel);
			textPanel.add(eventTimeTextField);
			textPanel.add(licensePlateLabel);
			textPanel.add(licensePlateTextField);
			eventnameTextField.setEditable(false);
			eventTimeTextField.setEditable(false);
			licensePlateTextField.setEditable(false);


			paramPanel.add(platePanel, BorderLayout.NORTH);
			paramPanel.add(textPanel, BorderLayout.CENTER);

			///////// 事件大图面板 ////////////////////////////
			SnapImagePanel = new PaintPanel(); // 事件大图
			SnapImagePanel.setSize(291, 200);

			JPanel snapJPanel = new JPanel();
			snapJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			snapJPanel.setLayout(new BorderLayout());
			snapJPanel.add(SnapImagePanel, BorderLayout.CENTER);

			add(snapJPanel, BorderLayout.CENTER);
			add(paramPanel, BorderLayout.WEST);
		}
	}

	/**
	 * 事件信息显示面板
	 */
	private class MessagePanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public MessagePanel() {
			BorderEx.set(this, Res.string().getEventInfo(), 2);

			Dimension dim = getPreferredSize();
			dim.height = 195;
			setPreferredSize(dim);
			setLayout(new BorderLayout());

			//////////////
			defaultModel = new DefaultTableModel(null, Res.string().getTrafficTableName());
			table = new JTable(defaultModel) {   // 列表不可编辑
				private static final long serialVersionUID = 1L;
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			defaultModel.setRowCount(8);
			model = (DefaultTableModel)table.getModel();

			table.getColumnModel().getColumn(0).setPreferredWidth(50);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
			table.getColumnModel().getColumn(3).setPreferredWidth(140);
			table.getColumnModel().getColumn(4).setPreferredWidth(80);
			table.getColumnModel().getColumn(5).setPreferredWidth(80);
			table.getColumnModel().getColumn(6).setPreferredWidth(80);
			table.getColumnModel().getColumn(7).setPreferredWidth(80);
			table.getColumnModel().getColumn(8).setPreferredWidth(80);
			table.getColumnModel().getColumn(9).setPreferredWidth(80);
			table.getColumnModel().getColumn(10).setPreferredWidth(60);
			table.getColumnModel().getColumn(11).setPreferredWidth(100);
			table.getColumnModel().getColumn(12).setPreferredWidth(100);
			table.getColumnModel().getColumn(13).setPreferredWidth(90);

			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 只能选中一行

			// 列表显示居中
			DefaultTableCellRenderer dCellRenderer = new DefaultTableCellRenderer();
			dCellRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.setDefaultRenderer(Object.class, dCellRenderer);

			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			JScrollPane scrollPane = new JScrollPane(table);
			add(scrollPane, BorderLayout.CENTER);

			table.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() < 2) {
						return;
					}
					// 列表点击显示图片
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							int row = table.getSelectedRow();

							if(model.getValueAt(row, 3) == null || String.valueOf(model.getValueAt(row, 3)).trim().equals("")) {
								return;
							}

							if(model.getValueAt(row, 9) == null || String.valueOf(model.getValueAt(row, 9)).trim().equals("")) {
								return;
							}

							if(model.getValueAt(row, 10) == null || String.valueOf(model.getValueAt(row, 10)).trim().equals("")) {
								return;
							}

							if(model.getValueAt(row, 11) == null || String.valueOf(model.getValueAt(row, 11)).trim().equals("")) {
								return;
							}

							String str2 = String.valueOf(model.getValueAt(row, 3)).trim().replace(" ", "_").replace("/", "").replace(":", "");
							String str9 = "_" +String.valueOf(model.getValueAt(row, 9)).trim();
							String str10 = "-" + String.valueOf(model.getValueAt(row, 10)).trim();
							String str11 = "-" + String.valueOf(model.getValueAt(row, 11)).trim();

							String selectPicture = SavePath.getSavePath().getSaveTrafficImagePath() + "Big_Time_" + str2 + str9 + str10 + str11 + ".jpg";
							BufferedImage bufferedImage = null;

							if(selectPicture == null || selectPicture.equals("")) {
								return;
							}

							File file = new File(selectPicture);
							if(!file.exists()) {
								return;
							}
							try {
								bufferedImage = ImageIO.read(file);
							} catch (IOException e) {
								e.printStackTrace();
							}
							ListPictureShowDialog demo = new ListPictureShowDialog();
							demo.listPanel.setOpaque(false);
							demo.listPanel.setImage(bufferedImage);
							demo.setVisible(true);
						}
					});
				}
			});
		}
	}

	class TrafficEvent extends AWTEvent {
		private static final long serialVersionUID = 1L;
		public static final int EVENT_ID = AWTEvent.RESERVED_ID_MAX + 1;

		private TRAFFIC_INFO trafficInfo = null;
		private BufferedImage snapImage = null;
		private BufferedImage plateImage = null;

		public TrafficEvent(Object target,
							BufferedImage snapImage,
							BufferedImage plateImage,
							TRAFFIC_INFO info) {
			super(target,EVENT_ID);

			this.snapImage = snapImage;
			this.plateImage = plateImage;
			this.trafficInfo = info;
		}

		public BufferedImage getSnapBufferedImage() {
			return snapImage;
		}

		public BufferedImage getPlaBufferedImage() {
			return plateImage;
		}

		public TRAFFIC_INFO getTrafficInfo() {
			return trafficInfo;
		}
	}

	@Override
	protected void processEvent( AWTEvent event)
	{
		if ( event instanceof TrafficEvent )
		{

			TrafficEvent ev = (TrafficEvent) event;

			TRAFFIC_INFO trafficInfo =  ev.getTrafficInfo();
			BufferedImage snapImage = ev.getSnapBufferedImage();
			BufferedImage plateImage = ev.getPlaBufferedImage();

			// 列表显示事件信息
			showTrafficEventInfo(trafficInfo);

			// 界面显示图片
			showPicture(snapImage, plateImage);
		}

		else    // other events go to the system default process event handler
		{
			super.processEvent( event );
		}
	}

	/*
	 * 智能报警事件回调
	 */
	private class AnalyzerDataCB implements NetSDKLib.fAnalyzerDataCallBack {
		public int invoke(LLong lAnalyzerHandle, int dwAlarmType,
						  Pointer pAlarmInfo, Pointer pBuffer, int dwBufSize,
						  Pointer dwUser, int nSequence, Pointer reserved)
		{
			if (lAnalyzerHandle.longValue() == 0) {
				return -1;
			}

			if(dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFICJUNCTION
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_RUNREDLIGHT
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_OVERLINE
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_RETROGRADE
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_TURNLEFT
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_TURNRIGHT
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_UTURN
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_OVERSPEED
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_UNDERSPEED
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_PARKING
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_WRONGROUTE
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_CROSSLANE
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_OVERYELLOWLINE
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_YELLOWPLATEINLANE
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_PEDESTRAINPRIORITY
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_MANUALSNAP
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_VEHICLEINROUTE
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_VEHICLEINBUSROUTE
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_BACKING
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_PARKINGSPACEPARKING
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_PARKINGSPACENOPARKING
					|| dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_WITHOUT_SAFEBELT) {

				// 获取识别对象 车身对象 事件发生时间 车道号等信息
				GetStuObject(dwAlarmType, pAlarmInfo);

				boolean flag=true;

				if((dwAlarmType == NetSDKLib.EVENT_IVS_TRAFFIC_MANUALSNAP)){
					NetSDKLib.DEV_EVENT_TRAFFIC_MANUALSNAP_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_MANUALSNAP_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);
					byte bPicEnble = msg.stuObject.bPicEnble;

					if(bPicEnble==0){
						flag=false;
					}
				}

				if(flag){
					// 保存图片，获取图片缓存
					savePlatePic(pBuffer, dwBufSize, trafficInfo);

					// 列表、图片界面显示
					EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
					if (eventQueue != null)
					{
						eventQueue.postEvent( new TrafficEvent(target,
								snapImage,
								plateImage,
								trafficInfo));
					}
				}else {
					// 保存图片，获取图片缓存
					savePlatePic(pBuffer, dwBufSize, trafficInfo);

					// 列表、图片界面显示
					EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
					if (eventQueue != null)
					{
						eventQueue.postEvent( new TrafficEvent(target,
								snapImage,
								null,
								trafficInfo));
					}
				}


			}

			return 0;
		}

		// 获取识别对象 车身对象 事件发生时间 车道号等信息
		private void GetStuObject(int dwAlarmType, Pointer pAlarmInfo)  {
			if(pAlarmInfo == null) {
				return;
			}

			switch(dwAlarmType) {
				case NetSDKLib.EVENT_IVS_TRAFFICJUNCTION: ///< 交通卡口事件
				{
					NetSDKLib.DEV_EVENT_TRAFFICJUNCTION_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFICJUNCTION_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFICJUNCTION);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;


					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_RUNREDLIGHT: ///< 闯红灯事件
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_RUNREDLIGHT_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_RUNREDLIGHT_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_RUNREDLIGHT);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_OVERLINE: ///< 压车道线事件
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_OVERLINE_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_OVERLINE_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_OVERLINE);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_RETROGRADE: ///< 逆行事件
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_RETROGRADE_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_RETROGRADE_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_RETROGRADE);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_TURNLEFT: ///< 违章左转
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_TURNLEFT_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_TURNLEFT_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_TURNLEFT);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_TURNRIGHT: ///< 违章右转
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_TURNRIGHT_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_TURNRIGHT_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_TURNRIGHT);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_UTURN: ///< 违章掉头
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_UTURN_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_UTURN_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_UTURN);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_OVERSPEED: ///< 超速
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_OVERSPEED_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_OVERSPEED_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_OVERSPEED);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_UNDERSPEED: ///< 低速
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_UNDERSPEED_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_UNDERSPEED_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_UNDERSPEED);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_PARKING: ///< 违章停车
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_PARKING_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_PARKING_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_PARKING);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_WRONGROUTE: ///< 不按车道行驶
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_WRONGROUTE_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_WRONGROUTE_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_WRONGROUTE);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_CROSSLANE: ///< 违章变道
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_CROSSLANE_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_CROSSLANE_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_CROSSLANE);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stuTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stuTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stuTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stuTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stuTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_OVERYELLOWLINE: ///< 压黄线
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_OVERYELLOWLINE_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_OVERYELLOWLINE_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_OVERYELLOWLINE);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_YELLOWPLATEINLANE: ///< 黄牌车占道事件
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_YELLOWPLATEINLANE_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_YELLOWPLATEINLANE_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_YELLOWPLATEINLANE);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_PEDESTRAINPRIORITY: ///< 斑马线行人优先事件
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_PEDESTRAINPRIORITY_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_PEDESTRAINPRIORITY_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_PEDESTRAINPRIORITY);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_MANUALSNAP: ///< 交通手动抓拍事件
				{
					JOptionPane.showMessageDialog(null, Res.string().getManualCaptureSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
					NetSDKLib.DEV_EVENT_TRAFFIC_MANUALSNAP_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_MANUALSNAP_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_MANUALSNAP);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;

					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;


					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_VEHICLEINROUTE: ///< 有车占道事件
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_VEHICLEINROUTE_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_VEHICLEINROUTE_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_VEHICLEINROUTE);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_VEHICLEINBUSROUTE: ///< 占用公交车道事件
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_VEHICLEINBUSROUTE_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_VEHICLEINBUSROUTE_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_VEHICLEINBUSROUTE);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_BACKING: ///< 违章倒车事件
				{
					NetSDKLib.DEV_EVENT_IVS_TRAFFIC_BACKING_INFO msg = new NetSDKLib.DEV_EVENT_IVS_TRAFFIC_BACKING_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_BACKING);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_PARKINGSPACEPARKING: ///< 车位有车事件
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_PARKINGSPACEPARKING_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_PARKINGSPACEPARKING_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_PARKINGSPACEPARKING);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_PARKINGSPACENOPARKING: ///< 车位无车事件
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_PARKINGSPACENOPARKING_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_PARKINGSPACENOPARKING_INFO();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_PARKINGSPACENOPARKING);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				case NetSDKLib.EVENT_IVS_TRAFFIC_WITHOUT_SAFEBELT: ///< 交通未系安全带事件
				{
					NetSDKLib.DEV_EVENT_TRAFFIC_WITHOUT_SAFEBELT msg = new NetSDKLib.DEV_EVENT_TRAFFIC_WITHOUT_SAFEBELT();
					ToolKits.GetPointerData(pAlarmInfo, msg);

					trafficInfo.m_EventName = Res.string().getEventName(NetSDKLib.EVENT_IVS_TRAFFIC_WITHOUT_SAFEBELT);
					try {
						trafficInfo.m_PlateNumber = new String(msg.stuObject.szText, "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					trafficInfo.m_PlateType = new String(msg.stuTrafficCar.szPlateType).trim();
					trafficInfo.m_FileCount = String.valueOf(msg.stuFileInfo.bCount);
					trafficInfo.m_FileIndex = String.valueOf(msg.stuFileInfo.bIndex);
					trafficInfo.m_GroupID =  String.valueOf(msg.stuFileInfo.nGroupId);
					trafficInfo.m_IllegalPlace = ToolKits.GetPointerDataToByteArr(msg.stuTrafficCar.szDeviceAddress);
					trafficInfo.m_LaneNumber = String.valueOf(msg.nLane);
					trafficInfo.m_PlateColor = new String(msg.stuTrafficCar.szPlateColor).trim();
					trafficInfo.m_VehicleColor = new String(msg.stuTrafficCar.szVehicleColor).trim();
					trafficInfo.m_VehicleType = new String(msg.stuVehicle.szObjectSubType).trim();
					trafficInfo.m_VehicleSize = Res.string().getTrafficSize(msg.stuTrafficCar.nVehicleSize);
					trafficInfo.m_Utc = msg.UTC;
					trafficInfo.m_bPicEnble = msg.stuObject.bPicEnble;
					trafficInfo.m_OffSet = msg.stuObject.stPicInfo.dwOffSet;
					trafficInfo.m_FileLength = msg.stuObject.stPicInfo.dwFileLenth;
					trafficInfo.m_BoundingBox = msg.stuObject.BoundingBox;

					break;
				}
				default:
					break;
			}
		}

	}

	/*
	 * 显示事件名称、车牌号、事件时间
	 */
	private void showTrafficEventInfo(TRAFFIC_INFO trafficInfo) {
		// 事件名称
		if(trafficInfo.m_EventName.equals("")) {
			eventnameTextField.setText("");
		} else {
			eventnameTextField.setText(trafficInfo.m_EventName);
		}

		// 车牌号
		if(trafficInfo.m_PlateNumber.equals("") ) {
			licensePlateTextField.setText(Res.string().getNoPlate());
			plateImagePanel.setOpaque(true);
			plateImagePanel.repaint();
		} else {
			licensePlateTextField.setText(trafficInfo.m_PlateNumber);
		}

		// 事件时间
		if(trafficInfo.m_Utc == null
				|| trafficInfo.m_Utc.toStringTime().equals("")) {
			eventTimeTextField.setText("");
		} else {
			eventTimeTextField.setText(trafficInfo.m_Utc.toStringTime());
		}

		Vector<String> vector = new Vector<String>();


		vector.add(String.valueOf(i)); 					// 序号
		vector.add(trafficInfo.m_EventName); 			// 事件名称
		vector.add(trafficInfo.m_PlateNumber); 			// 车牌号

		// 事件时间
		if(trafficInfo.m_Utc == null || trafficInfo.m_Utc.toStringTime().equals("")) {
			vector.add("");
		} else {
			vector.add(trafficInfo.m_Utc.toStringTime());
		}

		vector.add(trafficInfo.m_PlateType); 			// 车牌类型
		vector.add(trafficInfo.m_PlateColor); 			// 车牌颜色
		vector.add(trafficInfo.m_VehicleColor); 		// 车身颜色
		vector.add(trafficInfo.m_VehicleType); 			// 车身类型
		vector.add(trafficInfo.m_VehicleSize); 			// 车辆大小
		vector.add(trafficInfo.m_FileCount); 			// 文件总数
		vector.add(trafficInfo.m_FileIndex); 			// 文件编号
		vector.add(trafficInfo.m_GroupID); 				// 组ID
		vector.add(trafficInfo.m_IllegalPlace); 		// 违法地点
		vector.add(trafficInfo.m_LaneNumber); 			// 车道号

		defaultModel.insertRow(0, vector);
		defaultModel.setRowCount(8);

		table.updateUI();

		i++;
	}

	/*
	 * 界面显示图片
	 */
	private void showPicture(BufferedImage snapImage, BufferedImage plateImage) {
		if (snapImage == null) {
			SnapImagePanel.setOpaque(true);  // 不透明
			SnapImagePanel.repaint();

			plateImagePanel.setOpaque(true);
			plateImagePanel.repaint();
			return;
		}

		SnapImagePanel.setOpaque(false);  // 透明
		SnapImagePanel.setImage(snapImage);
		SnapImagePanel.repaint();

		if(plateImage == null) {
			plateImagePanel.setOpaque(true);
			plateImagePanel.repaint();
			return;
		}

		plateImagePanel.setOpaque(false);
		plateImagePanel.setImage(plateImage);
		plateImagePanel.repaint();
	}
	/*
	 * 保存车牌小图:DH早期交通抓拍机，设备不传单独的车牌小图文件，只传车牌在大图中的坐标;由应用来自行裁剪。
	 * 2014年后，陆续有设备版本，支持单独传车牌小图，小图附录在pBuffer后面。
	 */
	private void savePlatePic(Pointer pBuffer, int dwBufferSize, TRAFFIC_INFO trafficInfo) {

		String bigPicture; // 大图
		String platePicture; // 车牌图

		if (pBuffer == null || dwBufferSize <= 0 ) {
			return;
		}

		// 保存大图
		byte[] buffer = pBuffer.getByteArray(0, dwBufferSize);
		ByteArrayInputStream byteArrInput = new ByteArrayInputStream(buffer);

		bigPicture = SavePath.getSavePath().getSaveTrafficImagePath() + "Big_" + trafficInfo.m_Utc.toStringTitle() + "_" +
				trafficInfo.m_FileCount + "-" + trafficInfo.m_FileIndex + "-" + trafficInfo.m_GroupID + ".jpg";

		try {
			snapImage = ImageIO.read(byteArrInput);
			if(snapImage == null) {
				return;
			}
			ImageIO.write(snapImage, "jpg", new File(bigPicture));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		if(bigPicture == null || bigPicture.equals("")) {
			return;
		}

		if (trafficInfo.m_bPicEnble == 1) {
			//根据pBuffer中数据偏移保存小图图片文件
			if (trafficInfo.m_FileLength > 0) {
				platePicture = SavePath.getSavePath().getSaveTrafficImagePath() + "plate_" + trafficInfo.m_Utc.toStringTitle() + "_" +
						trafficInfo.m_FileCount + "-" + trafficInfo.m_FileIndex + "-" + trafficInfo.m_GroupID + ".jpg";

				int size = 0;
				if(dwBufferSize <= trafficInfo.m_OffSet) {
					return;
				}

				if(trafficInfo.m_FileLength <= dwBufferSize - trafficInfo.m_OffSet) {
					size = trafficInfo.m_FileLength;
				} else {
					size = dwBufferSize - trafficInfo.m_OffSet;
				}
				byte[] bufPlate = pBuffer. getByteArray(trafficInfo.m_OffSet, size);
				ByteArrayInputStream byteArrInputPlate = new ByteArrayInputStream(bufPlate);
				try {
					plateImage = ImageIO.read(byteArrInputPlate);
					if(plateImage == null) {
						return;
					}
					ImageIO.write(plateImage, "jpg", new File(platePicture));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			if(trafficInfo.m_BoundingBox == null) {
				return;
			}
			//根据大图中的坐标偏移计算显示车牌小图

			NetSDKLib.DH_RECT dhRect = trafficInfo.m_BoundingBox;
			//1.BoundingBox的值是在8192*8192坐标系下的值，必须转化为图片中的坐标
			//2.OSD在图片中占了64行,如果没有OSD，下面的关于OSD的处理需要去掉(把OSD_HEIGHT置为0)
			final int OSD_HEIGHT = 0;

			long nWidth = snapImage.getWidth(null);
			long nHeight = snapImage.getHeight(null);

			nHeight = nHeight - OSD_HEIGHT;
			if ((nWidth <= 0) || (nHeight <= 0)) {
				return ;
			}

			NetSDKLib.DH_RECT dstRect = new NetSDKLib.DH_RECT();

			dstRect.left.setValue((long)((double)(nWidth * dhRect.left.longValue()) / 8192.0));
			dstRect.right.setValue((long)((double)(nWidth * dhRect.right.longValue()) / 8192.0));
			dstRect.bottom.setValue((long)((double)(nHeight * dhRect.bottom.longValue()) / 8192.0));
			dstRect.top.setValue((long)((double)(nHeight * dhRect.top.longValue()) / 8192.0));

			int x = dstRect.left.intValue();
			int y = dstRect.top.intValue() + OSD_HEIGHT;
			int w = dstRect.right.intValue() - dstRect.left.intValue();
			int h = dstRect.bottom.intValue() - dstRect.top.intValue();

			if(x == 0 || y == 0 || w <= 0 || h <= 0) {
				return;
			}

			try {
				plateImage = snapImage.getSubimage(x, y, w, h);
				platePicture = SavePath.getSavePath().getSaveTrafficImagePath() + "plate_" + trafficInfo.m_Utc.toStringTitle() + "_" +
						trafficInfo.m_FileCount + "-" + trafficInfo.m_FileIndex + "-" + trafficInfo.m_GroupID + ".jpg";
				if(plateImage == null) {
					return;
				}
				ImageIO.write(plateImage, "jpg", new File(platePicture));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setButtonEnable(boolean bln) {
		chnComboBox.setEnabled(bln);
		realplayBtn.setEnabled(bln);
		attachBtn.setEnabled(bln);
		manualSnapBtn.setEnabled(bln);
		openStrobeButton.setEnabled(bln);
	}

	//登录组件
	private LoginPanel loginPanel;

	private ItsPanel itsPanel;
	private OperatePanel operatePanel;
	private JButton realplayBtn;
	private JButton attachBtn;

	/**
	 * 事件信息显示组件
	 */
	private MessagePanel messagePanel;
	private DefaultTableModel defaultModel;
	private JTable table;

	/**
	 * 实时预览组件
	 */
	private RealPlayPanel realPlayPanel;
	Panel realPlayWindow;
	private JLabel chnlabel;
	JComboBox chnComboBox;
	JComboBox streamComboBox;

	/**
	 * 事件及图片组件
	 */
	private EventInfoPanel eventInfoPanel;
	private PaintPanel SnapImagePanel;
	private PaintPanel plateImagePanel;
	private JLabel plateImageLabel;

	private JLabel eventnameLabel;
	private JTextField eventnameTextField;

	private JLabel eventTimeLabel;
	private JTextField eventTimeTextField;

	private JLabel licensePlateLabel;
	private JTextField licensePlateTextField;

	private JButton manualSnapBtn;
	private JButton openStrobeButton;

}

public class TrafficEvent {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TrafficEventFrame demo = new TrafficEventFrame();
				demo.setVisible(true);
			}
		});
	}
};

