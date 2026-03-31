package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 联动规则
 * @date 2022/04/20 11:28:15
 */
public class NET_THINGS_SERVICE_QUERULE_RULECONTENT extends NetSDKLib.SdkStructure {
    /**
     * 场景规则类型 {@link com.ruoyi.dahua.lib.enumeration.EM_THINGS_RULECONTENT_TYPE}
     */
    public int              emRuleContentType;
    /**
     * 用户分配的pstuTrigger个数
     */
    public int              nMaxTriggerNum;
    /**
     * 实际返回的pstuTrigger个数
     */
    public int              nRetTriggerNum;
    /**
     * 场景的触发器，用户分配内存,大小为nMaxTriggerNum * sizeof{@link NET_THINGS_SERVICE_RULECONTENT_TRIGGER}
     */
    public Pointer          pstuTrigger;
    /**
     * 用户分配的pstuAction个数
     */
    public int              nMaxActionNum;
    /**
     * 实际返回的pstuAction个数
     */
    public int              nRetActionNum;
    /**
     * 场景触发执行动作，用户分配内存,大小为nMaxActionNum * sizeof{@link NET_THINGS_SERVICE_RULECONTENT_ACTION}
     */
    public Pointer          pstuAction;
    /**
     * 用户分配的pstuCondition个数
     */
    public int              nMaxConditionNum;
    /**
     * 实际返回的pstuCondition个数
     */
    public int              nRetConditionNum;
    /**
     * 场景触发过滤条件，用户分配内存,大小为nMaxConditionNum * sizeof{@link NET_THINGS_SERVICE_RULECONTENT_CONDITION}
     */
    public Pointer          pstuCondition;
    /**
     * 保留字节
     */
    public byte[]           szReserve = new byte[1024];
}

