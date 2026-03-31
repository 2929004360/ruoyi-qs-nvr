package com.ruoyi.dahua.frame.Attendance;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.common.Res.LanguageType;
import com.ruoyi.dahua.lib.NetSDKLib.NET_ACCESS_DOOROPEN_METHOD;
import com.ruoyi.dahua.module.AttendanceModule.AccessEventInfo;
import com.ruoyi.dahua.module.AttendanceModule.UserData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class AttendanceShowPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int userIndex = 0;
	public static int eventIndex = 0;
    
	public AttendanceShowPanel() {
		setLayout(new BorderLayout());
		
		userShowPanel = new UserInfoShowPanel();
		eventShowPanel = new EventInfoShowPanel();
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(0);
		splitPane.setBorder(null);
		splitPane.add(userShowPanel, JSplitPane.LEFT);
		splitPane.add(eventShowPanel, JSplitPane.RIGHT);
		
		add(splitPane);
	}
	
	public void clearup() {
		userShowPanel.clearData();
		eventShowPanel.clearEvent();
	}
	
	/**
     * 用户信息显示界面
     * */
    public class UserInfoShowPanel extends JPanel {
    	/**
    	 * 
    	 */
    	private static final long serialVersionUID = 1L;
    	public static final int INDEX = 0;
    	public static final int USER_ID = 1;
    	public static final int USER_NAME = 2;
    	public static final int CARD_NO = 3;
    	public static final int FINGERPRINT_ID = 4;
    	public static final int FINGERPRINT_DATA = 5;
    	
    	public final static int QUERY_SHOW_COUNT = 15; // 查询人数
        private int realRows = 0;		// 实际显示个数
		
		public UserInfoShowPanel() {
			BorderEx.set(this, Res.string().getUserList(), 1);
			setLayout(new BorderLayout());
			setPreferredSize(new Dimension(395, 400));
			Vector<String> columnNames =  new Vector<String>();
			columnNames.add(Res.string().getIndex()); 			// 序号
			columnNames.add(Res.string().getUserId()); 			// 用户编号
			columnNames.add(Res.string().getUserName()); 		// 用户名
			columnNames.add(Res.string().getCardNo()); 			// 卡号
			
			tableModel = new DefaultTableModel(null, columnNames);
	        table = new JTable(tableModel) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int rowIndex, int columnIndex) { // 不可编辑
	                return false;
	            }
	        };

	        tableModel.setRowCount(QUERY_SHOW_COUNT);	// 设置最小显示行
	        
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 只能选中一行
			
			table.getColumnModel().getColumn(INDEX).setPreferredWidth(80);
			table.getColumnModel().getColumn(USER_ID).setPreferredWidth(150);
			table.getColumnModel().getColumn(USER_NAME).setPreferredWidth(150);
			table.getColumnModel().getColumn(CARD_NO).setPreferredWidth(150);
			
			((DefaultTableCellRenderer)
					table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			prePageBtn = new JButton(Res.string().getPreviousPage());
			nextPageBtn = new JButton(Res.string().getNextPage());
			prePageBtn.setPreferredSize(new Dimension(120, 20));
			nextPageBtn.setPreferredSize(new Dimension(120, 20));
			
			prePageBtn.setEnabled(false);
			nextPageBtn.setEnabled(false);
			
			functionPanel.add(prePageBtn);
			functionPanel.add(new JLabel("    "));
			functionPanel.add(nextPageBtn);
			
			add(scrollPane, BorderLayout.CENTER);
			add(functionPanel, BorderLayout.SOUTH);
		}
		
		public int getRows(){
			return realRows;
		}
		
		public UserData GetSelectedItem() {
			int currentRow = table.getSelectedRow(); //获得所选的单行
			if(currentRow < 0 || currentRow + 1 > realRows) {
				return null;
			}
			UserData userData = new UserData();
			
			userData.userId = (String) tableModel.getValueAt(currentRow, 1);
			userData.userName = (String) tableModel.getValueAt(currentRow, 2);
			userData.cardNo = (String) tableModel.getValueAt(currentRow, 3);
			
			return userData;
		}
		
		public void updateSelectedItem(UserData userData) {
			
			int currentRow = table.getSelectedRow(); //获得所选的单行
			if(currentRow < 0 || currentRow + 1 > realRows) {
				return;
			}
			
//			tableModel.setValueAt(userData.userId, currentRow, 1);
			tableModel.setValueAt(userData.userName, currentRow, 2);
			tableModel.setValueAt(userData.cardNo, currentRow, 3);
			table.updateUI();
		}
		
		public void insertData(UserData[] arrUserData) {
			if (arrUserData == null) {
				return;
			}
			
			realRows = 0;
			tableModel.setRowCount(0);
			
			for (UserData userData : arrUserData) {
				insertUserData(userData);
			}
			
			tableModel.setRowCount(QUERY_SHOW_COUNT);
			table.updateUI();
			
			setButtonEnable(true);
		}
		
		public void setButtonEnable(boolean b) {
			if (b) {
				if (UserData.nTotalUser - userIndex > 0) {
					nextPageBtn.setEnabled(true);
				}else {
					nextPageBtn.setEnabled(false);
				}
				
				if (userIndex - QUERY_SHOW_COUNT > 0) {
					prePageBtn.setEnabled(true);
				}else {
					prePageBtn.setEnabled(false);
				}
			}else {
				prePageBtn.setEnabled(false);
				nextPageBtn.setEnabled(false);
			}
		}
		
		public void insertData(UserData userData) {
			if (userData == null) {
				return;
			}
			
			clearData();
			tableModel.setRowCount(0);
			
			insertUserData(userData);
			
			tableModel.setRowCount(QUERY_SHOW_COUNT);
			table.updateUI();
			
			setButtonEnable(false);
		}
		
		
		private void insertUserData(UserData userData) {
			++userIndex;
			++realRows;
			Vector<String> vector = new Vector<String>();
			vector.add(String.valueOf(userIndex));
		    vector.add(userData.userId);
		    vector.add(userData.userName);
		    vector.add(userData.cardNo);
		    
		    tableModel.addRow(vector);
		}
			
		public void clearData() {
			realRows = 0;
			userIndex = 0;
			tableModel.setRowCount(0);
			tableModel.setRowCount(QUERY_SHOW_COUNT);
			table.updateUI();
			prePageBtn.setEnabled(false);
			nextPageBtn.setEnabled(false);
		}
		
		private JTable table = null;
		private DefaultTableModel tableModel = null;
		public JButton prePageBtn;
		public JButton nextPageBtn;
    }
    
    /**
     * 门禁事件显示界面
     * */
    public class EventInfoShowPanel extends JPanel {
    	/**
    	 * 
    	 */
    	private static final long serialVersionUID = 1L;
    	private static final int INDEX = 0;
    	private static final int USER_ID = 1;
    	private static final int CARD_NO = 2;
    	private static final int EVENT_TIME = 3;
    	private static final int DOOR_OPEN_METHOD = 4;
    	
    	private final static int MIN_SHOW_LINES = 17;
        private final static int MAX_SHOW_LINES = 50;
		
		public EventInfoShowPanel() {
			BorderEx.set(this, Res.string().getEventInfo(), 1);
			setLayout(new BorderLayout());
			setPreferredSize(new Dimension(395, 400));
			
			Vector<String> columnNames =  new Vector<String>();
			columnNames.add(Res.string().getIndex()); 			// 序号
			columnNames.add(Res.string().getUserId()); 			// 用户编号
			columnNames.add(Res.string().getCardNo()); 			// 卡号
			columnNames.add(Res.string().getEventTime()); 		// 事件时间
			columnNames.add(Res.string().getDoorOpenMethod()); 	// 开门方式
			
			tableModel = new DefaultTableModel(null, columnNames);
	        table = new JTable(tableModel) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return false;
	            }
	        };

	        tableModel.setRowCount(MIN_SHOW_LINES);	// 设置最小显示行
	        
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 只能选中一行
			
			table.getColumnModel().getColumn(INDEX).setPreferredWidth(80);
			table.getColumnModel().getColumn(USER_ID).setPreferredWidth(150);
			table.getColumnModel().getColumn(CARD_NO).setPreferredWidth(150);
			table.getColumnModel().getColumn(EVENT_TIME).setPreferredWidth(150);
			table.getColumnModel().getColumn(DOOR_OPEN_METHOD).setPreferredWidth(120);
			
			((DefaultTableCellRenderer)
					table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			add(scrollPane, BorderLayout.CENTER);
		}
		
		public void clearEvent() {
			eventIndex = 0;
			tableModel.setRowCount(0);
			tableModel.setRowCount(MIN_SHOW_LINES);
			table.updateUI();
		}
		
		public void insertEvent(AccessEventInfo accessEventInfo) {
			if (accessEventInfo == null) {
				return;
			}
			++eventIndex;
			tableModel.insertRow(0, convertEventData(accessEventInfo));
			if (eventIndex <= MIN_SHOW_LINES) {
				tableModel.setRowCount(MIN_SHOW_LINES);
			}else if (eventIndex >= MAX_SHOW_LINES){
				tableModel.setRowCount(MAX_SHOW_LINES);
			}
			
			table.updateUI();
		}
		
		private Vector<String> convertEventData(AccessEventInfo accessEventInfo) {    
			Vector<String> vector = new Vector<String>();
		    vector.add(String.valueOf(eventIndex));
		    vector.add(accessEventInfo.userId);
		    vector.add(accessEventInfo.cardNo);
		    vector.add(accessEventInfo.eventTime.replace("/", "-"));
		    String openDoor = openDoorMethodMap.get(accessEventInfo.openDoorMethod);
		    if (openDoor == null) {
		    	openDoor = Res.string().getUnKnow();
		    }
		    vector.add(openDoor);
		
		    return vector;
		}
		
		private JTable table = null;
		private DefaultTableModel tableModel = null;
    }
    
    private static HashMap<Integer, String> openDoorMethodMap = new HashMap<Integer, String>() {
    	
		private static final long serialVersionUID = 1L;

		{
    		put(NET_ACCESS_DOOROPEN_METHOD.NET_ACCESS_DOOROPEN_METHOD_FINGERPRINT, Res.string().getFingerPrint());
    		put(NET_ACCESS_DOOROPEN_METHOD.NET_ACCESS_DOOROPEN_METHOD_CARD, Res.string().getCard());
    	}
    };
    
    public UserInfoShowPanel userShowPanel;
    public EventInfoShowPanel eventShowPanel;
    
    public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		Res.string().switchLanguage(LanguageType.English);
		
		AttendanceShowPanel demo = new AttendanceShowPanel();
		JFrame frame = new JFrame();
		frame.setSize(800, 560);
		frame.add(demo);
		System.out.println("AttendanceShowPanel Test");
		frame.setVisible(true);		
	}
}
