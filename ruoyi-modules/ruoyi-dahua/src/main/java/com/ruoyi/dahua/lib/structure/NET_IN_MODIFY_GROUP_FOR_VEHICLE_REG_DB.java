package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description CLIENT_ModifyGroupForVehicleRegisterDB 接口输入参数
 * @date 2022/10/21 17:30
 */
public class NET_IN_MODIFY_GROUP_FOR_VEHICLE_REG_DB extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小
    public 	NET_VEHICLE_GROUP_INFO stuGroupInfo = new NET_VEHICLE_GROUP_INFO(); // 车辆组信息

    public NET_IN_MODIFY_GROUP_FOR_VEHICLE_REG_DB(){
        dwSize = this.size();
    }
}

