package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetStoragePortInfo 出参
*/
public class NET_OUT_GET_PORTINFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 总线的接口数目，包括扩展的总线
    */
    public int              nTotal;
    /**
     * 已挂载的硬盘的数量, 不包括坏盘
    */
    public int              nPlug;
    /**
     * 指示各个总线通道上是否挂载硬盘，包括坏盘
    */
    public int              nMask;
    /**
     * 指示各个通道上是否挂载了坏的硬盘
    */
    public int              nBadDisk;
    /**
     * 已挂载的IDE硬盘数
    */
    public int              nIDEDisk;
    /**
     * eSATA的接口总数
    */
    public int              nESATA;
    /**
     * 工作在USB2.0模式下的USB设备个数
    */
    public int              nUSB2;
    /**
     * 工作在USB3.0模式下的USB设备个数
    */
    public int              nUSB3;

    public NET_OUT_GET_PORTINFO() {
        this.dwSize = this.size();
    }
}

