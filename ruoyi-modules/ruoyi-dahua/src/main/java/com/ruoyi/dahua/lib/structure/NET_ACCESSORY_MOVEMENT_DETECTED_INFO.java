package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 移动报警检测信息
*/
public class NET_ACCESSORY_MOVEMENT_DETECTED_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 使能开关字段是否有效
    */
    public int              abEnable;
    /**
     * 使能开关
    */
    public int              bEnable;
    /**
     * 警号联动个数
    */
    public int              nSirenLinkageNum;
    /**
     * 警号联动
    */
    public int[]            nSirenLinkage = new int[256];
    /**
     * 预留字段
    */
    public byte[]           szReserved = new byte[64];

    public NET_ACCESSORY_MOVEMENT_DETECTED_INFO() {
    }
}

