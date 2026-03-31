package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetInstalledAppInfo 接口输入参数
*/
public class NET_IN_GET_INSTALLED_APP_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;

    public NET_IN_GET_INSTALLED_APP_INFO() {
        this.dwSize = this.size();
    }
}

