package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  金库门设置 
* @date 2022/08/10 13:54:51
*/
public class NET_FINANCIAL_CABINET_VAULTDOOR extends NetSDKLib.SdkStructure {
/** 
金库门开锁时需要开锁的动态密码锁数量
*/
    public			int            nDynamicPasswordLockNum;
/** 
金库门开锁时需要开锁的电控锁数量
*/
    public			int            nElectronicLockNum;
/** 
机械锁数量
*/
    public			int            nMechanicalLockNum;
/** 
总开锁数量
*/
    public			int            nTotalLockNum;
/** 
闭锁时间, 单位秒
*/
    public			int            nCloseTime;
/** 
预留字节
*/
    public			byte[]         szReserverd = new byte[252];

public			NET_FINANCIAL_CABINET_VAULTDOOR(){
}
}

