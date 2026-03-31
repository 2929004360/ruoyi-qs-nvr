package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 获取补光灯能力出参(对应 : NET_LIGHTINGCONTROL_CAPS)
 * @origin autoTool
 * @date 2023/10/19 17:27:54
 */
public class NET_OUT_LIGHTINGCONTROL_CAPS extends NetSDKLib.SdkStructure {
    /**
     * /结构体大小
     */
    public int              dwSize;
    /**
     * /是否支持灯光控制
     */
    public int              bSupport;
    /**
     * /支持的灯光配置版本 {@link com.ruoyi.dahua.lib.enumeration.EM_LC_CONFIG_VERSION}
     */
    public int              emConfigVersion;
    /**
     * /灯光类型 {@link com.ruoyi.dahua.lib.enumeration.EM_LC_LIGHT_TYPE}
     */
    public int              emLightType;
    /**
     * /复合灯光类型
     */
    public int[]            anLightTypeComplex = new int[3];
    /**
     * /复合灯光类型数量
     */
    public int              nLightTypeComplexLen;
    /**
     * /近光灯组数量
     */
    public int              nNearLightNumber;
    /**
     * /中光灯组数量
     */
    public int              nMiddleLightNumber;
    /**
     * /远光灯组数量
     */
    public int              nFarLightNumber;
    /**
     * /默认支持的模式 {@link com.ruoyi.dahua.lib.enumeration.EM_LC_MODE}
     */
    public int              emDefaultMode;
    /**
     * /支持的模式类型
     */
    public int[]            anModes = new int[20];
    /**
     * /支持的模式数量
     */
    public int              nModesLen;
    /**
     * /复合灯模式信息
     */
    public NET_MODES_COMPLEX_LIGHT stuModesComplex = new NET_MODES_COMPLEX_LIGHT();
    /**
     * /灯光组信息
     */
    public NET_LIGHT_TYPE_COMPLEX_DETAIL stuLightTypeComplexDetail = new NET_LIGHT_TYPE_COMPLEX_DETAIL();
    /**
     * /云台联动灯光信息
     */
    public NET_LINKING_DETAIL stuLinkingDetail = new NET_LINKING_DETAIL();
    /**
     * /灯光组功率控制掩码
     */
    public int[]            anPower = new int[3];
    /**
     * /灯光组激光角度控制掩码
     */
    public int[]            anAngleControl = new int[3];
    /**
     * /灯光补偿信息
     */
    public NET_CORRECTION   stuCorrection = new NET_CORRECTION();
    /**
     * /灯光灵敏度信息
     */
    public NET_SENSITIVITY  stuSensitivity = new NET_SENSITIVITY();
    /**
     * /是否支持激光灯光轴调节
     */
    public int              bSupportLaserLightMove;
    /**
     * /定时模式支持的时间段数量
     */
    public int              nLightingTimeSectionNum;
    /**
     * /是否支持分时配置
     */
    public int              bSupportByTime;
    /**
     * /是否支持复合灯模式信息
     */
    public int              bSupportModesComplex;
    /**
     * 防补光过曝,参见结构体定义 {@link NET_ANTIFILLLIGHTOVEREXPOSURE}
    */
    public NET_ANTIFILLLIGHTOVEREXPOSURE stuAntiFillLightOverExposure = new NET_ANTIFILLLIGHTOVEREXPOSURE();
    /**
     * 亮度上限调节,参见结构体定义 {@link NET_BRIGHTNESSUPPERLIMIT}
    */
    public NET_BRIGHTNESSUPPERLIMIT stuBrightnessUpperLimit = new NET_BRIGHTNESSUPPERLIMIT();

    public NET_OUT_LIGHTINGCONTROL_CAPS() {
        this.dwSize = this.size();
    }
}

