package com.ruoyi.dahua.frame.Gate;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib.NET_RECORDSET_ACCESS_CTL_CARD;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.GateModule;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

public class CardManegerDialog extends JDialog{
	private static final long serialVersionUID = 1L;

	private int count = 0;  	   // 查询了几次	
	private int index = 0;	 	   // 查询的卡信息索引
	private int nFindCount = 10;   // 每次查询的次数
	
	public CardManegerDialog(){
		setTitle(Res.string().getCardManager());
		setLayout(new BorderLayout());
	    setModal(true);  
	    pack();
		setSize(700, 390);
	    setResizable(false);
	    setLocationRelativeTo(null);  
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);   // 释放窗体
		
		CardListPanel cardListPanel = new CardListPanel();
		CardOperatePanel cardOperatePanel = new CardOperatePanel();
		
        add(cardListPanel, BorderLayout.CENTER);
        add(cardOperatePanel, BorderLayout.EAST);
        
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
		
	  	setOnClickListener();
	}
	
	/**
	 * 卡信息列表
	 */
	private class CardListPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public CardListPanel() {
			BorderEx.set(this, Res.string().getCardInfo(), 2);
			setLayout(new BorderLayout());
			
			defaultModel = new DefaultTableModel(null, Res.string().getCardTable());
			table = new JTable(defaultModel) {   // 列表不可编辑
				private static final long serialVersionUID = 1L;
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			defaultModel.setRowCount(18);
			
			table.getColumnModel().getColumn(0).setPreferredWidth(80);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
			table.getColumnModel().getColumn(3).setPreferredWidth(100);
			table.getColumnModel().getColumn(4).setPreferredWidth(100);
			table.getColumnModel().getColumn(5).setPreferredWidth(100);
			table.getColumnModel().getColumn(6).setPreferredWidth(100);
			table.getColumnModel().getColumn(7).setPreferredWidth(100);
			table.getColumnModel().getColumn(8).setPreferredWidth(100);
			table.getColumnModel().getColumn(9).setPreferredWidth(100);
			table.getColumnModel().getColumn(10).setPreferredWidth(100);
			table.getColumnModel().getColumn(11).setPreferredWidth(150);
			table.getColumnModel().getColumn(12).setPreferredWidth(150);
			
			// 列表显示居中
			DefaultTableCellRenderer dCellRenderer = new DefaultTableCellRenderer();
			dCellRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.setDefaultRenderer(Object.class, dCellRenderer);	
			((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
			
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			JScrollPane scrollPane = new JScrollPane(table);
			add(scrollPane, BorderLayout.CENTER);
		}
		
	}
	
	/**
	 * 卡操作
	 */
	private class CardOperatePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public CardOperatePanel() {
			BorderEx.set(this, Res.string().getCardOperate(), 2);
			setLayout(new BorderLayout());
			Dimension dimension = new Dimension();
			dimension.width = 210;
			setPreferredSize(dimension);
			
			Panel panel1 = new Panel();
			Panel panel2 = new Panel();
			
			add(panel1, BorderLayout.NORTH);
			add(panel2, BorderLayout.CENTER);
			
			// 
			JLabel cardNoLabel = new JLabel(Res.string().getCardNo() + ":", JLabel.CENTER);
			cardNoTextField = new JTextField("");
			
			cardNoLabel.setPreferredSize(new Dimension(50, 20));
			cardNoTextField.setPreferredSize(new Dimension(120, 20));
			cardNoTextField.setHorizontalAlignment(JTextField.CENTER);
			
			panel1.setLayout(new FlowLayout());
			panel1.add(cardNoLabel);
			panel1.add(cardNoTextField);
			
			//
			searchBtn = new JButton(Res.string().getSearch());
			addBtn = new JButton(Res.string().getAdd());
			modifyBtn = new JButton(Res.string().getModify());
			deleteBtn = new JButton(Res.string().getDelete());
			clearBtn = new JButton(Res.string().getClear());

			searchBtn.setPreferredSize(new Dimension(180, 21));
			addBtn.setPreferredSize(new Dimension(180, 21));
			modifyBtn.setPreferredSize(new Dimension(180, 21));
			deleteBtn.setPreferredSize(new Dimension(180, 21));
			clearBtn.setPreferredSize(new Dimension(180, 21));
			
			JLabel nullLabel = new JLabel();
			nullLabel.setPreferredSize(new Dimension(180, 30));
			
			panel2.setLayout(new FlowLayout());
			panel2.add(nullLabel);
			panel2.add(searchBtn);
			panel2.add(addBtn);
			panel2.add(modifyBtn);
			panel2.add(deleteBtn);
			panel2.add(clearBtn);
		}	
	}
	
	private void setOnClickListener() {
		searchBtn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							if (cardNoTextField.getText().getBytes("UTF-8").length > 31) {
								JOptionPane.showMessageDialog(null, Res.string().getCardNoExceedLength() + "(31)", Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
								return;
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						} 
						
						searchBtn.setEnabled(false);	
						defaultModel.setRowCount(0);
						defaultModel.setRowCount(18);
					}
				});				

				findCardInfo();
			}
		});
		
		addBtn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddCardDialog dialog = new AddCardDialog();
				dialog.setVisible(true);
			}
		});
		
		modifyBtn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = -1;
				row = table.getSelectedRow(); //获得所选的单行
				
				if(row < 0) {
					JOptionPane.showMessageDialog(null, Res.string().getSelectCard(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(defaultModel.getValueAt(row, 3) == null || String.valueOf(defaultModel.getValueAt(row, 3)).trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, Res.string().getSelectCard(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					return;
				}
				
			    @SuppressWarnings("unchecked")
				Vector<String> vector = (Vector<String>) defaultModel.getDataVector().get(row);
			    
				ModifyCardDialog dialog = new ModifyCardDialog(vector);
				dialog.setVisible(true);
			}
		});
		
		deleteBtn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = -1;
				row = table.getSelectedRow(); //获得所选的单行
				
				if(row < 0) {
					JOptionPane.showMessageDialog(null, Res.string().getSelectCard(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(defaultModel.getValueAt(row, 3) == null || String.valueOf(defaultModel.getValueAt(row, 3)).trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, Res.string().getSelectCard(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					return;
				}
							
			    Vector<String> v = (Vector<String>)defaultModel.getDataVector().get(row);

			    String recordNo = v.get(3).toString();   // 记录集编号
			    String userId = v.get(4).toString();     // 用户ID
			    
			    // 删除人脸和卡信息
			    if(!GateModule.deleteFaceInfo(userId) ||
			    		!GateModule.deleteCard(Integer.parseInt(recordNo))) {
			    	JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			    } else {
			    	JOptionPane.showMessageDialog(null, Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
			    	defaultModel.removeRow(row);
			    	table.updateUI();
			    }
			}
		});
		
		clearBtn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(null, Res.string().getWantClearAllInfo(), Res.string().getPromptMessage(), JOptionPane.YES_NO_OPTION);
				if(result == 0) {  // 0-是， 1-否
					// 清空人脸和卡信息
					if(!GateModule.clearFaceInfo() || 
							!GateModule.clearCard()) {
						JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
						defaultModel.setRowCount(0);
						defaultModel.setRowCount(18);
					}
				}
			}
		});
	}
	
	/**
	 * 查询卡的信息
	 */
	public void findCardInfo() {	
		new SwingWorker<Boolean, CardData>() {					
			@Override
			protected Boolean doInBackground() {
				count = 0;  		
				index = 0;	 	    
				nFindCount = 10; 
				
				// 卡号：  为空，查询所有的卡信息
				// 获取查询句柄
				if(!GateModule.findCard(cardNoTextField.getText())) {
					return false;
				}			
		
				// 查询具体信息
				while(true) {
					NET_RECORDSET_ACCESS_CTL_CARD[] pstRecord = GateModule.findNextCard(nFindCount);
					if(pstRecord == null) {
						break;
					}
					
					for(int i = 0; i < pstRecord.length; i++) {
						index = i + count * nFindCount;				
							
						try {
							Vector<String> vector = new Vector<String>();
							vector.add(String.valueOf(index + 1));   					 	 // 序号
							vector.add(new String(pstRecord[i].szCardNo).trim());   		 // 卡号
							vector.add(new String(pstRecord[i].szCardName, "GBK").trim());   // 卡名
							vector.add(String.valueOf(pstRecord[i].nRecNo));   				 // 记录集编号
							vector.add(new String(pstRecord[i].szUserID).trim());   		 // 用户ID
							vector.add(new String(pstRecord[i].szPsw).trim());   			 // 卡密码
							vector.add(Res.string().getCardStatus(pstRecord[i].emStatus));   // 卡状态
							vector.add(Res.string().getCardType(pstRecord[i].emType));   	 // 卡类型
							vector.add(String.valueOf(pstRecord[i].nUserTime));   	 		 // 使用次数
							vector.add(pstRecord[i].bFirstEnter == 1 ? Res.string().getFirstEnter() : Res.string().getNoFirstEnter());  // 是否首卡
							vector.add(pstRecord[i].bIsValid == 1? Res.string().getValid() : Res.string().getInValid());   	 			// 是否有效
							vector.add(pstRecord[i].stuValidStartTime.toStringTimeEx());   	 // 有效开始时间
							vector.add(pstRecord[i].stuValidEndTime.toStringTimeEx());   	 // 有效结束时间
				
							CardData data = new CardData();
							data.setIndex(index);
							data.setVector(vector);
							
							publish(data);

						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
		
					if (pstRecord.length < nFindCount) {
						break;
					} else {
						count ++;
					}

				}
				
				// 关闭查询接口
				GateModule.findCardClose();

				return true;
			}
			
			@Override
			protected void process(java.util.List<CardData> chunks) {
				for(CardData data : chunks) {
			        defaultModel.insertRow(data.getIndex(), data.getVector());
					if(data.getIndex() < 18) {
						 defaultModel.setRowCount(18);
					} else {
						defaultModel.setRowCount(data.getIndex() + 1);
					}
					
			        table.updateUI();
				}
				
				super.process(chunks);
			}
			
			@Override
			protected void done() {	
				searchBtn.setEnabled(true);
			}
		}.execute();
	}

	class CardData {
		private int nIndex = 0;
		private Vector<String> vector = null;
		
		public int getIndex() {
			return nIndex;
		}
		public void setIndex(int index) {
			this.nIndex = index;
		}
		public Vector<String> getVector() {
			return vector;
		}
		public void setVector(Vector<String> vector) {
			this.vector = vector;
		}
	}
	
	
	///
	private DefaultTableModel defaultModel;
	private JTable table;
	
	private JTextField cardNoTextField;
	
	private JButton searchBtn;
	private JButton addBtn;
	private JButton modifyBtn;
	private JButton deleteBtn;
	private JButton clearBtn;
}
