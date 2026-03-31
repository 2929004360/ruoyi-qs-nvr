package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * 远程录像任务接口 入参
 * 接口 {@link NetSDKLib#CLIENT_AddRecordBackupRestoreRemoteTask}
 * 出参 {@link NET_OUT_ADD_REC_BAK_RST_REMOTE_TASK}
 *
 * @author 47040
 * @since Created at 2021/5/27 20:18
 */
public class NET_IN_ADD_REC_BAK_RST_REMOTE_TASK extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 任务有效个数, 最大支持 16个
     */
    public int              nTaskCount;
    /**
     * 任务信息 -> {@link NET_RECORDBACKUP_REMOTE_TASK} 数组
     * 数组长度为 nTaskCount 由用户申请内存
     * 大小为 ( size of NET_RECORDBACKUP_REMOTE_TASK )*nTaskCount
     */
    public Pointer          pStuTask;

    public NET_IN_ADD_REC_BAK_RST_REMOTE_TASK() {
        dwSize = this.size();
    }
}

