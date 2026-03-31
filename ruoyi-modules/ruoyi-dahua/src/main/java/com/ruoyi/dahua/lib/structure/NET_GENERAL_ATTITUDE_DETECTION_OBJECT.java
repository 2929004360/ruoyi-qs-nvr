package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 检测到的物体信息
*/
public class NET_GENERAL_ATTITUDE_DETECTION_OBJECT extends NetSDKLib.SdkStructure
{
    /**
     * 目标ID
    */
    public int              nObjectID;
    /**
     * 物体类型
    */
    public byte[]           szObjectType = new byte[16];
    /**
     * 动作类型
    */
    public byte[]           szAttitudeType = new byte[16];
    /**
     * 包围盒,参见结构体定义 {@link NetSDKLib.NET_RECT}
    */
    public NetSDKLib.NET_RECT BoundingBox = new NetSDKLib.NET_RECT();
    /**
     * 智能物体全局唯一物体标识
    */
    public byte[]           szSerialUUID = new byte[22];
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[1022];

    public NET_GENERAL_ATTITUDE_DETECTION_OBJECT() {
    }
}

