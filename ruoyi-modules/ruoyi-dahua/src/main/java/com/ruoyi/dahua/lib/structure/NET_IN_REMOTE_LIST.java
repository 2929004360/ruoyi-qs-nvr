package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_RemoteList 接口输入参数
*/
public class NET_IN_REMOTE_LIST extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 通道号
    */
    public int              nChannel;
    /**
     * 指定路径
    */
    public byte[]           szPath = new byte[256];

    public NET_IN_REMOTE_LIST() {
        this.dwSize = this.size();
    }
}

