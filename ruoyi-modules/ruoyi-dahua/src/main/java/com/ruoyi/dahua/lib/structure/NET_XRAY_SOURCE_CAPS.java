package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * X射线源能力集信息
*/
public class NET_XRAY_SOURCE_CAPS extends NetSDKLib.SdkStructure
{
    /**
     * X射线源最小电流值组，元素表示各个视角最小电流值,参见结构体定义 {@link NET_XRAY_SOURCE_CURRENT_ARRAY}
    */
    public NET_XRAY_SOURCE_CURRENT_ARRAY stuMinCurrent = new NET_XRAY_SOURCE_CURRENT_ARRAY();
    /**
     * X射线源最大电流值组，元素表示各个视角最大电流值,参见结构体定义 {@link NET_XRAY_SOURCE_CURRENT_ARRAY}
    */
    public NET_XRAY_SOURCE_CURRENT_ARRAY stuMaxCurrent = new NET_XRAY_SOURCE_CURRENT_ARRAY();
    /**
     * 时间计划编号，从0开始
    */
    public int              nChannels;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_XRAY_SOURCE_CAPS() {
    }
}

