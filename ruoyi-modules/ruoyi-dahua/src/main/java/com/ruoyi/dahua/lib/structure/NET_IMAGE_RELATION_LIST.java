package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 一张大图检测结果列表
 * @date 2023/04/20 15:18:00
 */
public class NET_IMAGE_RELATION_LIST extends NetSDKLib.SdkStructure {
	/**
	 * base64特征值
	 */
    public Pointer          pszFeature;
	/**
	 * base64特征值长度
	 */
    public int              nFeatureLen;
	/**
	 * 特征ID
	 */
    public byte[]           szFeatureID = new byte[64];
	/**
	 * 目标数据
	 */
    public NetSDKLib.NET_FACE_DATA stuFaceData = new NetSDKLib.NET_FACE_DATA();
	/**
	 * 人体属性
	 */
    public HUMAN_ATTRIBUTES_INFO stuHumanAttributes = new HUMAN_ATTRIBUTES_INFO();
	/**
	 * 非机动车信息
	 */
    public NetSDKLib.VA_OBJECT_NONMOTOR stuNonMotor = new NetSDKLib.VA_OBJECT_NONMOTOR();
	/**
	 * 特征版本号
	 */
    public byte[]           szAlgorithmVersion = new byte[32];
	/**
	 * 厂商
	 */
    public byte[]           szVendor = new byte[32];
	/**
	 * 目标类型 {@link EM_OBJECT_TYPE}
	 */
    public int              emObjectType;
	/**
	 * 矩形区域
	 */
    public NetSDKLib.DH_POINT[] stuRectPoint = new NetSDKLib.DH_POINT[2];
	/**
	 * 机动车属性
	 */
    public NET_HISTORY_TRAFFIC_CAR_INFO stuHistoryTrafficCar = new NET_HISTORY_TRAFFIC_CAR_INFO();
	/**
	 * 保留字节
	 */
    public byte[]           bReserved = new byte[1024];

	public NET_IMAGE_RELATION_LIST() {
		for (int i = 0; i < stuRectPoint.length; i++) {
			stuRectPoint[i] = new NetSDKLib.DH_POINT();
		}
	}
}

