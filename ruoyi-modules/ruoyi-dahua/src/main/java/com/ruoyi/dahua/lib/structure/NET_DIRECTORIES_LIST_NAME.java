package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 工作组包含的工作目录名称
*/
public class NET_DIRECTORIES_LIST_NAME extends NetSDKLib.SdkStructure
{
    /**
     * 名称
    */
    public byte[]           szName = new byte[256];

    public NET_DIRECTORIES_LIST_NAME() {
    }
}

