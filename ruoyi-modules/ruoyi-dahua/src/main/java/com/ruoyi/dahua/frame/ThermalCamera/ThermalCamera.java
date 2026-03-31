package com.ruoyi.dahua.frame.ThermalCamera;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.FunctionList;
import com.ruoyi.dahua.common.LoginPanel;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.LoginModule;
import com.ruoyi.dahua.module.RealPlayModule;
import com.sun.jna.Pointer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

/**
 * 热成像
 */
class ThermalCameraFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int THERMAL_CHANNEL = 1; // thermal channel
	
	private Vector<String> chnlist = new Vector<String>(); 

	private boolean isrealplayOne = false;
	private boolean isrealplayTwo = false;

	// 设备断线通知回调
	private static DisConnect disConnect       = new DisConnect(); 
	
	// 网络连接恢复
	private static HaveReConnect haveReConnect = new HaveReConnect(); 
	
	// 预览句柄
	public static LLong m_hPlayHandleOne = new LLong(0);
	
	public static LLong m_hPlayHandleTwo = new LLong(0);
	
	// 获取界面窗口
	private static JFrame frame = new JFrame();   
	
	public ThermalCameraFrame() {
	    setTitle(Res.string().getThermalCamera());
	    setLayout(new BorderLayout());
	    pack();
	    setSize(800, 560);
	    setResizable(false);
	    setLocationRelativeTo(null);
		LoginModule.init(disConnect, haveReConnect);   // 打开工程，初始化
		
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        	e.printStackTrace();
        } 
	 
	    loginPanel = new LoginPanel();
	    operatePanel = new ThermalOperatePanel();
	    realPanelOne = new RealPanelOne();
	    realPanelTwo = new RealPanelTwo();
	    
	    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, loginPanel, operatePanel);
	    splitPane.setDividerSize(0);
	    splitPane.setBorder(null);
	    JPanel realPanel = new JPanel();;
	    realPanel.setLayout(new GridLayout(1,  2));
	    realPanel.add(realPanelOne);
	    realPanel.add(realPanelTwo);
	    
		add(splitPane, BorderLayout.NORTH);
	    add(realPanel, BorderLayout.CENTER);
	    
	    loginPanel.addLoginBtnActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	   	    
				if(loginPanel.checkLoginText()) {
					if(login()) {
						frame = ToolKits.getFrame(e);
						frame.setTitle(Res.string().getShowInfo("THERMAL_CAMERA") + " : " + Res.string().getOnline());
					}
				}
			}
		});
	    
	    loginPanel.addLogoutBtnActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setTitle(Res.string().getShowInfo("THERMAL_CAMERA"));
				logout();	
			}
		});
        
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		RealPlayModule.stopRealPlay(m_hPlayHandleOne);
	    		RealPlayModule.stopRealPlay(m_hPlayHandleTwo);
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
	private static class DisConnect implements NetSDKLib.fDisConnect {
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
			// 断线提示
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setTitle(Res.string().getShowInfo("THERMAL_CAMERA") + " : " + Res.string().getDisConnectReconnecting());
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
					frame.setTitle(Res.string().getShowInfo("THERMAL_CAMERA") + " : " + Res.string().getOnline());
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
				chnlist.add(Res.string().getChannel() + " " + String.valueOf(i));
			}
			
			setEnable(true);
			
		} else {
			JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	// 登出
	public void logout() {
		RealPlayModule.stopRealPlay(m_hPlayHandleOne);
		RealPlayModule.stopRealPlay(m_hPlayHandleTwo);
		LoginModule.logout();
		
		setEnable(false);
		chnlist.clear();
	}
	
	public void setEnable(boolean b) {
		loginPanel.setButtonEnable(b);
		realPanelOne.setRealPlayEnable(b);
		realPanelTwo.setRealPlayEnable(b);
		operatePanel.setOperateEnabled(b);
		isrealplayOne = false;
		isrealplayTwo = false;
	}
	
	/*
	 * 热成像操作面板
	 */
	private class ThermalOperatePanel extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		public ThermalOperatePanel() {
			BorderEx.set(this, Res.string().getShowInfo("THERMAL_OPERATE"), 2);
			setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
			
			JLabel chnlabel = new JLabel(Res.string().getChn());
			chnComboBox = new JComboBox();
			
			pointQueryBtn = new JButton(Res.string().getShowInfo("POINT_QUERY"));
			regionQueryBtn = new JButton(Res.string().getShowInfo("REGION_QUERY"));
			itemQueryBtn = new JButton(Res.string().getShowInfo("ITEM_QUERY"));
			historyQueryBtn = new JButton(Res.string().getShowInfo("TEMPER_QUERY"));
			heatMapBtn = new JButton(Res.string().getShowInfo("HEATMAP"));
			
			Dimension btnDimension = new Dimension(110, 20);
			pointQueryBtn.setPreferredSize(btnDimension);
			regionQueryBtn.setPreferredSize(btnDimension);
			itemQueryBtn.setPreferredSize(btnDimension); 
			historyQueryBtn.setPreferredSize(btnDimension); 
			heatMapBtn.setPreferredSize(btnDimension); 
			chnComboBox.setPreferredSize(new Dimension(80, 20)); 

			JPanel chnPanel = new JPanel();
			chnPanel.add(chnlabel);
			chnPanel.add(chnComboBox);

			add(chnPanel);
			add(pointQueryBtn);
			add(regionQueryBtn);
			add(itemQueryBtn);
			add(historyQueryBtn);
			add(heatMapBtn);
			
			setOperateEnabled(false);
			
			listener = new ThermalOperateActionListener();
			pointQueryBtn.addActionListener(listener);
			regionQueryBtn.addActionListener(listener);
			itemQueryBtn.addActionListener(listener);
			historyQueryBtn.addActionListener(listener);
			heatMapBtn.addActionListener(listener);
		}
		
		public void setOperateEnabled(boolean b) {
			pointQueryBtn.setEnabled(b);
			regionQueryBtn.setEnabled(b);
			itemQueryBtn.setEnabled(b);
			historyQueryBtn.setEnabled(b);
			heatMapBtn.setEnabled(b);
			chnComboBox.setEnabled(b);
			if (b) {
				chnComboBox.setModel(new DefaultComboBoxModel(chnlist));
				if (chnlist.size() > THERMAL_CHANNEL) {
					chnComboBox.setSelectedIndex(THERMAL_CHANNEL);
				}
			}else {
				chnComboBox.setModel(new DefaultComboBoxModel());
			}
		}
		
		private JComboBox chnComboBox;
		private ThermalOperateActionListener listener;
		private JButton pointQueryBtn;
		private JButton regionQueryBtn;
		private JButton itemQueryBtn;
		private JButton historyQueryBtn;
		private JButton heatMapBtn;
	}
	
	private enum ThermalOperate {UNKNOWN, POINT_QUERY, REGION_QUERY, ITEM_QUERY, TEMPER_QUERY, HEATMAP}
	
	/**
	 * 按键监听实现类
	 */
	private class ThermalOperateActionListener implements ActionListener {	
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			THERMAL_CHANNEL = operatePanel.chnComboBox.getSelectedIndex();
			ThermalOperate emType = getOperateType(arg0.getSource());
			switch(emType) {
				case POINT_QUERY:
					new PointQueryDialog().setVisible(true);
					break;
				case REGION_QUERY:
					new RegionQueryDialog().setVisible(true);
					break;
				case ITEM_QUERY:
					new ItemQueryDialog().setVisible(true);
					break;	
				case TEMPER_QUERY:
					new TemperQueryDialog().setVisible(true);
					break;	
				case HEATMAP:
					new HeatMapDialog().setVisible(true);
					break;	
				default:
					break;
			}
		}
		
		private ThermalOperate getOperateType(Object btn) {
			ThermalOperate type = ThermalOperate.UNKNOWN;
			
			if (btn == operatePanel.pointQueryBtn) {
				type = ThermalOperate.POINT_QUERY;
			}else if (btn == operatePanel.regionQueryBtn) {
				type = ThermalOperate.REGION_QUERY;
			}else if (btn == operatePanel.itemQueryBtn) {
				type = ThermalOperate.ITEM_QUERY;
			}else if (btn == operatePanel.historyQueryBtn) {
				type = ThermalOperate.TEMPER_QUERY;
			}else if (btn == operatePanel.heatMapBtn) {
				type = ThermalOperate.HEATMAP;
			}else{
				System.err.println("Unknown Event: " + btn);
			}
			
			return type;
			
		}
	}
	
	/*
	 * 预览界面通道、码流设置  以及抓图面板
	 */
	private class RealPanelOne extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public RealPanelOne() {
			BorderEx.set(this, Res.string().getRealplay(), 2);
			setLayout(new BorderLayout());
			
			channelPanelOne = new Panel();
			realplayPanelOne = new JPanel();
			
			add(channelPanelOne, BorderLayout.NORTH);
			add(realplayPanelOne, BorderLayout.CENTER);
			
			/************ 预览面板 **************/
			realplayPanelOne.setLayout(new BorderLayout());
			realplayPanelOne.setBorder(new EmptyBorder(5, 5, 5, 5));
			realPlayWindowOne = new Panel();
			realPlayWindowOne.setBackground(Color.GRAY);
			realplayPanelOne.add(realPlayWindowOne, BorderLayout.CENTER);
			
			/************ 通道、码流面板 **************/
			chnlabelOne = new JLabel(Res.string().getChn());
			chnComboBoxOne = new JComboBox();	 			

			streamLabelOne = new JLabel(Res.string().getStreamType());
			String[] stream = {Res.string().getMasterStream(), Res.string().getSubStream()};
			streamComboBoxOne = new JComboBox(stream);	 
			
			realplayBtnOne = new JButton(Res.string().getStartRealPlay());
			
			channelPanelOne.setLayout(new FlowLayout());			
			channelPanelOne.add(chnlabelOne);
			channelPanelOne.add(chnComboBoxOne);
			channelPanelOne.add(streamLabelOne);
			channelPanelOne.add(streamComboBoxOne);
			channelPanelOne.add(realplayBtnOne);
			
			chnComboBoxOne.setPreferredSize(new Dimension(80, 20)); 
			streamComboBoxOne.setPreferredSize(new Dimension(95, 20)); 
			realplayBtnOne.setPreferredSize(new Dimension(115, 20)); 
		    
			realPlayWindowOne.setEnabled(false);
 			chnComboBoxOne.setEnabled(false);
			streamComboBoxOne.setEnabled(false);
			realplayBtnOne.setEnabled(false);
		    
			realplayBtnOne.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					realPlay();
				}
			});
		}
		
		public void setRealPlayEnable(boolean bln) {
			realPlayWindowOne.setEnabled(bln);
			chnComboBoxOne.setEnabled(bln);
			streamComboBoxOne.setEnabled(bln);
			realplayBtnOne.setEnabled(bln);
			if (bln) {
				chnComboBoxOne.setModel(new DefaultComboBoxModel(chnlist));
			}else {
				realPlayWindowOne.repaint();    
				realplayBtnOne.setText(Res.string().getStartRealPlay());
				chnComboBoxOne.setModel(new DefaultComboBoxModel());
			}
		}
		
		private void realPlay() {
			if(!isrealplayOne) {
				m_hPlayHandleOne = RealPlayModule.startRealPlay(chnComboBoxOne.getSelectedIndex(), 
					    streamComboBoxOne.getSelectedIndex()==0? 0:3,
						realPlayWindowOne);
				if(m_hPlayHandleOne.longValue() != 0) {
					changePlayStatus(true);
				} else {
					JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
				}
			} else {
				RealPlayModule.stopRealPlay(m_hPlayHandleOne);
				changePlayStatus(false);
			}	
		}
		
		private void changePlayStatus(boolean b) {
			realPlayWindowOne.repaint();
			isrealplayOne = b;
			chnComboBoxOne.setEnabled(!b);
			streamComboBoxOne.setEnabled(!b);
			if (b) {
				realplayBtnOne.setText(Res.string().getStopRealPlay());
			} else {
				m_hPlayHandleOne.setValue(0);
				realplayBtnOne.setText(Res.string().getStartRealPlay());
			}
		}
		
		private JPanel realplayPanelOne;
	    private Panel realPlayWindowOne;
	    private Panel channelPanelOne;
	    
	    private JLabel chnlabelOne;
	    private JComboBox chnComboBoxOne;	
	    private JLabel streamLabelOne;
	    private JComboBox streamComboBoxOne;
	    private JButton realplayBtnOne;
	}
	
	private class RealPanelTwo extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public RealPanelTwo() {
			BorderEx.set(this, Res.string().getRealplay(), 2);
			setLayout(new BorderLayout());
			
			channelPanelTwo = new Panel();
			realplayPanelTwo = new JPanel();
			
			add(channelPanelTwo, BorderLayout.NORTH);
			add(realplayPanelTwo, BorderLayout.CENTER);
			
			/************ 预览面板 **************/
			realplayPanelTwo.setLayout(new BorderLayout());
			realplayPanelTwo.setBorder(new EmptyBorder(5, 5, 5, 5));
			realPlayWindowTwo = new Panel();
			realPlayWindowTwo.setBackground(Color.GRAY);
			realplayPanelTwo.add(realPlayWindowTwo, BorderLayout.CENTER);
			
			/************ 通道、码流面板 **************/
			chnlabelTwo = new JLabel(Res.string().getChn());
			chnComboBoxTwo = new JComboBox();				

			streamLabelTwo = new JLabel(Res.string().getStreamType());
			String[] stream = {Res.string().getMasterStream(), Res.string().getSubStream()};
			streamComboBoxTwo = new JComboBox(stream);	
			
			realplayBtnTwo = new JButton(Res.string().getStartRealPlay());
			
			channelPanelTwo.setLayout(new FlowLayout());			
			channelPanelTwo.add(chnlabelTwo);
			channelPanelTwo.add(chnComboBoxTwo);
			channelPanelTwo.add(streamLabelTwo);
			channelPanelTwo.add(streamComboBoxTwo);
			channelPanelTwo.add(realplayBtnTwo);
			
			chnComboBoxTwo.setPreferredSize(new Dimension(80, 20)); 
			streamComboBoxTwo.setPreferredSize(new Dimension(95, 20)); 
			realplayBtnTwo.setPreferredSize(new Dimension(115, 20)); 
		    
			realPlayWindowTwo.setEnabled(false);
 			chnComboBoxTwo.setEnabled(false);
			streamComboBoxTwo.setEnabled(false);
			realplayBtnTwo.setEnabled(false);
		    
			realplayBtnTwo.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					realPlay();
				}
			});
		}
		
		public void setRealPlayEnable(boolean bln) {
			realPlayWindowTwo.setEnabled(bln);
			chnComboBoxTwo.setEnabled(bln);
			streamComboBoxTwo.setEnabled(bln);
			realplayBtnTwo.setEnabled(bln);
			if (bln) {
				chnComboBoxTwo.setModel(new DefaultComboBoxModel(chnlist));
				if (chnlist.size() > THERMAL_CHANNEL) {
					chnComboBoxTwo.setSelectedIndex(THERMAL_CHANNEL);
				}
			}else {
				realPlayWindowTwo.repaint();    
				realplayBtnTwo.setText(Res.string().getStartRealPlay());
				chnComboBoxTwo.setModel(new DefaultComboBoxModel());
			}
		}
		
		private void realPlay() {
			if(!isrealplayTwo) {
				m_hPlayHandleTwo = RealPlayModule.startRealPlay(chnComboBoxTwo.getSelectedIndex(), 
					    streamComboBoxTwo.getSelectedIndex()==0? 0:3,
						realPlayWindowTwo);
				if(m_hPlayHandleTwo.longValue() != 0) {
					changePlayStatus(true);
				} else {
					JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
				}
			} else {
				RealPlayModule.stopRealPlay(m_hPlayHandleTwo);
				changePlayStatus(false);
			}	
		}
		
		private void changePlayStatus(boolean b) {
			realPlayWindowTwo.repaint();
			isrealplayTwo = b;
			chnComboBoxTwo.setEnabled(!b);
			streamComboBoxTwo.setEnabled(!b);
			if (b) {
				realplayBtnTwo.setText(Res.string().getStopRealPlay());
			} else {
				m_hPlayHandleTwo.setValue(0);
				realplayBtnTwo.setText(Res.string().getStartRealPlay());
			}
		}
		
	    private JPanel realplayPanelTwo;
	    private Panel realPlayWindowTwo;
	    private Panel channelPanelTwo;
	    
	    private JLabel chnlabelTwo;
	    private JComboBox chnComboBoxTwo;	
	    private JLabel streamLabelTwo;
	    private JComboBox streamComboBoxTwo;
	    private JButton realplayBtnTwo;
	}
	
	private LoginPanel loginPanel;	
	private ThermalOperatePanel operatePanel;
	private RealPanelOne realPanelOne;
	private RealPanelTwo realPanelTwo;
}


public class ThermalCamera {
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
//				Res.string().switchLanguage(LanguageType.English);
				ThermalCameraFrame demo = new ThermalCameraFrame();	
				demo.setVisible(true);
			}
		});		
	}
}
