package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_MatrixGetCameraAllByGroup 接口输入参数
*/
public class NET_IN_GET_CAMERA_ALL_BY_GROUP extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 输入通道类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_CAMERA_CHANNEL_TYPE}
    */
    public int              emChannelType;

    public NET_IN_GET_CAMERA_ALL_BY_GROUP() {
        this.dwSize = this.size();
    }
}

