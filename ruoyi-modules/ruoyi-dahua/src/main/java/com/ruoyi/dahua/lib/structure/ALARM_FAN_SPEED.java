package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 风扇转速异常事件
 * @origin autoTool
 * @date 2023/11/27 20:28:07
 */
public class ALARM_FAN_SPEED extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * / 0-开始, 1-停止
     */
    public int              dwAction;
    /**
     * / 风扇序号
     */
    public int              dwIndex;
    /**
     * / 传感器名称
     */
    public byte[]           szName = new byte[64];
    /**
     * / 事件发生时间
     */
    public NET_TIME         stuTime = new NET_TIME();
    /**
     * / 当前转速
     */
    public int              dwCurrent;
    /**
     * / 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();

    public ALARM_FAN_SPEED() {
        this.dwSize = this.size();
    }
}

