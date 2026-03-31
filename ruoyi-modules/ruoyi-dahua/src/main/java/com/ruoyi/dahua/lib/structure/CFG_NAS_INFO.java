package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description NAS信息
 * @date 2022/09/08 19:33:10
 */
public class CFG_NAS_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 使能开关
	 */
    public int              bEnable;
	/**
	 * 网络存储服务器版本0=老的FTP，1=NAS存储
	 */
    public int              nVersion;
	/**
	 * 协议类型0=FTP 1=SMB
	 */
    public int              nProtocol;
	/**
	 * IP地址或网络名
	 */
    public byte[]           szAddress = new byte[256];
	/**
	 * 端口号
	 */
    public int              nPort;
	/**
	 * 帐户名
	 */
    public byte[]           szUserName = new byte[64];
	/**
	 * 密码
	 */
    public byte[]           szPassword = new byte[64];
	/**
	 * 共享的目录名
	 */
    public byte[]           szDirectory = new byte[256];
	/**
	 * 文件长度
	 */
    public int              nFileLen;
	/**
	 * 相邻文件时间间隔
	 */
    public int              nInterval;
	/**
	 * 存储时间段
	 */
    public CFG_CHANNEL_TIME_SECTION[] stuChnTime = new CFG_CHANNEL_TIME_SECTION[256];
	/**
	 * 有效的存储时间段数
	 */
    public int              nChnTimeCount;

	public CFG_NAS_INFO() {
		for (int i = 0; i < stuChnTime.length; i++) {
			stuChnTime[i] = new CFG_CHANNEL_TIME_SECTION();
		}
	}
}

