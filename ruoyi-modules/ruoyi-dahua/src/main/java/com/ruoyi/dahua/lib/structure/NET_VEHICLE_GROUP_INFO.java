package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 车辆组信息
 * @date 2022/10/21 17:26
 */
public class NET_VEHICLE_GROUP_INFO extends NetSDKLib.SdkStructure {
    public    byte[]        szGroupID = new byte[64];             // 车辆组标识
    public	 byte[]          szGroupName = new byte[128];          // 车辆组名称
    public 	byte[]          szGroupDetail = new byte[256];        // 车辆组备注信息
    public	int              nGroupType;                           // 车辆组类型,0:未知 1:禁止列表 2：允许列表
    public	byte[]           szReserved = new byte[512];           // 保留字段
}

