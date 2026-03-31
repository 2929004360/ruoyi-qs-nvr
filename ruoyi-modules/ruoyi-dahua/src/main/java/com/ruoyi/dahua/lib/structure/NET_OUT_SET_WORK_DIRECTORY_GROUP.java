package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_SetWorkDirectoryGoup 接口输出参数
*/
public class NET_OUT_SET_WORK_DIRECTORY_GROUP extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;

    public NET_OUT_SET_WORK_DIRECTORY_GROUP() {
        this.dwSize = this.size();
    }
}

