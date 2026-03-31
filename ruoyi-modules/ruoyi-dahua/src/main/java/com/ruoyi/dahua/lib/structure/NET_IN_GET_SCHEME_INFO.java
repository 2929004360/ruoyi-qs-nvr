package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetSchemeInfo 入参
*/
public class NET_IN_GET_SCHEME_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 智能套餐方案ID, -1时表示查找全部智能套餐方案信息
    */
    public int              nSchemeID;

    public NET_IN_GET_SCHEME_INFO() {
        this.dwSize = this.size();
    }
}

