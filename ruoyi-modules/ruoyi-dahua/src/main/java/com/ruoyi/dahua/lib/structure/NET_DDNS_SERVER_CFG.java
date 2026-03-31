package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 多ddns配置结构体
*/
public class NET_DDNS_SERVER_CFG extends NetSDKLib.SdkStructure
{
    /**
     * ddns服务器id号
    */
    public int              dwId;
    /**
     * 使能,同一时间只能有一个ddns服务器处于使能状态
    */
    public int              bEnable;
    /**
     * 服务器类型,希网..
    */
    public byte[]           szServerType = new byte[32];
    /**
     * 服务器ip或者域名
    */
    public byte[]           szServerIp = new byte[256];
    /**
     * 服务器端口
    */
    public int              dwServerPort;
    /**
     * dvr域名,如jeckean.3322.org
    */
    public byte[]           szDomainName = new byte[256];
    /**
     * 用户名
    */
    public byte[]           szUserName = new byte[64];
    /**
     * 密码
    */
    public byte[]           szUserPsw = new byte[32];
    /**
     * 服务器别名
    */
    public byte[]           szAlias = new byte[32];
    /**
     * DDNS 保活时间
    */
    public int              dwAlivePeriod;
    /**
     * 域名模式:0-手动输入域名,szDomainName有效; 1-默认域名szDefaultDomainName有效。
    */
    public byte             ByMode;
    /**
     * 默认域名,只读
    */
    public byte[]           szDefaultDomainName = new byte[60];
    /**
     * 字节对齐
    */
    public byte[]           bReserved = new byte[3];
    /**
     * 邮箱用户名
    */
    public byte[]           szEmailUserName = new byte[64];
    public byte[]           reserved = new byte[128];

    public NET_DDNS_SERVER_CFG() {
    }
}

