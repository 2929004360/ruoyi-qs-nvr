package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 饱和度与单点自适应智能化配置表信息
 * @date 2021/09/28
 */
public class NET_OPTIMIZES_INFO extends NetSDKLib.SdkStructure {
	/**
     *  周期方案编号
     */
    public int              nCyclePlanNum;
    /**
     *  协调相位编号
     */
    public int              nCoordinatePhaseNum;
    /**
     *  协调相位重复出现的次数
     */
    public int              nCoordinatePhaseOrder;
    /**
     *  相位所属环编号
     */
    public int              nCoordinateRingNo;
    /**
     *  相位所属栅栏编号
     */
    public int              nCoordinateBarrierNo;
    /**
     *  相位所属栅栏内的位置
     */
    public int              nCoordinateGridNo;
    /**
     *  参照设备id
     */
    public byte[]           szReferDeviceId = new byte[72];
    /**
     *  参照设备名称
     */
    public byte[]           szReferDeviceName = new byte[72];
    /**
     *  参照路口的周期方案编号
     */
    public int              nReferCyclePlanNum;
    /**
     *  参照路口的周期方案名称
     */
    public byte[]           szReferCyclePlanName = new byte[72];
    /**
     *  参考相位
     */
    public int              nReferPhaseNum;
    /**
     *  参照相位重复出现的次数
     */
    public int              nReferPhaseOrder;
    /**
     *  参考类型 1为早，2为晚
     */
    public int              nReferType;
    /**
     *  相位差
     */
    public int              nOffset;
    /**
     *  感应参数
     */
    public NET_SENIOR_INFO  stuSeniorInfo;
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

