package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_FindSeekNextRecord 接口输出参数
*/
public class NET_OUT_FIND_SEEK_NEXT_RECORD_PARAM extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 记录列表,用户分配内存,根据NET_IN_FIND_RECORD_PARAM中的查询类型EM_NET_RECORD_TYPE，确定对应结构体，进入确定内存大小
    */
    public Pointer          pRecordList;
    /**
     * 最大查询列表记录数
    */
    public int              nMaxRecordNum;
    /**
     * 查询到的记录条数,当查询到的条数小于想查询的条数时,查询结束
    */
    public int              nRetRecordNum;

    public NET_OUT_FIND_SEEK_NEXT_RECORD_PARAM() {
        this.dwSize = this.size();
    }
}

