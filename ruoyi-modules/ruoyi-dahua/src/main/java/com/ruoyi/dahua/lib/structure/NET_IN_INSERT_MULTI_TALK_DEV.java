package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description CLIENT_InsertMultiTalkDev 入参
 * @origin autoTool
 * @date 2023/10/26 09:34:53
 */
public class NET_IN_INSERT_MULTI_TALK_DEV extends NetSDKLib.SdkStructure {
    /**
     * / 结构体大小
     */
    public int              dwSize;
    /**
     * / 新增的个数
     */
    public int              nInsertInfoNum;
    /**
     * / 添加的设备信息
     */
    public NET_TALK_DEV_DETAIL_INFO[] stuInsertInfo = new NET_TALK_DEV_DETAIL_INFO[128];

    public NET_IN_INSERT_MULTI_TALK_DEV() {
        for (int i = 0; i < stuInsertInfo.length; i++) {
            stuInsertInfo[i] = new NET_TALK_DEV_DETAIL_INFO();
        }
        this.dwSize = this.size();
    }
}

