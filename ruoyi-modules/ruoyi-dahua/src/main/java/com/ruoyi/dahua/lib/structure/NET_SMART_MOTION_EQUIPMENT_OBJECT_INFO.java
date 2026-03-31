package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 设备运行状况检测信息
*/
public class NET_SMART_MOTION_EQUIPMENT_OBJECT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 设备位置坐标, 取值均为0-8191,参见结构体定义 {@link NET_UINT_RECT}
    */
    public NET_UINT_RECT    stuRect = new NET_UINT_RECT();
    /**
     * 物体截图,参见结构体定义 {@link NET_SMART_MOTION_EQUIPMENT_OBJECT_IMAGE_INFO}
    */
    public NET_SMART_MOTION_EQUIPMENT_OBJECT_IMAGE_INFO stuImage = new NET_SMART_MOTION_EQUIPMENT_OBJECT_IMAGE_INFO();
    /**
     * 设备运行状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_SMART_MOTION_EQUIPMENT_MOTION_STATUS}
    */
    public int              emMotionStatus;
    /**
     * 报警所来自的配置的报警区域id
    */
    public int              nAlarmRegionId;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[256];

    public NET_SMART_MOTION_EQUIPMENT_OBJECT_INFO() {
    }
}

