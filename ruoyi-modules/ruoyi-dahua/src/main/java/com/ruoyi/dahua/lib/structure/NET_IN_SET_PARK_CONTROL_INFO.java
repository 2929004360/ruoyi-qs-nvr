package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;

public class NET_IN_SET_PARK_CONTROL_INFO extends SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 屏幕信息个数
	 */
    public int              nScreenShowInfoNum;
	/**
	 * 屏幕信息
	 */
    public NET_SCREEN_SHOW_INFO[] stuScreenShowInfo = new NET_SCREEN_SHOW_INFO[16];
	/**
	 * 字节补齐
	 */
    public byte[]           byReserved = new byte[4];
	/**
	 * 播报信息个数
	 */
    public int              nBroadcastInfoNum;
	/**
	 * 播报信息
	 */
    public NET_BROADCAST_INFO[] stuBroadcastInfo = new NET_BROADCAST_INFO[16];
    /**
     * 通道号,从0开始
    */
    public int              nChannel;

    public NET_IN_SET_PARK_CONTROL_INFO() {
        for(int i=0;i<stuScreenShowInfo.length;i++){
            stuScreenShowInfo[i]=new NET_SCREEN_SHOW_INFO();
        }
        for(int i=0;i<stuBroadcastInfo.length;i++){
            stuBroadcastInfo[i]=new NET_BROADCAST_INFO();
        }
        this.dwSize = this.size();
    }
}

