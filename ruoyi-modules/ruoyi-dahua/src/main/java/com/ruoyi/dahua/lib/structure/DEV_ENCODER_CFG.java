package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 前端接入配置参数信息
 * @date 2022/08/10
 */
public class DEV_ENCODER_CFG extends NetSDKLib.SdkStructure {
    /**
     *  数字通道个数
     */
    public int              nChannels;
    /**
     *  各数字通道的前端编码器信息
     */
    public DEV_ENCODER_INFO[] stuDevInfo = new DEV_ENCODER_INFO[32];
    /**
     *  数字通道中最多可支持高清视频个数(为0表示不支持高清设置)
     *  注：如果支持高清设置,高清通道为0~N-1,若将N之后的数字通道设置为高清,有可能保存不成功
     */
    public byte             byHDAbility;
    /**
     *  设备是否支持TV调节, 0:不支持 1:支持.
     */
    public byte             bTVAdjust;
    /**
     *  设备是否支持解码轮巡, 0:不支持 大于0:表示前端支持轮巡最大设备数.
     */
    public byte             bDecodeTour;
    /**
     *  指示是否支持远程云台控制。
     */
    public byte             bRemotePTZCtl;
    /**
     * reserved
     */
    public byte[]           reserved = new byte[12];

    public DEV_ENCODER_CFG() {
    	for (int i = 0; i < stuDevInfo.length; i++) {
    		stuDevInfo[i] = new DEV_ENCODER_INFO();
		}
    }
}

