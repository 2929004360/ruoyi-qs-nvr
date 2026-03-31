package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 设备类型信息
*/
public class CFG_DEVICE_CLASS_INFO extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 设备类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_DEVICE_TYPE}
    */
    public int              emDeviceType;

    public CFG_DEVICE_CLASS_INFO() {
        this.dwSize = this.size();
    }
}

