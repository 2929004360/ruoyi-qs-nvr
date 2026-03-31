package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetXRayDownloadRecord 接口输出参数
*/
public class NET_OUT_XRAY_DOWNLOAD_RECORD_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 文件信息,参见结构体定义 {@link NET_XRAY_DOWNLOAD_RECORD_FILEINFO}
    */
    public NET_XRAY_DOWNLOAD_RECORD_FILEINFO stuFileInfo = new NET_XRAY_DOWNLOAD_RECORD_FILEINFO();

    public NET_OUT_XRAY_DOWNLOAD_RECORD_INFO() {
        this.dwSize = this.size();
    }
}

