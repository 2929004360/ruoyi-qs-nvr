package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetFaceRecognitionGroupSpaceInfo 接口输出参数
*/
public class NET_OUT_GET_FACE_RECOGNITION_GROUP_SPACE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 人脸库总空间大小，单位MB
    */
    public int              nTotalSize;
    /**
     * 人脸库剩余空间大小，单位MB
    */
    public int              nFreeSize;
    /**
     * 人脸库可导入总条数
    */
    public int              nTotalCount;
    /**
     * 人脸库剩余可导入条数
    */
    public int              nFreeCount;

    public NET_OUT_GET_FACE_RECOGNITION_GROUP_SPACE_INFO() {
        this.dwSize = this.size();
    }
}

