package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description Mini雷达安装信息配置, 对应配置项 NET_EM_CFG_MINIRADAR_INSTALL_INFO
 * @date 2022/08/04 10:13:30
 */
public class NET_CFG_MINIRADAR_INSTALL_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 安装方式(室内雷达使用) 0:未知, 1:壁装, 2:墙角装
     */
    public int              nInstallType;
    /**
     * 安装高度；单位米，扩大100倍
     */
    public int              nInstallHeigh;
    /**
     * 安装角度；单位度，扩大100倍
     */
    public int              nInstallAngle;

    public NET_CFG_MINIRADAR_INSTALL_INFO() {
        this.dwSize = this.size();
    }
}

