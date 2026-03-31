package com.ruoyi.dahua.frame.vto;

import com.ruoyi.dahua.common.Base64;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.LoginModule;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class CollectionFingerPrint extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField ipTextField;
    private JTextField portTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JLabel collectionResult;

    private JButton btnLogin;
    private JButton btnLogout;
    private JButton btnCollection;

    private byte[] packageData;
    private int packageLen;
    private boolean bcollectionResult = false;
    private boolean isListen;

    public byte[] getPackageData() {
        return packageData;
    }

    public void setPackageData(byte[] packageData) {
        this.packageData = packageData;

    }

    public void setLabelResult(byte[] packageData) {
        collectionResult.setText(Base64.getEncoder().encodeToString(packageData));
    }

    public int getPackageLen() {
        return packageLen;
    }

    public void setPackageLen(int packageLen) {
        this.packageLen = packageLen;
    }

    public boolean isCollectionResult() {
        return bcollectionResult;
    }

    public void setCollectionResult(boolean bcollectionResult) {
        this.bcollectionResult = bcollectionResult;
        //显示结果
        collectionResult.setText(this.bcollectionResult ? "success" : "failed");
    }

    public void stopListen() {
        //获取到信息,停止监听
        if (loginHandler != null && loginHandler.longValue() != 0) {
            stopListen(loginHandler);
        }
        //获取按钮使能
        btnCollection.setEnabled(true);
        //设置监听状态
        isListen = false;
    }

    private NetSDKLib.LLong loginHandler;
    private NetSDKLib.NET_DEVICEINFO_Ex deviceinfoEx = new NetSDKLib.NET_DEVICEINFO_Ex();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            CollectionFingerPrint dialog = new CollectionFingerPrint();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public CollectionFingerPrint() {
        setBounds(100, 100, 304, 397);
        setTitle(Res.string().getVTOOperateCollectionFingerPrintTitle());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null, Res.string().getLogin(), TitledBorder.LEADING,
                    TitledBorder.TOP, null, null));
            panel.setBounds(10, 10, 268, 167);
            contentPanel.add(panel);
            panel.setLayout(null);
            {
                JLabel lblNewLabel = new JLabel(Res.string().getIp());
                lblNewLabel.setBounds(10, 23, 66, 15);
                panel.add(lblNewLabel);
            }
            {
                ipTextField = new JTextField();
                ipTextField.setText("172.23.32.61");
                ipTextField.setBounds(103, 20, 155, 21);
                panel.add(ipTextField);
                ipTextField.setColumns(10);
            }
            {
                JLabel lblPort = new JLabel(Res.string().getPort());
                lblPort.setBounds(10, 48, 83, 15);
                panel.add(lblPort);
            }
            {
                portTextField = new JTextField();
                portTextField.setText("37777");
                portTextField.setColumns(10);
                portTextField.setBounds(103, 45, 155, 21);
                panel.add(portTextField);
            }
            {
                JLabel lblName = new JLabel(Res.string().getUserName());
                lblName.setBounds(10, 73, 83, 15);
                panel.add(lblName);
            }
            {
                usernameTextField = new JTextField();
                usernameTextField.setText("admin");
                usernameTextField.setColumns(10);
                usernameTextField.setBounds(103, 70, 155, 21);
                panel.add(usernameTextField);
            }
            {
                JLabel lblPassword = new JLabel(Res.string().getPassword());
                lblPassword.setBounds(10, 98, 90, 15);
                panel.add(lblPassword);
            }

            passwordField = new JPasswordField();
            passwordField.setBounds(103, 95, 155, 18);
            passwordField.setText("admin123");
            panel.add(passwordField);
            {
                btnLogin = new JButton(Res.string().getLogin());
                btnLogin.setBounds(7, 134, 111, 23);
                panel.add(btnLogin);
                btnLogin.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (login()) {
                            btnCollection.setEnabled(true);
                        }
                    }
                });
            }
            {
                btnLogout = new JButton(Res.string().getLogout());
                btnLogout.setBounds(153, 134, 105, 23);
                panel.add(btnLogout);
                btnLogout.setEnabled(false);
                btnLogout.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        logout();
                    }
                });
            }
        }
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null, Res.string().getOperate(), TitledBorder.LEADING, TitledBorder.TOP,
                    null, null));
            panel.setBounds(10, 187, 268, 129);
            contentPanel.add(panel);
            panel.setLayout(null);
            {
                btnCollection = new JButton(Res.string().getStartCollection());
                btnCollection.setBounds(10, 26, 227, 41);
                panel.add(btnCollection);

                collectionResult = new JLabel(Res.string().getCollectionResult());
                collectionResult.setBounds(10, 77, 227, 26);
                panel.add(collectionResult);
                btnCollection.setEnabled(false);
                final CollectionFingerPrint print = this;
                btnCollection.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        isListen = startListen(loginHandler, VTOMessageCallBack.getINSTANCE(null, print));
                        if (isListen) {
                            //下发采集信息指令
                            if (!collectionFinger()) {
                                stopListen(loginHandler);
                            } else {
                                // 使其失效
                                btnCollection.setEnabled(false);
                            }
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(20000);
                                        //等待二十秒,如果没有获取到信息，则停止获取
                                        if (isListen) {
                                            stopListen();
                                        }

                                    } catch (InterruptedException interruptedException) {
                                        interruptedException.printStackTrace();
                                    }
                                }
                            }).start();
                        }

                    }
                });
                addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        //已经登录
                        if (loginHandler != null && loginHandler.longValue() != 0) {
                            if (isListen) {
                                //停止监听
                                stopListen(loginHandler);
                            }
                            //登出
                            logout();
                        }
                        //按钮复位
                        btnLogin.setEnabled(true);
                        btnLogout.setEnabled(false);
                        btnCollection.setEnabled(false);
                        //清除信息数据
                        bcollectionResult=false;
                        packageData=null;
                        //清除label显示
                        collectionResult.setText(Res.string().getCollectionResult());
                    }
                });
            }

        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                    }
                });

            }
        }
    }

    /**
     * 登录
     */
    public boolean login() {
        if (login(ipTextField.getText(), Integer.parseInt(portTextField.getText()), usernameTextField.getText(),
                new String(passwordField.getPassword()))) {
            btnLogin.setEnabled(false);
            btnLogout.setEnabled(true);
            btnCollection.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(),
                    Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * \if ENGLISH_LANG Login Device \else 登录设备 \endif
     */
    public boolean login(String m_strIp, int m_nPort, String m_strUser, String m_strPassword) {
        // IntByReference nError = new IntByReference(0);
        // 入参
        NetSDKLib.NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY pstInParam = new NetSDKLib.NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY();
        pstInParam.nPort = m_nPort;
        pstInParam.szIP = m_strIp.getBytes();
        pstInParam.szPassword = m_strPassword.getBytes();
        pstInParam.szUserName = m_strUser.getBytes();
        // 出参
        NetSDKLib.NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY pstOutParam = new NetSDKLib.NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY();
        pstOutParam.stuDeviceInfo = deviceinfoEx;
        loginHandler = LoginModule.netsdk.CLIENT_LoginWithHighLevelSecurity(pstInParam, pstOutParam);
        if (loginHandler.longValue() == 0) {
            JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(),
                    Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("Login Success [ " + m_strIp + " ]");
        }

        return loginHandler.longValue() == 0 ? false : true;
    }

    public long getLoginHandler() {
        if (loginHandler != null) {
            return loginHandler.longValue();
        }
        return 0;
    }

    /**
     * \if ENGLISH_LANG Logout Device \else 登出设备 \endif
     */
    public boolean logout() {
        if (loginHandler.longValue() == 0) {
            return false;
        }

        boolean bRet = LoginModule.netsdk.CLIENT_Logout(loginHandler);
        if (bRet) {
            loginHandler.setValue(0);
            btnLogin.setEnabled(true);
            btnLogout.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(),
                    Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
        }

        return bRet;
    }

    /**
     * 下发采集信息指令
     *
     * @return
     */
    public boolean collectionFinger() {
        NetSDKLib.NET_CTRL_CAPTURE_FINGER_PRINT capture = new NetSDKLib.NET_CTRL_CAPTURE_FINGER_PRINT();
        capture.nChannelID = 0;
        System.arraycopy("1".getBytes(), 0, capture.szReaderID, 0, "1".getBytes().length);
        Pointer pointer = new Memory(capture.size());
        ToolKits.SetStructDataToPointer(capture, pointer, 0);
        boolean ret = LoginModule.netsdk.CLIENT_ControlDevice(loginHandler, NetSDKLib.CtrlType.CTRLTYPE_CTRL_CAPTURE_FINGER_PRINT, pointer, 100000);
        if (!ret) {
            JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(),
                    Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;

    }

    public boolean startListen(NetSDKLib.LLong loginHandler, NetSDKLib.fMessCallBack cbMessage) {

        LoginModule.netsdk.CLIENT_SetDVRMessCallBack(cbMessage, null); // set alarm listen callback

        if (!LoginModule.netsdk.CLIENT_StartListenEx(loginHandler)) {
            JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(),
                    Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            System.out.println("CLIENT_StartListenEx success.");
        }
        return true;
    }

    public boolean stopListen(NetSDKLib.LLong loginHandler) {
        if (!LoginModule.netsdk.CLIENT_StopListen(loginHandler)) {
            JOptionPane.showMessageDialog(null, Res.string().getStopListenFailed()+","+ ToolKits.getErrorCodeShow(),
                    Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            System.out.println("CLIENT_StopListen success.");
        }
        return true;
    }

    /**
     * 清除获取信息的状态
     */
    public void clearStatus() {
        this.setPackageData(null);
        this.setCollectionResult(false);
        this.setPackageLen(0);
    }
}
