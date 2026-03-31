package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 接口 CLIENT_AttachTalkState 的输入参数
*/
public class NET_IN_ATTACH_TALK_STATE extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 通道号,用于标识对讲单元
    */
    public int              nChnId;
    /**
     * 回调函数,有对讲状态时,回调给上层,参见回调函数定义 {@link NetSDKLib.fNotifyTalkState}
    */
    public NetSDKLib.fNotifyTalkState cbCallBack;
    /**
     * 用户自定义参数
    */
    public Pointer          dwUser;

    public NET_IN_ATTACH_TALK_STATE() {
        this.dwSize = this.size();
    }
}

