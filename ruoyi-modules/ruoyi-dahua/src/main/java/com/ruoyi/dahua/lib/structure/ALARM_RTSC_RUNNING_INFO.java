package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 信号机运行事件, 对应事件类型NET_ALARM_RTSC_RUNING
 * @date 2021/09/01
 */
public class ALARM_RTSC_RUNNING_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  通道号,从0开始
     */
    public int              nChannelID;
    /**
     *  事件动作, 1表示开始, 2表示结束, -1表示未知
     */
    public int              nEventAction;
    /**
     *  bit0:运行状态
		bit1:控制方式
		bit2:灯色状态信息
		bit3:车道功能状态
		bit4:车道/匝道控制状态信息
		bit5:当前信号方案色步信息
		bit6: 下一个周期信号方案色步信息
     */
    public int              dwReportState;
    /**
     *  经度个数
     */
    public int              nLongitudeNum;
    /**
     *  经度,格式：度，分，秒(秒为浮点数)
     */
    public double[]         dbLongitude = new double[3];
    /**
     *  纬度个数
     */
    public int              nLatitudeNum;
    /**
     *  纬度,格式：度，分，秒(秒为浮点数)
     */
    public double[]         dbLatitude = new double[3];
    /**
     *  高度，单位为米 
     */
    public double           dbAltitude;
    /**
     *  事件发生时间，带时区偏差的UTC时间，单位秒
     */
    public NET_TIME_EX      UTC;
    /**
     *  设备状态 ,参考{ @link EM_STATUS}
     */
    public int              emStatus;
    /**
     *  控制模式,参考{ @link EM_CONTROL_MODE}
     */
    public int              emControlMode;
    /**
     *  灯色状态信息
     */
    public LAMP_STATE       stuLampStateInfo;
    /**
     *  车道功能状态
     */
    public LANE_STATE       stuLaneStateInfo;
    /**
     *  车道/匝道控制状态
     */
    public LANE_CTRL_STATE  stuLaneCtrlStateInfo;
    /**
     *  当前信号方案色步信息
     */
    public CUR_STEP_INFO    stuCurStepInfo;
    /**
     *  下一个周期信号方案色步信息
     */
    public NEXT_STEP_INFO   stuNextStepInfo;
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[256];
}

