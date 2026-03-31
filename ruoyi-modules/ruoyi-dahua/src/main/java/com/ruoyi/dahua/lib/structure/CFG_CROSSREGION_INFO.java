package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型EVENT_IVS_CROSSREGIONDETECTION(警戒区事件)对应的规则配置
 * @date 2021/07/06
 */
public class CFG_CROSSREGION_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  规则名称,不同规则不能重名
     */
    public byte[]           szRuleName = new byte[NetSDKLib.MAX_NAME_LEN];
    /**
     *  规则使能
     */
    public byte             bRuleEnable;
    /**
     *  触发跟踪使能,仅对绊线，入侵规则有效
     */
    public byte             bTrackEnable;
    /**
     *  保留字段
     */
    public byte[]           bReserved = new byte[2];
    /**
     *  相应物体类型个数
     */
    public int              nObjectTypeNum;
    /**
     *  相应物体类型列表
     */
    public MaxNameByteArrInfo[] szObjectTypes = (MaxNameByteArrInfo[])new MaxNameByteArrInfo().toArray(NetSDKLib.MAX_OBJECT_LIST_SIZE);
    /**
     *  检测方向:0:Enter;1:Leave;2:Both
     */
    public int              nDirection;
    /**
     *  警戒区顶点数
     */
    public int              nDetectRegionPoint;
    /**
     *  警戒区
     */
    public NetSDKLib.CFG_POLYGON[] stuDetectRegion = (NetSDKLib.CFG_POLYGON[]) new NetSDKLib.CFG_POLYGON().toArray(NetSDKLib.MAX_POLYGON_NUM);
    /**
     *  报警联动
     */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuEventHandler;
    /**
     *  事件响应时间段
     */
    public NetSDKLib.TIME_SECTION_WEEK_DAY_10[] stuTimeSectionWeekDay = (NetSDKLib.TIME_SECTION_WEEK_DAY_10[])new NetSDKLib.TIME_SECTION_WEEK_DAY_10().toArray(NetSDKLib.WEEK_DAY_NUM);
    /**
     *  stuTimeSection字段是否禁用，默认FALSE：不禁用，TRUE：禁用，用户控制
     */
    public int              bDisableTimeSection;
    /**
     *  云台预置点编号 0~65535
     */
    public int              nPtzPresetId;
    /**
     *  规则特定的尺寸过滤器是否有效
     */
    public int              bSizeFileter;
    /**
     *  规则特定的尺寸过滤器
     */
    public NetSDKLib.CFG_SIZEFILTER_INFO stuSizeFileter;
    /**
     *  检测动作数
     */
    public int              nActionType;
    /**
     *  检测动作列表,0-出现 1-消失 2-在区域内 3-穿越区域
     */
    public byte[]           bActionType = new byte[4];
    /**
     *  最小目标个数(当bActionType中包含"2-在区域内"时有效)
     */
    public int              nMinTargets;
    /**
     *  最大目标个数(当bActionType中包含"2-在区域内"时有效)
     */
    public int              nMaxTargets;
    /**
     *  最短持续时间， 单位秒(当bActionType中包含"2-在区域内"时有效)
     */
    public int              nMinDuration;
    /**
     *  报告时间间隔， 单位秒(当bActionType中包含"2-在区域内"时有效)
     */
    public int              nReportInterval;
    /**
     *  跟踪持续时间,0秒:一直跟踪,1~300秒:跟踪持续时间 
     */
    public int              nTrackDuration;
    /**
     *  检测的车辆子类型个数
     */
    public int              nVehicleSubTypeNum;
    /**
     *  检测的车辆子类型列表
     */
    public int[]            emVehicleSubType = new int[128];
    /**
     *  是否开启目标属性识别,默认false
     */
    public int              bFeatureEnable;
    /**
     *  前端视频分析联动
     */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuRemoteEventHandler;
    /**
     *  前端视频分析联动响应时间段
     */
    public NetSDKLib.TIME_SECTION_WEEK_DAY_10[] stuRemoteTimeSection = (NetSDKLib.TIME_SECTION_WEEK_DAY_10[])new NetSDKLib.TIME_SECTION_WEEK_DAY_10().toArray(NetSDKLib.WEEK_DAY_NUM);
    /**
     *  stuRemoteTimeSection字段是否禁用, 默认FALSE：不禁用, TRUE：禁用, 用户控制
     */
    public int              bDisableRemoteTimeSection;
    /**
     *  物体过滤器是否有效, 即stuObjectFilter 是否有效
     */
    public int              bObjectFilter;
    /**
     *  物体过滤器信息 
     */
    public NET_CFG_OBJECT_FILTER_INFO stuObjectFilter;
    /**
     *  灵敏度，值越小灵敏度越低。取值1-10
     */
    public int              nSensitivity;
    /**
     *  报警类型, EM_CFG_CROSSREGION_ALARMTYPE
     */
    public int              emAlarmType;
    /**
     * 绝对时间数组,参见结构体定义 {@link NET_ABSOLUTE_TIME_INFO}
    */
    public NET_ABSOLUTE_TIME_INFO[] stuAbsoluteTime = new NET_ABSOLUTE_TIME_INFO[24];
    /**
     * 绝对时间数组信息有效个数
    */
    public int              nAbsoluteTimeNum;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];
}

