package com.ruoyi.dahua.frame.scada;

import com.ruoyi.dahua.common.FunctionList;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.frame.vto.DefaultDisConnect;
import com.ruoyi.dahua.frame.vto.DefaultHaveReconnect;
import com.ruoyi.dahua.frame.vto.OperateManager;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.lib.Utils;
import com.ruoyi.dahua.lib.structure.NET_ATTRIBUTE_INFO;
import com.ruoyi.dahua.lib.structure.NET_GET_CONDITION_INFO;
import com.ruoyi.dahua.lib.structure.NET_IN_SCADA_GET_ATTRIBUTE_INFO;
import com.ruoyi.dahua.lib.structure.NET_OUT_SCADA_GET_ATTRIBUTE_INFO;
import com.ruoyi.dahua.module.LoginModule;
import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static com.ruoyi.dahua.module.LoginModule.m_hLoginHandle;

public class SCADADemo extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField ipTextField;
    private JTextField portTextField;
    private JTextField userNameTextField;
    private JPasswordField passwordField;

    private JButton btnLogin; // 登录
    private JButton btnLogout; // 登出
    private JButton btnGetDeviceList; // 获取设备列表
    private JButton btnAlarmAttachInfo; // 遥信，订阅报警
    private JButton btnAttachInfo; // 遥测，订阅信息
    private JButton btnAlarmAttachInfoStop; // 遥信，取消订阅报警
    private JButton btnAttachInfoStop; // 遥测，取消订阅信息

    private JButton btnStartListen; // 停止监听
    private JButton btnStopListen; // 停止监听


    private JTable devicesTable; // 设备列表
    private JTable pointListTable; // 设备点位列表
    private JTable alarmAttachInfoTable;
    private JScrollPane deviceScrollPane;


    private JButton btnGetSCADAAttribute; // 获取点位信息
    private boolean isListen = false;


    ///////////////////// 主面板 /////////////////////

    private static JFrame mainFrame = new JFrame();
    private OperateManager manager = new OperateManager();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SCADADemo frame = new SCADADemo();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * enable button
     *
     * @param enable
     */
    public void setButtonEnable(boolean enable) {
        btnLogin.setEnabled(enable);
        btnLogout.setEnabled(enable);
        btnGetDeviceList.setEnabled(enable);
        btnAlarmAttachInfo.setEnabled(enable);
        btnAttachInfo.setEnabled(enable);
        btnGetSCADAAttribute.setEnabled(enable);
        btnStartListen.setEnabled(enable);

    }


    private boolean isAlarmAttachInfo = false;
    private boolean isAttachInfo = false;

    /**
     * 登录
     */
    public boolean login() {
        if (LoginModule.login(ipTextField.getText(), Integer.parseInt(portTextField.getText()),
                userNameTextField.getText(), new String(passwordField.getPassword()))) {
            setButtonEnable(true); // login succeed，enable related button
            btnLogin.setEnabled(false);
            // 监听按钮使能
        } else {
            JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(),
                    Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * 登出
     */
    public void logout() {
        if (LoginModule.logout()) {
            setButtonEnable(false); // 所有按钮置灰
            btnLogin.setEnabled(true);
            if (isListen) {
                stopListen();
                isListen = false;
            }

            if (isAlarmAttachInfo) {
                scadaDetachInfo();
                isAlarmAttachInfo = false;
            }
            if (isAttachInfo) {
                scadaAlarmDetachInfo();
                isAttachInfo = false;
            }

            //清空表格
            //普通事件table清空数据
            ((DefaultTableModel) alarmTable.getModel()).setRowCount(0);

            // 设备列表table清空数据
            ((DefaultTableModel) devicesDataTable.getModel()).setRowCount(0);

            // 监测点位实时信息table清空数据
            ((DefaultTableModel) attachInfoDataTable.getModel()).setRowCount(0);

            // 监测点位报警table清空数据
            ((DefaultTableModel) alarmAttachInfoDataTable.getModel()).setRowCount(0);

            // 点位信息table清空数据
            ((DefaultTableModel) attributeDataTable.getModel()).setRowCount(0);
        }
    }


    /**
     * 获取当前主机接入的外部设备ID
     */
    Vector<String> deviceIds = new Vector<String>();
    // 多平台 编码
    private final static Charset encode = Charset.forName(Utils.getPlatformEncode());

    public boolean getDeviceIdList() {
        deviceIds.clear();

        // model.setColumnCount(0);
        int nCount = 64; //

        NetSDKLib.NET_SCADA_DEVICE_ID_INFO[] stuDeviceIDList = new NetSDKLib.NET_SCADA_DEVICE_ID_INFO[nCount];
        for (int i = 0; i < stuDeviceIDList.length; ++i) {
            stuDeviceIDList[i] = new NetSDKLib.NET_SCADA_DEVICE_ID_INFO();
        }

        NetSDKLib.NET_SCADA_DEVICE_LIST stuSCADADeviceInfo = new NetSDKLib.NET_SCADA_DEVICE_LIST();
        stuSCADADeviceInfo.nMax = nCount;
        int nSize = stuDeviceIDList[0].size() * nCount;
        stuSCADADeviceInfo.pstuDeviceIDInfo = new Memory(nSize);   // 监测设备信息
        stuSCADADeviceInfo.pstuDeviceIDInfo.clear(nSize);
        ToolKits.SetStructArrToPointerData(stuDeviceIDList, stuSCADADeviceInfo.pstuDeviceIDInfo);
        if (queryDevState(NetSDKLib.NET_DEVSTATE_SCADA_DEVICE_LIST, stuSCADADeviceInfo)) {
            JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(),
                    Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (stuSCADADeviceInfo.nRet == 0) {
            System.out.println("当前主机接入的外部设备ID有效个数为0.");                        // 外部设备没有有效ID
            return false;
        }

        // 从 Pointer 提取数据
        ToolKits.GetPointerDataToStructArr(stuSCADADeviceInfo.pstuDeviceIDInfo, stuDeviceIDList);
        // 打印数据并更新设备ID
        System.out.println("获取当前主机接入的外部设备ID的有效个数：" + stuSCADADeviceInfo.nRet);
        if (null != devicesDataTable) {
            DefaultTableModel model = (DefaultTableModel) devicesDataTable.getModel();
            model.getDataVector().clear();// 清空所有数据
            for (int i = 0; i < stuSCADADeviceInfo.nRet; ++i) {
                String deviceID = "";
                String deviceName = "";

                deviceName = new String(stuDeviceIDList[i].szDevName, encode).trim();
                deviceID = new String(stuDeviceIDList[i].szDeviceID, encode).trim();
                System.out.printf("外部设备[%d] 设备id[%s] 设备名称[%s]\n", i,
                        new String(stuDeviceIDList[i].szDeviceID, encode).trim(), deviceName);


                deviceIds.add(deviceID);
                model.addRow(new Object[]{deviceID, deviceName});
            }
        }

        return true;
    }

    /**
     * 查询设备状态
     */
    public boolean queryDevState(int nType, NetSDKLib.SdkStructure stuInfo) {

        IntByReference intRetLen = new IntByReference();
        stuInfo.write();
        if (!LoginModule.netsdk.CLIENT_QueryDevState(m_hLoginHandle, nType, stuInfo.getPointer(), stuInfo.size(), intRetLen, 3000)) {
            return true;
        }
        stuInfo.read();
        return false;
    }


    /**
     * 订阅监测点位报警
     */
    public void scadaAlarmAttachInfo() {

        if (!isAlarmAttachInfo) {
            // 入参
            NetSDKLib.NET_IN_SCADA_ALARM_ATTACH_INFO stIn = new NetSDKLib.NET_IN_SCADA_ALARM_ATTACH_INFO();
            stIn.cbCallBack = SCADAAlarmAttachInfoCallBack.getINSTANCE(alarmAttachInfoDataTable);
            // 出参
            NetSDKLib.NET_OUT_SCADA_ALARM_ATTACH_INFO stOut = new NetSDKLib.NET_OUT_SCADA_ALARM_ATTACH_INFO();

            alarmAttachInfoHandle = LoginModule.netsdk.CLIENT_SCADAAlarmAttachInfo(m_hLoginHandle, stIn, stOut, 3000);

            if (alarmAttachInfoHandle.longValue() != 0) {
                contentPane.repaint();
                isAlarmAttachInfo = true;
                btnAlarmAttachInfo.setText(Res.string().getCancel()); // 取消
            }
        } else {
            scadaAlarmDetachInfo();
        }

    }

    /**
     * 取消订阅监测点位报警信息
     */
    public void scadaAlarmDetachInfo() {
        if (alarmAttachInfoHandle.longValue() != 0) {
            LoginModule.netsdk.CLIENT_SCADAAlarmDetachInfo(alarmAttachInfoHandle);
            contentPane.repaint();
            isAlarmAttachInfo = false;
            alarmAttachInfoHandle.setValue(0);
            alarmAttachInfoDataModel.getDataVector().clear();
            btnAlarmAttachInfo.setText(Res.string().getSCADAAttach()); // 订阅

        }
    }

    private NetSDKLib.LLong attachInfoHandle = new NetSDKLib.LLong(0);  // 监测点位信息，订阅句柄
    private NetSDKLib.LLong alarmAttachInfoHandle = new NetSDKLib.LLong(0);  // 监测点位报警，订阅句柄

    /**
     * 普通报警监听回调
     */
    private static class MessCallback implements NetSDKLib.fMessCallBack {
        private static MessCallback INSTANCE;
        private JTable table;

        private MessCallback(JTable table) {
            this.table = table;
        }

        public static MessCallback getInstance(JTable table) {
            if (INSTANCE == null) {
                INSTANCE = new MessCallback(table);
            }
            if (table != null) {
                INSTANCE.table = table;
            }

            return INSTANCE;
        }

        @Override
        public boolean invoke(int lCommand, NetSDKLib.LLong lLoginID, Pointer pStuEvent,
                              int dwBufLen, String strDeviceIP, NativeLong nDevicePort,
                              Pointer dwUser) {
            switch (lCommand) {

                case NetSDKLib.NET_ALARM_SCADA_DEV_ALARM: {  // 12706 检测采集设备报警事件   "SCADADevAlarm"

                    NetSDKLib.ALARM_SCADA_DEV_INFO msg = new NetSDKLib.ALARM_SCADA_DEV_INFO();
                    ToolKits.GetPointerData(pStuEvent, msg);
                    String deviceId = new String(msg.szDevID,encode).trim();
                    System.out.println("[检测采集设备报警事件] nChannel :" + msg.nChannel);
                    System.out.println("设备ID :" + deviceId);
                    String description = new String(msg.szDesc, encode).trim();
                    String deviceName = new String(msg.szDevName,encode).trim();
                    System.out.println( " nAction :" + msg.nAction +
                            " nAlarmFlag :" + msg.nAlarmFlag + "\n" + "故障设备名称" + deviceName
                    + "\n" + "描述" + description + "时间" + msg.stuTime);
                    if (table != null) {
//                        NetSDKLib.ALARM_SCADA_DEV_INFO msg = new NetSDKLib.ALARM_SCADA_DEV_INFO();
//                        ToolKits.GetPointerData(pStuEvent, msg);
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.addRow(new Object[]{deviceId, deviceName, msg.nChannel, description});
                    }
                    break;
                }
                default:
                    System.out.println("What's lCommand: " + lCommand);
                    break;

            }

            return true;
        }
    }

    /**
     * 订阅监测点位实时信息
     */
    public void scadaAttachInfo() {
        if (!isBtnAttachInfo) {

            // 入参
            NetSDKLib.NET_IN_SCADA_ATTACH_INFO stIn = new NetSDKLib.NET_IN_SCADA_ATTACH_INFO();
            stIn.cbCallBack = SCADAAttachInfoCallBack.getInstance(attachInfoDataTable);
            // 出参
            System.err.println("登录句柄： " + m_hLoginHandle);
            NetSDKLib.NET_OUT_SCADA_ATTACH_INFO stOut = new NetSDKLib.NET_OUT_SCADA_ATTACH_INFO();
            attachInfoHandle = LoginModule.netsdk.CLIENT_SCADAAttachInfo(m_hLoginHandle, stIn, stOut, 3000);
            if (attachInfoHandle.longValue() != 0) {
                contentPane.repaint();
                isBtnAttachInfo = true;
                btnAttachInfo.setText(Res.string().getCancel()); // 取消
                System.out.println("CLIENT_SCADAAttachInfo: 订阅监测点位实时信息成功！");
            } else {
                JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(),
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            scadaDetachInfo();
        }
    }


    /**
     * 获取设备点位信息
     */
    public void getDeviceAttribute() throws UnsupportedEncodingException {
        Map<Integer, String> num2PointType = new HashMap<Integer, String>(6);
        num2PointType.put(0, "UNKNOWN");
        num2PointType.put(1, "ALL");
        num2PointType.put(2, "YC");
        num2PointType.put(3, "YX");
        num2PointType.put(4, "YT");
        num2PointType.put(5, "YK");
        DefaultTableModel model = (DefaultTableModel) attributeDataTable.getModel();
        model.getDataVector().clear(); // 清空所有数据
        for (int i = 0; i < deviceIds.size(); i++) {
            String deviceId = deviceIds.get(i);
            int nMaxAttributeInfoNum = 20;
            NET_IN_SCADA_GET_ATTRIBUTE_INFO inParam = new NET_IN_SCADA_GET_ATTRIBUTE_INFO(); // 获取设备点位信息 入参
            NET_OUT_SCADA_GET_ATTRIBUTE_INFO outParam = new NET_OUT_SCADA_GET_ATTRIBUTE_INFO(); // 获取设备点位信息 出参
            NET_GET_CONDITION_INFO conditionInfo = new NET_GET_CONDITION_INFO(); // 获取条件
            conditionInfo.szDeviceID = deviceIds.get(i).getBytes();
            conditionInfo.bIsSendID = 1;
            outParam.nMaxAttributeInfoNum = nMaxAttributeInfoNum;
            NET_ATTRIBUTE_INFO attributeInfo = new NET_ATTRIBUTE_INFO(); // 设备点位信息(内存由用户申请)

            outParam.pstuAttributeInfo = new Memory(nMaxAttributeInfoNum * attributeInfo.size());
            inParam.stuCondition = conditionInfo;
            inParam.write();
            outParam.write();
            boolean ret = LoginModule.netsdk.CLIENT_SCADAGetAttributeInfo(m_hLoginHandle, inParam.getPointer(), outParam.getPointer(), 3000);
            inParam.read();
            outParam.read();

            if (ret) {
                System.out.println("SCADAGetAttributeInfo succeed!");
                outParam.read();
                int retAttributeInfoNum = outParam.nRetAttributeInfoNum;
                System.out.println(outParam);

                NET_ATTRIBUTE_INFO infos[] = new NET_ATTRIBUTE_INFO[retAttributeInfoNum];
                for (int j = 0; j < retAttributeInfoNum; j++) {
                    infos[j] = new NET_ATTRIBUTE_INFO();
                }
                System.err.println("infos 大小： " + infos.length);
                ToolKits.GetPointerDataToStructArr(outParam.pstuAttributeInfo, infos);

                for (int n = 0; n < retAttributeInfoNum; n++) {
                    NET_ATTRIBUTE_INFO out = new NET_ATTRIBUTE_INFO();
                    out = infos[n];
                    model.addRow(new Object[]{deviceId, new String(out.szSignalName, encode).trim(), out.nDelay, out.bIsValid, num2PointType.get(out.emPointType)});

                    System.out.println("设备ID：" + deviceId
                            + "\n" + "点位类型: " + num2PointType.get(out.emPointType)
                            + "\n" + "点位名称: " + new String(out.szSignalName, encode).trim()
                            + "\n" + "时延: " + out.nDelay);

                }
            } else {
                JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(),
                        JOptionPane.ERROR_MESSAGE);
            }
        }


    }

    /**
     * 取消订阅监测点位实时信息
     */
    public void scadaDetachInfo() {
        if (attachInfoHandle.longValue() != 0) {
            LoginModule.netsdk.CLIENT_SCADADetachInfo(attachInfoHandle);
            //attachInfoHandle.setValue(0);
            contentPane.repaint();
            isBtnAttachInfo = false;
            attachInfoDataModel.getDataVector().clear();
            btnAttachInfo.setText(Res.string().getSCADAAttach()); // 订阅
            System.out.println("CLIENT_SCADADetachInfo: 取消订阅监测点位实时信息成功！");
        }
    }


    /**
     * 订阅报警信息
     */
    public void startListenEx() {
        if (!isListen) {

            LoginModule.netsdk.CLIENT_SetDVRMessCallBack(MessCallback.getInstance(alarmTable), null); // set alarm listen callback

            boolean b = LoginModule.netsdk.CLIENT_StartListenEx(m_hLoginHandle);
            if (b) {
                isListen = true;
                contentPane.repaint();
                btnStartListen.setText(Res.string().getCancel());
                //btnStopListen.setEnabled(true);
                System.out.println("CLIENT_StartListenEx success.");
            }
        } else {
            stopListen();
        }

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

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 只能选中一行
        // 列表显示居中
        DefaultTableCellRenderer dCellRenderer = new DefaultTableCellRenderer();
        dCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, dCellRenderer);
        return table;
    }


    /**
     * 停止监听
     */
    public void stopListen() {
        if (isListen) {
            if (LoginModule.netsdk.CLIENT_StopListen(m_hLoginHandle)) {
                isListen = false;
                contentPane.repaint();
                alarmModel.getDataVector().clear();
                btnStartListen.setText(Res.string().getStart());

            }
        }

    }

    // 获取界面窗口
    private static JFrame frame = new JFrame();


    /**
     * Create Frame
     */
    public SCADADemo() {
        setTitle(Res.string().getSCADA());
        setBounds(100, 100, 920, 750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setResizable(true);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoginModule.init(DefaultDisConnect.GetInstance(), DefaultHaveReconnect.getINSTANCE());
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Res.string().getLogin(),
                TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setBounds(0, 0, 905, 46);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel ipLabel = new JLabel(Res.string().getIp());
        ipLabel.setBounds(10, 15, 44, 21);
        panel.add(ipLabel);

        ipTextField = new JTextField();
        ipTextField.setText("172.3.0.223"); // 172.24.31.185 172.3.4.104 172.3.4.101
        ipTextField.setBounds(64, 15, 89, 21);
        panel.add(ipTextField);
        ipTextField.setColumns(10);

        JLabel portLabel = new JLabel(Res.string().getPort());
        portLabel.setBounds(174, 15, 44, 21);
        panel.add(portLabel);

        portTextField = new JTextField();
        portTextField.setText("37777");
        portTextField.setColumns(10);
        portTextField.setBounds(228, 15, 66, 21);
        panel.add(portTextField);

        JLabel lblName = new JLabel(Res.string().getUserName());
        lblName.setBounds(316, 15, 66, 21);
        panel.add(lblName);

        userNameTextField = new JTextField();
        userNameTextField.setText("admin");
        userNameTextField.setColumns(10);
        userNameTextField.setBounds(383, 15, 87, 21);
        panel.add(userNameTextField);

        JLabel lblPassword = new JLabel(Res.string().getPassword());
        lblPassword.setBounds(492, 15, 66, 21);
        panel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(568, 15, 112, 21);
        passwordField.setText("admin123");
        panel.add(passwordField);

        btnLogin = new JButton(Res.string().getLogin());
        btnLogin.setBounds(684, 14, 99, 23);
        panel.add(btnLogin);
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                login();
            }
        });

        btnLogout = new JButton(Res.string().getLogout());
        btnLogout.setBounds(785, 14, 110, 23);
        panel.add(btnLogout);
        btnLogout.setEnabled(false);
        btnLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                logout();


                LoginModule.cleanup();   // 关闭工程，释放资源
                dispose();
                // 返回主菜单
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        FunctionList demo = new FunctionList();
                        demo.setVisible(true);
                    }
                });
            }
        });

        // 设备列表
        JPanel devicesPanel = new JPanel();
        devicesPanel.setBorder(new TitledBorder(null, Res.string().getSCADADeviceList(), TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        devicesPanel.setBounds(0, 50, 450, 260);
        contentPane.add(devicesPanel);
        devicesPanel.setLayout(new BorderLayout(0, 0));

        btnGetDeviceList = new JButton(Res.string().getListBtn());
        btnGetDeviceList.setBounds(260, 315, 100, 29);
        contentPane.add(btnGetDeviceList);
        btnGetDeviceList.addMouseListener(

                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        getDeviceIdList();

                    }
                });
        devicesData = new Object[0][3];
        devicesDataTable = tableInit(devicesData, devicesDataTitle);
        devicesDataModel = (DefaultTableModel) devicesDataTable.getModel();
        JScrollPane deviceScrollPane = new JScrollPane(devicesDataTable);
        deviceScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        devicesPanel.add(deviceScrollPane, BorderLayout.CENTER);


        // 点位信息
        JPanel pointListPanel = new JPanel();
        pointListPanel.setBorder(new TitledBorder(null, Res.string().getSCADAPointList(), TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pointListPanel.setBounds(0, 350, 450, 260);
        contentPane.add(pointListPanel);
        pointListPanel.setLayout(new BorderLayout(0, 0));
        btnGetSCADAAttribute = new JButton(Res.string().getListBtn());
        btnGetSCADAAttribute.setBounds(260, 615, 100, 29);
        contentPane.add(btnGetSCADAAttribute);
        btnGetSCADAAttribute.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    getDeviceAttribute();
                } catch (UnsupportedEncodingException unsupportedEncodingException) {
                    unsupportedEncodingException.printStackTrace();
                }
            }
        });
        attributeData = new Object[0][3];
        attributeDataTable = tableInit(attributeData, attributeDataTitle);
        attributeDataModel = (DefaultTableModel) attributeDataTable.getModel();
        JScrollPane attributeDataScrollPane = new JScrollPane(attributeDataTable);
        attributeDataScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pointListPanel.add(attributeDataScrollPane, BorderLayout.CENTER);

        // 遥测
        JPanel attachAlarmPanel = new JPanel();
        attachAlarmPanel.setBorder(new TitledBorder(null, Res.string().getSCADAAlarmAttachInfo(), TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        attachAlarmPanel.setBounds(450, 50, 450, 170);
        contentPane.add(attachAlarmPanel);
        attachAlarmPanel.setLayout(new BorderLayout(0, 0));
        btnAlarmAttachInfo = new JButton(Res.string().getSCADAAttach());
        btnAlarmAttachInfo.setBounds(450, 225, 100, 29);
        contentPane.add(btnAlarmAttachInfo);
        btnAlarmAttachInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                scadaAlarmAttachInfo();
            }
        });
        alarmAttachInfoData = new Object[0][3];
        alarmAttachInfoDataTable = tableInit(alarmAttachInfoData, alarmAttachInfoDataTitle);
        alarmAttachInfoDataModel = (DefaultTableModel) alarmAttachInfoDataTable.getModel();
        JScrollPane alarmAttachInfoDataScrollPane = new JScrollPane(alarmAttachInfoDataTable);
        alarmAttachInfoDataScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        attachAlarmPanel.add(alarmAttachInfoDataScrollPane, BorderLayout.CENTER);

        // 普通报警
        JPanel attachPanel = new JPanel();
        attachPanel.setBorder(new TitledBorder(null, Res.string().getSCADAAttach(), TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        attachPanel.setBounds(450, 270, 450, 170);
        contentPane.add(attachPanel);
        attachPanel.setLayout(new BorderLayout(0, 0));
        btnStartListen = new JButton(Res.string().getSCADAAttach());
        btnStartListen.setBounds(450, 445, 100, 29);
        contentPane.add(btnStartListen);
        btnStartListen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                startListenEx();
            }
        });
