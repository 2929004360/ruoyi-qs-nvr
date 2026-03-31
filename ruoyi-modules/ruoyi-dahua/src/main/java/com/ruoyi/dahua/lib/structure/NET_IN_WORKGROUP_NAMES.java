package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_QueryDevInfo , NET_QUERY_WORKGROUP_NAMES 命令输入参数
*/
public class NET_IN_WORKGROUP_NAMES extends NetSDKLib.SdkStructure
{
    public int              dwSize;

    public NET_IN_WORKGROUP_NAMES() {
        this.dwSize = this.size();
    }
}

