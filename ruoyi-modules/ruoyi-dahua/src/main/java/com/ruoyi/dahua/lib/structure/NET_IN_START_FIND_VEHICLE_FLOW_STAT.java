package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_StartFindVehicleFlowStat 接口输入参数
*/
public class NET_IN_START_FIND_VEHICLE_FLOW_STAT extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 查询条件,参见结构体定义 {@link NET_VEHICLE_FLOW_STAT_CONDITION}
    */
    public NET_VEHICLE_FLOW_STAT_CONDITION stuCondition = new NET_VEHICLE_FLOW_STAT_CONDITION();

    public NET_IN_START_FIND_VEHICLE_FLOW_STAT() {
        this.dwSize = this.size();
    }
}

