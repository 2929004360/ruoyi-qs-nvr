package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 车辆进出虚拟线圈状态事件 (对应 DH_ALARM_TRAFFIC_CAR_PASSING)
*/
public class ALARM_TRAFFIC_CAR_PASSING_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 事件动作 0:脉冲
    */
    public int              nAction;
    /**
     * 通道号
    */
    public int              nChannel;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 事件发生的时间，设备本地时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件发生时间，表示毫秒
    */
    public int              nUTCMS;
    /**
     * 时区索引，参见时区列表DH_TIME_ZONE_TYPE
    */
    public int              nTimeZone;
    /**
     * 视频分析帧序号
    */
    public int              nFrameSequence;
    /**
     * 车辆进出个数
    */
    public int              nCarPassingCount;
    /**
     * 车辆进出信息,参见结构体定义 {@link NET_CAR_PASSING_INFO}
    */
    public NET_CAR_PASSING_INFO[] stuCarPassing = new NET_CAR_PASSING_INFO[16];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public ALARM_TRAFFIC_CAR_PASSING_INFO() {
        for(int i = 0; i < stuCarPassing.length; i++){
            stuCarPassing[i] = new NET_CAR_PASSING_INFO();
        }
    }
}

