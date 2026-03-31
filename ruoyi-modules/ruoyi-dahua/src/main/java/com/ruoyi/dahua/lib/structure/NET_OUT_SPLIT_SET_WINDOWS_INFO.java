package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CIENT_SetSplitWindowsInfo接口输出参数
*/
public class NET_OUT_SPLIT_SET_WINDOWS_INFO extends NetSDKLib.SdkStructure
{
    public int              dwSize;

    public NET_OUT_SPLIT_SET_WINDOWS_INFO() {
        this.dwSize = this.size();
    }
}

