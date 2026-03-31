package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 工装合规组信息
 * @date 2022/10/08 17:05:54
 */
public class NET_WORKSUIT_COMPARE_GROUP_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 合规组ID, 只读, 由设备生成, 创建工装库时无需指定
	 */
    public byte[]           szGroupID = new byte[64];
	/**
	 * 合规组名称
	 */
    public byte[]           szGroupName = new byte[128];
	/**
	 * 合规组备注信息
	 */
    public byte[]           szGroupDetail = new byte[256];
	/**
	 * 工装优选方案 {@link com.ruoyi.dahua.lib.enumeration.EM_WORKSUIT_GROUP_POLICY}
	 */
    public int              emWorkSuitPolicy;
	/**
	 * 相似度阈值，范围 [1, 100]，默认67
	 */
    public int              nSimilarity;
	/**
	 * 合规组类型 {@link com.ruoyi.dahua.lib.enumeration.EM_WORKSUIT_GROUP_TYPE}
	 */
    public int              emGroupType;
	/**
	 * 当前组内工装数, 只读, 获取时有效
	 */
    public int              nGroupSize;
	/**
	 * 合规库视频通道布控信息, 只读, 获取时有效
	 */
    public NET_WORKSUIT_CHANNEL_INFO[] stuChannelInfo = new NET_WORKSUIT_CHANNEL_INFO[1024];
	/**
	 * 当前组绑定的通道数量, 只读, 获取时有效
	 */
    public int              nChannelInfoNum;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[2044];

	public NET_WORKSUIT_COMPARE_GROUP_INFO() {
		for (int i = 0; i < stuChannelInfo.length; i++) {
			stuChannelInfo[i] = new NET_WORKSUIT_CHANNEL_INFO();
		}
	}
}

