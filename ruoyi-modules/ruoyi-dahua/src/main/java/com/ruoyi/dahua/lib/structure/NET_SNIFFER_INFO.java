package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 抓包信息
*/
public class NET_SNIFFER_INFO extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 抓包ID, 无抓包ID为0
    */
    public int              nSnifferID;
    /**
     * 抓包存储文件名。若当前网卡无正在进行的抓包任务，则为空
    */
    public byte[]           szPath = new byte[256];
    /**
     * 默认路径及start路径传入下的抓包文件。按时间从新到旧排序。
    */
    public BYTE_ARRAY_256[] szPathEx = new BYTE_ARRAY_256[10];
    /**
     * 实际返回的szPathEx有效个数
    */
    public int              nRetPathEx;
    /**
     * szPathEx元素对应文件大小, 单位字节
    */
    public int[]            nFileSize = new int[10];

    public NET_SNIFFER_INFO() {
        this.dwSize = this.size();
    }
}

