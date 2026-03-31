package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 相位列表信息
 * @date 2021/09/02
 */
public class NET_PHASES_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  相位在栅栏内的位置
     */
    public int              nGridNo;
    /**
     *  相位编号
     */
    public int              nPhaseNum;
    /**
     *  相位重复出现的次数，默认为1， A1为1，A2该字段为
     */
    public int              nPhaseOrder;
    /**
     *  迟起时间
     */
    public int              nStartDelayTime;
    /**
     *  预黄时间
     */
    public int              nStartYellowTime;
    /**
     *  绿信比时间（迟起+预黄+绿灯+绿闪+黄灯+红灯）
     */
    public int              nGreenTime;
    /**
     *  绿闪时间
     */
    public int              nGreenFlashTime;
    /**
     *  黄灯时间
     */
    public int              nYellowTime;
    /**
     *  红灯清空
     */
    public int              nRedclearTime;
    /**
     *  最小绿灯时间
     */
    public int              nMinimumGreen;
    /**
     *  最大绿灯时间
     */
    public int              nMaximumGreen;
    /**
     *  感应控制递减前时间
     */
    public int              nBeforeReduction;
    /**
     *  感应控制延长绿时间
     */
    public int              nAddedInitial;
    /**
     *  相位保持状态 0-不保持，1-黄闪，2-常红，3-红闪，4-灭灯
     */
    public int              nPhaseKeepState;
    /**
     *  相位是否启用感应控制
     */
    public int              bFreeEnable;
    /**
     *  启用的检测器个数
     */
    public int              nSelectDetectorsNum;
    /**
     *  启用的检测器编号
     */
    public int[]            nSelectDetectors = new int[128];
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

