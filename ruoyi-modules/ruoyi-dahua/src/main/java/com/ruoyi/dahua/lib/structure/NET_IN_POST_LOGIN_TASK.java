package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_PostLoginTask 输入参数
*/
public class NET_IN_POST_LOGIN_TASK extends NetSDKLib.SdkStructure
{
    /**
     * 赋值为结构体大小
    */
    public int              dwSize;
    /**
     * 保留字段1
    */
    public byte[]           szReserve1 = new byte[4];
    /**
     * 设备ip
    */
    public Pointer          szIp;
    /**
     * 登陆端口
    */
    public int              nPort;
    /**
     * 保留字段2
    */
    public byte[]           szReserve2 = new byte[4];
    /**
     * 用户名
    */
    public Pointer          szName;
    /**
     * 密码
    */
    public Pointer          szPwd;
    /**
     * 登录方式，目前只支持 EM_LOGIN_SPEC_CAP_TCP、, EM_LOGIN_SPEC_CAP_MAIN_CONN_ONLY、EM_LOGIN_SPEC_CAP_P2P,参见枚举定义 {@link NetSDKLib.EM_LOGIN_SPAC_CAP_TYPE}
    */
    public int              emSpecCap;
    /**
     * 当emSpecCap为EM_LOGIN_SPEC_CAP_TCP时，需要获取的配置类型,此时获取的配置和拉流相关, 当为EM_TCP_LOGIN_CONFIG_TYPE_UNKNOWN时，码流通过TCP传输，为EM_TCP_LOGIN_CONFIG_TYPE_MULTICAST时，码流通过组播传输,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_TCP_LOGIN_CONFIG_TYPE}
    */
    public int              emConfigType;
    /**
     * 登陆结果回调,参见回调函数定义 {@link NetSDKLib.fPostLoginTask}
    */
    public NetSDKLib.fPostLoginTask cbLogin;
    /**
     * 回调用户参数
    */
    public Pointer          pUser;
    /**
     * 是否高安全等级登陆
    */
    public int              bHighLevelSecurity;
    /**
     * 登录的TLS模式，目前仅支持EM_LOGIN_SPEC_CAP_TCP模式下的 tls登陆(TLS加密优先使用该选项),参见枚举定义 {@link NetSDKLib.EM_LOGIN_TLS_TYPE}
    */
    public int              emTLSCap;
    /**
     * 预留字段
    */
    public byte[]           byReserved = new byte[128];

    public NET_IN_POST_LOGIN_TASK() {
        this.dwSize = this.size();
    }
}

