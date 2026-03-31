package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 车位信息
*/
public class NET_PARKING_SPACE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 车位号
    */
    public int              nLane;
    /**
     * 停车状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_PARKING_SPACE_STATUS}
    */
    public int              emParkStatus;
    /**
     * 自定义车位号
    */
    public byte[]           szCustomParkNo = new byte[32];
    /**
     * 车位类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_PARKINGSPACE_TYPE}
    */
    public int              emSpaceType;
    /**
     * 有车时，车牌号
    */
    public byte[]           szPlate = new byte[64];
    /**
     * 车位检测功能是否开启
    */
    public int              bEnable;
    /**
     * 有车时，车辆进入时间
    */
    public long             nTime;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[176];

    public NET_PARKING_SPACE_INFO() {
    }
}

