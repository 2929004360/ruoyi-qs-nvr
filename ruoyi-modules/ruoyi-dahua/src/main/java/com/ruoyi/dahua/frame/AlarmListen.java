package com.ruoyi.dahua.frame;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.FunctionList;
import com.ruoyi.dahua.common.LoginPanel;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.AlarmListenModule;
import com.ruoyi.dahua.module.LoginModule;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 * Alarm Listen Demo
 */
class AlarmListenFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	// device disconnect callback instance
	private  DisConnect disConnect  = new DisConnect(); 
	
	// device reconnect callback instance
	private static HaveReConnect haveReConnect = new HaveReConnect(); 
	
	// alarm listen frame (this)
	private static JFrame frame = new JFrame();
	
	private Component  target = this;
	
	// alarm event info list 
    Vector<AlarmEventInfo> data = new Vector<AlarmEventInfo>();

    public AlarmListenFrame() { 	
    	setTitle(Res.string().getAlarmListen());
 	    setLayout(new BorderLayout());
 	    pack();
 	    setSize(800, 530);
 	    setResizable(false);
 	    setLocationRelativeTo(null);
 		LoginModule.init(disConnect, haveReConnect);
 	 
 		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        	e.printStackTrace();
        }
 		
 	    loginPanel = new LoginPanel();
 	    alarmListenPanel = new AlarmListenPanel();
	    showAlarmPanel = new ShowAlarmEventPanel();
	    
	    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, loginPanel, alarmListenPanel);
	    splitPane.setDividerSize(0);
 	    add(splitPane, BorderLayout.NORTH);
	    add(showAlarmPanel, BorderLayout.CENTER);
        
	    loginPanel.addLoginBtnActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	   	    
				if(loginPanel.checkLoginText()) {
					if(login()) {
						frame = ToolKits.getFrame(e);
						frame.setTitle(Res.string().getAlarmListen() + " : " + Res.string().getOnline());
					}
				}
			}
		});
	    
	    loginPanel.addLogoutBtnActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setTitle(Res.string().getAlarmListen());
				logout();	
			}
		});
        
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		AlarmListenModule.stopListen();
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
		public DisConnect() { }
		
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setTitle(Res.string().getAlarmListen() + " : " + Res.string().getDisConnectReconnecting());
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
					frame.setTitle(Res.string().getAlarmListen() + " : " + Res.string().getOnline());
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
			alarmListenPanel.setButtonEnable(true);
			
		} else {
			JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public void logout() {
		AlarmListenModule.stopListen();
		LoginModule.logout();
		
		loginPanel.setButtonEnable(false);
		alarmListenPanel.initButtonEnable();
		showAlarmPanel.clean();
	}

    public Vector<String> convertAlarmEventInfo(AlarmEventInfo alarmEventInfo) {
        Vector<String> vector = new Vector<String>();
        
        vector.add(String.valueOf(alarmEventInfo.id));
        vector.add(formatDate(alarmEventInfo.date));
        vector.add(String.valueOf(alarmEventInfo.chn));
        String status = null;
        if (alarmEventInfo.status == AlarmStatus.ALARM_START) {
        	status = Res.string().getStart();
        }else {
        	status = Res.string().getStop();
        }
        vector.add(alarmMessageMap.get(alarmEventInfo.type) + status);

        return vector;
    }
    
    private String formatDate(Date date) {
		final SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDate.format(date);
	}
    
    private fAlarmDataCB cbMessage = new fAlarmDataCB();
    // alarm listen data callback
    private class fAlarmDataCB implements NetSDKLib.fMessCallBack{
    	private final EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
         
		@Override
		public boolean invoke(int lCommand, LLong lLoginID,
				Pointer pStuEvent, int dwBufLen, String strDeviceIP,
				NativeLong nDevicePort, Pointer dwUser) {
			 	
			switch (lCommand) {
	                case NetSDKLib.NET_ALARM_ALARM_EX:
	                case NetSDKLib.NET_MOTION_ALARM_EX:
	                case NetSDKLib.NET_VIDEOLOST_ALARM_EX:
	                case NetSDKLib.NET_SHELTER_ALARM_EX:
	                case NetSDKLib.NET_DISKFULL_ALARM_EX:
	                case NetSDKLib.NET_DISKERROR_ALARM_EX: {
	                	byte []alarm = new byte[dwBufLen];
	                	pStuEvent.read(0, alarm, 0, dwBufLen);
	                	for (int i = 0; i < dwBufLen; i++) {
	                		if (alarm[i] == 1) {
                				AlarmEventInfo alarmEventInfo = new AlarmEventInfo(i, lCommand, AlarmStatus.ALARM_START);
                				if (!data.contains(alarmEventInfo)) {
                		            data.add(alarmEventInfo);
	                				eventQueue.postEvent(new AlarmListenEvent(target, alarmEventInfo));
	                			}
	                		}else {
	                			AlarmEventInfo alarmEventInfo = new AlarmEventInfo(i, lCommand, AlarmStatus.ALARM_STOP);
	                			if (data.remove(alarmEventInfo)) {
	                				eventQueue.postEvent(new AlarmListenEvent(target, alarmEventInfo));
	                			}
	                		}
	                	}
	                	break;
	                }
	                default:
	                	break;
	                	
	            }
			
				return true;
		}
    	
    }

    // alarm listen event
	class AlarmListenEvent extends AWTEvent {
		private static final long serialVersionUID = 1L;
		public static final int EVENT_ID = AWTEvent.RESERVED_ID_MAX + 1;
		
		private AlarmEventInfo alarmEventInfo;
		
		public AlarmListenEvent(Object target,
				AlarmEventInfo alarmEventInfo) {
			super(target,EVENT_ID);
			
			this.alarmEventInfo = alarmEventInfo;
			++AlarmEventInfo.index;
			this.alarmEventInfo.id = AlarmEventInfo.index;
		}
		
		public AlarmEventInfo getAlarmEventInfo() {
			return alarmEventInfo;
		}	
	}
	
	@Override
    protected void processEvent( AWTEvent event) {
        if ( event instanceof AlarmListenEvent) {
        	AlarmEventInfo alarmEventInfo = ((AlarmListenEvent)event).getAlarmEventInfo();
        	showAlarmPanel.insert(alarmEventInfo);
		} else {
            super.processEvent(event);   
        }
    } 
    
	// alarm listen control panel
    private class AlarmListenPanel extends JPanel {
    	private static final long serialVersionUID = 1L;
		
		public AlarmListenPanel() {
			BorderEx.set(this, Res.string().getAlarmListen(), 2);
			setLayout(new FlowLayout());
			
			btnStartListen = new JButton(Res.string().getStartListen());
		    btnStopListen = new JButton(Res.string().getStopListen());
		    
		    btnStartListen.setPreferredSize(new Dimension(150, 20));
		    btnStopListen.setPreferredSize(new Dimension(150, 20));
		    
		    add(btnStartListen);
		    add(new JLabel("                  "));
		    add(btnStopListen);
		    
		    initButtonEnable();
		    
		    btnStartListen.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if (AlarmListenModule.startListen(cbMessage)) {
						setButtonEnable(false);
					}else {
						JOptionPane.showMessageDialog(null, Res.string().getAlarmListenFailed() + "," + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}
				}
		    });
		    
		    btnStopListen.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if (AlarmListenModule.stopListen()) {
						showAlarmPanel.clean();
						setButtonEnable(true);
					}else {
						JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}
				}	
		    });
		}
	    
	    public void setButtonEnable(boolean b) {
	    	btnStartListen.setEnabled(b);
	    	btnStopListen.setEnabled(!b);
	    }
	    
	    public void initButtonEnable() {
	    	btnStartListen.setEnabled(false);
	    	btnStopListen.setEnabled(false);
	    }
		
	    private JButton btnStartListen;
	    private JButton btnStopListen;
    }
    
    // alarm listen event show panel
    private class ShowAlarmEventPanel extends JPanel {
    	private static final long serialVersionUID = 1L;
        private final static int MIN_SHOW_LINES = 20;
        private final static int MAX_SHOW_LINES = 100;
        private int currentRowNums = 0;
		
		public ShowAlarmEventPanel() {
			BorderEx.set(this, Res.string().getShowAlarmEvent(), 2);
			setLayout(new BorderLayout());
			
			Vector<String> columnNames =  new Vector<String>();
			columnNames.add(Res.string().getIndex()); 			// index
			columnNames.add(Res.string().getEventTime()); 		// event time
			columnNames.add(Res.string().getChannel()); 		// channel
			columnNames.add(Res.string().getAlarmMessage()); 	// alarm message
			
			tableModel = new DefaultTableModel(null, columnNames);
	        table = new JTable(tableModel) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return false;
	            }
	        };

	        tableModel.setRowCount(MIN_SHOW_LINES);	// set min show lines
	        
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			table.getColumnModel().getColumn(0).setPreferredWidth(90);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setPreferredWidth(400);
			
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.setAutoscrolls(false);
			
			table.getTableHeader().setReorderingAllowed(false);
