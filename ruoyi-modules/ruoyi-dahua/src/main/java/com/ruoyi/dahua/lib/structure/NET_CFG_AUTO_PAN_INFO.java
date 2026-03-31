package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 水平旋转设置配置, 对应枚举 NET_EM_CFG_AUTO_PAN
 * @origin autoTool
 * @date 2023/10/16 19:21:33
 */
public class NET_CFG_AUTO_PAN_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 结构体大小, 必须赋值
     */
    public int              dwSize;
    /**
     * / 水平旋转速度,0~7
     */
    public int              nPanSpeed;

    public NET_CFG_AUTO_PAN_INFO() {
        this.dwSize = this.size();
    }
}

