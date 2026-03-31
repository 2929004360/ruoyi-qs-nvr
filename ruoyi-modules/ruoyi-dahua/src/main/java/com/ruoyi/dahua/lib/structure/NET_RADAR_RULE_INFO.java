package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 雷达检测对象
 *
 * @author ： 251823
 * @since ： Created in 2021/9/26
 */
public class NET_RADAR_RULE_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  规则编号
     */
    public int              nRuleID;
    /**
     *  规则内轨迹数量
     */
    public int              nPointNumber;
    /**
     *  正在跟踪目标的球机, 0 为无效值
     */
    public int              nTrackerIP;
    /**
     *  保留字节
     */
    public byte[]           byReserved = new byte[60];
}

