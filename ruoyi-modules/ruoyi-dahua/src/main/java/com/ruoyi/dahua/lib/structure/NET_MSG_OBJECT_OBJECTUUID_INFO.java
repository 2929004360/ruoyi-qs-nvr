package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 智能物体全局唯一物体标识
*/
public class NET_MSG_OBJECT_OBJECTUUID_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 物体全局唯一性标识
    */
    public byte[]           szUUID = new byte[48];
    /**
     * 扩展字节
    */
    public byte[]           szReserved = new byte[208];

    public NET_MSG_OBJECT_OBJECTUUID_INFO() {
    }
}

