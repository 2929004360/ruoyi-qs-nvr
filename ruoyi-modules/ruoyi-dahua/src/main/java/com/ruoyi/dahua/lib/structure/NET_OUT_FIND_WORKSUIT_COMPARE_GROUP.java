package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description CLIENT_FindWorkSuitCompareGroup 接口输出参数
 * @date 2022/10/10 13:42:51
 */
public class NET_OUT_FIND_WORKSUIT_COMPARE_GROUP extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved = new byte[4];
	/**
	 * 工装合规组最大个数, 由用户指定, 即pstWorkSuitGroupInfo 数组大小
	 */
    public int              nMaxGroupNum;
	/**
	 * 实际返回的工装合规组个数, 即pstWorkSuitGroupInfo数组的有效元素个数
	 */
    public int              nRetGroupNum;
	/**
	 * 工装合规组信息, 内存由用户申请, 申请大小nMaxGroupNum*sizeof(NET_WORKSUIT_COMPARE_GROUP_INFO)
	 */
    public Pointer          pstWorkSuitGroupInfo;

	public NET_OUT_FIND_WORKSUIT_COMPARE_GROUP() {
		this.dwSize = this.size();
	}
}

