package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * 上报的雷达报警点信息
 *
 * @author ： 119178
 * @since ： Created in 2021/2/03 13:56
 */
public class NET_RADAR_NOTIFY_ALARMPOINTINFO extends NetSDKLib.SdkStructure {
	/**
	 * 雷达报警点的数量
	 */
    public int              nNumAlarmPoint;
	/**
	 * 雷达报警点信息
	 * NET_RADAR_MAX_NUM_ALARMPOINT=64(雷达报警点的最大数量)
	 */
    public NET_RADAR_ALARMPOINT_INFO[] stuAlarmPoint = (NET_RADAR_ALARMPOINT_INFO[])new NET_RADAR_ALARMPOINT_INFO().toArray(64);
	/**
	 * 通道号
	 */
    public int              nChannel;
	 /**
     *  规则数量
     */
    public int              nRuleNum;
    /**
     *  雷达规则信息
     */
    public NET_RADAR_RULE_INFO[] stuRuleInfo = (NET_RADAR_RULE_INFO[]) new NET_RADAR_RULE_INFO().toArray(10);
    /**
     * 雷达设备识别到目标上报的时间，时间是标准UTC时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuRealUTC = new NetSDKLib.NET_TIME();
    /**
     * 雷达报警点的数量扩展
    */
    public int              nNumAlarmPointEx;
    /**
     * 雷达报警点信息扩展，指针由SDK内部申请释放,参见结构体定义 {@link NET_RADAR_ALARMPOINT_INFO}
    */
    public Pointer          pstuAlarmPointEx;
    /**
     *  保留字节
     */
    public byte[]           byReserved = new byte[268-NetSDKLib.POINTERSIZE];
}

