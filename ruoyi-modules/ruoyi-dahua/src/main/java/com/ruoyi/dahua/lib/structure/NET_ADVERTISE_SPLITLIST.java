package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 广告分屏模式列表
*/
public class NET_ADVERTISE_SPLITLIST extends NetSDKLib.SdkStructure
{
    /**
     * 广告分屏模式：0：不分屏1：广告显示在前（左边或者上边）2：广告显示在后（右边或者下边）3.预览模式，只展示上传的广告图片
    */
    public int              nAdvertiseSplitID;
    /**
     * 广告分屏模式名称，设备和web通过显示广告模式类型
    */
    public byte[]           szAdvertiseSplitName = new byte[256];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[128];

    public NET_ADVERTISE_SPLITLIST() {
    }
}

