package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @version 1.0
 * @description 设备详细信息
 * @date 2021/11/07
 */
public class NET_MONITOR_WALL_DEVICE_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  机器名称
     */
    public byte[]           szName = new byte[64];
    /**
     *  密码
     */
    public byte[]           szPassword = new byte[64];
    /**
     *  用户名
     */
    public byte[]           szUserName = new byte[64];
    /**
     *  端口
     */
    public int              nPort;
    /**
     *  保留字节
     */
    public byte[]           byReserverd = new byte[256];
}

