package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 查询联动规则输入参数(对应 EM_THINGS_SERVICE_TYPE_QUERULE)
 * @date 2022/04/20 10:50:22
 */
public class NET_IN_THINGS_SERVICE_QUERULE extends NetSDKLib.SdkStructure {
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
     * 每一页的数据大小
     */
    public int              nPageSize;
    /**
     * 按页查询
     */
    public int              nPage;
    /**
     * 类型名称“timer”、“alarm”
     */
    public byte[]           szClassName = new byte[256];

    public NET_IN_THINGS_SERVICE_QUERULE() {
        this.dwSize = this.size();
    }
}

