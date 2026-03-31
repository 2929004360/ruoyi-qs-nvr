package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * NET_PARKING_SPACE_LIGHT_CONTROL 车位状态指示灯控制配置信息
*/
public class NET_PARKING_SPACE_LIGHT_CONTROL extends NetSDKLib.SdkStructure
{
    /**
     * 车位状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_SPACE_STATE}
    */
    public int              emSpaceState;
    /**
     * 车位状态亮灯时间, 单位：秒, 取值范围 -1 ~ 300, 0表示不亮, -1表示常亮
    */
    public int              nLightKeepTime;
    /**
     * 保留字节
    */
    public byte[]           szResvered = new byte[1024];

    public NET_PARKING_SPACE_LIGHT_CONTROL() {
    }
}

