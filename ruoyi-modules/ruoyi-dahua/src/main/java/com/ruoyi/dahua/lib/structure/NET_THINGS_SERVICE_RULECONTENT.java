package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 场景的触发器
 * @date 2022/04/20 10:50:21
 */
public class NET_THINGS_SERVICE_RULECONTENT extends NetSDKLib.SdkStructure {
    /**
     * 场景规则类型 {@link com.ruoyi.dahua.lib.enumeration.EM_THINGS_RULECONTENT_TYPE}
     */
    public int              emRuleContentType;
    /**
     * 场景触发个数
     */
    public int              nTriggerNum;
    /**
     * 场景的触发器，用户分配内存,大小为nTriggerNum * sizeof{@link NET_THINGS_SERVICE_RULECONTENT_TRIGGER}
     */
    public Pointer          pstuTrigger;
    /**
     * 场景触发执行动作，用户分配内存,大小为nActionNum * sizeof{@link NET_THINGS_SERVICE_RULECONTENT_ACTION}
     */
    public Pointer          pstuAction;
    /**
     * 场景触发执行动作个数
     */
    public int              nActionNum;
    /**
     * 场景触发过滤条件个数
     */
    public int              nConditionNum;
    /**
     * 场景触发过滤条件，用户分配内存,大小为nConditionNum * sizeof{@link NET_THINGS_SERVICE_RULECONTENT_CONDITION}
     */
    public Pointer          pstuCondition;
    /**
     * 保留字节
     */
    public byte[]           szReserve = new byte[1024];
}

