package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 物体截图
*/
public class NET_SMART_MOTION_EQUIPMENT_OBJECT_IMAGE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 物体小图的索引, 不存在时为~0u
    */
    public int              nIndexInData;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[124];

    public NET_SMART_MOTION_EQUIPMENT_OBJECT_IMAGE_INFO() {
    }
}

