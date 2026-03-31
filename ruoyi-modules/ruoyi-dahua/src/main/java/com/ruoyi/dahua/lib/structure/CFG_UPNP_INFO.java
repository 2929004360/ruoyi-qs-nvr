package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * UPNP配置，对应配置项目CFG_CMD_UPNP
*/
public class CFG_UPNP_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 端口映射使能
    */
    public int              bEnable;
    /**
     * 是否开启UPnP被查找功能
    */
    public int              bStartDeviceDiscover;
    /**
     * 模式,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_CONFIGURATION_MODE}
    */
    public int              emMode;
    /**
     * 最大映射表个数
    */
    public int              nMaxTable;
    /**
     * 实际映射表个数
    */
    public int              nReturnTable;
    /**
     * 映射表信息,参见结构体定义 {@link CFG_MAP_TABLE_INFO}
    */
    public Pointer          pstuMapTable;

    public CFG_UPNP_INFO() {
    }
}

