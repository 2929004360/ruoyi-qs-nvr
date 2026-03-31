package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description
 * @date 2021/8/23 19:46
 */
public class NET_SECURITYGATE_ALARM_FACEIMAGE_INFO extends NetSDKLib.SdkStructure {
    public int              nOffSet;                              // 在二进制数据块中的偏移
    public int              nLength;                              // 图片大小,单位字节
    public byte[]           byReserved = new byte[24];            // 预留字节
}

