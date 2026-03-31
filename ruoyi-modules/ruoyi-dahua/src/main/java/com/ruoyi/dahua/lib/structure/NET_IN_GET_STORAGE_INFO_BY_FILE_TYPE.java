package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetStorageInfoByFileType 接口输入参数
*/
public class NET_IN_GET_STORAGE_INFO_BY_FILE_TYPE extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 工作目录名称, 用于获取工作目录实例
    */
    public byte[]           szName = new byte[256];
    /**
     * 接口子类型, 用于获取工作目录实例
    */
    public byte[]           szSubClassID = new byte[32];
    /**
     * 根据类型获取各个存储类型信息, "Image": 表示图片区, "Video": 表示录像区
    */
    public byte[]           szFileType = new byte[32];

    public NET_IN_GET_STORAGE_INFO_BY_FILE_TYPE() {
        this.dwSize = this.size();
    }
}

