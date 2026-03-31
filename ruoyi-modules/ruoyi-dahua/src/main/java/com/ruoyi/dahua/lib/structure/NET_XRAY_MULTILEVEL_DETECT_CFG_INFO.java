package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
* @author 291189
* @description  安全等级信息 
* @date 2022/12/01 16:22:06
*/
public class NET_XRAY_MULTILEVEL_DETECT_CFG_INFO extends NetSDKLib.SdkStructure {
/** 
当前配置等级 {@link com.ruoyi.dahua.lib.enumeration.EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL}
*/
    public			int            emCurrentLevel;
/** 
等级配置最大个数（用户配置）
*/
    public			int            nMultiLevelConfigMax;
/** 
等级配置个数（实际返回）
*/
    public			int            nMultiLevelConfigCnt;
/** 
等级配置 此部分内存需要用户申请,申请大小为(nMultiLevelConfigMax*sizeof(NET_MULTI_LEVEL_INFO))。
*/
    public Pointer          pstuMultiLevelConfig;
/** 
预留
*/
    public			byte[]         szReserved = new byte[1024];

public NET_XRAY_MULTILEVEL_DETECT_CFG_INFO(){
}
}

