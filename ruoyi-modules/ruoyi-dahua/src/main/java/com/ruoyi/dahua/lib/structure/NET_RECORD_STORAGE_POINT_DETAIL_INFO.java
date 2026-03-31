package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 录像/图片存储点配置详细信息
*/
public class NET_RECORD_STORAGE_POINT_DETAIL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 本地工作目录组序号, -1表示未知
    */
    public int              nLocal;
    /**
     * 是否存储到FTP
    */
    public int              bFTP;
    /**
     * 实时压缩存储目录组
    */
    public byte[]           szRealtimeCompress = new byte[64];
    /**
     * 冗余工作目录组名称
    */
    public byte[]           szRedundant = new byte[64];
    /**
     * 远程工作目录组名称
    */
    public byte[]           szRemote = new byte[64];
    /**
     * 远程存储网络故障恢复后，是否自动将本地存储的数据同步到远程存储
    */
    public int              bAutoSync;
    /**
     * 从网络恢复的时刻开始，需要往前同步的数据时间范围，单位: 小时, -1:未知 0:同步所有数据
    */
    public int              nAutoSyncRange;
    /**
     * 只有在远程目录无法访问时，才保存到本地目录，平时不录到本地
    */
    public int              bLocalForEmergency;
    /**
     * 设置将多少天之前的录像文件进行压缩, 单位: 天, -1:未知 0:不压缩
    */
    public int              nCompressBefore;
    /**
     * 定制存储
    */
    public int              bCustom;
    /**
     * 断网续传上传顺序, bLocalForEmergency为TRUE时有效,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_STORAGE_POINT_ORDER_TYPE}
    */
    public int              emOrder;
    /**
     * 是否存储到USB设备
    */
    public int              bUSB;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_RECORD_STORAGE_POINT_DETAIL_INFO() {
    }
}

