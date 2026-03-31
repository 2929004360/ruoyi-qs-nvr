package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 视频分析联动信息
*/
public class NET_ANALYSE_RULE_EVENT_HANDLER_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 报警时间段,参见结构体定义 {@link com.ruoyi.dahua.lib.structure.NET_TSECT}
    */
    public NET_TSECT_ARRAY_6[] stuTimeSection = new NET_TSECT_ARRAY_6[8];
    /**
     * 报警时间段有效个数
    */
    public int[]            nTimeSectionValidNum = new int[8];
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_ANALYSE_RULE_EVENT_HANDLER_INFO() {
        for(int i = 0; i < stuTimeSection.length; i++){
            stuTimeSection[i] = new NET_TSECT_ARRAY_6();
        }
    }
}

