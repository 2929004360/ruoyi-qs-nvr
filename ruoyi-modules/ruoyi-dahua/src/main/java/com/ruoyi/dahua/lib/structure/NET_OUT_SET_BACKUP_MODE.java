package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_SetRtscBackupMode 接口输出参数
 * @date 2021/09/28
 */
public class NET_OUT_SET_BACKUP_MODE extends NetSDKLib.SdkStructure {
	 /**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  备份模式设置错误码。参考枚举{ @link EM_BACKUP_ERROR_CODE }
     */
    public int              emBackUpErrCode;

    public NET_OUT_SET_BACKUP_MODE(){
        this.dwSize = this.size();
    }
}

