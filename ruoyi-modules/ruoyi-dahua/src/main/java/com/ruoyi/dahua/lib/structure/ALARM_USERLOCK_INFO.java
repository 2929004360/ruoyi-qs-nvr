package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 用户锁定报警事件, 对应 DH_ALARM_USERLOCK
 * @origin autoTool
 * @date 2023/11/28 15:07:03
 */
public class ALARM_USERLOCK_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 时间戳(单位是毫秒)
     */
    public double           dbPTS;
    /**
     * / 事件发生的时间
     */
    public NET_TIME_EX      stuTime = new NET_TIME_EX();
    /**
     * / 事件ID
     */
    public int              nEventID;
    /**
     * / 用户名
     */
    public byte[]           szUserName = new byte[128];
    /**
     * / 组名
     */
    public byte[]           szGroup = new byte[128];
    /**
     * / 非法登录的IP
     */
    public byte[]           szIllegalLoginIP = new byte[40];
    /**
     * / 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * / 保留字节
     */
    public byte[]           byReserved = new byte[1024];

    public ALARM_USERLOCK_INFO() {
    }
}