//        btnStartListen.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                isBtnStartListen = !isBtnStartListen;
//                if (isBtnStartListen) {
//                    startListenEx();
//                    btnStartListen.setText(Res.string().getCancel());
//                } else {
//                    stopListen();
//                    btnStartListen.setText(Res.string().getSCADAAttach());
//                }
//                contentPane.add(btnStartListen);
//                contentPane.setOpaque(true);
//                contentPane.repaint();
//            }
//        });
        alarmData = new Object[0][3];
        alarmTable = tableInit(alarmData, alarmTableTitle);
        alarmModel = (DefaultTableModel) alarmTable.getModel();
        JScrollPane scrollPane = new JScrollPane(alarmTable);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        attachPanel.add(scrollPane, BorderLayout.CENTER);

        // 遥信 订阅监测点位实时信息
        boolean isBtnGetDeviceList = false;
        JPanel attachInfoPanel = new JPanel();
        attachInfoPanel.setBorder(new TitledBorder(null, Res.string().getSCADAAttachInfo(), TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        attachInfoPanel.setBounds(450, 480, 450, 170);
        contentPane.add(attachInfoPanel);
        attachInfoPanel.setLayout(new BorderLayout(0, 0));
        btnAttachInfo = new JButton(Res.string().getSCADAAttach()); // 订阅
        btnAttachInfo.setBounds(450, 655, 100, 29);
        contentPane.add(btnAttachInfo);
        btnAttachInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                scadaAttachInfo();
            }
        });
