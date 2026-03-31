package com.ruoyi.dahua.lib.enumeration;
/**
 * @author 251823
 * @description 网络应用组件 操作类型
 * @date 2021/09/01
 */
public enum EM_RPC_NETAPP_TYPE {
	/**
     *获得网络资源统计, pstuIn 对应 NET_IN_NETAPP_GET_NET_RESOURCE_STAT*,pstuOut对应 NET_OUT_NETAPP_GET_NET_RESOURCE_STAT*
     */
    EM_RPC_NETAPP_TYPE_GET_NET_RESOURCE_STAT(0),
    /**
     * 获取3G网络的信号强度, pstuIn 对应 NET_IN_NETAPP_GET_MOBILE_RSSI*, pstuOut 对应 NET_OUT_NETAPP_GET_MOBILE_RSSI*
      */
    EM_RPC_NETAPP_TYPE_GET_MOBILE_RSSI(1),
    /**
     * 获取所有移动网络接口, pstuIn 对应 NET_IN_NETAPP_GET_MOBILE_INTERFACE*,pstuOut对应 NET_OUT_NETAPP_GET_MOBILE_INTERFACE*
     */
    EM_PRC_NETAPP_TYPE_GET_MOBILE_INTERFACE(2),
    /**
     * 获取拨号信息, pstuIn 对应 NET_IN_NETAPP_GET_DIAL_INFO*,pstuOut对应 NET_OUT_NETAPP_GET_DIAL_INFO*
     */
    EM_PRC_NETAPP_TYPE_GET_DIAL_INFO(3);

    private final int id;

    private EM_RPC_NETAPP_TYPE(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }
}

