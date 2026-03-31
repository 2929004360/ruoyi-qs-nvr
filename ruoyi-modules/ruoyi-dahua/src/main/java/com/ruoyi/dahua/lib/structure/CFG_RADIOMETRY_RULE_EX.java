package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 测温规则扩展
 * @date 2023/03/15 10:25:00
 */
public class CFG_RADIOMETRY_RULE_EX extends NetSDKLib.SdkStructure {
	/**
	 * 标定黑体温度，精度0.1，实际值扩大了10倍；比如显示是38度，实际该值获取的是380；如果需要设置39度，如该值需要传入390。
	 */
    public int              nBlackBodyTemp;
    /**
     * 高精度目标反射温度使能
    */
    public int              bUsefReflectedTemp;
    /**
     * 目标反射温度，使用此字段需赋值bUsefReflectedTemp为true
    */
    public float            fReflectedTemp;
    /**
     * 测温点报警设置Ex，数组有效个数为CFG_RADIOMETRY_RULE.nAlarmSettingCnt,参见结构体定义 {@link CFG_RADIOMETRY_ALARMSETTING_EX}
    */
    public CFG_RADIOMETRY_ALARMSETTING_EX[] stuAlarmSettingEx = new CFG_RADIOMETRY_ALARMSETTING_EX[64];
    /**
     * 物体距离(小数部分)
    */
    public float            fDistanceDecimalPart;
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[240];

	public CFG_RADIOMETRY_RULE_EX() {
	}
}

