package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetCollectDevicesInfoByTsfId 接口输入参数
*/
public class NET_IN_GET_COLLECT_DEVICES_INFO_BY_TSFID_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 执法人id
    */
    public byte[]           szTipStaffId = new byte[32];

    public NET_IN_GET_COLLECT_DEVICES_INFO_BY_TSFID_INFO() {
        this.dwSize = this.size();
    }
}

