package com.ruoyi.haikang.callback;

import com.ruoyi.haikang.net.HCNetSDK;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FMSGCallBack_V31 implements HCNetSDK.FMSGCallBack_V31 {

    @Override
    public boolean invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
        try {
            AlarmDataParse.alarmDataHandle(lCommand, pAlarmer, pAlarmInfo, dwBufLen, pUser);
        } catch (Exception e) {
            log.error("处理报警信息异常", e);
        }
        return true;
    }
}
