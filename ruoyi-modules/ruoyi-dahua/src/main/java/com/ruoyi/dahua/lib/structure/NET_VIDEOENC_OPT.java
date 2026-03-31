package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description
 * @date 2023/11/18 15:10
 */
public class NET_VIDEOENC_OPT extends NetSDKLib.SdkStructure {
    /**
     * 视频使能；1：打开,0：关闭
     */
    public    byte          byVideoEnable;
    /**
     * 码流控制；参照常量"码流控制"定义
     */
    public    byte          byBitRateControl;
    /**
     * 帧率
     */
    public    byte          byFramesPerSec;
    /**
     * 编码模式；参照常量"编码模式"定义
     */
    public    byte          byEncodeMode;
    /**
     * 分辨率；参照常量"分辨率"定义
     */
    public    byte          byImageSize;
    /**
     * 将byImageQlty进行扩展,若byImageQlty为0,抓图配置的ImgQlty为10/30/50/60/80/100六个值,码流配置的ImgQlty值为1-6,兼容之前的1-6档次,若///ImgQltyType为1,则ImgQlty范围为0~100
     */
    public    byte          byImageQltyData;
    //  public    byte                 byImageQlty:7;
    //  public    byte      byImageQltyType:1;
    public short            wLimitStream;                         /// 限码流参数
    /**
     *   音频使能；1：打开,0：关闭
     */
    public    byte          byAudioEnable;
    /**
     *   音频编码类型: 0:G711A,1:PCM,2:G711U,3:AMR,4:AAC
     */
    public    byte          wFormatTag;
    /**
     * 声道数
     */
    public short            nChannels;
    /**
     * 采样深度
     */
    public short            wBitsPerSample;
    /**
     * 音频叠加使能
     */
    public    byte          bAudioOverlay;
    /**
     * H.264 Profile等级(当编码模式为H264时此参数值有效),参照枚举类型EM_H264_PROFILE_RANK,传0,兼容以前,表示该字段无效,不需要考虑级别。
     */
    public    byte          bH264ProfileRank;
    /**
     * 采样率
     */
    public int              nSamplesPerSec;
    /**
     * I帧间隔帧数量,描述两个I帧之间的P帧个数,0-149
     */
    public    byte          bIFrameInterval;
    /**
     * NSP
     */
    public    byte          bScanMode;
    public    byte          bReserved_3;
    public    byte          bReserved_4;

    public byte getByImageQlty(){
        byte a=0;
        byte b=7;
        return getAnd(byImageQltyData,a,b);
    }

    public byte getByImageQltyType(){
        byte a=7;
        byte b=7+1;
        return getAnd(byImageQltyData,a,b);
    }

    private byte getAnd(byte date,byte start,byte end){

        return (byte) (date>>start&getOr(start,end));
    }

    private byte getOr(byte start,byte end){
        byte result=0;
        for (int i = 0; i < end-start; i++) {
            result|=(1<<i);
        }
        return result;
    }
}

