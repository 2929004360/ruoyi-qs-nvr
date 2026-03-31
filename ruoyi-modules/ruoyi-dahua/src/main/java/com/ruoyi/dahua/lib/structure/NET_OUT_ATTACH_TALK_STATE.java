package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 接口 CLIENT_AttachTalkState 的输出参数
*/
public class NET_OUT_ATTACH_TALK_STATE extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 呼叫惟一标识符
    */
    public byte[]           szCallID = new byte[64];

    public NET_OUT_ATTACH_TALK_STATE() {
        this.dwSize = this.size();
    }
}

