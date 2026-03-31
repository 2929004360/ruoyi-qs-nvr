package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 平台下发轮询配置信息
 * @date 2022/07/04 10:56:48
 */
public class NET_SET_POLLING_CONFIG_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 使能开关
	 */
    public int              bEnable;
	/**
	 * 通道号
	 */
    public int              nChannel;
	/**
	 * 全局配置
	 */
    public byte[]           szGlobalType = new byte[64];
	/**
	 * 规则配置个数
	 */
    public int              nRulelTypeCnt;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved1 = new byte[4];
	/**
	 * 规则配置
	 */
    public Byte64Arr[]      szRulelType = new Byte64Arr[16];
	/**
	 * 全局配置列表, 扩展支持单个通道多种智能的开启,和szGlobalType二选一使能,如果szGlobalTypeList 里面有场景内容则优先使用
	 */
    public Byte64Arr[]      szGlobalTypeList = new Byte64Arr[6];
	/**
	 * 全局配置列表个数
	 */
    public int              nGlobalTypeListNum;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[636];

	public NET_SET_POLLING_CONFIG_INFO() {
		for (int i = 0; i < szRulelType.length; i++) {
			szRulelType[i] = new Byte64Arr();
		}
		for (int i = 0; i < szGlobalTypeList.length; i++) {
			szGlobalTypeList[i] = new Byte64Arr();
		}

	}
}

