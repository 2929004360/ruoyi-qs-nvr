package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * VideoAnalyseObject中车辆信息的额外扩展
*/
public class NET_VAO_EXTRA_VEHICLE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 货物状态：0：位置载货状态 1：空载状态 2：正常载货状态 3：超载状态
    */
    public int              nCargoState;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_VAO_EXTRA_VEHICLE_INFO() {
    }
}

