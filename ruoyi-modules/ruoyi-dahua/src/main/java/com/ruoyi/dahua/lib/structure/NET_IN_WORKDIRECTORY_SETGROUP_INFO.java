package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 设置工作目录组名 入参
*/
public class NET_IN_WORKDIRECTORY_SETGROUP_INFO extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 工作目录组名
    */
    public byte[]           szGroupName = new byte[256];
    /**
     * 磁盘目录名称
    */
    public byte[]           szDirectoryName = new byte[256];

    public NET_IN_WORKDIRECTORY_SETGROUP_INFO() {
        this.dwSize = this.size();
    }
}

