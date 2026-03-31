package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 网络存储配置
 * @date 2022/09/08 19:33:10
 */
public class CFG_NAS_INFO_NEW extends NetSDKLib.SdkStructure {
	/**
	 * 使能
	 */
    public int              bEnable;
	/**
	 * 名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 协议类型, 0-FTP, 1-SMB, 2-ISCSI, 3-NFS, 4-Cloud ,协议类型是 4-Cloud时，具体云协议定义见emCloudProtocol
	 */
    public int              nPortocol;
	/**
	 * IP地址或网址
	 */
    public byte[]           szAddress = new byte[256];
	/**
	 * 端口
	 */
    public int              nPort;
	/**
	 * 用户名
	 */
    public byte[]           szUser = new byte[64];
	/**
	 * 密码
	 */
    public byte[]           szPassword = new byte[64];
	/**
	 * 共享的目录名
	 */
    public byte[]           szDirectory = new byte[128];
	/**
	 * 字符编码格式, 0-UTF8, 1-GB2312
	 */
    public int              nCharEncoding;
	/**
	 * 超时时间, 单位毫秒
	 */
    public int              nTimeOut;
	/**
	 * 数据流ID
	 */
    public int              nStreamID;
	/**
	 * 更新时间, 该字段值变化后，需要重新初始化iSCSI功能
	 */
    public CFG_NET_TIME_EX  stuUpdateTime = new CFG_NET_TIME_EX();
	/**
	 * 云存储协议类型 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_NAS_CLOUDPROTOCOL_TYPE}
	 */
    public int              emCloudProtocol;
	/**
	 * 客户端设备在服务器上的存储子目录，可以是IP地址，设备序列号，设备机器号，为空使用设备机器号
	 */
    public byte[]           szSubDirectory = new byte[256];

	public CFG_NAS_INFO_NEW() {
	}
}

