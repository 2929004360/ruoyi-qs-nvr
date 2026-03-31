package com.ruoyi.dahua.frame;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.FunctionList;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib.*;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.DeviceInitModule;
import com.ruoyi.dahua.module.DeviceSearchModule;
import com.ruoyi.dahua.module.LoginModule;
import com.sun.jna.Pointer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class DeviceSearchAndInitFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	private Object[][] data;
	
	private static int index = 0;
	
	private int count = 0;
	
	// 设备搜索句柄
	private static LLong m_DeviceSearchHandle = new LLong(0);
	
	// key:MAC  value:密码重置方式
	private static Map<String, Byte> pwdResetHashMap = new HashMap<String, Byte>();
	
	// MAC列表，用于设备搜索过滤
	private static ArrayList<String> macArrayList = new ArrayList<String>();
	
	private Component  target     = this;
	
	// true表示单播搜索结束
	private volatile boolean bFlag = true;
	
	// 线程池，用于单播搜索
	private ExecutorService executorService = Executors.newFixedThreadPool(4);
	
	public DeviceSearchAndInitFrame() {
	    setTitle(Res.string().getDeviceSearchAndInit());
	    setSize(700, 560);
	    setLayout(new BorderLayout());
	    setResizable(false);
	    setLocationRelativeTo(null);
		LoginModule.init(null, null);   // 打开工程，初始化
		
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        	e.printStackTrace();
        } 
	 
	    deviceSearchPanel = new DeviceSearchPanel();
	    deviceSearchResultShowPanel = new DeviceSearchResultShowListPanel();
	    deviceIntPanel = new DeviceInitPanel();

	    add(deviceSearchPanel, BorderLayout.NORTH);
	    add(deviceSearchResultShowPanel, BorderLayout.CENTER);
	    add(deviceIntPanel, BorderLayout.SOUTH);
        
	    enableEvents(WindowEvent.WINDOW_EVENT_MASK);
	}
	
	@Override
	protected void processWindowEvent(WindowEvent e) {
		// 关闭窗口监听事件
		if(e.getID() == WindowEvent.WINDOW_CLOSING) {
			if(!bFlag) {
				// 等待单播搜索结束
				JOptionPane.showMessageDialog(null, Res.string().getSearchingWait(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {				
				for(int i=0 ;i<handles.size() ;i++) {
					DeviceSearchModule.stopDeviceSearch(handles.get(i));
				}				
        		if(!executorService.isShutdown()) {
    				executorService.shutdown();			
    			}
    	
        		LoginModule.cleanup();   // 关闭工程，释放资源
        		dispose();	
        		
        		SwingUtilities.invokeLater(new Runnable() {
        			public void run() {
        				FunctionList demo = new FunctionList();
        				demo.setVisible(true);
        			}
        		});
			}
		} 
		
		super.processWindowEvent(e);
	}

	/*
	 * 设备搜索操作面板
	 */
	private class DeviceSearchPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public DeviceSearchPanel() {
			BorderEx.set(this, Res.string().getDeviceSearchOperate(), 2);
			setLayout(new BorderLayout());
			Dimension dimension = new Dimension();
			dimension.height = 85;
			setPreferredSize(dimension);
	
			MulticastAndBroadcastDeviceSearchPanel multiAndBroadPanel = new MulticastAndBroadcastDeviceSearchPanel();
			UnicastDeviceSearchPanel unicastPanel = new UnicastDeviceSearchPanel();
			
			add(multiAndBroadPanel, BorderLayout.WEST);
			add(unicastPanel, BorderLayout.CENTER);
		}
	}
	
	/*
	 * 设备组播和广播搜索面板(设备搜索)
	 */
	private class MulticastAndBroadcastDeviceSearchPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public MulticastAndBroadcastDeviceSearchPanel() {
			BorderEx.set(this, Res.string().getDeviceSearch(), 1);
			setLayout(new FlowLayout());
			Dimension dimension = new Dimension();
			dimension.width = 220;
			setPreferredSize(dimension);
			
			multiAndBroadcastSearchBtn = new JButton(Res.string().getStartSearch());
			multiAndBroadcastSearchBtn.setPreferredSize(new Dimension(120, 20));
			add(multiAndBroadcastSearchBtn);
			
			multiAndBroadcastSearchBtn.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent arg0) {	
					deviceInitBtn.setEnabled(true);
					for(int i=0 ;i<handles.size() ;i++) {
						DeviceSearchModule.stopDeviceSearch(handles.get(i));
					}	

				    // 列表清空
					data = new Object[1000][11];
					defaultModel = new DefaultTableModel(data, Res.string().getDeviceTableName());
					table.setModel(defaultModel);

					table.getColumnModel().getColumn(0).setPreferredWidth(50);
					table.getColumnModel().getColumn(1).setPreferredWidth(80);
					table.getColumnModel().getColumn(2).setPreferredWidth(80);
					table.getColumnModel().getColumn(3).setPreferredWidth(120);
					table.getColumnModel().getColumn(4).setPreferredWidth(80);
					table.getColumnModel().getColumn(5).setPreferredWidth(120);
					table.getColumnModel().getColumn(6).setPreferredWidth(120);
					table.getColumnModel().getColumn(7).setPreferredWidth(140);
					table.getColumnModel().getColumn(8).setPreferredWidth(100);
					table.getColumnModel().getColumn(9).setPreferredWidth(100);
					table.getColumnModel().getColumn(10).setPreferredWidth(100);					
					

					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					
					pwdResetHashMap.clear();
					macArrayList.clear();
					list.clear();
					index = 0;
					try {
						for(int i=0 ;i<DeviceSearchModule.getHostAddress().size(); i++) {
						LLong handle= m_DeviceSearchHandle = DeviceSearchModule.multiBroadcastDeviceSearch(callbackEx,DeviceSearchModule.getHostAddress().get(i));
						handles.add(handle);
						}
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}
			});
			
		}
	}
	
	/*
	 * 设备IP单播搜索面板(设备IP点到点搜索)
	 */
	private class UnicastDeviceSearchPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public UnicastDeviceSearchPanel() {
			BorderEx.set(this, Res.string().getDevicePointToPointSearch(), 1);
			setLayout(new FlowLayout());
			
			JLabel startIpLabel = new JLabel(Res.string().getStartIp());
			JLabel endIpLabel = new JLabel(Res.string().getEndIp());
			
			startIpTextField = new JTextField("172.23.3.0");
			endIpTextField = new JTextField("172.23.3.231");
			
			unicastSearchBtn = new JButton(Res.string().getStartSearch());
			
			startIpTextField.setPreferredSize(new Dimension(100, 20));
			endIpTextField.setPreferredSize(new Dimension(100, 20));
			unicastSearchBtn.setPreferredSize(new Dimension(120, 20));
			
			add(startIpLabel);
			add(startIpTextField);
			add(endIpLabel);
			add(endIpTextField);
			add(unicastSearchBtn);
			
			unicastSearchBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {		
					deviceInitBtn.setEnabled(false);
					index = 0;
					count = 0;
					bFlag = false;
					
					if(!checkIP()) {
						return;
					}
					
					SwingUtilities.invokeLater(new Runnable() {				
						@Override
						public void run() {
							unicastSearchBtn.setEnabled(false);
						}
					});
					
					// 清空列表
					data = new Object[1000][11];
					defaultModel = new DefaultTableModel(data, Res.string().getDeviceTableName());
					table.setModel(defaultModel);

					table.getColumnModel().getColumn(0).setPreferredWidth(50);
					table.getColumnModel().getColumn(1).setPreferredWidth(80);
					table.getColumnModel().getColumn(2).setPreferredWidth(80);
					table.getColumnModel().getColumn(3).setPreferredWidth(120);
					table.getColumnModel().getColumn(4).setPreferredWidth(80);
					table.getColumnModel().getColumn(5).setPreferredWidth(120);
					table.getColumnModel().getColumn(6).setPreferredWidth(120);
					table.getColumnModel().getColumn(7).setPreferredWidth(140);
					table.getColumnModel().getColumn(8).setPreferredWidth(100);
					table.getColumnModel().getColumn(9).setPreferredWidth(100);
					table.getColumnModel().getColumn(10).setPreferredWidth(100);
					
					
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					
					pwdResetHashMap.clear();
					macArrayList.clear();
					
					for(int i=0 ;i<handles.size() ;i++) {
						DeviceSearchModule.stopDeviceSearch(handles.get(i));
					}	
					
					if(count > 0 && count <= 256) {	
						executorService.execute(new Runnable() {					
							@Override
							public void run() {
								try {
									for(int i = 0; i<DeviceSearchModule.getHostAddress().size() ; i++) {
									DeviceSearchModule.unicastDeviceSearch(DeviceSearchModule.getHostAddress().get(i),startIpTextField.getText(), count, callback);
									}
								} catch (SocketException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}							

								bFlag = true;
								SwingUtilities.invokeLater(new Runnable() {							
									@Override
									public void run() {
										unicastSearchBtn.setEnabled(true);
									}
								});							
							}
						});
					} else if(count > 256 && count <= 512){	
						executorService.execute(new Runnable() {					
							@Override
							public void run() {
								try {
									for(int i = 0; i<DeviceSearchModule.getHostAddress().size() ; i++) {
									DeviceSearchModule.unicastDeviceSearch(DeviceSearchModule.getHostAddress().get(i),startIpTextField.getText(), 256, callback);
									}
								} catch (SocketException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						
						executorService.execute(new Runnable() {					
							@Override
							public void run() {
								String[] szIp = startIpTextField.getText().split("\\.");
								try {
									for(int i = 0; i<DeviceSearchModule.getHostAddress().size() ; i++) {
									DeviceSearchModule.unicastDeviceSearch(DeviceSearchModule.getHostAddress().get(i),DeviceSearchModule.getIp(szIp, 255), count - 256, callback);
									}
								} catch (SocketException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}						

								bFlag = true;
								SwingUtilities.invokeLater(new Runnable() {							
									@Override
									public void run() {
										unicastSearchBtn.setEnabled(true);
									}
								});					
							}
						});			
					} else if(count > 512 && count <= 768){	
						executorService.execute(new Runnable() {					
							@Override
							public void run() {
								try {
									for(int i = 0; i<DeviceSearchModule.getHostAddress().size() ; i++) {
									DeviceSearchModule.unicastDeviceSearch(DeviceSearchModule.getHostAddress().get(i),startIpTextField.getText(), 256, callback);
									}
								} catch (SocketException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						
						executorService.execute(new Runnable() {					
							@Override
							public void run() {
								String[] szIp = startIpTextField.getText().split("\\.");
								try {
									for(int i = 0; i<DeviceSearchModule.getHostAddress().size() ; i++) {
									DeviceSearchModule.unicastDeviceSearch(DeviceSearchModule.getHostAddress().get(i),DeviceSearchModule.getIp(szIp, 255), 256, callback);
									}
								} catch (SocketException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						
						executorService.execute(new Runnable() {					
							@Override
							public void run() {
								String[] szIp = startIpTextField.getText().split("\\.");
								szIp = DeviceSearchModule.getIp(szIp, 255).split("\\.");
								try {
									for(int i = 0; i<DeviceSearchModule.getHostAddress().size() ; i++) {
									DeviceSearchModule.unicastDeviceSearch(DeviceSearchModule.getHostAddress().get(i),DeviceSearchModule.getIp(szIp, 255), count - 512, callback);
									}
								} catch (SocketException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
														
								bFlag = true;
								SwingUtilities.invokeLater(new Runnable() {							
									@Override
									public void run() {
										unicastSearchBtn.setEnabled(true);
									}
								});							
							}
						});						
					} else if(count > 768 && count <= 1000){
						executorService.execute(new Runnable() {					
							@Override
							public void run() {
								try {
									for(int i = 0; i<DeviceSearchModule.getHostAddress().size() ; i++) {
									DeviceSearchModule.unicastDeviceSearch(DeviceSearchModule.getHostAddress().get(i),startIpTextField.getText(), 256, callback);
									}
								} catch (SocketException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						
						executorService.execute(new Runnable() {					
							@Override
							public void run() {
								String[] szIp = startIpTextField.getText().split("\\.");
								try {
									for(int i = 0; i<DeviceSearchModule.getHostAddress().size() ; i++) {
									DeviceSearchModule.unicastDeviceSearch(DeviceSearchModule.getHostAddress().get(i),DeviceSearchModule.getIp(szIp, 255), 256, callback);
									}
								} catch (SocketException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
							}
						});
						
						executorService.execute(new Runnable() {					
							@Override
							public void run() {
								String[] szIp = startIpTextField.getText().split("\\.");
								szIp = DeviceSearchModule.getIp(szIp, 255).split("\\.");
								try {
									for(int i = 0; i<DeviceSearchModule.getHostAddress().size() ; i++) {
									DeviceSearchModule.unicastDeviceSearch(DeviceSearchModule.getHostAddress().get(i),DeviceSearchModule.getIp(szIp, 255), 256, callback);
									}
								} catch (SocketException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						
						executorService.execute(new Runnable() {					
							@Override
							public void run() {
								String[] szIp = startIpTextField.getText().split("\\.");
								szIp = DeviceSearchModule.getIp(szIp, 255).split("\\.");
								szIp = DeviceSearchModule.getIp(szIp, 255).split("\\.");
								try {
									for(int i = 0; i<DeviceSearchModule.getHostAddress().size() ; i++) {
									DeviceSearchModule.unicastDeviceSearch(DeviceSearchModule.getHostAddress().get(i),DeviceSearchModule.getIp(szIp, 255), count - 768, callback);
									}
								} catch (SocketException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								bFlag = true;
								SwingUtilities.invokeLater(new Runnable() {							
									@Override
									public void run() {
										unicastSearchBtn.setEnabled(true);
									}
								});								
							}
						});
					}	
				}
			});
		}
	}
	
	/*
	 * 设备搜索结果显示列表面板
	 */
	private class DeviceSearchResultShowListPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public DeviceSearchResultShowListPanel() {
			BorderEx.set(this, Res.string().getDeviceSearchResult(), 2);
			setLayout(new BorderLayout());
			
			data = new Object[1000][11];			
			defaultModel = new DefaultTableModel(data, Res.string().getDeviceTableName());
			table = new JTable(defaultModel) {   // 列表不可编辑
				private static final long serialVersionUID = 1L;
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 只能选中一行
			
			table.getColumnModel().getColumn(0).setPreferredWidth(50);
			table.getColumnModel().getColumn(1).setPreferredWidth(80);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setPreferredWidth(120);
			table.getColumnModel().getColumn(4).setPreferredWidth(80);
			table.getColumnModel().getColumn(5).setPreferredWidth(120);
			table.getColumnModel().getColumn(6).setPreferredWidth(120);
			table.getColumnModel().getColumn(7).setPreferredWidth(140);
			table.getColumnModel().getColumn(8).setPreferredWidth(100);
			table.getColumnModel().getColumn(9).setPreferredWidth(100);
			table.getColumnModel().getColumn(10).setPreferredWidth(100);			
			
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

			add(scrollPane, BorderLayout.CENTER);
			
		}
	}
	
	/*
	 * 设备初始化操作面板
	 */
	private class DeviceInitPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public DeviceInitPanel() {
			BorderEx.set(this, Res.string().getDeviceInit(), 2);
			setLayout(new BorderLayout());
			Dimension dimension = new Dimension();
			dimension.height = 55;
			setPreferredSize(dimension);
			
			deviceInitBtn = new JButton(Res.string().getDeviceInit());
			
			add(deviceInitBtn, BorderLayout.WEST);
			
			deviceInitBtn.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent arg0) {	
					int row = -1;
					row = table.getSelectedRow(); //获得所选的单行
					System.out.println(new String (list.get(row).szLocalIP));
					if(defaultModel == null) {
						JOptionPane.showMessageDialog(null, Res.string().getPleaseSelectInitializedDevice(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(row < 0) {
						JOptionPane.showMessageDialog(null, Res.string().getPleaseSelectInitializedDevice(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					

					if(defaultModel.getValueAt(row, 7) == null || String.valueOf(defaultModel.getValueAt(row, 7)).trim().equals("")) {
						JOptionPane.showMessageDialog(null, Res.string().getPleaseSelectInitializedDevice(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}

					if(defaultModel.getValueAt(row, 1) == null || String.valueOf(defaultModel.getValueAt(row, 1)).trim().equals(Res.string().getInitialized())) {
						JOptionPane.showMessageDialog(null, Res.string().getInitialized(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}						
				
					String localIp = new String(list.get(row).szLocalIP).trim();
					
					String mac = String.valueOf(defaultModel.getValueAt(row, 7)).trim(); // MAC地址
					byte passwdReset = pwdResetHashMap.get(mac);  // 密码重置方式
							
					DevcieInitFrame demo = new DevcieInitFrame(localIp,passwdReset, mac, row, defaultModel, table);
					demo.setLocationRelativeTo(null);
					demo.setVisible(true);		    
				}
			});
		}
	}
	
	/*
	 *  设备组播和广播搜索回调
	 */
	private Test_fSearchDevicesCbEx callbackEx = new Test_fSearchDevicesCbEx();
	private class Test_fSearchDevicesCbEx implements fSearchDevicesCBEx {
		
		/*
		 * @Override public void invoke(Pointer pDevNetInfo, Pointer pUserData) {
		 * DEVICE_NET_INFO_EX deviceInfo = new DEVICE_NET_INFO_EX();
		 * ToolKits.GetPointerData(pDevNetInfo, deviceInfo);
		 * 
		 * EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue(); if
		 * (eventQueue != null) { eventQueue.postEvent( new DeviceSearchList(target,
		 * deviceInfo)); } }
		 */

		@Override
		public void invoke(LLong lSearchHandle, Pointer pDevNetInfo, Pointer pUserData) {
			// TODO Auto-generated method stub
			DEVICE_NET_INFO_EX2  deviceInfo =  new DEVICE_NET_INFO_EX2();	
			ToolKits.GetPointerData(pDevNetInfo, deviceInfo);
			
			EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
            if (eventQueue != null) {
                eventQueue.postEvent( new DeviceSearchListEx(target, deviceInfo));
            }  
		}	
	}
	
	/*
	 *  设备单播搜索回调
	 */
	private Test_fSearchDevicesCB callback = new Test_fSearchDevicesCB();
	private class Test_fSearchDevicesCB implements fSearchDevicesCB {
		
		
		  @Override 
		  public void invoke(Pointer pDevNetInfo, Pointer pUserData) {
		  DEVICE_NET_INFO_EX deviceInfo = new DEVICE_NET_INFO_EX();
		  ToolKits.GetPointerData(pDevNetInfo, deviceInfo);
		  
		  EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
		  if(eventQueue != null) 
		  { 
			  eventQueue.postEvent( new DeviceSearchList(target,deviceInfo)); 
			  } 
		  }		 
	}
	
	/*
	 *  设备搜索的信息处理
	 */
	class DeviceSearchList extends AWTEvent {
		private static final long serialVersionUID = 1L;
		public static final int EVENT_ID = AWTEvent.RESERVED_ID_MAX + 1;
		
		private DEVICE_NET_INFO_EX deviceInfo;
		
		public DeviceSearchList(Object target,
								DEVICE_NET_INFO_EX deviceInfo) {
			super(target,EVENT_ID);

			this.deviceInfo = deviceInfo;
		}
		
		public DEVICE_NET_INFO_EX getDeviceInfo() {
			return deviceInfo;
		}	
	}
	
	
	
	/*
	 *  设备搜索的信息处理
	 */
	class DeviceSearchListEx extends AWTEvent {
		private static final long serialVersionUID = 1L;
		public static final int EVENT_ID = AWTEvent.RESERVED_ID_MAX + 1;
		
		private DEVICE_NET_INFO_EX2 deviceInfo;
		
		public DeviceSearchListEx(Object target,
								DEVICE_NET_INFO_EX2 deviceInfo) {
			super(target,EVENT_ID);

			this.deviceInfo = deviceInfo;
		}
		
		public DEVICE_NET_INFO_EX2 getDeviceInfo() {
			return deviceInfo;
		}	
	}
	
	@Override
    protected void processEvent( AWTEvent event)
    {
        if ( event instanceof DeviceSearchListEx )
        {
        	
        	DeviceSearchListEx ev = (DeviceSearchListEx) event;
        	
        	DEVICE_NET_INFO_EX2 deviceInfo =  ev.getDeviceInfo();        	
        	
        	if(!macArrayList.contains(new String(deviceInfo.stuDevInfo.szMac))) {  
        		list.add(deviceInfo);        		
				if(index < 1000) {   // 此demo，只显示1000行搜索结果	
					macArrayList.add(new String(deviceInfo.stuDevInfo.szMac));

					// 序号
					defaultModel.setValueAt(index + 1, index, 0);
					
					// 初始化状态
					defaultModel.setValueAt(Res.string().getInitStateInfo(deviceInfo.stuDevInfo.byInitStatus & 0x03), index, 1);
					
					// IP版本
					defaultModel.setValueAt("IPV" + String.valueOf(deviceInfo.stuDevInfo.iIPVersion), index, 2);
					
					// IP
					if(!new String(deviceInfo.stuDevInfo.szIP).trim().isEmpty()) {
						defaultModel.setValueAt(new String(deviceInfo.stuDevInfo.szIP).trim(), index, 3);
					} else {
						defaultModel.setValueAt("", index, 3);
					}
					
					// 端口号
					defaultModel.setValueAt(String.valueOf(deviceInfo.stuDevInfo.nPort), index, 4);
					
					// 子网掩码
					if(!new String(deviceInfo.stuDevInfo.szSubmask).trim().isEmpty()) {
						defaultModel.setValueAt(new String(deviceInfo.stuDevInfo.szSubmask).trim(), index, 5);
					} else {
						defaultModel.setValueAt("", index, 5);
					}			
					
					// 网关
					if(!new String(deviceInfo.stuDevInfo.szGateway).trim().isEmpty()) {
						defaultModel.setValueAt(new String(deviceInfo.stuDevInfo.szGateway).trim(), index, 6);
					} else {
						defaultModel.setValueAt("", index, 6);
					}
					
					// MAC地址
					if(!new String(deviceInfo.stuDevInfo.szMac).trim().isEmpty()) {
						defaultModel.setValueAt(new String(deviceInfo.stuDevInfo.szMac).trim(), index, 7);
					} else {
						defaultModel.setValueAt("", index, 7);
					}
					
					// 设备类型
					if(!new String(deviceInfo.stuDevInfo.szDeviceType).trim().isEmpty()) {
						defaultModel.setValueAt(new String(deviceInfo.stuDevInfo.szDeviceType).trim(), index, 8);
					} else {
						defaultModel.setValueAt("", index, 8);
					}
					
					// 详细类型
					if(!new String(deviceInfo.stuDevInfo.szNewDetailType).trim().isEmpty()) {
						defaultModel.setValueAt(new String(deviceInfo.stuDevInfo.szNewDetailType).trim(), index, 9);
					} else {
						defaultModel.setValueAt("", index, 9);
					}
					
					// HTTP端口号
					defaultModel.setValueAt(String.valueOf(deviceInfo.stuDevInfo.nHttpPort), index, 10);
			
					// 将MAC地址   跟 密码重置方式，放进容器
					pwdResetHashMap.put(new String(deviceInfo.stuDevInfo.szMac).trim(), deviceInfo.stuDevInfo.byPwdResetWay);
		
					for(int i = 0; i < 11; i++) {
						table.getColumnModel().getColumn(i).setCellRenderer(new MyTableCellRender());
					}
					table.updateUI();

					index++;
				}
			}
		}
        
        else if ( event instanceof DeviceSearchList )
        {
        	
        	DeviceSearchList ev = (DeviceSearchList) event;
        	
        	DEVICE_NET_INFO_EX deviceInfo =  ev.getDeviceInfo();

        	if(!macArrayList.contains(new String(deviceInfo.szMac))) {  
				if(index < 1000) {   // 此demo，只显示1000行搜索结果	
					macArrayList.add(new String(deviceInfo.szMac));

					// 序号
					defaultModel.setValueAt(index + 1, index, 0);
					
					// 初始化状态
					defaultModel.setValueAt(Res.string().getInitStateInfo(deviceInfo.byInitStatus & 0x03), index, 1);
					
					// IP版本
					defaultModel.setValueAt("IPV" + String.valueOf(deviceInfo.iIPVersion), index, 2);
					
					// IP
					if(!new String(deviceInfo.szIP).trim().isEmpty()) {
						defaultModel.setValueAt(new String(deviceInfo.szIP).trim(), index, 3);
					} else {
						defaultModel.setValueAt("", index, 3);
					}
					
					// 端口号
					defaultModel.setValueAt(String.valueOf(deviceInfo.nPort), index, 4);
					
					// 子网掩码
					if(!new String(deviceInfo.szSubmask).trim().isEmpty()) {
						defaultModel.setValueAt(new String(deviceInfo.szSubmask).trim(), index, 5);
					} else {
						defaultModel.setValueAt("", index, 5);
					}			
					
					// 网关
					if(!new String(deviceInfo.szGateway).trim().isEmpty()) {
						defaultModel.setValueAt(new String(deviceInfo.szGateway).trim(), index, 6);
					} else {
						defaultModel.setValueAt("", index, 6);
					}
					
					// MAC地址
					if(!new String(deviceInfo.szMac).trim().isEmpty()) {
						defaultModel.setValueAt(new String(deviceInfo.szMac).trim(), index, 7);
					} else {
						defaultModel.setValueAt("", index, 7);
					}
					
					// 设备类型
					if(!new String(deviceInfo.szDeviceType).trim().isEmpty()) {
						defaultModel.setValueAt(new String(deviceInfo.szDeviceType).trim(), index, 8);
					} else {
						defaultModel.setValueAt("", index, 8);
					}
					
					// 详细类型
					if(!new String(deviceInfo.szNewDetailType).trim().isEmpty()) {
						defaultModel.setValueAt(new String(deviceInfo.szNewDetailType).trim(), index, 9);
					} else {
						defaultModel.setValueAt("", index, 9);
					}
					
					// HTTP端口号
					defaultModel.setValueAt(String.valueOf(deviceInfo.nHttpPort), index, 10);
	
					// 将MAC地址   跟 密码重置方式，放进容器
					pwdResetHashMap.put(new String(deviceInfo.szMac).trim(), deviceInfo.byPwdResetWay);
		
					for(int i = 0; i < 11; i++) {
						table.getColumnModel().getColumn(i).setCellRenderer(new MyTableCellRender());
					}
					table.updateUI();

					index++;
				}
			}
		}
        
        else    
        {
            super.processEvent( event );   
        }
    } 
	
	private static class MyTableCellRender implements TableCellRenderer {
		public MyTableCellRender() {}
		
		DefaultTableCellRenderer dCellRenderer = new DefaultTableCellRenderer();
		
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelect, boolean hasFocus, int row, int colum) {
			
			Component component = dCellRenderer.getTableCellRendererComponent(table, value, 
					isSelect, hasFocus, row, colum);
			if(String.valueOf(defaultModel.getValueAt(row, 1)).trim().equals(Res.string().getNotInitialized())) { // 未初始化，字体颜色变红
				component.setForeground(Color.RED);
			} else {
				component.setForeground(Color.BLACK);
			}
			
			// 列表显示居中
			dCellRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.setDefaultRenderer(Object.class, dCellRenderer);	
			
			return component;
		}
	}
	
	/*
	 * 检查设备IP点到点搜索的IP范围
	 */
	private boolean checkIP() {
		String[] startIp = startIpTextField.getText().split("\\.");
		
		String[] endIp = endIpTextField.getText().split("\\.");
		
		if(startIpTextField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, Res.string().getInputDeviceIP(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(endIpTextField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, Res.string().getInputDeviceIP(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if(!startIp[0].equals(endIp[0])) {
			JOptionPane.showMessageDialog(null, Res.string().getCheckIp(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(!startIp[1].equals(endIp[1])) {
			JOptionPane.showMessageDialog(null, Res.string().getCheckIp(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(Integer.parseInt(startIp[2]) > Integer.parseInt(endIp[2])) {
			JOptionPane.showMessageDialog(null, Res.string().getCheckIp(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(Integer.parseInt(startIp[2]) == Integer.parseInt(endIp[2])
				&& Integer.parseInt(startIp[3]) > Integer.parseInt(endIp[3])) {
			JOptionPane.showMessageDialog(null, Res.string().getCheckIp(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		count = (Integer.parseInt(endIp[2]) - Integer.parseInt(startIp[2])) * 256
				+ Integer.parseInt(endIp[3]) - Integer.parseInt(startIp[3]) + 1;
		
		if(count > 1000) {
			JOptionPane.showMessageDialog(null, Res.string().getControlScope(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
    private DeviceSearchPanel deviceSearchPanel;
    private DeviceSearchResultShowListPanel deviceSearchResultShowPanel;
    private DeviceInitPanel deviceIntPanel;
    
    private JButton deviceInitBtn;
    private JButton multiAndBroadcastSearchBtn;
    private JButton unicastSearchBtn;
    
    private JTextField startIpTextField;
    private JTextField endIpTextField;
    
	// 列表
	private static DefaultTableModel defaultModel;
	private static JTable table;
	private static List<DEVICE_NET_INFO_EX2> list =new ArrayList<DEVICE_NET_INFO_EX2>();
	private static List<LLong> handles =new ArrayList<LLong>();
}

class DevcieInitFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	byte passwdReset; 
	String localIp;
	String mac;
	int row;
	DefaultTableModel defaultModel;
	JTable table;
	
	public DevcieInitFrame(String localIp,byte passwdReset, String mac, int row, DefaultTableModel defaultModel, JTable table) {
		setTitle(Res.string().getDeviceInit());
	    setSize(300, 350);
	    setLayout(new BorderLayout());
	    setResizable(false);
	    
	    this.passwdReset = passwdReset;
	    this.localIp = localIp;
	    this.mac = mac;
	    this.row = row;
	    this.defaultModel = defaultModel;
	    this.table = table;
	    
	    initPanel = new InitPanel();
	    
	    add(initPanel, BorderLayout.CENTER);
	    
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		dispose();	
	    	}
	    });    
	}
	
	private class InitPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public InitPanel() {
			BorderEx.set(this, Res.string().getDeviceInit(), 2);
			setLayout(new BorderLayout());
			
			JPanel panel_1 = new JPanel();
			JPanel panel_2 = new JPanel();
			
			add(panel_1, BorderLayout.CENTER);
			add(panel_2, BorderLayout.SOUTH);
			
			panel_1.setLayout(new GridLayout(10, 1));
			panel_2.setLayout(new BorderLayout());
			
			JLabel userLabel = new JLabel(Res.string().getUserName() + " : ");
			JLabel passwdLabel = new JLabel(Res.string().getPassword() + " : ");	
			JLabel passwdLabelEx = new JLabel(Res.string().getConfirmPassword() + " : ");
			JTextField userTextField = new JTextField("admin");
			passwdPasswordField = new JPasswordField("admin123");
			passwdPasswordFieldEx = new JPasswordField("admin123");
			
			panel_1.add(userLabel);
			panel_1.add(userTextField);
			panel_1.add(passwdLabel);
			panel_1.add(passwdPasswordField);
			panel_1.add(passwdLabelEx);
			panel_1.add(passwdPasswordFieldEx);
			
			userTextField.setEnabled(false);
			
			if((passwdReset >> 1 & 0x01) == 0) {   // 手机号
				JLabel phoneLabel = new JLabel(Res.string().getPhone() + " : ");
				phoneTextField = new JTextField();
				panel_1.add(phoneLabel);
				panel_1.add(phoneTextField);
			} else if((passwdReset >> 1 & 0x01) == 1) {  // 邮箱
				JLabel mailLabel = new JLabel(Res.string().getMail() + " : ");
				mailTextField = new JTextField();
				panel_1.add(mailLabel);
				panel_1.add(mailTextField);
			}
			
			deviceInitBtn = new JButton(Res.string().getDeviceInit());
			panel_2.add(deviceInitBtn, BorderLayout.CENTER);
			
			deviceInitBtn.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent arg0) {	
					// 密码判空
					if(new String(passwdPasswordField.getPassword()).equals("")) {
						JOptionPane.showMessageDialog(null, Res.string().getInputPassword(), 
							      Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// 确认密码判空
					if(new String(passwdPasswordFieldEx.getPassword()).equals("")) {
						JOptionPane.showMessageDialog(null, Res.string().getInputConfirmPassword(), 
							      Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// 密码确认
					if(!new String(passwdPasswordField.getPassword())
							.equals(new String(passwdPasswordFieldEx.getPassword()))) {
						JOptionPane.showMessageDialog(null, Res.string().getInconsistent(), 
							      Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// 获取手机或邮箱
					String phone_mail = "";
					if((passwdReset >> 1 & 0x01) == 0) {
						phone_mail = phoneTextField.getText();							
					} else if((passwdReset >> 1 & 0x01) == 1) {
						phone_mail = mailTextField.getText();
					}	
					
					// 手机或邮箱判空
					if(phone_mail.equals("")) {
						if((passwdReset >> 1 & 0x01) == 0) {   // 手机号
							JOptionPane.showMessageDialog(null, Res.string().getInputPhone(), 
								      Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
							
							return;
						} else if((passwdReset >> 1 & 0x01) == 1) {  // 邮箱
							JOptionPane.showMessageDialog(null, Res.string().getInputMail(), 
								      Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
							
							return;
						}
					}

					// 初始化
					if(DeviceInitModule.initDevAccount(localIp,mac, new String(passwdPasswordField.getPassword()), phone_mail, passwdReset)) {
						dispose();
						
						defaultModel.setValueAt(Res.string().getInitialized(), row, 1);
						
						for(int i = 0; i < 11; i++) {
							table.getColumnModel().getColumn(i).setCellRenderer(new MyTableCellRender(defaultModel));
						}
						table.updateUI();
						
						JOptionPane.showMessageDialog(null, Res.string().getDeviceInit() + Res.string().getSucceed(), Res.string().getPromptMessage(), JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, Res.string().getDeviceInit() + Res.string().getFailed() + "," + ToolKits.getErrorCodeShow(), 
							      Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}	
				}
			});
		}
	}
	
	private static class MyTableCellRender implements TableCellRenderer {
		DefaultTableModel defaultModel;
		public MyTableCellRender(DefaultTableModel defaultModel) {
			this.defaultModel = defaultModel;
		}
		
		DefaultTableCellRenderer dCellRenderer = new DefaultTableCellRenderer();
		
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelect, boolean hasFocus, int row, int colum) {
			
			Component component = dCellRenderer.getTableCellRendererComponent(table, value, 
					isSelect, hasFocus, row, colum);
			if(String.valueOf(defaultModel.getValueAt(row, 1)).trim().equals(Res.string().getNotInitialized())) { // 未初始化，字体颜色变红
				component.setForeground(Color.RED);
			} else {
				component.setForeground(Color.BLACK);
			}
			
			// 列表显示居中
			dCellRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.setDefaultRenderer(Object.class, dCellRenderer);	
			
			return component;
		}
	}
	
	private InitPanel initPanel;
	private JPasswordField passwdPasswordField;
	private JPasswordField passwdPasswordFieldEx;
	private JTextField phoneTextField;
	private JTextField mailTextField;
	private JButton deviceInitBtn;

}
public class DeviceSearchAndInit {
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DeviceSearchAndInitFrame demo = new DeviceSearchAndInitFrame();	
			    demo.setLocationRelativeTo(null);
			    demo.setVisible(true);
			}
		});		
	}
}
