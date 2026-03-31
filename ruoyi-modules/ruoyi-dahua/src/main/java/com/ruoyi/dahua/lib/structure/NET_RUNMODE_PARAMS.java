package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

public class NET_RUNMODE_PARAMS extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 运行模式0:非内存池模式1:内存池模式
     */
    public int              nMode;
    /**
     * 针对nWinConnectBufSize (windows) 每个连接接收数据缓冲大小(字节为单位)，
     * 为0表示维持原来的值，250*1024最佳，最小为128*1024
     */
    public int              nWinConnectBufSize;
    /**
     * 针对nWinSocketRcvBuf (windows/linux), socket接收缓存大小(字节为单位)，
     * 为0表示维持原来的值，64*1024最佳，最小为36*1024
     */
    public int              nWinSocketRcvBuf;

    public NET_RUNMODE_PARAMS() {
        dwSize = this.size();
    }
}

