package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 行人检测器列表信息
 * @date 2021/09/27
 */
public class NET_PED_DETECTOR_INFO extends NetSDKLib.SdkStructure {
    /**
     *  行人检测器编号，1-16
     */
    public int              nPedestrianDetectorNumber;
    /**
     *  行人检测器关联的相位
     */
    public int              nPedestrianDetectorCallPhase;
    /**
     *  行人检测器对应的接线端子
     */
    public int              nPedestrianDetectorPortNum;
    /**
     *  检测器类型1:行人按钮 2视频
     */
    public int              nPedestrianDetectorType;
    /**
     *  视频检测器IP
     */
    public byte[]           szPedestrianDetectorIp = new byte[16];
    /**
     *  视频检测器端口
     */
    public int              nPedestrianDetectorPort;
    /**
     *  视频检测器用户名
     */
    public byte[]           szPedestrianDetectorUser = new byte[72];
    /**
     *  视频检测器密码
     */
    public byte[]           szPedestrianDetectorPwd = new byte[72];
    /**
     *  检测区1,2
     */
    public int              nPedestrianDetectorArea;
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

