package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 临时用户账号信息
*/
public class NET_TEMP_USER_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 生成新的临时用户账号, 与Token绑定使用
    */
    public byte[]           szUsername = new byte[128];
    /**
     * 获取临时token, 长度为64
    */
    public byte[]           szToken = new byte[128];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_TEMP_USER_INFO() {
    }
}

