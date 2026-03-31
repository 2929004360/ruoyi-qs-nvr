package com.ruoyi.dahua.frame.TargetRecognition;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.DateChooserJButton;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib.MEDIAFILE_FACERECOGNITION_INFO;
import com.ruoyi.dahua.module.LoginModule;
import com.ruoyi.dahua.module.TargetRecognitionModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

/**
 * 查找人脸事件的信息记录
 */
public class FindFaceEventRecordDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Vector<String> chnList = new Vector<String>(); 
	
	public FindFaceEventRecordDialog(){
		setTitle("查找人脸事件的信息记录");
		setLayout(new BorderLayout());
	    setModal(true);   
		pack();
		setSize(750, 430);
	    setResizable(false);
	    setLocationRelativeTo(null); 
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);   // 释放窗体	
		
		FaceEventRecordPanel faceRecordPanel = new FaceEventRecordPanel();
        add(faceRecordPanel, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
	}
	
	public class FaceEventRecordPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public FaceEventRecordPanel() {
			BorderEx.set(this, "", 4);
			setLayout(new BorderLayout());
			
			JPanel panel1 = new JPanel();	
			JPanel panel2 = new JPanel();	
			add(panel1, BorderLayout.NORTH);
			add(panel2, BorderLayout.CENTER);
			
			//
			JLabel chnlabel = new JLabel(Res.string().getChannel());
			chnComboBox = new JComboBox(); 
			for(int i = 1; i < LoginModule.m_stDeviceInfo.byChanNum + 1; i++) {
				chnList.add(Res.string().getChannel() + " " + String.valueOf(i));
			}
			
			// 登陆成功，将通道添加到控件
			chnComboBox.setModel(new DefaultComboBoxModel(chnList));
			chnComboBox.setPreferredSize(new Dimension(80, 20));
			
			JLabel startLabel = new JLabel(Res.string().getStartTime());
			startTimeBtn = new DateChooserJButton("2018-10-30 11:11:11");
			
			JLabel endLabel = new JLabel(Res.string().getEndTime());
			endTimeBtn = new DateChooserJButton();
			
			searchBtn = new JButton(Res.string().getSearch());
			searchBtn.setPreferredSize(new Dimension(70, 20));
			
			JButton downloadBth = new JButton("下载查询到的图片");
			downloadBth.setPreferredSize(new Dimension(140, 20));
			
			msgTextArea = new JTextArea();
			
			Dimension dimension1 = new Dimension();
			dimension1.width = 130;
			dimension1.height = 20;
			startTimeBtn.setPreferredSize(dimension1);
			endTimeBtn.setPreferredSize(dimension1);
			
			panel1.setLayout(new FlowLayout());
			panel1.add(chnlabel);
			panel1.add(chnComboBox);
			panel1.add(startLabel);
			panel1.add(startTimeBtn);
			panel1.add(endLabel);
			panel1.add(endTimeBtn);
			panel1.add(searchBtn);
			panel1.add(downloadBth);
			
			panel2.setLayout(new BorderLayout());
			panel2.add(new JScrollPane(msgTextArea), BorderLayout.CENTER);

			searchBtn.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {					
						@Override
						public void run() {
							searchBtn.setEnabled(false);
						}
					});
					findEventInfo();
				}
			});
			
			downloadBth.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					DownloadPictureDialog dialog = new DownloadPictureDialog();
					dialog.setVisible(true);
				}
			});
		}	
	}
	
	
	private JComboBox chnComboBox;
	private DateChooserJButton startTimeBtn;
	private DateChooserJButton endTimeBtn;
	private JTextArea msgTextArea;
	private JButton searchBtn;
	
	public void findEventInfo() {
		new SwingWorker<Boolean, StringBuffer>() {					
			@Override
			protected Boolean doInBackground() {
				int count = 0;  	  // 循环查询了几次	
				int index = 0;	 	  // index + 1 为查询到的总个数    
				int nFindCount = 10;  // 每次查询的个数
				StringBuffer message = null;
				msgTextArea.setText("");
		
				// 获取查询句柄
				if(!TargetRecognitionModule.findFile(chnComboBox.getSelectedIndex(), startTimeBtn.getText(), endTimeBtn.getText())) {
					message = new StringBuffer();
					message.append("未查询到相关信息");
					publish(message);
					return false;
				}			
		
				// 查询具体信息, 循环查询， nFindCount为每次查询的个数
				while(true) {
					MEDIAFILE_FACERECOGNITION_INFO[] msg = TargetRecognitionModule.findNextFile(nFindCount);
					if(msg == null) {
						message = new StringBuffer();
						message.append("查询结束!");
						publish(message);
						break;
					}
					
					for(int i = 0; i < msg.length; i++) {
						index = i + count * nFindCount + 1;		
	
						// 清空
						message = new StringBuffer();
							
						message.append("[" + index + "]通道号 :" + msg[i].nChannelId + "\n");
						message.append("[" + index + "]报警发生时间 :" + msg[i].stTime.toStringTime() + "\n");
						message.append("[" + index + "]全景图 :" + new String(msg[i].stGlobalScenePic.szFilePath).trim() + "\n");
						message.append("[" + index + "]人脸图路径 :" + new String(msg[i].stObjectPic.szFilePath).trim() + "\n");
						message.append("[" + index + "]匹配到的候选对象数量 :" + msg[i].nCandidateNum + "\n");		
						
						for(int j = 0; j < msg[i].nCandidateNum; j++) {  
							for(int k = 0; k < msg[i].stuCandidatesPic[j].nFileCount; k++) {
								message.append("[" + index + "]对比图路径 :" + new String(msg[i].stuCandidatesPic[j].stFiles[k].szFilePath).trim() + "\n");
							}	
						}	

						message.append("[" + index + "]匹配到的候选对象数量 :" + msg[i].nCandidateExNum + "\n");
						
						// 对比信息   
						for(int j = 0; j < msg[i].nCandidateExNum; j++) {  
							message.append("[" + index + "]人员唯一标识符 :" + new String(msg[i].stuCandidatesEx[j].stPersonInfo.szUID).trim() + "\n");
							
							// 以下参数，设备有些功能没有解析，如果想要知道   对比图的人员信息，可以根据上面获取的 szUID，来查询人员信息。
							// findFaceRecognitionDB() 此示例的方法是根据 GroupId来查询的，这里的查询，GroupId不填，根据 szUID 来查询
							message.append("[" + index + "]姓名 :" + new String(msg[i].stuCandidatesEx[j].stPersonInfo.szPersonName).trim() + "\n");
							message.append("[" + index + "]相似度 :" + msg[i].stuCandidatesEx[j].bySimilarity + "\n");
							message.append("[" + index + "]年龄 :" + msg[i].stuCandidatesEx[j].stPersonInfo.byAge + "\n");
							message.append("[" + index + "]人脸库名称 :" + new String(msg[i].stuCandidatesEx[j].stPersonInfo.szGroupName).trim() + "\n");
							message.append("[" + index + "]人脸库ID :" + new String(msg[i].stuCandidatesEx[j].stPersonInfo.szGroupID).trim() + "\n");
						}
						message.append("\n");
						publish(message);
					}
		
					if (msg.length < nFindCount) {
						message = new StringBuffer();
						message.append("查询结束!");
						publish(message);
						break;
					} else {
						count ++;
					}
				}
				
				// 关闭查询接口
				TargetRecognitionModule.findCloseFile();

				return true;
			}
			
			@Override
			protected void process(java.util.List<StringBuffer> chunks) {
				for(StringBuffer data : chunks) {
			        msgTextArea.append(data.toString());
			        msgTextArea.updateUI();
				}
				
				super.process(chunks);
			}
			
			@Override
			protected void done() {	
				searchBtn.setEnabled(true);
			}
		}.execute();
	}
}
