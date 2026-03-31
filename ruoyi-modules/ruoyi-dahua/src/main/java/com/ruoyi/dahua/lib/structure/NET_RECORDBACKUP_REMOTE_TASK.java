package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.ENUM_RECORDBACKUP_FILE_TYPE;

/**
 * 远程录像备份
 *
 * @author 47040
 * @since Created at 2021/5/27 20:24
 */
public class NET_RECORDBACKUP_REMOTE_TASK extends NetSDKLib.SdkStructure {
    /**
     * 通道有效个数
     */
    public int              nChannelCount;
    /**
     * 通道号数组
     */
    public int[]            nChannels = new int[512];
    /**
     * 远程通道号数组
     */
    public int[]            nRemoteChannels = new int[512];
    /**
     * IP地址
     */
    public byte[]           szDeviceIP = new byte[64];
    /**
     * 端口
     */
    public int              nPort;
    /**
     * 用户名
     */
    public byte[]           szUserName = new byte[64];
    /**
     * 密码
     */
    public byte[]           szPassword = new byte[64];
    /**
     * 开始时间
     */
    public NET_TIME         stuStartTime;
    /**
     * 结束时间
     */
    public NET_TIME         stuEndTime;
    /**
     * 录像类型 枚举值 {@link ENUM_RECORDBACKUP_FILE_TYPE}
     */
    public int              emFileType;
    /**
     * 视频码流 枚举值 {@link NetSDKLib.NET_STREAM_TYPE}
     */
    public int              emStreamType;
    /**
     * 保留字段
     */
    public byte[]           byReserved = new byte[512];
}

