package com.ruoyi.dahua.frame.scada;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.Utils;
import com.sun.jna.Pointer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.charset.Charset;

/**
 * className：SCADAAlarmAttachInfoCallBack
 * description：动环主机：智能事件回调函数 订阅监测点报警信息回调
 * author：251589
 * createTime：2021/4/10 16:54
 *
 * @version v1.0
 */

public class SCADAAlarmAttachInfoCallBack implements NetSDKLib.fSCADAAlarmAttachInfoCallBack {
    // 多平台 编码
    private final Charset encode = Charset.forName(Utils.getPlatformEncode());
    private static SCADAAlarmAttachInfoCallBack INSTANCE;
    private JTable table;

    @Override
    public void invoke(NetSDKLib.LLong lAttachHandle,
                       NetSDKLib.NET_SCADA_NOTIFY_POINT_ALARM_INFO_LIST pInfo, int nBufLen,
                       Pointer dwUser) {

        System.out.println("————————————————————【订阅监测点报警信息回调】————————————————————");
        //更新列表
        if (table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < pInfo.nList; i++) {
                System.out.println(" 设备ID:" + new String(pInfo.stuList[i].szDevID).trim());
                System.out.println(" 点位ID:" + new String(pInfo.stuList[i].szPointID).trim());
                String alarmDesc = new String(pInfo.stuList[i].szAlarmDesc, encode).trim();
                System.out.println(" 报警描述:" + alarmDesc);

                System.out.println(" 报警标志:" + (pInfo.stuList[i].bAlarmFlag == 1));
                System.out.println(" 报警时间:" + pInfo.stuList[i].stuAlarmTime.toStringTime());
                System.out.println(" 报警级别(0~6):" + pInfo.stuList[i].nAlarmLevel);
                System.out.println(" 报警编号(同一个告警的开始和结束的编号是相同的):" + pInfo.stuList[i].nSerialNo);
                model.addRow(new Object[]{new String(pInfo.stuList[i].szDevID).trim(), new String(pInfo.stuList[i].szPointID).trim(), alarmDesc, pInfo.stuList[i].stuAlarmTime.toStringTime(), pInfo.stuList[i].nAlarmLevel});
            }
        }
        System.out.println("————————————————————【订阅监测点报警信息回调】————————————————————");
    }

    private SCADAAlarmAttachInfoCallBack(JTable table) {
        this.table = table;
    }

    public static SCADAAlarmAttachInfoCallBack getINSTANCE(JTable table) {
        if (INSTANCE == null) {
            INSTANCE = new SCADAAlarmAttachInfoCallBack(table);
        }
        if (table != null) {
            INSTANCE.table = table;
        }

        return INSTANCE;
    }

}
