package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_SecurityImportDataEx接口入参
*/
public class NET_IN_SECURITY_IMPORT_DATA_INFO extends NetSDKLib.SdkStructure
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
     * 是否覆盖
    */
    public int              bIsOverWrite;
    /**
     * 设备导入文件状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_STATUS_TYPE}
    */
    public int              emStatus;
    /**
     * 下发二进制文件数据
    */
    public Pointer          pBufData;
    /**
     * 数据长度,单位：字节
    */
    public int              nLength;
    /**
     * 通道号
    */
    public int              nChannel;
    /**
     * 是否下发通道号
    */
    public int              bChannel;

    public NET_IN_SECURITY_IMPORT_DATA_INFO() {
        this.dwSize = this.size();
    }
}

