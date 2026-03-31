package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description CLIENT_StartFindVehicleRegisterDB 查询条件
 * @origin autoTool
 * @date 2023/10/30 16:34:24
 */
public class NET_START_FIND_VEHICLE_REG_DB_CONDITION extends NetSDKLib.SdkStructure {
    /**
     * / 平台端车牌唯一标识列表有效个数
     */
    public int              nUUIDsNum;
    /**
     * / 平台端车牌唯一标识列表，存在该字段时，stuVehicleInfo不生效
     */
    public byte[]           szUUIDs = new byte[64 * 64];
    /**
     * / 是否使用车牌库的车牌的入库时间、结束时间
     */
    public int              bIsUsingRegisterStorageTime;
    /**
     * / 开始时间 开始时间与结束时间对车牌库的车牌的入库时间RegisterStorageTime进行选择过滤
     */
    public NET_TIME         stuStartRegisterStorageTime = new NET_TIME();
    /**
     * / 结束时间
     */
    public NET_TIME         stuEndRegisterStorageTime = new NET_TIME();
    /**
     * / 是否使用车牌库的车牌的修改时间、结束时间
     */
    public int              bIsUsingModifyTime;
    /**
     * / 开始时间 开始时间与结束时间对车牌库的车牌的修改时间ModifyTime进行选择过滤
     */
    public NET_TIME         stuStartModifyTime = new NET_TIME();
    /**
     * / 结束时间
     */
    public NET_TIME         stuEndModifyTime = new NET_TIME();
    /**
     * / 预留字节
     */
    public byte[]           szReserved = new byte[1024];

    public NET_START_FIND_VEHICLE_REG_DB_CONDITION() {
    }
}

