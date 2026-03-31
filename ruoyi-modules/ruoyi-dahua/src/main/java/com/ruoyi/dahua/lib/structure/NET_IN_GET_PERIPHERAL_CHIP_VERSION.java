package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetPeripheralChipVersion 接口输入参数
*/
public class NET_IN_GET_PERIPHERAL_CHIP_VERSION extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 从片类型, 0:未知, 1:MOVIDIUS, 2:Warpway, 3:TX1, 4:Proto, 5:Blob
    */
    public int              nType;

    public NET_IN_GET_PERIPHERAL_CHIP_VERSION() {
        this.dwSize = this.size();
    }
}

