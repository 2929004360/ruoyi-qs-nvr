package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @version 1.0
 * @description CLIENT_AttachTransmitInfo输入参数
 * @date 2022/02/14
 */
public class NET_IN_ATTACH_TRANSMIT_INFO extends NetSDKLib.SdkStructure {
    /**
     *  用户使用该结构体时，dwSize需赋值为sizeof(NET_IN_ATTACH_TRANSMIT_INFO)
     */
    public int              dwSize;
    /**
     *  回调函数
     */
    public  NetSDKLib.AsyncTransmitInfoCallBack cbTransmitInfo;
    /**
     *  用户数据
     */
    public Pointer          dwUser;
    /**
     *  Json请求数据,用户申请空间
     */
    public Pointer          szInJsonBuffer;
    /**
     *  Json请求数据长度 `
     */
    public int              dwInJsonBufferSize;
    /**
     *  TRUE-当设备支持时，使用子连接方式接收订阅数据 FALSE-只在主连接接收订阅数据
     */
    public int              bSubConnFirst;

    public NET_IN_ATTACH_TRANSMIT_INFO(){
        this.dwSize = this.size();
    }
}

