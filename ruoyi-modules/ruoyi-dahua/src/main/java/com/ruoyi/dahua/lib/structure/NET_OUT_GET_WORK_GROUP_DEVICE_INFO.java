package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_GetWorkGroupDeviceInfos 接口输出参数
*/
public class NET_OUT_GET_WORK_GROUP_DEVICE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 存储设备信息,由用户申请内存，大小为sizeof(DH_STORAGE_DEVICE)*nMaxCount,参见结构体定义 {@link NET_STORAGE_DEVICE}
    */
    public Pointer          pstuDeviceInfo;
    /**
     * 存储设备信息的个数, 最大为128个
    */
    public int              nMaxCount;
    /**
     * 返回的存储设备信息的个数, 最大为128个
    */
    public int              nReturnCount;

    public NET_OUT_GET_WORK_GROUP_DEVICE_INFO() {
        this.dwSize = this.size();
    }
}

