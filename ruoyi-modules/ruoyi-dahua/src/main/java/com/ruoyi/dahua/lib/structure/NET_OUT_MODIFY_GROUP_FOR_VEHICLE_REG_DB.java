package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description CLIENT_ModifyGroupForVehicleRegisterDB 接口输出参数
 * @date 2022/10/21 17:32
 */
public class NET_OUT_MODIFY_GROUP_FOR_VEHICLE_REG_DB extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小

    public NET_OUT_MODIFY_GROUP_FOR_VEHICLE_REG_DB(){
        dwSize = this.size();
    }
}

