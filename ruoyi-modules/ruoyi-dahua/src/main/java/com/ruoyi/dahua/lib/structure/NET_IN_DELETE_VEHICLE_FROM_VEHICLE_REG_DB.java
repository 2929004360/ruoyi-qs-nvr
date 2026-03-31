package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description CLIENT_DeleteVehicleFromVehicleRegisterDB 接口输入参数
 * @date 2022/10/22 10:31
 */
public class NET_IN_DELETE_VEHICLE_FROM_VEHICLE_REG_DB extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小
    public int              nUID;                                 // 车辆唯一标识符
    public byte[]           szGroupID = new byte[64];             // 车辆组ID
    public byte[]           szPlateNumber = new byte[64];         // 车牌

    public NET_IN_DELETE_VEHICLE_FROM_VEHICLE_REG_DB(){
        dwSize=this.size();
    }
}

