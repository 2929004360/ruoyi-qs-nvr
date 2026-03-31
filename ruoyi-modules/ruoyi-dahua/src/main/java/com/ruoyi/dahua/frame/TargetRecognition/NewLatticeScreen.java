package com.ruoyi.dahua.frame.TargetRecognition;

import com.ruoyi.dahua.common.DateChooserJButtonEx;
import com.ruoyi.dahua.common.FunctionList;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.CtrlType;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.lib.structure.NET_IN_SET_PARK_CONTROL_INFO;
import com.ruoyi.dahua.lib.structure.NET_OUT_SET_PARK_CONTROL_INFO;
import com.ruoyi.dahua.module.DotmatrixScreenModule;
import com.ruoyi.dahua.module.LoginModule;
import com.sun.jna.Pointer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 119178 点阵屏下发demo
 */
public class NewLatticeScreen {

    // 设备断线通知回调
    private DisConnect disConnect = new DisConnect();

    // 网络连接恢复
    private HaveReConnect haveReConnect = new HaveReConnect();
    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JPasswordField passwordField;

    private JButton btnNewButton;
    private JButton btnNewButton_1;
    private JTabbedPane tabbedPane;
    private JPanel panel_2;
    private JLabel label_CarStatus;
    private JComboBox comboBox_CarStatus;
    private JLabel label_InTime;
    private DateChooserJButtonEx dateChooser_InTime;
    private JLabel label_OutTime;
    private DateChooserJButtonEx dateChooser_OutTime;
    private JLabel label_PlateNmuber;
    private JTextField textField_PlateNumber;
    private JLabel label_CarOwner;
    private JTextField textField_CarOwner;
    private JLabel label_ParkingTime;
    private JTextField textField_ParkingTime;
    private JLabel label_UserType;
    private JComboBox comboBox_UserType;
    private JLabel label_ParkingCharge;
    private JTextField textField_ParkingCharge;
    private JLabel label_DaysDue;
    private JTextField textField_DaysDue;
    private JLabel label_RemainParkingSpaces;
    private JTextField textField_RemainParkingSpaces;
    private JRadioButton radioButton_Allow2Pass;
    private JRadioButton radioButton_Deny2Pass;
    private JButton button;
    private JLabel label_CustomerUserInfo;
    private JLabel label_RemarksInfo;
    private JLabel label_CustomerInfo;
    private JTextField textField_CustomerUserInfo;
    private JTextField textField_RemarksInfo;
    private JTextField textField_CustomerInfo;
    private JPanel panel_1;
    private JScrollPane scrollPane;
    private Object[][] ScreenTableData;
    private Object[][] VideoTableData;
    private JTable ScreenTable;
    private JTable VideoTable;
    private JPanel ScreenInfo;

    private final String[] ScreenTableTitle = {Res.string().getScreenNumber(), Res.string().getContain(),
            Res.string().getContainType(), Res.string().getContainColor(), Res.string().getScrollType(),
            Res.string().getScrollSpeed()};

    private final String[] VideoTableTitle = {Res.string().getScreenNumber(), Res.string().getContain(),
            Res.string().getContainType()};

    private DefaultTableModel Model;
    private DefaultTableModel VideoModel;
    private JLabel label_17;

    private JComboBox comboBox_8;
    private JComboBox comboBox_4;
    private JComboBox comboBox_5;
    private JComboBox comboBox_6;
    private JComboBox comboBox_7;
    private JButton btnNewButton_3;
    private JButton btnNewButton_4;
    private JButton btnNewButton_5;
    private JTextField textField_21;
    private JTextField textField_22;
    private JTextField textField_23;
    private JButton button_1;
    private JButton button_2;
    private JButton button_4;
    private JButton btnNewButton_6;

    private JLabel label_19;

    // private static NET_IN_SET_PARK_CONTROL_INFO pIn = new
    // NET_IN_SET_PARK_CONTROL_INFO();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                NewLatticeScreen demo = new NewLatticeScreen();
                demo.frame.setVisible(true);
            }
        });
    }

    /**
     * Create the application.
     */
    public NewLatticeScreen() {
        LoginModule.init(disConnect, haveReConnect); // 打开工程，初始化
        initialize();
        // 注册窗体清出事件
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                LoginModule.logout(); // 退出
                LoginModule.cleanup(); // 关闭工程，释放资源
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
    private class DisConnect implements NetSDKLib.fDisConnect {
        public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
// 断线提示
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    frame.setTitle(
                            Res.string().getTargetRecognition() + " : " + Res.string().getDisConnectReconnecting());
                }
            });
        }
    }

    // 网络连接恢复，设备重连成功回调
