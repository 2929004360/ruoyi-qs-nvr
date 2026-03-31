package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_UpgraderInstall接口的 EM_UPGRADE_INSTALL_APPEND_DATA命令出参
*/
public class NET_OUT_INSTALL_APPEND_DATA extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;

    public NET_OUT_INSTALL_APPEND_DATA() {
        this.dwSize = this.size();
    }
}

