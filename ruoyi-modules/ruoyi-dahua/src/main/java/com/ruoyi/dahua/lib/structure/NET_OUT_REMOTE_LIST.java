package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_RemoteList 接口输出参数
*/
public class NET_OUT_REMOTE_LIST extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 文件信息,参见结构体定义 {@link NET_REMOTE_FILE_INFO}
    */
    public Pointer          pstuFileInfo;
    /**
     * 文件信息数组大小, 用户填写
    */
    public int              nMaxFileCount;
    /**
     * 返回的文件数量
    */
    public int              nRetFileCount;

    public NET_OUT_REMOTE_LIST() {
        this.dwSize = this.size();
    }
}

