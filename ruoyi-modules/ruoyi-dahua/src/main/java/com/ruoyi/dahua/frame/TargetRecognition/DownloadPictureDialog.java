package com.ruoyi.dahua.frame.TargetRecognition;

import com.ruoyi.dahua.common.PaintPanel;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.TargetRecognitionModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.concurrent.ExecutionException;

public class DownloadPictureDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DownloadPictureDialog() {
	    setTitle(Res.string().getDownLoadPicture());
	    setLayout(new BorderLayout());
	    setModal(true);  
	    pack();
	    setSize(380, 500);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);   // 释放窗体
	    
	    JLabel picPathLabel = new JLabel(Res.string().getPicturePath()+":", JLabel.CENTER);
	    picPathTextField = new JTextField();
	    downloadBth = new JButton(Res.string().getDownLoadPicture());
	    
	    picPathTextField.setPreferredSize(new Dimension(180, 20));
	    downloadBth.setPreferredSize(new Dimension(80, 20));
	    
	    paintPanel = new PaintPanel();
	    paintPanel.setPreferredSize(new Dimension(360, 450));
	    
	    setLayout(new FlowLayout());
	    add(picPathLabel);
	    add(picPathTextField);
	    add(downloadBth);
	    add(paintPanel);
	    
	    downloadBth.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {				
					@Override
					public void run() {
						paintPanel.setOpaque(true);
						paintPanel.repaint();
						downloadBth.setEnabled(false);
					}
				});
				
				downloadPicture();
			}
		});
	    
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		dispose();
	    	}
	    });
	}
	
	private void downloadPicture() {
		new SwingWorker<Boolean, Integer>() {	
			String szFileName = "";   		       // 要下载的图片路径
			String pszFileDst = "";    			   // 保存图片的路径
			
			@Override
			protected Boolean doInBackground() {
				if(picPathTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, Res.string().getEnterPicturePath(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					return false;
				}
				
				szFileName = picPathTextField.getText();    
				pszFileDst = "./person.jpg";    			  
				
				File file = new File(pszFileDst);
				if(file.exists()) {
					file.delete();
				}
				
				if(!TargetRecognitionModule.downloadPersonPic(szFileName, pszFileDst)) {
					return false;
				}

				return true;
			}	
			
			@Override
			protected void done() {
				if(picPathTextField.getText().isEmpty()) {
					return;
				}
				
				try {
					if(get()) {				
						// 下载成功后，面板上显示下载的图片
						ToolKits.openPictureFile(pszFileDst, paintPanel);	
					} else {
						JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
					}
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				downloadBth.setEnabled(true);
			}
		}.execute();
	}
	
	private JTextField picPathTextField;
	private PaintPanel paintPanel;
	private JButton downloadBth;
}
