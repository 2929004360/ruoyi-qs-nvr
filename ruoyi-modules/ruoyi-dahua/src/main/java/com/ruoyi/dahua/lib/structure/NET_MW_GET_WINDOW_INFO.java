package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 解码通道信息
*/
public class NET_MW_GET_WINDOW_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 窗口使能状态
    */
    public int              bEnable;
    /**
     * 窗口解码状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.NET_VIDEOCHANNEL_STATE}
    */
    public int              emState;
    /**
     * 网络流量(单位：kbps)
    */
    public int              unNetflow;
    /**
     * 码率(单位：kbps)
    */
    public int              unBitrate;
    /**
     * 帧率
    */
    public int              nFrame;
    /**
     * 分辨率,参见枚举定义 {@link NetSDKLib.CAPTURE_SIZE}
    */
    public int              emResolution;
    /**
     * 实时统计的帧率
    */
    public int              unRealTimeFrame;
    /**
     * 码流的编码方式,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_NET_ENCODE_COMPRESSION}
    */
    public int              emCompression;
    /**
     * 分辨率细分,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_CAPTURE_SIZE_EX}
    */
    public int              emResolutionEx;
    /**
     * 预留字节
    */
    public byte[]           byReserved = new byte[508];

    public NET_MW_GET_WINDOW_INFO() {
    }
}

