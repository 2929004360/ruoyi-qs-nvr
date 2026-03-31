package com.ruoyi.dahua.frame.Attendance;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.ALARM_CAPTURE_FINGER_PRINT_INFO;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.NetSDKLib.fMessCallBack;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.AlarmListenModule;
import com.ruoyi.dahua.module.AttendanceModule;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 添加信息信息
 */
public class AddFingerPrintDialog extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int CHANNEL_ID = 0;   // 门禁序号
	private static final String READER_ID = "1";    // 读卡器ID
	private static final long TIMER_DELAY = 30000;	// 定时器超时时间
	
	private String userID = null;
	private byte []collectionData = null;
	private Timer timer = new Timer();	// 信息采集定时器
	private ReentrantLock lock = new ReentrantLock();
	
	public AddFingerPrintDialog(String userId) { 
		
		setTitle(Res.string().getAddFingerPrint());
		setLayout(new BorderLayout());
		setModal(true);
		pack();
		setSize(300, 180);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//////////采集面板  /////////////////
		JPanel collectionPanel = new JPanel();
		BorderEx.set(collectionPanel, Res.string().getcFingerPrintCollection(), 4);
		collectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 35, 25));
		collectionBtn = new JButton(Res.string().getStartCollection());
		collectionBtn.setPreferredSize(new Dimension(150, 20));
		promptLabel = new JLabel();
		promptLabel.setPreferredSize(new Dimension(150, 20));
		promptLabel.setHorizontalAlignment(JLabel.CENTER);

		collectionPanel.add(collectionBtn);
		collectionPanel.add(promptLabel);
		
		//////////功能面板  /////////////////
		JPanel functionPanel = new JPanel();
		addBtn = new JButton(Res.string().getAdd());
		cancelBtn = new JButton(Res.string().getCancel());
		addBtn.setPreferredSize(new Dimension(100, 20));
		cancelBtn.setPreferredSize(new Dimension(100, 20));
		
		functionPanel.add(addBtn);
		functionPanel.add(cancelBtn);
		
		add(collectionPanel, BorderLayout.CENTER);
		add(functionPanel, BorderLayout.SOUTH);
		
		addBtn.setEnabled(false);
		userID = userId;
		
	    cbMessage = new fCollectionDataCB();
		
		collectionBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				collectionFinger();
			}
		});
		
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (AttendanceModule.insertFingerByUserId(userID, collectionData)) {
					JOptionPane.showMessageDialog(null, Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, Res.string().getFailed() , Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
				}
				dispose();
			}
			
		});
		
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AlarmListenModule.stopListen();
				timer.cancel();
	    		dispose();
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AlarmListenModule.stopListen();
				timer.cancel();
	    		dispose();
	    	}
		});
	}
	
	public void collectionFinger() {
		
		if (!AlarmListenModule.startListen(cbMessage)) {
			JOptionPane.showMessageDialog(null, Res.string().getCollectionFailed() + "," + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		collectionData = null;
		if (!AttendanceModule.collectionFinger(CHANNEL_ID, READER_ID)) {
			JOptionPane.showMessageDialog(null, Res.string().getCollectionFailed() + "," + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return;
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				promptLabel.setText(Res.string().getInCollection());
				collectionBtn.setEnabled(false);
			}
		});
		
		timer.schedule(new TimerTask() {
			public void run() {
				lock.lock();
				if (collectionData == null) {
					AlarmListenModule.stopListen();
					promptLabel.setText(Res.string().getCollectionFailed());
					collectionBtn.setEnabled(true);
				}
				lock.unlock();
			}
			
		}, TIMER_DELAY);
	}
	
   /**
	 *  信息采集监听回调
	 **/
    private class fCollectionDataCB implements fMessCallBack{
    	
		@Override
		public boolean invoke(int lCommand, LLong lLoginID,
				Pointer pStuEvent, int dwBufLen, String strDeviceIP,
				NativeLong nDevicePort, Pointer dwUser) {
			 
			if (lCommand == NetSDKLib.NET_ALARM_FINGER_PRINT) {
				lock.lock();
				if (collectionData == null) {
					timer.cancel();
					ALARM_CAPTURE_FINGER_PRINT_INFO msg = new ALARM_CAPTURE_FINGER_PRINT_INFO();
		  			ToolKits.GetPointerData(pStuEvent, msg);
					collectionData = new byte[msg.nPacketLen * msg.nPacketNum];
					msg.szFingerPrintInfo.read(0, collectionData, 0, msg.nPacketLen * msg.nPacketNum);
		    		SwingUtilities.invokeLater(new Runnable() {
		    			public void run() {
		    				AlarmListenModule.stopListen();
		    				promptLabel.setText(Res.string().getcCompleteCollection());
		    				addBtn.setEnabled(true);
		    			}
		    		});
				}
				lock.unlock();
			}
			
			return true;
		}
    	
    }
	
    private fMessCallBack cbMessage; 	// 信息采集回调
	private JLabel promptLabel; 		// 提示信息
	private JButton collectionBtn;		// 采集按钮
	private JButton addBtn;				// 添加按钮
	private JButton cancelBtn;			// 取消按钮
}
