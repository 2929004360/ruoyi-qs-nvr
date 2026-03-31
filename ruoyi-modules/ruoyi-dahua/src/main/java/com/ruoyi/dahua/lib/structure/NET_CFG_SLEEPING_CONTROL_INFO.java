package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 休眠策略配置, 对应枚举 NET_EM_CFG_SLEEPING_CONTROL
*/
public class NET_CFG_SLEEPING_CONTROL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小, 必须赋值
    */
    public int              dwSize;
    /**
     * 是否生效TRUE-生效，FALSE-不生效
    */
    public int              bEnable;
    /**
     * 低功耗模式,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_SLEEPING_CONTRONL_MODE}
    */
    public int              emMode;
    /**
     * 云台联动动作打断归位时间 单位秒
    */
    public int              nAutoHomingTime;
    /**
     * 自动控制信息, Mode为AutoCtrl时有效，根据设备电压判断是否休眠,参见结构体定义 {@link NET_SLEEPING_CONTROL_AUTO_CTRL}
    */
    public NET_SLEEPING_CONTROL_AUTO_CTRL stuAutoCtrl = new NET_SLEEPING_CONTROL_AUTO_CTRL();
    /**
     * 低功耗设备定时唤醒功能,参见结构体定义 {@link NET_SLEEPING_CONTROL_TIME_WAKEUP}
    */
    public NET_SLEEPING_CONTROL_TIME_WAKEUP stuTimingWakeUp = new NET_SLEEPING_CONTROL_TIME_WAKEUP();

    public NET_CFG_SLEEPING_CONTROL_INFO() {
        this.dwSize = this.size();
    }
}

