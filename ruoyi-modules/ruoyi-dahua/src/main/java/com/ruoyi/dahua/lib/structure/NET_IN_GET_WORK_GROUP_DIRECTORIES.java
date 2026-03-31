package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetWorkGroupDirectories 接口输入参数
*/
public class NET_IN_GET_WORK_GROUP_DIRECTORIES extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 工作组名称
    */
    public byte[]           szName = new byte[32];

    public NET_IN_GET_WORK_GROUP_DIRECTORIES() {
        this.dwSize = this.size();
    }
}

