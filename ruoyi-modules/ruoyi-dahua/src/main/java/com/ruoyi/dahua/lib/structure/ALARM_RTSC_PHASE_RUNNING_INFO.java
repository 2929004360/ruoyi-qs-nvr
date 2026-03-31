package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description RTSC跑动暂停事件, 对应事件类型 NET_ALARM_RTSC_PHASE_RUNING
 * @date 2021/08/24
 */
public class ALARM_RTSC_PHASE_RUNNING_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  通道号,从0开始
     */
    public int              nChannelID;
    /**
     *  事件动作, 1表示开始, 2表示结束, -1表示未知
     */
    public int              nEventAction;
    /**
     *  当前运行模式,参考枚举{ @link EM_CURRENT_OPERATE_MODE }
     */
    public int              emCurOperateMode;
    /**
     *  运行相位个数
     */
    public int              nRunPhaseNum;
    /**
     *  运行相位信息
     */
    public RUN_PHASE_INFO[] stRunPhaseInfo = (RUN_PHASE_INFO[]) new RUN_PHASE_INFO().toArray(16);
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[512];
}

