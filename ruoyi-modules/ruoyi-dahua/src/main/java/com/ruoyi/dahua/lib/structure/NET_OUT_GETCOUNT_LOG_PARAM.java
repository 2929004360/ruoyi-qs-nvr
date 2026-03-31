package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_QueryDevLogCount获取日志条数输出参数
 * @date 2023/05/05 11:12:30
 */
public class NET_OUT_GETCOUNT_LOG_PARAM extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 日志数(设备返回)
     */
    public int              nLogCount;

    public NET_OUT_GETCOUNT_LOG_PARAM() {
        this.dwSize = this.size();
    }
}

