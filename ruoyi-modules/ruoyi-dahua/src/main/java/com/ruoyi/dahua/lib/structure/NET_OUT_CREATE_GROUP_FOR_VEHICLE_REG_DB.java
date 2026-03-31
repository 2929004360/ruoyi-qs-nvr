package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description   CLIENT_CreateGroupForVehicleRegisterDB 接口输出参数
 * @date 2021/8/17 11:28
 */
public class NET_OUT_CREATE_GROUP_FOR_VEHICLE_REG_DB extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小
    public byte[]           szGroupID = new byte[64];             // 车辆组标识
    /**
     * 错误码描述
    */
    public byte[]           szMessage = new byte[256];
    /**
     * 错误码，区分错误原因，, -1: 可忽略，创建成功但设备未响应GroupID等参数, 0x1134021: 超出车牌库数量限制
    */
    public int              nCode;

    public NET_OUT_CREATE_GROUP_FOR_VEHICLE_REG_DB(){
        this.dwSize=this.size();
    }
}

