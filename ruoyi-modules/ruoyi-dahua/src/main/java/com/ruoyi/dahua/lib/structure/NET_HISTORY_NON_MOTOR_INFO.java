package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 非机动车信息
 * @date 2022/11/24 17:12:01
 */
public class NET_HISTORY_NON_MOTOR_INFO extends NetSDKLib.SdkStructure {
    /**
     * 非机动车唯一标识符
     */
    public byte[]           szUID = new byte[64];
    /**
     * 包围盒(8192坐标系)
     */
    public NET_RECT         stuBoundingBox = new NET_RECT();
    /**
     * 车上人员头盔状态 0-未知 1-没有 2-有
     */
    public int              nHelmet;
    /**
     * 帽子颜色
     */
    public byte[]           szCapColor = new byte[16];
    /**
     * 非机动车子类型
     */
    public byte[]           szCategory = new byte[32];
    /**
     * 非机动车颜色
     */
    public byte[]           szColor = new byte[16];
    /**
     * 非机动车车篮  0-未知 1-否 2-有
     */
    public int              nBasket;
    /**
     * 非机动车后备箱 0: 未知 1: 无后备箱 2: 非机动车自带箱 3: 非机动车自装箱 4: 非机动车自带自装箱都有
     */
    public int              nStoragebox;
    /**
     * 骑车人数, 0xff表示未知
     */
    public int              nNumOfCycling;
    /**
     * 非机动车图片信息
     */
    public SDK_PIC_INFO_EX  stuImage = new SDK_PIC_INFO_EX();
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[256];

    public NET_HISTORY_NON_MOTOR_INFO() {
    }
}

