package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 通道信息
 * @date 2023/04/20 08:58:40
 */
public class NET_DEVICE_CHANNEL_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 远程通道号
	 */
    public int              nRemoteChannel;
	/**
	 * 分配的逻辑通道, -1表示未分配
	 */
    public int              nLogicChannel;
	/**
	 * 通道编码
	 */
    public byte[]           szChannelID = new byte[32];
	/**
	 * 通道国标编码
	 */
    public byte[]           szGbCode = new byte[32];
	/**
	 * 显示在设备树的名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 设备详细信息
	 */
    public byte[]           szDetail = new byte[512];
	/**
	 * 设备型号
	 */
    public byte[]           szDeviceType = new byte[64];
	/**
	 * 设备大类
	 */
    public byte[]           szDeviceClass = new byte[16];
	/**
	 * 通道是否在线, 0:离线 1:在线
	 */
    public int              nOnline;
	/**
	 * 该通道是否被本地设备使用 0:未使用 1:使用
	 */
    public int              nUsed;
	/**
	 * ip地址
	 */
    public byte[]           szIP = new byte[40];
	/**
	 * mac地址
	 */
    public byte[]           szMac = new byte[18];
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved1 = new byte[6];
	/**
	 * 序列号
	 */
    public byte[]           szSN = new byte[32];
	/**
	 * 软件版本
	 */
    public byte[]           szSoftwareVersion = new byte[128];
	/**
	 * 视频输入通道数
	 */
    public int              nVideoInChannels;
	/**
	 * 视频输出通道数
	 */
    public int              nVideoOutChannels;
	/**
	 * 音频输入通道数
	 */
    public int              nAudioInChannels;
	/**
	 * 音频输出通道数
	 */
    public int              nAudioOutChannels;
	/**
	 * 报警输入通道数
	 */
    public int              nAlarmInChannels;
	/**
	 * 报警输出通道数
	 */
    public int              nAlarmOutChannels;
	/**
	 * 支持最大辅码流路数(0~3路)
	 */
    public int              nMaxExtraStream;
	/**
	 * 设备接入网络服务类型 {@link com.ruoyi.dahua.lib.enumeration.EM_NET_SERVER_TYPE}
	 */
    public int              emNetServerType;
	/**
	 * 跟设备对接时的拉流协议类型 {@link com.ruoyi.dahua.lib.enumeration.EM_CHANNEL_PROTOCOL_TYPE}
	 */
    public int              emProtocolType;
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved2 = new byte[4];
	/**
	 * 温度值，摄氏度
	 */
    public double           dbTemperature;
	/**
	 * 通道在离线状态改变时的时间
	 */
    public NET_TIME         stuStateChangeTime = new NET_TIME();
	/**
	 * 视图库通道编码
	 */
    public byte[]           szVideoImageChannelID = new byte[32];
	/**
	 * 平台对接编号
	 */
    public byte[]           szPlatformDoID = new byte[32];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1024];

	public NET_DEVICE_CHANNEL_INFO() {
	}
}

