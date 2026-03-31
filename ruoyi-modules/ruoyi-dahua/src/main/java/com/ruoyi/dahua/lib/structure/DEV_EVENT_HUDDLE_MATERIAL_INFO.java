package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.EVENT_INTELLI_COMM_INFO;
import com.ruoyi.dahua.lib.NetSDKLib.NET_MSG_OBJECT;
import com.ruoyi.dahua.lib.NetSDKLib.NET_POINT;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_HUDDLE_MATERIAL (乱放物料检测事件)对应的数据块描述信息
 * @date 2020/11/19
 */
public class DEV_EVENT_HUDDLE_MATERIAL_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 0:脉冲 1:开始 2:停止
	 */
    public int              nAction;
	/**
	 * 事件名称
	 */
    public byte[]           szName = new byte[NetSDKLib.NET_COMMON_STRING_128];
	/**
	 * 时间戳(单位是毫秒)
	 */
    public double           PTS;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      UTC;
	/**
	 * 事件ID
	 */
    public int              nEventID;
	/**
	 * 事件对应文件信息
	 */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo;
	/**
	 * 规则检测区域
	 */
    public NET_POINT[]      DetectRegion = new NET_POINT[NetSDKLib.NET_MAX_DETECT_REGION_NUM];
	/**
	 * 规则检测区域顶点数
	 */
    public int              nDetectRegionNum;
	/**
	 * 检测到的物体
	 */
    public NET_MSG_OBJECT[] stuObjects = new NET_MSG_OBJECT[NetSDKLib.HDBJ_MAX_OBJECTS_NUM];
	/**
	 * 检测到的物体个数
	 */
    public int              nObjectNum;
	/**
	 * 智能事件公共信息
	 */
    public EVENT_INTELLI_COMM_INFO stuIntelliCommInfo;
	/**
	 * 事件触发的预置名称
	 */
    public byte[]           szPresetName = new byte[NetSDKLib.NET_COMMON_STRING_64];
	/**
	 * 违法持续时长，单位：秒，缺省值0表示无意义
	 */
    public int              nViolationDuration;
	/**
	 * 事件关联ID,同一个物体或图片生成多个事件时SourceID相同
	 */
    public byte[]           szSourceID = new byte[NetSDKLib.NET_COMMON_STRING_32];
	/**
	 * 抓图标志(按位),具体见NET_RESERVED_COMMON
	 */
    public int              dwSnapFlagMask;
	/**
	 * 全景图图片信息,事件前2~5s抓图
	 */
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
	/**
	 * 图片信息数组,指针对应NET_IMAGE_INFO_EX2数组
	 */
    public Pointer          pstuImageInfo;
	/**
	 * 图片信息个数
	 */
    public int              nImageInfoNum;
	/**
	 * 合成图,指针对应SCENE_IMAGE_INFO_EX数组
	 */
    public Pointer          pstuMosaicImage;
	/**
	 * 合成图个数
	 */
    public int              nMosaicImageNum;
	/**
	 * 事件发生前抓图，指针对应SCENE_IMAGE_INFO_EX数组
	 */
    public Pointer          pstuAdvanceImage;
	/**
	 * 事件发生前抓图个数
	 */
    public int              nAdvanceImageNum;
    /**
     * 车速，单位km/h
    */
    public int              nVehicleSpeed;
    /**
     * 航向角 以正北方向为基准输出车辆运动方向同正北方向的角度:范围 0~360,顺时针正,单位为度
    */
    public double           dbHeadingAngle;
    /**
     * 经度,格式:度,分,秒(秒为浮点数)
    */
    public double[]         dbLongitude = new double[3];
    /**
     * 纬度,格式:度,分,秒(秒为浮点数)
    */
    public double[]         dbLatitude = new double[3];
	/**
	 * 保留字节,留待扩展
	 */
    public byte[]           byReserved = new byte[3232-NetSDKLib.POINTERSIZE*3];

	public DEV_EVENT_HUDDLE_MATERIAL_INFO() {
		for (int i = 0; i < stuObjects.length; ++i) {
			stuObjects[i] = new NET_MSG_OBJECT();
		}

		for (int i = 0; i < DetectRegion.length; ++i) {
			DetectRegion[i] = new NET_POINT();
		}
	}
}

