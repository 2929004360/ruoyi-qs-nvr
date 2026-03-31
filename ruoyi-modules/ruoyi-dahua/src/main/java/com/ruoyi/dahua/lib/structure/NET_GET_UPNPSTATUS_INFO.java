package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 获取UPnP映射状态(对应DH_DEVSTATE_GET_UPNP_STATUS)
*/
public class NET_GET_UPNPSTATUS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 是否正在工作
    */
    public int              bWorking;
    /**
     * 状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_UPNP_STATUS_TYPE}
    */
    public int              emStatus;
    /**
     * 内部IP
    */
    public byte[]           szInnerAddress = new byte[64];
    /**
     * 外部IP
    */
    public byte[]           szOuterAddress = new byte[64];
    /**
     * 申请的端口映射状态个数
    */
    public int              nMaxPortMapStatus;
    /**
     * 实际返回的端口映射状态个数
    */
    public int              nReturnNum;
    /**
     * 端口映射状态信息指针，由调用者分配内存,分配的大小为 nMaxPortMapStatus*sizeof(EM_PORTMAP_STATUS_TYPE),参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_PORTMAP_STATUS_TYPE}
    */
    public Pointer          pemPortMapStatus;

    public NET_GET_UPNPSTATUS_INFO() {
        this.dwSize = this.size();
    }
}

