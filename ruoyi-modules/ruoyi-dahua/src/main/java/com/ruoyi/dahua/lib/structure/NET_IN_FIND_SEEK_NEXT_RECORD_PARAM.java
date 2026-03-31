package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_FindSeekNextRecord 接口输入参数
*/
public class NET_IN_FIND_SEEK_NEXT_RECORD_PARAM extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 查询句柄
    */
    public NetSDKLib.LLong  lFindeHandle;
    /**
     * 每次查询的条数
    */
    public int              nCount;
    /**
     * 查询的偏移量
    */
    public int              nOffset;

    public NET_IN_FIND_SEEK_NEXT_RECORD_PARAM() {
        this.dwSize = this.size();
    }
}

