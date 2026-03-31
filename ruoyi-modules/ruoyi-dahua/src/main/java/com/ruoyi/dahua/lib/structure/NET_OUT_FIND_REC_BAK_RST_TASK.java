package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @version 1.0
 * @description CLIENT_FindRecordBackupRestoreTaskInfos接口输出参数
 * @date 2022/01/14
 */
public class NET_OUT_FIND_REC_BAK_RST_TASK extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     *  任务数组,由用户申请内存，大小为sizeof{@link NetSDKLib.NET_REC_BAK_RST_TASK}*nMaxCount
     */
    public Pointer          pTasks;
    /**
     *  数组大小
     */
    public int              nMaxCount;
    /**
     *  返回的任务数量
     */
    public int              nRetCount;

    public NET_OUT_FIND_REC_BAK_RST_TASK(){
    	
        this.dwSize = this.size();
    }
}

