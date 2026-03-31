package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * 远程录像任务接口 出参
 * 接口 {@link NetSDKLib#CLIENT_AddRecordBackupRestoreRemoteTask}
 * 入参 {@link NET_IN_ADD_REC_BAK_RST_REMOTE_TASK}
 *
 * @author 47040
 * @since Created at 2021/5/27 20:33
 */
public class NET_OUT_ADD_REC_BAK_RST_REMOTE_TASK extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 任务ID信息 -> {@link NET_RECORDBACKUP_TASKID_INFO} 数组
     * 数组长度为入参参数 {@link NET_IN_ADD_REC_BAK_RST_REMOTE_TASK#nTaskCount}
     * 由用户申请内存 大小为 (size of NET_RECORDBACKUP_TASKID_INFO)*nTaskCount
     */
    public Pointer          pStuID;

    public NET_OUT_ADD_REC_BAK_RST_REMOTE_TASK() {
        dwSize = this.size();
    }
}

