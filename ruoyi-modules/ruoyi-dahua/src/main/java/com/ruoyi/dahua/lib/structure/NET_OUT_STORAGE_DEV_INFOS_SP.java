package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_QueryDevInfo , NET_QUERY_DEV_STORAGE_INFOS_SP接口输出参数
*/
public class NET_OUT_STORAGE_DEV_INFOS_SP extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 获取到设备的存储模块信息列表
    */
    public int              nDevInfosNum;
    /**
     * 设备的存储模块信息列表,用户赋值
    */
    public int              nMaxDevInfosNum;
    /**
     * 设备信息列表,DH_STORAGE_DEVICE的dwsize需赋值,用户申请内存,参见结构体定义 {@link NET_STORAGE_DEVICE}
    */
    public Pointer          pstuStoregeDevInfos;

    public NET_OUT_STORAGE_DEV_INFOS_SP() {
        this.dwSize = this.size();
    }
}

