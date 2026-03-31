package com.ruoyi.dahua.frame.TargetRecognition;

import com.ruoyi.dahua.common.*;
import com.ruoyi.dahua.lib.NetSDKLib.CANDIDATE_INFOEX;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.TargetRecognitionModule;
import com.sun.jna.Memory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ModifyPersonDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String groupId = ""; 
	private String groupName = "";
	private String uid = "";
	private String pszFileDst = "";
	private Memory memory = null;
	private CANDIDATE_INFOEX stuCandidate = null;
	
	private WindowCloseListener listener;
	public void addWindowCloseListener(WindowCloseListener listener) {
		this.listener = listener;
	}
	
	/**
	 * @param groupId 人脸库ID
	 * @param groupName  人脸库名称
	 * @param uid  人员标识符
	 * @param pszFileDst  下载到本地的图片路径
	 * @param memory  图片数据
	 * @param stuCandidate  人员信息
	 */
	public ModifyPersonDialog(String groupId, 
							  String groupName, 
							  String uid, 
							  String pszFileDst, 
							  Memory memory, 
							  CANDIDATE_INFOEX stuCandidate){
		
		setTitle(Res.string().getModifyPerson());
		setLayout(new BorderLayout());
	    setModal(true); 
		pack();
		setSize(520, 400);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);   // 释放窗体
	    
		this.groupId = groupId;
		this.groupName = groupName;
		this.uid = uid;
		this.pszFileDst = pszFileDst;
		this.memory = memory;
		this.stuCandidate = stuCandidate;
		
		FaceServerAddPanel faceServerAddPanel = new FaceServerAddPanel();
        add(faceServerAddPanel, BorderLayout.CENTER);
		
        showPersonInfo();
        
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
	}
	public class FaceServerAddPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public FaceServerAddPanel() {
			BorderEx.set(this, "", 4);
			setLayout(new BorderLayout());
			
			JPanel imagePanel = new JPanel();
			JPanel personInfoPanel = new JPanel();
			
			Dimension dimension = this.getPreferredSize();
			dimension.height = 400;
			dimension.width = 250;
			personInfoPanel.setPreferredSize(dimension);
			
			add(imagePanel, BorderLayout.CENTER);
			add(personInfoPanel, BorderLayout.WEST);
			
			/////////// 添加的人脸图片面板 //////////////////
			imagePanel.setLayout(new BorderLayout());
			addImagePanel = new PaintPanel();   // 添加的人员信息图片显示
			selectImageBtn = new JButton(Res.string().getSelectPicture());
			imagePanel.add(addImagePanel, BorderLayout.CENTER);
			imagePanel.add(selectImageBtn, BorderLayout.SOUTH);
			
			////////// 添加的人脸信息面板 /////////////////
			personInfoPanel.setLayout(new FlowLayout());
			JLabel goroupIdLabel = new JLabel(Res.string().getFaceGroupId(), JLabel.CENTER);
			JLabel goroupNameLabel = new JLabel(Res.string().getFaceGroupName(), JLabel.CENTER);
			JLabel uidLabel = new JLabel(Res.string().getUid(), JLabel.CENTER);
			JLabel nameLabel = new JLabel(Res.string().getName(), JLabel.CENTER);
			JLabel sexLabel = new JLabel(Res.string().getSex(), JLabel.CENTER);
			JLabel birthdayLabel = new JLabel(Res.string().getBirthday(), JLabel.CENTER);
			JLabel idTypeLabel = new JLabel(Res.string().getIdType(), JLabel.CENTER);
			JLabel idLabel = new JLabel(Res.string().getIdNo(), JLabel.CENTER);
			
			Dimension dimension2 = new Dimension();
			dimension2.width = 80;
			dimension2.height = 20;
			uidLabel.setPreferredSize(dimension2);
			goroupIdLabel.setPreferredSize(dimension2);
			goroupNameLabel.setPreferredSize(dimension2);
			nameLabel.setPreferredSize(dimension2);
			sexLabel.setPreferredSize(dimension2);
			birthdayLabel.setPreferredSize(dimension2);
			idTypeLabel.setPreferredSize(dimension2);
			idLabel.setPreferredSize(dimension2);
			
			goroupIdTextField = new JTextField();
			goroupNameTextField = new JTextField();
			uidTextField = new JTextField();
			nameTextField = new JTextField();
			sexComboBox = new JComboBox(Res.string().getSexStrings());
			birthdayBtn = new DateChooserJButtonEx(""); 
			idTypeComboBox = new JComboBox(Res.string().getIdStrings());
			idTextField = new JTextField();
			birthdayCheckBox = new JCheckBox();
			
			modifyBtn = new JButton(Res.string().getModify());
			cancelBtn = new JButton(Res.string().getCancel());
			
			birthdayBtn.setStartYear(1900);
			
			Dimension dimension3 = new Dimension();
			dimension3.width = 150;
			dimension3.height = 20;
			
			sexComboBox.setPreferredSize(dimension3);	
			idTypeComboBox.setPreferredSize(dimension3);
			goroupIdTextField.setPreferredSize(dimension3);
			goroupNameTextField.setPreferredSize(dimension3);
			uidTextField.setPreferredSize(dimension3);
			nameTextField.setPreferredSize(dimension3);
			idTextField.setPreferredSize(dimension3);
			birthdayBtn.setPreferredSize(new Dimension(130, 20));
			birthdayCheckBox.setPreferredSize(new Dimension(20, 20));
			modifyBtn.setPreferredSize(new Dimension(120, 20));
			cancelBtn.setPreferredSize(new Dimension(120, 20));

			goroupIdTextField.setEditable(false);
			goroupNameTextField.setEditable(false);
			uidTextField.setEditable(false);
			birthdayCheckBox.setSelected(true);
			
			goroupIdTextField.setText(groupId);
			goroupNameTextField.setText(groupName);
			uidTextField.setText(uid);
			
			personInfoPanel.add(goroupIdLabel);
			personInfoPanel.add(goroupIdTextField);
			personInfoPanel.add(goroupNameLabel);
			personInfoPanel.add(goroupNameTextField);
			personInfoPanel.add(uidLabel);
			personInfoPanel.add(uidTextField);
			personInfoPanel.add(nameLabel);
			personInfoPanel.add(nameTextField);
			personInfoPanel.add(sexLabel);
			personInfoPanel.add(sexComboBox);
			personInfoPanel.add(idTypeLabel);
			personInfoPanel.add(idTypeComboBox);
			personInfoPanel.add(idLabel);
			personInfoPanel.add(idTextField);
			personInfoPanel.add(birthdayLabel);
			personInfoPanel.add(birthdayBtn);
			personInfoPanel.add(birthdayCheckBox);
			personInfoPanel.add(modifyBtn);
			personInfoPanel.add(cancelBtn);
			
			birthdayCheckBox.addChangeListener(new ChangeListener() {		
				@Override
				public void stateChanged(ChangeEvent arg0) {
					if(birthdayCheckBox.isSelected()) {
						birthdayBtn.setEnabled(true);
					} else {
						birthdayBtn.setEnabled(false);
					}
				}
			});
			
			// 选择图片，获取图片的信息
			selectImageBtn.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String picPath = "";
					
					// 选择图片，获取图片路径，并在界面显示
					picPath = ToolKits.openPictureFile(addImagePanel);
							
					if(!picPath.equals("")) {
						memory = null;
						try {
							memory = ToolKits.readPictureFile(picPath);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}				
				}
			});
			
			// 修改人员信息
			modifyBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean bRet = TargetRecognitionModule.modifyPerson(groupId, uid,
																      memory, 
																      nameTextField.getText(), 
																      sexComboBox.getSelectedIndex(), 
																      birthdayCheckBox.isSelected(), birthdayBtn.getText().toString(), 
																      idTypeComboBox.getSelectedIndex(), idTextField.getText());								

					if(bRet) {
						JOptionPane.showMessageDialog(null, Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, Res.string().getFailed() + "," + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}
	
					dispose();
					
					listener.windowClosing();								
				}
			});
			
			// 取消，关闭
			cancelBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();	
				}
			});
		}		 
	}
	
	private void showPersonInfo() {
		File file = null;
		if(!pszFileDst.equals("")) {
			file = ToolKits.openPictureFile(pszFileDst, addImagePanel);		
		}
		
		if(file != null) {
			file.delete();
		}
		
		try {
			nameTextField.setText(new String(stuCandidate.stPersonInfo.szPersonName, "GBK").trim());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		sexComboBox.setSelectedIndex(stuCandidate.stPersonInfo.bySex & 0xff);
		
		idTypeComboBox.setSelectedIndex(stuCandidate.stPersonInfo.byIDType & 0xff);
		
		idTextField.setText(new String(stuCandidate.stPersonInfo.szID).trim());
		
		if(stuCandidate.stPersonInfo == null) {
			birthdayCheckBox.setSelected(false);
			birthdayBtn.setText(birthdayBtn.getDate().toString());
		} else {
			if(String.valueOf((int)stuCandidate.stPersonInfo.wYear).equals("0000") 
				|| String.valueOf((int)stuCandidate.stPersonInfo.wYear).equals("0") 
				|| String.valueOf( stuCandidate.stPersonInfo.byMonth & 0xff).equals("0") 
				|| String.valueOf( stuCandidate.stPersonInfo.byMonth & 0xff).equals("00") 
				|| String.valueOf(stuCandidate.stPersonInfo.byDay & 0xff).equals("0") 
				|| String.valueOf(stuCandidate.stPersonInfo.byDay & 0xff).equals("00")) {
				birthdayCheckBox.setSelected(false);
				birthdayBtn.setText(birthdayBtn.getDate().toString());
			} else {
				birthdayCheckBox.setSelected(true);
				birthdayBtn.setText(String.valueOf((int)stuCandidate.stPersonInfo.wYear) + "-" + 
									                  String.valueOf( stuCandidate.stPersonInfo.byMonth & 0xff) + "-" + 
									                  String.valueOf(stuCandidate.stPersonInfo.byDay & 0xff));
			}
		}
	}
	
	// 添加人员信息窗口的组件
	public PaintPanel addImagePanel;
	private JButton selectImageBtn;
	
	private JTextField goroupIdTextField;
	private JTextField goroupNameTextField;
	private JTextField uidTextField;
	public JTextField nameTextField;
	public JComboBox sexComboBox;
	public DateChooserJButtonEx birthdayBtn; 
	public JComboBox idTypeComboBox;
	public JTextField idTextField;
	private JButton modifyBtn;
	private JButton cancelBtn;
	public JCheckBox birthdayCheckBox;
}
