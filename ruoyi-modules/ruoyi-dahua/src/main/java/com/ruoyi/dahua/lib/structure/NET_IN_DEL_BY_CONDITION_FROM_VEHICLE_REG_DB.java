package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description CLIENT_DeleteByConditionFromVehicleRegisterDB 接口输入参数
 * @date 2022/02/25 15:03:27
 */
public class NET_IN_DEL_BY_CONDITION_FROM_VEHICLE_REG_DB extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 车辆组标识
     */
    public byte[]           szGroupID = new byte[64];
    /**
     * 删除车辆的过滤条件
     */
    public NET_DELETE_VEHICLE_CONDITION_INFO stuDelCondition = new NET_DELETE_VEHICLE_CONDITION_INFO();
    /**
     * 不删除的车辆列表有效个数
     */
    public int              dwUIDCnt;
    /**
     * 不删除的车辆列表
     */
    public byte[]           szUIDList = new byte[1024 * 32];
    /**
     * 车牌唯一标识符列表有效个数
     */
    public int              nUUIDNum;
    /**
     * 车牌唯一标识符列表,存在该字段时，字段szGroupID，stuDelCondition，szUIDList 不生效
     */
    public byte[]           szUUID = new byte[512 * 64];

    public NET_IN_DEL_BY_CONDITION_FROM_VEHICLE_REG_DB() {
        this.dwSize = this.size();
    }
}

