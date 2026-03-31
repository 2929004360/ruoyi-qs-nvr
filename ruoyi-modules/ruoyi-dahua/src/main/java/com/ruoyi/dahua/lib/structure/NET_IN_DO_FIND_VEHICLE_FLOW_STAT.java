package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_DoFindVehicleFlowStat 输入参数
*/
public class NET_IN_DO_FIND_VEHICLE_FLOW_STAT extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 查询起始序号
    */
    public int              nBeginNumber;
    /**
     * 本次查询需要获取的条数，一次最多查64条记录
    */
    public int              nCount;

    public NET_IN_DO_FIND_VEHICLE_FLOW_STAT() {
        this.dwSize = this.size();
    }
}

