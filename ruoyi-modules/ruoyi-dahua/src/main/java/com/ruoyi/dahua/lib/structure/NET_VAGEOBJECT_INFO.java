package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 251823
 * @description 视频分析泛型物体类型
 * @date 2021/10/09
 */
public class NET_VAGEOBJECT_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  物体ID，每个ID表示一个唯一的物体
     */
    public int              nObjectID;
    /**
     *  模型支持的泛类型物体类型编号
     */
    public int              nTypeIndex;
    /**
     *  模型支持的泛类型物体类型
     */
    public byte[]           szTypeName = new byte[32];
    /**
     *  置信度,值越大表示置信度越高 0~100
     */
    public int              nConfidence;
    /**
     *  物体动作支持类型，参考枚举{ @link ENUM_VAGEOBJECT_ACTION}
     */
    public int              emAction;
    /**
     *  包围盒 矩形类型，当物体类型为车辆、车牌时为0~8191相对坐标
     */
    public NetSDKLib.NET_RECT stuBoundingBox;
    /**
     *  包围盒 绝对坐标
     */
    public NetSDKLib.NET_RECT stuOriginalBoundingBox;
    /**
     *  物体型心（不是包围盒中心）, 二维空间点类型
     */
    public NetSDKLib.NET_POINT stuCenter;
    /**
     *  主要颜色是否有效
     */
    public int              bMainColor;
    /**
     *  物体主要颜色, rgb
     */
    public byte[]           byMainColor = new byte[4];
    /**
     *  物体截图
     */
    public NET_VAGEOBJECT_IMAGE stuImage;
    /**
     *  当前时间戳（物体抓拍或识别时，会将此识别智能帧附在一个视频帧或jpeg图片中，此帧所在原始视频中的出现时间）
     */
    public NET_TIME_EX      stuCurrentTimeStamp;
    /**
     *  保留字节
     */
    public byte[]           byReserved = new byte[1024];
}

