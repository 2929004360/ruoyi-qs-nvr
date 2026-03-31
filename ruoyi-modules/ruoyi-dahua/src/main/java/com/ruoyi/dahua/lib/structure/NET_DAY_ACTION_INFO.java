package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 时段计划列表信息
 * @date 2021/09/27
 */
public class NET_DAY_ACTION_INFO extends NetSDKLib.SdkStructure {
	/**
     *  时段编号
     */
    public int              nActionNumber;
    /**
     *  时段的起始小时
     */
    public int              nBeginHour;
    /**
     *  时段的起始分钟
     */
    public int              nBeginMinute;
    /**
     *  周期方案编号
     */
    public int              nCyclePlanNum;
    /**
     *  时段模式| 1定时控制 | 242单点自适应模式 | 246实时控制模式 | 247灭灯模式 | 224行人优先控制模式 | 225 拥堵溢出控制模式 | 226 夜间请求模式 | 250手动控制 | 252全红控制 | 254感应控制 | 255黄闪控制 |
     */
    public int              nCyclePlanControlMode;
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

