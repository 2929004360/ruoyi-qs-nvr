package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description DH_MEDIA_QUERY_TRAFFICCAR对应的查询条件 参数扩展
 * @date 2022/09/06 09:43:57
 */
public class NET_MEDIA_QUERY_TRAFFICCAR_PARAM_EX extends NetSDKLib.SdkStructure {
    /**
     * 违法代码
     */
    public byte[]           szViolationCode = new byte[16];
    /**
     * 国籍，2字节，符合ISO3166规范
     */
    public byte[]           szCountry = new byte[4];
    /**
     * 为TRUE表示仅下发stuStartTimeRealUTC和stuEndTimeRealUTC(不下发StartTime, EndTime), 为FALSE表示仅下发StartTime, EndTime(不下发stuStartTimeRealUTC和stuEndTimeRealUTC)
     */
    public int              bOnlySupportRealUTC;
    /**
     * UTC开始时间(标准UTC时间), 与stuEndTimeRealUTC配对使用，与(StartTime, EndTime)互斥
     */
    public NetSDKLib.NET_TIME stuStartTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * UTC结束时间(标准UTC时间), 与stuStartTimeRealUTC配对使用，与(StartTime, EndTime)互斥
     */
    public NetSDKLib.NET_TIME stuEndTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * 车牌代码
    */
    public byte[]           szPlateCode = new byte[16];
    /**
     * 是否使用szViolationCode字段
    */
    public int              bUseViolationCode;
    /**
     * 是否使用szCountry字段
    */
    public int              bUseCountry;
    /**
     * 是否使用szPlateCode字段
    */
    public int              bUsePlateCode;
    /**
     * 保留字节
     */
    public byte[]           byReserved = new byte[940];

    public NET_MEDIA_QUERY_TRAFFICCAR_PARAM_EX() {
    }
}

