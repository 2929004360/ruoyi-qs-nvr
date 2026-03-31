package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_AttachRecordManagerState 入参
*/
public class NET_IN_RECORDMANAGER_ATTACH_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 回调函数,参见回调函数定义 {@link NetSDKLib.fRecordManagerStateCallBack}
    */
    public NetSDKLib.fRecordManagerStateCallBack cbNotify;
    /**
     * 用户信息
    */
    public Pointer          dwUser;

    public NET_IN_RECORDMANAGER_ATTACH_INFO() {
        this.dwSize = this.size();
    }
}

