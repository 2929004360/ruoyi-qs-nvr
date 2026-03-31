package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 设备信息格式
 * @origin autoTool
 * @date 2023/10/26 09:34:53
 */
public class NET_TALK_DEV_DETAIL_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 设备在组网中的父节点
     */
    public byte[]           szNode = new byte[32];
    /**
     * / 设备的通讯录信息
     */
    public NET_VT_CONTACTINFO_INFO stuContactInfo = new NET_VT_CONTACTINFO_INFO();
    /**
     * / 设备类型前端设备, 0:未知, 1:前端 – VTAVTO, 2:后端 other
     */
    public int              nDevType;
    /**
     * / 前端设备详细信息
     */
    public NET_TALK_DEV_FRONT_DEV_INFO stuFrontDevInfo = new NET_TALK_DEV_FRONT_DEV_INFO();
    /**
     * / 保留字段
     */
    public byte[]           szResvered = new byte[248];

    public NET_TALK_DEV_DETAIL_INFO() {
    }
}

