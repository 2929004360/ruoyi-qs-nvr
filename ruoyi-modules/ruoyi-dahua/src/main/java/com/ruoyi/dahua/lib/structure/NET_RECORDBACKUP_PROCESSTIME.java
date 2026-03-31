package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;

public class NET_RECORDBACKUP_PROCESSTIME extends SdkStructure {
    public NET_TIME_EX1     stuStartTime;                         // 开始时间
    public NET_TIME_EX1     stuEndTime;                           // 结束时间
    public byte[]           bReserved = new byte[64];             // 保留字段
}

