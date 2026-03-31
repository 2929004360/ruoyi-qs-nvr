package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 人体测温过滤条件
 * @date 2021/02/22
 */
public class NET_ANATOMY_TEMP_DETECT_FILTER extends NetSDKLib.SdkStructure {
	 /**
     *  温度类型 ,参考{ @link EM_QUERY_TEMPERATURE_TYPE}
     */
    public int              emTempType;
    /**
     *  预留字段
     */
    public byte[]           byReserved1 = new byte[4];
    /**
     *  温度值，当 emTempType 取值为全部、正常、高时有效
     */
    public double           dbTemperature;
    /**
     *  人员信息
     */
    public ANATOMY_TEMP_DETECT_PERSON_FILTER stuPersonInfo;
    /**
     *  保留字节
     */
    public byte[]           byReserved = new byte[1024];
}

