package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 选票设置信息
 * @date 2021/09/28
 */
public class NET_VOTE_SETTINGS_INFO extends NetSDKLib.SdkStructure {
	/**
     *  周期容许秒数：周期增加或减少幅度未超出容许值，认为周期未增加或减少（0 ~ 10）
     */
    public int              nCycleChangeTolerate;
    /**
     *  周期增加所需票数（1 ~ 5）
     */
    public int              nCycleUpVote;
    /**
     *  周期减少所需票数（1 ~ 5）
     */
    public int              ncycleDnVote;
    /**
     *  预留字节 
     */
    public byte[]           szReserved = new byte[32];
}

