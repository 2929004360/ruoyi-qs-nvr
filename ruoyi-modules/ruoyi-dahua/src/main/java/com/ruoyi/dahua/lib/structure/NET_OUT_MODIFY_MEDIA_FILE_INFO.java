package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_ModifyMediaFile 接口输出参数
*/
public class NET_OUT_MODIFY_MEDIA_FILE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 返回结果个数
    */
    public int              nResultCodeNum;
    /**
     * 返回结果，是一个数组，与输入Id对应,0 C 修改成功,1 - 修改失败
    */
    public int[]            nResultCode = new int[32];

    public NET_OUT_MODIFY_MEDIA_FILE_INFO() {
        this.dwSize = this.size();
    }
}

