 package com.ruoyi.dahua.lib.structure;


 import com.ruoyi.dahua.lib.NetSDKLib;
 /** 叠加元素 */
public class NET_CFG_VSP_LXSJ_ELEMENT extends NetSDKLib.SdkStructure {
/** 名称类型 NET_EM_LXSJ_ELEM_NAMETYPE*/
    public			int            emNameType;
/** 名称*/
    public			byte[]         szName = new byte[32];
/** 前缀字符串*/
    public			byte[]         szPrefix = new byte[64];
/** 后缀字符串*/
    public			byte[]         szPostfix = new byte[64];
/** 后面添加分隔符个数*/
    public			int            nSeperaterCount;
/** 预留*/
    public			byte[]         byReserved = new byte[856];
}

