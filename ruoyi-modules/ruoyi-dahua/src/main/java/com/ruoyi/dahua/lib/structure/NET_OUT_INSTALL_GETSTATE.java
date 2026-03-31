package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_UpgraderInstall接口的 EM_UPGRADE_INSTALL_GETSTATE命令出参
*/
public class NET_OUT_INSTALL_GETSTATE extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 升级状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_NET_UPGRADE_STATUS}
    */
    public int              emUpgradeStatus;
    /**
     * 升级进度
    */
    public int              nProgress;
    /**
     * 升级文件
    */
    public byte[]           szFile = new byte[256];

    public NET_OUT_INSTALL_GETSTATE() {
        this.dwSize = this.size();
    }
}

