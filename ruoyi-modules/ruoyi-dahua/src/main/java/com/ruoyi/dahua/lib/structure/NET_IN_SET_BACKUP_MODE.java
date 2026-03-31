package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_SetRtscBackupMode 接口输入参数
 * @date 2021/09/28
 */
public class NET_IN_SET_BACKUP_MODE extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  红绿灯/可变车道方案：150-152代表红绿灯 153-155代表可变车道方案。参考枚举{ @link EM_CTRL_SCHEME }
     */
    public int              emCtrlScheme;

    public NET_IN_SET_BACKUP_MODE(){
        this.dwSize = this.size();
    }
}

