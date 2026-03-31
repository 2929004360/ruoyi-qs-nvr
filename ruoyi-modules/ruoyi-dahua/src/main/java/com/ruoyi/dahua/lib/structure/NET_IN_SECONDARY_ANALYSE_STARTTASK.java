package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 开始录像二次分析任务输入参数
*/
public class NET_IN_SECONDARY_ANALYSE_STARTTASK extends NetSDKLib.SdkStructure
{
    /**
     * 赋值为结构体大小
    */
    public int              dwSize;
    /**
     * 任务ID个数, 为0表示全部
    */
    public int              nTaskIdNum;
    /**
     * 任务ID，0表示全部
    */
    public int[]            nTaskID = new int[32];

    public NET_IN_SECONDARY_ANALYSE_STARTTASK() {
        this.dwSize = this.size();
    }
}

