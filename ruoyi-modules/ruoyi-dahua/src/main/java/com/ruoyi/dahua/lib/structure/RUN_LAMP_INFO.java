package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 运行灯组信息
 * @date 2021/08/30
 */
public class RUN_LAMP_INFO extends NetSDKLib.SdkStructure {
	/**
     *  通道编号
     */
    public int              nLampNum;
    /**
     *  相机状态,参考枚举{ @link EM_CAMERA_STATUS }
     */
    public int              emCameraStatus;
    /**
     *  通道对应灯色倒计时
     */
    public int              nCntDown;
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[256];
}

