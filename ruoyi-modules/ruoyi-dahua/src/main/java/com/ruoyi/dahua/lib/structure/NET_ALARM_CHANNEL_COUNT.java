package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 报警通道数
 * @date 2021/11/04
 */
public class NET_ALARM_CHANNEL_COUNT extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     *  本地报警输入通道数
     */
    public int              nLocalAlarmIn;
    /**
     *  本地报警输出通道数
     */
    public int              nLocalAlarmOut;
    /**
     *  远程报警输入通道数
     */
    public int              nRemoteAlarmIn;
    /**
     *  远程报警输出通道数
     */
    public int              nRemoteAlarmOut;

    public NET_ALARM_CHANNEL_COUNT (){
        this.dwSize = this.size();
    }
}

