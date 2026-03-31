package com.ruoyi.dahua.frame.TargetRecognition;

import com.ruoyi.dahua.common.*;
import com.ruoyi.dahua.lib.NetSDKLib.CANDIDATE_INFOEX;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.TargetRecognitionModule;
import com.sun.jna.Memory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class PersonOperateDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String groupId = "";
	private String groupName = "";
	
	// 添加人员界面
	public AddPersonDialog addPersonDialog = null;
	
	// 修改人员界面
	public ModifyPersonDialog modifyPersonDialog = null;
	
	// 查询起始索引
	private int nBeginNum = 0;
	
	// 页数
	private int nPagesNumber = 0;
	
	// 查询人员总数
	private int nTotalCount = 0;
	
	private HashMap<String, CANDIDATE_INFOEX> cadidateHashMap = new HashMap<String, CANDIDATE_INFOEX>();
	
	public PersonOperateDialog(String groupId, String groupName) {
	    setTitle(Res.string().getPersonOperate());
	    setLayout(new BorderLayout());
	    setModal(true);  
	    pack();
	    setSize(680, 520);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);   // 释放窗体
	    
		this.groupId = groupId;
		this.groupName = groupName;
		
	    PersonInfoPanel personInfoPanel = new PersonInfoPanel();
	    PersonInfoListPanel personInfoListPanel = new PersonInfoListPanel();
	    
	    add(personInfoPanel, BorderLayout.NORTH);
	    add(personInfoListPanel, BorderLayout.CENTER);
	    
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		nBeginNum = 0;    		
	    		nPagesNumber = 0;    		
	    		nTotalCount = 0;    		
	    		cadidateHashMap.clear();

	    		dispose();
	    	}
	    });
	}
	
	
	/*
	 * 查找条件信息
	 */
	private class SearchInfoPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public SearchInfoPanel() {
			BorderEx.set(this, Res.string().getFindCondition(), 1);
			setLayout(new FlowLayout());
			
			JLabel goroupIdLabel = new JLabel(Res.string().getFaceGroupId(), JLabel.CENTER);
			JLabel goroupNameLabel = new JLabel(Res.string().getFaceGroupName(), JLabel.CENTER);
			JLabel nameLabel = new JLabel(Res.string().getName(), JLabel.CENTER);
			JLabel sexLabel = new JLabel(Res.string().getSex(), JLabel.CENTER);
			JLabel IdTypeLabel = new JLabel(Res.string().getIdType(), JLabel.CENTER);
			JLabel IdLabel = new JLabel(Res.string().getIdNo(), JLabel.CENTER);
			JLabel birthdayLabel = new JLabel(Res.string().getBirthday(), JLabel.CENTER);
			JLabel lineLabel = new JLabel("-", JLabel.CENTER);
			startBirthdayCheckBox = new JCheckBox();
			endBirthdayCheckBox = new JCheckBox();
			JLabel nullLabel = new JLabel();
			
			Dimension dimension1 = new Dimension();
			dimension1.height = 20;
			dimension1.width = 80;
			goroupIdLabel.setPreferredSize(dimension1);
			goroupNameLabel.setPreferredSize(dimension1);
			nameLabel.setPreferredSize(dimension1);
			sexLabel.setPreferredSize(dimension1);
			IdTypeLabel.setPreferredSize(dimension1);
			IdLabel.setPreferredSize(dimension1);
			birthdayLabel.setPreferredSize(dimension1);
			lineLabel.setPreferredSize(new Dimension(50, 20));
			nullLabel.setPreferredSize(new Dimension(180, 20));
			
			goroupIdTextField = new JTextField();
			goroupNameTextField = new JTextField();
			nameTextField = new JTextField();
			sexComboBox = new JComboBox(Res.string().getSexStringsFind());
			idTypeComboBox = new JComboBox(Res.string().getIdStringsFind());
			idTextField = new JTextField();
			
			startTimeBtn = new DateChooserJButtonEx("2018-07-01");
			endTimeBtn = new DateChooserJButtonEx();
			
			startTimeBtn.setStartYear(1900);
			endTimeBtn.setStartYear(1900);
	
			Dimension dimension2 = new Dimension();
			dimension2.height = 20;
			goroupIdTextField.setPreferredSize(dimension2);
			goroupNameTextField.setPreferredSize(dimension2);
			nameTextField.setPreferredSize(dimension2);		
			idTextField.setPreferredSize(dimension2);
			
			goroupIdTextField.setPreferredSize(new Dimension(120, 20));
			goroupNameTextField.setPreferredSize(new Dimension(120, 20));
			nameTextField.setPreferredSize(new Dimension(120, 20));
			idTextField.setPreferredSize(new Dimension(120, 20));	
			sexComboBox.setPreferredSize(new Dimension(120, 20));
			startTimeBtn.setPreferredSize(new Dimension(125, 20));
			endTimeBtn.setPreferredSize(new Dimension(125, 20));	
			idTypeComboBox.setPreferredSize(new Dimension(120, 20));
			startBirthdayCheckBox.setPreferredSize(new Dimension(20, 20));
			endBirthdayCheckBox.setPreferredSize(new Dimension(20, 20));
		
			add(goroupIdLabel);
			add(goroupIdTextField);
			add(goroupNameLabel);
			add(goroupNameTextField);
			add(nameLabel);
			add(nameTextField);
			add(sexLabel);
			add(sexComboBox);
			add(IdTypeLabel);
			add(idTypeComboBox);
			add(IdLabel);
		    add(idTextField);
			add(birthdayLabel);
			add(startTimeBtn);
			add(startBirthdayCheckBox);
			add(lineLabel);
			add(endTimeBtn);
			add(endBirthdayCheckBox);
			add(nullLabel);
			
			goroupIdTextField.setEditable(false);
			goroupNameTextField.setEditable(false);
			startBirthdayCheckBox.setSelected(false);
			endBirthdayCheckBox.setSelected(false);
			startTimeBtn.setEnabled(false);
			endTimeBtn.setEnabled(false);
		}
	}
	/*
	 * 人员信息以及操作面板
	 */
	private class PersonInfoPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public PersonInfoPanel() {
			BorderEx.set(this, "", 2);
			setLayout(new BorderLayout());
			
			Dimension dimension = new Dimension();
			dimension.height = 150;
			setPreferredSize(dimension);
			
			SearchInfoPanel searchInfoPanel = new SearchInfoPanel();
			JPanel operatePanel = new JPanel();
			
			add(searchInfoPanel, BorderLayout.CENTER);
			add(operatePanel, BorderLayout.SOUTH);
			
			/*
			 * 操作
			 */
			searchPersonBtn = new JButton(Res.string().getFindPerson());
			JButton addPersonBtn = new JButton(Res.string().getAddPerson());
			JButton modifyPersonBtn = new JButton(Res.string().getModifyPerson());
			JButton deletePersonBtn = new JButton(Res.string().getDelPerson());
			
			operatePanel.setLayout(new GridLayout(1, 4));
			
			operatePanel.add(searchPersonBtn);
			operatePanel.add(addPersonBtn);
			operatePanel.add(modifyPersonBtn);
			operatePanel.add(deletePersonBtn);
			
			goroupIdTextField.setText(groupId);
			goroupNameTextField.setText(groupName);
			
			startBirthdayCheckBox.addChangeListener(new ChangeListener() {		
				@Override
				public void stateChanged(ChangeEvent arg0) {
					if(startBirthdayCheckBox.isSelected()) {
						startTimeBtn.setEnabled(true);
					} else {
						startTimeBtn.setEnabled(false);
					}
				}
			});
			
			endBirthdayCheckBox.addChangeListener(new ChangeListener() {		
				@Override
				public void stateChanged(ChangeEvent arg0) {
					if(endBirthdayCheckBox.isSelected()) {
						endTimeBtn.setEnabled(true);
					} else {
						endTimeBtn.setEnabled(false);
					}
				}
			});
			
			// 查找人员
			searchPersonBtn.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {					
						@Override
						public void run() {
							searchPersonBtn.setEnabled(false);
						}
					});	
					
					new SwingWorker<CANDIDATE_INFOEX[], String>() {					
						@Override
						protected CANDIDATE_INFOEX[] doInBackground() {
							nTotalCount = 0;
							nBeginNum = 0;
							cleanList();
							cadidateHashMap.clear();
							
							nTotalCount = TargetRecognitionModule.startFindPerson(goroupIdTextField.getText(),
																			    startBirthdayCheckBox.isSelected(), startTimeBtn.getText().toString(), 
																			    endBirthdayCheckBox.isSelected(), endTimeBtn.getText().toString(), 
																			    nameTextField.getText(), sexComboBox.getSelectedIndex(), 
																			    idTypeComboBox.getSelectedIndex(), idTextField.getText());
								
							if(nTotalCount <= 0) {
								searchPersonBtn.setEnabled(true);
								previousPageBtn.setEnabled(false);
								lastPageBtn.setEnabled(false);
								numTextField.setText("");
								return null;
							}
							
							CANDIDATE_INFOEX[] stuCandidatesEx = TargetRecognitionModule.doFindPerson(nBeginNum, 17);
							return stuCandidatesEx;
						}
						
						@Override
						protected void done() {							
							try {
								CANDIDATE_INFOEX[] stuCandidatesEx = get();
								findPersonInfo(stuCandidatesEx);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}						
						}
					}.execute();
				}
			});
						
			// 添加人员
			addPersonBtn.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					addPersonDialog = new AddPersonDialog(groupId, groupName);
			
					addPersonDialog.addWindowCloseListener(new WindowCloseListener() {							
						@Override
						public void windowClosing() {
							new SwingWorker<CANDIDATE_INFOEX[], String>() {					
								@Override
								protected CANDIDATE_INFOEX[] doInBackground() {
									nTotalCount = 0;
									nBeginNum = 0;
									cleanList();
									cadidateHashMap.clear();
									
									nTotalCount = TargetRecognitionModule.startFindPerson(goroupIdTextField.getText(),
																					    startBirthdayCheckBox.isSelected(), startTimeBtn.getText().toString(), 
																					    endBirthdayCheckBox.isSelected(), endTimeBtn.getText().toString(), 
																					    nameTextField.getText(), sexComboBox.getSelectedIndex(), 
																					    idTypeComboBox.getSelectedIndex(), idTextField.getText());
										
									if(nTotalCount <= 0) {
										searchPersonBtn.setEnabled(true);
										previousPageBtn.setEnabled(false);
										lastPageBtn.setEnabled(false);
										numTextField.setText("");
										return null;
									}
									
									CANDIDATE_INFOEX[] stuCandidatesEx = TargetRecognitionModule.doFindPerson(nBeginNum, 17);
									return stuCandidatesEx;
								}
								
								@Override
								protected void done() {				
									try {
										CANDIDATE_INFOEX[] stuCandidatesEx = get();
										findPersonInfo(stuCandidatesEx);
									} catch (InterruptedException e) {
										e.printStackTrace();
									} catch (ExecutionException e) {
										e.printStackTrace();
									}						
								}
							}.execute();
						}
					});
					
					addPersonDialog.setVisible(true);							
				}
			});
			
			// 修改人员
			modifyPersonBtn.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int row = -1;
					row = table.getSelectedRow(); //获得所选的单行
					
					if(row < 0) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectPerson(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(defaultTableModel.getValueAt(row, 0) == null || String.valueOf(defaultTableModel.getValueAt(row, 0)).trim().equals("")) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectPerson(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// 人员信息
					CANDIDATE_INFOEX stuCandidate = cadidateHashMap.get(String.valueOf(defaultTableModel.getValueAt(row, 0)).trim());
					
					// URL地址
					String szFilePath = stuCandidate.stPersonInfo.szFacePicInfo[0].pszFilePath.getString(0);

					// 存放图片的本地路径
					String pszFileDst = "./person.jpg";
							
					// 下载图片, 下载到本地, 图片路径  "./person.jpg"
					boolean bRet = TargetRecognitionModule.downloadPersonPic(szFilePath, pszFileDst);
					
					Memory memory = null;
					if(bRet) {
						try {
							memory = ToolKits.readPictureFile(pszFileDst);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						pszFileDst = "";
					}
					
					// 人员标识符
					String uid = String.valueOf(defaultTableModel.getValueAt(row, 0)).trim();
					
					modifyPersonDialog = new ModifyPersonDialog(groupId, groupName, uid, pszFileDst, memory, stuCandidate);

					modifyPersonDialog.addWindowCloseListener(new WindowCloseListener() {							
						@Override
						public void windowClosing() {
							new SwingWorker<CANDIDATE_INFOEX[], String>() {					
						@Override
						protected CANDIDATE_INFOEX[] doInBackground() {
							nTotalCount = 0;
							nBeginNum = 0;
							cleanList();
							cadidateHashMap.clear();
							
							nTotalCount = TargetRecognitionModule.startFindPerson(goroupIdTextField.getText(),
																			    startBirthdayCheckBox.isSelected(), startTimeBtn.getText().toString(), 
																			    endBirthdayCheckBox.isSelected(), endTimeBtn.getText().toString(), 
																			    nameTextField.getText(), sexComboBox.getSelectedIndex(), 
																			    idTypeComboBox.getSelectedIndex(), idTextField.getText());
								
							if(nTotalCount <= 0) {
								searchPersonBtn.setEnabled(true);
								previousPageBtn.setEnabled(false);
								lastPageBtn.setEnabled(false);
								numTextField.setText("");
								return null;
							}
							
							CANDIDATE_INFOEX[] stuCandidatesEx = TargetRecognitionModule.doFindPerson(nBeginNum, 17);
							return stuCandidatesEx;
						}
						
						@Override
						protected void done() {				
							try {
								CANDIDATE_INFOEX[] stuCandidatesEx = get();
								findPersonInfo(stuCandidatesEx);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}						
						}
					}.execute();
						}
					});
					
					modifyPersonDialog.setVisible(true);							
				}
			});
			
			// 删除人员
			deletePersonBtn.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int row = -1;
					row = table.getSelectedRow(); //获得所选的单行
					
					if(row < 0) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectPerson(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(defaultTableModel.getValueAt(row, 0) == null || String.valueOf(defaultTableModel.getValueAt(row, 0)).trim().equals("")) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectPerson(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(!TargetRecognitionModule.delPerson(goroupIdTextField.getText(), String.valueOf(defaultTableModel.getValueAt(row, 0)).trim())) {
						JOptionPane.showMessageDialog(null, Res.string().getFailed() + "," + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
					}
					
					new SwingWorker<CANDIDATE_INFOEX[], String>() {					
						@Override
						protected CANDIDATE_INFOEX[] doInBackground() {
							nTotalCount = 0;
							nBeginNum = 0;
							cleanList();
							cadidateHashMap.clear();
							
							nTotalCount = TargetRecognitionModule.startFindPerson(goroupIdTextField.getText(),
																			    startBirthdayCheckBox.isSelected(), startTimeBtn.getText().toString(), 
																			    endBirthdayCheckBox.isSelected(), endTimeBtn.getText().toString(), 
																			    nameTextField.getText(), sexComboBox.getSelectedIndex(), 
																			    idTypeComboBox.getSelectedIndex(), idTextField.getText());
								
							if(nTotalCount <= 0) {
								searchPersonBtn.setEnabled(true);
								previousPageBtn.setEnabled(false);
								lastPageBtn.setEnabled(false);
								numTextField.setText("");
								return null;
							}
							
							CANDIDATE_INFOEX[] stuCandidatesEx = TargetRecognitionModule.doFindPerson(nBeginNum, 17);
							return stuCandidatesEx;
						}
						
						@Override
						protected void done() {
							
							try {
								CANDIDATE_INFOEX[] stuCandidatesEx = get();
								findPersonInfo(stuCandidatesEx);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}						
						}
					}.execute();
				}
			});
		}
	}
	
	/*
	 * 人员信息显示列表
	 */
	private class PersonInfoListPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public PersonInfoListPanel() {
			BorderEx.set(this, "", 2);
			setLayout(new BorderLayout());
			
			data = new Object[17][6];
			defaultTableModel = new DefaultTableModel(data, Res.string().getPersonTable());
			table = new JTable(defaultTableModel) {   // 列表不可编辑
				private static final long serialVersionUID = 1L;
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			table.getColumnModel().getColumn(1).setPreferredWidth(150);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
			table.getColumnModel().getColumn(3).setPreferredWidth(200);
			table.getColumnModel().getColumn(4).setPreferredWidth(150);
			table.getColumnModel().getColumn(5).setPreferredWidth(250);
			
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 只能选中一行
			
			DefaultTableCellRenderer dCellRenderer = new DefaultTableCellRenderer();
			dCellRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.setDefaultRenderer(Object.class, dCellRenderer);
			
			JPanel panel = new JPanel();
			previousPageBtn = new JButton(Res.string().getPreviousPage());
			lastPageBtn = new JButton(Res.string().getLastPage());
			JLabel numLabel = new JLabel(Res.string().getPagesNumber(), JLabel.CENTER);
			numTextField = new JTextField();
			
			numTextField.setHorizontalAlignment(JTextField.CENTER);
			numTextField.setPreferredSize(new Dimension(80, 20));
			
			Dimension dimension = new Dimension();
			dimension.height = 25;
			panel.setPreferredSize(dimension);
			
			numLabel.setPreferredSize(new Dimension(80, 20));
			numTextField.setPreferredSize(new Dimension(120, 20));
			previousPageBtn.setPreferredSize(new Dimension(120, 20));
			lastPageBtn.setPreferredSize(new Dimension(120, 20));
			
			panel.setLayout(new FlowLayout());
			panel.add(previousPageBtn);
			panel.add(numLabel);
			panel.add(numTextField);
			panel.add(lastPageBtn);
			
			previousPageBtn.setEnabled(false);
			lastPageBtn.setEnabled(false);
			numTextField.setEnabled(false);
			
			add(new JScrollPane(table), BorderLayout.CENTER);
			add(panel, BorderLayout.SOUTH);
			
			// 前一页
			previousPageBtn.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent arg0) {							
					SwingUtilities.invokeLater(new Runnable() {					
						@Override
						public void run() {
							previousPageBtn.setEnabled(false);		
						}
					});	
					
					new SwingWorker<CANDIDATE_INFOEX[], String>() {					
						@Override
						protected CANDIDATE_INFOEX[] doInBackground() {
							nBeginNum -= 17;						
							
							CANDIDATE_INFOEX[] stuCandidatesEx = TargetRecognitionModule.doFindPerson(nBeginNum, 17);
							return stuCandidatesEx;
						}
						
						@Override
						protected void done() {
							
							try {
								CANDIDATE_INFOEX[] stuCandidatesEx = get();
								findPreviousPage(stuCandidatesEx);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}				
						}
					}.execute();
				}
			});
			
			// 下一页
			lastPageBtn.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent arg0) {	
					SwingUtilities.invokeLater(new Runnable() {					
						@Override
						public void run() {
							lastPageBtn.setEnabled(false);
						}
					});	
					
					new SwingWorker<CANDIDATE_INFOEX[], String>() {					
						@Override
						protected CANDIDATE_INFOEX[] doInBackground() {
							nBeginNum += 17;						
							
							CANDIDATE_INFOEX[] stuCandidatesEx = TargetRecognitionModule.doFindPerson(nBeginNum, 17);
							return stuCandidatesEx;
						}
						
						@Override
						protected void done() {
							
							try {
								CANDIDATE_INFOEX[] stuCandidatesEx = get();
								findLastPage(stuCandidatesEx);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}						
						}
					}.execute();
				}
			});
		}
	}	
	
	
    
	/*
	 * 查找前17个
	 */
	public void findPersonInfo(CANDIDATE_INFOEX[] stuCandidatesEx) {
		if(stuCandidatesEx != null) {
			searchPersonBtn.setEnabled(true);
			previousPageBtn.setEnabled(false);
			nPagesNumber = 1;
			numTextField.setText(String.valueOf(nPagesNumber));			
			
			for(int i = 0; i < stuCandidatesEx.length; i++) {
				if(!cadidateHashMap.containsKey(new String(stuCandidatesEx[i].stPersonInfo.szUID).trim())) {
					cadidateHashMap.put(new String(stuCandidatesEx[i].stPersonInfo.szUID).trim(), stuCandidatesEx[i]);
				}
				
				// UID
				defaultTableModel.setValueAt(new String(stuCandidatesEx[i].stPersonInfo.szUID).trim(), i, 0);
				
				// 姓名
				try {
					defaultTableModel.setValueAt(new String(stuCandidatesEx[i].stPersonInfo.szPersonName, "GBK").trim(), i, 1);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				// 性别
				defaultTableModel.setValueAt(Res.string().getSex(stuCandidatesEx[i].stPersonInfo.bySex & 0xff), i, 2);
				
				// 生日
				defaultTableModel.setValueAt(String.valueOf((int)stuCandidatesEx[i].stPersonInfo.wYear) + "-" + 
							                 String.valueOf( stuCandidatesEx[i].stPersonInfo.byMonth & 0xff) + "-" + 
							                 String.valueOf(stuCandidatesEx[i].stPersonInfo.byDay & 0xff), i, 3);
				
				// 证件类型
				defaultTableModel.setValueAt(Res.string().getIdType(stuCandidatesEx[i].stPersonInfo.byIDType & 0xff), i, 4);
				
				// 证件号
				try {
					defaultTableModel.setValueAt(new String(stuCandidatesEx[i].stPersonInfo.szID, "GBK").trim(), i, 5);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}	
			}
			
			if(nTotalCount > nBeginNum + stuCandidatesEx.length) {
				lastPageBtn.setEnabled(true);
			}
		} else {
			searchPersonBtn.setEnabled(true);
			previousPageBtn.setEnabled(false);
			lastPageBtn.setEnabled(false);
			numTextField.setText("");
		}
	}
	
	/*
	 * 上一页查找
	 */
	private void findPreviousPage(CANDIDATE_INFOEX[] stuCandidatesEx) {
		if(stuCandidatesEx != null) {
			nPagesNumber -= 1;
			numTextField.setText(String.valueOf(nPagesNumber));
			cadidateHashMap.clear();
			cleanList();
			lastPageBtn.setEnabled(true);
			
			for(int i = 0; i < stuCandidatesEx.length; i++) {
				if(!cadidateHashMap.containsKey(new String(stuCandidatesEx[i].stPersonInfo.szUID).trim())) {
					cadidateHashMap.put(new String(stuCandidatesEx[i].stPersonInfo.szUID).trim(), stuCandidatesEx[i]);
				}
					
				// UID
				defaultTableModel.setValueAt(new String(stuCandidatesEx[i].stPersonInfo.szUID).trim(), i, 0);
				
				// 姓名
				try {
					defaultTableModel.setValueAt(new String(stuCandidatesEx[i].stPersonInfo.szPersonName, "GBK").trim(), i, 1);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				// 性别
				defaultTableModel.setValueAt(Res.string().getSex(stuCandidatesEx[i].stPersonInfo.bySex & 0xff), i, 2);
				
				// 生日
				defaultTableModel.setValueAt(String.valueOf((int)stuCandidatesEx[i].stPersonInfo.wYear) + "-" + 
							                 String.valueOf( stuCandidatesEx[i].stPersonInfo.byMonth & 0xff) + "-" + 
							                 String.valueOf(stuCandidatesEx[i].stPersonInfo.byDay & 0xff), i, 3);
				
				// 证件类型
				defaultTableModel.setValueAt(Res.string().getIdType(stuCandidatesEx[i].stPersonInfo.byIDType & 0xff), i, 4);
				
				// 证件号
				try {
					defaultTableModel.setValueAt(new String(stuCandidatesEx[i].stPersonInfo.szID, "GBK").trim(), i, 5);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}	
			}	
			
			if(nBeginNum >= 17) {		
				previousPageBtn.setEnabled(true);
			} else {
				previousPageBtn.setEnabled(false);
			}
		} else{
			JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			previousPageBtn.setEnabled(true);
			nBeginNum += 17;
		}
	}
	
	/*
	 * 下一页查找
	 */
	private void findLastPage(CANDIDATE_INFOEX[] stuCandidatesEx) {	
		if(stuCandidatesEx != null) {
			nPagesNumber += 1;
			numTextField.setText(String.valueOf(nPagesNumber));
			cadidateHashMap.clear();
			cleanList();
			previousPageBtn.setEnabled(true);
			
			for(int i = 0; i < stuCandidatesEx.length; i++) {
				if(!cadidateHashMap.containsKey(new String(stuCandidatesEx[i].stPersonInfo.szUID).trim())) {
					cadidateHashMap.put(new String(stuCandidatesEx[i].stPersonInfo.szUID).trim(), stuCandidatesEx[i]);
				}
				
				// UID
				defaultTableModel.setValueAt(new String(stuCandidatesEx[i].stPersonInfo.szUID).trim(), i, 0);
				
				// 姓名
				try {
					defaultTableModel.setValueAt(new String(stuCandidatesEx[i].stPersonInfo.szPersonName, "GBK").trim(), i, 1);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				// 性别
				defaultTableModel.setValueAt(Res.string().getSex(stuCandidatesEx[i].stPersonInfo.bySex & 0xff), i, 2);
				
				// 生日
				defaultTableModel.setValueAt(String.valueOf((int)stuCandidatesEx[i].stPersonInfo.wYear) + "-" + 
							                 String.valueOf( stuCandidatesEx[i].stPersonInfo.byMonth & 0xff) + "-" + 
							                 String.valueOf(stuCandidatesEx[i].stPersonInfo.byDay & 0xff), i, 3);
				
				// 证件类型
				defaultTableModel.setValueAt(Res.string().getIdType(stuCandidatesEx[i].stPersonInfo.byIDType & 0xff), i, 4);
				
				// 证件号
				try {
					defaultTableModel.setValueAt(new String(stuCandidatesEx[i].stPersonInfo.szID, "GBK").trim(), i, 5);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}		
			}				
			
			if(nTotalCount > nBeginNum + stuCandidatesEx.length) {
				lastPageBtn.setEnabled(true);			
			} else {
				lastPageBtn.setEnabled(false);
			}
		} else {
			JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			lastPageBtn.setEnabled(true);	
			nBeginNum -= 17;	
		}
	}
	
	/*
	 * 清空列表
	 */
	private void cleanList() {
		for(int i = 0; i < 17; i++) {
			for(int j = 0; j < 6; j++) {
				defaultTableModel.setValueAt("", i, j);
			}
		}
	}
	
	private Object[][] data;
	private DefaultTableModel defaultTableModel;
	private JTable table;
	
	private JButton previousPageBtn;
	private JButton lastPageBtn;
	private JTextField goroupIdTextField;
	private JTextField goroupNameTextField;
	private JTextField nameTextField;
	private JComboBox sexComboBox;
	private JComboBox idTypeComboBox;
	private JTextField idTextField;
	private JCheckBox startBirthdayCheckBox;
	private JCheckBox endBirthdayCheckBox;
	private DateChooserJButtonEx startTimeBtn;
	private DateChooserJButtonEx endTimeBtn;
	private JTextField numTextField;
	private JButton searchPersonBtn;
	
}
