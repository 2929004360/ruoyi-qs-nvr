package com.ruoyi.dahua.frame.ThermalCamera;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.DateChooserJButton;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib.*;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.ThermalCameraModule;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Vector;

public class TemperQueryDialog extends JDialog{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private NET_IN_RADIOMETRY_STARTFIND stuStartFind = new NET_IN_RADIOMETRY_STARTFIND();
	
	public TemperQueryDialog() {
		setTitle(Res.string().getShowInfo("TEMPER_INFO"));
		setLayout(new BorderLayout());
		setModal(true);
		pack();
		setSize(800, 550);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		///////////////////////////////
		queryPanel = new QueryPanel();
		showPanel = new QueryShowPanel();
		
	    add(queryPanel, BorderLayout.NORTH);
	    add(showPanel, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				try {
					ThermalCameraModule.stopFind();
				}finally {
					dispose();
				}
			}
		});
	}
	
	public void setSearchEnable(boolean b) {
		showPanel.setButtonEnable(b);
		queryPanel.setButtonEnable(b);
	}
	
	public void queryHistoryInfo(final QUERY_TYPE type) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setSearchEnable(false);
				if (type == QUERY_TYPE.FIRST_PAGE_QUERY) {
					showPanel.clearData();
				}
			}
		});
		new QuerySwingWorker(type).execute();
	}
	
	/**
     * 查询界面
     * */
    public class QueryPanel extends JPanel {
    	/**
    	 * 
    	 */
    	private static final long serialVersionUID = 1L;
    	
		public QueryPanel() {
			BorderEx.set(this, Res.string().getShowInfo("QUERY_CONDITION"), 1);
			setLayout(new BorderLayout());			

			JLabel startTimeLabel = new JLabel(Res.string().getShowInfo("START_TIME"), JLabel.LEFT);
			startTimeBtn = new DateChooserJButton();
			JLabel endTimeLabel = new JLabel(Res.string().getShowInfo("END_TIME"), JLabel.LEFT);
			endTimeBtn = new DateChooserJButton();
			JLabel meterTypeLabel = new JLabel(Res.string().getShowInfo("METER_TYPE"), JLabel.LEFT);
			meterTypeComboBox = new JComboBox();
			meterTypeComboBox.setModel(new DefaultComboBoxModel(Res.string().getMeterTypeList()));
			JLabel periodLabel = new JLabel(Res.string().getShowInfo("SAVE_PERIOD"), JLabel.LEFT);
			periodComboBox = new JComboBox();
			periodComboBox.setModel(new DefaultComboBoxModel(Res.string().getPeriodList()));
			queryBtn = new JButton(Res.string().getShowInfo("QUERY"));
			
			Dimension lableDimension = new Dimension(85, 20);
			Dimension btnDimension = new Dimension(125, 20);
			
			startTimeLabel.setPreferredSize(lableDimension);
			startTimeBtn.setPreferredSize(btnDimension);
			endTimeLabel.setPreferredSize(lableDimension);
			endTimeBtn.setPreferredSize(btnDimension);
			meterTypeLabel.setPreferredSize(lableDimension);
			meterTypeComboBox.setPreferredSize(btnDimension);
			periodLabel.setPreferredSize(lableDimension);
			periodComboBox.setPreferredSize(btnDimension);
			queryBtn.setPreferredSize(btnDimension);
			
			JPanel startTimePanel = new JPanel();
			startTimePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			startTimePanel.add(startTimeLabel);
			startTimePanel.add(startTimeBtn);
			JPanel endTimePanel = new JPanel();
			endTimePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			endTimePanel.add(endTimeLabel);
			endTimePanel.add(endTimeBtn);
			JPanel topPanel = new JPanel();
			topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 2));
			topPanel.add(startTimePanel);
			topPanel.add(endTimePanel);
			
			JPanel meterTypePanel = new JPanel();
			meterTypePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			meterTypePanel.add(meterTypeLabel);
			meterTypePanel.add(meterTypeComboBox);
			JPanel periodPanel = new JPanel();
			periodPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			periodPanel.add(periodLabel);
			periodPanel.add(periodComboBox);
			JPanel bottomPanel = new JPanel();
			bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 2));
			bottomPanel.add(meterTypePanel);
			bottomPanel.add(periodPanel);
			
			JPanel leftPanel = new JPanel(new GridLayout(2,1));
			BorderEx.set(leftPanel, "", 1);
			leftPanel.add(topPanel);
			leftPanel.add(bottomPanel);
			
			JPanel rightPanel = new JPanel();
			rightPanel.setLayout(null);
			BorderEx.set(rightPanel, "", 1);
			queryBtn.setBounds(50, 30, 125, 20);
			rightPanel.add(queryBtn);
			
			JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
			pane.setDividerSize(0);
			pane.setBorder(null);
			
			add(pane, BorderLayout.CENTER);
			
			queryBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setStuStartFind();
					queryHistoryInfo(QUERY_TYPE.FIRST_PAGE_QUERY);
				}
			});
		}
		
		private void setStuStartFind() {
			
			setTime(stuStartFind.stStartTime, startTimeBtn.getText());
			setTime(stuStartFind.stEndTime, endTimeBtn.getText());
			stuStartFind.nMeterType = meterTypeComboBox.getSelectedIndex() + 1;
			stuStartFind.nChannel = ThermalCameraFrame.THERMAL_CHANNEL;
			int[] arrPeriod = {5, 10, 15, 30};
			stuStartFind.emPeriod = arrPeriod[periodComboBox.getSelectedIndex()];
		}
		
		private void setTime(NET_TIME netTime, String date) {
  
	    	String[] dateTime = date.split(" ");
	        String[] arrDate = dateTime[0].split("-");
	        String[] arrTime = dateTime[1].split(":");
	        
	        netTime.dwYear = Integer.parseInt(arrDate[0]);
	        netTime.dwMonth = Integer.parseInt(arrDate[1]);
	        netTime.dwDay = Integer.parseInt(arrDate[2]);
	        netTime.dwHour = Integer.parseInt(arrTime[0]);
	        netTime.dwMinute = Integer.parseInt(arrTime[1]);
	        netTime.dwSecond = Integer.parseInt(arrTime[2]);
		}
		
		public void setButtonEnable(boolean b) {
			queryBtn.setEnabled(b);
		}
				
		private DateChooserJButton startTimeBtn;
		private DateChooserJButton endTimeBtn;
	    private JComboBox meterTypeComboBox;
	    private JComboBox periodComboBox;
		private JButton queryBtn;
    }
	
	/**
     * 热成像查询结果信息显示界面
     * */
    public class QueryShowPanel extends JPanel {
    	/**
    	 * 
    	 */
    	private static final long serialVersionUID = 1L;
    	public static final int INDEX = 0;
    	public static final int RECORD_TIME = 1;
    	public static final int PRESET_ID = 2;
    	public static final int RULE_ID = 3;
    	public static final int ITEM_NAME = 4;
    	public static final int CHANNEL = 5;
    	public static final int COORDINATE = 6;
    	public static final int METER_TYPE = 7;
    	public static final int TEMPER_UNIT = 8;
    	public static final int TEMPER_AVER = 9;
    	public static final int TEMPER_MAX = 10;
    	public static final int TEMPER_MIN = 11;
    	public static final int TEMPER_MID = 12;
    	public static final int TEMPER_STD = 13;
		
    	public final static int QUERY_SHOW_COUNT = 20; // 查询个数
        private int currentIndex = 0;		// 实际显示个数
		
        private String [] arrMeterType = Res.string().getMeterTypeList();
        private String [] arrTemperUnit = Res.string().getTemperUnitList(); // 减少次数
		
		public QueryShowPanel() {
			BorderEx.set(this, Res.string().getShowInfo("QUERY_LIST"), 1);
			setLayout(new BorderLayout());
			
			String[] columnNames = {
					Res.string().getShowInfo("INDEX"), Res.string().getShowInfo("RECORD_TIME"),
					Res.string().getShowInfo("PRESET_ID"), Res.string().getShowInfo("RULE_ID"),
					Res.string().getShowInfo("ITEM_NAME"), Res.string().getShowInfo("CHANNEL"),
					Res.string().getShowInfo("COORDINATE"), Res.string().getShowInfo("METER_TYPE"),
					Res.string().getShowInfo("TEMPER_UNIT"), Res.string().getShowInfo("TEMPER_AVER"),
					Res.string().getShowInfo("TEMPER_MAX"), Res.string().getShowInfo("TEMPER_MIN"),
					Res.string().getShowInfo("TEMPER_MID"), Res.string().getShowInfo("TEMPER_STD")
			};
						
			tableModel = new DefaultTableModel(null, columnNames);
	        table = new JTable(tableModel) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int rowIndex, int columnIndex) { // 不可编辑
	                return false;
	            }
	        };

	        tableModel.setRowCount(QUERY_SHOW_COUNT);	// 设置最小显示行
	        
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 只能选中一行
			
	        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
			while (columns.hasMoreElements()) {
				columns.nextElement().setPreferredWidth(140);
			}
			
			table.getColumnModel().getColumn(RECORD_TIME).setPreferredWidth(140);
			table.getColumnModel().getColumn(ITEM_NAME).setPreferredWidth(180);

			((DefaultTableCellRenderer)
					table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
			
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			table.setDefaultRenderer(Object.class, tcr);
						
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 120, 5));
			prePageBtn = new JButton(Res.string().getPreviousPage());
			nextPageBtn = new JButton(Res.string().getNextPage());
			prePageBtn.setPreferredSize(new Dimension(120, 20));
			nextPageBtn.setPreferredSize(new Dimension(120, 20));
			
			setButtonEnable(false);
			