//        btnAttachInfo.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                scadaAttachInfo();
//            }
//        });

        attachInfoData = new Object[0][3];
        attachInfoDataTable = tableInit(attachInfoData, attachInfoDataTitle);
        attachInfoDataModel = (DefaultTableModel) attachInfoDataTable.getModel();
        JScrollPane attachInfoDataScrollPane = new JScrollPane(attachInfoDataTable);
        attachInfoDataScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        attachInfoPanel.add(attachInfoDataScrollPane, BorderLayout.CENTER);

        setButtonEnable(false);
        btnLogin.setEnabled(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (LoginModule.m_hLoginHandle.longValue() != 0) {
                    logout();
                }
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

    private boolean isBtnGetDeviceList = false;
    private boolean isBtnStartListen = false;
    private boolean isBtnAlarmAttachInfo = false;
    private boolean isBtnAttachInfo = false;
    private boolean isBtnPointList = false;

    // 普通订阅数据表
    private Object[][] alarmData;
    private JTable alarmTable;
    private final String[] alarmTableTitle = {Res.string().getDeviceID(), Res.string().getDeviceName(), Res.string().getChannel(), Res.string().getAlarmDescribe()};
    private DefaultTableModel alarmModel;

    // 设备列表
    private Object[][] devicesData;
    private JTable devicesDataTable;
    private final String[] devicesDataTitle = {Res.string().getDeviceID(), Res.string().getDeviceName()};
    private DefaultTableModel devicesDataModel;

    // 报警列表
    private Object[][] alarmAttachInfoData;
    private JTable alarmAttachInfoDataTable;
    private final String[] alarmAttachInfoDataTitle = {Res.string().getDeviceID(), Res.string().getPointID(), Res.string().getAlarmDescribe(), Res.string().getCollectTime(), Res.string().getAlarmLevel()};
    private DefaultTableModel alarmAttachInfoDataModel;


    // 实时信息列表
    private Object[][] attachInfoData;
    private JTable attachInfoDataTable;
    private final String[] attachInfoDataTitle = {Res.string().getDeviceName(), Res.string().getCollectTime(), Res.string().getPointID()};
    private DefaultTableModel attachInfoDataModel;

    // 点位信息列表
    private Object[][] attributeData;
    private JTable attributeDataTable;
    private final String[] attributeDataTitle = {Res.string().getDeviceID(), Res.string().getPointName(), Res.string().getAlarmDelay(), Res.string().getIfValidSignalPoint(), Res.string().getAlarmType()};
    private DefaultTableModel attributeDataModel;

    // 设备断线通知回调
    private DisConnect disConnectCallback = new DisConnect();

    /////////////////面板///////////////////
    // 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
    private static class DisConnect implements NetSDKLib.fDisConnect {
        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
            // 断线提示
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    frame.setTitle(Res.string().getSCADA() + " : " + Res.string().getDisConnectReconnecting());
                }
            });
        }
    }

