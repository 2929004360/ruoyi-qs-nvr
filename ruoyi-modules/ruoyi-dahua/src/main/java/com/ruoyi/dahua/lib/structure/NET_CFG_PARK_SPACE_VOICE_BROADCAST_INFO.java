package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * NET_CFG_PARK_SPACE_VOICE_BROADCAST_INFO 停车场语音播报配置
*/
public class NET_CFG_PARK_SPACE_VOICE_BROADCAST_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 使能语音播报
    */
    public int              bEnable;
    /**
     * 播报元素数组,参见结构体定义 {@link NET_ELEMENT_INFO}
    */
    public NET_ELEMENT_INFO[] stuElement = new NET_ELEMENT_INFO[64];
    /**
     * 播报元素数组个数
    */
    public int              nElementNum;

    public NET_CFG_PARK_SPACE_VOICE_BROADCAST_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuElement.length; i++){
            stuElement[i] = new NET_ELEMENT_INFO();
        }
    }
}

