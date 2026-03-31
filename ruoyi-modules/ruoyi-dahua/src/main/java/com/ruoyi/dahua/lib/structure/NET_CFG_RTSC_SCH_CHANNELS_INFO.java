package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 通道表, 对应配置项NET_EM_CFG_RTSC_SCH_CHANNELS
 * @date 2021/09/02
 */
public class NET_CFG_RTSC_SCH_CHANNELS_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  最大通道个数，只读
     */
    public int              nMaxChannels;
    /**
     *  通道列表个数
     */
    public int              nChannelsInfoNum;
    /**
     *  通道列表
     */
    public NET_CHANNELS_INFO[] stuChannelsInfo = (NET_CHANNELS_INFO[]) new NET_CHANNELS_INFO().toArray(36);

    public NET_CFG_RTSC_SCH_CHANNELS_INFO(){
        this.dwSize = this.size();
    }
}

