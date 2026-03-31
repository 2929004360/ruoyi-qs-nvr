package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 分析文件的加密密钥
*/
public class NET_ANALYSE_TASK_VIDEO_KEY_ENCRYPTION_KEYS extends NetSDKLib.SdkStructure
{
    /**
     * 加密密钥ID
    */
    public byte[]           szId = new byte[64];
    /**
     * 加密密钥，256-bit随机码
    */
    public byte[]           szKey = new byte[256];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[256];

    public NET_ANALYSE_TASK_VIDEO_KEY_ENCRYPTION_KEYS() {
    }
}

