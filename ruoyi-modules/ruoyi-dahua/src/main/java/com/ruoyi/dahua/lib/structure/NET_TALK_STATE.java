package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_AttachTalkState 回调函数返回的语音对讲状态
*/
public class NET_TALK_STATE extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 呼叫惟一标识符
    */
    public byte[]           szCallID = new byte[64];
    /**
     * 音频端口
    */
    public int              nAudioPort;
    /**
     * 视频端口
    */
    public int              nVideoPort;
    /**
     * 取流地址
    */
    public byte[]           szMediaAddr = new byte[16];
    /**
     * 状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_TALK_STATE}
    */
    public int              emState;
    /**
     * 呼叫号码数
    */
    public int              nNumberCount;
    /**
     * 呼叫号码列表
    */
    public BYTE_ARRAY_32[]  szNumbers = new BYTE_ARRAY_32[128];

    public NET_TALK_STATE() {
        this.dwSize = this.size();
    }
}

