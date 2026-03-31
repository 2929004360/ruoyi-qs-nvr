package com.ruoyi.dahua.frame;

import com.ruoyi.dahua.common.*;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.LoginModule;
import com.ruoyi.dahua.module.RealPlayModule;
import com.ruoyi.dahua.module.TargetRecognitionModule;
import com.ruoyi.dahua.module.VideoStateSummaryModule;
import com.sun.jna.Pointer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 人数统计事件 demo
 */
class HumanNumberStatisticFrame extends JFrame {

    private static final long serialVersionUID = 1L;


    /*
     * 界面、SDK初始化及登录
     */
    public HumanNumberStatisticFrame() {
        setTitle(Res.string().getHumanNumberStatistic());
        setLayout(new BorderLayout());
        pack();
        setSize(1080, 560);
        setResizable(false);
        setLocationRelativeTo(null);
        LoginModule.init(disConnectCB, haveReConnectCB);   // 打开工程，SDK初始化，注册断线和重连回调函数

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        loginPanel = new LoginPanel();
        humanStatisticPanel = new HumanStatisticPanel();

        add(loginPanel, BorderLayout.NORTH);
        add(humanStatisticPanel, BorderLayout.CENTER);

        // 调用按钮登录事件
        loginPanel.addLoginBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginPanel.checkLoginText()) {
                    if (login()) {
                        mainFrame = ToolKits.getFrame(e);
                        mainFrame.setTitle(Res.string().getHumanNumberStatistic() + " : " + Res.string().getOnline());
                    }
                }
            }
        });
        // 调用按钮登出事件
        loginPanel.addLogoutBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setTitle(Res.string().getHumanNumberStatistic());
                logout();
            }
        });

        // 注册窗体清出事件
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                RealPlayModule.stopRealPlay(m_hPlayHandle);   // 退出句柄
                TargetRecognitionModule.renderPrivateData(m_hPlayHandle, 0);  // 关闭规则框
                VideoStateSummaryModule.detachAllVideoStatSummary(); // 退订事件
                LoginModule.logout();    // 退出
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
    }

    /////////////////////////////// 登录相关 //////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////

    // 设备断线通知回调
    private static DisConnectCallBack disConnectCB = new DisConnectCallBack();

    // 网络连接恢复
    private static HaveReConnectCallBack haveReConnectCB = new HaveReConnectCallBack();

    private Vector<String> chnList = new Vector<String>();

    // 预览句柄
    public static NetSDKLib.LLong m_hPlayHandle = new NetSDKLib.LLong(0);

    // 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
    private static class DisConnectCallBack implements NetSDKLib.fDisConnect {
        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            System.out.printf("Device[%s] Port[%d] DisConnectCallBack!\n", pchDVRIP, nDVRPort);
            // 断线提示
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    mainFrame.setTitle(Res.string().getHumanNumberStatistic() + " : " + Res.string().getDisConnectReconnecting());
                }
            });
        }

    }

    // 网络连接恢复，设备重连成功回调
    // 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
    private static class HaveReConnectCallBack implements NetSDKLib.fHaveReConnect {
        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);

            // 重连提示
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    mainFrame.setTitle(Res.string().getHumanNumberStatistic() + " : " + Res.string().getOnline());
                }
            });

            // 断线后需要重新订阅
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(new Runnable() {
                @Override
                public void run() {

                    if (b_RealPlay) {     // 如果断前正在预览
                        stopRealPlay();   // 退出预览
                        realPlay();       // 重新开启预览
                    }

                    if (b_Attachment) {   // 如果断前正在订阅
                        // 重订阅事件
                        VideoStateSummaryModule.reAttachAllVideoStatSummary(humanNumberStatisticCB);
                        setAttachBtnTextEnable();
                    }
                }
            });
            service.shutdown();
        }

    }

    // 登录
    public boolean login() {
        if (LoginModule.login(loginPanel.ipTextArea.getText(),
                Integer.parseInt(loginPanel.portTextArea.getText()),
                loginPanel.nameTextArea.getText(),
                new String(loginPanel.passwordTextArea.getPassword()))) {

            loginPanel.setButtonEnable(true);
            setButtonEnable(true);

            final int chanNum = LoginModule.m_stDeviceInfo.byChanNum;

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    // 更新列表长度
                    int listSize = Math.max(chanNum, 32);
                    groupListPanel.remove(scrollPane);
                    groupListPanel.creatGroupInfoPanel(listSize);

                    // 登陆成功，将通道添加到控件
                    for (int i = 0; i < chanNum; i++) {
                        chnList.add(Res.string().getChannel() + " " + String.valueOf(i + 1));
                        SummaryInfo summaryInfo = new SummaryInfo();
                        summaryInfo.nChannelID = i;
                        EventDisplay.dataList.add(summaryInfo);
                    }
                    chnComboBox.setModel(new DefaultComboBoxModel(chnList));

                    setEnableAllInnerComponent(controlPanel, true);

                    EventDisplay.setEventInfo(groupInfoTable, EventDisplay.dataList);
                }
            });

        } else {
            JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // 登出
    public void logout() {

        VideoStateSummaryModule.detachAllVideoStatSummary();  // 退订阅
        stopRealPlay(); //退出播放
        LoginModule.logout();  // 退出登录

        chnList.clear();   // 清除通道号列表
        EventDisplay.clearEventInfoList();  // 清除事件列表数据
        chnComboBox.setModel(new DefaultComboBoxModel());

        loginPanel.setButtonEnable(false);
        setAttachBtnTextDisable();
        setEnableAllInnerComponent(controlPanel, false);
    }

    /////////////////////////////// 人数统计事件  ////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    private static boolean b_RealPlay = false;
    private static boolean b_Attachment = false;

    /*
     * 一级面板：人数统计控制面板
     */
    private class HumanStatisticPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public HumanStatisticPanel() {
            setLayout(new BorderLayout());
            Dimension dim = getPreferredSize();
            dim.width = 320;
            setPreferredSize(dim);

            humanStatisticAttachPanel = new HumanStatisticControlPanel(); // 人数统计控制面板
            realPanel = new RealPanel();            // 实时显示面啊
            groupListPanel = new GroupListPanel();  // 事件展示面板

            add(humanStatisticAttachPanel, BorderLayout.NORTH);
            add(realPanel, BorderLayout.EAST);
            add(groupListPanel, BorderLayout.CENTER);
        }
    }

    /*
     * 二级面板： 控制面板 通道、码流设置，事件订阅
     */
    private class HumanStatisticControlPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public HumanStatisticControlPanel() {
            BorderEx.set(this, Res.string().getHumanNumberStatisticAttach(), 2);
            setLayout(new FlowLayout());

            /* 预览控制面板 */
            controlPanel = new Panel();
            add(controlPanel);

            chnLabel = new JLabel(Res.string().getChannel());
            chnComboBox = new JComboBox();

            streamLabel = new JLabel(Res.string().getStreamType());
            String[] stream = {Res.string().getMasterStream(), Res.string().getSubStream()};
            streamComboBox = new JComboBox(stream);
            realPlayBtn = new JButton(Res.string().getStartRealPlay());
            attachBtn = new JButton(Res.string().getAttach());
            clearBtn = new JButton(Res.string().getHumanNumberStatisticEventClearOSD());

            controlPanel.setLayout(new FlowLayout());
            controlPanel.add(chnLabel);
            controlPanel.add(chnComboBox);
            controlPanel.add(streamLabel);
            controlPanel.add(streamComboBox);
            controlPanel.add(realPlayBtn);
            controlPanel.add(attachBtn);
            controlPanel.add(clearBtn);

            chnComboBox.setPreferredSize(new Dimension(90, 20));
            streamComboBox.setPreferredSize(new Dimension(120, 20));
            realPlayBtn.setPreferredSize(new Dimension(120, 20));
            attachBtn.setPreferredSize(new Dimension(120, 20));
            clearBtn.setPreferredSize(new Dimension(120, 20));

            chnComboBox.setEnabled(false);
            streamComboBox.setEnabled(false);
            realPlayBtn.setEnabled(false);
            attachBtn.setEnabled(false);
            clearBtn.setEnabled(false);

            realPlayBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    realPlay();
                }
            });

            attachBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {

                    int channel = chnComboBox.getSelectedIndex();

                    if (!VideoStateSummaryModule.channelAttached(channel)) {
                        if (VideoStateSummaryModule.attachVideoStatSummary(channel, humanNumberStatisticCB)) {
                            setAttachBtnTextEnable();
                        } else {
                            JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        if (VideoStateSummaryModule.detachVideoStatSummary(channel)) {
                            setAttachBtnTextDisable();
                            SummaryInfo info = new SummaryInfo();
                            info.nChannelID = channel;
                            EventDisplay.dataList.add(channel, info);
                            EventDisplay.dataList.remove(channel + 1);
                            EventDisplay.setEventInfo(groupInfoTable, EventDisplay.dataList);
                        } else {
                            JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            // 添加下拉框事件监听器
            chnComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        int channel = chnComboBox.getSelectedIndex();
                        if (VideoStateSummaryModule.channelAttached(channel)) {
                            setAttachBtnTextEnable();
                        } else {
                            setAttachBtnTextDisable();
                        }
                    }
                }
            });

            clearBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clearSummaryInfo();
                }
            });
        }
    }

    private static void setRealPlayBtnTextEnable() {
        b_RealPlay = true;
        realPlayBtn.setText(Res.string().getStopRealPlay());
    }

    private static void setRealPlayBtnTextDisable() {
        b_RealPlay = false;
        realPlayBtn.setText(Res.string().getStartRealPlay());
    }

    private static void setAttachBtnTextEnable() {
        b_Attachment = VideoStateSummaryModule.getM_hAttachMap().size() > 0;
        attachBtn.setText(Res.string().getDetach());
    }

    private static void setAttachBtnTextDisable() {
        b_Attachment = VideoStateSummaryModule.getM_hAttachMap().size() > 0;
        attachBtn.setText(Res.string().getAttach());
    }

    private static void setButtonEnable(boolean bln) {
        realPlayWindow.setEnabled(bln);
        chnComboBox.setEnabled(bln);
        streamComboBox.setEnabled(bln);
        realPlayBtn.setEnabled(bln);
        attachBtn.setEnabled(bln);
        clearBtn.setEnabled(bln);
    }

    // 启用/禁用 Container 内所有组件
    public static void setEnableAllInnerComponent(Component container, boolean enable) {
        for (Component component : getComponents(container)) {
            component.setEnabled(enable);
        }
    }

    // 获取 Swing Container 内所有的非 Container 组件
    public static Component[] getComponents(Component container) {
        ArrayList<Component> list = null;

        try {
            list = new ArrayList<Component>(Arrays.asList(
                    ((Container) container).getComponents()));
            for (int index = 0; index < list.size(); index++) {
                list.addAll(Arrays.asList(getComponents(list.get(index))));
            }
        } catch (ClassCastException e) {
            list = new ArrayList<Component>();
        }

        return list.toArray(new Component[0]);
    }

    private void clearSummaryInfo() {
        VideoStateSummaryModule.clearVideoStateSummary(chnComboBox.getSelectedIndex());
    }

    /*
     * 二级面板：预览面板
     */
    private class RealPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public RealPanel() {
            BorderEx.set(this, Res.string().getRealplay(), 2);
            Dimension dim = this.getPreferredSize();
            dim.width = 420;
            this.setPreferredSize(dim);
            this.setLayout(new BorderLayout());

            realPlayPanel = new JPanel();
            add(realPlayPanel, BorderLayout.CENTER);

            /************ 预览面板 **************/
            realPlayPanel.setLayout(new BorderLayout());
            realPlayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            realPlayWindow = new Panel();
            realPlayWindow.setBackground(Color.GRAY);
            realPlayWindow.setSize(480, 480);
            realPlayPanel.add(realPlayWindow, BorderLayout.CENTER);
        }
    }

    // 预览
    public static void realPlay() {
        if (!b_RealPlay) {
            m_hPlayHandle = RealPlayModule.startRealPlay(
                    chnComboBox.getSelectedIndex(),
                    streamComboBox.getSelectedIndex() == 0 ? 0 : 3,
                    realPlayWindow);

            if (m_hPlayHandle.longValue() != 0) {     // 正常状态下句柄不为空
                TargetRecognitionModule.renderPrivateData(m_hPlayHandle, 1); // 开启规则框
                realPlayWindow.repaint();
                chnComboBox.setEnabled(false);
                streamComboBox.setEnabled(false);
                setRealPlayBtnTextEnable();
            }
        } else {
            stopRealPlay();
        }
    }

    public static void stopRealPlay() {
        RealPlayModule.stopRealPlay(m_hPlayHandle);   // 为空则说明失败，退出拉流
        TargetRecognitionModule.renderPrivateData(m_hPlayHandle, 0); // 关闭规则框
        realPlayWindow.repaint();
        chnComboBox.setEnabled(true);
        streamComboBox.setEnabled(true);
        setRealPlayBtnTextDisable();
    }

    // 搜索数据列表
    public class GroupListPanel extends JPanel {

        private Object[][] statisticData = null;    // 人脸库列表

        private final String[] groupName = {
                Res.string().getHumanNumberStatisticEventChannel(),
                Res.string().getHumanNumberStatisticEventTime(),
                Res.string().getHumanNumberStatisticEventHourIn(),
                Res.string().getHumanNumberStatisticEventTodayIn(),
                Res.string().getHumanNumberStatisticEventTotalIn(),
                Res.string().getHumanNumberStatisticEventHourOut(),
                Res.string().getHumanNumberStatisticEventTodayOut(),
                Res.string().getHumanNumberStatisticEventTotalOut()
        };
        private DefaultTableModel groupInfoModel;

        public GroupListPanel() {
            BorderEx.set(this, Res.string().getHumanNumberStatisticEventTitle(), 2);
            setLayout(new BorderLayout());

            statisticData = new Object[32][9];
            creatGroupInfoPanel(32);
        }

        private void creatGroupInfoPanel(int listSize) {
            statisticData = new Object[listSize][9];    // 人脸库列表集合修改
            groupInfoModel = new DefaultTableModel(statisticData, groupName);
            groupInfoTable = new JTable(groupInfoModel) {
                @Override    // 不可编辑
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            groupInfoTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            groupInfoTable.getColumnModel().getColumn(1).setPreferredWidth(80);
            groupInfoTable.getColumnModel().getColumn(2).setPreferredWidth(10);
            groupInfoTable.getColumnModel().getColumn(3).setPreferredWidth(10);
            groupInfoTable.getColumnModel().getColumn(4).setPreferredWidth(10);
            groupInfoTable.getColumnModel().getColumn(5).setPreferredWidth(10);
            groupInfoTable.getColumnModel().getColumn(6).setPreferredWidth(10);
            groupInfoTable.getColumnModel().getColumn(7).setPreferredWidth(10);

            groupInfoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 只能选中一行

            // 列表显示居中
            DefaultTableCellRenderer dCellRenderer = new DefaultTableCellRenderer();
            dCellRenderer.setHorizontalAlignment(JLabel.CENTER);
            groupInfoTable.setDefaultRenderer(Object.class, dCellRenderer);

            scrollPane = new JScrollPane(groupInfoTable);
            this.add(scrollPane, BorderLayout.CENTER);
        }
    }

    // 人数统计回调事件
    public static fHumanNumberStatisticCallBack humanNumberStatisticCB = fHumanNumberStatisticCallBack.getInstance();

    public static class fHumanNumberStatisticCallBack implements NetSDKLib.fVideoStatSumCallBack {

        private static fHumanNumberStatisticCallBack instance = new fHumanNumberStatisticCallBack();

        public static fHumanNumberStatisticCallBack getInstance() {
            return instance;
        }

        private EventTaskCommonQueue eventTaskQueue = new EventTaskCommonQueue();

        public fHumanNumberStatisticCallBack() {
            eventTaskQueue.init();
        }

        public void invoke(NetSDKLib.LLong lAttachHandle, NetSDKLib.NET_VIDEOSTAT_SUMMARY stVideoState, int dwBufLen, Pointer dwUser) {

            SummaryInfo summaryInfo = new SummaryInfo(
                    stVideoState.nChannelID, stVideoState.stuTime.toStringTime(),
                    stVideoState.stuEnteredSubtotal.nToday,
                    stVideoState.stuEnteredSubtotal.nHour,
                    stVideoState.stuEnteredSubtotal.nTotal,
                    stVideoState.stuExitedSubtotal.nToday,
                    stVideoState.stuExitedSubtotal.nHour,
                    stVideoState.stuExitedSubtotal.nTotal);
            System.out.printf("Channel[%d] GetTime[%s]\n" +
                            "People In  Information[Total[%d] Hour[%d] Today[%d]]\n" +
                            "People Out Information[Total[%d] Hour[%d] Today[%d]]\n",
                    summaryInfo.nChannelID, summaryInfo.eventTime,
                    summaryInfo.enteredTotal, summaryInfo.enteredHour, summaryInfo.enteredToday,
                    summaryInfo.exitedTotal, summaryInfo.exitedHour, summaryInfo.exitedToday);
            eventTaskQueue.addEvent(new EventDisplay(summaryInfo));
        }
    }

    private static class SummaryInfo {

        public int nChannelID;
        public String eventTime;
        public int enteredToday;
        public int enteredHour;
        public int enteredTotal;
        public int exitedToday;
        public int exitedHour;
        public int exitedTotal;

        public SummaryInfo() {
        }

        public SummaryInfo(int nChannelID, String eventTime,
                           int enteredToday, int enteredHour,
                           int enteredTotal, int exitedToday,
                           int exitedHour, int exitedTotal) {
            this.nChannelID = nChannelID;
            this.eventTime = eventTime;
            this.enteredToday = enteredToday;
            this.enteredHour = enteredHour;
            this.enteredTotal = enteredTotal;
            this.exitedToday = exitedToday;
            this.exitedHour = exitedHour;
            this.exitedTotal = exitedTotal;
        }

    }

    private static class EventDisplay implements EventTaskHandler {

        private static List<SummaryInfo> dataList = new LinkedList<SummaryInfo>();

        private int getMaxSize() {
            int channelNum = LoginModule.m_stDeviceInfo.byChanNum;
            return Math.max(channelNum, 32);
        }

        private static final Object lockObj = new Object();

        private final SummaryInfo summaryInfo;

        public EventDisplay(SummaryInfo Info) {
            this.summaryInfo = Info;
        }

        @Override
        public void eventTaskProcess() {
            InsertOrUpdateEventInfo(summaryInfo);
        }

        private void InsertOrUpdateEventInfo(SummaryInfo summaryInfo) {

            synchronized (lockObj) {
                dataList.add(summaryInfo.nChannelID, summaryInfo);
                dataList.remove(summaryInfo.nChannelID + 1);
                if (dataList.size() > getMaxSize()) {
                    dataList.remove(getMaxSize());
                }
                setEventInfo(groupInfoTable, dataList);
            }
        }

        private static void setEventInfo(JTable groupInfoTable, List<SummaryInfo> dataList) {
            clearTableModel(groupInfoTable);

            for (int i = 0; i < dataList.size(); i++) {
                groupInfoTable.setValueAt(dataList.get(i).nChannelID + 1, i, 0);
                groupInfoTable.setValueAt(dataList.get(i).eventTime, i, 1);
                groupInfoTable.setValueAt(dataList.get(i).enteredHour, i, 2);
                groupInfoTable.setValueAt(dataList.get(i).enteredToday, i, 3);
                groupInfoTable.setValueAt(dataList.get(i).enteredTotal, i, 4);
                groupInfoTable.setValueAt(dataList.get(i).exitedHour, i, 5);
                groupInfoTable.setValueAt(dataList.get(i).exitedToday, i, 6);
                groupInfoTable.setValueAt(dataList.get(i).exitedTotal, i, 7);
            }

        }

        // 清空 DefaultTableModel
        public static void clearTableModel(JTable jTableModel) {
            int rowCount = jTableModel.getRowCount();
            int columnCount = jTableModel.getColumnCount();
            //清空DefaultTableModel中的内容
            for (int i = 0; i < rowCount; i++)//表格中的行数
            {
                for (int j = 0; j < columnCount; j++) {//表格中的列数
                    jTableModel.setValueAt(" ", i, j);//逐个清空
                }
            }
        }

        private static void clearEventInfoList() {
            synchronized (lockObj) {
                dataList.clear();
                setEventInfo(groupInfoTable, dataList);
            }
        }

    }

    /////////////////////////////// 界面控件定义  ////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    /**********************************************************************
     * 主界面窗口(mainFrame): mainFrame
     *     1) 登录(login): loginPanel
     *     2) 人数事件统计(humanStatistic): humanStatisticPanel
     *        (1) 控制面板(HumanStatisticAttach): HumanStatisticControlPanel
     *        (2) 预览(realPlay): realPanel
     *        (3) 事件信息展示面板(eventInfo): groupListPanel
     **********************************************************************/

    ///////////////////// 主面板 /////////////////////

    private static JFrame mainFrame = new JFrame();

    ///////////////////// 一级面板 /////////////////////

    /* 登录面板 */
    private LoginPanel loginPanel;

    /* 人数统计面板 */
    private HumanStatisticPanel humanStatisticPanel;

    ///////////////////// 二级面板 /////////////////////

    /* 人数统计面板 */
    private HumanStatisticControlPanel humanStatisticAttachPanel;
    private Panel controlPanel;
    private JLabel chnLabel;
    private static JComboBox chnComboBox;
    private JLabel streamLabel;
    private static JComboBox streamComboBox;
    private static JButton realPlayBtn;
    private static JButton attachBtn;
    private static JButton clearBtn;

    /* 实时预览面板 */
    private RealPanel realPanel;
    private JPanel realPlayPanel;
    private static Panel realPlayWindow;


    /* 事件数据展示面板 */
    private static JTable groupInfoTable;
    private GroupListPanel groupListPanel;
    private JScrollPane scrollPane;
}

public class HumanNumberStatistic {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                HumanNumberStatisticFrame demo = new HumanNumberStatisticFrame();
                demo.setVisible(true);
            }
        });
    }
}