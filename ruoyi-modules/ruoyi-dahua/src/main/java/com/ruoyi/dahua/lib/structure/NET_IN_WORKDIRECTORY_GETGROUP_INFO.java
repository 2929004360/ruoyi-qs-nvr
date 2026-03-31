package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 获取工作目录实例 入参
*/
public class NET_IN_WORKDIRECTORY_GETGROUP_INFO extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 磁盘目录名称
    */
    public byte[]           szDirectoryName = new byte[256];

    public NET_IN_WORKDIRECTORY_GETGROUP_INFO() {
        this.dwSize = this.size();
    }
}

