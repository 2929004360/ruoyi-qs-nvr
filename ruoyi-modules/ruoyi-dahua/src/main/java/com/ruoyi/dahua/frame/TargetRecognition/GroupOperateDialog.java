package com.ruoyi.dahua.frame.TargetRecognition;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib.NET_FACERECONGNITION_GROUP_INFO;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.TargetRecognitionModule;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;

public class GroupOperateDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 人脸库名称
	private String inputGroupName = "";
	
	// 布控界面
	public DispositionOperateDialog dispositionOperateDialog = null;
	
	// 人员操作界面
	public PersonOperateDialog personOperateDialog = null;

	public GroupOperateDialog() {
	    setTitle(Res.string().getGroupOperate());
	    setLayout(new BorderLayout());
	    setModal(true);  
	    pack();
	    setSize(650, 360);
	    setResizable(false);
	    setLocationRelativeTo(null);  
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);   // 释放窗体
	    
	    GroupListPanel GroupPanel = new GroupListPanel();
	    GroupOperatePanel GroupOperatePanel = new GroupOperatePanel();
	    
	    add(GroupPanel, BorderLayout.CENTER);
	    add(GroupOperatePanel, BorderLayout.EAST);
	    
	    findGroupInfo();
	    
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		dispose();
	    	}
	    });
	}
	
	/*
	 * 人脸库显示列表
	 */
	private class GroupListPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public GroupListPanel() {
			BorderEx.set(this, "", 2);
			setLayout(new BorderLayout());
			
			data = new Object[20][3];
			defaultTableModel = new DefaultTableModel(data, Res.string().getGroupTable());
			table = new JTable(defaultTableModel) {   // 列表不可编辑
				private static final long serialVersionUID = 1L;
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
			table.getColumnModel().getColumn(0).setPreferredWidth(80);
			table.getColumnModel().getColumn(1).setPreferredWidth(280);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
			
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 只能选中一行
			
			DefaultTableCellRenderer dCellRenderer = new DefaultTableCellRenderer();
			dCellRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.setDefaultRenderer(Object.class, dCellRenderer);
			
			add(new JScrollPane(table), BorderLayout.CENTER);

		}
	}
	
	/*
	 * 人脸库操作
	 */
	private class GroupOperatePanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public GroupOperatePanel() {
			BorderEx.set(this, "", 2);
			setLayout(new BorderLayout());
			
			Dimension dimension = new Dimension();
			dimension.width = 230;
			setPreferredSize(dimension);
			
			JPanel GroupPanel = new JPanel();
			JPanel panel = new JPanel();
			
			add(GroupPanel, BorderLayout.CENTER);
			add(panel, BorderLayout.SOUTH);
			
			/*JButton searchByPicBtn = new JButton(Res.string().getSearchByPic());*/
			JButton personOperateBtn = new JButton(Res.string().getPersonOperate());
			panel.setPreferredSize(new Dimension(230, 45));
			panel.setLayout(new GridLayout(2, 1));
			/*panel.add(searchByPicBtn);*/
			panel.add(personOperateBtn);
			
			/*
			 * 人脸库增删改， 布控、撤控
			 */
			JButton refreshBtn = new JButton(Res.string().getFresh());
			JButton addGroupBtn = new JButton(Res.string().getAddGroup());
			JButton modifyGroupBtn = new JButton(Res.string().getModifyGroup());
			JButton deleteGroupBtn = new JButton(Res.string().getDelGroup());
			JButton dispositionBtn = new JButton(Res.string().getDisposition() + "/" + Res.string().getDelDisposition());
			
			GroupPanel.setLayout(new GridLayout(12, 1));
			GroupPanel.add(refreshBtn);
			GroupPanel.add(addGroupBtn);
			GroupPanel.add(modifyGroupBtn);
			GroupPanel.add(deleteGroupBtn);
			GroupPanel.add(dispositionBtn);
			
			// 刷新人脸库列表
			refreshBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					findGroupInfo();		
				}
			});
						
			// 添加人脸库
			addGroupBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					inputGroupName = JOptionPane.showInputDialog(GroupOperateDialog.this, 
							 									 Res.string().getInputGroupName(), "");	
	
					if(inputGroupName == null) {   // 取消或者关闭按钮
						return;
					} 
	
					if(TargetRecognitionModule.addGroup(inputGroupName)) {
						JOptionPane.showMessageDialog(null, Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}
		
					// 更新人脸库列表
					findGroupInfo();		
				}
			});
			
			// 修改人脸库
			modifyGroupBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {				
					int row = -1;
					row = table.getSelectedRow(); //获得所选的单行
					
					if(row < 0) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectGroup(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}						

					if(defaultTableModel.getValueAt(row, 0) == null || String.valueOf(defaultTableModel.getValueAt(row, 0)).trim().equals("")) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectGroup(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
								
					inputGroupName = JOptionPane.showInputDialog(GroupOperateDialog.this, 
																 Res.string().getInputGroupName(), String.valueOf(defaultTableModel.getValueAt(row, 1)).trim());	
					
					if(inputGroupName == null) { // 取消或者关闭按钮
						return;
					} 
										
					if(TargetRecognitionModule.modifyGroup(inputGroupName, String.valueOf(defaultTableModel.getValueAt(row, 0)).trim())) {
						JOptionPane.showMessageDialog(null, Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}
					
					// 更新人脸库列表
					findGroupInfo(); 			
				}
			});
			
			// 删除人脸库
			deleteGroupBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int row = -1;
					row = table.getSelectedRow(); //获得所选的单行
					
					if(row < 0) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectGroup(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
								
					if(defaultTableModel.getValueAt(row, 0) == null || String.valueOf(defaultTableModel.getValueAt(row, 0)).trim().equals("")) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectGroup(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
						
					if(!TargetRecognitionModule.deleteGroup(String.valueOf(defaultTableModel.getValueAt(row, 0)).trim())) {
						JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
					}
					
					// 更新人脸库列表
					findGroupInfo(); 				
				}
			});
			
			// 布控/撤控
			dispositionBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int row = -1;
					row = table.getSelectedRow(); //获得所选的单行
					
					if(row < 0) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectGroup(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
								
					if(defaultTableModel.getValueAt(row, 0) == null || String.valueOf(defaultTableModel.getValueAt(row, 0)).trim().equals("")) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectGroup(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}			
					
					dispositionOperateDialog = new DispositionOperateDialog(String.valueOf(defaultTableModel.getValueAt(row, 0)).trim(), 
																	      String.valueOf(defaultTableModel.getValueAt(row, 1)).trim());
					dispositionOperateDialog.setVisible(true);	
							
				}
			});
			
			/*// 以图搜图
			searchByPicBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SearchByPicDialog dialog = new SearchByPicDialog();
					dialog.setVisible(true);
				}
			});*/
			
			// 人员操作
			personOperateBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int row = -1;
					row = table.getSelectedRow(); //获得所选的单行
					
					if(row < 0) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectGroup(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
								
					if(defaultTableModel.getValueAt(row, 0) == null || String.valueOf(defaultTableModel.getValueAt(row, 0)).trim().equals("")) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectGroup(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					personOperateDialog = new PersonOperateDialog(String.valueOf(defaultTableModel.getValueAt(row, 0)).trim(), 
															    String.valueOf(defaultTableModel.getValueAt(row, 1)).trim());
					personOperateDialog.setVisible(true);				
				}
			});
		}
	}
	
	/*
	 *  查找所有人脸库
	 */
	private void findGroupInfo() {
		// 清空列表
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 3; j++) {
				defaultTableModel.setValueAt("", i, j);
			}
		}	
		
		// 查询人脸库
		NET_FACERECONGNITION_GROUP_INFO[] groupInfoArr = TargetRecognitionModule.findGroupInfo("");
		if(groupInfoArr != null) {
			for(int i = 0; i < groupInfoArr.length; i++) {
				defaultTableModel.setValueAt(new String(groupInfoArr[i].szGroupId).trim(), i, 0);
				try {
					defaultTableModel.setValueAt(new String(groupInfoArr[i].szGroupName, "GBK").trim(), i, 1);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				defaultTableModel.setValueAt(String.valueOf(groupInfoArr[i].nGroupSize).trim(), i, 2);
			}
		}
	}
	
	private Object[][] data;
	private DefaultTableModel defaultTableModel;
	private JTable table;
}
