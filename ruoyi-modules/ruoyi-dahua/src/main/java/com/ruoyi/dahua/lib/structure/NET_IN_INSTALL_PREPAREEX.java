package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_UpgraderInstall接口的 EM_UPGRADE_INSTALL_PREPAREEX命令入参
*/
public class NET_IN_INSTALL_PREPAREEX extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 升级的app名称
    */
    public byte[]           szAppName = new byte[32];
    /**
     * 接下来的操作,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_NET_NEXT_OPERATE}
    */
    public int              emNextOperate;
    /**
     * 升级包大小
    */
    public int              nTotalLength;
    /**
     * 升级可靠性
    */
    public int              bReliable;

    public NET_IN_INSTALL_PREPAREEX() {
        this.dwSize = this.size();
    }
}

