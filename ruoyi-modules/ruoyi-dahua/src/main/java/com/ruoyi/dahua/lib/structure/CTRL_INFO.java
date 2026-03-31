package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 控制信息
 * @date 2021/09/01
 */
public class CTRL_INFO extends NetSDKLib.SdkStructure {
	/**
     *  按位表示：Bit0：车道 Bit1：进口匝道 Bit2：出口匝道 Bit3~Bit7：保留 取值1表示具有，0表示不具有 
     */
    public int              nType;
    /**
     *  编号
     */
    public int              nNo;
    /**
     *  方向 整数，以地理正北方向为起点的顺时针旋转角度，单位为度（°）
     */
    public int              nDir;
    /**
     *  车道/匝道信号 1：开放 2：关闭
     */
    public int              nStatus;
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[128];
}

