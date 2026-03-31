package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 查找返回的车辆组信息
 * @date 2022/10/21 17:38
 */
public class NET_VEHICLE_GROUP_FOUND_INFO extends NetSDKLib.SdkStructure {
    public byte[]           szGroupID = new byte[64];             // 车辆组标识
    public byte[]           szGroupName = new byte[128];          // 车辆组名称
    public byte[]           szGroupDetail = new byte[256];        // 车辆组备注信息
    public int              nGroupType;                           // 车辆组类型，0:未知 1:禁止列表 2：允许列表
    public int              nGroupSize;                           // 当前组内车辆数
    public int[]            nChannels = new int[1024];            // 当前组绑定到的视频通道号列表,未和任何视频通道关联填[-1]
    public int              nChannelsNum;                         // 返回视频通道号列表个数
    public byte[]           szReserved = new byte[508];           // 保留字段
}

