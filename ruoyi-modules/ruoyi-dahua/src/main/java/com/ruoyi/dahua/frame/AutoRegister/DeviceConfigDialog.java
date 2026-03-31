package com.ruoyi.dahua.frame.AutoRegister;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.LoginPanel;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib.CFG_DVRIP_INFO;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.AutoRegisterModule;
import com.ruoyi.dahua.module.LoginModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主动注册网络配置
 */
public class DeviceConfigDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private CFG_DVRIP_INFO info = null;
	private ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	public DeviceConfigDialog(){
		setTitle(Res.string().getDeviceConfig());
		setLayout(new BorderLayout());
	    setModal(true);
		pack();
		setSize(300, 380);
	    setResizable(false);
	    setLocationRelativeTo(null);    
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);   // 释放窗体	
		
		loginDevicePanel = new LoginDevicePanel();
		ConfigDevicePanel configDevicePanel = new ConfigDevicePanel();
		
        add(loginDevicePanel, BorderLayout.NORTH);
        add(configDevicePanel, BorderLayout.CENTER);
        
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				if(!executorService.isShutdown()) {
					executorService.shutdown();
				}

				LoginModule.logout();
				dispose();
			}
		});
	}
	
	/*
	 * 登陆设备面板
	 */
	private class LoginDevicePanel extends LoginPanel {
		private static final long serialVersionUID = 1L;
		
		public LoginDevicePanel() {
			BorderEx.set(this, Res.string().getLogin(), 2);
			setLayout(new FlowLayout());
			Dimension dimension = new Dimension();
			dimension.height = 180;
			setPreferredSize(dimension);
			
			ipLabel.setPreferredSize(new Dimension(100, 21));
			portLabel.setPreferredSize(new Dimension(100, 21));
			nameLabel.setPreferredSize(new Dimension(100, 21));
			passwordLabel.setPreferredSize(new Dimension(100, 21));
			
			ipLabel.setHorizontalAlignment(JLabel.CENTER);
			portLabel.setHorizontalAlignment(JLabel.CENTER);
			nameLabel.setHorizontalAlignment(JLabel.CENTER);
			passwordLabel.setHorizontalAlignment(JLabel.CENTER);
			
			ipTextArea.setPreferredSize(new Dimension(140, 21));
			portTextArea.setPreferredSize(new Dimension(140, 21));
			nameTextArea.setPreferredSize(new Dimension(140, 21));
			passwordTextArea.setPreferredSize(new Dimension(140, 21));		
			loginBtn.setPreferredSize(new Dimension(120, 21));
			logoutBtn.setPreferredSize(new Dimension(120, 21));
			
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
			
			// 登陆
			loginBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					login();
				}
			});
			
			// 登出
			logoutBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					logout();
				}
			});
		}
	}
	
	/*
	 * 配置设备面板
	 */
	private class ConfigDevicePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public ConfigDevicePanel() {
			BorderEx.set(this, Res.string().getDeviceConfig(), 2);
			setLayout(new FlowLayout());
			
			enableCheckBox = new JCheckBox(Res.string().getEnable());
			JLabel nullLabel = new JLabel();
			JLabel autoRegisterIpLabel = new JLabel(Res.string().getRegisterAddress(), JLabel.CENTER);
			JLabel autoRegisterPortLabel = new JLabel(Res.string().getRegisterPort(), JLabel.CENTER);
			JLabel deviceIdLabel = new JLabel(Res.string().getDeviceID(), JLabel.CENTER);
			
			enableCheckBox.setPreferredSize(new Dimension(80, 21));
			nullLabel.setPreferredSize(new Dimension(120, 21));
			autoRegisterIpLabel.setPreferredSize(new Dimension(100, 21));
			autoRegisterPortLabel.setPreferredSize(new Dimension(100, 21));
			deviceIdLabel.setPreferredSize(new Dimension(100, 21));
			
			autoRegisterIpTextField = new JTextField();
			autoRegisterPortTextField = new JTextField();
			deviceIdTextField = new JTextField();
			
			autoRegisterIpTextField.setPreferredSize(new Dimension(140, 21));
			autoRegisterPortTextField.setPreferredSize(new Dimension(140, 21));
			deviceIdTextField.setPreferredSize(new Dimension(140, 21));
			
			getBtn = new JButton(Res.string().getGet());
			setBtn = new JButton(Res.string().getSet());
			
			getBtn.setPreferredSize(new Dimension(120, 21));
			setBtn.setPreferredSize(new Dimension(120, 21));
			
			add(enableCheckBox);
			add(nullLabel);
			add(autoRegisterIpLabel);
			add(autoRegisterIpTextField);
			add(autoRegisterPortLabel);
			add(autoRegisterPortTextField);
			add(deviceIdLabel);
			add(deviceIdTextField);
			add(getBtn);
			add(setBtn);
			
			enableCheckBox.setSelected(true);
			enableCheckBox.setEnabled(false);
			getBtn.setEnabled(false);
			setBtn.setEnabled(false);
			autoRegisterIpTextField.setEnabled(false);
			autoRegisterPortTextField.setEnabled(false);
			deviceIdTextField.setEnabled(false);
			
			// 获取
			getBtn.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent arg0) {
					executorService.execute(new Runnable() {					
						@Override
						public void run() {
							getBtn.setEnabled(false);
						}
					});
					
					executorService.execute(new Runnable() {					
						@Override
						public void run() {
							getConfig();
						}
					});
				}
			});
			
			// 设置
			setBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					executorService.execute(new Runnable() {					
						@Override
						public void run() {
							setBtn.setEnabled(false);
						}
					});
					
					executorService.execute(new Runnable() {					
						@Override
						public void run() {
							setConfig();
						}
					});
				}
			});
		}
	}
	
	// 登陆
	private void login() {
		if(loginDevicePanel.checkLoginText()) {
			if(LoginModule.login(loginDevicePanel.ipTextArea.getText(), 
								 Integer.parseInt(loginDevicePanel.portTextArea.getText()), 
								 loginDevicePanel.nameTextArea.getText(), 
								 new String(loginDevicePanel.passwordTextArea.getPassword()))) {
				loginDevicePanel.setButtonEnable(true);
				enableCheckBox.setEnabled(true);
				getBtn.setEnabled(true);
				setBtn.setEnabled(true);
				autoRegisterIpTextField.setEnabled(true);
				autoRegisterPortTextField.setEnabled(true);
				deviceIdTextField.setEnabled(true);
			} else {				
				JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			}
		} 
	}
	
	// 登出
	private void logout() {
		LoginModule.logout();
		loginDevicePanel.setButtonEnable(false);
		enableCheckBox.setEnabled(false);
		getBtn.setEnabled(false);
		setBtn.setEnabled(false);
		autoRegisterIpTextField.setEnabled(false);
		autoRegisterPortTextField.setEnabled(false);
		deviceIdTextField.setEnabled(false);
		autoRegisterIpTextField.setText("");
		autoRegisterPortTextField.setText("");
		deviceIdTextField.setText("");
	}
	
	// 获取
	private void getConfig() {
		info = AutoRegisterModule.getDVRIPConfig(LoginModule.m_hLoginHandle);
		if(info == null) {
			autoRegisterIpTextField.setText("");
			autoRegisterPortTextField.setText("");
			deviceIdTextField.setText("");
			JOptionPane.showMessageDialog(null, Res.string().getGet() + Res.string().getFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
		} else {
			if(info.stuRegisters[0].bEnable == 1) {
				enableCheckBox.setSelected(true);
			} else {
				enableCheckBox.setSelected(false);
			}
			
			autoRegisterIpTextField.setText(new String(info.stuRegisters[0].stuServers[0].szAddress).trim());
			autoRegisterPortTextField.setText(String.valueOf(info.stuRegisters[0].stuServers[0].nPort));
			try {
				deviceIdTextField.setText(new String(info.stuRegisters[0].szDeviceID, "GBK").trim());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(null, Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
		}
		getBtn.setEnabled(true);
	}
	
	/**
	 * 设置(在获取的基础上配置)
	 */
	private void setConfig() {
		info = AutoRegisterModule.getDVRIPConfig(LoginModule.m_hLoginHandle);
		if(autoRegisterIpTextField.getText().equals("")) {
			setBtn.setEnabled(true);
			JOptionPane.showMessageDialog(null, Res.string().getInput() + Res.string().getRegisterAddress(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(autoRegisterPortTextField.getText().equals("")) {
			setBtn.setEnabled(true);
			JOptionPane.showMessageDialog(null, Res.string().getInput() + Res.string().getRegisterPort(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(deviceIdTextField.getText().equals("")) {
			setBtn.setEnabled(true);
			JOptionPane.showMessageDialog(null, Res.string().getInput() + Res.string().getDeviceID(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// win下，中文需要转换为GBK
		byte[] deviceId = new byte[256];
		try {
			System.arraycopy(deviceIdTextField.getText().getBytes("GBK"), 0, deviceId, 0, deviceIdTextField.getText().getBytes("GBK").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		System.out.println("登录句柄"+LoginModule.m_hLoginHandle + "是否启用" +  enableCheckBox.isSelected() + "ip" + autoRegisterIpTextField.getText() + "端口" + autoRegisterPortTextField.getText() + "设备id" + new String(deviceId));
		
		if(AutoRegisterModule.setDVRIPConfig(LoginModule.m_hLoginHandle, 
										    enableCheckBox.isSelected(),
										    autoRegisterIpTextField.getText(), 
										    Integer.parseInt(autoRegisterPortTextField.getText()), 
										    deviceId,
										    info)) {
			JOptionPane.showMessageDialog(null, Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, Res.string().getSet() + Res.string().getFailed() + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
		}
		
		setBtn.setEnabled(true);
	}
	
	private LoginDevicePanel loginDevicePanel;
	
	private JTextField autoRegisterIpTextField;
	private JTextField autoRegisterPortTextField;
	private JTextField deviceIdTextField;	
	private JCheckBox enableCheckBox;
	private JButton getBtn;
	private JButton setBtn;
}