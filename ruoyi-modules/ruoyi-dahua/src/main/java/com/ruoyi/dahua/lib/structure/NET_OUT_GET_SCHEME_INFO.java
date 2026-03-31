package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetSchemeInfo 出参
*/
public class NET_OUT_GET_SCHEME_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 查找到的智能套餐方案信息数量
    */
    public int              nSchemeInfoNum;
    /**
     * 查找到的智能套餐方案信息,参见结构体定义 {@link NET_SCHEME_INFO}
    */
    public NET_SCHEME_INFO[] stuSchemeInfo = new NET_SCHEME_INFO[32];

    public NET_OUT_GET_SCHEME_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuSchemeInfo.length; i++){
            stuSchemeInfo[i] = new NET_SCHEME_INFO();
        }
    }
}

