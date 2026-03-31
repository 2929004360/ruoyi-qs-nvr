package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 检测器列表信息
 * @date 2021/09/27
 */
public class NET_SIGNAL_VEHICLE_DETECTOR_INFO extends NetSDKLib.SdkStructure {
	/**
     *  检测器编号
     */
    public int              nVehicleDetectorNumber;
    /**
     *  车道编号
     */
    public int              nVehicleDetectorRoadNum;
    /**
     *  1:线圈 2:视频1 (电警、卡口，实时过车协议) 3:视频2 (无锡所相机) 4:地磁 5:视频3(流量相机，压占信息协议) 6:视频4-出口
     */
    public int              nVehicleDetectorType;
    /**
     *  呼叫主相位
     */
    public int              nVehicleDetectorCallPhase;
    /**
     *  检测器位置 1:前置 2:后置
     */
    public int              nvehicleDetectorPost;
    /**
     *  检测器优先级(1表示第一优先级), 取值1-2
     */
    public int              nVehiclePrority;
    /**
     *  视频检测器IP
     */
    public byte[]           szVideoDetectorIp = new byte[16];
    /**
     *  视频检测器端口
     */
    public int              nVideoDetectorPort;
    /**
     *  视频检测器用户名
     */
    public byte[]           szVideoDetectorUser = new byte[72];
    /**
     *  视频检测器密码
     */
    public byte[]           szVideoDetectorPassword = new byte[72];
    /**
     *  检测通道编号编号范围为1-96,视频1车道号是1-40,视频2车道号是41-96,视频3车道号是1-40
     */
    public int              nVehicleDetectorChannelNum;
    /**
     *  相机里的车道编号,范围1-9
     */
    public int              nVideoDetectorChn;
    /**
     *  进口方向
     */
    public int              nVehicleDetectorentryDir;
    /**
     *  进口类型
     */
    public int              nVehicleDetectorentryType;
    /**
     *  在非视频检测器模式下，需要设置接线端子编号
     */
    public int              nVehicleDetectorPortNum;
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

