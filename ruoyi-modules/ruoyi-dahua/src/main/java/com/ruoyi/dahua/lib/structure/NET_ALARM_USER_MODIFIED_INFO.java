package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description用户信息被修改(增加、删除、修改)报警上报事件 (对应 NET_ALARM_USER_MODIFIED)
 * @date 2022/7/19 10:59
 */
public class NET_ALARM_USER_MODIFIED_INFO extends NetSDKLib.SdkStructure {
    public 	int             nAction;                              // 事件动作 0:脉冲
    public 	int             nChannel;                             // 通道号
    public byte[]           szUser = new byte[128];               // 用户名称
    /**
     * {@link com.ruoyi.dahua.lib.enumeration.NET_EVENT_OPERATE_TYPE;}
     */
    public int              emOpType;                             // 操作类型
    /**
     * {@link com.ruoyi.dahua.lib.enumeration.NET_EVENT_USER_TYPE;}
     */
    public int              emUserType;                           // 用户类型
    public byte[]           szReserved = new byte[1024];          // 保留字节
}

