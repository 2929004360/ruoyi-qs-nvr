package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 预置点配置列表信息
*/
public class NET_PRESET_CONFIG_LIST_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 预置点编号，从1开始编号
    */
    public int              nIndex;
    /**
     * 预置点到位后的停留时间(单位：秒)，取值范围1-3600
    */
    public int              nStayTime;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[128];

    public NET_PRESET_CONFIG_LIST_INFO() {
    }
}

