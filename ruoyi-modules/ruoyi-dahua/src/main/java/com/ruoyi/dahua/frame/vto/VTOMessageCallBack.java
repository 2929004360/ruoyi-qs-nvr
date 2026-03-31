package com.ruoyi.dahua.frame.vto;

import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.ToolKits;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author 47081
 * @version 1.0
 * @description vto监听事件的回调函数, 建议写成单例模式
 * @date 2020/8/15
 */
public class VTOMessageCallBack implements NetSDKLib.fMessCallBack {
    private static VTOMessageCallBack INSTANCE;
    private JTable table;
    private CollectionFingerPrint print;

    private VTOMessageCallBack(JTable table) {
        this.table = table;
    }

    public static VTOMessageCallBack getINSTANCE(JTable table, CollectionFingerPrint print) {
        if (INSTANCE == null) {
            INSTANCE = new VTOMessageCallBack(table);
        }
        if (table != null) {
            INSTANCE.table = table;
        }
        if (print != null) {
            INSTANCE.print = print;
        }
        return INSTANCE;
    }

    @Override
    public boolean invoke(int lCommand, NetSDKLib.LLong lLoginID, Pointer pStuEvent, int dwBufLen, String strDeviceIP, NativeLong nDevicePort, Pointer dwUser) {
        //门禁事件
        if (lCommand == NetSDKLib.NET_ALARM_ACCESS_CTL_EVENT) {
            NetSDKLib.ALARM_ACCESS_CTL_EVENT_INFO info = new NetSDKLib.ALARM_ACCESS_CTL_EVENT_INFO();
            ToolKits.GetPointerDataToStruct(pStuEvent, 0, info);
            //更新列表
            if (table != null) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{new String(info.szUserID).trim(), new String(info.szCardNo).trim(), info.stuTime.toStringTime(), openDoorMethod(info.emOpenMethod), info.bStatus == 1 ? Res.string().getSucceed() : Res.string().getFailed()});

            }
        }
        //信息事件
        if (lCommand == NetSDKLib.NET_ALARM_FINGER_PRINT) {
            if (print != null) {
                if (lLoginID.longValue() == print.getLoginHandler()) {
                    NetSDKLib.ALARM_CAPTURE_FINGER_PRINT_INFO info = new NetSDKLib.ALARM_CAPTURE_FINGER_PRINT_INFO();
                    ToolKits.GetPointerDataToStruct(pStuEvent, 0, info);
                    print.setCollectionResult(info.bCollectResult == 1);
                    if (info.bCollectResult == 1) {
                        print.setPackageLen(info.nPacketLen * info.nPacketNum);
                        int length = info.nPacketLen * info.nPacketNum;
                        byte[] data = new byte[length];
                        info.szFingerPrintInfo.read(0, data, 0, length);
                        print.setPackageData(data);
                        //显示结果
                        print.setLabelResult(data);
                    }
                    print.stopListen();
                }
            }
        }
        return true;
    }

    /**
     * 开门方式
     *
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
                method = Res.string().getRemoteOpenDoor();
                break;
            default:
                method = Res.string().getUnKnow();
                break;
        }
        return method;
    }
}
