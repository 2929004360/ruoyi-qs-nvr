package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 报警事件类型 EVENT_ALARM_ANALOGALARM(模拟量报警通道报警),
 * @date 2023/01/10 19:44:57
 */
public class DEV_EVENT_ALARM_ANALOGALRM_INFO extends NetSDKLib.SdkStructure {
    /**
     * (视频)通道号
     */
    public int              nChannelID;
    /**
     * 模拟量报警通道名称
     */
    public byte[]           szName = new byte[128];
    /**
     * 保留字节对齐
     */
    public byte[]           Reserved = new byte[4];
    /**
     * 时间戳(单位是毫秒)
     */
    public double           PTS;
    /**
     * 事件发生的时间
     */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
    /**
     * 事件ID
     */
    public int              nEventID;
    /**
     * 事件对应文件信息
     */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
    /**
     * 模拟量报警通道号
     */
    public int              nIndex;
    /**
     * 传感器类型 {@link com.ruoyi.dahua.lib.enumeration.NET_SENSE_METHOD}
     */
    public int              emSensor;
    /**
     * 数据状态, -1:未知,0:正常,1:数据无效(超过量程),
     * 2:超过阈值1,3:超过阈值2,4:超过阈值3,5:超过阈值4,
     * 6:低于阈值1,7:低于阈值2,8:低于阈值3,9:低于阈值4
     */
    public int              nStatus;
    /**
     * null
     */
    public float            fValue;
    /**
     * 数据采集时间(UTC)
     */
    public NET_TIME         stuCollectTime = new NET_TIME();
    /**
     * 抓图标志(按位),具体见NET_RESERVED_COMMON
     */
    public int              dwSnapFlagMask;
    /**
     * 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
     */
    public byte             bEventAction;
    /**
     * 传感器数值单位 {@link NetSDKLib.NET_TEMPERATURE_UNIT}
     */
    public int              emTemperatureUnit;
    /**
     * 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 保留字节,留待扩展
     */
    public byte[]           byReserved2 = new byte[1019];

    public DEV_EVENT_ALARM_ANALOGALRM_INFO() {
    }
}

