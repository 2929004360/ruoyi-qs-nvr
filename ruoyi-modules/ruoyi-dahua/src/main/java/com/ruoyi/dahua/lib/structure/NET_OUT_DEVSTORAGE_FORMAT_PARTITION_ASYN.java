package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_DevStorageFormatPartitionAsyn 接口输出参数
*/
public class NET_OUT_DEVSTORAGE_FORMAT_PARTITION_ASYN extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;

    public NET_OUT_DEVSTORAGE_FORMAT_PARTITION_ASYN() {
        this.dwSize = this.size();
    }
}

