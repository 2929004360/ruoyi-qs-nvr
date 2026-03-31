package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.NET_EM_LIGHTING_MODE;

/**
 * @author 421657
 * @description 补光灯配置
 * @origin autoTool
 * @date 2023/10/13 08:58:46
 */
public class NET_VIDEOIN_LIGHTING_INFO extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * / 灯光模式 {@link NET_EM_LIGHTING_MODE}
     */
    public int              emLightMode;
    /**
     * / 灯光补偿值，倍率优先时有效0-4
     */
    public int              nCorrection;
    /**
     * / 灯光灵敏度，倍率优先时有效，0-5，默认为3
     */
    public int              nSensitive;
    /**
     * / 近光灯亮度0-100
     */
    public int              nNearLight;
    /**
     * / 远光灯亮度0-100
     */
    public int              nFarLight;

    public NET_VIDEOIN_LIGHTING_INFO() {
        this.dwSize = this.size();
    }
}

