package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetTemporaryToken 接口入参
*/
public class NET_IN_GET_TEMPORARY_TOKEN extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 请求token的客户端IP. 可为空("")
    */
    public byte[]           szclientIP = new byte[48];
    /**
     * token有效时间, 单位:秒, 范围5-90s
    */
    public int              nkeepAliveTime;
    /**
     * 获取级联模式Token
    */
    public int              bRemoteMode;
    /**
     * 级联设备通道号,从0开始，当bRemoteMode为TRUE时有效
    */
    public int              nChannelID;

    public NET_IN_GET_TEMPORARY_TOKEN() {
        this.dwSize = this.size();
    }
}

