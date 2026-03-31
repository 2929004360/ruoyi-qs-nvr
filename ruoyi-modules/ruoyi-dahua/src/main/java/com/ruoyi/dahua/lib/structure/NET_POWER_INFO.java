package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * className：NET_POWER_INFO
 * description：对应C++头文件DH_POWER_INFO
 * author：251589
 * createTime：2021/2/25 14:10
 *
 * @version v1.0
 */

public class NET_POWER_INFO extends NetSDKLib.SdkStructure {
    /**
     * dwSize;
     */
    public int              dwSize;
    /**
     *  电源状态, 0-关闭, 1-打开, 2-打开但有故障
     */
    public int              bPowerOn;
    /**
     *  电源电流状态
     */
    public int              emCurrentState;
    /**
     *  电源电压状态
     */
    public int              emVoltageState;

    public NET_POWER_INFO(){
        this.dwSize = this.size();
    }
}

