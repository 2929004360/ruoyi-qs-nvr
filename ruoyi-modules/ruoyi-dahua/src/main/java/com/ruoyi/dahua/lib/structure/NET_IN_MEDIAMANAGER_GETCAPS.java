package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetDevCaps NET_MEDIAMANAGER_CAPS 命令入参
*/
public class NET_IN_MEDIAMANAGER_GETCAPS extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 需要获取的能力集类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.NET_MEDIA_CAP_TYPE}
    */
    public int              emType;

    public NET_IN_MEDIAMANAGER_GETCAPS() {
        this.dwSize = this.size();
    }
}

