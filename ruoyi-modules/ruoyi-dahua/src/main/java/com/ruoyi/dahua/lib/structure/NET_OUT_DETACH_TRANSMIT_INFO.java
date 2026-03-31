package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @version 1.0
 * @description CLIENT_DetachTransmitInfo输出参数
 * @date 2022/02/14
 */
public class NET_OUT_DETACH_TRANSMIT_INFO extends NetSDKLib.SdkStructure {
    /**
     *  用户使用该结构体时，dwSize需赋值为sizeof(NET_OUT_DETACH_TRANSMIT_INFO)
     */
    public int              dwSize;
    /**
     *  应答数据缓冲空间, 用户申请空间
     */
    public Pointer          szOutBuffer;
    /**
     *  应答数据缓冲空间长度
     */
    public int              dwOutBufferSize;
    /**
     *  应答Json数据长度
     */
    public int              dwOutJsonLen;

    public NET_OUT_DETACH_TRANSMIT_INFO(){
        this.dwSize = this.size();
    }
}

