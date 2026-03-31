package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description CLIENT_FindGroupFormVehicleRegisterDB 接口输入参数
 * @date 2022/10/21 17:35
 */
public class NET_IN_FIND_GROUP_FROM_VEHICLE_REG_DB extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小
    public byte[]           szGroupID = new byte[64];             // 组ID，为空表示查找全部车辆组信息

    public NET_IN_FIND_GROUP_FROM_VEHICLE_REG_DB(){
        dwSize = this.size();
    }
}

