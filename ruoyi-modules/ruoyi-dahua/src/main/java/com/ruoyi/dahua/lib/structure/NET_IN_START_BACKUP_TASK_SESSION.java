package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;
/**
 * 
 * @author 119178
 * CLIENT_StartBackupTaskSession 接口输入参数
 * {@link NetSDKLib#CLIENT_StartBackupTaskSession}
 */
public class NET_IN_START_BACKUP_TASK_SESSION extends SdkStructure {
    public int              dwSize;
    public int              nGroupID;                             // 任务组ID，0表示不属于任何任务组

	public NET_IN_START_BACKUP_TASK_SESSION(){
        this.dwSize = this.size();
    }	
}

