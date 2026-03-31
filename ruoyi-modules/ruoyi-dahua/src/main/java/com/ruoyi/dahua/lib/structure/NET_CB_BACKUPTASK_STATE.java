package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;
/**
 * 
 * @author 119178
 *
 */
public class NET_CB_BACKUPTASK_STATE extends SdkStructure {
    public NET_BACKUP_STATES_INFO[] stuStates = (NET_BACKUP_STATES_INFO[])new NET_BACKUP_STATES_INFO().toArray(1024); // 备份状态信息
    public int              nStatesNum;                           // 备份状态信息个数
	//public NET_CB_STATES[]		stuStates =(NET_CB_STATES[])new NET_CB_STATES().toArray(1024);				// 备份状态信息
    public byte[]           bReserved = new byte[1020];           // 预留字节数
}

