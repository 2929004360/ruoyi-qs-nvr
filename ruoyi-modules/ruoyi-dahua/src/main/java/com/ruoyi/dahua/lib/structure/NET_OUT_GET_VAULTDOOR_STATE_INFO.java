package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description   CLIENT_GetVaultDoorState 的输出参数 
* @date 2022/08/10 11:46:30
*/
public class NET_OUT_GET_VAULTDOOR_STATE_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
锁状态 {@link com.ruoyi.dahua.lib.enumeration.EM_VAULT_LOCKSTATE}
*/
    public			int            emLockState;
/** 
门状态 {@link com.ruoyi.dahua.lib.enumeration.EM_VAULT_DOORSTATE}
*/
    public			int            emDoorState;

public NET_OUT_GET_VAULTDOOR_STATE_INFO(){
		this.dwSize=this.size();
}
}

