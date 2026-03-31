package com.ruoyi.dahua.frame.Gate;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.DateChooserJButton;
import com.ruoyi.dahua.common.PaintPanel;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.GateModule;
import com.sun.jna.Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class AddCardDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	
	private Memory memory = null;
	
	private String picPath = "";

	public AddCardDialog(){
		setTitle(Res.string().getAdd() + Res.string().getCardInfo());
		setLayout(new BorderLayout());
	    setModal(true);
		pack();
		setSize(520, 390);
	    setResizable(false);
	    setLocationRelativeTo(null);    
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);   // 释放窗体
		
		CardInfoPanel cardInfoPanel = new CardInfoPanel();
		ImagePanel imagePanel = new ImagePanel();
		
        add(cardInfoPanel, BorderLayout.CENTER);
        add(imagePanel, BorderLayout.EAST);
        
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				clear();
				dispose();
			}
		});
	}
	
	/**
	 * 卡信息
	 */
	private class CardInfoPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		public CardInfoPanel() {
			BorderEx.set(this, Res.string().getCardInfo(), 4);
			setLayout(new FlowLayout());
			
			JLabel cardNoLabel = new JLabel(Res.string().getCardNo() + ":", JLabel.CENTER);
			JLabel userIdLabel = new JLabel(Res.string().getUserId() + ":", JLabel.CENTER);
			JLabel cardNameLabel = new JLabel(Res.string().getCardName() + ":", JLabel.CENTER);
			JLabel cardPasswdLabel = new JLabel(Res.string().getCardPassword() + ":", JLabel.CENTER);
			JLabel cardStatusLabel = new JLabel(Res.string().getCardStatus() + ":", JLabel.CENTER);
			JLabel cardTypeLabel = new JLabel(Res.string().getCardType() + ":", JLabel.CENTER);
			JLabel useTimesLabel = new JLabel(Res.string().getUseTimes() + ":", JLabel.CENTER);
			JLabel validPeriodLabel = new JLabel(Res.string().getValidPeriod() + ":", JLabel.CENTER);
			
			Dimension dimension = new Dimension();
			dimension.width = 85;
			dimension.height = 20;
			cardNoLabel.setPreferredSize(dimension);
			userIdLabel.setPreferredSize(dimension);
			cardNameLabel.setPreferredSize(dimension);
			cardPasswdLabel.setPreferredSize(dimension);
			cardStatusLabel.setPreferredSize(dimension);
			cardTypeLabel.setPreferredSize(dimension);
			useTimesLabel.setPreferredSize(dimension);
			validPeriodLabel.setPreferredSize(dimension);
			
			cardNoTextField = new JTextField();
			userIdTextField = new JTextField();
			cardNameTextField = new JTextField();
			cardPasswdField = new JPasswordField();		
			cardStatusComboBox = new JComboBox(Res.string().getCardStatusList());
			cardTypeComboBox = new JComboBox(Res.string().getCardTypeList());	
			useTimesTextField = new JTextField("0");
			firstEnterCheckBox = new JCheckBox(Res.string().getIsFirstEnter());
			enableCheckBox = new JCheckBox(Res.string().getEnable());
			enableCheckBox.setSelected(true);
			enableCheckBox.setVisible(false);
			startTimeBtn = new DateChooserJButton();
			endTimeBtn = new DateChooserJButton();
			
			cardNoTextField.setPreferredSize(new Dimension(145, 20));
			userIdTextField.setPreferredSize(new Dimension(145, 20));
			cardNameTextField.setPreferredSize(new Dimension(145, 20));
			cardPasswdField.setPreferredSize(new Dimension(145, 20));
			useTimesTextField.setPreferredSize(new Dimension(145, 20));	
			cardStatusComboBox.setPreferredSize(new Dimension(145, 20));
			cardTypeComboBox.setPreferredSize(new Dimension(145, 20));
			startTimeBtn.setPreferredSize(new Dimension(145, 20));
			endTimeBtn.setPreferredSize(new Dimension(145, 20));
			firstEnterCheckBox.setPreferredSize(new Dimension(170, 20));
			enableCheckBox.setPreferredSize(new Dimension(70, 20));
			
			JLabel nullLabel1 = new JLabel();
			JLabel nullLabel2 = new JLabel();
			JLabel nullLabel3 = new JLabel();
			nullLabel1.setPreferredSize(new Dimension(5, 20));
			nullLabel2.setPreferredSize(new Dimension(30, 20));
			nullLabel3.setPreferredSize(new Dimension(85, 20));

			addBtn = new JButton(Res.string().getAdd());
			cancelBtn = new JButton(Res.string().getCancel());
			JLabel nullLabel4 = new JLabel();
			nullLabel4.setPreferredSize(new Dimension(250, 20));
			addBtn.setPreferredSize(new Dimension(110, 20));
			cancelBtn.setPreferredSize(new Dimension(110, 20));
			
			add(cardNoLabel);
			add(cardNoTextField);
			add(userIdLabel);
			add(userIdTextField);
			
			add(cardNameLabel);
			add(cardNameTextField);
			add(cardPasswdLabel);
			add(cardPasswdField);
			
			add(cardStatusLabel);
			add(cardStatusComboBox);
			add(cardTypeLabel);
			add(cardTypeComboBox);
			
			add(useTimesLabel);
			add(useTimesTextField);
			add(nullLabel1);
			add(firstEnterCheckBox);
			add(nullLabel2);
			add(enableCheckBox);
			
			add(validPeriodLabel);
			add(startTimeBtn);
			add(nullLabel3);
			add(endTimeBtn);
			
			add(nullLabel4);
			add(addBtn);
			add(cancelBtn);
			
			// 添加
			addBtn.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(cardNoTextField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, Res.string().getInputCardNo(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(userIdTextField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, Res.string().getInputUserId(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					

					
					if(memory == null) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectPicture(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					try {
						if (cardNoTextField.getText().getBytes("UTF-8").length > 31) {
							JOptionPane.showMessageDialog(null, Res.string().getCardNoExceedLength() + "(31)", Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						if (userIdTextField.getText().getBytes("UTF-8").length > 31) {
							JOptionPane.showMessageDialog(null, Res.string().getUserIdExceedLength() + "(31)", Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						if (cardNameTextField.getText().getBytes("UTF-8").length > 63) {
							JOptionPane.showMessageDialog(null, Res.string().getCardNameExceedLength() + "(63)", Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						if (new String(cardPasswdField.getPassword()).getBytes("UTF-8").length > 63) {
							JOptionPane.showMessageDialog(null, Res.string().getCardPasswdExceedLength() + "(63)", Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					} 								
					
					// 先添加卡，卡添加成功后，再添加图片
					int useTimes = 0;
					if(useTimesTextField.getText().isEmpty()) {
						useTimes = 0;
					} else {
						useTimes = Integer.parseInt(useTimesTextField.getText());
					}
					
					boolean bCardFlags = GateModule.insertCard(cardNoTextField.getText(), userIdTextField.getText(), cardNameTextField.getText(), 
							new String(cardPasswdField.getPassword()), Res.string().getCardStatusInt(cardStatusComboBox.getSelectedIndex()), 
							Res.string().getCardTypeInt(cardTypeComboBox.getSelectedIndex()), useTimes, 
							firstEnterCheckBox.isSelected() ? 1:0, enableCheckBox.isSelected() ? 1:0, startTimeBtn.getText(), endTimeBtn.getText());
					String cardError = "";
					if(!bCardFlags) {
						cardError = ToolKits.getErrorCodeShow();
					}
					
					
					boolean bFaceFalgs = GateModule.addFaceInfo(userIdTextField.getText(), memory);
					String faceError = "";
					if(!bFaceFalgs) {
						faceError = ToolKits.getErrorCodeShow();
					}

					// 添加卡信息和人脸成功
					if(bCardFlags && bFaceFalgs) {
						JOptionPane.showMessageDialog(null, Res.string().getSucceedAddCardAndPerson(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
					
					// 添加卡信息和人脸失败
					if(!bCardFlags && !bFaceFalgs) {
						JOptionPane.showMessageDialog(null, Res.string().getFailedAddCard() + " : " + cardError, 
														    Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}
					
					// 添加卡信息成功，添加人脸失败
					if(bCardFlags && !bFaceFalgs) {
						JOptionPane.showMessageDialog(null, Res.string().getSucceedAddCardButFailedAddPerson() + " : " + faceError, Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}
					
					// 卡信息已存在，添加人脸成功
					if(!bCardFlags && bFaceFalgs) {
						JOptionPane.showMessageDialog(null, Res.string().getCardExistedSucceedAddPerson(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			
			// 取消
			cancelBtn.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent arg0) {
					clear();
					dispose();				
				}
			});
		}
	}
	
	/**
	 * 选择图片
	 */
	private class ImagePanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		public ImagePanel() {
			BorderEx.set(this, Res.string().getPersonPicture(), 4);
			Dimension dimension = new Dimension();
			dimension.width = 250;
			setPreferredSize(dimension);
			setLayout(new BorderLayout());
			
			addImagePanel = new PaintPanel();   // 添加的人员信息图片显示
			selectImageBtn = new JButton(Res.string().getSelectPicture());
			add(addImagePanel, BorderLayout.CENTER);
			add(selectImageBtn, BorderLayout.SOUTH);
			
			// 选择图片，获取图片的信息
			selectImageBtn.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent arg0) {		
					// 选择图片，获取图片路径，并在界面显示
					picPath = ToolKits.openPictureFile(addImagePanel);
							
					if(!picPath.isEmpty()) {
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
	
	private void clear() {
		memory = null;
		picPath = "";
	}
	
	private PaintPanel addImagePanel;
	private JButton selectImageBtn;
	
	private JTextField cardNoTextField;
	private JTextField userIdTextField;
	private JTextField cardNameTextField;
	private JPasswordField cardPasswdField;		
	private JComboBox cardStatusComboBox;
	private JComboBox cardTypeComboBox;	
	private JTextField useTimesTextField;
	private JCheckBox firstEnterCheckBox;
	private JCheckBox enableCheckBox;
	private DateChooserJButton startTimeBtn;
	private DateChooserJButton endTimeBtn;
	
	private JButton addBtn;
	private JButton cancelBtn;
}
