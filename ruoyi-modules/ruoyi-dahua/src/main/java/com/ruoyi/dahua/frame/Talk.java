package com.ruoyi.dahua.frame;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.FunctionList;
import com.ruoyi.dahua.common.LoginPanel;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.LoginModule;
import com.ruoyi.dahua.module.TalkModule;
import com.sun.jna.Pointer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Talk Demo
 */
class TalkFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	// device channel list
	private Vector<String> chnlist = new Vector<String>();
	
	// device disconnect callback instance
	private static DisConnect disConnect = new DisConnect(); 
	
	// talk frame (this)
	private static JFrame frame = new JFrame();
	
	public TalkFrame() {
	    setTitle(Res.string().getTalk());
	    setLayout(new BorderLayout());
	    pack();
	    setSize(400, 450);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    LoginModule.init(disConnect, null);
	    
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        	e.printStackTrace();
        } 
	 
	    loginPanel = new TalkLoginPanel();
	    talkPanel = new TalkPanel();
	    
	    add(loginPanel, BorderLayout.CENTER);
	    add(talkPanel, BorderLayout.SOUTH);
	    
	    loginPanel.addLoginBtnActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	   	    
				if(loginPanel.checkLoginText()) {
					if(login()) {
						frame = ToolKits.getFrame(e);
						frame.setTitle(Res.string().getTalk() + " : " + Res.string().getOnline());
					}
				}
			}
		});
	    
	    loginPanel.addLogoutBtnActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setTitle(Res.string().getTalk());
				logout();	
			}
		});
        
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		TalkModule.stopTalk();
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
	private static class DisConnect implements NetSDKLib.fDisConnect {
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setTitle(Res.string().getTalk() + " : " + Res.string().getDisConnectReconnecting());
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
		
				for(int i = 1; i < LoginModule.m_stDeviceInfo.byChanNum + 1; i++) {
					chnlist.add(Res.string().getChannel() + " " + String.valueOf(i));
				}
				
				talkPanel.talkEnable();
								
		}else {
			JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}

	public void logout() {
		TalkModule.stopTalk();
		LoginModule.logout();
		
		loginPanel.setButtonEnable(false);
		chnlist.clear();
		talkPanel.initTalkEnable();
	}
	
	private class TalkPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public TalkPanel() {
			BorderEx.set(this, Res.string().getTalk(), 2);
			setLayout(new GridLayout(3, 1));
			setPreferredSize(new Dimension(350, 220));
			
			transmitPanel = new JPanel();
			chnPanel = new JPanel();
			talkBtnPanel = new JPanel();
			
			transmitLabel = new JLabel(Res.string().getTransmitType());
			transmitLabel.setPreferredSize(new Dimension(100, 25));
			transmitComboBox = new JComboBox();
			transmitComboBox.setPreferredSize(new Dimension(150, 25));
			transmitPanel.add(transmitLabel);
			transmitPanel.add(transmitComboBox);
			
			chnlabel = new JLabel(Res.string().getTransmitChannel());
			chnlabel.setPreferredSize(new Dimension(100, 25));
			chnComboBox = new JComboBox();	
			chnComboBox.setPreferredSize(new Dimension(150, 25)); 
			chnPanel.add(chnlabel);
			chnPanel.add(chnComboBox);
			
			startTalkBtn = new JButton(Res.string().getStartTalk());
			startTalkBtn.setPreferredSize(new Dimension(100, 20));
			JLabel nullLabel = new JLabel("      ");
			stopTalkBtn = new JButton(Res.string().getStopTalk());
			stopTalkBtn.setPreferredSize(new Dimension(100, 20));
			talkBtnPanel.add(startTalkBtn);
			talkBtnPanel.add(nullLabel);
			talkBtnPanel.add(stopTalkBtn);
			
			add(transmitPanel);
			add(chnPanel);
			add(talkBtnPanel);
		    
			initTalkEnable();
		    
			startTalkBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					
//					if(TalkModule.startTalk(transmitComboBox.getSelectedIndex(),
//							chnComboBox.getSelectedIndex())) {
//						setButtonEnable(false);
//					}else {
//						JOptionPane.showMessageDialog(null, Res.string().getTalkFailed() + "," + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
//					}
				}
			});
			
			stopTalkBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TalkModule.stopTalk();
					setButtonEnable(true);
				}
			});
			
			transmitComboBox.addItemListener(new ItemListener() {			
				@Override
				public void itemStateChanged(ItemEvent e) {

					if (e.getStateChange() == ItemEvent.SELECTED) {
						if (transmitComboBox.getSelectedIndex() == 1) {
							chnComboBox.setModel(new DefaultComboBoxModel(chnlist));
							chnComboBox.setEnabled(true);
						}else {
							chnComboBox.setModel(new DefaultComboBoxModel());
							chnComboBox.setEnabled(false);
						}
					}
				}
			});
			
		}
		
		public void talkEnable() {
			
			String[] transmit = {Res.string().getLocalTransmitType(), Res.string().getRemoteTransmitType()};
			transmitComboBox.setModel(new DefaultComboBoxModel(transmit));
			setButtonEnable(true);
		}
		
		public void initTalkEnable() {
			
			chnComboBox.setModel(new DefaultComboBoxModel());
			transmitComboBox.setModel(new DefaultComboBoxModel());
			chnComboBox.setEnabled(false);
 			transmitComboBox.setEnabled(false);
			startTalkBtn.setEnabled(false);
			stopTalkBtn.setEnabled(false);
		}

		private void setButtonEnable(boolean bln) {
			
 			transmitComboBox.setEnabled(bln);
 			if (bln && transmitComboBox.getSelectedIndex() == 1) {
				chnComboBox.setEnabled(true);
			}else {
				chnComboBox.setEnabled(false);
			}
 			startTalkBtn.setEnabled(bln);
			stopTalkBtn.setEnabled(!bln);
		}
		
		private JPanel transmitPanel;
		private JPanel chnPanel;
		private JPanel talkBtnPanel;
	    private JLabel transmitLabel;
	    private JComboBox transmitComboBox;
	    private JLabel chnlabel;
	    private JComboBox chnComboBox;	
	    private JButton startTalkBtn;
	    private JButton stopTalkBtn;
	}

	private class TalkLoginPanel extends LoginPanel {
	
		private static final long serialVersionUID = 1L;

		public TalkLoginPanel() {
			setLayout(new GridLayout(3, 1));
			removeAll();
			JPanel ipPanel = new JPanel();
			JPanel userPanel = new JPanel();
			JPanel btnPanel = new JPanel();
			JLabel nullLabel = new JLabel("          ");
			JLabel nullLabel1 = new JLabel("          ");
			
			resetSize();
	
			ipPanel.add(ipLabel);
			ipPanel.add(ipTextArea);
			ipPanel.add(portLabel);
			ipPanel.add(portTextArea);
			
			userPanel.add(nameLabel);
			userPanel.add(nameTextArea);
			userPanel.add(passwordLabel);
			userPanel.add(passwordTextArea);
			    
			btnPanel.add(nullLabel);
			btnPanel.add(loginBtn);
			btnPanel.add(nullLabel1);
			btnPanel.add(logoutBtn);

			add(ipPanel);
			add(userPanel);
			add(btnPanel);
		}
		
		private void resetSize() {
			
			ipLabel.setPreferredSize(new Dimension(70, 20));
			portLabel.setPreferredSize(new Dimension(70, 20));
			nameLabel.setText(Res.string().getUserName());
			nameLabel.setPreferredSize(new Dimension(70, 20));
			passwordLabel.setPreferredSize(new Dimension(70, 20));

			ipTextArea.setPreferredSize(new Dimension(90, 20));
			portTextArea.setPreferredSize(new Dimension(90, 20));
			nameTextArea.setPreferredSize(new Dimension(90, 20));
			passwordTextArea.setPreferredSize(new Dimension(90, 20));
		}
	}
	
	private TalkLoginPanel loginPanel;
	private TalkPanel talkPanel;
	
}

public class Talk {  
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TalkFrame demo = new TalkFrame();
				demo.setVisible(true);
			}
		});		
	}
};

