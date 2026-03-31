package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description CLIENT_UpdateMultiTalkDev 入参
 * @origin autoTool
 * @date 2023/10/26 09:52:01
 */
public class NET_IN_UPDATE_MULTI_TALK_DEV extends NetSDKLib.SdkStructure {
    /**
     * / 结构体大小
     */
    public int              dwSize;
    /**
     * / 要更新的个数
     */
    public int              nUpdataInfoNum;
    /**
     * / 批量更新的数据
     */
    public NET_TALK_DEV_UPDATE_INFO[] stuUpdateInfo = new NET_TALK_DEV_UPDATE_INFO[128];

    public NET_IN_UPDATE_MULTI_TALK_DEV() {
        for (int i = 0; i < stuUpdateInfo.length; i++) {
            stuUpdateInfo[i] = new NET_TALK_DEV_UPDATE_INFO();
        }
        this.dwSize = this.size();
    }
}

