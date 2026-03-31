package com.ruoyi.dahua.frame;

import com.ruoyi.dahua.common.*;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.CapturePictureModule;
import com.ruoyi.dahua.module.LoginModule;
import com.ruoyi.dahua.module.RealPlayModule;
import com.sun.jna.CallbackThreadInitializer;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Capture Picture Demo
 */
class CapturePictureFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	// device channel list
	private Vector<String> chnlist = new Vector<String>(); 

	// This field indicates whether the device is playing
	private boolean bRealPlay = false;
	
	// This field indicates whether the device is timing capture
	private boolean bTimerCapture = false;	
	
	// device disconnect callback instance
	private static DisConnect disConnect       = new DisConnect(); 
	
	// device reconnect callback instance
	private static HaveReConnect haveReConnect = new HaveReConnect(); 
	
	// realplay handle
	public static LLong m_hPlayHandle = new LLong(0);
	
	// capture picture frame (this)
	private static JFrame frame = new JFrame();   
	
	public CapturePictureFrame() {
	    setTitle(Res.string().getCapturePicture());
	    setLayout(new BorderLayout());
	    pack();
	    setSize(800, 560);
	    setResizable(false);
	    setLocationRelativeTo(null);
		LoginModule.init(disConnect, haveReConnect);   // init sdk
		
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        	e.printStackTrace();
        } 
	 
	    loginPanel = new LoginPanel();
	    realPanel = new RealPanel();
	    picPanel = new PICPanel();
	    
	    add(loginPanel, BorderLayout.NORTH);
	    add(realPanel, BorderLayout.CENTER);
	    add(picPanel, BorderLayout.EAST);
	    
	    loginPanel.addLoginBtnActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	   	    
				if(loginPanel.checkLoginText()) {
					if(login()) {
						frame = ToolKits.getFrame(e);
						frame.setTitle(Res.string().getCapturePicture() + " : " + Res.string().getOnline());
					}
				}
			}
		});
	    
	    loginPanel.addLogoutBtnActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setTitle(Res.string().getCapturePicture());
				logout();	
			}
		});
        
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		RealPlayModule.stopRealPlay(m_hPlayHandle);
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
					frame.setTitle(Res.string().getCapturePicture() + " : " + Res.string().getDisConnectReconnecting());
				}
			});
		}
	}
	
	// device reconnect(success) callback class
	// set it's instance by call CLIENT_SetAutoReconnect, when device reconnect success sdk will call it.
	private static class HaveReConnect implements NetSDKLib.fHaveReConnect {
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setTitle(Res.string().getCapturePicture() + " : " + Res.string().getOnline());
				}
			});
		}
	}
	
	public boolean login() {
		Native.setCallbackThreadInitializer(m_CaptureReceiveCB, 
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
			
			chnComboBox.setModel(new DefaultComboBoxModel(chnlist));
		
			CapturePictureModule.setSnapRevCallBack(m_CaptureReceiveCB);		
		} else {
			JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public void logout() {
		if (bTimerCapture) {
			CapturePictureModule.stopCapturePicture(chnComboBox.getSelectedIndex());
		}
		RealPlayModule.stopRealPlay(m_hPlayHandle);
		LoginModule.logout();
		
		loginPanel.setButtonEnable(false);
		setButtonEnable(false);
		realPlayWindow.repaint();    
		pictureShowWindow.setOpaque(true);
		pictureShowWindow.repaint();
		
		bRealPlay = false;
		realplayBtn.setText(Res.string().getStartRealPlay());
		
		for(int i = 0; i < LoginModule.m_stDeviceInfo.byChanNum; i++) {
			chnlist.clear();
		}
		
		chnComboBox.setModel(new DefaultComboBoxModel());
		
		bTimerCapture = false;
		timerCaptureBtn.setText(Res.string().getTimerCapture());
	}
	
	/*
	 * realplay show and control panel
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
			
			/************ realplay panel **************/
			realplayPanel.setLayout(new BorderLayout());
			realplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			realPlayWindow = new Panel();
			realPlayWindow.setBackground(Color.GRAY);
			realplayPanel.add(realPlayWindow, BorderLayout.CENTER);
			
			/************ channel and stream panel **************/
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
	
	public void realplay() {
		if(!bRealPlay) {
			m_hPlayHandle = RealPlayModule.startRealPlay(chnComboBox.getSelectedIndex(), 
				    streamComboBox.getSelectedIndex()==0? 0:3,
					realPlayWindow);
			if(m_hPlayHandle.longValue() != 0) {
				realPlayWindow.repaint();
				bRealPlay = true;
				chnComboBox.setEnabled(false);
				streamComboBox.setEnabled(false);
				realplayBtn.setText(Res.string().getStopRealPlay());
			} 
		} else {
			RealPlayModule.stopRealPlay(m_hPlayHandle);
			realPlayWindow.repaint();
			bRealPlay = false;
			chnComboBox.setEnabled(true && !bTimerCapture);
			streamComboBox.setEnabled(true);
			realplayBtn.setText(Res.string().getStartRealPlay());
		}	
	}
	
	/*
	 * capture picture panel 
	 */
	private class PICPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public PICPanel() {
			
			setPreferredSize(new Dimension(350, 600));
			BorderEx.set(this, Res.string().getCapturePicture(), 2);
			setLayout(new BorderLayout());
			
			pictureShowPanel = new JPanel();  
			capturePanel = new JPanel();
			
			add(pictureShowPanel, BorderLayout.CENTER);
			add(capturePanel, BorderLayout.SOUTH);
			
			/************** capture picture button ************/
			capturePanel.setLayout(new GridLayout(3, 1));
			
			localCaptureBtn = new JButton(Res.string().getLocalCapture());
			remoteCaptureBtn = new JButton(Res.string().getRemoteCapture());
			timerCaptureBtn = new JButton(Res.string().getTimerCapture());
			
			localCaptureBtn.setPreferredSize(new Dimension(150, 20));
			remoteCaptureBtn.setPreferredSize(new Dimension(150, 20));
			timerCaptureBtn.setPreferredSize(new Dimension(150, 20));
			
			capturePanel.add(localCaptureBtn);
			capturePanel.add(remoteCaptureBtn);
			capturePanel.add(timerCaptureBtn);
			
			localCaptureBtn.setEnabled(false);
			remoteCaptureBtn.setEnabled(false);
			timerCaptureBtn.setEnabled(false);
			
			/************** picture show panel ************/
			pictureShowPanel.setLayout(new BorderLayout());
			pictureShowPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			pictureShowWindow = new PaintPanel();
			pictureShowPanel.add(pictureShowWindow, BorderLayout.CENTER);
		    
			localCaptureBtn.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (!bRealPlay) {
						JOptionPane.showMessageDialog(null, Res.string().getNeedStartRealPlay(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					String strFileName = SavePath.getSavePath().getSaveCapturePath();
					System.out.println("strFileName = " + strFileName);
					
					if(!CapturePictureModule.localCapturePicture(m_hPlayHandle, strFileName)) {
						JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					BufferedImage bufferedImage = null;       
					try {
						bufferedImage = ImageIO.read(new File(strFileName)); 
						if(bufferedImage == null) {
							return;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}	
					pictureShowWindow.setOpaque(false);
					pictureShowWindow.setImage(bufferedImage);
					pictureShowWindow.repaint();	
					
				}
			});
			
			remoteCaptureBtn.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(!CapturePictureModule.remoteCapturePicture(chnComboBox.getSelectedIndex())) {
						JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			
			timerCaptureBtn.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (!bTimerCapture) {
						
						if(!CapturePictureModule.timerCapturePicture(chnComboBox.getSelectedIndex())) {
							JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						}else{
							bTimerCapture = true;
							timerCaptureBtn.setText(Res.string().getStopCapture());
							chnComboBox.setEnabled(false);
							remoteCaptureBtn.setEnabled(false);
						}
					}else {
						if(!CapturePictureModule.stopCapturePicture(chnComboBox.getSelectedIndex())) {
							JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						}else{
							bTimerCapture = false;
							timerCaptureBtn.setText(Res.string().getTimerCapture());
							chnComboBox.setEnabled(true && !bRealPlay);
							remoteCaptureBtn.setEnabled(true);
						}
					}
				}
			});
		}
	}
	
	public fCaptureReceiveCB  m_CaptureReceiveCB = new fCaptureReceiveCB();
	public class fCaptureReceiveCB implements NetSDKLib.fSnapRev{
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
				
				// show picture	 
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
		localCaptureBtn.setEnabled(bln);
		remoteCaptureBtn.setEnabled(bln);
		timerCaptureBtn.setEnabled(bln);
		realPlayWindow.setEnabled(bln);
		chnComboBox.setEnabled(bln);
		streamComboBox.setEnabled(bln);
		realplayBtn.setEnabled(bln);
	}
	
	private LoginPanel loginPanel;
	
    private RealPanel realPanel;
    private JPanel realplayPanel;
    private Panel realPlayWindow;
    private Panel channelPanel;
    private JLabel chnlabel;
    private JComboBox chnComboBox;	
    private JLabel streamLabel;
    private JComboBox streamComboBox;
    private JButton realplayBtn;
    
	private PICPanel picPanel;
	private JPanel pictureShowPanel;
	private JPanel capturePanel;
	private PaintPanel pictureShowWindow;
    private JButton localCaptureBtn;
    private JButton remoteCaptureBtn;
    private JButton timerCaptureBtn;

}

public class CapturePicture {  
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CapturePictureFrame demo = new CapturePictureFrame();	
				demo.setVisible(true);
			}
		});		
	}
};


