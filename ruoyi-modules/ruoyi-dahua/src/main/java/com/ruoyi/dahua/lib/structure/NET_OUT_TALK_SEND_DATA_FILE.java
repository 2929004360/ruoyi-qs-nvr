package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @version 1.0
 * @description CLIENT_TalkSendDataByFile接口 出参
 * @date 2021/12/29
 */
public class NET_OUT_TALK_SEND_DATA_FILE extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小
     */
    public int              dwSize;

    public NET_OUT_TALK_SEND_DATA_FILE(){
        this.dwSize = this.size();
    }
}

