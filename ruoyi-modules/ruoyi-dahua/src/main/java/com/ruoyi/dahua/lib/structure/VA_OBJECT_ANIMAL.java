package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @version 1.0
 * @description 视频分析物体(动物)
 * @date 2022/03/31
 */
public class VA_OBJECT_ANIMAL extends NetSDKLib.SdkStructure {
    /**
     *  物体ID, 每个ID表示一个唯一的物体
     */
    public int              nObjectID;
    /**
     *  目标动物类型
     */
    public int              emCategory;
    /**
     *  包围盒 动物对象在全景图中的框坐标,为0~8191相对坐标
     */
    public NET_RECT         stuBoundingBox;
    /**
     *  动物对象重量，单位:g
     */
    public int              nObjectWeight;
    /**
     *  物体截图
     */
    public NET_OBJECT_IMAGE_INFO stuImage;
    /**
     *  运动状态,参考枚举{ @link ENUM_MOTION_STATUS }
     */
    public int              emMoveStatus;
    /**
     *  区域内状态,参考枚举{ @link ENUM_IN_REGION_STATUS }
     */
    public int              emInRegionStatus;
    /**
     *  结果类型 0-实时 1-非实时
     */
    public int              nResultType;
    /**
     *  保留字节,留待扩展
     */
    public byte[]           bReserved = new byte[1024];
}

