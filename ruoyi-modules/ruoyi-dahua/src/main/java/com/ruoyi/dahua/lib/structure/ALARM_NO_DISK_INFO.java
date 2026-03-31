package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 47081
 * @version 1.0
 * @description 无硬盘报警
 * @date 2020/9/9
 */
public class ALARM_NO_DISK_INFO extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 时间
     */
    public NET_TIME         stuTime;
    /**
     * 事件动作, 0:Start, 1:Stop
     */
    public int              dwAction;
    /**
     * 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();

    public ALARM_NO_DISK_INFO() {
        this.dwSize = this.size();
    }
}

