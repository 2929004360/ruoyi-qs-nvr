package com.ruoyi.dahua.frame.TargetRecognition;

import com.ruoyi.dahua.common.*;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.*;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.LoginModule;
import com.ruoyi.dahua.module.RealPlayModule;
import com.ruoyi.dahua.module.TargetRecognitionModule;
import com.sun.jna.Pointer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Vector;

class TargetRecognitionFrame extends JFrame {
  private static final long serialVersionUID = 1L;

  private Vector<String> chnList = new Vector<String>();

  private boolean isRealplay = false;
  private static boolean isAttach = false;

  // 设备断线通知回调
  private static DisConnect disConnect = new DisConnect();

  // 网络连接恢复
  private static HaveReConnect haveReConnect = new HaveReConnect();

  // 预览句柄
  public static LLong m_hPlayHandle = new LLong(0);

  // 订阅句柄
  public static LLong m_hAttachHandle = new LLong(0);

  // 获取界面窗口
  private static JFrame frame = new JFrame();

  // 人脸库界面
  private GroupOperateDialog groupOperateDialog = null;

  // 全景图
  private static BufferedImage globalBufferedImage = null;

  // 人脸图
  private static BufferedImage personBufferedImage = null;

  // 候选人图
  private static BufferedImage candidateBufferedImage = null;

  // 用于目标检测
  private static int groupId = 0;

  private static int index = -1;

