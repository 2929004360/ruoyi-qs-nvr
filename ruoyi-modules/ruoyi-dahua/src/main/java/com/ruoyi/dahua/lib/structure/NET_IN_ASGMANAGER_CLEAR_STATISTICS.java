package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_ASGManagerClearStatistics 入参
*/
public class NET_IN_ASGMANAGER_CLEAR_STATISTICS extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 是否清除通行人数
    */
    public int              bCleanPassNum;

    public NET_IN_ASGMANAGER_CLEAR_STATISTICS() {
        this.dwSize = this.size();
    }
}

