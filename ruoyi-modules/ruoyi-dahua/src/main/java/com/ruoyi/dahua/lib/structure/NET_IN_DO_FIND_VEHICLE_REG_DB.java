package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description  CLIENT_DoFindVehicleRegisterDB 接口输入参数
 * @date 2022/10/22 10:50
 */
public class NET_IN_DO_FIND_VEHICLE_REG_DB extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小
    public 	NET_FIND_VEHICLE_CONDITION stuCondition = new NET_FIND_VEHICLE_CONDITION(); // 查询条件

    public NET_IN_DO_FIND_VEHICLE_REG_DB(){
        dwSize=this.size();
    }
}

