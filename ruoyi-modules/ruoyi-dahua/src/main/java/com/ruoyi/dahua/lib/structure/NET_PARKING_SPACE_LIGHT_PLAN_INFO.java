package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 停车场车位指示灯亮灯计划信息
*/
public class NET_PARKING_SPACE_LIGHT_PLAN_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 车灯颜色,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_PARKINGSPACE_LIGHT_COLOR}
    */
    public int              emColor;
    /**
     * 状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_PARKINGSPACE_LIGHT_STATE}
    */
    public int              emState;
    /**
     * 持续时间,单位秒，-1表示常亮
    */
    public int              nKeepTime;
    /**
     * 车灯闪烁颜色, emState 为 EM_PARKINGSPACE_LIGHT_STATE_ALTERNATE_FLICKER 有效,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_PARKINGSPACE_LIGHT_COLOR}
    */
    public int              emColorEx;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[124];

    public NET_PARKING_SPACE_LIGHT_PLAN_INFO() {
    }
}

