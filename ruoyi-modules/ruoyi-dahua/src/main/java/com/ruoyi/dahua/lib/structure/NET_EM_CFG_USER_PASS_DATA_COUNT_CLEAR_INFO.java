package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 定期通行人数清除功能配置
 * @date 2022/5/27 9:51
 */
public class NET_EM_CFG_USER_PASS_DATA_COUNT_CLEAR_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 是否开启定期通行人数清除功能使能
     */
    public int              bEnable;
    /**
     * 定期人数清除周期 {@link com.ruoyi.dahua.lib.enumeration.EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD}
     */
    public int              emPeriod;

    public NET_EM_CFG_USER_PASS_DATA_COUNT_CLEAR_INFO(){
    this.dwSize=this.size();
    }
}

