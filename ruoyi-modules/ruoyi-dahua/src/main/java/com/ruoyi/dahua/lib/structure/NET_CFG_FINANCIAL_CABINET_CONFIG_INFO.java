package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  金融业务库配置 对应枚举 NET_EM_CFG_FINANCIAL_CABINET_CONFIG 
* @date 2022/08/10 13:54:52
*/
public class NET_CFG_FINANCIAL_CABINET_CONFIG_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
舱位数量
*/
    public			int            nCabinNum;
/** 
金库门设置
*/
    public			NET_FINANCIAL_CABINET_VAULTDOOR stuVaultDoor = new NET_FINANCIAL_CABINET_VAULTDOOR();
/** 
是否开启远程审批
*/
    public			int            bRemoteApprovalEnable;
/** 
排班时间
*/
    public			NET_FINANCIAL_CABINET_WORKTIME stuWorkTime = new NET_FINANCIAL_CABINET_WORKTIME();
/** 
验证模式 0: 人脸或信息 1: 人脸+信息
*/
    public			int            nIdentifyMode;
/** 
自动布撤防
*/
    public			NET_FINANCIAL_CABINET_AUTOARM stuAutoArm = new NET_FINANCIAL_CABINET_AUTOARM();
/** 
短信推送
*/
    public			NET_FINANCIAL_CABINET_MESSAGE stuMobileMessage = new NET_FINANCIAL_CABINET_MESSAGE();
/** 
互锁使能
*/
    public			int            bABLockEnable;
/** 
现金数额使能
*/
    public			int            bCashAmountEnable;
/** 
多舱位权限使能
*/
    public			int            bCabinsEnable;
/** 
录屏使能
*/
    public			int            bRecordScreenEnable;
/** 
冻结业务库使能
*/
    public			int            bFreezeEnable;

public NET_CFG_FINANCIAL_CABINET_CONFIG_INFO(){
		this.dwSize=this.size();
}
}