//			functionPanel.add(prePageBtn);
			functionPanel.add(nextPageBtn);
			
			add(scrollPane, BorderLayout.CENTER);
			add(functionPanel, BorderLayout.SOUTH);
			
			prePageBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					queryHistoryInfo(QUERY_TYPE.PRE_PAGE_QUERY);
				}
			});
			
			nextPageBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					queryHistoryInfo(QUERY_TYPE.NEXT_PAGE_QUERY);
				}
			});
		}
		
		public void setButtonEnable(boolean b) {
	
			prePageBtn.setEnabled(false);
			nextPageBtn.setEnabled(false);
			
			if (b) {
				if (currentIndex < ThermalCameraModule.getTotalCount()) {
					nextPageBtn.setEnabled(true);
				}
				
				if (currentIndex > QUERY_SHOW_COUNT) {
					prePageBtn.setEnabled(true);
				}
			}
		}
		
		public int getIndex() {
			return currentIndex;
		}
		
		public void setIndex(int index) {
			currentIndex = index;
		}
		
		public void insertData(NET_OUT_RADIOMETRY_DOFIND stuDoFind) {
			if (stuDoFind == null) {
				return;
			}
			
			tableModel.setRowCount(0);
			for (int i = 0; i < stuDoFind.nFound; ++i) {
				insertData(stuDoFind.stInfo[i]);
			}

			tableModel.setRowCount(QUERY_SHOW_COUNT);
			table.updateUI();
		}
    
	    private void insertData(NET_RADIOMETRY_QUERY data) {
			++currentIndex;
			Vector<String> vector = new Vector<String>();
			
			vector.add(String.valueOf(currentIndex));
			vector.add(data.stTime.toStringTimeEx());
			vector.add(String.valueOf(data.nPresetId));
			vector.add(String.valueOf(data.nRuleId));
			try {
				vector.add(new String(data.szName, "GBK").trim());
			} catch (UnsupportedEncodingException e) {
				vector.add(new String(data.szName).trim());
			}
			vector.add(String.valueOf(data.nChannel));
			
			if (data.stTemperInfo.nMeterType == NET_RADIOMETRY_METERTYPE.NET_RADIOMETRY_METERTYPE_SPOT) {
				vector.add("(" + data.stCoordinates[0].nx + "," + data.stCoordinates[0].ny + ")");
			}else if(data.stTemperInfo.nMeterType == NET_RADIOMETRY_METERTYPE.NET_RADIOMETRY_METERTYPE_LINE){
				vector.add("(" + data.stCoordinates[0].nx + "," + data.stCoordinates[0].ny + "),(" + data.stCoordinates[1].nx + "," + data.stCoordinates[1].ny + ")");
			}else{
				switch(data.nCoordinateNum){
				case 3:
					vector.add("(" + data.stCoordinates[0].nx + "," + data.stCoordinates[0].ny + "),"
							+ "(" + data.stCoordinates[1].nx + "," + data.stCoordinates[1].ny + "),"
							+ "(" + data.stCoordinates[2].nx + "," + data.stCoordinates[2].ny + ")");
					break;
				case 4:
					vector.add("(" + data.stCoordinates[0].nx + "," + data.stCoordinates[0].ny + "),"
							+ "(" + data.stCoordinates[1].nx + "," + data.stCoordinates[1].ny + "),"
						    + "(" + data.stCoordinates[2].nx + "," + data.stCoordinates[2].ny + "),"
						    + "(" + data.stCoordinates[3].nx + "," + data.stCoordinates[3].ny + ")");
					break;
				case 5:
					vector.add("(" + data.stCoordinates[0].nx + "," + data.stCoordinates[0].ny + "),"
							+ "(" + data.stCoordinates[1].nx + "," + data.stCoordinates[1].ny + "),"
						    + "(" + data.stCoordinates[2].nx + "," + data.stCoordinates[2].ny + "),"
						    + "(" + data.stCoordinates[3].nx + "," + data.stCoordinates[3].ny + "),"
						    + "(" + data.stCoordinates[4].nx + "," + data.stCoordinates[4].ny + ")");
					break;
				case 6:
					vector.add("(" + data.stCoordinates[0].nx + "," + data.stCoordinates[0].ny + "),"
							+ "(" + data.stCoordinates[1].nx + "," + data.stCoordinates[1].ny + "),"
						    + "(" + data.stCoordinates[2].nx + "," + data.stCoordinates[2].ny + "),"
						    + "(" + data.stCoordinates[3].nx + "," + data.stCoordinates[3].ny + "),"
						    + "(" + data.stCoordinates[4].nx + "," + data.stCoordinates[4].ny + "),"
						    + "(" + data.stCoordinates[5].nx + "," + data.stCoordinates[5].ny + "),");
					break;
				case 7:
					vector.add("(" + data.stCoordinates[0].nx + "," + data.stCoordinates[0].ny + "),"
							+ "(" + data.stCoordinates[1].nx + "," + data.stCoordinates[1].ny + "),"
						    + "(" + data.stCoordinates[2].nx + "," + data.stCoordinates[2].ny + "),"
						    + "(" + data.stCoordinates[3].nx + "," + data.stCoordinates[3].ny + "),"
						    + "(" + data.stCoordinates[4].nx + "," + data.stCoordinates[4].ny + "),"
						    + "(" + data.stCoordinates[5].nx + "," + data.stCoordinates[5].ny + "),"
						    + "(" + data.stCoordinates[6].nx + "," + data.stCoordinates[6].ny + ")");
					break;
				case 8:
					vector.add("(" + data.stCoordinates[0].nx + "," + data.stCoordinates[0].ny + "),"
							+ "(" + data.stCoordinates[1].nx + "," + data.stCoordinates[1].ny + "),"
						    + "(" + data.stCoordinates[2].nx + "," + data.stCoordinates[2].ny + "),"
						    + "(" + data.stCoordinates[3].nx + "," + data.stCoordinates[3].ny + "),"
						    + "(" + data.stCoordinates[4].nx + "," + data.stCoordinates[4].ny + "),"
						    + "(" + data.stCoordinates[5].nx + "," + data.stCoordinates[5].ny + "),"
						    + "(" + data.stCoordinates[6].nx + "," + data.stCoordinates[6].ny + "),"
						    + "(" + data.stCoordinates[7].nx + "," + data.stCoordinates[7].ny + ")");
					break;
				}
			}
			
			if (data.stTemperInfo.nMeterType >= 1 && 
					data.stTemperInfo.nMeterType <= arrMeterType.length) {
				vector.add(arrMeterType[data.stTemperInfo.nMeterType-1]);
			}else {
				vector.add(Res.string().getShowInfo("UNKNOWN"));
			}
			
			if (data.stTemperInfo.nTemperUnit >= 1 && 
					data.stTemperInfo.nTemperUnit <= arrTemperUnit.length) {
				vector.add(arrTemperUnit[data.stTemperInfo.nTemperUnit-1]);
			}else {
				vector.add(Res.string().getShowInfo("UNKNOWN"));
			}
			
			vector.add(String.valueOf(data.stTemperInfo.fTemperAver));
			vector.add(String.valueOf(data.stTemperInfo.fTemperMax));
			vector.add(String.valueOf(data.stTemperInfo.fTemperMin));
			vector.add(String.valueOf(data.stTemperInfo.fTemperMid));
			vector.add(String.valueOf(data.stTemperInfo.fTemperStd));
		    
		    tableModel.addRow(vector);
		}
			
		public void clearData() {
			currentIndex = 0;
			tableModel.setRowCount(0);
			tableModel.setRowCount(QUERY_SHOW_COUNT);
			table.updateUI();
			setButtonEnable(false);
		}
		
		private JTable table = null;
		private DefaultTableModel tableModel = null;
		public JButton prePageBtn;
		public JButton nextPageBtn;
    }
    
    /**
     * 查询类型
     * */
	public enum QUERY_TYPE {
		UNKNOWN,						// 未知
		FIRST_PAGE_QUERY,				// 第一页
		PRE_PAGE_QUERY,					// 上一页
		NEXT_PAGE_QUERY					// 下一页
	};
    
    /**
	 * 查找工作线程（完成异步搜索）
	 */
	public class QuerySwingWorker extends SwingWorker<NET_OUT_RADIOMETRY_DOFIND, Object> {
		
		private QUERY_TYPE type;
		private int offset = 0;
		
		public QuerySwingWorker(QUERY_TYPE type) {
			this.type = type;
		}
		
		protected NET_OUT_RADIOMETRY_DOFIND doInBackground() {

			int currentIndex = showPanel.getIndex();
			try {
				switch(type) {
					case FIRST_PAGE_QUERY:
						ThermalCameraModule.stopFind();
						if (!ThermalCameraModule.startFind(stuStartFind)) {
							return null;
						}
						offset = 0;
						break;
					case PRE_PAGE_QUERY:
						offset = ((currentIndex-1)/QueryShowPanel.QUERY_SHOW_COUNT-1) * QueryShowPanel.QUERY_SHOW_COUNT;
						break;
					case NEXT_PAGE_QUERY:
						offset = currentIndex;
						break;
					default:
						break;
				}
				
				
				NET_OUT_RADIOMETRY_DOFIND stuDoFind = ThermalCameraModule.doFind(offset, QueryShowPanel.QUERY_SHOW_COUNT);
	
				return stuDoFind;
			}catch (Exception e) {
				System.out.println(" -------- doInBackground Exception -------- ");
			}
			return null;
		}
		
		@Override
		protected void done() {	
			
			try {

				NET_OUT_RADIOMETRY_DOFIND stuDoFind = get();
				if (stuDoFind == null) {
					JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					return;
				}
				
//				System.out.println("offset " + offset + " nFound " + stuDoFind.nFound + " Total " + ThermalCameraModule.getTotalCount());

				if (stuDoFind.nFound == 0) {
					JOptionPane.showMessageDialog(null, Res.string().getFailed(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				showPanel.setIndex(offset);
				showPanel.insertData(stuDoFind);				
			} catch (Exception e) {
//				e.printStackTrace();
			}finally {
				setSearchEnable(true);
			}
		}
	}
    
    private QueryPanel queryPanel;
	private QueryShowPanel showPanel;
}
