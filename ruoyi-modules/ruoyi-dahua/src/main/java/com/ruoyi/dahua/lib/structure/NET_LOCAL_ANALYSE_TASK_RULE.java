package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 本地分析任务分析规则
*/
public class NET_LOCAL_ANALYSE_TASK_RULE extends NetSDKLib.SdkStructure
{
    /**
     * 分析大类类型,参见枚举定义 {@link NetSDKLib.EM_SCENE_CLASS_TYPE}
    */
    public int              emClassType;
    /**
     * 分析规则类型, 详见dhnetsdk.h中"智能分析事件类型"
    */
    public int              dwRuleType;
    /**
     * 检测的物体类型列表个数
    */
    public int              nObjectTypesCount;
    /**
     * 检测的物体类型列表,参见枚举定义 {@link NetSDKLib.EM_ANALYSE_OBJECT_TYPE}
    */
    public int[]            emObjectTypes = new int[16];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[516];

    public NET_LOCAL_ANALYSE_TASK_RULE() {
    }
}

