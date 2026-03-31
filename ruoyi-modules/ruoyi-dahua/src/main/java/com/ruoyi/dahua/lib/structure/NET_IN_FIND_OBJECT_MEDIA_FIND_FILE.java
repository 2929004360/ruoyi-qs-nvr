package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_FindObjectMediaFindFile 接口输入参数
*/
public class NET_IN_FIND_OBJECT_MEDIA_FIND_FILE extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 查询的偏移条数
    */
    public int              nOffset;
    /**
     * 本次查询需要获取的条数
    */
    public int              nCount;

    public NET_IN_FIND_OBJECT_MEDIA_FIND_FILE() {
        this.dwSize = this.size();
    }
}

