package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * 事件类型EVENT_IVS_CROSSLINEDETECTION_EX(警戒线事件)对应的数据块描述信息
 */
public class DEV_EVENT_CROSSLINE_INFO_EX extends NetSDKLib.SdkStructure {
	/** 
	
	*/
    public int              dwSize;
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 事件名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 字节对齐
	 */
    public byte[]           bReserved1 = new byte[4];
	/**
	 * 时间戳(单位是毫秒)
	 */
    public float            PTS;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
	/**
	 * 事件ID
	 */
    public int              nEventID;
	/**
	 * 检测到的物体
	 */
    public NetSDKLib.NET_MSG_OBJECT_EX stuObject = new NetSDKLib.NET_MSG_OBJECT_EX();
	/**
	 * 事件对应文件信息
	 */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
	/**
	 * 规则检测线
	 */
    public NetSDKLib.DH_POINT[] DetectLine = (NetSDKLib.DH_POINT[]) new NetSDKLib.DH_POINT().toArray(20);
	/**
	 * 规则检测线顶点数
	 */
    public int              nDetectLineNum;
	/**
	 * 物体运动轨迹
	 */
    public NetSDKLib.DH_POINT[] TrackLine = (NetSDKLib.DH_POINT[]) new NetSDKLib.DH_POINT().toArray(20);
	/**
	 * 物体运动轨迹顶点数
	 */
    public int              nTrackLineNum;
	/**
	 * 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
	 */
    public byte             bEventAction;
	/**
	 * 表示入侵方向, 0-由左至右, 1-由右至左
	 */
    public byte             bDirection;
	/** 
	
	*/
    public byte[]           byReserved = new byte[1];
	/**
	 * 图片的序号, 同一时间内(精确到秒)可能有多张图片, 从0开始
	 */
    public byte             byImageIndex;
	/**
	 * 抓图标志(按位),具体见NET_RESERVED_COMMON
	 */
    public int              dwSnapFlagMask;
	/**
	 * 事件源设备上的index,-1表示数据无效,-1表示数据无效
	 */
    public int              nSourceIndex;
	/**
	 * 事件源设备唯一标识,字段不存在或者为空表示本地设备
	 */
    public byte[]           szSourceDevice = new byte[260];
	/**
	 * 事件触发累计次数
	 */
    public int              nOccurrenceCount;
	/**
	 * 检测到的物体信息个数
	 */
    public int              nMsgObjArrayCount;
	/**
	 * 检测到的物体信息数组指针
	 */
    public Pointer          pMsgObjArray;
	/**
	 * 智能事件公共信息
	 */
    public NetSDKLib.EVENT_INTELLI_COMM_INFO stuIntelliCommInfo = new NetSDKLib.EVENT_INTELLI_COMM_INFO();
	/**
	 * 全景广角图
	 */
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
	/**
	 * 检测到人的数量
	 */
    public int              nObjetcHumansNum;
	/**
	 * 检测的到人
	 */
    public NET_VAOBJECT_NUMMAN[] stuObjetcHumans = (NET_VAOBJECT_NUMMAN[]) new NET_VAOBJECT_NUMMAN().toArray(100);
	/**
	 * 规则编号,用于标示哪个规则触发的事件，缺省时默认为0
	 */
    public int              nRuleID;
	/**
	 * 事件级别
	 */
    public int              emEventType;
	/**
	 * 预置点的坐标和放大倍数
	 */
    public NET_PRESET_POSITION stPosition = new NET_PRESET_POSITION();
	/**
	 * 可见光横向视场角,单位度 实际角度乘以100
	 */
    public int              nVisibleHFOV;
	/**
	 * 可见光纵向视场角,单位度 实际角度乘以100
	 */
    public int              nVisibleVFOV;
	/**
	 * 当前报警通道的横向视场角，单位度，实际角度乘以100
	 */
    public int              nCurChannelHFOV;
	/**
	 * 当前报警通道的纵向视场角，单位度，实际角度乘以100
	 */
    public int              nCurChannelVFOV;
	/**
	 * 图片信息个数
	 */
    public int              nImageNum;
	/**
	 * 图片信息数组,结构体NET_IMAGE_INFO_EX2数组指针
	 */
    public Pointer          pImageArray;
	/**
	 * 车的后视镜状态，-1: 未知, 0: 正常, 1: 不正常(如数量缺失等)
	 */
    public int              nCarMirrorStatus;
	/**
	 * 车的车灯状态,-1: 未知, 0: 正常, 1:不正常(如灯未亮等)
	 */
    public int              nCarLightStatus;
	/**
	 * 船只物体个数
	 */
    public int              nObjectBoatsNum;
	/**
	 * 船只物品信息
	 */
    public NET_BOAT_OBJECT[] stuBoatObjects = new NET_BOAT_OBJECT[100];
	/**
	 * 车道/航道方向, 0:未知, 1:上行, 2:下行
	 */
    public int              nUpDownGoing;
    /**
     * 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
	/**
	 * 预留字节
	 */
    public byte[]           byReserved1 = new byte[452];

	public DEV_EVENT_CROSSLINE_INFO_EX() {
		for (int i = 0; i < stuBoatObjects.length; i++) {
			stuBoatObjects[i] = new NET_BOAT_OBJECT();
		}
	}
}

