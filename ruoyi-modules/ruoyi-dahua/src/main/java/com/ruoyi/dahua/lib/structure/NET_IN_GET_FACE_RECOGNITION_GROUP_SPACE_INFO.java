package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetFaceRecognitionGroupSpaceInfo 接口输入参数
*/
public class NET_IN_GET_FACE_RECOGNITION_GROUP_SPACE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;

    public NET_IN_GET_FACE_RECOGNITION_GROUP_SPACE_INFO() {
        this.dwSize = this.size();
    }
}

