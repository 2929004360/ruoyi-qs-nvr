package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 291189
 * @description 仪表检测事件, 对应事件类型 EVENT_IVS_DIALRECOGNITION
 * @date 2022/06/28 19:24:08
 */
public class DEV_EVENT_DIALRECOGNITION_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 视频通道号,从0开始
	 */
    public int              nChannelID;
	/**
	 * 预置点ID,如果普通IPC则为0
	 */
    public int              nPresetID;
	/**
	 * 任务ID.添加时设备端生成
	 */
    public byte[]           szTaskID = new byte[64];
	/**
	 * 仪表类型 {@link com.ruoyi.dahua.lib.enumeration.EM_INSTRUMENT_TYPE}
	 */
    public int              emType;
	/**
	 * 返回的图片信息个数
	 */
    public int              nRetImageInfoNum;
	/**
	 * 图片信息
	 */
    public NET_IMAGE_INFO[] stuImgaeInfo = new NET_IMAGE_INFO[8];
	/**
	 * 检测结果，根据Type的不同，格式也不同
	 */
    public byte[]           szDialResult = new byte[2048];
	/**
	 * 原始图片在二进制数据块中的偏移
	 */
    public int              nOriginalImageOffset;
	/**
	 * 原始图片大小,单位：字节
	 */
    public int              nOriginalImageLength;
	/**
	 * 告警类型：0-该字段无效;1-数值异常;2-定时上报; 3-高阀值报警; 4-低阀值报警
	 */
    public int              nAlarmType;
	/**
	 * 仪表检测具体子类型
	 */
    public byte[]           szDialSubType = new byte[32];
	/**
	 * 报警上限阈值
	 */
    public float            fUpperThreshold;
	/**
	 * 报警下限阈值
	 */
    public float            fLowerThreshold;
	/**
	 * 包围盒
	 */
    public NET_RECT[]       stuBoundingBox = new NET_RECT[128];
	/**
	 * 包围盒个数
	 */
    public int              nRetBoundingBoxNum;
    /**
     * 单位
    */
    public byte[]           szUnit = new byte[8];
    /**
     * 自定义结果上报，区别于协议定好的结果上报字段DialResult
    */
    public byte[]           szDialCustomResult = new byte[128];
    /**
     * 多个检测到的仪表目标信息，内存由NetSDK申请释放,参见结构体定义 {@link NET_DIAL_OBJECT_INFO}
    */
    public Pointer          pstuDialObjects;
    /**
     * 检测到的仪表目标信息有效个数，最多100个
    */
    public int              nDialObjectsNum;
	/**
	 * 预留字节
	 */
    public byte[]           szReserved = new byte[828-NetSDKLib.POINTERSIZE];

	public DEV_EVENT_DIALRECOGNITION_INFO() {
		for (int i = 0; i < stuImgaeInfo.length; i++) {
			stuImgaeInfo[i] = new NET_IMAGE_INFO();
		}
	}
}

