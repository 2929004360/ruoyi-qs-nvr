package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetRtscRunningInfo 接口输入参数
 * @date 2021/09/28
 */
public class NET_IN_GET_RUNNING_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  查询类型，按位表示： bit0:运行状态 bit1:控制方式 bit2:车道功能状态 bit3:车道/匝道控制状态信息 bit4:当前信号方案色步信息 bit5: 下一个周期信号方案色步信息
     */
    public int              nType;

    public NET_IN_GET_RUNNING_INFO(){
        this.dwSize = this.size();
    }
}

