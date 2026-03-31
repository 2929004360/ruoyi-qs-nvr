package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 删除联动规则输入参数(对应 EM_THINGS_SERVICE_TYPE_DELRULE)
 * @date 2022/04/20 10:50:21
 */
public class NET_IN_THINGS_SERVICE_DELRULE extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 产品ID，全网唯一
     */
    public byte[]           szProductID = new byte[64];
    /**
     * 设备ID
     */
    public byte[]           szDeviceID = new byte[64];
    /**
     * 规则ID
     */
    public byte[]           szRuleID = new byte[256];
    /**
     * 类型名称“timer”、“alarm”
     */
    public byte[]           szClassName = new byte[256];

    public NET_IN_THINGS_SERVICE_DELRULE() {
        this.dwSize = this.size();
    }
}

