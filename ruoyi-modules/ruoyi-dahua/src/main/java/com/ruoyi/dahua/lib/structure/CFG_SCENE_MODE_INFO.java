package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 情景模式配置 (对应 CFG_CMD_SCENE_MODE)
 * @date 2023/03/15 20:39:47
 */
public class CFG_SCENE_MODE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 当前情景模式ID号
	 */
    public int              nCurrentID;
	/**
	 * 情景模式个数
	 */
    public int              nProfileCount;
	/**
	 * 情景模式信息
	 */
    public CFG_PROFILE_INFO[] stuProfiles = new CFG_PROFILE_INFO[54];

	public CFG_SCENE_MODE_INFO() {
		for (int i = 0; i < stuProfiles.length; i++) {
			stuProfiles[i] = new CFG_PROFILE_INFO();
		}
	}
}

