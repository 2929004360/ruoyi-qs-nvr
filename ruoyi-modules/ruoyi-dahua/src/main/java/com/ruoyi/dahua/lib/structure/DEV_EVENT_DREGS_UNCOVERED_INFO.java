package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

import static com.ruoyi.dahua.lib.NetSDKLib.NET_MAX_DETECT_REGION_NUM;

/**
 * @author ： 260611
 * @description ： 事件类型 EVENT_IVS_DREGS_UNCOVERED(渣土车未遮盖载货检测事件)对应的数据块描述信息
 * @since ： Created in 2022/01/18 14:34
 */

public class DEV_EVENT_DREGS_UNCOVERED_INFO extends NetSDKLib.SdkStructure {
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 0:脉冲
     */
    public int              nAction;
    /**
     * 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * 时间戳(单位是毫秒)
     */
    public double           PTS;
    /**
     * 事件发生的时间
     */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
    /**
     * 事件ID
     */
    public int              nEventID;
    /**
     * 智能事件所属大类
     */
    public int              emClassType;
    /**
     * 检测区域顶点数
     */
    public int              nDetectRegionNum;
    /**
     * 检测区域,[0,8191]
     */
    public NetSDKLib.NET_POINT[] stuDetectRegion = (NetSDKLib.NET_POINT[]) new NetSDKLib.NET_POINT().toArray(NET_MAX_DETECT_REGION_NUM);
    /**
     * 车辆信息
     */
    public DREGS_UNCOVERED_VEHICLE_INFO stuVehicleInfo = new DREGS_UNCOVERED_VEHICLE_INFO();
    /**
     * 预留字节
     */
    public byte[]           byReserved = new byte[1024];
}

