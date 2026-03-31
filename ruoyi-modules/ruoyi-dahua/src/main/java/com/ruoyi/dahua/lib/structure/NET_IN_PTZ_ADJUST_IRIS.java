package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_PTZAdjustIris 接口输入参数
*/
public class NET_IN_PTZ_ADJUST_IRIS extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 通道号, 范围 0~设备通道数
    */
    public int              nChannel;
    /**
     * 相对变化, 归一化到-1~1
    */
    public float            fChange;

    public NET_IN_PTZ_ADJUST_IRIS() {
        this.dwSize = this.size();
    }
}

