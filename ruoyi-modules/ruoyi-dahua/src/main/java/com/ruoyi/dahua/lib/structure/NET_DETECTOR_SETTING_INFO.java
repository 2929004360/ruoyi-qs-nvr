package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 检测器配置表
 * @date 2021/09/28
 */
public class NET_DETECTOR_SETTING_INFO extends NetSDKLib.SdkStructure {
	/**
     *  检测器编号
     */
    public int              nDetectorNum;
    /**
     *  车道编号
     */
    public int              nDetectorRoadNum;
    /**
     *  进口方向
     */
    public int              nDetectorentryDir;
    /**
     *  进口类型
     */
    public int              nDetectorentryType;
    /**
     *  参与相位饱和度计算
     */
    public int              bJoinPhaseSplit;
    /**
     *  参与周期饱和度计算
     */
    public int              bJoinCycleSplit;
    /**
     *  预留字节 
     */
    public byte[]           szReserved = new byte[32];
}

