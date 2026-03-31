package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 智能分析结果的信息
 * @origin autoTool
 * @date 2023/08/31 14:21:53
 */
public class NET_VEHICLE_INOUT_ANALYSE_PROC extends NetSDKLib.SdkStructure {
	/**
	 * 事件名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 时间戳(单位是毫秒)
	 */
    public double           dbPTS;
	/**
	 * 发生的时间
	 */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
	/**
	 * 事件ID
	 */
    public int              nEventID;
	/**
	 * 包序号,用于校验是否丢包
	 */
    public int              nSequence;
	/**
	 * 视频分析帧序号
	 */
    public int              nFrameSequence;
	/**
	 * 物体列表, refer to {@link NET_VEHICLE_OBJECT}
	 */
    public Pointer          pstuObjets;
	/**
	 * 物体有效个数
	 */
    public int              nObjectNum;
	/**
	 * 统计有效个数
	 */
    public int              nStatNum;
	/**
	 * 各个车道实时交通流量统计信息, 最大支持8车道
	 */
    public NET_TRAFFIC_FLOW_STAT[] stuStats = new NET_TRAFFIC_FLOW_STAT[8];
	/**
	 * 雷达安装角度（雷达坐标系Y轴与正北方向的逆时针夹角）单位：度 (0 ~ 360)
	 */
    public double           dbRadarInstallAngle;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[2048];

	public NET_VEHICLE_INOUT_ANALYSE_PROC() {
		for (int i = 0; i < stuStats.length; i++) {
			stuStats[i] = new NET_TRAFFIC_FLOW_STAT();
		}
	}
}

