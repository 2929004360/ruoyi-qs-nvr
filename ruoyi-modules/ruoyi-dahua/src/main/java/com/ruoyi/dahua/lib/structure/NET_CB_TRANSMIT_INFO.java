package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @version 1.0
 * @description CLIENT_AttachTransmitInfo 上报信息回调
 * @date 2022/02/14
 */
public class NET_CB_TRANSMIT_INFO extends NetSDKLib.SdkStructure {
    /**
     *  数据缓冲地址，SDK内部申请空间
     */
    public Pointer          pBuffer;
    /**
     *  数据缓冲总长度
     */
    public int              dwBufferSize;
    /**
     *  Json数据长度
     */
    public int              dwJsonLen;
    /**
     *  二进制数据长度
     */
    public int              dwBinLen;
    /**
     *  保留字节
     */
    public byte[]           byReserved = new byte[512];
}

