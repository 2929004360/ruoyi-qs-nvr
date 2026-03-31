package com.ruoyi.dahua.frame.vto;

import com.ruoyi.dahua.common.Base64;
import com.ruoyi.dahua.common.PaintPanel;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.LoginModule;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import static com.ruoyi.dahua.lib.NetSDKLib.CtrlType.CTRLTYPE_CTRL_RECORDSET_INSERTEX;
import static com.ruoyi.dahua.lib.NetSDKLib.CtrlType.CTRLTYPE_CTRL_RECORDSET_UPDATEEX;

public class OperateInfo extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField cardNoTextField;
    private JTextField roomNoTextField;
    private CollectionFingerPrint fingerPrint = new CollectionFingerPrint();
    private PaintPanel paintPanel;
    private Memory memory;
    private JCheckBox needFingerCheckBox;

    private boolean bListen = false;
    /**
     * 窗口使用的类型:
     * 0:新增卡信息
     * 1:修改卡信息
     */
    private int infoType;
    private String userId;
    private String cardNo;
    private String fingerPrintData;

    public void setCardNoTextFieldEditEnable(boolean enable) {
        this.cardNoTextField.setEditable(enable);
    }
    public void setRoomNoTextFieldEditEnable(boolean enable){
        this.roomNoTextField.setEditable(enable);
    }
    public void syncData(String userId, String cardNo, String fingerPrintData) {
        this.userId = userId;
        this.cardNo = cardNo;
        this.fingerPrintData = fingerPrintData;
        if (fingerPrintData == null || fingerPrintData.trim().equals("")) {
            needFingerCheckBox.setSelected(false);
        } else {
            needFingerCheckBox.setSelected(true);
        }
        cardNoTextField.setText(cardNo);
        roomNoTextField.setText(userId);

    }

    public int getInfoType() {
        return infoType;
    }

    public void setInfoType(int infoType) {
        this.infoType = infoType;
    }

    public void receiveData(int infoType, String userId, String cardNo, String fingerPrintData) {
        this.infoType = infoType;
        //新增卡
        if (infoType == 0) {
            this.userId = "";
            this.cardNo = "";
            this.fingerPrintData = "";
        } else if (infoType == 1) {
            //修改卡
            this.userId = userId;
            this.cardNo = cardNo;
            this.fingerPrintData = fingerPrintData;
        }
        this.cardNoTextField.setText(this.cardNo);
        this.roomNoTextField.setText(this.userId);
        if (!this.fingerPrintData.trim().equals("")) {
            needFingerCheckBox.setSelected(true);
        } else {
            needFingerCheckBox.setSelected(false);
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            OperateInfo dialog = new OperateInfo();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public OperateInfo() {
        setTitle(Res.string().getVTOOperateInfoTitle());
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
        setBounds(100, 100, 476, 294);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null, Res.string().getCardInfo(), TitledBorder.LEFT, TitledBorder.TOP,
                    null, null));
            panel.setBounds(0, 0, 285, 87);
            contentPanel.add(panel);
            panel.setLayout(null);
            {
                JLabel lblNewLabel = new JLabel(Res.string().getCardNo());
                lblNewLabel.setBounds(10, 22, 89, 15);
                panel.add(lblNewLabel);
            }
            {
                cardNoTextField = new JTextField();
                cardNoTextField.setBounds(111, 19, 164, 21);
                panel.add(cardNoTextField);
                cardNoTextField.setColumns(10);
            }
            {
                JLabel lblNewLabel_1 = new JLabel(Res.string().getVTOOperateManagerRoomNo());
                lblNewLabel_1.setBounds(10, 62, 96, 15);
                panel.add(lblNewLabel_1);
            }
            {
                roomNoTextField = new JTextField();
                roomNoTextField.setColumns(10);
                roomNoTextField.setBounds(111, 59, 164, 21);
                panel.add(roomNoTextField);
            }
        }

        needFingerCheckBox = new JCheckBox(Res.string().getNeedFingerPrint());
        needFingerCheckBox.setSelected(true);
        needFingerCheckBox.setBounds(6, 106, 190, 23);
        contentPanel.add(needFingerCheckBox);


        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, Res.string().getFingerPrint(), TitledBorder.LEFT,
                TitledBorder.TOP, null, null));
        panel.setBounds(0, 135, 285, 84);
        contentPanel.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_2 = new JLabel(Res.string().getFingerPrint());
        lblNewLabel_2.setBounds(10, 35, 109, 28);
        panel.add(lblNewLabel_2);

        final JButton btnGetFinger = new JButton(Res.string().getGet());
        btnGetFinger.setBounds(129, 38, 93, 23);
        panel.add(btnGetFinger);
        btnGetFinger.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (fingerPrint == null) {
                    fingerPrint = new CollectionFingerPrint();
                    fingerPrint.setVisible(true);
                    fingerPrint.setFocusable(true);
                } else {
                    //清除信息数据
                    fingerPrint.dispose();
                    //显示信息对话框
                    fingerPrint.setVisible(true);
                }
            }
        });
        needFingerCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //获取事件源
                JCheckBox checkBox = (JCheckBox) e.getSource();
                //选中
                if (checkBox.isSelected()) {
                    btnGetFinger.setEnabled(true);
                } else {
                    btnGetFinger.setEnabled(false);
                }
            }
        });

        {
            paintPanel = new PaintPanel();
            paintPanel.setBackground(Color.GRAY);
            paintPanel.setBorder(new TitledBorder(null, Res.string().getFaceInfo(), TitledBorder.LEADING,
                    TitledBorder.TOP, null, null));
            paintPanel.setBounds(295, 10, 155, 209);
            contentPanel.add(paintPanel);
            paintPanel.setLayout(null);
            {
                JButton open = new JButton(Res.string().getOpen());
                open.setBounds(26, 90, 93, 23);
                paintPanel.add(open);
                // 选择图片，获取图片的信息
                open.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        String picPath = "";

                        // 选择图片，获取图片路径，并在界面显示
                        picPath = ToolKits.openPictureFile(paintPanel);

                        if (!picPath.equals("")) {
                            try {
                                memory = ToolKits.readPictureFile(picPath);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

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
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        //获取到信息
                        if (fingerPrint.isCollectionResult()) {
                            fingerPrintData = Base64.getEncoder().encodeToString(fingerPrint.getPackageData()).trim();
                        }
                        if (infoType == 0) {
                            //新增卡信息
                            if (!checkCardNo(cardNoTextField.getText().trim().getBytes(), true)) {
                                //卡号已存在
                                return;
                            }
                            //添加失败,直接返回,不隐藏窗口
                            if (!addCard(cardNoTextField.getText().trim().getBytes(), roomNoTextField.getText().getBytes(), needFingerCheckBox.isSelected() ? 1 : 0, fingerPrintData)) {
                                return;
                            }

                        } else if (infoType == 1) {
                            //修改卡信息
                            if (!checkCardNo(cardNoTextField.getText().trim().getBytes(), false)) {
                                //卡号不存在,不能修改
                                return;
                            }
                            //修改失败,则直接返回,不隐藏界面
                            if (!modifyCard(cardNoTextField.getText().trim().getBytes(), roomNoTextField.getText().trim().getBytes(), needFingerCheckBox.isSelected() ? 1 : 0, fingerPrintData)) {
                                return;
                            }
                        }
                        dispose();
                    }
                });
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        dispose();
                    }
                });
            }
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    //清除信息状态
                    if (fingerPrint.isCollectionResult()) {
                        fingerPrint.clearStatus();
                    }
                }
            });
        }
    }

    /**
     * 检查下卡号是否存在
     * true:不存在
     * false:存在
     *
     * @param type true:卡存在即弹窗
     * @return
     */
    public boolean checkCardNo(byte[] cardNo, boolean type) {
        if (cardNo.length == 0) {
            JOptionPane.showMessageDialog(null, Res.string().getInputCardNo(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }

        //check whether the card number already exists查询一下卡号是否已经存在
        NetSDKLib.NET_IN_FIND_RECORD_PARAM inParam = new NetSDKLib.NET_IN_FIND_RECORD_PARAM();
        inParam.emType = NetSDKLib.EM_NET_RECORD_TYPE.NET_RECORD_ACCESSCTLCARD;
        //查询条件
        NetSDKLib.FIND_RECORD_ACCESSCTLCARD_CONDITION condition = new NetSDKLib.FIND_RECORD_ACCESSCTLCARD_CONDITION();
        //卡号查询有效
        condition.abCardNo = 1;
        if (cardNo.length > condition.szCardNo.length - 1) {
            JOptionPane.showMessageDialog(null, Res.string().getCardNoExceedLength(), Res.string().getErrorMessage(),
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        System.arraycopy(cardNo, 0, condition.szCardNo, 0, cardNo.length);
        inParam.pQueryCondition = new Memory(condition.size());
        ToolKits.SetStructDataToPointer(condition, inParam.pQueryCondition, 0);
        NetSDKLib.NET_OUT_FIND_RECORD_PARAM outParam = new NetSDKLib.NET_OUT_FIND_RECORD_PARAM();
        boolean startFind = LoginModule.netsdk.CLIENT_FindRecord(LoginModule.m_hLoginHandle, inParam, outParam, 5000);
        if (!startFind) {
            JOptionPane.showMessageDialog(null, Res.string().getQueryCardExistFailed(), Res.string().getErrorMessage(),
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        //查询卡号是否已存在
        int max = 1;
        NetSDKLib.NET_IN_FIND_NEXT_RECORD_PARAM inNextParam = new NetSDKLib.NET_IN_FIND_NEXT_RECORD_PARAM();
        inNextParam.lFindeHandle = outParam.lFindeHandle;
        inNextParam.nFileCount = max;
        NetSDKLib.NET_OUT_FIND_NEXT_RECORD_PARAM outNextParam = new NetSDKLib.NET_OUT_FIND_NEXT_RECORD_PARAM();
        outNextParam.nMaxRecordNum = max;
        NetSDKLib.NET_RECORDSET_ACCESS_CTL_CARD[] card = new NetSDKLib.NET_RECORDSET_ACCESS_CTL_CARD[1];
        card[0] = new NetSDKLib.NET_RECORDSET_ACCESS_CTL_CARD();
        outNextParam.pRecordList = new Memory(card[0].size() * max);
        ToolKits.SetStructArrToPointerData(card, outNextParam.pRecordList);
        LoginModule.netsdk.CLIENT_FindNextRecord(inNextParam, outNextParam, 5000);
        if (outNextParam.nRetRecordNum != 0 && type) {
            //卡号已存在
            JOptionPane.showMessageDialog(null, Res.string().getFindCardExist(), Res.string().getErrorMessage(),
                    JOptionPane.ERROR_MESSAGE);
            //停止查询
            LoginModule.netsdk.CLIENT_FindRecordClose(outParam.lFindeHandle);
            return false;
        }
        //停止查询
        LoginModule.netsdk.CLIENT_FindRecordClose(outParam.lFindeHandle);
        return true;
    }

    /**
     * 新增卡
     */
    public boolean addCard(byte[] cardNo, byte[] userID, int enableFinger, String fingerPrintData) {
        if (cardNo.length == 0) {
            JOptionPane.showMessageDialog(null, Res.string().getInputCardNo(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (userID.length == 0) {
            JOptionPane.showMessageDialog(null, Res.string().getInputRoomNo(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (memory == null) {
            JOptionPane.showMessageDialog(null, Res.string().getChooseFacePic(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        NetSDKLib.NET_RECORDSET_ACCESS_CTL_CARD card = new NetSDKLib.NET_RECORDSET_ACCESS_CTL_CARD();
        if (cardNo.length > card.szCardNo.length - 1) {
            JOptionPane.showMessageDialog(null, Res.string().getCardNoExceedLength(), Res.string().getErrorMessage(),
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (userID.length > card.szUserID.length - 1) {
            JOptionPane.showMessageDialog(null, Res.string().getRoomNoExceedLength(), Res.string().getErrorMessage(),
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        System.arraycopy(cardNo, 0, card.szCardNo, 0, cardNo.length);
        System.arraycopy(userID, 0, card.szUserID, 0, userID.length);

        card.nDoorNum = 1;
        card.sznDoors[0] = 0;
        if (enableFinger == 1) {
            //信息不存在
            if (fingerPrintData == null || fingerPrintData.trim().equals("")) {
                JOptionPane.showMessageDialog(null, Res.string().getFingerPrintIdNotExist(), Res.string().getErrorMessage(),
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
            //base64 string to bytes
            byte[] bytes = Base64.getDecoder().decode(fingerPrintData);
            //增加信息
            card.bEnableExtended = 1;
            card.stuFingerPrintInfoEx.nCount = 1;
            card.stuFingerPrintInfoEx.nLength = bytes.length;
            card.stuFingerPrintInfoEx.nPacketLen = bytes.length;
            card.stuFingerPrintInfoEx.pPacketData = new Memory(bytes.length);
            card.stuFingerPrintInfoEx.pPacketData.clear(bytes.length);

            card.stuFingerPrintInfoEx.pPacketData.write(0, bytes, 0, bytes.length);
        } else {
            card.bEnableExtended = 0;
        }

        NetSDKLib.NET_CTRL_RECORDSET_INSERT_PARAM inParam = new NetSDKLib.NET_CTRL_RECORDSET_INSERT_PARAM();
        inParam.stuCtrlRecordSetInfo.emType = NetSDKLib.EM_NET_RECORD_TYPE.NET_RECORD_ACCESSCTLCARD;
        inParam.stuCtrlRecordSetInfo.nBufLen = card.size();
        inParam.stuCtrlRecordSetInfo.pBuf = new Memory(card.size());
        ToolKits.SetStructDataToPointer(card, inParam.stuCtrlRecordSetInfo.pBuf, 0);
        Pointer pointer = new Memory(inParam.size());
        ToolKits.SetStructDataToPointer(inParam, pointer, 0);
        // 插入信息必须用  CTRLTYPE_CTRL_RECORDSET_INSERTEX，不能用 CTRLTYPE_CTRL_RECORDSET_INSERT
        boolean res = LoginModule.netsdk.CLIENT_ControlDevice(LoginModule.m_hLoginHandle, CTRLTYPE_CTRL_RECORDSET_INSERTEX, pointer, 5000);
        if (!res) {
            JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(),
                    JOptionPane.ERROR_MESSAGE);
            return res;
        }
        ToolKits.GetPointerData(pointer, inParam);

        if (memory != null) {
            //增加人脸图片
            NetSDKLib.NET_IN_ADD_FACE_INFO inAddFaceInfo = new NetSDKLib.NET_IN_ADD_FACE_INFO();
            System.arraycopy(userID, 0, inAddFaceInfo.szUserID, 0, userID.length);
            inAddFaceInfo.stuFaceInfo.nFacePhoto = 1;
            inAddFaceInfo.stuFaceInfo.nFacePhotoLen[0] = (int) memory.size();
            inAddFaceInfo.stuFaceInfo.pszFacePhotoArr[0].pszFacePhoto = new Memory(memory.size());
            inAddFaceInfo.stuFaceInfo.pszFacePhotoArr[0].pszFacePhoto.write(0, memory.getByteArray(0, (int) memory.size()), 0, (int) memory.size());
            inAddFaceInfo.stuFaceInfo.nRoom = 1;
            System.arraycopy(userID, 0, inAddFaceInfo.stuFaceInfo.szRoomNoArr[0].szRoomNo, 0, userID.length);

            NetSDKLib.NET_OUT_ADD_FACE_INFO outAddFaceInfo = new NetSDKLib.NET_OUT_ADD_FACE_INFO();
            Pointer outFaceParam = new Memory(outAddFaceInfo.size());
            ToolKits.SetStructDataToPointer(outAddFaceInfo, outFaceParam, 0);
            Pointer inFace = new Memory(inAddFaceInfo.size());
            ToolKits.SetStructDataToPointer(inAddFaceInfo, inFace, 0);
            boolean result = LoginModule.netsdk.CLIENT_FaceInfoOpreate(LoginModule.m_hLoginHandle, NetSDKLib.EM_FACEINFO_OPREATE_TYPE.EM_FACEINFO_OPREATE_ADD,
                    inFace, outFaceParam, 10000);
            if (!result) {
                JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(),
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;

    }

    /**
     * 修改卡信息
     *
     * @param cardNo 卡号
     * @param userID 房间号
     * @param enableFinger 是否使用信息
     * @param fingerPrintData 信息数据,Base64编码字符串
     */
    public boolean modifyCard(byte[] cardNo, byte[] userID, int enableFinger, String fingerPrintData) {
        //modify card
        NetSDKLib.NET_RECORDSET_ACCESS_CTL_CARD card = new NetSDKLib.NET_RECORDSET_ACCESS_CTL_CARD();
        NetSDKLib.NET_CTRL_RECORDSET_PARAM inParam = new NetSDKLib.NET_CTRL_RECORDSET_PARAM();
        inParam.emType = NetSDKLib.EM_NET_RECORD_TYPE.NET_RECORD_ACCESSCTLCARD;
        card.nDoorNum = 1;
        card.sznDoors[0] = 0;
        System.arraycopy(cardNo, 0, card.szCardNo, 0, cardNo.length);
        System.arraycopy(userID, 0, card.szUserID, 0, userID.length);
        if (enableFinger == 1) {
            //信息不存在,输入信息
            if (fingerPrintData == null || fingerPrintData.trim().equals("")) {
                JOptionPane.showMessageDialog(null, Res.string().getFingerPrintIdNotExist(), Res.string().getErrorMessage(),
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
            byte[] data = Base64.getDecoder().decode(fingerPrintData);
            //modify finger print
            card.bEnableExtended = 1;
            card.stuFingerPrintInfoEx.nCount = 1;
            card.stuFingerPrintInfoEx.nLength = data.length;
            card.stuFingerPrintInfoEx.nPacketLen = data.length;
            card.stuFingerPrintInfoEx.pPacketData = new Memory(data.length);
            card.stuFingerPrintInfoEx.pPacketData.clear(data.length);
            card.stuFingerPrintInfoEx.pPacketData.write(0, data, 0, data.length);
        } else {
            card.bEnableExtended = 0;
        }
        inParam.pBuf = new Memory(card.size());
        ToolKits.SetStructDataToPointer(card, inParam.pBuf, 0);

        Pointer pointer = new Memory(inParam.size());
        ToolKits.SetStructDataToPointer(inParam, pointer, 0);

        boolean res = LoginModule.netsdk.CLIENT_ControlDevice(LoginModule.m_hLoginHandle, CTRLTYPE_CTRL_RECORDSET_UPDATEEX, pointer, 10000);
        if (!res) {
            JOptionPane.showMessageDialog(null, Res.string().getFailedModifyCard() + ", " + ToolKits.getErrorCodeShow(),
                    Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            return res;
        }
        //modify face
        if (memory != null) {
            NetSDKLib.NET_IN_UPDATE_FACE_INFO inUpdateFaceInfo = new NetSDKLib.NET_IN_UPDATE_FACE_INFO();
            System.arraycopy(userID, 0, inUpdateFaceInfo.szUserID, 0, userID.length);
            inUpdateFaceInfo.stuFaceInfo.nFacePhoto = 1;
            inUpdateFaceInfo.stuFaceInfo.nFacePhotoLen[0] = (int) memory.size();
            //inUpdateFaceInfo.stuFaceInfo.pszFacePhotoArr[0].pszFacePhoto=memory;
            inUpdateFaceInfo.stuFaceInfo.pszFacePhotoArr[0].pszFacePhoto = new Memory(memory.size());
            inUpdateFaceInfo.stuFaceInfo.pszFacePhotoArr[0].pszFacePhoto.write(0, memory.getByteArray(0, (int) memory.size()), 0, (int) memory.size());
            inUpdateFaceInfo.stuFaceInfo.nRoom = 1;
            System.arraycopy(userID, 0, inUpdateFaceInfo.stuFaceInfo.szRoomNoArr[0].szRoomNo, 0, userID.length);

            NetSDKLib.NET_OUT_UPDATE_FACE_INFO outUpdateFaceInfo = new NetSDKLib.NET_OUT_UPDATE_FACE_INFO();
            Pointer inUpdateParam = new Memory(inUpdateFaceInfo.size());
            ToolKits.SetStructDataToPointer(inUpdateFaceInfo, inUpdateParam, 0);
            Pointer outUpdateParam = new Memory(outUpdateFaceInfo.size());
            ToolKits.SetStructDataToPointer(outUpdateFaceInfo, outUpdateParam, 0);
            boolean result = LoginModule.netsdk.CLIENT_FaceInfoOpreate(LoginModule.m_hLoginHandle, NetSDKLib.EM_FACEINFO_OPREATE_TYPE.EM_FACEINFO_OPREATE_UPDATE, inUpdateParam, outUpdateParam, 5000);
            if (!result) {
                JOptionPane.showMessageDialog(null, Res.string().getFailedModifyCard() +","+ ToolKits.getErrorCodeShow(),
                        Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
                return result;
            }
        }
        return true;
    }

    /**
     * 清除人脸图片
     */
    public void clearImage() {
        paintPanel.setOpaque(false);
        paintPanel.setImage(null);
        paintPanel.repaint();
        memory = null;
    }
}
