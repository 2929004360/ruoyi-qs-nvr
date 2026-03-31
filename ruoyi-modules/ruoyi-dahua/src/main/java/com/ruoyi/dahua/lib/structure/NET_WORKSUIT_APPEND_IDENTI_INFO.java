package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 工装合规导入标识信息
 * @date 2022/10/08 19:49:52
 */
public class NET_WORKSUIT_APPEND_IDENTI_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 该次图片导入的token值
	 */
    public int              nToken;
	/**
	 * 表示该次添加图片是否结束，TRUE表示结束，FALSE表示未结束
	 */
    public int              bAppendEnd;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1024];

	public NET_WORKSUIT_APPEND_IDENTI_INFO() {
	}
}

