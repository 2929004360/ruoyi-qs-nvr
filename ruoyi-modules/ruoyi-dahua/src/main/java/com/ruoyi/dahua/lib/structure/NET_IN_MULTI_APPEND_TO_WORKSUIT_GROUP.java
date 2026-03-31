package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description CLIENT_MultiAppendToWorkSuitCompareGroup 接口输入参数
 * @date 2022/10/08 19:49:53
 */
public class NET_IN_MULTI_APPEND_TO_WORKSUIT_GROUP extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 工装合规样本个数, 由用户指定大小
	 */
    public int              nWorkSuitSampleNum;
	/**
	 * 工装合规样本信息, 内存由用户申请, 申请大小为nWorkSuitSampleNum * sizeof(NET_WORKSUIT_SAMPLE_INFO)
	 */
    public Pointer          pstWorkSuitSampleInfo;
	/**
	 * 工装合规导入标识信息
	 */
    public NET_WORKSUIT_APPEND_IDENTI_INFO stuAppendIdentiInfo = new NET_WORKSUIT_APPEND_IDENTI_INFO();
	/**
	 * 工装合规导入图片缓冲区, 用于存放需要导入的图片数据, 内存由用户申请
	 */
    public Pointer          pszImageBuffer;
	/**
	 * 图片缓冲区大小
	 */
    public int              nImageBufLen;
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved = new byte[4];

	public NET_IN_MULTI_APPEND_TO_WORKSUIT_GROUP() {
		this.dwSize = this.size();
	}
}

