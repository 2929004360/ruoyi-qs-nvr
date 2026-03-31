package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 相位所属配置表
 * @date 2021/09/28
 */
public class NET_CLOSURE_SETTING_INFO extends NetSDKLib.SdkStructure {
	/**
     *  相位所属环编号
     */
    public int              nRingNo;
    /**
     *  相位所属栅栏编号
     */
    public int              nBarrierNo;
    /**
     *  相位所属栅栏内的位置
     */
    public int              nGridNo;
    /**
     *  相位编号
     */
    public int              nPhaseNum;
    /**
     *  相位重复出现的次数
     */
    public int              nPhaseOrder;
    /**
     *  参照设备id
     */
    public byte[]           szReferDeviceId = new byte[72];
    /**
     *  参照设备名称
     */
    public byte[]           szReferDeviceName = new byte[72];
    /**
     *  参照方案编号
     */
    public int              nReferPlanNum;
    /**
     *  参照方案名称
     */
    public byte[]           szReferPlanName = new byte[72];
    /**
     *  参照相位
     */
    public int              nReferPhaseNum;
    /**
     *  参照相位重复出现的次数
     */
    public int              nReferPhaseOrder;
    /**
     *  绿信比/时间
     */
    public double           dbSplitTime;
    /**
     *  类型，1绿信比/2时间
     */
    public int              nReferType;
    /**
     *  字节对齐
     */
    public byte[]           szReserved1 = new byte[4];
    /**
     *  预留字节 
     */
    public byte[]           szReserved = new byte[32];
}

