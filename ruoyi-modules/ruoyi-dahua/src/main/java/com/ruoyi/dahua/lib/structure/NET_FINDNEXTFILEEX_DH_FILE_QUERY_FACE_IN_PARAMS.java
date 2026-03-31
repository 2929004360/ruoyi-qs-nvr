package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 枚举EM_FINDNEXTFILEEX_DH_FILE_QUERY_FACE 对应的入参结构体
*/
public class NET_FINDNEXTFILEEX_DH_FILE_QUERY_FACE_IN_PARAMS extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 查找句柄
    */
    public NetSDKLib.LLong  lFindHandle;
    /**
     * 查找数量
    */
    public int              nFilecount;

    public NET_FINDNEXTFILEEX_DH_FILE_QUERY_FACE_IN_PARAMS() {
        this.dwSize = this.size();
    }
}

