package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description CLIENT_DoFindTalkDev 接口输入参数
 * @origin autoTool
 * @date 2023/10/26 10:05:01
 */
public class NET_IN_DO_FIND_TALK_DEV extends NetSDKLib.SdkStructure {
    /**
     * / 结构体大小
     */
    public int              dwSize;
    /**
     * / 查询的偏移条数
     */
    public int              nOffset;
    /**
     * / 本次查询需要获取的条数, 不能超过startFind返回的nCaps查询能力
     */
    public int              nCount;

    public NET_IN_DO_FIND_TALK_DEV() {
        this.dwSize = this.size();
    }
}

