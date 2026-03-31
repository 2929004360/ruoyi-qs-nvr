package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description CLIENT_AttachDetectMultiFaceState 接口输入参数
 * @date 2022/11/21 17:53:13
 */
public class NET_IN_MULTIFACE_DETECT_STATE extends NetSDKLib.SdkStructure {
    /**
     * 此结构体大小
     */
    public int              dwSize;
    /**
     * 用于字节对齐
     */
    public byte[]           bReserved = new byte[4];
    /**
     * 回调函数
     */
    public NetSDKLib.fMultiFaceDetectState cbMultiFaceDetectState;
    /**
     * 用户数据
     */
    public Pointer          dwUser;
    /**
     * 回调函数扩展（推荐使用）
     */
    public NetSDKLib.fMultiFaceDetectStateEx cbMultiFaceDetectStateEx;
    /**
     * 查询令牌
     */
    public int[]            nTokens = new int[10];
    /**
     * 查询令牌个数, 若为0，则默认订阅token为0的检测, 若为-1表示订阅全部token
     */
    public int              nTokensNum;

    public NET_IN_MULTIFACE_DETECT_STATE() {
        this.dwSize = this.size();
    }
}

