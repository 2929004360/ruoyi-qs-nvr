package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 心率呼吸异常报警
 * @date 2022/08/04 10:13:30
 */
public class NET_CFG_MINIRADAR_HEATERATE extends NetSDKLib.SdkStructure {
    /**
     * 使能开关
     */
    public int              bEnable;
    /**
     * 合理范围的上限值，大于这个值报警
     */
    public int              nMax;
    /**
     * 合理范围的下限值，小于这个值报警
     */
    public int              nMin;
    /**
     * 单位秒, 报警延迟时间
     */
    public int              nDelay;
    /**
     * 关联报警输出端口，每一bit代表一路报警输出
     */
    public int              nAlarmOutMask;
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[1020];

    public NET_CFG_MINIRADAR_HEATERATE() {
    }
}

