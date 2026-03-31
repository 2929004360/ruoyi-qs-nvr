package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 251823
 * @description 从加油机获取的信息
 * @date 2022/04/11
 */
public class NET_FUEL_DISPENSER_INFO extends NetSDKLib.SdkStructure {
    /**
     *  开始加油时间，带时区偏差的UTC时间，单位秒
     */
    public int              nFuelingStartTime;
    /**
     *  结束加油时间，带时区偏差的UTC时间，单位秒
     */
    public int              nFuelingEndTime;
    /**
     *  加油金额，单位分
     */
    public int              nMoney;
    /**
     *  加油升数，单位0.01升
     */
    public int              nLitre;
    /**
     *  加油类型，加油油号
     */
    public byte[]           szOilType = new byte[8];
    /**
     *  保留字节
     */
    public byte[]           szReserved = new byte[128];
}

