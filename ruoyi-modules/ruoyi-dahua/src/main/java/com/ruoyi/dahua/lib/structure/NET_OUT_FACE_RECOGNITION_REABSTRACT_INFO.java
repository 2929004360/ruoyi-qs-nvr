package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_FaceRecognitionReAbstract 接口输出参数
*/
public class NET_OUT_FACE_RECOGNITION_REABSTRACT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小
    */
    public int              dwSize;

    public NET_OUT_FACE_RECOGNITION_REABSTRACT_INFO() {
        this.dwSize = this.size();
    }
}

