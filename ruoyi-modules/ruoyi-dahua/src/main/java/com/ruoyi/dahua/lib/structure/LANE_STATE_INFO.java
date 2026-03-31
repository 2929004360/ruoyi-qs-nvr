package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 车道交通运行状态信息
 * @date 2021/09/01
 */
public class LANE_STATE_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  车道类型 
     *  车道类型 按位表示，Bit0~Bit15分别为： Bit0：直行 Bit1：左转 Bit2：右转 Bit3：掉头 Bit4：可变功能 Bit5：潮汐 Bit6：非机动 Bit7：公交专用 Bit8：有轨电车 Bit9~ Bit15保留 取值1表示具有，0表示不具有 
     */
    public int              nType;
    /**
     * 车道交通运行状态,参考枚举{ @link EM_LANE_RUNING_STATE}
     */
    public int              emLaneRunState;
    /**
     *  预留字节 
     */
    public byte[]           byReserved = new byte[128];
}

