package com.ruoyi.dahua.frame.TargetRecognition;

import com.ruoyi.dahua.common.DateChooserJButtonEx;
import com.ruoyi.dahua.common.FunctionList;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.NetSDKLib.NET_CTRL_SET_PARK_INFO;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.DotmatrixScreenModule;
import com.ruoyi.dahua.module.LoginModule;
import com.sun.jna.Pointer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
/**
 * 
 * @author 119178
 * 点阵屏下发demo
 */
public class LatticeScreen {

	
	// 设备断线通知回调
	private  DisConnect disConnect  = new DisConnect();
		
	// 网络连接恢复
	private  HaveReConnect haveReConnect = new HaveReConnect(); 
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	
	private DateChooserJButtonEx startTime;
	private DateChooserJButtonEx EndTime;
	private JPasswordField passwordField;
	
	private JButton btnNewButton ;
	private JButton btnNewButton_1 ;
	private JButton btnNewButton_2 ;
	private JTextField textField_3;
	private JTextField textField_10;
	private JTextField textField_11;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	LatticeScreen demo = new LatticeScreen();
                demo.frame.setVisible(true);
            }
        });
	}

	/**
	 * Create the application.
	 */
	public LatticeScreen() {
		LoginModule.init(disConnect, haveReConnect);   // 打开工程，初始化
		initialize();
		// 注册窗体清出事件
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                LoginModule.logout();    // 退出
                LoginModule.cleanup();   // 关闭工程，释放资源
                frame.dispose();
                // 返回主菜单
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        FunctionList demo = new FunctionList();
                        demo.setVisible(true);
                    }
                });
            }
        });
	}

/////////////////面板///////////////////
// 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
private  class DisConnect implements NetSDKLib.fDisConnect {
public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
// 断线提示
SwingUtilities.invokeLater(new Runnable() {
	public void run() {
		frame.setTitle(Res.string().getTargetRecognition() + " : " + Res.string().getDisConnectReconnecting());
	}
});
}
}

// 网络连接恢复，设备重连成功回调
// 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
private  class HaveReConnect implements NetSDKLib.fHaveReConnect {
public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);

