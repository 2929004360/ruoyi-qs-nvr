package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 报警接收中心离线报警上报事件 (对应 NET_ALARM_ARC_OFFLINE)
 * @date 2022/7/19 11:31
 */
/**
 * @author 291189
 * @description  报警接收中心离线上报事件 (对应 DH_ALARM_ARC_OFFLINE)
 * @date 2022/07/25 15:31:01
 */
public class NET_ALARM_ARC_OFFLINE_INFO extends NetSDKLib.SdkStructure {
    /**
     事件动作, 1:开始 2:停止
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
     ARC通讯异常描述信息
     */
    public			byte[]         szDetail = new byte[128];
    /**
     保留字节
     */
    public			byte[]         szReserved = new byte[1024];

    public			NET_ALARM_ARC_OFFLINE_INFO(){
    }
}

