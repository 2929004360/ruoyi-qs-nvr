package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 事件类型 DH_ALARM_USER_LOCK_EVENT (用户锁定报警事件)对应的数据描述信息
 * @origin autoTool
 * @date 2023/11/27 20:14:15
 */
public class ALARM_USER_LOCK_EVENT_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 用户名
     */
    public byte[]           szUser = new byte[128];
    /**
     * / 组名
     */
    public byte[]           szGroup = new byte[128];
    /**
     * / 设备IP
     */
    public byte[]           szIP = new byte[40];
    /**
     * / 预留
     */
    public byte[]           reserved = new byte[512];

    public ALARM_USER_LOCK_EVENT_INFO() {
    }
}

