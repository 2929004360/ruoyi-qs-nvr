package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetXRayDownloadRecord 接口输入参数
*/
public class NET_IN_XRAY_DOWNLOAD_RECORD_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 查询条件,参见结构体定义 {@link NET_XRAY_DOWNLOAD_RECORD_CONDITION}
    */
    public NET_XRAY_DOWNLOAD_RECORD_CONDITION stuCondition = new NET_XRAY_DOWNLOAD_RECORD_CONDITION();

    public NET_IN_XRAY_DOWNLOAD_RECORD_INFO() {
        this.dwSize = this.size();
    }
}

