package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_DoFindThingsHistoryData 输入参数
 * @date 2022/04/20 10:16:55
 */
public class NET_IN_THINGS_DOFIND_HISTORYDATA extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 偏移量
     */
    public int              uOffset;
    /**
     * 每次查询数量
     */
    public int              uCount;

    public NET_IN_THINGS_DOFIND_HISTORYDATA() {
        this.dwSize = this.size();
    }
}

