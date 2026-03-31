package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_FaceRecognitionGroupReAbstract 接口输出参数
*/
public class NET_OUT_FACE_RECOGNITION_GROUP_REABSTRACT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小
    */
    public int              dwSize;
    /**
     * 重建令牌，可以用该令牌来中止重建
    */
    public int              nToken;

    public NET_OUT_FACE_RECOGNITION_GROUP_REABSTRACT_INFO() {
        this.dwSize = this.size();
    }
}

