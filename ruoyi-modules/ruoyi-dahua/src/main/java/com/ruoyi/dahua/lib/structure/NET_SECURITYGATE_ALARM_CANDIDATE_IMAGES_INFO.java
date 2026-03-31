package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 注册库人员图片信息
*/
public class NET_SECURITYGATE_ALARM_CANDIDATE_IMAGES_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 在二进制数据块中的偏移
    */
    public int              nOffSet;
    /**
     * 图片大小,单位字节
    */
    public int              nLength;
    /**
     * 预留字节
    */
    public byte[]           byReserved = new byte[24];

    public NET_SECURITYGATE_ALARM_CANDIDATE_IMAGES_INFO() {
    }
}

