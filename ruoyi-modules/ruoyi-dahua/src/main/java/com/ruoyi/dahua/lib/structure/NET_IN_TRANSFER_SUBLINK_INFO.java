package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
CLIENT_TransferSubLinkInfo 接口输入参数
*/
public class NET_IN_TRANSFER_SUBLINK_INFO extends NetSDKLib.SdkStructure {
/** 
/< 结构体大小
*/
    public			int            dwSize;
/** 
/< 连接方式 {@link com.ruoyi.dahua.lib.enumeration.EM_SUBLINK_TYPE }
*/
    public			int            emLinkType;
/** 
/< 会话ID
*/
    public			byte[]         szBSID = new byte[64];
/** 
/< 本地服务信息, emLinkType为EM_SUBCONNECT_TYPE_PORT(主动注册)方式时有效
*/
    public NET_LOCAL_SERVER_NET_INFO stuLoaclServerInfo = new NET_LOCAL_SERVER_NET_INFO();
/** 
连接模式 {@link com.ruoyi.dahua.lib.enumeration.EM_SUBLINK_MODE}
*/
    public			int            emLinkMode;
    /**
     * Access Key
    */
    public byte[]           szAK = new byte[32];
    /**
     * Secret Access Key
    */
    public byte[]           szSK = new byte[64];
    /**
     * 哈希算法，目前可填写 MD5
    */
    public byte[]           szHash = new byte[32];

public NET_IN_TRANSFER_SUBLINK_INFO(){
    this.dwSize=this.size();
}
}

