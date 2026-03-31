package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description CLIENT_StartFindVehicleRegisterDB 接口输入参数
 * @date 2022/10/22 10:44
 */
public class NET_IN_START_FIND_VEHICLE_REG_DB extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小
    public NetSDKLib.NET_VEHICLE_INFO stuVehicleInfo = new NetSDKLib.NET_VEHICLE_INFO(); // 车辆信息
    public NET_START_FIND_VEHICLE_REG_DB_CONDITION stuCondition = new NET_START_FIND_VEHICLE_REG_DB_CONDITION(); //查询条件

    public NET_IN_START_FIND_VEHICLE_REG_DB(){
        dwSize=this.size();
    }
}

