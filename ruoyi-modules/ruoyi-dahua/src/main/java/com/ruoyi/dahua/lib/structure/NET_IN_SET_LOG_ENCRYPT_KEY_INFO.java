package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_ExportEncrypedLog 接口入参
*/
public class NET_IN_SET_LOG_ENCRYPT_KEY_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 加密密码，不超过32位，必须是可见的ASCII字符，不支持是中文。该密码作为zip压缩包密码，与设备无关
    */
    public byte[]           szPassword = new byte[33];
    /**
     * 字节对齐
    */
    public byte[]           szReserved1 = new byte[3];

    public NET_IN_SET_LOG_ENCRYPT_KEY_INFO() {
        this.dwSize = this.size();
    }
}

