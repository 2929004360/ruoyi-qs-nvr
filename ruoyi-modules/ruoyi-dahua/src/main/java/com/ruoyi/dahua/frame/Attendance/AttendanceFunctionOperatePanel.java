package com.ruoyi.dahua.frame.Attendance;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.frame.Attendance.AttendanceShowPanel.UserInfoShowPanel;
import com.ruoyi.dahua.module.AttendanceModule;
import com.ruoyi.dahua.module.AttendanceModule.OPERATE_TYPE;
import com.ruoyi.dahua.module.AttendanceModule.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 考勤机操作面板
 */
public class AttendanceFunctionOperatePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static boolean bLogout = false;
	public AttendanceShowPanel showPanel;	// 显示面板
	private AttendanceFunctionOperatePanel target = this; // 为了传值
	
	public AttendanceFunctionOperatePanel(AttendanceShowPanel showPanel) {
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(800, 120));
		
		listener = new UserOperateActionListener();
		
		userPanel = new FunctionOperatePanel();
		subscribePanel = new SubscribePanel(showPanel.eventShowPanel);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(0);
		splitPane.setBorder(null);
		splitPane.add(userPanel, JSplitPane.LEFT);
		splitPane.add(subscribePanel, JSplitPane.RIGHT);
		
	    add(splitPane, BorderLayout.CENTER);
			
		this.showPanel = showPanel;
		this.showPanel.userShowPanel.prePageBtn.addActionListener(listener);
		this.showPanel.userShowPanel.nextPageBtn.addActionListener(listener);
	}
	
	public void setButtonEnable(boolean b) {
		bLogout=!b;
		userPanel.setButtonEnable(b);
		subscribePanel.setButtonEnable(b);
	}
	
	public void setSearchEnable(boolean b) {
		showPanel.userShowPanel.setButtonEnable(b);
		userPanel.searchPersonBtn.setEnabled(b);
	}
	
	public void insertData(UserData[] arrUserData) {
		showPanel.userShowPanel.insertData(arrUserData);
	}
	
	public void insertData(UserData userData) {
		showPanel.userShowPanel.insertData(userData);
	}
	
	/**
	 * 总的功能操作面板
	 */
	public class FunctionOperatePanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public FunctionOperatePanel() {
//			BorderEx.set(this, Res.string().getOperateByUser(), 1);
			setLayout(new BorderLayout());
			setPreferredSize(new Dimension(600, 60));
			
			////////// 查询条件 /////////////////
			JLabel userIdLabel = new JLabel(Res.string().getUserId(), JLabel.CENTER);
			userIdTextField = new JTextField();
			userIdLabel.setPreferredSize(new Dimension(80, 20));
			userIdTextField.setPreferredSize(new Dimension(110, 20));
			
			////////// 功能面板 /////////////////
			// 用户功能面板
			JPanel userFunctionPanel = new JPanel();
			userFunctionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			BorderEx.set(userFunctionPanel, Res.string().getUserOperate(), 1);
			searchPersonBtn = new JButton(Res.string().getSearch());
			addPersonBtn = new JButton(Res.string().getAdd());
			modifyPersonBtn = new JButton(Res.string().getModify());
			deletePersonBtn = new JButton(Res.string().getDelete());

			searchPersonBtn.setPreferredSize(new Dimension(90, 20));
			addPersonBtn.setPreferredSize(new Dimension(90, 20));
			modifyPersonBtn.setPreferredSize(new Dimension(90, 20));
			deletePersonBtn.setPreferredSize(new Dimension(90, 20));
			
			userFunctionPanel.add(userIdLabel);
			userFunctionPanel.add(userIdTextField);
			userFunctionPanel.add(searchPersonBtn);
			userFunctionPanel.add(addPersonBtn);
			userFunctionPanel.add(modifyPersonBtn);
			userFunctionPanel.add(deletePersonBtn);
			
			// 信息功能面板
			JPanel fingerPrintFunctionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			BorderEx.set(fingerPrintFunctionPanel, Res.string().getFingerPrintOperate(), 1);
			operateByUserIdBtn = new JButton(Res.string().getOperateByUserId());
			operateByFingerPrintIdBtn = new JButton(Res.string().getOperateByFingerPrintId());

			operateByUserIdBtn.setPreferredSize(new Dimension(260, 20));
			operateByFingerPrintIdBtn.setPreferredSize(new Dimension(260, 20));
			fingerPrintFunctionPanel.add(operateByUserIdBtn);
			fingerPrintFunctionPanel.add(operateByFingerPrintIdBtn);
			
			JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			splitPane.setDividerSize(0);
			splitPane.setBorder(null);
			splitPane.add(userFunctionPanel, JSplitPane.TOP);
			splitPane.add(fingerPrintFunctionPanel, JSplitPane.BOTTOM);
		 	add(splitPane, BorderLayout.CENTER);
			
		 	searchPersonBtn.addActionListener(listener);
			addPersonBtn.addActionListener(listener);
			modifyPersonBtn.addActionListener(listener);
			deletePersonBtn.addActionListener(listener);
			operateByUserIdBtn.addActionListener(listener);
			operateByFingerPrintIdBtn.addActionListener(listener);
			
			setButtonEnable(false);
		}
	
		public void setButtonEnable(boolean b) {
			searchPersonBtn.setEnabled(b);
			addPersonBtn.setEnabled(b);
			modifyPersonBtn.setEnabled(b);
			deletePersonBtn.setEnabled(b);
			operateByUserIdBtn.setEnabled(b);
			operateByFingerPrintIdBtn.setEnabled(b);
		}
		
		public void searchPerson(OPERATE_TYPE type) { // flush 为 true 时 强制刷新
			if (type == OPERATE_TYPE.SEARCH_USER && !userIdTextField.getText().isEmpty()) {
				UserData userData = AttendanceModule.getUser(userIdTextField.getText());
				if (userData == null) {
					JOptionPane.showMessageDialog(null, Res.string().getFailed(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					return;
				}
				showPanel.userShowPanel.insertData(userData);				
			}else {
				setSearchEnable(false);
				new SearchPersonSwingWorker(type, target).execute();
			}
		}
		
		private JTextField userIdTextField;
		public JButton searchPersonBtn;
		private JButton addPersonBtn;
		private JButton modifyPersonBtn;
		private JButton deletePersonBtn;
		private JButton operateByUserIdBtn;
		private JButton operateByFingerPrintIdBtn;
	}
	
	/**
	 * 按键监听实现类
	 */
	private class UserOperateActionListener implements ActionListener {	

		@Override
		public void actionPerformed(ActionEvent arg0) {

			OPERATE_TYPE emType = getOperateType(arg0.getSource());
			switch(emType) {
				case SEARCH_USER:
				case PRE_SEARCH_USER:
				case NEXT_SEARCH_USER:
					SwingUtilities.invokeLater(new SearchRunnable(emType));
					break;	
				case ADD_USER: 
					new AttendanceOperateShareDialog(emType, null, "").setVisible(true);
					break;
				case MODIFIY_USER:
				case DELETE_USER:
				case FINGERPRINT_OPEARTE_BY_USERID:
					UserData userData = showPanel.userShowPanel.GetSelectedItem();
					if(userData == null) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectPerson(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (emType == OPERATE_TYPE.FINGERPRINT_OPEARTE_BY_USERID) {
						new OperateByUserIdDialog(userData).setVisible(true);
					}else {
						new AttendanceOperateShareDialog(emType, userData).setVisible(true);
					}
					
					break;
				case FINGERPRINT_OPEARTE_BY_ID:
					new OperateByFingerPrintIdDialog().setVisible(true);
				default:
					break;
			}
		}
		
		private OPERATE_TYPE getOperateType(Object btn) {
			OPERATE_TYPE type = OPERATE_TYPE.UNKNOWN;
			
			if (btn == userPanel.searchPersonBtn) { // 查找人员
				type = OPERATE_TYPE.SEARCH_USER;
			}else if (btn == showPanel.userShowPanel.prePageBtn) { // 上一页查找人员
				type = OPERATE_TYPE.PRE_SEARCH_USER;
			}else if (btn == showPanel.userShowPanel.nextPageBtn) { // 下一页查找人员
				type = OPERATE_TYPE.NEXT_SEARCH_USER;
			}else if (btn == userPanel.addPersonBtn) { // 添加人员
				type = OPERATE_TYPE.ADD_USER;
			}else if (btn == userPanel.modifyPersonBtn) { // 修改人员
				type = OPERATE_TYPE.MODIFIY_USER;
			}else if (btn == userPanel.deletePersonBtn) {	// 删除人员
				type = OPERATE_TYPE.DELETE_USER;
			}else if (btn == userPanel.operateByUserIdBtn) {		// 通过用户ID操作信息
				type = OPERATE_TYPE.FINGERPRINT_OPEARTE_BY_USERID;
			}else if (btn == userPanel.operateByFingerPrintIdBtn) {	// 通过信息ID操作信息
				type = OPERATE_TYPE.FINGERPRINT_OPEARTE_BY_ID;
			}else {
				System.err.println("Unknown Event: " + btn);
			}
			
			return type;
			
		}
	}
	
	public class SearchRunnable implements Runnable {
		private OPERATE_TYPE searchType;
		public SearchRunnable(OPERATE_TYPE searchType) {
			this.searchType = searchType;
		}
		
		@Override
		public void run() {
			userPanel.searchPerson(searchType);
		}
	}
	
	/**
	 * 人员搜索工作线程（完成异步搜索）
	 */
	public class SearchPersonSwingWorker extends SwingWorker<UserData[], Object> {
		private AttendanceFunctionOperatePanel operatePanel;
		private int offset = 0;
		private OPERATE_TYPE type;
		public SearchPersonSwingWorker(OPERATE_TYPE type, AttendanceFunctionOperatePanel operatePanel) {
			this.operatePanel = operatePanel;
			this.type = type;
		}
		
		protected UserData[] doInBackground() throws Exception {

			switch(type) {
				case SEARCH_USER:
					offset = 0;
					break;
				case PRE_SEARCH_USER:
					offset = UserInfoShowPanel.QUERY_SHOW_COUNT * ((AttendanceShowPanel.userIndex-1)/UserInfoShowPanel.QUERY_SHOW_COUNT - 1);
					break;
				case NEXT_SEARCH_USER:
					offset = AttendanceShowPanel.userIndex;
					break;
				default:
					break;
			}
			
			UserData[] arrUserData = AttendanceModule.findUser(offset, UserInfoShowPanel.QUERY_SHOW_COUNT);
			
			return arrUserData;
		}
		
		@Override
		protected void done() {	
			if (bLogout) {
				return;
			}
			
			try {
				
				UserData[] arrUserData = get();
				if (arrUserData == null) {
					JOptionPane.showMessageDialog(null, Res.string().getFailed(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (type == OPERATE_TYPE.SEARCH_USER || 
						type == OPERATE_TYPE.PRE_SEARCH_USER) { // 更新userIndex
					AttendanceShowPanel.userIndex = offset;
				}
				operatePanel.insertData(arrUserData);				
			} catch (Exception e) {
//				e.printStackTrace();
			}finally {
				operatePanel.setSearchEnable(true);
			}
			
		}
	}
    
	private UserOperateActionListener listener;
	public FunctionOperatePanel userPanel;
	public SubscribePanel subscribePanel;
}
