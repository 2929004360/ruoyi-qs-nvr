 package com.ruoyi.dahua.lib.structure;
 import com.ruoyi.dahua.lib.NetSDKLib;
 /**
  * @author 291189
  * @description  网桥
  * @date 2021/10/29
  */
public class CFG_NETWORK_BR_INTERFACE extends NetSDKLib.SdkStructure {
/** 网络接口名称*/
    public			byte[]         szName = new byte[128];
/** 使能*/
    public			int            bEnable;
/** 网络最大传输单元*/
    public			int            nMTU;
/** 物理网口成员*/
    public			byte[]         szMembers = new byte[16*16];
/** ip地址*/
    public			byte[]         szIP = new byte[256];
/** 子网掩码*/
    public			byte[]         szSubnetMask = new byte[256];
/** 默认网关*/
    public			byte[]         szDefGateway = new byte[256];
/** DNS服务器地址*/
    public			byte[]         szDnsServers = new byte[2*256];
/** 是否开启DHCP*/
    public			int            bDhcpEnable;
/** DHCP失败时是否使用保留IP，使用保留IP时还继续发DHCP请求*/
    public			int            bReservedIPEnable;
/** DNS获取方式，dhcp使能时可以设置为true，支持通过dhcp获取*/
    public			int            bDnsAutoGet;
}

