package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_TRAFFIC_DRIVER_NO_BELT (未系安全带报警事件)对应的数据块描述信息
*/
public class DEV_EVENT_TRAFFIC_DRIVER_NO_BELT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 0:脉冲,1:开始, 2:停止
    */
    public int              nAction;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 相对事件时间戳,(单位是毫秒)
    */
    public double           dbPTS;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 司机ID
    */
    public byte[]           szDriverID = new byte[32];
    /**
     * GPS信息,参见结构体定义 {@link NetSDKLib.NET_GPS_STATUS_INFO}
    */
    public NetSDKLib.NET_GPS_STATUS_INFO stuGPSStatus = new NetSDKLib.NET_GPS_STATUS_INFO();
    /**
     * 违章关联视频FTP上传路径
    */
    public byte[]           szVideoPath = new byte[256];
    /**
     * 违章关联的多个视频信息个数
    */
    public int              nRelatingVideoInfoNum;
    /**
     * 违章关联的多个视频信息数组，最多支持16个视频,参见结构体定义 {@link NET_RELATING_VIDEO_INFO}
    */
    public NET_RELATING_VIDEO_INFO[] stuRelatingVideoInfo = new NET_RELATING_VIDEO_INFO[16];
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[768];

    public DEV_EVENT_TRAFFIC_DRIVER_NO_BELT_INFO() {
        for(int i = 0; i < stuRelatingVideoInfo.length; i++){
            stuRelatingVideoInfo[i] = new NET_RELATING_VIDEO_INFO();
        }
    }
}

