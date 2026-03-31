package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_ResetPicMap 接口输入参数
*/
public class NET_IN_RESET_PIC_MAP extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;

    public NET_IN_RESET_PIC_MAP() {
        this.dwSize = this.size();
    }
}

