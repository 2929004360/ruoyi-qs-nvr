package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_SetRtscRunningMode 接口输出参数
 * @date 2021/09/28
 */
public class NET_OUT_SET_RUNNING_MODE extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小
     */
    public int              dwSize;

    public NET_OUT_SET_RUNNING_MODE(){
        this.dwSize = this.size();
    }
}

