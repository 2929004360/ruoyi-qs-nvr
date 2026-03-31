package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description  CLIENT_DeleteGroupFromVehicleRegisterDB 接口输入参数
 * @date 2021/8/17 11:02
 */
public class NET_IN_DELETE_GROUP_FROM_VEHICLE_REG_DB extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小
    public byte[]           szGroupID = new byte[64];             // 车辆组标识, 为空时表示删除全部车辆组

    public NET_IN_DELETE_GROUP_FROM_VEHICLE_REG_DB(){
        this.dwSize=this.size();
    }
}

