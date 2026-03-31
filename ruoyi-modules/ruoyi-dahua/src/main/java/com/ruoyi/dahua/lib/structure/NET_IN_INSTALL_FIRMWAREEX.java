package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_UpgraderInstall接口的 EM_UPGRADER_INSTALL_FIRMWAREEX命令入参
*/
public class NET_IN_INSTALL_FIRMWAREEX extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 支持的操作,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_NET_UPGRADE_ACTION}
    */
    public int              emUpgradeAction;
    /**
     * 升级包的url路径
    */
    public byte[]           szUrlString = new byte[1024];
    /**
     * 被操作的第三方APP的名称
    */
    public byte[]           szAppName = new byte[64];

    public NET_IN_INSTALL_FIRMWAREEX() {
        this.dwSize = this.size();
    }
}