// 重连提示
SwingUtilities.invokeLater(new Runnable() {
	public void run() {
		frame.setTitle(Res.string().getTargetRecognition() + " : " + Res.string().getOnline());
	}
});
}
}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle(Res.string().getmatrixScreen());
		frame.setBounds(200, 200, 807, 578);
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null); 
			try {
			            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			        } catch (Exception e) {
			            e.printStackTrace();
			        } 

		final JPanel panel1 = new JPanel();
		panel1.setBounds(10, 10, 100, 50);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Res.string().getLogin(), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 10, 771, 40);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(Res.string().getDeviceIp()+":");
		lblNewLabel.setBounds(26, 14, 54, 15);
		panel.add(lblNewLabel);
		
		textField = new JTextField("172.24.31.178");
		textField.setBounds(82, 11, 76, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel(Res.string().getPort()+":");
		lblNewLabel_1.setBounds(168, 14, 47, 15);
		panel.add(lblNewLabel_1);
		
		textField_1 = new JTextField("37777");
		textField_1.setBounds(217, 11, 76, 21);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel(Res.string().getUserName()+":");
		lblNewLabel_2.setBounds(302, 14, 47, 15);
		panel.add(lblNewLabel_2);
		
		textField_2 = new JTextField("admin");
		textField_2.setBounds(351, 11, 76, 21);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel(Res.string().getPassword()+":");
		lblNewLabel_3.setBounds(435, 14, 38, 15);
		panel.add(lblNewLabel_3);
		
		passwordField = new JPasswordField("admin123");
		passwordField.setBounds(476, 11, 89, 21);
		panel.add(passwordField);
		
		btnNewButton = new JButton(Res.string().getLogin());
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(LoginModule.login(textField.getText(), 
						Integer.parseInt(textField_1.getText()), 
						textField_2.getText(), 
						new String(passwordField.getPassword()))) {
					btnNewButton.setEnabled(false);
					btnNewButton_1.setEnabled(true);
					btnNewButton_2.setEnabled(true);
					
					textField.setEnabled(true);;
					textField_1.setEditable(true);;
					textField_2.setEnabled(true);
					textField_3.setEnabled(true);
					textField_4.setEnabled(true);
					textField_5.setEnabled(true);
					textField_6.setEnabled(true);
					textField_7.setEnabled(true);
					textField_8.setEnabled(true);
					textField_9.setEnabled(true);
					textField_10.setEnabled(true);
					textField_11.setEnabled(true);
					comboBox.setEnabled(true);
					comboBox_1.setEnabled(true);
					rdbtnNewRadioButton.setEnabled(true);
					rdbtnNewRadioButton_1.setEnabled(true);
					startTime.setEnabled(true);
					EndTime.setEnabled(true);
					passwordField.setEnabled(true);
				}else{
					JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(584, 10, 76, 23);
		panel.add(btnNewButton);
		btnNewButton.setEnabled(true);
		
	    btnNewButton_1 = new JButton(Res.string().getLogout());
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginModule.logout();
				btnNewButton.setEnabled(true);
				btnNewButton_1.setEnabled(false);
				btnNewButton_2.setEnabled(false);
				
				textField.setEnabled(false);;
				textField_1.setEditable(false);;
				textField_2.setEnabled(false);
				textField_3.setEnabled(false);
				textField_4.setEnabled(false);
				textField_5.setEnabled(false);
				textField_6.setEnabled(false);
				textField_7.setEnabled(false);
				textField_8.setEnabled(false);
				textField_9.setEnabled(false);
				textField_10.setEnabled(false);
				textField_11.setEnabled(false);
				comboBox.setEnabled(false);
				comboBox_1.setEditable(false);
				rdbtnNewRadioButton.setEnabled(false);
				rdbtnNewRadioButton_1.setEnabled(false);
				startTime.setEnabled(true);
				EndTime.setEnabled(true);
				passwordField.setEnabled(true);
			}
		});
		btnNewButton_1.setBounds(678, 10, 76, 23);
		panel.add(btnNewButton_1);
		btnNewButton_1.setEnabled(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 56, 771, 474);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel(Res.string().getPassingState()+":");
		lblNewLabel_4.setBounds(71, 26, 95, 21);
		panel_1.add(lblNewLabel_4);
		
		comboBox = new JComboBox();
		comboBox.setEnabled(false);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {Res.string().getPassingCar(), Res.string().getNoCar()}));
		comboBox.setBounds(176, 26, 131, 21);
		panel_1.add(comboBox);
		
		JLabel lblNewLabel_5 = new JLabel(Res.string().getInTime()+":");
		lblNewLabel_5.setBounds(391, 29, 102, 15);
		panel_1.add(lblNewLabel_5);
		
		startTime=new DateChooserJButtonEx();
		startTime.setEnabled(false);
		startTime.setBounds(503, 26, 131, 21);
		panel_1.add(startTime);
		
		JLabel lblNewLabel_6 = new JLabel(Res.string().getOutTime()+":");
		lblNewLabel_6.setBounds(391, 84, 102, 15);
		panel_1.add(lblNewLabel_6);
		
		EndTime=new DateChooserJButtonEx();
		EndTime.setEnabled(false);
		EndTime.setBounds(503, 81, 131, 21);
		panel_1.add(EndTime);
		
		JLabel lblNewLabel_7 = new JLabel(Res.string().getPlateNumber()+":");
		lblNewLabel_7.setBounds(71, 84, 95, 15);
		panel_1.add(lblNewLabel_7);
		
		textField_4 = new JTextField();
		textField_4.setEnabled(false);
		textField_4.setBounds(176, 81, 131, 21);
		panel_1.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel(Res.string().getCarOwner()+":");
		lblNewLabel_8.setBounds(77, 142, 89, 15);
		panel_1.add(lblNewLabel_8);
		
		textField_5 = new JTextField();
		textField_5.setEnabled(false);
		textField_5.setBounds(176, 139, 131, 21);
		panel_1.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel(Res.string().getParkingTime()+":");
		lblNewLabel_9.setBounds(391, 133, 102, 15);
		panel_1.add(lblNewLabel_9);
		
		textField_6 = new JTextField();
		textField_6.setEnabled(false);
		textField_6.setBounds(503, 130, 131, 21);
		panel_1.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel(Res.string().getUserType()+":");
		lblNewLabel_10.setBounds(71, 198, 95, 15);
		panel_1.add(lblNewLabel_10);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setEnabled(false);
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {Res.string().getMonthlyCardUser()
				,Res.string().getAnnualCardUser(), Res.string().getLongTermUser(), Res.string().getTemporaryUser()}));
		comboBox_1.setBounds(176, 195, 131, 21);
		panel_1.add(comboBox_1);
		
		JLabel lblNewLabel_11 = new JLabel(Res.string().getParkingCharge());
		lblNewLabel_11.setBounds(391, 178, 102, 15);
		panel_1.add(lblNewLabel_11);
		
		textField_7 = new JTextField();
		textField_7.setEnabled(false);
		textField_7.setBounds(503, 175, 131, 21);
		panel_1.add(textField_7);
		textField_7.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel(Res.string().getDaysDue());
		lblNewLabel_12.setBounds(71, 253, 95, 15);
		panel_1.add(lblNewLabel_12);
		
		textField_8 = new JTextField();
		textField_8.setEnabled(false);
		textField_8.setBounds(176, 250, 131, 21);
		panel_1.add(textField_8);
		textField_8.setColumns(10);
		
		JLabel lblNewLabel_13 = new JLabel(Res.string().getRemainingParkingSpaces());
		lblNewLabel_13.setBounds(391, 215, 102, 15);
		panel_1.add(lblNewLabel_13);
		
		textField_9 = new JTextField();
		textField_9.setEnabled(false);
		textField_9.setBounds(503, 212, 131, 21);
		panel_1.add(textField_9);
		textField_9.setColumns(10);
		
		rdbtnNewRadioButton = new JRadioButton(Res.string().getVehiclesNotAllowedToPass());
		rdbtnNewRadioButton.setEnabled(false);
		rdbtnNewRadioButton.setBounds(367, 249, 155, 23);
		panel_1.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton(Res.string().getAllowedVehiclesToPass());
		rdbtnNewRadioButton_1.setEnabled(false);
		rdbtnNewRadioButton_1.setBounds(524, 249, 162, 23);
		panel_1.add(rdbtnNewRadioButton_1);
		
		ButtonGroup group=new ButtonGroup();
		group.add(rdbtnNewRadioButton);
		group.add(rdbtnNewRadioButton_1);
		
		btnNewButton_2 = new JButton(Res.string().getSetUp());
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int emType=NetSDKLib.CtrlType.CTRLTYPE_CTRL_SET_PARK_INFO;
				
				NET_CTRL_SET_PARK_INFO msg=new NET_CTRL_SET_PARK_INFO();

				ToolKits.StringToByteArray(textField_4.getText(), msg.szPlateNumber);
				
				if(!textField_6.getText().equals("")) {
				msg.nParkTime=Integer.parseInt(textField_6.getText());
				}else {
					msg.nParkTime=0;
				}

				ToolKits.StringToByteArray(textField_5.getText(), msg.szMasterofCar);
				
				if(comboBox_1.getSelectedItem().equals(Res.string().getMonthlyCardUser())) {
					ToolKits.StringToByteArray("monthlyCardUser", msg.szUserType);
				}
				else if(comboBox_1.getSelectedItem().equals(Res.string().getAnnualCardUser())){
					ToolKits.StringToByteArray("yearlyCardUser", msg.szUserType);
				}
				else if(comboBox_1.getSelectedItem().equals(Res.string().getLongTermUser())){
					ToolKits.StringToByteArray("longTimeUser", msg.szUserType);
				}
				else if(comboBox_1.getSelectedItem().equals(Res.string().getTemporaryUser())){
					ToolKits.StringToByteArray("casualUser", msg.szUserType);
				}
				
				if(!textField_8.getText().equals("")) {
				msg.nRemainDay=Integer.parseInt(textField_8.getText());
				}else {
					msg.nRemainDay=0;
				}
								
				if(rdbtnNewRadioButton.isSelected()) {
					msg.nPassEnable=0;
					}else if(rdbtnNewRadioButton_1.isSelected()){
						msg.nPassEnable=1;
					}
				String InTime=startTime.getText().toString();// 车辆入场时间
				String[] InTimes = InTime.split("-");
				
				msg.stuInTime.dwYear = (short)Integer.parseInt(InTimes[0]);
				msg.stuInTime.dwMonth = (byte)Integer.parseInt(InTimes[1]);
				msg.stuInTime.dwDay = (byte)Integer.parseInt(InTimes[2]);
				
				String OutTime=EndTime.getText().toString();// 车辆出场时间
				String[] OutTimes = OutTime.split("-");
				
				msg.stuOutTime.dwYear = (short)Integer.parseInt(OutTimes[0]);
				msg.stuOutTime.dwMonth = (byte)Integer.parseInt(OutTimes[1]);
				msg.stuOutTime.dwDay = (byte)Integer.parseInt(OutTimes[2]);
				
				//msg.stuInTime.setTime(2019, 7, 9, 10, 45, 0); 	// 车辆入场时间
				//msg.stuOutTime.setTime(2019, 7, 9, 10, 52, 0); // 车辆出场时间
				try {
					System.arraycopy(textField_7.getText().getBytes("GBK"), 0,msg.szParkCharge ,0, textField_7.getText().getBytes("GBK").length);
				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				if(!textField_9.getText().equals("")) {
					msg.nRemainSpace=Integer.parseInt(textField_9.getText());
					}else {
						msg.nRemainSpace=0;
					}
				
				/*
				 * if(rdbtnNewRadioButton.isSelected()) { msg.nPassEnable=1; }else
				 * if(rdbtnNewRadioButton_1.isSelected()){ msg.nPassEnable=0; }
				 */
				if(comboBox.getSelectedItem().equals(Res.string().getPassingCar())) {
					msg.emCarStatus=1;
				}
				else if(comboBox.getSelectedItem().equals(Res.string().getNoCar())){
					msg.emCarStatus=2;
				}
				
				try {
					System.arraycopy(textField_3.getText().getBytes("GBK"), 0,msg.szSubUserType ,0, textField_3.getText().getBytes("GBK").length);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					System.arraycopy(textField_10.getText().getBytes("GBK"), 0,msg.szRemarks ,0, textField_10.getText().getBytes("GBK").length);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					System.arraycopy(textField_11.getText().getBytes("GBK"), 0,msg.szCustom ,0, textField_11.getText().getBytes("GBK").length);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				msg.write();
				boolean ret=DotmatrixScreenModule.setDotmatrixScreen(emType, msg);
				if(ret) {
					JOptionPane.showMessageDialog(panel1, Res.string().getSetUpSuccess());
				}else {
					JOptionPane.showMessageDialog(panel1, Res.string().getSetUpFailed());
				}
			}
		});
		btnNewButton_2.setBounds(618, 441, 93, 23);
		panel_1.add(btnNewButton_2);
		btnNewButton_2.setEnabled(false);	
		
		JLabel label = new JLabel(Res.string().getCostomUserInfo()+":");
		label.setBounds(58, 309, 119, 21);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel(Res.string().getRemarksInfo()+":");
		label_1.setBounds(58, 350, 97, 21);
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel(Res.string().getCostomInfo()+":");
		label_2.setBounds(60, 394, 95, 21);
		panel_1.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		textField_3.setColumns(10);
		textField_3.setBounds(187, 306, 460, 28);
		panel_1.add(textField_3);
		
		textField_10 = new JTextField();
		textField_10.setEnabled(false);
		textField_10.setColumns(10);
		textField_10.setBounds(187, 347, 460, 28);
		panel_1.add(textField_10);
		
		textField_11 = new JTextField();
		textField_11.setEnabled(false);
		textField_11.setColumns(10);
		textField_11.setBounds(187, 391, 460, 28);
		panel_1.add(textField_11);
	}
}
