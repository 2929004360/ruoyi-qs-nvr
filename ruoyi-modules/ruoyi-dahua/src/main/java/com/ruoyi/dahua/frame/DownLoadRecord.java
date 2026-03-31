package com.ruoyi.dahua.frame;

import com.ruoyi.dahua.common.*;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.DownLoadRecordModule;
import com.ruoyi.dahua.module.LoginModule;
import com.sun.jna.CallbackThreadInitializer;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

/*
 * 下载录像Demo
 */
class DownLoadRecordFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private Vector<String> chnlist = new Vector<String>(); 
	
	private DefaultTableModel model;
	private LLong m_hDownLoadByTimeHandle = new LLong(0);   // 按时间下载句柄
	private LLong m_hDownLoadByFileHandle = new LLong(0);   // 按文件下载句柄
	
	private boolean b_downloadByTime = false;
	private boolean b_downloadByFile = false;
	private IntByReference nFindCount = new IntByReference(0);
    
	// 设备断线通知回调
	private DisConnect disConnect       = new DisConnect(); 
	
	// 网络连接恢复
	private static HaveReConnect haveReConnect = new HaveReConnect(); 
	
	// 开始时间
	private NetSDKLib.NET_TIME stTimeStart = new NetSDKLib.NET_TIME(); 
	
	// 结束时间
	private NetSDKLib.NET_TIME stTimeEnd = new NetSDKLib.NET_TIME();
	
	// 录像文件信息
	private NetSDKLib.NET_RECORDFILE_INFO[] stFileInfo = (NetSDKLib.NET_RECORDFILE_INFO[])new NetSDKLib.NET_RECORDFILE_INFO().toArray(2000);

	Object[][] data = null;
	
	// 获取界面窗口
	private static JFrame frame = new JFrame();   
	
	public DownLoadRecordFrame() {
	    setTitle(Res.string().getDownloadRecord());
	    setLayout(new BorderLayout());
	    pack();
	    setSize(800, 560);
	    setResizable(false);
	    setLocationRelativeTo(null);
		LoginModule.init(disConnect, haveReConnect);   // 打开工程，初始化
		
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        	e.printStackTrace();
        } 
        	    
	    loginPanel = new LoginPanel();
		downloadRecordPanel = new DownLoadRecordPanel(); 
	
	    add(loginPanel, BorderLayout.NORTH);
		add(downloadRecordPanel, BorderLayout.CENTER);
		
	    loginPanel.addLoginBtnActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(loginPanel.checkLoginText()) {
					if(login()) {
						frame = ToolKits.getFrame(e);
						frame.setTitle(Res.string().getDownloadRecord() + " : " + Res.string().getOnline());
					}	
				}
			}
		});
	    
	    loginPanel.addLogoutBtnActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setTitle(Res.string().getDownloadRecord());
				logout();
			}
		});
	    
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		DownLoadRecordModule.stopDownLoadRecordFile(m_hDownLoadByFileHandle);
	    		DownLoadRecordModule.stopDownLoadRecordFile(m_hDownLoadByTimeHandle);
	    		LoginModule.logout();
	    		LoginModule.cleanup();   // 关闭工程，释放资源
	    		dispose();		
	    		
	    		SwingUtilities.invokeLater(new Runnable() {
	    			public void run() {
	    				FunctionList demo = new FunctionList();
	    				demo.setVisible(true);
	    			}
	    		});
	    	}
	    });
	}
	
	/////////////////面板//////////////////
	// 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
	private class DisConnect implements NetSDKLib.fDisConnect {
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
			// 断线提示
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setTitle(Res.string().getDownloadRecord() + " : " + Res.string().getDisConnectReconnecting());

					setButtonEnable(true);
					b_downloadByFile = false;
					downloadByFileBtn.setText(Res.string().getDownload());
					b_downloadByTime = false;
					downloadByTimeBtn.setText(Res.string().getDownload());
		    		DownLoadRecordModule.stopDownLoadRecordFile(m_hDownLoadByFileHandle);
		    		DownLoadRecordModule.stopDownLoadRecordFile(m_hDownLoadByTimeHandle);
				}
			});	
		}
	}
	
	// 网络连接恢复，设备重连成功回调
	// 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
	private static class HaveReConnect implements NetSDKLib.fHaveReConnect {
		public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
			System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
			
			// 重连提示
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setTitle(Res.string().getDownloadRecord() + " : " + Res.string().getOnline());
				}
			});
		}
	}
	
	// 登录
	public boolean login() {
		Native.setCallbackThreadInitializer(m_DownLoadPosByFile, 
										    new CallbackThreadInitializer(false, false, "downloadbyfile callback thread")); 
		Native.setCallbackThreadInitializer(m_DownLoadPosByTime, 
										    new CallbackThreadInitializer(false, false, "downloadbytime callback thread")); 
		if(LoginModule.login(loginPanel.ipTextArea.getText(), 
						Integer.parseInt(loginPanel.portTextArea.getText()), 
						loginPanel.nameTextArea.getText(), 
						new String(loginPanel.passwordTextArea.getPassword()))) {
			loginPanel.setButtonEnable(true);
			setButtonEnable(true);      		
			
			for(int i = 1; i < LoginModule.m_stDeviceInfo.byChanNum + 1; i++) {
				chnlist.add(Res.string().getChannel() + " " + String.valueOf(i));
			}
			
			// 默认设置主辅码流
			DownLoadRecordModule.setStreamType(streamComboBoxByFile.getSelectedIndex());
			
			// 登陆成功，将通道添加到控件
			chnComboBoxByFile.setModel(new DefaultComboBoxModel(chnlist));
			chnComboBoxByTime.setModel(new DefaultComboBoxModel(chnlist));	  
		} else {
			JOptionPane.showMessageDialog(null, Res.string().getLoginFailed() + ", " + ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	//登出
	public void logout() {
		LoginModule.logout();
		loginPanel.setButtonEnable(false);
		setButtonEnable(false);
	    
	    // 列表清空
		data = new Object[14][5];
		table.setModel(new DefaultTableModel(data, Res.string().getDownloadTableName()));
		table.getColumnModel().getColumn(0).setPreferredWidth(23);
		table.getColumnModel().getColumn(1).setPreferredWidth(28);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);	  			
			
		for(int i = 0; i < LoginModule.m_stDeviceInfo.byChanNum; i++) {
			chnlist.clear();
		}
		
		chnComboBoxByFile.setModel(new DefaultComboBoxModel());
		chnComboBoxByTime.setModel(new DefaultComboBoxModel());
		
		b_downloadByFile = false;
		downloadByFileBtn.setText(Res.string().getDownload());
		b_downloadByTime = false;
		downloadByTimeBtn.setText(Res.string().getDownload());
	}
	
	/*
	 * 下载录像面板
	 */
	private class DownLoadRecordPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public DownLoadRecordPanel() {
			BorderEx.set(this, Res.string().getDownloadRecord(), 2);
			setLayout(new GridLayout(1, 2));
			
			downloadByTimePanel = new DownLoadByTimePanel(); // 按时间下载
			downloadByFilePanel = new DownLoadByFilePanel();  // 按文件下载
			
			add(downloadByTimePanel);
			add(downloadByFilePanel);
		}
	}
	
	/*
	 * 按文件下载面板
	 */
	private class DownLoadByFilePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public DownLoadByFilePanel() {
			BorderEx.set(this, Res.string().getDownloadByFile(), 2);
			setLayout(new BorderLayout());
			
			downloadByFileSetPanel = new JPanel(); // 设置
		    queryPanel = new JPanel(); // 查询
			downByFilePanel = new JPanel();  // 下载
			
			add(downloadByFileSetPanel, BorderLayout.NORTH);
			add(queryPanel, BorderLayout.CENTER);
			add(downByFilePanel, BorderLayout.SOUTH);
		    
			/******** 设置面板***********/
			JPanel startTimeByFile = new JPanel();
			JPanel endTimeByFile = new JPanel();
			JPanel chnByFile = new JPanel();
			JPanel streamByFile = new JPanel();
			
			downloadByFileSetPanel.setLayout(new GridLayout(2, 2));
			
			downloadByFileSetPanel.add(startTimeByFile);
			downloadByFileSetPanel.add(endTimeByFile);
			downloadByFileSetPanel.add(chnByFile);
			downloadByFileSetPanel.add(streamByFile);
			
			// 开始时间设置
			startTimeByFile.setBorder(new EmptyBorder(5, 5, 5, 20));
			startTimeByFile.setLayout(new GridLayout(2, 1));
			JLabel startLabel = new JLabel(Res.string().getStartTime());
			dateChooserStartByFile = new DateChooserJButton();
			
			Dimension dimension = new Dimension();
			dimension.height = 20;
			dateChooserStartByFile.setPreferredSize(dimension);
			
			startTimeByFile.add(startLabel);
			startTimeByFile.add(dateChooserStartByFile);
		    
			// 结束时间设置
			endTimeByFile.setBorder(new EmptyBorder(5, 20, 5, 5));
			endTimeByFile.setLayout(new GridLayout(2, 1));
			JLabel endLabel = new JLabel(Res.string().getEndTime());
		    dateChooserEndByFile = new DateChooserJButton();
		    dateChooserEndByFile.setPreferredSize(dimension);
		    
		    endTimeByFile.add(endLabel);
		    endTimeByFile.add(dateChooserEndByFile);
		    
		    // 通道设置
		    chnByFile.setBorder(new EmptyBorder(5, 10, 0, 5));
		    chnByFile.setLayout(new FlowLayout());
			chnlabel = new JLabel(Res.string().getChannel());
			chnComboBoxByFile = new JComboBox();			
			chnComboBoxByFile.setPreferredSize(new Dimension(115, 20));  
			chnByFile.add(chnlabel);
			chnByFile.add(chnComboBoxByFile);
			
			// 码流设置
			streamByFile.setBorder(new EmptyBorder(5, 10, 0, 5));
			streamByFile.setLayout(new FlowLayout());
			streamLabel = new JLabel(Res.string().getStreamType());
			String[] stream = {Res.string().getMasterAndSub(), Res.string().getMasterStream(), Res.string().getSubStream()};
			streamComboBoxByFile = new JComboBox(stream);	
			streamComboBoxByFile.setModel(new DefaultComboBoxModel(stream));
			streamComboBoxByFile.setPreferredSize(new Dimension(115, 20));  
			streamByFile.add(streamLabel);
			streamByFile.add(streamComboBoxByFile);

		    /******** 查询面板***********/
			queryPanel.setLayout(new BorderLayout());
		    queryPanel.setBorder(new EmptyBorder(0, 5, 5, 5));
			
			data = new Object[14][5];
			defaultmodel = new DefaultTableModel(data, Res.string().getDownloadTableName());
			table = new JTable(defaultmodel){
				private static final long serialVersionUID = 1L;
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 只能选中一行	
			
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			table.getColumnModel().getColumn(1).setPreferredWidth(20);
			table.getColumnModel().getColumn(2).setPreferredWidth(50);
			
			DefaultTableCellRenderer dCellRenderer = new DefaultTableCellRenderer();
			dCellRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.setDefaultRenderer(Object.class, dCellRenderer);
		    
			queryPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		    
			/******** 下载面板***********/
			downByFilePanel.setLayout(new BorderLayout());
			downByFilePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		    
		    JPanel btnPanel1 = new JPanel();
		    downloadByFileProgressBar = new JProgressBar(0, 100);
		    
		    downloadByFileProgressBar.setPreferredSize(new Dimension(100, 20)); 
		    downloadByFileProgressBar.setStringPainted(true);
		    
		    downByFilePanel.add(btnPanel1, BorderLayout.CENTER);
		    downByFilePanel.add(downloadByFileProgressBar, BorderLayout.SOUTH);
		    
		    // 查询、下载按钮
		    queryRecordBtn = new JButton(Res.string().getQuery());
		    downloadByFileBtn = new JButton(Res.string().getDownload());    
		    
		    queryRecordBtn.setPreferredSize(new Dimension(175, 20)); 
		    downloadByFileBtn.setPreferredSize(new Dimension(175, 20)); 
		    
		    btnPanel1.setLayout(new FlowLayout());
		    btnPanel1.add(queryRecordBtn);
		    btnPanel1.add(downloadByFileBtn);
		    
		    queryRecordBtn.setEnabled(false);
		    downloadByFileBtn.setEnabled(false);
		    downloadByFileProgressBar.setEnabled(false);
		    chnComboBoxByFile.setEnabled(false);
		    streamComboBoxByFile.setEnabled(false);
		    dateChooserStartByFile.setEnabled(false);
		    dateChooserEndByFile.setEnabled(false);
		    
		    streamComboBoxByFile.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {
					DownLoadRecordModule.setStreamType(streamComboBoxByFile.getSelectedIndex());
				}
			});
		    
		    queryRecordBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int i = 1; // 列表序号
					int time = 0;
					
					System.out.println(dateChooserStartByFile.getText() + "\n" + dateChooserEndByFile.getText());
					// 开始时间
					String[] dateStartByFile = dateChooserStartByFile.getText().split(" ");
					String[] dateStart1 = dateStartByFile[0].split("-");
					String[] dateStart2 = dateStartByFile[1].split(":");
					
					stTimeStart.dwYear = Integer.parseInt(dateStart1[0]);
					stTimeStart.dwMonth = Integer.parseInt(dateStart1[1]);
					stTimeStart.dwDay = Integer.parseInt(dateStart1[2]);
					
					stTimeStart.dwHour = Integer.parseInt(dateStart2[0]);
					stTimeStart.dwMinute = Integer.parseInt(dateStart2[1]);
					stTimeStart.dwSecond = Integer.parseInt(dateStart2[2]);
					
					// 结束时间
					String[] dateEndByFile = dateChooserEndByFile.getText().split(" ");
					String[] dateEnd1 = dateEndByFile[0].split("-");
					String[] dateEnd2 = dateEndByFile[1].split(":");
					
					stTimeEnd.dwYear = Integer.parseInt(dateEnd1[0]);
					stTimeEnd.dwMonth = Integer.parseInt(dateEnd1[1]);
					stTimeEnd.dwDay = Integer.parseInt(dateEnd1[2]);
					
					stTimeEnd.dwHour = Integer.parseInt(dateEnd2[0]);
					stTimeEnd.dwMinute = Integer.parseInt(dateEnd2[1]);
					stTimeEnd.dwSecond = Integer.parseInt(dateEnd2[2]);
						
					if(stTimeStart.dwYear != stTimeEnd.dwYear
					   || stTimeStart.dwMonth != stTimeEnd.dwMonth
					   || (stTimeEnd.dwDay - stTimeStart.dwDay > 1)) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectTimeAgain(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;				
					}
					
					if(stTimeEnd.dwDay - stTimeStart.dwDay == 1) {
						time = (24 + stTimeEnd.dwHour)*60*60 + stTimeEnd.dwMinute*60 + stTimeEnd.dwSecond -
								   stTimeStart.dwHour*60*60 - stTimeStart.dwMinute*60 - stTimeStart.dwSecond;
					} else {
						time = stTimeEnd.dwHour*60*60 + stTimeEnd.dwMinute*60 + stTimeEnd.dwSecond -
								   stTimeStart.dwHour*60*60 - stTimeStart.dwMinute*60 - stTimeStart.dwSecond;
					}

					if(time > 6 * 60 * 60 
					   || time <= 0) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectTimeAgain(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;	
					}
										
					if(!DownLoadRecordModule.queryRecordFile(chnComboBoxByFile.getSelectedIndex(), 
											   stTimeStart, 
											   stTimeEnd, 
											   stFileInfo,
											   nFindCount)) {
					    // 列表清空
						data = new Object[14][5];
						table.setModel(new DefaultTableModel(data, Res.string().getDownloadTableName()));
						table.getColumnModel().getColumn(0).setPreferredWidth(23);
						table.getColumnModel().getColumn(1).setPreferredWidth(28);
						table.getColumnModel().getColumn(2).setPreferredWidth(50);	 
						JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					} else {
						System.out.println(nFindCount.getValue());
		      			int count = 0;
		      			if(nFindCount.getValue() > 14) {
		      				count = nFindCount.getValue();
		      			} else {
		      				count = 14;
		      			}
		      			data = new Object[count][5];
		      			table.setModel(new DefaultTableModel(data, Res.string().getDownloadTableName()));
		    			table.getColumnModel().getColumn(0).setPreferredWidth(23);
		    			table.getColumnModel().getColumn(1).setPreferredWidth(28);
		    			table.getColumnModel().getColumn(2).setPreferredWidth(50);
		                
						if(nFindCount.getValue() == 0) {	 
							return;
						}
						
		    			model = (DefaultTableModel)table.getModel();
		    			
						for(int j = 0; j < nFindCount.getValue(); j++) {
							model.setValueAt(String.valueOf(i), j, 0);
							model.setValueAt(String.valueOf(stFileInfo[j].ch + 1), j, 1);    // 设备返回的通道加1
							model.setValueAt(Res.string().getRecordTypeStr(stFileInfo[j].nRecordFileType), j, 2);
							model.setValueAt(stFileInfo[j].starttime.toStringTime(), j, 3);
							model.setValueAt(stFileInfo[j].endtime.toStringTime(), j, 4);
							
							i++;
						}
					}
				}
			});
		    
		    downloadByFileBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {	
					int row = -1;
					row = table.getSelectedRow(); //获得所选的单行
		
					if(model == null) {
						JOptionPane.showMessageDialog(null, Res.string().getQueryRecord(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(row < 0) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectRowWithData(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					for(int m = 1; m < 5; m++) {
						if(model.getValueAt(row, m) == null || String.valueOf(model.getValueAt(row, m)).trim().equals("")) {
							JOptionPane.showMessageDialog(null, Res.string().getSelectRowWithData(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
							return;
						}
					}					
					
					// 开始时间
					String[] dateStart = String.valueOf(model.getValueAt(row, 3)).split(" ");
					String[] dateStartByFile1 = dateStart[0].split("/");
					String[] dateStartByFile2 = dateStart[1].split(":");
					
					stTimeStart.dwYear = Integer.parseInt(dateStartByFile1[0]);
					stTimeStart.dwMonth = Integer.parseInt(dateStartByFile1[1]);
					stTimeStart.dwDay = Integer.parseInt(dateStartByFile1[2]);
					
					stTimeStart.dwHour = Integer.parseInt(dateStartByFile2[0]);
					stTimeStart.dwMinute = Integer.parseInt(dateStartByFile2[1]);
					stTimeStart.dwSecond = Integer.parseInt(dateStartByFile2[2]);
					
					// 结束时间
					String[] dateEnd = String.valueOf(model.getValueAt(row, 4)).split(" ");
					String[] dateEndByFile1 = dateEnd[0].split("/");
					String[] dateEndByFile2 = dateEnd[1].split(":");
					
					stTimeEnd.dwYear = Integer.parseInt(dateEndByFile1[0]);
					stTimeEnd.dwMonth = Integer.parseInt(dateEndByFile1[1]);
					stTimeEnd.dwDay = Integer.parseInt(dateEndByFile1[2]);
					
					stTimeEnd.dwHour = Integer.parseInt(dateEndByFile2[0]);
					stTimeEnd.dwMinute = Integer.parseInt(dateEndByFile2[1]);
					stTimeEnd.dwSecond = Integer.parseInt(dateEndByFile2[2]);
					
					if(!b_downloadByFile) {
						System.out.println("ByFile" + String.valueOf(model.getValueAt(row, 3)) + "\n" + String.valueOf(model.getValueAt(row, 4)));
					    SwingUtilities.invokeLater(new Runnable() {				
							@Override
							public void run() {
								downloadByFileProgressBar.setValue(0);
							}
						});	
						m_hDownLoadByFileHandle = DownLoadRecordModule.downloadRecordFile(Integer.parseInt(String.valueOf(model.getValueAt(row, 1))) - 1, 
																			  Res.string().getRecordTypeInt(String.valueOf(model.getValueAt(row, 2))), 
																			  stTimeStart, 
																			  stTimeEnd, 
																			  SavePath.getSavePath().getSaveRecordFilePath(),
																			  m_DownLoadPosByFile);
						if(m_hDownLoadByFileHandle.longValue() != 0) {
							b_downloadByFile = true;
							downloadByFileBtn.setText(Res.string().getStopDownload());
						} else {
							JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						}
					} else {
						DownLoadRecordModule.stopDownLoadRecordFile(m_hDownLoadByFileHandle);			
						b_downloadByFile = false;
						downloadByFileBtn.setText(Res.string().getDownload());
					    SwingUtilities.invokeLater(new Runnable() {				
							@Override
							public void run() {
								downloadByFileProgressBar.setValue(0);
							}
						});
					}
				}			
			});
		}
	}
	
	/*
	 * 按时间下载面板
	 */
	private class DownLoadByTimePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public DownLoadByTimePanel() {
			BorderEx.set(this, Res.string().getDownloadByTime(), 2);
			setLayout(new BorderLayout());
			
			downloadByTimeSetPanel = new JPanel(); // 设置
			downByTimePanel = new JPanel();  // 下载
		    
			add(downloadByTimeSetPanel, BorderLayout.NORTH);
			add(downByTimePanel, BorderLayout.CENTER);
			
			/******** 设置面板***********/
			JPanel startTimeByTime = new JPanel();
			JPanel endTimeByTime = new JPanel();
			JPanel chnByTime = new JPanel();
			JPanel streamByTime = new JPanel();
			
			downloadByTimeSetPanel.setLayout(new GridLayout(2, 2));
			
			downloadByTimeSetPanel.add(startTimeByTime);
			downloadByTimeSetPanel.add(endTimeByTime);
			downloadByTimeSetPanel.add(chnByTime);
			downloadByTimeSetPanel.add(streamByTime);
			
			// 开始时间设置
			startTimeByTime.setBorder(new EmptyBorder(5, 5, 5, 20));
			startTimeByTime.setLayout(new GridLayout(2, 1));
			JLabel startLabel = new JLabel(Res.string().getStartTime());
			dateChooserStartByTime = new DateChooserJButton();
			Dimension dimension = new Dimension();
			dimension.height = 20;
			dateChooserStartByTime.setPreferredSize(dimension);
			
			startTimeByTime.add(startLabel);
			startTimeByTime.add(dateChooserStartByTime);
		    
			// 结束时间设置
			endTimeByTime.setBorder(new EmptyBorder(5, 20, 5, 5));
			endTimeByTime.setLayout(new GridLayout(2, 1));
			JLabel endLabel = new JLabel(Res.string().getEndTime());
			dateChooserEndByTime = new DateChooserJButton();
			dateChooserEndByTime.setPreferredSize(dimension);
			
			endTimeByTime.add(endLabel);
			endTimeByTime.add(dateChooserEndByTime);
		    
		    // 通道设置
			chnByTime.setBorder(new EmptyBorder(5, 10, 0, 5));
			chnByTime.setLayout(new FlowLayout());
			chnlabel = new JLabel(Res.string().getChannel());
			chnComboBoxByTime = new JComboBox();	
			chnComboBoxByTime.setPreferredSize(new Dimension(115, 20));  
			chnByTime.add(chnlabel);
			chnByTime.add(chnComboBoxByTime);
			
			// 码流设置
			streamByTime.setBorder(new EmptyBorder(5, 10, 0, 5));
			streamByTime.setLayout(new FlowLayout());
			streamLabel = new JLabel(Res.string().getStreamType());
			String[] stream = {Res.string().getMasterAndSub(), Res.string().getMasterStream(), Res.string().getSubStream()};
			streamComboBoxByTime = new JComboBox();	
			streamComboBoxByTime.setModel(new DefaultComboBoxModel(stream));
			streamComboBoxByTime.setPreferredSize(new Dimension(115, 20));  
			streamByTime.add(streamLabel);
			streamByTime.add(streamComboBoxByTime);

			/******** 下载面板***********/
			downByTimePanel.setLayout(new FlowLayout());
			downByTimePanel.setBorder(new EmptyBorder(0, 5, 0, 5));
		    
		    JPanel btnPanel2 = new JPanel();
		    downloadByTimeProgressBar = new JProgressBar(0, 100);
		    
		    downloadByTimeProgressBar.setPreferredSize(new Dimension(355, 20)); 
		    downloadByTimeProgressBar.setStringPainted(true);
		    
		    downByTimePanel.add(btnPanel2);
		    downByTimePanel.add(downloadByTimeProgressBar);
		    
		    // 下载按钮
		    downloadByTimeBtn = new JButton(Res.string().getDownload());
		    JLabel nullLabel = new JLabel();
		    nullLabel.setPreferredSize(new Dimension(180, 20)); 
		    downloadByTimeBtn.setPreferredSize(new Dimension(170, 20)); 
		    
		    btnPanel2.setLayout(new FlowLayout());
		    btnPanel2.add(downloadByTimeBtn);
		    btnPanel2.add(nullLabel);

		    downloadByTimeBtn.setEnabled(false);
		    downloadByTimeProgressBar.setEnabled(false);
		    chnComboBoxByTime.setEnabled(false);
		    streamComboBoxByTime.setEnabled(false);
		    dateChooserStartByTime.setEnabled(false);
		    dateChooserEndByTime.setEnabled(false);
		    
		    streamComboBoxByTime.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					DownLoadRecordModule.setStreamType(streamComboBoxByTime.getSelectedIndex());
				}
			});
		    
		    downloadByTimeBtn.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					int time = 0;
					// 开始时间
					String[] dateStartByTime = dateChooserStartByTime.getText().split(" ");
					String[] dateStart1 = dateStartByTime[0].split("-");
					String[] dateStart2 = dateStartByTime[1].split(":");
					
					stTimeStart.dwYear = Integer.parseInt(dateStart1[0]);
					stTimeStart.dwMonth = Integer.parseInt(dateStart1[1]);
					stTimeStart.dwDay = Integer.parseInt(dateStart1[2]);
					
					stTimeStart.dwHour = Integer.parseInt(dateStart2[0]);
					stTimeStart.dwMinute = Integer.parseInt(dateStart2[1]);
					stTimeStart.dwSecond = Integer.parseInt(dateStart2[2]);
					
					// 结束时间
					String[] dateEndByTime = dateChooserEndByTime.getText().split(" ");
					String[] dateEnd1 = dateEndByTime[0].split("-");
					String[] dateEnd2 = dateEndByTime[1].split(":");
					
					stTimeEnd.dwYear = Integer.parseInt(dateEnd1[0]);
					stTimeEnd.dwMonth = Integer.parseInt(dateEnd1[1]);
					stTimeEnd.dwDay = Integer.parseInt(dateEnd1[2]);
					
					stTimeEnd.dwHour = Integer.parseInt(dateEnd2[0]);
					stTimeEnd.dwMinute = Integer.parseInt(dateEnd2[1]);
					stTimeEnd.dwSecond = Integer.parseInt(dateEnd2[2]);
					
					if(stTimeStart.dwYear != stTimeEnd.dwYear
					   || stTimeStart.dwMonth != stTimeEnd.dwMonth
					   || (stTimeEnd.dwDay - stTimeStart.dwDay) > 1) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectTimeAgain(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;				
					}
					
					if(stTimeEnd.dwDay - stTimeStart.dwDay == 1) {
						time = (24 + stTimeEnd.dwHour)*60*60 + stTimeEnd.dwMinute*60 + stTimeEnd.dwSecond -
								   stTimeStart.dwHour*60*60 - stTimeStart.dwMinute*60 - stTimeStart.dwSecond;
					} else {
						time = stTimeEnd.dwHour*60*60 + stTimeEnd.dwMinute*60 + stTimeEnd.dwSecond -
								   stTimeStart.dwHour*60*60 - stTimeStart.dwMinute*60 - stTimeStart.dwSecond;
					}
					System.out.println("time :" + time);
					if(time > 6 * 60 * 60 
					   || time <= 0) {
						JOptionPane.showMessageDialog(null, Res.string().getSelectTimeAgain(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;	
					}
					
					if(!b_downloadByTime) {
						System.out.println("ByTime" + dateChooserStartByTime.getText() + "\n" + dateChooserEndByTime.getText());
					    SwingUtilities.invokeLater(new Runnable() {				
							@Override
							public void run() {
								downloadByTimeProgressBar.setValue(0);
							}
						});
						m_hDownLoadByTimeHandle = DownLoadRecordModule.downloadRecordFile(chnComboBoxByTime.getSelectedIndex(), 
																		    0, 
																		    stTimeStart, 
																		    stTimeEnd, 
																		    SavePath.getSavePath().getSaveRecordFilePath(),
																		    m_DownLoadPosByTime);
						if(m_hDownLoadByTimeHandle.longValue() != 0) {
							b_downloadByTime = true;
							downloadByTimeBtn.setText(Res.string().getStopDownload());
						    chnComboBoxByTime.setEnabled(false);
						    streamComboBoxByTime.setEnabled(false);
						    dateChooserStartByTime.setEnabled(false);
						    dateChooserEndByTime.setEnabled(false);
						} else {
							JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						}
					} else {
						DownLoadRecordModule.stopDownLoadRecordFile(m_hDownLoadByTimeHandle);
						b_downloadByTime = false;
						downloadByTimeBtn.setText(Res.string().getDownload());
					    chnComboBoxByTime.setEnabled(true);
					    streamComboBoxByTime.setEnabled(true);
					    dateChooserStartByTime.setEnabled(true);
					    dateChooserEndByTime.setEnabled(true);
					    
					    SwingUtilities.invokeLater(new Runnable() {				
							@Override
							public void run() {
								downloadByTimeProgressBar.setValue(0);
							}
						});
					}
				}
			});
		}
	}
	
	/*
	 * 按文件下载回调
	 */
	private DownLoadPosCallBackByFile m_DownLoadPosByFile = new DownLoadPosCallBackByFile(); // 录像下载进度
	class DownLoadPosCallBackByFile implements NetSDKLib.fTimeDownLoadPosCallBack{
		public void invoke(LLong lLoginID, final int dwTotalSize, final int dwDownLoadSize, int index, NetSDKLib.NET_RECORDFILE_INFO.ByValue recordfileinfo, Pointer dwUser) {	
			SwingUtilities.invokeLater(new Runnable() {	
				@Override
				public void run() {
//					System.out.println("ByFile " + dwDownLoadSize + " / " + dwTotalSize);
					downloadByFileProgressBar.setValue(dwDownLoadSize*100 / dwTotalSize);
					if(dwDownLoadSize == -1) {
						downloadByFileProgressBar.setValue(100);
						DownLoadRecordModule.stopDownLoadRecordFile(m_hDownLoadByFileHandle);
						b_downloadByFile = false;
						downloadByFileBtn.setText(Res.string().getDownload());
						JOptionPane.showMessageDialog(null, Res.string().getDownloadCompleted(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
	}
	
	/*
	 * 按时间下载回调
	 */
	private DownLoadPosCallBackByTime m_DownLoadPosByTime = new DownLoadPosCallBackByTime(); // 录像下载进度
	class DownLoadPosCallBackByTime implements NetSDKLib.fTimeDownLoadPosCallBack{
		public void invoke(LLong lLoginID, final int dwTotalSize, final int dwDownLoadSize, int index, NetSDKLib.NET_RECORDFILE_INFO.ByValue recordfileinfo, Pointer dwUser) {	
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
//					System.out.println("ByTime " + dwDownLoadSize + " / " + dwTotalSize);
					downloadByTimeProgressBar.setValue(dwDownLoadSize*100 / dwTotalSize);
					if(dwDownLoadSize == -1) {
						downloadByTimeProgressBar.setValue(100);
						DownLoadRecordModule.stopDownLoadRecordFile(m_hDownLoadByTimeHandle);
						b_downloadByTime = false;
						downloadByTimeBtn.setText(Res.string().getDownload());
					    chnComboBoxByTime.setEnabled(true);
					    streamComboBoxByTime.setEnabled(true);
					    dateChooserStartByTime.setEnabled(true);
					    dateChooserEndByTime.setEnabled(true);
						JOptionPane.showMessageDialog(null, Res.string().getDownloadCompleted(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
	}
	
	private void setButtonEnable(boolean bln) {
		queryRecordBtn.setEnabled(bln);
		downloadByFileBtn.setEnabled(bln);
		downloadByFileProgressBar.setValue(0);
		downloadByFileProgressBar.setEnabled(bln);
		downloadByTimeBtn.setEnabled(bln);
		downloadByTimeProgressBar.setValue(0);
		downloadByTimeProgressBar.setEnabled(bln);	 
		chnComboBoxByFile.setEnabled(bln);
		streamComboBoxByFile.setEnabled(bln);
		chnComboBoxByTime.setEnabled(bln);
		streamComboBoxByTime.setEnabled(bln);
		dateChooserStartByFile.setEnabled(bln);
		dateChooserEndByFile.setEnabled(bln);
		dateChooserStartByTime.setEnabled(bln);
		dateChooserEndByTime.setEnabled(bln);	
	}
		
	//登录组件
	private LoginPanel loginPanel;
	// 下载
	private DownLoadRecordPanel downloadRecordPanel;
	
	// 按文件下载
	private DownLoadByTimePanel downloadByTimePanel; 
	private JPanel downloadByFileSetPanel;
	private JPanel queryPanel;
	private JPanel downByFilePanel;
	private JButton queryRecordBtn;
	private JButton downloadByFileBtn;
	private JProgressBar downloadByFileProgressBar;
	private JButton downloadByTimeBtn;
	private JProgressBar downloadByTimeProgressBar;
	private JTable table;
	private DefaultTableModel defaultmodel;
    private JLabel chnlabel;
    private JComboBox chnComboBoxByFile;	
    private JComboBox chnComboBoxByTime;	
    private JLabel streamLabel;
    private JComboBox streamComboBoxByFile;
    private JComboBox streamComboBoxByTime;
    
    private DateChooserJButton dateChooserStartByFile;
    private DateChooserJButton dateChooserEndByFile;
	
	 // 按文件下载
	private DownLoadByFilePanel downloadByFilePanel; 
	private JPanel downloadByTimeSetPanel;
	private JPanel downByTimePanel;
	
    private DateChooserJButton dateChooserStartByTime;
    private DateChooserJButton dateChooserEndByTime;
}

public class DownLoadRecord {  
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DownLoadRecordFrame demo = new DownLoadRecordFrame();
				demo.setVisible(true);
			}
		});		
	}
};

