package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型EVENT_IVS_PARKINGSPACE_STATUS(停车位状态事件)对应的数据块描述信息
*/
public class DEV_EVENT_PARKINGSPACE_STATUS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 事件ID
    */
    public int              nEventID;
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
     * 事件对应文件信息,参见结构体定义 {@link NetSDKLib.NET_EVENT_FILE_INFO}
    */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
    /**
     * 视频分析的数据源地址
    */
    public int              nSource;
    /**
     * 帧序号
    */
    public int              nFrameSequence;
    /**
     * 用于标记抓拍帧
    */
    public int              nMark;
    /**
     * 车位的状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_PARKINGSPACE_STATUS}
    */
    public int              emParkingSpaceStatus;
    /**
     * 车位(地磁)编号
    */
    public byte[]           szParkingNum = new byte[32];
    /**
     * 车牌号码
    */
    public byte[]           szText = new byte[32];
    /**
     * 车门状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_CAR_DOOR_STATE}
    */
    public int              emCarDoorState;
    /**
     * 车厢作业人数
    */
    public int              nWorkerNum;
    /**
     * 车厢状态检测区域, 使用相对坐标体系，取值均为0-8191,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT[] stuBoxcarDetectRegion = new NetSDKLib.NET_POINT[4];
    /**
     * 车厢状态检测区域个数
    */
    public int              nBoxcarDetectRegionNum;
    /**
     * 车厢货物装载率，取值范围：0-100之间
    */
    public int              nLoadingRate;
    /**
     * 车厢内卸货长度，单位cm
    */
    public int              nUnLoadLength;
    /**
     * 人员闯入报警标志,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_PERSON_INTRUDE_STATUS}
    */
    public int              emPersonIntrudeStatus;
    /**
     * 预留字节
    */
    public byte[]           byReserved = new byte[472];

    public DEV_EVENT_PARKINGSPACE_STATUS_INFO() {
        for(int i = 0; i < stuBoxcarDetectRegion.length; i++){
            stuBoxcarDetectRegion[i] = new NetSDKLib.NET_POINT();
        }
    }
}

