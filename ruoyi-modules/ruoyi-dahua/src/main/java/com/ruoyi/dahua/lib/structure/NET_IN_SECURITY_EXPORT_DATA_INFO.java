package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_SecurityExportDataEx接口入参
*/
public class NET_IN_SECURITY_EXPORT_DATA_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 公钥加密后再经过Base64编码的密码串
    */
    public byte[]           szKey = new byte[1024];
    /**
     * 导出数据类型, 其文件的类型取值即此文件的掩码位数, Bit3通讯录, Bit4密码信息, Bit5 卡片信息, Bit6 目标信息, Bit7信息信息, Bit8 对讲IPC信息, Bit13 预置点信息
    */
    public int              nfileTypeMask;
    /**
     * 导出偏移
    */
    public int              nOffset;
    /**
     * 需要导出的字节长度
    */
    public int              nNeedLength;
    /**
     * 通道号
    */
    public int              nChannel;
    /**
     * 是否下发通道号
    */
    public int              bChannel;

    public NET_IN_SECURITY_EXPORT_DATA_INFO() {
        this.dwSize = this.size();
    }
}

