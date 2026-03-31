package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_StartFindAccessTimeSchedule 接口出参
*/
public class NET_OUT_START_FIND_ACCESS_TIME_SCHEDULE extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 查找到的总条数
    */
    public int              nTotal;
    /**
     * 查询能力，doFind一次查询结果可提供的最大条数
    */
    public int              nCaps;

    public NET_OUT_START_FIND_ACCESS_TIME_SCHEDULE() {
        this.dwSize = this.size();
    }
}

