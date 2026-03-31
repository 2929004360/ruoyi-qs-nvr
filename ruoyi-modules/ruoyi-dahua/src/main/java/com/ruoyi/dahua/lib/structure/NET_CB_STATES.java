package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;
/**
 * 
 * @author 119178
 *
 */
public class NET_CB_STATES extends SdkStructure {
    public int              nProgress;                            // 备份进度
    public int              emState;                              // 备份状态值EM_CB_STATES
    public byte[]           szDeviceName = new byte[64];          // 备份的设备名称
    public byte[]           byReserved = new byte[256];           // 保留字节
}

