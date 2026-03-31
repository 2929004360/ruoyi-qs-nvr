package com.ruoyi.dahua.frame.Gate;

import com.ruoyi.dahua.common.*;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.*;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.GateModule;
import com.ruoyi.dahua.module.LoginModule;
import com.sun.jna.Pointer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

class GateFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// 获取界面窗口
	private static JFrame frame = new JFrame();
	
	// 设备断线通知回调
	private static DisConnect disConnect       = new DisConnect(); 
	
	// 网络连接恢复
	private static HaveReConnect haveReConnect = new HaveReConnect(); 
	
	// 订阅句柄
	public static LLong m_hAttachHandle = new LLong(0);
	
	private Vector<String> chnList = new Vector<String>(); 
	
	private AnalyzerDataCB analyzerCallback = new AnalyzerDataCB();

	private Component target = this;
	
	private boolean isAttach = false;
	
	public GateFrame() {
	    setTitle(Res.string().getGate());
	    setLayout(new BorderLayout());
	    pack();
	    setSize(800, 400);
	    setResizable(false);
	    setLocationRelativeTo(null);
		LoginModule.init(disConnect, haveReConnect);   // 打开工程，初始化
		
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        	e.printStackTrace();
        } 
	 
	    loginPanel = new LoginPanel();
	    GatePanel gatePanel = new GatePanel();
	

	    add(loginPanel, BorderLayout.NORTH);
	    add(gatePanel, BorderLayout.CENTER);

	    
	    loginPanel.addLoginBtnActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				if(loginPanel.checkLoginText()) {
					if(login()) {
						frame = ToolKits.getFrame(e);
						frame.setTitle(Res.string().getGate() + " : " + Res.string().getOnline());
					}
				}	
			}
		});
	    
	    loginPanel.addLogoutBtnActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setTitle(Res.string().getGate());
				logout();				
			}
		});
        
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		GateModule.stopRealLoadPic(m_hAttachHandle);
	    		LoginModule.logout();
	    		LoginModule.cleanup();   // 关闭工程，释放资源
	    			
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
	private static class DisConnect implements fDisConnect {
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
			// 断线提示
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setTitle(Res.string().getGate() + " : " + Res.string().getDisConnectReconnecting());
				}
			});
		}
	}
	
	// 网络连接恢复，设备重连成功回调
	// 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
	private static class HaveReConnect implements fHaveReConnect {
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
			
			// 重连提示
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setTitle(Res.string().getGate() + " : " + Res.string().getOnline());
				}
			});
		}
	}
	
	// 登录
	public boolean login() {
		if(LoginModule.login(loginPanel.ipTextArea.getText(), 
						Integer.parseInt(loginPanel.portTextArea.getText()), 
						loginPanel.nameTextArea.getText(), 
						new String(loginPanel.passwordTextArea.getPassword()))) {	
			
			for(int i = 1; i < LoginModule.m_stDeviceInfo.byChanNum + 1; i++) {
				chnList.add(Res.string().getChannel() + " " + String.valueOf(i));
			}
			
			// 登陆成功，将通道添加到控件
			chnComboBox.setModel(new DefaultComboBoxModel(chnList));
			
			loginPanel.setButtonEnable(true);
			setEnable(true);
			
		} else {
			JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	// 登出
	public void logout() {
		GateModule.stopRealLoadPic(m_hAttachHandle);
		LoginModule.logout();

		loginPanel.setButtonEnable(false);
		
		for(int i = 0; i < LoginModule.m_stDeviceInfo.byChanNum; i++) {
			chnList.clear();
		}
		
		chnComboBox.setModel(new DefaultComboBoxModel());	
		setEnable(false);
		detachBtn.setEnabled(false);
		
		isAttach = false;
		
		clearPanel();
	}
	
	/**
	 * 闸机界面面板
	 */
	private class GatePanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public GatePanel() {
			BorderEx.set(this, "", 4);
			setLayout(new BorderLayout());
			
			JPanel gateOperatePanel = new JPanel();
			JPanel gateShowPanel = new JPanel();
			
			add(gateOperatePanel, BorderLayout.WEST);
			add(gateShowPanel, BorderLayout.CENTER);
			
			/**
			 *  闸机操作面板
			 */
			gateOperatePanel.setLayout(new BorderLayout());
			gateOperatePanel.setPreferredSize(new Dimension(250, 70));
			
			JPanel channelPanel = new JPanel();
			JPanel operatePanel = new JPanel();		
			gateOperatePanel.add(channelPanel, BorderLayout.NORTH);
			gateOperatePanel.add(operatePanel, BorderLayout.CENTER);
			
			// 通道面板
			channelPanel.setBorder(BorderFactory.createTitledBorder(""));
			channelPanel.setPreferredSize(new Dimension(220, 70));
			channelPanel.setLayout(new FlowLayout());
			
			JLabel channelLabel = new JLabel(Res.string().getChannel());
			chnComboBox = new JComboBox(); 
			
			chnComboBox.setPreferredSize(new Dimension(100, 20));
			
			channelPanel.add(channelLabel);
			channelPanel.add(chnComboBox);
			
			// 按钮面板
			operatePanel.setBorder(BorderFactory.createTitledBorder(Res.string().getOperate()));
			operatePanel.setLayout(new FlowLayout());
			
			attachBtn = new JButton(Res.string().getAttach());
			detachBtn = new JButton(Res.string().getDetach());
			cardOperateBtn = new JButton(Res.string().getCardOperate());
			JLabel nullJLabel = new JLabel("");
			
			nullJLabel.setPreferredSize(new Dimension(205, 40));
			attachBtn.setPreferredSize(new Dimension(100, 20));
			detachBtn.setPreferredSize(new Dimension(100, 20));
			cardOperateBtn.setPreferredSize(new Dimension(205, 20));

			operatePanel.add(attachBtn);
			operatePanel.add(detachBtn);
			operatePanel.add(nullJLabel);
			operatePanel.add(cardOperateBtn);
			
			setEnable(false);
			detachBtn.setEnabled(false);
			
			/**
			 * 闸机订阅展示面板
			 */
			gateShowPanel.setBorder(BorderFactory.createTitledBorder(""));
			gateShowPanel.setLayout(new BorderLayout());
			
			personPaintPanel = new PaintPanel();
			JPanel cardInfoPanel = new JPanel();
			
			personPaintPanel.setPreferredSize(new Dimension(250, 70));			
			
			gateShowPanel.add(personPaintPanel, BorderLayout.WEST);
			gateShowPanel.add(cardInfoPanel, BorderLayout.CENTER);
			
			//
			cardInfoPanel.setLayout(new FlowLayout());
			
			JLabel timeLable = new JLabel(Res.string().getTime() + ":", JLabel.CENTER);
			JLabel openStatusLable = new JLabel(Res.string().getOpenStatus() + ":", JLabel.CENTER);
			JLabel openMethodLable = new JLabel(Res.string().getOpenMethod() + ":", JLabel.CENTER);
			JLabel cardNameLable = new JLabel(Res.string().getCardName() + ":", JLabel.CENTER);
			JLabel cardNoLable = new JLabel(Res.string().getCardNo() + ":", JLabel.CENTER);
			JLabel userIdLable = new JLabel(Res.string().getUserId() + ":", JLabel.CENTER);
			JLabel tempLable = new JLabel(Res.string().getTemp() + ":", JLabel.CENTER);
			JLabel maskstutasLable = new JLabel(Res.string().getMaskstutas() + ":", JLabel.CENTER);
			
			
			
			timeLable.setPreferredSize(new Dimension(80, 20));
			openStatusLable.setPreferredSize(new Dimension(80, 20));
			openMethodLable.setPreferredSize(new Dimension(80, 20));
			cardNameLable.setPreferredSize(new Dimension(80, 20));
			cardNoLable.setPreferredSize(new Dimension(80, 20));
			userIdLable.setPreferredSize(new Dimension(80, 20));
			tempLable.setPreferredSize(new Dimension(80, 20));
			maskstutasLable.setPreferredSize(new Dimension(80, 20));
			
			
			timeTextField = new JTextField("");
			openStatusTextField = new JTextField("");
			openMethodTextField = new JTextField("");
			cardNameTextField = new JTextField("");
			cardNoTextField = new JTextField("");
			userIdTextField = new JTextField("");
			tempTextField = new JTextField("");
			maskStatusTextField = new JTextField("");
			
			
			Dimension dimension = new Dimension();
			dimension.width = 150;
			dimension.height = 20;
			timeTextField.setPreferredSize(dimension);
			openStatusTextField.setPreferredSize(dimension);
			openMethodTextField.setPreferredSize(dimension);
			cardNameTextField.setPreferredSize(dimension);
			cardNoTextField.setPreferredSize(dimension);
			userIdTextField.setPreferredSize(dimension);
			tempTextField.setPreferredSize(dimension);
			maskStatusTextField.setPreferredSize(dimension);			
			
			
			timeTextField.setHorizontalAlignment(JTextField.CENTER);
			openStatusTextField.setHorizontalAlignment(JTextField.CENTER);
			openMethodTextField.setHorizontalAlignment(JTextField.CENTER);
			cardNameTextField.setHorizontalAlignment(JTextField.CENTER);
			cardNoTextField.setHorizontalAlignment(JTextField.CENTER);
			userIdTextField.setHorizontalAlignment(JTextField.CENTER);
			tempTextField.setHorizontalAlignment(JTextField.CENTER);
			maskStatusTextField.setHorizontalAlignment(JTextField.CENTER);			
			
			
			timeTextField.setEditable(false);
			openStatusTextField.setEditable(false);
			openMethodTextField.setEditable(false);
			cardNameTextField.setEditable(false);
			cardNoTextField.setEditable(false);
			userIdTextField.setEditable(false);
			tempTextField.setEditable(false);
			maskStatusTextField.setEditable(false);			
			
			
			cardInfoPanel.add(timeLable);
			cardInfoPanel.add(timeTextField);
			cardInfoPanel.add(openStatusLable);
			cardInfoPanel.add(openStatusTextField);
			cardInfoPanel.add(openMethodLable);
			cardInfoPanel.add(openMethodTextField);
			cardInfoPanel.add(cardNameLable);
			cardInfoPanel.add(cardNameTextField);
			cardInfoPanel.add(cardNoLable);
			cardInfoPanel.add(cardNoTextField);
			cardInfoPanel.add(userIdLable);
			cardInfoPanel.add(userIdTextField);
			cardInfoPanel.add(tempLable);
			cardInfoPanel.add(tempTextField);
			cardInfoPanel.add(maskstutasLable);
			cardInfoPanel.add(maskStatusTextField);
			
			setOnClickListener();
		}
	}
	
	// 监听
	private void setOnClickListener() {
		// 订阅
		attachBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				m_hAttachHandle = GateModule.realLoadPic(chnComboBox.getSelectedIndex(), analyzerCallback);
				if(m_hAttachHandle.longValue() != 0) {
					isAttach = true;
					attachBtn.setEnabled(false);
					detachBtn.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		// 取消订阅
		detachBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GateModule.stopRealLoadPic(m_hAttachHandle);
				synchronized (this) {
					isAttach = false;
				}
				attachBtn.setEnabled(true);
				detachBtn.setEnabled(false);
	        	
	        	clearPanel();
			}
		});
		
		
		// 卡操作
		cardOperateBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {		
				CardManegerDialog dialog = new CardManegerDialog();
				dialog.setVisible(true);
			}
		});
	}
	
	private void setEnable(boolean bln) {
		chnComboBox.setEnabled(bln);
		attachBtn.setEnabled(bln);
		cardOperateBtn.setEnabled(bln);
	}
	
	private void clearPanel() {
	  	personPaintPanel.setOpaque(true); 
    	personPaintPanel.repaint();
    	
    	timeTextField.setText("");
    	openStatusTextField.setText("");
    	openMethodTextField.setText("");
    	cardNameTextField.setText("");
    	cardNoTextField.setText("");
    	userIdTextField.setText("");
    	tempTextField.setText("");
    	maskStatusTextField.setText("");
	}
	
	private class AnalyzerDataCB implements fAnalyzerDataCallBack {
		private BufferedImage gateBufferedImage = null;
		
        public int invoke(LLong lAnalyzerHandle, int dwAlarmType,
		        		 Pointer pAlarmInfo, Pointer pBuffer, int dwBufSize,
		                 Pointer dwUser, int nSequence, Pointer reserved) 
        {
            if (lAnalyzerHandle.longValue() == 0 || pAlarmInfo == null) {
                return -1;
            }   
            
			File path = new File("./GateSnapPicture/");
            if (!path.exists()) {
                path.mkdir();
            }
			
            ///< 门禁事件
			if(dwAlarmType == NetSDKLib.EVENT_IVS_ACCESS_CTL) {
				DEV_EVENT_ACCESS_CTL_INFO msg = new DEV_EVENT_ACCESS_CTL_INFO();
                ToolKits.GetPointerData(pAlarmInfo, msg);  
        	
                // 保存图片，获取图片缓存
            	String snapPicPath = path + "\\" + System.currentTimeMillis() + "GateSnapPicture.jpg";  // 保存图片地址
            	byte[] buffer = pBuffer.getByteArray(0, dwBufSize);
    			ByteArrayInputStream byteArrInputGlobal = new ByteArrayInputStream(buffer);
    			
    			try {
    				gateBufferedImage = ImageIO.read(byteArrInputGlobal);
    				if(gateBufferedImage != null) {
    					ImageIO.write(gateBufferedImage, "jpg", new File(snapPicPath));
    				}	    				
    			} catch (IOException e2) {
    				e2.printStackTrace();
    			}
 	           
 	            // 图片以及门禁信息界面显示                            		      
 	            EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
 	            if (eventQueue != null) {
 	            eventQueue.postEvent( new AccessEvent(target,
 	            									  gateBufferedImage, 
								        		      msg));
 	            }   
			}
                   
			return 0;           
        }
	}
	
	class AccessEvent extends AWTEvent {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public static final int EVENT_ID = AWTEvent.RESERVED_ID_MAX + 1;
		
		private BufferedImage gateBufferedImage = null;
		private DEV_EVENT_ACCESS_CTL_INFO msg = null;
		
		public AccessEvent(Object target,
				     	   BufferedImage gateBufferedImage, 
						   DEV_EVENT_ACCESS_CTL_INFO msg) {
			super(target, EVENT_ID);
			this.gateBufferedImage = gateBufferedImage;
			this.msg = msg;
		}
		
		public BufferedImage getGateBufferedImage() {
			return gateBufferedImage;
		}
		
		public DEV_EVENT_ACCESS_CTL_INFO getAccessInfo() {
			return msg;
		}
	}
	
	@Override
    protected void processEvent(AWTEvent event) {
		if (event instanceof AccessEvent) {  // 门禁事件处理
			AccessEvent ev = (AccessEvent) event;

			BufferedImage gateBufferedImage = ev.getGateBufferedImage();        
	        DEV_EVENT_ACCESS_CTL_INFO msg = ev.getAccessInfo();

	        if(!isAttach) {
             	return;
            }
	         
			// 图片显示
	        if(gateBufferedImage != null) {
	        	personPaintPanel.setImage(gateBufferedImage);
	        	personPaintPanel.setOpaque(false); 
	        	personPaintPanel.repaint();
	        } else {
	        	personPaintPanel.setOpaque(true); 
	        	personPaintPanel.repaint();
	        }
	        
          	// 时间
	        if(msg.UTC == null || msg.UTC.toString().isEmpty()) {
	        	timeTextField.setText("");
	        } else {
	        	msg.UTC.setTime(msg.UTC.dwYear, msg.UTC.dwMonth, msg.UTC.dwDay, msg.UTC.dwHour+8, msg.UTC.dwMinute, msg.UTC.dwSecond);
	        	timeTextField.setText(msg.UTC.toString());
	        }
	        
	        // 开门状态
	        if(msg.bStatus == 1) {
	        	openStatusTextField.setText(Res.string().getSucceed());
	        } else {
	        	openStatusTextField.setText(Res.string().getFailed());
	        }        
	        
	        // 开门方式
	        openMethodTextField.setText(Res.string().getOpenMethods(msg.emOpenMethod));
	        
	        // 卡名
	        try {
				cardNameTextField.setText(new String(msg.szCardName, "GBK").trim());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	        
	        // 卡号
	        cardNoTextField.setText(new String(msg.szCardNo).trim());
	        
	        // 用户ID
	        userIdTextField.setText(new String(msg.szUserID).trim());
	        
	        // 口罩状态
	        maskStatusTextField.setText(Res.string().getMaskStatus(msg.emMask)); 
	        
	        //温度
	        if(msg.stuManTemperatureInfo.emTemperatureUnit==0) {
	        	tempTextField.setText(String.valueOf(msg.stuManTemperatureInfo.fCurrentTemperature+"℃"));
	        }else if(msg.stuManTemperatureInfo.emTemperatureUnit==1){
	        	tempTextField.setText(String.valueOf(msg.stuManTemperatureInfo.fCurrentTemperature+"℉"));
	        }else if(msg.stuManTemperatureInfo.emTemperatureUnit==2) {
	        	tempTextField.setText(String.valueOf(msg.stuManTemperatureInfo.fCurrentTemperature+"K"));
	        }
	        
	        
        } else {
            super.processEvent(event);   
        }
    } 
	
	/*
	 * 登录控件
	 */
	private LoginPanel loginPanel;	
	
    private JComboBox chnComboBox;
    private JButton attachBtn;
    private JButton detachBtn;
    private JButton cardOperateBtn;
    
    private PaintPanel personPaintPanel;
    
    private JTextField timeTextField;
    private JTextField openStatusTextField;
    private JTextField openMethodTextField;
    private JTextField cardNameTextField;
    private JTextField cardNoTextField;
    private JTextField userIdTextField;
    private JTextField tempTextField;
    private JTextField maskStatusTextField;
}

public class Gate {
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GateFrame demo = new GateFrame();	
				demo.setVisible(true);
			}
		});		
	}
}
