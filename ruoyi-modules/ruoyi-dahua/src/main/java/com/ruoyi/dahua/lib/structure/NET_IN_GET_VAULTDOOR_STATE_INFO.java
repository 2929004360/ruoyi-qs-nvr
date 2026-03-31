package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description   CLIENT_GetVaultDoorState 的输入参数 
* @date 2022/08/10 11:38:37
*/
public class NET_IN_GET_VAULTDOOR_STATE_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;

public NET_IN_GET_VAULTDOOR_STATE_INFO(){
		this.dwSize=this.size();
}
}