  public TargetRecognitionFrame() {
    setTitle(Res.string().getTargetRecognition());
    setLayout(new BorderLayout());
    pack();
    setSize(800, 560);
    setResizable(false);
    setLocationRelativeTo(null);
    LoginModule.init(disConnect, haveReConnect); // 打开工程，初始化

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    loginPanel = new LoginPanel();
    TargetRecognitionEventPanel facePanel = new TargetRecognitionEventPanel();

    add(loginPanel, BorderLayout.NORTH);
    add(facePanel, BorderLayout.CENTER);

    loginPanel.addLoginBtnActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (loginPanel.checkLoginText()) {
              if (login()) {
                frame = ToolKits.getFrame(e);
                frame.setTitle(
                    Res.string().getTargetRecognition() + " : " + Res.string().getOnline());
              }
            }
          }
        });

    loginPanel.addLogoutBtnActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            frame.setTitle(Res.string().getTargetRecognition());
            logout();
          }
        });

    addWindowListener(
        new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            TargetRecognitionModule.renderPrivateData(m_hPlayHandle, 0);
            RealPlayModule.stopRealPlay(m_hPlayHandle);
            TargetRecognitionModule.stopRealLoadPicture(m_hAttachHandle);
            LoginModule.logout();
            LoginModule.cleanup(); // 关闭工程，释放资源

            dispose();

            SwingUtilities.invokeLater(
                new Runnable() {
                  public void run() {
                    FunctionList demo = new FunctionList();
                    demo.setVisible(true);
                  }
                });
          }
        });
  }

  ///////////////// 面板///////////////////
  // 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
  private static class DisConnect implements fDisConnect {
    public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
      System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
      // 断线提示
      SwingUtilities.invokeLater(
          new Runnable() {
            public void run() {
              frame.setTitle(
                  Res.string().getTargetRecognition()
                      + " : "
                      + Res.string().getDisConnectReconnecting());
            }
          });
    }
  }

  // 网络连接恢复，设备重连成功回调
  // 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
  private static class HaveReConnect implements fHaveReConnect {
    public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
      System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);

      // 重连提示
      SwingUtilities.invokeLater(
          new Runnable() {
            public void run() {
              frame.setTitle(Res.string().getTargetRecognition() + " : " + Res.string().getOnline());
            }
          });
    }
  }

  // 登录
  public boolean login() {
    if (LoginModule.login(
        loginPanel.ipTextArea.getText(),
        Integer.parseInt(loginPanel.portTextArea.getText()),
        loginPanel.nameTextArea.getText(),
        new String(loginPanel.passwordTextArea.getPassword()))) {

      loginPanel.setButtonEnable(true);
      setEnable(true);

      for (int i = 1; i < LoginModule.m_stDeviceInfo.byChanNum + 1; i++) {
        chnList.add(Res.string().getChannel() + " " + String.valueOf(i));
      }

      // 登陆成功，将通道添加到控件
      chnComboBox.setModel(new DefaultComboBoxModel(chnList));
    } else {
      JOptionPane.showMessageDialog(
          null,
          Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(),
          Res.string().getErrorMessage(),
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  // 登出
  public void logout() {
    TargetRecognitionModule.renderPrivateData(m_hPlayHandle, 0);
    RealPlayModule.stopRealPlay(m_hPlayHandle);
    TargetRecognitionModule.stopRealLoadPicture(m_hAttachHandle);
    LoginModule.logout();

    loginPanel.setButtonEnable(false);
    setEnable(false);
    realplayWindowPanel.repaint();

    isRealplay = false;
    realplayBtn.setText(Res.string().getStartRealPlay());

    isAttach = false;

    attachBtn.setText(Res.string().getAttach());
    globalPicLabel.setText(
        Res.string().getGlobalPicture() + " ------ [" + Res.string().getEventType() + "]");

    globalPicShowPanel.setOpaque(true);
    globalPicShowPanel.repaint();

    personPicShowPanel.setOpaque(true);
    personPicShowPanel.repaint();

    candidatePicShowPanel.setOpaque(true);
    candidatePicShowPanel.repaint();

    timeTextField.setText("");
    sexTextField.setText("");
    ageTextField.setText("");
    eyeTextField.setText("");
    mouthTextField.setText("");
    maskTextField.setText("");
    beardTextField.setText("");

    nameTextField.setText("");
    sexTextField2.setText("");
    birthdayTextField.setText("");
    idNoTextField.setText("");
    groupIdTextField.setText("");
    groupNameTextField.setText("");
    similaryTextField.setText("");

    for (int i = 0; i < LoginModule.m_stDeviceInfo.byChanNum; i++) {
      chnList.clear();
    }

    chnComboBox.setModel(new DefaultComboBoxModel());

    groupId = 0;
    globalBufferedImage = null;
    personBufferedImage = null;
    candidateBufferedImage = null;
  }

  public class TargetRecognitionEventPanel extends JPanel {
    /** */
    private static final long serialVersionUID = 1L;

    public TargetRecognitionEventPanel() {
      BorderEx.set(this, "", 2);
      setLayout(new BorderLayout());

      JPanel operatePanel = new JPanel(); // 通道、预览、订阅
      JPanel panel = new JPanel();

      add(operatePanel, BorderLayout.NORTH);
      add(panel, BorderLayout.CENTER);

      /*
       * 操作面板：通道、预览、订阅按钮
       */
      chnlabel = new JLabel(Res.string().getChannel());
      chnComboBox = new JComboBox();

      realplayBtn = new JButton(Res.string().getStartRealPlay());
      attachBtn = new JButton(Res.string().getAttach());
      faceDataBaseBtn = new JButton(Res.string().getGroupOperate());
      faceEventRecordBtn = new JButton("查找事件记录");
      /** 以图搜图按钮 */
      searchByPicBtn = new JButton(Res.string().getSearchByPic());

      operatePanel.setLayout(new FlowLayout());
      operatePanel.add(chnlabel);
      operatePanel.add(chnComboBox);
      operatePanel.add(realplayBtn);
      operatePanel.add(attachBtn);
      operatePanel.add(faceDataBaseBtn);
      operatePanel.add(searchByPicBtn);
      //			operatePanel.add(faceEventRecordBtn);

      Dimension dim = new Dimension();
      dim.width = 120;
      dim.height = 20;

      chnComboBox.setPreferredSize(new Dimension(80, 20));
      attachBtn.setPreferredSize(dim);
      realplayBtn.setPreferredSize(dim);
      faceDataBaseBtn.setPreferredSize(dim);
      faceEventRecordBtn.setPreferredSize(dim);
      searchByPicBtn.setPreferredSize(dim);

      chnComboBox.setEnabled(false);
      realplayBtn.setEnabled(false);
      attachBtn.setEnabled(false);
      faceDataBaseBtn.setEnabled(false);
      faceEventRecordBtn.setEnabled(false);
      searchByPicBtn.setEnabled(false);

      /*
       * 预览、图片面板
       */
      JPanel realplayPanel = new JPanel();
      JPanel globalPicPanel = new JPanel();
      JPanel personPicPanel = new JPanel();
      JPanel candidatePanel = new JPanel();

      realplayPanel.setBorder(new EmptyBorder(0, 5, 5, 5));
      globalPicPanel.setBorder(new EmptyBorder(0, 5, 5, 5));
      personPicPanel.setBorder(new EmptyBorder(0, 5, 5, 5));
      candidatePanel.setBorder(new EmptyBorder(0, 5, 5, 5));

      panel.setLayout(new GridLayout(2, 2));

      panel.add(realplayPanel);
      panel.add(globalPicPanel);
      panel.add(personPicPanel);
      panel.add(candidatePanel);

      /*
       * 预览面板
       */
      JLabel realplayLabel = new JLabel(Res.string().getRealplay());
      realplayWindowPanel = new Panel();
      realplayWindowPanel.setBackground(Color.GRAY);
      realplayPanel.setLayout(new BorderLayout());
      realplayPanel.add(realplayLabel, BorderLayout.NORTH);
      realplayPanel.add(realplayWindowPanel, BorderLayout.CENTER);

      /*
       * 全景图面板
       */
      globalPicLabel =
          new JLabel(
              Res.string().getGlobalPicture() + " ------ [" + Res.string().getEventType() + "]");
      globalPicShowPanel = new PaintPanel();
      globalPicPanel.setLayout(new BorderLayout());
      globalPicPanel.add(globalPicLabel, BorderLayout.NORTH);
      globalPicPanel.add(globalPicShowPanel, BorderLayout.CENTER);

      /*
       * 人脸图面板
       */
      JLabel personPiclabel = new JLabel(Res.string().getPersonPicture());
      personPicShowPanel = new PaintPanel();
      JPanel faceDataPanel = new JPanel();

      Dimension dimension = new Dimension();
      dimension.width = 200;
      faceDataPanel.setPreferredSize(dimension);

      personPicPanel.setLayout(new BorderLayout());
      personPicPanel.add(personPiclabel, BorderLayout.NORTH);
      personPicPanel.add(personPicShowPanel, BorderLayout.CENTER);
      personPicPanel.add(faceDataPanel, BorderLayout.EAST);

      // 人脸信息
      JLabel timeLabel = new JLabel(Res.string().getTime(), JLabel.CENTER);
      JLabel sexLabel = new JLabel(Res.string().getSex(), JLabel.CENTER);
      JLabel ageLabel = new JLabel(Res.string().getAge(), JLabel.CENTER);
      JLabel colorLabel = new JLabel(Res.string().getColor(), JLabel.CENTER);
      JLabel eyeLabel = new JLabel(Res.string().getEye(), JLabel.CENTER);
      JLabel mouthLabel = new JLabel(Res.string().getMouth(), JLabel.CENTER);
      JLabel maskLabel = new JLabel(Res.string().getMask(), JLabel.CENTER);
      JLabel beardLabel = new JLabel(Res.string().getBeard(), JLabel.CENTER);

      Dimension dimension1 = new Dimension();
      dimension1.height = 18;
      dimension1.width = 50;
      timeLabel.setPreferredSize(dimension1);
      sexLabel.setPreferredSize(dimension1);
      ageLabel.setPreferredSize(dimension1);
      colorLabel.setPreferredSize(dimension1);
      eyeLabel.setPreferredSize(dimension1);
      mouthLabel.setPreferredSize(dimension1);
      maskLabel.setPreferredSize(dimension1);
      beardLabel.setPreferredSize(dimension1);

      timeTextField = new JTextField();
      sexTextField = new JTextField();
      ageTextField = new JTextField();
      eyeTextField = new JTextField();
      mouthTextField = new JTextField();
      maskTextField = new JTextField();
      beardTextField = new JTextField();

      Dimension dimension2 = new Dimension();
      dimension2.width = 125;
      dimension2.height = 19;
      timeTextField.setPreferredSize(dimension2);
      sexTextField.setPreferredSize(dimension2);
      ageTextField.setPreferredSize(dimension2);
      eyeTextField.setPreferredSize(dimension2);
      mouthTextField.setPreferredSize(dimension2);
      maskTextField.setPreferredSize(dimension2);
      beardTextField.setPreferredSize(dimension2);

      timeTextField.setHorizontalAlignment(JTextField.CENTER);
      sexTextField.setHorizontalAlignment(JTextField.CENTER);
      ageTextField.setHorizontalAlignment(JTextField.CENTER);
      eyeTextField.setHorizontalAlignment(JTextField.CENTER);
      mouthTextField.setHorizontalAlignment(JTextField.CENTER);
      maskTextField.setHorizontalAlignment(JTextField.CENTER);
      beardTextField.setHorizontalAlignment(JTextField.CENTER);

      timeTextField.setEnabled(false);
      sexTextField.setEnabled(false);
      ageTextField.setEnabled(false);
      eyeTextField.setEnabled(false);
      mouthTextField.setEnabled(false);
      maskTextField.setEnabled(false);
      beardTextField.setEnabled(false);

      timeTextField.setFont(new Font("黑体", Font.PLAIN, 11));
      sexTextField.setFont(new Font("黑体", Font.PLAIN, 11));
      ageTextField.setFont(new Font("黑体", Font.PLAIN, 11));
      eyeTextField.setFont(new Font("黑体", Font.PLAIN, 11));
      mouthTextField.setFont(new Font("黑体", Font.PLAIN, 11));
      maskTextField.setFont(new Font("黑体", Font.PLAIN, 11));
      beardTextField.setFont(new Font("黑体", Font.PLAIN, 11));

      faceDataPanel.setLayout(new FlowLayout());

      faceDataPanel.add(timeLabel);
      faceDataPanel.add(timeTextField);
      faceDataPanel.add(sexLabel);
      faceDataPanel.add(sexTextField);
      faceDataPanel.add(ageLabel);
      faceDataPanel.add(ageTextField);
      faceDataPanel.add(eyeLabel);
      faceDataPanel.add(eyeTextField);
      faceDataPanel.add(mouthLabel);
      faceDataPanel.add(mouthTextField);
      faceDataPanel.add(maskLabel);
      faceDataPanel.add(maskTextField);
      faceDataPanel.add(beardLabel);
      faceDataPanel.add(beardTextField);

      /*
       * 候选人图面板
       */
      JLabel candidateLabel = new JLabel(Res.string().getCandidatePicture());
      candidatePicShowPanel = new PaintPanel();
      JPanel candidateDataPanel = new JPanel();

      Dimension dimension4 = new Dimension();
      dimension4.width = 220;
      candidateDataPanel.setPreferredSize(dimension4);

      candidatePanel.setLayout(new BorderLayout());
      candidatePanel.add(candidateLabel, BorderLayout.NORTH);
      candidatePanel.add(candidatePicShowPanel, BorderLayout.CENTER);
      candidatePanel.add(candidateDataPanel, BorderLayout.EAST);

      // 候选人信息
      JLabel nameLabel = new JLabel(Res.string().getName(), JLabel.CENTER);
      JLabel sexLabel2 = new JLabel(Res.string().getSex(), JLabel.CENTER);
      JLabel birthdayLabel = new JLabel(Res.string().getBirthday(), JLabel.CENTER);
      JLabel idNoLabel = new JLabel(Res.string().getIdNo(), JLabel.CENTER);
      JLabel groupIdLabel = new JLabel(Res.string().getFaceGroupId(), JLabel.CENTER);
      JLabel groupNameLabel = new JLabel(Res.string().getFaceGroupName(), JLabel.CENTER);
      JLabel similaryLabel = new JLabel(Res.string().getSimilarity(), JLabel.CENTER);

      Dimension dimension3 = new Dimension();
      dimension3.height = 19;
      dimension3.width = 80;
      nameLabel.setPreferredSize(dimension3);
      sexLabel2.setPreferredSize(dimension3);
      birthdayLabel.setPreferredSize(dimension3);
      idNoLabel.setPreferredSize(dimension3);
      groupIdLabel.setPreferredSize(dimension3);
      groupNameLabel.setPreferredSize(dimension3);
      similaryLabel.setPreferredSize(dimension3);

      nameTextField = new JTextField();
      sexTextField2 = new JTextField();
      birthdayTextField = new JTextField();
      idNoTextField = new JTextField();
      groupIdTextField = new JTextField();
      groupNameTextField = new JTextField();
      similaryTextField = new JTextField();

      nameTextField.setHorizontalAlignment(JTextField.CENTER);
      sexTextField2.setHorizontalAlignment(JTextField.CENTER);
      birthdayTextField.setHorizontalAlignment(JTextField.CENTER);
      idNoTextField.setHorizontalAlignment(JTextField.CENTER);
      groupIdTextField.setHorizontalAlignment(JTextField.CENTER);
      groupNameTextField.setHorizontalAlignment(JTextField.CENTER);
      similaryTextField.setHorizontalAlignment(JTextField.CENTER);

      nameTextField.setPreferredSize(dimension2);
      sexTextField2.setPreferredSize(dimension2);
      birthdayTextField.setPreferredSize(dimension2);
      idNoTextField.setPreferredSize(dimension2);
      groupIdTextField.setPreferredSize(dimension2);
      groupNameTextField.setPreferredSize(dimension2);
      similaryTextField.setPreferredSize(dimension2);

      nameTextField.setFont(new Font("黑体", Font.PLAIN, 11));
      sexTextField2.setFont(new Font("黑体", Font.PLAIN, 11));
      birthdayTextField.setFont(new Font("黑体", Font.PLAIN, 11));
      idNoTextField.setFont(new Font("黑体", Font.PLAIN, 11));
      groupIdTextField.setFont(new Font("黑体", Font.PLAIN, 11));
      groupNameTextField.setFont(new Font("黑体", Font.PLAIN, 11));
      similaryTextField.setFont(new Font("黑体", Font.PLAIN, 11));

      nameTextField.setEnabled(false);
      sexTextField2.setEnabled(false);
      birthdayTextField.setEnabled(false);
      idNoTextField.setEnabled(false);
      groupIdTextField.setEnabled(false);
      groupNameTextField.setEnabled(false);
      similaryTextField.setEnabled(false);

      candidateDataPanel.setLayout(new FlowLayout());

      candidateDataPanel.add(nameLabel);
      candidateDataPanel.add(nameTextField);
      candidateDataPanel.add(sexLabel2);
      candidateDataPanel.add(sexTextField2);
      candidateDataPanel.add(birthdayLabel);
      candidateDataPanel.add(birthdayTextField);
      candidateDataPanel.add(idNoLabel);
      candidateDataPanel.add(idNoTextField);
      candidateDataPanel.add(groupIdLabel);
      candidateDataPanel.add(groupIdTextField);
      candidateDataPanel.add(groupNameLabel);
      candidateDataPanel.add(groupNameTextField);
      candidateDataPanel.add(similaryLabel);
      candidateDataPanel.add(similaryTextField);

      // 预览
      realplayBtn.addActionListener(
          new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
              realplay();
            }
          });

      // 订阅
      attachBtn.addActionListener(
          new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
              realLoadPicture();
            }
          });

      // 人脸库操作
      faceDataBaseBtn.addActionListener(
          new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
              groupOperateDialog = new GroupOperateDialog();
              groupOperateDialog.setVisible(true);
            }
          });

      // 查询目标识别事件记录
      faceEventRecordBtn.addActionListener(
          new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
              FindFaceEventRecordDialog faceEventRecordDialog = new FindFaceEventRecordDialog();
              faceEventRecordDialog.setVisible(true);
            }
          });
      // 以图搜图
      searchByPicBtn.addActionListener(
          new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              SearchByPicDialog dialog = new SearchByPicDialog();
              dialog.setVisible(true);
            }
          });
    }
  }

  // 预览
  public void realplay() {
    if (!isRealplay) {
      m_hPlayHandle =
          RealPlayModule.startRealPlay(chnComboBox.getSelectedIndex(), 0, realplayWindowPanel);
      if (m_hPlayHandle.longValue() != 0) {
        realplayWindowPanel.repaint();
        isRealplay = true;
        chnComboBox.setEnabled(false);
        realplayBtn.setText(Res.string().getStopRealPlay());

        TargetRecognitionModule.renderPrivateData(m_hPlayHandle, 1);
      } else {
        JOptionPane.showMessageDialog(
            null,
            ToolKits.getErrorCodeShow(),
            Res.string().getErrorMessage(),
            JOptionPane.ERROR_MESSAGE);
      }
    } else {
      TargetRecognitionModule.renderPrivateData(m_hPlayHandle, 0);

      RealPlayModule.stopRealPlay(m_hPlayHandle);
      realplayWindowPanel.repaint();
      isRealplay = false;
      chnComboBox.setEnabled(true);
      realplayBtn.setText(Res.string().getStartRealPlay());
    }
  }

  // 订阅
  public void realLoadPicture() {
    if (!isAttach) {
      m_hAttachHandle =
          TargetRecognitionModule.realLoadPicture(
              chnComboBox.getSelectedIndex(), AnalyzerDataCB.getInstance());
      if (m_hAttachHandle.longValue() != 0) {
        isAttach = true;
        attachBtn.setText(Res.string().getDetach());
      } else {
        JOptionPane.showMessageDialog(
            null,
            ToolKits.getErrorCodeShow(),
            Res.string().getErrorMessage(),
            JOptionPane.ERROR_MESSAGE);
      }
    } else {
      TargetRecognitionModule.stopRealLoadPicture(m_hAttachHandle);
      isAttach = false;
      attachBtn.setText(Res.string().getAttach());

      globalPicLabel.setText(
          Res.string().getGlobalPicture() + " ------ [" + Res.string().getEventType() + "]");

      globalPicShowPanel.setOpaque(true);
      globalPicShowPanel.repaint();

      personPicShowPanel.setOpaque(true);
      personPicShowPanel.repaint();

      candidatePicShowPanel.setOpaque(true);
      candidatePicShowPanel.repaint();

      timeTextField.setText("");
      sexTextField.setText("");
      ageTextField.setText("");
      eyeTextField.setText("");
      mouthTextField.setText("");
      maskTextField.setText("");
      beardTextField.setText("");

      nameTextField.setText("");
      sexTextField2.setText("");
      birthdayTextField.setText("");
      idNoTextField.setText("");
      groupIdTextField.setText("");
      groupNameTextField.setText("");
      similaryTextField.setText("");

      groupId = 0;
      globalBufferedImage = null;
      personBufferedImage = null;
      candidateBufferedImage = null;
    }
  }

  /** 写成静态主要是防止被回收 */
  private static class AnalyzerDataCB implements fAnalyzerDataCallBack {
    private AnalyzerDataCB() {}

    private static class AnalyzerDataCBHolder {
      private static final AnalyzerDataCB instance = new AnalyzerDataCB();
    }

    public static AnalyzerDataCB getInstance() {
      return AnalyzerDataCBHolder.instance;
    }

    public int invoke(
        LLong lAnalyzerHandle,
        int dwAlarmType,
        Pointer pAlarmInfo,
        Pointer pBuffer,
        int dwBufSize,
        Pointer dwUser,
        int nSequence,
        Pointer reserved) {
      if (lAnalyzerHandle.longValue() == 0 || pAlarmInfo == null) {
        return -1;
      }

      switch (dwAlarmType) {
        case NetSDKLib.EVENT_IVS_FACERECOGNITION: // /< 目标识别事件
          {
            // DEV_EVENT_FaceRecognition_INFO 结构体比较大，new对象会比较耗时， ToolKits.GetPointerData内容拷贝是不耗时的。
            // 如果多台设备或者事件处理比较频繁，可以考虑将 static DEV_EVENT_FaceRecognition_INFO msg = new
            // DEV_EVENT_FaceRecognition_INFO(); 改为全局。
            // 写成全局，是因为每次new花费时间较多, 如果改为全局，此case下的处理需要加锁
            // 加锁，是因为共用一个对象，防止数据出错

            // 耗时800ms左右
            DEV_EVENT_FACERECOGNITION_INFO msg = new DEV_EVENT_FACERECOGNITION_INFO();

            // 耗时20ms左右
            ToolKits.GetPointerData(pAlarmInfo, msg);

            // 保存图片，获取图片缓存
            // 耗时20ms左右
            try {
              saveTargetRecognitionPic(pBuffer, dwBufSize, msg);
            } catch (FileNotFoundException e) {
              e.printStackTrace();
            }

            // 列表、图片界面显示
            // 回调属于子线程，以下是个UI线程，来刷新UI
            EventQueue.invokeLater(
                new TargetRecognitionRunnable(
                    globalBufferedImage, personBufferedImage, candidateBufferedImage, msg, index));

            // 释放内存
            msg = null;
            System.gc();

            break;
          }
        case NetSDKLib.EVENT_IVS_FACEDETECT: // /<目标检测
          {
            DEV_EVENT_FACEDETECT_INFO msg = new DEV_EVENT_FACEDETECT_INFO();

            ToolKits.GetPointerData(pAlarmInfo, msg);

            // 保存图片，获取图片缓存
            try {
              saveFaceDetectPic(pBuffer, dwBufSize, msg);
            } catch (FileNotFoundException e) {
              e.printStackTrace();
            }

            // 列表、图片界面显示
            EventQueue.invokeLater(
                new FaceDetectRunnable(globalBufferedImage, personBufferedImage, msg));

            // 释放内存
            msg = null;
            System.gc();

            break;
          }
        default:
          break;
      }

      return 0;
    }

    /**
     * 保存目标识别事件图片
     *
     * @param pBuffer 抓拍图片信息
     * @param dwBufSize 抓拍图片大小
     * @param TargetRecognitionInfo 目标识别事件信息
     */
    public void saveTargetRecognitionPic(
        Pointer pBuffer, int dwBufSize, DEV_EVENT_FACERECOGNITION_INFO TargetRecognitionInfo)
        throws FileNotFoundException {
      index = -1;
      globalBufferedImage = null;
      personBufferedImage = null;
      candidateBufferedImage = null;

      File path = new File("./TargetRecognition/");
      if (!path.exists()) {
        path.mkdir();
      }

      if (pBuffer == null || dwBufSize <= 0) {
        return;
      }

      /////////////// 保存全景图 ///////////////////
      if (TargetRecognitionInfo.bGlobalScenePic == 1) {

        String strGlobalPicPathName =
            path + "\\" + TargetRecognitionInfo.UTC.toStringTitle() + "_TargetRecognition_Global.jpg";
        byte[] bufferGlobal =
            pBuffer.getByteArray(
                TargetRecognitionInfo.stuGlobalScenePicInfo.dwOffSet,
                TargetRecognitionInfo.stuGlobalScenePicInfo.dwFileLenth);
        ByteArrayInputStream byteArrInputGlobal = new ByteArrayInputStream(bufferGlobal);

        try {
          globalBufferedImage = ImageIO.read(byteArrInputGlobal);
          if (globalBufferedImage != null) {
            File globalFile = new File(strGlobalPicPathName);
            if (globalFile != null) {
              ImageIO.write(globalBufferedImage, "jpg", globalFile);
            }
          }
        } catch (IOException e2) {
          e2.printStackTrace();
        }
      }

      /////////////// 保存人脸图 /////////////////////////
      if (TargetRecognitionInfo.stuObject.stPicInfo != null) {
        String strPersonPicPathName =
            path + "\\" + TargetRecognitionInfo.UTC.toStringTitle() + "_TargetRecognition_Person.jpg";
        byte[] bufferPerson =
            pBuffer.getByteArray(
                TargetRecognitionInfo.stuObject.stPicInfo.dwOffSet,
                TargetRecognitionInfo.stuObject.stPicInfo.dwFileLenth);
        ByteArrayInputStream byteArrInputPerson = new ByteArrayInputStream(bufferPerson);

        try {
          personBufferedImage = ImageIO.read(byteArrInputPerson);
          if (personBufferedImage != null) {
            File personFile = new File(strPersonPicPathName);
            if (personFile != null) {
              ImageIO.write(personBufferedImage, "jpg", personFile);
            }
          }
        } catch (IOException e2) {
          e2.printStackTrace();
        }
      }

      ///////////// 保存对比图 //////////////////////
      if (TargetRecognitionInfo.nRetCandidatesExNum > 0
          && TargetRecognitionInfo.stuCandidatesEx != null) {
        int maxValue = -1;

        // 设备可能返回多张图片，这里只显示相似度最高的
        int[] nSimilary = new int[TargetRecognitionInfo.nRetCandidatesExNum];
        for (int i = 0; i < TargetRecognitionInfo.nRetCandidatesExNum; i++) {
          nSimilary[i] = TargetRecognitionInfo.stuCandidatesEx[i].bySimilarity & 0xff;
        }

        for (int i = 0; i < nSimilary.length; i++) {
          if (maxValue < nSimilary[i]) {
            maxValue = nSimilary[i];
            index = i;
          }
        }

        String strCandidatePicPathName =
            path
                + "\\"
                + TargetRecognitionInfo.UTC.toStringTitle()
                + "_TargetRecognition_Candidate.jpg";

        // 每个候选人的图片个数：TargetRecognitionInfo.stuCandidatesEx[index].stPersonInfo.wFacePicNum，
        // 正常情况下只有1张。如果有多张，此demo只显示第一张
        byte[] bufferCandidate =
            pBuffer.getByteArray(
                TargetRecognitionInfo.stuCandidatesEx[index].stPersonInfo.szFacePicInfo[0].dwOffSet,
                TargetRecognitionInfo
                    .stuCandidatesEx[index]
                    .stPersonInfo
                    .szFacePicInfo[0]
                    .dwFileLenth);
        ByteArrayInputStream byteArrInputCandidate = new ByteArrayInputStream(bufferCandidate);

        try {
          candidateBufferedImage = ImageIO.read(byteArrInputCandidate);
          if (candidateBufferedImage != null) {
            File candidateFile = new File(strCandidatePicPathName);
            if (candidateFile != null) {
              ImageIO.write(candidateBufferedImage, "jpg", candidateFile);
            }
          }
        } catch (IOException e2) {
          e2.printStackTrace();
        }
      }
    }

    /**
     * 保存目标检测事件图片
     *
     * @param pBuffer 抓拍图片信息
     * @param dwBufSize 抓拍图片大小
     * @param faceDetectInfo 目标检测事件信息
     */
    public void saveFaceDetectPic(
        Pointer pBuffer, int dwBufSize, DEV_EVENT_FACEDETECT_INFO faceDetectInfo)
        throws FileNotFoundException {
      File path = new File("./FaceDetection/");
      if (!path.exists()) {
        path.mkdir();
      }

      if (pBuffer == null || dwBufSize <= 0) {
        return;
      }

      // 小图的 stuObject.nRelativeID 来匹配大图的 stuObject.nObjectID，来判断是不是 一起的图片
      if (groupId != faceDetectInfo.stuObject.nRelativeID) { // /->保存全景图
        personBufferedImage = null;
        groupId = faceDetectInfo.stuObject.nObjectID;

        String strGlobalPicPathName =
            path + "\\" + faceDetectInfo.UTC.toStringTitle() + "_FaceDetection_Global.jpg";
        byte[] bufferGlobal = pBuffer.getByteArray(0, dwBufSize);
        ByteArrayInputStream byteArrInputGlobal = new ByteArrayInputStream(bufferGlobal);

        try {
          globalBufferedImage = ImageIO.read(byteArrInputGlobal);
          if (globalBufferedImage != null) {
            File globalFile = new File(strGlobalPicPathName);
            if (globalFile != null) {
              ImageIO.write(globalBufferedImage, "jpg", globalFile);
            }
          }
        } catch (IOException e2) {
          e2.printStackTrace();
        }
      } else if (groupId == faceDetectInfo.stuObject.nRelativeID) { // /->保存人脸图
        if (faceDetectInfo.stuObject.stPicInfo != null) {
          String strPersonPicPathName =
              path + "\\" + faceDetectInfo.UTC.toStringTitle() + "_FaceDetection_Person.jpg";
          byte[] bufferPerson = pBuffer.getByteArray(0, dwBufSize);
          ByteArrayInputStream byteArrInputPerson = new ByteArrayInputStream(bufferPerson);

          try {
            personBufferedImage = ImageIO.read(byteArrInputPerson);
            if (personBufferedImage != null) {
              File personFile = new File(strPersonPicPathName);
              if (personFile != null) {
                ImageIO.write(personBufferedImage, "jpg", personFile);
              }
            }
          } catch (IOException e2) {
            e2.printStackTrace();
          }
        }
      }
    }
  }

  private static class TargetRecognitionRunnable implements Runnable {
    private BufferedImage globalBufferedImage;
    private BufferedImage personBufferedImage;
    private BufferedImage candidateBufferedImage;
    private DEV_EVENT_FACERECOGNITION_INFO TargetRecognitionInfo;
    private int index = -1;

    public TargetRecognitionRunnable(
        BufferedImage globalBufferedImage,
        BufferedImage personBufferedImage,
        BufferedImage candidateBufferedImage,
        DEV_EVENT_FACERECOGNITION_INFO TargetRecognitionInfo,
        int index) {

      this.globalBufferedImage = globalBufferedImage;
      this.personBufferedImage = personBufferedImage;
      this.candidateBufferedImage = candidateBufferedImage;
      this.TargetRecognitionInfo = TargetRecognitionInfo;
      this.index = index;
    }

    @Override
    public void run() {
      if (!isAttach) {
        return;
      }

      // 列表显示事件信息
      showTargetRecognitionEventInfo(
          globalBufferedImage,
          personBufferedImage,
          candidateBufferedImage,
          TargetRecognitionInfo,
          index);
    }
  }

  private static class FaceDetectRunnable implements Runnable {
    private BufferedImage globalBufferedImage = null;
    private BufferedImage personBufferedImage = null;
    private DEV_EVENT_FACEDETECT_INFO facedetectInfo = null;

    public FaceDetectRunnable(
        BufferedImage globalBufferedImage,
        BufferedImage personBufferedImage,
        DEV_EVENT_FACEDETECT_INFO facedetectInfo) {

      this.globalBufferedImage = globalBufferedImage;
      this.personBufferedImage = personBufferedImage;
      this.facedetectInfo = facedetectInfo;
    }

    @Override
    public void run() {
      if (!isAttach) {
        return;
      }

      showFaceDetectEventInfo(globalBufferedImage, personBufferedImage, facedetectInfo);
    }
  }

  private static void showTargetRecognitionEventInfo(
      BufferedImage globalBufferedImage,
      BufferedImage personBufferedImage,
      BufferedImage candidateBufferedImage,
      DEV_EVENT_FACERECOGNITION_INFO targetRecognitionInfo,
      int index) {
    globalPicLabel.setText(
        Res.string().getGlobalPicture()
            + " ------ ["
            + Res.string().getTargetRecognitionEvent()
            + "]");

    // 全景图
    if (globalBufferedImage != null) {
      globalPicShowPanel.setImage(globalBufferedImage);
      globalPicShowPanel.setOpaque(false);
      globalPicShowPanel.repaint();
    } else {
      globalPicShowPanel.setOpaque(true);
      globalPicShowPanel.repaint();
    }

    // 人脸图
    if (personBufferedImage != null) {
      personPicShowPanel.setImage(personBufferedImage);
      personPicShowPanel.setOpaque(false);
      personPicShowPanel.repaint();
    } else {
      personPicShowPanel.setOpaque(true);
      personPicShowPanel.repaint();
    }

    // 候选人图
    if (candidateBufferedImage != null) {
      candidatePicShowPanel.setImage(candidateBufferedImage);
      candidatePicShowPanel.setOpaque(false);
      candidatePicShowPanel.repaint();
    } else {
      candidatePicShowPanel.setOpaque(true);
      candidatePicShowPanel.repaint();
    }

    // 时间
    if (targetRecognitionInfo.UTC == null || targetRecognitionInfo.UTC.toString().equals("")) {
      timeTextField.setText("");
    } else {
      timeTextField.setText(targetRecognitionInfo.UTC.toString());
    }

    // 人脸信息
    if (targetRecognitionInfo.stuFaceData == null) {
      sexTextField.setText("");
      ageTextField.setText("");
      eyeTextField.setText("");
      mouthTextField.setText("");
      maskTextField.setText("");
      beardTextField.setText("");
    } else {
      sexTextField.setText(Res.string().getSex(targetRecognitionInfo.stuFaceData.emSex));
      if (targetRecognitionInfo.stuFaceData.nAge == -1) {
        ageTextField.setText(Res.string().getUnKnow());
      } else {
        ageTextField.setText(String.valueOf(targetRecognitionInfo.stuFaceData.nAge));
      }
      eyeTextField.setText(Res.string().getEyeState(targetRecognitionInfo.stuFaceData.emEye));
      mouthTextField.setText(Res.string().getMouthState(targetRecognitionInfo.stuFaceData.emMouth));
      maskTextField.setText(Res.string().getMaskState(targetRecognitionInfo.stuFaceData.emMask));
      beardTextField.setText(Res.string().getBeardState(targetRecognitionInfo.stuFaceData.emBeard));
    }

    // 候选人信息
    if (targetRecognitionInfo.nRetCandidatesExNum == 0 || index == -1) {
      nameTextField.setText("");
      sexTextField2.setText("");
      birthdayTextField.setText("");
      idNoTextField.setText("");
      groupIdTextField.setText("");
      groupNameTextField.setText("");
      similaryTextField.setText(Res.string().getStranger());
    } else {
      sexTextField2.setText(
          Res.string()
              .getSex(targetRecognitionInfo.stuCandidatesEx[index].stPersonInfo.bySex & 0xff));
      birthdayTextField.setText(
          String.valueOf((int) targetRecognitionInfo.stuCandidatesEx[index].stPersonInfo.wYear)
              + "-"
              + String.valueOf(
                  targetRecognitionInfo.stuCandidatesEx[index].stPersonInfo.byMonth & 0xff)
              + "-"
              + String.valueOf(
                  targetRecognitionInfo.stuCandidatesEx[index].stPersonInfo.byDay & 0xff));

      try {
        nameTextField.setText(
            new String(targetRecognitionInfo.stuCandidatesEx[index].stPersonInfo.szPersonName, "GBK")
                .trim());
        idNoTextField.setText(
            new String(targetRecognitionInfo.stuCandidatesEx[index].stPersonInfo.szID, "GBK").trim());
        groupIdTextField.setText(
            new String(targetRecognitionInfo.stuCandidatesEx[index].stPersonInfo.szGroupID, "GBK")
                .trim());
        groupNameTextField.setText(
            new String(targetRecognitionInfo.stuCandidatesEx[index].stPersonInfo.szGroupName, "GBK")
                .trim());
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }

      similaryTextField.setText(
          String.valueOf(targetRecognitionInfo.stuCandidatesEx[index].bySimilarity & 0xff));
    }
  }

  private static void showFaceDetectEventInfo(
      BufferedImage globalBufferedImage,
      BufferedImage personBufferedImage,
      DEV_EVENT_FACEDETECT_INFO facedetectInfo) {

    globalPicLabel.setText(
        Res.string().getGlobalPicture() + " ------ [" + Res.string().getTargetDetectEvent() + "]");

    // 全景图
    if (globalBufferedImage != null) {
      globalPicShowPanel.setImage(globalBufferedImage);
      globalPicShowPanel.setOpaque(false);
      globalPicShowPanel.repaint();
    } else {
      globalPicShowPanel.setOpaque(true);
      globalPicShowPanel.repaint();
    }

    // 人脸图
    if (personBufferedImage != null) {
      personPicShowPanel.setImage(personBufferedImage);
      personPicShowPanel.setOpaque(false);
      personPicShowPanel.repaint();
    } else {
      personPicShowPanel.setOpaque(true);
      personPicShowPanel.repaint();
    }

    // 时间
    if (facedetectInfo.UTC == null || facedetectInfo.UTC.toString().equals("")) {
      timeTextField.setText("");
    } else {
      timeTextField.setText(facedetectInfo.UTC.toString());
    }

    // 人脸信息
    sexTextField.setText(Res.string().getSex(facedetectInfo.emSex));
    if (facedetectInfo.nAge == -1) {
      ageTextField.setText(Res.string().getUnKnow());
    } else {
      ageTextField.setText(String.valueOf(facedetectInfo.nAge));
    }
    eyeTextField.setText(Res.string().getEyeState(facedetectInfo.emEye));
    mouthTextField.setText(Res.string().getMouthState(facedetectInfo.emMouth));
    maskTextField.setText(Res.string().getMaskState(facedetectInfo.emMask));
    beardTextField.setText(Res.string().getBeardState(facedetectInfo.emBeard));

    // 候选人图和信息, 重绘清空
    candidatePicShowPanel.setOpaque(true);
    candidatePicShowPanel.repaint();

    nameTextField.setText("");
    sexTextField2.setText("");
    birthdayTextField.setText("");
    idNoTextField.setText("");
    groupIdTextField.setText("");
    groupNameTextField.setText("");
    similaryTextField.setText("");
  }

  private void setEnable(boolean bln) {

    chnComboBox.setEnabled(bln);
    realplayBtn.setEnabled(bln);
    attachBtn.setEnabled(bln);
    faceDataBaseBtn.setEnabled(bln);
    faceEventRecordBtn.setEnabled(bln);
    searchByPicBtn.setEnabled(bln);
  }

  /*
   * 登录
   */
  private LoginPanel loginPanel;

  /*
   * 预览
   */
  private JLabel chnlabel;
  private JComboBox chnComboBox;
  private JButton realplayBtn;
  private JButton attachBtn;
  private JButton faceDataBaseBtn;
  private JButton faceEventRecordBtn;
  /** 以图搜图按钮 */
  private JButton searchByPicBtn;

  private Panel realplayWindowPanel;
  private static PaintPanel globalPicShowPanel;
  private static PaintPanel personPicShowPanel;
  private static PaintPanel candidatePicShowPanel;

  private static JLabel globalPicLabel;

  /*
   * 人脸信息
   */
  private static JTextField timeTextField;
  private static JTextField sexTextField;
  private static JTextField ageTextField;
  private static JTextField eyeTextField;
  private static JTextField mouthTextField;
  private static JTextField maskTextField;
  private static JTextField beardTextField;

  /*
   * 候选人信息
   */
  private static JTextField nameTextField;
  private static JTextField sexTextField2;
  private static JTextField birthdayTextField;
  private static JTextField idNoTextField;
  private static JTextField groupIdTextField;
  private static JTextField groupNameTextField;
  private static JTextField similaryTextField;
}

public class TargetRecognition {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            TargetRecognitionFrame demo = new TargetRecognitionFrame();
            demo.setVisible(true);
          }
        });
  }
}
