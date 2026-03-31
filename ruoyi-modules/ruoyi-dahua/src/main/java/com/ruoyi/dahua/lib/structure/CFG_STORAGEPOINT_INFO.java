package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 存储点配置信息
 * @origin autoTool
 * @date 2023/10/30 15:54:18
 */
public class CFG_STORAGEPOINT_INFO extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 存储点类型 {@link com.ruoyi.dahua.lib.enumeration.EM_STORAGEPOINT_TYPE}
     */
    public int              emStoragePointType;
    /**
     * 本地工作目录组名称, 空表示不录到本地
     */
    public byte             nLocalDir;
    /**
     * 实时压缩存储目录组，空表示不使用实时压缩存储。
     */
    public byte[]           szCompressDir = new byte[256];
    /**
     * 冗余工作目录组名称,空表示没有冗余录像。
     */
    public byte[]           szRedundantDir = new byte[256];
    /**
     * 远程工作目录组名称,空表示不录到远程。
     */
    public byte[]           szRemoteDir = new byte[256];
    /**
     * 远程存储网络故障恢复后，是否自动将本地存储的数据同步到远程存储。
     */
    public int              bAutoSync;
    /**
     * 从网络恢复的时刻开始，需要往前同步的数据时间范围，小时为单位，0表示同步所有数据。
     */
    public int              nAutoSyncRange;
    /**
     * 远程目录无法访问时，是否保存到本地目录。
     */
    public int              bLocalEmergency;
    /**
     * 设置将多少天之前的录像文件进行压缩。
     */
    public int              nCompressBefore;

    public CFG_STORAGEPOINT_INFO() {
        this.dwSize = this.size();
    }
}

