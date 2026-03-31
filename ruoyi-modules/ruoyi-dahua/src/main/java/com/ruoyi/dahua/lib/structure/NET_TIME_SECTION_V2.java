package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
public class NET_TIME_SECTION_V2 extends NetSDKLib.SdkStructure
{
    /**
     * 时间段数量
    */
    public int              nTimeSectionNum;
    /**
     * 每一个月的时间段信息,参见结构体定义 {@link NET_TIME_SECTION_EX}
    */
    public NET_TIME_SECTION_EX[] stuTimeSectionEx = new NET_TIME_SECTION_EX[16];
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[36];

    public NET_TIME_SECTION_V2() {
        for(int i = 0; i < stuTimeSectionEx.length; i++){
            stuTimeSectionEx[i] = new NET_TIME_SECTION_EX();
        }
    }
}

