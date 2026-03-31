package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;
/**
 * 
 * @author 119178
 * CLIENT_AttachBackupTaskState接口输入参数
 * {@link NetSDKLib#CLIENT_AttachBackupTaskState}
 */
public class NET_OUT_ATTACH_BACKUP_STATE extends SdkStructure {
    public int              dwSize;

	public NET_OUT_ATTACH_BACKUP_STATE(){
        this.dwSize = this.size();
    }
}

