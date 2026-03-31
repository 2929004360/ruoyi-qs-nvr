package com.ruoyi.dahua.frame;

import com.ruoyi.dahua.common.*;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.DeviceControlModule;
import com.ruoyi.dahua.module.LoginModule;
import com.sun.jna.Pointer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Device Control Demo
 */
class DeviceControlFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// device disconnect callback instance
	private DisConnect disConnect = new DisConnect(); 
	
	// device control frame (this)
	private static JFrame frame = new JFrame();
	
	public DeviceControlFrame() {
	    setTitle(Res.string().getDeviceControl());
	    setLayout(new BorderLayout());
	    pack();
	    setSize(550, 350);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    LoginModule.init(disConnect, null);
	    
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        	e.printStackTrace();
        } 
	 
	    loginPanel = new DeviceControlLoginPanel();
	    deviceCtlPanel = new DeviceControlPanel();
	    
	    add(loginPanel, BorderLayout.NORTH);
	    add(deviceCtlPanel, BorderLayout.CENTER);
	    
	    loginPanel.addLoginBtnActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	   	    
				if(loginPanel.checkLoginText()) {
					if(login()) {
						frame = ToolKits.getFrame(e);
						frame.setTitle(Res.string().getDeviceControl() + " : " + Res.string().getOnline());
					}
				}
			}
		});
	    
	    loginPanel.addLogoutBtnActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setTitle(Res.string().getDeviceControl());
				logout();	
			}
		});
        
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		LoginModule.logout();
	    		LoginModule.cleanup();
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
	
	/////////////////function///////////////////
	// device disconnect callback class 
	// set it's instance by call CLIENT_Init, when device disconnect sdk will call it.
	private class DisConnect implements NetSDKLib.fDisConnect {
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					JOptionPane.showMessageDialog(null, Res.string().getDisConnect(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					frame.setTitle(Res.string().getDeviceControl());
					logout();
				}
			});
		}
	}

	public boolean login() {

		if(LoginModule.login(loginPanel.ipTextArea.getText(), 
					Integer.parseInt(loginPanel.portTextArea.getText()), 
					loginPanel.nameTextArea.getText(), 
					new String(loginPanel.passwordTextArea.getPassword()))) {
		
				loginPanel.setButtonEnable(true);
				deviceCtlPanel.setButtonEnabled(true);
								
		}else {
			JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}

	public void logout() {
		
		LoginModule.logout();
		
		loginPanel.setButtonEnable(false);
		deviceCtlPanel.resetButtonEnabled();
	}
	
	private class DeviceControlPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public DeviceControlPanel() {
			BorderEx.set(this, Res.string().getDeviceControl(), 2);
			setLayout(new BorderLayout());
			setPreferredSize(new Dimension(350, 220));
			setResizable(false);
			JLabel nullLable = new JLabel();
			
		    currentTimeCheckBox = new JCheckBox(Res.string().getCurrentTime());
		    
			getDateChooser = new DateChooserJButton();
			setDateChooser = new DateChooserJButton(2000, 2037);
			
			rebootBtn = new JButton(Res.string().getReboot());
			getTimeBtn = new JButton(Res.string().getGetTime());
			setTimeBtn = new JButton(Res.string().getSetTime());
			
			nullLable.setPreferredSize(currentTimeCheckBox.getPreferredSize());
			getDateChooser.setPreferredSize(new Dimension(150, 20));
			setDateChooser.setPreferredSize(new Dimension(150, 20));
			rebootBtn.setPreferredSize(new Dimension(100, 20));
			getTimeBtn.setPreferredSize(new Dimension(100, 20));
			setTimeBtn.setPreferredSize(new Dimension(100, 20));
			
			JPanel rebootPanel = new JPanel();
			BorderEx.set(rebootPanel, Res.string().getDeviceReboot(), 2);
			
			rebootPanel.add(rebootBtn);
			
			JPanel timePanel = new JPanel(new GridLayout(2,1));
			BorderEx.set(timePanel, Res.string().getSyncTime(), 2);
			
			JPanel getPanel = new JPanel();
			JPanel setPanel = new JPanel();
			
			getPanel.add(nullLable);
			getPanel.add(getDateChooser);
			getPanel.add(getTimeBtn);
			
			setPanel.add(currentTimeCheckBox);
			setPanel.add(setDateChooser);
			setPanel.add(setTimeBtn);
			
			timePanel.add(getPanel);
			timePanel.add(setPanel);
			
			JSplitPane splitPane = new JSplitPane();
			splitPane.setDividerSize(0);
			splitPane.setBorder(null);
			splitPane.add(rebootPanel, JSplitPane.LEFT);
			splitPane.add(timePanel, JSplitPane.RIGHT);
		 	add(splitPane);
			
			getDateChooser.setEnabled(false);
			setButtonEnabled(false);

			rebootBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					OptionDialog optionDialog = new OptionDialog();
					optionDialog.setVisible(true);
				}
			});
			
			getTimeBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					String date = DeviceControlModule.getTime();
					if (date == null) {
						JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}else {
						getDateChooser.setText(date);
					}
					
				}
			});
			
			setTimeBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					String date = null;
					if (!currentTimeCheckBox.isSelected()) {
						date = setDateChooser.getText();
					}
					if (!DeviceControlModule.setTime(date)) {
						JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, Res.string().getOperateSuccess(), Res.string().getPromptMessage(), JOptionPane.PLAIN_MESSAGE);
					}
				}
			});
			
			currentTimeCheckBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					JCheckBox jcb = (JCheckBox)e.getItem();
					if (jcb.isSelected()) {
						setDateChooser.setEnabled(false);
					}else {
						setDateChooser.setEnabled(true);
					}
				}
			});
			
		}
		
		public void setButtonEnabled(boolean b) {
			
			currentTimeCheckBox.setEnabled(b);
			setDateChooser.setEnabled(b);
			rebootBtn.setEnabled(b);
			getTimeBtn.setEnabled(b);
			setTimeBtn.setEnabled(b);
		}
		
		public void resetButtonEnabled() {
			currentTimeCheckBox.setSelected(false);
			setButtonEnabled(false);
		}
		
		private class OptionDialog extends JDialog {
			private static final long serialVersionUID = 1L;

			public OptionDialog() {
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				pack();
		        setSize(250, 100);
		        setLocationRelativeTo(null);
		        setModal(true);
		        setTitle(Res.string().getDeviceReboot());
		        
		        JLabel messageLable = new JLabel(Res.string().getRebootTips()); 
		        confirmBtn = new JButton(Res.string().getConfirm());
		        cancelBtn = new JButton(Res.string().getCancel());
		        
		        JPanel messagePanel = new JPanel();
		        messagePanel.add(messageLable);
		        
		        JPanel btnPanel = new JPanel();
		        btnPanel.add(cancelBtn);
		        btnPanel.add(confirmBtn);
		        
		        add(messagePanel, BorderLayout.NORTH);
		        add(btnPanel, BorderLayout.CENTER);
		        
		        addListener();
		        
			}
			
			private void addListener() {
			  confirmBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						cancelBtn.setEnabled(false);
						if (!DeviceControlModule.reboot()) {
							JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(null, Res.string().getOperateSuccess(), Res.string().getPromptMessage(), JOptionPane.PLAIN_MESSAGE);
						}
						dispose();
					}
		        });
		        
		        cancelBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
		        });
			}
			
			private JButton confirmBtn;
			private JButton cancelBtn;
			
		}
		
		private JButton rebootBtn;
		private DateChooserJButton getDateChooser;
		private JButton getTimeBtn;
	    private JCheckBox currentTimeCheckBox;
	    private DateChooserJButton setDateChooser;
	    private JButton setTimeBtn; 
	}

	private class DeviceControlLoginPanel extends LoginPanel {
	
		private static final long serialVersionUID = 1L;

		public DeviceControlLoginPanel() {
			setLayout(new GridLayout(3, 1));
			removeAll();
			JPanel ipPanel = new JPanel();
			JPanel userPanel = new JPanel();
			JPanel btnPanel = new JPanel();
			
			resetSize();
	
			ipPanel.add(ipLabel);
			ipPanel.add(ipTextArea);
			ipPanel.add(portLabel);
			ipPanel.add(portTextArea);
			
			userPanel.add(nameLabel);
			userPanel.add(nameTextArea);
			userPanel.add(passwordLabel);
			userPanel.add(passwordTextArea);
			    
			btnPanel.add(loginBtn);
			btnPanel.add(new JLabel("  "));
			btnPanel.add(logoutBtn);

			add(ipPanel);
			add(userPanel);
			add(btnPanel);
		}
		
		private void resetSize() {
			
			ipLabel.setPreferredSize(new Dimension(70, 25));
			portLabel.setPreferredSize(new Dimension(70, 25));
			nameLabel.setText(Res.string().getUserName());
			nameLabel.setPreferredSize(new Dimension(70, 25));
			passwordLabel.setPreferredSize(new Dimension(70, 25));

			loginBtn.setPreferredSize(new Dimension(100, 20));
			logoutBtn.setPreferredSize(new Dimension(100, 20));
			
			ipTextArea.setPreferredSize(new Dimension(100, 20));
			portTextArea.setPreferredSize(new Dimension(100, 20));
			nameTextArea.setPreferredSize(new Dimension(100, 20));
			passwordTextArea.setPreferredSize(new Dimension(100, 20));
		}
	}
	
	private DeviceControlLoginPanel loginPanel;
	
	private DeviceControlPanel deviceCtlPanel;
	
}

public class DeviceControl {  
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {	
				DeviceControlFrame demo = new DeviceControlFrame();
				demo.setVisible(true);
			}
		});		
	}
};

