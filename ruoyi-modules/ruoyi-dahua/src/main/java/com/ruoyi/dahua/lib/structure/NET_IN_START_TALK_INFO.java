package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.fAudioDataCallBackEx;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @version 1.0
 * @description CLIENT_StartTalkByDataType接口 入参
 * @date 2021/12/29
 */
public class NET_IN_START_TALK_INFO extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  音频数据回调函数,实现时使用{ @link fAudioDataCallBackEx }
     */
    public fAudioDataCallBackEx pfAudioDataCallBackEx;
    /**
     *  pfAudioDataCallBackEx回调对应的用户指针
     */
    public Pointer          dwUser;

    public NET_IN_START_TALK_INFO(){
        this.dwSize = this.size();
    }
}

