package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 251823
 * @version 1.0
 * @description CLIENT_FindRecordBackupRestoreTaskInfos接口输入参数
 * @date 2022/01/14
 */
public class NET_IN_FIND_REC_BAK_RST_TASK extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     *  根据条件查询备份任务的查询方式，{ @link EM_RECORD_BACKUP_FIND_TYPE}
     */
    public int              emFindType;
    /**
     *  任务ID,emFindType为 EM_RECORD_BACKUP_FIND_TYPE_BY_TASKID 时有效,否则无效
     */
    public int              dwTaskID;
    /**
     *  设备端本地通道号,emFindType为 EM_RECORD_BACKUP_FIND_TYPE_BY_CHN_AND_TIME 时有效,否则无效
     */
    public int              nLocalChannelID;
    /**
     *  备份录像的开始时间,emFindType为 EM_RECORD_BACKUP_FIND_TYPE_BY_CHN_AND_TIME 时有效,否则无效
     */
    public NetSDKLib.NET_TIME stuStartTime;
    /**
     *  备份录像的结束时间,emFindType为 EM_RECORD_BACKUP_FIND_TYPE_BY_CHN_AND_TIME 时有效,否则无效
     */
    public NetSDKLib.NET_TIME stuEndTime;
    /**
     *主动注册的设备ID, emFindType为EM_RECORD_BACKUP_FIND_TYPE_BY_DEVICE_ID时必选有效，否则无效
     */
    public			byte[]         szDeviceID = new byte[128];

    public NET_IN_FIND_REC_BAK_RST_TASK(){
        this.dwSize = this.size();
    }
}

