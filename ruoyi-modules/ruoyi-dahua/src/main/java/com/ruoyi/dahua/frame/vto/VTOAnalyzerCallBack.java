package com.ruoyi.dahua.frame.vto;

import com.ruoyi.dahua.common.PaintPanel;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.common.SavePath;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.ToolKits;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static com.ruoyi.dahua.lib.NetSDKLib.EVENT_IVS_ACCESS_CTL;

/**
 * @author 47081
 * @version 1.0
 * @description vto智能分析的回调函数, 建议写成单例模式
 * 对应接口 {@link NetSDKLib#CLIENT_RealLoadPictureEx(NetSDKLib.LLong, int, int, int, Callback, Pointer, Pointer)}
 * @date 2020/8/15
 */
public class VTOAnalyzerCallBack implements NetSDKLib.fAnalyzerDataCallBack {
    private static VTOAnalyzerCallBack INSTANCE;
    private JTable table;
    private PaintPanel paintPanel;
    private BufferedImage bufferedImage;

    private VTOAnalyzerCallBack(JTable table,PaintPanel panel) {
        this.table = table;
        this.paintPanel=panel;
    }

    public static VTOAnalyzerCallBack getINSTANCE(JTable table,PaintPanel paintPanel) {
        if (INSTANCE == null) {
            INSTANCE = new VTOAnalyzerCallBack(table,paintPanel);
        }
        return INSTANCE;
    }

    @Override
    public int invoke(NetSDKLib.LLong lAnalyzerHandle, int dwAlarmType, Pointer pAlarmInfo, Pointer pBuffer, int dwBufSize, Pointer dwUser, int nSequence, Pointer reserved) {
        //门禁事件
        if (dwAlarmType == EVENT_IVS_ACCESS_CTL) {
            NetSDKLib.DEV_EVENT_ACCESS_CTL_INFO info = new NetSDKLib.DEV_EVENT_ACCESS_CTL_INFO();
            ToolKits.GetPointerDataToStruct(pAlarmInfo, 0, info);
            //更新列表
            if(table!=null){
                DefaultTableModel model= (DefaultTableModel) table.getModel();
                NetSDKLib.NET_TIME_EX time=info.UTC;
                if(info.UTC.dwYear==0&&info.UTC.dwMonth==0&&info.UTC.dwDay==0){
                    time=info.stuFileInfo.stuFileTime;
                }
                model.addRow(new Object[]{new String(info.szUserID).trim(),new String(info.szCardNo).trim(),time.toString().trim(),getEventInfo(info).trim()});
            }
            if(pBuffer != null && dwBufSize > 0) {
                String strFileName = SavePath.getSavePath().getSaveCapturePath();
                byte[] buf = pBuffer.getByteArray(0, dwBufSize);
                ByteArrayInputStream byteArrInput = new ByteArrayInputStream(buf);
                try {
                    bufferedImage = ImageIO.read(byteArrInput);
                    if (bufferedImage == null) {
                        return 0;
                    }
                    ImageIO.write(bufferedImage, "jpg", new File(strFileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 界面显示抓图
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        paintPanel.setOpaque(false);
                        paintPanel.setImage(bufferedImage);
                        paintPanel.repaint();
                    }
                });
            }
        }
        return 1;
    }

    /**
     * 获取事件信息
     * @param info
     * @return
     */
    private String getEventInfo(NetSDKLib.DEV_EVENT_ACCESS_CTL_INFO info){
        StringBuilder builder=new StringBuilder();
        builder.append(Res.string().getChannel()).append(info.nChannelID).append(",")
                .append(Res.string().getOpenMethod()).append(openDoorMethod(info.emOpenMethod)).append(",")
                .append(Res.string().getOpenStatus()).append(info.bStatus==1?Res.string().getSucceed():Res.string().getFailed());
        return builder.toString();
    }
    /**
     * 开门方式
     * @param emOpenMethod
     * @return
     */
    private String openDoorMethod(int emOpenMethod) {
        String method;
        switch (emOpenMethod) {
            case NetSDKLib.NET_ACCESS_DOOROPEN_METHOD.NET_ACCESS_DOOROPEN_METHOD_CARD:
                method = Res.string().getCard();
                break;
            case NetSDKLib.NET_ACCESS_DOOROPEN_METHOD.NET_ACCESS_DOOROPEN_METHOD_FACE_RECOGNITION:
                method = Res.string().getTargetRecognition();
                break;
            case NetSDKLib.NET_ACCESS_DOOROPEN_METHOD.NET_ACCESS_DOOROPEN_METHOD_FINGERPRINT:
                method = Res.string().getFingerPrint();
                break;
            case NetSDKLib.NET_ACCESS_DOOROPEN_METHOD.NET_ACCESS_DOOROPEN_METHOD_REMOTE:
                method = Res.string().getRemoteCapture();
                break;
            default:
                method = Res.string().getUnKnow();
                break;
        }
        return method;
    }
}
