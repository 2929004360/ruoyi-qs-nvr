package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  环境温度信息配置(热成像内部配置,只可读,不可设),对应的枚举 NET_EM_CFG_ENVIRONMENT_THERM_INFO 
* @date 2022/08/30 17:09:25
*/
public class NET_CFG_ENVIRONMENT_THERM_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
环境温度,精度0.01,实际值扩大100倍,只可读,不可设
*/
    public			int            nEnvironmentTemp;
/** 
环境温度最后一次保存的时间,只可读,不可设
*/
    public NET_TIME         stuLastEnvSaveTime = new NET_TIME();
/** 
环境温度保存的次数,只可读,不可设
*/
    public			int            nEnvSaveCnt;
/** 
字节对齐
*/
    public			byte[]         byReserved = new byte[4];

public NET_CFG_ENVIRONMENT_THERM_INFO(){
		this.dwSize=this.size();
}
}

