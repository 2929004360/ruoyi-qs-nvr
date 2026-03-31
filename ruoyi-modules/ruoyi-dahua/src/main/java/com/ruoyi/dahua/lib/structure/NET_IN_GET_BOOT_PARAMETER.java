package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetBootParameter 入参
*/
public class NET_IN_GET_BOOT_PARAMETER extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 参数个数
    */
    public int              nNameNum;
    /**
     * 具体参数名称列表
    */
    public BYTE_ARRAY_32[]  szNames = new BYTE_ARRAY_32[32];

    public NET_IN_GET_BOOT_PARAMETER() {
        this.dwSize = this.size();
    }
}

