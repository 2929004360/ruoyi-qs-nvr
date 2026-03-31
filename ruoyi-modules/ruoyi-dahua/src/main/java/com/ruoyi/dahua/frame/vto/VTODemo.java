package com.ruoyi.dahua.frame.vto;

import com.ruoyi.dahua.common.FunctionList;
import com.ruoyi.dahua.common.PaintPanel;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.CtrlType;
import com.ruoyi.dahua.lib.NetSDKLib.EM_OPEN_DOOR_TYPE;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.NetSDKLib.NET_CTRL_ACCESS_OPEN;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.*;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VTODemo extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField ipTextField;
    private JTextField portTextField;
    private JTextField userNameTextField;
    private JPasswordField passwordField;
    private JTable alarmTable;

    private Panel realPlay;
    private PaintPanel imagePanel;
    private JButton btnLogin;
    private JButton btnLogout;
    private JButton btnRealPlay;
    private JButton btnStopplay;
    private JButton btnTalk;
    private JButton btnStoptalk;
    private JButton btnOpendoor;
    private JButton btnClosedoor;
    private JButton btnOperate;
    private JButton btnStartlisten;
    private JButton btnStoplisten;
    private JButton btnStartrealload;
    private JButton btnStoprealload;
    JTabbedPane tabbedPane;
    private static boolean b_RealPlay = false;
    private static boolean b_Attachment = false;
    private boolean isListen = false;

