package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 相位表信息
 * @date 2021/09/28
 */
public class NET_PHASE_LIST_INFO extends NetSDKLib.SdkStructure {
	/**
     *  相位编号
     */
    public int              nPhaseNum;
    /**
     *  相位重复出现的次数
     */
    public int              nPhaseOrder;
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
     *  是否为时长变化
     */
    public int              bUsePhaseTime;
    /**
     *  相位时长
     */
    public int              nPhaseTime;
    /**
     *  相位时长上限（0 ~ 100）
     */
    public int              nPhaseUpLimit;
    /**
     *  相位时长下限（0 ~ 100）
     */
    public int              nPhaseDnLimit;
    /**
     *  是否为绿信比变化
     */
    public int              bUseSplitTime;
    /**
     *  基准方案绿信比
     */
    public double           dSplitBase;
    /**
     *  绿信比变化最大值
     */
    public int              nSplitUpLimit;
    /**
     *  绿信比变化最小值
     */
    public int              nSplitDnLimit;
    /**
     *  相位饱和度加权值
     */
    public double           dSaturitionWeight;
    /**
     *  相位每周期绿信比最大变化值
     */
    public double           dSplitMaxStep;
    /**
     *  相位饱和度计算策略：0：饱和度=average{车道饱和度} 1：饱和度=max{车道饱和度}
     */
    public int              bPermitMaxLaneSaturition;
    /**
     *  预留字节 
     */
    public byte[]           szReserved = new byte[32];
}

