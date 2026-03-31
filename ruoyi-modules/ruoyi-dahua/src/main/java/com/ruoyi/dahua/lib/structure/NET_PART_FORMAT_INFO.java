package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 分区格式信息
*/
public class NET_PART_FORMAT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 文件系统类型, 若取EM_FS_TYPE_UNKNWON 表示由系统层决定,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_FS_TYPE}
    */
    public int              emFsType;
    /**
     * 卷标
    */
    public byte[]           szVolumeLabel = new byte[64];
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[2044];

    public NET_PART_FORMAT_INFO() {
    }
}

