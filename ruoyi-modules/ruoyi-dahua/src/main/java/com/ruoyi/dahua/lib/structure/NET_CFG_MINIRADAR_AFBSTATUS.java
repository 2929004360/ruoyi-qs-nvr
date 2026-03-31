package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 在/离床报警
 * @date 2022/08/04 10:13:30
 */
public class NET_CFG_MINIRADAR_AFBSTATUS extends NetSDKLib.SdkStructure {
    /**
     * 使能开关
     */
    public int              bEnable;
    /**
     * 0表示离床报警  1表示在床报警
     */
    public int              nAFBFlag;
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
    public byte[]           szReserved = new byte[1024];

    public NET_CFG_MINIRADAR_AFBSTATUS() {
    }
}

