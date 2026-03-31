package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_SetRtscRunningMode 接口输入参数
 * @date 2021/09/28
 */
public class NET_IN_SET_RUNNING_MODE extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  平台下发运行模式：
     *   0x01 //定时模式 
     *   0xfb //关灯模式 
     *   0xfc //全红模式
     *   0xfd //区域自适应模式 
     *   0xfe //感应模式	   
     *   0xff //黄闪模式	
     *   0xf6 //实时控制模式	
     *   0xf2 //单点自适应模式	
     *   0xf8 //红闪模式	
     *   0xf9 //绿闪模式
     */
    public int              nMode;

    public NET_IN_SET_RUNNING_MODE(){
        this.dwSize = this.size();
    }
}

