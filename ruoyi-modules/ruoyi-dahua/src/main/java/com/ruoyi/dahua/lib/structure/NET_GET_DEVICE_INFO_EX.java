package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 已添加设备的结果信息
 * @date 2023/04/19 18:56:54
 */
public class NET_GET_DEVICE_INFO_EX extends NetSDKLib.SdkStructure {
	/**
	 * 设备ID
	 */
    public byte[]           szDeviceID = new byte[128];
	/**
	 * url
	 */
    public byte[]           szUrl = new byte[512];
	/**
	 * 设备序列号
	 */
    public byte[]           szSerialNo = new byte[64];
	/**
	 * 设备国标编码
	 */
    public byte[]           szGBCode = new byte[32];
	/**
	 * 设备所属组织编码
	 */
    public byte[]           szOrgCode = new byte[64];
	/**
	 * 设备类型
	 */
    public byte[]           szDeviceType = new byte[64];
	/**
	 * 设备大类
	 */
    public byte[]           szDeviceClass = new byte[16];
	/**
	 * 设备mac个数
	 */
    public int              nMacCount;
	/**
	 * 设备mac地址组
	 */	
    public BYTE_18[]        szMacs = new BYTE_18[8];
	/**
	 * 设备软件版本号
	 */
    public byte[]           szDevSoftVersion = new byte[128];
	/**
	 * 设备名称
	 */
    public byte[]           szDeviceName = new byte[128];
	/**
	 * 设备详细信息
	 */
    public byte[]           szDetail = new byte[512];
	/**
	 * 视频输入通道数
	 */
    public int              nVideoInChannel;
	/**
	 * 视频输出通道数
	 */
    public int              nVideoOutChannel;
	/**
	 * 音频输入通道数
	 */
    public int              nAudioInChannel;
	/**
	 * 音频输出通道数
	 */
    public int              nAudioOutChannel;
	/**
	 * 报警输入通道数
	 */
    public int              nAlarmIn;
	/**
	 * 报警输出通道数
	 */
    public int              nAlarmOut;
	/**
	 * 设备是否在线 0:离线 1：在线
	 */
    public int              nOnline;
	/**
	 * 设备离线错误码
	 */
    public int              nErrorCode;
	/**
	 * 门禁设备可控制的门的总数
	 */
    public int              nVtoDoors;
	/**
	 * 是否是POE设备, 0不是, 1是POE设备
	 */
    public int              nIsPoeDevice;
	/**
	 * Poe端口号, 取值范围>= 0; isPoeDevice字段为真时有效
	 */
    public int              nPoePort;
	/**
	 * 是否支持目标比对
	 */
    public int              bSupportedFR;
	/**
	 * 是否支持语音联动
	 */
    public int              bControlSpeaker;
	/**
	 * 设备属性 {@link com.ruoyi.dahua.lib.enumeration.EM_DEVICE_ATTRIBUTE}
	 */
    public int              emDeviceAttribute;
	/**
	 * 是否具备统计功能
	 */
    public int              bSupportedStatistic;
	/**
	 * 主协议是否存在
	 */
    public int              bIsMainProtocolExist;
	/**
	 * 视图库是否使能
	 */
    public int              bIsVideoImageEnable;
	/**
	 * 视图库是否在线 0:离线 1:在线
	 */
    public int              nVideoImageOnline;
	/**
	 * 视图库离线错误码
	 */
    public int              nVideoImageErrorCode;
	/**
	 * 视图库注册编号
	 */
    public byte[]           szVideoImageRegID = new byte[32];
	/**
	 * 用户申请的通道个数
	 */
    public int              nMaxChannelCount;
	/**
	 * 实际返回的通道个数
	 */
    public int              nRetChannelCount;
	/**
	 * 通道信息, 由用户申请和释放内存, 申请大小为sizeof(NET_DEVICE_CHANNEL_INFO)*nMaxChannelCount，结构体参考{@link NET_DEVICE_CHANNEL_INFO}数组
	 */
    public Pointer          pstuChannelsInfo;
	/**
	 * 是否支持白光灯控制
	 */
    public int              bControlLighter;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1024];

	public NET_GET_DEVICE_INFO_EX() {
		for (int i = 0; i < szMacs.length; i++) {
			szMacs[0] = new BYTE_18();
		}
	}
}