// 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
    private class HaveReConnect implements NetSDKLib.fHaveReConnect {
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
        // frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final JPanel panel1 = new JPanel();
        panel1.setBounds(10, 10, 100, 50);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Res.string().getLogin(),
                TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setBounds(10, 10, 771, 40);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel(Res.string().getDeviceIp() + ":");
        lblNewLabel.setBounds(26, 14, 54, 15);
        panel.add(lblNewLabel);

        textField = new JTextField("172.23.11.180");
        textField.setBounds(82, 11, 76, 21);
        panel.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel(Res.string().getPort() + ":");
        lblNewLabel_1.setBounds(168, 14, 47, 15);
        panel.add(lblNewLabel_1);

        textField_1 = new JTextField("37777");
        textField_1.setBounds(217, 11, 76, 21);
        panel.add(textField_1);
        textField_1.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel(Res.string().getUserName() + ":");
        lblNewLabel_2.setBounds(302, 14, 47, 15);
        panel.add(lblNewLabel_2);

        textField_2 = new JTextField("admin");
        textField_2.setBounds(351, 11, 76, 21);
        panel.add(textField_2);
        textField_2.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel(Res.string().getPassword() + ":");
        lblNewLabel_3.setBounds(435, 14, 38, 15);
        panel.add(lblNewLabel_3);

        passwordField = new JPasswordField("admin123");
        passwordField.setBounds(476, 11, 89, 21);
        panel.add(passwordField);

        btnNewButton = new JButton(Res.string().getLogin());
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (LoginModule.login(textField.getText(), Integer.parseInt(textField_1.getText()),
                        textField_2.getText(), new String(passwordField.getPassword()))) {
                    btnNewButton.setEnabled(false);
                    btnNewButton_1.setEnabled(true);
                    button.setEnabled(true);

                    comboBox_8.setEnabled(true);
                    ;
                    comboBox_4.setEnabled(true);
                    comboBox_5.setEnabled(true);
                    comboBox_6.setEnabled(true);
                    comboBox_7.setEnabled(true);
                    btnNewButton_3.setEnabled(true);
                    btnNewButton_4.setEnabled(true);
                    btnNewButton_5.setEnabled(true);
                    textField_21.setEnabled(true);
                    textField_22.setEnabled(true);
                    textField_23.setEnabled(true);
                    button_1.setEnabled(true);
                    button_2.setEnabled(true);
                    button_4.setEnabled(true);
                    btnNewButton_6.setEnabled(true);

                    textField.setEnabled(true);
                    ;
                    textField_1.setEditable(true);
                    ;
                    textField_2.setEnabled(true);
                    textField_CustomerInfo.setEnabled(true);
                    textField_RemarksInfo.setEnabled(true);
                    textField_CustomerUserInfo.setEnabled(true);
                    textField_RemainParkingSpaces.setEnabled(true);
                    textField_DaysDue.setEnabled(true);
                    textField_ParkingCharge.setEnabled(true);
                    textField_ParkingTime.setEnabled(true);
                    textField_CarOwner.setEnabled(true);
                    textField_PlateNumber.setEnabled(true);
                    comboBox_UserType.setEnabled(true);
                    comboBox_CarStatus.setEnabled(true);
                    radioButton_Allow2Pass.setEnabled(true);
                    radioButton_Deny2Pass.setEnabled(true);
                    dateChooser_InTime.setEnabled(true);
                    dateChooser_OutTime.setEnabled(true);
                    passwordField.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null,
                            Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(),
                            Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
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
                button.setEnabled(false);

                comboBox_8.setEnabled(false);
                ;
                comboBox_4.setEnabled(false);
                comboBox_5.setEnabled(false);
                comboBox_6.setEnabled(false);
                comboBox_7.setEnabled(false);
                btnNewButton_3.setEnabled(false);
                btnNewButton_4.setEnabled(false);
                btnNewButton_5.setEnabled(false);
                textField_21.setEnabled(false);
                textField_22.setEnabled(false);
                textField_23.setEnabled(false);
                button_1.setEnabled(false);
                button_2.setEnabled(false);
                button_4.setEnabled(false);
                btnNewButton_6.setEnabled(false);

                textField.setEnabled(false);
                ;
                textField_1.setEditable(false);
                ;
                textField_2.setEnabled(false);
                textField_CustomerInfo.setEnabled(false);
                textField_RemarksInfo.setEnabled(false);
                textField_CustomerUserInfo.setEnabled(false);
                textField_RemainParkingSpaces.setEnabled(false);
                textField_DaysDue.setEnabled(false);
                textField_ParkingCharge.setEnabled(false);
                textField_ParkingTime.setEnabled(false);
                textField_CarOwner.setEnabled(false);
                textField_PlateNumber.setEnabled(false);
                comboBox_UserType.setEnabled(false);
                comboBox_CarStatus.setEnabled(false);
                radioButton_Allow2Pass.setEnabled(false);
                radioButton_Deny2Pass.setEnabled(false);
                dateChooser_InTime.setEnabled(false);
                dateChooser_OutTime.setEnabled(false);
                passwordField.setEnabled(true);
            }
        });
        btnNewButton_1.setBounds(678, 10, 76, 23);
        panel.add(btnNewButton_1);
        btnNewButton_1.setEnabled(false);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 60, 771, 470);
        frame.getContentPane().add(tabbedPane);

        panel_2 = new JPanel();
        panel_2.setLayout(null);
        panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        tabbedPane.addTab("part1", null, panel_2, null);

        label_CarStatus = new JLabel(Res.string().getPassingState() + ":");
        label_CarStatus.setBounds(71, 26, 95, 21);
        panel_2.add(label_CarStatus);

        comboBox_CarStatus = new JComboBox();
        comboBox_CarStatus.setEnabled(false);
        comboBox_CarStatus.setModel(
                new DefaultComboBoxModel(new String[]{Res.string().getPassingCar(), Res.string().getNoCar()}));
        comboBox_CarStatus.setBounds(176, 26, 131, 21);
        comboBox_CarStatus.setSelectedIndex(0);
        panel_2.add(comboBox_CarStatus);

        label_InTime = new JLabel(Res.string().getInTime() + ":");
        label_InTime.setBounds(391, 29, 102, 15);
        panel_2.add(label_InTime);

        dateChooser_InTime = new DateChooserJButtonEx();
        dateChooser_InTime.setEnabled(false);
        dateChooser_InTime.setBounds(503, 26, 131, 21);
        panel_2.add(dateChooser_InTime);

        label_OutTime = new JLabel(Res.string().getOutTime() + ":");
        label_OutTime.setBounds(391, 84, 102, 15);
        panel_2.add(label_OutTime);

        dateChooser_OutTime = new DateChooserJButtonEx();
        dateChooser_OutTime.setEnabled(false);
        dateChooser_OutTime.setBounds(503, 81, 131, 21);
        panel_2.add(dateChooser_OutTime);

        label_PlateNmuber = new JLabel(Res.string().getPlateNumber() + ":");
        label_PlateNmuber.setBounds(71, 84, 95, 15);
        panel_2.add(label_PlateNmuber);

        textField_PlateNumber = new JTextField();
        textField_PlateNumber.setEnabled(false);
        textField_PlateNumber.setColumns(10);
        textField_PlateNumber.setBounds(176, 81, 131, 21);
        panel_2.add(textField_PlateNumber);

        label_CarOwner = new JLabel(Res.string().getCarOwner() + ":");
        label_CarOwner.setBounds(77, 142, 89, 15);
        panel_2.add(label_CarOwner);

        textField_CarOwner = new JTextField();
        textField_CarOwner.setEnabled(false);
        textField_CarOwner.setColumns(10);
        textField_CarOwner.setBounds(176, 139, 131, 21);
        panel_2.add(textField_CarOwner);

        label_ParkingTime = new JLabel(Res.string().getParkingTime() + ":");
        label_ParkingTime.setBounds(391, 133, 102, 15);
        panel_2.add(label_ParkingTime);

        textField_ParkingTime = new JTextField();
        textField_ParkingTime.setEnabled(false);
        textField_ParkingTime.setColumns(10);
        textField_ParkingTime.setBounds(503, 130, 131, 21);
        panel_2.add(textField_ParkingTime);

        label_UserType = new JLabel(Res.string().getUserType() + ":");
        label_UserType.setBounds(71, 198, 95, 15);
        panel_2.add(label_UserType);

        comboBox_UserType = new JComboBox();
        comboBox_UserType.setEnabled(false);
        comboBox_UserType.setModel(new DefaultComboBoxModel(new String[]{Res.string().getMonthlyCardUser(),
                Res.string().getAnnualCardUser(), Res.string().getLongTermUser(), Res.string().getTemporaryUser()}));
        comboBox_UserType.setBounds(176, 195, 131, 21);
        comboBox_UserType.setSelectedIndex(0);
        panel_2.add(comboBox_UserType);

        label_ParkingCharge = new JLabel(Res.string().getParkingCharge());
        label_ParkingCharge.setBounds(391, 178, 102, 15);
        panel_2.add(label_ParkingCharge);

        textField_ParkingCharge = new JTextField();
        textField_ParkingCharge.setEnabled(false);
        textField_ParkingCharge.setColumns(10);
        textField_ParkingCharge.setBounds(503, 175, 131, 21);
        panel_2.add(textField_ParkingCharge);

        label_DaysDue = new JLabel(Res.string().getDaysDue());
        label_DaysDue.setBounds(71, 253, 95, 15);
        panel_2.add(label_DaysDue);

        textField_DaysDue = new JTextField();
        textField_DaysDue.setEnabled(false);
        textField_DaysDue.setColumns(10);
        textField_DaysDue.setBounds(176, 250, 131, 21);
        panel_2.add(textField_DaysDue);

        label_RemainParkingSpaces = new JLabel(Res.string().getRemainingParkingSpaces());
        label_RemainParkingSpaces.setBounds(391, 215, 102, 15);
        panel_2.add(label_RemainParkingSpaces);

        textField_RemainParkingSpaces = new JTextField();
        textField_RemainParkingSpaces.setEnabled(false);
        textField_RemainParkingSpaces.setColumns(10);
        textField_RemainParkingSpaces.setBounds(503, 212, 131, 21);
        panel_2.add(textField_RemainParkingSpaces);

        radioButton_Deny2Pass = new JRadioButton(Res.string().getVehiclesNotAllowedToPass());
        radioButton_Deny2Pass.setEnabled(false);
        radioButton_Deny2Pass.setBounds(367, 249, 155, 23);
        radioButton_Deny2Pass.setSelected(true);
        panel_2.add(radioButton_Deny2Pass);

        radioButton_Allow2Pass = new JRadioButton(Res.string().getAllowedVehiclesToPass());
        radioButton_Allow2Pass.setEnabled(false);
        radioButton_Allow2Pass.setBounds(524, 249, 162, 23);
        panel_2.add(radioButton_Allow2Pass);

        ButtonGroup group = new ButtonGroup();
        group.add(radioButton_Allow2Pass);
        group.add(radioButton_Deny2Pass);

        button = new JButton(Res.string().getSetUp());
        button.setEnabled(false);
        button.setBounds(633, 408, 93, 23);
        panel_2.add(button);

        label_CustomerUserInfo = new JLabel(Res.string().getCostomUserInfo() + ":");
        label_CustomerUserInfo.setBounds(58, 290, 119, 21);
        panel_2.add(label_CustomerUserInfo);

        label_RemarksInfo = new JLabel(Res.string().getRemarksInfo() + ":");
        label_RemarksInfo.setBounds(58, 332, 97, 21);
        panel_2.add(label_RemarksInfo);

        label_CustomerInfo = new JLabel(Res.string().getCostomInfo() + ":");
        label_CustomerInfo.setBounds(58, 375, 95, 21);
        panel_2.add(label_CustomerInfo);

        textField_CustomerUserInfo = new JTextField();
        textField_CustomerUserInfo.setEnabled(false);
        textField_CustomerUserInfo.setColumns(10);
        textField_CustomerUserInfo.setBounds(176, 287, 460, 28);
        panel_2.add(textField_CustomerUserInfo);

        textField_RemarksInfo = new JTextField();
        textField_RemarksInfo.setEnabled(false);
        textField_RemarksInfo.setColumns(10);
        textField_RemarksInfo.setBounds(176, 329, 460, 28);
        panel_2.add(textField_RemarksInfo);

        textField_CustomerInfo = new JTextField();
        textField_CustomerInfo.setEnabled(false);
        textField_CustomerInfo.setColumns(10);
        textField_CustomerInfo.setBounds(176, 372, 460, 28);
        panel_2.add(textField_CustomerInfo);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int emType = CtrlType.CTRLTYPE_CTRL_SET_PARK_INFO;

                NetSDKLib.NET_CTRL_SET_PARK_INFO msg = new NetSDKLib.NET_CTRL_SET_PARK_INFO();

                try {
                    byte[] plateNumber = textField_PlateNumber.getText().getBytes("GBK");
                    System.arraycopy(plateNumber, 0, msg.szPlateNumber, 0, plateNumber.length);
                } catch (UnsupportedEncodingException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }

                if (!textField_ParkingTime.getText().equals("")) {
                    msg.nParkTime = Integer.parseInt(textField_ParkingTime.getText());
                } else {
                    msg.nParkTime = 0;
                }

                try {
                    byte[] masterOfCar = textField_CarOwner.getText().getBytes("GBK");
                    System.arraycopy(masterOfCar, 0, msg.szMasterofCar, 0, masterOfCar.length);
                } catch (UnsupportedEncodingException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }

                if (comboBox_UserType.getSelectedItem() != null) {
                    if (comboBox_UserType.getSelectedItem().equals(Res.string().getMonthlyCardUser())) {
                        System.arraycopy("monthlyCardUser".getBytes(), 0, msg.szUserType, 0, "monthlyCardUser".length());
                    } else if (comboBox_UserType.getSelectedItem().equals(Res.string().getAnnualCardUser())) {
                        System.arraycopy("yearlyCardUser".getBytes(), 0, msg.szUserType, 0, "yearlyCardUser".length());
                    } else if (comboBox_UserType.getSelectedItem().equals(Res.string().getLongTermUser())) {
                        System.arraycopy("longTimeUser".getBytes(), 0, msg.szUserType, 0, "longTimeUser".length());
                    } else if (comboBox_UserType.getSelectedItem().equals(Res.string().getTemporaryUser())) {
                        System.arraycopy("casualUser".getBytes(), 0, msg.szUserType, 0, "casualUser".length());
                    }
                }

                if (!textField_DaysDue.getText().equals("")) {
                    msg.nRemainDay = Integer.parseInt(textField_DaysDue.getText());
                } else {
                    msg.nRemainDay = 0;
                }

                if (radioButton_Deny2Pass.isSelected()) {
                    msg.nPassEnable = 0;
                } else if (radioButton_Allow2Pass.isSelected()) {
                    msg.nPassEnable = 1;
                }
                String InTime = dateChooser_InTime.getText();// 车辆入场时间
                String[] InTimes = InTime.split("-");

                msg.stuInTime.dwYear = (short) Integer.parseInt(InTimes[0]);
                msg.stuInTime.dwMonth = (byte) Integer.parseInt(InTimes[1]);
                msg.stuInTime.dwDay = (byte) Integer.parseInt(InTimes[2]);

                String OutTime = dateChooser_OutTime.getText();// 车辆出场时间
                String[] OutTimes = OutTime.split("-");

                msg.stuOutTime.dwYear = (short) Integer.parseInt(OutTimes[0]);
                msg.stuOutTime.dwMonth = (byte) Integer.parseInt(OutTimes[1]);
                msg.stuOutTime.dwDay = (byte) Integer.parseInt(OutTimes[2]);

                try {
                    byte[] parkCharge = textField_ParkingCharge.getText().getBytes("GBK");
                    System.arraycopy(parkCharge, 0, msg.szParkCharge, 0, parkCharge.length);
                } catch (UnsupportedEncodingException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }

                if (!textField_RemainParkingSpaces.getText().equals("")) {
                    msg.nRemainSpace = Integer.parseInt(textField_RemainParkingSpaces.getText());
                } else {
                    msg.nRemainSpace = 0;
                }

                if (comboBox_CarStatus.getSelectedItem() != null) {
                    if (comboBox_CarStatus.getSelectedItem().equals(Res.string().getPassingCar())) {
                        msg.emCarStatus = 1;
                    } else if (comboBox_CarStatus.getSelectedItem().equals(Res.string().getNoCar())) {
                        msg.emCarStatus = 2;
                    }
                }

                try {
                    byte[] subUserType = textField_CustomerUserInfo.getText().getBytes("GBK");
                    System.arraycopy(subUserType, 0, msg.szSubUserType, 0, subUserType.length);
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    byte[] remarks = textField_RemarksInfo.getText().getBytes("GBK");
                    System.arraycopy(remarks, 0, msg.szRemarks, 0, remarks.length);
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    byte[] custom = textField_CustomerInfo.getText().getBytes("GBK");
                    System.arraycopy(custom, 0, msg.szCustom, 0, custom.length);
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                msg.write();
                boolean ret = DotmatrixScreenModule.setDotmatrixScreen(emType, msg);
                if (ret) {
                    JOptionPane.showMessageDialog(panel1, Res.string().getSetUpSuccess());
                } else {
                    JOptionPane.showMessageDialog(panel1, Res.string().getSetUpFailed());
                }
            }
        });

        panel_1 = new JPanel();
        tabbedPane.addTab("part2", null, panel_1, null);
        panel_1.setLayout(null);
        ScreenInfo = new JPanel();
        ScreenInfo.setBorder(
                new TitledBorder(null, Res.string().getEventInfo(), TitledBorder.LEFT, TitledBorder.TOP, null, null));
        ScreenInfo.setSize(500, 405);
        ScreenInfo.setLocation(10, 5);
        panel_1.add(ScreenInfo);
        ScreenInfo.setLayout(null);

        ScreenTableData = new Object[0][6];
        ScreenTable = tableInit(ScreenTableData, ScreenTableTitle);
        Model = (DefaultTableModel) ScreenTable.getModel();
        scrollPane = new JScrollPane(ScreenTable);
        scrollPane.setBounds(10, 19, 480, 221);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ScreenInfo.add(scrollPane);

        btnNewButton_3 = new JButton(Res.string().getAdd());
        btnNewButton_3.setEnabled(false);
        btnNewButton_3.setBounds(10, 250, 75, 20);
        btnNewButton_3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub

                String Contain = textField_21.getText();
                int ScreenNum = Integer.parseInt(textField_22.getText());
                String ContainType = null;
                String ContainColor = null;
                String ScrollType = null;
                String ScrollSpeed = null;
                if (comboBox_4.getSelectedItem().equals(Res.string().getOrdinary())) {
                    ContainType = /* "普通" */Res.string().getOrdinary();
                } else if (comboBox_4.getSelectedItem().equals(Res.string().getLocalTime())) {
                    ContainType = /* "本地时间" */Res.string().getLocalTime();
                } else if (comboBox_4.getSelectedItem().equals(Res.string().getQRCode())) {
                    ContainType = /* "二维码" */Res.string().getQRCode();
                } /*
                 * else if(comboBox_4.getSelectedItem().equals(Res.string().getResource())) {
                 * ContainType = "资源文件" Res.string().getResource(); }
                 */

                if (comboBox_5.getSelectedItem().equals(Res.string().getGreen())) {
                    ContainColor = /* "绿色" */Res.string().getGreen();
                } else if (comboBox_5.getSelectedItem().equals(Res.string().getRed())) {
                    ContainColor = /* "红色" */Res.string().getRed();
                } else if (comboBox_5.getSelectedItem().equals(Res.string().getYellow())) {
                    ContainColor = /* "黄色" */Res.string().getYellow();
                } else if (comboBox_5.getSelectedItem().equals(Res.string().getWhite())) {
                    ContainColor = /* "白色" */Res.string().getWhite();
                }
                if (comboBox_6.getSelectedItem().equals(Res.string().getNoRolling())) {
                    ScrollType = /* "不滚动" */Res.string().getNoRolling();
                } else if (comboBox_6.getSelectedItem().equals(Res.string().getScrollLeftAndRight())) {
                    ScrollType = /* "左右滚动" */Res.string().getScrollLeftAndRight();
                } else if (comboBox_6.getSelectedItem().equals(Res.string().getScrollTopAndDown())) {
                    ScrollType = /* "上下翻页滚动" */Res.string().getScrollTopAndDown();
                }
                if (comboBox_7.getSelectedItem().equals("1")) {
                    ScrollSpeed = /* "速率1" */Res.string().getSpeed() + "1";
                } else if (comboBox_7.getSelectedItem().equals("2")) {
                    ScrollSpeed = /* "速率2" */Res.string().getSpeed() + "2";
                } else if (comboBox_7.getSelectedItem().equals("3")) {
                    ScrollSpeed = /* "速率3" */Res.string().getSpeed() + "3";
                } else if (comboBox_7.getSelectedItem().equals("4")) {
                    ScrollSpeed = /* "速率4" */Res.string().getSpeed() + "4";
                } else if (comboBox_7.getSelectedItem().equals("5")) {
                    ScrollSpeed = /* "速率5" */Res.string().getSpeed() + "5";
                }
                /*
                 * Model.setValueAt(ScreenNum, index, 0); Model.setValueAt(Contain, index, 1);
                 * Model.setValueAt(ContainType, index, 2); Model.setValueAt(ContainColor,
                 * index, 3); Model.setValueAt(ScrollType, index, 4);
                 * Model.setValueAt(ScrollSpeed, index, 5); index++;
                 */
                List<Integer> num = new ArrayList<Integer>();
                for (int i = 0; i < Model.getRowCount(); i++) {
                    num.add((Integer) Model.getValueAt(i, 0));
                }
                if (ScreenNum == -1 || Contain == null) {
                    JOptionPane.showMessageDialog(panel1, Res.string().getPrompt());
                } else {
                    if (num.contains(ScreenNum)) {
                        JOptionPane.showMessageDialog(panel1, "屏幕编号已存在,请删除或修改屏幕编号！");
                    } else {
                        Model.addRow(new Object[]{ScreenNum, Contain, ContainType, ContainColor, ScrollType,
                                ScrollSpeed});
                    }
                }
            }
        });
        ScreenInfo.add(btnNewButton_3);

        btnNewButton_4 = new JButton(Res.string().getModify());
        btnNewButton_4.setEnabled(false);
        btnNewButton_4.setBounds(96, 250, 75, 20);
        btnNewButton_4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                String Contain = textField_21.getText();
                int ScreenNum = Integer.parseInt(textField_22.getText());
                // int ScreenNum = (int)Model.getValueAt(ScreenTable.getSelectedRow(), 0);
                String ContainType = null;
                String ContainColor = null;
                String ScrollType = null;
                String ScrollSpeed = null;
                if (comboBox_4.getSelectedItem().equals(Res.string().getOrdinary())) {
                    ContainType = /* "普通" */Res.string().getOrdinary();
                } else if (comboBox_4.getSelectedItem().equals(Res.string().getLocalTime())) {
                    ContainType = /* "本地时间" */Res.string().getLocalTime();
                } else if (comboBox_4.getSelectedItem().equals(Res.string().getQRCode())) {
                    ContainType = /* "二维码" */Res.string().getQRCode();
                } /*
                 * else if (comboBox_4.getSelectedItem().equals(Res.string().getResource())) {
                 * ContainType = "资源文件" Res.string().getResource(); }
                 */

                if (comboBox_5.getSelectedItem().equals(Res.string().getGreen())) {
                    ContainColor = /* "绿色" */Res.string().getGreen();
                } else if (comboBox_5.getSelectedItem().equals(Res.string().getRed())) {
                    ContainColor = /* "红色" */Res.string().getRed();
                } else if (comboBox_5.getSelectedItem().equals(Res.string().getYellow())) {
                    ContainColor = /* "黄色" */Res.string().getYellow();
                } else if (comboBox_5.getSelectedItem().equals(Res.string().getWhite())) {
                    ContainColor = /* "白色" */Res.string().getWhite();
                }
                if (comboBox_6.getSelectedItem().equals(Res.string().getNoRolling())) {
                    ScrollType = /* "不滚动" */Res.string().getNoRolling();
                } else if (comboBox_6.getSelectedItem().equals(Res.string().getScrollLeftAndRight())) {
                    ScrollType = /* "左右滚动" */Res.string().getScrollLeftAndRight();
                } else if (comboBox_6.getSelectedItem().equals(Res.string().getScrollTopAndDown())) {
                    ScrollType = /* "上下翻页滚动" */Res.string().getScrollTopAndDown();
                }
                if (comboBox_7.getSelectedItem().equals("1")) {
                    ScrollSpeed = /* "速率1" */Res.string().getSpeed() + "1";
                } else if (comboBox_7.getSelectedItem().equals("2")) {
                    ScrollSpeed = /* "速率2" */Res.string().getSpeed() + "2";
                } else if (comboBox_7.getSelectedItem().equals("3")) {
                    ScrollSpeed = /* "速率3" */Res.string().getSpeed() + "3";
                } else if (comboBox_7.getSelectedItem().equals("4")) {
                    ScrollSpeed = /* "速率4" */Res.string().getSpeed() + "4";
                } else if (comboBox_7.getSelectedItem().equals("5")) {
                    ScrollSpeed = /* "速率5" */Res.string().getSpeed() + "5";
                }

                List<Integer> num = new ArrayList<Integer>();
                for (int i = 0; i < Model.getRowCount(); i++) {
                    num.add((Integer) Model.getValueAt(i, 0));
                }
                num.remove(ScreenTable.getSelectedRow());
                if (Model.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(panel1, Res.string().getPrompt());
                } else {
                    if (num.contains(ScreenNum)) {
                        JOptionPane.showMessageDialog(panel1, "屏幕编号已存在,请删除或修改屏幕编号！");
                    } else {
                        Model.setValueAt(ScreenNum, ScreenTable.getSelectedRow(), 0);
                        Model.setValueAt(Contain, ScreenTable.getSelectedRow(), 1);
                        Model.setValueAt(ContainType, ScreenTable.getSelectedRow(), 2);
                        Model.setValueAt(ContainColor, ScreenTable.getSelectedRow(), 3);
                        Model.setValueAt(ScrollType, ScreenTable.getSelectedRow(), 4);
                        Model.setValueAt(ScrollSpeed, ScreenTable.getSelectedRow(), 5);
                    }
                }
            }
        });
        ScreenInfo.add(btnNewButton_4);

        btnNewButton_5 = new JButton(Res.string().getDelete());
        btnNewButton_5.setEnabled(false);
        btnNewButton_5.setBounds(181, 250, 75, 20);
        btnNewButton_5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                // System.out.println(ScreenTable.getSelectedRow());
                if (Model.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(panel1, Res.string().getPrompt());
                } else {
                    Model.removeRow(ScreenTable.getSelectedRow());
                }
            }
        });
        ScreenInfo.add(btnNewButton_5);

        textField_21 = new JTextField();
        textField_21.setEnabled(false);
        textField_21.setBounds(21, 305, 171, 92);
        ScreenInfo.add(textField_21);
        textField_21.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel(Res.string().getScreenNumber() + ":");
        lblNewLabel_4.setBounds(266, 265, 69, 15);
        ScreenInfo.add(lblNewLabel_4);

        textField_22 = new JTextField(new String("-1"));
        textField_22.setEnabled(false);
        textField_22.setBounds(327, 262, 159, 21);
        ScreenInfo.add(textField_22);
        textField_22.setColumns(10);

        JLabel label = new JLabel(Res.string().getContainType() + ":");
        label.setBounds(266, 297, 69, 15);
        ScreenInfo.add(label);

        comboBox_4 = new JComboBox();
        comboBox_4.setEnabled(false);
        comboBox_4.setModel(new DefaultComboBoxModel(new String[]{Res.string().getOrdinary(),
                Res.string().getLocalTime(), Res.string().getQRCode()/* , Res.string().getResource() */}));
        comboBox_4.setBounds(327, 294, 159, 21);
        ScreenInfo.add(comboBox_4);

        JLabel label_1 = new JLabel(Res.string().getContainColor() + ":");
        label_1.setBounds(266, 325, 69, 15);
        ScreenInfo.add(label_1);

        comboBox_5 = new JComboBox();
        comboBox_5.setEnabled(false);
        comboBox_5.setModel(new DefaultComboBoxModel(new String[]{Res.string().getGreen(), Res.string().getRed(),
                Res.string().getYellow(), Res.string().getWhite()}));
        comboBox_5.setBounds(327, 322, 159, 21);
        ScreenInfo.add(comboBox_5);

        JLabel label_2 = new JLabel(Res.string().getScrollType() + ":");
        label_2.setBounds(266, 350, 69, 15);
        ScreenInfo.add(label_2);

        comboBox_6 = new JComboBox();
        comboBox_6.setEnabled(false);
        comboBox_6.setModel(new DefaultComboBoxModel(new String[]{Res.string().getNoRolling(),
                Res.string().getScrollLeftAndRight(), Res.string().getScrollTopAndDown()}));
        comboBox_6.setBounds(327, 350, 159, 21);
        ScreenInfo.add(comboBox_6);

        JLabel label_16 = new JLabel(Res.string().getScrollSpeed() + ":");
        label_16.setBounds(266, 380, 69, 15);
        ScreenInfo.add(label_16);

        comboBox_7 = new JComboBox();
        comboBox_7.setEnabled(false);
        comboBox_7.setModel(new DefaultComboBoxModel(new String[]{"1", "2", "3", "4", "5"}));
        comboBox_7.setBounds(327, 377, 159, 21);
        ScreenInfo.add(comboBox_7);

        JLabel label_18 = new JLabel(Res.string().getContain() + ":");
        label_18.setBounds(22, 280, 69, 15);
        ScreenInfo.add(label_18);

        JPanel panel_3 = new JPanel();
        panel_3.setBounds(520, 5, 240, 405);
        panel_3.setBorder(
                new TitledBorder(null, Res.string().getEventInfo(), TitledBorder.LEFT, TitledBorder.TOP, null, null));
        panel_1.add(panel_3);
        panel_3.setLayout(null);

        VideoTableData = new Object[0][3];
        VideoTable = tableInit(VideoTableData, VideoTableTitle);
        VideoModel = (DefaultTableModel) VideoTable.getModel();
        JScrollPane scrollPane_1 = new JScrollPane(VideoTable);
        scrollPane_1.setBounds(10, 20, 225, 180);
        panel_3.add(scrollPane_1);

        button_1 = new JButton(Res.string().getAdd());
        button_1.setEnabled(false);
        button_1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                String containType = null;
                String contain = null;
                int ScreenNum = Integer.parseInt(textField_22.getText());
                contain = textField_23.getText();
                if (comboBox_8.getSelectedItem().equals(Res.string().getOrdinary())) {
                    containType = /* "普通" */Res.string().getOrdinary();
                } else if (comboBox_8.getSelectedItem().equals(Res.string().getPlateNumber())) {
                    containType = /* "车牌号码" */Res.string().getPlateNumber();
                } else if (comboBox_8.getSelectedItem().equals(Res.string().getTime())) {
                    containType = /* "时间" */Res.string().getTime();
                } else if (comboBox_8.getSelectedItem().equals(Res.string().getNumericString())) {
                    containType = /* "数字字符串" */Res.string().getNumericString();
                }

                /*
                 * VideoModel.setValueAt(ScreenNum, VedioIndex, 0);
                 * VideoModel.setValueAt(containType, VedioIndex, 1); VedioIndex++;
                 */

                List<Integer> num = new ArrayList<Integer>();
                for (int i = 0; i < VideoModel.getRowCount(); i++) {
                    num.add((Integer) VideoModel.getValueAt(i, 0));
                }
                if (ScreenNum == -1 || contain == null) {
                    JOptionPane.showMessageDialog(panel1, Res.string().getPrompt());
                } else {
                    if (num.contains(ScreenNum)) {
                        JOptionPane.showMessageDialog(panel1, "屏幕编号已存在,请删除或修改屏幕编号！");
                    } else {
                        VideoModel.addRow(new Object[]{ScreenNum, contain, containType});
                    }
                }
            }
        });
        button_1.setBounds(10, 213, 67, 20);
        panel_3.add(button_1);

        button_2 = new JButton(Res.string().getModify());
        button_2.setEnabled(false);
        button_2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                String containType = null;
                int ScreenNum = Integer.parseInt(textField_22.getText());
                // int ScreenNum = (int)VideoModel.getValueAt(ScreenTable.getSelectedRow(), 0);
                String contain = textField_23.getText();
                if (comboBox_8.getSelectedItem().equals(Res.string().getOrdinary())) {
                    containType = /* "普通" */Res.string().getOrdinary();
                } else if (comboBox_8.getSelectedItem().equals(Res.string().getPlateNumber())) {
                    containType = /* "车牌号码" */Res.string().getPlateNumber();
                } else if (comboBox_8.getSelectedItem().equals(Res.string().getTime())) {
                    containType = /* "时间" */Res.string().getTime();
                } else if (comboBox_8.getSelectedItem().equals(Res.string().getNumericString())) {
                    containType = /* "数字字符串" */Res.string().getNumericString();
                }

                List<Integer> num = new ArrayList<Integer>();
                for (int i = 0; i < VideoTable.getRowCount(); i++) {
                    num.add((Integer) VideoTable.getValueAt(i, 0));
                }
                num.remove(VideoTable.getSelectedRow());
                if (VideoTable.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(panel1, Res.string().getPrompt());
                } else {

                    if (num.contains(ScreenNum)) {
                        JOptionPane.showMessageDialog(panel1,
                                "屏幕编号已存在,请删除或修改屏幕编号！");
                    } else {
                        VideoModel.setValueAt(ScreenNum,
                                VideoTable.getSelectedRow(), 0);

                        VideoModel.setValueAt(contain, VideoTable.getSelectedRow(), 1);
                        VideoModel.setValueAt(containType, VideoTable.getSelectedRow(), 2);
                    }
                }
            }
        });
        button_2.setBounds(87, 213, 67, 20);
        panel_3.add(button_2);

        button_4 = new JButton(Res.string().getDelete());
        button_4.setEnabled(false);
        button_4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                // System.out.println(VideoTable.getSelectedRow());
                if (VideoTable.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(panel1, Res.string().getPrompt());
                } else {
                    VideoModel.removeRow(VideoTable.getSelectedRow());
                }

                /*
                 * VideoModel.setValueAt("", VideoTable.getSelectedRow(), 0);
                 * VideoModel.setValueAt("", VideoTable.getSelectedRow(), 1); VedioIndex= 0;
                 */
            }
        });
        button_4.setBounds(164, 213, 71, 20);
        panel_3.add(button_4);

        label_17 = new JLabel(Res.string().getContainType() + ":");
        label_17.setBounds(10, 252, 69, 15);
        panel_3.add(label_17);

        comboBox_8 = new JComboBox();
        comboBox_8.setEnabled(false);
        comboBox_8.setModel(new DefaultComboBoxModel(new String[]{Res.string().getOrdinary(),
                Res.string().getPlateNumber(), Res.string().getTime(), Res.string().getNumericString()}));
        comboBox_8.setBounds(71, 249, 159, 21);
        panel_3.add(comboBox_8);

        label_19 = new JLabel(Res.string().getVoiceText() + ":");
        label_19.setBounds(10, 280, 69, 15);
        panel_3.add(label_19);

        textField_23 = new JTextField();
        textField_23.setEnabled(false);
        textField_23.setColumns(10);
        textField_23.setBounds(10, 305, 220, 92);
        panel_3.add(textField_23);

        btnNewButton_6 = new JButton(Res.string().getIssued());
        btnNewButton_6.setEnabled(false);
        btnNewButton_6.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                NET_IN_SET_PARK_CONTROL_INFO pIn = new NET_IN_SET_PARK_CONTROL_INFO();

                pIn.nScreenShowInfoNum = Model.getRowCount();
                // System.out.println(pIn.nScreenShowInfoNum);
                for (int i = 0; i < pIn.nScreenShowInfoNum; i++) {
                    pIn.stuScreenShowInfo[i].nScreenNo = Integer.parseInt(String.valueOf(Model.getValueAt(i, 0)));
                    try {
                        ToolKits.StringToByteArray(String.valueOf(Model.getValueAt(i, 1)), pIn.stuScreenShowInfo[i].szText);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (String.valueOf(Model.getValueAt(i, 2)).trim().equals(Res.string().getOrdinary())) {
                        pIn.stuScreenShowInfo[i].emTextType = 0;
                    } else if (String.valueOf(Model.getValueAt(i, 2)).trim().equals(Res.string().getLocalTime())) {
                        pIn.stuScreenShowInfo[i].emTextType = 1;
                    } else if (String.valueOf(Model.getValueAt(i, 2)).trim().equals(Res.string().getQRCode())) {
                        pIn.stuScreenShowInfo[i].emTextType = 2;
                    } else if (String.valueOf(Model.getValueAt(i, 2)).trim().equals(Res.string().getResource())) {
                        pIn.stuScreenShowInfo[i].emTextType = 3;
                    }

                    if (String.valueOf(Model.getValueAt(i, 3)).trim().equals(Res.string().getGreen())) {
                        pIn.stuScreenShowInfo[i].emTextColor = 0;
                    } else if (String.valueOf(Model.getValueAt(i, 3)).trim().equals(Res.string().getRed())) {
                        pIn.stuScreenShowInfo[i].emTextColor = 1;
                    } else if (String.valueOf(Model.getValueAt(i, 3)).trim().equals(Res.string().getYellow())) {
                        pIn.stuScreenShowInfo[i].emTextColor = 2;
                    } else if (String.valueOf(Model.getValueAt(i, 3)).trim().equals(Res.string().getWhite())) {
                        pIn.stuScreenShowInfo[i].emTextColor = 3;
                    }

                    if (String.valueOf(Model.getValueAt(i, 4)).trim().equals(Res.string().getNoRolling())) {
                        pIn.stuScreenShowInfo[i].emTextRollMode = 0;
                    } else if (String.valueOf(Model.getValueAt(i, 4)).trim()
                            .equals(Res.string().getScrollLeftAndRight())) {
                        pIn.stuScreenShowInfo[i].emTextRollMode = 1;
                    } else if (String.valueOf(Model.getValueAt(i, 4)).trim()
                            .equals(Res.string().getScrollTopAndDown())) {
                        pIn.stuScreenShowInfo[i].emTextRollMode = 2;
                    }
                    System.out.println(String.valueOf(Model.getValueAt(i, 5)).trim());
                    System.out.println(Res.string().getSpeed() + "1");
                    if (String.valueOf(Model.getValueAt(i, 5)).trim().equals(Res.string().getSpeed() + "1")) {
                        pIn.stuScreenShowInfo[i].nRollSpeed = 1;
                    } else if (String.valueOf(Model.getValueAt(i, 5)).trim().equals(Res.string().getSpeed() + "2")) {
                        pIn.stuScreenShowInfo[i].nRollSpeed = 2;
                    } else if (String.valueOf(Model.getValueAt(i, 5)).trim().equals(Res.string().getSpeed() + "3")) {
                        pIn.stuScreenShowInfo[i].nRollSpeed = 3;
                    } else if (String.valueOf(Model.getValueAt(i, 5)).trim().equals(Res.string().getSpeed() + "4")) {
                        pIn.stuScreenShowInfo[i].nRollSpeed = 4;
                    } else if (String.valueOf(Model.getValueAt(i, 5)).trim().equals(Res.string().getSpeed() + "5")) {
                        pIn.stuScreenShowInfo[i].nRollSpeed = 5;
                    }
                    // pIn.stuScreenShowInfo[i].nRollSpeed =
                    // Integer.parseInt(String.valueOf(Model.getValueAt(i, 5))) ;
                }
                pIn.nBroadcastInfoNum = VideoModel.getRowCount();
                System.out.println(pIn.nBroadcastInfoNum);
                for (int i = 0; i < pIn.nBroadcastInfoNum; i++) {
                    System.out.println(String.valueOf(VideoModel.getValueAt(i, 2)).trim());
                    try {
                        System.out.println(new String(pIn.stuBroadcastInfo[i].szText, "GBK").trim());
                        ToolKits.StringToByteArray(String.valueOf(VideoModel.getValueAt(i, 1)), pIn.stuBroadcastInfo[i].szText);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (String.valueOf(VideoModel.getValueAt(i, 2)).trim().equals(Res.string().getOrdinary())) {
                        pIn.stuBroadcastInfo[i].emTextType = 0;
                    } else if (String.valueOf(VideoModel.getValueAt(i, 2)).trim()
                            .equals(Res.string().getPlateNumber())) {
                        pIn.stuBroadcastInfo[i].emTextType = 1;
                    } else if (String.valueOf(VideoModel.getValueAt(i, 2)).trim().equals(Res.string().getTime())) {
                        pIn.stuBroadcastInfo[i].emTextType = 2;
                    } else if (String.valueOf(VideoModel.getValueAt(i, 2)).trim()
                            .equals(Res.string().getNumericString())) {
                        pIn.stuBroadcastInfo[i].emTextType = 3;
                    }
                }
                if (pIn.nScreenShowInfoNum == 0 && pIn.nBroadcastInfoNum == 0) {
                    JOptionPane.showMessageDialog(panel1, Res.string().getPrompt());
                } else {
                    if (controlDevice(pIn)) {
                        JOptionPane.showMessageDialog(panel1, Res.string().getSetUpSuccess());
                    } else {
                        JOptionPane.showMessageDialog(panel1, Res.string().getSetUpFailed());
                    }
                }
            }
        });
        btnNewButton_6.setBounds(652, 413, 93, 23);
        panel_1.add(btnNewButton_6);

    }

    public JTable tableInit(Object[][] data, String[] columnName) {
        JTable table;
        DefaultTableModel model;
        model = new DefaultTableModel(data, columnName);
        table = new JTable(model) {
            @Override // 不可编辑
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model = (DefaultTableModel) table.getModel();

        table.setPreferredScrollableViewportSize(new Dimension(500, 500));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 只能选中一行

        // 列表显示居中
        DefaultTableCellRenderer dCellRenderer = new DefaultTableCellRenderer();
        dCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, dCellRenderer);
        return table;
    }

    public Boolean controlDevice(NET_IN_SET_PARK_CONTROL_INFO pIn) {
        for (int i = 0; i < 5; i++) {
            System.out.println(pIn.stuScreenShowInfo[i].emTextType);
        }

        int emType = CtrlType.CTRL_SET_PARK_CONTROL_INFO;
        // System.out.println(new String(pIn.stuScreenShowInfo[0].szText));
        NET_OUT_SET_PARK_CONTROL_INFO pOut = new NET_OUT_SET_PARK_CONTROL_INFO();
        pIn.write();
        pOut.write();
        Boolean ret = LoginModule.netsdk.CLIENT_ControlDeviceEx(LoginModule.m_hLoginHandle, emType, pIn.getPointer(),
                pOut.getPointer(), 3000);
        if (ret) {
            System.out.println("CLIENT_ControlDeviceEx success");
        } else {
            System.out.println("CLIENT_ControlDeviceEx fail " + getErrorCode());
        }
        return ret;

    }

    public String getErrorCode() {
        return " { error code: ( 0x80000000|" + (LoginModule.netsdk.CLIENT_GetLastError() & 0x7fffffff)
                + " ). 参考  NetSDKLib.java }";
    }
}
