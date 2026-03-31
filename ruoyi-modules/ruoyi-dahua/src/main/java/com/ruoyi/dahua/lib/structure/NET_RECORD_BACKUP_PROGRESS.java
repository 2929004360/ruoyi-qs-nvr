package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 录像备份进度
 * @date 2021/11/07
 */
public class NET_RECORD_BACKUP_PROGRESS extends NetSDKLib.SdkStructure {
	 /**
     *  当前备份文件名
     */
    public byte[]           szFilePath = new byte[256];
    /**
     *  最后传递成功的数据包类型,参考ENUM_RECORDBACKUP_FILE_PROGRESS_TYPE
     */
    public int              emType;
    /**
     *  最后传递成功的数据包位置
     */
    public int              nPosition;
    /**
     *  任务进度百分比,0-100
     */
    public int              nPercent;
    /**
     *  任务预计剩余时长,单位秒。-1 – 未知； 0 – 完成；
     */
    public int              nRemainingTime;
    /**
     *  保留字节
     */
    public byte[]           szReserved = new byte[512];
}

