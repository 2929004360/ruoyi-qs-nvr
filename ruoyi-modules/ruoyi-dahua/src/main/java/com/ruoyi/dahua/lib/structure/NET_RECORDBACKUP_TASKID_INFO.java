package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 任务ID信息
 *
 * @author 47040
 * @since Created at 2021/5/27 20:37
 */
public class NET_RECORDBACKUP_TASKID_INFO extends NetSDKLib.SdkStructure {
    /**
     * 通道任务ID
     */
    public int[]            nTaskIDs = new int[512];
    /**
     * 通道任务ID有效个数
     */
    public int              nTaskIDCount;
    /**
     * 保留字段
     */
    public byte[]           byReserved = new byte[1024];
}

