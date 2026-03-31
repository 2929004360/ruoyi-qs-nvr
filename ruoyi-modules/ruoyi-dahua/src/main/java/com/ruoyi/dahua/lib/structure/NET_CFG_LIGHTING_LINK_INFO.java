package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 云台补光灯联动项
*/
public class NET_CFG_LIGHTING_LINK_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 使能
    */
    public int              bEnable;
    /**
     * 闪烁灯光类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_FILCKERLIGHT_TYPE}
    */
    public int              emFilckerLightType;
    /**
     * 灯光联动方式,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_LIGHTLINK_TYPE}
    */
    public int              emLightlinkType;
    /**
     * 闪烁间隔时间
    */
    public float            fFilckerIntevalTime;
    /**
     * 闪烁可配置的次数
    */
    public int              nFilckerTimes;
    /**
     * 灯光闪烁或常亮持续时间,单位秒
    */
    public int              nLightDuration;
    /**
     * 联动灯光的亮度
    */
    public int              nLightBright;
    /**
     * 白光灯联动时间段,参见结构体定义 {@link NET_CFG_TIME_SECTION}
    */
    public NET_CFG_TIME_SECTION_ARRAY_6[] stuWhiteLightTimeSection = new NET_CFG_TIME_SECTION_ARRAY_6[7];

    public NET_CFG_LIGHTING_LINK_INFO() {
        for(int i = 0; i < stuWhiteLightTimeSection.length; i++){
            stuWhiteLightTimeSection[i] = new NET_CFG_TIME_SECTION_ARRAY_6();
        }
    }
}

