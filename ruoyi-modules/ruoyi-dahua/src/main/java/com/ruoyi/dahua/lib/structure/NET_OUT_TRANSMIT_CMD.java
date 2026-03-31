package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description CLIENT_TransmitCmd 接口输出参数
 * @date 2023/03/15 21:31:34
 */
public class NET_OUT_TRANSMIT_CMD extends NetSDKLib.SdkStructure {
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
	 * 返回命令长度
	 */
    public int              nRetMessageCount;
	/**
	 * 用户填写,为pMessage指针所指的数组元素数量
	 */
    public int              nMaxMessageCount;
	/**
	 * 返回值，是一个数组，每个元素为每个字节的整形值，需要用户分配内存空间。指针对应int[]数组
	 */
    public Pointer          pMessage;

	public NET_OUT_TRANSMIT_CMD() {
		this.dwSize = this.size();
	}
}

