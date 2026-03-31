package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description EM_PRC_NETAPP_TYPE_GET_MOBILE_INTERFACE 出参
 * @date 2021/9/17
 */
public class NET_OUT_NETAPP_GET_MOBILE_INTERFACE extends NetSDKLib.SdkStructure {
	/**
     * 结构体大小
     */
    public int              dwSize;
    /**
     *  网络接口有效个数
     */
    public int              nInterfaceNum;
    /**
     *  移动网络接口信息
     */
    public NETDEV_NETINTERFACE_INFO[] stuInterface = (NETDEV_NETINTERFACE_INFO[]) new NETDEV_NETINTERFACE_INFO().toArray(64);

    public NET_OUT_NETAPP_GET_MOBILE_INTERFACE(){
        this.dwSize = this.size();
    }
}

