package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 重复分析信息
*/
public class NET_LOCAL_ANALYSE_TASK_REPEAT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 目标类型,参见枚举定义 {@link NetSDKLib.EM_ANALYSE_OBJECT_TYPE}
    */
    public int              emObjectType;
    /**
     * 重复开始时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStartTime = new NetSDKLib.NET_TIME();
    /**
     * 重复结束时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuEndTime = new NetSDKLib.NET_TIME();
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[512];

    public NET_LOCAL_ANALYSE_TASK_REPEAT_INFO() {
    }
}

