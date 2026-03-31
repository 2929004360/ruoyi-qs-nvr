package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_QueryDevLogCount获取日志条数输入参数
 * @date 2023/05/05 11:12:30
 */
public class NET_IN_GETCOUNT_LOG_PARAM extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 查询记录数条件
     */
    public QUERY_DEVICE_LOG_PARAM stuQueryCondition = new QUERY_DEVICE_LOG_PARAM();

    public NET_IN_GETCOUNT_LOG_PARAM() {
        this.dwSize = this.size();
    }
}

