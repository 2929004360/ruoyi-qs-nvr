package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * @author 291189
 * @description  超温上报事件 (对应 DH_ALARM_OVER_TEMPERATURE)
 * @date 2022/07/25 15:28:37
 */
public class NET_ALARM_OVER_TEMPERATURE_INFO extends NetSDKLib.SdkStructure {
    /**
     事件动作 0:脉冲
     */
    public			int            nAction;
    /**
     通道号
     */
    public			int            nChannel;
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
     超温类型 0:温度恢复正常;1:温度超过下限;2:温度超过上限
     */
    public			int            nTemperatureType;
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

    public			NET_ALARM_OVER_TEMPERATURE_INFO(){
        for(int i=0;i<stuAreaInfo.length;i++){
            stuAreaInfo[i]=new NET_EVENT_AREAR_INFO();
        }
    }
}

