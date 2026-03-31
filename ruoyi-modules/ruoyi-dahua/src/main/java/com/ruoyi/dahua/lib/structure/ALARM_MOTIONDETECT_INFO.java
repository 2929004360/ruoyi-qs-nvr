package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 报警事件类型DH_EVENT_MOTIONDETECT(视频移动侦测事件)对应的数据描述信息
*/
public class ALARM_MOTIONDETECT_INFO extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 时间戳(单位是毫秒)
    */
    public double           PTS;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX UTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
    */
    public int              nEventAction;
    /**
     * 动检区域个数
    */
    public int              nRegionNum;
    /**
     * 动检区域信息,参见结构体定义 {@link NET_MOTIONDETECT_REGION_INFO}
    */
    public NET_MOTIONDETECT_REGION_INFO[] stuRegion = new NET_MOTIONDETECT_REGION_INFO[32];
    /**
     * 智能动检是否使能
    */
    public int              bSmartMotionEnable;
    /**
     * 动检触发类型个数
    */
    public int              nDetectTypeNum;
    /**
     * 动检触发类型, 当nRegionNum大于0时，和stuRegion数组一一对应, , 若nRegionNum为0，触发区域未知，不与窗口绑定，默认第一个元素表示触发类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_MOTION_DETECT_TYPE}
    */
    public int[]            emDetectType = new int[32];
    /**
     * 事件公共扩展字段结构体,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * GPS信息,参见结构体定义 {@link NetSDKLib.NET_GPS_STATUS_INFO}
    */
    public NetSDKLib.NET_GPS_STATUS_INFO stuGPSStatusInfo = new NetSDKLib.NET_GPS_STATUS_INFO();

    public ALARM_MOTIONDETECT_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuRegion.length; i++){
            stuRegion[i] = new NET_MOTIONDETECT_REGION_INFO();
        }
    }
}

