package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 251823
 * @description RTSC跑动暂停事件, 对应事件类型 NET_ALARM_RTSC_PHASE_RUNING
 * @date 2021/08/24
 */
public class RUN_PHASE_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  相位编号，A为1
     */
    public int              nPhaseNum;
    /**
     *  nt, 相位重复出现的次数，默认为1， A1为1，A2该字段为2
     */
    public int              nPhaseOrder;
    /**
     *  当前相位对应的车行灯通道，按位表示，低bit表示通道1
     */
    public  int             nCarLamp;
    /**
     *  当前相位对应的行人灯通道，按位表示，低bit表示通道1
     */
    public  int             nPsgLamp;
    /**
     *  相机状态,参考枚举{ @link EM_CAMERA_STATUS }
     */
    public int              emCameraStatus;
    /**
     *  相位对应灯色倒计时
     */
    public int              nCntDown;
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[512];
}

