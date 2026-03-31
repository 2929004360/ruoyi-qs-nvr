package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 测温查询结果信息
*/
public class NET_RADIOMETRY_QUERY_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 记录时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuTime = new NetSDKLib.NET_TIME();
    /**
     * 预置点编号
    */
    public int              nPresetId;
    /**
     * 规则编号
    */
    public int              nRuleId;
    /**
     * 查询类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_RADIOMETRY_QUERY_TYPE}
    */
    public int              emQueryType;
    /**
     * 查询项名称
    */
    public byte[]           szName = new byte[64];
    /**
     * 查询测温点坐标,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT[] stuCoordinate = new NetSDKLib.NET_POINT[8];
    /**
     * 通道号
    */
    public int              nChannel;
    /**
     * 温度单位,参见枚举定义 {@link NetSDKLib.NET_TEMPERATURE_UNIT}
    */
    public int              emTemperatureUnit;
    /**
     * 温度信息,参见结构体定义 {@link NET_QUERY_TEMPER_INFO}
    */
    public NET_QUERY_TEMPER_INFO stuQueryTemperInfo = new NET_QUERY_TEMPER_INFO();
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[256];

    public NET_RADIOMETRY_QUERY_INFO() {
        for(int i = 0; i < stuCoordinate.length; i++){
            stuCoordinate[i] = new NetSDKLib.NET_POINT();
        }
    }
}

