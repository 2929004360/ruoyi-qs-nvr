package com.ruoyi.dahua.frame.Attendance;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.module.AttendanceModule;
import com.ruoyi.dahua.module.AttendanceModule.OPERATE_TYPE;
import com.ruoyi.dahua.module.AttendanceModule.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 考勤机操作对话框
 */
public class AttendanceOperateShareDialog extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OPERATE_TYPE emType = OPERATE_TYPE.UNKNOWN;					// 操作类型
	private boolean bSuccess = false;									// 接口调用结果
	
	public AttendanceOperateShareDialog(OPERATE_TYPE emType, UserData userData) { 
		this(emType, userData, "");
	}
	
	public AttendanceOperateShareDialog(OPERATE_TYPE emType, String fingerPrintId) { 
		this(emType, null, fingerPrintId);
	}
	
	public AttendanceOperateShareDialog(OPERATE_TYPE emType, UserData userData, String fingerPrintId) { 
		
		setTitle(Res.string().getPersonOperate());
		setLayout(new BorderLayout());
		setModal(true);
		pack();
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//////////人员信息面板  /////////////////
		
		JPanel personInfoPanel = new JPanel();
		BorderEx.set(personInfoPanel, "", 4);
		Dimension dimLable = new Dimension(80, 20);
		JLabel userIdLabel = new JLabel(Res.string().getUserId());
		JLabel userNameLabel = new JLabel(Res.string().getUserName(true));
		JLabel cardNoLabel = new JLabel(Res.string().getCardNo());
		JLabel	fingerPrintIdLabel = new JLabel(Res.string().getFingerPrintId());
		userIdLabel.setPreferredSize(dimLable);
		userNameLabel.setPreferredSize(dimLable);
		cardNoLabel.setPreferredSize(dimLable);
		fingerPrintIdLabel.setPreferredSize(new Dimension(85, 20));
		
		Dimension dimValue = new Dimension(150, 20);
		userIdTextField = new JTextField();
		userNameTextField = new JTextField();
		cardNoTextField = new JTextField();
		fingerPrintIdTextField = new JTextField();
		userIdTextField.setPreferredSize(dimValue);
		userNameTextField.setPreferredSize(dimValue);
		cardNoTextField.setPreferredSize(dimValue);
		fingerPrintIdTextField.setPreferredSize(dimValue);
		
		// 数据处理
		if (userData != null) {
			if (userData.userId != null) {
				userIdTextField.setText(userData.userId);
			}
			
			if (userData.userName != null) {
				userNameTextField.setText(userData.userName);
			}
			
			if (userData.cardNo != null) {
				cardNoTextField.setText(userData.cardNo);
			}
		}
		
		if (!fingerPrintId.isEmpty()) {
			fingerPrintIdTextField.setText(fingerPrintId);
		}
		
		if (emType == OPERATE_TYPE.DELETE_FINGERPRINT_BY_ID) { // 根据信息ID删除用户
			JPanel fingerPrintPanel = new JPanel();
			fingerPrintPanel.add(fingerPrintIdLabel);
			fingerPrintPanel.add(fingerPrintIdTextField);
			personInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 40));
			personInfoPanel.add(fingerPrintPanel);
		}else {
			personInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
			personInfoPanel.add(userIdLabel);
			personInfoPanel.add(userIdTextField);
			personInfoPanel.add(userNameLabel);
			personInfoPanel.add(userNameTextField);
			personInfoPanel.add(cardNoLabel);
			personInfoPanel.add(cardNoTextField);
			
			if (emType == OPERATE_TYPE.DELETE_FINGERPRINT_BY_USERID
					|| emType == OPERATE_TYPE.DELETE_USER) {
				JLabel promptLabel = new JLabel(" " + Res.string().getDeleteFingerPrintPrompt() + " ");
				promptLabel.setEnabled(false);
				personInfoPanel.add(promptLabel);
			}
		}
		
		//////////功能面板  /////////////////
		JPanel functionPanel = new JPanel();
		confirmBtn = new JButton(Res.string().getConfirm());
		cancelBtn = new JButton(Res.string().getCancel());
		confirmBtn.setPreferredSize(new Dimension(100, 20));
		cancelBtn.setPreferredSize(new Dimension(100, 20));
		
		functionPanel.add(confirmBtn);
		functionPanel.add(cancelBtn);
		
		add(personInfoPanel, BorderLayout.CENTER);
		add(functionPanel, BorderLayout.SOUTH);
		
		operateListener = new UserOperateListener();
		confirmBtn.addActionListener(operateListener);
		cancelBtn.addActionListener(operateListener);
		
		this.emType = emType;
		switch(emType) {
			case ADD_USER:
				setTitle(Res.string().getAddPerson());
				confirmBtn.setText(Res.string().getAdd());
				break;
			case MODIFIY_USER:
				setTitle(Res.string().getModifyPerson());
				confirmBtn.setText(Res.string().getModify());
				userIdTextField.setEnabled(false);
				break;
			case DELETE_USER:
				setTitle(Res.string().getDelPerson());
				confirmBtn.setText(Res.string().getDelete());
				userIdTextField.setEnabled(false);
				userNameTextField.setEnabled(false);
				cardNoTextField.setEnabled(false);
				break;
			case DELETE_FINGERPRINT_BY_USERID:
			case DELETE_FINGERPRINT_BY_ID:
				setTitle(Res.string().getDeleteFingerPrint());
				confirmBtn.setText(Res.string().getDelete());
				userIdTextField.setEnabled(false);
				userNameTextField.setEnabled(false);
				cardNoTextField.setEnabled(false);
				fingerPrintIdTextField.setEditable(false);
			default:
				break;
		}
	}
	
	public boolean checkDataValidity() {

		if (emType == OPERATE_TYPE.ADD_USER) {
			if (userIdTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, Res.string().getInput() + Res.string().getUserId(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
				return false;
			}
	
			try {
				if (userIdTextField.getText().getBytes("UTF-8").length > NetSDKLib.MAX_COMMON_STRING_32-1) {
					JOptionPane.showMessageDialog(null, Res.string().getUserIdExceedLength(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}catch (Exception e){
				
			}
		}
		
		try {
			if (userNameTextField.getText().getBytes("UTF-8").length > NetSDKLib.MAX_ATTENDANCE_USERNAME_LEN-1) {
				JOptionPane.showMessageDialog(null, Res.string().getUserNameExceedLength(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			if (cardNoTextField.getText().getBytes("UTF-8").length > NetSDKLib.MAX_COMMON_STRING_32-1) {
				JOptionPane.showMessageDialog(null, Res.string().getCardNoExceedLength(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}catch (Exception e){
			
		}
		
		
		return true;
	}
	
	public UserData getUserData() {
		UserData userData = new UserData();
		userData.cardNo = userIdTextField.getText();
		userData.userName = userNameTextField.getText();
		userData.cardNo = cardNoTextField.getText();
		return userData;
	}
	
	private class UserOperateListener implements ActionListener {				
		@Override
		public void actionPerformed(ActionEvent arg0) {
	
			if (arg0.getSource() == cancelBtn) {
				dispose();
			}else if (arg0.getSource() == confirmBtn) {
				switch(emType) {
					case ADD_USER:
						if (!checkDataValidity()) {
							return;
						}
						bSuccess = AttendanceModule.addUser(userIdTextField.getText(), userNameTextField.getText(), cardNoTextField.getText());
						break;
					case MODIFIY_USER:
						if (!checkDataValidity()) {
							return;
						}
						bSuccess = AttendanceModule.modifyUser(userIdTextField.getText(), userNameTextField.getText(), cardNoTextField.getText());
						break;
					case DELETE_USER:
						bSuccess = AttendanceModule.deleteUser(userIdTextField.getText());
						break;
					case DELETE_FINGERPRINT_BY_USERID:
						bSuccess = AttendanceModule.removeFingerByUserId(userIdTextField.getText());
						break;
					case DELETE_FINGERPRINT_BY_ID:
						bSuccess = AttendanceModule.removeFingerRecord(Integer.parseInt(fingerPrintIdTextField.getText()));
						break;
					default:
						System.err.println("Can't Deal Operate Type: " + emType);
						break;
				}
				
				if(bSuccess) {
					JOptionPane.showMessageDialog(null, Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, Res.string().getFailed(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
				}
				dispose();
			}else {
				System.err.println("Unknown Event: " + arg0.getSource());
			}
		}
	}
	
	private UserOperateListener operateListener;	// 按键监听
	private JTextField userIdTextField;				// 用户ID
	private JTextField userNameTextField;			// 用户名
	private JTextField cardNoTextField;				// 卡号
	private JTextField	fingerPrintIdTextField;		// 信息ID
	private JButton confirmBtn;						// 确认(根据emType类型变化)
	private JButton cancelBtn;						// 取消
}