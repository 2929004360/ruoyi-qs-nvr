package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 安装的应用信息
*/
public class NET_INSTALLED_APP_INFO extends NetSDKLib.SdkStructure
{
    /**
     * APP的名称
    */
    public byte[]           szAppName = new byte[128];
    /**
     * APP的版本
    */
    public byte[]           szVersion = new byte[64];
    /**
     * 扩展信息
    */
    public byte[]           szExtend = new byte[64];
    /**
     * APP的ID
    */
    public int              nAppID;
    /**
     * APP的调试状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_APP_DEBUG_STATE}
    */
    public int              emAppDebugState;
    /**
     * APP的运行状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_APP_RUNNING_STATE}
    */
    public int              emAppRunningState;
    /**
     * APP的license状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_APP_LICENSE_STATE}
    */
    public int              emAppLicenseState;
    /**
     * APP的能力信息,参见结构体定义 {@link NET_APP_CAPS}
    */
    public NET_APP_CAPS     stuAppCaps = new NET_APP_CAPS();
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[256];

    public NET_INSTALLED_APP_INFO() {
    }
}

