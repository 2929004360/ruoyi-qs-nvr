package com.ruoyi.dahua.frame.Attendance;

import com.ruoyi.dahua.common.FunctionList;
import com.ruoyi.dahua.common.LoginPanel;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.AttendanceModule;
import com.ruoyi.dahua.module.LoginModule;
import com.sun.jna.Pointer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 考勤机Demo：包含门禁事件订阅、人员操作、信息操作
 */
class AttendanceFrame  extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 设备断线通知回调
	private  DisConnect disConnect  = new DisConnect(); 
	
	// 获取界面窗口
	private static JFrame frame = new JFrame();
		
	public AttendanceFrame(){
		setTitle(Res.string().getAttendance());
		setLayout(new BorderLayout());
		pack();
		setSize(800, 555);
	    setResizable(false);
	    setLocationRelativeTo(null);  
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);   // 释放窗体
	    
	    LoginModule.init(disConnect, null);   // 打开工程，初始化 

	    try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	    loginPanel = new LoginPanel();
	    showPanel = new AttendanceShowPanel();
	    operatePanel = new AttendanceFunctionOperatePanel(showPanel);
	    
	    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, loginPanel, operatePanel);
	    splitPane.setDividerSize(0);
	    splitPane.setBorder(null);
		add(splitPane, BorderLayout.NORTH);
	    add(showPanel, BorderLayout.CENTER);
	    
	    loginPanel.addLoginBtnActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	   	    
				if(loginPanel.checkLoginText()) {
					if(login()) {
						frame = ToolKits.getFrame(e);
						frame.setTitle(Res.string().getAttendance() + " : " + Res.string().getOnline());
					}
				}
			}
		});
	    
	    loginPanel.addLogoutBtnActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						logout();
					}
				});
			}
		});
	    
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AttendanceModule.stopRealLoadPicture();
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
	
	// 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
	private class DisConnect implements NetSDKLib.fDisConnect {
		public DisConnect() { }
		
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
			// 断线提示
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					JOptionPane.showMessageDialog(null, Res.string().getDisConnect(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					logout();
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
	
			loginPanel.setButtonEnable(true);
			operatePanel.setButtonEnable(true);
			
		} else {
			JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
		
	// 登出
	public void logout() {
		AttendanceModule.stopRealLoadPicture();
		LoginModule.logout();
		
		frame.setTitle(Res.string().getAttendance());
		loginPanel.setButtonEnable(false);
		operatePanel.setButtonEnable(false);
		showPanel.clearup();
	}
	
	private LoginPanel loginPanel;							// 登陆面板
	private AttendanceFunctionOperatePanel operatePanel;	// 操作面板
	private AttendanceShowPanel showPanel;					// 显示面板
}

public class Attendance {
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AttendanceFrame demo = new AttendanceFrame();	
				demo.setVisible(true);
			}
		});
	}
}
