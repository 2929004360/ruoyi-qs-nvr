package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_QueryMediaFileClose 接口输入参数
*/
public class NET_IN_QUERY_MEDIA_FILE_CLOSE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 查询ID号
    */
    public int              nFindID;

    public NET_IN_QUERY_MEDIA_FILE_CLOSE_INFO() {
        this.dwSize = this.size();
    }
}

