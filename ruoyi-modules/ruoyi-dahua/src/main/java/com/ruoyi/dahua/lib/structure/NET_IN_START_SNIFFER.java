package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_StartSniffer 接口输入参数
*/
public class NET_IN_START_SNIFFER extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 网卡名称
    */
    public Pointer          pszNetInterface;
    /**
     * 抓包存储文件名, 空表示保存到默认路径
    */
    public Pointer          pszPath;
    /**
     * 文件类型, 0-Wireshark/Tcpdump 1-Remotecap 流式远程抓包，即通过 CLIENT_AttachSniffer
    */
    public int              nSaveType;
    /**
     * 过滤条件, 如"host 172.9.88.200 and port 8080 and tcp"
    */
    public Pointer          pszFilter;
    /**
     * 抓包超时时间，单位：秒, 0 则默认不超时.
    */
    public int              nCaptureTime;
    /**
     * 指定此次抓包的最大大小, 单位kb
    */
    public int              nCurrentFileSize;
    /**
     * 高优先级规则使能，若为真，则不采用pszFilter里的过滤规则
    */
    public int              bFilterEx;
    /**
     * 高优先级的过滤规则,参见结构体定义 {@link NET_SNIFFER_FILTER}
    */
    public NET_SNIFFER_FILTER stuFilterEx = new NET_SNIFFER_FILTER();

    public NET_IN_START_SNIFFER() {
        this.dwSize = this.size();
    }
}

