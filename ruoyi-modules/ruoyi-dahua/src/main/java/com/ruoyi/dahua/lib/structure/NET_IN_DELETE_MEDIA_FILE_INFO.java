package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_DeleteMediaFile 接口输入参数
*/
public class NET_IN_DELETE_MEDIA_FILE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 文件Id号个数
    */
    public int              nIdNum;
    /**
     * 文件Id号，是一个数组，表示要删除的文件Id号
    */
    public int[]            nId = new int[32];

    public NET_IN_DELETE_MEDIA_FILE_INFO() {
        this.dwSize = this.size();
    }
}

