package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_FocusPTZContinuously 接口输入参数
*/
public class NET_IN_FOCUS_PTZ_CONTINUOUSLY_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 云台通道号,创建实例
    */
    public int              nChannel;
    /**
     * 聚焦值,归一化到-1~1，负数表示减,正数表示加
    */
    public float            fSpeed;
    /**
     * 持续聚焦超时时间,单位毫秒
    */
    public int              nTimeout;

    public NET_IN_FOCUS_PTZ_CONTINUOUSLY_INFO() {
        this.dwSize = this.size();
    }
}

