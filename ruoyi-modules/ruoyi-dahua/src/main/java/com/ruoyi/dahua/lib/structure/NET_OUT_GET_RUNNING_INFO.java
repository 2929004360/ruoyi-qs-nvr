package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetRtscRunningInfo 接口输出参数
 * @date 2021/09/28
 */
public class NET_OUT_GET_RUNNING_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * // bit0:运行状态 // bit1:控制方式 // bit2:车道功能状态 // bit3:车道/匝道控制状态信息 //bit4:当前信号方案色步信息 // bit5: 下一个周期信号方案色步信息
	 */
    public int              dwReportState;
	/**
	 * 经度个数
	 */
    public int              nLongitudeNum;
	/**
	 * 经度,格式：度，分，秒(秒为浮点数)
	 */
    public double[]         dbLongitude = new double[3];
	/**
	 * 纬度个数
	 */
    public int              nLatitudeNum;
	/**
	 * 纬度,格式：度，分，秒(秒为浮点数)
	 */
    public double[]         dbLatitude = new double[3];
	/**
	 * 高度，单位为米
	 */
    public double           dbAltitude;
	/**
	 * 事件发生时间，带时区偏差的UTC时间，单位秒
	 */
    public NET_TIME_EX      UTC;
	/**
	 * 设备状态, { @link EM_STATUS }
	 */
    public int              emStatus;
	/**
	 * 控制模式
	 */
    public int              emControlMode;
	/**
	 * 车道功能状态
	 */
    public LANE_STATE       stuLaneStateInfo;
	/**
	 * 车道/匝道控制状态
	 */
    public LANE_CTRL_STATE  stuLaneCtrlStateInfo;
	/**
	 * 当前信号方案色步信息
	 */
    public CUR_STEP_INFO    stuCurStepInfo;
	/**
	 * 下一个周期信号方案色步信息
	 */
    public NEXT_STEP_INFO   stuNextStepInfo;

	public NET_OUT_GET_RUNNING_INFO() {
		this.dwSize = this.size();
	}
}

