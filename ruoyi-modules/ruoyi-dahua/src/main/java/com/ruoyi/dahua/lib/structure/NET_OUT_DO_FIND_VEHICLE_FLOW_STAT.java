package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_DoFindVehicleFlowStat 输出参数
*/
public class NET_OUT_DO_FIND_VEHICLE_FLOW_STAT extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 查询到的条数
    */
    public int              nFound;
    /**
     * 车流量统计结果信息最大个数
    */
    public int              nMaxInfoNum;
    /**
     * 车流量统计结果信息返回个数
    */
    public int              nRetInfoNum;
    /**
     * 车流量统计结果信息，内存由用户申请释放,参见结构体定义 {@link NET_VEHICLE_FLOW_STAT_INFO}
    */
    public Pointer          pstuInfo;

    public NET_OUT_DO_FIND_VEHICLE_FLOW_STAT() {
        this.dwSize = this.size();
    }
}