///////////////////// 主面板 /////////////////////

    private static JFrame mainFrame = new JFrame();
    private OperateManager manager = new OperateManager();

    private Object[][] alarmData;
    private Object[][] realData;

    private DefaultTableModel alarmModel;
    private DefaultTableModel realModel;

    private final String[] alarmTableTitle = {Res.string().getVTOAlarmEventRoomNo(),
            Res.string().getVTOAlarmEventCardNo(), Res.string().getVTOAlarmEventTime(),
            Res.string().getVTOAlarmEventOpenMethod(), Res.string().getVTOAlarmEventStatus()};
    private final String[] realTableTitle = {Res.string().getVTORealLoadRoomNO(), Res.string().getVTORealLoadCardNo(),
            Res.string().getVTORealLoadTime(), Res.string().getVTORealLoadEventInfo()};

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VTODemo frame = new VTODemo();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public VTODemo() {
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(Res.string().getVTO());
        setBounds(100, 100, 920, 651);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
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
        ipTextField.setText("10.34.3.63");
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
        btnLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                logout();
            }
        });
        JPanel previewPanel = new JPanel();
        previewPanel.setBorder(
                new TitledBorder(null, Res.string().getRealplay(), TitledBorder.LEFT, TitledBorder.TOP, null, null));
        previewPanel.setBounds(10, 56, 409, 313);
        contentPane.add(previewPanel);
        previewPanel.setLayout(new BorderLayout(0, 0));

        realPlay = new Panel();
        realPlay.setBackground(Color.GRAY);
        previewPanel.add(realPlay, BorderLayout.CENTER);

        JPanel operatePanel = new JPanel();
        operatePanel.setBorder(
                new TitledBorder(null, Res.string().getOperate(), TitledBorder.LEFT, TitledBorder.TOP, null, null));
        operatePanel.setBounds(429, 56, 452, 194);
        contentPane.add(operatePanel);
        operatePanel.setLayout(null);

        btnRealPlay = new JButton(Res.string().getStartRealPlay());
        btnRealPlay.setBounds(37, 23, 162, 29);
        operatePanel.add(btnRealPlay);
        btnRealPlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                realPlay();
            }
        });
        btnTalk = new JButton(Res.string().getStartTalk());
        btnTalk.setBounds(248, 23, 152, 29);
        operatePanel.add(btnTalk);
        btnTalk.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                talk();
            }
        });
        btnStopplay = new JButton(Res.string().getStopRealPlay());
        btnStopplay.setBounds(37, 62, 162, 29);
        operatePanel.add(btnStopplay);
        btnStopplay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                stopPlay();
            }
        });
        btnStoptalk = new JButton(Res.string().getStopTalk());
        btnStoptalk.setBounds(248, 62, 152, 29);
        operatePanel.add(btnStoptalk);
        btnStoptalk.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                stopTalk();
            }
        });
        btnOpendoor = new JButton(Res.string().getDoorOpen());
        btnOpendoor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                openDoor();
            }
        });
        btnOpendoor.setBounds(37, 110, 162, 29);
        operatePanel.add(btnOpendoor);

        btnClosedoor = new JButton(Res.string().getDoorClose());
        btnClosedoor.setBounds(37, 149, 162, 29);
        operatePanel.add(btnClosedoor);
        btnClosedoor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                closeDoor();
            }
        });
        btnOperate = new JButton(Res.string().getCardOperate());
        btnOperate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (manager == null) {
                    manager = new OperateManager();
                }
                manager.setVisible(true);
            }
        });
        btnOperate.setBounds(248, 125, 152, 29);
        operatePanel.add(btnOperate);

        JPanel eventOperate = new JPanel();
        eventOperate.setBorder(new TitledBorder(null, Res.string().getEventOperate(), TitledBorder.LEFT,
                TitledBorder.TOP, null, null));
        eventOperate.setBounds(429, 260, 452, 104);
        contentPane.add(eventOperate);
        eventOperate.setLayout(null);

        btnStartlisten = new JButton(Res.string().getStartListen());
        btnStartlisten.setBounds(35, 21, 178, 29);
        eventOperate.add(btnStartlisten);
        btnStartlisten.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isListen = startListen();
                tabbedPane.setSelectedIndex(0);
            }
        });

        btnStoplisten = new JButton(Res.string().getStopListen());
        btnStoplisten.setBounds(35, 60, 178, 29);
        eventOperate.add(btnStoplisten);
        btnStoplisten.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //table清空数据
                ((DefaultTableModel) alarmTable.getModel()).setRowCount(0);
                stopListen();
            }
        });

        btnStartrealload = new JButton(Res.string().getStartRealLoad());
        btnStartrealload.setBounds(234, 21, 195, 29);
        eventOperate.add(btnStartrealload);
        btnStartrealload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startRealLoad();
                tabbedPane.setSelectedIndex(1);
            }
        });

        btnStoprealload = new JButton(Res.string().getStopRealLoad());
        btnStoprealload.setBounds(234, 60, 195, 29);
        eventOperate.add(btnStoprealload);
        btnStoprealload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //清空table数据
                ((DefaultTableModel) realLoadTable.getModel()).setRowCount(0);
                stopRealLoad();
                //图片清空
                imagePanel.setImage(null);
                imagePanel.repaint();
            }
        });

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 379, 871, 224);
        contentPane.add(tabbedPane);

        JPanel alarmPanel = new JPanel();
        alarmPanel.setBorder(
                new TitledBorder(null, Res.string().getEventInfo(), TitledBorder.LEFT, TitledBorder.TOP, null, null));
        tabbedPane.addTab(Res.string().getAlarmEvent(), null, alarmPanel, null);
        alarmPanel.setLayout(new BorderLayout(0, 0));

        alarmData = new Object[0][5];
        alarmTable = tableInit(alarmData, alarmTableTitle);
        alarmModel = (DefaultTableModel) alarmTable.getModel();
        JScrollPane scrollPane = new JScrollPane(alarmTable);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        alarmPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel realLoadPanel = new JPanel();
        realLoadPanel.setBorder(new TitledBorder(null, Res.string().getVTORealLoadEventInfo(), TitledBorder.LEFT,
                TitledBorder.TOP, null, null));
        tabbedPane.addTab(Res.string().getRealLoadEvent(), null, realLoadPanel, null);
        realLoadPanel.setLayout(null);

        imagePanel = new PaintPanel();
        imagePanel.setBounds(671, 20, 185, 165);
        realLoadPanel.add(imagePanel);
        realData = new Object[0][4];
        realLoadTable = tableInit(realData, realTableTitle);
        realModel = (DefaultTableModel) realLoadTable.getModel();
        realScrollPane = new JScrollPane(realLoadTable);
        realScrollPane.setBounds(10, 20, 654, 165);
        realScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        realLoadPanel.add(realScrollPane);

        btnEnable(false);
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

    /**
     * 按钮使能
     *
     * @param enable
     */
    public void btnEnable(boolean enable) {
        btnLogin.setEnabled(enable);
        btnLogout.setEnabled(enable);
        btnRealPlay.setEnabled(enable);
        btnStopplay.setEnabled(enable);
        btnTalk.setEnabled(enable);
        btnStoptalk.setEnabled(enable);
        btnOpendoor.setEnabled(enable);
        btnClosedoor.setEnabled(enable);
        btnOperate.setEnabled(enable);
        btnStartlisten.setEnabled(enable);
        btnStoplisten.setEnabled(enable);
        btnStartrealload.setEnabled(enable);
        btnStoprealload.setEnabled(enable);
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


        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 只能选中一行

        // 列表显示居中
        DefaultTableCellRenderer dCellRenderer = new DefaultTableCellRenderer();
        dCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, dCellRenderer);
        return table;
    }

    /**
     * 登录
     */
    public boolean login() {
        if (LoginModule.login(ipTextField.getText(), Integer.parseInt(portTextField.getText()),
                userNameTextField.getText(), new String(passwordField.getPassword()))) {
            btnEnable(true);
            btnLogin.setEnabled(false);
            // 监听按钮使能
            btnRealPlay.setEnabled(true);
            btnStopplay.setEnabled(false);
            btnStartlisten.setEnabled(true);
            btnStoplisten.setEnabled(false);
            btnStartrealload.setEnabled(true);
            btnStoprealload.setEnabled(false);
            btnTalk.setEnabled(true);
            btnStoptalk.setEnabled(false);
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
        if (b_RealPlay) {
            stopPlay();
        }
        if (TalkModule.m_hTalkHandle != null) {
            stopTalk();
        }
        if (isListen) {
            stopListen();
            isListen = false;
        }
        stopRealLoad();
        LoginModule.logout();
        btnEnable(false);
        //清空两个表格
        //普通事件table清空数据
        ((DefaultTableModel) alarmTable.getModel()).setRowCount(0);
        //智能事件table清空数据
        ((DefaultTableModel) realLoadTable.getModel()).setRowCount(0);
        //图片清空
        imagePanel.setImage(null);
        imagePanel.repaint();
        btnLogin.setEnabled(true);
    }

    private LLong m_hPlayHandle;

    public void realPlay() {
        if (!b_RealPlay) {
            m_hPlayHandle = RealPlayModule.startRealPlay(0, 0, realPlay);
            if (m_hPlayHandle.longValue() != 0) {
                b_RealPlay = true;
                btnRealPlay.setEnabled(false);
                btnStopplay.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * 停止实时预览
     */
    public void stopPlay() {
        if (b_RealPlay) {
            RealPlayModule.stopRealPlay(m_hPlayHandle);
            realPlay.repaint();
            b_RealPlay = false;
            btnRealPlay.setEnabled(true);
            btnStopplay.setEnabled(false);
        }
    }

    /**
     * 对讲
     */
    public void talk() {
//        if (TalkModule.startTalk(0, 0)) {
//            btnTalk.setEnabled(false);
//            btnStoptalk.setEnabled(true);
//        } else {
//            JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(),
//                    JOptionPane.ERROR_MESSAGE);
//        }
    }

    /**
     * 停止对讲
     */
    public void stopTalk() {
        TalkModule.stopTalk();
        btnTalk.setEnabled(true);
        btnStoptalk.setEnabled(false);
    }

    /**
     * 开门
     */
    public void openDoor() {
        NET_CTRL_ACCESS_OPEN openInfo = new NET_CTRL_ACCESS_OPEN();
        openInfo.nChannelID = 0;
        openInfo.emOpenDoorType = EM_OPEN_DOOR_TYPE.EM_OPEN_DOOR_TYPE_REMOTE;

        Pointer pointer = new Memory(openInfo.size());
        ToolKits.SetStructDataToPointer(openInfo, pointer, 0);
        boolean ret = LoginModule.netsdk.CLIENT_ControlDeviceEx(LoginModule.m_hLoginHandle,
                CtrlType.CTRLTYPE_CTRL_ACCESS_OPEN, pointer, null, 10000);
        if (!ret) {
            JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    /**
     * 关门
     */
    public void closeDoor() {
        final NetSDKLib.NET_CTRL_ACCESS_CLOSE close = new NetSDKLib.NET_CTRL_ACCESS_CLOSE();
        close.nChannelID = 0; // 对应的门编号 - 如何开全部的门
        close.write();
        Pointer pointer = new Memory(close.size());
        boolean result = LoginModule.netsdk.CLIENT_ControlDeviceEx(LoginModule.m_hLoginHandle,
                CtrlType.CTRLTYPE_CTRL_ACCESS_CLOSE, close.getPointer(), null, 5000);
        close.read();
        if (!result) {
            JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 监听事件
     */
    public boolean startListen() {
        if (AlarmListenModule.startListen(VTOMessageCallBack.getINSTANCE(alarmTable, null))) {
            btnStartlisten.setEnabled(false);
            btnStoplisten.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(),
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * 停止监听
     */
    public void stopListen() {
        if (isListen) {
            if (AlarmListenModule.stopListen()) {
                isListen = false;
                btnStartlisten.setEnabled(true);
                btnStoplisten.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private LLong m_attachHandle;
    private JTable realLoadTable;
    private JScrollPane realScrollPane;

    /**
     * 智能事件监听
     */
    public void startRealLoad() {
        m_attachHandle = GateModule.realLoadPic(0, VTOAnalyzerCallBack.getINSTANCE(realLoadTable, imagePanel));
        if (m_attachHandle != null && m_attachHandle.longValue() != 0) {
            btnStartrealload.setEnabled(false);
            btnStoprealload.setEnabled(true);
        }
    }

    /**
     * 停止智能事件监听
     */
    public void stopRealLoad() {
        if (m_attachHandle != null && m_attachHandle.longValue() != 0) {
            GateModule.stopRealLoadPic(m_attachHandle);
            m_attachHandle.setValue(0);
            btnStartrealload.setEnabled(true);
            btnStoprealload.setEnabled(false);
        }

    }

}
