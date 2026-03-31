package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 抓包过滤元素
*/
public class NET_SNIFFER_FILTER_ELEMENT extends NetSDKLib.SdkStructure
{
    /**
     * 源地址
    */
    public byte[]           szSrcIP = new byte[64];
    /**
     * 目的地址
    */
    public byte[]           szDstIP = new byte[64];
    /**
     * 源端口
    */
    public int              nSrcPort;
    /**
     * 目的端口
    */
    public int              nDstPort;
    /**
     * 协议类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_SNIFFER_FILTER_PROTOCOL}
    */
    public int              emProtocol;
    /**
     * 保留字段
    */
    public byte[]           byReserved = new byte[60];

    public NET_SNIFFER_FILTER_ELEMENT() {
    }
}

