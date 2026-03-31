package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_ExportEncrypedLog 接口出参
*/
public class NET_OUT_SET_LOG_ENCRYPT_KEY_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;

    public NET_OUT_SET_LOG_ENCRYPT_KEY_INFO() {
        this.dwSize = this.size();
    }
}

