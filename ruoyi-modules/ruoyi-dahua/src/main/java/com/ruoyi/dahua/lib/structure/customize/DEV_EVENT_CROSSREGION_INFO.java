package com.ruoyi.dahua.lib.structure.customize;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_CAPTURE_PROCESS_END_TYPE;
import com.ruoyi.dahua.lib.enumeration.EM_TRIGGER_TYPE;

import static com.ruoyi.dahua.lib.NetSDKLib.*;

/**
 * 船只的绊线入侵事件信息
 * <p>
 * ERR210428143 水面预警事件 专用结构体
 * <p>
 * 注意: 其他项目不要使用, 通用的绊线入侵事件结构体见:
 * {@link NetSDKLib.DEV_EVENT_CROSSREGION_INFO}
 *
 * @author 47040
 * @since Created in 2021/5/13 17:35
 */
public class DEV_EVENT_CROSSREGION_INFO extends SdkStructure {

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
    public NET_TIME_EX UTC;
    /**
     * 事件ID
     */
    public int nEventID;
    /**
     * 检测到的物体
     */
    public NET_MSG_OBJECT stuObject;
    /**
     * 事件对应文件信息
     */
    public NET_EVENT_FILE_INFO stuFileInfo;
    /**
     * 规则检测区域
     */
    public NET_POINT[] DetectRegion = (NET_POINT[]) new NET_POINT().toArray(NET_MAX_DETECT_REGION_NUM);
    /**
     * 规则检测区域顶点数
     */
    public int nDetectRegionNum;
    /**
     * 物体运动轨迹
     */
    public NET_POINT[] TrackLine = (NET_POINT[]) new NET_POINT().toArray(NET_MAX_TRACK_LINE_NUM);
    /**
     * 物体运动轨迹顶点数
     */
    public int nTrackLineNum;
    /**
     * 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
     */
    public byte bEventAction;
    /**
     * 表示入侵方向, 0-进入, 1-离开,2-出现,3-消失
     */
    public byte bDirection;
    /**
     * 表示检测动作类型,0-出现 1-消失 2-在区域内 3-穿越区域
     */
    public byte bActionType;
    /**
     * 图片的序号, 同一时间内(精确到秒)可能有多张图片, 从0开始
     */
    public byte byImageIndex;
    /**
     * 抓图标志(按位),具体见NET_RESERVED_COMMON ,
     * 0位:"*",1位:"Timing",2位:"Manual",
     * 3位:"Marked",4位:"Event",5位:"Mosaic",6位:"Cutout"
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
     * 事件触发累计次数, int 类型
     */
    public int nOccurrenceCount;
    /**
     * 货物通道信息
     */
    public NET_CUSTOM_INFO stuCustom;
    /**
     * 扩展信息
     */
    public NET_EXTENSION_INFO stuExtensionInfo;
    /**
     * 智能事件规则编号，用于标示哪个规则触发的事件
     */
    public int nRuleID;
    /**
     * 热成像横向视场角,单位度,实际角度乘以100 (ERR210428143 专用, 其他项目不要使用)
     */
    public int nThermoHFOV;
    /**
     * 热成像纵向视场角,单位度,实际角度乘以100 (ERR210428143 专用, 其他项目不要使用)
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
     * 保留字节,留待扩展.
     */
    public byte[] bReserved = new byte[300];
    /**
     * 检测到的物体个数
     */
    public int nObjectNum;
    /**
     * 检测到的物体
     */
    public NET_MSG_OBJECT[] stuObjectIDs = (NET_MSG_OBJECT[]) new NET_MSG_OBJECT().toArray(NET_MAX_OBJECT_LIST);
    /**
     * 轨迹数(与检测到的物体个数  nObjectNum 对应)
     */
    public int nTrackNum;
    /**
     * 轨迹信息(与检测到的物体对应)
     */
    public NET_POLY_POINTS[] stuTrackInfo = (NET_POLY_POINTS[]) new NET_POLY_POINTS().toArray(NET_MAX_OBJECT_LIST);
    /**
     * 智能事件公共信息
     */
    public EVENT_INTELLI_COMM_INFO stuIntelliCommInfo;
    /**
     * 全景广角图
     */
    public SCENE_IMAGE_INFO_EX stuSceneImage;
    /**
     * 检测到人的数量
     */
    public int nObjetcHumansNum;
    /**
     * 检测的到人
     */
    public NET_VAOBJECT_NUMMAN[] stuObjetcHumans = (NET_VAOBJECT_NUMMAN[]) new NET_VAOBJECT_NUMMAN().toArray(100);
    /**
     * 车身信息
     */
    public NET_MSG_OBJECT stuVehicle;
    /**
     * 触发类型,参考 {@link EM_TRIGGER_TYPE}
     */
    public int emTriggerType;
    /**
     * 标记抓拍帧
     */
    public int nMark;
    /**
     * 视频分析的数据源地址
     */
    public int nSource;
    /**
     * 视频分析帧序号
     */
    public int nFrameSequence;
    /**
     * 抓拍过程,参考 {@link EM_CAPTURE_PROCESS_END_TYPE}
     */
    public int emCaptureProcess;
    /**
     * 交通车辆信息
     */
    public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;
    /**
     * 公共信息
     */
    public EVENT_COMM_INFO stuCommInfo;

}
