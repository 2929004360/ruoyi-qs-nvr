package com.ruoyi.dahua.lib.structure.customize;

import com.ruoyi.dahua.lib.NetSDKLib;

import static com.ruoyi.dahua.lib.NetSDKLib.MAX_PATH;
import static com.ruoyi.dahua.lib.NetSDKLib.NET_MAX_DETECT_REGION_NUM;

/**
 * 停船检测事件结构体
 * <p>
 * ERR210428143 重型云台 水面预警事件 专用结构体
 * <p>
 * 注意: 其他项目不要使用, 通用的停车检测事件结构体见:
 * {@link NetSDKLib.DEV_EVENT_PARKINGDETECTION_INFO}
 *
 * @author 47040
 * @since Created in 2021/5/13 16:57
 */
public class DEV_EVENT_PARKINGDETECTION_INFO extends NetSDKLib.SdkStructure {
    /**
     * 通道号
     */
    public int nChannelID;
    /**
     * 事件名称
     */
    public byte[] szName = new byte[128];
    /**
     * 字节对齐
     */
    public byte[] bReserved1 = new byte[4];
    /**
     * 时间戳(单位是毫秒)
     */
    public double PTS;
    /**
     * 事件发生的时间
     */
    public NetSDKLib.NET_TIME_EX UTC;
    /**
     * 事件ID
     */
    public int nEventID;
    /**
     * 检测到的物体
     */
    public NetSDKLib.NET_MSG_OBJECT stuObject;
    /**
     * 规则检测区域顶点数
     */
    public int nDetectRegionNum;
    /**
     * 规则检测区域
     */
    public NetSDKLib.NET_POINT[] DetectRegion = new NetSDKLib.NET_POINT[NET_MAX_DETECT_REGION_NUM];
    /**
     * 事件对应文件信息
     */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo;
    /**
     *
     */
    public byte bEventAction;
    /**
     *
     */
    public byte[] byReserved = new byte[2];
    /**
     * 图片的序号, 同一时间内(精确到秒)可能有多张图片, 从 0 开始
     */
    public byte byImageIndex;
    /**
     * 抓图标志(按位),具体见NET_RESERVED_COMMON
     */
    public int dwSnapFlagMask;
    /**
     * 事件源设备上的index,-1表示数据无效
     */
    public int nSourceIndex;
    /**
     * 事件源设备唯一标识,字段不存在或者为空表示本地设备
     */
    public byte[] szSourceDevice = new byte[MAX_PATH];
    /**
     * 事件触发累计次数
     */
    public int nOccurrenceCount;
    /**
     * 智能事件公共信息
     */
    public EVENT_INTELLI_COMM_INFO stuIntelliCommInfo;
    /**
     * 扩展信息
     */
    public NetSDKLib.NET_EXTENSION_INFO stuExtensionInfo;
    /**
     * 车位名称
     */
    public byte[] szCustomParkNo = new byte[64];

    /**
     * 热成像横向视场角,单位度,实际角度乘以100 (ERR210428143 专用)
     */
    public int nThermoHFOV;
    /**
     * 热成像纵向视场角,单位度,实际角度乘以100 (ERR210428143 专用)
     */
    public int nThermoVFOV;
    /**
     * 船高 (ERR210428143 专用, 其他项目不要使用)
     */
    public int nBoatHeight;
    /**
     * 船长 (ERR210428143 专用, 其他项目不要使用)
     */
    public int nBoatLength;
    /**
     * 船速 (ERR210428143 专用, 其他项目不要使用)
     */
    public int nBoatSpeed;
    /**
     * 船距 (ERR210428143 专用, 其他项目不要使用)
     */
    public int nBoatDistance;
    /**
     * 保留字节,留待扩展
     */
    public byte[] bReserved = new byte[396];

    public DEV_EVENT_PARKINGDETECTION_INFO() {
        for (int i = 0; i < DetectRegion.length; ++i) {
            DetectRegion[i] = new NetSDKLib.NET_POINT();
        }
    }

}
