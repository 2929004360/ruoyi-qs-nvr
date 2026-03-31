package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  环境光线配置信息 
* @origin autoTool
* @date 2023/11/30 16:17:33
*/
public class CFG_VIDEOIN_BACKLIGHT_BASE extends NetSDKLib.SdkStructure {
/** 
背光模式 {@link com.ruoyi.dahua.lib.enumeration.EM_VIDEOIN_BACKLIGHT_MODE}
*/
    public			int            emMode;
/** 
背光补偿模式，当emMode == EM_BACKLIGHT_BACKLIGHT时有效 {@link com.ruoyi.dahua.lib.enumeration.EM_BACKLIGHT_MODE}
*/
    public			int            emBacklightMode;
/** 
背光补偿区域，当emBacklightMode == EM_BACKLIGHT_REGION时有效
*/
    public NetSDKLib.CFG_RECT stuBacklightRegion = new NetSDKLib.CFG_RECT();
/** 
宽动态值，emMode == EM_BACKLIGHT_WIDEDYNAMIC时有效
*/
    public			int            nWideDynamicRange;
/** 
强光抑制，emMode == EM_BACKLIGHT_GLAREINHIBITION时有效
*/
    public			int            nGlareInhibition;
/** 
SSA对比度调节模式，emMode==EM_BACKLIGHT_SSA时有效 {@link com.ruoyi.dahua.lib.enumeration.EM_INTENSITY_MODE}
*/
    public			int            emInitensityMode;
/** 
SSA手动调整强度值，emInitensityMode==EM_INTENSITY_MANUAL时有效
*/
    public			int            nIntensity;
    /**
     * 场景名称
    */
    public byte[]           szName = new byte[16];
    /**
     * 强光抑制模式, emMode == EM_BACKLIGHT_GLAREINHIBITION时有效,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_GLARE_INHIBITION_MODE}
    */
    public int              emGlareInhibitionMode;
    /**
     * FPGA模式下强光抑制灵敏度, 范围[0-100]
    */
    public int              nGlareInSensitivity;
    /**
     * FPGA模式下强光抑制增益下限, 范围[0-100], 且GainMinBLC <= GainMaxBLC
    */
    public int              nGainMinBLC;
    /**
     * FPGA模式下强光抑制增益上限, 范围[0-100], 且GainMinBLC <= GainMaxBLC
    */
    public int              nGainMaxBLC;
    /**
     * FPGA模式下强光抑制曝光时间下限, 单位ms, 范围[0.1-40.0], ExposureMinBLC <= ExposureMaxBLC
    */
    public double           dExposureMinBLC;
    /**
     * FPGA模式下强光抑制曝光时间上限, 单位ms, 范围[0.1-40.0], ExposureMinBLC <= ExposureMaxBLC
    */
    public double           dExposureMaxBLC;
    /**
     * 宽动态模式, 0：关闭 1：手动调节 2：自动调节
    */
    public int              nWideDynamicRangeMode;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[252];

public			CFG_VIDEOIN_BACKLIGHT_BASE(){
}

    @Override
    public String toString() {
        return "CFG_VIDEOIN_BACKLIGHT_BASE{" +
                "emMode=" + emMode +
                ", emBacklightMode=" + emBacklightMode +
                ", stuBacklightRegion=" + stuBacklightRegion +
                ", nWideDynamicRange=" + nWideDynamicRange +
                ", nGlareInhibition=" + nGlareInhibition +
                ", emInitensityMode=" + emInitensityMode +
                ", nIntensity=" + nIntensity +
                '}';
    }
}

