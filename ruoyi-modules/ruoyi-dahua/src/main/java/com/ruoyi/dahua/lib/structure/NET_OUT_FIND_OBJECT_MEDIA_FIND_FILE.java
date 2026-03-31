package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_FindObjectMediaFindFile 接口输出参数
*/
public class NET_OUT_FIND_OBJECT_MEDIA_FIND_FILE extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 查找到的录像片段总数
    */
    public int              nTotalCount;
    /**
     * 录像片段文件列表最大个数
    */
    public int              nMaxCount;
    /**
     * 录像片段文件列表实际返回个数
    */
    public int              nRetCount;
    /**
     * 录像片段文件列表, 内存由用户申请释放, 大小为sizeof(NET_OBJECT_MEDIA_FILE_INFOS)*nMaxCount,参见结构体定义 {@link NET_OBJECT_MEDIA_FILE_INFOS}
    */
    public Pointer          pstuFileInfos;

    public NET_OUT_FIND_OBJECT_MEDIA_FIND_FILE() {
        this.dwSize = this.size();
    }
}

