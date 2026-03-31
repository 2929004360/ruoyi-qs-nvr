package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * EAS设备能力集数据
*/
public class NET_EAS_DEVICE_CAPS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * EAS通道号，从0开始
    */
    public int              nDeviceChannel;
    /**
     * 是否支持待机灯光颜色设置
    */
    public int              bSupportLedColor;
    /**
     * 可设置的报警音频数
    */
    public int              nToneNum;
    /**
     * 报警音量最小值
    */
    public int              nVolumeMin;
    /**
     * 报警音量最大值
    */
    public int              nVolumeMax;
    /**
     * 保留字节
    */
    public byte[]           szResvered = new byte[116];

    public NET_EAS_DEVICE_CAPS_INFO() {
    }
}