//			table.getTableHeader().setResizingAllowed(false);
	
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			add(scrollPane, BorderLayout.CENTER);
		}
		
		public void insert(AlarmEventInfo alarmEventInfo) {
			tableModel.insertRow(0,convertAlarmEventInfo(alarmEventInfo));
			if (currentRowNums < MAX_SHOW_LINES) {
				++currentRowNums;
			}
			
			if (currentRowNums <= MIN_SHOW_LINES) {
				tableModel.setRowCount(MIN_SHOW_LINES);
			}else if (currentRowNums == MAX_SHOW_LINES) {
				tableModel.setRowCount(MAX_SHOW_LINES);
			}
			
			table.updateUI();
		}
		
		public void clean() {
			currentRowNums = 0;
			data.clear();
			AlarmEventInfo.index=0;
			tableModel.setRowCount(0);
			tableModel.setRowCount(MIN_SHOW_LINES);
			table.updateUI();
		}
		
		private JTable table = null;
		private DefaultTableModel tableModel = null;
    }
    
    private static HashMap<Integer, String> alarmMessageMap = new HashMap<Integer, String>() {
    	
		private static final long serialVersionUID = 1L;

		{
    		put(NetSDKLib.NET_ALARM_ALARM_EX, Res.string().getExternalAlarm());
    		put(NetSDKLib.NET_MOTION_ALARM_EX, Res.string().getMotionAlarm());
    		put(NetSDKLib.NET_VIDEOLOST_ALARM_EX, Res.string().getVideoLostAlarm());
    		put(NetSDKLib.NET_SHELTER_ALARM_EX, Res.string().getShelterAlarm());
    		put(NetSDKLib.NET_DISKFULL_ALARM_EX, Res.string().getDiskFullAlarm());
    		put(NetSDKLib.NET_DISKERROR_ALARM_EX, Res.string().getDiskErrorAlarm());
    	}
    };
    
    private LoginPanel loginPanel;
    private AlarmListenPanel alarmListenPanel;
    private ShowAlarmEventPanel showAlarmPanel;
    
    enum AlarmStatus {
    	ALARM_START, ALARM_STOP
    }
    
    // struct of alarm event
    static class AlarmEventInfo {
    	public static long index = 0;
    	public long id;
    	public int chn;
    	public int type;
    	public Date date;
    	public AlarmStatus status;
        
        public AlarmEventInfo(int chn, int type, AlarmStatus status) {
        	this.chn = chn;
        	this.type = type;
        	this.status = status;
        	this.date = new Date();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AlarmEventInfo showInfo = (AlarmEventInfo) o;
            return chn == showInfo.chn && type == showInfo.type;
        }
    }
    
}

public class AlarmListen {  
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AlarmListenFrame demo = new AlarmListenFrame();
				demo.setVisible(true);
			}
		});		
	}
};