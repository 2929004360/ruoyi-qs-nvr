package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description  CLIENT_StartFindVehicleRegisterDB 接口输出参数
 * @date 2022/10/22 10:46
 */
public class NET_OUT_START_FIND_VEHICLE_REG_DB extends NetSDKLib.SdkStructure {
    public 	int             dwSize;                               // 结构体大小
    public int              nToken;                               // 取到的查询令牌
    public 	int             nTotalCount;                          // 符合此次查询条件的结果总条数

    public NET_OUT_START_FIND_VEHICLE_REG_DB(){
        dwSize=this.size();
    }
}

