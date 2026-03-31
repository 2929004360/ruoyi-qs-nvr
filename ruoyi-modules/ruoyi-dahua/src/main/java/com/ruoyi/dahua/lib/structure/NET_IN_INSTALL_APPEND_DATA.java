package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_UpgraderInstall接口的 EM_UPGRADE_INSTALL_APPEND_DATA命令入参
*/
public class NET_IN_INSTALL_APPEND_DATA extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 升级文件总大小
    */
    public int              nTotalLen;
    /**
     * 本次发送的二进制长度
    */
    public int              nAppendLen;
    /**
     * 本次发送的二进制数据 由用户申请释放
    */
    public Pointer          pAppendData;

    public NET_IN_INSTALL_APPEND_DATA() {
        this.dwSize = this.size();
    }
}

