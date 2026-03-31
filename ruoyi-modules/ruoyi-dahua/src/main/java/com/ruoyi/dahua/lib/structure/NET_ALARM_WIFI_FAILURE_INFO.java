package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description  wifi故障上报事件 (对应 DH_ALARM_WIFI_FAILURE)
 * @date 2022/07/25 15:29:53
 */
public class NET_ALARM_WIFI_FAILURE_INFO extends NetSDKLib.SdkStructure {
    /**
     事件动作, 1:开始 2:停止
     */
    public			int            nAction;
    /**
     事件发生的时间,标准的（不带时区偏差的）UTC时间
     */
    public			NET_TIME_EX    stuUTC = new NET_TIME_EX();
    /**
     配件序列号
     */
    public			byte[]         szSN = new byte[32];
    /**
     配件名称
     */
    public			byte[]         szName = new byte[64];
    /**
     wifi故障错误码 1:未知错误;2:无效的网络名称;3:无效的网络口令;4:网络故障
     */
    public			int            nErrorCode;
    /**
     所属区域信息个数
     */
    public			int            nAreaInfoNum;
    /**
     所属区域信息
     */
    public			NET_EVENT_AREAR_INFO[] stuAreaInfo = new NET_EVENT_AREAR_INFO[64];
    /**
     保留字节
     */
    public			byte[]         szReserved = new byte[1024];

    public			NET_ALARM_WIFI_FAILURE_INFO(){
        for(int i=0;i<stuAreaInfo.length;i++){
            stuAreaInfo[i]=new NET_EVENT_AREAR_INFO();
        }
    }
}

