package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_StartFindVehicleFlowStat 接口输出参数
*/
public class NET_OUT_START_FIND_VEHICLE_FLOW_STAT extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 符合此次查询条件的结果总条数
    */
    public int              nTotalCount;

    public NET_OUT_START_FIND_VEHICLE_FLOW_STAT() {
        this.dwSize = this.size();
    }
}

