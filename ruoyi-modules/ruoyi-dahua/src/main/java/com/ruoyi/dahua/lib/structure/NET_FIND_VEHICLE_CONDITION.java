package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 车辆查询条件
 * @date 2022/10/22 10:49
 */
public class NET_FIND_VEHICLE_CONDITION extends NetSDKLib.SdkStructure {
    public int              nToken;                               // 查询令牌
    public 	int             nBeginNumber;                         // 查询起始序号 表示从beginNumber条记录开始，取count条记录返回；0<=beginNumber<= totalCount-1
    public 	int             nCount;                               // 每次获取的条数
    public 	byte[]          szReserved = new byte[128];           // 保留字段

    @Override
    public String toString() {
        return "NET_FIND_VEHICLE_CONDITION{" +
                "nToken=" + nToken +
                ", nBeginNumber=" + nBeginNumber +
                ", nCount=" + nCount +
                '}';
    }
}

