 package com.ruoyi.dahua.lib.structure;


 import com.ruoyi.dahua.lib.NetSDKLib;
 /**
  * @author 291189
  * @description  绑定虚拟网口
  * @date 2021/10/29
  */
public class CFG_NETWORK_BOND_INTERFACE extends NetSDKLib.SdkStructure {
/** 是否绑定虚拟网口，只有网卡名是bondxx时，才允许有Bonding字段，其它网卡不能用*/
    public			int            bBonding;
/** 网卡绑定模式 CFG_ENUM_NET_BOND_MODE */
    public			int            emMode;
/** 802.3ad链路聚合控制方式 CFG_ENUM_NET_BOND_LACP*/
    public			int            emLacp;
/** 网络最大传输单元*/
    public			int            nMTU;
/** 物理网口成员*/
    public			byte[]         szMembers = new byte[16*16];
/** ip地址*/
    public			byte[]         szIP = new byte[256];
/** 网络接口名称*/
    public			byte[]         szName = new byte[128];
/** 网络接口名称*/
    public			byte[]         szAlias = new byte[128];
/** DNS服务器地址*/
    public			byte[]         szDnsServers = new byte[2*256];
/** mac地址*/
    public			byte[]         szMacAddress = new byte[256];
/** 子网掩码*/
    public			byte[]         szSubnetMask = new byte[256];
/** 默认网关*/
    public			byte[]         szDefGateway = new byte[256];
/** 是否开启DHCP*/
    public			int            bDhcpEnable;
}

