package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
CLIENT_CreateSubBusinessModule 接口输入参数
*/
public class NET_IN_CREAT_SUB_BUSINESS_MDL_INFO extends NetSDKLib.SdkStructure {
/** 
/< 结构体大小
*/
    public			int            dwSize;
/** 
/< 连接方式  {@link com.ruoyi.dahua.lib.enumeration.EM_SUBLINK_TYPE}
*/
    public			int            emLinkType;
/** 
/< bsid
*/
    public			byte[]         szBSID = new byte[64];
/** 
/< 设备网络信息, emLinkType 为 EM_SUBLINK_TYPE_PASSIVE(直连)方式时有效
*/
    public			NET_DEV_NETWORK_INFO stuDevNetInfo = new NET_DEV_NETWORK_INFO();
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

public NET_IN_CREAT_SUB_BUSINESS_MDL_INFO(){
    this.dwSize=this.size();
}
}

