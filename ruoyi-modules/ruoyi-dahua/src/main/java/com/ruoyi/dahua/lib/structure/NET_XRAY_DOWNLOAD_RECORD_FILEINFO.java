package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 文件信息
*/
public class NET_XRAY_DOWNLOAD_RECORD_FILEINFO extends NetSDKLib.SdkStructure
{
    /**
     * 路径
    */
    public byte[]           szPath = new byte[128];
    /**
     * 大小，单位字节
    */
    public long             nSize;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_XRAY_DOWNLOAD_RECORD_FILEINFO() {
    }
}

