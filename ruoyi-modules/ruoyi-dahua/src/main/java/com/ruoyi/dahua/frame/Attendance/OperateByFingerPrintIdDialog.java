package com.ruoyi.dahua.frame.Attendance;

import com.ruoyi.dahua.common.Base64;
import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.module.AttendanceModule;
import com.ruoyi.dahua.module.AttendanceModule.OPERATE_TYPE;
import com.ruoyi.dahua.module.AttendanceModule.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 通过信息ID操作信息对话框
 */
public class OperateByFingerPrintIdDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OperateByFingerPrintIdDialog() {
		setTitle(Res.string().getOperateByFingerPrintId());
		setLayout(new BorderLayout());
		setModal(true);
		pack();
		setSize(600, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		////////// 查询条件 /////////////////
		JLabel fingerPrintIdLabel = new JLabel(Res.string().getFingerPrintId(), JLabel.CENTER);
		fingerPrintIdTextField = new JTextField();
		fingerPrintIdLabel.setPreferredSize(new Dimension(85, 20));
		fingerPrintIdTextField.setPreferredSize(new Dimension(100, 20));
		
		////////// 信息功能 /////////////////
		searchFingerPrintBtn = new JButton(Res.string().getSearchFingerPrint());
		deleteFingerPrintBtn = new JButton(Res.string().getDeleteFingerPrint());
		
		searchFingerPrintBtn.setPreferredSize(new Dimension(140, 20));
		deleteFingerPrintBtn.setPreferredSize(new Dimension(140, 20));

		JPanel functionPanel = new JPanel();
		BorderEx.set(functionPanel, Res.string().getOperateByFingerPrintId(), 1);
		functionPanel.add(fingerPrintIdLabel);
		functionPanel.add(fingerPrintIdTextField);
		functionPanel.add(searchFingerPrintBtn);
		functionPanel.add(deleteFingerPrintBtn);
		
		//////////信息信息 /////////////////
		JPanel fingerPrintPanel = new JPanel();
		BorderEx.set(fingerPrintPanel, Res.string().getFingerPrintInfo(), 1);
		fingerPrintPanel.setLayout(null);
		JLabel userIdLabel = new JLabel(Res.string().getUserId());
		userId = new JLabel();
		JLabel fingerPrintDataLabel = new JLabel(Res.string().getFingerPrintData());
		fingerPrintData = new JTextArea();
		fingerPrintData.setBackground(null);
		fingerPrintData.setEditable(false);
		fingerPrintData.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(fingerPrintData);
		userIdLabel.setBounds(30, 30, 90, 20);
		userId.setBounds(150, 30, 300, 20);
		fingerPrintDataLabel.setBounds(30, 60, 150, 20);
		fingerPrintData.setBounds(30, 80, 600, 20);
		scrollPane.setBounds(30, 80, 550, 300);
		scrollPane.setBorder(null);
		
		fingerPrintPanel.add(userIdLabel);
		fingerPrintPanel.add(userId);
		fingerPrintPanel.add(fingerPrintDataLabel);
		fingerPrintPanel.add(scrollPane);
		
		add(functionPanel, BorderLayout.NORTH);
		add(fingerPrintPanel, BorderLayout.CENTER);
		
		fingerPrintIdTextField.addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
				  int key = e.getKeyChar();
				  if (key < 48 || key > 57) {
					  e.consume();
				  }
			}

			public void keyPressed(KeyEvent e) {}
			
			public void keyReleased(KeyEvent e) {}
		});
		
		listener = new FingerPrintIdOperateActionListener();
		searchFingerPrintBtn.addActionListener(listener);
		deleteFingerPrintBtn.addActionListener(listener);
	}
	
	public String getFingerPrintId() {
		
		if (fingerPrintIdTextField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, Res.string().getInput()+Res.string().getFingerPrintId(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		try {
			Integer.parseInt(fingerPrintIdTextField.getText());
		}catch (NumberFormatException e){
			JOptionPane.showMessageDialog(null, Res.string().getFingerPrintIdIllegal(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return fingerPrintIdTextField.getText();
	}
	
	public void searchFingerPrint() {
		clearFingerPrintInfo();
		
		String fingerPrintId = getFingerPrintId();
		if (fingerPrintId == null) {
			return;
		}
		UserData userData = AttendanceModule.getFingerRecord(Integer.parseInt(fingerPrintId));
		if (userData == null) {
			JOptionPane.showMessageDialog(null, Res.string().getFailed(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (userData.szFingerPrintInfo[0].length == 0) {
			JOptionPane.showMessageDialog(null, Res.string().getFingerPrintIdNotExist(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		dealFingerPrintInfo(userData);
	}
	
	public void dealFingerPrintInfo(UserData userData) {
		userId.setText(userData.userId);
		fingerPrintData.setText(formatFingerPrintData(userData.szFingerPrintInfo[0]));
	}
	
	private String formatFingerPrintData(byte[] fingerPrintData) {
		String formatData = Base64.getEncoder().encodeToString(fingerPrintData);
		return formatData;
	}
	
	public void clearFingerPrintInfo() {
		userId.setText("");
		fingerPrintData.setText("");
	}
	
	/**
	 * 按键监听实现类
	 */
	private class FingerPrintIdOperateActionListener implements ActionListener {	

		@Override
		public void actionPerformed(ActionEvent arg0) {

			OPERATE_TYPE emType = getOperateType(arg0.getSource());
			switch(emType) {
				case SEARCH_FINGERPRINT_BY_ID:
					searchFingerPrint();
					break;
				case DELETE_FINGERPRINT_BY_ID:
					String fingerPrintId = getFingerPrintId();
					if (fingerPrintId == null) {
						return;
					}
					new AttendanceOperateShareDialog(emType, fingerPrintId).setVisible(true);
					break;
				default:
					break;
			}
		}
		
		private OPERATE_TYPE getOperateType(Object btn) {
			OPERATE_TYPE type = OPERATE_TYPE.UNKNOWN;
			if (btn == searchFingerPrintBtn) { // 查找信息
				type = OPERATE_TYPE.SEARCH_FINGERPRINT_BY_ID;
			}else if (btn == deleteFingerPrintBtn) {	// 删除信息
				type = OPERATE_TYPE.DELETE_FINGERPRINT_BY_ID;
			}else {
				System.err.println("Unknown Event: " + btn);
			}
			
			return type;
			
		}
	}
	
	private JTextField fingerPrintIdTextField;
	public JButton searchFingerPrintBtn;
	private JButton deleteFingerPrintBtn;
	private JLabel userId;
	private JTextArea fingerPrintData;
	private FingerPrintIdOperateActionListener listener;
}