package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_DevStorageFormatPartitionAsyn 接口输入参数
*/
public class NET_IN_DEVSTORAGE_FORMAT_PARTITION_ASYN extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 字节对齐
    */
    public byte[]           byReserved = new byte[4];
    /**
     * 分区名称, 填"" 表示对整块硬盘分区
    */
    public byte[]           szPartName = new byte[32];
    /**
     * 分区格式信息,参见结构体定义 {@link NET_PART_FORMAT_INFO}
    */
    public NET_PART_FORMAT_INFO stuPartFormatInfo = new NET_PART_FORMAT_INFO();

    public NET_IN_DEVSTORAGE_FORMAT_PARTITION_ASYN() {
        this.dwSize = this.size();
    }
}

