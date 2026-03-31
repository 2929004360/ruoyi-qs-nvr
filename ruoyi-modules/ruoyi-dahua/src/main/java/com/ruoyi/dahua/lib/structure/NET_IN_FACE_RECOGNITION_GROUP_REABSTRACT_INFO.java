package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_FaceRecognitionGroupReAbstract 接口输入参数
*/
public class NET_IN_FACE_RECOGNITION_GROUP_REABSTRACT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小
    */
    public int              dwSize;
    /**
     * 人员组个数
    */
    public int              nGroupNum;
    /**
     * 人员组ID
    */
    public BYTE_ARRAY_64[]  szGroupId = new BYTE_ARRAY_64[128];

    public NET_IN_FACE_RECOGNITION_GROUP_REABSTRACT_INFO() {
        this.dwSize = this.size();
    }
}

