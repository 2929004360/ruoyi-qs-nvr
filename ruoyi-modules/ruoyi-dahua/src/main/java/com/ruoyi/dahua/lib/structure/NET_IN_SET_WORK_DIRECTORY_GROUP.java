package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_SetWorkDirectoryGoup 接口输入参数
*/
public class NET_IN_SET_WORK_DIRECTORY_GROUP extends NetSDKLib.SdkStructure
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
     * 目录所属组
    */
    public byte[]           szGroup = new byte[32];

    public NET_IN_SET_WORK_DIRECTORY_GROUP() {
        this.dwSize = this.size();
    }
}

