package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 批量更新的数据
 * @origin autoTool
 * @date 2023/10/26 09:52:01
 */
public class NET_TALK_DEV_UPDATE_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 更新后的数据信息
     */
    public NET_TALK_DEV_DETAIL_INFO stuInfo = new NET_TALK_DEV_DETAIL_INFO();
    /**
     * / 设备编号
     */
    public int              nRecNo;
    /**
     * / 保留字段
     */
    public byte[]           szResvered = new byte[60];

    public NET_TALK_DEV_UPDATE_INFO() {
    }
}

