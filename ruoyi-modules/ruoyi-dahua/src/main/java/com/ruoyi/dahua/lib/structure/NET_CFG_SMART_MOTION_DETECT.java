package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 智能动态检测配置
*/
public class NET_CFG_SMART_MOTION_DETECT extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 动检使能，开：TRUE 关：FALSE
    */
    public int              bEnable;
    /**
     * 动检敏感级别,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_SMART_MOTION_DETECT_SENSITIVITY_LEVEL}
    */
    public int              emMotionDetectSensitivityLevel;
    /**
     * 智能动态检测对象,参见结构体定义 {@link NET_SMART_MOTION_DETECT_OBJECT}
    */
    public NET_SMART_MOTION_DETECT_OBJECT stuMotionDetectObject = new NET_SMART_MOTION_DETECT_OBJECT();
    /**
     * 智能跟踪是否开启
    */
    public int              bSmartTrack;

    public NET_CFG_SMART_MOTION_DETECT() {
        this.dwSize = this.size();
    }
}

