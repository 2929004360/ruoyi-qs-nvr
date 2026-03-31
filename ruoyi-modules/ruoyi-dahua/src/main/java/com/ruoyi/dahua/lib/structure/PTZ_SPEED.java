package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 251823
* @description  云台控制坐标单元 
* @date 2022/07/21 15:09:08
*/
public class PTZ_SPEED extends NetSDKLib.SdkStructure {
	/**
	 * 云台水平方向速率,归一化到-1~1
	 */
    public float            fPositionX;
    /**
     * 云台垂直方向速率,归一化到-1~1
     */
    public float            fPositionY;
    /**
     * 云台光圈放大倍率,归一化到0~1
     */
    public float            fZoom;
    /**
     * 预留32字节
     */
    public byte[]           szReserve = new byte[32];
}

