package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 人数去重策略配置
*/
public class CFG_DUPLICATE_REMOVE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 使能开关
    */
    public int              bEnable;
    /**
     * 人数去重灵敏度，取值1-10，默认5，值越小越不容易判断重复，值越高越容易被判断为重复，仅在支持人数去重时生效。
    */
    public int              nSensitivity;
    /**
     * 策略类型 0: 按时刻表
    */
    public int              nType;
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[124];

    public CFG_DUPLICATE_REMOVE_INFO() {
    }
}

