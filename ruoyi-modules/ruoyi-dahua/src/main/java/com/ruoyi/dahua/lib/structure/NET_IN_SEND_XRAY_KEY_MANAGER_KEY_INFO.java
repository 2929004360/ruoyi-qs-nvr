package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_SendXRayKeyManagerKey 的输入参数
*/
public class NET_IN_SEND_XRAY_KEY_MANAGER_KEY_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 按键消息,参见结构体定义 {@link NET_KEY_MESSAGE_INFO}
    */
    public NET_KEY_MESSAGE_INFO stuKeyMessage = new NET_KEY_MESSAGE_INFO();

    public NET_IN_SEND_XRAY_KEY_MANAGER_KEY_INFO() {
        this.dwSize = this.size();
    }
}

