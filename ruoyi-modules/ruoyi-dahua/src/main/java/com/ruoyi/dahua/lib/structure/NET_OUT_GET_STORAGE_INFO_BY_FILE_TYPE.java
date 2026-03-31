package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_GetStorageInfoByFileType 接口输出参数
*/
public class NET_OUT_GET_STORAGE_INFO_BY_FILE_TYPE extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 工作目录信息最大个数
    */
    public int              nMaxInfoNum;
    /**
     * 工作目录信息, 内存由用户申请，大小为nMaxInfoNum * sizeof(NET_STORAGE_INFO_BY_FILE_TYPE),参见结构体定义 {@link NET_STORAGE_INFO_BY_FILE_TYPE}
    */
    public Pointer          pstuInfo;
    /**
     * 实际返回工作目录信息个数
    */
    public int              nRetInfoNum;

    public NET_OUT_GET_STORAGE_INFO_BY_FILE_TYPE() {
        this.dwSize = this.size();
    }
}

