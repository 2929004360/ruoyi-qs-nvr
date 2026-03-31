package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description CLIENT_CreateGroupForVehicleRegisterDB 接口输入参数
 * @date 2021/8/17 10:52
 */
public class NET_IN_CREATE_GROUP_FOR_VEHICLE_REG_DB extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小
    public byte[]           szGroupName = new byte[128];          // 车辆组名称
    public byte[]           szGroupDetail = new byte[256];        // 车辆组备注信息
    public   int            nGroupType;                           // 车辆组类型，0:代表允许，1：代表禁止

    public NET_IN_CREATE_GROUP_FOR_VEHICLE_REG_DB(){
            this.dwSize=this.size();
    }
}