//    // 网络连接恢复，设备重连成功回调
//    // 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
//    private static class HaveReConnect implements NetSDKLib.fHaveReConnect {
//        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
//            System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
//
//            // 重连提示
//            SwingUtilities.invokeLater(new Runnable() {
//                public void run() {
//                    frame.setTitle(Res.string().getPowerEnvironmentMonitor() + " : " + Res.string().getOnline());
//                }
//            });
//        }
//    }

    /**
     * 订阅监测点位信息回调
     */
    private static class SCADAAttachInfoCallBack implements NetSDKLib.fSCADAAttachInfoCallBack {
        private JTable attachInfoTable;
        private static SCADAAttachInfoCallBack INSTANCE;

        private SCADAAttachInfoCallBack(JTable attachInfoTable) {
            this.attachInfoTable = attachInfoTable;
        }

        public static final SCADAAttachInfoCallBack getInstance(JTable table) {
            if (INSTANCE == null) {
                INSTANCE = new SCADAAttachInfoCallBack(table);
            }
            if (table != null) {
                INSTANCE.attachInfoTable = table;
            }
            return INSTANCE;
        }

        @Override
        public void invoke(NetSDKLib.LLong lLoginID, NetSDKLib.LLong lAttachHandle,
                           NetSDKLib.NET_SCADA_NOTIFY_POINT_INFO_LIST pInfo, int nBufLen, Pointer dwUser) {
            System.out.println("————————————————————【订阅监测点位信息回调】————————————————————");
//            for (int i = 0; i < pInfo.nList; i++) {
//                System.out.println(" 设备名称:" + new String(pInfo.stuList[i].szDevName).trim());
//                System.out.println(" 点位名(与点位表的取值一致):" + new String(pInfo.stuList[i].szPointName).trim());
//                System.out.println(" 现场监控单元ID:" + new String(pInfo.stuList[i].szFSUID).trim());
//                System.out.println(" 点位ID:" + new String(pInfo.stuList[i].szID).trim());
//                System.out.println(" 探测器ID:" + new String(pInfo.stuList[i].szSensorID).trim());
//                System.out.println(" 点位类型:" + pInfo.stuList[i].emPointType);
//                System.out.println(" 采集时间 : " + pInfo.stuList[i].stuCollectTime.toStringTime());
//            }

            //更新列表
            if (attachInfoTable != null) {
                DefaultTableModel model = (DefaultTableModel) attachInfoTable.getModel();
                for (int i = 0; i < pInfo.nList; i++) {
                    String deviceName = new String(pInfo.stuList[i].szDevName, encode).trim();
                    String time = pInfo.stuList[i].stuCollectTime.toStringTime();
                    System.out.println(" 设备名称:" + deviceName);
                    System.out.println(" 采集时间:" + time);
                    System.out.println(" 点位ID:" + new String(pInfo.stuList[i].szID).trim());
                    model.addRow(new Object[]{deviceName, time, new String(pInfo.stuList[i].szID).trim()});
                }
            }

            System.out.println("————————————————————【订阅监测点位信息回调】————————————————————");

        }
    }
}
