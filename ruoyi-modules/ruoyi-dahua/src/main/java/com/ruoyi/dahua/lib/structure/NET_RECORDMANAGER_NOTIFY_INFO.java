package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 向客户端发送录像文件回调信息
*/
public class NET_RECORDMANAGER_NOTIFY_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 码流类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_STREAM_TYPE}
    */
    public int              emStreamType;
    /**
     * 是否在录像
    */
    public int              bState;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[128];

    public NET_RECORDMANAGER_NOTIFY_INFO() {
    }
}

