package com.ruoyi.dahua.frame.TargetRecognition;

import com.ruoyi.dahua.common.*;
import com.ruoyi.dahua.lib.NetSDKLib.NET_FACERECONGNITION_GROUP_INFO;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.LoginModule;
import com.ruoyi.dahua.module.TargetRecognitionModule;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class DispositionOperateDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 通道列表
	private Vector<String> chnlist = new Vector<String>(); 
	
	// 通道个数
	private int nChn = LoginModule.m_stDeviceInfo.byChanNum;
	
	// 通道列表, 用于布控
	private ArrayList< Integer> arrayList = new ArrayList<Integer>();
	
	// key：通道     value：相似度, 用于撤控
	private HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
	
	String groupId = "";
	String groupName = "";

	public DispositionOperateDialog(String groupId, String groupName) {
	    setTitle(Res.string().getDisposition() + "/" + Res.string().getDelDisposition());
	    setLayout(new BorderLayout());
	    setModal(true);   
	    pack();
	    setSize(450, 400);
	    setResizable(false);
	    setLocationRelativeTo(null); 
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);   // 释放窗体
	    
		for(int i = 1; i < nChn + 1; i++) {
			chnlist.add(Res.string().getChannel() + " " + String.valueOf(i));
		}
		
		this.groupId = groupId;
		this.groupName = groupName;
		
	    DispositionListPanel dispositionListPanel = new DispositionListPanel();
	    DispositionInfoPanel dispositionInfoPanel = new DispositionInfoPanel();
	    
	    add(dispositionListPanel, BorderLayout.CENTER);
	    add(dispositionInfoPanel, BorderLayout.NORTH);
	    
	    findChnAndSimilary();
	    
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		dispose();
	    	}
	    });
	}
	
	/*
	 * 布控显示列表
	 */
	private class DispositionListPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public DispositionListPanel() {
			BorderEx.set(this, "", 2);
			setLayout(new BorderLayout());
			
			JPanel panel = new JPanel();
			JPanel panel2 = new JPanel();
			JPanel panel3 = new JPanel();
			
			Dimension dimension = new Dimension();
			dimension.width = 145;
			panel.setPreferredSize(dimension);
			panel.setLayout(new BorderLayout());
			panel.add(panel2, BorderLayout.NORTH);
			panel.add(panel3, BorderLayout.SOUTH);
			
			addBtn = new JButton(Res.string().getAdd());
			refreshBtn = new JButton(Res.string().getFresh());
			panel2.setLayout(new GridLayout(2, 1));
			panel2.add(addBtn);
			panel2.add(refreshBtn);
			
			dispositionBtn = new JButton(Res.string().getDisposition());
			delDispositionBtn = new JButton(Res.string().getDelDisposition());
			panel3.setLayout(new GridLayout(2, 1));
			panel3.add(dispositionBtn);
			panel3.add(delDispositionBtn);
		
			data = new Object[512][2];
			defaultTableModel = new DefaultTableModel(data, Res.string().getDispositionTable());
			table = new JTable(defaultTableModel){   // 列表不可编辑
				private static final long serialVersionUID = 1L;
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
			DefaultTableCellRenderer dCellRenderer = new DefaultTableCellRenderer();
			dCellRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.setDefaultRenderer(Object.class, dCellRenderer);
			
			add(new JScrollPane(table), BorderLayout.CENTER);
			add(panel, BorderLayout.EAST);

			// 添加通道和相似度到列表
			addBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean isExit = false;
					
					String chn = String.valueOf(chnComboBox.getSelectedIndex() + 1);
					String similary = similaryTextField.getText();
					
					if(similaryTextField.getText().equals("") 
							|| Integer.parseInt(similaryTextField.getText()) > 100) {						
						JOptionPane.showMessageDialog(null, Res.string().getSimilarityRange(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);					
						return;
					}
					
					// 如果存在了通道号，列表修改相应的相似度
					for(int i = 0; i < nChn; i++) {
						if(chn.equals(String.valueOf(defaultTableModel.getValueAt(i, 0)).trim())) {
							defaultTableModel.setValueAt(similary, i, 1);
							isExit = true;
							break;
						}
					}
					
					if(isExit) {
						return;
					}
					
					// 如果不存在通道号，按顺序添加人列表
					for(int i = 0; i < nChn; i++) {
						if(String.valueOf(defaultTableModel.getValueAt(i, 0)).trim().equals("") 
								|| defaultTableModel.getValueAt(i, 0) == null) {
							defaultTableModel.setValueAt(chn, i, 0);
							defaultTableModel.setValueAt(similary, i, 1);
							break;
						}
					}						
				}
			});
			
			// 刷新已布控的通道和相似度信息
			refreshBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					findChnAndSimilary();			
				}
			});
			
			// 布控
			dispositionBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					hashMap.clear();
					
					// 获取列表里的数据
					for(int i = 0; i < nChn; i++) {
						// 判断通道号是否为空
						if(!String.valueOf(defaultTableModel.getValueAt(i, 0)).trim().equals("") 
								&& defaultTableModel.getValueAt(i, 0) != null) {		
							
							// 判断相似度是否为空
							if(!String.valueOf(defaultTableModel.getValueAt(i, 1)).trim().equals("") 
								&& defaultTableModel.getValueAt(i, 1) != null) {
								hashMap.put(Integer.parseInt(String.valueOf(defaultTableModel.getValueAt(i, 0)).trim()), 
										Integer.parseInt(String.valueOf(defaultTableModel.getValueAt(i, 1)).trim()));
							} else {
								hashMap.put(Integer.parseInt(String.valueOf(defaultTableModel.getValueAt(i, 0)).trim()), 0);
							}
						} 
					}
					System.out.println("size:" + hashMap.size());
					if(hashMap.size() == 0) {
						JOptionPane.showMessageDialog(null, Res.string().getAddDispositionInfo(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);	
						return;
					}
					
					if(!TargetRecognitionModule.putDisposition(goroupIdTextField.getText(), hashMap)) {
						JOptionPane.showMessageDialog(null, Res.string().getFailed() + "," + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);	
					} else {
						JOptionPane.showMessageDialog(null, Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
					}
					
					// 刷新列表
					findChnAndSimilary();		
				}
			});
			
			delDispositionBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					arrayList.clear();
					
					// 获取所有选中行数
					int[] rows = null;
					rows = table.getSelectedRows();

					if(rows == null) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectDelDispositionInfo(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// 获取所有选中，非空的通道号
					for(int i = 0; i < rows.length; i++) {
						if(!String.valueOf(defaultTableModel.getValueAt(rows[i], 0)).trim().equals("") 
								&& defaultTableModel.getValueAt(rows[i], 0) != null) {
							arrayList.add(Integer.parseInt(String.valueOf(defaultTableModel.getValueAt(rows[i], 0)).trim()));
						}	
					}
					
					if(arrayList.size() == 0) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectDelDispositionInfo(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(!TargetRecognitionModule.delDisposition(goroupIdTextField.getText(), arrayList)) {
						JOptionPane.showMessageDialog(null, Res.string().getFailed() + "," + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);	
					} else {
						JOptionPane.showMessageDialog(null, Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
					}
					
					// 刷新列表
					findChnAndSimilary();				
				}
			});
		}
	}
	
	/*
	 * 布控信息	
	 */
	private class DispositionInfoPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public DispositionInfoPanel() {	
			BorderEx.set(this, "", 2);
			setLayout(new FlowLayout());
			
			Dimension dimension = new Dimension();
			dimension.height = 80;
			setPreferredSize(dimension);
			
			JLabel goroupIdLabel = new JLabel(Res.string().getFaceGroupId(), JLabel.CENTER);
			JLabel goroupNameLabel = new JLabel(Res.string().getFaceGroupName(), JLabel.CENTER);
			JLabel chnLabel = new JLabel(Res.string().getChannel(), JLabel.CENTER);
			JLabel similaryLabel = new JLabel(Res.string().getSimilarity(), JLabel.CENTER);
			
			goroupIdTextField = new JTextField();
			goroupNameTextField = new JTextField();
		    chnComboBox = new JComboBox(chnlist);	
			similaryTextField = new JTextField();
			
			Dimension dimension1 = new Dimension();
			dimension1.width = 80;
			dimension1.height = 20;
			goroupIdLabel.setPreferredSize(dimension1);
			goroupNameLabel.setPreferredSize(dimension1);
			chnLabel.setPreferredSize(dimension1);
			similaryLabel.setPreferredSize(dimension1);
			goroupIdTextField.setPreferredSize(new Dimension(120, 20));
			goroupNameTextField.setPreferredSize(new Dimension(120, 20));
			chnComboBox.setPreferredSize(new Dimension(120, 20));
			similaryTextField.setPreferredSize(new Dimension(120, 20));
	
			add(goroupIdLabel);
			add(goroupIdTextField);
			add(goroupNameLabel);
			add(goroupNameTextField);
			add(chnLabel);
			add(chnComboBox);
			add(similaryLabel);
			add(similaryTextField);
			
			ToolKits.limitTextFieldLength(similaryTextField, 3);
			
			goroupIdTextField.setEditable(false);
			goroupNameTextField.setEditable(false);
			
			goroupIdTextField.setText(groupId);
			goroupNameTextField.setText(groupName);
		}
	}
	
	// 查找人脸库的布控通道以及对应的相似度
	private void findChnAndSimilary() {
		// 清空列表
		defaultTableModel.setRowCount(0);
		defaultTableModel.setRowCount(512);
		
		// 查询布控信息
		NET_FACERECONGNITION_GROUP_INFO[] groupInfos = TargetRecognitionModule.findGroupInfo(goroupIdTextField.getText());
		
		if(groupInfos == null) {
			return;
		}
		
		for(int i = 0; i < groupInfos[0].nRetChnCount; i++) {
			defaultTableModel.setValueAt(String.valueOf(groupInfos[0].nChannel[i] + 1), i, 0);
			defaultTableModel.setValueAt(String.valueOf(groupInfos[0].nSimilarity[i]), i, 1);
		}			
	}
	
	private Object[][] data;
	private DefaultTableModel defaultTableModel;
	private JTable table;
	
	private JButton addBtn;
	private JButton refreshBtn;
	private JButton dispositionBtn;
	private JButton delDispositionBtn;
	private JTextField goroupIdTextField;
	private JTextField goroupNameTextField;
	private JComboBox chnComboBox;
	private JTextField similaryTextField;
}
