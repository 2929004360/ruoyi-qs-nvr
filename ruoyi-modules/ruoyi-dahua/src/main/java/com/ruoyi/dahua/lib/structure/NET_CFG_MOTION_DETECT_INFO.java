package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 动态检测报警配置配置, 对应枚举NET_EM_CFG_MOTION_DETECT
*/
public class NET_CFG_MOTION_DETECT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小, 必须赋值
    */
    public int              dwSize;
    /**
     * 动态检测开启使能
    */
    public int              bEnable;
    /**
     * 手动控制云台时，动检是否开启
    */
    public int              bPtzManualEnable;
    /**
     * 动检提示OSD闪烁使能
    */
    public int              bOsdTwinkleEnable;
    /**
     * 面积占用比, 1-100, 0 表示此配置无效
    */
    public int              nVolumeRatio;
    /**
     * 动检的残差阈值, 1-100, 0 表示此配置无效
    */
    public int              nSubRatio;
    /**
     * 动态检测联动,参见结构体定义 {@link NetSDKLib.NET_ALARM_MSG_HANDLE}
    */
    public NetSDKLib.NET_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.NET_ALARM_MSG_HANDLE();
    /**
     * 三代动检支持的视频窗口个数
    */
    public int              nMotionDetectWindowCount;
    /**
     * 三代动检支持的视频窗口,参见结构体定义 {@link NET_MOTION_DETECT_WINDOW}
    */
    public NET_MOTION_DETECT_WINDOW[] stuMotionDetectWindow = new NET_MOTION_DETECT_WINDOW[4];

    public NET_CFG_MOTION_DETECT_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuMotionDetectWindow.length; i++){
            stuMotionDetectWindow[i] = new NET_MOTION_DETECT_WINDOW();
        }
    }
}

