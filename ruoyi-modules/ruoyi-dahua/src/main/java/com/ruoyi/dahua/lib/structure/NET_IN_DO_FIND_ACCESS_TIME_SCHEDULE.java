package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_DoFindAccessTimeSchedule 接口入参
*/
public class NET_IN_DO_FIND_ACCESS_TIME_SCHEDULE extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 查询的偏移位置
    */
    public int              nOffset;
    /**
     * 本次查询需要获取的条数
    */
    public int              nCount;

    public NET_IN_DO_FIND_ACCESS_TIME_SCHEDULE() {
        this.dwSize = this.size();
    }
}

