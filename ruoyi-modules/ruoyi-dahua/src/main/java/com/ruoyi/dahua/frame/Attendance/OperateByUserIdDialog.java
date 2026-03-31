package com.ruoyi.dahua.frame.Attendance;

import com.ruoyi.dahua.common.Base64;
import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.module.AttendanceModule;
import com.ruoyi.dahua.module.AttendanceModule.OPERATE_TYPE;
import com.ruoyi.dahua.module.AttendanceModule.UserData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class OperateByUserIdDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserData userData;
	
	public OperateByUserIdDialog(UserData userData) {
		setTitle(Res.string().getOperateByUserId());
		setLayout(new BorderLayout());
		setModal(true);
		pack();
		setSize(570, 383);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		////////// 用户信息 (不可改变)/////////////////
		JPanel userInfoPanel = new JPanel();
		userInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		BorderEx.set(userInfoPanel, Res.string().getUserInfo(), 2);

		JLabel userIdLabel = new JLabel(Res.string().getUserId(), JLabel.CENTER);
		JTextField userIdTextField = new JTextField(userData.userId);
		JLabel userNameLabel = new JLabel(Res.string().getUserName(true), JLabel.CENTER);
		JTextField userNameTextField = new JTextField(userData.userName);
		JLabel cardNoLabel = new JLabel(Res.string().getCardNo(), JLabel.CENTER);
		JTextField cardNoTextField = new JTextField(userData.cardNo);
		
		userIdTextField.setEnabled(false);
		userNameTextField.setEnabled(false);
		cardNoTextField.setEnabled(false);
		
		Dimension dimLable = new Dimension(55, 20);
		userIdLabel.setPreferredSize(dimLable);
		userNameLabel.setPreferredSize(dimLable);
		cardNoLabel.setPreferredSize(dimLable);
		Dimension dimValue = new Dimension(100, 20);
		userIdTextField.setPreferredSize(dimValue);
		userNameTextField.setPreferredSize(dimValue);
		cardNoTextField.setPreferredSize(dimValue);
		
		userInfoPanel.add(userIdLabel);
		userInfoPanel.add(userIdTextField);
		userInfoPanel.add(userNameLabel);
		userInfoPanel.add(userNameTextField);
		userInfoPanel.add(cardNoLabel);
		userInfoPanel.add(cardNoTextField);
		
		////////// 信息功能 /////////////////
		JPanel functionPanel = new JPanel();
		BorderEx.set(functionPanel, Res.string().getOperateByUserId(), 2);
		searchFingerPrintBtn = new JButton(Res.string().getSearchFingerPrint());
		addFingerPrintBtn = new JButton(Res.string().getAddFingerPrint());
		deleteFingerPrintBtn = new JButton(Res.string().getDeleteFingerPrint());
		
		searchFingerPrintBtn.setPreferredSize(new Dimension(150, 20));
		addFingerPrintBtn.setPreferredSize(new Dimension(150, 20));
		deleteFingerPrintBtn.setPreferredSize(new Dimension(150, 20));

		functionPanel.add(searchFingerPrintBtn);
		functionPanel.add(addFingerPrintBtn);
		functionPanel.add(deleteFingerPrintBtn);
		
		//////////布局 /////////////////
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerSize(0);
		splitPane.setBorder(null);
		splitPane.add(userInfoPanel, JSplitPane.TOP);
		splitPane.add(functionPanel, JSplitPane.BOTTOM);
	 	add(splitPane, BorderLayout.NORTH);
		
	 	fingerPrintShowPanel = new FingerPrintShowPanel();
	 	add(fingerPrintShowPanel, BorderLayout.CENTER);
	 	
		listener = new UserIdOperateActionListener();
		searchFingerPrintBtn.addActionListener(listener);
		addFingerPrintBtn.addActionListener(listener);
		deleteFingerPrintBtn.addActionListener(listener);
		
		this.userData = userData;
	}
	
	public void searchFingerPrint() {
		clearTable();
		boolean bSuccess = AttendanceModule.getFingerByUserId(userData.userId, userData);
		if (bSuccess){
			fingerPrintShowPanel.insertData(userData);
		} else {
			JOptionPane.showMessageDialog(null, Res.string().getFailed(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void clearTable() {
		fingerPrintShowPanel.clearData();
	}
	
	public void addFingerPrint(int fingerPrintId, byte[] fingerPrintData) {
		fingerPrintShowPanel.insertData(fingerPrintId, fingerPrintData);
	}
	
	/**
	 * 按键监听实现类
	 */
	private class UserIdOperateActionListener implements ActionListener {	

		@Override
		public void actionPerformed(ActionEvent arg0) {

			OPERATE_TYPE emType = getOperateType(arg0.getSource());
			switch(emType) {
				case SEARCH_FINGERPRINT_BY_USERID:
					searchFingerPrint();
					break;
				case ADD_FINGERPRINT:
					new AddFingerPrintDialog(userData.userId).setVisible(true);
					break;
				case DELETE_FINGERPRINT_BY_USERID:
					new AttendanceOperateShareDialog(emType, userData).setVisible(true);
					break;
				default:
					break;
			}
		}
		
		private OPERATE_TYPE getOperateType(Object btn) {
			OPERATE_TYPE type = OPERATE_TYPE.UNKNOWN;
			if (btn == searchFingerPrintBtn) { // 查找信息
				type = OPERATE_TYPE.SEARCH_FINGERPRINT_BY_USERID;
			}else if (btn == addFingerPrintBtn) {	// 添加信息
				type = OPERATE_TYPE.ADD_FINGERPRINT;
			}else if (btn == deleteFingerPrintBtn) {	// 删除信息(用户ID)
				type = OPERATE_TYPE.DELETE_FINGERPRINT_BY_USERID;
			}else {
				System.err.println("Unknown Event: " + btn);
			}
			
			return type;
			
		}
	}
	
	/**
     * 信息信息显示界面
     * */
    public class FingerPrintShowPanel extends JPanel {
    	/**
    	 * 
    	 */
    	private static final long serialVersionUID = 1L;
    	public static final int INDEX = 0;
    	public static final int USER_ID = 1;
    	public static final int FINGERPRINT_ID = 2;
    	public static final int FINGERPRINT_DATA = 3;
    	
    	public final static int MAX_FINGERPRINT_NUM = 10; // 最大信息个数, 也做为显示个数
    	private int realRows = 0;		// 实际显示个数
    	
		public FingerPrintShowPanel() {
			BorderEx.set(this, Res.string().getFingerPrintInfo(), 1);
			setLayout(new BorderLayout());
			setPreferredSize(new Dimension(550, 375));
			Vector<String> columnNames =  new Vector<String>();
			columnNames.add(Res.string().getIndex()); 			// 序号
			columnNames.add(Res.string().getUserId()); 			// 用户编号
			columnNames.add(Res.string().getFingerPrintId());	// 信息ID
			columnNames.add(Res.string().getFingerPrintData());	// 信息
			
			tableModel = new DefaultTableModel(null, columnNames);
	        table = new JTable(tableModel) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int rowIndex, int columnIndex) { // 不可编辑
	                return false;
	            }
	        };

	        tableModel.setRowCount(MAX_FINGERPRINT_NUM);	// 设置最小显示行
	        
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 只能选中一行（其实无意义）
			
			table.getColumnModel().getColumn(INDEX).setPreferredWidth(80);
			table.getColumnModel().getColumn(USER_ID).setPreferredWidth(100);
			table.getColumnModel().getColumn(FINGERPRINT_ID).setPreferredWidth(100);
			table.getColumnModel().getColumn(FINGERPRINT_DATA).setPreferredWidth(8888);
			
			((DefaultTableCellRenderer)
					table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			add(scrollPane, BorderLayout.CENTER);
		}
		
		public void insertData(UserData userData) {
			if (userData.nFingerPrintIDs == null) {
				return;
			}
			
			clearData();
			tableModel.setRowCount(0);
			for (int i = 0; i < userData.nFingerPrintIDs.length; ++i) {
				insertFingerPrintData(userData.nFingerPrintIDs[i], userData.szFingerPrintInfo[i]);
			}
			
			tableModel.setRowCount(MAX_FINGERPRINT_NUM);
			table.updateUI();
		}
		
		public void insertData(int fingerPrintId, byte[] fingerPrintData) {
			tableModel.setRowCount(realRows);
			insertFingerPrintData(fingerPrintId, fingerPrintData);
			tableModel.setRowCount(MAX_FINGERPRINT_NUM);
			table.updateUI();
		}
		
		private void insertFingerPrintData(int fingerPrintId, byte[] fingerPrintData) {
			++realRows;
			Vector<String> vector = new Vector<String>();
			vector.add(String.valueOf(realRows));
			vector.add(userData.userId);
			vector.add(String.valueOf(fingerPrintId));
			vector.add(formatFingerPrintData(fingerPrintData));
			tableModel.addRow(vector);
		}
		
		private String formatFingerPrintData(byte[] fingerPrintData) {
			String formatData = Base64.getEncoder().encodeToString(fingerPrintData);
			return formatData;
		}
			
		public void clearData() {
			realRows = 0;
			tableModel.setRowCount(0);
			tableModel.setRowCount(MAX_FINGERPRINT_NUM);
			table.updateUI();
		}
		
		private JTable table = null;
		private DefaultTableModel tableModel = null;
    }
    
	public JButton searchFingerPrintBtn;
	private JButton addFingerPrintBtn;
	private JButton deleteFingerPrintBtn;
	private UserIdOperateActionListener listener;
	private FingerPrintShowPanel fingerPrintShowPanel;
}
