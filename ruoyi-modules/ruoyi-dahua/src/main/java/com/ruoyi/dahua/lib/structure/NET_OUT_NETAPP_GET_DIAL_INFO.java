package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * EM_PRC_NETAPP_TYPE_GET_DIAL_INFO 出参
*/
public class NET_OUT_NETAPP_GET_DIAL_INFO extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 拨号IP地址
    */
    public byte[]           szIP = new byte[48];
    /**
     * 子网掩码
    */
    public byte[]           szSubnetMask = new byte[40];
    /**
     * 默认网关
    */
    public byte[]           szDefaultGateway = new byte[40];
    /**
     * DNS服务器地址
    */
    public BYTE_ARRAY_40[]  szDnsServers = new BYTE_ARRAY_40[2];
    /**
     * 拨号IPv6地址
    */
    public byte[]           szIPv6Address = new byte[48];
    /**
     * Pv6默认网关
    */
    public byte[]           szIPv6DefaultGateway = new byte[40];
    /**
     * IPv6网络前缀 [1~128]
    */
    public int              nIPv6Prefix;
    /**
     * IPv6 DNS服务器地址
    */
    public BYTE_ARRAY_40[]  szIPv6DnsServers = new BYTE_ARRAY_40[2];
    /**
     * IPv6链路本地地址
    */
    public byte[]           szIPv6LinkLocalAddress = new byte[48];
    /**
     * 服务提供商 3G网络使用
    */
    public byte[]           szVendor = new byte[64];
    /**
     * 网络类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_NET_NETWORK_CARD_TYPE}
    */
    public int              emNetwork;
    /**
     * 拨号状态: 0:未知, 1:拨号成功, 2:拨号中, 3:拨号失败
    */
    public int              nState;

    public NET_OUT_NETAPP_GET_DIAL_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < szDnsServers.length; i++){
            szDnsServers[i] = new BYTE_ARRAY_40();
        }
        for(int i = 0; i < szIPv6DnsServers.length; i++){
            szIPv6DnsServers[i] = new BYTE_ARRAY_40();
        }
    }
}

