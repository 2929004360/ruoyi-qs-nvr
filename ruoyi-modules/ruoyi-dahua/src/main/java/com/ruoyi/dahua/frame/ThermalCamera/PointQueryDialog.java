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
 * 查询测温点对话框
 */
public class PointQueryDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PointQueryDialog() {
		setTitle(Res.string().getShowInfo("POINT_TEMPER"));
		setLayout(new BorderLayout());
		setModal(true);
		pack();
		setSize(350, 300);
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
     * 查询界面
     * */
    public class QueryPanel extends JPanel {
    	/**
    	 * 
    	 */
    	private static final long serialVersionUID = 1L;
    	
		public QueryPanel() {
			BorderEx.set(this, Res.string().getShowInfo("QUERY_CONDITION"), 1);
			setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));			

			JLabel XLabel = new JLabel(Res.string().getShowInfo("X"), JLabel.CENTER);
			XTextField = new JTextField("0");
			JLabel YLabel = new JLabel(Res.string().getShowInfo("Y"), JLabel.CENTER);
			YTextField = new JTextField("0");
			queryBtn = new JButton(Res.string().getShowInfo("QUERY"));
			
			Dimension lableDimension = new Dimension(10, 20);
			Dimension textFieldDimension = new Dimension(70, 20);
			Dimension btnDimension = new Dimension(100, 20);

			XLabel.setPreferredSize(lableDimension);
			XTextField.setPreferredSize(textFieldDimension);
			YLabel.setPreferredSize(lableDimension);
			YTextField.setPreferredSize(textFieldDimension);
			queryBtn.setPreferredSize(btnDimension);
			
			add(XLabel);
			add(XTextField);
			add(YLabel);
			add(YTextField);
			add(queryBtn);
			
			listener = new NumberKeyListener();
			XTextField.addKeyListener(listener);
			YTextField.addKeyListener(listener);
			
			queryBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					queryPointInfo();
				}
			});
		}
		
		private void queryPointInfo() {
			try {
				showPanel.clearData();
				
				short x = Short.parseShort(XTextField.getText());
				short y = Short.parseShort(YTextField.getText());
	
				NET_RADIOMETRYINFO pointInfo = 
						ThermalCameraModule.queryPointTemper(ThermalCameraFrame.THERMAL_CHANNEL, x, y);
				if (pointInfo == null) {
					JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				showPanel.updateData(pointInfo);
				
			}catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, Res.string().getShowInfo("COORDINATE_ILLEGAL"), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			}
		}
		
		private NumberKeyListener listener;
		
		private JTextField XTextField;
		private JTextField YTextField;
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
			setLayout(new FlowLayout(FlowLayout.CENTER, 5, 30));
			
			JLabel meterTypeLabel = new JLabel(Res.string().getShowInfo("METER_TYPE"), JLabel.LEFT);
			meterTypeTextField = new JTextField();
			JLabel temperUnitLabel = new JLabel(Res.string().getShowInfo("TEMPER_UNIT"), JLabel.LEFT);
			temperUnitTextField = new JTextField();
			JLabel temperLabel = new JLabel(Res.string().getShowInfo("TEMPER"), JLabel.LEFT);
			temperTextField = new JTextField();
			
			Dimension lableDimension = new Dimension(100, 20);
			Dimension textFieldDimension = new Dimension(130, 20);
			meterTypeLabel.setPreferredSize(lableDimension);
			temperUnitLabel.setPreferredSize(lableDimension);
			temperLabel.setPreferredSize(lableDimension);
			meterTypeTextField.setPreferredSize(textFieldDimension);
			temperUnitTextField.setPreferredSize(textFieldDimension);
			temperTextField.setPreferredSize(textFieldDimension);
			
			meterTypeTextField.setEditable(false);
			temperUnitTextField.setEditable(false);
			temperTextField.setEditable(false);

			add(meterTypeLabel);
			add(meterTypeTextField);
			add(temperUnitLabel);
			add(temperUnitTextField);
			add(temperLabel);
			add(temperTextField);
		}
		
		public void updateData(NET_RADIOMETRYINFO stPointInfo) {
			String[] data = new String[3];
			
			String [] arrMeterType = Res.string().getMeterTypeList();
			if (stPointInfo.nMeterType >= 1 && 
					stPointInfo.nMeterType <= arrMeterType.length) {
				data[0] = arrMeterType[stPointInfo.nMeterType-1];
			}else {
				data[0] = Res.string().getShowInfo("UNKNOWN");
			}
			
			String [] arrTemperUnit = Res.string().getTemperUnitList();
			if (stPointInfo.nTemperUnit >= 1 && 
					stPointInfo.nTemperUnit <= arrTemperUnit.length) {
				data[1] = arrTemperUnit[stPointInfo.nTemperUnit-1];
			}else {
				data[1] = Res.string().getShowInfo("UNKNOWN");
			}
			
			data[2] = String.valueOf(stPointInfo.fTemperAver);
			
			setData(data);
		}
		
		public void clearData() {
			setData(new String[3]);
		}
		
		private void setData(String[] data) {
			
			if (data.length != 3) {
				System.err.printf("data length  %d != 3", data.length);
				return;
			}

			meterTypeTextField.setText(data[0]);
			temperUnitTextField.setText(data[1]);
			temperTextField.setText(data[2]);
		}
				
		private JTextField meterTypeTextField;
		private JTextField temperUnitTextField;
		private JTextField temperTextField;
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