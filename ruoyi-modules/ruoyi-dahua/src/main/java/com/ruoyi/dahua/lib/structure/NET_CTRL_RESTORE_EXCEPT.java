package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 恢复相机默认设置输入参数
*/
public class NET_CTRL_RESTORE_EXCEPT extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 配置名称；如果有多个配置，则用&&分割；配置名参见dhconfigsdk.h的CLIENT_GetNewDevConfig和CLIENT_SetNewDevConfig接口的配置命令
    */
    public byte[]           szCfgName = new byte[128];

    public NET_CTRL_RESTORE_EXCEPT() {
        this.dwSize = this.size();
    }
}

