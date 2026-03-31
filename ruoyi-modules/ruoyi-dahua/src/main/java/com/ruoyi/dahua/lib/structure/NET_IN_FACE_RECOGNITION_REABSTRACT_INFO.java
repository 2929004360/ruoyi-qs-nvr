package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_FaceRecognitionReAbstract 接口输入参数
*/
public class NET_IN_FACE_RECOGNITION_REABSTRACT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小
    */
    public int              dwSize;
    /**
     * 重新建模的人员个数
    */
    public int              nPersonNum;
    /**
     * 重新建模的人员信息,参见结构体定义 {@link NET_REABSTRACT_PERSON_INFO}
    */
    public Pointer          pstReAbstractPersonInfo;

    public NET_IN_FACE_RECOGNITION_REABSTRACT_INFO() {
        this.dwSize = this.size();
    }
}

