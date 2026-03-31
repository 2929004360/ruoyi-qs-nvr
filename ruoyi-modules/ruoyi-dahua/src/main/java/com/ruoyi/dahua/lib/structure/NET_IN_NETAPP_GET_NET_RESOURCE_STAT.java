package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * EM_RPC_NETAPP_TYPE_GET_NET_RESOURCE_STAT 入参
*/
public class NET_IN_NETAPP_GET_NET_RESOURCE_STAT extends NetSDKLib.SdkStructure
{
    public int              dwSize;

    public NET_IN_NETAPP_GET_NET_RESOURCE_STAT() {
        this.dwSize = this.size();
    }
}

