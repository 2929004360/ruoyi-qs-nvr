package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 设备设息修改(增加、删除、修改)报警上报事件 (对应 NET_ALARM_DEVICE_MODIFIED)
 * @date 2022/7/19 11:17
 */
public class NET_ALARM_DEVICE_MODIFIED_INFO extends NetSDKLib.SdkStructure {
    public  int             nAction;                              // 事件动作 0:脉冲
    public	int              nChannel;                             // 通道号
    public	NET_TIME_EX      stuUTC = new NET_TIME_EX();           // 事件发生的时间,标准的（不带时区偏差的）UTC时间
    public	byte[]           szUser = new byte[32];                // 用户名称
    /**
     * {@link  com.ruoyi.dahua.lib.enumeration.NET_EVENT_OPERATE_TYPE}
     */
    public  int             emOpType;                             // 操作类型
    /**
     * {@link  com.ruoyi.dahua.lib.enumeration.NET_EVENT_DEVICE_TYPE}
     */
    public  int             emDeviceType;                         // 设备类型
    public byte[]           szReserved = new byte[1024];          // 保留字节
}

