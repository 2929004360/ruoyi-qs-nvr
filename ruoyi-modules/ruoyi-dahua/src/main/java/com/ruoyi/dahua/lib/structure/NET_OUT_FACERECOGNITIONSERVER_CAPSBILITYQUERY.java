package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_GetDevCaps 对应的类型(NET_FACERECOGNITIONSE_CAPS)输出参数
 * @origin autoTool
 * @date 2023/07/31 09:25:34
 */
public class NET_OUT_FACERECOGNITIONSERVER_CAPSBILITYQUERY extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 是否支持多通道查找
	 */
    public int              bmultiFind;
	/**
	 * 最多支持添加自定义目标类型数量
	 */
    public int              nmaxFaceType;
	/**
	 * 目标检测框颜色能力
	 */
    public NET_FACEBOXCOLOR_CAPS stuFaceBoxColor = new NET_FACEBOXCOLOR_CAPS();
	/**
	 * 是否支持以图搜图数据推送
	 */
    public int              bAsynPush;
	/**
	 * 最大目标库数量, 0表示无效
	 */
    public int              nMaxGroupNum;
	/**
	 * 最大存储目标容量, 0表示无效
	 */
    public int              nMaxPersonNum;
	/**
	 * 批量导图每次接收图片的大小, 单位KB
	 */
    public int              nMultiAppendPicLength;
	/**
	 * 是否支持以图搜图 {@link com.ruoyi.dahua.lib.enumeration.EM_SUPPORT_SEARCH_BY_PIC}
	 */
    public int              emSupportSearchByPic;
	/**
	 * 以图搜图支持的目标类型掩码, emSupportSearchByPic为EM_SUPPORT_SEARCH_BY_PIC_SUPPORT时有效
	 * bit0:目标, bit1:人体, bit2:机动车, bit3:非机动车
	 */
    public int              dwSearchTypeMask;
	/**
	 * 单个图片最大大小的值，单位KB
	 */
    public int              nSinglePicMaxLength;
	/**
	 * 设备目标注册库是否支持一人多图
	 */
    public int              bPersonWithMultiFace;
	/**
	 * 是否支持使用目标库GroupID和人员UUID分别对目标库和人员进行管理
	 */
    public int              bSupportIDFromServer;
	/**
	 * 是否支持SMD属性特征以图搜图的类型掩码, bit0: 人脸, bit1: 人体, bit2: 机动车, bit3: 非机动车
	 */
    public int              nSupportSMDPicSearchMask;
	/**
	 * 查询条件与结果中的时间能否支持UTC时间格式 0: 不支持，1: 支持
	 */
    public int              nSupportRealUTC;
	/**
	 * 支持从大图检测小图的方法，1-支持detectMultiFace，0(或不存在该字段)-不确定,兼容老设备   
	 */
    public int              nDetectMethod;
    /**
     * 支持的电梯控制方式类型的集合个数
    */
    public int              nSupportLiftControlTypesCount;
    /**
     * 支持的电梯控制方式类型的集合,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_FACE_RECOGNITION_SUPPORT_LIFT_CONTROL_TYPES}
    */
    public int[]            emSupportLiftControlTypes = new int[8];

	public NET_OUT_FACERECOGNITIONSERVER_CAPSBILITYQUERY() {		
		this.dwSize = this.size();
	}
}

