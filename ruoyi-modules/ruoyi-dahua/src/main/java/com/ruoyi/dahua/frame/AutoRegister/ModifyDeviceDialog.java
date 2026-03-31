package com.ruoyi.dahua.frame.AutoRegister;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.DeviceManagerListener;
import com.ruoyi.dahua.common.Res;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 在树上修改设备
 */
public class ModifyDeviceDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private DeviceManagerListener listener;
	public void addDeviceManagerListener(DeviceManagerListener listener) {
		this.listener = listener;
	}
	
	private String deviceId = ""; 
	private String username = "";
	private String password = "";
	
	public ModifyDeviceDialog(String deviceId, String username, String password){
		setTitle(Res.string().getModifyDevice());
		setLayout(new BorderLayout());
	    setModal(true);    
		pack();
		setSize(220, 180);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);   // 释放窗体
		
	    this.deviceId = deviceId;
	    this.username = username;
	    this.password = password;
		
		ModifyDevicePanel addDevicePanel = new ModifyDevicePanel();
        add(addDevicePanel, BorderLayout.CENTER);
        
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
	}
	
	/*
	 * 修改设备面板
	 */
	private class ModifyDevicePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public ModifyDevicePanel() {
			BorderEx.set(this, "", 2);
			setLayout(new FlowLayout());
			
			JLabel deviceIdLabel = new JLabel(Res.string().getDeviceID(), JLabel.CENTER);
			JLabel usernameLabel = new JLabel(Res.string().getUserName(), JLabel.CENTER);
			JLabel passwordLabel = new JLabel(Res.string().getPassword(), JLabel.CENTER);
			
			deviceIdLabel.setPreferredSize(new Dimension(60, 21));
			usernameLabel.setPreferredSize(new Dimension(60, 21));
			passwordLabel.setPreferredSize(new Dimension(60, 21));
			
			deviceIdTextField = new JTextField();
			usernameTextField = new JTextField();
			passwordPasswordField = new JPasswordField();
			
			deviceIdTextField.setPreferredSize(new Dimension(120, 20));
			usernameTextField.setPreferredSize(new Dimension(120, 20));
			passwordPasswordField.setPreferredSize(new Dimension(120, 20));
			
			JButton modifyDeviceBtn = new JButton(Res.string().getModify());
			JButton cancelBtn = new JButton(Res.string().getCancel());
			
			modifyDeviceBtn.setPreferredSize(new Dimension(90, 21));
			cancelBtn.setPreferredSize(new Dimension(90, 21));
			
			add(deviceIdLabel);
			add(deviceIdTextField);
			add(usernameLabel);
			add(usernameTextField);
			add(passwordLabel);
			add(passwordPasswordField);
			add(modifyDeviceBtn);
			add(cancelBtn);
			
			deviceIdTextField.setText(deviceId);
			usernameTextField.setText(username);
			passwordPasswordField.setText(password);
			
			// 修改
			modifyDeviceBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(deviceIdTextField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, Res.string().getInput() + Res.string().getDeviceID(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(usernameTextField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, Res.string().getInput() + Res.string().getUserName(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if((new String(passwordPasswordField.getPassword()).trim()).equals("")) {
						JOptionPane.showMessageDialog(null, Res.string().getInput() + Res.string().getPassword(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					dispose();
					listener.onDeviceManager(deviceIdTextField.getText(), 
											 usernameTextField.getText(), 
										     new String(passwordPasswordField.getPassword()).trim());					
				}
			});
			
			// 取消，关闭
			cancelBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();						
				}
			});
		}
	}
	
	private JTextField deviceIdTextField;
	private JTextField usernameTextField;
	private JPasswordField passwordPasswordField;
}
