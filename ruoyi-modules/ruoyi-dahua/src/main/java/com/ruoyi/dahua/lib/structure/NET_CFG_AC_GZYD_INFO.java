package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： 二维码对接、人员开门时间配置、二维码密钥卡号 对应枚举 NET_EM_CFG_AC_GZYD
 * @since ： Created in 2021/11/24 14:19
 */

public class NET_CFG_AC_GZYD_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public			int            dwSize;
    /**
     * 使能
     */
    public			int            bEnable;
    /**
     * 时间间隔
     */
    public			int            nInterval;
    /**
     * 二维码有效时间
     */
    public			int            nQRLastTime;
    /**
     * 请求地址，注册地址
     */
    public			byte[]         szUrl0 = new byte[256];
    /**
     * 请求地址，心跳地址
     */
    public			byte[]         szUrl1 = new byte[256];
    /**
     * 设备编号
     */
    public			byte[]         szDeviceID = new byte[32];
    /**
     * 二维码内容
     */
    public			byte[]         szQRCONTENT = new byte[256];
    /**
     * 预留字节
     */
    public			byte[]         szReserved = new byte[1024];

    public NET_CFG_AC_GZYD_INFO() {
        this.dwSize = this.size();
    }
}

