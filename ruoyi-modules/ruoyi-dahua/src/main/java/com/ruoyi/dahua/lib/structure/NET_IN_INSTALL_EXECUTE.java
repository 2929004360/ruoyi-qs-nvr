package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_UpgraderInstall接口的 EM_UPGRADE_INSTALL_EXECUTE命令入参
*/
public class NET_IN_INSTALL_EXECUTE extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 升级后是否自动重启
    */
    public int              bAutoReboot;

    public NET_IN_INSTALL_EXECUTE() {
        this.dwSize = this.size();
    }
}

