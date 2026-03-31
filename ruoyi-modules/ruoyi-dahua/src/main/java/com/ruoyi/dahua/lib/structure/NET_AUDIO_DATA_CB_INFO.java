package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @version 1.0
 * @description fAudioDataCallBackEx 回调音频信息
 * @date 2021/12/29
 */
public class NET_AUDIO_DATA_CB_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  带音频头的音频数据
     */
    public Pointer          pBuf;
    /**
     *  带音频头的音频数据长度
     */
    public int              dwBufSize;
    /**
     *  音频编码格式,参考枚举{ @link NET_TALK_CODING_TYPE }
     */
    public int              emAudioCode;
    /**
     *  不带音频头的音频裸数据
     */
    public Pointer          pRawBuf;
    /**
     *  不带音频头的音频数据长度
     */
    public int              dwRawBufSize;
    /**
     *  位数,如8或16等
     */
    public int              nAudioBit;
    /**
     *  采样率,如8000或16000等
     */
    public int              dwSampleRate;
    /**
     *  预留字节
     */
    public byte[]           bReserved = new byte[256];
}

