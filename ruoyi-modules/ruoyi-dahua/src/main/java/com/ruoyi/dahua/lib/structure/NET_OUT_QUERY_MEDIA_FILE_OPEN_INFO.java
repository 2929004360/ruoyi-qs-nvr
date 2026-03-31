package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_QueryMediaFileOpen 接口输出参数
*/
public class NET_OUT_QUERY_MEDIA_FILE_OPEN_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 查询到的总个数
    */
    public int              nTotalNum;
    /**
     * 查询ID号
    */
    public int              nFindID;

    public NET_OUT_QUERY_MEDIA_FILE_OPEN_INFO() {
        this.dwSize = this.size();
    }
}

