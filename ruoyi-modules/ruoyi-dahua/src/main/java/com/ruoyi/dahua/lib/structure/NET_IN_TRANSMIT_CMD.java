package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description CLIENT_TransmitCmd 接口输入参数
 * @date 2023/03/15 21:31:22
 */
public class NET_IN_TRANSMIT_CMD extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 通道号, 范围 0~设备通道数
	 */
    public int              nChannel;
	/**
	 * 协议类型，0 : 整形数组
	 */
    public int              nHannel;
	/**
	 * 此命令需要接收回复,0 - false   1 -true
	 */
    public int              bReply;
	/**
	 * 命令字节数量
	 */
    public int              nMessageCount;
	/**
	 * 具体命令，是一个数组，每个元素为每个字节的整形值，使用DVR同前端交互协议,需要用户分配内存空间。指针对应int[]数组
	 */
    public Pointer          pMessage;

	public NET_IN_TRANSMIT_CMD() {
		this.dwSize = this.size();
	}
}

