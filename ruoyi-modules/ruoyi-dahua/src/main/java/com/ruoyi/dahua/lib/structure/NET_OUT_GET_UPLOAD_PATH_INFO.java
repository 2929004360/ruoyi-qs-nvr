package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetUploadPath接口出参
*/
public class NET_OUT_GET_UPLOAD_PATH_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 实际用于上传的文件名称
    */
    public byte[]           szUploadName = new byte[128];
    /**
     * 文件上传路径
    */
    public byte[]           szFilePath = new byte[256];

    public NET_OUT_GET_UPLOAD_PATH_INFO() {
        this.dwSize = this.size();
    }
}

