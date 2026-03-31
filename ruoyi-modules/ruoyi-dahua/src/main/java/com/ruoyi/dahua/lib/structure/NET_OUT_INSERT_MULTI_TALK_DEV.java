package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description CLIENT_InsertMultiTalkDev 出参
 * @origin autoTool
 * @date 2023/10/26 09:34:53
 */
public class NET_OUT_INSERT_MULTI_TALK_DEV extends NetSDKLib.SdkStructure {
    /**
     * / 结构体大小
     */
    public int              dwSize;
    /**
     * / 下发失败数量
     */
    public int              nFailCount;
    /**
     * / 信息个数
     */
    public int              nFailCodesNum;
    /**
     * / 详细的失败信息, 0表示失败, 1表示成功
     */
    public int[]            nFailCodes = new int[128];

    public NET_OUT_INSERT_MULTI_TALK_DEV() {
        this.dwSize = this.size();
    }
}

