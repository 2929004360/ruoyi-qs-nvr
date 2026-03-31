package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetDevCaps NET_MEDIAMANAGER_CAPS 命令出参
*/
public class NET_OUT_MEDIAMANAGER_GETCAPS extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 镜头传感器信息,参见结构体定义 {@link NET_MEDIA_SENSORINFO}
    */
    public NET_MEDIA_SENSORINFO stuSensorInfo = new NET_MEDIA_SENSORINFO();

    public NET_OUT_MEDIAMANAGER_GETCAPS() {
        this.dwSize = this.size();
    }
}

