package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @version 1.0
 * @description CLIENT_TalkSendDataByStream接口 入参
 * @date 2021/12/29
 */
public class NET_IN_TALK_SEND_DATA_STREAM extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  音频流缓冲
     */
    public Pointer          pBuf;
    /**
     *  音频流缓冲大小
     */
    public int              dwBufSize;
    /**
     *  是否需要加音频头。
     *  TRUE，表示需要SDK根据下面的音频信息加音频头；
     *  FALSE，表示不需要SDK根据音频信息加音频头，直接发送pBuf指向的数据给设备。
     */
    public int              bNeedHead;
    /**
     *  音频编码格式,参考枚举{ @link NET_TALK_CODING_TYPE }
     */
    public int              emEncodeType;
    /**
     *  位数,如8或16等
     */
    public int              nAudioBit;
    /**
     *  采样率,如8000或16000等
     */
    public int              dwSampleRate;

    public NET_IN_TALK_SEND_DATA_STREAM(){
        this.dwSize = this.size();
    }
}

