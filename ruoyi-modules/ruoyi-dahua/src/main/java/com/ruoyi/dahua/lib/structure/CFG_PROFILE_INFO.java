package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 情景详细信息
 * @date 2023/03/15 20:39:47
 */
public class CFG_PROFILE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 情景ID
	 */
    public int              nSceneID;
	/**
	 * 厂家名称
	 */
    public byte[]           szBrand = new byte[64];
	/**
	 * 情景模式 {@link com.ruoyi.dahua.lib.enumeration.EM_SMARTHOME_SCENE_MODE}
	 */
    public int              emScene;
	/**
	 * 串口地址
	 */
    public NetSDKLib.CFG_COMMADDR_INFO stuCommAddr = new NetSDKLib.CFG_COMMADDR_INFO();

	public CFG_PROFILE_INFO() {
	}
}

