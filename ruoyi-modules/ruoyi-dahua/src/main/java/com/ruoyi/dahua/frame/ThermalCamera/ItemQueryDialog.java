package com.ruoyi.dahua.frame.ThermalCamera;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib.NET_RADIOMETRYINFO;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.ThermalCameraModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * 查询测温项对话框
 */
public class ItemQueryDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ItemQueryDialog() {
		setTitle(Res.string().getShowInfo("ITEM_TEMPER"));
		setLayout(new BorderLayout());
		setModal(true);
		pack();
		setSize(365, 460);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		///////////////////////////////
		queryPanel = new QueryPanel();
		showPanel = new QueryShowPanel();
		
	    add(queryPanel, BorderLayout.NORTH);
	    add(showPanel, BorderLayout.CENTER);

	}
	
	
	/**
     * 查询测温项界面
     * */
    public class QueryPanel extends JPanel {
    	/**
    	 * 
    	 */
    	private static final long serialVersionUID = 1L;
    	
		public QueryPanel() {
			BorderEx.set(this, Res.string().getShowInfo("QUERY_CONDITION"), 1);
			setLayout(new BorderLayout());			

			JLabel presetIdLabel = new JLabel(Res.string().getShowInfo("PRESET_ID"), JLabel.CENTER);
			presetIdTextField = new JTextField("1");
			JLabel ruleIdLabel = new JLabel(Res.string().getShowInfo("RULE_ID"), JLabel.CENTER);
			ruleIdTextField = new JTextField("1");
			JLabel meterTypeLabel = new JLabel(Res.string().getShowInfo("METER_TYPE"), JLabel.CENTER);
			meterTypeComboBox = new JComboBox();
			meterTypeComboBox.setModel(new DefaultComboBoxModel(Res.string().getMeterTypeList()));
			queryBtn = new JButton(Res.string().getShowInfo("QUERY"));
			
			Dimension lableDimension = new Dimension(85, 20);
			Dimension textFieldDimension = new Dimension(80, 20);
			Dimension btnDimension = new Dimension(100, 20);

			presetIdLabel.setPreferredSize(lableDimension);
			presetIdTextField.setPreferredSize(textFieldDimension);
			ruleIdLabel.setPreferredSize(lableDimension);
			ruleIdTextField.setPreferredSize(textFieldDimension);
			meterTypeLabel.setPreferredSize(lableDimension);
			meterTypeComboBox.setPreferredSize(textFieldDimension);
			JLabel label = new JLabel();
			label.setPreferredSize(new Dimension(40, 20));
			queryBtn.setPreferredSize(btnDimension);
			
			JPanel topPanel = new JPanel();
			topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 10));			
			topPanel.add(presetIdLabel);
			topPanel.add(presetIdTextField);
			topPanel.add(ruleIdLabel);
			topPanel.add(ruleIdTextField);
			
			JPanel bottomPanel = new JPanel();
			bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 10));			
			bottomPanel.add(meterTypeLabel);
			bottomPanel.add(meterTypeComboBox);
			bottomPanel.add(label);
			bottomPanel.add(queryBtn);
			
			JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			splitPane.setDividerSize(0);
			splitPane.setBorder(null);
			splitPane.add(topPanel, JSplitPane.TOP);
			splitPane.add(bottomPanel, JSplitPane.BOTTOM);
			
			add(splitPane, BorderLayout.CENTER);
			
			listener = new NumberKeyListener();
			
			presetIdTextField.addKeyListener(listener);
			ruleIdTextField.addKeyListener(listener);
			
			queryBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					queryItemInfo();
				}
			});
		}
		
		public void queryItemInfo() {
			try {
				showPanel.clearData();
				
				int nPresetId = Integer.parseInt(presetIdTextField.getText());
				int nRuleId = Integer.parseInt(ruleIdTextField.getText());
				int nMeterType = meterTypeComboBox.getSelectedIndex() + 1;
	
				NET_RADIOMETRYINFO stItemInfo = 
						ThermalCameraModule.queryItemTemper(ThermalCameraFrame.THERMAL_CHANNEL, nPresetId, nRuleId, nMeterType);
				if (stItemInfo == null) {
					JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				showPanel.updateData(stItemInfo);
				
			}catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, Res.string().getShowInfo("INPUT_ILLEGAL"), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			}
		}
		
		private NumberKeyListener listener;
		
		private JTextField presetIdTextField;
		private JTextField ruleIdTextField;
	    private JComboBox meterTypeComboBox;
		private JButton queryBtn;
    }
	
    /**
     * 查询显示界面
     * */
    public class QueryShowPanel extends JPanel {
    	/**
    	 * 
    	 */
    	private static final long serialVersionUID = 1L;

		public QueryShowPanel() {
			BorderEx.set(this, Res.string().getShowInfo("QUERY_RESULT"), 1);
			setLayout(new FlowLayout(FlowLayout.CENTER, 5, 25));
			
			JLabel meterTypeLabel = new JLabel(Res.string().getShowInfo("METER_TYPE"), JLabel.LEFT);
			meterTypeTextField = new JTextField();
			JLabel temperUnitLabel = new JLabel(Res.string().getShowInfo("TEMPER_UNIT"), JLabel.LEFT);
			temperUnitTextField = new JTextField();
			JLabel temperAverLabel = new JLabel(Res.string().getShowInfo("TEMPER_AVER"), JLabel.LEFT);
			temperAverTextField = new JTextField();
			JLabel temperMaxLabel = new JLabel(Res.string().getShowInfo("TEMPER_MAX"), JLabel.LEFT);
			temperMaxTextField = new JTextField();
			JLabel temperMinLabel = new JLabel(Res.string().getShowInfo("TEMPER_MIN"), JLabel.LEFT);
			temperMinTextField = new JTextField();
			JLabel temperMidLabel = new JLabel(Res.string().getShowInfo("TEMPER_MID"), JLabel.LEFT);
			temperMidTextField = new JTextField();
			JLabel temperStdLabel = new JLabel(Res.string().getShowInfo("TEMPER_STD"), JLabel.LEFT);
			temperStdTextField = new JTextField();
			
			Dimension lableDimension = new Dimension(120, 20);
			Dimension textFieldDimension = new Dimension(130, 20);
			meterTypeLabel.setPreferredSize(lableDimension);
			temperUnitLabel.setPreferredSize(lableDimension);
			temperAverLabel.setPreferredSize(lableDimension);
			temperMaxLabel.setPreferredSize(lableDimension);
			temperMinLabel.setPreferredSize(lableDimension);
			temperMidLabel.setPreferredSize(lableDimension);
			temperStdLabel.setPreferredSize(lableDimension);
			meterTypeTextField.setPreferredSize(textFieldDimension);
			temperUnitTextField.setPreferredSize(textFieldDimension);
			temperAverTextField.setPreferredSize(textFieldDimension);
			temperMaxTextField.setPreferredSize(textFieldDimension);
			temperMinTextField.setPreferredSize(textFieldDimension);
			temperMidTextField.setPreferredSize(textFieldDimension);
			temperStdTextField.setPreferredSize(textFieldDimension);
			
			meterTypeTextField.setEditable(false);
			temperUnitTextField.setEditable(false);
			temperAverTextField.setEditable(false);
			temperMaxTextField.setEditable(false);
			temperMinTextField.setEditable(false);
			temperMidTextField.setEditable(false);
			temperStdTextField.setEditable(false);

			add(meterTypeLabel);
			add(meterTypeTextField);
			add(temperUnitLabel);
			add(temperUnitTextField);
			add(temperMaxLabel);
			add(temperMaxTextField);
			add(temperMinLabel);
			add(temperMinTextField);
			add(temperMidLabel);
			add(temperMidTextField);
			add(temperStdLabel);
			add(temperStdTextField);
		}
		
		public void updateData(NET_RADIOMETRYINFO stItemInfo) {
			String[] data = new String[7];

			String [] arrMeterType = Res.string().getMeterTypeList();
			if (stItemInfo.nMeterType >= 1 && 
					stItemInfo.nMeterType <= arrMeterType.length) {
				data[0] = arrMeterType[stItemInfo.nMeterType-1];
			}else {
				data[0] = Res.string().getShowInfo("UNKNOWN");
			}
			
			String [] arrTemperUnit = Res.string().getTemperUnitList();
			if (stItemInfo.nTemperUnit >= 1 && 
					stItemInfo.nTemperUnit <= arrTemperUnit.length) {
				data[1] = arrTemperUnit[stItemInfo.nTemperUnit-1];
			}else {
				data[1] = Res.string().getShowInfo("UNKNOWN");
			}
			
			data[2] = String.valueOf(stItemInfo.fTemperAver);
			data[3] = String.valueOf(stItemInfo.fTemperMax);
			data[4] = String.valueOf(stItemInfo.fTemperMin);
			data[5] = String.valueOf(stItemInfo.fTemperMid);
			data[6] = String.valueOf(stItemInfo.fTemperStd);
			
			setData(data);
		}
		
		public void clearData() {
			setData(new String[7]);
		}
		
		private void setData(String[] data) {
			
			if (data.length != 7) {
				System.err.printf("data length  %d != 7", data.length);
				return;
			}

			meterTypeTextField.setText(data[0]);
			temperUnitTextField.setText(data[1]);
			temperAverTextField.setText(data[2]);
			temperMaxTextField.setText(data[3]);
			temperMinTextField.setText(data[4]);
			temperMidTextField.setText(data[5]);
			temperStdTextField.setText(data[6]);
		}
				
		private JTextField meterTypeTextField;
		private JTextField temperUnitTextField;
		private JTextField temperAverTextField;
		private JTextField temperMaxTextField;
		private JTextField temperMinTextField;
		private JTextField temperMidTextField;
		private JTextField temperStdTextField;
    }
    
	class NumberKeyListener implements KeyListener {
		
		public void keyTyped(KeyEvent e) {
			  int key = e.getKeyChar();
			  if (key < 48 || key > 57) {
				  e.consume();
			  }
		}

		public void keyPressed(KeyEvent e) {}
		
		public void keyReleased(KeyEvent e) {}
	}
	
	
	private QueryPanel queryPanel;
	private QueryShowPanel showPanel;
}