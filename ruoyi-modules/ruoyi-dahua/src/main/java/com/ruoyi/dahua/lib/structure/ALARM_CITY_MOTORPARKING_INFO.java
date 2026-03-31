package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 城市机动车违停事件信息(对应 DH_ALARM_CITY_MOTORPARKING)
*/
public class ALARM_CITY_MOTORPARKING_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 事件动作: 0:脉冲 1:开始 2:停止
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
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX UTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 智能事件所属大类,参见枚举定义 {@link NetSDKLib.EM_CLASS_TYPE}
    */
    public int              emClassType;
    /**
     * 检测到的物体个数
    */
    public int              nObjectNum;
    /**
     * 检测到的物体,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT}
    */
    public NetSDKLib.NET_MSG_OBJECT[] stuObjects = new NetSDKLib.NET_MSG_OBJECT[64];
    /**
     * 事件触发的预置名称
    */
    public byte[]           szPresetName = new byte[64];
    /**
     * 事件触发的预置点号
    */
    public int              nPresetID;
    /**
     * 检测区域顶点数
    */
    public int              nDetectRegionNum;
    /**
     * 检测区域,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT[] DetectRegion = new NetSDKLib.NET_POINT[20];
    /**
     * 事件关联ID,同一个物体或图片生成多个事件时SourceID相同
    */
    public byte[]           szSourceID = new byte[32];
    /**
     * 违停持续时长，单位：秒 缺省值0表示无意义
    */
    public int              nParkingDuration;
    /**
     * stuPtzPosition 是否有效
    */
    public int              bPtzPosition;
    /**
     * 云台信息,参见结构体定义 {@link PTZ_NORMALIZED_POSITION_UNIT}
    */
    public PTZ_NORMALIZED_POSITION_UNIT stuPtzPosition = new PTZ_NORMALIZED_POSITION_UNIT();
    /**
     * 车辆状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_CITYMOTOR_STATUS}
    */
    public int              emMotorStatus;
    /**
     * 事件公共扩展字段结构体,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 检测区名称
    */
    public byte[]           szDetectRegionName = new byte[128];
    /**
     * 检测区编号
    */
    public int              nDetectRegionNumber;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[1896];

    public ALARM_CITY_MOTORPARKING_INFO() {
        for(int i = 0; i < stuObjects.length; i++){
            stuObjects[i] = new NetSDKLib.NET_MSG_OBJECT();
        }
        for(int i = 0; i < DetectRegion.length; i++){
            DetectRegion[i] = new NetSDKLib.NET_POINT();
        }
    }
}

