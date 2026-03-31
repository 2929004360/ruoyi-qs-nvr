package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_QueryDevInfo NET_QUERY_DEV_IO_STATS 类型接口输入参数
 * @date 2021/07/09
 */
public class NET_OUT_DEV_IO_STATS extends NetSDKLib.SdkStructure {
	/**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 存储设备io信息个数
     */
    public int              nInfoNum;
    /**
     * 存储设备io信息 
     */       
    public NET_DEV_IO_STAT_INFO[] stuInfo = (NET_DEV_IO_STAT_INFO[])new NET_DEV_IO_STAT_INFO().toArray(512);

    public NET_OUT_DEV_IO_STATS(){
        this.dwSize=size();
    }
}

