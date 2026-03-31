package com.ruoyi.dahua.frame.ThermalCamera;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.ImageAlgLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.NetSDKLib.NET_RADIOMETRY_DATA;
import com.ruoyi.dahua.lib.NetSDKLib.fRadiometryAttachCB;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.LoginModule;
import com.ruoyi.dahua.module.ThermalCameraModule;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 热图信息对话框
 */
public class HeatMapDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDialog target = this;
	private ReentrantLock lock = new ReentrantLock();
	private NET_RADIOMETRY_DATA gData = new NET_RADIOMETRY_DATA();


	public HeatMapDialog() {
		setTitle(Res.string().getShowInfo("HEATMAP"));
		setLayout(new BorderLayout());
		setModal(true);
		pack();
		setSize(400, 440);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		///////////////////////////////
		operatePanel = new OperatePanel();
		showPanel = new HeatMapShowPanel();
		
	    add(showPanel, BorderLayout.CENTER);
	    add(operatePanel, BorderLayout.NORTH);

	    addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				try {
					ThermalCameraModule.radiometryDetach();
				}finally {
					dispose();
				}
			}
		});
	}


	/**
	 * 订阅回调
	 */
	private RadiometryAttachCB cbNotify = new RadiometryAttachCB();
	private class RadiometryAttachCB implements fRadiometryAttachCB {
		
		@Override
		public void invoke(LLong lAttachHandle, final NET_RADIOMETRY_DATA pBuf,
				int nBufLen, Pointer dwUser) {

			copyRadiometryData(pBuf);
			SwingUtilities.invokeLater(new Runnable() {
    			public void run() {
    				target.setTitle(Res.string().getShowInfo("HEATMAP"));
    				operatePanel.saveBtn.setEnabled(true);
    				showPanel.updateData();
    			}
    		});
		}					
	}


	static ImageAlgLib imageAlgApi = ImageAlgLib.IMAGEALG_INSTANCE;

	private void copyRadiometryData(NET_RADIOMETRY_DATA data) {
		lock.lock();
		gData.stMetaData = data.stMetaData;
		gData.dwBufSize = data.dwBufSize;
		gData.pbDataBuf = new Memory(data.dwBufSize);
		gData.pbDataBuf.write(0, data.pbDataBuf.getByteArray(0, data.dwBufSize), 0, data.dwBufSize);
		lock.unlock();
	}
	
	/**
     * 操作界面
     * */
    public class OperatePanel extends JPanel {
    	/**
    	 * 
    	 */
    	private static final long serialVersionUID = 1L;
    	
		public OperatePanel() {
			BorderEx.set(this, Res.string().getShowInfo("HEATMAP_OPERATE"), 1);
			setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));			
			
			attachBtn = new JButton(Res.string().getShowInfo("RADIOMETRY_ATTACH"));
			fetchBtn = new JButton(Res.string().getShowInfo("RADIOMETRY_FETCH"));
			saveBtn = new JButton(Res.string().getShowInfo("SAVE_HEATMAP"));

			Dimension btnDimension = new Dimension(120, 20);
			attachBtn.setPreferredSize(btnDimension);
			fetchBtn.setPreferredSize(btnDimension);
			saveBtn.setPreferredSize(btnDimension);
			
			fetchBtn.setEnabled(false);
			saveBtn.setEnabled(false);
			
			add(attachBtn);
			add(fetchBtn);
			add(saveBtn);
						
			attachBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
		    			public void run() {
							attach();
		    			}
		    		});
				}
			});
			
			fetchBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
		    			public void run() {
		    				fetch();
		    			}
		    		});
				}
			});	
			
			saveBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
		    			public void run() {
		    				try {
								save();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		    			}
		    		});
				}
			});	
		}
		
		public void attach() {
			
			freeMemory();
			
			target.setTitle(Res.string().getShowInfo("HEATMAP"));
			if (ThermalCameraModule.isAttaching()) {
				ThermalCameraModule.radiometryDetach();
				fetchBtn.setEnabled(false);
				saveBtn.setEnabled(false);
				attachBtn.setText(Res.string().getShowInfo("RADIOMETRY_ATTACH"));
			}else {
				if (ThermalCameraModule.radiometryAttach(ThermalCameraFrame.THERMAL_CHANNEL, cbNotify)) {
					attachBtn.setText(Res.string().getShowInfo("RADIOMETRY_DETACH"));
					showPanel.clearData();
					fetchBtn.setEnabled(true);
				}else {
					JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		public void fetch() {
			
			freeMemory();
			saveBtn.setEnabled(false);
			int nStatus = ThermalCameraModule.radiometryFetch(ThermalCameraFrame.THERMAL_CHANNEL);
			
			if (nStatus != -1) {
				showPanel.clearData();
				String[] arrStatus = Res.string().getTemperStatusList();
				if (nStatus >= 1 && nStatus <= arrStatus.length) {
					target.setTitle(Res.string().getShowInfo("HEATMAP") + " : " + arrStatus[nStatus-1]);
				}
			}else {
				JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			}		
		}
		
		public void save() throws IOException {
			lock.lock();
			float maxTemp = 0f;
			float minTemp = 0f;
			if (gData == null) {
				JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			}

			int nWidth = gData.stMetaData.nWidth;
			int nHeight = gData.stMetaData.nHeight;

			short[] pGrayImg = new short[nWidth * nHeight];
			float[] pTempForPixels = new float[nWidth * nHeight];

			if(LoginModule.netsdk.CLIENT_RadiometryDataParse(gData, pGrayImg, pTempForPixels)) {
				byte[] pYData = new byte[nWidth*nHeight*2];
				imageAlgApi.drcTable(pGrayImg, (short)nWidth, (short)nHeight, 0, pYData, null);
				ToolKits.savePicture(pYData, "./GrayscaleMap.yuv");
				Arrays.sort(pTempForPixels);
				minTemp = pTempForPixels[0];
				maxTemp = pTempForPixels[nWidth * nHeight-1];
				StringBuffer buff = new StringBuffer();
				buff.append(Res.string().getShowInfo("HEATMAP_SAVE_SUCCESS")).append("\n");
				buff.append(Res.string().getShowInfo("TEMPER_MAX")).append(":").append(maxTemp).append("\n");
				buff.append(Res.string().getShowInfo("TEMPER_MIN")).append(":").append(minTemp).append("\n");
				JOptionPane.showMessageDialog(null, buff.toString() , Res.string().getPromptMessage(), JOptionPane.PLAIN_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
			}
			lock.unlock();
		}
		
		public void freeMemory() {
			lock.lock();
			if (gData.pbDataBuf != null) {	
				Native.free(Pointer.nativeValue(gData.pbDataBuf));
				Pointer.nativeValue(gData.pbDataBuf, 0);
			}
			lock.unlock();
		}
		
		private JButton attachBtn;
		private JButton fetchBtn;
		private JButton saveBtn;
    }
	
    /**
     * 查询显示界面
     * */
    public class HeatMapShowPanel extends JPanel {
    	/**
    	 * 
    	 */
    	private static final long serialVersionUID = 1L;

		public HeatMapShowPanel() {
			BorderEx.set(this, Res.string().getShowInfo("HEATMAP_METADATA_INFO"), 1);
			setLayout(new FlowLayout(FlowLayout.CENTER, 15, 25));
			
			JLabel heightLabel = new JLabel(Res.string().getShowInfo("HEIGHT"), JLabel.LEFT);
			heightTextField = new JTextField();
			JLabel widthLabel = new JLabel(Res.string().getShowInfo("WIDTH"), JLabel.LEFT);
			widthTextField = new JTextField();
			JLabel channelLabel = new JLabel(Res.string().getShowInfo("CHANNEL"), JLabel.LEFT);
			channelTextField = new JTextField();
			JLabel timeLabel = new JLabel(Res.string().getShowInfo("TIME"), JLabel.LEFT);
			timeTextField = new JTextField();
			JLabel lengthLabel = new JLabel(Res.string().getShowInfo("LENGTH"), JLabel.LEFT);
			lengthTextField = new JTextField();
			JLabel sensorTypeLabel = new JLabel(Res.string().getShowInfo("SENSOR_TYPE"), JLabel.LEFT);
			sensorTypeTextField = new JTextField();
			
			Dimension lableDimension = new Dimension(100, 20);
			Dimension textFieldDimension = new Dimension(140, 20);
			heightLabel.setPreferredSize(lableDimension);
			widthLabel.setPreferredSize(lableDimension);
			channelLabel.setPreferredSize(lableDimension);
			timeLabel.setPreferredSize(lableDimension);
			lengthLabel.setPreferredSize(lableDimension);
			sensorTypeLabel.setPreferredSize(lableDimension);
			heightTextField.setPreferredSize(textFieldDimension);
			widthTextField.setPreferredSize(textFieldDimension);
			channelTextField.setPreferredSize(textFieldDimension);
			timeTextField.setPreferredSize(textFieldDimension);
			lengthTextField.setPreferredSize(textFieldDimension);
			sensorTypeTextField.setPreferredSize(textFieldDimension);
			
			heightTextField.setEditable(false);
			widthTextField.setEditable(false);
			channelTextField.setEditable(false);
			timeTextField.setEditable(false);
			lengthTextField.setEditable(false);
			sensorTypeTextField.setEditable(false);

			add(heightLabel);
			add(heightTextField);
			add(widthLabel);
			add(widthTextField);
			add(channelLabel);
			add(channelTextField);
			add(timeLabel);
			add(timeTextField);
			add(lengthLabel);
			add(lengthTextField);
			add(sensorTypeLabel);
			add(sensorTypeTextField);
		}
		
		public void updateData() {
			String[] data = new String[6];

			lock.lock();
			data[0] = String.valueOf(gData.stMetaData.nHeight);
			data[1] = String.valueOf(gData.stMetaData.nWidth);
			data[2] = String.valueOf(gData.stMetaData.nChannel+1);
			data[3] = gData.stMetaData.stTime.toStringTimeEx();
			data[4] = String.valueOf(gData.stMetaData.nLength);
			try {
				data[5] = new String(gData.stMetaData.szSensorType, "GBK").trim();
			} catch (UnsupportedEncodingException e) {
				data[5] = new String(gData.stMetaData.szSensorType).trim();
			}
			lock.unlock();
			
			setData(data);
		}
		
		public void clearData() {
			setData(new String[6]);
		}
		
		private void setData(String[] data) {
			
			if (data.length != 6) {
				System.err.printf("data length  %d != 6", data.length);
				return;
			}

			heightTextField.setText(data[0]);
			widthTextField.setText(data[1]);
			channelTextField.setText(data[2]);
			timeTextField.setText(data[3]);
			lengthTextField.setText(data[4]);
			sensorTypeTextField.setText(data[5]);
		}

		private JTextField heightTextField;
		private JTextField widthTextField;
		private JTextField channelTextField;
		private JTextField timeTextField;
		private JTextField lengthTextField;
		private JTextField sensorTypeTextField;
    }
	
	
	private OperatePanel operatePanel;
	private HeatMapShowPanel showPanel;
}
