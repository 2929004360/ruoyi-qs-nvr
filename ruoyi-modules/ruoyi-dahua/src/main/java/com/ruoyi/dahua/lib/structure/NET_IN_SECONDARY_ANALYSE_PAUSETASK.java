package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 暂停录像二次分析任务输入参数
*/
public class NET_IN_SECONDARY_ANALYSE_PAUSETASK extends NetSDKLib.SdkStructure
{
    /**
     * 赋值为结构体大小
    */
    public int              dwSize;
    /**
     * 任务数量
    */
    public int              nTaskIdNum;
    /**
     * 任务ID
    */
    public int[]            nTaskID = new int[32];

    public NET_IN_SECONDARY_ANALYSE_PAUSETASK() {
        this.dwSize = this.size();
    }
}

