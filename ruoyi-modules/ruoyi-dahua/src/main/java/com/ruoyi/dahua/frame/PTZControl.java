package com.ruoyi.dahua.frame;

import com.ruoyi.dahua.common.*;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.CapturePictureModule;
import com.ruoyi.dahua.module.LoginModule;
import com.ruoyi.dahua.module.PtzControlModule;
import com.ruoyi.dahua.module.RealPlayModule;
import com.sun.jna.CallbackThreadInitializer;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * 实时预览Demo
 */
class PTZControlFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private Vector<String> chnlist = new Vector<String>(); 

	private boolean b_realplay = false;

	// 设备断线通知回调
	private static DisConnect disConnect       = new DisConnect(); 
	
	// 网络连接恢复
	private static HaveReConnect haveReConnect = new HaveReConnect(); 
	
	// 预览句柄
	public static LLong m_hPlayHandle = new LLong(0);
	
	// 获取界面窗口
	private static JFrame frame = new JFrame();   
	
	public PTZControlFrame() {
	    setTitle(Res.string().getPTZ());
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
	    realPanel = new RealPanel();
	    ptz_picPanel = new PTZ_PICPanel();
	    
	    add(loginPanel, BorderLayout.NORTH);
	    add(realPanel, BorderLayout.CENTER);
	    add(ptz_picPanel, BorderLayout.EAST);
	    
	    loginPanel.addLoginBtnActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	   	    
				if(loginPanel.checkLoginText()) {
					if(login()) {
						frame = ToolKits.getFrame(e);
						frame.setTitle(Res.string().getPTZ() + " : " + Res.string().getOnline());
					}
				}
			}
		});
	    
	    loginPanel.addLogoutBtnActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setTitle(Res.string().getPTZ());
				logout();	
			}
		});
        
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		RealPlayModule.stopRealPlay(m_hPlayHandle);
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
					frame.setTitle(Res.string().getPTZ() + " : " + Res.string().getDisConnectReconnecting());
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
					frame.setTitle(Res.string().getPTZ() + " : " + Res.string().getOnline());
				}
			});
		}
	}
	
	// 登录
	public boolean login() {
		Native.setCallbackThreadInitializer(m_SnapReceiveCB, 
										    new CallbackThreadInitializer(false, false, "snapPicture callback thread")); 
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
		
			CapturePictureModule.setSnapRevCallBack(m_SnapReceiveCB);		
		} else {
			JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	// 登出
	public void logout() {
		RealPlayModule.stopRealPlay(m_hPlayHandle);
		LoginModule.logout();
		
		loginPanel.setButtonEnable(false);
		setButtonEnable(false);
		realPlayWindow.repaint();    
		pictureShowWindow.setOpaque(true);
		pictureShowWindow.repaint();
		
		b_realplay = false;
		realplayBtn.setText(Res.string().getStartRealPlay());
		
		for(int i = 0; i < LoginModule.m_stDeviceInfo.byChanNum; i++) {
			chnlist.clear();
		}
		
		chnComboBox.setModel(new DefaultComboBoxModel());
	}
	
	/*
	 * 预览界面通道、码流设置  以及抓图面板
	 */
	private class RealPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public RealPanel() {
			BorderEx.set(this, Res.string().getRealplay(), 2);
			setLayout(new BorderLayout());
			
			channelPanel = new Panel();
			realplayPanel = new JPanel();
			
			add(channelPanel, BorderLayout.SOUTH);
			add(realplayPanel, BorderLayout.CENTER);
			
			/************ 预览面板 **************/
			realplayPanel.setLayout(new BorderLayout());
			realplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			realPlayWindow = new Panel();
			realPlayWindow.setBackground(Color.GRAY);
			realplayPanel.add(realPlayWindow, BorderLayout.CENTER);
			
			/************ 通道、码流面板 **************/
			chnlabel = new JLabel(Res.string().getChannel());
			chnComboBox = new JComboBox();	 			

			streamLabel = new JLabel(Res.string().getStreamType());
			String[] stream = {Res.string().getMasterStream(), Res.string().getSubStream()};
			streamComboBox = new JComboBox(stream);	 
			
			realplayBtn = new JButton(Res.string().getStartRealPlay());
			
			channelPanel.setLayout(new FlowLayout());			
			channelPanel.add(chnlabel);
			channelPanel.add(chnComboBox);
			channelPanel.add(streamLabel);
			channelPanel.add(streamComboBox);
			channelPanel.add(realplayBtn);
			
			chnComboBox.setPreferredSize(new Dimension(90, 20)); 
			streamComboBox.setPreferredSize(new Dimension(90, 20)); 
			realplayBtn.setPreferredSize(new Dimension(120, 20)); 
		    
			realPlayWindow.setEnabled(false);
 			chnComboBox.setEnabled(false);
			streamComboBox.setEnabled(false);
			realplayBtn.setEnabled(false);
		    
			realplayBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					realplay();
				}
			});
		}
	}
	
	// 预览
	public void realplay() {
		if(!b_realplay) {
			m_hPlayHandle = RealPlayModule.startRealPlay(chnComboBox.getSelectedIndex(), 
				    streamComboBox.getSelectedIndex()==0? 0:3,
					realPlayWindow);
			if(m_hPlayHandle.longValue() != 0) {
				realPlayWindow.repaint();
				b_realplay = true;
				chnComboBox.setEnabled(false);
				streamComboBox.setEnabled(false);
				realplayBtn.setText(Res.string().getStopRealPlay());
			} 
		} else {
			RealPlayModule.stopRealPlay(m_hPlayHandle);
			realPlayWindow.repaint();
			b_realplay = false;
			chnComboBox.setEnabled(true);
			streamComboBox.setEnabled(true);
			realplayBtn.setText(Res.string().getStartRealPlay());
		}	
	}
	
	/*
	 * 抓图显示与云台控制面板
	 */
	private class PTZ_PICPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public PTZ_PICPanel() {
			setLayout(new BorderLayout());
			Dimension dim = getPreferredSize();
			dim.width = 320;
			setPreferredSize(dim);
			
			picPanel = new PICPanel(); // 图片显示面板
			ptzPanel = new PTZPanel(); // 云台面板
			
			add(picPanel, BorderLayout.CENTER);
			add(ptzPanel, BorderLayout.SOUTH);
		}
	}
	
	/*
	 * 抓图显示面板
	 */
	private class PICPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public PICPanel() {
			BorderEx.set(this, Res.string().getCapturePicture(), 2);
			setLayout(new BorderLayout());
			
			pictureShowPanel = new JPanel();  
			snapPanel = new JPanel();
			
			add(pictureShowPanel, BorderLayout.CENTER);
			add(snapPanel, BorderLayout.SOUTH);
			
			/************** 抓图按钮 ************/
			snapPanel.setLayout(new BorderLayout());
			snapPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			snapBtn = new JButton(Res.string().getRemoteCapture());
			snapBtn.setPreferredSize(new Dimension(40, 23));
			snapPanel.add(snapBtn, BorderLayout.CENTER);
			snapBtn.setEnabled(false);
			
			/************** 图片显示 ************/
			pictureShowPanel.setLayout(new BorderLayout());
			pictureShowPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			pictureShowWindow = new PaintPanel();
			pictureShowPanel.add(pictureShowWindow, BorderLayout.CENTER);
		    
			snapBtn.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(!CapturePictureModule.remoteCapturePicture(chnComboBox.getSelectedIndex())) {
						JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
	}
	
	/*
	 * 云台控制面板
	 */
	private class PTZPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public PTZPanel() {
			BorderEx.set(this, Res.string().getPTZControl(), 2);
			setPreferredSize(new Dimension(40, 205));
			setLayout(new GridLayout(2, 1));
			
			directionPanel = new JPanel();
			JPanel panel1 = new JPanel();
			JPanel panel2 = new JPanel();
			ptzCtrlPanel = new JPanel();
			
			add(directionPanel);
			add(ptzCtrlPanel);
			
			directionPanel.setLayout(new BorderLayout());
			
			directionPanel.add(panel1, BorderLayout.NORTH);
			directionPanel.add(panel2, BorderLayout.CENTER);
			
			/*************** 云台方向 **************/
			panel1.setLayout(new BorderLayout());
			panel1.setBorder(new EmptyBorder(0, 5, 0, 5));
			
			panel2.setLayout(new GridLayout(3, 3));
			panel2.setBorder(new EmptyBorder(0, 5, 0, 5));
			
			leftUpBtn = new JButton(Res.string().getLeftUp());
			upBtn = new JButton(Res.string().getUp());
			rightUpBtn = new JButton(Res.string().getRightUp());
			leftBtn = new JButton(Res.string().getLeft());
			rightBtn = new JButton(Res.string().getRight());
			leftDownBtn = new JButton(Res.string().getLeftDown());
			downBtn = new JButton(Res.string().getDown());
			rightDownBtn = new JButton(Res.string().getRightDown());
			operateJLabel = new JLabel("", JLabel.CENTER);
			
			String[] speed = {Res.string().getSpeed() + " 1",
							  Res.string().getSpeed() + " 2",
							  Res.string().getSpeed() + " 3",
							  Res.string().getSpeed() + " 4",
							  Res.string().getSpeed() + " 5",
							  Res.string().getSpeed() + " 6",
							  Res.string().getSpeed() + " 7",
							  Res.string().getSpeed() + " 8"};
			
			speedComboBox = new JComboBox(speed);
			speedComboBox.setSelectedIndex(4);
			speedComboBox.setPreferredSize(new Dimension(40, 21));
			
			panel1.add(speedComboBox, BorderLayout.CENTER);
			
			panel2.add(leftUpBtn);
			panel2.add(upBtn);
			panel2.add(rightUpBtn);
			panel2.add(leftBtn);
			panel2.add(operateJLabel);
			panel2.add(rightBtn);
			panel2.add(leftDownBtn);
			panel2.add(downBtn);
			panel2.add(rightDownBtn);
			
			leftUpBtn.setEnabled(false);
			upBtn.setEnabled(false);
			rightUpBtn.setEnabled(false);
			leftBtn.setEnabled(false);
			rightBtn.setEnabled(false);
			leftDownBtn.setEnabled(false);
			downBtn.setEnabled(false);
			rightDownBtn.setEnabled(false);
			speedComboBox.setEnabled(false);
			
			/*************** 变焦、变倍、光圈 **************/
			ptzCtrlPanel.setLayout(new GridLayout(3, 2));
			ptzCtrlPanel.setBorder(new EmptyBorder(15, 5, 5, 5));
			zoomAddBtn = new JButton(Res.string().getZoomAdd());
			zoomDecBtn = new JButton(Res.string().getZoomDec());
			focusAddBtn = new JButton(Res.string().getFocusAdd());
			focusDecBtn = new JButton(Res.string().getFocusDec());
			irisAddBtn = new JButton(Res.string().getIrisAdd());
			irisDecBtn = new JButton(Res.string().getIrisDec());
			
			ptzCtrlPanel.add(zoomAddBtn);
			ptzCtrlPanel.add(zoomDecBtn);
			ptzCtrlPanel.add(focusAddBtn);
			ptzCtrlPanel.add(focusDecBtn);
			ptzCtrlPanel.add(irisAddBtn);
			ptzCtrlPanel.add(irisDecBtn);
			
			zoomAddBtn.setEnabled(false);
			zoomDecBtn.setEnabled(false);
			focusAddBtn.setEnabled(false);
			focusDecBtn.setEnabled(false);
			irisAddBtn.setEnabled(false);
			irisDecBtn.setEnabled(false);
			
			// 向上
			upBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlUpStart(chnComboBox.getSelectedIndex(), 
											0, 
											speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlUpEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});

			
			// 向下
			downBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlDownStart(chnComboBox.getSelectedIndex(), 
											0, 
											speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}	
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlDownEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});

			
			// 向左
			leftBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlLeftStart(chnComboBox.getSelectedIndex(), 
											0, 
											speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}		
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlLeftEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});
			
			// 向右
			rightBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlRightStart(chnComboBox.getSelectedIndex(), 
											0, 
											speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}		
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlRightEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});
			
			// 向左上
			leftUpBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlLeftUpStart(chnComboBox.getSelectedIndex(), 
												speedComboBox.getSelectedIndex(), 
												speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}		
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlLeftUpEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});
			
			// 向右上
			rightUpBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlRightUpStart(chnComboBox.getSelectedIndex(), 
												speedComboBox.getSelectedIndex(), 
												speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}			
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlRightUpEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});
			
			// 向左下
			leftDownBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlLeftDownStart(chnComboBox.getSelectedIndex(), 
												speedComboBox.getSelectedIndex(), 
												speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}		
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlLeftDownEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});
			  
			// 向右下
			rightDownBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlRightDownStart(chnComboBox.getSelectedIndex(), 
												speedComboBox.getSelectedIndex(), 
												speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}		
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlRightDownEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});
			
			// 变倍+
			zoomAddBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlZoomAddStart(chnComboBox.getSelectedIndex(), 
												 speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}		
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlZoomAddEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});
			
			// 变倍-
			zoomDecBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlZoomDecStart(chnComboBox.getSelectedIndex(), 
												 speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}			
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlZoomDecEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});
			
			// 变焦+
			focusAddBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlFocusAddStart(chnComboBox.getSelectedIndex(), 
												 speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}		
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlFocusAddEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});
			
			// 变焦-
			focusDecBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlFocusDecStart(chnComboBox.getSelectedIndex(), 
												 speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}			
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlFocusDecEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});
			
			// 光圈+
			irisAddBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlIrisAddStart(chnComboBox.getSelectedIndex(), 
												 speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}		
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlIrisAddEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});
			
			// 光圈-
			irisDecBtn.addMouseListener(new MouseListener() {			
				@Override
				public void mouseExited(MouseEvent e) {		
				}	
				@Override
				public void mouseEntered(MouseEvent e) {
				}			
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					if(PtzControlModule.ptzControlIrisDecStart(chnComboBox.getSelectedIndex(), 
												 speedComboBox.getSelectedIndex())) {
						operateJLabel.setText(Res.string().getSucceed());
					} else {
						operateJLabel.setText(Res.string().getFailed());
					}			
				}
				@Override
				public void mouseReleased(MouseEvent e) {	
					PtzControlModule.ptzControlIrisDecEnd(chnComboBox.getSelectedIndex());
					operateJLabel.setText("");
				}
			});
		}
	}

	public fSnapReceiveCB  m_SnapReceiveCB = new fSnapReceiveCB();
	public class fSnapReceiveCB implements NetSDKLib.fSnapRev{
		BufferedImage bufferedImage = null;
		public void invoke( LLong lLoginID, Pointer pBuf, int RevLen, int EncodeType, int CmdSerial, Pointer dwUser) {	
			if(pBuf != null && RevLen > 0) {			        
				String strFileName = SavePath.getSavePath().getSaveCapturePath(); 

				System.out.println("strFileName = " + strFileName);

				byte[] buf = pBuf.getByteArray(0, RevLen);
				ByteArrayInputStream byteArrInput = new ByteArrayInputStream(buf);
				try {
					bufferedImage = ImageIO.read(byteArrInput);
					if(bufferedImage == null) {
						return;
					}
					ImageIO.write(bufferedImage, "jpg", new File(strFileName));	
				} catch (IOException e) {
					e.printStackTrace();
				}	
				
				// 界面显示抓图	 
				SwingUtilities.invokeLater(new Runnable() {	
					@Override
					public void run() {			
						pictureShowWindow.setOpaque(false);
						pictureShowWindow.setImage(bufferedImage);
						pictureShowWindow.repaint();				
					}
				});
			}
		}
	}
	
	private void setButtonEnable(boolean bln) {
	    snapBtn.setEnabled(bln);
		leftUpBtn.setEnabled(bln);
		upBtn.setEnabled(bln);
		rightUpBtn.setEnabled(bln);
		leftBtn.setEnabled(bln);
		rightBtn.setEnabled(bln);
		leftDownBtn.setEnabled(bln);
		downBtn.setEnabled(bln);
		rightDownBtn.setEnabled(bln);
		zoomAddBtn.setEnabled(bln);
		zoomDecBtn.setEnabled(bln);
		focusAddBtn.setEnabled(bln);
		focusDecBtn.setEnabled(bln);
		irisAddBtn.setEnabled(bln);
		irisDecBtn.setEnabled(bln);		  
		speedComboBox.setEnabled(bln);
		realPlayWindow.setEnabled(bln);
		chnComboBox.setEnabled(bln);
		streamComboBox.setEnabled(bln);
		realplayBtn.setEnabled(bln);
	}
	
	/*
	 * 登录
	 */
	private LoginPanel loginPanel;	
	
	/*
	 * 预览
	 */
    private RealPanel realPanel;
    private JPanel realplayPanel;
    private Panel realPlayWindow;
    private Panel channelPanel;
    
    private JLabel chnlabel;
    private JComboBox chnComboBox;	
    private JLabel streamLabel;
    private JComboBox streamComboBox;
    private JButton realplayBtn;
    private JButton snapBtn;
    
    /*
     * 抓图与云台
     */
    private PTZ_PICPanel ptz_picPanel;
	private PICPanel picPanel;
	private JPanel pictureShowPanel;
	private JPanel snapPanel;
	private PaintPanel pictureShowWindow;
	
    /*
     * 云台
     */
    private PTZPanel ptzPanel;
	private JPanel directionPanel;
	private JPanel ptzCtrlPanel;
	private JButton leftUpBtn;
	private JButton upBtn;
	private JButton rightUpBtn;
	private JButton leftBtn;
	private JButton rightBtn;
	private JButton leftDownBtn;
	private JButton downBtn;
	private JButton rightDownBtn;
	private JComboBox speedComboBox;
	private JLabel operateJLabel;
	
	private JButton zoomAddBtn;
	private JButton zoomDecBtn;
	private JButton focusAddBtn;
	private JButton focusDecBtn;
	private JButton irisAddBtn;
	private JButton irisDecBtn;
}

public class PTZControl {  
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PTZControlFrame demo = new PTZControlFrame();	
				demo.setVisible(true);
			}
		});		
	}
}


