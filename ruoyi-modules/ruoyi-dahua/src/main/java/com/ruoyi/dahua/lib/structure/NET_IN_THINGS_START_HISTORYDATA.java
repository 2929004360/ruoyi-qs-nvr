package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_StartThingsHistoryData 输入参数
 * @date 2022/04/20 10:16:56
 */
public class NET_IN_THINGS_START_HISTORYDATA extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 历史数据主题个数
     */
    public int              nTopicsCount;
    /**
     * 历史数据主题列表
     */
    public NET_THINGS_START_HISTORYDATA_TOPICS[] stuTopics = (NET_THINGS_START_HISTORYDATA_TOPICS[]) new NET_THINGS_START_HISTORYDATA_TOPICS().toArray(64);

    public NET_IN_THINGS_START_HISTORYDATA() {
        this.dwSize = this.size();
    }
}

