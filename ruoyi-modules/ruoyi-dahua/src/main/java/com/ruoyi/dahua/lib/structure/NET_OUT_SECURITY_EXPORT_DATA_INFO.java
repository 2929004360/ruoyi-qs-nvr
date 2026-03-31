package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_SecurityExportDataEx接口出参
*/
public class NET_OUT_SECURITY_EXPORT_DATA_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 导出文件数据, 用户申请内存, 大小为nBufLen
    */
    public Pointer          pBufData;
    /**
     * pBufData最大长度
    */
    public int              nBufLen;
    /**
     * 实际返回的长度
    */
    public int              nBufRet;
    /**
     * 导出文件数据总长度
    */
    public int              nTotalLength;
    /**
     * 二进制包长度
    */
    public int              nPackLength;

    public NET_OUT_SECURITY_EXPORT_DATA_INFO() {
        this.dwSize = this.size();
    }
}

