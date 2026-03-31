package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 删除录像二次分析任务输入参数
*/
public class NET_IN_SECONDARY_ANALYSE_REMOVETASK extends NetSDKLib.SdkStructure
{
    /**
     * 赋值为结构体大小
    */
    public int              dwSize;
    /**
     * 任务数量, 0表示删除全部
    */
    public int              nTaskIdNum;
    /**
     * 任务ID
    */
    public int[]            nTaskID = new int[32];

    public NET_IN_SECONDARY_ANALYSE_REMOVETASK() {
        this.dwSize = this.size();
    }
}

