package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @version 1.0
 * @description CLIENT_AttachTransmitInfo输出参数
 * @date 2022/02/14
 */
public class NET_OUT_ATTACH_TRANSMIT_INFO extends NetSDKLib.SdkStructure {
    /**
     *  用户使用该结构体时，dwSize需赋值为sizeof(NET_OUT_ATTACH_TRANSMIT_INFO)
     */
    public int              dwSize;
    /**
     *  应答缓冲地址,用户申请空间
     */
    public Pointer          szOutBuffer;
    /**
     *  应答缓冲总长度
     */
    public int              dwOutBufferSize;
    /**
     *  应答Json数据长度
     */
    public int              dwOutJsonLen;
    /**
     *  应答二进制数据长度 
     */
    public int              dwOutBinLen;

    public NET_OUT_ATTACH_TRANSMIT_INFO(){
        this.dwSize = this.size();
    }
}

